
/**
 * ///////////////////////////////////////////////////////////////
 * //                                                           //
 * //   This script uploads an archive file to the rivta site   //
 * //                                                           //
 * ///////////////////////////////////////////////////////////////
 *
 *
 * @author Peter Hernfalk
 * Last update: 2013-12-06

 * LEO:s önskemål (2013-12-04)
 Man skall ju zippa ihop en TAGgad map, dvs en mappstruktur under en undermap till tags i Subversion.
 Skulle behöva läsa på konfig-styrning, men har för mig att vi sagt att zipfilnamnet skall innehålla, förutom domännamnet, tagens namn.
 Man borde alltså kunna skapa sätta namnet automatiskt.

 */

archiveFile = ""
localArchiveTargetFolder = ""
returnCode = 0
useOptionalLogging = true
userName = ""
userPassword = ""


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
    cli.ad(longOpt: 'archivedir', args:1, required:true, argName:'Archive file directory', 'Directory in which the archive file will be stored.')
    cli.af(longOpt: 'archivefile', args:1, required:true, argName:'Archive file', 'The path and file name of the archive file (.zip) that will be uploaded to the rivta site.')
    cli.l(longOpt: 'useoptionallogging', args:1, required:false, argName:'Optional: true', 'If "true" then the script logs extra output to the console')
    cli.un(longOpt: 'username', args:1, required:true, argName:'User name', 'User name for the user that will be responsible for the upload.')
    cli.up(longOpt: 'userpassword', args:1, required:true, argName:'User password', 'Password for the user that will be responsible for the upload.')

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

    useOptionalLogging = false
    def arguseOptionalLogging=options.getProperty('useoptionallogging')
    if (arguseOptionalLogging.toString().toLowerCase() == "true") {
        useOptionalLogging = true
    }

    def argUserName=options.getProperty('username')
    if (argUserName.toString().length() == 0) {
        log('* The supplied user name seems to be empty\n', true)
        cli.usage()
        return 1
    }
    userName = argUserName

    def argUserPassword=options.getProperty('userpassword')
    if (argUserPassword.toString().length() == 0) {
        log('* The supplied user password seems to be empty\n', true)
        cli.usage()
        return 1
    }
    userPassword = argUserPassword

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

/** Uploads the archive file to the rivta site */
def uploadArchiveFile() {
    log("--------------------------------------------------------------------", true)
    log("This script is NOT ready yet", true)
    log("2do: create code that calls the rivta site's API for uploading files", true)
    log("--------------------------------------------------------------------", true)
    log("", true)

    //connection details for the rivta site's API: ??
    //Authentication: userName + userPassword
    //Path and file name for the archive file: localArchiveTargetFolder + archiveFile
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
    log("Archve file: " + archiveFile, false)
    log("useOptionalLogging: " + useOptionalLogging, false)
    log("User name: " + userName, false)
    log("User password: " + userPassword, false)

    uploadArchiveFile()
}


//-----Exit the script execution
log("\n", true)
log("ReturnCode = " + returnCode, true)
//System.exit(returnCode)
return returnCode