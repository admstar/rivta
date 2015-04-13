
```
    <xs:complexType name="HealthcareProfessionalType">
        <xs:annotation>
            <xs:documentation>
                Information om hälso- och omsorgspesonal
                
                healthcareProfessionalHsaId: 
                HSA-id för vård- och omsorgspersonal 
                healthcareProfessionalName
                Namn på vård- och omsorgspersonal
                healthcareProfessionalRoleCode: 
                Information om personens befattning om annat kodverk än KV Befattning används. 
                Ska anges om healthcareProfessionalOtherRoleCode saknas. Kan inte anges 
                samtidigt med healthcareProfessionalOtherRoleCode.
                healthcareProfessionalOtherRoleCode: 
                Information om författarens befattning om annat kodverk än KV Befattning 
                används. Ska anges om healthcareProfessionalRoleCode saknas. Kan inte anges 
                samtidigt med healthcareProfessionalRoleCode.
                healthcareProfessionalCareUnitHSAid: 
                HSA-id för PDL-enhet som vård- och omsorgspersonen är uppdragstagare för
                healthcareProfessionalCareGiverHSAid: 
                HSA-id för vårdgivaren, som är vårdgivare för den enhet som författaren är uppdragstagare för
            </xs:documentation>
        </xs:annotation>
        <xs:sequence>
            <xs:element name="authorTime" type="tns:TimeStampType"/>
            <xs:element name="healthcareProfessionalHSAId" type="tns:HSAIdType" minOccurs="0"/>
            <xs:element name="healthcareProfessionalName" type="xs:string" minOccurs="0"/>
            <xs:element name="healthcareProfessionalRoleCode" type="tns:CVType" minOccurs="0"/>
            <xs:element name="healthcareProfessionalOrgUnit" type="tns:OrgUnitType" minOccurs="0"/>
            <xs:element name="healthcareProfessionalCareUnitHSAId" type="tns:HSAIdType"
                minOccurs="0"/>
            <xs:element minOccurs="0" name="healthcareProfessionalCareGiverHSAId"
                type="tns:HSAIdType"/>
        </xs:sequence>
    </xs:complexType>
```