:- module(generateHTML, [
	      html_generate/0,
	      sql_generate/0
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
	html_generate_domain_index ,
	xml_generate_domain_index ,
	sql_generate .

/* =======================================================================
Generate an XML file for all domains and services
========================================================================== */

xml_generate_domain_index :-
	c_www_domains_dir(Dir) ,
	atomic_concat(Dir, 'domains.xml' , XMLFile),
	open(XMLFile, write, Stream, []) ,
	get_xml_domains(Content) ,
	l_html_write(Content, Stream) ,
	close(Stream).

% ----------------------------------------------------------------------

get_xml_domains(domains(DomainInfoList)) :-
	findall(
	    DomainInfo,
	    (	 sv_get_domain(Domain) , get_xml_domain(Domain, DomainInfo) ) ,
	    DomainInfoList ) .

get_xml_domain(Domain, domain([
			   attribute(name, DomainName),
			   attribute(swedish_name, LongSwedish),
			   attribute(swedish_nickname, ShortSwedish)
		       ],
			      ReleaseInfo) ) :-
	get_latest_tkb_info(Domain, _Tag, _TkbLink, _LastChanged, _TkbDescription, LongSwedish, ShortSwedish) ,
	atomic_list_concat(Domain, ':', DomainName) ,
	get_domain_presentation_list(Domain, ReleaseList) ,
	get_xml_release(Domain, ReleaseList, ReleaseInfo) .

get_xml_release(_Domain, [], '') :- ! .
get_xml_release(Domain, [Tag | Rest], [release([attribute(version, Tag)], ServiceContractInfo) | RestServiceContractInfo]) :-
	get_xml_serviceContract(Domain, Tag, ServiceContractInfo),
	get_xml_release(Domain, Rest, RestServiceContractInfo ) .

get_xml_serviceContract(Domain,
			DomVersion,
			SCList) :-
	format_tag(Domain, DomVersion, Tag),
	findall(
	    serviceContract([attribute(version, Version), attribute('lastUpdated', Date)] ,	Service ) ,
	    sv_get_interaction(Service, Version, _RivVersion, _Description, Domain, Tag, lastChanged(Date, _Time)) ,
	    SCList) .

/* =======================================================================
Generate an index file for all domains
========================================================================== */

html_generate_domain_index :-
	html_domain_index_info(Body) ,
%	c_tmp_dir(Tmp),
	c_www_domains_dir(Dir) ,
	atomic_concat(Dir, 'index.html' , DomainIndex),
	open(DomainIndex, write, Stream, []) ,
	inera_html_template('Index över tjänstedomäner', Body, Content) ,
	l_html_write(Content, Stream) ,
	close(Stream).

% ----------------------------------------------------------------------

html_domain_index_info([
    h1('Tjänstedomäner') ,
    div(attribute(class, ingress),
	'Här hittar du en förteckning över samtliga tjänstedomäner. I tabellen kan du också se om de är installerade i den nationella Tjänsteplattformen eller inte.') ,
    p(' ') ,
    'Informationen på denna sida är hämtad från subversion, tjänstekontraktsbeskrivningar samt tjänsteadresseringskatalogerna i den nationella tjänsteplattformen. Klicka på länkarna i tabellen för mer information.' ,
    newline ,
    a([attribute(href, 'interaction_index.html')],'Se även förteckning över samtliga tjänstekontrakt.') ,
    p(' ') ,
    table(
	[
	tr(
	    [
	    th([attribute(class, dom1)], 'Tjänstedomän') ,
	    th([attribute(class, dom2)], 'Svenskt namn') ,
	    th([attribute(class, dom3)], 'Engelskt namn') ,
	    th([attribute(class, dom4), attribute(align, left)], 'NTjP') ,
	    th([attribute(class, dom5)], ['QA ',
					  info(left, ['Informationen kommer från NTjPs tjänsteadresseringskataloger. Den uppdaterades senast ', Date])
					 ]
		    )
	]
	) ,
	TrList
    ]
    )
]
		      ) :-
	tk_get_tak_date(prod, Date) ,
	findall(
	    tr([
		td(ShortSwedish) ,
		td(LongSwedish) ,
		td(DomainNameLink) ,
		td([attribute(align,center)], InTp) ,
		td([attribute(align,center)], InQA)
	    ]),
	    get_domain_index_info_x(DomainNameLink, InTp, InQA, _WebText, LongSwedish, ShortSwedish),
	    TrList).

get_domain_index_info_x(
    DomainNameLink,
    InTp,
    InQA,
    _Text,
    LongSwedish,
    ShortSwedish) :-
	sv_get_domain(Domain) ,
	atomic_list_concat(Domain, ':', DomainName) ,
	html_domain_filename(Domain, FileName) ,
	(   html_domain_file_exist(Domain) -> DomainNameLink = a(attribute(href, FileName), DomainName) ; DomainNameLink = DomainName ) ,
	domain_index_tp_info(prod, Domain, InTp) ,
	domain_index_tp_info(qa, Domain, InQA) ,
	get_latest_tkb_info(Domain, trunk, _TkbLink, _LastChanged, _TkbDescription, LongSwedish, ShortSwedish) .

% ----------------------------------------------------------------------

domain_index_tp_info(Envir, Domain, 'X') :-
%	tk_get_interaction(Envir, _Interaction, _Version, _BPVersion,
%	Domain) ,
	tk_get_service_contract(Envir, _Sc_Id, _Interaction, Domain, _IVersion, _RivVersion) ,
	! .
domain_index_tp_info(_Envir, _Domain, ' ') .


/* =======================================================================
Generate an index file of all service interactions
========================================================================== */

html_generate_interaction_index :-
	html_interaction_index_info(Body) ,
%	c_tmp_dir(Tmp),
	c_www_domains_dir(Dir) ,
	atomic_concat(Dir, 'interaction_index.html' , InteractionIndex),
	open(InteractionIndex, write, Stream, []) ,
	inera_html_template('Index över tjänstekontrakt', Body, Content) ,
	l_html_write(Content, Stream) ,
	close(Stream).

% ----------------------------------------------------------------------

html_interaction_index_info([
    h1('Tjänstekontrakt'),
    div(attribute(class, ingress),
	'Här hittar du en förteckning över samtliga tjänstekontakt. I tabellen kan du också se om tjänstekontrakten är installerade i den nationella Tjänsteplattformen eller inte.' ),
    p(' '),
    'Informationen på denna sida är direkt hämtad från WSDL-filer i subversion samt tjänsteadresseringskatalogerna i den nationella Tjänsteplattformen. Klicka på länkarna i tabellen för mer information. ' ,
    newline,
    a([attribute(href, 'index.html')], 'Se även förteckning över alla tjänstedomäner.') ,
    p(' '),
    table(
	[
	tr(
	    [
	    th([attribute(class, int1)], 'Tjänstekontrakt') ,
	    th([attribute(class, int2)], ['Beskrivning ',
					  info('Beskrivningen kommer från tjänstens WSDL-fil')]),
	    th([attribute(class, int3)], 'Tjänstedomän') ,
	    th([attribute(class, int4)], 'NTjP') ,
	    th([attribute(class, int5)],  ['QA ',
					   info(left,['Informationen kommer från från NTjPs tjänsteadresseringskataloger. Den uppdaterades senast ', Date])
					  ]) ,
	    th([attribute(class, int6)], 'Ändrad')
	]) ,
	      TrList2
    ]
    )
]
			   ) :-
	tk_get_tak_date(prod, Date) ,
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

% Removed the version number from the list. If we show TAK info
% we need to show each version of each interaction.
% Need to think about this
get_interaction_index_info(
    Interaction,
    Description,
    DomainNameLink,
    InTp,
    InQA ,
    LastChanged) :-
	sv_get_interaction(Inter, _Version, _BP, Description, Domain, trunk, lastChanged(LastChanged, _Time)) ,
	Interaction = Inter, %atomic_list_concat([Inter, Version], ' ', Interaction) ,
	atomic_list_concat(Domain, ':', DomainName) ,
	html_domain_filename(Domain, FileName) ,
	(   html_domain_file_exist(Domain) -> DomainNameLink = a(attribute(href, FileName), DomainName) ; DomainNameLink = DomainName ) ,
	interaction_index_tp_info(prod, Domain, Inter, InTp) ,
	interaction_index_tp_info(qa, Domain, Inter, InQA) .

% ----------------------------------------------------------------------

interaction_index_tp_info(Envir, Domain, Interaction, 'X') :-
%	tk_get_interaction(Envir, Interaction, _Version, _BPVersion,
%	Domain) ,
	tk_get_service_contract(Envir, _Sc_Id, Interaction, Domain, _IVersion, _RivVersion) ,
	! .
interaction_index_tp_info(_Envir, _Domain, _Interaction, ' ') .


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
	get_latest_tkb_info(Domain, trunk, _TkbLink, lastChanged(_TkbDate, _), _TkbDescriptionList, _LongSwedish, ShortSwedish) ,
	atomic_list_concat([ShortSwedish, DomainName], ' - ', BothNames),
	% Show Swedish name if it exist
	(   ShortSwedish = '-' -> Heading = DomainName ; Heading = BothNames ) ,
	append([
	    h1(Heading),
	    div(attribute(class, ingress),
		['Här hittar du information om godkända releaser samt pågående utveckling av denna tjänstedomän.']) ,
	    'Se även listan över ',
	    a([attribute(href, 'index.html')], 'samtliga tjänstedomäner') ,
	    ' och ',
	    a([attribute(href, 'interaction_index.html')], 'samtliga tjänstekontrakt.')
	],
	       Tkb_html_list,
	       Content) ,
	html_domain_filename(Domain, FileName) ,
%	c_tmp_dir(Tmp),
%	atomic_concat(Tmp, 'www/domains/' , DomainFolder),
	c_www_domains_dir(DomainFolder) ,
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
	html_domain_info_consumers(Domain, Consumer_list) ,
	html_domain_info_producers(Domain, Producer_list) .

% ----------------------------------------------------------------------

html_domain_info_description(Domain,
		     [
				 h2(['Beskrivning av tjänstedomänen ' ,
				     info('Beskrivningen av tjänstedomänen är hämtad från kap 1.2 i dess Tjänstekontraktsbeskrivning (i trunk).' )]),
				 p(TkbDescription)
		     ]
		    ) :-
	get_latest_tkb_info(Domain, trunk, _TkbLink, lastChanged(_TkbDate, _), TkbDescriptionList, _LongSwedish, _ShortSwedish) ,
	format_description(TkbDescriptionList, TkbDescription) .



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
				      p(['Pågående utveckling innebär att dessa ännu inte är granskade av Inera Arkitektur och regelverk.']),
				      ul(
					  li([a([attribute(href, TkbLink)],'Tjänstekontraktsbeskrivning'), i([' (uppdaterades senast ', TkbDate, ').'])])) ,
				      ServiceList
				  ]
			) :-
	! ,
	get_latest_tkb_info(Domain, trunk, TkbLink, lastChanged(TkbDate, _), _TkbDescription, _LongSwedish, _ShortSwedish) ,
	html_domain_info_services(Domain, trunk, ServiceList) .

