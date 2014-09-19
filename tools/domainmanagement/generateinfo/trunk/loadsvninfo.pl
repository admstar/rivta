:- module(loadsvninfo, [
	      sv_get_domain/1,
	      sv_get_all_domains/1,
	      sv_get_interaction/7,
	      sv_get_latest_tkb_info/7,
	      sv_get_tkb_info/7 ,
	      sv_load_svninfo/1 ,
	      sv_verify/2
	  ]).

:- use_module(leolib).


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Iterates through an ServiceInteractions/riv directory tree and extract
and store information.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
sv_load_svninfo(Rootdir) :-
	\+ exists_directory(Rootdir) ->  l_write_trace([Rootdir, ' does not exist!'],0) , fail ; true ,
	l_erase_all(svnInfo) ,
	l_path_to_rpath(Rootdir, Rpath) ,
	record_files(Rpath, _, _) .

% -----------------------------------------------------------------------

sv_get_domain(Domain) :-
	recorded(_,svnDomain(domain(Domain))) .

sv_get_all_domains(Domains) :-
	setof(Domain, sv_get_domain(Domain), Domains) .

% -----------------------------------------------------------------------
% Must change to only return newest TKB
sv_get_latest_tkb_info(InDomain, InTag, OutTkbLink, OutTkbDate, OutTkbDescription, OutLongSwedish, OutShortSwedish) :-
	nonvar(InDomain),
	setof(
	    [TkbDate, InDomain, InTag, TkbLink, TkbDescription, LongSwedish, ShortSwedish] ,
	    sv_get_tkb_info(InDomain, InTag, TkbLink, TkbDate, TkbDescription, LongSwedish, ShortSwedish) ,
	    TkbInfoList ) ,
	reverse(TkbInfoList,
		[[OutTkbDate, InDomain, InTag, OutTkbLink, OutTkbDescription, OutLongSwedish, OutShortSwedish ] | _Rest ]) .


sv_get_tkb_info(Domain, Tag, TkbLink2, TkbDate, TkbDescription, LongSwedish, ShortSwedish) :-
	recorded(svnInfo,
		 svnTkb(
		     FilePath,
		     TkbDescription ,
		     svnInfo(_Rev, TkbDate) ,
		     domain(Domain, Tag) ,
		     swedishNames(LongSwedish, ShortSwedish)
		 )
		) ,
	svnRootDir(RootDir) ,
	atom_concat(RootDir, RestName, FilePath),
	atom_concat('http://rivta.googlecode.com/svn/ServiceInteractions/riv', RestName, TkbLink1) ,
	l_urlencode(TkbLink1, TkbLink2) .

% -----------------------------------------------------------------------

sv_get_interaction(Service, SVersion, RivVersion, Description, Domain, DTag, Date) :-
	recorded(_,svnInteraction(interaction(Service, SVersion, RivVersion, Description),domain(Domain, DTag),svnInfo(_, Date))) .

% -----------------------------------------------------------------------

sv_verify(domains, No) :-
	setof(Domain,
	      recorded(svnInfo, svnDomain(domain(Domain))) ,
	      List) ,
	length(List, No) .

sv_verify(tkb, No) :-
	setof(struct(A,B,C,D,E),
%	      sv_get_tkb_info(A,B,C,D,E,F,G),
	      recorded(svnInfo,
		       svnTkb(
			   A ,
			   B ,
			   C ,
			   D ,
			   E
		       )
		      ) ,
	      List) ,
	length(List, No) .

sv_verify(interactions, No) :-
	setof(struct(A,B,C,D,E,F,G),
	      sv_get_interaction(A,B,C,D,E,F,G),
	      List) ,
	length(List, No) .



