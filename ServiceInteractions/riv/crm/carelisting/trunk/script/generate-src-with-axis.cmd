@REM --------------------------------------------------------------------------
@REM 
@REM Generates Java Proxy classes with Axis2 1.4.1 and JDK 1.5.
@REM 
@REM --------------------------------------------------------------------------
set AXIS2_HOME=C:\Program\Java\axis2-1.4.1
set JAVA_HOME=C:\Program\Java\jdk1.5.0_19
set Path=C:\Program\Java\jdk1.5.0_19\bin;C:\Program\Java\axis2-1.4.1\bin;%Path%
set AXIS2_CLASS_PATH =""
setlocal EnableDelayedExpansion
set AXIS2_CLASS_PATH=%AXIS2_HOME%
FOR %%c in ("%AXIS2_HOME%\lib\*.jar") DO set AXIS2_CLASS_PATH=!AXIS2_CLASS_PATH!;%%c

@rem ======================================================================
CALL wsdl2java -g -uri GetAvailableFacilitiesInteraction.wsdl
CALL javac -cp .;%AXIS2_CLASS_PATH%  src/rivtabp20/_1/getavailableserviceproviders/carelisting/crm/riv/*.java
cd  src
jar -cf ..\lib\axis1.4.1_With_jdk5_0_19_listingservice.jar rivtabp20\_1\getavailableserviceproviders\carelisting\crm\riv
cd ..
rem rmdir /S /Q src