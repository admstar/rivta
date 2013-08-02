#!/usr/bin/env groovy

import groovy.io.FileType
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

@Grab(group='dom4j', module='dom4j', version='1.6.1')
import org.dom4j.io.SAXReader


/**
 *Script to generate a jaxws pom.xml from a rivta-structured service domain. Run the script to get information on input arguments.
 *
 * Revision history:
 *
 * Version 1.0
 * First version of this script.
 *
 * LEO 2013-08-02 Beta of second version
 *
 */

def getAllFilesMatching(direcory, pattern){
	def filesFound = []
	direcory?.traverse(type:FileType.FILES, nameFilter: ~pattern){ fileFound -> filesFound << fileFound }
	filesFound.each { fileFound -> println "File to process: ${fileFound.name}" }
	return filesFound
}

def getAllDirectoriesMatching(direcory, pattern){
	def dirsFound = []
	direcory?.traverse(type:FileType.DIRECTORIES, nameFilter: ~pattern){ dirFound -> dirsFound << dirFound }
	dirsFound.each { dirFound -> println "Directory to process: ${dirFound}" }
	return dirsFound
}

def getTemplatePom(){
    // Changed path to schema directory to be based two levels up
	def template = '''<?xml version="1.0" encoding="UTF-8"?>
	<!-- Licensed to the Apache Software Foundation (ASF) under one or more contributor
		license agreements. See the NOTICE file distributed with this work for additional
		information regarding copyright ownership. Inera AB licenses this file to
		you under the Apache License, Version 2.0 (the "License"); you may not use
		this file except in compliance with the License. You may obtain a copy of
		the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required
		by applicable law or agreed to in writing, software distributed under the
		License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
		OF ANY KIND, either express or implied. See the License for the specific
		language governing permissions and limitations under the License. -->
	<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
		<modelVersion>4.0.0</modelVersion>
		<groupId>se.rivta.tools</groupId>
		<artifactId>artifactId.replace</artifactId>
		<packaging>jar</packaging>
		<version>1.1-SNAPSHOT</version>
		<name>name.replace</name>
		<url>http://maven.apache.org</url>
	
		<properties>
			<schema.path>${basedir}/../../schemas</schema.path>
			<cxf.version>2.2.2</cxf.version>
		</properties>
	
		<dependencies>
		</dependencies>
		
		<repositories>
			<!-- for jaxb-impl -->
			<repository>
				<id>java.net</id>
				<url>http://download.java.net/maven/1/</url>
				<layout>legacy</layout>
			</repository>
			<repository>
				<id>m2.java.net</id>
				<name>Java.net Repository for Maven2</name>
				<url>http://download.java.net/maven/2/</url>
				<layout>default</layout>
			</repository>
		</repositories>
		<pluginRepositories>
			<pluginRepository>
				<id>repository.codehaus.org</id>
				<url>http://repository.codehaus.org/</url>
			</pluginRepository>
		</pluginRepositories>
	
		<build>
			<plugins>
				<plugin>
					<groupId>org.apache.cxf</groupId>
					<artifactId>cxf-codegen-plugin</artifactId>
					<version>${cxf.version}</version>
					<executions>
						<execution>
							<id>generate-sources</id>
							<phase>generate-sources</phase>
							<configuration>
								<sourceRoot>${basedir}/target/generated/src/main/java</sourceRoot>
								<wsdlOptions>
									<!-- HERE NAMESPACES WILL BE PLACED -->
								</wsdlOptions>
							</configuration>
							<goals>
								<goal>wsdl2java</goal>
							</goals>
						</execution>
					</executions>
				</plugin>
				<plugin>
					<artifactId>maven-compiler-plugin</artifactId>
					<configuration>
						<source>1.6</source>
						<target>1.6</target>
					</configuration>
				</plugin>
			</plugins>
		</build>
	</project>'''

	def pomTemplate = new XmlSlurper().parseText(template) //.asWritable()
	return pomTemplate
}

def getFilePattern(rivtaProfile, version){
	def pattern = ".*_${version}.*(?i)${rivtaProfile}\\.wsdl"
	return pattern
}

def updateArtifactInformation(pom, domain, version){
	pom.artifactId = domain.replace(/:/,"-") + "-schemas"
	pom.name = domain.replace(/:/,"-") + "-schemas"
	pom.version = version
}