/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
There is a number of clauses to handle different kind of folders and
files
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
% Manage TKB files ---------------------------------
% Ensure we are in a docs folder
record_files([ docs | RpathRest], DomainList, Dtag) :-
	nonvar(Dtag),
	Rpath = [ docs | RpathRest],
	l_ls(Rpath, ItemList) ,
	member(File, ItemList),
	sub_atom(File, _Start, _End, Remaining, '.doc'),
	member(Remaining, [0, 1]) , % Ensure match för "doc" and "docx"
	sub_atom(File, 0, 1, _Rem, 'T'),
	l_path_to_rpath(Filepath, [File| Rpath]),
	get_svn_info(Filepath, svnInfo(Revision, Date)) ,
	get_tkb_file_info(DomainList, Dtag, Filepath, Date, Description, SwedishNames) ,
	l_write_trace(['TKB: ', File, DomainList, Dtag, svnInfo(Revision, Date), Description, SwedishNames], 1) ,
	store_tkb(svnTkb(
		      Filepath,
		      Description ,
		      svnInfo(Revision, Date) ,
		      domain(DomainList, Dtag) ,
		      SwedishNames
		  )
		 ) .


% This clause manage WSDL files ---------------------
record_files(Rpath, DomainList, Dtag) :-
	nonvar(Dtag),
	Rpath = [ _InteractionName, interactions, schemas | _Rest] ,
	l_ls(Rpath, ItemList) ,
	member(File, ItemList),
	sub_atom(File, _Before, _Len, _After, 'Interaction'),
	sub_atom(File, _Before2, _Len2, 0, '.wsdl'),
	l_write_trace(['WDSL file identfied: ', File, DomainList], 2),
	l_path_to_rpath(Filepath, [File | Rpath]),
	get_svn_info(Filepath, SvnInfo) ,
	wsdl_to_interaction(File, Interaction, IVersion, RivVersion) ,
	wsdl_description(Filepath, Description),
	l_write_trace(['WDSL file: ', File, Interaction, IVersion, RivVersion, Description], 2),
	store_wsdl(File, svnInteraction(interaction(Interaction, IVersion, RivVersion, Description), domain(DomainList, Dtag), SvnInfo)) .

% Manage a tags folder --------------------------------
record_files([ TagName , tags | Rpath], DomainList, Dtag) :-
	var(Dtag) ,
	! ,
	l_ls([TagName, tags | Rpath], Itemlist) ,
	member(Item, Itemlist),
	record_files([Item, TagName, tags | Rpath], DomainList, tag(TagName)) .

% Manage a branches folder -----------------------------
record_files([ TagName , branches | Rpath], DomainList, Dtag) :-
	var(Dtag),
	! ,
	l_ls([TagName, branches | Rpath], Itemlist) ,
	member(Item, Itemlist),
	record_files([Item, TagName, branches | Rpath], DomainList, branch(TagName)) .

% Manage a trunk folder - --------------------------------
record_files([ trunk | Rpath], DomainList, Dtag) :-
	var(Dtag),
	! ,
	l_ls([trunk | Rpath], Itemlist) ,
	member(Item, Itemlist),
	record_files([Item, trunk | Rpath], DomainList, trunk) .

% This clause identifies a domain by looking at the subfolders
record_files(Rpath, DomainList, Dtag) :-
	var(DomainList),
	var(Dtag),
	l_ls(Rpath, Itemlist) ,
	l_write_trace(['record_files2 - Itemlist: ', Itemlist], 4),
	member(trunk, Itemlist),
	member(tags, Itemlist),
	\+ excluded_domain(Rpath) ,
	l_write_trace(['Domain identified: ', Rpath], 2),
	get_domain_name(Rpath, DomainList),
	store_domain(DomainList),
	! ,
	member(Item, Itemlist),
	record_files([Item|Rpath], DomainList, Dtag) ,
	fail.

% This clause is responsible for the iteration down the directory tree
record_files(Rpath, DomainList, DTag) :-
	l_ls(Rpath, Itemlist) ,
	l_write_trace(['record_files3 - Itemlist: ', Itemlist], 4),
	member(Item, Itemlist),
	l_write_trace(['record_files3 - Path: ', Item, Rpath], 3),
	record_files([Item | Rpath], DomainList, DTag) ,
	fail.

