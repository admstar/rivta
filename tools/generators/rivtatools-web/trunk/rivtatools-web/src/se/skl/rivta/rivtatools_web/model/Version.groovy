package se.skl.rivta.rivtatools_web.model

class Version {
	int majorVersion
	int minorVersion
	
	public String toString() {
		"${majorVersion}.${minorVersion}"
	}
}
