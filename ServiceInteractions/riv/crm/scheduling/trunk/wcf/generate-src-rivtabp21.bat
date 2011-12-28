
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
	
	SET W0=%SCHEMADIR%\.\interactions\CancelBookingInteraction\CancelBookingInteraction_1.1_RIVTABP21.wsdl
SET X0=%SCHEMADIR%\interactions\CancelBookingInteraction\*.xsd

SET W1=%SCHEMADIR%\.\interactions\GetAllCareTypesInteraction\GetAllCareTypesInteraction_1.1_RIVTABP21.wsdl
SET X1=%SCHEMADIR%\interactions\GetAllCareTypesInteraction\*.xsd

SET W2=%SCHEMADIR%\.\interactions\GetAllHealthcareFacilitiesInteraction\GetAllHealthcareFacilitiesInteraction_1.1_RIVTABP21.wsdl
SET X2=%SCHEMADIR%\interactions\GetAllHealthcareFacilitiesInteraction\*.xsd

SET W3=%SCHEMADIR%\.\interactions\GetAllPerformersInteraction\GetAllPerformersInteraction_1.1_RIVTABP21.wsdl
SET X3=%SCHEMADIR%\interactions\GetAllPerformersInteraction\*.xsd

SET W4=%SCHEMADIR%\.\interactions\GetAllTimeTypesInteraction\GetAllTimeTypesInteraction_1.1_RIVTABP21.wsdl
SET X4=%SCHEMADIR%\interactions\GetAllTimeTypesInteraction\*.xsd

SET W5=%SCHEMADIR%\.\interactions\GetAvailableDatesInteraction\GetAvailableDatesInteraction_1.1_RIVTABP21.wsdl
SET X5=%SCHEMADIR%\interactions\GetAvailableDatesInteraction\*.xsd

SET W6=%SCHEMADIR%\.\interactions\GetAvailableTimeslotsInteraction\GetAvailableTimeslotsInteraction_1.1_RIVTABP21.wsdl
SET X6=%SCHEMADIR%\interactions\GetAvailableTimeslotsInteraction\*.xsd

SET W7=%SCHEMADIR%\.\interactions\GetBookingDetailsInteraction\GetBookingDetailsInteraction_1.1_RIVTABP21.wsdl
SET X7=%SCHEMADIR%\interactions\GetBookingDetailsInteraction\*.xsd

SET W8=%SCHEMADIR%\.\interactions\GetSubjectOfCareScheduleInteraction\GetSubjectOfCareScheduleInteraction_1.1_RIVTABP21.wsdl
SET X8=%SCHEMADIR%\interactions\GetSubjectOfCareScheduleInteraction\*.xsd

SET W9=%SCHEMADIR%\.\interactions\MakeBookingInteraction\MakeBookingInteraction_1.1_RIVTABP21.wsdl
SET X9=%SCHEMADIR%\interactions\MakeBookingInteraction\*.xsd

SET W10=%SCHEMADIR%\.\interactions\UpdateBookingInteraction\UpdateBookingInteraction_1.1_RIVTABP21.wsdl
SET X10=%SCHEMADIR%\interactions\UpdateBookingInteraction\*.xsd

SET XCORE=%SCHEMADIR%\core_components\*.xsd

SET SCHEMAS=%XCORE% %W0% %X0% %W1% %X1% %W2% %X2% %W3% %X3% %W4% %X4% %W5% %X5% %W6% %X6% %W7% %X7% %W8% %X8% %W9% %X9% %W10% %X10% 

SET OUTFILE=/out:wcf\generated-src\EhrSchedulingInteractions.cs
SET APPCONFIG=/config:wcf\generated-src\app.config
SET NAMESPACE=/namespace:*,Riv.Ehr.Scheduling.Schemas.v1
SET SVCUTIL="svcutil.exe"
%SVCUTIL% /language:cs %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS%

CD wcf
ECHO Generating Service contract .Net Binding interfaces and classes for ehr:scheduling Release 1
ECHO I DotNetprojektet ska du ta lagga till referens till System.ServiceModel