% Finally, to succed at the end.
record_files(_,_,_) .

excluded_domain([headers, interoperability | _Rest]) .

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Store information about a WSDL file
There will be one entry for each WSDL files found
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
store_wsdl(File,
	   svnInteraction(
	       interaction(
		   Interaction,
		   IVersion,
		   RivVersion,
		   Description),
	       domain(
		   Domain,
		   Type),
	       svnInfo(
		   Revision,
		   Date)
	   )) :-
	nonvar(Interaction) ,
	nonvar(IVersion) ,
	nonvar(RivVersion) ,
	nonvar(Description) ,
	nonvar(Domain) ,
	nonvar(Type) ,
	nonvar(Revision) ,
	nonvar(Date) ,
	! ,
	l_write_trace(['store_wsdl - File: ', File], 4),
	recordz(svnInfo,
		svnInteraction(
		    interaction(
			Interaction,
			IVersion,
			RivVersion,
			Description),
		    domain(
			Domain,
			Type),
		    svnInfo(
			Revision,
			Date))) ,
	l_write_trace(['store_wsdl: ', File], 4) ,
	l_write_trace(['store_wsdl recorded: ', svnInteraction(interaction(Interaction, IVersion, RivVersion, Description), domain(Domain,Type), svnInfo(Revision, Date))], 4) .

store_wsdl(File, SvnInfo) :-
	l_write_trace(['store_wsdl - error : ',SvnInfo, File, nl], 0) .


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Store information about a domain found in svn.
There will be one entry for each domain.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
store_domain(DomainList) :-
	l_write_trace(['store_domain: ', DomainList], 4),
	recordz(svnInfo, svnDomain(domain(DomainList))) .

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Store information about a TKB.
There will be one entry for each TKB found.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
store_tkb(svnTkb(
	      File,
	      Description ,
	      SvnInfo ,
	      domain(DomainList, Dtag),
	      SwedishNames
	  )
	 ) :-
	  nonvar(File) ,
	  nonvar(Description) ,
	  nonvar(SvnInfo) ,
	  nonvar(DomainList),
	  nonvar(Dtag) ,
	  nonvar(SwedishNames) ,
	  ! ,
	  recordz(svnInfo,
		  svnTkb(
		      File,
		      Description ,
		      SvnInfo ,
		      domain(DomainList, Dtag) ,
		      SwedishNames
		  )
		 ) .

store_tkb(SvnTkb) :-
	l_write_trace(['store_tkb - error : ',SvnTkb, nl], 0) .


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Extract interaction name, version and RIV-version from a WSDL file name
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
wsdl_to_interaction(WSDL_file_name, Interaction, IVersion, RivVersion) :-
	atomic_list_concat([Interaction | [Rest]], 'Interaction', WSDL_file_name ) ,
	atomic_list_concat([_, IVersion , RivVersionandWSDL], '_', Rest ) ,
	atom_concat(RivVersionLong, '.wsdl', RivVersionandWSDL) ,
	wsdl_to_interaction2(RivVersion, RivVersionLong) ,
	! .

wsdl_to_interaction(WSDL_file_name, _, _, _) :-
	l_write_trace(['wsdl_to_interaction - error : ', WSDL_file_name, nl], 0) ,
	fail.

wsdl_to_interaction2(RivVersion, RivVersionLong) :-
	atom_concat('RIVTABP', RivVersion, RivVersionLong) ,
	! .

% It seems ApSe used lower case letters for "rivtabp". Check if legal.
wsdl_to_interaction2(RivVersion, RivVersionLong) :-
	atom_concat('rivtabp', RivVersion, RivVersionLong) .



