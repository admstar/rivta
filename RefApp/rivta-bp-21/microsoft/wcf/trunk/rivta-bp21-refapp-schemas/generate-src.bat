@REM --------------------------------------------------------------------------
@REM 
@REM Generates WCF Proxy and config code for MakeBooking Interaction
@REM 
@REM --------------------------------------------------------------------------
@REM 
@REM Problem with adressing the wsdl locally. Workaround in place is to use the Java - Apache CXF Web Service to provide the wsdl.
@REM --------------------------------------------------------------------------
svcutil.exe /language:cs /out:generated-src\MakeBookingResponderProxy.cs /config:generated-src\app.config /namespace:urn:riv:crm:scheduling:MakeBooking:1:rivtabp21,rivta_bp21_refapp_schemas.wsdl /namespace:urn:riv:crm:scheduling:MakeBookingResponder:1,rivta_bp21_refapp_schemas.xsd interactions\MakeBookingInteraction\MakeBookingInteraction_1.1_rivtabp21.wsdl interactions\MakeBookingInteraction\*.xsd core_components\*.xsd
svcutil.exe /language:cs /out:generated-src\PingForConfigurationResponderProxy.cs /config:generated-src\pingapp.config /namespace:urn:riv:itintegration:monitoring:PingForConfiguration:1:rivtabp21,rivta_bp21_refapp_schemas.wsdl /namespace:urn:riv:itintegration:monitoring:PingForConfigurationResponder:1,rivta_bp21_refapp_schemas.xsd interactions\PingForConfiguration\PingForConfigurationInteraction_1.0_rivtabp21.wsdl interactions\PingForConfiguration\*.xsd core_components\*.xsd
