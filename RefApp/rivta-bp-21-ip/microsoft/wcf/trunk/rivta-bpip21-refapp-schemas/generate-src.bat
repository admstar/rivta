@REM --------------------------------------------------------------------------
@REM 
@REM Generates WCF Proxy and config code for MakeBooking Interaction
@REM 
@REM --------------------------------------------------------------------------
@REM 
@REM Problem with adressing the wsdl locally. Workaround in place is to use the Java - Apache CXF Web Service to provide the wsdl.
@REM --------------------------------------------------------------------------
svcutil.exe /language:cs /out:generated-src\MakeBookingResponderProxy.cs /config:generated-src\app.config /namespace:urn:riv:crm:scheduling:MakeBooking:1:rivtabpip21,rivta_bpip21_refapp_schemas.wsdl /namespace:urn:riv:crm:scheduling:MakeBookingResponder:1,rivta_bpip21_refapp_schemas.xsd /namespace:urn:oasis:names:tc:SAML:2.0:assertion,rivta_bpip21_refapp_schemas.xsd interactions\MakeBookingInteraction\MakeBookingInteraction_1.1_rivtabpip21.wsdl interactions\MakeBookingInteraction\*.xsd core_components\*.xsd profile\*.xsd