% A release or RC (or beta) which can be found in a tag in svn
html_domain_info_version2(Domain, Version,
			  [
				      h2([Uname, ' ', Version]),
				      p(['Denna ', Lname, ' är godkänd av Inera Arkitektur och regelverk. ']) ,
				      ul([
					  li(ZipLinkText),
					  li([a([attribute(href, TkbLink)],'Tjänstekontraktsbeskrivning'), i([' (uppdaterades senast ', TkbDate, ').'])])
				      ]),
				      ServiceList
				  ]
			 ) :-
	atomic_list_concat(Domain, '_', DomainName),
	atomic_list_concat([DomainName, Version], '_', Tag) ,
	tag_synonym(Tag, Uname, Lname),
	sv_get_latest_tkb_info(Domain, tag(Tag), TkbLink, lastChanged(TkbDate, _), _TkbDescription, _LongSwedish, _ShortSwedish) ,
	! ,
	html_ziplink(Domain, Version, ZipLinkText) , %% MUST ADD TAG HERE
	html_domain_info_services(Domain, tag(Tag), ServiceList) ,
	l_write_trace([Tag, ' - ', ServiceList],1) .

% A release or RC (or beta) which cannot be found in a tag in svn
html_domain_info_version2(Domain, Version,
			  [
				      h2([Uname, ' ', Version]),
				      p(['Denna ', Lname, ' är godkänd av Inera Arkitektur och regelverk.']) ,
				      ul([
					  li(ZipLinkText)
				      ]) ,
				      ['Dock finns ingen tag ', i(Tag), ' med en TKB i versionshanteringssystemet. Av den orsaken kan information om tjänstekontraktsbeskrivning och tjänster inte presenteras.']
				  ]
			 ) :-
	atomic_list_concat(Domain, '_', DomainName),
	atomic_list_concat([DomainName, Version], '_', Tag) ,
	tag_synonym(Tag, Uname, Lname),
	html_ziplink(Domain, Version, ZipLinkText) .


