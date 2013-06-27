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
package se.skl.rivta.testsupport.soapui.datasource

import com.eviware.soapui.model.testsuite.TestCaseRunContext
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertEquals
/**
 *
 */
class XmlDataReaderTest {

    XmlDataReader dataSource
    TestCaseRunContext context
    Map<String, String> resultMap

    @Before
    void setUp() {
        resultMap = [:]
        context = [setProperty: {key, value -> resultMap[key] = value }] as TestCaseRunContext

        File dataFile = new File(this.class.getResource("data.xml").toURI())

        dataSource = new XmlDataReader(context, dataFile)
    }

    @Test
    void testLoad() {
        dataSource.load("TestCase1")

        assertEquals("Wrong number of parameters", 8, resultMap.size())
        assertEquals("Wrong value of webServiceUrl", "http://localhost:9090/abc", resultMap["webServiceUrl"])
        assertEquals("Wrong value of careUnitHSAid", "123465", resultMap["careUnitHSAid"])
        assertEquals("Wrong value of overridden global value patientType", "2", resultMap["patientType"])

    }

}
