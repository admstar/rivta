:- module(statistics, [
	      loadstat/0,
	      st_get_all_stat_records/2,
	      st_get_stat_record/5,
	      st_get_stat_record/6,
	      st_load_statistics/2,
	      st_verify/1
	  ]) .

:- use_module(leolib).
:- use_module(loadtak).



loadstat :- st_load_statistics(prod, 'AnropProd.csv') .

st_load_statistics(Envir, File) :-
	loadStat2(Envir, File) .

% -----------------------------------------------------------------------
% The default addressing mechanism might give me multiple lines in the
% stat file which point to the same stat record in the db. The consumer
% might have addressed different LAs, but they default back to the same
% VG. I must add them up.
set_stat_record(Envir, ConsumerHSA, LogicalAddress, LogicalAddressWithHash, Sc_Id, Count ) :-
	\+ recorded(statInfo, stat_record(Envir, ConsumerHSA, LogicalAddress, LogicalAddressWithHash, Sc_Id, _ )) ,
	! ,
	atom_number(Count, IntCount) ,
%	set_stat_record2(Envir, ConsumerHSA, LogicalAddress, Sc_Id,
%	AlreadyCounted ) ,
%	NewCount is AlreadyCounted + IntCount ,
	recordz(statInfo, stat_record(Envir, ConsumerHSA, LogicalAddress, LogicalAddressWithHash, Sc_Id, IntCount )) .

set_stat_record(Envir, ConsumerHSA, LogicalAddress, LogicalAddressWithHash, Sc_Id, _Count ) :-
	l_write_trace(['** Error - duplicate stat record found: ', Envir, ConsumerHSA, LogicalAddress, LogicalAddressWithHash, Sc_Id], 3) .

% This predicate read the actual recorded db
% st_get_stat_record/6
st_get_stat_record(Envir, ConsumerHSA, LogicalAddress, LogicalAddressWithHash ,Sc_Id, Count ) :-
	recorded(statInfo, stat_record(Envir, ConsumerHSA, LogicalAddress, LogicalAddressWithHash, Sc_Id, Count )) .

st_get_all_stat_records(Envir, List) :-
	setof(stat_record(B,C,D,E,F),
	      st_get_stat_record(Envir,B,C,D,E,F) ,
	      List) .

% This predicate return statistic records where sum is accumulated for
% the hash addresses where the LA is the same
% st_get_stat_record/5
st_get_stat_record(Envir, ConsumerHSA, LogicalAddress, Sc_Id, Count ) :-
	setof(
	    record(Envir, ConsumerHSA, LogicalAddress, Sc_Id, Count1 ) ,
	    st_get_stat_record2(Envir, ConsumerHSA, LogicalAddress, Sc_Id, Count1 ) ,
	    List ),
	member(record(Envir, ConsumerHSA, LogicalAddress, Sc_Id , Count) , List) .

st_get_stat_record2(Envir, ConsumerHSA, LogicalAddress, Sc_Id, Count ) :-
	setof(
	    record(Envir, ConsumerHSA, LogicalAddress, Sc_Id ) ,
	    st_get_stat_record(Envir, ConsumerHSA, LogicalAddress, _LogicalAddressWithHash1, Sc_Id, _Count1 ) ,
	    List ),
	List \= [] ,
	member(record(Envir, ConsumerHSA, LogicalAddress, Sc_Id ) , List),
	get_count(Envir, ConsumerHSA, LogicalAddress, Sc_Id, Count ) .

% This predicate accumulates the sums for
% the same LogicalAddres where LogicalAddressWithHash is different.
get_count(Envir, ConsumerHSA, LogicalAddress, Sc_Id, _Count ) :-
	nonvar(Envir),
	nonvar(ConsumerHSA),
	nonvar(LogicalAddress),
	nonvar(Sc_Id),
	l_counter_set(call_count, 0) ,
	st_get_stat_record(Envir, ConsumerHSA, LogicalAddress, _LogicalAddressWithHash1, Sc_Id, Count ) ,
	l_counter_add(call_count, Count) ,
	fail .

get_count(_Envir, _ConsumerHSA, _LogicalAddress, _Sc_Id, Count ) :-
	l_counter_get(call_count, Count) ,
	l_counter_remove(call_count) .


