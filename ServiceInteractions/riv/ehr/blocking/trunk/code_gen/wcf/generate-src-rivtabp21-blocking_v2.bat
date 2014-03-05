
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
	
	SET SCHEMADIR=..\schemas
	
	SET W0=%SCHEMADIR%\interactions\administration\CancelTemporaryExtendedRevokeInteraction\CancelTemporaryExtendedRevokeInteraction_2.0_RIVTABP21.wsdl
SET X0=%SCHEMADIR%\interactions\administration\CancelTemporaryExtendedRevokeInteraction\*.xsd

SET W1=%SCHEMADIR%\interactions\administration\DeleteExtendedBlockInteraction\DeleteExtendedBlockInteraction_2.0_RIVTABP21.wsdl
SET X1=%SCHEMADIR%\interactions\administration\DeleteExtendedBlockInteraction\*.xsd

SET W2=%SCHEMADIR%\interactions\administration\GetExtendedBlocksForPatientInteraction\GetExtendedBlocksForPatientInteraction_2.0_RIVTABP21.wsdl
SET X2=%SCHEMADIR%\interactions\administration\GetExtendedBlocksForPatientInteraction\*.xsd

SET W3=%SCHEMADIR%\interactions\administration\GetPatientIdsInteraction\GetPatientIdsInteraction_2.0_RIVTABP21.wsdl
SET X3=%SCHEMADIR%\interactions\administration\GetPatientIdsInteraction\*.xsd

SET W4=%SCHEMADIR%\interactions\administration\RegisterExtendedBlockInteraction\RegisterExtendedBlockInteraction_2.0_RIVTABP21.wsdl
SET X4=%SCHEMADIR%\interactions\administration\RegisterExtendedBlockInteraction\*.xsd

SET W5=%SCHEMADIR%\interactions\administration\RegisterTemporaryExtendedRevokeInteraction\RegisterTemporaryExtendedRevokeInteraction_2.0_RIVTABP21.wsdl
SET X5=%SCHEMADIR%\interactions\administration\RegisterTemporaryExtendedRevokeInteraction\*.xsd

SET W6=%SCHEMADIR%\interactions\administration\RevokeExtendedBlockInteraction\RevokeExtendedBlockInteraction_2.0_RIVTABP21.wsdl
SET X6=%SCHEMADIR%\interactions\administration\RevokeExtendedBlockInteraction\*.xsd

SET W7=%SCHEMADIR%\interactions\querying\GetAllBlocksForPatientInteraction\GetAllBlocksForPatientInteraction_2.0_RIVTABP21.wsdl
SET X7=%SCHEMADIR%\interactions\querying\GetAllBlocksForPatientInteraction\*.xsd

SET W8=%SCHEMADIR%\interactions\querying\GetAllBlocksInteraction\GetAllBlocksInteraction_2.0_RIVTABP21.wsdl
SET X8=%SCHEMADIR%\interactions\querying\GetAllBlocksInteraction\*.xsd

SET W9=%SCHEMADIR%\interactions\querying\GetBlocksForPatientInteraction\GetBlocksForPatientInteraction_2.0_RIVTABP21.wsdl
SET X9=%SCHEMADIR%\interactions\querying\GetBlocksForPatientInteraction\*.xsd

SET W10=%SCHEMADIR%\interactions\querying\GetBlocksInteraction\GetBlocksInteraction_2.0_RIVTABP21.wsdl
SET X10=%SCHEMADIR%\interactions\querying\GetBlocksInteraction\*.xsd

SET W11=%SCHEMADIR%\interactions\synchronization\RegisterBlockInteraction\RegisterBlockInteraction_2.0_RIVTABP21.wsdl
SET X11=%SCHEMADIR%\interactions\synchronization\RegisterBlockInteraction\*.xsd

SET W12=%SCHEMADIR%\interactions\synchronization\RegisterTemporaryRevokeInteraction\RegisterTemporaryRevokeInteraction_2.0_RIVTABP21.wsdl
SET X12=%SCHEMADIR%\interactions\synchronization\RegisterTemporaryRevokeInteraction\*.xsd

SET W13=%SCHEMADIR%\interactions\synchronization\UnregisterBlockInteraction\UnregisterBlockInteraction_2.0_RIVTABP21.wsdl
SET X13=%SCHEMADIR%\interactions\synchronization\UnregisterBlockInteraction\*.xsd

SET W14=%SCHEMADIR%\interactions\synchronization\UnregisterTemporaryRevokeInteraction\UnregisterTemporaryRevokeInteraction_2.0_RIVTABP21.wsdl
SET X14=%SCHEMADIR%\interactions\synchronization\UnregisterTemporaryRevokeInteraction\*.xsd

SET N1=%SCHEMADIR%\interactions\administration\*.xsd

SET XCORE=%SCHEMADIR%\core_components\*.xsd

SET SCHEMAS=%XCORE% %W0% %X0% %W1% %X1% %W2% %X2% %W3% %X3% %W4% %X4% %W5% %X5% %W6% %X6% %W7% %X7% %W8% %X8% %W9% %X9% %W10% %X10% %W11% %X11% %W12% %X12% %W13% %X13% %W14% %X14% %N1%

SET OUTFILE=/out:wcf\generated-src_blocking_v2\EhrBlockingInteractions.cs
SET APPCONFIG=/config:wcf\generated-src_blocking_v2\app.config
SET NAMESPACE=/namespace:*,Riv.Ehr.Blocking.Schemas.v2
SET SVCUTIL="svcutil.exe"
%SVCUTIL% /language:cs %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS%

CD wcf
ECHO Generating Service contract .Net Binding interfaces and classes for ehr:blocking Release 2
ECHO I DotNetprojektet ska du ta lagga till referens till System.ServiceModel