/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Extract the domain name part from an rpath
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
get_domain_name([D2, D1, riv | _] , [D1,D2]) :- ! .
get_domain_name([D3, D2, D1, riv | _] , [D1,D2,D3]) :- ! .


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Use "svn info" command to extract revision and change date from an svn
file
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
get_svn_info(File, svnInfo(Revision, Date)) :-
	l_write_trace(['svn-info File: ', File], 2) ,
	process_create(path(svn), [ 'info', file(File) ],
                       [ stdout(pipe(Out)) ,
			 stderr(null)
                       ]),
%        read_lines(Out, Lines) ,
        l_read_stream_to_list(Out, Lines),
	close(Out),
	svn_extract_revision(Lines, Revision) ,
	svn_extract_date(Lines, Date).

svn_extract_revision([], _ ) :-
	! ,
	l_write_trace(['svn_extract_revision - Error: did not find a revision!'], 0) .

svn_extract_revision([Line|_], Revision) :-
	atomic_list_concat(['Last','Changed','Rev:',Revision|_], ' ', Line ),
	! .

svn_extract_revision([_|Rest], Revision) :-
	svn_extract_revision(Rest, Revision) .

svn_extract_date([], _ ) :-
	! ,
	l_write_trace(['svn_extract_date - Error: did not find a date!'], 0) .

svn_extract_date([Line|_], lastChanged(Date, Time)) :-
	atomic_list_concat(['Last','Changed','Date:',Date, Time | _], ' ', Line ) ,
	! .

svn_extract_date([_|Rest], Date) :-
	svn_extract_date(Rest, Date) .

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Read a WSDL file to extract the description line(s)
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
wsdl_description(File, Description) :-
	l_read_file_to_list(File, Lines) ,
%	open(File, read, _, [alias(wsdl)]),
%	read_lines(wsdl, Lines) ,
	wsdl_description2(Lines, Description).

wsdl_description2([], '** The description could not be extracted from the WSDL file **') :- !.

% Case where "Beskrivning:" is on its own line
wsdl_description2([Line, Desc1 | _Rest], Description) :-
	l_strip_blanks(Line, 'Beskrivning:') ,
	l_strip_blanks(Desc1, Description) ,
	! .

% Case when the description is on the same line as "Beskrivning"
wsdl_description2([Line | _Rest], Description) :-
	l_strip_blanks(Line, Line2) ,
	atomic_list_concat(['Beskrivning', Desc], ':', Line2),
	l_strip_blanks(Desc, Description),
	atom_length(Description, Len),
	Len > 3 ,
	! .

wsdl_description2([_Line | Rest], Description) :-
	wsdl_description2(Rest, Description) .
/*
read_lines(Out, Lines) :-
        read_line_to_codes(Out, Line1),
        read_lines(Line1, Out, Lines).

read_lines(end_of_file, Out, []) :-
	! ,
	close(Out) .

read_lines(Codes, Out, [Line|Lines]) :-
        atom_codes(Line, Codes),
	l_write_trace([Line, nl], 5),
        read_line_to_codes(Out, Line2),
        read_lines(Line2, Out, Lines).
*/


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
There are a number of predicates to read the description and Swedish
names from the TK files. The TKB word files are converted to text with
Libre office. The text files are kept since the conversion is quite time
consuming. The date is checked to see when a text file is to be
re-generated. There is a number of alternatives to extract the
description from the text file. We assume that Libre office is not
available in windows.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
get_tkb_file_info(_DomainList, _Dtag, _Filepath, lastChanged(_TkbDate, _TkbTime), 'Cannot read TKB in a Windows system.', _SwedishNames) :-
	l_current_os(win) .