/*
% Do we already have a count for this record to be added
set_stat_record2(Envir, ConsumerHSA, LogicalAddress, Sc_Id, Count ) :-
	st_get_stat_record(Envir, ConsumerHSA, LogicalAddress, Sc_Id, Count ) ,
	l_erase_all(statInfo, stat_record(Envir, ConsumerHSA, LogicalAddress, Sc_Id, _Count )) ,
	integer(Count),
	! .
% If not, return 0
set_stat_record2(_Envir, _ConsumerHSA, _LogicalAddress, _Sc_Id, 0 ) .
*/
/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Verify the loading of the statistics
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
st_verify(No) :-
	st_get_all_stat_records(_, List) ,
	length(List, No) .
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
	l_erase_all(statInfo, stat_record(Envir, _ConsumerHSA, _LogicalAddress, _LogicalAddressWithHash, _Sc_Id, _Count )) .

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
	l_write_trace(['st_loadstat3: could not parse line', Line], 0) ,
	loadStat3(Envir, Rest) .

loadStat4(Envir, [InConsumerHSA, _InConsumerDesc, InCount, InInteractionLong, InLogicalAddress, _InLADesc, _InProducerHSA, _InProducerDesc] ) :-
	l_strip_blanks(InConsumerHSA, ConsumerHSA),
%	l_strip_blanks(InConsumerDesc, ConsumerDesc),
	l_strip_blanks(InCount, Count),
	l_strip_blanks(InInteractionLong, InteractionLong),
	l_strip_blanks(InLogicalAddress, LogicalAddressWithHash),
	check_consumer(Envir, ConsumerHSA) ,
	check_logical_address(Envir, LogicalAddressWithHash, LogicalAddress),
	check_interaction(Envir, InteractionLong, Sc_Id) ,
	set_stat_record(Envir, ConsumerHSA, LogicalAddress, LogicalAddressWithHash, Sc_Id, Count ),
	! ,
	l_write_trace(['Read: ', Envir, ConsumerHSA, LogicalAddress, Sc_Id], 3) .

loadStat4(_Envir, [_InConsumerHSA, InConsumerDesc, _InCount, InInteractionLong, _InLogicalAddress, InLADesc, _InProducerHSA, _InProducerDesc] ) :-
	l_write_trace(['Stat line could not be parsed', InConsumerDesc, InInteractionLong, InLADesc], 3) .

% -----------------------------------------------------------------------

check_consumer(Envir, ConsumerHSA) :-
	tk_get_consumer(Envir, ConsumerHSA, _Desc) ,
	! .

check_consumer(_Envir, ConsumerHSA) :-
	l_write_trace(['** Error: Stat line contain unknown consumer: ', ConsumerHSA], 0) ,
	fail .

% -----------------------------------------------------------------------

check_logical_address(Envir, LogicalAddressWithHash, LogicalAddress) :-
	atomic_list_concat( List, '#', LogicalAddressWithHash) ,
	reverse(List, RList ),
	check_logical_address2(Envir, RList, LogicalAddress),
	! .

check_logical_address(_Envir, LogicalAddressWithHash, _LogicalAddress) :-
	l_write_trace(['** Error: Stat line contain unknown Logical Address: ', LogicalAddressWithHash], 0) ,
	fail .

% Manage the hack with VG#VE addressing
check_logical_address2(_Envir, [], _) :-
	! ,
	fail.

check_logical_address2(Envir, [LogicalAddress|_VGList], LogicalAddress) :-
	tk_get_logical_address(Envir, LogicalAddress, _Desc) ,
	! .

check_logical_address2(Envir, [_LogicalAddress|VGList], LogicalAddress) :-
	check_logical_address2(Envir, VGList, LogicalAddress) .
% -----------------------------------------------------------------------

check_interaction(Envir, InteractionLong, Sc_Id) :-
	tk_get_interaction_info(InteractionLong, Interaction, Domain, IVersion, RivVersion) ,
	tk_get_service_contract(Envir, Sc_Id, Interaction, Domain, IVersion, RivVersion) ,
	! .

check_interaction(_Envir, InteractionLong, 0) :-
	l_write_trace(['** Error: Stat line contain unknown Interaction: ', InteractionLong], 0) ,
	fail.

% -----------------------------------------------------------------------


/* ========================================================================
Below are a number of predicates to extract the statistic information in
different formats
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

total_count(_X) :-
	l_counter_set(total_count, 0) ,
	st_get_stat_record(prod,_Cons,_LA,_Sc,Count) ,
	l_counter_add(total_count, Count) ,
	fail.
total_count(X) :-
	l_counter_get(total_count, X) ,
	l_counter_remove(total_count) .
