using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using rivta_bpip21_refapp_schemas.wsdl;

namespace rivta_bpip21_refapp_consumer
{
    class Initiator
    {
        private const string INFO_MSG = "RIV TA BP2.1 Ref App OK";
        private const string LOGICAL_ADDRESS = "SE2321000016-3MKB";

        private MakeBookingResponderInterface service = null;

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
            Console.WriteLine("RIV TA Basic Profile v2.1 - Ref App, Microsoft WCF C# Consumer");

            Console.WriteLine("Calling makeBooking-operation...");

            MakeBookingResponse response = initiator.callMakeBooking(INFO_MSG, LOGICAL_ADDRESS);
            Console.WriteLine("Response from service");
            Console.WriteLine("Booking id: " + response.bookingId);
            Console.WriteLine("Result code: " + response.resultCode);
            Console.WriteLine("Result text: " + response.resultText);

            Console.WriteLine("Press <ENTER> to terminate service.");
            Console.WriteLine();
            Console.ReadLine();
    	}

        public Initiator(String address)
        {
            if (address == null)
            {
                service = new MakeBookingResponderInterfaceClient("ResponderPort");
            }
            else
            {
                service = new MakeBookingResponderInterfaceClient("ResponderPort", address);
            }

        }

        public MakeBookingResponse callMakeBooking(String info, String logicalAddresss)
        {
            MakeBookingRequest request = new MakeBookingRequest();
            request.LogicalAddress = "1234561234";
            request.MakeBooking = new MakeBookingType();
            request.Assertion = new AssertionType();

            MakeBookingResponse response = service.MakeBooking(request);
            return response;
        }
    }
}
