:- module(loadtak, [
	      tk_get_all_authorizations/2,
	      tk_get_all_consumers/2,
	      tk_get_all_logical_addresses/2,
	      tk_get_all_producers/2,
	      tk_get_all_routings/2,
	      tk_get_all_service_contracts/2,
	      tk_get_authorization/4,
	      tk_get_consumer/3,
	      tk_get_domain/2,
	      tk_get_interaction_info/5,
	      tk_get_logical_address/3,
	      tk_get_producer/4,
	      tk_get_routing/4,
	      tk_get_service_contract/6,
	      tk_get_service_contract_consumers/7,
	      tk_get_service_contract_producers/7,
	      tk_get_tak_date/2,
	      tk_loadtak/2 ,
	      /*
	      tk_get_domain_consumer/4,
	      tk_get_domain_producer/4,
	      tk_get_interaction/5,
	      tk_get_tak_date/2,
	      tk_get_tak_info/5,
	      */
	      tk_verify/1
	  ]) .


tk_loadtak(Envir, File) :- loadTAK2(Envir, File) .
% -----------------------------------------------------------------------
set_consumer(Envir, HSA, Desc) :-
	tk_get_consumer(Envir, HSA, Desc),
	! .
set_consumer(Envir, HSA, Desc) :-
	recordz(takConsumer, consumer(HSA, Desc, Envir)) .

tk_get_consumer(Envir, HSA, Desc) :-
	recorded(takConsumer, consumer(HSA, Desc, Envir)) .

tk_get_all_consumers(Envir, List) :-
	setof(consumer( HSA, Desc),
	      tk_get_consumer(Envir, HSA, Desc),
	      List) .
% -----------------------------------------------------------------------
set_producer(Envir, HSA, DescList, Hostname) :-
	get_producer(Envir, HSA, DescList, Hostname) ,
	! .
set_producer(Envir, HSA, DescList, Hostname) :-
	recordz(takProducer, producer(HSA, DescList, Hostname, Envir)) .

get_producer(Envir, HSA, DescList, Hostname) :-
	recorded(takProducer, producer(HSA, DescList, Hostname, Envir)) .

remove_producer(Envir, HSAStored, DescList, Hostname) :-
	l_erase_all(takProducer, producer(HSAStored, DescList, Hostname, Envir)) .

tk_get_producer(Envir, HSA, Desc, Hostname) :-
	get_producer(Envir, HSA, DescList, Hostname) ,
	atomic_list_concat(DescList, ' / ', Desc) .

tk_get_all_producers(Envir, List) :-
	setof(producer(HSA, Desc, Hostname) ,
	      tk_get_producer(Envir, HSA, Desc, Hostname) ,
	      List).

% -----------------------------------------------------------------------
set_service_contract(Envir, Sc_Id, Interaction, Domain, IVersion, RivVersion) :-
	tk_get_service_contract(Envir, Sc_Id, Interaction, Domain, IVersion, RivVersion) ,
	! .

set_service_contract(Envir, Sc_Id, Interaction, Domain, IVersion, RivVersion) :-
	recordz(takService, service_contract(Sc_Id, Interaction, Domain, IVersion, RivVersion, Envir)) .

tk_get_service_contract(Envir, Sc_Id, Interaction, Domain, IVersion, RivVersion) :-
	recorded(takService, service_contract(Sc_Id, Interaction, Domain, IVersion, RivVersion, Envir)) .

tk_get_all_service_contracts(Envir, List) :-
	setof(service_contract(Sc_Id, Interaction, Domain, IVersion, RivVersion) ,
	      tk_get_service_contract(Envir, Sc_Id, Interaction, Domain, IVersion, RivVersion) ,
	      List) .
% -----------------------------------------------------------------------
tk_get_domain(Envir, Domain) :-
	findall(
	    domain(Domain) ,
	    tk_get_service_contract(Envir, _Sc_Id, _Interaction, Domain, _IVersion, _RivVersion) ,
	    List),
	List \= [] ,
	sort(List, L2),
	member(domain(Domain), L2).
% -----------------------------------------------------------------------
set_authorization(Envir, Sc_Id, Logical_address, ConsumerHSA) :-
	tk_get_authorization(Envir, Sc_Id, Logical_address, ConsumerHSA) ,
	! .

