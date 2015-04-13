# RIV Teknisk anvisning - Basic Profile v2.1 - Referensapplikation - JAXWS with the Apache CXF implementation #



## Environment ##

The reference application requires the following to be pre installed:

  * Java SE Development Kit v5.0 or later
    * [download](http://java.sun.com/javase/downloads/index.jsp)
  * Maven v2.0.9 or later
    * [download](http://maven.apache.org/download.html)
  * Any Subversion client
    * [download](http://http://subversion.apache.org/)

## Checkout and build the reference application ##

  1. [Checkout the source code](http://code.google.com/p/rivta/source/checkout)
  1. `cd RefApp/rivta-bp-21/java/cxf/trunk`
  1. `mvn install`

## Running the reference application ##

### Producer ###

  1. cd rivta-bp21-refapp-producer
  1. mvn -e exec:java -Dexec.mainClass="se.skl.rivta.bp21.refapp.producer.Server"

This should produce output after running the consumer once similar to the following:

![http://rivta.googlecode.com/svn/wiki/images/refApp/cxf-producer-rivtabp21.png](http://rivta.googlecode.com/svn/wiki/images/refApp/cxf-producer-rivtabp21.png)

### Consumer ###

  1. cd rivta-bp21-refapp-consumer
  1. mvn -e exec:java -Dexec.mainClass="se.skl.rivta.bp21.refapp.consumer.crm.scheduling.Initiator"

This should produce output similar to the following:

![http://rivta.googlecode.com/svn/wiki/images/refApp/cxf-consumer-rivtabp21.png](http://rivta.googlecode.com/svn/wiki/images/refApp/cxf-consumer-rivtabp21.png)

## Sample soap/http-request ##

```

Address: /rivtabp21/refapp/MakeBookingResponderService
Encoding: UTF-8
Content-Type: text/xml; charset=UTF-8
Headers: {Content-Length=443, Host=localhost:11000, User-Agent=Apache CXF 2.2.1, connection=keep-alive, SOAPAction="urn:riv:crm:scheduling:MakeBookingResponder:1:MakeBooking", Pragma=no-cache, content-type=text/xml; charset=UTF-8, Cache-Control=no-cache, Accept=*/*}
Payload:

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:riv:interoperability:headers:1" xmlns:urn1="urn:riv:itintegration:registry:1" xmlns:urn2="urn:riv:crm:scheduling:MakeBookingResponder:1" xmlns:urn3="urn:riv:crm:scheduling:1" xmlns:urn4="urn:riv:crm:scheduling:1.1">
   <soapenv:Header>
      <urn:Actor>
         <urn:actorId>201202162742</urn:actorId>
         <urn:actorType>subject_of_care</urn:actorType>
      </urn:Actor>
      <urn1:LogicalAddress>SE123456789012-1234</urn1:LogicalAddress>
   </soapenv:Header>
   <soapenv:Body>
      <urn2:MakeBooking>
         <urn2:healthcare_facility_med>SE123456789012-1234</urn2:healthcare_facility_med>
         <urn2:requestedTimeslot>
            <urn3:startTimeInclusive>20120216140000</urn3:startTimeInclusive>
            <urn3:endTimeExclusive>20120216150000</urn3:endTimeExclusive>
            <urn3:healthcare_facility>SE0129876543210-4321</urn3:healthcare_facility>
            <urn3:subject_of_care>201202162742</urn3:subject_of_care>
         </urn2:requestedTimeslot>
         <urn2:subject_of_care_info>
            <urn4:firstName>Maj-Lis</urn4:firstName>
            <urn4:lastName>Stolt</urn4:lastName>
         </urn2:subject_of_care_info>
      </urn2:MakeBooking>
   </soapenv:Body>
</soapenv:Envelope>
```

Sample response

```
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Body>
      <ns2:MakeBookingResponse xmlns:ns2="urn:riv:crm:scheduling:MakeBookingResponder:1"  >
         <ns2:bookingId>76378467</ns2:bookingId>
         <ns2:resultCode>INFO</ns2:resultCode>
         <ns2:resultText>Dont eat anything within 2 hours before this appointment</ns2:resultText>
      </ns2:MakeBookingResponse>
   </soap:Body>
</soap:Envelope>
```
## Prepare for development in Eclipse ##

`mvn eclipse:eclipse`

## Overview of Maven modules ##

  * schemas
> > Contains XML Schemas and WSDL artefacts.
> > Also responsible for generating JAXB and JAX-WS binding code.

  * util
> > Contains reusable code, log-settings and so on.

  * producer
> > Service producer.

  * consumer
> > Service consumer.