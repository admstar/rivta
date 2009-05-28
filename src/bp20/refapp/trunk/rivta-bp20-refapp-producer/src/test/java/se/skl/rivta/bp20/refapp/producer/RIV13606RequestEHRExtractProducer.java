package se.skl.rivta.bp20.refapp.producer;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.xmlsoap.schemas.ws._2003._03.addressing.AttributedURI;

import se.skl.riv.wsdl.v1.RivHeaderType;
import se.skl.riv13606.riv13606RequestEHRExtract.v1.RIV13606REQUESTEHREXTRACTCONTINUATIONRequestType;
import se.skl.riv13606.riv13606RequestEHRExtract.v1.RIV13606REQUESTEHREXTRACTPortType;
import se.skl.riv13606.riv13606RequestEHRExtract.v1.RIV13606REQUESTEHREXTRACTRequestType;
import se.skl.riv13606.riv13606RequestEHRExtract.v1.RIV13606REQUESTEHREXTRACTResponseType;
import se.skl.riv13606.v1.EHREXTRACT;
import se.skl.riv13606.v1.II;
import se.skl.riv13606.v1.TS;

@WebService(serviceName = "RIV13606REQUEST_EHR_EXTRACT_Service", portName = "RIV13606REQUEST_EHR_EXTRACT_Port", targetNamespace = "urn:riv13606:13606RequestEHRExtract:v1")
public class RIV13606RequestEHRExtractProducer implements RIV13606REQUESTEHREXTRACTPortType {

	public RIV13606REQUESTEHREXTRACTResponseType riv13606REQUESTEHREXTRACT(
			AttributedURI logicalAddress,
			RivHeaderType rivHeader,
			RIV13606REQUESTEHREXTRACTRequestType riv13606REQUESTEHREXTRACTRequest) {
		try {
			RIV13606REQUESTEHREXTRACTResponseType r = new RIV13606REQUESTEHREXTRACTResponseType();

			List<EHREXTRACT> list = r.getEhrExtract();
			
			EHREXTRACT e = new EHREXTRACT();

			II ap = new II();
			ap.setRoot("AuthorisingParty-UID");
			e.setAuthorisingParty(ap);

			II eid = new II();
			eid.setRoot("EhrId-UID");
			e.setEhrId(eid);

			II es = new II();
			es.setRoot("EhrSystem-UID");
			e.setEhrSystem(es);

			e.setRmId("RmId");

			// relay something back that can be verified at the original
			// requester (end user)
			II soc = new II();
			soc.setRoot("SubjectOfCare-UID");
			soc.setFlavorId("sugar");
			II socRequest = riv13606REQUESTEHREXTRACTRequest.getSubjectOfCareId();
			String identifierName = socRequest.getRoot();
			soc.setIdentifierName(identifierName);
			e.setSubjectOfCare(soc);

			TS tc = new TS();
			tc.setValue(new Date().toString());
			e.setTimeCreated(tc);

			list.add(e);
			return r;
		} catch (RuntimeException e) {
			System.out.println("Error occured: " + e);
			throw e;
		}
	}

	public RIV13606REQUESTEHREXTRACTResponseType riv13606REQUESTEHREXTRACTCONTINUATION(
			AttributedURI logicalAddress,
			RivHeaderType rivHeader,
			RIV13606REQUESTEHREXTRACTCONTINUATIONRequestType riv13606REQUESTEHREXTRACTRequest) {
		try {
			RIV13606REQUESTEHREXTRACTResponseType r = new RIV13606REQUESTEHREXTRACTResponseType();
			return r;
		} catch (RuntimeException e) {
			System.out.println("Error occured: " + e);
			throw e;
		}

	}
}