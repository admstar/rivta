:- module(loadtak, [
	      tk_loadtak/2 ,
	      tk_get_domain_consumer/4,
	      tk_get_domain_producer/4,
	      tk_get_interaction/5,
	      tk_get_tak_date/2,
	      tk_get_tak_info/5,
	      tk_verify/1
	  ]) .


tk_loadtak(Envir, File) :- loadTAK2(Envir, File) .

tk_get_tak_info(Envir, Domain, Interaction, IVersion, RivVersion) :-
	recorded(takInfo, takInfo(Envir, Domain, Interaction, IVersion, RivVersion)) .

tk_get_tak_date(Envir, Date) :-
	recorded(takInfo, takDate(Envir, Date)) .

% This predicate use bagof since I do not want it to return duplicates
tk_get_domain_consumer(Envir, Domain, Consumer_HSA, Consumer_Desc) :-
	findall(
	    [C_HSA, C_Desc] ,
	    tk_get_tak_info(Envir,
			    access(interaction(Domain, _, _, _), _, _, _),
			    consumer(C_HSA, C_Desc) ,
			    _,
			    _) ,
	    PairList) ,
	list_to_ord_set(PairList, PairSet) ,
	! ,
	member([Consumer_HSA, Consumer_Desc], PairSet) .

tk_get_domain_producer(Envir, Domain, Producer_HSA, Producer_Desc) :-
	findall(
	    [P_HSA, P_Desc] ,
	    tk_get_tak_info(Envir,
			    access(interaction(Domain, _, _, _), _, _, _),
			    _ ,
			    producer(P_HSA, P_Desc, _) ,
			    _) ,
	    PairList) ,
	list_to_ord_set(PairList, PairSet) ,
	! ,
%	\+ length(PairSet, 0),
	member([Producer_HSA, Producer_Desc], PairSet) .

tk_get_interaction(Envir, Interaction, IntVersion, BPVersion, Domain) :-
	tk_get_tak_info(
	    Envir,
	    access(interaction(Domain, Interaction, IntVersion, BPVersion), _, _, _) ,
	    _ ,
	    _ ,
	    _ ) .

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Verify the loading of the domain table
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

tk_verify(No) :-
	setof(struct(A,B,C,D,E),
	      tk_get_tak_info(A,B,C,D,E),
	      List) ,
	length(List, No) .


loadTAK2(Envir, File) :-
	nonvar(Envir) ,
	l_erase_all(takInfo, takDate(Envir, _Date)) ,
	l_file_date_time(File, Date, _Time),
	recordz(takInfo, takDate(Envir, Date)) ,
	l_erase_all(takInfo, takInfo(Envir, _Acc, _Cons, _Prod, _LA)) ,
	l_read_file_to_list(File, [_HeaderLine | Lines], [encoding(iso_latin_1)]) ,
%	l_write_trace(Lines, 3),
	sort(Lines, Lines2),
	loadTAK3(Envir, Lines2) .

loadTAK3(_Envir, []) :- ! .

loadTAK3(Envir, [Line|Rest]) :-
	atomic_list_concat(NameList, '\t', Line) ,
	loadTAK4(NameList, Cons, Prod, LA, Acc) ,
	nonvar(Cons), % Verify that we were able to parse the line
	nonvar(Prod),
	nonvar(LA),
	nonvar(Acc),
	recordz(takInfo, takInfo(Envir, Acc, Cons, Prod, LA)) ,
	l_write_trace([Cons, Prod, LA, Acc], 2) ,
	! ,
	loadTAK3(Envir, Rest) .

loadTAK3(Envir, [Line|Rest]) :-
	l_write_trace(['tk_loadtak: could not parse line', Line], 0) ,
	loadTAK3(Envir, Rest) .

loadTAK4([InteractionLong, ConsumerHSA, ConsumerDesc, LogicalAddress, LADesc, ProducerHSA, ProducerDesc, ProducerUrl],
	 consumer(ConsumerHSA, ConsumerDesc) ,
	 producer(ProducerHSA, ProducerDesc, ProducerUrl) ,
	 logical_adress(LogicalAddress, LADesc) ,
	 access(InteractionStruct, ConsumerHSA, LogicalAddress, ProducerHSA)
	) :-
	get_interaction_info(InteractionLong, InteractionStruct) ,
	l_write_trace([InteractionStruct, ConsumerHSA, ConsumerDesc, LogicalAddress, LADesc, ProducerHSA, ProducerDesc], 3) .

loadTAK4([InteractionLong, _ConsumerHSA, _ConsumerDesc, _LogicalAddress, _LADesc, _ProducerHSA, _ProducerDesc, _ProducerUrl],
	_Cons, _Prod, _LA, _Acc) :-
	l_write_trace('Line could not be parsed', InteractionLong, 0) .

% Parse: urn:riv:ehr:accesscontrol:AssertCareEngagement:1:rivtabp20
get_interaction_info(InteractionLong, interaction(DomainList, Interaction, IVersion, RivV)) :-
	atomic_list_concat([urn, riv | InterList], ':', InteractionLong) ,
	reverse(InterList, [RivVersion, IVersion, Interaction | DomainListRev]),
	reverse(DomainListRev, DomainList) ,
	get_interaction_info2(RivVersion, RivV) .
%	l_write_trace([DomainList, Interaction, IVersion, RivV],3 ) .

get_interaction_info2(rivtabp20, 20) :- ! .
get_interaction_info2(rivtabp21, 21) :- ! .
get_interaction_info2(Rivtabp, 00) :-
	l_write_trace(['Error in get_interaction_info2', Rivtabp], 0) .



