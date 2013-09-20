
/**
 * Created with IntelliJ IDEA.
 * User: peterhernfalk
 * Date: 2013-09-20
 * Time: 07:52
 * To change this template use File | Settings | File Templates.
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
useLogging = true


/**
 //////////////////////////////////////////////////////////
 //////////////////////////////////////////////////////////
 //////////////////// Methods /////////////////////////////
 //////////////////////////////////////////////////////////
 //////////////////////////////////////////////////////////
 */

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
    def args = ['-d', domainName] as String[]
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


//-----Call other script to verify that the folder structure fully follows the rules in the configuration document

//-----Create an archive file for the service domain
        //---2do: fetch the domain name from a parameter to the script
if (verifyServiceDomainFolder("itintegration") == 0) {
    createArchiveFile()
} else {
    log(loglevelDebug, "Verification failed")
}


//-----Exit the script execution
log(loglevelDebug, "\n")
log(loglevelDebug, "ReturnCode = " + returnCode)
System.exit(returnCode)

