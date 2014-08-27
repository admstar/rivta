:- module(generateHTML, [
	      html_generate/0
	  ]) .



:- use_module(leolib).
:- use_module(loaddomaintable).
:- use_module(loadsvninfo).
:- use_module(loadtak).
:- use_module(library(http/html_write)).




/* ===================================================================
Create HTML output
====================================================================== */

html_generate :-
	html_generate_domain_pages ,
	html_generate_interaction_index ,
	html_generate_domain_index .

/* =======================================================================
Generate an index file for all domains
========================================================================== */

html_generate_domain_index :-
	html_domain_index_info(Body) ,
	c_tmp_dir(Tmp),
	atomic_concat(Tmp, 'www/domains/index.html' , DomainIndex),
	open(DomainIndex, write, Stream, []) ,
	inera_html_template('Index över tjänstedomäner', Body, Content) ,
	l_html_write(Content, Stream) ,
	close(Stream).

% ----------------------------------------------------------------------

html_domain_index_info([
    h1('Tjänstedomäner') ,
    p(' ') ,
    a([attribute(href, 'index.html')], 'Index över tjänstedomäner') ,
    '  |  ',
    a([attribute(href, 'interaction_index.html')], 'Index över tjänstekontrakt') ,
        div(attribute(class, ingress), 'Här hittar du en förteckning över samtliga tjänstedomäner och tjänstekontrakt, samt om de är installerade i den nationella Tjänsteplattformen eller inte') ,
    p(' ') ,
    div(attribute(class, ingress), 'Information på denna sida är extraherad från subversion, tjänstekontraktsbeskrivningar samt tjänsteadresseringskatalogerna i NTjP. Klicka på länkarna i tabellen för mer information.') ,
    p(' ') ,
    table(
	  [
	      tr(
		  [
		  th([attribute(class, dom1)], 'Tjänstedomän') ,
		  th([attribute(class, dom2)], 'Svenskt namn') ,
		  th([attribute(class, dom3)], 'Engelskt namn') ,
		  th([attribute(class, dom4)], 'NTjP') ,
		  th([attribute(class, dom5)], 'QA')
	      ]) ,
	TrList
    ]
    )
]
		      ) :-
	findall(
	    tr([
		td(ShortSwedish) ,
		td(LongSwedish) ,
		td(DomainNameLink) ,
		td([attribute(align,center)], InTp) ,
		td([attribute(align,center)], InQA)
	    ]),
%	    get_domain_index_info(DomainNameLink, Popular, InTp, InQA,
%	    TkbLink),
	    get_domain_index_info_x(DomainNameLink, _Popular, InTp, InQA, _WebText, LongSwedish, ShortSwedish),
	    TrList).

get_domain_index_info_x(
    DomainNameLink,
    Popular,
    InTp,
    InQA,
    Text,
    LongSwedish,
    ShortSwedish) :-
	sv_get_domain(Domain) ,
	atomic_list_concat(Domain, ':', DomainName) ,
	html_domain_filename(Domain, FileName) ,
	(   html_domain_file_exist(Domain) -> DomainNameLink = a(attribute(href, FileName), DomainName) ; DomainNameLink = DomainName ) ,
%	dt_get_popular_name(Domain, Popular) ,
	get_swedish_name(Domain, Popular) , % Take the name from Sonjas table
	domain_index_tp_info(prod, Domain, InTp) ,
	domain_index_tp_info(qa, Domain, InQA) ,
	sv_get_tkb_info(Domain, trunk, _TkbLink, _LastChanged, TkbDescription, LongSwedish, ShortSwedish) ,
	atom_length(TkbDescription, Len) ,
	(   Len > 1 ->
	Text = 'OK' ;
	Text = '-' ) .

/*
get_domain_index_info(
    DomainNameLink,
    Popular,
    InTp,
    InQA,
    Link) :-
	sv_get_domain(Domain) ,
	atomic_list_concat(Domain, ':', DomainName) ,
	html_domain_filename(Domain, FileName) ,
	(   html_domain_file_exist(Domain) -> DomainNameLink = a(attribute(href, FileName), DomainName) ; DomainNameLink = DomainName ) ,
%	dt_get_popular_name(Domain, Popular) ,
	get_swedish_name(Domain, Popular) , % Take the name from Sonjas table
	domain_index_tp_info(prod, Domain, InTp) ,
	domain_index_tp_info(qa, Domain, InQA) ,
	(   sv_get_tkb_info(Domain, trunk, TkbLink, _LastChanged, _TkbDescription) ->
	Link = a([attribute(href, TkbLink)], 'TKB') ;
	Link = '-' ) .
*/

