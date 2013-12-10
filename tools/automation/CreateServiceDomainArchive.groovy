
/**
 * /////////////////////////////////////////////////////////////////////////////
 * //                                                                         //
 * //   This script creates an archive file containing service domain files   //
 * //                                                                         //
 * ////////////////////////////////////////////////////////////////////////////
 *
 *
 * @author Peter Hernfalk
 * Last update: 2013-12-10

 */

import java.util.zip.ZipOutputStream
import java.util.zip.ZipEntry
import java.nio.channels.FileChannel

archiveFile = ""
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
    cli.af(longOpt: 'archivefile', args:1, required:true, argName:'Archive file', 'The path and file name of the archive file (.zip) that will be uploaded to the rivta site.')
    cli.l(longOpt: 'useoptionallogging', args:1, required:false, argName:'Optional: true', 'If "true" then the script logs extra output to the console')
    cli.sd(longOpt: 'sourcedir', args:1, required:true, argName:'Source directory', 'Directory in which the service domain files exists.')

    //-----Verify all parameters
    def options = cli.parse(args)
    if (!options) return

    //-----Parameter: ad (archivedir)
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

    //-----Parameter: af (archivefile)
    def argArchiveFile=options.getProperty('archivefile')
    if (argArchiveFile.toString().length() == 0) {
        log('* The supplied name of the archive file seems to be empty\n', true)
        cli.usage()
        return 1
    } /*else if (***filen finns ej på angiven sökväg***) {
        log('* The supplied file does not exist in the supplied path\n', true)
        cli.usage()
        return 1
    }*/
    archiveFile = argArchiveFile

    //-----Parameter: l (useOptionalLogging)
    useOptionalLogging = false
    def arguseOptionalLogging=options.getProperty('useoptionallogging')
    if (arguseOptionalLogging.toString().toLowerCase() == "true") {
        useOptionalLogging = true
    }

    //-----Parameter: sd (sourcedir)
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
            destfile: localArchiveTargetFolder + archiveFile,
            basedir: localRIVTASourceFolder
    )
    log(localArchiveTargetFolder + archiveFile, false)
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
    log("useOptionalLogging: " + useOptionalLogging, false)
    log("localRIVTASourceFolder: " + localRIVTASourceFolder, false)

    //-----Create an archive file for the service domain
    createArchiveFile()
}


//-----Exit the script execution
log("Return code from the script: " + returnCode, true)
return returnCode
