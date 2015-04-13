
```
    <xs:complexType name="PartialDateType">
        <xs:annotation>
            <xs:documentation>YYYY, YYYYMM or YYYYMMDD</xs:documentation>
        </xs:annotation>
        <xs:sequence>
            <xs:element name="format" type="codes:DateTypeFormatEnum"/>
            <xs:element name="value" type="tns:PartialDateValueType"/>
        </xs:sequence>
    </xs:complexType>
```