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
package se.skl.rivta.rivtatools_web.generator;

import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.w3c.dom.Document;

import se.skl.rivta.rivtatools_web.model.MEPEnum;
import se.skl.rivta.rivtatools_web.model.Repository;
import se.skl.rivta.rivtatools_web.model.RivtaProfileEnum;
import se.skl.rivta.rivtatools_web.model.ServiceInteraction;
import se.skl.rivta.rivtatools_web.generator.SchemaGenerator;

import groovy.util.GroovyTestCase;

class SchemaGeneratorTest extends GroovyTestCase {
	void testGenerateResponderSchema() {
		XMLUnit.setIgnoreWhitespace(true)
		String xsdPath = "/se/skl/rivta/rivtatools_web/generator/RegisterMedicalCertificateResponder_1.0.xsd"
		InputStream responderSchema = getClass().getResourceAsStream(xsdPath)
        Document myExpectedSchema = XMLUnit.buildDocument(XMLUnit.getControlParser(), new InputStreamReader(responderSchema, Charset.forName("UTF-8")))
		
		Repository rep = new Repository();
		rep.addServiceInteraction("Fake Org", "riv", "insuranceprocess", "healthreporting", 1, 0, "RegisterMedicalCertificate", "Interaction to transfer a new report for sickness","LogicalAddress is the organization id for F�rs�kringskassan.", false, MEPEnum.InOut, false, RivtaProfileEnum.RIVTABP21);
		ServiceInteraction si = rep.getServiceInteraction("RegisterMedicalCertificate","riv", "insuranceprocess", "healthreporting", 1, 0)
		
		String generatedXSDString = new SchemaGenerator().generateResponderSchema(si)
		Document generatedXSD = XMLUnit.buildDocument(XMLUnit.getControlParser(), new StringReader(generatedXSDString))
		
        Diff myDiff = new Diff(myExpectedSchema, generatedXSD);
        assertTrue("Responder Schema not generated as expected:" + myDiff, myDiff.similar());
	}
	
	void testGenerateReadOnlyResponderSchema() {
		XMLUnit.setIgnoreWhitespace(true)
		String xsdPath = "/se/skl/rivta/rivtatools_web/generator/GetMedicalCertificateResponder_1.0.xsd"
		InputStream responderSchema = getClass().getResourceAsStream(xsdPath)
		Document myExpectedSchema = XMLUnit.buildDocument(XMLUnit.getControlParser(), new InputStreamReader(responderSchema, Charset.forName("UTF-8")))
		
		Repository rep = new Repository();
		rep.addServiceInteraction("Fake Org", "riv", "insuranceprocess", "healthreporting", 1, 0, "GetMedicalCertificate", "Interaction to fetch a sickness report","LogicalAddress is the organization id for F�rs�kringskassan.", false, MEPEnum.InOut, true, RivtaProfileEnum.RIVTABP21);
		ServiceInteraction si = rep.getServiceInteraction("GetMedicalCertificate","riv", "insuranceprocess", "healthreporting", 1, 0)
		
		String generatedXSDString = new SchemaGenerator().generateResponderSchema(si)
		Document generatedXSD = XMLUnit.buildDocument(XMLUnit.getControlParser(), new StringReader(generatedXSDString))
		
		Diff myDiff = new Diff(myExpectedSchema, generatedXSD);
		assertTrue("Responder Schema not generated as expected:" + myDiff, myDiff.similar());
	}
	
	void testGenerateInitiatorSchema() {
		XMLUnit.setIgnoreWhitespace(true)
		String xsdPath = "/se/skl/rivta/rivtatools_web/generator/RegisterMedicalCertificateInitiator_1.0.xsd"
		InputStream initiatorSchema = getClass().getResourceAsStream(xsdPath)
        Document myExpectedSchema = XMLUnit.buildDocument(XMLUnit.getControlParser(), new InputStreamReader(initiatorSchema, Charset.forName("UTF-8")))
		
		Repository rep = new Repository();
		rep.addServiceInteraction("Fake Org","riv", "insuranceprocess", "healthreporting", 1, 0, "RegisterMedicalCertificate", "Interaction to transfer a new report for sickness","LogicalAddress is the organization id for F�rs�kringskassan.", false, MEPEnum.InOut, false, RivtaProfileEnum.RIVTABP20);
		ServiceInteraction si = rep.getServiceInteraction("RegisterMedicalCertificate","riv", "insuranceprocess", "healthreporting", 1, 0)
		
		String generatedXSDString = new SchemaGenerator().generateInitiatorSchema(si)
		Document generatedXSD = XMLUnit.buildDocument(XMLUnit.getControlParser(), new StringReader(generatedXSDString))
		
        Diff myDiff = new Diff(myExpectedSchema, generatedXSD);
        assertTrue("Initiator Schema not generated as expected:" + myDiff, myDiff.similar());
	}	
	
	void testGenerateCoreSchema() {
		XMLUnit.setIgnoreWhitespace(true)
		String xsdPath = "/se/skl/rivta/rivtatools_web/generator/insuranceprocess_healthreporting_1.0.xsd"
		InputStream coreSchema = getClass().getResourceAsStream(xsdPath)
        Document myExpectedSchema = XMLUnit.buildDocument(XMLUnit.getControlParser(), new InputStreamReader(coreSchema, Charset.forName("UTF-8")))
		
		Repository rep = new Repository();
		rep.addServiceInteraction("Fake Org","riv", "insuranceprocess", "healthreporting", 1, 0, "RegisterMedicalCertificate", "Interaction to transfer a new report for sickness","LogicalAddress is the organization id for F�rs�kringskassan.", false, MEPEnum.InOut, false, RivtaProfileEnum.RIVTABP20);
		ServiceInteraction si = rep.getServiceInteraction("RegisterMedicalCertificate","riv", "insuranceprocess", "healthreporting", 1, 0)
		
		String generatedXSDString = new SchemaGenerator().generateCoreSchema(si)
		Document generatedXSD = XMLUnit.buildDocument(XMLUnit.getControlParser(), new StringReader(generatedXSDString))
		
        Diff myDiff = new Diff(myExpectedSchema, generatedXSD);
        assertTrue("Core Schema not generated as expected:" + myDiff, myDiff.similar());
	}	
	
	void testGetWsaSchema() {
		XMLUnit.setIgnoreWhitespace(true)
		String xsdPath = "/se/skl/rivta/rivtatools_web/generator/ws-addressing-1.0.xsd"
		InputStream wsaSchema = getClass().getResourceAsStream(xsdPath)
        Document myExpectedSchema = XMLUnit.buildDocument(XMLUnit.getControlParser(), new InputStreamReader(wsaSchema, Charset.forName("UTF-8")))
		
		String generatedXSDString = new SchemaGenerator().getWsaSchema()
		Document generatedXSD = XMLUnit.buildDocument(XMLUnit.getControlParser(), new StringReader(generatedXSDString))
		
        Diff myDiff = new Diff(myExpectedSchema, generatedXSD);
        assertTrue("WSA Schema not loaded as expected:" + myDiff, myDiff.similar());
	}	
}
