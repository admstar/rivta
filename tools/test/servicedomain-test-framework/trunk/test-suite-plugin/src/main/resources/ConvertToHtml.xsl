<?xml version="1.0" encoding="utf-8"?>
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
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output
            method="xml"
            omit-xml-declaration="yes"
            encoding="UTF-8"
            indent="yes" />
    <xsl:template match="/">
        <xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html></xsl:text>
        <html>
            <head>
               <title>TestSuite description <xsl:value-of select="/testsuite/id"/></title>
                <style>
                    body {
                        font-family: sans-serif;
                        width: 800px;
                        margin: 16px;
                        counter-reset: section
                    }
                    p {
                        margin: 0.2em;
                    }
                    h1 {
                        counter-reset: sub-section;
                        margin-bottom: 5px;
                    }
                    h2 {
                        counter-reset: composite;
                        margin-bottom: 5px;
                    }
                    h3 {
                        counter-reset: detail;
                        margin-bottom: 5px;
                    }

                    h1:before{
                        counter-increment: section;
                        content: counter(section) " ";
                    }
                    h2:before{
                        counter-increment: sub-section;
                        content: counter(section) "." counter(sub-section) " ";
                    }
                    h3:before{
                        counter-increment: composite;
                        content: counter(section) "." counter(sub-section) "." counter(composite) " ";
                    }
                    h4:before{
                        counter-increment: detail;
                        content: counter(section) "." counter(sub-section) "." counter(composite) "." counter(detail) " ";
                    }
                </style>
            </head>
            <body>
                <section>
                <h1>TestSuite description <xsl:value-of select="/testsuite/id"/></h1>
                <summary>
                    <p>This document describes the testcases used to verify the implementation of <xsl:value-of select="/testsuite/id"/>
                        before integration with the national platform.</p>
                </summary>
                    <article>
                        <h2>Tools</h2>
                        <h3>SoapUI</h3>
                        The testsuite uses SoapUI to verify the implementation. Documentation of SoapUI can be found at <a href="http://www.soapui.org" target="_blank">http://www.soapui.org</a><br/>
                        Link to download site: <a href="http://sourceforge.net/projects/soapui/files/soapui/4.5.2/" target="_blank">http://sourceforge.net/projects/soapui/files/soapui/4.5.2/</a><br/>
                        Install SoapUI according to the documentation.

                        <h3>Setup instructions</h3>
                        <ol>
                            <li>Unzip the testsuite package.</li>
                            <li>Copy the jar-file ‘soapui-support.jar’ to &lt;SoapUI install dir&gt;/bin/ext</li>
                            <li>Open SoapUI and import the testsuite project, choose ‘Import Project’ from the File-menu.</li>
                            <li>If your WebService endpoint requires a SSL Certificate, this can be configured from the Preferences (in the File menu). In the Preferences window  open the ‘SSL Settings’ tab and import the Keystore containing the Client Certificate.</li>
                            <li>Update test-data to match the contents in your system.</li>
                            <li>You should now be able to run the test suite!</li>
                        </ol>
                    </article>
                    <h2>Testcase description</h2>
                    <xsl:apply-templates select="testsuite/testcase"/>
                </section>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="testcase">
        <article>
            <h3><xsl:value-of select="@id"/></h3>
            <p>
                <xsl:apply-templates select="description" />
            </p>
            <details>
                <summary>Variables used</summary>
                <xsl:apply-templates select="data" />
            </details>
        </article>
    </xsl:template>

    <xsl:template match="description//*">
        <xsl:choose>
            <xsl:when test="local-name() = 'br'">
                <xsl:value-of select="concat('&lt;',local-name(),'&gt;')" disable-output-escaping="yes"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="concat('&lt;',local-name(),'&gt;')" disable-output-escaping="yes"/>
                <xsl:apply-templates/>
                <xsl:value-of select="concat('&lt;/',local-name(),'&gt;')" disable-output-escaping="yes"/>
            </xsl:otherwise>
        </xsl:choose>

    </xsl:template>

    <xsl:template match="data">
        <ul>
            <xsl:for-each select="descendant::*">
                <li><xsl:value-of select="local-name()" /></li>
            </xsl:for-each>
        </ul>
    </xsl:template>

</xsl:stylesheet>