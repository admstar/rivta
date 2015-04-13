
```

<xs:complexType name="ResultType">
    <xs:annotation>
        <xs:documentation>
            ResultType hanterar logiska fel.
            
            resultCode: 
            Kan endast vara OK, INFO eller ERROR.
            errorCode: 
            Sätts endast om resultCode är ERROR. Tillåtna värden sätts per tjänstedomän.
            subcode: 
            Tillåtna värden sätts per tjänstedomän.
            logId: 
            En UUID som kan användas vid felanmälan för att användas vid felsökning av producent.
            message: 
            En beskrivande text som kan visas för användaren.
        </xs:documentation>
    </xs:annotation>
    <xs:sequence>
        <xs:element name="resultCode" type="codes:ResultCodeEnum"/>
        <xs:element name="errorCode" type="codes:ErrorCodeEnum" minOccurs="0" maxOccurs="1"/>
        <xs:element name="subcode" type="xs:string" minOccurs="0" maxOccurs="1"/>
        <xs:element name="logId" type="xs:string"/>
        <xs:element name="message" type="xs:string" minOccurs="0" maxOccurs="1"/>
    </xs:sequence>
</xs:complexType>
```