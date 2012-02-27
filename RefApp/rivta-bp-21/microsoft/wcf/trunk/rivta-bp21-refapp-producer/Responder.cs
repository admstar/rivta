using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using rivta_bp21_refapp_schemas.wsdl;

namespace rivta_bp21_refapp_producer
{
    class Responder : MakeBookingResponderInterface
    {
           public MakeBookingResponse  MakeBooking(MakeBookingRequest request){
 	            Console.WriteLine("MakeBooking service invoked");

               MakeBookingResponse response = new MakeBookingResponse();
               response.bookingId = "unique id of created booking";
               response.resultCode = ResultCodeEnum.OK;
               response.resultText = "";

               return response;
           }
    }
}