get_tkb_file_info(DomainList, Dtag, Filepath, lastChanged(TkbDate, TkbTime), Description, swedishNames(LongName, ShortName)) :-
	l_current_os(unix),
	file_base_name(Filepath, File),
	file_name_extension(FName, _Ext, File),
	get_tkb_convert_tag(Dtag, Dtag2),
	atomic_list_concat(DomainList, '_', Domain),
	atomic_list_concat([Domain, Dtag2], '-', DomTag),
	atomic_list_concat([TkbDate, 'T', TkbTime], TS),
	atomic_list_concat(['/home/leo/tmp/tkb_store/', DomTag, '/'], Txtpath),
	atomic_list_concat([Txtpath,FName,'.txt'], Txtfilepath),
	parse_time(TS, _Format, TkbTimestamp),
	get_tkb_file_info2(Filepath, Txtfilepath, TkbTimestamp) ,
	get_tkb_webtext(Txtfilepath, Description) ,
	get_tkb_swedish_name(Txtfilepath, LongName, ShortName) .


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
This clause verifies that the text file exist and is newer than the doc
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
get_tkb_file_info2(_Filepath, Txtfilepath, TkbTimestamp) :-
	exists_file(Txtfilepath),
	time_file(Txtfilepath, TxtTimestamp) ,
	TxtTimestamp > TkbTimestamp ,
	l_write_trace(['TKB txt exist and is newer: ', Txtfilepath], 2) ,
	! .

% We need to (re-)generate a txt file
get_tkb_file_info2(Filepath, Txtfilepath, _TkbTimestamp) :-
	file_directory_name(Txtfilepath, Txtpath),
	l_write_trace(['TKB txt to be generated: ', Txtfilepath], 1) ,
	atomic_list_concat(['libreoffice --invisible --convert-to txt:Text --outdir ' ,Txtpath, ' \'', Filepath,'\' '], Command),
	shell(Command).

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
The description is read from the text file.
We try to find the WEB description.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

% ----- Swedish name
get_tkb_swedish_name(Txtfilepath, LongName, ShortName) :-
	l_read_file_to_list(Txtfilepath, Lines) ,
	get_tkb_inlednings1(Lines, LongName1, ShortName1) ,
	l_strip_blanks(LongName1, LongName) ,
	l_strip_blanks(ShortName1, ShortName) ,
	atom_length(LongName, Llen),
	Llen > 0 ,
	atom_length(ShortName, Slen),
	Slen > 0 ,
	! .

get_tkb_swedish_name(Txtfilepath, '-', '-') :-
	l_write_trace(['Could not extract Swedish names for: ', Txtfilepath], 1) .

% ----- WEB description
%get_tkb_webtext(Txtfilepath, WebDescription) :-
get_tkb_webtext(Txtfilepath, DescriptionList) :-
%	l_read_file_to_list(Txtfilepath, Lines, [encoding(iso_latin_1)]
%	) ,
	l_read_file_to_list(Txtfilepath, Lines) ,
	get_tkb_inledningw1(Lines, After) ,
	get_tkb_inledningw2(After, DescriptionList) ,
	! .
%	atomic_list_concat(DescriptionList, ' ', Desc) ,
%	l_strip_blanks(Desc, WebDescription) ,
%	atom_length(WebDescription, Len) ,
%	Len > 0 ,
%	! .

get_tkb_webtext(Txtfilepath, ['-']) :-
	l_write_trace(['Could not extract TKB WEB description for: ', Txtfilepath], 1) .


% ----- Ordinary description. Will not be read anymore. Code will be
% removed eventually.
/*
get_tkb_description3(Txtfilepath, Description) :-
	l_read_file_to_list(Txtfilepath, Lines) ,
	get_tkb_inledning1(Lines, After) ,
	get_tkb_inledning2(After, DescriptionList) ,
	atomic_list_concat(DescriptionList, ' ', Desc1) ,
	atom_concat(Desc1, ' (WEB paragraph text not found in TKB)', Desc) ,
	l_strip_blanks(Desc, Description) ,
	atom_length(Description, Len) ,
	Len > 0 ,
	! .
*/


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Find start of Swedish name
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
get_tkb_inlednings1([Inledning, LongName, ShortName| _Rest], LongName, ShortName ) :-
	member(Inledning,
	       [
		   '1.1 Svenskt namn' ,
		   '1.1. Svenskt namn'
	       ]),
	       ! .