set_authorization(Envir, Sc_Id, Logical_address, ConsumerHSA) :-
	recordz(takAuth, authorization(Sc_Id, Logical_address, ConsumerHSA, Envir)) .

tk_get_authorization(Envir, Sc_Id, Logical_address, ConsumerHSA) :-
	recorded(takAuth, authorization(Sc_Id, Logical_address, ConsumerHSA, Envir)) .

tk_get_all_authorizations(Envir, List) :-
	setof(authorization(B,C,D),
	      tk_get_authorization(Envir,B,C,D) ,
	      List) .

% -----------------------------------------------------------------------
set_routing(Envir, Sc_Id, Logical_address, Hostname) :-
	tk_get_routing(Envir, Sc_Id, Logical_address, Hostname) ,
	! .

set_routing(Envir, Sc_Id, Logical_address, Hostname) :-
	recordz(takRouting, routing(Sc_Id, Logical_address, Hostname, Envir)) .

tk_get_routing(Envir, Sc_Id, Logical_address, Hostname) :-
	recorded(takRouting, routing(Sc_Id, Logical_address, Hostname, Envir)) .

tk_get_all_routings(Envir, List) :-
	setof(routing(B,C,D) ,
	      tk_get_routing(Envir, B, C, D),
	      List) .
% -----------------------------------------------------------------------

set_logical_address(Envir, LA, LADesc) :-
	tk_get_logical_address(Envir, LA, LADesc) ,
	! .

set_logical_address(Envir, LA, LADesc) :-
	recordz(takLA, logical_address(LA, LADesc, Envir)) .

tk_get_logical_address(Envir, LA, LADesc) :-
	recorded(takLA, logical_address(LA, LADesc, Envir)) .

tk_get_all_logical_addresses(Envir, List) :-
	setof(logical_address(B,C),
	      tk_get_logical_address(Envir,B,C) ,
	      List) .
% -----------------------------------------------------------------------

tk_get_service_contract_consumers(Envir, Interaction, Domain, IVersion, RivVersion, ConsumerHSA, ConsumerDesc) :-
	findall(
	    cons(ConsumerDesc, ConsumerHSA),
	    (  tk_get_service_contract(Envir, Sc_Id, Interaction, Domain, IVersion, RivVersion) ,
	       tk_get_authorization(Envir, Sc_Id, _Logical_address, ConsumerHSA) ,
	       tk_get_consumer(Envir, ConsumerHSA, ConsumerDesc) ) ,
	    List ) ,
	sort(List, List2) ,
	member(cons(ConsumerDesc, ConsumerHSA), List2) .

% -----------------------------------------------------------------------

% Do not use in reverse, input is contract info, out is producer info
tk_get_service_contract_producers(Envir, Interaction, Domain, IVersion, RivVersion, ProducerDesc, Hostname) :-
	findall(
	    prod(ProducerDesc, Hostname) ,
	    (   tk_get_service_contract(Envir, Sc_Id, Interaction, Domain, IVersion, RivVersion) ,
		tk_get_routing(Envir, Sc_Id, _Logical_address, Hostname) ,
		tk_get_producer(Envir, _ProducerHSA, ProducerDesc, Hostname) ) ,
	    List ) ,
	sort(List, List2) ,
	member(prod(ProducerDesc, Hostname), List2) .



% -----------------------------------------------------------------------

set_tak_date(Envir, Date) :-
	l_erase_all(takInfo, takDate(Envir, _Date)) ,
	recordz(takInfo, takDate(Envir, Date)) .

tk_get_tak_date(Envir, Date) :-
	recorded(takInfo, takDate(Envir, Date)) .


/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Verify the loading of the domain table
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

tk_verify(No) :-
	tk_get_all_authorizations(_, List) ,
	length(List, No) .

% -----------------------------------------------------------------------
tak_erase_all(Envir) :-
	l_erase_all(takConsumer, consumer(_, _, Envir)) ,
	l_erase_all(takProducer, producer(_, _, _, Envir)) ,
	l_erase_all(takLA, logical_adress(_, _, Envir)) ,
	l_erase_all(takAuth, authorization(_, _, _, Envir)) ,
	l_erase_all(takRouting, routing(_, _, _, Envir)) ,
	l_erase_all(takService, service_contract(_, _, _, _, _, Envir)) .

