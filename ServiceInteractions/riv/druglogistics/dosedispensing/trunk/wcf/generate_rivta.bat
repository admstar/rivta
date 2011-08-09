CD ..
SET OUTFILE=/out:wcf\generated-src\DruglogisticsDosedispensing.cs
SET APPCONFIG=/config:wcf\generated-src\app.config
SET NAMESPACE=/namespace:*,Riv.Druglogistics.Dosedispensing.Schemas.v1
SET SCHEMADIR="schemas\interactions"
SET SVCUTIL="svcutil.exe"

SET W1=%SCHEMADIR%\AvbestallOrginalforpackningInteraction\AvbestallOrginalforpackningInteraction_1.0_rivtabp20.wsdl
SET X1=%SCHEMADIR%\AvbestallOrginalforpackningInteraction\*.xsd

SET W2=%SCHEMADIR%\BestallOrginalforpackningInteraction\BestallOrginalforpackningInteraction_1.0_rivtabp20.wsdl
SET X2=%SCHEMADIR%\BestallOrginalforpackningInteraction\*.xsd

SET W3=%SCHEMADIR%\HamtaLokaltProduktSortimentInteraction\HamtaLokaltProduktSortimentInteraction_1.0_rivtabp20.wsdl
SET X3=%SCHEMADIR%\HamtaLokaltProduktSortimentInteraction\*.xsd

SET W4=%SCHEMADIR%\HamtaMeddelandenInteraction\HamtaMeddelandenInteraction_1.0_rivtabp20.wsdl
SET X4=%SCHEMADIR%\HamtaMeddelandenInteraction\*.xsd

SET W5=%SCHEMADIR%\HamtaOrginalforpackningInteraction\HamtaOrginalforpackningInteraction_1.0_rivtabp20.wsdl
SET X5=%SCHEMADIR%\HamtaOrginalforpackningInteraction\*.xsd

SET W6=%SCHEMADIR%\HamtaVardtagareinformationInteraction\HamtaVardtagareinformationInteraction_1.0_rivtabp20.wsdl
SET X6=%SCHEMADIR%\HamtaVardtagareinformationInteraction\*.xsd

SET W7=%SCHEMADIR%\SkapaVardtagareInteraction\SkapaVardtagareInteraction_1.0_rivtabp20.wsdl
SET X7=%SCHEMADIR%\SkapaVardtagareInteraction\*.xsd

SET W8=%SCHEMADIR%\SkickaMeddelandenInteraction\SkickaMeddelandenInteraction_1.0_rivtabp20.wsdl
SET X8=%SCHEMADIR%\SkickaMeddelandenInteraction\*.xsd

SET W9=%SCHEMADIR%\UppdateraMeddelandeStatusInteraction\UppdateraMeddelandeStatusInteraction_1.0_rivtabp20.wsdl
SET X9=%SCHEMADIR%\UppdateraMeddelandeStatusInteraction\*.xsd

SET W10=%SCHEMADIR%\UppdateraVardtagareinformationInteraction\UppdateraVardtagareinformationInteraction_1.0_rivtabp20.wsdl
SET X10=%SCHEMADIR%\UppdateraVardtagareinformationInteraction\*.xsd

SET X11=schemas\core_components\*.xsd


SET SCHEMAS=%W1% %X1% %W2% %X2% %W3% %X3% %W4% %X4% %W5% %X5% %W6% %X6% %W7% %X7% %W8% %X8% %W9% %X9% %W10% %X10% %X11% 


%SVCUTIL% /language:cs /messageContract /a %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS% 


REM CD wcf
ECHO Generating Service contract .Net Binding interfaces and classes for inera:ior Release 1.0
ECHO I DotNetprojektet ska du ta lagga till referens till System.ServiceModel