% ----------------------------------------------------------------------

domain_index_tp_info(Envir, Domain, 'X') :-
	tk_get_interaction(Envir, _Interaction, _Version, _BPVersion, Domain) ,
	! .
domain_index_tp_info(_Envir, _Domain, ' ') .


/* =======================================================================
Generate an index file of all service interactions
========================================================================== */

html_generate_interaction_index :-
	html_interaction_index_info(Body) ,
	c_tmp_dir(Tmp),
	atomic_concat(Tmp, 'www/domains/interaction_index.html' , InteractionIndex),
	open(InteractionIndex, write, Stream, []) ,
	inera_html_template('Index över tjänstekontrakt', Body, Content) ,
	l_html_write(Content, Stream) ,
	close(Stream).

% ----------------------------------------------------------------------

html_interaction_index_info([
    h1('Tjänstekontrakt'),
    p(' ') ,
    a([attribute(href, 'index.html')], 'Index över tjänstedomäner') ,
    '  |  ',
    a([attribute(href, 'interaction_index.html')], 'Index över tjänstekontrakt') ,
    p(' ') ,
    p('Information på denna sida är extraherad från WSDL-filer i subversion samt tjänsteadresseringskatalogerna i NTjP.') ,
    p(' ') ,
    p(' '),
    table(
	  [
	      tr(
		  [
		  th([attribute(class, int1)], 'Tjänstekontrakt') ,
		  th([attribute(class, int2)], 'Beskrivning') ,
		  th([attribute(class, int3)], 'Tjänstedomän') ,
		  th([attribute(class, int4)], 'NTjP') ,
		  th([attribute(class, int5)], 'QA') ,
		  th([attribute(class, int6)], 'Ändrad')
	      ]) ,
	      TrList2
	  ]
	 )
]
			   ) :-
	findall(
	    tr([
		td(Interaction),
		td(Description),
		td(DomainNameLink),
		td([attribute(align,center)], InTp) ,
		td([attribute(align,center)], InQA) ,
		td([attribute(align,center)], LastChanged)
	    ]),
	    get_interaction_index_info(Interaction, Description, DomainNameLink, InTp, InQA, LastChanged),
	    TrList) ,
	sort(TrList, TrList2).

get_interaction_index_info(
    Interaction,
    Description,
    DomainNameLink,
    InTp,
    InQA ,
    LastChanged) :-
	sv_get_interaction(Inter, Version, _BP, Description, Domain, trunk, lastChanged(LastChanged, _Time)) ,
	atomic_list_concat([Inter, Version], ' ', Interaction) ,
	atomic_list_concat(Domain, ':', DomainName) ,
	html_domain_filename(Domain, FileName) ,
	(   html_domain_file_exist(Domain) -> DomainNameLink = a(attribute(href, FileName), DomainName) ; DomainNameLink = DomainName ) ,
%	dt_get_popular_name(Domain, Popular) ,
	interaction_index_tp_info(prod, Domain, Inter, InTp) ,
	interaction_index_tp_info(qa, Domain, Inter, InQA) .

% ----------------------------------------------------------------------

interaction_index_tp_info(Envir, Domain, Interaction,  'X') :-
	tk_get_interaction(Envir, Interaction, _Version, _BPVersion, Domain) ,
	! .
interaction_index_tp_info(_Envir, _Domain, _Interaction,  ' ') .


/* =======================================================================
Generate an HTML file describing a service domain
========================================================================== */

html_generate_domain_pages :-
	sv_get_domain(Domain) ,
	html_domain_page(Domain) ,
	fail .

html_generate_domain_pages .

% ----------------------------------------------------------------------

html_domain_page(Domain) :-
	sv_get_domain(Domain) ,
	! ,
	html_domain_info(Domain, Tkb_html_list) ,
	atomic_list_concat(Domain, ':', DomainName) ,
	append([
	    h1(DomainName),
	    p(' ') ,
	    a([attribute(href, 'index.html')], 'Index över tjänstedomäner') ,
	    '  |  ',
	    a([attribute(href, 'interaction_index.html')], 'Index över tjänstekontrakt') ,
	    p(' ')
	],
	       Tkb_html_list,
	       Content) ,
	html_domain_filename(Domain, FileName) ,
	c_tmp_dir(Tmp),
	atomic_concat(Tmp, 'www/domains/' , DomainFolder),
	atomic_concat(DomainFolder, FileName, Txtpath),
	open(Txtpath, write, Stream, []) ,
	inera_html_template(DomainName, Content, Page) ,
	l_html_write(Page, Stream) ,
	close(Stream).