% -----------------------------------------------------------------------

loadTAK2(Envir, File) :-
	nonvar(Envir) ,
	l_counter_set(service_contract_id, 0) ,
	l_file_date_time(File, Date, _Time),
	set_tak_date(Envir, Date) ,
	tak_erase_all(Envir) ,
	l_read_file_to_list(File, [_HeaderLine | Lines], [encoding(iso_latin_1)]) ,
	sort(Lines, Lines2),
	loadTAK3(Envir, Lines2) .

loadTAK3(_Envir, []) :- ! .

loadTAK3(Envir, [Line|Rest]) :-
	atomic_list_concat(NameList, '\t', Line) ,
	loadTAK4(Envir, NameList) ,
	l_write_trace(NameList, 2) ,
	! ,
	loadTAK3(Envir, Rest) .

loadTAK3(Envir, [Line|Rest]) :-
	l_write_trace(['tk_loadtak: could not parse line', Line], 0) ,
	loadTAK3(Envir, Rest) .

loadTAK4(Envir, [InteractionLong, InConsumerHSA, InConsumerDesc, InLogicalAddress, InLADesc, InProducerHSA, InProducerDesc, InProducerUrl]
%        ,
%	 consumer(ConsumerHSA, ConsumerDesc) ,
%	 producer(ProducerHSA, ProducerDesc, Hostname) ,
%	 logical_adress(LogicalAddress, LADesc) ,
%	 InteractionStruct,
%	 access(InteractionStruct, ConsumerHSA, LogicalAddress,
%	 ProducerHSA)
	) :-
	l_strip_blanks(InConsumerHSA, ConsumerHSA),
	l_strip_blanks(InConsumerDesc, ConsumerDesc),
	l_strip_blanks(InLogicalAddress, LogicalAddress),
	l_strip_blanks(InLADesc, LADesc),
	l_strip_blanks(InProducerHSA, ProducerHSA),
	l_strip_blanks(InProducerDesc, ProducerDesc),
	l_strip_blanks(InProducerUrl, ProducerUrl),
%	store(consumer, Envir, ConsumerHSA, ConsumerDesc) ,
	set_consumer(Envir, ConsumerHSA, ConsumerDesc) ,
	clean_producer_hsa(ProducerHSA, ProdHSA),
	l_strip_blanks(ProdHSA, ProdHSA2) ,
	l_get_hostname(ProducerUrl, Hostname) ,
	store(producer, Envir, ProdHSA2, ProducerDesc, Hostname) ,
	set_logical_address(Envir, LogicalAddress, LADesc) ,
	tk_get_interaction_info(InteractionLong, Interaction, Domain, IVersion, RivVersion) ,
	store(service_contract, Envir, Interaction, Domain, IVersion, RivVersion, SC_Id) ,
	set_routing(Envir, SC_Id, LogicalAddress, Hostname),
	set_authorization(Envir, SC_Id, LogicalAddress, ConsumerHSA) ,
	l_write_trace([Envir, ConsumerHSA, ConsumerDesc, LogicalAddress, LADesc, ProducerHSA, ProducerDesc, Hostname, Interaction, Domain, IVersion, RivVersion, SC_Id ], 3) .

loadTAK4(_Envir, [InteractionLong, _ConsumerHSA, _ConsumerDesc, _LogicalAddress, _LADesc, _ProducerHSA, _ProducerDesc, _ProducerUrl]
%	, _Cons, _Prod, _LA, _Acc
	) :-
	l_write_trace('Line could not be parsed', InteractionLong, 0) .

% Parse: urn:riv:ehr:accesscontrol:AssertCareEngagement:1:rivtabp20
tk_get_interaction_info(InteractionLong, Interaction, DomainList, IVersion, RivV) :-
	atomic_list_concat([urn, riv | InterList1], ':', InteractionLong) ,
	% A patch to manage the faked level in Sec services domains
	patchBifDomain(InterList1, InterList2),
	reverse(InterList2, [RivVersion, IVersion, Interaction | DomainListRev]),
	reverse(DomainListRev, DomainList) ,
	get_interaction_info2(RivVersion, RivV) .
