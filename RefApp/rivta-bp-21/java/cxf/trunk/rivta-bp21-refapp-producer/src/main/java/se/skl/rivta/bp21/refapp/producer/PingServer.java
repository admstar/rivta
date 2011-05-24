package se.skl.rivta.bp21.refapp.producer;

import javax.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.skl.rivta.bp21.refapp.producer.crm.scheduling.PingResponder;
import se.skl.rivta.bp21.refapp.util.Util;

public class PingServer {

	static private final Logger logger = LoggerFactory.getLogger(Util.class);
	
	public static void main(final String[] args) {
		// Make CXF use log4j (instead of JDK-logging), currently can't use slf4j
		System.setProperty("org.apache.cxf.Logger", "org.apache.cxf.common.logging.Log4jLogger");
		
		// Parse main arguments
		String address = null;
		if (args.length == 0) {
			// Start default service
			address = Util.getProperty("cxf.ping.url");
		} else {
			// Start a service on the provided URL, note that this requires pre-configuration in cxf.xml to work, i.e.: <httpj:engine port="11000">
			address = args[0];
		}
		
		logger.info("RIV TA Basic Profile v2.1 - Ref App, Apache CXF Producer running on Java version {}", System.getProperty("java.version"));
		logger.info("Starting server...");
		startService(new PingResponder(), address);
		logger.info("Server ready!");
    }

	static private void startService(Object serviceImpl, String address) {
        Endpoint.publish(address, serviceImpl);
		logger.info("Service available at: " + address + "?wsdl");
    }
}
