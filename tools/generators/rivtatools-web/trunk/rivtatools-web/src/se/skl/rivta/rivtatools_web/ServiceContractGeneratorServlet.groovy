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
package se.skl.rivta.rivtatools_web;

import javax.servlet.ServletException;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import se.skl.rivta.rivtatools_web.generator.GeneratorUtil;
//import se.skl.rivta.rivtatools_web.generator.MavenJaxwsPomGenerator;
import se.skl.rivta.rivtatools_web.generator.SchemaGenerator;
import se.skl.rivta.rivtatools_web.generator.ServiceInteractionWsdlDSL;
import se.skl.rivta.rivtatools_web.generator.WSDLGenerator;
import se.skl.rivta.rivtatools_web.model.MEPEnum;
import se.skl.rivta.rivtatools_web.model.Repository;
import se.skl.rivta.rivtatools_web.model.RivtaProfileEnum;
import se.skl.rivta.rivtatools_web.model.ServiceInteraction;
import static se.skl.rivta.rivtatools_web.model.ServiceInteractionTypeEnum.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.servlet.http.*;

public class ServiceContractGeneratorServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		// Set default values for form fields
		[description : "Submission of medical health reports to the Swedish social insurance institutions",
		iprHolder : "Sveriges Kommuner och Landsting",
		name : "RegisterMedicalCertificate",
		architectureDomain: "riv",
		domain : "insuranceprocess",
		subDomain : "healthreporting",
		majorVersion : 1,
		minorVersion : 0,
		logicalAddressDescription : "The organisation number of the receiving insurance institution",
		serviceInteractionType : "Request-Response",
		profile : "RIVTABP21",
		isReadOnly : "false"].each {key, value -> req.setAttribute key, value}
		req.getRequestDispatcher("ServiceContractGeneratorForm.html").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		Map p = [:]
		// Create parameter map and at the same time copy parameters to request attributes
		req.getParameterMap().each {key, value -> p.put(key, value[0]); req.setAttribute(key, value[0])}
		
		Repository rep = new Repository();
		
		String isType = p.serviceInteractionType
		String isReadOnly = p.isReadOnly
		boolean isBiDir
		MEPEnum mep
		
		if ("Request-Response".equals(isType)) {
			mep = MEPEnum.InOut
		}
		else mep = MEPEnum.In
		
		if ("Bidirectional".equals(isType)) {
			isBiDir = true
		}
		else {
			isBiDir = false
		}
		
		rep.addServiceInteraction(p.iprHolder, p.architectureDomain, p.domain, p.subDomain, Integer.parseInt(p.majorVersion), Integer.parseInt(p.minorVersion), p.name, p.description, p.logicalAddressDescription, isBiDir, mep, Boolean.parseBoolean(p.isReadOnly), RivtaProfileEnum.valueOf(p.profile));
		ServiceInteraction si = rep.getServiceInteraction(p.name, p.architectureDomain, p.domain, p.subDomain, Integer.parseInt(p.majorVersion), Integer.parseInt(p.minorVersion))
		
		String button = p.generate		
		
		if ("Generate zip".equals(button)) generateZip(resp, si)			
		if ("Generate complete WSDL".equals(button)) resp.writer << generateWsdl(resp, si)
		if ("Generate Responder Schema skeleton".equals(button)) generateResponderXsd(resp, si)
		if ("Generate Initiator Schema skeleton".equals(button)) generateInitiatorXsd(resp, si)
		if ("Show Diagram".equals(button)) showUml(req, resp, si)
	}
	
	def showUml(HttpServletRequest req, HttpServletResponse resp, ServiceInteraction si) {
		def produceRequestResponseUrl = {"http://yuml.me/diagram/scruffy;dir:TB;/class/[Initiator{bg:orange}]-${si.getResponder().name}(Request) : Response>[Responder{bg:orange}]"}
		def produceOneWayUrlProducer = {"http://yuml.me/diagram/scruffy;dir:TB;/class/[Initiator{bg:orange}]-${si.getResponder().name}(Request)>[Responder{bg:orange}]"}	
		def produceBiDirUrlProducer = {"http://yuml.me/diagram/scruffy;dir:TB;/class/[Responder{bg:orange}]-${si.getInitiator().name}(Response)>[Initiator{bg:orange}], [Initiator{bg:orange}]-${si.getResponder().name}(Request)>[Responder{bg:orange}]"}
		def type = si.type
		String imageUrl
		switch (si.type) {
			case RequestResponse : imageUrl = produceRequestResponseUrl(); break
			case OneWay : imageUrl = produceOneWayUrlProducer(); break
			case BiDir : imageUrl = produceBiDirUrlProducer(); break
		}
		req.setAttribute ("umlImageUrl", imageUrl)
		req.getRequestDispatcher("ServiceContractGeneratorForm.html").forward(req, resp);
	}
	
	void generateWsdl(HttpServletResponse resp, ServiceInteraction si) {
		final Logger log = Logger.getLogger(this.getClass().getName())
		String generatedString = new WSDLGenerator().generateWSDL(si)
		resp.contentType = "application/octet-stream"
		use(ServiceInteractionWsdlDSL) {
			resp.setHeader( "Content-Disposition", "attachment; filename=${si.wsdlFileName}" );
		}
		resp.setCharacterEncoding("UTF-8")
		resp.writer << generatedString
		log.info("WSDL for: ${generatedString}")
	}
	
	void generateResponderXsd(HttpServletResponse resp, ServiceInteraction si) {
		final Logger log = Logger.getLogger(this.getClass().getName())
		String generatedString = new SchemaGenerator().generateResponderSchema(si)
		resp.contentType = "application/octet-stream"
		use(ServiceInteractionWsdlDSL) {
			resp.setHeader( "Content-Disposition", "attachment; filename=${si.responderSchemaName}" );
		}	
		resp.setCharacterEncoding("UTF-8")		
		resp.writer << generatedString
		log.info("Responder schema for: ${generatedString}")
	}	
	
	void generateInitiatorXsd(HttpServletResponse resp, ServiceInteraction si) {
		final Logger log = Logger.getLogger(this.getClass().getName())
		if (!si.getIsBiDir()) throw new RuntimeException("Initiator schema can only be generated for Bidirectional services")
		String generatedString = new SchemaGenerator().generateInitiatorSchema(si)
		resp.contentType = "application/octet-stream"
		use(ServiceInteractionWsdlDSL) {
			resp.setHeader( "Content-Disposition", "attachment; filename=${si.initiatorSchemaName}" );
		}
		resp.setCharacterEncoding("UTF-8")		
		resp.writer << generatedString
		log.info("Initiator schema for: ${generatedString}")
	}
	
	/**
	 * Generate content to a zip stream with the following structure:
	 * /core_components
	 *  |-<core-schema>.xsd
	 *  |-ws-addressing-1.0.xsd
	 * /interactions
	 *  |-<name of interaction>
	 *    |-<wsdl>.wsdl
	 *    |-<responder schema>.xsd
	 *    |-<initiator schema>.xsd
	 * @param os
	 * @param si
	 * @return
	 */	
	void generateZip(HttpServletResponse resp, ServiceInteraction si) {
		
		final Logger log = Logger.getLogger(this.getClass().getName())
		ByteArrayOutputStream ba = new ByteArrayOutputStream()		
		ZipOutputStream zipFile = new ZipOutputStream(ba)
		resp.contentType = "application/octet-stream"
		
		use(ServiceInteractionWsdlDSL) {
			resp.setHeader( "Content-Disposition", "attachment; filename=${si.name}_${si.version.getMajorVersion()}.${si.version.getMinorVersion()}.zip" );	
			String InteractionsBaseName = "schemas/interactions/${si.name}Interaction"
			String coreComponentsBaseName = "schemas/core_components"
			
			def genFiles = si.profile == RivtaProfileEnum.RIVTABP20 ?
				[
					[fileContent: new WSDLGenerator().generateWSDL(si), name: "${InteractionsBaseName}/${si.wsdlFileName}"],
					[fileContent: new SchemaGenerator().generateResponderSchema(si), name: "${InteractionsBaseName}/${si.responderSchemaName}"],
					[fileContent: new SchemaGenerator().generateCoreSchema(si), name: "${coreComponentsBaseName}/${si.coreSchemaName}"],
					[fileContent: new SchemaGenerator().getWsaSchema(), name: "${coreComponentsBaseName}/ws-addressing-1.0.xsd"]
					// TODO: Add generation of JAXWS maven pom
					// [fileContent: new MavenJaxwsPomGenerator().generatePom(si), name: "jaxws/pom.xml"]
					// TODO: Add generation of .Net svcutil bat file
					] : si.profile == RivtaProfileEnum.WSIBP11 ?
				[
					[fileContent: new WSDLGenerator().generateWSDL(si), name: "${InteractionsBaseName}/${si.wsdlFileName}"],
					[fileContent: new SchemaGenerator().generateResponderSchema(si), name: "${InteractionsBaseName}/${si.responderSchemaName}"],
					[fileContent: new SchemaGenerator().generateCoreSchema(si), name: "${coreComponentsBaseName}/${si.coreSchemaName}"]
					// TODO: Add generation of JAXWS maven pom
					// [fileContent: new MavenJaxwsPomGenerator().generatePom(si), name: "jaxws/pom.xml"]
					// TODO: Add generation of .Net svcutil bat file
					] : 
				[
					[fileContent: new WSDLGenerator().generateWSDL(si), name: "${InteractionsBaseName}/${si.wsdlFileName}"],
					[fileContent: new SchemaGenerator().generateResponderSchema(si), name: "${InteractionsBaseName}/${si.responderSchemaName}"],
					[fileContent: new SchemaGenerator().generateCoreSchema(si), name: "${coreComponentsBaseName}/${si.coreSchemaName}"],
					[fileContent: new SchemaGenerator().getRegistrySchema(), name: "${coreComponentsBaseName}/itintegration_registry_1.0.xsd"]
					]
			if (si.getIsBiDir()) genFiles.add([fileContent: new SchemaGenerator().generateInitiatorSchema(si), name: "${InteractionsBaseName}/${si.initiatorSchemaName}"])
			genFiles.each {
				def byteContent = it.fileContent.getBytes(Charset.forName("UTF-8"))						
				def codeSize = byteContent.size()
				zipFile.putNextEntry(new ZipEntry(it.name))
				zipFile.write(byteContent, 0, codeSize)
				zipFile.closeEntry()
			}
			zipFile.close()
		}
		resp.outputStream << ba.toByteArray()
	}
}
