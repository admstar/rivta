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

package se.skl.rivta.bp20.refapp.producer.ehrextraction;

import java.util.Date;

import javax.jws.WebService;

import org.w3.wsaddressing10.AttributedURIType;

import se.skl.riv.ehr.ehrexchange.ehrextraction.v1.rivtabp20.EhrExtractionResponderInterface;
import se.skl.riv.ehr.ehrexchange.ehrextractionresponder.v1.GetEhrExtractContinuationRequestType;
import se.skl.riv.ehr.ehrexchange.ehrextractionresponder.v1.GetEhrExtractContinuationResponseType;
import se.skl.riv.ehr.ehrexchange.ehrextractionresponder.v1.GetEhrExtractRequestType;
import se.skl.riv.ehr.ehrexchange.ehrextractionresponder.v1.GetEhrExtractResponseType;
import se.skl.riv.ehr.ehrexchange.ehrextractionresponder.v1.PingRequestType;
import se.skl.riv.ehr.ehrexchange.ehrextractionresponder.v1.PingResponseType;
import se.skl.riv13606.v1.EHREXTRACT;
import se.skl.riv13606.v1.II;
import se.skl.riv13606.v1.TS;

@WebService(serviceName = "EhrExtractionResponderService", portName = "EhrExtractionResponderPort", targetNamespace = "urn:riv:ehr:ehrexchange:EhrExtraction:1:rivtabp20")
public class Responder implements EhrExtractionResponderInterface {

	public GetEhrExtractResponseType getEhrExtract(
			AttributedURIType logicalAddress,
			GetEhrExtractRequestType parameters) {

		try {
			GetEhrExtractResponseType response = new GetEhrExtractResponseType();

			EHREXTRACT e = createEhrExtract(parameters);

			response.getEhrExtract().add(e);

			return response;

		} catch (RuntimeException e) {
			System.out.println("Error occured: " + e);
			throw e;
		}
	}

	private EHREXTRACT createEhrExtract(GetEhrExtractRequestType parameters) {
		EHREXTRACT e = new EHREXTRACT();

		II ap = new II();
		ap.setRoot("AuthorisingParty-UID");
		e.setAuthorisingParty(ap);

		II eid = new II();
		eid.setRoot("EhrId-UID");
		e.setEhrId(eid);

		II es = new II();
		es.setRoot("EhrSystem-UID");
		e.setEhrSystem(es);

		e.setRmId("RmId");

		// relay something back that can be verified at the original
		// requester (end user)
		II soc = new II();
		soc.setRoot("SubjectOfCare-UID");
		soc.setFlavorId("sugar");
		II socRequest = parameters.getSubjectOfCareId();
		String identifierName = socRequest.getRoot();
		soc.setIdentifierName(identifierName);
		e.setSubjectOfCare(soc);

		TS tc = new TS();
		tc.setValue(new Date().toString());
		e.setTimeCreated(tc);
		return e;
	}

	public GetEhrExtractContinuationResponseType getEhrExtractContinuation(
			AttributedURIType logicalAddress,
			GetEhrExtractContinuationRequestType parameters) {

		GetEhrExtractContinuationResponseType response = new GetEhrExtractContinuationResponseType();
		return response;
	}

	public PingResponseType ping(AttributedURIType logicalAddress, PingRequestType parameters) {
		PingResponseType response = new PingResponseType();
		response.setInfo(parameters.getInfo());
		response.setLogicalAddress(logicalAddress.getValue());
		return response;
	}
}