:- module(loaddomaintable, [
	      dt_get_domain_acceptance/3 ,
	      dt_get_domain_table/2 ,
	      dt_get_popular_name/2 ,
	      dt_get_swedish_name/2 ,
	      dt_get_zip_link/2 ,
	      dt_get_zip_link/3 ,
	      dt_load_domain_table/1 ,
	      dt_load_swedish_names/1
       ]) .


:- use_module(leolib).

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
This exported predicate load information in the recoreded db about all
domains in Servce Domain Table
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
dt_load_domain_table(URL) :-
	l_load_dom_http(URL, DOM) ,
	! ,
	l_erase_all(domainTable),
	process_dom(DOM) .

dt_load_domain_table(_).

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
These predicates loads and retrieves Swedish domain names from CSV file
(Sonjas file)
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
dt_load_swedish_names(File) :-
	l_erase_all(swTable),
	l_read_file_to_list(File, [_Headers | Lines ], [encoding(iso_latin_1)]) ,
	load_swedish_names(Lines) .

dt_get_swedish_name(Domain, Swedish) :-
	recorded(swTable, swName(Domain, Swedish)) ,
	Domain \= [''] .

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Retrieve the link to the zip archiv
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
dt_get_zip_link(Domain, ZipLink) :-
	dt_get_zip_link(Domain, _Version, ZipLink) .

dt_get_zip_link(Domain, Version, ZipLink) :-
	recorded(domainTable, domainTable(
				 Domain,
				 _PopularName,
				 Version,
				 check(s,_S_code, _S_link),
				 check(i,_I_code, _I_link),
				 check(t,_T_code, _T_link),
				 ZipLink )) .

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
This is the exported predicate to fetch a row from the domain table
store
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
dt_get_domain_table(Domain, Version) :-
	recorded(domainTable, domainTable(
				 Domain,
				 _PopularName,
				 Version,
				 check(s,_S_code, _S_link),
				 check(i,_I_code, _I_link),
				 check(t,_T_code, _T_link),
				 _ZipLink )) .



/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Get the Swedish name as specified on the SD table
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
dt_get_popular_name(Domain, Popular) :-
	recorded(_, domainTable(Domain, Popular, _,_,_,_,_ )) ,
	! .

dt_get_popular_name(_, ' ') .

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Get the acceptance level of a version of a domain from the DT-table
OkType is one of:
    0 - Godkänd under nuvarande regelverk
    1 - Godkänd under tidigare regelverk
    2 - Ej granskad/godkänd
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
dt_get_domain_acceptance(Domain, Version, OkType) :-
	recorded(_,domainTable(
		       Domain,
		       _,
		       Version,
		       _,
		       _,
		       check(t, 'G', Link),
		       _ )) ,
	get_domain_acceptance2(Link, OkType) .

get_domain_acceptance2(Link, 0) :-
	atom_length(Link, Len), Len > 3 ,  % To be current it must have a link to the review file
	! .
get_domain_acceptance2(_Link, 1) .

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Load Swedish names from List of CSV lines
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
load_swedish_names([]) :- ! .
load_swedish_names([Line | Rest]) :-
	atomic_list_concat([ _, _ , SwName, _, _, _, DomainName | _ ], ';' , Line ) ,
	atomic_list_concat(Domain, ':', DomainName),
	! ,
	recordz(swTable, swName(Domain, SwName)) ,
	load_swedish_names(Rest) .

% Error is usually due to crlf in fields. Search for '"'
load_swedish_names([Line | Rest]) :-
	l_write_trace(['** Error reading Swedish name CSV file, line: ', Line], 0 ) ,
	load_swedish_names(Rest) .


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Parse the dom to extract information from the domain table
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
process_dom([]) :- ! .

process_dom([element(tr,_,
		     [ element(td,_, [DomainName]) ,
		       element(td,_, [PopularName]) ,
		       element(td,_, [Version]) ,
		       element(td,_, _) ,
		       element(td,_, _) ,
		       element(td,_, S) ,
		       element(td,_, I) ,
		       element(td,_, T) ,
		       element(td,_, Link)
		     ]) |Tail]) :-
	l_strip_blanks(DomainName, DomainName2) ,
	atom_length(DomainName2, Len),
	Len > 2 ,
	! ,
	atomic_list_concat(DomainNameList, ':', DomainName2) ,
	l_strip_blanks(PopularName, PopularName2) ,
	l_strip_blanks(Version, Version2) ,
	% mk_tag_name(DomainNameList, Version2, TagName) ,
	% !!! The line above should probably change.
	%It is really better to save the Version and not try to
	%guess the tag name at this point. The responsibility of the
	%matching should be kept in main.pl.
	l_write_trace(['process_dom 1: Version:', Version2], 2),
	extract_link_text(S, S_code, S_link) ,
	extract_link_text(I, I_code, I_link) ,
	extract_link_text(T, T_code, T_link) ,
	extract_link_text(Link, _, ZipLink) ,
	recordz(domainTable, domainTable(
				 DomainNameList,
				 PopularName2,
				 Version2,
				 check(s,S_code, S_link),
				 check(i,I_code, I_link),
				 check(t,T_code, T_link),
				 ZipLink )) ,
	l_write_trace(['Domain table - Loaded: ', DomainName2], 2 ),
	process_dom(Tail) .

process_dom([element(_,_,C)|T]) :-
	! ,
	process_dom(C) ,
	process_dom(T) .

process_dom([H|T]) :-
	! ,
	process_dom(H) ,
	process_dom(T) .

process_dom(_) .


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Extract link and text
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
extract_link_text(List, Text2, Link) :-
	member(element(a, [=(href,Link)|_], [Text]), List), % Extract the URL to the zip file from the anchor element tag
	! ,
	l_strip_blanks(Text, Text2) .

extract_link_text([String], Text, '') :-
	l_strip_blanks(String, Text) .







