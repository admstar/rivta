echo Must use JAW-WS 2.1.17 or higher. 
echo Run each line separate.
CALL wsimport -d ..\..\..\..\..\target\classes -b ws-addressing-1.0.xsd -Xno-addressing-databinding -XadditionalHeaders GetListningTypesInteraction.wsdl
CALL wsimport -d ..\..\..\..\..\target\classes -b ws-addressing-1.0.xsd -Xno-addressing-databinding -XadditionalHeaders GetListningInteraction.wsdl
CALL wsimport -d ..\..\..\..\..\target\classes -b ws-addressing-1.0.xsd -Xno-addressing-databinding -XadditionalHeaders GetAvailableFacilitiesInteraction.wsdl
CALL wsimport -d ..\..\..\..\..\target\classes -b ws-addressing-1.0.xsd -Xno-addressing-databinding -XadditionalHeaders CreateListningInteraction.wsdl
cd C:\Program\Java\eclipseworkspaces\VVAL\target\classes
jar -cf ..\..\Lib\listingservice.jar riv\crm org
cd C:\Program\Java\eclipseworkspaces\VVAL\src\main\resources\META-INF\wsdl