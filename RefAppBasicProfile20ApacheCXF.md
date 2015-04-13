# RIV Teknisk anvisning - Basic Profile v2.0 - Referensapplikation - Apache CXF Environment #



## Environment ##

The reference application requires the following to be pre installed:

  * Java SE Development Kit v5.0 or later
    * [download](http://java.sun.com/javase/downloads/index.jsp)
  * Maven v2.0.9 or later
    * [download](http://maven.apache.org/download.html)
  * Any Subversion client
    * [download](http://subversion.tigris.org/)

## Checkout and build the reference application ##

  1. [Checkout the source code](http://code.google.com/p/rivta/source/checkout)
  1. mvn install

## Running the reference application ##

### Producer ###

  1. `cd rivta-bp20-refapp-producer`
  1. `mvn -e exec:java -Dexec.mainClass="se.skl.rivta.bp20.refapp.producer.Server"

This should produce output after running the consumer once similar to the following:

![http://rivta.googlecode.com/svn/wiki/images/cxf-producer.png](http://rivta.googlecode.com/svn/wiki/images/cxf-producer.png)

### Consumer ###

  1. `cd rivta-bp20-refapp-consumer`
  1. `mvn -e exec:java -Dexec.mainClass="se.skl.rivta.bp20.refapp.consumer.ehrextraction.Initiator"

This should produce output similar to the following:

![http://rivta.googlecode.com/svn/wiki/images/cxf-consumer.png](http://rivta.googlecode.com/svn/wiki/images/cxf-consumer.png)

or if you want to call a another service than the default cxf service:

`mvn -e exec:java -Dexec.mainClass="se.skl.rivta.bp20.refapp.consumer.ehrextraction.Initiator" -Dexec.args="https://localhost:12000/rivtabp20/refapp/riv13606RequestEHRExtract"

## Sample soap/http-request ##

Using the reference application results in soap/http-requests that look like:

```
Address: /rivtabp20/refapp/!riv13606RequestEHRExtract
Encoding: UTF-8
Content-Type: text/xml; charset=UTF-8
Headers: {Content-Length=541, Host=localhost:20000, User-Agent=Apache CXF 2.2.1, connection=keep-alive, SOAPAction="urn:riv:ehr:ehrexchange:EhrExtractionResponder:1:GetEhrExtract", Pragma=no-cache, content-type=text/xml; charset=UTF-8, Cache-Control=no-cache, Accept=*/*}
Payload:
 <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
   <soap:Header>
     <ns4:To xmlns:ns4="http://www.w3.org/2005/08/addressing" xmlns:ns3="urn:riv:ehr:ehrexchange:EhrExtractionResponder:1" xmlns:ns2="urn:riv13606:v1">SE2321000016-3MKB</ns4:To>
   </soap:Header>
   <soap:Body>
     <ns2:GetEhrExtract xmlns:ns2="urn:riv:ehr:ehrexchange:EhrExtractionResponder:1" xmlns:ns3="urn:riv13606:v1" xmlns:ns4="http://www.w3.org/2005/08/addressing">
       <ns2:subject_of_care_id root="RIV TA BP2.0 Ref App OK"/>
    </ns2:GetEhrExtract>
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