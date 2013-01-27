package se.skl.rivta.rivtatools_web.model

class ServiceDomain {
	String root
	String domain
	String subDomain
	List<ServiceInteraction> serviceInteractions = new ArrayList<ServiceInteraction>()
	
	String toString() {
		"${domain}:${subDomain}"
	}
}