% ----------------------------------------------------------------------
%
%html_domain_info_services(OkType, Domain, Tag,
html_domain_info_services(Domain, Tag,
			  [
				      %					      h2('Tjänstekontrakt'),
				      p(b(['Tjänstekontrakt som är definierade i denna ', Lname])),
				      p('Informationen nedan är hämtad från respektive WSDL-fil.'),
				      ul( TrList )
				  ]
			 ) :-
	tag_synonym(Tag, _Uname, Lname),
	findall(
	    li([
		b(Service), ' ',
		b(Version), ' - ',
		Description,
		i([' (uppdaterades senast ', Date, ')'])
	    ]),
	    sv_get_interaction(Service, Version, _RivVersion,Description,Domain,Tag, lastChanged(Date, _Time)),
	    TrList) ,
	TrList \= [] ,
	! .

html_domain_info_services(_Domain, _Tag, p('(Denna tag kunde ej återfinnas i svn, varför information om tjänster ej kan presenteras)')) .

% ----------------------------------------------------------------------

html_domain_info_consumers(Domain,
			   [
			       h2(['Tjänstekonsumenter anslutna till domänens kontrakt ',
				   info(['Informationen nedan kommer från NTjPs tjänsteadresseringskataloger. Den uppdaterades senast ', Date])]),
			       p([
				   'Följande tjänstekonsumenter är anslutna till tjänster (tjänstekontrakt) i domänen i den nationellt gemensamma tjänsteplattformen.']),
			       ul( TrList )
			   ]
			  ) :-
	tk_get_tak_date(prod, Date) ,
	findall(
	    li([b(ConsumerDesc), ' (', ConsumerHSA, ')']),
%	    tk_get_domain_consumer(prod, Domain, C_HSA, C_Desc) ,
	    tk_get_service_contract_consumers(prod, _Interaction, Domain, _IVersion, _RivVersion, ConsumerHSA, ConsumerDesc) ,
	    TrList) ,
	TrList \= [] ,
	! .


html_domain_info_consumers(_Domain, [] ) .

% ----------------------------------------------------------------------

html_domain_info_producers(Domain,
			   [
			       h2(['Tjänsteproducenter anslutna till domänens kontrakt ',
				   info(['Informationen nedan kommer från NTjPs tjänsteadresseringskataloger. Den uppdaterades senast ', Date])]),
			       p('Följande tjänsteproducenter är anslutna via tjänster (tjänstekontrakt) i den nationellt gemensamma tjänsteplattformen.'),
			       ul( TrList )
			   ]
			  ) :-
	tk_get_tak_date(prod, Date) ,
	findall(
	    li([b(ProducerDesc), ' (', ProducerHostname, ')']),
%	    tk_get_domain_producer(prod, Domain, C_HSA, C_Desc) ,
	    tk_get_service_contract_producers(prod, _Interaction, Domain, _IVersion, _RivVersion, ProducerDesc, ProducerHostname),
	    TrList),
	TrList \= [] ,
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
			 a([attribute(href, ZipLink)],['Ladda ner zip-arkivet ']),
			 info([
			     'Länken till zip-arkivet har hämtats från tabellen över granskade domäner; http://rivta.se/servicedomaintable'
			 ])
		     ] ) :-
