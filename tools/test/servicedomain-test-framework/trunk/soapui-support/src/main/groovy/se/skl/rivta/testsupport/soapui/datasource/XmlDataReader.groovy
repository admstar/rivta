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
import org.apache.log4j.Logger

/**
 * DataSource used to add testcase parameters from an external xml file.
 */
class XmlDataReader {

    private Logger log = Logger.getLogger(this.class)

    TestCaseRunContext context
    File dataFile

    public XmlDataReader(TestCaseRunContext context, File dataFile) {
        this.context = context
        this.dataFile = dataFile


        if (!dataFile.exists()) {
            throw new IllegalArgumentException("dataFile does not exist: " + dataFile.absolutePath)
        }
    }

    public void load(String testCaseName) {
        def testsuite = new XmlParser().parse(dataFile)

        testsuite.globaldata[0].each { child -> context.setProperty(child.name(), child.text()) }

        def testcaseList = testsuite.testcase.findAll { child -> child.@id == testCaseName }
        if (testcaseList.size() == 0) {
            throw new IllegalArgumentException("Could not find data for TestCaseName: " + testCaseName)
        } else if (testcaseList.size() > 1) {
            throw new IllegalArgumentException("TestCaseName is not unique: " + testCaseName)
        }
        assert testcaseList.size() == 1
        def testcase = testcaseList[0]

        testcase.data[0].each { child -> context.setProperty(child.name(), child.text()) }
    }

}
