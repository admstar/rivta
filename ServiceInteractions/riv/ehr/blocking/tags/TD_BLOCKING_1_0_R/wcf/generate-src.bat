@REM ---------------------------------------------------------------------------------
@REM Generates c# WCF service contracts (interface), client proxies and wcf config 
@REM file for all ehr:blocking WSDLs + XML Schemas, using .Net WCF tool svcuti.exe
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
SET OUTFILE=/out:wcf\generated-src\EhrBlockingInteractions.cs
SET APPCONFIG=/config:wcf\generated-src\app.config
SET NAMESPACE=/namespace:*,Riv.Ehr.Blocking.Schemas.v1
SET SCHEMADIR="schemas"
SET SVCUTIL="svcutil.exe"

SET W1=%SCHEMADIR%\accesscontrol\CheckBlocksInteraction\CheckBlocksInteraction_1.0_RIVTABP20.wsdl
SET X1=%SCHEMADIR%\accesscontrol\CheckBlocksInteraction\*.xsd

SET W2=%SCHEMADIR%\administration\CancelTemporaryExtendedRevokeInteraction\CancelTemporaryExtendedRevokeInteraction_1.0_RIVTABP20.wsdl
SET X2=%SCHEMADIR%\administration\CancelTemporaryExtendedRevokeInteraction\*.xsd

SET W3=%SCHEMADIR%\administration\DeleteExtendedBlockInteraction\DeleteExtendedBlockInteraction_1.0_RIVTABP20.wsdl
SET X3=%SCHEMADIR%\administration\DeleteExtendedBlockInteraction\*.xsd

SET W4=%SCHEMADIR%\administration\GetExtendedBlocksForPatientInteraction\GetExtendedBlocksForPatientInteraction_1.0_RIVTABP20.wsdl
SET X4=%SCHEMADIR%\administration\GetExtendedBlocksForPatientInteraction\*.xsd

SET W6=%SCHEMADIR%\administration\RegisterExtendedBlockInteraction\RegisterExtendedBlockInteraction_1.0_RIVTABP20.wsdl
SET X6=%SCHEMADIR%\administration\RegisterExtendedBlockInteraction\*.xsd

SET W7=%SCHEMADIR%\administration\RegisterTemporaryExtendedRevokeInteraction\RegisterTemporaryExtendedRevokeInteraction_1.0_RIVTABP20.wsdl
SET X7=%SCHEMADIR%\administration\RegisterTemporaryExtendedRevokeInteraction\*.xsd

SET W8=%SCHEMADIR%\administration\RevokeExtendedBlockInteraction\RevokeExtendedBlockInteraction_1.0_RIVTABP20.wsdl
SET X8=%SCHEMADIR%\administration\RevokeExtendedBlockInteraction\*.xsd

SET W9=%SCHEMADIR%\querying\GetAllBlocksInteraction\GetAllBlocksInteraction_1.0_RIVTABP20.wsdl
SET X9=%SCHEMADIR%\querying\GetAllBlocksInteraction\*.xsd

SET W10=%SCHEMADIR%\querying\GetBlocksForPatientInteraction\GetBlocksForPatientInteraction_1.0_RIVTABP20.wsdl
SET X10=%SCHEMADIR%\querying\GetBlocksForPatientInteraction\*.xsd

SET W11=%SCHEMADIR%\synchronization\RegisterBlockInteraction\RegisterBlockInteraction_1.0_RIVTABP20.wsdl
SET X11=%SCHEMADIR%\synchronization\RegisterBlockInteraction\*.xsd

SET W12=%SCHEMADIR%\synchronization\RegisterTemporaryRevokeInteraction\RegisterTemporaryRevokeInteraction_1.0_RIVTABP20.wsdl
SET X12=%SCHEMADIR%\synchronization\RegisterTemporaryRevokeInteraction\*.xsd

SET W13=%SCHEMADIR%\synchronization\UnregisterBlockInteraction\UnregisterBlockInteraction_1.0_RIVTABP20.wsdl
SET X13=%SCHEMADIR%\synchronization\UnregisterBlockInteraction\*.xsd

SET W14=%SCHEMADIR%\synchronization\UnregisterTemporaryRevokeInteraction\UnregisterTemporaryRevokeInteraction_1.0_RIVTABP20.wsdl
SET X14=%SCHEMADIR%\synchronization\UnregisterTemporaryRevokeInteraction\*.xsd

SET X15=%SCHEMADIR%\common\*.xsd

SET SCHEMAS=%W1% %X1% %W2% %X2% %W3% %X3% %W4% %X4% %W6% %X6% %W7% %X7% %W8% %X8% %W9% %X9% %W10% %X10% %W11% %X11% %W12% %X12% %W13% %X13% %W14% %X14% %X15%

%SVCUTIL% /language:cs /wrapped %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS%

CD wcf

@ECHO I DotNet-projektet ska du ta lagga till referens till System.ServiceModel