%	tag_synonym(Version, _Uname, Lname),
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

/* Add following to stop caching of pages:

<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
*/

inera_html_template(Title, Body,
    [
    string('<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">') ,
    comment(Comment),
    html([attribute(xmlns,'http://www.w3.org/1999/xhtml'), attribute('xml:lang','sv'), attribute(lang,'sv')],
	 [
	     head( [
		 link([
		     attribute(href,'http://fonts.googleapis.com/css?family=PT+Sans:400,700,400italic,700italic&subset=latin-ext'),
		     attribute(rel,'../css/Normal.css'),
		     attribute(type,'text/css')] ,
		      []) ,
		 meta([attribute('http-equiv','Content-Type') ,
		       attribute(content, 'text/html;'),
		       attribute(charset, 'UTF-8')],
		      []) ,
		 meta([attribute('http-equiv','Cache-Control') ,
		       attribute(content, 'no-cache, no-store, must-revalidate')],
		      []) ,
		 meta([attribute('http-equiv','Pragma') ,
		       attribute(content, 'no-cache')],
		      []) ,
		 meta([attribute('http-equiv','Expires') ,
		       attribute(content, '0')],
		      []) ,
		 link([
		     attribute(href,'../css/Normal.css'),
		     attribute(rel,'stylesheet'),
		     attribute(type, 'text/css'),
		     attribute(media, 'screen')],
		      []) ,
		 title(Title)
	     ] ) ,
	     body([Body, p([b('Senast uppdaterad '), Date])])
	 ] ) ] ) :-
	l_current_date(Date),
	l_current_time(Time) ,
	atomic_list_concat(['This HTML page was generated by leolib:html_generate_page/1', Date, Time], ' ', Comment) .

