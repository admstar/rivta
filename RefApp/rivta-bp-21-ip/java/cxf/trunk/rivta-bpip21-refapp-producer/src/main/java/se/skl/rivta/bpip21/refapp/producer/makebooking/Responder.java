/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package se.skl.rivta.bpip21.refapp.producer.makebooking;

import javax.jws.WebService;

import oasis.names.tc.saml._2_0.assertion.AssertionType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.skl.crm.scheduling.v1.ResultCodeEnum;
import se.skl.riv.crm.scheduling.makebooking.v1.rivtabpip21.MakeBookingResponderInterface;
import se.skl.riv.crm.scheduling.makebookingresponder.v1.MakeBookingResponseType;
import se.skl.riv.crm.scheduling.makebookingresponder.v1.MakeBookingType;
import se.skl.rivta.bp20.refapp.util.Util;

@WebService(
	serviceName = "MakeBookingResponderService", 
	endpointInterface="se.skl.riv.crm.scheduling.makebooking.v1.rivtabpip21.MakeBookingResponderInterface", 
	portName = "MakeBookingResponderPort", 
	targetNamespace = "urn:riv:crm:scheduling:MakeBooking:1:rivtabpip21",
	wsdlLocation = "schemas/interactions/MakeBookingInteraction/MakeBookingInteraction_1.1_rivtabpip21.wsdl")
public class Responder implements MakeBookingResponderInterface {

	static private final Logger logger = LoggerFactory.getLogger(Util.class);

	@Override
	public MakeBookingResponseType makeBooking(String logicalAddress,
			AssertionType hcAssertion, MakeBookingType parameters) {
		
		logger.info("Tog emot anrop. Logisk adress {} Assertion: {}, Parameters: {}", new Object[]{logicalAddress, hcAssertion, parameters});
		
		
		final MakeBookingResponseType response = new MakeBookingResponseType();
		
		response.setBookingId("1");
		response.setResultCode(ResultCodeEnum.OK);
		response.setResultText("Bokning genomfšrd.");
		
		return response;
	}



//	public GetEhrExtractResponseType getEhrExtract(
//			AttributedURIType logicalAddress,
//			AssertionType hcAssertion,
//			GetEhrExtractRequestType parameters) {
//
//		logger.info("Operation getEhrExtract called");
//		try {
//			GetEhrExtractResponseType response = new GetEhrExtractResponseType();
//
//			EHREXTRACT e = createEhrExtract(parameters);
//
//			response.getEhrExtract().add(e);
//
//			return response;
//
//		} catch (RuntimeException e) {
//			System.out.println("Error occured: " + e);
//			throw e;
//		}
//	}
//
//	public GetEhrExtractContinuationResponseType getEhrExtractContinuation(
//			AttributedURIType logicalAddress,
//			AssertionType hcAssertion,
//			GetEhrExtractContinuationRequestType parameters) {
//
//		logger.info("Operation getEhrExtractContinuation called");
//
//		GetEhrExtractContinuationResponseType response = new GetEhrExtractContinuationResponseType();
//		return response;
//	}
//
//	public PingResponseType ping(AttributedURIType logicalAddress, PingRequestType parameters) {
//		logger.info("Operation ping called");
//		PingResponseType response = new PingResponseType();
//		response.setInfo(parameters.getInfo());
//		response.setLogicalAddress(logicalAddress.getValue());
//		return response;
//	}
//
//	private EHREXTRACT createEhrExtract(GetEhrExtractRequestType parameters) {
//		EHREXTRACT e = new EHREXTRACT();
//
//		II ap = new II();
//		ap.setRoot("AuthorisingParty-UID");
//		e.setAuthorisingParty(ap);
//
//		II eid = new II();
//		eid.setRoot("EhrId-UID");
//		e.setEhrId(eid);
//
//		II es = new II();
//		es.setRoot("EhrSystem-UID");
//		e.setEhrSystem(es);
//
//		e.setRmId("RmId");
//
//		// relay something back that can be verified at the original
//		// requester (end user)
//		II soc = new II();
//		soc.setRoot("SubjectOfCare-UID");
//		soc.setFlavorId("sugar");
//		II socRequest = parameters.getSubjectOfCareId();
//		String identifierName = socRequest.getRoot();
//		soc.setIdentifierName(identifierName);
//		e.setSubjectOfCare(soc);
//
//		TS tc = new TS();
//		tc.setValue(new Date().toString());
//		e.setTimeCreated(tc);
//		return e;
//	}

}