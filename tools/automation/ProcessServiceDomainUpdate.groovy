
/**
 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 * //                                                                                                                       //
 * //   This script accepts all necessary parameters for all the scripts, and executes the other scripts in correct order   //
 * //                                                                                                                       //
 * ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 *
 *
 * @author Peter Hernfalk
 * Last update: 2013-12-06
 */

archiveFile = ""
checkoutFiles = true
excelFile = ""
localArchiveTargetFolder = ""
localRIVTASourceFolder = ""
localRIVTATargetFolder = ""
returnCode = 0
targetDirWiki = ""
usedDomainName = ""
usedDomainDirectory = ""
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

    //-----Parameters used by more than one script
    cli.dn(longOpt: 'domainname', args:1, required:true, argName:'Domain name', 'Name of the domain')
    cli.l(longOpt: 'useoptionallogging', args:1, required:false, argName:'Optional: true', 'If "true" then the script logs extra output to the console')

    //-----Specific parameters used by the verification script
    cli.cf(longOpt: 'checkoutfiles', args:1, required:true, argName:'Optional: true', 'If "true" then files will be downloaded to the target directory')
    cli.dd(longOpt: 'domaindir', args:1, required:true, argName:'Domain directory', 'Directory in which service domain files exists. If the checkoutfiles parameter is set to "y" then they will be downloaded to this folder')

    //-----Specific parameters used by the archive creation script
    cli.sd(longOpt: 'sourcedir', args:1, required:true, argName:'Source directory', 'Directory in which the service domain files exists.')
    cli.ad(longOpt: 'archivedir', args:1, required:true, argName:'Archive file directory', 'Directory in which the archive file will be stored.')

    //-----Specific parameters used by the archive upload script
    cli.af(longOpt: 'archivefile', args:1, required:true, argName:'Archive file', 'The path and file name of the archive file (.zip) that will be uploaded to the rivta site.')
    cli.un(longOpt: 'username', args:1, required:true, argName:'User name', 'User name for the user that will be responsible for the upload.')
    cli.up(longOpt: 'userpassword', args:1, required:true, argName:'User password', 'Password for the user that will be responsible for the upload.')

    //-----Specific parameters used by the wiki page update script
    cli.xf(longOpt: 'excelfile', args:1, required:true, argName:'Excel file', 'Name and path to the Excel file')
    cli.td(longOpt: 'targetdirwiki', args:1, required:true, argName:'target directory', 'directory to which the generated wiki page file will be written')

    //-----Verify all parameters
    def options = cli.parse(args)
    if (!options) return

    //-----Parameter: dn (domainname)
    def argDomainName=options.getProperty('domainname')
    if (argDomainName.toString().length() == 0) {
        log('* The supplied domain name seems to be empty\n', true)
        cli.usage()
        return 1
    }
    usedDomainName = argDomainName

    //-----Parameter: l (useOptionalLogging)
    useOptionalLogging = false
    def arguseOptionalLogging=options.getProperty('useoptionallogging')
    if (arguseOptionalLogging.toString().toLowerCase() == "true") {
        useOptionalLogging = true
    }

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

    //-----Parameter: un (username)
    def argUserName=options.getProperty('username')
    if (argUserName.toString().length() == 0) {
        log('* The supplied user name seems to be empty\n', true)
        cli.usage()
        return 1
    }
    userName = argUserName

    //-----Parameter: up (userpassword)
    def argUserPassword=options.getProperty('userpassword')
    if (argUserPassword.toString().length() == 0) {
        log('* The supplied user password seems to be empty\n', true)
        cli.usage()
        return 1
    }
    userPassword = argUserPassword

    //-----Parameter: xf (excelfile)
    def argExcelFile=options.getProperty('excelfile')
    if ( (argExcelFile =~ '.xls').count <1 ) {
        log(loglevelInfo, '* The supplied file name \"' + argExcelFile + '\" does not seems to be an excel file (missing .xls)\n')
        cli.usage()
        return 1
    }
    excelFile = argExcelFile

    //-----Parameter: td (targetdirwiki)
    def argTargetDirWiki=options.getProperty('targetdirwiki')
    if (argTargetDirWiki.toString().length() == 0) {
        log(loglevelInfo, '* The supplied target directory name seems to be empty\n')
        cli.usage()
        return 1
    }
    targetDirWiki = argTargetDirWiki

    /*
        ===== Script parameters for this script, used for test purposes =====
        -dn "itintegration" -l true -cf true -dd "/Users/peterhernfalk/Desktop/_Peter_Files/rivtadomain/
        -sd "/Users/peterhernfalk/Desktop/_Peter_Files/rivtadomain/monitoring/" -ad "/Users/peterhernfalk/Desktop/__CeHisscript/"
        -af "_itintegration.monitoring.zip" -un "peter.hernfalk" -up "abc123"
        -xf "/Users/peterhernfalk/Desktop/_Peter_Files/HOS-projekt/AL/Aktiviteter/Landskap med TP och TK/Groovyscript/Underlag/MasterFlikad5_peter.xls"
        -td "/Users/peterhernfalk/Desktop/__CeHisscript/"
     */
    log("usedDomainName: " + usedDomainName, false)
    log("useOptionalLogging: " + useOptionalLogging, false)
    log("checkoutFiles: " + checkoutFiles, false)
    log("localRIVTATargetFolder: " + localRIVTATargetFolder, false)
    log("localRIVTASourceFolder: " + localRIVTASourceFolder, false)
    log("localArchiveTargetFolder: " + localArchiveTargetFolder, false)
    log("archiveFile: " + archiveFile, false)
    log("userName: " + userName, false)
    log("userPassword: " + userPassword, false)
    log("excelFile: " + excelFile, false)
    log("targetDirWiki: " + targetDirWiki, false)

    return true
}