html_domain_page(Domain) :-
	l_write_trace(['HTML_domain does not exist',Domain], 0) ,
	fail.

% ----------------------------------------------------------------------
% The structure of the domain page
% ----------------------------------------------------------------------

html_domain_info(Domain, [Desc, VersionInfo, Consumer_list, Producer_list]) :-
	html_domain_info_description(Domain, Desc) ,
	get_domain_presentation_list(Domain, DomVersionList),
	html_domain_info_version(Domain, DomVersionList, VersionInfo),
%	get_domain_acceptance(Domain, Version, OkType) ,
%	atomic_list_concat(Domain, '_', DomainName),
%	atomic_list_concat([DomainName, Version], '_', Tag) ,
%	l_write_trace([tag, Tag], 1),
%	html_domain_info_tkb(Domain, Tkb_html_list) ,
%	html_domain_info_tkb(OkTypFe, Domain, Tag, Version,
%	Tkb_html_list) ,
%	html_domain_info_services(OkType, Domain, Tag,
%	Services_html_list) ,
%	l_write_trace(['Services_list: ', Services_html_list], 1 ),
	html_domain_info_consumers(Domain, Consumer_list) ,
	html_domain_info_producers(Domain, Producer_list) .

% ----------------------------------------------------------------------

html_domain_info_description(Domain,
		     [
			 h2('Beskrivning'),
			 p(['Beskrivning av tjänstedomänen (från kap 1.1 i ',
			     a([attribute(href, TkbLink)],'Tjänstekontraktsbeskrivningen'), ' i trunk)'] ),
			 p(TkbDescription)
		     ]
		    ) :-
	sv_get_tkb_info(Domain, trunk, TkbLink, lastChanged(_TkbDate, _), TkbDescription, _LongSwedish, _ShortSwedish) .



% ---------------------------------------------------------------------

html_domain_info_version(_Domain, [], '' ) :- ! .

html_domain_info_version(Domain, [First | Rest], [VI | VRest]) :-
	html_domain_info_version2(Domain, First, VI),
	html_domain_info_version(Domain, Rest, VRest).

% ---------------------------------------------------------------------

% trunk clause
html_domain_info_version2(Domain, trunk,
			  [
				      h2('Pågående utveckling (trunk)'),
				      p(['Detta är en pågående utveckling som ännu inte är granskad av Arkitektur och regelverk på Inera.']),
				      p([a([attribute(href, TkbLink)],'Tjänstekontraktsbeskrivning'), ' som uppdaterades senast ', b(TkbDate), '.']) ,
				      ServiceList
				  ]
			) :-
	! ,
%	atomic_list_concat(Domain, '_', DomainName),
%	atomic_list_concat([DomainName, Version], '_', Tag) ,
	sv_get_tkb_info(Domain, trunk, TkbLink, lastChanged(TkbDate, _), _TkbDescription, _LongSwedish, _ShortSwedish) ,
	html_domain_info_services(Domain, trunk, ServiceList) .

% A release or RC (or beta) which can be found in a tag in svn
html_domain_info_version2(Domain, Version,
			  [
				      h2([Uname, ' ', Version]),
				      p(['Denna ', Lname, ' är godkänd av Inera Arkitektur och regelverk. ', ZipLinkText]) ,
				      p([a([attribute(href, TkbLink)],'Tjänstekontraktsbeskrivning'), ' som uppdaterades senast ', b(TkbDate), '.']) ,
				      ServiceList
				  ]
			 ) :-
	atomic_list_concat(Domain, '_', DomainName),
	atomic_list_concat([DomainName, Version], '_', Tag) ,
	tag_synonym(Tag, Uname, Lname),
	sv_get_tkb_info(Domain, tag(Tag), TkbLink, lastChanged(TkbDate, _), _TkbDescription, _LongSwedish, _ShortSwedish) ,
	! ,
	html_ziplink(Domain, Version, ZipLinkText) , %% MUST ADD TAG HERE
	html_domain_info_services(Domain, tag(Tag), ServiceList) ,
	l_write_trace([Tag, ' - ', ServiceList],1) .

