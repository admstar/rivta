:- use_module(leolib).
:- use_module(loaddomaintable).
:- use_module(loadsvninfo).
:- use_module(loadtak).
:- use_module(generateHTML).
:- use_module(library(http/html_write)).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Constants

domainTableUrl('http://code.google.com/p/rivta/wiki/ServiceDomainTable').
svnRootDir('/home/leo/rivta-read-only/ServiceInteractions/riv') :-
	l_current_os(unix) .
svnRootDir('C:/rivta-read-only/ServiceInteractions/riv') :-
	l_current_os(win) .
svnTestDir('/home/leo/rivta-read-only/ServiceInteractions/riv/clinicalprocess/healthcond/actoutcome/trunk/schemas/interactions/GetMaternityMedicalHistoryInteraction') :-
	l_current_os(unix) .

c_tmp_dir('/home/leo/tmp/') .

c_html_domains_dir(Dir) :-
	c_tmp_dir(TmpDir) ,
	atomic_concat(TmpDir, 'www/domains/', Dir) .

c_takFile(prod, 'TakProd.csv') .
c_takFile(qa, 'TakQA.csv') .


% ----------------------------------------------------------------------

load :-
	loadDT ,
	loadSvn ,
	loadTak .
	tk_loadtak .

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



% ----------------------------------------------------------------------