def getRelativeSchemaPath(absoluteSchemaPath){
	def startIndex = absoluteSchemaPath.indexOf('schemas')
	def relativeSchemaPath = absoluteSchemaPath.getAt(startIndex..-1)
	relativeSchemaPath = relativeSchemaPath.replace('schemas','')
	return '${schema.path}' + relativeSchemaPath
}

def updateMajorVersionToFitJavaPackageConvention(orig) {
	orig.replaceFirst(/.(\d)/) { fullMatch, firstCharacter -> ".v" + firstCharacter }
}

def makeValidJavaPackageName(orig) {
	orig.toLowerCase().replace(/:/,'.')
}

def makePackageNameStartWithSE(orig){
	orig.replace('urn','se')
}

def getAllUniqueRivNameSpaces(wsdlFile, coreSchemasFiles){
	def rivNameSpaces = []

	new SAXReader().read(wsdlFile).getRootElement().declaredNamespaces().grep(~/.*urn:riv.*/).each{ namespace ->
		rivNameSpaces << namespace.text
	}

	coreSchemasFiles.each { coreSchemasFile ->
		new SAXReader().read(coreSchemasFile).getRootElement().declaredNamespaces().grep(~/.*urn:riv.*/).each{ namespace ->
			rivNameSpaces << namespace.text
		}
	}

	return rivNameSpaces.unique()
}

def addJaxWsInformation(pom, wsdlFiles, coreSchemasFiles){

	def pluginConfiguration = pom.build.plugins.plugin[0].executions.execution[0].configuration

	wsdlFiles.each { wsdlFile ->

		def rivNameSpaces = getAllUniqueRivNameSpaces(wsdlFile, coreSchemasFiles)

		pluginConfiguration.wsdlOptions.appendNode {
			wsdloption {
				wsdl(getRelativeSchemaPath(wsdlFile.absolutePath))
				extraargs() {
					extraarg('-p')
					extraarg("http://www.w3.org/2005/08/addressing=org.w3c.addressing.v1")

					rivNameSpaces.each {rivNamespace ->
						def urn = rivNamespace
						def packageName = makeValidJavaPackageName(urn)
						packageName = updateMajorVersionToFitJavaPackageConvention(packageName)
						packageName = makePackageNameStartWithSE(packageName)
						extraarg('-p')
						extraarg("${urn}=${packageName}")
					}
				}
			}
		}
	}
}

def printPom(baseDir, pom, rivtaProfile){
    // Let us put the file in the code-gen directory according to the RIV TA 2.1 Konfigurationsstyrning
	def jaxwsDir = "${baseDir}/code-gen/jaxws"
    /* LEO
       We do not want to delete the directory
	   new File(jaxwsDir).deleteDir() */
    new File(jaxwsDir).mkdirs()


	def smb = new StreamingMarkupBuilder().bind {
		mkp.declareNamespace(
				xsi: "http://www.w3.org/2001/XMLSchema-instance",
				'':"http://maven.apache.org/POM/4.0.0"
				)
		mkp.yield pom
	}

	String newPom = XmlUtil.serialize(smb)
    // LEO Delete the old file (not the folder)
    new File("${jaxwsDir}/pom.xml").delete()
    new File("${jaxwsDir}/pom.xml").write(newPom, "UTF-8")
}


/*
*   Remove old parameter processing
if( args.size() < 4){
	println "For this script to work, you need Groovy version 1.8.1 or higher."
	println "This is a script that will generate a pom.xml. The script requires the directory"
	println "structure to follow the RIVTA standard: "
	println "schemas/..."
	println "    ...interactions/<a folder per service interaction>/<service schemas and wsdl file>"
	println "    ...core_components/<all core schemas>"
	println ""
	println "Required parameters: service domain root directory [domainDir], service domain [domain], RIV TA Profile [rivtaprofile], Major version [version] \n"
	println "PARAMETERS DESCRIPTION:"
	println "[domainDir] is the base direcory where this script will start working, i.e. the parent directory of the 'schemas' directory "
	println "[domain] is the name of the service domain to process, e.g crm:scheduling"
	println "[rivtaprofile], the short-name of the RIVTA profile e.g rivtabp20"
	println "[version], the major version of the service domain e.g 1, 2 or 3 etc"
	println "example invocation:"
	println "	groovy JaxWsMavenPomGenerator . ehr:scheduling rivtabp20 1"
	println ""
	println "OUTPUT:"
	println "generated-scripts/jaxws/pom.xml"
	return
}

def baseDir = new File(args[0])
def domain = args[1]
def rivtaProfile = args[2]
def version = args[3]
*/

