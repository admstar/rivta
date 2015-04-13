
```
    <xs:complexType name="MultimediaType">
        <xs:sequence>
            <xs:element name="id" type="xs:string" minOccurs="0"/>
            <xs:element name="mediaType" type="codes:MediaTypeEnum"/>
            <xs:element name="value" type="xs:base64Binary" minOccurs="0"/>
            <xs:element name="reference" type="xs:anyURI" minOccurs="0"/>
        </xs:sequence>
    </xs:complexType>
```