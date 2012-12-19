@REM ---------------------------------------------------------------------------------
@REM Generates c# WCF service contracts (interface), client proxies and wcf config 
@REM file for clinicalprocess:activityrequest WSDLs + XML Schemas, using .Net WCF tool svcutil.exe
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
SET OUTFILE=/out:wcf\generated-src\ClinicalProcessActivityRequestInteraction.cs
SET APPCONFIG=/config:wcf\generated-src\app.config
SET NAMESPACE=/namespace:*,Riv.ClinicalProcess.ActivityRequest.Schemas.v1
SET SCHEMADIR=..\trunk\schemas\interactions

SET W1=%SCHEMADIR%\GetRequestInstructionInteraction\GetRequestInstructionInteraction_1.0_RIVTABP21.wsdl 
SET X1=%SCHEMADIR%\GetRequestInstructionInteraction\*.xsd 

SET W2=%SCHEMADIR%\GetRequestStatusInteraction\GetRequestStatusInteraction_1.0_RIVTABP21.wsdl 
SET X2=%SCHEMADIR%\GetRequestStatusInteraction\*.xsd 

SET W3=%SCHEMADIR%\ProcessRequestConfirmationInteraction\ProcessRequestConfirmationInteraction_1.0_RIVTABP21.wsdl 
SET X3=%SCHEMADIR%\ProcessRequestConfirmationInteraction\*.xsd 

SET W4=%SCHEMADIR%\ProcessRequestInteraction\ProcessRequestInteraction_1.0_RIVTABP21.wsdl 
SET X4=%SCHEMADIR%\ProcessRequestInteraction\*.xsd 

SET W5=%SCHEMADIR%\ProcessRequestOutcomeInteraction\ProcessRequestOutcomeInteraction_1.0_RIVTABP21.wsdl 
SET X5=%SCHEMADIR%\ProcessRequestOutcomeInteraction\*.xsd 


SET X6=..\trunk\schemas\core_components\*.xsd

SET SCHEMAS=%W1% %X1% %W2% %X2% %W3% %X3% %W4% %X4% %W5% %X5% %W6% %X6% 

svcutil.exe /language:cs /wrapped %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS%

CD wcf

ECHO I DotNetprojektet ska du ta lagga till referens till System.ServiceModel