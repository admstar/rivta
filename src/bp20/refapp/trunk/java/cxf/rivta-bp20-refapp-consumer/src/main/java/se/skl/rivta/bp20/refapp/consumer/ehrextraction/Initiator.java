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

package se.skl.rivta.bp20.refapp.consumer.ehrextraction;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3.wsaddressing10.AttributedURIType;

import se.skl.riv.ehr.ehrexchange.ehrextraction.v1.rivtabp20.EhrExtractionResponderInterface;
import se.skl.riv.ehr.ehrexchange.ehrextraction.v1.rivtabp20.EhrExtractionResponderService;
import se.skl.riv.ehr.ehrexchange.ehrextractionresponder.v1.GetEhrExtractRequestType;
import se.skl.riv.ehr.ehrexchange.ehrextractionresponder.v1.GetEhrExtractResponseType;
import se.skl.riv.ehr.ehrexchange.ehrextractionresponder.v1.PingRequestType;
import se.skl.riv.ehr.ehrexchange.ehrextractionresponder.v1.PingResponseType;
import se.skl.riv13606.v1.EHREXTRACT;
import se.skl.riv13606.v1.II;
import se.skl.rivta.bp20.refapp.util.Util;

public class Initiator {

	static private final Logger logger = LoggerFactory.getLogger(Util.class);

	static private EhrExtractionResponderInterface service = null;

	public static void main(String[] args) {
		
		String address = null;
		if (args.length == 0) {
			// Default address
			address = Util.getProperty("cxf.url");
		} else {
			// Use the provided URL, note that this address has to be using the https - protocoll as defined in cxf.xml, i.e.: <http:conduit name="...">

			address = args[0];			
		}

		Initiator initiator = new Initiator();

		// Setup ssl info for the initial ?wsdl lookup...
		initiator.setupSsl();
		
		String logicalAddress = "SE2321000016-3MKB"; // alternative hsaid: "cn=server3,ou=Division 1,ou=Lasarettet i Ystad,o=Region Skåne,l=Skåne län c=SE"		
		String testMessage = "RIV TA BP2.0 Ref App OK";
		logger.info("...Consumer connecting to {}/{}, testmessage: {}", new String[] {address, logicalAddress, testMessage});

		logger.info("Calling ping-operation...");
		PingResponseType pingReply = initiator.callPing(testMessage, address, logicalAddress);
		logger.info("...Producer returned: {}/{}", pingReply.getLogicalAddress(), pingReply.getInfo());

		logger.info("Calling getEhrExtract-operation...");
		GetEhrExtractResponseType reply = initiator.callGetEhrExtract("RIV TA BP2.0 Ref App OK", address, logicalAddress);
		logger.info("...Producer returned: " + initiator.getIdentifierName(reply));

	}

	/**
	 * Setup ssl info for the initial ?wsdl lookup...
	 */
	public void setupSsl() {
		System.setProperty("javax.net.ssl.keyStore","../certs/consumer.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "password");
		System.setProperty("javax.net.ssl.trustStore", "../certs/consumer-truststore.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "password");
	}

	public GetEhrExtractResponseType callGetEhrExtract(String id, String serviceAddress, String logicalAddresss) {
		
		EhrExtractionResponderInterface service = getService(serviceAddress);

		AttributedURIType logicalAddressHeader = createLogicalAddressHeader(logicalAddresss);

		GetEhrExtractRequestType request = new GetEhrExtractRequestType();
		II ii = new II();
		ii.setRoot(id);
		request.setSubjectOfCareId(ii);
		
		GetEhrExtractResponseType result = service.getEhrExtract(logicalAddressHeader, request);

		return result;
	}

	public PingResponseType callPing(String info, String serviceAddress, String logicalAddresss) {
		
		EhrExtractionResponderInterface service = getService(serviceAddress);

		AttributedURIType logicalAddressHeader = createLogicalAddressHeader(logicalAddresss);

		PingRequestType request = new PingRequestType();
		request.setInfo(info);
		
		PingResponseType result = service.ping(logicalAddressHeader, request);

		return result;
	}

	private EhrExtractionResponderInterface getService(String serviceAddress) {
		if (service == null) {
			service = new EhrExtractionResponderService(
				createEndpointUrlFromServiceAddress(serviceAddress)).getEhrExtractionResponderPort();
		}
		return service;
	}

	private URL createEndpointUrlFromServiceAddress(String serviceAddress) {
		try {
			return new URL(serviceAddress + "?wsdl");
		} catch (MalformedURLException e) {
			throw new RuntimeException("Malformed URL Exception: " + e.getMessage());
		}
	}

	private AttributedURIType createLogicalAddressHeader(String logicalAddresss) {
		AttributedURIType logicalAddressHeader = new AttributedURIType();
		logicalAddressHeader.setValue(logicalAddresss);
		return logicalAddressHeader;
	}

	private String getIdentifierName(GetEhrExtractResponseType result) {
		List<EHREXTRACT> list = result.getEhrExtract();
		EHREXTRACT firstExtract = list.get(0);
		return firstExtract.getSubjectOfCare().getIdentifierName();
	}
}