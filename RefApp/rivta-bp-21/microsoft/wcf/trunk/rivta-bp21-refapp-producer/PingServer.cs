using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ServiceModel;

namespace rivta_bp21_refapp_producer
{
    class PingServer
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Initializing service...");
            ServiceHost host = new ServiceHost(typeof(PingResponder));
            host.Open();
            Console.WriteLine("Service started");
            Console.ReadLine();
            host.Close();
        }
    }
}
