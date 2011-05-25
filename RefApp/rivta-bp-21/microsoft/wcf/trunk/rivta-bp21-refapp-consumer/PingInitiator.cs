using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using rivta_bp21_refapp_schemas.wsdl;

namespace rivta_bp21_refapp_consumer
{
    class PingInitiator
    {
        private const string INFO_MSG = "RIV TA BP2.1 Ref App OK";
        private const string LOGICAL_ADDRESS = "SE2321000016-3MKB";

        private PingForConfigurationResponderInterface service = null;

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

            PingInitiator initiator = new PingInitiator(address);
            Console.WriteLine("RIV TA Basic Profile v2.1 - Ref App, Microsoft WCF C# Consumer");

            Console.WriteLine("Calling ping-operation...");

            PingForConfigurationResponse response = initiator.callPing(INFO_MSG, LOGICAL_ADDRESS);
            Console.WriteLine("Response from service");

            ConfigurationType[] confs = response.configuration;
            for (int i = 0; i < confs.Length; i++)
            {
                ConfigurationType c = confs[i];
                Console.WriteLine("Configuration item --- key: " + c.name + " val: " + c.value);
            }

            Console.WriteLine("Press <ENTER> to terminate service.");
            Console.WriteLine();
            Console.ReadLine();
    	}

        public PingInitiator(String address)
        {
            if (address == null)
            {
                service = new PingForConfigurationResponderInterfaceClient("PingResponderPort");
            }
            else
            {
                service = new PingForConfigurationResponderInterfaceClient("PingResponderPort", address);
            }

        }

        public PingForConfigurationResponse callPing(String info, String logicalAddresss)
        {
            PingForConfigurationRequest request = new PingForConfigurationRequest();
            request.LogicalAddress = LOGICAL_ADDRESS;
            request.PingForConfiguration = new PingForConfigurationType();

            PingForConfigurationResponse response = service.PingForConfiguration(request);

            return response;
        }
    }
}