% ----------------------------------------------------------------------


get_tkb_info(Domain, trunk, TkbLink, lastChanged(TkbDate, _), TkbDescription) :-
	sv_get_tkb_info(Domain, trunk, TkbLink, lastChanged(TkbDate, _), TkbDescription, _LongSwedish, _ShortSwedish) ,
	! .
get_tkb_info(_Domain, trunk, '-', lastChanged('-', _Time), ['TKB kunde inte hittas.']) .

get_latest_tkb_info(Domain, Tag, TkbLink, LastChanged, TkbDescription, LongSwedish, ShortSwedish) :-
	sv_get_latest_tkb_info(Domain, Tag, TkbLink, LastChanged, TkbDescription, LongSwedish, ShortSwedish) ,
	! .

get_latest_tkb_info(_Domain, _Tag, '-', lastChanged('-', _Time), ['TKB kunde inte hittas.'], '-', '-') .
% ----------------------------------------------------------------------

html_domain_filename(Domain, FileName) :-
	atomic_list_concat(Domain, '_', DomainFileName) ,
	atomic_list_concat([DomainFileName, 'html'], '.', FileName) .

% ----------------------------------------------------------------------

html_domain_file_path_name(Domain, TheFile) :-
	c_www_domains_dir(HtmlDir) ,
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

% ----------------------------------------------------------------------
% Create a correct tag
format_tag(_Domain, trunk, trunk) :- ! .
format_tag(Domain, Version, tag(Tag)) :-
	atomic_list_concat(Domain, '_', Tag1) ,
	atomic_list_concat([Tag1, Version], '_', Tag) .

% ----------------------------------------------------------------------

format_description([], []) :- ! .

