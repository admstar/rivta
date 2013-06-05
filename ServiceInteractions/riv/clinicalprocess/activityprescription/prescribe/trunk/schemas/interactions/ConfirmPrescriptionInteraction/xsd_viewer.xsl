<?xml version="1.0" encoding="utf-8"?>
<!--
/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns="http://www.w3.org/1999/xhtml"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                version="2.0"
                exclude-result-prefixes="xs">
    <xsl:output method="xml" version="1.0" encoding="utf-8" indent="no"
                omit-xml-declaration="no"
                media-type="text/html"
                doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
                doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"/>

    <xsl:strip-space elements="*"/>

    <!-- Variables -->
    <xsl:variable name="css">

        html {
            background-color: white;
        }

        body {
            margin: 0;
            padding: 0;
            height: auto;
            font: normal 80%/120% Arial, Helvetica, sans-serif;
        }

        .header {
            margin: 5px;
            font-weight: bold;
        }

        table {
            margin: 5px;
        }

        table, td, tr {
            border-color: black;
            border-width: 1px;
            border-spacing: 0;
            border-collapse: collapse;
            border-style: solid;
            padding: 5px;
        }

    </xsl:variable>

    <!-- Renderpipe -->

    <xsl:template name="htmlRender" match="/">
        <html>
            <head>
                <title><xsl:value-of select="xs:schema/@targetNamespace"/></title>
                <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
                <style type="text/css"><xsl:value-of select="$css" disable-output-escaping="yes"/></style>
            </head>
            <body>
                <xsl:call-template name="renterSchemaTypeMatrix" />
                <xsl:call-template name="renderSchemaComplexTypes" />
                <xsl:call-template name="renderSchemaSimpleTypes" />
            </body>
        </html>
    </xsl:template>

    <xsl:template name="renterSchemaTypeMatrix" match="*">
        <table>
            <tr>
                <td class="header">Klass.attribut</td>
                <td class="header">Mappning mot V-TIM</td>
                <td class="header">Mappning mod XSD</td>
            </tr>
            <xsl:call-template name="renderSchemaMatrix" />
        </table>
        <br/>
    </xsl:template>

    <xsl:template name="renderSchemaMatrix" match="*">
        <xsl:for-each select="child::*">
            <xsl:call-template name="renderMatrixRow"/>
            <xsl:for-each select="document(xs:include/@schemaLocation)">
                <xsl:call-template name="renderSchemaMatrix"/>
            </xsl:for-each>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="renderSchemaComplexTypes" match="*">
        <xsl:for-each select="child::*">
            <xsl:call-template name="renderComplexTypes"/>
            <xsl:for-each select="document(xs:include/@schemaLocation)">
                <xsl:call-template name="renderSchemaComplexTypes"/>
            </xsl:for-each>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="renderSchemaSimpleTypes" match="*">
        <xsl:for-each select="child::*">
            <xsl:call-template name="renderSimpleTypes"/>
            <xsl:for-each select="document(xs:include/@schemaLocation)">
                <xsl:call-template name="renderSchemaSimpleTypes"/>
            </xsl:for-each>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="renderMatrixRow" match="*">
        <xsl:for-each select="xs:complexType">
            <xsl:for-each select="*">
                <xsl:for-each select="child::*">
                    <xsl:if test="@name != ''">
                        <xsl:if test="contains(@type, 'xs')">
                            <tr>
                                <td></td>
                                <td></td>
                                <td>
                                    <a href="#tns:{parent::*/parent::*/@name}"><xsl:value-of select="parent::*/parent::*/@name"/></a>.<xsl:value-of select="@name"/>
                                </td>
                            </tr>
                        </xsl:if>
                    </xsl:if>
                </xsl:for-each>
            </xsl:for-each>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="renderComplexTypes" match="*">
        <xsl:for-each select="xs:complexType">
            <table>
                <tr>
                    <td class="header" colspan="4">
                        <a name="tns:{@name}"><xsl:value-of select="@name"/></a>
                    </td>
                </tr>
                <tr>
                    <td class="header">Fältnamn</td>
                    <td class="header">Datatyp</td>
                    <td class="header">Beskrivning</td>
                    <td class="header">Multip.</td>
                </tr>
                <xsl:for-each select="*/xs:choice">
                    <xsl:for-each select="xs:element">
                        <tr>
                            <td><xsl:value-of select="@name"/></td>
                            <td>
                                <xsl:choose>
                                    <xsl:when test="contains(@type, 'tns')">
                                        <a href="#{@type}"><xsl:value-of select="@type"/></a>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of select="@type"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </td>
                            <td>
                                <xsl:value-of select="xs:annotation/xs:documentation"/>
                            </td>
                            <td>Del av val</td>
                        </tr>
                    </xsl:for-each>
                </xsl:for-each>
                <xsl:for-each select="*/xs:element">
                    <tr>
                        <td><xsl:value-of select="@name"/></td>
                        <td>
                            <xsl:choose>
                                <xsl:when test="contains(@type, 'tns')">
                                    <a href="#{@type}"><xsl:value-of select="@type"/></a>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="@type"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </td>
                        <td><xsl:value-of select="xs:annotation/xs:documentation"/></td>
                        <td>
                            <xsl:choose>
                                <xsl:when test="@minOccurs!=''">
                                    <xsl:choose>
                                        <xsl:when test="@maxOccurs!=''">
                                            <xsl:value-of select="@minOccurs"/>..<xsl:value-of select="@maxOccurs"/>

                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:value-of select="@minOccurs"/>..1
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:choose>
                                        <xsl:when test="@maxOccurs!=''">
                                            1..<xsl:value-of select="@maxOccurs"/>
                                        </xsl:when>
                                        <xsl:otherwise>1..1</xsl:otherwise>
                                    </xsl:choose>
                                </xsl:otherwise>
                            </xsl:choose>
                        </td>
                    </tr>
                </xsl:for-each>
            </table><br/>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="renderSimpleTypes" match="*">
        <xsl:for-each select="xs:simpleType">
            <table>
                <tr>
                    <td class="header" colspan="3">
                        <a name="tns:{@name}"><xsl:value-of select="@name"/></a>
                    </td>
                </tr>
                <tr>
                    <td class="header">Namn</td>
                    <td class="header">Typ</td>
                    <td class="header">Begränsning</td>
                </tr>
                <tr>
                    <td><xsl:value-of select="@name"/></td>
                    <xsl:choose>
                        <xsl:when test="contains(xs:restriction/@base, 'tns')">
                            <td><a name="tns:{@base}"><xsl:value-of select="xs:restriction/@base"/></a></td>
                        </xsl:when>
                        <xsl:otherwise>
                            <td><xsl:value-of select="xs:restriction/@base"/></td>
                        </xsl:otherwise>
                    </xsl:choose>
                    <td>
                        <xsl:for-each select="xs:restriction/*">
                            <xsl:value-of select="name()"/> = <xsl:value-of select="@*"/><br/>
                        </xsl:for-each>
                    </td>
                </tr>
            </table><br/>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>