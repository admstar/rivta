#!/usr/bin/env groovy

import groovy.io.FileType

/**
 *Script to generate a scvutil bat file for .Net WCF WSDL-first. Run the script to get information on input arguments.
 *
 * Revision history:
 *
 * Version 1.0
 * First version of this script.
 * 
 * Version 1.1 - 2014-01-31
 * The generated script is placed in a code_gen directory
 *
 * Version 1.2 - 2014-08-13
 * Fix relative path to be able to run the resultning script from the code_gen\wcf directory
 * Insert pragma directive at the top of the resulting c# file to remove compiler warning
 * Fix contributed by Kentor IT
 *
 */

def getAllFilesMathcing(direcory, pattern){
	def filesFound = []
	direcory.traverse(type:FileType.FILES, nameFilter: ~pattern){ fileFound -> filesFound << fileFound }
	filesFound.each { fileFound -> println "File to process: ${fileFound.name}" }
	return filesFound
}

def getTemplateWcf(baseDir, rivtaProfile){
	
	def template = '''
	@REM ---------------------------------------------------------------------------------
	@REM Generates c# WCF service contracts (interface), client proxies and wcf config
	@REM file for the WSDLs + XML Schemas of the service domain, using .Net WCF tool svcuti.exe
	@REM ---------------------------------------------------------------------------------
	@REM Licensed to the Apache Software Foundation (ASF) under one
	@REM or more contributor license agreements. See the NOTICE file
	@REM distributed with this work for additional information
	@REM regarding copyright ownership. Inera AB licenses this file
	@REM to you under the Apache License, Version 2.0 (the
	@REM "License"); you may not use this file except in compliance
	@REM with the License. You may obtain a copy of the License at
	@REM
	@REM http://www.apache.org/licenses/LICENSE-2.0
	@REM Unless required by applicable law or agreed to in writing,
	@REM software distributed under the License is distributed on an
	@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
	@REM KIND, either express or implied. See the License for the
	@REM specific language governing permissions and limitations
	@REM under the License.
	@REM ---------------------------------------------------------------------------------
	CD ..\\..
	
	SET SCHEMADIR=schemas
	
	'''
	
	def wcfDir = "${baseDir}/code_gen/wcf"
	new File(wcfDir).deleteDir()
	new File(wcfDir).mkdirs()

	def wcfTemplate = new File("${wcfDir}/generate-src-${rivtaProfile}.bat") << template
	return wcfTemplate
}

def getFilePattern(rivtaProfile, version){
	def pattern = ".*_${version}.*(?i)${rivtaProfile}\\.wsdl"
	return pattern
}


def getRelativeSchemaPath(absoluteSchemaPath){
	def startIndex = absoluteSchemaPath.indexOf('schemas')
	def relativeSchemaPath = absoluteSchemaPath.getAt(startIndex..-1)
	relativeSchemaPath = relativeSchemaPath.replace('schemas','')
	return '%SCHEMADIR%' + relativeSchemaPath
}

def buildOutFileNameForWcf(domain){
	domain += ':Interactions'
	domain = domain.replaceAll(/\:(\w)/) { fullMatch, firstCharacter -> firstCharacter.toUpperCase() }
	domain = domain[0].toUpperCase() + domain[1..-1]
	return "${domain}.cs"
}

def buildCorrectNamespace(domain,version){
	namespace = "Riv:${domain}:Schemas"
	namespace = namespace.replaceAll(/\:(\w)/) { fullMatch, firstCharacter -> "." + firstCharacter.toUpperCase() }
	return namespace += ".v${version}"
}

def addSchemaAndWsdlVariableAssignments(wcf, wsdlFiles){

	def newLine = System.getProperty("line.separator")
	
	wsdlFiles.eachWithIndex { wsdlFile, pos ->
		println "WSDL Schema to process for wcf: ${wsdlFile.name}"
		def slashyPath = getRelativeSchemaPath(wsdlFile.absolutePath).replaceAll("/","\\\\")
		wcf.append("SET W${pos}=" + slashyPath + newLine)
		wcf.append("SET X${pos}=%SCHEMADIR%\\interactions\\" + new File(wsdlFile.parent).name + "\\*.xsd" + newLine)
		wcf.append(newLine)
	}

	wcf.append('SET XCORE=%SCHEMADIR%\\core_components\\*.xsd' + newLine)
	wcf.append(newLine)
	
	wcf.append('SET SCHEMAS=%XCORE% ')
	wsdlFiles.eachWithIndex { wsdlFile, pos ->
		wcf.append("%W${pos}% %X${pos}% ")
	}
}