/**
 * LEO 2013-08-01
 * Parse and verify command line options
 */

// Get name of this script and set up usage message
me = this.class.name
mecall = me + ' [options]'

medesc = """\
\nThis script requires Groovy 1.8.1 or later.
It generates a <directory>/code-gen/jaxws/pom.xml - file. The directories are created if necessary, and an existing pom.xml is replaced.
The basedir parameter should refer to the \"trunk\" directory in a structure which follows the \"RIVTA 2.1 Konfigurationsstyrning\" guideline.
"""

metext = me + ' [options] ' + medesc

// Create the cli and define the parameters
def cli = new CliBuilder(usage: mecall, header: 'Options:', footer: medesc)
cli.b(longOpt: 'basedir', args:1, required:true, argName:'directory', 'trunk directory where this script will start working, i.e. the parent directory of the \'schemas\' directory')
cli.d(longOpt: 'domain', args:1, required:true, argName:'domain-name', 'the name of this Service Domain (like \"clinicalprocess:activity:request\"')
cli.m(longOpt: 'major', args:1, required:true, argName:'major', 'the major version of the service domain (an integer)')
cli.r(longOpt: 'rivtaprofile', required:true, args:1, argName:'profile', 'the short name of the RIVTA profile (rivtabp20 or rivtabp21)')

// Verify all parameters
def options = cli.parse(args)
if (!options) return

// basedir
def argBasedir=options.getProperty('basedir')
def dir = new File(argBasedir)

if (! dir.exists()) {
    println('* The supplied basedir \"' + argBasedir + '\" does not exist\n')
    cli.usage()
    return 1
}

// domain
def argDomain=options.getProperty('domain')

if ( (argDomain =~ ':').count <1 ) {
    println('* The supplied domain \"' + argDomain + '\" does not contain at least one colon\n')
    cli.usage()
    return 1
}

// major
def argMajor=options.getProperty('major')

if (! argMajor.isInteger()) {
    println('* The supplied major version \"' + argMajor + '\" is not an integer\n')
    cli.usage()
    return 1
}

// rivtaprofile
def argRivtaProfile=options.getProperty('rivtaprofile')

if (! ( (argRivtaProfile.equals('rivtabp20')) || (argRivtaProfile.equals('rivtabp21')) ) ) {
    println('* The supplied rivta profile \"' + argRivtaProfile + '\" must be \"rivtabp20\" or \"rivtabp21\"\n')
    cli.usage()
    return 1
}

// Finally for v2 - assign parameters to v1 variables
def baseDir = dir
def domain = argDomain
def rivtaProfile = argRivtaProfile
def version = argMajor

// Continue with V1 version as before.

def coreSchemaDirectories = getAllDirectoriesMatching(baseDir, /core_components/)
def coreSchemasFiles = getAllFilesMatching(coreSchemaDirectories[0], ".*\\.xsd")

def pattern = getFilePattern(rivtaProfile, version)

def wsdlFiles = getAllFilesMatching(baseDir, pattern)
if (wsdlFiles.isEmpty()){
	println "NOTE! No wsdl files found under dir ${baseDir}"
	return
}

def pom = getTemplatePom()

updateArtifactInformation(pom, domain, version)
addJaxWsInformation(pom, wsdlFiles, coreSchemasFiles)
printPom(baseDir, pom, rivtaProfile)

assert '${schema.path}/subdomain/Service/Service_1.1_RIVTABP20.wsdl' == getRelativeSchemaPath("/absolute/path/to/a/service/trunk/schemas/subdomain/Service/Service_1.1_RIVTABP20.wsdl")
assert "se.v1.something" == updateMajorVersionToFitJavaPackageConvention("se.1.something")
assert "urn.riv.service" == makeValidJavaPackageName("urn:riv:Service")
assert "se.riv.service" == makePackageNameStartWithSE("urn.riv.service")
assert ".*_1.*(?i)rivtabp21/.wsdl" == getFilePattern("rivtabp21", "1").replaceAll("\\\\","/")