% A release or RC (or beta) which cannot be found in a tag in svn
html_domain_info_version2(Domain, Version,
			  [
				      h2([Uname, ' ', Version]),
				      p(['Denna ', Lname, ' är godkänd av Inera Arkitektur och regelverk. ', ZipLinkText]) ,
				      p(['Denna ', Lname, ' kan inte återfinnas i versionshanteringssystemet (som en tag i svn). Av den orsaken kan ingen information om Tjänstekontraktsbeskrivning eller tjänster presenteras. Se zip-arkivet ovan.']),
				      ServiceList
				  ]
			 ) :-
	atomic_list_concat(Domain, '_', DomainName),
	atomic_list_concat([DomainName, Version], '_', Tag) ,
	tag_synonym(Tag, Uname, Lname),
%	sv_get_tkb_info(Domain, tag(Tag), TkbLink, lastChanged(TkbDate,
%	_), _TkbDescription) , ! ,
	html_ziplink(Domain, Version, ZipLinkText) ,
	html_domain_info_services(Domain, tag(Tag), ServiceList) ,
	l_write_trace([Tag, ' - ', ServiceList],1) .



% ---------------------------------------------------------------------


% ----------------------------------------------------------------------
% ----------------------------------------------------------------------
% ----------------------------------------------------------------------

/*
html_domain_info3(Domain, trunk, VersionInfo) :-
	! ,
%	get_domain_acceptance(Domain, Version, OkType) ,
%	atomic_list_concat(Domain, '_', DomainName),
%	atomic_list_concat([DomainName, Version], '_', Tag) ,
%	l_write_trace([tag, Tag], 1),
%	html_domain_info_tkb(Domain, Tkb_html_list) ,
	html_domain_info_tkb(trunk, Domain, VersionInfo ),
	! .
*/
/*
	html_domain_info_services(OkType, Domain, Tag, Services_html_list) ,
	l_write_trace(['Services_list: ', Services_html_list], 1 ),
	html_domain_info_consumers(Domain, Consumer_list) ,
	html_domain_info_producers(Domain, Producer_list) .


html_domain_info3(_Domain, _Version, 'VersionInfo') .
	get_domain_acceptance(Domain, Version, OkType) ,
	atomic_list_concat(Domain, '_', DomainName),
	atomic_list_concat([DomainName, Version], '_', Tag) ,
	l_write_trace([tag, Tag], 1),
	html_domain_info_tkb(Domain, Tkb_html_list) ,
%	html_domain_info_tkb(OkType, Domain, Tag, Version,
%	Tkb_html_list) ,
	html_domain_info_services(OkType, Domain, Tag, Services_html_list) ,
	l_write_trace(['Services_list: ', Services_html_list], 1 ),
	html_domain_info_consumers(Domain, Consumer_list) ,
	html_domain_info_producers(Domain, Producer_list) .
*/

