:- use_module(leolib).
:- use_module(loaddomaintable).
:- use_module(loadsvninfo).
:- use_module(loadtak).
:- use_module(generateHTML).
:- use_module(library(http/html_write)).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Constants

sonjasCsv('sonja2.csv') .
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

% ----------------------------------------------------------------------

load :-
	loadDT ,
	loadSvn ,
	tk_loadtak .
%	tk_loadtak(qa) .

loadDT :-
	domainTableUrl(URL) ,
	dt_load_domain_table(URL) ,
	sonjasCsv(SonjasFil),
	dt_load_swedish_names(SonjasFil) .

loadSvn :-
	svnRootDir(Dir) ,
	sv_load_svninfo(Dir) .

% ----------------------------------------------------------------------
