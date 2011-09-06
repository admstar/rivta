
	@REM ---------------------------------------------------------------------------------
	@REM Generates c# WCF service contracts (interface), client proxies and wcf config
	@REM file for the WSDLs + XML Schemas of the service domain, using .Net WCF tool svcuti.exe
	@REM ---------------------------------------------------------------------------------
	@REM Licensed to the Apache Software Foundation (ASF) under one
	@REM or more contributor license agreements. See the NOTICE file
	@REM distributed with this work for additional information
	@REM regarding copyright ownership. Inera AB licenses this file
	@REM to you under the Apache License, Version 2.0 (the
	@REM "License"); you may not use this file except in compliance
	@REM with the License. You may obtain a copy of the License at
	@REM
	@REM http://www.apache.org/licenses/LICENSE-2.0
	@REM Unless required by applicable law or agreed to in writing,
	@REM software distributed under the License is distributed on an
	@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
	@REM KIND, either express or implied. See the License for the
	@REM specific language governing permissions and limitations
	@REM under the License.
	@REM ---------------------------------------------------------------------------------
	CD ..
	
	SET SCHEMADIR=schemas
SET W0=%SCHEMADIR%%SCHEMADIR%\interactions\HamtaApoteksinfoEgetInteraction\HamtaApoteksinfoEgetInteraction_1.0_rivtabp20.wsdl
SET X0=%SCHEMADIR%\interactions\HamtaApoteksinfoEgetInteraction\*.xsd

SET SCHEMADIR=schemas
SET W1=%SCHEMADIR%%SCHEMADIR%\interactions\HamtaApoteksInfoInteraction\HamtaApoteksInfoInteraction_1.0_rivtabp20.wsdl
SET X1=%SCHEMADIR%\interactions\HamtaApoteksInfoInteraction\*.xsd

SET SCHEMADIR=schemas
SET W2=%SCHEMADIR%%SCHEMADIR%\interactions\KontaktuppgifterHamtaInteraction\KontaktuppgifterHamtaInteraction_1.0_rivtabp20.wsdl
SET X2=%SCHEMADIR%\interactions\KontaktuppgifterHamtaInteraction\*.xsd

SET SCHEMADIR=schemas
SET W3=%SCHEMADIR%%SCHEMADIR%\interactions\KontaktuppgifterUppdateraInteraction\KontaktuppgifterUppdateraInteraction_1.0_rivtabp20.wsdl
SET X3=%SCHEMADIR%\interactions\KontaktuppgifterUppdateraInteraction\*.xsd

SET SCHEMADIR=schemas
SET W4=%SCHEMADIR%%SCHEMADIR%\interactions\SkapaApotekInteraction\SkapaApotekInteraction_1.0_rivtabp20.wsdl
SET X4=%SCHEMADIR%\interactions\SkapaApotekInteraction\*.xsd

SET SCHEMADIR=schemas
SET W5=%SCHEMADIR%%SCHEMADIR%\interactions\SkapaDosmottagareInteraction\SkapaDosmottagareInteraction_1.0_rivtabp20.wsdl
SET X5=%SCHEMADIR%\interactions\SkapaDosmottagareInteraction\*.xsd

SET SCHEMADIR=schemas
SET W6=%SCHEMADIR%%SCHEMADIR%\interactions\SokDosmottagareInteraction\SokDosmottagareInteraction_1.0_rivtabp20.wsdl
SET X6=%SCHEMADIR%\interactions\SokDosmottagareInteraction\*.xsd

SET SCHEMADIR=schemas
SET W7=%SCHEMADIR%%SCHEMADIR%\interactions\TaBortDosmottagareInteraction\TaBortDosmottagareInteraction_1.0_rivtabp20.wsdl
SET X7=%SCHEMADIR%\interactions\TaBortDosmottagareInteraction\*.xsd

SET SCHEMADIR=schemas
SET W8=%SCHEMADIR%%SCHEMADIR%\interactions\UppdateraDosmottagareInteraction\UppdateraDosmottagareInteraction_1.0_rivtabp20.wsdl
SET X8=%SCHEMADIR%\interactions\UppdateraDosmottagareInteraction\*.xsd

SET SCHEMADIR=schemas
SET W9=%SCHEMADIR%%SCHEMADIR%\interactions\UppdateraExpoMedApotekInteraction\UppdateraExpoMedApotekInteraction_1.0_rivtabp20.wsdl
SET X9=%SCHEMADIR%\interactions\UppdateraExpoMedApotekInteraction\*.xsd

SET XCORE=%SCHEMADIR%\core_components\*.xsd

SET SCHEMAS=%XCORE% %W0% %X0% %W1% %X1% %W2% %X2% %W3% %X3% %W4% %X4% %W5% %X5% %W6% %X6% %W7% %X7% %W8% %X8% %W9% %X9% 

SET OUTFILE=/out:wcf\generated-src\Se_apotekensserviceExpoInteractions.cs
SET APPCONFIG=/config:wcf\generated-src\app.config
SET NAMESPACE=/namespace:*,Riv.Se_apotekensservice.Expo.Schemas.v1
SET SVCUTIL="svcutil.exe"
SET XCORE=%SCHEMADIR%\core_components\*.xsd
%SVCUTIL% /language:cs %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS%

CD wcf
ECHO Generating Service contract .Net Binding interfaces and classes for se_apotekensservice:expo Release 1
ECHO I DotNetprojektet ska du ta lagga till referens till System.ServiceModel
