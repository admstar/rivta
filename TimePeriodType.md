
```
    <xs:complexType name="TimePeriodType">
        <xs:annotation>
            <xs:documentation>
                Används för att specificera ett datumintervall
                med hjälp av start- och slutdatum.
                
                start: 
                Startdatum på formatet YYYYMMDDhhmmss
                end: 
                Slutdatum på formatet YYYYMMDDhhmmss
            </xs:documentation>
        </xs:annotation>
        <xs:sequence>
            <xs:element name="start" type="tns:TimeStampType" minOccurs="0"/>
            <xs:element name="end" type="tns:TimeStampType" minOccurs="0"/>
        </xs:sequence>
    </xs:complexType>
```