get_tkb_inlednings1([_First|Rest], LongName, ShortName ) :-
	get_tkb_inlednings1(Rest, LongName, ShortName ) .

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Identify end of Swedish name
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
get_tkb_inlednings2([Avslutning|_Rest], [] ) :-
	member(Text,
	       [ 'teknikoberoende',
		 'Versionsinformation' ,
		 'de tekniska kontrakten' ,
		 % 'teknik-oberoende',
		 '2 Målgrupp',
		 'Förändrade tjänstekontrakt',
		 '2. Generella regler',
		 'tekniskt-oberoende']) ,
	       sub_atom_icasechk(Avslutning, _Start, Text) ,
	       ! .

get_tkb_inlednings2([Line|Rest], [Line|Sofar]) :-
	get_tkb_inledningw2(Rest, Sofar) .




/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Find start of WEB description text.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
get_tkb_inledningw1([Inledning|Rest], Rest ) :-
	member(Inledning,
	       [
		   '1.2 WEB beskrivning',
		   '1.2 Webbeskrivning',
		   '1.2. WEB beskrivning'
	       ]),
	       ! .

get_tkb_inledningw1([_First|Rest], Rest2 ) :-
	get_tkb_inledningw1(Rest, Rest2 ) .

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Identify end of WEB description
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
get_tkb_inledningw2([Avslutning|_Rest], [] ) :-
	member(Text,
	       [ 'teknikoberoende',
		 'Versionsinformation' ,
		 'de tekniska kontrakten' ,
		 % 'teknik-oberoende',
		 '2 Målgrupp',
		 'Förändrade tjänstekontrakt',
		 '2. Generella regler',
		 'tekniskt-oberoende']) ,
	       sub_atom_icasechk(Avslutning, _Start, Text) ,
	       ! .

get_tkb_inledningw2([Line|Rest], [Line|Sofar]) :-
	get_tkb_inledningw2(Rest, Sofar) .



/*
%Find start of ordinary description text. There are a number of
%alternatives.

get_tkb_inledning1([Inledning|Rest], Rest ) :-
	member(Inledning,
	       ['Inledning',
		'1 Inledning',
		'1. Inledning',
		'1 Om dokumentet',
		'1.1 Om dokumentet',
		'2. Inledning',
		'2 Inledning',
		'3 Inledning',
		'4 Inledning']),
	       ! .

get_tkb_inledning1([_First|Rest], Rest2 ) :-
	get_tkb_inledning1(Rest, Rest2 ) .



%Identify end of ordinary description

get_tkb_inledning2([Avslutning|_Rest], [] ) :-
	member(Text,
	       [ 'teknikoberoende',
		 %'de tekniska kontrakten' ,
		 'de tekniska kontrakten' ,
		 % 'teknik-oberoende',
		 '2 Målgrupp',
		 'Förändrade tjänstekontrakt',
		 '2. Generella regler',
		 '1.2. Bakgrund',
		 'tekniskt-oberoende']) ,
	       sub_atom_icasechk(Avslutning, _Start, Text) ,
	       ! .

get_tkb_inledning2([Line|Rest], [Line|Sofar]) :-
	get_tkb_inledning2(Rest, Sofar) .
*/

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Convert between different formats for tags, branches and trunk
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
get_tkb_convert_tag(tag(Dtag) , Dtag) :- ! .
get_tkb_convert_tag(branch(Dtag), Dtag) :- ! .
get_tkb_convert_tag(trunk, trunk) :- ! .
get_tkb_convert_tag(Dtag, _) :-
	l_write_trace(['get_tkb_convert_tag - error. Dtag is : ', Dtag], 0) ,
	fail .











