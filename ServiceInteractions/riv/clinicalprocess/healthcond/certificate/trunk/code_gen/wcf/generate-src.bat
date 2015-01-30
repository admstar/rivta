@REM ---------------------------------------------------------------------------------
@REM Generates c# WCF service contracts (interface), client proxies and wcf config 
@REM file for clinicalprocess:healthcond:certificate WSDLs + XML Schemas, using .Net WCF tool svcutil.exe
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

CD ../..
SET OUTFILE=/out:wcf\generated-src\ClinicalprocessHealthcondCertificateInteractions.cs
SET APPCONFIG=/config:wcf\generated-src\app.config
SET NAMESPACE=/namespace:*,Riv.Clinicalprocess.Healthcond.Certificate.Schemas.v1
SET SCHEMADIR="schemas\interactions"
SET SVCUTIL="svcutil.exe"

SET W1=%SCHEMADIR%\GetCertificateForCareInteraction\GetCertificateForCareInteraction_0.9_RIVTABP21.wsdl
SET X1=%SCHEMADIR%\GetCertificateForCareInteraction\*.xsd

SET W2=%SCHEMADIR%\ListCertificatesForCareInteraction\ListCertificatesForCareInteraction_0.9_RIVTABP21.wsdl
SET X2=%SCHEMADIR%\ListCertificatesForCareInteraction\*.xsd

SET W3=%SCHEMADIR%\ListCertificatesForCitizenInteraction\ListCertificatesForCitizenInteraction_0.9_RIVTABP21.wsdl
SET X3=%SCHEMADIR%\ListCertificatesForCitizenInteraction\*.xsd

SET W4=%SCHEMADIR%\RegisterCertificateInteraction\RegisterCertificateInteraction_0.9_RIVTABP21.wsdl
SET X4=%SCHEMADIR%\RegisterCertificateInteraction\*.xsd

SET W5=%SCHEMADIR%\ListCertificatesForCareWithQAInteraction\ListCertificatesForCareWithQAInteraction_0.9_RIVTABP21.wsdl
SET X5=%SCHEMADIR%\ListCertificatesForCareWithQAInteraction\*.xsd

SET W6=%SCHEMADIR%\CertificateStatusUpdateForCareInteraction\CertificateStatusUpdateForCareInteraction_0.9_RIVTABP21.wsdl
SET X6=%SCHEMADIR%\CertificateStatusUpdateForCareInteraction\*.xsd

SET W7=%SCHEMADIR%\CreateDraftCertificateInteraction\CreateDraftCertificateInteraction_0.9_RIVTABP21.wsdl
SET X7=%SCHEMADIR%\CreateDraftCertificateInteraction\*.xsd

SET X15=schemas\core_components\*.xsd

SET SCHEMAS=%W1% %X1% %W2% %X2% %W3% %X3% %W4% %X4% %W5% %X5% %W6% %X6%  %W7% %X7% %X15%

%SVCUTIL% /language:cs /wrapped %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS%

CD wcf

ECHO I DotNetprojektet ska du ta lagga till referens till System.ServiceModel