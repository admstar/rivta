package se.skl.rivta.bp20.refapp.consumer;

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
import se.skl.riv13606.v1.EHREXTRACT;
import se.skl.riv13606.v1.II;
import se.skl.rivta.bp20.refapp.util.Util;

public class EhrExtractionResponderConsumer {

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
		String reply = callGetEhrExtract("RIV TA BP2.0 Ref App OK", adress, "SE2321000016-3MKB");
		logger.info("Producer returned: " + reply);
	}

	public static String callGetEhrExtract(String id, String serviceAddress, String logicalAddresss) {
		
		EhrExtractionResponderInterface service = new EhrExtractionResponderService(
			createEndpointUrlFromServiceAddress(serviceAddress)).getEhrExtractionResponderPort();

		AttributedURIType logicalAddressHeader = new AttributedURIType();
		logicalAddressHeader.setValue(logicalAddresss);

		GetEhrExtractRequestType request = new GetEhrExtractRequestType();
		II ii = new II();
		ii.setRoot(id);
		request.setSubjectOfCareId(ii);
		
		GetEhrExtractResponseType result = service.getEhrExtract(logicalAddressHeader, request);

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