
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
SET W0=%SCHEMADIR%%SCHEMADIR%\interactions\AvslutaFullmaktInteraction\AvslutaFullmaktInteraction_1.0_rivtabp20.wsdl
SET X0=%SCHEMADIR%\interactions\AvslutaFullmaktInteraction\*.xsd

SET SCHEMADIR=schemas
SET W1=%SCHEMADIR%%SCHEMADIR%\interactions\HamtaArbetsstalleInteraction\HamtaArbetsstalleInteraction_1.0_rivtabp20.wsdl
SET X1=%SCHEMADIR%\interactions\HamtaArbetsstalleInteraction\*.xsd

SET SCHEMADIR=schemas
SET W2=%SCHEMADIR%%SCHEMADIR%\interactions\HamtaFolkbokforingsinformationInteraction\HamtaFolkbokforingsinformationInteraction_1.0_rivtabp20.wsdl
SET X2=%SCHEMADIR%\interactions\HamtaFolkbokforingsinformationInteraction\*.xsd

SET SCHEMADIR=schemas
SET W3=%SCHEMADIR%%SCHEMADIR%\interactions\HamtaFullmaktInteraction\HamtaFullmaktInteraction_1.0_rivtabp20.wsdl
SET X3=%SCHEMADIR%\interactions\HamtaFullmaktInteraction\*.xsd

SET SCHEMADIR=schemas
SET W4=%SCHEMADIR%%SCHEMADIR%\interactions\HamtaSparInformationInteraction\HamtaSparInformationInteraction_1.0_rivtabp20.wsdl
SET X4=%SCHEMADIR%\interactions\HamtaSparInformationInteraction\*.xsd

SET SCHEMADIR=schemas
SET W5=%SCHEMADIR%%SCHEMADIR%\interactions\HamtaVardenhetInteraction\HamtaVardenhetInteraction_1.0_rivtabp20.wsdl
SET X5=%SCHEMADIR%\interactions\HamtaVardenhetInteraction\*.xsd

SET SCHEMADIR=schemas
SET W6=%SCHEMADIR%%SCHEMADIR%\interactions\KontrolleraFullmaktInteraction\KontrolleraFullmaktInteraction_1.0_rivtabp20.wsdl
SET X6=%SCHEMADIR%\interactions\KontrolleraFullmaktInteraction\*.xsd

SET SCHEMADIR=schemas
SET W7=%SCHEMADIR%%SCHEMADIR%\interactions\RegistreraFullmaktInteraction\RegistreraFullmaktInteraction_1.0_rivtabp20.wsdl
SET X7=%SCHEMADIR%\interactions\RegistreraFullmaktInteraction\*.xsd

SET SCHEMADIR=schemas
SET W8=%SCHEMADIR%%SCHEMADIR%\interactions\RegistreraVardenhetAnstalldInteraction\RegistreraVardenhetAnstalldInteraction_1.0_rivtabp20.wsdl
SET X8=%SCHEMADIR%\interactions\RegistreraVardenhetAnstalldInteraction\*.xsd

SET SCHEMADIR=schemas
SET W9=%SCHEMADIR%%SCHEMADIR%\interactions\RegistreraVardenhetInteraction\RegistreraVardenhetInteraction_1.0_rivtabp20.wsdl
SET X9=%SCHEMADIR%\interactions\RegistreraVardenhetInteraction\*.xsd

SET SCHEMADIR=schemas
SET W10=%SCHEMADIR%%SCHEMADIR%\interactions\TabortDosTillhorighetInteraction\TabortDosTillhorighetInteraction_1.0_rivtabp20.wsdl
SET X10=%SCHEMADIR%\interactions\TabortDosTillhorighetInteraction\*.xsd

SET SCHEMADIR=schemas
SET W11=%SCHEMADIR%%SCHEMADIR%\interactions\UppdateraDosTillhorighetInteraction\UppdateraDosTillhorighetInteraction_1.0_rivtabp20.wsdl
SET X11=%SCHEMADIR%\interactions\UppdateraDosTillhorighetInteraction\*.xsd

SET SCHEMADIR=schemas
SET W12=%SCHEMADIR%%SCHEMADIR%\interactions\UppdateraVardenhetAnstalldInteraction\UppdateraVardenhetAnstalldInteraction_1.0_rivtabp20.wsdl
SET X12=%SCHEMADIR%\interactions\UppdateraVardenhetAnstalldInteraction\*.xsd

SET SCHEMADIR=schemas
SET W13=%SCHEMADIR%%SCHEMADIR%\interactions\UppdateraVardenhetInteraction\UppdateraVardenhetInteraction_1.0_rivtabp20.wsdl
SET X13=%SCHEMADIR%\interactions\UppdateraVardenhetInteraction\*.xsd

SET XCORE=%SCHEMADIR%\core_components\*.xsd

SET SCHEMAS=%XCORE% %W0% %X0% %W1% %X1% %W2% %X2% %W3% %X3% %W4% %X4% %W5% %X5% %W6% %X6% %W7% %X7% %W8% %X8% %W9% %X9% %W10% %X10% %W11% %X11% %W12% %X12% %W13% %X13% 

SET OUTFILE=/out:wcf\generated-src\Se_apotekensserviceFolkInteractions.cs
SET APPCONFIG=/config:wcf\generated-src\app.config
SET NAMESPACE=/namespace:*,Riv.Se_apotekensservice.Folk.Schemas.v1
SET SVCUTIL="svcutil.exe"
SET XCORE=%SCHEMADIR%\core_components\*.xsd
%SVCUTIL% /language:cs %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS%

CD wcf
ECHO Generating Service contract .Net Binding interfaces and classes for se_apotekensservice:folk Release 1
ECHO I DotNetprojektet ska du ta lagga till referens till System.ServiceModel
