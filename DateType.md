
```
    <!-- YYYYMMDD -->
    <xs:simpleType name="DateType">
        <xs:annotation>
            <xs:documentation/>
        </xs:annotation>
        <xs:restriction base="xs:string">
            <xs:pattern value="(19|20)\d\d(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])"/>
        </xs:restriction>
    </xs:simpleType>
```