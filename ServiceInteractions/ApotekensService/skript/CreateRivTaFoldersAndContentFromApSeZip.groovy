import groovy.io.FileType

// Skriptet startas i rot-mappen i den uppackade zip-filen
// Skriptet kopierar filerna i från ApSe:s distribution till den struktur som gäller för RIVTA. 
// Kataloger som redan finns skapas inte om.
// En fil med samma namn i destinationsstrukturen, tas filen bort innan den nya kopieras in.
//
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
	mkMissingDir(targetSubDomainDirPath)
	
	// Create trunk/branches/tags folders
	// def domainDir = new File(targetSubDomainDirPath)
	mkMissingDir("${targetSubDomainDirPath}/trunk")
	mkMissingDir("${targetSubDomainDirPath}/tags")
	mkMissingDir("${targetSubDomainDirPath}/branches")
	
	// Create Schemas folder
	def schemasDirPath = "${targetSubDomainDirPath}/trunk/schemas"
	mkMissingDir(schemasDirPath)
	
	// Create core_components folder
	def coreComponentDirPath = "${schemasDirPath}/core_components"
	mkMissingDir(coreComponentDirPath)
	
	// Copy all common component files from source dir to target dir
	new File(c.SOURCE_CORE_COMPONENTS_DIR).eachFile(FileType.FILES) {core_component ->
		copyFileWithOverwright(core_component, "${coreComponentDirPath}/${core_component.name}")		
	}
	
	// Copy the service domain common schema into the target common components folder
	def domain_core_components_schema_file_name = "${c.DOMAIN_NAME}_${subDomainSourceDir.name}_${c.MAJOR_VERSION}.${c.MINOR_VERSION}.xsd"
	copyFileWithOverwright("${subDomainSourceDir.path}/${domain_core_components_schema_file_name}", "${coreComponentDirPath}/${domain_core_components_schema_file_name}")
	
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
			copyFileWithOverwright(sourceFile ,"${serviceInteractionDirPath}/${sourceFile.name}")
			
			// Copy service schema file
			def serviceSchemaFileName = "${serviceContractName}Responder_${c.MAJOR_VERSION}.${c.MINOR_VERSION}.xsd"
			copyFileWithOverwright("${subDomainSourceDir.path}/${serviceSchemaFileName}", "${serviceInteractionDirPath}/${serviceSchemaFileName}")
		}
	}
}

def copyFileWithOverwright(String sourcePath, String targetPath) {
		File sourceFile = new File(sourcePath)
		copyFileWithOverwright(sourceFile,targetPath)
}

def copyFileWithOverwright(File sourceFile, String targetPath) {
		def newFile = new File(targetPath)
		if (newFile.delete()) {		
			println "Existing file deleted: " + targetPath
		}
		// newFile.createNewFile()
		newFile << sourceFile.bytes
		println "Created file: ${targetPath}"
}

def mkMissingDir(String path) {
	def dir = new File(path)
	if (!dir.exists()) {
		dir.mkdir()
		println "Created directory: ${path}"	
	}
}