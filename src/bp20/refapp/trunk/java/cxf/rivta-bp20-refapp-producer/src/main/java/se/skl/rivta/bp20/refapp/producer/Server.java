package se.skl.rivta.bp20.refapp.producer;

import javax.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.skl.rivta.bp20.refapp.producer.ehrextraction.Responder;
import se.skl.rivta.bp20.refapp.util.Util;

public class Server {

	static private final Logger logger = LoggerFactory.getLogger(Util.class);

	static public void main(String[] args) throws Exception {
		
		logger.info("Starting server...");

		if (args.length == 0) {
			// Start default service
			startService(new Responder(), Util.getProperty("cxf.url"));
		} else {
			// Start a service on the provided URL, note that this requires pre-configuration in cxf.xml to work, i.e.: <httpj:engine port="11000">
			startService(new Responder(), args[0]);
		}
		logger.info("Server ready!");
    }

	static private void startService(Object serviceImpl, String address) {
        Endpoint.publish(address, serviceImpl);
		logger.info("Service available at: " + address + "?wsdl");
    }
}
