
/**
 * Created with IntelliJ IDEA.
 * User: peterhernfalk
 * Date: 2013-10-30
 *
 * LEO's description:
 * Denna skall verifiera att en utpekad mapp här rooten i en mappstruktur som till fullo följer (det nya) konfigurationsstyrningsdokumentet.
 * Om det finns fel skall detta skrivas ut till stderror samt scriptet avslutas med rc=1. Om allt ok avslutas med rc=0
 */

import groovy.lang.Binding
import groovy.io.FileType

domainFolderStructure = []
//domainSubfolder = []
//localRIVTATargetFolder = "/Users/peterhernfalk/Desktop/_Peter_Files/rivtadomain/"
localRIVTATargetFolder = ""
loglevelDebug = "DEBUG"
loglevelError = "ERROR"
loglevelInfo = "INFO"
loglevelWarning = "WARNING"
returnCode = 0
RIVTADomainFolder = "http://rivta.googlecode.com/svn/ServiceInteractions/riv/"
RIVTACheckoutCommand = "svn checkout "
structureTemplate = []
structureTemplate[0]  = [subPath:"branches", mandatoryContent:"", optionalContent:""]
structureTemplate[1]  = [subPath:"tags", mandatoryContent:"", optionalContent:""]
structureTemplate[2]  = [subPath:"trunk", mandatoryContent:"", optionalContent:""]
structureTemplate[3]  = [subPath:"trunk/docs", mandatoryContent:"*.doc", optionalContent:""]
structureTemplate[4]  = [subPath:"trunk/code_gen", mandatoryContent:"", optionalContent:""]
structureTemplate[5]  = [subPath:"trunk/code_gen/wcf", mandatoryContent:"", optionalContent:""]
structureTemplate[6]  = [subPath:"trunk/code_gen/jaxws", mandatoryContent:"", optionalContent:""]
structureTemplate[7]  = [subPath:"trunk/schemas", mandatoryContent:"", optionalContent:""]

//An empty folder is allowed for core_components but (probably) not for interactions
structureTemplate[8]  = [subPath:"trunk/schemas/core_components", mandatoryContent:"*.xsd", optionalContent:""]
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
    \nThis script requires Groovy 1.8.1 or later.
    It generates a wiki page, containing information about the current service domains and their status.
    """

    def cli = new CliBuilder(usage: mecall, header: 'Options:', footer: medesc)
    cli.d(longOpt: 'domainname', args:1, required:true, argName:'Domain name', 'Name of the domain')
    cli.t(longOpt: 'targetdir', args:1, required:true, argName:'target directory', 'directory to which the generated wiki page file will be written')
    cli.l(longOpt: 'uselogging', args:1, required:false, argName:'use logging', 'if the parmeter "-l" is used, then the script logs output to the console')

    //-----Verify all parameters
    def options = cli.parse(args)
    if (!options) return

    def argDomainName=options.getProperty('domainname')
    if (argDomainName.toString().length() == 0) {
        log(loglevelInfo, '* The supplied domain name seems to be empty\n')
        cli.usage()
        return 1
    }
    usedDomain = argDomainName

    def argTargetDir=options.getProperty('targetdir')
    if (argTargetDir.toString().length() == 0) {
        log(loglevelInfo, '* The supplied target directory name seems to be empty\n')
        cli.usage()
        return 1
    }
    localRIVTATargetFolder = argTargetDir

    useLogging = false
    def argUseLogging=options.getProperty('uselogging')
    if (argUseLogging.asBoolean().booleanValue() == true) {
        useLogging = true
    }
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

            //-----The found directory should be verified against the directory template, maybe in a batch verification
            //-----The directory contents should also be verified, so that mandatory files exist in the directory
            ruleVerificationResult = verifySubDomainAgainstStructureRules(file.name)
            //-----2do: use ruleVerificationResult
        }
    }

    return true
}


/* Verifies that the given domain path has contents that follows the domain structure rules */
def verifySubDomainAgainstStructureRules(String domainPath) {

    /*
    •	WSDL: er och scheman ordnas i katalogen ”schemas”
    •	Tjänstekontraktsbeskrivningen ska ligga i docs katalogen
    •	I ”schemas” ska två underkataloger finnas: ”core_components” och ”interactions”.
    •	I ”core_components” ska scheman ligga som är generella för domänen (t.ex. domän-scheman och header-scheman).
    •	I ”interactions” ska scheman och tjänstebeskrivningar ligga som är specifika för tjänsteinteraktionen.
    •	I ”code_gen”-katalog ska det finnas bygg-script för att generera kod från WSDL-filerna, som stöd för utveckling av tjänstekonsumenter och tjänsteproducenter. Underkataloger till code_gen ska skapas för Javaplattformens standard (JAX-WS) och .Net.
     */

    //-----2do: add logic that verifies that the structure follows the rules
    //---Rules exist in the structureTemplate map

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
getValuesFromParameters()
log(loglevelDebug, "Domain name = " + usedDomain)

//-----Download the domain structure that should be verified

downloadFileStructureFromRivtaSite(usedDomain)

//-----Iterate through the downloaded structure and verify that it's correct
verifyServiceDomainStructure(localRIVTATargetFolder)

//-----Exit the script execution and return the return code to the caller
return returnCode