/**
 * ///////////////////////////////////////////////////////////////////////
 * //                                                                   //
 * //   This script verifies the folder structure of a service domain   //
 * //                                                                   //
 * ///////////////////////////////////////////////////////////////////////
 *
 * @author Peter Hernfalk
 * Last update: 2014-01-11

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
structureTemplate[0]  = [subPath:"branches", mandatoryContent:""]
structureTemplate[1]  = [subPath:"tags", mandatoryContent:""]
structureTemplate[2]  = [subPath:"trunk", mandatoryContent:""]
structureTemplate[3]  = [subPath:"trunk/code_gen", mandatoryContent:""]
structureTemplate[4]  = [subPath:"trunk/code_gen/wcf", mandatoryContent:""]
structureTemplate[5]  = [subPath:"trunk/code_gen/jaxws", mandatoryContent:""]
structureTemplate[6]  = [subPath:"trunk/docs", mandatoryContent:".doc*"]
structureTemplate[7]  = [subPath:"trunk/schemas", mandatoryContent:""]
structureTemplate[8]  = [subPath:"trunk/schemas/core_components", mandatoryContent:".xsd"] //An empty folder is allowed for core_components but (probably) not for interactions
structureTemplate[9]  = [subPath:"trunk/schemas/interactions", mandatoryContent:".wsdl,.xsd"]

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

    //-----Parameter: cf (checkoutfiles)
    checkoutFiles = false
    def argCheckoutFiles=options.getProperty('checkoutfiles')
    if (argCheckoutFiles.toString().toLowerCase() == "true") {
        checkoutFiles = true
    }

    //-----Parameter: dd (domaindir)
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

    //-----Parameter: dn (domainname)
    def argDomainName=options.getProperty('domainname')
    if (argDomainName.toString().length() == 0) {
        log('* The supplied domain name seems to be empty\n', true)
        cli.usage()
        return 1
    }
    usedDomain = argDomainName

    //-----Parameter: l (useOptionalLogging)
    useOptionalLogging = false
    def arguseOptionalLogging=options.getProperty('useOptionalLogging')
    if (arguseOptionalLogging.toString().toLowerCase() == "true") {
        useOptionalLogging = true
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
    log("Domain structure, root: " + domainStructure, false)

    //-----Extract subdirectories and make a call to the verification method for each subdirectory
    def dir = new File(domainStructure)
    dir.eachDir { subDir ->
        if ((subDir.name.contains('.svn')) == false) {
            log("", true)
            log("Verifying subdir: " + subDir.name, true)
            if (verifySubDomainStructure(domainStructure, subDir.name) == false) {
               returnCode = 1
               return
            }
        }
    }

    //-----Failed verification sets the return code to: 1
    //returnCode = 1
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

                /////////////////////////////////////////////////////////////////////////
                //log("..folderPath: " + folderPath.toString().getAt(from..to), true)
                //log("..mandatoryContent: " + structureTemplate[it].mandatoryContent, true)
                if (structureTemplate[it].mandatoryContent.size() > 0) {
                    log("..verifying mandatoryContent: " + structureTemplate[it].mandatoryContent + " in " + folderPath.toString().getAt(from..to), true)
                }
                if (structureTemplate[it].mandatoryContent != "") {
                    if (verifyFolderContentsAgainstStructureRules(folderPath, structureTemplate[it].mandatoryContent) == false) {
                        log("....failed verification of folder contents for " + folderPath.toString().getAt(from..to), true)
                        verificationResult[it] = false
                        result = false
                    }
                }
                /////////////////////////////////////////////////////////////////////////

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


/* Verifies that the given domain path has contents that follows the domain structure rules */
def verifyFolderContentsAgainstStructureRules(folderPath, mandatoryContentInFolder) {
    returnValue = false

    //-----Splits mandatoryContentInFolder into an array and verifies each entry
    patternArray = mandatoryContentInFolder.tokenize(',')
    List<File> files
    if (patternArray.size() > 0) {
        patternArray.size().times() {
            index = it
            files = new File(folderPath.toString()).listFiles().findAll { it.name =~ patternArray[index] }
        }
    }

    if (files.size() >= patternArray.size()) {
        returnValue = true
    }

    log(".... " + returnValue + " Rule: " + mandatoryContentInFolder + " folder: " + folderPath + " files: " + files, true)

    return returnValue
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
    verifyServiceDomainStructure(localRIVTATargetFolder+"/"+usedDomain+"/")
}

//-----Exit the script execution and return the return code to the caller
log("", true)
log("Return code from the script: " + returnCode, true)
return returnCode