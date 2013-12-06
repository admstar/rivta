
/**
 * ///////////////////////////////////////////////////////////////////////
 * //                                                                   //
 * //   This script verifies the folder structure of a service domain   //
 * //                                                                   //
 * ///////////////////////////////////////////////////////////////////////
 *
 * Information sources used by this script:
 * - Main source: the Excel document describing service domains
 *
 *
 * @author Peter Hernfalk
 * Last update: 2013-12-06

 */

import groovy.lang.Binding
import groovy.io.FileType

checkoutFiles = false
domainFolderStructure = []
localRIVTATargetFolder = ""
returnCode = 0
RIVTADomainFolder = "http://rivta.googlecode.com/svn/ServiceInteractions/riv/"
RIVTACheckoutCommand = "svn checkout "
structureTemplate = []
structureTemplate[0]  = [subPath:"branches", mandatoryContent:"", optionalContent:""]
structureTemplate[1]  = [subPath:"tags", mandatoryContent:"", optionalContent:""]
structureTemplate[2]  = [subPath:"trunk", mandatoryContent:"", optionalContent:""]
structureTemplate[3]  = [subPath:"trunk/code_gen", mandatoryContent:"", optionalContent:""]
structureTemplate[4]  = [subPath:"trunk/code_gen/wcf", mandatoryContent:"", optionalContent:""]
structureTemplate[5]  = [subPath:"trunk/code_gen/jaxws", mandatoryContent:"", optionalContent:""]
structureTemplate[6]  = [subPath:"trunk/docs", mandatoryContent:"*.doc", optionalContent:""]
structureTemplate[7]  = [subPath:"trunk/schemas", mandatoryContent:"", optionalContent:""]
structureTemplate[8]  = [subPath:"trunk/schemas/core_components", mandatoryContent:"*.xsd", optionalContent:""] //An empty folder is allowed for core_components but (probably) not for interactions
structureTemplate[9]  = [subPath:"trunk/schemas/interactions", mandatoryContent:"*.wsdl,*.xsd", optionalContent:""]

usedDomain = ""
useOptionalLogging = true


/**
 //////////////////////////////////////////////////////////
 //////////////////////////////////////////////////////////
 //////////////////// Methods /////////////////////////////
 //////////////////////////////////////////////////////////
 //////////////////////////////////////////////////////////
 */

/** Fetches values from parameters, saves the values in variables */
def getValuesFromParameters() {
    me = this.class.name
    mecall = me + ' [options]'
    medesc = """\
    This script requires Groovy 1.8.1 or later.
    The purpose of the script is to verify the folder structure of a specific service domain.
    """

    def cli = new CliBuilder(usage: mecall, header: 'Options:', footer: medesc)
    cli.cf(longOpt: 'checkoutfiles', args:1, required:true, argName:'Optional: true', 'If "true" then files will be downloaded to the target directory')
    cli.dd(longOpt: 'domaindir', args:1, required:true, argName:'Domain directory', 'Directory in which service domain files exists. If the checkoutfiles parameter is set to "y" then they will be downloaded to this folder')
    cli.dn(longOpt: 'domainname', args:1, required:true, argName:'Domain name', 'Name of the domain')
    cli.l(longOpt: 'useOptionalLogging', args:1, required:false, argName:'Optional: true', 'If "true" then the script logs extra output to the console')

    //-----Verify all parameters
    def options = cli.parse(args)
    if (!options) return

    checkoutFiles = false
    def argCheckoutFiles=options.getProperty('checkoutfiles')
    if (argCheckoutFiles.toString().toLowerCase() == "true") {
        checkoutFiles = true
    }

    def argDomainName=options.getProperty('domainname')
    if (argDomainName.toString().length() == 0) {
        log('* The supplied domain name seems to be empty\n', true)
        cli.usage()
        return 1
    }
    usedDomain = argDomainName

    useOptionalLogging = false
    def arguseOptionalLogging=options.getProperty('useOptionalLogging')
    if (arguseOptionalLogging.toString().toLowerCase() == "true") {
        useOptionalLogging = true
    }
    /*
    def argTargetDir=options.getProperty('targetdir')
    if (argTargetDir.toString().length() == 0) {
        log('* The supplied target directory name seems to be empty\n', true)
        cli.usage()
        return 1
    }
    localRIVTATargetFolder = argTargetDir
    if (localRIVTATargetFolder.endsWith("/") == false) {
        localRIVTATargetFolder += "/"
    }
    */
    def argDomainDir=options.getProperty('domaindir')
    if (argDomainDir.toString().length() == 0) {
        log('* The supplied domain directory name seems to be empty\n', true)
        cli.usage()
        return 1
    }
    localRIVTATargetFolder = argDomainDir
    if (localRIVTATargetFolder.endsWith("/") == false) {
        localRIVTATargetFolder += "/"
    }

    return true
}

