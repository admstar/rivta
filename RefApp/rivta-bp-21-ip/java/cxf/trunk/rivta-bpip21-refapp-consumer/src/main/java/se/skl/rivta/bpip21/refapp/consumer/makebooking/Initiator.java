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

package se.skl.rivta.bpip21.refapp.consumer.makebooking;

import java.net.MalformedURLException;
import java.net.URL;

import oasis.names.tc.saml._2_0.assertion.AssertionType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.skl.riv.crm.scheduling.makebooking.v1.rivtabpip21.MakeBookingResponderInterface;
import se.skl.riv.crm.scheduling.makebooking.v1.rivtabpip21.MakeBookingResponderService;
import se.skl.riv.crm.scheduling.makebookingresponder.v1.MakeBookingResponseType;
import se.skl.riv.crm.scheduling.makebookingresponder.v1.MakeBookingType;
import se.skl.rivta.bp20.refapp.util.Util;

public class Initiator {

	static private final Logger logger = LoggerFactory.getLogger(Util.class);

	static private final String LOGICAL_ADDRESS = "SE165565968202-3P3Q";
	static private final AssertionType HP_ASSERTION = new AssertionType();
	
	private MakeBookingResponderInterface service = null;

	public static void main(String[] args) {
		
		// Make CXF use log4j (instead of JDK-logging), currently can't use slf4j
		System.setProperty("org.apache.cxf.Logger", "org.apache.cxf.common.logging.Log4jLogger");

		// Parse main arguments
		String address = null;
		if (args.length == 0) {
			// Default address
			address = Util.getProperty("cxf.url");
		} else {
			// Use the provided URL, note that this address has to be using the https - protocol as defined in cxf.xml, i.e.: <http:conduit name="...">
			address = args[0];			
		}

        Initiator initiator = new Initiator(address);

		logger.info("RIV TA Basic Profile med intygspropagering v2.1 - Ref App, Apache CXF Consumer running on Java version {}", System.getProperty("java.version"));
		logger.info("...connecting to {}", address);

		logger.info("Calling makeBooking-operation...");
		MakeBookingResponseType reply = initiator.callMakeBooking("RIV TA BP IP 2.1 Ref App OK", LOGICAL_ADDRESS);

		logger.info("=== Reply ===");
		logger.info("Booking id: {}", reply.getBookingId());
		logger.info("Result code: {}", reply.getResultCode().name());
		logger.info("Information: {}", reply.getResultText());
		logger.info("===");
	}

	public Initiator(String address) {
		// Setup ssl info for the initial ?wsdl lookup...
		setupSsl();
		service = new MakeBookingResponderService(createEndpointUrlFromServiceAddress(address)).getMakeBookingResponderPort();
	}

	/**
	 * Setup ssl info for the initial ?wsdl lookup...
	 */
	private void setupSsl() {
		System.setProperty("javax.net.ssl.keyStore","../certs/consumer.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "password");
		System.setProperty("javax.net.ssl.trustStore", "../certs/consumer-truststore.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "password");
	}

	public MakeBookingResponseType callMakeBooking(String id, String logicalAddress) {
		MakeBookingType request = new MakeBookingType();
		
		MakeBookingResponseType result = service.makeBooking(logicalAddress, HP_ASSERTION, request);
		return result;
	}

	private URL createEndpointUrlFromServiceAddress(String serviceAddress) {
		try {
			return new URL(serviceAddress + "?wsdl");
		} catch (MalformedURLException e) {
			throw new RuntimeException("Malformed URL Exception: " + e.getMessage());
		}
	}
}