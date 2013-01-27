package se.skl.rivta.rivtatools_web.generator

import se.skl.rivta.rivtatools_web.model.MEPEnum;
import se.skl.rivta.rivtatools_web.model.ServiceInteraction;

/**
 * Category to enrich the domain model with behaviour required by the generators
 * @author johan
 *
 */
class ServiceInteractionWsdlDSL {
	
	/**
	 * urn:riv:${tjänsteDomän}:${tjänsteInteraktion}:m:${profilKortnamn}
	 * 
	 * @author johan
	 *
	 */	
	static String getWsdlTargetNamespace(ServiceInteraction that) {
		"urn:${that.serviceDomain.root}:${that.serviceDomain.domain}:${that.serviceDomain.subDomain}:${that.name}:${that.version.majorVersion}:${that.profile.toString().toLowerCase()}"
	}
	
	/**
	 * urn:riv:${tjänsteDomän}:${tjänsteInteraktion}Responder:${m}
	 * 
	 * @author johan
	 *
	 */	
	static String getResponderServiceSchemaTargetNamespace(ServiceInteraction that) {
		"urn:${that.serviceDomain.root}:${that.serviceDomain.domain}:${that.serviceDomain.subDomain}:${that.name}Responder:${that.version.majorVersion}"
	}	
	
	/**
	 * urn:riv:${tjänsteDomän}:${tjänsteInteraktion}Responder:${m}
	 * 
	 * @author johan
	 *
	 */	
	static String getInitiatorServiceSchemaTargetNamespace(ServiceInteraction that) {
		"urn:${that.serviceDomain.root}:${that.serviceDomain.domain}:${that.serviceDomain.subDomain}:${that.name}Initiator:${that.version.majorVersion}"
	}
	
	/**
	 * urn:riv:${tjänsteDomän}:${m}
	 * 
	 * @author johan
	 *
	 */	
	static String getCoreSchemaNamespace(ServiceInteraction that) {
		"urn:${that.serviceDomain.root}:${that.serviceDomain.domain}:${that.serviceDomain.subDomain}:${that.version.majorVersion}"
	}	
	
	/**
	 * ${huvuddomän}_${underdomän}_${m.n}.xsd
	 * @param that
	 * @return
	 */
	static String getCoreSchemaName(ServiceInteraction that) {
		"${that.serviceDomain.domain}_${that.serviceDomain.subDomain}_${that.version}.xsd"
	}	
	
	/**
	 * ${tjänsteInteraktion}Initiator_${m.n}.xsd
	 * @param that
	 * @return
	 */
	static String getInitiatorSchemaName(ServiceInteraction that) {
		"${that.name}Initiator_${that.version.majorVersion}.${that.version.minorVersion}.xsd"
	}
	
	/**
	 * ${tjänsteInteraktion}Responder_${m.n}.xsd
	 * @param that
	 * @return
	 */	
	static String getResponderSchemaName(ServiceInteraction that) {
		"${that.name}Responder_${that.version}.xsd"
	}
	
	/**
	 * ${tjänsteInteraktion}Interaction_${m.n}_${profilKortnamn}.wsdl
	 * @param that
	 * @return
	 */	
	static String getWsdlFileName(ServiceInteraction that) {
		"${that.name}Interaction_${that.version.majorVersion}.${that.version.minorVersion}_${that.profile}.wsdl"
	}

}
