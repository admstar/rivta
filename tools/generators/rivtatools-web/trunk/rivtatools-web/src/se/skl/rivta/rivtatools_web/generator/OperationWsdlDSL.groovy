package se.skl.rivta.rivtatools_web.generator

import se.skl.rivta.rivtatools_web.model.Operation;
import se.skl.rivta.rivtatools_web.model.ServiceInteraction;

class OperationWsdlDSL {
	
	static String nameSpaceAlias(Operation that) {
		that.serviceInteraction.responder == that ? "tjsr" : "tjsi"
	}
	
/**
 * ${name}Request
 * @param that
 * @return
 */
	static String getInputMessageName(Operation that) {
		"${that.name}Request"
	}
	
	/**
	 * ${name}Response
	 * @param that
	 * @return
	 */	
	static String getOutputMessageName(Operation that) {
		"${that.name}Response"
	}	

	/**
	 * ${name}
	 * @param that
	 * @return
	 */		
	static String getInputElementName(Operation that) {
		"${nameSpaceAlias(that)}:${that.name}"
	}

	/**
	 * ${name}Response
	 * @param that
	 * @return
	 */		
	static String getOutputElementName(Operation that) {
		"${nameSpaceAlias(that)}:${that.name}Response"
	}
	
	static String getPortType(Operation that) {
		String siName = that.getServiceInteraction().name
		"${siName}${that.roleName}Interface"
	}
	
	/**
	 * ${tjänsteInteraktion}${roll}Binding
	 * @param that
	 * @return
	 */
	static String getBindingName(Operation that) {
		String siName = that.getServiceInteraction().name
		"${siName}${that.roleName}Binding"
	}
	
	/**
	 * urn:riv:${tjänsteDomän}:${tjänsteInteraktion}${roll}:m:${operation}
	 * @param that
	 * @return
	 */
	static String getSoapAction(Operation that) {
		"urn:${that.serviceInteraction.getServiceDomain().root}:${that.serviceInteraction.getServiceDomain().domain}:${that.serviceInteraction.getServiceDomain().subDomain}:${that.name}${that.roleName}:${that.serviceInteraction.version.majorVersion}:${that.name}"
	}
	
	/**
	 * ${tjänsteInteraktion}${roll}Service
	 * @param that
	 * @return
	 */	
	static String getServiceName(Operation that) {
		String siName = that.getServiceInteraction().name
		"${siName}${that.roleName}Service"
	}		
	
	/**
	 * ${tjänsteInteraktion}${roll}Port
	 * @param that
	 * @return
	 */	
	static String getPortName(Operation that) {
		String siName = that.getServiceInteraction().name
		"${siName}${that.roleName}Port"
	}		
}
