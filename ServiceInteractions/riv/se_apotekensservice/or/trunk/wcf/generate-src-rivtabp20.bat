
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
SET W0=%SCHEMADIR%%SCHEMADIR%\interactions\AvregistreraDosunderlagInteraction\AvregistreraDosunderlagInteraction_1.0_rivtabp20.wsdl
SET X0=%SCHEMADIR%\interactions\AvregistreraDosunderlagInteraction\*.xsd

SET SCHEMADIR=schemas
SET W1=%SCHEMADIR%%SCHEMADIR%\interactions\BackaKrediteraUttagInteraction\BackaKrediteraUttagInteraction_1.0_rivtabp20.wsdl
SET X1=%SCHEMADIR%\interactions\BackaKrediteraUttagInteraction\*.xsd

SET SCHEMADIR=schemas
SET W2=%SCHEMADIR%%SCHEMADIR%\interactions\ForlangOrdinationVardInteraction\ForlangOrdinationVardInteraction_1.0_rivtabp20.wsdl
SET X2=%SCHEMADIR%\interactions\ForlangOrdinationVardInteraction\*.xsd

SET SCHEMADIR=schemas
SET W3=%SCHEMADIR%%SCHEMADIR%\interactions\GodkannDosunderlagInteraction\GodkannDosunderlagInteraction_1.0_rivtabp20.wsdl
SET X3=%SCHEMADIR%\interactions\GodkannDosunderlagInteraction\*.xsd

SET SCHEMADIR=schemas
SET W4=%SCHEMADIR%%SCHEMADIR%\interactions\GodkannUttagInteraction\GodkannUttagInteraction_1.0_rivtabp20.wsdl
SET X4=%SCHEMADIR%\interactions\GodkannUttagInteraction\*.xsd

SET SCHEMADIR=schemas
SET W5=%SCHEMADIR%%SCHEMADIR%\interactions\HamtaAktuellaOrdinationerFoddatumInteraction\HamtaAktuellaOrdinationerFoddatumInteraction_1.0_rivtabp20.wsdl
SET X5=%SCHEMADIR%\interactions\HamtaAktuellaOrdinationerFoddatumInteraction\*.xsd

SET SCHEMADIR=schemas
SET W6=%SCHEMADIR%%SCHEMADIR%\interactions\HamtaAktuellaOrdinationerInteraction\HamtaAktuellaOrdinationerInteraction_1.0_rivtabp20.wsdl
SET X6=%SCHEMADIR%\interactions\HamtaAktuellaOrdinationerInteraction\*.xsd

SET SCHEMADIR=schemas
SET W7=%SCHEMADIR%%SCHEMADIR%\interactions\HamtaEjGodkandaDosunderlagInteraction\HamtaEjGodkandaDosunderlagInteraction_1.0_rivtabp20.wsdl
SET X7=%SCHEMADIR%\interactions\HamtaEjGodkandaDosunderlagInteraction\*.xsd

SET SCHEMADIR=schemas
SET W8=%SCHEMADIR%%SCHEMADIR%\interactions\HamtaGallandeOrdinationsversionInteraction\HamtaGallandeOrdinationsversionInteraction_1.0_rivtabp20.wsdl
SET X8=%SCHEMADIR%\interactions\HamtaGallandeOrdinationsversionInteraction\*.xsd

SET SCHEMADIR=schemas
SET W9=%SCHEMADIR%%SCHEMADIR%\interactions\HamtaIckeAktuellaOrdinationerFoddatumInteraction\HamtaIckeAktuellaOrdinationerFoddatumInteraction_1.0_rivtabp20.wsdl
SET X9=%SCHEMADIR%\interactions\HamtaIckeAktuellaOrdinationerFoddatumInteraction\*.xsd