% Remove double empty lines
format_description([Line1, Line2 | Rest], Rest2) :-
	l_strip_blanks(Line1, Sline1),
	atom_length(Sline1, Len1) ,
	Len1 = 0 ,
	l_strip_blanks(Line2, Sline2),
	atom_length(Sline2, Len2) ,
	Len2 = 0 ,
	! ,
	format_description([Line2 | Rest], Rest2) .

% Insert a brake instead of single empty line
format_description([Line|Rest], ['<br>'|Rest2]) :-
	l_strip_blanks(Line, Sline),
	atom_length(Sline, Len) ,
	Len = 0 ,
	! ,
	format_description(Rest, Rest2) .

format_description([Line|Rest], [Line|Rest2]) :-
	format_description(Rest, Rest2) .


/* =======================================================================
	The SQL DATA FILE GENERATION STUFF
========================================================================== */

sql_generate :-
	l_counter_set(domains_tbl, 0),
	l_counter_set(domain_releases_tbl, 0),
	l_counter_set(interactions_tbl, 0),
	l_counter_set(domain_releases_interactions_tbl, 0),
	l_counter_set(tak_tbl, 0),
	l_counter_set(service_components_tbl, 0),
	l_counter_set(logical_addresses_tbl, 0),
	l_counter_set(routing_tbl, 0),
	l_counter_set(call_authorization_tbl, 0),
	l_erase_all(domains_tbl),
	l_erase_all(domain_releases_tbl),
	l_erase_all(interactions_tbl),
	l_erase_all(domain_releases_interactions_tbl),
	l_erase_all(tak_tbl),
	l_erase_all(service_components_tbl),
	l_erase_all(logical_addresses_tbl),
	l_erase_all(routing_tbl),
	l_erase_all(call_authorization_tbl),
	c_www_domains_dir(Dir) ,
	atomic_concat(Dir, 'domdb.sql.txt' , SQLFile),
	open(SQLFile, write, Stream, []) ,
	store_domains(Stream) ,
	close(Stream).





/* ===================================================================
Generate the domains table
- description
- id
- lvl1
- lvl2
- lvl3
- swedish_long
- swedish_short

recordz(domains_tbl,
	domains_tbl(id,
		    lvl1,
		    lvl2,
		    lvl3,
		    description,
		    swedish_short,
		    swedish_long))

recordz(domain_releases_tbl,
	domain_releases_tbl(id,
			    tag,
			    domain_id))

recorded(interactions_tbl,
	 interactions_tbl(Id,
			  Name,
			  Date,
			  Major,
			  Minor,
			  RivVersion,
			  Description )) .


recordz(domain_releases_interactions_tbl,
	domain_releases_interactions_tbl(id,
					 domain_release_id,
					 interaction_id))

recordz(tak_tbl, tak_tbl(id,
			 name))

recordz(service_components_tbl, service_components_tbl(id,
						       tak_id,
						       hsaid,
						       description,
						       hostname ))

recordz(logical_addresses_tbl,
	logical_addresses_tbl(id,
			      logical_address,
			      description,
			      tak_id))

recordz(routing_tbl,
	routing_tbl(id,
		    tak_id,
		    service_component_id,
		    interaction_id,
		    logical_address_id))

recordz(call_authorization_tbl,
	call_authorization_tbl(id,
			       interaction_id,
			       logical_address_id,
			       service_component_id,
			       tak_id))

====================================================================== */
store_domains(Stream) :-
	sv_get_domain(Domain) ,
	store_domain(Stream, Domain) ,
	fail .

store_domains(_Stream) .

% ----------------------------------------------------------------------

