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
import se.skl.rivta.rivtatools_web.model.Operation;

class SchemaGenerator {
	public String generateResponderSchema(ServiceInteraction si) {
		use (ServiceInteractionWsdlDSL, OperationWsdlDSL) {
			
			StringWriter xsd = new StringWriter()
			def xmlBuilder = new groovy.xml.MarkupBuilder(xsd)
			
			// Create map of namespace alias declarations
			def rootElementAttributes = [
					'xmlns:xs': 'http://www.w3.org/2001/XMLSchema',
					'xmlns:tns': si.responderServiceSchemaTargetNamespace,
					'xmlns:core': si.coreSchemaNamespace,
					targetNamespace: si.responderServiceSchemaTargetNamespace,
					elementFormDefault: 'qualified',
					attributeFormDefault: 'unqualified',
					version: si.version]
			
			
			xmlBuilder.'xs:schema'(rootElementAttributes) {			
				// Import core schema			
				'xs:import' (schemaLocation : "../../core_components/${si.coreSchemaName}", namespace : si.coreSchemaNamespace)
				
				'xs:element' (name: si.name, type: "tns:${si.name}Type")
				'xs:element' (name: "${si.name}Response", type: "tns:${si.name}ResponseType")
				
				'xs:complexType'(name: "${si.name}Type") {
					'xs:sequence' {
						'xs:element' (name: 'SomeElement', type: 'core:SomeElementType')
						'xs:any' (namespace: '##other', processContents: 'lax', minOccurs: '0', maxOccurs: 'unbounded')
					}
				}
				
				if (!si.responder.isReadOnly) {
					'xs:complexType'(name: "${si.name}ResponseType") {
						'xs:sequence' {
							'xs:element' (name: 'ResultCode', type: 'tns:ResultCodeEnum')
							'xs:element' (name: 'comment', type: 'xs:string', minOccurs: '0')
							'xs:any' (namespace: '##other', processContents: 'lax', minOccurs: '0', maxOccurs: 'unbounded')
						}
					}
					
					'xs:simpleType'(name: "ResultCodeEnum") {
						'xs:restriction' (base: 'xs:string') {
							'xs:enumeration' (value: 'OK')
							'xs:enumeration' (value: 'ERROR')
							'xs:enumeration' (value: 'INFO')
						}
					}
				} else {
					'xs:complexType'(name: "${si.name}ResponseType") {
						'xs:sequence' {
							'xs:element' (name: 'SomeElement', type: 'core:SomeElementType')
							'xs:any' (namespace: '##other', processContents: 'lax', minOccurs: '0', maxOccurs: 'unbounded')
						}
					}
				}
			}
			"${GeneratorUtil.xmlPrologue}${GeneratorUtil.getLicenseDeclaration(si.iprHolder)}${xsd}"
		}
	}
	
	public String generateInitiatorSchema(ServiceInteraction si) {
		use (ServiceInteractionWsdlDSL, OperationWsdlDSL) {
			
			StringWriter xsd = new StringWriter()
			def xmlBuilder = new groovy.xml.MarkupBuilder(xsd)
			
			// Create map of namespace alias declarations
			def rootElementAttributes = [
					'xmlns:xs': 'http://www.w3.org/2001/XMLSchema',
					'xmlns:tns': si.initiatorServiceSchemaTargetNamespace,
					'xmlns:core': si.coreSchemaNamespace,
					targetNamespace: si.initiatorServiceSchemaTargetNamespace,
					elementFormDefault: 'qualified',
					attributeFormDefault: 'unqualified',
					version: si.version]
			
			
			xmlBuilder.'xs:schema'(rootElementAttributes) {			
				// Import core schema			
				'xs:import' (schemaLocation : "../../core_components/${si.coreSchemaName}", namespace : si.coreSchemaNamespace)
				
				'xs:element' (name: "Process${si.name}Response", type: "tns:Process${si.name}ResponseType")
				'xs:element' (name: "Process${si.name}ResponseResponse", type: "tns:Process${si.name}ResponseResponseType")
				
				'xs:complexType'(name: "Process${si.name}ResponseType") {
					'xs:sequence' {
						'xs:element' (name: 'SomeElement', type: 'core:SomeElementType')
						'xs:any' (namespace: '##other', processContents: 'lax', minOccurs: '0', maxOccurs: 'unbounded')
					}
				}
				'xs:complexType'(name: "Process${si.name}ResponseResponseType") {
					'xs:sequence' {
						'xs:element' (name: 'ResultCode', type: 'tns:ResultCodeEnum')
						'xs:element' (name: 'comment', type: 'xs:string', minOccurs: '0')
						'xs:any' (namespace: '##other', processContents: 'lax', minOccurs: '0', maxOccurs: 'unbounded')
					}
				}
				
				'xs:simpleType'(name: "ResultCodeEnum") {
					'xs:restriction' (base: 'xs:string') {
						'xs:enumeration' (value: 'OK')
						'xs:enumeration' (value: 'ERROR')
						'xs:enumeration' (value: 'INFO')
					}
				}
			}
			"${GeneratorUtil.xmlPrologue}${GeneratorUtil.getLicenseDeclaration(si.iprHolder)}${xsd}"
		}
	}	
	
	public String generateCoreSchema(ServiceInteraction si) {
use (ServiceInteractionWsdlDSL, OperationWsdlDSL) {
			
			StringWriter xsd = new StringWriter()
			def xmlBuilder = new groovy.xml.MarkupBuilder(xsd)
			
			// Create map of namespace alias declarations
			def rootElementAttributes = [
					'xmlns:xs': 'http://www.w3.org/2001/XMLSchema',
					'xmlns:tns': si.coreSchemaNamespace,
					targetNamespace: si.coreSchemaNamespace,
					elementFormDefault: 'qualified',
					attributeFormDefault: 'unqualified',
					version: si.version]
			
			
			xmlBuilder.'xs:schema'(rootElementAttributes) {			
				
				'xs:complexType'(name: "SomeElementType") {
					'xs:sequence' {
						'xs:element' (name: 'SomeElement', type: 'xs:string')
						'xs:any' (namespace: '##other', processContents: 'lax', minOccurs: '0', maxOccurs: 'unbounded')
					}
				}
			}
			"${GeneratorUtil.xmlPrologue}${GeneratorUtil.getLicenseDeclaration(si.iprHolder)}${xsd}"
		}		
	}
	
	public String getWsaSchema() {
		getClass().getResourceAsStream("/se/skl/rivta/rivtatools_web/generator/ws-addressing-1.0.xsd").getText()
	}
	
	public String getRegistrySchema() {
		getClass().getResourceAsStream("/se/skl/rivta/rivtatools_web/generator/itintegration_registry_1.0.xsd").getText()
	}	
}
