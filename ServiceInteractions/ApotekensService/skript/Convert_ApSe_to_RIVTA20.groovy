import groovy.xml.StreamingMarkupBuilder
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Script to update Apotekens Service RIVTA-based WSDL-files to full RIVTA (they lack logiocal address header)
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

import groovy.xml.XmlUtil
import javax.xml.parsers.SAXParserFactory

// Process all wsdl files recursively found bellow current directory
new File('.').eachFileRecurse {
	file -> 
		if (file.path.endsWith('rivtabp20.wsdl')) {
			processFile file
		}
}

def processFile(File file) {
	
	def definitions = new XmlSlurper().parse(file).declareNamespace(xs: "http://www.w3.org/2001/XMLSchema", wsdl: "http://schemas.xmlsoap.org/wsdl/", soap: "http://schemas.xmlsoap.org/wsdl/soap/")

	// Derive service schema namespace from target namespace attribute. Namespace declarations are lost when re-writing the 
	// processed WSDL, so we need to derive the namespace from the targetNamespace attribute (which is not lost)
	
	def serviceSchemaNS = "${definitions.'@targetNamespace'}" - ':1:rivtabp20' + 'Responder:1'
	
	// Change WSDL target namespace, since we produce a different wsdl than the original one	
	definitions.'@targetNamespace' = "urn:riv:inera" + ("${definitions.'@targetNamespace'}" - "urn:riv")
	
	// Add import for logical address header element
	definitions.'wsdl:types'.'xs:schema'.appendNode {
		'xs:import' (schemaLocation : "../../rivta20/ws-addressing-1.0.xsd", namespace : "http://www.w3.org/2005/08/addressing")
	}

	// Add LogicalAddress part to request messages
	definitions.'wsdl:message'.findAll {it.@name.toString().endsWith("Request")}.each {
		it.appendNode {
			'wsdl:part'(name : 'LogicalAddress', element : "wsa:To") {
				'xs:annotation' {
					'xs:documentation' ("Orgnr of Apotekens Service AB")
				}
			}
		}
	}
	
	// Add soap header binding to all input operation bindings
	definitions.'wsdl:binding'.'wsdl:operation'.each {
		def messageName = "" + it.'@name' + "Request"
		it.'wsdl:input'.appendNode {
			'soap:header'(use: 'literal', message: messageName, part: 'LogicalAddress' )
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
						'': serviceSchemaNS
					)
	                mkp.yield definitions
	              }
	
	String newWsdl = XmlUtil.serialize(smb)
	
	new File(file.path).write(newWsdl, "UTF-8");
}
	