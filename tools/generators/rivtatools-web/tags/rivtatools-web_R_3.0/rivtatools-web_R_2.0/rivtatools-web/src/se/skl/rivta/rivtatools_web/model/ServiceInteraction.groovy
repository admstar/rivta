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
