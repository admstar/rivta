using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using rivta_bp20_refapp_schemas.wsdl;

namespace rivta_bp20_refapp_consumer
{
    class Initiator
    {
        private const string INFO_MSG = "RIV TA BP2.0 Ref App OK";
        private const string LOGICAL_ADDRESS = "SE2321000016-3MKB";

        private EhrExtractionResponderInterface service = null;


        public static void Main(string[] args)
        {
            String address = null;
            if (args.Length == 0)
            {
                // Use default address as specified in app.config
                address = null;
            }
            else
            {
                // Use the provided URL
                address = args[0];
            }

            Initiator initiator = new Initiator(address);
            DateTime ts;

            Console.WriteLine("RIV TA Basic Profile v2.0 - Ref App, Microsoft WCF C# Consumer");

            Console.WriteLine("Calling ping-operation first time...");
            ts = DateTime.Now;
            PingResponse pingResponse = initiator.callPing(INFO_MSG, LOGICAL_ADDRESS);
            Console.WriteLine(logText(ts) + pingResponse.logicalAddress + "/" + pingResponse.info);

            Console.WriteLine("Calling ping-operation second time...");
            ts = DateTime.Now;
            pingResponse = initiator.callPing(INFO_MSG, LOGICAL_ADDRESS);
            Console.WriteLine(logText(ts) + pingResponse.logicalAddress + "/" + pingResponse.info);

            Console.WriteLine("Calling getEhrExtract-operation first time...");
            ts = DateTime.Now;
            GetEhrExtractResponse response = initiator.callGetEhrExtract(INFO_MSG, LOGICAL_ADDRESS);
            Console.WriteLine(logText(ts) + getInfo(response));

            Console.WriteLine("Calling getEhrExtract-operation second time...");
            ts = DateTime.Now;
            response = initiator.callGetEhrExtract(INFO_MSG, LOGICAL_ADDRESS);
            Console.WriteLine(logText(ts) + getInfo(response));

            Console.WriteLine("Press <ENTER> to terminate service.");
            Console.WriteLine();
            Console.ReadLine();
    	}

        private static string getInfo(GetEhrExtractResponse response) 
        {
            EHR_EXTRACT firstExtract = response.ehr_extract[0];
            String identifierName = firstExtract.subject_of_care.identifierName;
            return identifierName;
        }

        private static string logText(DateTime starttime)
        {
            return "...Producer returned (in " + (int)DateTime.Now.Subtract(starttime).TotalMilliseconds + "ms): ";
        }


        public Initiator(String address)
        {
            if (address == null)
            {
                service = new EhrExtractionResponderInterfaceClient("EhrExtractionResponderPort");
            }
            else
            {
                service = new EhrExtractionResponderInterfaceClient("EhrExtractionResponderPort", address);
            }

        }

        public GetEhrExtractResponse callGetEhrExtract(String info, String logicalAddresss)
        {
            AttributedURIType logicalAddressHeader = new AttributedURIType();
            logicalAddressHeader.Value = logicalAddresss;

            GetEhrExtractRequestType request = new GetEhrExtractRequestType();
            II ii = new II();
            ii.root = info;
            request.subject_of_care_id = ii;

            GetEhrExtractResponse response = service.GetEhrExtract(new GetEhrExtract(logicalAddressHeader, request));

            return response;
        }

        public PingResponse callPing(String info, String logicalAddress)
        {
            AttributedURIType logicalAddressHeader = new AttributedURIType();
            logicalAddressHeader.Value = logicalAddress;

            PingRequestType parameters = new PingRequestType();
            parameters.info = info;

            PingResponse response = service.Ping(new Ping(logicalAddressHeader, parameters));

            return response;
        }
    }
}