/** Logs text to standard output */
def log(String text, boolean logEntryIsMandatory) {
    if (logEntryIsMandatory == true) {
        println this.class.name + " - " + text
    } else if (useOptionalLogging == true && logEntryIsMandatory == false) {
        println this.class.name + " - " + text
    }
}

def downloadFileStructureFromRivtaSite(String domainNameRivta) {

    RIVTACheckoutCommandToFolder = RIVTACheckoutCommand + RIVTADomainFolder + domainNameRivta.trim() + "/ " + localRIVTATargetFolder
    log(RIVTACheckoutCommandToFolder, false)

    //-----Delete the local target folder before download
    new File(localRIVTATargetFolder).deleteDir()

    //-----Download files from the RIV-TA site to the local target folder
    def process = RIVTACheckoutCommandToFolder.execute()
    process.in.eachLine { line -> log("Downloading: " + line, false) }

}


/* Verifies that that the service domains structure and contents are correct on the rivta site */
def verifyServiceDomainStructure(String domainStructure) {
    //-----2do: add logic that verifies the downloaded domain file structure
    log("Domain structure, root: " + domainStructure, false)

    //-----Extract subdirectories and make a call to the verification method for each subdirectory
    def dir = new File(domainStructure)
    dir.eachDir { subDir ->
        if ((subDir.name.contains('.svn')) == false) {
            log("\n", true)
            log("\nSubdir: " + subDir.name, false)
            if (verifySubDomainStructure(domainStructure, subDir.name) == false) {
               return
            }
        }
    }

    //-----Failed verification sets the return code to: 1
    returnCode = 1
}


/* Verifies that that the service domains structure and contents are correct on the rivta site */
def verifySubDomainStructure(String domainStructure, String subDomainName) {

    verificationResult = [false, false, false, false, false, false, false, false, false, false]

    def dir = new File(domainStructure+subDomainName)
    dir.eachFileRecurse (FileType.DIRECTORIES) { file ->
        //-----Filter out all directories except the '.svn' directories
        if ((file.toString().contains('.svn')) == false) {

            //-----Verification of the found directory against the directory template
            //-----The directory contents should also be verified, so that mandatory files exist in the directory
            verificationResult = verifySubDomainAgainstStructureRules(file.name, file, verificationResult)
        }
    }

    structureTemplate.size.times {
        if (verificationResult[it] == false) {
            log("'" + subDomainName + "' is missing the subfolder: '" + structureTemplate[it].subPath + "'", true)
        }

    }
    return !verificationResult.contains(false)
}


/* Verifies that the given domain path has contents that follows the domain structure rules */
def verifySubDomainAgainstStructureRules(leafFolderName, folderPath, verificationResult) {

    result = false
    structureTemplate.size.times {
        //-----if the templates subPath contains at least one "/" then verify the end of the folder path with the template's subpath
        if (structureTemplate[it].subPath.indexOf("/") > 0) {
            from = folderPath.toString().size()-structureTemplate[it].subPath.size()
            to = folderPath.toString().size()-1
            if (folderPath.toString().getAt(from..to) == structureTemplate[it].subPath) {
                verificationResult[it] = true
                result = true
                true
            }
        }  else if (leafFolderName.toString() == structureTemplate[it].subPath) {
            verificationResult[it] = true
            result = true
            true
        }
    }
    return verificationResult
}


/**
 ////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////
 //////////////////// Executing section /////////////////////////////
 ////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////
 */

//-----Fetch the domain name from the script parameter
if (getValuesFromParameters() == true) {
    log("checkoutFiles: " + checkoutFiles, false)
    log("localRIVTATargetFolder: " + localRIVTATargetFolder, false)
    log("usedDomain: " + usedDomain, false)
    log("useOptionalLogging: " + useOptionalLogging, false)

    //-----Download the domain structure that should be verified
    if (checkoutFiles == true) {
        downloadFileStructureFromRivtaSite(usedDomain)
    }

    //-----Iterate through the downloaded structure and verify that it's correct
    verifyServiceDomainStructure(localRIVTATargetFolder)
}

//-----Exit the script execution and return the return code to the caller

//*********Temporary test setting
//returnCode = 0
//*********Temporary test setting
log("\n\nreturnCode: " + returnCode, true)
return returnCode