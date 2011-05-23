import groovy.xml.StreamingMarkupBuilder
import groovy.io.FileType

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Script to update Apotekens Service RIVTA-based WSDL-files to full RIVTA (they lack logiocal address header) plus Argos Header
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import groovy.xml.XmlUtil
import javax.xml.parsers.SAXParserFactory

// Process all wsdl files recursively found bellow current directory
new File('.').eachFileRecurse(FileType.FILES) {
	file -> 
		if (file.path.contains('rivtabp') && !file.path.contains('.svn')) {
			processWsdlFile file
		} else if (file.path.contains('Responder') && !file.path.contains('.svn')) {
			processServiceSchemaFile file
		}
}

def processWsdlFile(File file) {
	
	println "Processing " + file.path
	def argosNS = "urn:riv:inera.se.apotekensservice:argos:1"
	
	def definitions = new XmlSlurper().parse(file).declareNamespace(xs: "http://www.w3.org/2001/XMLSchema", wsdl: "http://schemas.xmlsoap.org/wsdl/", soap: "http://schemas.xmlsoap.org/wsdl/soap/")

	// Derive service schema namespace from target namespace attribute. Namespace declarations are lost when re-writing the 
	// processed WSDL, so we need to derive the namespace from the targetNamespace attribute (which is not lost)
	
	def serviceSchemaNS = "${definitions.'@targetNamespace'}" - ':1:rivtabp20' + 'Responder:1'
	
	// Change WSDL target namespace, since we produce a different wsdl than the original one	
	definitions.'@targetNamespace' = "urn:riv:inera" + ("${definitions.'@targetNamespace'}" - "urn:riv")
	
	// Add target  namespace to schema tag (required by BizTalk)
	definitions.'wsdl:types'.'xs:schema'.'@targetNamespace' = "${definitions.'@targetNamespace'}"
	
	// Add import for logical address header and argos header elements
	definitions.'wsdl:types'.'xs:schema'.appendNode {
		'xs:import' (schemaLocation : "../../core_components/ws-addressing-1.0.xsd", namespace : "http://www.w3.org/2005/08/addressing")
		'xs:import' (schemaLocation : "../../core_components/ArgosHeader_1.0.xsd", namespace : argosNS)
	}

	// Add LogicalAddress and argos header parts to request messages
	definitions.'wsdl:message'.findAll {it.@name.toString().endsWith("Request")}.each {
		it.appendNode {
			'wsdl:part'(name : 'LogicalAddress', element : "wsa:To") {
				'wsdl:documentation' ("Orgnr of Apotekens Service AB")
			}
		}
		it.appendNode {
			'wsdl:part'(name : 'ArgosHeader', element : "argos:ArgosHeader") {
					'wsdl:documentation' ("Argos header of Apotekens Service AB. Check documentation regarding mandatory for this specific service interaction")
			}
		}		
	}
	
	// Add soap header bindings to all input operation bindings
	definitions.'wsdl:binding'.'wsdl:operation'.each {
		def messageName = "" + it.'@name' + "Request"
		it.'wsdl:input'.appendNode {
			'soap:header'(use: 'literal', message: messageName, part: 'LogicalAddress' )
		}
		it.'wsdl:input'.appendNode {
			'soap:header'(use: 'literal', message: messageName, part: 'ArgosHeader' )
		}		
	}	
	
	// Re-define namespace declarations and over-write the wsdl file with the modifed content
	def smb = new StreamingMarkupBuilder().bind {
					mkp.declareNamespace(
						xs: "http://www.w3.org/2001/XMLSchema", 
						wsdl: "http://schemas.xmlsoap.org/wsdl/", 
						soap: "http://schemas.xmlsoap.org/wsdl/soap/",
	                  	http: "http://schemas.xmlsoap.org/wsdl/http/",
						spdr: serviceSchemaNS,
						wsa: "http://www.w3.org/2005/08/addressing",
						argos: argosNS,
						'': "${definitions.'@targetNamespace'}"
					)
	                mkp.yield definitions
	              }
	
	String newWsdl = XmlUtil.serialize(smb)
	
	new File(file.path).write(newWsdl, "UTF-8");
}

def processServiceSchemaFile(File file) {
	println "Processing " + file.path
	
	def parentFile = file
	5.times {
		parentFile = new File(parentFile.getParent())
	}
	def subDomainName = parentFile.name
	def domainName =  "se.apotekensservice"
	def versionStringEndIdex = file.name.indexOf(".xsd")
	def versionStringBeginIdex = file.name.indexOf("_") + 1
	def versionString = file.name.substring(versionStringBeginIdex,versionStringEndIdex)
	def majorVersion = versionString.substring(0, versionString.indexOf("."))
	
	def domainSchemaFileName = "${domainName}_${subDomainName}_${versionString}.xsd"
	
	def schema = new XmlSlurper().parse(file).declareNamespace(xs: "http://www.w3.org/2001/XMLSchema")

	def serviceSchemaNS = schema.'@targetNamespace'
	
	// Update schemaLocation for existing import of the domain schema, since domain schema location has changed
	def domainSchemaOldLocation = schema.'xs:import'.'@schemaLocation'
	def domainSchemaNewLocation = "../../core_components/" + domainSchemaOldLocation.toString()
	schema.'xs:import'.'@schemaLocation' = domainSchemaNewLocation
	
	// Re-define namespace declarations and over-write the wsdl file with the modifed content
	def smb = new StreamingMarkupBuilder().bind {
					mkp.declareNamespace(
						xs: "http://www.w3.org/2001/XMLSchema", 
						(subDomainName) : "urn:riv:${domainName}:${subDomainName}:${majorVersion}",
						'': serviceSchemaNS
					)
	                mkp.yield schema
	              }
	
	String newServiceSchema = XmlUtil.serialize(smb)
	
	new File(file.path).write(newServiceSchema, "UTF-8");
}
	