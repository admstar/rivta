@REM ---------------------------------------------------------------------------------
@REM Generates c# WCF service contracts (interface), client proxies and wcf config 
@REM file for insuranceprocess:helathreporting WSDLs + XML Schemas, using .Net WCF tool svcutil.exe
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

CD ..
SET OUTFILE=/out:wcf\generated-src\InfektionsverktygetRegistrationServicesInteractions.cs
SET APPCONFIG=/config:wcf\generated-src\app.config
SET NAMESPACE=/namespace:*,Riv.Infektionsverktyget.RegistrationServices.Schemas.v1
SET SCHEMADIR=schemas\interactions

SET W1=%SCHEMADIR%\DeleteActivityInteraction\DeleteActivityInteraction_1.0_rivtabp20.wsdl 
SET X1=%SCHEMADIR%\DeleteActivityInteraction\*.xsd 

SET W2=%SCHEMADIR%\DeleteCareEncounterInteraction\DeleteCareEncounterInteraction_1.0_rivtabp20.wsdl 
SET X2=%SCHEMADIR%\DeleteCareEncounterInteraction\*.xsd 

SET W3=%SCHEMADIR%\DeleteConditionInteraction\DeleteConditionInteraction_1.0_rivtabp20.wsdl 
SET X3=%SCHEMADIR%\DeleteConditionInteraction\*.xsd 

SET W4=%SCHEMADIR%\DeleteLaboratoryReportInteraction\DeleteLaboratoryReportInteraction_1.0_rivtabp20.wsdl 
SET X4=%SCHEMADIR%\DeleteLaboratoryReportInteraction\*.xsd 

SET W5=%SCHEMADIR%\DeletePrescriptionReasonInteraction\DeletePrescriptionReasonInteraction_1.0_rivtabp20.wsdl 
SET X5=%SCHEMADIR%\DeletePrescriptionReasonInteraction\*.xsd 

SET W6=%SCHEMADIR%\ProcessActivityInteraction\ProcessActivityInteraction_1.0_rivtabp20.wsdl 
SET X6=%SCHEMADIR%\ProcessActivityInteraction\*.xsd 

SET W7=%SCHEMADIR%\ProcessCareEncounterInteraction\ProcessCareEncounterInteraction_1.0_rivtabp20.wsdl 
SET X7=%SCHEMADIR%\ProcessCareEncounterInteraction\*.xsd 

SET W8=%SCHEMADIR%\ProcessConditionInteraction\ProcessConditionInteraction_1.0_rivtabp20.wsdl 
SET X8=%SCHEMADIR%\ProcessConditionInteraction\*.xsd 

SET W9=%SCHEMADIR%\ProcessLaboratoryReportInteraction\ProcessLaboratoryReportInteraction_1.0_rivtabp20.wsdl 
SET X9=%SCHEMADIR%\ProcessLaboratoryReportInteraction\*.xsd 

SET W10=%SCHEMADIR%\ProcessPrescriptionReasonInteraction\ProcessPrescriptionReasonInteraction_1.0_rivtabp20.wsdl 
SET X10=%SCHEMADIR%\ProcessPrescriptionReasonInteraction\*.xsd 

SET X11=schemas\core_components\*.xsd

ECHO %W1%

SET SCHEMAS=%W1% %X1% %W2% %X2% %W3% %X3% %W4% %X4% %W5% %X5% %W6% %X6% %W7% %X7% %W8% %X8% %W9% %X9% %W10% %X10% %X11%

svcutil.exe /language:cs /wrapped %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS%

CD wcf

ECHO I DotNetprojektet ska du ta lagga till referens till System.ServiceModel