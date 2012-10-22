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
SET OUTFILE=/out:generated-src\EhrBlockingInteractions.cs
SET APPCONFIG=/config:generated-src\app.config
SET NAMESPACE=/namespace:*,Riv.Ehr.Blocking.Schemas.v2
SET SCHEMADIR="..\schemas"
SET BLOCKINGDIR="%SCHEMADIR%\interactions"
SET SVCUTIL="svcutil.exe"

SET W1=%BLOCKINGDIR%\accesscontrol\CheckBlocksInteraction\CheckBlocksInteraction_2.0_RIVTABP21.wsdl
SET X1=%BLOCKINGDIR%\accesscontrol\CheckBlocksInteraction\*.xsd

SET W2=%BLOCKINGDIR%\administration\CancelTemporaryExtendedRevokeInteraction\CancelTemporaryExtendedRevokeInteraction_2.0_RIVTABP21.wsdl
SET X2=%BLOCKINGDIR%\administration\CancelTemporaryExtendedRevokeInteraction\*.xsd

SET W3=%BLOCKINGDIR%\administration\DeleteExtendedBlockInteraction\DeleteExtendedBlockInteraction_2.0_RIVTABP21.wsdl
SET X3=%BLOCKINGDIR%\administration\DeleteExtendedBlockInteraction\*.xsd

SET W4=%BLOCKINGDIR%\administration\GetExtendedBlocksForPatientInteraction\GetExtendedBlocksForPatientInteraction_2.0_RIVTABP21.wsdl
SET X4=%BLOCKINGDIR%\administration\GetExtendedBlocksForPatientInteraction\*.xsd

SET W6=%BLOCKINGDIR%\administration\RegisterExtendedBlockInteraction\RegisterExtendedBlockInteraction_2.0_RIVTABP21.wsdl
SET X6=%BLOCKINGDIR%\administration\RegisterExtendedBlockInteraction\*.xsd

SET W7=%BLOCKINGDIR%\administration\RegisterTemporaryExtendedRevokeInteraction\RegisterTemporaryExtendedRevokeInteraction_2.0_RIVTABP21.wsdl
SET X7=%BLOCKINGDIR%\administration\RegisterTemporaryExtendedRevokeInteraction\*.xsd

SET W8=%BLOCKINGDIR%\administration\RevokeExtendedBlockInteraction\RevokeExtendedBlockInteraction_2.0_RIVTABP21.wsdl
SET X8=%BLOCKINGDIR%\administration\RevokeExtendedBlockInteraction\*.xsd

SET W9=%BLOCKINGDIR%\querying\GetAllBlocksInteraction\GetAllBlocksInteraction_2.0_RIVTABP21.wsdl
SET X9=%BLOCKINGDIR%\querying\GetAllBlocksInteraction\*.xsd

SET W10=%BLOCKINGDIR%\querying\GetAllBlocksForPatientInteraction\GetAllBlocksForPatientInteraction_2.0_RIVTABP21.wsdl
SET X10=%BLOCKINGDIR%\querying\GetAllBlocksForPatientInteraction\*.xsd

SET W11=%BLOCKINGDIR%\administration\GetPatientIdsInteraction\GetPatientIdsInteraction_2.0_RIVTABP21.wsdl
SET X11=%BLOCKINGDIR%\administration\GetPatientIdsInteraction\*.xsd

SET W12=%BLOCKINGDIR%\synchronization\RegisterBlockInteraction\RegisterBlockInteraction_2.0_RIVTABP21.wsdl
SET X12=%BLOCKINGDIR%\synchronization\RegisterBlockInteraction\*.xsd

SET W13=%BLOCKINGDIR%\synchronization\RegisterTemporaryRevokeInteraction\RegisterTemporaryRevokeInteraction_2.0_RIVTABP21.wsdl
SET X13=%BLOCKINGDIR%\synchronization\RegisterTemporaryRevokeInteraction\*.xsd

SET W14=%BLOCKINGDIR%\synchronization\UnregisterBlockInteraction\UnregisterBlockInteraction_2.0_RIVTABP21.wsdl
SET X14=%BLOCKINGDIR%\synchronization\UnregisterBlockInteraction\*.xsd

SET W15=%BLOCKINGDIR%\synchronization\UnregisterTemporaryRevokeInteraction\UnregisterTemporaryRevokeInteraction_2.0_RIVTABP21.wsdl
SET X15=%BLOCKINGDIR%\synchronization\UnregisterTemporaryRevokeInteraction\*.xsd

SET W16=%BLOCKINGDIR%\querying\GetBlocksInteraction\GetBlocksInteraction_2.0_RIVTABP21.wsdl
SET X16=%BLOCKINGDIR%\querying\GetBlocksInteraction\*.xsd

SET W17=%BLOCKINGDIR%\querying\GetBlocksForPatientInteraction\GetBlocksForPatientInteraction_2.0_RIVTABP21.wsdl
SET X17=%BLOCKINGDIR%\querying\GetBlocksForPatientInteraction\*.xsd

SET X20=%SCHEMADIR%\core_components\*.xsd

SET X21=%BLOCKINGDIR%\administration\*.xsd

SET SCHEMAS=%W1% %X1% %W2% %X2% %W3% %X3% %W4% %X4% %W6% %X6% %W7% %X7% %W8% %X8% %W9% %X9% %W10% %X10% %W11% %X11% %W12% %X12% %W13% %X13% %W14% %X14% %W15% %X15% %W16% %X16% %W17% %X17% %X20% %X21%

%SVCUTIL% /language:cs /wrapped %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS%

CD wcf

@ECHO I DotNet-projektet ska du ta lagga till referens till System.ServiceModel