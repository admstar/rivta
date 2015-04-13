
```
    <xs:complexType name="OrgUnitType">
        <xs:annotation>
            <xs:documentation>
                Information om en organisationsenhet
                
                orgUnitHsaId: 
                HSA-id för organisationsenhet
                orgUnitName
                Namn på organisationsenhet
                orgUnitTelecom: 
                Telefon till organisationsenhet
                orgUnitEmail: 
                Epost till enhet
                orgUnitAddress: 
                Postadress till enhet
                orgUnitLocation: 
                Text som anger namnet på plats eller ort för enhetens eller funktionens fysiska placering
            </xs:documentation>
        </xs:annotation>
        <xs:sequence>
            <xs:element name="orgUnitHSAId" type="tns:HSAIdType" minOccurs="0"/>
            <xs:element name="orgUnitName" type="xs:string" minOccurs="0"/>
            <xs:element name="orgUnitTelecom" type="xs:string" minOccurs="0"/>
            <xs:element name="orgUnitEmail" type="xs:string" minOccurs="0"/>
            <xs:element name="orgUnitAddress" type="xs:string" minOccurs="0"/>
            <xs:element name="orgUnitLocation" type="xs:string" minOccurs="0"/>
        </xs:sequence>
    </xs:complexType>
```