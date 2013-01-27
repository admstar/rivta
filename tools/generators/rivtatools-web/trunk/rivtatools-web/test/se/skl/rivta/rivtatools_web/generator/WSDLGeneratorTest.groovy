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

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Transform;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;

import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.w3c.dom.Document;

import se.skl.rivta.rivtatools_web.model.MEPEnum;
import se.skl.rivta.rivtatools_web.model.Repository;
import se.skl.rivta.rivtatools_web.model.RivtaProfileEnum;
import se.skl.rivta.rivtatools_web.model.ServiceInteraction;
import se.skl.rivta.rivtatools_web.generator.WSDLGenerator;

import groovy.util.GroovyTestCase;

class WSDLGeneratorTest extends GroovyTestCase {
	
	public void testGenerateRIVTABP20_WSDL() {
		XMLUnit.setIgnoreWhitespace(true)
		String wsdlPath = "/se/skl/rivta/rivtatools_web/generator/RegisterMedicalCertificateInteraction_1.0_rivtabp20.wsdl"
		InputStream noBiDirInOutWSDL = getClass().getResourceAsStream(wsdlPath)
        Document myExpectedWSDL = XMLUnit.buildDocument(XMLUnit.getControlParser(), new InputStreamReader(noBiDirInOutWSDL, Charset.forName("UTF-8")))
		
		Repository rep = new Repository();
		rep.addServiceInteraction( "Fake Org","riv", "insuranceprocess", "healthreporting", 1, 0, "RegisterMedicalCertificate", "Submission of medical health reports to the Swedish social insurance institutions","LogicalAddress is the organization id for the insurance institution.", false, MEPEnum.InOut, false, RivtaProfileEnum.RIVTABP20);
		ServiceInteraction si = rep.getServiceInteraction("RegisterMedicalCertificate","riv", "insuranceprocess", "healthreporting", 1, 0)
		
		String generatedWSDLString = new WSDLGenerator().generateWSDL(si)
		Document generatedWSDL = XMLUnit.buildDocument(XMLUnit.getControlParser(), new StringReader(generatedWSDLString))
		
        Diff myDiff = new Diff(generatedWSDL,myExpectedWSDL);
        assertTrue("WSDL not generated as expected:" + myDiff, myDiff.similar());
	}
	
	public void testGenerateRIVTABP21_WSDL() {
		XMLUnit.setIgnoreWhitespace(true)
		String wsdlPath = "/se/skl/rivta/rivtatools_web/generator/RegisterMedicalCertificateInteraction_1.0_rivtabp21.wsdl"
		InputStream noBiDirInOutWSDL = getClass().getResourceAsStream(wsdlPath)
        Document myExpectedWSDL = XMLUnit.buildDocument(XMLUnit.getControlParser(), new InputStreamReader(noBiDirInOutWSDL, Charset.forName("UTF-8")))
		
		Repository rep = new Repository();
		rep.addServiceInteraction( "Fake Org","riv", "insuranceprocess", "healthreporting", 1, 0, "RegisterMedicalCertificate", "Submission of medical health reports to the Swedish social insurance institutions","LogicalAddress is the organization id for the insurance institution.", false, MEPEnum.InOut, false, RivtaProfileEnum.RIVTABP21);
		ServiceInteraction si = rep.getServiceInteraction("RegisterMedicalCertificate","riv", "insuranceprocess", "healthreporting", 1, 0)
		
		String generatedWSDLString = new WSDLGenerator().generateWSDL(si)
		Document generatedWSDL = XMLUnit.buildDocument(XMLUnit.getControlParser(), new StringReader(generatedWSDLString))
		
        Diff myDiff = new Diff(generatedWSDL, myExpectedWSDL);
        assertTrue("WSDL not generated as expected:" + myDiff, myDiff.similar());
	}	
	
	public void testGenerateWSIBP11_WSDL() {
		XMLUnit.setIgnoreWhitespace(true)
		String wsdlPath = "/se/skl/rivta/rivtatools_web/generator/RegisterMedicalCertificateInteraction_1.0_WSIBP11.wsdl"
		InputStream noBiDirInOutWSDL = getClass().getResourceAsStream(wsdlPath)
		Document myExpectedWSDL = XMLUnit.buildDocument(XMLUnit.getControlParser(), new InputStreamReader(noBiDirInOutWSDL, Charset.forName("UTF-8")))
		
		Repository rep = new Repository();
		rep.addServiceInteraction("Fake Org", "other", "insuranceprocess", "healthreporting", 1, 0, "RegisterMedicalCertificate", "Submission of medical health reports to the Swedish social insurance institutions",null, false, MEPEnum.InOut, false, RivtaProfileEnum.WSIBP11);
		ServiceInteraction si = rep.getServiceInteraction("RegisterMedicalCertificate","other", "insuranceprocess", "healthreporting", 1, 0)
		
		String generatedWSDLString = new WSDLGenerator().generateWSDL(si)
		Document generatedWSDL = XMLUnit.buildDocument(XMLUnit.getControlParser(), new StringReader(generatedWSDLString))
		
		Diff myDiff = new Diff(generatedWSDL,myExpectedWSDL);
		assertTrue("WSDL not generated as expected:" + myDiff, myDiff.similar());
	}
}
