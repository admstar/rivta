:- module(statistics, [
	      loadstat/0,
	      st_get_all_stat_records/2,
	      st_get_stat_record/5,
	      st_load_statistics/2,
	      st_verify/1
	  ]) .

loadstat :- st_load_statistics(prod, 'AnropProd.csv') .

st_load_statistics(Envir, File) :-
	loadStat2(Envir, File) .

% -----------------------------------------------------------------------
set_stat_record(Envir, ConsumerHSA, LogicalAddress, Sc_Id, _Countx ) :-
	st_get_stat_record(Envir, ConsumerHSA, LogicalAddress, Sc_Id, _County ),
	l_write_trace(['** Error: Duplicate stat record found: ', ConsumerHSA, LogicalAddress, Sc_Id], 0) ,
	! ,
	fail.

set_stat_record(Envir, ConsumerHSA, LogicalAddress, Sc_Id, Count ) :-
	recordz(statInfo, stat_record(Envir, ConsumerHSA, LogicalAddress, Sc_Id, Count )) .

st_get_stat_record(Envir, ConsumerHSA, LogicalAddress, Sc_Id, Count ) :-
	recorded(statInfo, stat_record(Envir, ConsumerHSA, LogicalAddress, Sc_Id, Count )) .

st_get_all_stat_records(Envir, List) :-
	setof(stat_record(B,C,D,E),
	      st_get_stat_record(Envir,B,C,D,E) ,
	      List) .

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Verify the loading of the statistics
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
st_verify(No) :-
	st_get_all_stat_records(_, List) ,
	length(List, No) .

% -----------------------------------------------------------------------

loadStat2(Envir, File) :-
	nonvar(Envir) ,
	l_file_date_time(File, Date, _Time),
	set_stat_date(Envir, Date) ,
	stat_erase_all(Envir) ,
	l_read_file_to_list(File, [_HeaderLine | Lines], [encoding(iso_latin_1)]) ,
	sort(Lines, Lines2),
	loadStat3(Envir, Lines2) .

loadStat3(_Envir, []) :- ! .

loadStat3(Envir, [Line|Rest]) :-
	atomic_list_concat(NameList, ';', Line) ,
	loadStat4(Envir, NameList) ,
	l_write_trace(NameList, 2) ,
	! ,
	loadStat3(Envir, Rest) .

loadStat3(Envir, [Line|Rest]) :-
	l_write_trace(['tk_loadtak: could not parse line', Line], 0) ,
	loadStat3(Envir, Rest) .

loadStat4(Envir, [InConsumerHSA, _InConsumerDesc, InCount, InInteractionLong, InLogicalAddress, _InLADesc, _InProducerHSA, _InProducerDesc] ) :-
	l_strip_blanks(InConsumerHSA, ConsumerHSA),
%	l_strip_blanks(InConsumerDesc, ConsumerDesc),
	l_strip_blanks(InCount, Count),
	l_strip_blanks(InInteractionLong, InteractionLong),
	l_strip_blanks(InLogicalAddress, LogicalAddress),
	check_consumer(Envir, ConsumerHSA) ,
	check_logical_address(Envir, LogicalAddress),
	check_interaction(Envir, InteractionLong, Sc_Id) ,
	set_stat_record(Envir, ConsumerHSA, LogicalAddress, Sc_Id, Count ),
	l_write_trace(['Read: ', Envir, ConsumerHSA, LogicalAddress, Sc_Id], 3) .

loadStat4(_Envir, [_InConsumerHSA, InConsumerDesc, _InCount, InInteractionLong, _InLogicalAddress, InLADesc, _InProducerHSA, _InProducerDesc] ) :-
	l_write_trace(['Stat line could not be parsed', InConsumerDesc, InInteractionLong, InLADesc], 0) .

% -----------------------------------------------------------------------
%
set_stat_date(Envir, Date) :-
	l_erase_all(statInfo, statDate(Envir, _Date)) ,
	recordz(statInfo, statDate(Envir, Date)) .

get_stat_date(Envir, Date) :-
	recorded(statInfo, statDate(Envir, Date)) .

% -----------------------------------------------------------------------
%	recordz(statInfo, stat_record(Envir, ConsumerHSA, LogicalAddress, Sc_Id, _Count )) .
stat_erase_all(Envir) :-
	l_erase_all(statInfo, stat_record(Envir, _ConsumerHSA, _LogicalAddress, _Sc_Id, _Count )) .

% -----------------------------------------------------------------------

check_consumer(Envir, ConsumerHSA) :-
	tk_get_consumer(Envir, ConsumerHSA, _Desc) ,
	! .

check_consumer(_Envir, ConsumerHSA) :-
	l_write_trace(['** Error: Stat line contain unknown consumer: ', ConsumerHSA], 0) ,
	fail .

% -----------------------------------------------------------------------

check_logical_address(Envir, LogicalAddress) :-
	tk_get_logical_address(Envir, LogicalAddress, _Desc) ,
	! .

check_logical_address(_Envir, LogicalAddress) :-
	l_write_trace(['** Error: Stat line contain unknown Logical Address: ', LogicalAddress], 0) ,
	fail .

% -----------------------------------------------------------------------

check_interaction(Envir, InteractionLong, Sc_Id) :-
	tk_get_interaction_info(InteractionLong, Interaction, Domain, IVersion, RivVersion) ,
	tk_get_service_contract(Envir, Sc_Id, Interaction, Domain, IVersion, RivVersion) ,
	! .

check_interaction(_Envir, InteractionLong, 0) :-
	l_write_trace(['** Error: Stat line contain unknown Interaction: ', InteractionLong], 0) ,
	fail.



