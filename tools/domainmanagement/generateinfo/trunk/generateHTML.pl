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
    p(' ') ,
    div(attribute(class, ingress), 'Information på denna sida är extraherad från subversion, tjänstekontraktsbeskrivningar, tjänsteadresseringskatalogerna i NTjP samt Arkitektur och regelverks förteckning över svenska domännamn.') ,
    p(' ') ,
    table(
	  [
	      tr(
		  [
		  th([attribute(class, dom1)], 'Tjänstedomän') ,
		  th([attribute(class, dom2)], 'Svensk beteckning') ,
		  th([attribute(class, dom3)], 'NTjP') ,
		  th([attribute(class, dom4)], 'QA') ,
		  th([attribute(class, dom5)], 'Länk')
	      ]) ,
	      TrList
	  ]
	 )
]
		      ) :-
	findall(
	    tr([
		td(DomainNameLink),
		td(Popular),
		td([attribute(align,center)],InTp),
		td([attribute(align,center)],InQA),
		td([attribute(align,center)], TkbLink)]),
	    get_domain_index_info(DomainNameLink, Popular, InTp, InQA, TkbLink),
	    TrList).

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
% html_domain_info_tkb
% Basic information and acceptance level for domains accepted according
% to current rules
% ----------------------------------------------------------------------
html_domain_info_tkb(0, Domain, Tag, Version,
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
html_domain_info_tkb(1, Domain, _Tag, _Version,
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
html_domain_info_tkb(2, Domain, _Tag, _Version,
		     [
					  h2('Inledning'),
					  p(['Denna beskrivning är baserad på version på utvecklingsversionen (trunk) av domänen. Den är ännu inte granskad av Arkitektur och regelverk på Inera.']),
					  h2(['Beskrivning av tjänstedomänen (från ',
					      a([attribute(href, TkbLink)],'Tjänstekontraktsbeskrivningen'), ')'] ),
					  p(TkbDescription) ,
					  p([a([attribute(href, TkbLink)],'Tjänstekontraktsbeskrivningen'), ' uppdaterades senast ', b(TkbDate), '.'])
			     ]
		    ) :-
	sv_get_tkb_info(Domain, trunk, TkbLink, lastChanged(TkbDate, _), TkbDescription) ,
	! .

% ----- No TKB found
html_domain_info_tkb(_OkType, _Domain, _Tag, _Version,
		  [
				 h2('Inledning'),
				 p(['Denna beskrivning är baserad på version på utvecklingsversionen (trunk) av domänen. ']),
				 p(['Det var inte möjligt att identifiera eller extrahera information från Tjänstekontraktsbeskrivningen.'])
			     ]
		 ) .

% ----------------------------------------------------------------------

html_domain_info_services(OkType, Domain, Tag,
			  [
					      h2('Tjänstekontrakt'),
					      p('Följande tjänstekontrakt är definierade i denna version. Beskrivningstexten är hämtad från respektive WSDL-fil.'),
					      ul( TrList )
					  ]
			 ) :-
	(   OkType > 0 -> Tag2=trunk ; Tag2=tag(Tag) ),
	bagof(
	    li([b(Service), ' ', Version, ' - ', Description, ' (', Date, ')' ]),
	    sv_get_interaction(Service,Version, _RivVersion,Description,Domain,Tag2,lastChanged(Date, _Time)),
	    TrList).

% ----------------------------------------------------------------------

html_domain_info_consumers(Domain,
			   [
			       h2('Tjänstekonsumenter anslutna till domänen'),
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
			       h2('Tjänsteproducenter anslutna till domänen'),
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
% Create a text i a zip link exist
% ----------------------------------------------------------------------

html_ziplink(Domain,
	     [
		 h2('Nedladdning'),
		 p([
		     a([attribute(href, ZipLink)],'Ladda ner domänens zip-arkiv'),
		     ' (baserat på den aktuella ',
		     a([attribute(href, 'http://rivta.se/servicedomaintable')],'tabellen över granskade domäner'),
		     ').'
		 ])
	     ] ) :-
	dt_get_zip_link(Domain, ZipLink) ,
	! .

html_ziplink(_Domain, '') .


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
	l_write_trace(['* No Swedish name defined for: ', Domain], 0) .

% ----------------------------------------------------------------------

get_tkb_info(Domain, trunk, TkbLink, lastChanged(TkbDate, _), TkbDescription) :-
	sv_get_tkb_info(Domain, trunk, TkbLink, lastChanged(TkbDate, _), TkbDescription) ,
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

html_domain_info(Domain, [Tkb_html_list, Services_html_list, Consumer_list, Producer_list]) :-
	dt_get_domain_acceptance(Domain, Version, OkType) ,
	atomic_list_concat(Domain, '_', DomainName),
	atomic_list_concat([DomainName, Version], '_', Tag) ,
	l_write_trace([tag, Tag], 1),
	html_domain_info_tkb(OkType, Domain, Tag, Version, Tkb_html_list) ,
	html_domain_info_services(OkType, Domain, Tag, Services_html_list) ,
	l_write_trace(['Services_list: ', Services_html_list], 1 ),
	html_domain_info_consumers(Domain, Consumer_list) ,
	html_domain_info_producers(Domain, Producer_list) .




/* =======================================================================
	Other misc stuff
========================================================================== */

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Generate redirect files to tkb on request from Maria Brunsell
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
html_tkb_redirect(Domain) :-
	sv_get_tkb_info(Domain, trunk, TkbLink, _TkbDate, _TkbDescription) ,
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
	dt_get_domain_acceptance(Domain, Version, OkType),
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


testa(A) :-
	loadsvninfo:get_tkb_description3(
	    '/home/leo/tmp/tkb_store/clinicalprocess_activity_request-clinicalprocess_activity_request_1.0_beta_r2693/Tjänstekontraktbeskrivning NeR.txt', A).







