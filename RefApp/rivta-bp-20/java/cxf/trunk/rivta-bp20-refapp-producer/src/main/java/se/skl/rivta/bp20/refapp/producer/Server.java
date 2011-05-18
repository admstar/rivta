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

package se.skl.rivta.bp20.refapp.producer;

import javax.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.skl.rivta.bp20.refapp.producer.ehrextraction.Responder;
import se.skl.rivta.bp20.refapp.util.Util;

public class Server {

	static private final Logger logger = LoggerFactory.getLogger(Util.class);

	static public void main(String[] args) throws Exception {
		
		// Make CXF use log4j (instead of JDK-logging), currently can't use slf4j
		System.setProperty("org.apache.cxf.Logger", "org.apache.cxf.common.logging.Log4jLogger");

		// Parse main arguments
		String address = null;
		if (args.length == 0) {
			// Start default service
			address = Util.getProperty("cxf.url");
		} else {
			// Start a service on the provided URL, note that this requires pre-configuration in cxf.xml to work, i.e.: <httpj:engine port="11000">
			address = args[0];
		}

		logger.info("RIV TA Basic Profile v2.0 - Ref App, Apache CXF Producer running on Java version {}", System.getProperty("java.version"));
		logger.info("Starting server...");
		startService(new Responder(), address);
		logger.info("Server ready!");
    }

	static private void startService(Object serviceImpl, String address) {
        Endpoint.publish(address, serviceImpl);
		logger.info("Service available at: " + address + "?wsdl");
    }
}
