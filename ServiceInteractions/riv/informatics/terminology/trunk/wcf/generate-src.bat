@REM ---------------------------------------------------------------------------------
@REM Generates c# WCF service contracts (interface), client proxies and wcf config 
@REM file for informatics terminology WSDLs + XML Schemas, using .Net WCF tool svcutil.exe
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
SET OUTFILE=/out:wcf\generated-src\InformaticsTerminologyServicesInteractions.cs
SET APPCONFIG=/config:wcf\generated-src\app.config
SET NAMESPACE=/namespace:*,Riv.InformaticsTerminology.Services.Schemas.v1
SET SCHEMADIR=schemas\interactions

SET W1=%SCHEMADIR%\GetTerminologySubsetInteraction\GetTerminologySubsetInteraction_1.0_rivtabp20.wsdl 
SET X1=%SCHEMADIR%\GetTerminologySubsetInteraction\*.xsd 

SET W2=%SCHEMADIR%\GetTerminologySubsetVersionInteraction\GetTerminologySubsetVersionInteraction_1.0_rivtabp20.wsdl 
SET X2=%SCHEMADIR%\GetTerminologySubsetVersionInteraction\*.xsd 

SET X3=schemas\core_components\*.xsd

ECHO %W1%

SET SCHEMAS=%W1% %W2% %X1% %X2% %X3%

svcutil.exe /language:cs /wrapped %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS%

CD wcf

ECHO I DotNetprojektet ska du ta lagga till referens till System.ServiceModel