/**
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements. See the NOTICE file
distributed with this work for additional information
regarding copyright ownership. Sveriges Kommuner och Landsting licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied. See the License for the
specific language governing permissions and limitations
under the License.
*/
package se.skl.rivta.rivtatools_web.model

import java.util.List;

class Repository {
	List<ServiceDomain> serviceDomains
	static Repository singleton = new Repository()
	
	Repository() {
		serviceDomains = new ArrayList<ServiceDomain>()
	}
	
	public void addServiceInteraction(String iprHolder, String architectureDomain,String domain, String subDomain, int majorVersion, int minorVersion, String name, String description, String logicalAddressDescription, boolean isBiDir, MEPEnum mep, boolean isReadOnly, RivtaProfileEnum profile) {
		ServiceInteraction si = new ServiceInteraction(iprHolder: iprHolder, name: name, description: description, logicalAddressDescription: logicalAddressDescription, profile: profile)
		si.version = new Version(majorVersion: majorVersion, minorVersion: minorVersion)
		si.responder = new Operation(mep: mep, isReadOnly: isReadOnly)
		if (isBiDir) si.initiator = new Operation(mep: mep, isReadOnly: false)
			
		// Assure ServiceDomain
		ServiceDomain existingDomain = getDomain(architectureDomain, domain, subDomain)
		if (existingDomain == null) {
			existingDomain = new ServiceDomain(root: architectureDomain, domain: domain, subDomain: subDomain)
			serviceDomains.add(existingDomain)
		}
		
		// Add interaction if not already present, else through exception
		if (!getServiceInteraction(name, architectureDomain, domain, subDomain, majorVersion, minorVersion)) {
			existingDomain.serviceInteractions.add(si)
			si.serviceDomain = existingDomain
		}
		else
			throw new RuntimeException("Duplicate service interaction. Name: ${name}, domain: ${domain}, subDomain: ${subDomain}, majorVersion: ${majorVersion}, minorVersion: ${minorVersion}")
	}
	
	ServiceDomain getDomain(String architectureDomain, String domain, String subDomain) {
		serviceDomains.find {ServiceDomain currDomain -> 
			currDomain.root.equals(architectureDomain) && currDomain.domain.equals(domain) && currDomain.subDomain.equals(subDomain)}
	}
	
	public ServiceInteraction getServiceInteraction(String name, String architectureDomain, String domain, String subDomain, int majorVersion, int minorVersion) {
		serviceDomains.find {
			ServiceDomain sd ->
				sd.root.equals(architectureDomain) && sd.domain.equals(domain) && sd.subDomain.equals(subDomain)}?.serviceInteractions.find {
					ServiceInteraction si -> si.name.equals(name) && si.version.majorVersion == majorVersion && si.version.minorVersion == minorVersion}
	}
	
	
}
