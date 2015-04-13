
```
    <xs:complexType name="ActorType">
        <xs:sequence>
            <xs:element name="hsaId" type="tns:HSAIdType" minOccurs="0"/>
            <xs:element name="name" type="xs:string" minOccurs="0"/>
            <xs:element name="personTelecom" type="xs:string" minOccurs="0"/>
            <xs:element name="personEmail" type="xs:string" minOccurs="0"/>
            <xs:element name="personAddress" type="xs:string" minOccurs="0"/>
        </xs:sequence>
    </xs:complexType>
```