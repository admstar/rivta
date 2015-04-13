<?xml version="1.0" encoding="UTF-8"?>
<!--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements. See the NOTICE file
distributed with this work for additional information
regarding copyright ownership. Sveriges Kommuner och Landsting licenses this file
to you under the Apache License, Version 2.0 (the
        "License"); you may not use this file except in compliance
with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied. See the License for the
specific language governing permissions and limitations
under the License.
-->
<schema 
    xmlns="http://purl.oclc.org/dsdl/schematron"
    queryBinding="xslt2">
    
    <title>Validation for GetQualityIndicators response</title>
    <ns prefix='urn' uri='urn:riv:followup:groupoutcomes:qualityreporting:GetQualityIndicatorsResponder:2' />
    <ns prefix='urn1' uri='urn:riv:followup:groupoutcomes:qualityreporting:2' />
    
    <!-- MeasureId, oid = 1.2.826.0.1.3680043.9.4672.7 -->
    
    <!-- Rules for Response-->
    
    <pattern id="Verify_measurment">
        <rule context="urn1:measurement">
            <assert test="urn1:measureId[urn1:root = '1.2.826.0.1.3680043.9.4672.7']">Invalid OID ('<value-of select="urn1:measureId/urn1:root"/>') given for measureId.root</assert>
            <assert test="urn1:measureId/urn1:extension">measureId.extension has to be given</assert>
            <assert test="count(urn1:proportionMeasure|urn1:continuousVariableMeasure|urn1:missingMeasure|urn1:cohortMeasure) = 1">
                One and only one of proportionMeasure, continuousVariableMeasure, cohortMeasure eller missingMeasure has to be present.</assert>
        </rule>
    </pattern>
    
    <pattern id="Verify_proportionMeasure">
        <rule context="urn1:proportionMeasure">
            <assert test="urn1:rate = 0 or urn1:rate = 1 or (0 &lt; urn1:rate and urn1:rate &lt; 1)"
                ><name/>: Invalid value for <name path="./urn1:rate"/>, must be between 0 and 1, inclusive</assert>
            <assert test="not(urn1:coverage) or (urn1:coverage = 0 or urn1:coverage = 1 or 0 &lt; urn1:coverage and urn1:coverage &lt; 1)"
                ><name/>: Invalid value for <name path="./urn1:coverage"/>, must be between 0 and 1, inclusive</assert>
        </rule>
    </pattern>
    
    <pattern id="Verify_continuousVariableMeasure">
        <rule context="urn1:continuousVariableMeasure">
            <assert test="not(urn1:coverage) or (urn1:coverage = 0 or urn1:coverage = 1 or 0 &lt; urn1:coverage and urn1:coverage &lt; 1)"
                ><name/>: Invalid value for <name path="./urn1:coverage"/>, must be between 0 and 1, inclusive</assert>
        </rule>
    </pattern>
    
    <pattern id="Verify_cohortMeasure">
        <rule context="urn1:cohortMeasure">
            <assert test="not(urn1:coverage) or (urn1:coverage = 0 or urn1:coverage = 1 or 0 &lt; urn1:coverage and urn1:coverage &lt; 1)"
                ><name/>: Invalid value ('<value-of select="urn1:coverage"/>') for <name path="./urn1:coverage"/>, must be between 0 and 1, inclusive</assert>
        </rule>
    </pattern>

    <pattern id="Verify_missingMeasure">
        <rule context="urn1:missingMeasure">
            <assert test="urn1:reasonCode"><name/>: 'reasonCode' must be given.</assert>
        </rule>
    </pattern>
    
    <!-- 
        Organisation skall vara korrekt ifyllt.
        Rätt hierarki beroende på resultatenhet.
        Korrekta hierakier är följande:
        1. [Vardenhet-Län-Land]
        2. [Vardenhet-Kommun-Län-Land]
        3. [Vardenhet-Sjukhus-Län-Land]
        4. [Sjukvårdsregion-Land]
        Enheter kan utelämnas från vänster 
    -->    
    <pattern id="Verify_Organization">
        <rule context="*[urn1:organizationType = 'Vardenhet']">
            <assert test="urn1:hsaId">Vardenhet ('<value-of select="urn1:organizationName"/>') missing hsaId</assert>
            <assert test="urn1:asOrganizationPartOf[urn1:organizationType = 'Lan']
                or urn1:asOrganizationPartOf[urn1:organizationType = 'Kommun']
                or urn1:asOrganizationPartOf[urn1:organizationType = 'Sjukhus']"
                >Vardenhet has to be part of Lan, Kommun or sjukhus</assert>
        </rule>
        
        <rule context="*[urn1:organizationType = 'Kommun']">
            <assert test="urn1:asOrganizationPartOf[urn1:organizationType = 'Lan']">Kommun ('<value-of select="urn1:organizationName"/>') has to be part of Lan</assert>
            <assert test="urn1:organizationId">Kommun ('<value-of select="urn1:organizationName"/>') must be identified by organizationId</assert>
            <assert test="urn1:organizationId[urn1:root = '1.2.752.129.2.2.1.17']">Invalid OID ('<value-of select="urn1:organizationId/urn1:root"/>') given for kommun ('<value-of select="urn1:organizationName"/>')</assert>
        </rule>
        
        <rule context="*[urn1:organizationType = 'Sjukhus']">
            <assert test="urn1:hsaId or urn1:organizationId">Sjukhus ('<value-of select="urn1:organizationName"/>') must be identified by hsaId or organizationId</assert>
            <assert test="urn1:asOrganizationPartOf[urn1:organizationType = 'Lan']">Sjukhus ('<value-of select="urn1:organizationName"/>') must be part of Lan</assert>
            <assert test="not(urn1:organizationId) or urn1:organizationId[urn1:root = '1.2.826.0.1.3680043.9.4672.5']">Invalid OID ('<value-of select="urn1:organizationId/urn1:root"/>') given for sjukhus ('<value-of select="urn1:organizationName"/>')</assert>
        </rule>
 
        <rule context="*[urn1:organizationType = 'Lan']">
            <assert test="urn1:asOrganizationPartOf[urn1:organizationType = 'Land']">Lan ('<value-of select="urn1:organizationName"/>') must be part of Land</assert>
            <assert test="urn1:organizationId">Lan ('<value-of select="urn1:organizationName"/>') is missing organizationId</assert>
            <assert test="urn1:organizationId[urn1:root = '1.2.752.129.2.2.1.18']">Invalid OID ('<value-of select="urn1:organizationId/urn1:root"/>') given for Lan ('<value-of select="urn1:organizationName"/>')</assert>
        </rule>

        <rule context="*[urn1:organizationType = 'Sjukvardsregion']">
            <assert test="urn1:asOrganizationPartOf[urn1:organizationType = 'Land']">Sjukvardsregion ('<value-of select="urn1:organizationName"/>') must be part of Land</assert>
            <assert test="urn1:organizationId">Sjukvardsregion ('<value-of select="urn1:organizationName"/>') must be identified by organizationId</assert>
        </rule>

        <rule context="*[urn1:organizationType = 'Land']">
            <assert test="not(urn1:asOrganizationPartOf)">Land ('<value-of select="urn1:organizationName"/>') must not have 'asOrganizationPartOf'.</assert>
            <assert test="urn1:organizationId">Land ('<value-of select="urn1:organizationName"/>') must be identified by organizationId</assert>
            <assert test="urn1:organizationId[urn1:root = '1.2.752.129.2.2.1.19']">Invalid OID ('<value-of select="urn1:organizationId/urn1:root"/>') given for Land ('<value-of select="urn1:organizationName"/>')</assert>
            <assert test="urn1:organizationId[urn1:extension = 'SE']">Ivalid value organizationId('<value-of select="urn1:organizationId/urn1:extension"/>') given for Land ('<value-of select="urn1:organizationName"/>')</assert>
        </rule>
    </pattern>
        
    <!-- En organisation får bara förekomma en gång med samma measurePeriod för samma indikator.  -->

    <!-- Numerator, Täljare? -->
    <!-- Denominator, Nämnare? -->
    <!-- Exclusions, bortfall? -->
    
    <!-- Om rapporten innehåller konfidensinterval skall detta vara symmetriskt fördelat över medelvärdet men inte understiga 0 -->
    
    <!-- sourceSystem -->
    <pattern id="Verify_SourceSystem">
        <rule context="urn1:sourceSystem">
            <assert test="urn1:systemId[urn1:root ='1.2.752.129.2.1.2.1'] 
                or urn1:systemId[urn1:root ='1.2.752.129.2.1.4.1'] ">Identifier OID for <name/> must be either '1.2.752.129.2.1.2.1' or '1.2.752.129.2.1.4.1'.</assert>
        </rule>
    </pattern>
     
</schema>