SET SCHEMADIR=schemas
SET W10=%SCHEMADIR%%SCHEMADIR%\interactions\HamtaIckeAktuellaOrdinationerInteraction\HamtaIckeAktuellaOrdinationerInteraction_1.0_rivtabp20.wsdl
SET X10=%SCHEMADIR%\interactions\HamtaIckeAktuellaOrdinationerInteraction\*.xsd

SET SCHEMADIR=schemas
SET W11=%SCHEMADIR%%SCHEMADIR%\interactions\HamtaOrdinationFoddatumInteraction\HamtaOrdinationFoddatumInteraction_1.0_rivtabp20.wsdl
SET X11=%SCHEMADIR%\interactions\HamtaOrdinationFoddatumInteraction\*.xsd

SET SCHEMADIR=schemas
SET W12=%SCHEMADIR%%SCHEMADIR%\interactions\HamtaOrdinationshistorikInteraction\HamtaOrdinationshistorikInteraction_1.0_rivtabp20.wsdl
SET X12=%SCHEMADIR%\interactions\HamtaOrdinationshistorikInteraction\*.xsd

SET SCHEMADIR=schemas
SET W13=%SCHEMADIR%%SCHEMADIR%\interactions\HamtaProduktionsunderlagInteraction\HamtaProduktionsunderlagInteraction_1.0_rivtabp20.wsdl
SET X13=%SCHEMADIR%\interactions\HamtaProduktionsunderlagInteraction\*.xsd

SET SCHEMADIR=schemas
SET W14=%SCHEMADIR%%SCHEMADIR%\interactions\KontrolleraUttagInteraction\KontrolleraUttagInteraction_1.0_rivtabp20.wsdl
SET X14=%SCHEMADIR%\interactions\KontrolleraUttagInteraction\*.xsd

SET SCHEMADIR=schemas
SET W15=%SCHEMADIR%%SCHEMADIR%\interactions\KorrigeraOrdinationApotekInteraction\KorrigeraOrdinationApotekInteraction_1.0_rivtabp20.wsdl
SET X15=%SCHEMADIR%\interactions\KorrigeraOrdinationApotekInteraction\*.xsd

SET SCHEMADIR=schemas
SET W16=%SCHEMADIR%%SCHEMADIR%\interactions\KorrigeraOrdinationVardInteraction\KorrigeraOrdinationVardInteraction_1.0_rivtabp20.wsdl
SET X16=%SCHEMADIR%\interactions\KorrigeraOrdinationVardInteraction\*.xsd

SET SCHEMADIR=schemas
SET W17=%SCHEMADIR%%SCHEMADIR%\interactions\MakuleraOrdinationApotekInteraction\MakuleraOrdinationApotekInteraction_1.0_rivtabp20.wsdl
SET X17=%SCHEMADIR%\interactions\MakuleraOrdinationApotekInteraction\*.xsd

SET SCHEMADIR=schemas
SET W18=%SCHEMADIR%%SCHEMADIR%\interactions\MakuleraOrdinationVardInteraction\MakuleraOrdinationVardInteraction_1.0_rivtabp20.wsdl
SET X18=%SCHEMADIR%\interactions\MakuleraOrdinationVardInteraction\*.xsd

SET SCHEMADIR=schemas
SET W19=%SCHEMADIR%%SCHEMADIR%\interactions\NotifieringsTestInteraction\NotifieringsTestInteraction_1.0_rivtabp20.wsdl
SET X19=%SCHEMADIR%\interactions\NotifieringsTestInteraction\*.xsd

SET SCHEMADIR=schemas
SET W20=%SCHEMADIR%%SCHEMADIR%\interactions\SattUtOrdinationApotekInteraction\SattUtOrdinationApotekInteraction_1.0_rivtabp20.wsdl
SET X20=%SCHEMADIR%\interactions\SattUtOrdinationApotekInteraction\*.xsd

SET SCHEMADIR=schemas
SET W21=%SCHEMADIR%%SCHEMADIR%\interactions\SattUtOrdinationVardInteraction\SattUtOrdinationVardInteraction_1.0_rivtabp20.wsdl
SET X21=%SCHEMADIR%\interactions\SattUtOrdinationVardInteraction\*.xsd