% ----------------------------------------------------------------------
% html_domain_info_tkb
% Basic information and acceptance level for domains accepted according
% to current rules
% ----------------------------------------------------------------------
% ----------------------------------------------------------------------
/*
html_domain_info_tkb(release, Domain, Tag, Version,
		     [
					 h2('Inledning'),
					 p(['Denna beskrivning är baserad på version ', b(Version), '. Den är godkänd enligt gällande regelverk. ', ZipLinkText]),
					 h2(['Beskrivning av tjänstedomänen (från ',
					     a([attribute(href, TkbLink)],'Tjänstekontraktsbeskrivningen'), ')'] ),
					 p(TkbDescription) ,
					 p([a([attribute(href, TkbLink)],'Tjänstekontraktsbeskrivningen'), ' uppdaterades senast ', b(TkbDate), '.'])
				     ]
		    ) :- % Godkänd enligt nuvarande regelverk
	sv_get_tkb_info(Domain, tag(Tag), TkbLink, lastChanged(TkbDate, _), TkbDescription) ,
	! ,
	html_ziplink(Domain, ZipLinkText) .


% ----- Accepted according to old rules
html_domain_info_tkb(rc, Domain, _Tag, _Version,
		     [
					  h2('Inledning'),
					  p('Denna beskrivning är baserad på version på utvecklingsversionen (trunk) av domänen. Den är godkänd enligt ett tidigare regelverk.'),
					  ZipLinkText,
					  h2(['Beskrivning av tjänstedomänen (från ',
					      a([attribute(href, TkbLink)],'Tjänstekontraktsbeskrivningen'), ')'] ),
					  p(TkbDescription) ,
					  p([a([attribute(href, TkbLink)],'Tjänstekontraktsbeskrivningen'), ' uppdaterades senast ', b(TkbDate), '.'])
				      ]
		 ) :-
	sv_get_tkb_info(Domain, trunk, TkbLink, lastChanged(TkbDate, _), TkbDescription) ,
	! ,
	html_ziplink(Domain, ZipLinkText) .

% ----- Not yet reviewed
html_domain_info_tkb(trunk, Domain,
		     [
				h2('Pågående utveckling'),
				p(['Denna beskrivning är baserad på utvecklingsversionen (trunk) av domänen. Den är ännu inte granskad av Arkitektur och regelverk på Inera.']),
				p(['Se beskrivning i ', a([attribute(href, TkbLink)],'Tjänstekontraktsbeskrivningen') ] ),
				p([a([attribute(href, TkbLink)],'Tjänstekontraktsbeskrivningen'), ' uppdaterades senast ', b(TkbDate), '.'])
			    ]
		    ) :-
	sv_get_tkb_info(Domain, trunk, TkbLink, lastChanged(TkbDate, _), _TkbDescription) ,
	! .
*/
% ----- No TKB found
/*
html_domain_info_tkb(_OkType, _Domain, _Tag, _Version,
		  [
				 h2('Inledning'),
				 p(['Denna beskrivning är baserad på version på utvecklingsversionen (trunk) av domänen. ']),
				 p(['Det var inte möjligt att identifiera eller extrahera information från Tjänstekontraktsbeskrivningen.'])
			     ]
		 ) .
*/
% ----------------------------------------------------------------------

