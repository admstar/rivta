using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using rivta_bp20_refapp_schemas.wsdl;

namespace rivta_bp20_refapp_producer
{
    class Responder : EhrExtractionResponderInterface
    {
        public GetEhrExtractResponse GetEhrExtract(GetEhrExtractRequest request)
        {
            GetEhrExtractResponse response = new GetEhrExtractResponse();
            EHR_EXTRACT[] list = new EHR_EXTRACT[1];
            response.ehr_extract = list;

            EHR_EXTRACT e = createEhrExtract(request);

            list[0] = e;
            return response;
        }

        private EHR_EXTRACT createEhrExtract(GetEhrExtractRequest request)
        {
            EHR_EXTRACT e = new EHR_EXTRACT();

            II ap = new II();
            ap.root = "AuthorisingParty-UID";
            e.authorising_party = ap;

            II eid = new II();
            eid.root = "EhrId-UID";
            e.ehr_id = eid;

            II es = new II();
            es.root = "EhrSystem-UID";
            e.ehr_system = es;

            e.rm_id = "RmId";

            // relay something back that can be verified at the original requester (end user)
            II soc = new II();
            soc.root = "SubjectOfCare-UID";
            soc.flavorId = "sugar";
            II socRequest = request.GetEhrExtract.subject_of_care_id;
            String identifierName = socRequest.root;
            soc.identifierName = identifierName;
            e.subject_of_care = soc;

            TS tc = new TS();
            tc.value = DateTime.Now.ToString();
            e.time_created = tc;
            return e;
        }

        public GetEhrExtractContinuationResponse GetEhrExtractContinuation(GetEhrExtractContinuationRequest request)
        {
            throw new NotImplementedException();
        }

        public PingResponse Ping(PingRequest request)
        {
            PingResponse response = new PingResponse();
            response.info = request.Ping.info;
            response.logicalAddress = request.To.Value;
            return response;
        }
    }
}