%	l_write_trace([DomainList, Interaction, IVersion, RivV],3 ) .

get_interaction_info2(rivtabp20, 20) :- ! .
get_interaction_info2(rivtabp21, 21) :- ! .
get_interaction_info2(Rivtabp, 00) :-
	l_write_trace(['Error in get_interaction_info2', Rivtabp], 0) .

% Store producer information
store(producer, Envir, ProducerHSA, Desc, Hostname) :-
	store_producer( Envir, ProducerHSA, Desc, Hostname).

% Service contract
store(service_contract, Envir, Interaction, Domain, IVersion, RivVersion, Sc_Id) :-
	tk_get_service_contract(Envir, Sc_Id, Interaction, Domain, IVersion, RivVersion) ,
	! .

store(service_contract, Envir, Interaction, Domain, IVersion, RivVersion, Sc_Id) :-
	% Get unique id
	l_counter_inc(service_contract_id, Sc_Id),
	set_service_contract(Envir, Sc_Id, Interaction, Domain, IVersion, RivVersion) .

% Help predicate to store(producer clause
store_producer(Envir, HSA, Desc, Hostname) :-
	\+ get_producer(Envir, _HSA, _DescList, Hostname) ,
	atom_length(Desc, Len),
	Len > 1 ,
	! ,
	set_producer(Envir, HSA, [Desc], Hostname) .

store_producer(Envir, HSA, Desc, Hostname) :-
	get_producer(Envir, HSAStored, DescList, Hostname) ,
	% Remove different postfixes of all the descriptions
	manage_producer_descriptions(DescList, Desc, UpdatedDescList),
	remove_producer(Envir, HSAStored, DescList, Hostname) ,
	! ,
	set_producer(Envir, HSA, UpdatedDescList, Hostname) .

store_producer(Envir, HSA, Desc, _Hostname) :-
	l_write_trace(['*** Error, producer with HSA= "', HSA, '" exist with description problem ("', Desc, '") in ', Envir, nl], 0).

% Remove all characters after " - " sequence
clean_producer_hsa(InHsa, OutHsa) :-
	sub_atom(InHsa, FoundAt, _Len, _After, ' - '),
	sub_atom(InHsa, 0, FoundAt, _After2, OutHsa).

% Manage producer descriptions
% Take a list of existing description and a new one. Try tto match the
% new description with the existing ones, finding a common start longer
% than (for example) 2.
% If match, just return the list unchanged, otherwise add the new
% description to the list and return.

% No match, return the new description
manage_producer_descriptions([], InDesc, [InDesc]) :- ! .

% Got a match for item, updated the string
manage_producer_descriptions([Desc1 | InRest], InDesc, [OutDesc | InRest]) :-
	manage_producer_combine( Desc1, InDesc, OutDesc) ,
	! .

% No match for item, continue with next
manage_producer_descriptions([Desc | InRest], InDesc, [Desc | OutRest]) :-
	manage_producer_descriptions(InRest, InDesc, OutRest) .

% Combine two items if the have the same prefix
manage_producer_combine(Desc1, InDesc, NewDesc4) :-
	l_common_prefix([Desc1, InDesc], NewDesc) ,
	l_strip_blanks(NewDesc, NewDesc2),
	l_strip_trailing_chars(NewDesc2, '-', NewDesc3),
	l_strip_blanks(NewDesc3, NewDesc4),
	atom_length(NewDesc4, Len),
	Len > 2 .


% Mangage error in Security services domains
patchBifDomain([ehr,blocking, _FakeLvl | Rest] ,[ehr,blocking | Rest]) :- ! .
patchBifDomain([ehr,commission, _FakeLvl | Rest] ,[ehr,commission | Rest]) :- ! .
patchBifDomain([ehr,log, _FakeLvl | Rest] ,[ehr,log | Rest]) :- ! .
patchBifDomain([ehr,patientconsent, _FakeLvl | Rest] ,[ehr,patientconsent | Rest]) :- ! .
patchBifDomain([ehr,patientrelationship, _FakeLvl | Rest] ,[ehr,patientrelationship | Rest]) :- ! .
patchBifDomain(OkDomain, OkDomain).












