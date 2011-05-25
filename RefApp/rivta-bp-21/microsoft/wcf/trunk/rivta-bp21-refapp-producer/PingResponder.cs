using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using rivta_bp21_refapp_schemas.wsdl;

namespace rivta_bp21_refapp_producer
{
    class PingResponder : PingForConfigurationResponderInterface
    {
        public PingForConfigurationResponse PingForConfiguration(PingForConfigurationRequest request)
        {
            PingForConfigurationResponse response = new PingForConfigurationResponse();
            ConfigurationType c1 = new ConfigurationType();
            c1.name = "os";
            c1.value = "Mac OSX";

            return response;
        }
    }
}
