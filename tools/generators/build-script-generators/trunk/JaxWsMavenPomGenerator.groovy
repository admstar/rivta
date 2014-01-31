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
 * Version 1.1 - 2014-01-31
 * The generated script is placed in a code_gen directory
 * The path to schema files in the generated POM will start with ../../ instead of ../ to match Konfigurationsstyrningen
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
	def jaxwsDir = "${baseDir}/code_gen/jaxws"
	new File(jaxwsDir).deleteDir()
	new File(jaxwsDir).mkdirs()

	def smb = new StreamingMarkupBuilder().bind {
		mkp.declareNamespace(
				xsi: "http://www.w3.org/2001/XMLSchema-instance",
				'':"http://maven.apache.org/POM/4.0.0"
				)
		mkp.yield pom
	}

	String newPom = XmlUtil.serialize(smb)
	new File("${jaxwsDir}/pom.xml").write(newPom, "UTF-8");
}

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
	println "code_gen/jaxws/pom.xml"
	return
}

def baseDir = new File(args[0])
def domain = args[1]
def rivtaProfile = args[2]
def version = args[3]

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