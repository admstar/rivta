chcp 1252

SET SCHEMADIR="..\..\schemas\interactions"
SET XSDDIR="..\..\schemas\core_components"
rem SET SCHEMADIR="C:\Users\plus\Google Drive\NOD Tj?nstekontrakt v2\schemas\interactions"
rem SET XSDDIR="C:\Users\plus\Google Drive\NOD Tj?nstekontrakt v2\schemas\core_components"
rem SET SCHEMADIR="C:\BACKED\HCT_VSS\INERA\NOD\wcf\schemas\interactions"
rem SET XSDDIR="C:\BACKED\HCT_VSS\INERA\NOD\wcf\schemas\core_components"

SET W0=%SCHEMADIR%\AttachMedicationDispenseAuthorizationInteraction\*.wsdl
SET X0=%SCHEMADIR%\AttachMedicationDispenseAuthorizationInteraction\*.xsd

SET W1=%SCHEMADIR%\CancelMedicationDispenseAuthorizationInteraction\*.wsdl
SET X1=%SCHEMADIR%\CancelMedicationDispenseAuthorizationInteraction\*.xsd

SET W2=%SCHEMADIR%\CheckMedicationListVersionInteraction\*.wsdl
SET X2=%SCHEMADIR%\CheckMedicationListVersionInteraction\*.xsd

SET W3=%SCHEMADIR%\DiscontinueMedicationInteraction\*.wsdl
SET X3=%SCHEMADIR%\DiscontinueMedicationInteraction\*.xsd

SET W4=%SCHEMADIR%\GetDispensedDrugsInteraction\*.wsdl
SET X4=%SCHEMADIR%\GetDispensedDrugsInteraction\*.xsd

SET W12=%SCHEMADIR%\GetLFConsentInteraction\*.wsdl
SET X12=%SCHEMADIR%\GetLFConsentInteraction\*.xsd

SET W5=%SCHEMADIR%\GetMedicationDispenseAuthorizationsInteraction\*.wsdl
SET X5=%SCHEMADIR%\GetMedicationDispenseAuthorizationsInteraction\*.xsd

SET W6=%SCHEMADIR%\GetMedicationPrescriptionsInteraction\*.wsdl
SET X6=%SCHEMADIR%\GetMedicationPrescriptionsInteraction\*.xsd

SET W15=%SCHEMADIR%\PrintListOfDispensedDrugsInteraction\*.wsdl
SET X15=%SCHEMADIR%\PrintListOfDispensedDrugsInteraction\*.xsd

SET W7=%SCHEMADIR%\RegisterMedicationDispenseAuthorizationInteraction\*.wsdl
SET X7=%SCHEMADIR%\RegisterMedicationDispenseAuthorizationInteraction\*.xsd

SET W8=%SCHEMADIR%\RegisterMedicationDispenseAuthorizationWithoutPersonIdInteraction\*.wsdl
SET X8=%SCHEMADIR%\RegisterMedicationDispenseAuthorizationWithoutPersonIdInteraction\*.xsd

SET W9=%SCHEMADIR%\RegisterMedicationPrescriptionInteraction\*.wsdl
SET X9=%SCHEMADIR%\RegisterMedicationPrescriptionInteraction\*.xsd

SET W10=%SCHEMADIR%\SetMedicationListReviewedInteraction\*.wsdl
SET X10=%SCHEMADIR%\SetMedicationListReviewedInteraction\*.xsd

SET W11=%SCHEMADIR%\SetMedicationListReviewNeededInteraction\*.wsdl
SET X11=%SCHEMADIR%\SetMedicationListReviewNeededInteraction\*.xsd

SET W13=%SCHEMADIR%\UpdateLFConsentInteraction\*.wsdl
SET X13=%SCHEMADIR%\UpdateLFConsentInteraction\*.xsd

SET W14=%SCHEMADIR%\PrintListOfDispensedDrugsInteraction\*.wsdl
SET X14=%SCHEMADIR%\PrintListOfDispensedDrugsInteraction\*.xsd


SET XCORE=%XSDDIR%\*.xsd %XSDDIR%\generated\*.xsd

SET SCHEMAS=%XCORE% %W0% %X0% %W1% %X1% %W2% %X2%  %W3% %X3%  %W4% %X4%  %W5% %X5%  %W6% %X6%  %W7% %X7%  %W8% %X8%  %W9% %X9%  %W10% %X10%  %W11% %X11%  %W12% %X12%  %W13% %X13% %W14% %X14% %W15% %X15%

SET OUTFILE=/out:generated-src\nod.cs
SET APPCONFIG=/config:generated-src\app.config
SET NAMESPACE=/namespace:*,clinicalprocess_activityprescription_prescribe_2_0
SET SVCUTIL="svcutil.exe"

rem chcp 850
%SVCUTIL% /language:cs %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS%
pause