%html_domain_info_services(OkType, Domain, Tag,
html_domain_info_services(Domain, Tag,
			  [
%					      h2('Tjänstekontrakt'),
					      p(['Följande tjänstekontrakt är definierade i denna ', Lname, '. Beskrivningstexten är hämtad från respektive WSDL-fil.']),
					      ul( TrList )
					  ]
			 ) :-
%	(   OkType > 0 -> Tag2=trunk ; Tag2=tag(Tag) ),
	tag_synonym(Tag, _Uname, Lname),
	bagof(
	    li([b(Service), ' ', Version, ' - ', Description, ' (', Date, ')' ]),
	    sv_get_interaction(Service, Version, _RivVersion,Description,Domain,Tag, lastChanged(Date, _Time)),
	    TrList) ,
	! .

html_domain_info_services(_Domain, _Tag, p('(Denna tag kunde ej återfinnas i svn, varför information om tjänster ej kan presenteras)')) .

% ----------------------------------------------------------------------

html_domain_info_consumers(Domain,
			   [
			       h2('Tjänstekonsumenter anslutna till domänens kontrakt'),
			       p('Följande tjänstekonsumenter är anslutna till tjänster (tjänstekontrakt) i domänen i den nationellt gemensamma tjänsteplattformen.'),
			       ul( TrList )
			   ]
			  ) :-
	bagof(
	    li([b(C_Desc), ' (', C_HSA, ')']),
	    tk_get_domain_consumer(prod, Domain, C_HSA, C_Desc) ,
	    TrList),
	! .


html_domain_info_consumers(_Domain, [] ) .

% ----------------------------------------------------------------------

html_domain_info_producers(Domain,
			   [
			       h2('Tjänsteproducenter anslutna till domänens kontrakt'),
			       p('Följande tjänsteproducenter är anslutna via tjänster (tjänstekontrakt) i den nationellt gemensamma tjänsteplattformen.'),
			       ul( TrList )
			   ]
			  ) :-
	bagof(
	    li([b(C_Desc), ' (', C_HSA, ')']),
	    tk_get_domain_producer(prod, Domain, C_HSA, C_Desc) ,
	    TrList),
	! .

html_domain_info_producers(_Domain, [] ) .

% ----------------------------------------------------------------------
% Calculate the list of domain versions that should be displayed
% on the domain page.
%
% 1. [Accepted release with highest version]
% 2. [Accepted RC with highest version]
% 3. trunk - always added last
%
% ----------------------------------------------------------------------
% We only look for accepted versions
get_domain_presentation_list(Domain, DomList) :-
	findall(
	    Version,
	    dt_get_domain_acceptance(Domain, Version, _OkType),
	    UnorderedList),
	sort(UnorderedList, OList),
	reverse(OList, ROList) ,
	extract_highest_release(ROList, Release) ,
	extract_highest_rc(ROList, RC) ,
	append(Release, RC, DomList1) ,
	append(DomList1, [trunk], DomList) .

extract_highest_release([], [] ) :- ! .
% If there is not an underscrore, then we assume a release (no beta or
% RC)
extract_highest_release([First|_Rest], [First] ) :-
	\+ sub_atom(First, _Before, _Len, _After, '_') ,
	! .
extract_highest_release([_First|Rest], First ) :-
	extract_highest_release(Rest, First ) .

% If we do not find an accepted RC, then we return null
extract_highest_rc([], [] ) :- ! .
% If there is an underscrore, then we assume a release (no beta or
% RC)
extract_highest_rc([First|_Rest], [First] ) :-
	sub_atom(First, _Before, _Len, _After, '_') ,
	! .
extract_highest_rc([_First|Rest], First ) :-
	extract_highest_rc(Rest, First ) .



% ----------------------------------------------------------------------
% Create a text i a zip link exist
% ----------------------------------------------------------------------

html_ziplink(Domain, Version,
	     [
		 p([
		     a([attribute(href, ZipLink)],['Ladda ner zip-arkivet för denna ', Lname]),
		     ' (baserat på den aktuella ',
		     a([attribute(href, 'http://rivta.se/servicedomaintable')],'tabellen över granskade domäner'),
		     ').'
		 ])
	     ] ) :-
	tag_synonym(Version, _Uname, Lname),
	dt_get_zip_link(Domain, Version, ZipLink) ,
	! .

html_ziplink(Domain, Version, '') :-
	write(Domain), write(' - '), write(Version), nl.



/* =======================================================================
HTML generation support predicates
========================================================================== */

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Inera HTML template
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
inera_html_template(Title, Body,
    [
    string('<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">') ,
    comment(Comment),
    html([attribute(xmlns,'http://www.w3.org/1999/xhtml'), attribute('xml:lang','sv'), attribute(lang,'sv')],
	 [
	     head( [
		 meta([attribute('http-equiv','Content-Type') ,
		       attribute(content, 'text/html;'),
		       attribute(charset, 'UTF-8')],
		      []) ,
		 link([
		     attribute(href,'../css/Normal.css'),
		     attribute(rel,'stylesheet'),
		     attribute(type, 'text/css'),
		     attribute(media, 'screen')],
		      []) ,
		 title(Title)
	     ] ) ,
	     body([Body, p(i(['Senast uppdaterad ', Date]))])
	 ] ) ] ) :-
	l_current_date(Date),
	l_current_time(Time) ,
	atomic_list_concat(['This HTML page was generated by leolib:html_generate_page/1', Date, Time], ' ', Comment) .

% ----------------------------------------------------------------------



get_swedish_name(Domain, Swedish) :-
	dt_get_swedish_name(Domain, Swedish) ,
	! .
get_swedish_name(Domain, ' - ') :-
%	dt_get_popular_name(Domain, Swedish) ,
	l_write_trace(['* No Swedish name defined for: ', Domain], 1) .

% ----------------------------------------------------------------------

get_tkb_info(Domain, trunk, TkbLink, lastChanged(TkbDate, _), TkbDescription) :-
	sv_get_tkb_info(Domain, trunk, TkbLink, lastChanged(TkbDate, _), TkbDescription, _LongSwedish, _ShortSwedish) ,
	! .
get_tkb_info(_Domain, trunk, '-', lastChanged('-', _Time), 'TKB kunde inte hittas.') .

% ----------------------------------------------------------------------

html_domain_filename(Domain, FileName) :-
	atomic_list_concat(Domain, '_', DomainFileName) ,
	atomic_list_concat([DomainFileName, 'html'], '.', FileName) .

% ----------------------------------------------------------------------

html_domain_file_path_name(Domain, TheFile) :-
	c_html_domains_dir(HtmlDir) ,
	html_domain_filename(Domain, FileName) ,
	atomic_concat(HtmlDir, FileName, TheFile) .

% ----------------------------------------------------------------------

html_domain_file_exist(Domain) :-
	html_domain_file_path_name(Domain, TheFile) ,
	exists_file(TheFile) .
% ----------------------------------------------------------------------

get_domain_acceptance(Domain, Version, OkType) :-
	dt_get_domain_acceptance(Domain, Version, OkType) ,
	! .

get_domain_acceptance(_Domain, '-', 2) .

% ----------------------------------------------------------------------

tag_synonym(trunk, 'Utvecklingsversion', 'utvecklingsversion') :- ! .

tag_synonym(tag(Tag), 'Releasekandidat', 'releasekandidat') :-
	member(Check, ['_RC', 'beta']) ,
	sub_atom(Tag, _, _, _, Check) ,
	! .

tag_synonym(Tag, 'Releasekandidat', 'releasekandidat') :-
	atom(Tag),
	member(Check, ['_RC', 'beta']) ,
	sub_atom(Tag, _, _, _, Check) ,
	! .

tag_synonym(_Tag, 'Release', 'release') .


/* =======================================================================
	Other misc stuff
========================================================================== */

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Generate redirect files to tkb on request from Maria Brunsell
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
html_tkb_redirect(Domain) :-
	sv_get_tkb_info(Domain, trunk, TkbLink, _TkbDate, _TkbDescription, _LongSwedish, _ShortSwedish) ,
	! ,
	l_current_date(Date),
	l_current_time(Time) ,
	atomic_list_concat(['This HTML page was generated by leolib:html_tkb_redirect/1', Date, Time], ' ', Comment) ,
	html_domain_filename(Domain, FileName) ,
	atomic_concat('/home/leo/tmp/tkbredirects/', FileName, Txtpath),
	atomic_concat('0; url=', TkbLink, NewUrl) ,
	open(Txtpath, write, Stream, []) ,
	l_html_write([
	    comment(Comment) ,
	    html(
		head(
		    meta([
			attribute('http-equiv','refresh'),
			attribute(content, NewUrl),
			attribute('http-equiv','Content-Type') ,
			attribute(content, 'text/html;'),
			attribute(charset, 'UTF-8')

		    ],
			 [] )
		)
	    )
	],
		     Stream
		    ),
	close(Stream).

% ----------------------------------------------------------------------

write_excel :-
	open('domains.csv', write, _, [alias(domains)]),
	atomic_list_concat(['Tjänstedomän', 'Tjänstedomän-svensk namn (enligt http://rivta.se/servicedomaintable)', 'Version', 'Granskningsresultat', 'Länk till TKB', 'TKB senast ändrad', 'Beskrivning enligt TKB'], ';', Firstline) ,
	write(domains, Firstline), nl(domains),
	open('services.csv', write, _, [alias(services)]),
	atomic_list_concat(['Tjänstedomän', 'Tjänstekontrakt', 'Version', 'Senast ändrad', 'Beskrivning hämtad från WSDL-fil'], ';', Firstline2),
	write(services, Firstline2), nl(services) ,
	sv_get_all_domains(Domains) ,
	member(Domain, Domains) ,
	dt_get_popular_name(Domain, Popular) ,
	get_domain_acceptance(Domain, Version, OkType),
	get_tkb_info(Domain, trunk, TkbLink, lastChanged(TkbDate, _Time), TkbDescription) ,
	atomic_list_concat(Domain, ':', DomainName) ,
	atomic_list_concat([DomainName, Popular, Version, OkType, TkbLink, TkbDate, TkbDescription], ';', DLine),
	l_write_trace(['DOMAINs to CSV file: ', DLine, nl], 2) ,
	write(domains, DLine), nl(domains),
	sv_get_interaction(Service, SVersion, _RivVersion, Description, Domain, trunk, lastChanged(Date, _Timestamp)) ,
	atomic_list_concat([DomainName, Service, SVersion, Date, Description], ';', SLine),
	l_write_trace(['SERVICES to CSV file: ', SLine], 2),
	write(services, SLine), nl(services) ,
	fail.

write_excel :-
	close(services) ,
	close(domains) .

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Verification predicates
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
list_services_in_unaccepted_domains :-
	write('*** Service in TAK from a domain which has not been accepted: '), nl ,
	tk_get_tak_info(prod, Domain, Interaction, IVersion, _RivVersion) ,
	\+ dt_get_domain_table(Domain, _Version),
	write(Domain),
	write(' : '),
	write(Interaction),
	write(':'),
	write(IVersion),
	nl ,
	fail.

list_services_in_unaccepted_domains .








