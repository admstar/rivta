
```
    <xs:complexType name="DatePeriodType">
        <xs:annotation>
            <xs:documentation>
                Används för att specificera ett datumintervall
                med hjälp av start- och slutdatum.
                
                start: 
                    Startdatum på formatet ÅÅÅÅMMDD
                end: 
                    Slutdatum på formatet ÅÅÅÅMMDD
            </xs:documentation>
        </xs:annotation>
        <xs:sequence>
            <xs:element name="start" type="tns:DateType" minOccurs="0"/>
            <xs:element name="end" type="tns:DateType" minOccurs="0"/>
        </xs:sequence>
    </xs:complexType>
```