/* Verifies that that the service domains structure and contents are correct on the rivta site */
def verifyServiceDomainFolder(String domainName) {
    //-----Create parameters tha will be used when calling the script
    def args = ['-cf', checkoutFiles, '-dd', localRIVTATargetFolder, '-dn', domainName, '-l', useOptionalLogging] as String[]
    Binding context = new Binding(args)

    //-----Call the script
    log("Call the \"VerifyServiceDomainFolder\" script", true)
    returnCodeCalledScript = new GroovyShell(context).evaluate(new File("VerifyServiceDomainFolder.groovy"))

    //-----Take care of the return code from the called script
    if (returnCodeCalledScript == 0) {
        log("\n", true)
        log("Successful verification", true)
        returnCode = returnCodeCalledScript
    } else {
        log("\n", true)
        log("**************************************************", true)
        log("**************************************************", true)
        log("Verification failed with return code: " + returnCodeCalledScript, true)
        log("**************************************************", true)
        log("**************************************************", true)
        returnCode = 1
    }

    return true
}

/* Creates an archive file, containing service domain files */
def createArchiveFile() {
    //-----Create parameters tha will be used when calling the script
    def args = ['-ad', localArchiveTargetFolder, '-dn', usedDomainName, '-l', useOptionalLogging, '-sd', localRIVTASourceFolder] as String[]
    Binding context = new Binding(args)

    //-----Call the script
    log("Call the \"CreateServiceDomainArchive\" script", true)
    returnCodeCalledScript = new GroovyShell(context).evaluate(new File("CreateServiceDomainArchive.groovy"))

    //-----Take care of the return code from the script
    if (returnCodeCalledScript == 0) {
        log("\n", true)
        log("Successful creation of archive file", true)
        returnCode = returnCodeCalledScript
    } else {
        log("\n", true)
        log("**************************************************", true)
        log("**************************************************", true)
        log("Creation of archive file failed with return code: " + returnCodeCalledScript, true)
        log("**************************************************", true)
        log("**************************************************", true)
        returnCode = 1
    }

    return true
}

/* Creates an archive file, containing service domain files */
def uploadArchiveFile() {
    //-----Create parameters tha will be used when calling the script
    def args = ['-ad', localArchiveTargetFolder, '-af', archiveFile, '-l', useOptionalLogging, '-un', userName, '-up', userPassword] as String[]
    Binding context = new Binding(args)

    //-----Call the script
    log("Call the \"UploadServiceDomainArchive\" script", true)
    returnCodeCalledScript = new GroovyShell(context).evaluate(new File("UploadServiceDomainArchive.groovy"))

    //-----Take care of the return code from the script
    if (returnCodeCalledScript == 0) {
        log("\n", true)
        log("Successful upload of archive file to the rivta site", true)
        returnCode = returnCodeCalledScript
    } else {
        log("\n", true)
        log("**************************************************", true)
        log("**************************************************", true)
        log("Upload of archive file failed with return code: " + returnCodeCalledScript, true)
        log("**************************************************", true)
        log("**************************************************", true)
        returnCode = 1
    }

    return true
}

/* Creates an archive file, containing service domain files */
def updateWikiPage() {
    //-----Create parameters tha will be used when calling the script
    def args = ['-l', useOptionalLogging, '-td', targetDirWiki, '-xf', excelFile] as String[]
    Binding context = new Binding(args)

    //-----Call the script
    log("Call the \"CreateServiceDomainTable_WikiPage\" script", true)
    returnCodeCalledScript = new GroovyShell(context).evaluate(new File("CreateServiceDomainTable_WikiPage.groovy"))

    //-----Take care of the return code from the script
    if (returnCodeCalledScript == 0) {
        log("\n", true)
        log("Successful creation of the wiki page, containing an updated service domain table", true)
        returnCode = returnCodeCalledScript
    } else {
        log("\n", true)
        log("**************************************************", true)
        log("**************************************************", true)
        log("Creation of the wiki page failed with return code: " + returnCodeCalledScript, true)
        log("**************************************************", true)
        log("**************************************************", true)
        returnCode = 1
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


/**
 ////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////
 //////////////////// Executing section /////////////////////////////
 ////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////
 */

if (getValuesFromParameters() == true) {

    //-----Execute the scripts in correct order
    verifyServiceDomainFolder(usedDomainName)
    if (returnCode == 0) {
        log("Successful verification of service domain structure", true)
        createArchiveFile()
        if (returnCode == 0) {
            log("Successful creation of archive file", true)
            uploadArchiveFile()
            if (returnCode == 0) {
                log("Successful upload of archive file", true)
                updateWikiPage()
                if (returnCode == 0) {
                    log("Successful update of the wiki page at the rivta site", true)
                } else {
                    log("Failed to update the wiki page at the rivta site. Process aborted", true)
                }
            } else {
                log("Failed to upload archive file. Process aborted", true)
            }
        } else {
            log("Failed to create archive file. Process aborted", true)
        }
    } else {
        log("Verification of service domain structure failed. Process aborted", true)
    }
}


//-----Exit the script execution
log("\n", true)
log("ReturnCode = " + returnCode, true)
//System.exit(returnCode)
return returnCode