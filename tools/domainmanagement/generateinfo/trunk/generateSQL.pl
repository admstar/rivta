:- module(generateSQL, [
	      sql_generate/0
	  ]) .

:- use_module(leolib).
:- use_module(loaddomaintable).
:- use_module(loadsvninfo).
:- use_module(loadtak).
:- use_module(library(http/html_write)).



/* ===================================================================
Create HTML output
====================================================================== */

sql_generate :-
	l_counter_set(db_id, 0),
	sql_generate_domains .



/* ===================================================================
Generate the domains table
- description
- id
- lvl1
- lvl2
- lvl3
- swedish_long
- swedish_short

recordz(domains_tbl, domains_tbl(id, description, lvl1, lvl2, lvl3,
swedish_short, swedish_long))

recordz(domain_releases_tbl, domain_releases_tbl(id, s_accept , i_accept
, t_accept, tag, domain_id))

recordz(interactions_tbl, interactions_tbl(id, last_changed_date,
description, minor, major, name, rivta_version))

recordz(domain_releases_interactions_tbl,
domain_releases_interactions_tbl(id, domain_release_id, interaction_id))

recordz(tak_tbl, tak_tbl(id, name))

recordz(service_components_tbl, service_components_tbl(id, tak_id,
hsaid, description, hostname ))

recordz(logical_addresses_tbl,
	logical_addresses_tbl(id,
			      logical_address,
			      description,
			      tak_id))

recordz(routing_tbl, routing_tbl(id, tak_id,
service_component_id, interaction_id, logical_address_id))

recordz(call_authorization_tbl, call_authorization_tbl(id,
interaction_id, logical_address_id, service_component_id, tak_id))

====================================================================== */
sql_generate_domains :-
	sv_get_domain(Domain) .

