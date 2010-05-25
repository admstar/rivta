@REM --------------------------------------------------------------------------
@REM 
@REM Generates WCF Proxy and config code for RIV13606 EhrExtraction Interaction
@REM 
@REM --------------------------------------------------------------------------
@REM 
@REM Problem with adressing the wsdl locally. Workaround in place is to use the Java - Apache CXF Web Service to provide the wsdl.
@REM 
@REM The command should look like:
@REM 	svcutil.exe /language:cs /out:EhrExtractionResponderProxy.cs /config:app.config business\JournalinfoApoteketRIV\EhrExtractionInteraction.wsdl business\JournalinfoApoteketRIV\*.xsd  technical\wsaddressing\ws-addressing-1.0.xsd
@REM 
@REM But gives us errormessages like
@REM 	Validation Error: Type 'urn:riv13606:v1:II' is not declared.
@REM 
@REM --------------------------------------------------------------------------
svcutil /language:cs CreateListningInteraction.wsdl ws-addressing-1.0.xsd CreateListing_1_0.xsd
svcutil /language:cs GetAvailableServiceProvidersInteraction.wsdl ws-addressing-1.0.xsd GetAvailableServiceProviders_1_0.xsd 
svcutil /language:cs GetlListningInteraction.wsdl ws-addressing-1.0.xsd GetListing_1_0.xsd 