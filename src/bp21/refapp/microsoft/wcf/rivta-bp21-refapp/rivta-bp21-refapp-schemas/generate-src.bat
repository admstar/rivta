@REM --------------------------------------------------------------------------
@REM 
@REM Generates WCF Proxy and config code for RIV13606 EhrExtraction Interaction
@REM 
@REM --------------------------------------------------------------------------
@REM 
@REM Problem with adressing the wsdl locally. Workaround in place is to use the Java - Apache CXF Web Service to provide the wsdl.
@REM 
@REM The command should look like:
@REM 	svcutil.exe /language:cs /out:EhrExtractionResponderProxy.cs /config:app.config business\JournalinfoApoteketRIV\EhrExtractionInteraction_1.1_rivtabp20.wsdl business\JournalinfoApoteketRIV\*.xsd  technical\wsaddressing\ws-addressing-1.0.xsd
@REM 
@REM But gives us errormessages like
@REM 	Validation Error: Type 'urn:riv13606:v1:II' is not declared.
@REM 
@REM --------------------------------------------------------------------------
svcutil.exe /language:cs /out:generated-src\MakeBookingResponderProxy.cs /config:generated-src\app.config /namespace:urn:riv:crm:scheduling:MakeBooking:1:rivtabp21,rivta_bp21_refapp_schemas.wsdl /namespace:urn:riv:crm:scheduling:MakeBookingResponder:1,rivta_bp21_refapp_schemas.xsd interactions\MakeBookingInteraction\MakeBookingInteraction_1.1_rivtabp21.wsdl interactions\MakeBookingInteraction\*.xsd core_components\*.xsd
