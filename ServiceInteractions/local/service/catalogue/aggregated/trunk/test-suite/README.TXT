The testsuite uses SoapUI to verify the implementation, and consists of 2 parts for each interaction.
	1. A mock webservice for consumers to test interactions with.
	2. Test-suite for testing producers of interactions.

Documentation of SoapUI can be found at http://www.soapui.org
Install SoapUI according to the documentation.

Setup instructions
1. Copy the jar-file ‘soapui-support.jar’ to "SoapUI install dir"
2. Open SoapUI and import the testsuite project, choose ‘Import Project’ from the File-menu.
3. If your WebService endpoint requires a SSL Certificate, this can be configured from the Preferences (in the File menu). In the Preferences window  open the ‘SSL Settings’ tab and import the Keystore containing the Client Certificate.
4. Update test-data to match the contents in your system.
5. You should now be able to run the test suite!
