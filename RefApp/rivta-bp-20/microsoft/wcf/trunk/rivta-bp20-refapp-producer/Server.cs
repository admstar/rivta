using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.ServiceModel;

namespace rivta_bp20_refapp_producer
{
    class Server
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Initializing service...");
            ServiceHost host = new ServiceHost(typeof(Responder));
            host.Open();
            Console.WriteLine("Service started");
            Console.ReadLine();
            host.Close();
        }
    }
}
