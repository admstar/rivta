package se.skl.rivta.bp20.refapp.producer;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.w3.wsaddressing10.AttributedURIType;

import se.skl.riv.ehr.ehrexchange.ehrextraction.v1.rivtabp20.EhrExtractionResponderInterface;
import se.skl.riv.ehr.ehrexchange.ehrextractionresponder.v1.GetEhrExtractContinuationRequestType;
import se.skl.riv.ehr.ehrexchange.ehrextractionresponder.v1.GetEhrExtractRequestType;
import se.skl.riv.ehr.ehrexchange.ehrextractionresponder.v1.GetEhrExtractResponseType;
import se.skl.riv13606.v1.EHREXTRACT;
import se.skl.riv13606.v1.II;
import se.skl.riv13606.v1.TS;

@WebService(serviceName = "EhrExtractionResponderService", portName = "EhrExtractionResponderPort", targetNamespace = "urn:riv:ehr:ehrexchange:EhrExtraction:1:rivtabp20")
public class EhrExtractionResponderProducer implements EhrExtractionResponderInterface {

	public GetEhrExtractResponseType getEhrExtract(
			AttributedURIType logicalAddress,
			GetEhrExtractRequestType parameters) {

		try {
			GetEhrExtractResponseType response = new GetEhrExtractResponseType();
			List<EHREXTRACT> list = response.getEhrExtract();
			
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
			II socRequest = parameters.getSubjectOfCareId();
			String identifierName = socRequest.getRoot();
			soc.setIdentifierName(identifierName);
			e.setSubjectOfCare(soc);

			TS tc = new TS();
			tc.setValue(new Date().toString());
			e.setTimeCreated(tc);

			list.add(e);
			return response;

		} catch (RuntimeException e) {
			System.out.println("Error occured: " + e);
			throw e;
		}
	}

	public GetEhrExtractResponseType getEhrExtractContinuation(
			AttributedURIType logicalAddress,
			GetEhrExtractContinuationRequestType parameters) {

		GetEhrExtractResponseType response = new GetEhrExtractResponseType();
		return response;
	}
}