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

package se.skl.rivta.bp21.refapp.consumer.crm.scheduling;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.skl.riv.crm.scheduling.v1.MakeBookingResponseType;
import se.skl.riv.crm.scheduling.v1.MakeBookingType;
import se.skl.riv.crm.scheduling.v1.rivtabp21.MakeBookingResponderInterface;
import se.skl.riv.crm.scheduling.v1.rivtabp21.MakeBookingResponderService;
import se.skl.rivta.bp21.refapp.util.Util;

public class Initiator {

	static private final Logger logger = LoggerFactory.getLogger(Util.class);
	static private final String LOGICAL_ADDRESS = "SE2321000016-3MKB"; // alternative hsaid: "cn=server3,ou=Division 1,ou=Lasarettet i Ystad,o=Region Skåne,l=Skåne län c=SE"		
	
	private MakeBookingResponderInterface service = null;

	public static void main(final String[] args) {
		
		// Make CXF use log4j (instead of JDK-logging), currently can't use slf4j
		System.setProperty("org.apache.cxf.Logger", "org.apache.cxf.common.logging.Log4jLogger");

		// Parse main arguments
		String address = null;
		if (args.length == 0) {
			// Default address
			address = Util.getProperty("cxf.url");
		} else {
			// Use the provided URL, note that this address has to be using the https - protocoll as defined in cxf.xml, i.e.: <http:conduit name="...">
			address = args[0];			
		}
		
		logger.info("Calling producer at address: " + address);
		
		final Initiator initiator = new Initiator(address);
		final MakeBookingResponseType responseType = initiator.callGetAvailableEServices(LOGICAL_ADDRESS);
		
		logger.info("Producer answered.");
		logger.info("Booking id: " + responseType.getBookingId());
		logger.info("Result code: " + responseType.getResultCode());
		logger.info("Result text: " + responseType.getResultText());
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

	public MakeBookingResponseType callGetAvailableEServices(String logicalAddresss) {
		final MakeBookingResponseType response = service.makeBooking("1234561234", new MakeBookingType());
		return response;
	}

	private URL createEndpointUrlFromServiceAddress(String serviceAddress) {
		try {
			return new URL(serviceAddress + "?wsdl");
		} catch (MalformedURLException e) {
			throw new RuntimeException("Malformed URL Exception: " + e.getMessage());
		}
	}
}