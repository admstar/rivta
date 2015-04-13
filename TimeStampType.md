
```
    <xs:simpleType name="TimeStampType">
        <xs:annotation>
            <xs:documentation>
                A quantity specifying a point on the axis of natural time.
                A point in time is most often represented as a calendar
                expression.
                
                The time has the format YYYYMMDDhhmmss
            </xs:documentation>
        </xs:annotation>
        <xs:restriction base="xs:string">
            <xs:pattern
                value="(19|20)\d\d(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(0[0-9]|1[0-9]|2[0123])([0-5])([0-9])([0-5])([0-9])"
            />
        </xs:restriction>
    </xs:simpleType>
```