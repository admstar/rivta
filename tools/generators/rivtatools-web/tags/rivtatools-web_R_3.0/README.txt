(This file is UTF-8-encoded)

rivtatools-web is a web application project set up för deployment to Google App Engine. 

It is deployed in Google App Engeine at the following URL: http://rivtatools.appspot.com.
It was originally developed by Callista Enterprise AB, who donated the code and IPR to SKL in January 2013.

It is currently maintained by Callista Enterprise AB. This means that deployment activities to the GAE project "rivtatools" depends on access to a google account with permissions to deploy the rivatools application. The source code may of cause be built for any other deployment environment by anyone at any time. 

Some of the GAE application admins (with deploy privileges) are:

- johan.eltes@callistaenterprise.se (Johan Eltes, 0708 22 41 86)
- lars.erik.rojeras@skoview.se (Lars-Erik Röjerås, +46 70-558 90 08, NTjP-responsible at the architecture unit of Center for eHealth in Sweden.)

The following technology is used:

Development language: Groovy
Unit-testing: JUnit 4 + XMLUnit
IDE: Eclipse with the latest Groovy (springsource) and Google App Engine (google) plug-ins. The Eclipse project files are committed to the project, while the Eclipse workspace isn't
Target web container: Google App Engine
JRE: 1.6 due to restrictions of GAE
Web framework: None. It is currently coded on the plain Servlet API.

Eclipse IDE: Use GGTS, see http://spring.io/tools. Currently v3.6.1 (based on Eclipse 4.4, Luna)

Google App Engine (google) plug-ins
- See https://cloud.google.com/appengine/docs/java/tools/eclipse
- Install Google Plugin for Eclipse 4.4, v3.7.0 + SDKs/Google App Enginge Java SDK, v1.9.11

The project has a dependency to xml unit v1.1. Setup xml unit as:
- Download from http://sourceforge.net/projects/xmlunit/files/xmlunit%20for%20Java/XMLUnit%20Java%201.1/xmlunit-1.1-bin.zip/download
- Unzip to a folder of your choice
- Define a classpath variable XMLUNIT_HOME in Eclipse to point to that folder
  (in Preferences --> Java --> Build Path --> Classpath Variables)
