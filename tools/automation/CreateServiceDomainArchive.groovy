
/**
 * Created with IntelliJ IDEA.
 * User: peterhernfalk
 * Date: 2013-10-30
 *
 * LEO's description:
 * Skall skall använda scriptet "VerifyServiceDomainFolder" för att verifiera innehållet, och därefter skapa en zip-fil
 */

loglevelDebug = "DEBUG"
loglevelError = "ERROR"
loglevelInfo = "INFO"
loglevelWarning = "WARNING"
returnCode = 0
returnCodeCalledScript = 0
usedDomainName = ""
useLogging = true


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
    \nThis script requires Groovy 1.8.1 or later.
    It generates a wiki page, containing information about the current service domains and their status.
    """

    def cli = new CliBuilder(usage: mecall, header: 'Options:', footer: medesc)
    cli.d(longOpt: 'domainname', args:1, required:true, argName:'Domain name', 'Name of the domain')

    //-----Verify all parameters
    def options = cli.parse(args)
    if (!options) return

    def argDomainName=options.getProperty('domainname')
    if (argDomainName.toString().length() == 0) {
        log(loglevelInfo, '* The supplied domain name seems to be empty\n')
        cli.usage()
        return 1
    }
    usedDomainName = argDomainName
}


/** Logs text to standard output */
def log(level, text) {
    //-----2do: add logic that directs the output to configured target
    if (useLogging == true) {
        println "CreateServiceDomainArchive: " + text
    }
}


/* Verifies that that the service domains structure and contents are correct on the rivta site */
def verifyServiceDomainFolder(String domainName) {
    //-----Create parameters to the "VerifyServiceDomainFolder" script
    def args = ['-d', domainName, '-t', '/Users/peterhernfalk/Desktop/_Peter_Files/rivtadomain/', '-l', 'true'] as String[]
    Binding context = new Binding(args)

    //-----Call the"VerifyServiceDomainFolder" script
    returnCodeCalledScript = new GroovyShell(context).evaluate(new File("VerifyServiceDomainFolder.groovy"))

    //-----Take care of the return code from the "VerifyServiceDomainFolder" script
    if (returnCodeCalledScript == 0) {
        log(loglevelDebug, "\n")
        log(loglevelDebug, "Succesful verification")
        returnCode = returnCodeCalledScript
    } else {
        log(loglevelDebug, "\n")
        log(loglevelDebug, "**************************************************")
        log(loglevelDebug, "**************************************************")
        log(loglevelDebug, "Verification failed with return code: " + returnCodeCalledScript)
        log(loglevelDebug, "**************************************************")
        log(loglevelDebug, "**************************************************")
        returnCode = 1
    }
}

/* Creates an archive file, containing files belonging to one service domain */
def createArchiveFile() {
    //-----2do
    log(loglevelDebug, "CreateArchiveFile")
}


/**
 ////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////
 //////////////////// Executing section /////////////////////////////
 ////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////
 */

//---------------------------------------- Usage settings ----------------------------------------//
useLogging = true
//-----------------------------------------------------------------------------------------------//

getValuesFromParameters()

//-----Call other script to verify that the folder structure fully follows the rules in the configuration document

//-----Create an archive file for the service domain
        //---2do: fetch the domain name from a parameter to the script  itintegration  informatics
if (verifyServiceDomainFolder(usedDomainName) == 0) {
    createArchiveFile()
} else {
    log(loglevelDebug, "Verification failed")
}


//-----Exit the script execution
log(loglevelDebug, "\n")
log(loglevelDebug, "ReturnCode = " + returnCode)
System.exit(returnCode)
