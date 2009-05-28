package se.skl.rivta.bp20.refapp.consumer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3.wsaddressing10.AttributedURIType;

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

		// Setup ssl info for the initial ?wsdl lookup...
		System.setProperty("javax.net.ssl.keyStore","../certs/consumer.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "password");
		System.setProperty("javax.net.ssl.trustStore", "../certs/consumer-truststore.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "password");

		// Try 13606-GetEhrExtract.https.url for using plain http
		String adress = Util.getProperty("13606-GetEhrExtract.https.url");

		logger.info("Consumer connecting to "  + adress);
		// alternative hsaid: "cn=server3,ou=Division 1,ou=Lasarettet i Ystad,o=Region Skåne,l=Skåne län c=SE"
		String reply = callRequestEHRExtract("RIV TA BP2.0 Ref App OK", adress, "SE2321000016-3MKB");
		logger.info("Producer returned: " + reply);
	}

	public static String callRequestEHRExtract(String id, String serviceAddress, String logicalAddresss) {
		
		RIV13606REQUESTEHREXTRACTPortType service = new RIV13606REQUESTEHREXTRACTService(
			createEndpointUrlFromServiceAddress(serviceAddress)).getRIV13606REQUESTEHREXTRACTPort();

		AttributedURIType logicalAddressHeader = new AttributedURIType();
		logicalAddressHeader.setValue(logicalAddresss);
		RIV13606REQUESTEHREXTRACTRequestType request = new RIV13606REQUESTEHREXTRACTRequestType();
		II ii = new II();
		ii.setRoot(id);
		request.setSubjectOfCareId(ii);
		RIV13606REQUESTEHREXTRACTResponseType result = service.riv13606REQUESTEHREXTRACT(logicalAddressHeader, request);

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
}