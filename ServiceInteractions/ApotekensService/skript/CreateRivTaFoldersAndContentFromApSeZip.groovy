import groovy.io.FileType

// Skriptet startas i rot-mappen i den uppackade zip-filen
// Uppdatera TARGET_ROOT_DIR och SOURCE_CORE_COMPONENTS_DIR med dina lokala sökvägar
class Constants {
	def DOMAIN_NAME="se.apotekensservice"
	def MAJOR_VERSION="1"
	def MINOR_VERSION="0"
	def RIVTA_VERSION="20"
	def SOURCE_ROOT_DIR = new File(".").name
	def TARGET_ROOT_DIR = "/Users/johan/Documents/Development/Projects/SKL/rivta/ServiceInteractions/riv/se_apotekensservice"
	def SOURCE_CORE_COMPONENTS_DIR = "/Users/johan/Documents/Development/Projects/SKL/rivta/ServiceInteractions/ApotekensService/common_components"
}

Constants c = new Constants()

new File(c.SOURCE_ROOT_DIR).eachDir { subDomainSourceDir ->
	println ""
	println "Begin processing subdomain " + subDomainSourceDir.name
	println "-------------------------------------------------------"
	createSubDomainStructureAndContent(subDomainSourceDir, c)
}

def createSubDomainStructureAndContent(def subDomainSourceDir, Constants c) {
	// Create target subdomain folder
	def targetSubDomainDirPath = "${c.TARGET_ROOT_DIR}/${subDomainSourceDir.name}"
	new File(targetSubDomainDirPath).mkdir()
	println "Created subdomain directory " + targetSubDomainDirPath
	
	// Create trunk/branches/tags folders
	def domainDir = new File(targetSubDomainDirPath)
	new File("${targetSubDomainDirPath}/trunk").mkdir()
	new File("${targetSubDomainDirPath}/tags").mkdir()
	new File("${targetSubDomainDirPath}/branches").mkdir()
	println "Created trunk, tags and branches in " + targetSubDomainDirPath
	
	// Create Schemas folder
	def schemasDirPath = "${domainDir.path}/trunk/schemas"
	new File(schemasDirPath).mkdir()
	println "Created schema directory: " + schemasDirPath
	
	// Create core_components folder
	def coreComponentDirPath = "${schemasDirPath}/core_components"
	new File(coreComponentDirPath).mkdir()
	println "Created core_components directory: " + coreComponentDirPath
	
	// Copy all common component files from source dir to target dir
	new File(c.SOURCE_CORE_COMPONENTS_DIR).eachFile(FileType.FILES) {core_component ->
		
		new File("${coreComponentDirPath}/${core_component.name}") << core_component.bytes
		println "Copied " + core_component.name + " to directory " + coreComponentDirPath
	}
	
	// Copy the service domain common schema into the target common components folder
	def domain_core_components_schema_file_name = "${c.DOMAIN_NAME}_${subDomainSourceDir.name}_${c.MAJOR_VERSION}.${c.MINOR_VERSION}.xsd"
	new File("${coreComponentDirPath}/${domain_core_components_schema_file_name}") << new File("${subDomainSourceDir.path}/${domain_core_components_schema_file_name}").bytes
	println "Copied service schema into file " + "${coreComponentDirPath}/${domain_core_components_schema_file_name}"
	
	// Create interaction directory
	def interactionsDirPath = "${schemasDirPath}/interactions"
	new File(interactionsDirPath).mkdir()
	println "Created interaction directory for subdmomain: " + interactionsDirPath
	
	//Copy each pair of WSDL + service schema into a sub-directory structure
	subDomainSourceDir.eachFile(FileType.FILES) {sourceFile ->
		if (sourceFile.name.endsWith("rivtabp${c.RIVTA_VERSION}.wsdl")) {
			def serviceContractName = "${sourceFile.name}" - "Interaction_${c.MAJOR_VERSION}.${c.MINOR_VERSION}_rivtabp${c.RIVTA_VERSION}.wsdl"
			
			// Create service interaction directory
			def serviceInteractionDirPath = "${interactionsDirPath}/${serviceContractName}Interaction"
			new File(serviceInteractionDirPath).mkdir()
			println "Created service interaction directory: " + serviceInteractionDirPath
			
			// Copy wsdl into service interaction directory
			new File("${serviceInteractionDirPath}/${sourceFile.name}") << sourceFile.bytes
			println "Created target wsdl: " + "${serviceInteractionDirPath}/${sourceFile.name}"
			
			// Copy service schema file
			def serviceSchemaFileName = "${serviceContractName}Responder_${c.MAJOR_VERSION}.${c.MINOR_VERSION}.xsd"
			new File("${serviceInteractionDirPath}/${serviceSchemaFileName}") << new File("${subDomainSourceDir.path}/${serviceSchemaFileName}").bytes		
			println "Created target service schema: " + "${serviceInteractionDirPath}/${serviceSchemaFileName}"	
		}
	}
}