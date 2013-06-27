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

import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertEquals
/**
 *
 */
class SchematronValidatorTest {

    SchematronValidator validator

    @Before
    void setup() {
        validator = new SchematronValidator()
    }

    @Test
    void testValidateMessage() {

        def schematronFile = new File(this.class.getResource("constraints.xml").toURI())

        def message = new File(this.class.getResource("GetCareContacts.xml").toURI()).text

        def results = validator.validateMessage(message, schematronFile)

        assertEquals("Should have one failure", 1, results.size())
    }


}
