
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

SET OUTFILE=/out:generated-src\EhrPatientrelationshipInteractions.cs
SET APPCONFIG=/config:generated-src\app.config
SET NAMESPACE=/namespace:*,Riv.Ehr.Patientrelationship.Schemas.v1
SET SCHEMADIR="..\..\schemas"
SET SVCUTIL="svcutil.exe"
	
SET W1=%SCHEMADIR%\interactions\administration\CancelExtendedPatientRelationInteraction\CancelExtendedPatientRelationInteraction_1.0_RIVTABP21.wsdl
SET X1=%SCHEMADIR%\interactions\administration\CancelExtendedPatientRelationInteraction\*.xsd

SET W2=%SCHEMADIR%\interactions\administration\DeleteExtendedPatientRelationInteraction\DeleteExtendedPatientRelationInteraction_1.0_RIVTABP21.wsdl
SET X2=%SCHEMADIR%\interactions\administration\DeleteExtendedPatientRelationInteraction\*.xsd

SET W3=%SCHEMADIR%\interactions\administration\GetExtendedPatientRelationsForPatientInteraction\GetExtendedPatientRelationsForPatientInteraction_1.0_RIVTABP21.wsdl
SET X3=%SCHEMADIR%\interactions\administration\GetExtendedPatientRelationsForPatientInteraction\*.xsd

SET W4=%SCHEMADIR%\interactions\administration\RegisterExtendedPatientRelationInteraction/RegisterExtendedPatientRelationInteraction_1.0_RIVTABP21.wsdl
SET X4=%SCHEMADIR%\interactions\administration\RegisterExtendedPatientRelationInteraction\*.xsd

SET W5=%SCHEMADIR%\interactions\querying/GetPatientRelationsForCareProviderInteraction/GetPatientRelationsForCareProviderInteraction_1.0_RIVTABP21.wsdl
SET X5=%SCHEMADIR%\interactions\querying\GetPatientRelationsForCareProviderInteraction\*.xsd

SET W6=%SCHEMADIR%\interactions\querying/GetPatientRelationsForPatientInteraction/GetPatientRelationsForPatientInteraction_1.0_RIVTABP21.wsdl
SET X6=%SCHEMADIR%\interactions\querying\GetPatientRelationsForPatientInteraction\*.xsd

SET W7=%SCHEMADIR%\interactions\accesscontrol\CheckPatientRelationInteraction\CheckPatientRelationInteraction_1.0_RIVTABP21.wsdl
SET X7=%SCHEMADIR%\interactions\accesscontrol\CheckPatientRelationInteraction\*.xsd

SET XCORE=%SCHEMADIR%\core_components\*.xsd
SET SCHEMAS=%XCORE% %W1% %X1% %W2% %X2% %W3% %X3% %W4% %X4% %W5% %X5% %W6% %X6% %W7% %X7% 

%SVCUTIL% /language:cs /wrapped %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS%


@ECHO Generating Service contract .Net Binding interfaces and classes for ehr:patientsummary Release 1
@ECHO I DotNetprojektet ska du ta lagga till referens till System.ServiceModel
