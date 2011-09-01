#!/usr/bin/env groovy

package se.skl.tp.rivtatools

import groovy.io.FileType


/**
 *Script to generate valid jaxws scripts (pom.xml and wcf bat) to be able to generate JAX-WS services. Run the script to get information on input arguments.
 */

def getAllFilesMathcing(direcory, pattern){
	def filesFound = []
	direcory.traverse(type:FileType.FILES, nameFilter: ~pattern){ fileFound -> filesFound << fileFound }
	filesFound.each { fileFound -> println "Files to process: ${fileFound.name}" }
	return filesFound
}

def getTemplateWzf(baseDir, rivtaProfile){
	def wcfDir = "${baseDir}/generated-scripts/wcf"
	new File(wcfDir).deleteDir()
	new File(wcfDir).mkdirs()

	def wcfTemplate = new File("${wcfDir}/generate-src-${rivtaProfile}.bat") << new File("src/main/resources/wcf-generate-src-template.bat").asWritable()
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
	domain += '.Interactions'
	domain = domain.replaceAll(/\.(\w)/) { fullMatch, firstCharacter -> firstCharacter.toUpperCase() }
	domain = domain[0].toUpperCase() + domain[1..-1]
	return "${domain}.cs"
}

def buildCorrectNamespace(domain,version){
	namespace = "Riv.${domain}.Schemas"
	namespace = namespace.replaceAll(/\.(\w)/) { fullMatch, firstCharacter -> "." + firstCharacter.toUpperCase() }
	return namespace += ".v${version}"
}

def addJaxWsInformation(wcf, wsdlFiles){

	def newLine = System.getProperty("line.separator")
	
	

	wsdlFiles.eachWithIndex { wsdlFile, pos ->
		println "WSDL Schema to process for wcf: ${wsdlFile.name}"
		def slashyPath = getRelativeSchemaPath(wsdlFile.absolutePath).replaceAll("/","\\\\")
		wcf.append("SET W${pos}=%SCHEMADIR%" + slashyPath + newLine)
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
	wcf.append('SET OUTFILE=/out:wcf\\generated-src\\' + buildOutFileNameForWcf(domain) + newLine)
	wcf.append('SET APPCONFIG=/config:wcf\\generated-src\\app.config' + newLine)
	wcf.append('SET NAMESPACE=/namespace:*,' + buildCorrectNamespace(domain, version) + newLine)
	wcf.append('SET SCHEMADIR="schemas\\interactions' + newLine)
	wcf.append('SET SVCUTIL="svcutil.exe"' + newLine)
	wcf.append('SET XCORE=%SCHEMADIR%\\core_components\\*.xsd' + newLine)
	wcf.append('%SVCUTIL% /language:cs %OUTFILE% %APPCONFIG% %NAMESPACE% %SCHEMAS%'+ newLine)
	wcf.append(newLine)
	wcf.append('CD wcf' + newLine)
	wcf.append("ECHO Generating Service contract .Net Binding interfaces and classes for ${domain} Release ${version}" +   newLine)
	wcf.append('ECHO I DotNetprojektet ska du ta lagga till referens till System.ServiceModel' + newLine)
}


if( args.size() < 4){
	println "This is a tool that will generate wcf sources bat. Before running this tool it is important to make sure that the directory "
	println "structure is correct. This script will assume under the [domainDir] following:"
	println "/schemas/interaction/<all interaction schemas>"
	println "/schemas/core_components/<all core schemas>"
	println ""
	println "Required parameters: domain directory [domainDir], domain [domain], RIV TA Profile [rivtaProfile], Version [version] \n"
	println "PARAMETERS DESCRIPTION:"
	println "[domainDir] is the base direcory where this script will start working, e.g /repository/rivta/ServiceInteractions/riv/crm/scheduling/trunk "
	println "[domain] is the name of the domain to process, e.g crm.scheduling"
	println "[rivtaprofile], e.g rivtabp20"
	println "[version], e.g 1 or 2 or 3 etc"
	println ""
	println "OUTPUT:"
	println "directory /generated-scripts/jaxws including pom.xml"
	return
}

def baseDir = new File(args[0])
def domain = args[1]
def rivtaProfile = args[2]
def version = args[3]

///*TEST SETUP*/
//def baseDir = new File('/Users/hansthunberg/svn-views/rivta/ServiceInteractions/riv/crm/scheduling/trunk')
//def rivtaProfile = "rivtabp20"
//def version = "1"
//def domain = "crm.scheduling"

def pattern = getFilePattern(rivtaProfile, version)
def wsdlFiles = getAllFilesMathcing(baseDir, pattern)
def wcf = getTemplateWzf(baseDir, rivtaProfile)

addJaxWsInformation(wcf, wsdlFiles)
addWcfScriptInformation(wcf, wsdlFiles, domain, version)

assert "CrmSchedulingInteractions.cs" == buildOutFileNameForWcf("crm.scheduling")
assert "Riv.Crm.Scheduling.Schemas.v1" == buildCorrectNamespace("crm.scheduling","1")
assert '%SCHEMADIR%/subdomain/Service/Service_1.1_RIVTABP20.wsdl' == getRelativeSchemaPath("/absolute/path/to/a/service/trunk/schemas/subdomain/Service/Service_1.1_RIVTABP20.wsdl")
assert ".*_1.*(?i)rivtabp21/.wsdl" == getFilePattern("rivtabp21", "1").replaceAll("\\\\","/")