def addWcfScriptInformation(wcf, wsdlFiles, domain, version){
	
	def newLine = System.getProperty("line.separator")
	
	wcf.append(newLine)
	wcf.append(newLine)
	wcf.append('SET OUTFILE=code_gen\\wcf\\generated-src\\' + buildOutFileNameForWcf(domain) + newLine)
	wcf.append('SET PRAGMAFILE=code_gen\\wcf\\generated-src\\pragma.cs' + newLine)
	wcf.append('SET OUTPARAM=/out:%OUTFILE%' + newLine)
	wcf.append('SET APPCONFIG=/config:code_gen\\wcf\\generated-src\\app.config' + newLine)
	wcf.append('SET NAMESPACE=/namespace:*,' + buildCorrectNamespace(domain, version) + newLine)
	wcf.append('SET SVCUTIL="svcutil.exe"' + newLine)
	//wcf.append('SET XCORE=%SCHEMADIR%\\core_components\\*.xsd' + newLine)
	wcf.append('%SVCUTIL% /language:cs /syncOnly %OUTPARAM% %APPCONFIG% %NAMESPACE% %SCHEMAS%'+ newLine)
	wcf.append(newLine)
	wcf.append('ECHO Adding #pragma warning disable 1591' + newLine)
	wcf.append('ECHO #pragma warning disable 1591 > %PRAGMAFILE%' + newLine)
	wcf.append('TYPE %OUTFILE% >> %PRAGMAFILE%' + newLine)
	wcf.append('DEL %OUTFILE%' + newLine)
	wcf.append('REN %PRAGMAFILE% ' + buildOutFileNameForWcf(domain) + newLine)
	wcf.append(newLine)
	wcf.append('REM Strip \', ReplyAction="*"\' in server code to make it work' + newLine)
	wcf.append('powershell -NoProfile -command "Get-Content code_gen\\wcf\\generated-src\\' + buildOutFileNameForWcf(domain) + ' | %% { $_ -replace \'\\, ReplyAction.*\\)\\]\',\')]\' } | Set-Content code_gen\\wcf\\generated-src\\' + buildOutFileNameForWcf(domain).replace('.cs','-server.cs') + '"' + newLine)
	wcf.append(newLine)
	wcf.append('CD code_gen\\wcf' + newLine)
	wcf.append("ECHO Generating Service contract .Net Binding interfaces and classes for ${domain} Release ${version}" +   newLine)
	wcf.append('ECHO I DotNetprojektet ska du ta lagga till referens till System.ServiceModel' + newLine)
}


if( args.size() < 4){
	println "This is a tool that will generate a bat file with a svcutil command. The script requires the directory"
	println "structure to follow the RIVTA standard: "
	println "schemas/..."
	println "    ...interactions/<a folder per service interaction>/<service schemas and wsdl file>"
	println "    ...core_components/<all core schemas>"
	println ""
	println "Required parameters: domain directory [domainDir], service domain [domain], RIV TA Profile [rivtaProfile], Major version [version] \n"
	println "PARAMETERS DESCRIPTION:"
	println "[domainDir] is the base direcory where this script will start working, e.g /repository/rivta/ServiceInteractions/riv/crm/scheduling/trunk "
	println "[domain] is the name of the service domain to process, e.g crm:scheduling"
	println "[rivtaprofile], e.g rivtabp20"
	println "[version], Major version, e.g 1 or 2 or 3 etc"
	println "example invocation:"
	println "	groovy WcfSvcutilBatfileGenerator . ehr:scheduling rivtabp20 1"	
	println ""
	println "OUTPUT:"
	println "	code_gen/wcf/generate-src-<rivtaProfile>.bat"
	return
}

def baseDir = new File(args[0])
def domain = args[1]
def rivtaProfile = args[2]
def version = args[3]

def pattern = getFilePattern(rivtaProfile, version)

def wsdlFiles = getAllFilesMathcing(baseDir, pattern)
if (wsdlFiles.isEmpty()){
	println "NOTE! No wsdl files found under dir ${baseDir}"
	return
}

def wcf = getTemplateWcf(baseDir, rivtaProfile)

addSchemaAndWsdlVariableAssignments(wcf, wsdlFiles)
addWcfScriptInformation(wcf, wsdlFiles, domain, version)

assert "CrmSchedulingInteractions.cs" == buildOutFileNameForWcf("crm:scheduling")
assert "Riv.Crm.Scheduling.Schemas.v1" == buildCorrectNamespace("crm:scheduling","1")
assert '%SCHEMADIR%/subdomain/Service/Service_1.1_RIVTABP20.wsdl' == getRelativeSchemaPath("/absolute/path/to/a/service/trunk/schemas/subdomain/Service/Service_1.1_RIVTABP20.wsdl")
assert ".*_1.*(?i)rivtabp21/.wsdl" == getFilePattern("rivtabp21", "1").replaceAll("\\\\","/")