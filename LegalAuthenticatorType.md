
```
    <xs:complexType name="LegalAuthenticatorType">
        <xs:annotation>
            <xs:documentation>
                Information om signering
                
                signatureTime: 
                    Tidpunkt för signering, format ÅÅÅÅMMDDttmmss. 
                assignedEntity: 
                    Information om person som signerat dokumentet
            </xs:documentation>
        </xs:annotation>
        <xs:sequence>
            <xs:element name="signatureTime" type="tns:TimeStampType"/>
            <xs:element name="legalAuthenticatorHSAId" type="tns:HSAIdType" minOccurs="0"/>
            <xs:element name="legalAuthenticatorName" type="xs:string" minOccurs="0"/>
            <xs:element name="legalAuthenticatorRoleCode" type="tns:CVType" minOccurs="0"/>
        </xs:sequence>
    </xs:complexType>
```