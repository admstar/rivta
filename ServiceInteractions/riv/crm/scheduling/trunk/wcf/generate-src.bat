@REM ---------------------------------------------------------------------------------
@REM Generates c# WCF service contracts (interface), client proxies and wcf config 
@REM file for crm:scheduling 1.0 WSDLs + XML Schemas, using .Net WCF tool svcuti.exe
@REM ---------------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements. See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership. The ASF licenses this file
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
svcutil.exe /language:cs /wrapped /out:generated-src\CrmSchedulingInteractions.cs /config:generated-src\app.config /namespace:*,CrmScheduling.Schemas.v1 ..\schemas\interactions\CancelBookingInteraction\CancelBookingInteraction_1.0_rivtabp20.wsdl ..\schemas\interactions\CancelBookingInteraction\*.xsd ..\schemas\interactions\GetAllTimeTypesInteraction\GetAllTimeTypesInteraction_1.0_rivtabp20.wsdl ..\schemas\interactions\GetAllTimeTypesInteraction\*.xsd ..\schemas\interactions\GetAvailableDatesInteraction\GetAvailableDatesInteraction_1.0_rivtabp20.wsdl ..\schemas\interactions\GetAvailableDatesInteraction\*.xsd ..\schemas\interactions\GetAvailableTimeslotsInteraction\GetAvailableTimeslotsInteraction_1.0_rivtabp20.wsdl ..\schemas\interactions\GetAvailableTimeslotsInteraction\*.xsd ..\schemas\interactions\GetBookingDetailsInteraction\GetBookingDetailsInteraction_1.0_rivtabp20.wsdl ..\schemas\interactions\GetBookingDetailsInteraction\*.xsd ..\schemas\interactions\GetCancelledAndRebookedInteraction\GetCancelledAndRebookedInteraction_1.0_rivtabp20.wsdl ..\schemas\interactions\GetCancelledAndRebookedInteraction\*.xsd ..\schemas\interactions\GetSubjectOfCareScheduleInteraction\GetSubjectOfCareScheduleInteraction_1.0_rivtabp20.wsdl ..\schemas\interactions\GetSubjectOfCareScheduleInteraction\*.xsd ..\schemas\interactions\MakeBookingInteraction\MakeBookingInteraction_1.0_rivtabp20.wsdl ..\schemas\interactions\MakeBookingInteraction\*.xsd ..\schemas\interactions\UpdateBookingInteraction\UpdateBookingInteraction_1.0_rivtabp20.wsdl ..\schemas\interactions\UpdateBookingInteraction\*.xsd ..\schemas\core_components\*.xsd