SET SCHEMADIR=schemas
SET W22=%SCHEMADIR%%SCHEMADIR%\interactions\SkapaDosunderlagApotekInteraction\SkapaDosunderlagApotekInteraction_1.0_rivtabp20.wsdl
SET X22=%SCHEMADIR%\interactions\SkapaDosunderlagApotekInteraction\*.xsd

SET SCHEMADIR=schemas
SET W23=%SCHEMADIR%%SCHEMADIR%\interactions\SkapaDosunderlagVardInteraction\SkapaDosunderlagVardInteraction_1.0_rivtabp20.wsdl
SET X23=%SCHEMADIR%\interactions\SkapaDosunderlagVardInteraction\*.xsd

SET SCHEMADIR=schemas
SET W24=%SCHEMADIR%%SCHEMADIR%\interactions\SkapaOrdinationApotekInteraction\SkapaOrdinationApotekInteraction_1.0_rivtabp20.wsdl
SET X24=%SCHEMADIR%\interactions\SkapaOrdinationApotekInteraction\*.xsd

SET SCHEMADIR=schemas
SET W25=%SCHEMADIR%%SCHEMADIR%\interactions\SkapaOrdinationVardInteraction\SkapaOrdinationVardInteraction_1.0_rivtabp20.wsdl
SET X25=%SCHEMADIR%\interactions\SkapaOrdinationVardInteraction\*.xsd

SET SCHEMADIR=schemas
SET W26=%SCHEMADIR%%SCHEMADIR%\interactions\SkrivUtDosReceptInteraction\SkrivUtDosReceptInteraction_1.0_rivtabp20.wsdl
SET X26=%SCHEMADIR%\interactions\SkrivUtDosReceptInteraction\*.xsd

SET SCHEMADIR=schemas
SET W27=%SCHEMADIR%%SCHEMADIR%\interactions\UppdateraDostillhorighetInteraction\UppdateraDostillhorighetInteraction_1.0_rivtabp20.wsdl
SET X27=%SCHEMADIR%\interactions\UppdateraDostillhorighetInteraction\*.xsd

SET SCHEMADIR=schemas
SET W28=%SCHEMADIR%%SCHEMADIR%\interactions\UppdateraDosunderlagAvlidenInteraction\UppdateraDosunderlagAvlidenInteraction_1.0_rivtabp20.wsdl
SET X28=%SCHEMADIR%\interactions\UppdateraDosunderlagAvlidenInteraction\*.xsd

SET XCORE=%SCHEMADIR%\core_components\*.xsd

SET SCHEMAS=%XCORE% %W0% %X0% %W1% %X1% %W2% %X2% %W3% %X3% %W4% %X4% %W5% %X5% %W6% %X6% %W7% %X7% %W8% %X8% %W9% %X9% %W10% %X10% %W11% %X11% %W12% %X12% %W13% %X13% %W14% %X14% %W15% %X15% %W16% %X16% %W17% %X17% %W18% %X18% %W19% %X19% %W20% %X20% %W21% %X21% %W22% %X22% %W23% %X23% %W24% %X24% %W25% %X25% %W26% %X26% %W27% %X27% %W28% %X28% 

SET OUTFILE=/out:wcf\generated-src\Se_apotekensserviceOrInteractions.cs
SET APPCONFIG=/config:wcf\generated-src\app.config
SET NAMESPACE=/namespace:*,Riv.Se_apotekensservice.Or.Schemas.v1
SET SVCUTIL="svcutil.exe"
SET XCORE=%SCHEMADIR%\core_components\*.xsd
%SVCUTIL% /language:cs %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS%

CD wcf
ECHO Generating Service contract .Net Binding interfaces and classes for se_apotekensservice:or Release 1
ECHO I DotNetprojektet ska du ta lagga till referens till System.ServiceModel
