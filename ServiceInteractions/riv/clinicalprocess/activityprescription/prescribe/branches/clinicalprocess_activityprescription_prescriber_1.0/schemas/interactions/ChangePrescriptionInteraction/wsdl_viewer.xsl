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
                xmlns:wsdl='http://schemas.xmlsoap.org/wsdl/'
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
        padding: 10px;
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
                <title><xsl:value-of select="wsdl:definitions/@name"/></title>
                <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
                <style type="text/css"><xsl:value-of select="$css" disable-output-escaping="yes"/></style>
            </head>
            <body>
                <h1><xsl:value-of select="wsdl:definitions/@name"/></h1>
                <xsl:call-template name="FormatText">
                    <xsl:with-param name="text"
                                    select="wsdl:definitions/xs:annotation/xs:documentation"/>
                </xsl:call-template>
                <h2>Operation</h2>
                <xsl:value-of select="wsdl:definitions/wsdl:portType/wsdl:operation/@name"/><br/>
                Input:  <xsl:value-of select="wsdl:definitions/wsdl:portType/wsdl:operation/wsdl:input/@message"/><br/>
                Output: <xsl:value-of select="wsdl:definitions/wsdl:portType/wsdl:operation/wsdl:output/@message"/><br/>

                <h2>Imports</h2>
                <xsl:for-each select="wsdl:definitions">
                    <xsl:call-template name="renderTypeImports"/>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>

    <xsl:template name="renderTypeImports" match="*">
        <xsl:for-each select="wsdl:types/xs:schema/xs:import">
            <a href="{@schemaLocation}"><xsl:value-of select="@namespace"/></a><br/>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="FormatText">
        <xsl:param name="text" />
        <xsl:choose>
            <xsl:when test="contains($text,'&#xA;')">
                <xsl:value-of select="substring-before($text,'&#xA;')"/>
                <br/>
                <xsl:call-template name="FormatText">
                    <xsl:with-param name="text">
                        <xsl:value-of select="substring-after($text,'&#xA;')"/>
                    </xsl:with-param>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$text"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>