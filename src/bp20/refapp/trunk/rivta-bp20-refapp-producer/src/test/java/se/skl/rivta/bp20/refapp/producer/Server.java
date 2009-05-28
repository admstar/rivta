package se.skl.rivta.bp20.refapp.producer;

import javax.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.skl.rivta.bp20.refapp.util.Util;

public class Server {

	static private final Logger logger = LoggerFactory.getLogger(Util.class);

	static public void main(String[] args) throws Exception {
		
		logger.info("Starting server...");

		startService(new RIV13606RequestEHRExtractProducer(), "13606-GetEhrExtract.url");
        logger.info("Server ready!");
    }

	static private void startService(Object serviceImpl, String addressId) {
        String address = Util.getProperty(addressId);
        Endpoint.publish(address, serviceImpl);
		logger.info("Service available at: " + address + "?wsdl");
    }
}
