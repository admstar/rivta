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
package se.skl.rivta.rivtatools_web.generator

import se.skl.rivta.rivtatools_web.model.MEPEnum;
import se.skl.rivta.rivtatools_web.model.ServiceInteraction;
import se.skl.rivta.rivtatools_web.model.RivtaProfileEnum;
import se.skl.rivta.rivtatools_web.model.Operation;

class WSDLGenerator {
	public String generateWSDL(ServiceInteraction si) {
		use (ServiceInteractionWsdlDSL, OperationWsdlDSL) {

			StringWriter wsdl = new StringWriter()
			def xmlBuilder = new groovy.xml.MarkupBuilder(wsdl)

			// Create map of namespace alias declarations
			def rootElementAttributes = [
						name: "${si.name}Interaction",
						'xmlns:wsdl': 'http://schemas.xmlsoap.org/wsdl/',
						'xmlns:soap': 'http://schemas.xmlsoap.org/wsdl/soap/',
						'xmlns:xs': 'http://www.w3.org/2001/XMLSchema',
						'xmlns:tjsr': si.responderServiceSchemaTargetNamespace,
						'xmlns:tjsi': si.initiatorServiceSchemaTargetNamespace,
						'xmlns:tns': si.wsdlTargetNamespace,
						targetNamespace: si.wsdlTargetNamespace]

			if (si.profile == RivtaProfileEnum.RIVTABP20) {
				rootElementAttributes += ['xmlns:wsa' : 'http://www.w3.org/2005/08/addressing']
			}
			if (si.profile == RivtaProfileEnum.RIVTABP21) {
				rootElementAttributes += ['xmlns:itr' : "urn:riv:itintegration:registry:1"]
			}

			xmlBuilder.'wsdl:definitions'(rootElementAttributes) {

				'xs:annotation' { 'xs:documentation' ( """
			 Tjänsteinteraktionens namn: ${si.name}Interaction
			 Beskrivning: 
			   ${si.description}
			 Revisioner: 
			 Tjänstedomän: ${si.serviceDomain}
			 Tjänsteinteraktionstyp: ${si.interactionTypeDescription}
			 WS-profil: ${si.profile}
			 Förvaltas av: ${si.iprHolder}
			 """) }

				'wsdl:types' {
					'xs:schema'(targetNamespace : si.wsdlTargetNamespace) {
						// Import the responder service schema
						'xs:import' (schemaLocation : "${si.responderSchemaName}", namespace : si.responderServiceSchemaTargetNamespace)
						if (si.isBiDir) {
							// Import the initiator service schema
							'xs:import' (schemaLocation : "${si.initiatorSchemaName}", namespace : si.initiatorServiceSchemaTargetNamespace)
						}
						if (si.profile == RivtaProfileEnum.RIVTABP20) {
							// Import the LogicalAdress header schema
							'xs:import' (schemaLocation : "../../core_components/ws-addressing-1.0.xsd", namespace : "http://www.w3.org/2005/08/addressing")
						}
						if (si.profile == RivtaProfileEnum.RIVTABP21) {
							// Import the LogicalAdress header schema
							'xs:import' (schemaLocation : "../../core_components/itintegration_registry_1.0.xsd", namespace : "urn:riv:itintegration:registry:1")
						}
					}

				}

				// Produce Message elements for responder request + ev. response and ev. the same for initiator
				si.operations.each {Operation operation ->
					'wsdl:message'(name : operation.inputMessageName) {
						if (si.profile == RivtaProfileEnum.RIVTABP20) {
							'wsdl:part'(name : 'LogicalAddress', element : "wsa:To") {
								'xs:annotation' {
									'xs:documentation' (operation.serviceInteraction.logicalAddressDescription)
								}
							}
						}
						if (si.profile == RivtaProfileEnum.RIVTABP21) {
							'wsdl:part'(name : 'LogicalAddress', element : "itr:LogicalAddress") {
								'wsdl:documentation' (operation.serviceInteraction.logicalAddressDescription)
							}
						}
						'wsdl:part'(name : 'parameters', element : "${operation.inputElementName}")
					}

					'wsdl:message'(name : operation.outputMessageName) {
						'wsdl:part'(name : 'parameters', element : "${operation.outputElementName}")
					}

				}

				si.operations.each {Operation operation ->
					'wsdl:portType'(name : operation.portType) {
						'wsdl:operation'(name : operation.name) {
							'wsdl:input'(message : "tns:${operation.inputMessageName}")
							'wsdl:output'(message : "tns:${operation.outputMessageName}")
						}
					}
				}

				// The concrete binding section for responder and eventually initiator service ports

				si.operations.each {Operation operation ->
					'wsdl:binding'(name : operation.bindingName, type : "tns:${operation.portType}") {
						'soap:binding'(style : "document", transport : "http://schemas.xmlsoap.org/soap/http")
						'wsdl:operation'(name : "${operation.name}") {
							'soap:operation'(soapAction : operation.soapAction, style : "document")
							'wsdl:input' {
								if (si.profile != RivtaProfileEnum.WSIBP11) {
									'soap:header'(use : "literal", message : "tns:${operation.inputMessageName}", part : "LogicalAddress")
								}
								'soap:body'(use : "literal", parts : "parameters")
							}
							'wsdl:output' { 'soap:body'(use : "literal") }
						}
					}
				}
				// The concrete service port section. All services are published by the port of the
				// service gateway for the specified deployment context
				si.operations.each {Operation operation ->
					'wsdl:service'(name : operation.serviceName) {
						'wsdl:port'(name : operation.portName,
								binding : "tns:${operation.bindingName}") { 'soap:address'(location : "http://tempuri.org") }
					}
				}
			}

			return "${GeneratorUtil.xmlPrologue}${GeneratorUtil.getLicenseDeclaration(si.iprHolder)}${wsdl}"
		}
	}
}
