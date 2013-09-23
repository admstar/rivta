
/**
 * Created with IntelliJ IDEA.
 * User: peterhernfalk
 * Date: 2013-09-20
 * Time: 07:51
 * To change this template use File | Settings | File Templates.
 *
 * LEO's description:
 * Denna skall verifiera att en utpekad mapp här rooten i en mappstruktur som till fullo följer (det nya) konfigurationsstyrningsdokumentet.
 * Om det finns fel skall detta skrivas ut till stderror samt scriptet avslutas med rc=1. Om allt ok avslutas med rc=0
 */

import groovy.lang.Binding
import groovy.io.FileType

domainFolderStructure = []
//domainSubfolder = []
localRIVTATargetFolder = "/Users/peterhernfalk/Desktop/_Peter_Files/rivtadomain/"
loglevelDebug = "DEBUG"
loglevelError = "ERROR"
loglevelInfo = "INFO"
loglevelWarning = "WARNING"
returnCode = 0
RIVTADomainFolder = "http://rivta.googlecode.com/svn/ServiceInteractions/riv/"
RIVTACheckoutCommand = "svn checkout "
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
useLogging = true
//-----------------------------------------------------------------------------------------------//


/** Gets the domain name from the scripts parameter */
def getDomainNameFromParameter() {
    Binding binding = new Binding();
    int argNo = 1
    for (a in this.args) {
        binding.setProperty("arg$argNo", a);
        argNo += 1
    }
    log(loglevelDebug, binding.getProperty("arg1") + " " + binding.getProperty("arg2"))
    usedDomain = binding.getProperty("arg2")
}


/** Logs text to standard output */
def log(level, text) {
    //-----2do: add logic that directs the output to configured target
    if (useLogging == true) {
        println "VerifyServiceDomainFolder: " + text
    }
}

def downloadFileStructureFromRivtaSite(String domainNameRivta) {

    RIVTACheckoutCommandToFolder = RIVTACheckoutCommand + RIVTADomainFolder + domainNameRivta.trim() + "/ " + localRIVTATargetFolder
    log(loglevelDebug, RIVTACheckoutCommandToFolder)

    //-----Delete the local target folder before download
    new File(localRIVTATargetFolder).deleteDir()

    //-----Download files from the RIV-TA site to the local target folder
    def process = RIVTACheckoutCommandToFolder.execute()
    process.in.eachLine { line -> log(loglevelDebug, "Downloading: " + line) }

}


/* Verifies that that the service domains structure and contents are correct on the rivta site */
def verifyServiceDomainStructure(String domainStructure) {
    //-----2do: add logic that verifies the downloaded domain file structure
    log(loglevelDebug, "Domain structure, root: " + domainStructure)

    //-----Extract subdirectories and make a call to the verification method for each subdirectory
    def dir = new File(domainStructure)
    dir.eachDir { subDir ->
        if ((subDir.name.contains('.svn')) == false) {
            //domainSubfolder << subDir
            log(loglevelDebug,  "Subdir: " + subDir.name )
            if (verifySubDomainStructure(domainStructure, subDir.name) == false) {
               return
            }
        }
    }

    //-----Failed verification sets the return code to: 1
    //returnCode = 1
}


/* Verifies that that the service domains structure and contents are correct on the rivta site */
def verifySubDomainStructure(String domainStructure, String subDomain) {

    def dir = new File(domainStructure+subDomain)
    dir.eachFileRecurse (FileType.DIRECTORIES) { file ->
        //-----Filter out all directories except the '.svn' directories
        if ((file.toString().contains('.svn')) == false) {
            log(loglevelDebug,  "\tFile: " + file )

            //-----The found file should be verified against the directory template
        }
    }

    return true
}


/**
 ////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////
 //////////////////// Executing section /////////////////////////////
 ////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////
 */

//-----Fetch the domain name from the script parameter
getDomainNameFromParameter()
log(loglevelDebug, "Domain name = " + usedDomain)

//-----Download the domain structure that should be verified

downloadFileStructureFromRivtaSite(usedDomain)

//-----Iterate through the downloaded structure and verify that it's correct
verifyServiceDomainStructure(localRIVTATargetFolder)

//-----Exit the script execution and return the return code to the caller
return returnCode