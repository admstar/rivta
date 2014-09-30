:- module(loadtak, [
	      tk_get_producer/4,
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

tk_get_producer(Envir, HSA, Desc, Hostname) :-
	recorded(takInfo, producer(Envir, HSA, Desc, Hostname)) .


/*

%	recorded(takInfo, service_contract(Envir, Sc_Id, Interaction, Domain, IVersion, RivVersion)) ,
tk_get_tak_info(Envir, Domain, Interaction, IVersion, RivVersion) :-
	recorded(takInfo, service_contract(Envir, _ScId, Interaction, Domain, IVersion, RivVersion)) .

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

*/

/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
Verify the loading of the domain table
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

tk_verify(No) :-
	setof(struct(A,B,C,D,E),
	      tk_get_tak_info(A,B,C,D,E),
	      List) ,
	length(List, No) .

% -----------------------------------------------------------------------

loadTAK2(Envir, File) :-
	nonvar(Envir) ,
	l_erase_all(takInfo, takDate(Envir, _Date)) ,
	l_counter_set(service_contract_id, 0) ,
	l_file_date_time(File, Date, _Time),
	recordz(takInfo, takDate(Envir, Date)) ,
	l_erase_all(takInfo, consumer(Envir, _, _)) ,
	l_erase_all(takInfo, producer(Envir, _, _, _)) ,
	l_erase_all(takInfo, logical_adress(Envir, _, _)) ,
	l_erase_all(takInfo, authorization(Envir, _, _, _)) ,
	l_erase_all(takInfo, routing(Envir, _, _, _)) ,
	l_erase_all(takInfo, service_contract(Envir, _, _, _, _, _)) ,
	l_read_file_to_list(File, [_HeaderLine | Lines], [encoding(iso_latin_1)]) ,
%	l_write_trace(Lines, 3),
	sort(Lines, Lines2),
	loadTAK3(Envir, Lines2) .

loadTAK3(_Envir, []) :- ! .

loadTAK3(Envir, [Line|Rest]) :-
	atomic_list_concat(NameList, '\t', Line) ,
	loadTAK4(Envir, NameList) ,
%	nonvar(Cons), % Verify that we were able to parse the line
%	nonvar(Prod),
%	nonvar(LA),
%	nonvar(Acc),
%	recordz(takInfo, takInfo(Envir, Acc, Cons, Prod, LA)) ,
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
	store(consumer, Envir, ConsumerHSA, ConsumerDesc) ,
	l_get_hostname(ProducerUrl, Hostname) ,
	store(producer, Envir, ProducerHSA, ProducerDesc, Hostname) ,
	store(logical_address, Envir, LogicalAddress, LADesc) ,
	get_interaction_info(InteractionLong, Interaction, Domain, IVersion, RivVersion) ,
	store(service_contract, Envir, Interaction, Domain, IVersion, RivVersion, SC_Id) ,
	store(routing, Envir, SC_Id, LogicalAddress, ProducerHSA),
	store(authorization, Envir, SC_Id, LogicalAddress, ConsumerHSA) ,
	l_write_trace([Envir, ConsumerHSA, ConsumerDesc, LogicalAddress, LADesc, ProducerHSA, ProducerDesc, Hostname, Interaction, Domain, IVersion, RivVersion, SC_Id ], 3) .

loadTAK4(_Envir, [InteractionLong, _ConsumerHSA, _ConsumerDesc, _LogicalAddress, _LADesc, _ProducerHSA, _ProducerDesc, _ProducerUrl]
%	, _Cons, _Prod, _LA, _Acc
	) :-
	l_write_trace('Line could not be parsed', InteractionLong, 0) .

% Parse: urn:riv:ehr:accesscontrol:AssertCareEngagement:1:rivtabp20
get_interaction_info(InteractionLong, Interaction, DomainList, IVersion, RivV) :-
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

% Store consumer information
store(consumer, Envir, HSA, Desc) :-
	recorded(takInfo, consumer(Envir, HSA, Desc)) ,
	! .
store(consumer, Envir, HSA, Desc) :-
	\+ recorded(takInfo, consumer(Envir, HSA, _Desc)) ,
	recordz(takInfo, consumer(Envir, HSA, Desc)) ,
	! .
store(consumer, Envir, HSA, Desc) :-
%	recorded(takInfo, consumer(Envir, HSA, StoredDesc)) ,
	l_write_trace(['*** Error, consumer with HSA= "', HSA, '" exist with different descriptions in ', Envir, nl,Desc,nl], 0).


% Store information about logical addresses
store(logical_address, Envir, LA, LADesc) :-
	recorded(takInfo, logical_address(Envir, LA, LADesc)) ,
	! .
store(logical_address, Envir, LA, LADesc) :-
	\+ recorded(takInfo, logical_address(Envir, LA, _LADesc)) ,
	recordz(takInfo, logical_address(Envir, LA, LADesc)) ,
	! .
store(logical_address, Envir, LA, _Desc) :-
%	recorded(takInfo, consumer(Envir, HSA, storedDesc)) ,
	l_write_trace(['*** Error, logical_address with HSA= "', LA, '" exist with different descriptions in ', Envir, nl], 0).


% Store producer information
store(producer, Envir, ProducerHSA, Desc, Hostname) :-
	% Remove extra characters from HSA-id
	clean_producer_hsa(ProducerHSA, InHSA),
	l_strip_blanks(InHSA, HSA) ,
	store_producer( Envir, HSA, Desc, Hostname).

% Routing records
store(routing, Envir, Sc_Id, Logical_address, ProducerHSA) :-
	recorded(takInfo, routing(Envir, Sc_Id, Logical_address, ProducerHSA)) ,
	! .
store(routing, Envir, Sc_Id, Logical_address, ProducerHSA) :-
	recordz(takInfo, routing(Envir, Sc_Id, Logical_address, ProducerHSA)) .

% Authorization records
store(authorization, Envir, Sc_Id, Logical_address, ConsumerHSA) :-
	recorded(takInfo, authorization(Envir, Sc_Id, Logical_address, ConsumerHSA)) ,
	! .
store(authorization, Envir, Sc_Id, Logical_address, ConsumerHSA) :-
	recordz(takInfo, authorization(Envir, Sc_Id, Logical_address, ConsumerHSA)) .


% Service contract
store(service_contract, Envir, Interaction, Domain, IVersion, RivVersion, Sc_Id) :-
	recorded(takInfo, service_contract(Envir, Sc_Id, Interaction, Domain, IVersion, RivVersion)) ,
	! .
store(service_contract, Envir, Interaction, Domain, IVersion, RivVersion, Sc_Id) :-
	% Get unique id
	l_counter_inc(service_contract_id, Sc_Id),
	recordz(takInfo, service_contract(Envir, Sc_Id, Interaction, Domain, IVersion, RivVersion)) .

% Help predicate to store(producer clause
store_producer(Envir, HSA, Desc, Hostname) :-
	\+ recorded(takInfo, producer(Envir, HSA, _DescStored, _HostnameStored)) ,
	atom_length(Desc, Len),
	Len > 1 ,
	! ,
	recordz(takInfo, producer(Envir, HSA, Desc, Hostname)) .

store_producer(Envir, HSA, Desc, Hostname) :-
	recorded(takInfo, producer(Envir, HSA, DescStored, HostnameStored)) ,
	% Remove different postfixes of all the descriptions
	l_common_prefix([Desc, DescStored], NewDesc) ,
	l_strip_blanks(NewDesc, NewDesc2),
	l_strip_trailing_chars(NewDesc2, '-', NewDesc3),
	l_strip_blanks(NewDesc3, NewDesc4),
	atom_length(NewDesc4, Len),
	Len > 1 ,
	l_erase_all(takInfo, producer(Envir, HSA, DescStored, HostnameStored)) ,
	! ,
	recordz(takInfo, producer(Envir, HSA, NewDesc4, Hostname)) .


store_producer(Envir, HSA, Desc, _Hostname) :-
	l_write_trace(['*** Error, producer with HSA= "', HSA, '" exist with description problem ("', Desc, '") in ', Envir, nl], 0).

% Remove all characters after " - " sequence
clean_producer_hsa(InHsa, OutHsa) :-
	sub_atom(InHsa, FoundAt, _Len, _After, ' - '),
	sub_atom(InHsa, 0, FoundAt, _After2, OutHsa).

% Mangage error in Security services domains
patchBifDomain([ehr,patientrelationship, _FakeLvl | Rest] ,[ehr,patientrelationship | Rest]) :- ! .
patchBifDomain([ehr,patientconsent, _FakeLvl | Rest] ,[ehr,patientconsent | Rest]) :- ! .
patchBifDomain(OkDomain, OkDomain).








