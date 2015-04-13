
```
<xs:complexType name="PersonIdType">
    <xs:annotation>
      <xs:documentation>
        id
        Personal identity has the following format depending on the type
        personnummer: yyyymmddnnnn
        samordningsnummer: yyyymmddnnnn
        reservnummer: Is not standardized, there are several different variants.
        type
        Type av identification.
        personnummer = '1.2.752.129.2.1.3.1', se http://sv.wikipedia.org/wiki/Personnummer#Sverige
        samordningsnummer = '1.2.752.129.2.1.3.3', se http://sv.wikipedia.org/wiki/Samordningsnummer
        reservnummer SLL = '1.2.752.97.3.1.3', se http://sv.wikipedia.org/wiki/Reservnummer
      </xs:documentation>
    </xs:annotation>
    <xs:sequence>
      <xs:element name="id" type="xs:string"/>
      <xs:element name="type" type="xs:string"/>
      <xs:any namespace='##other' processContents='lax' minOccurs='0' maxOccurs='unbounded' />
    </xs:sequence>
  </xs:complexType>
```