
```

<xs:complexType name="PatientSummaryHeaderType">
        <xs:sequence>
            <xs:element name="documentId" type="xs:string"/>
            <xs:element name="sourceSystemHSAId" type="tns:HSAIdType"/>
            <xs:element name="documentTitle" type="xs:string" minOccurs="0" maxOccurs="1"/>
            <xs:element name="documentTime" type="tns:TimeStampType" minOccurs="0" maxOccurs="1"/>
            <xs:element name="patientId" type="tns:PersonIdType"/>
<xs:element name="accountableHealthcareProfessional" type="tns:HealthcareProfessionalType"/>
            <xs:element name="legalAuthenticator" type="tns:LegalAuthenticatorType" minOccurs="0"/>
            <xs:element name="approvedForPatient" type="xs:boolean"/>
            <xs:element name="careContactId" type="xs:string" minOccurs="0"/>
            <xs:element name="nullified" type="xs:boolean" minOccurs="0"/>
            <xs:element name="nullifiedReason" type="xs:string" minOccurs="0"/>
        </xs:sequence>
</xs:complexType>
```