store_domain(Stream, Domain) :-
	% domain table
	get_latest_tkb_info(Domain, _Tag, _TkbLink, _LastChanged, DescriptionList, Swedish_short, Swedish_long) ,
	atomic_list_concat(Domain, ':', DomainName) ,
	atomic_list_concat(DescriptionList, ' ', DomDescription),
	l_remove_characters(DomDescription, [''''], DomDescription2) ,
	atomic_list_concat([DomainName, Swedish_short, Swedish_long, DomDescription2], ''' , ''', Values),
	write(Stream, 'INSERT INTO domain ( name, swedish_short, swedish_long, description) VALUES ('''),
	write(Stream, Values ),
	write(Stream, ''' );'),
	nl(Stream) ,
	%set @domain_id var
	write(Stream, 'SET @domain_id = (SELECT id FROM domain WHERE name = '''),
	write(Stream, DomainName),
	write(Stream, ''' );'),
	nl(Stream),
	% domain_version table
	get_domain_presentation_list(Domain, DomVerList) ,
	member(DomVer, DomVerList),
	write(Stream, 'INSERT INTO domain_version (domain_id, name) VALUES (@domain_id, '''),
	write(Stream, DomVer),
	write(Stream, ''' );'),
	nl(Stream) ,
	% interactions table
	format_tag(Domain, DomVer, Tag),
	sv_get_interaction(Service, Version, RivVersion, IntDescription, Domain, Tag, lastChanged(Date, _Time)),
	l_remove_characters(IntDescription, [''''], IntDescription2) ,
	split_version(Version, Major, Minor),
	write(Stream, 'INSERT IGNORE INTO interaction (domain_id, name, major, minor, last_changed_date, rivta_version, description) VALUES (@domain_id, '''),
	atomic_list_concat(['rivtabp', RivVersion], RivtaBpVersion),
	atomic_list_concat([Service, Major, Minor, Date, RivtaBpVersion, IntDescription2], ''' , ''', Values2),
	write(Stream, Values2),
	write(Stream, ''' );'),
	nl(Stream) ,
	% domain_version_interactions table
%	rivta_check(DomVer, Service, Major, Minor, AndRestrict),
	atomic_list_concat(['INSERT INTO domain_version_interactions (domain_version_id, interaction_id) VALUES ( (SELECT id FROM domain_version WHERE domain_id = @domain_id AND name = ''',
			    DomVer,
			    '''), (SELECT id FROM interaction WHERE domain_id = @domain_id AND name = ''',
			    Service,
			    ''' AND major = ',
			    Major,
			    ' AND minor = ',
			    Minor,
			    ' AND rivta_version = ''',
			    RivtaBpVersion,
			    ''') );'],
			    Values3) ,
	write(Stream, Values3),
	nl(Stream).


split_version(Version, Major, Minor) :- atomic_list_concat([Major, Minor], '.', Version) .

% rivta_check('1.0.3', 'AssertCareEngagement', 1, 0, ' AND rivta_version
% = ''rivtabp21''') :- ! .
%rivta_check(_DomVer, _Service, _Major, _Minor, '') .
/*
INSERT INTO domain_version_interactions (domain_version_id, interaction_id) VALUES ( (SELECT id FROM domain_version WHERE domain_id = @domain_id AND name = '1.0.3'), (SELECT id FROM interaction WHERE domain_id = @domain_id AND name = 'AssertCareEngagement' AND major = 1 AND minor = 0 AND rivta_version = 'rivtabp21') )
*/
/* ===================================================================

Generate the SQL insert statements

INSERT INTO domain (name, swedish_short, swedish_long, description)
VALUES ('crm:scheduling', 'Tidbokning', 'individens processtöd:tillgängliggör kontaktväg:tidbokning', 'beskrivning...');

SET @domain_id = (SELECT id FROM domain WHERE name = 'crm:scheduling');

INSERT INTO domain_version (domain_id, name)
VALUES (@domain_id, '1.1.1');

INSERT INTO interaction (domain_id, name, major, minor, last_changed_date, rivta_version, description)
    VALUES (@domain_id, 'CancelBooking', 1, 1, '2014-07-01', 'rivtabp20', 'beskrivning...');

INSERT INTO domain_version_interactions (domain_version_id, interaction_id)
    VALUES (
      (SELECT id FROM domain_version WHERE domain_id = @domain_id AND name = '1.1.1'),
      (SELECT id FROM interaction WHERE domain_id = @domain_id AND name = 'CancelBooking' AND major = 1 AND minor = 1)
    );


====================================================================== */







