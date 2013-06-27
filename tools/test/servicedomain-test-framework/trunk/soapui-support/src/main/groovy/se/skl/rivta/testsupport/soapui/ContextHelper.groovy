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
package se.skl.rivta.testsupport.soapui

import com.eviware.soapui.model.testsuite.TestCaseRunContext
import org.apache.log4j.Logger

/**
 * Helper class used to handle context operations.
 */
class ContextHelper {

    private Logger log = Logger.getLogger(ContextHelper.class)

    TestCaseRunContext context

    public ContextHelper(TestCaseRunContext context) {
        this.context = context
    }

    /**
     * Method used to fail current test.
     * When test-running scripts in SoapUI, the TestRunner instance is null. This method will issue a warning
     * log instead of breaking the script.
     * @param message Reason for failing
     */
    public void fail(String message) {
        if (context.testRunner != null) {
            context.testRunner.fail(message)
        } else {
            log.warn("Failing test: $message")
        }
    }

}
