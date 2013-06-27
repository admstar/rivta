/*
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
*/
package se.skl.rivta.testsupport.soapui.validation

import org.apache.log4j.Logger

import javax.xml.transform.Source
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.URIResolver
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

/**
 * Validator used to verify soap messages according to Schematron rules.
 */
class SchematronValidator {

    private Logger log = Logger.getLogger(SchematronValidator.class)

    private TransformerFactory transformerFactory

    public SchematronValidator() {
        transformerFactory = TransformerFactory.newInstance()

        transformerFactory.setURIResolver(new URIResolver() {
            @Override
            Source resolve(String href, String base) throws TransformerException {
                log.debug("Resolve " + href + ", " + base)
                source(href)
            }
        })
    }


    public List<ValidationFailure> validateMessage(String message, File schematronFile) {

        def gatheredConstraints = gatherContraints(new StreamSource(schematronFile))

        def expandedConstraints = expandContraints(stringSource(gatheredConstraints))

        def rules = convertToRules(stringSource(expandedConstraints))

        def validationResults = validateDocument(stringSource(message), stringSource(rules))

        def resultDoc = new XmlSlurper().parseText(validationResults)

        def failedAsserts = resultDoc.breadthFirst().findAll { child -> child.name() == "failed-assert" }

        def failures = new ArrayList<ValidationFailure>()
        failedAsserts.each { node -> failures.add(new ValidationFailure(test: node.@test, location: node.@location, text: node.text.text())) }

        failures
    }


    private String gatherContraints(Source constraints) {
        transform(source("iso_dsdl_include.xsl"), constraints)
    }

    private String expandContraints(Source gatheredConstraints) {
        transform(source("iso_abstract_expand.xsl"), gatheredConstraints)
    }

    private String convertToRules(Source expandedConstraints) {
        transform(source("iso_svrl_for_xslt2.xsl"), expandedConstraints)
    }

    private String validateDocument(Source document, Source rules) {
        transform(rules, document)
    }

    String transform(Source templateSource, Source documentSource) {
        def transformer = transformerFactory.newTransformer(templateSource)
        def result = new StringResult()
        transformer.transform(documentSource, result)

        result.toString()
    }

    Source source(path) {
        def resource = this.getClass().getResourceAsStream(path)
        log.debug("Resource for path " + path + ": " + resource)
        new StreamSource(resource)
    }

    Source stringSource(contents) {
        new StreamSource(new StringReader(contents))
    }

    static class ValidationFailure {
        String test
        String location
        String text
    }

    static class StringResult extends StreamResult {
        StringWriter writer

        StringResult() {
            super()
            writer = new StringWriter()
            setWriter(writer)
        }

        @Override
        String toString() {
            writer.toString()
        }
    }


}
