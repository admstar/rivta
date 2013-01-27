package se.skl.rivta.rivtatools_web.model

import static ServiceInteractionTypeEnum.*

class ServiceInteraction {
	String iprHolder
	String name
	String description
	String logicalAddressDescription
	Version version
	Operation responder
	Operation initiator
	RivtaProfileEnum profile
	ServiceDomain serviceDomain
	
	public boolean getIsBiDir() {
		initiator != null
	}
	
	String getInteractionTypeDescription() {
		if (getIsBiDir()) return "Uppdrag-Resultat"
		if (responder.mep == MEPEnum.InOut) return "Fråga-Svar"
		return "Informationsspridning"
	}
	
	ServiceInteractionTypeEnum getType() {
		this.isBiDir ? BiDir : (responder.mep == MEPEnum.InOut ? RequestResponse : OneWay)
	}
	
	List getOperations() {
		if (isBiDir) [responder, initiator]
		else [responder]
	}
	
	void setResponder(Operation responder) {
		this.responder = responder
		responder.serviceInteraction = this
	}
	
	void setInitiator(Operation initiator) {
		this.initiator = initiator
		initiator.serviceInteraction = this
	}	
}
