#!/usr/bin/env groovy

package se.skl.tp.rivtatools


import groovy.io.FileType
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

@Grab(group='dom4j', module='dom4j', version='1.6.1')
import org.dom4j.io.SAXReader


/**
 *Script to generate valid jaxws scripts (pom.xml and wcf bat) to be able to generate JAX-WS services. Run the script to get information on input arguments.
 */

def getAllFilesMathcing(direcory, pattern){
	def filesFound = []
	direcory.traverse(type:FileType.FILES, nameFilter: ~pattern){ fileFound -> filesFound << fileFound }
	filesFound.each { fileFound -> println "Files to process: ${fileFound.name}" }
	return filesFound
}

def getTemplatePom(){
	def pomTemplate = new XmlSlurper().parse(new File("src/main/resources/jaxws-pom-template.xml").asWritable())
	return pomTemplate
}

def getFilePattern(rivtaProfile, version){
	def pattern = ".*_${version}.*(?i)${rivtaProfile}\\.wsdl"
	return pattern
}

def updateArtifactInformation(pom, domain, version){
	pom.artifactId = domain.replace(/./,"-") + "-schemas"
	pom.name = domain.replace(/./,"-") + "-schemas"
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

def addJaxWsInformation(pom, wsdlFiles){

	def pluginConfiguration = pom.build.plugins.plugin[0].executions.execution[0].configuration

	wsdlFiles.each { wsdlFile ->

		def rivNameSpaces = new SAXReader().read(wsdlFile).getRootElement().declaredNamespaces().grep{it.getText().contains("urn:riv")}

		pluginConfiguration.wsdlOptions.appendNode {
			wsdloption {
				wsdl(getRelativeSchemaPath(wsdlFile.absolutePath))
				extraargs() {
					extraarg('-p')
					extraarg("http://www.w3.org/2005/08/addressing=org.w3c.addressing.v1")

					rivNameSpaces.each {rivNamespace ->
						def urn = rivNamespace.getText()
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
	def jaxwsDir = "${baseDir}/generated-scripts/jaxws"
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
	new File("${jaxwsDir}/pom_${rivtaProfile}.xml").write(newPom, "UTF-8");
}

//if( args.size() < 4){
//	println "This is a tool that will generate pom.xml. Before running this tool it is important to make sure that the directory "
//	println "structure is correct. This script will assume under the [domainDir] following:"
//	println "/schemas/interaction/<all interaction schemas>"
//	println "/schemas/core_components/<all core schemas>"
//	println ""
//	println "Required parameters: domain directory [domainDir], domain [domain], RIV TA Profile [rivtaProfile], Version [version] \n"
//	println "PARAMETERS DESCRIPTION:"
//	println "[domainDir] is the base direcory where this script will start working, e.g /repository/rivta/ServiceInteractions/riv/crm/scheduling/trunk "
//	println "[domain] is the name of the domain to process, e.g crm.scheduling"
//	println "[rivtaprofile], e.g rivtabp20"
//	println "[version], e.g 1 or 2 or 3 etc"
//	println ""
//	println "OUTPUT:"
//	println "directory /generated-scripts/jaxws including pom.xml"
//	return
//}
//
//def baseDir = new File(args[0])
//def domain = args[1]
//def rivtaProfile = args[2]
//def version = args[3]

/*TEST SETUP*/
def baseDir = new File('/Users/hansthunberg/svn-views/rivta/ServiceInteractions/riv/crm/scheduling/trunk')
def rivtaProfile = "rivtabp20"
def version = "1"
def domain = "crm.scheduling"

def pattern = getFilePattern(rivtaProfile, version)
def wsdlFiles = getAllFilesMathcing(baseDir, pattern)
def pom = getTemplatePom()

updateArtifactInformation(pom, domain, version)
addJaxWsInformation(pom, wsdlFiles)
printPom(baseDir, pom, rivtaProfile)

assert '${schema.path}/subdomain/Service/Service_1.1_RIVTABP20.wsdl' == getRelativeSchemaPath("/absolute/path/to/a/service/trunk/schemas/subdomain/Service/Service_1.1_RIVTABP20.wsdl")
assert "se.v1.something" == updateMajorVersionToFitJavaPackageConvention("se.1.something")
assert "urn.riv.service" == makeValidJavaPackageName("urn:riv:Service")
assert "se.riv.service" == makePackageNameStartWithSE("urn.riv.service")
assert ".*_1.*(?i)rivtabp21/.wsdl" == getFilePattern("rivtabp21", "1").replaceAll("\\\\","/")