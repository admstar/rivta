@REM ---------------------------------------------------------------------------------
@REM Generates c# WCF service contracts (interface), client proxies and wcf config 
@REM file for crm:scheduling 1.0 WSDLs + XML Schemas, using .Net WCF tool svcuti.exe
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
SET OUTFILE=/out:wcf\generated-src\CrmSchedulingInteractions.cs
SET APPCONFIG=/config:wcf\generated-src\app.config
SET NAMESPACE=/namespace:*,Riv.Crm.Scheduling.Schemas.v1
SET SCHEMADIR="schemas\interactions"
SET SVCUTIL="svcutil.exe"

SET W1=%SCHEMADIR%\CancelBookingInteraction\CancelBookingInteraction_1.1_rivtabp20.wsdl
SET X1=%SCHEMADIR%\CancelBookingInteraction\*.xsd

SET W2=%SCHEMADIR%\GetAllTimeTypesInteraction\GetAllTimeTypesInteraction_1.1_rivtabp20.wsdl
SET X2=%SCHEMADIR%\GetAllTimeTypesInteraction\*.xsd

SET W3=%SCHEMADIR%\GetAvailableDatesInteraction\GetAvailableDatesInteraction_1.1_rivtabp20.wsdl
SET X3=%SCHEMADIR%\GetAvailableDatesInteraction\*.xsd

SET W4=%SCHEMADIR%\GetAvailableTimeslotsInteraction\GetAvailableTimeslotsInteraction_1.1_rivtabp20.wsdl
SET X4=%SCHEMADIR%\GetAvailableTimeslotsInteraction\*.xsd

SET W5=%SCHEMADIR%\GetBookingDetailsInteraction\GetBookingDetailsInteraction_1.1_rivtabp20.wsdl
SET X5=%SCHEMADIR%\GetBookingDetailsInteraction\*.xsd

SET W6=%SCHEMADIR%\GetCancelledAndRebookedInteraction\GetCancelledAndRebookedInteraction_1.1_rivtabp20.wsdl
SET X6=%SCHEMADIR%\GetCancelledAndRebookedInteraction\*.xsd

SET W7=%SCHEMADIR%\GetSubjectOfCareScheduleInteraction\GetSubjectOfCareScheduleInteraction_1.1_rivtabp20.wsdl
SET X7=%SCHEMADIR%\GetSubjectOfCareScheduleInteraction\*.xsd

SET W8=%SCHEMADIR%\MakeBookingInteraction\MakeBookingInteraction_1.1_rivtabp20.wsdl
SET X8=%SCHEMADIR%\MakeBookingInteraction\*.xsd

SET W9=%SCHEMADIR%\UpdateBookingInteraction\UpdateBookingInteraction_1.1_rivtabp20.wsdl
SET X9=%SCHEMADIR%\UpdateBookingInteraction\*.xsd 

SET W10=%SCHEMADIR%\GetAllCareTypesInteraction\GetAllCareTypesInteraction_1.1_rivtabp20.wsdl
SET X10=%SCHEMADIR%\GetAllCareTypesInteraction\*.xsd

SET W11=%SCHEMADIR%\GetAllHealthcareFacilitiesInteraction\GetAllHealthcareFacilitiesInteraction_1.1_rivtabp20.wsdl
SET X11=%SCHEMADIR%\GetAllHealthcareFacilitiesInteraction\*.xsd

SET W12=%SCHEMADIR%\GetAllPerformersInteraction\GetAllPerformersInteraction_1.1_rivtabp20.wsdl
SET X12=%SCHEMADIR%\GetAllPerformersInteraction\*.xsd

SET X13=schemas\core_components\*.xsd

SET SCHEMAS=%W1% %X1% %W2% %X2% %W3% %X3% %W4% %X4% %W5% %X5% %W6% %X6% %W7% %X7% %W8% %X8% %W9% %X9% %W10% %X10% %W11% %X11% %W12% %X12% %X13%

%SVCUTIL% /language:cs %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS%

CD wcf
ECHO Generating Service contract .Net Binding interfaces and classes for crm:scheduling Release 1.1
ECHO The following Service Contracts are new for 1.1: GetAllCareTypes, GetAllHealthcareFacilities, GetAllPerformers
ECHO The following Service Contracts have been updated with maintained producer backwards compatibility: GetAllTimeTypes, MakeBooking
ECHO The following Service Contracts have not changed since 1.0: CancelBooking, GetAvailableDates, GetAvailableTimeslots, GetBookingDetails, GetCancelledAndRebooked, GetSubjectOfCareSchedule, UpdateBooking
ECHO I DotNetprojektet ska du ta lagga till referens till System.ServiceModel