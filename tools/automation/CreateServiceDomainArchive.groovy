
/**
 * /////////////////////////////////////////////////////////////////////////////
 * //                                                                         //
 * //   This script creates an archive file containing service domain files   //
 * //                                                                         //
 * ////////////////////////////////////////////////////////////////////////////
 *
 *
 * @author Peter Hernfalk
 * Last update: 2013-12-06

 */

import java.util.zip.ZipOutputStream
import java.util.zip.ZipEntry
import java.nio.channels.FileChannel

localArchiveTargetFolder = ""
localRIVTASourceFolder = ""
returnCode = 0
usedDomainName = ""
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
    \nThis script requires Groovy 1.8.1 or later.
    The purpose of the script is to create an archive file (.zip), containing the folder structure of a specific service domain.
    """

    def cli = new CliBuilder(usage: mecall, header: 'Options:', footer: medesc)
    cli.ad(longOpt: 'archivedir', args:1, required:true, argName:'Target directory', 'Directory in which the archive file will be stored.')
    cli.dn(longOpt: 'domainname', args:1, required:true, argName:'Domain name', 'Name of the domain')
    cli.l(longOpt: 'useoptionallogging', args:1, required:false, argName:'Optional: true', 'If "true" then the script logs extra output to the console')
    cli.sd(longOpt: 'sourcedir', args:1, required:true, argName:'Source directory', 'Directory in which the service domain files exists.')

    //-----Verify all parameters
    def options = cli.parse(args)
    if (!options) return

    def argArchiveDir=options.getProperty('archivedir')
    if (argArchiveDir.toString().length() == 0) {
        log('* The supplied directory name for the archive file seems to be empty\n', true)
        cli.usage()
        return 1
    }
    localArchiveTargetFolder = argArchiveDir
    if (localArchiveTargetFolder.endsWith("/") == false) {
        localArchiveTargetFolder += "/"
    }

    def argDomainName=options.getProperty('domainname')
    if (argDomainName.toString().length() == 0) {
        log('* The supplied domain name seems to be empty\n', true)
        cli.usage()
        return 1
    }
    usedDomainName = argDomainName

    useOptionalLogging = false
    def arguseOptionalLogging=options.getProperty('useoptionallogging')
    if (arguseOptionalLogging.toString().toLowerCase() == "true") {
        useOptionalLogging = true
    }

    def argSourceDir=options.getProperty('sourcedir')
    if (argSourceDir.toString().length() == 0) {
        log('* The supplied source directory name seems to be empty\n', true)
        cli.usage()
        return 1
    }
    localRIVTASourceFolder = argSourceDir
    if (localRIVTASourceFolder.endsWith("/") == false) {
        localRIVTASourceFolder += "/"
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

/* Creates an archive file, containing files belonging to one service domain */
def createArchiveFile() {
    def ant = new AntBuilder()
    ant.zip(
            destfile: localArchiveTargetFolder + "_" + usedDomainName + ".zip",
            basedir: localRIVTASourceFolder
    )
    log(localArchiveTargetFolder + "_" + usedDomainName + ".zip", true)
}


/**
 ////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////
 //////////////////// Executing section /////////////////////////////
 ////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////
 */

if (getValuesFromParameters() == true) {
    log("localArchiveTargetFolder: " + localArchiveTargetFolder, false)
    log("usedDomainName: " + usedDomainName, false)
    log("useOptionalLogging: " + useOptionalLogging, false)
    log("localRIVTASourceFolder: " + localRIVTASourceFolder, false)

    //-----Create an archive file for the service domain
    createArchiveFile()
}


//-----Exit the script execution
log("\n", true)
log("ReturnCode = " + returnCode, true)
return returnCode
