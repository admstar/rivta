:- use_module(leolib).
:- use_module(loaddomaintable).
:- use_module(loadsvninfo).
:- use_module(loadtak).
:- use_module(generateHTML).
:- use_module(library(http/html_write)).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Constants
%

domainTableUrl(Url) :-
	getenv('DOMAINTABLEURL', Url) ,
	! .

domainTableUrl('http://code.google.com/p/rivta/wiki/ServiceDomainTable').
% ----------------------------------------------------------------------
svnRootDir(Value) :-
	getenv('SVNRIVDIR', Value) ,
	! .

svnRootDir('/home/leo/rivta-read-only/ServiceInteractions/riv') :-
	l_current_os(unix) .

svnRootDir('C:/rivta-read-only/ServiceInteractions/riv') :-
	l_current_os(win) .

% ----------------------------------------------------------------------

c_www_domains_dir(Dir) :-
	getenv('WWWDIR', WwwDir),
	! ,
	atomic_concat(WwwDir, '/', Dir) .

c_www_domains_dir(Dir) :-
	getenv('HOME', HomeDir),
	! ,
	atomic_concat(HomeDir, '/tmp/www/domains/', Dir) .

c_www_domains_dir(_Dir) :-
	l_write_trace('WWW dir not found in main.pl!', 0) ,
	fail .

% ----------------------------------------------------------------------

c_takFile(prod, Prod) :-
	getenv('TAKPROD', Prod) ,
	! .

c_takFile(prod, 'TakProd.csv') :- ! .


c_takFile(qa, Qa) :-
	getenv('TAKQA', Qa) ,
	! .

c_takFile(qa, 'TakQA.csv') :- ! .

c_takFile(Envir, _) :-
	l_write_trace('*** Tak file not found ', Envir, 0 ).

% ----------------------------------------------------------------------
% main - to be called iteractivly
%
main :- main(_RC) .
main(RC) :-
	load ,
	verify ,
	l_counter_get(error_count, RC1) ,
	generate(RC1) ,
	l_counter_get(error_count, RC) .

external_main :-
	main(RC) ,
	halt(RC) .

load :-
	l_counter_set(error_count, 0),
	loadDT ,
	loadSvn ,
	loadTak .

loadDT :-
	domainTableUrl(URL) ,
	dt_load_domain_table(URL) .

loadSvn :-
	svnRootDir(Dir) ,
	sv_load_svninfo(Dir) .

loadTak :-
	c_takFile(prod, ProdFile) ,
	c_takFile(qa, QaFile) ,
	tk_loadtak(prod, ProdFile) ,
	tk_loadtak(qa, QaFile) .

generate(0) :-
	! ,
	html_generate.

generate(_) .

verify :-
	l_counter_set(error_count, 0),
	dt_verify(DLines) ,
	verify2('Domain table', DLines, 40) ,
	tk_verify(TLines),
	verify2('TAK (prod+QA)', TLines, 18000) ,
	sv_verify(domains, DoLines),
	verify2('Domains in SVN', DoLines, 50 ),
	sv_verify(interactions, ILines),
	verify2('Interactions in SVN', ILines, 1100 ),
	sv_verify(tkb, BLines),
	verify2('TKBs in SVN', BLines, 240 ),
	l_counter_get(error_count, RC) ,
	l_write_list(['RC = ', RC, nl ]) .

verify2(Label, Size, MinSize) :-
	Size > MinSize ,
	! ,
	l_write_list([Label, ': ', Size, nl]) .

verify2(Label, Size, _MinSize) :-
	l_counter_inc(error_count, _),
	l_write_list(['*** ERROR ***: ', Label, ' only ', Size, ' records loaded', nl]) .

% ----------------------------------------------------------------------
hello :-
	write('Hello world!'), nl .
