package se.skl.rivta.rivtatools_web.model

class Operation {
	MEPEnum mep
	boolean isReadOnly
	ServiceInteraction serviceInteraction
	
	boolean getIsResponderOperation() {
		serviceInteraction.responder == this
	}
	
	String getRoleName() {
		isResponderOperation ? "Responder" : "Initiator"
	}
	
	String getName() {
		isResponderOperation ? serviceInteraction.name : "Process${serviceInteraction.name}Response"
	}
}