package se.skl.tp.journalinfoapoteketriv.consumer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.skl.riv.wsdl.v1.ReceiverIdType;
import se.skl.riv.wsdl.v1.RivHeaderType;
import se.skl.riv.wsdl.v1.SenderIdType;
import se.skl.riv13606.riv13606RequestEHRExtract.v1.RIV13606REQUESTEHREXTRACTPortType;
import se.skl.riv13606.riv13606RequestEHRExtract.v1.RIV13606REQUESTEHREXTRACTRequestType;
import se.skl.riv13606.riv13606RequestEHRExtract.v1.RIV13606REQUESTEHREXTRACTResponseType;
import se.skl.riv13606.riv13606RequestEHRExtract.v1.RIV13606REQUESTEHREXTRACTService;
import se.skl.riv13606.v1.EHREXTRACT;
import se.skl.riv13606.v1.II;
import se.skl.rivta.bp20.refapp.util.Util;

public class Riv13606RequestEhrExtractConsumer {

	static private final Logger logger = LoggerFactory.getLogger(Util.class);

	public static void main(String[] args) {

/*		
		// Needed for accessing the WSDL file from an https URL
		if (args.length > 0) {
			System.setProperty("javax.net.ssl.keyStore", args[1]);
			System.setProperty("javax.net.ssl.keyStorePassword", args[2]);
			System.setProperty("javax.net.ssl.trustStore", args[3]);
			System.setProperty("javax.net.ssl.trustStorePassword", args[4]);
		} else {
			System.setProperty("javax.net.ssl.keyStore","../certs/consumer.jks");
			System.setProperty("javax.net.ssl.keyStorePassword", "password");
			System.setProperty("javax.net.ssl.trustStore", "../certs/consumer-truststore.jks");
			System.setProperty("javax.net.ssl.trustStorePassword", "password");
		}
*/
		String adress = Util.getProperty("13606-GetEhrExtract.url");

		logger.info("Consumer connecting to "  + adress);
		String reply = callRequestEHRExtract("RIV TA BP2.0 Ref App OK", adress);
		logger.info("Producer returned: " + reply);
	}

	public static String callRequestEHRExtract(String id, String serviceAddress) {
		
		RIV13606REQUESTEHREXTRACTPortType service = new RIV13606REQUESTEHREXTRACTService(
			createEndpointUrlFromServiceAddress(serviceAddress)).getRIV13606REQUESTEHREXTRACTPort();

		RivHeaderType rivHeader = createRivHeader("VardgivareA-IVK", "VardgivareC-IVK");

		RIV13606REQUESTEHREXTRACTRequestType request = new RIV13606REQUESTEHREXTRACTRequestType();
		II ii = new II();
		ii.setRoot(id);
		request.setSubjectOfCareId(ii);
		RIV13606REQUESTEHREXTRACTResponseType result = service.riv13606REQUESTEHREXTRACT(rivHeader, request);

		List<EHREXTRACT> list = result.getEhrExtract();

		EHREXTRACT firstExtract = list.get(0);
		return firstExtract.getSubjectOfCare().getIdentifierName();
	}

	public static URL createEndpointUrlFromServiceAddress(String serviceAddress) {
		try {
			return new URL(serviceAddress + "?wsdl");
		} catch (MalformedURLException e) {
			throw new RuntimeException("Malformed URL Exception: " + e.getMessage());
		}
	}

	/**
	 * Helper method for creating a RivHeader
	 * 
	 * @param hsaSender
	 * @param hsaReceiver
	 * @return
	 */
	public static RivHeaderType createRivHeader(String hsaSender, String hsaReceiver) {
		RivHeaderType rivh = new RivHeaderType();

		ReceiverIdType receiverIdType = new ReceiverIdType();
		receiverIdType.setReceiverIdType("hsaid");
		receiverIdType.setValue(hsaReceiver);
		rivh.setReceiverId(receiverIdType);

		SenderIdType senderIdType = new SenderIdType();
		senderIdType.setSenderIdType("hsaid");
		senderIdType.setValue(hsaSender);
		rivh.setSenderId(senderIdType);
		return rivh;
	}
}