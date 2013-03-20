@echo off

AltovaXML /xslt2 "MappingMapToCDA.xslt" /in "../sample/RequestOutcomeInteraction.xml" /out "../../../../../../../../Resources/HL7 CDA/infrastructure/cda/CDA.xml" %*
IF ERRORLEVEL 1 EXIT/B %ERRORLEVEL%
