
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
 * Last update: 2013-11-15

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
useLogging = true


/**
 //////////////////////////////////////////////////////////
 //////////////////////////////////////////////////////////
 //////////////////// Methods /////////////////////////////
 //////////////////////////////////////////////////////////
 //////////////////////////////////////////////////////////
 */

//---------------------------------------- Usage settings ----------------------------------------//
//useLogging = true
//-----------------------------------------------------------------------------------------------//

/** Fetches values from parameters, saves the values in variables */
def getValuesFromParameters() {
    me = this.class.name
    mecall = me + ' [options]'
    medesc = """\
    This script requires Groovy 1.8.1 or later.
    The purpose of the script is to verify the folder structure of a specific service domain.
    """

    def cli = new CliBuilder(usage: mecall, header: 'Options:', footer: medesc)
    cli.c(longOpt: 'checkoutfiles', args:1, required:false, argName:'Optional: true', 'If "true" then files will be downloaded to the target directory')
    cli.d(longOpt: 'domainname', args:1, required:true, argName:'Domain name', 'Name of the domain')
    cli.l(longOpt: 'uselogging', args:1, required:false, argName:'Optional: true', 'If "true" then the script logs output to the console')
    cli.t(longOpt: 'targetdir', args:1, required:true, argName:'Target directory', 'Directory in which service domain files exists. If the checkoutfiles parameter is set to "y" then they will be downloaded to this folder')

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
        log('* The supplied domain name seems to be empty\n')
        cli.usage()
        return 1
    }
    usedDomain = argDomainName

    useLogging = false
    def argUseLogging=options.getProperty('uselogging')
    if (argUseLogging.toString().toLowerCase() == "true") {
        useLogging = true
    }

    def argTargetDir=options.getProperty('targetdir')
    if (argTargetDir.toString().length() == 0) {
        log('* The supplied target directory name seems to be empty\n')
        cli.usage()
        return 1
    }
    localRIVTATargetFolder = argTargetDir
    return true
}

/** Logs text to standard output */
def log(text) {
    //-----2do: add logic that directs the output to configured target
    if (useLogging == true) {
        println "VerifyServiceDomainFolder: " + text
    }
}

def downloadFileStructureFromRivtaSite(String domainNameRivta) {

    RIVTACheckoutCommandToFolder = RIVTACheckoutCommand + RIVTADomainFolder + domainNameRivta.trim() + "/ " + localRIVTATargetFolder
    log(RIVTACheckoutCommandToFolder)

    //-----Delete the local target folder before download
    new File(localRIVTATargetFolder).deleteDir()

    //-----Download files from the RIV-TA site to the local target folder
    def process = RIVTACheckoutCommandToFolder.execute()
    process.in.eachLine { line -> log("Downloading: " + line) }

}


/* Verifies that that the service domains structure and contents are correct on the rivta site */
def verifyServiceDomainStructure(String domainStructure) {
    //-----2do: add logic that verifies the downloaded domain file structure
    log("Domain structure, root: " + domainStructure)

    //-----Extract subdirectories and make a call to the verification method for each subdirectory
    def dir = new File(domainStructure)
    dir.eachDir { subDir ->
        if ((subDir.name.contains('.svn')) == false) {
            log("\n\nSubdir: " + subDir.name )
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

            //-----The found directory should be verified against the directory template
            //-----The directory contents should also be verified, so that mandatory files exist in the directory
            //ruleVerificationResult = verifySubDomainAgainstStructureRules(file.name, file)
            verificationResult = verifySubDomainAgainstStructureRules(file.name, file, verificationResult)
            //log("verificationResult: " + verificationResult)
            //-----2do: use ruleVerificationResult
        }
    }

    structureTemplate.size.times {
        if (verificationResult[it] == false) {
            log("'" + subDomainName + "' is missing the subfolder: '" + structureTemplate[it].subPath + "'")
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
    log("Domain name = " + usedDomain)

    //-----Download the domain structure that should be verified
    if (checkoutFiles == true) {
        downloadFileStructureFromRivtaSite(usedDomain)
    }

    //-----Iterate through the downloaded structure and verify that it's correct
    verifyServiceDomainStructure(localRIVTATargetFolder)
}

//-----Exit the script execution and return the return code to the caller
log("\n\nreturnCode: " + returnCode)
return returnCode