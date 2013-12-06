
/**
 * ///////////////////////////////////////////////////////////////////////////////////////////
 * //                                                                                       //
 * //   This script creates documentation of service domains in the format of a wiki page   //
 * //                                                                                       //
 * ///////////////////////////////////////////////////////////////////////////////////////////
 *
 * Information sources used by this script:
 * - Main source: the Excel document describing service domains
 *
 *
 * @author Peter Hernfalk
 * Last update: 2013-12-06
 */


import groovy.util.slurpersupport.GPathResult
import static groovy.io.FileType.FILES


//-----Definitions
cehisCategoriesLink = "http://code.google.com/p/rivta/wiki/CehisKategorier"
excelFile = ""
excelMasterFile = []
output2All = "all"
output2WikiPageRIVTA = "wikiPageRIVTA"
returnCode = 0
serviceDomains = []
useOptionalLogging = true
wikiTableFileRIVTA = "/_WikiTableRIVTA.txt"
WikiTableRIVTAPageBeginning = []
WikiTableRIVTAPageBeginning[0] = '#summary Tabell över godkända tjänstedomäner\n'
WikiTableRIVTAPageBeginning[1] = '=Tabell över godkända tjänstedomäner=\n'
WikiTableRIVTAPageBeginning[2] = 'Tabellen nedan innehåller information om vilka tjänstedomäner som godkänts av Cehis _Arkitektur och regelverk_. '
WikiTableRIVTAPageBeginning[3] = 'I samband med granskningen tilldelas domänen en [CehisKategorier kategori] som styr hur den kan användas i den gemensamma tjänsteplattformen.  \n\n'
WikiTableRIVTAPageBeginning[4] = 'En granskning initieras genom att skicka ett mail till e-postadressen "arkitektur (at) cehis.se". '
WikiTableRIVTAPageBeginning[5] = 'Först när godkännandet är klart och zip-filen tillgänglig i tabellen nedan kan man gå vidare med att beställa installation i Tjänsteplattformen.\n\n'
WikiTableRIVTAPageBeginning[6] = 'Mer information om tjänstedomäner återfinns på http://www.cehis.se/arkitektur_och_regelverk/befintliga_tjanstekontrakt/.\n\n'
WikiTableRIVTAPageEnd = []
WikiTableRIVTAPageEnd[0] = '\n_OBS! Denna wiki-sida får enbart uppdateras av personer inom Cehis Arkitektur och regelverk. Alla förändringar loggas!_'


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
    cli.l(longOpt: 'useoptionallogging', args:1, required:false, argName:'use logging', 'if the parmeter "-l" is used, then the script logs output to the console')
    cli.td(longOpt: 'targetdirwiki', args:1, required:true, argName:'target directory', 'directory to which the generated wiki page file will be written')
    cli.xf(longOpt: 'excelfile', args:1, required:true, argName:'Excel file', 'Name and path to the Excel file')

    //-----Verify all parameters
    def options = cli.parse(args)
    if (!options) return

    useOptionalLogging = false
    def arguseOptionalLogging=options.getProperty('useoptionallogging')
    if (arguseOptionalLogging.toString().toLowerCase() == "true") {
        useOptionalLogging = true
    }

    def argTargetDirWiki=options.getProperty('targetdirwiki')
    if (argTargetDirWiki.toString().length() == 0) {
        log('* The supplied target directory name seems to be empty\n', true)
        cli.usage()
        return 1
    }
    targetDirWiki = argTargetDirWiki

    def argExcelFile=options.getProperty('excelfile')
    if ( (argExcelFile =~ '.xls').count <1 ) {
        log('* The supplied file name \"' + argExcelFile + '\" does not seems to be an excel file (missing .xls)\n', true)
        cli.usage()
        return 1
    }
    excelFile = argExcelFile
    log("useOptionalLogging: " + useOptionalLogging, true)
    log("excelFile: " + excelFile, true)
    log("targetDirWiki: " + targetDirWiki, true)

    return true
}


def wikiTableStringRIVTA (String stringContent) {
    "|| " + stringContent + " "
}


/** Logs text to standard output */
def log(String text, boolean logEntryIsMandatory) {
    if (logEntryIsMandatory == true) {
        println this.class.name + " - " + text
    } else if (useOptionalLogging == true && logEntryIsMandatory == false) {
        println this.class.name + " - " + text
    }
}


/** Displays execution statistics */
def ShowExecutionStatistics() {
    log("", true)
    log("----- Execution statistics -----", true)
    log(serviceDomains.size + " service domains were used", true)
}

def buildloadLinks(String filterValue, String version, String altLink) {
    if (filterValue.isEmpty() == false) {
        adjustedFilterValue = filterValue.replaceAll(":", "_")
        adjustedFilterValue = adjustedFilterValue.replaceAll(" ", "")
    }
    returnURL = "https://rivta.googlecode.com/files/ServiceContracts_" + adjustedFilterValue + "_" + version + ".zip"
    if (verifyURL(returnURL) == false) {
        newVersion = version.replaceAll("-", "_")
        returnURL = "https://rivta.googlecode.com/files/ServiceContracts_" + adjustedFilterValue + "_" + newVersion + ".zip"
        if (verifyURL(returnURL) == false) {
            log("Failed to build a correct URL: " + returnURL, true)
            newVersion = version.replaceAll("\\.", "_")
            log("version: " + version + " newVersion: " + newVersion, false)
            returnURL = "https://rivta.googlecode.com/files/ServiceContracts_" + adjustedFilterValue + "_" + newVersion + ".zip"
            if (verifyURL(returnURL) == false) {
                log("Failed to build a correct URL: " + returnURL, true)
                //-----Last chance to crea a working link; use the edited link from the altlink column
                if (altLink.trim().length() > 0) {
                    returnURL = altLink
                    if (verifyURL(returnURL) == false) {
                        log("Failed to build a correct URL: " + returnURL, true)
                        returnURL = ""
                    }
                }
            }
        }
    }
    return returnURL
}


def parseFilteredURLFromHref(String hrefString, String filterValue) {
    if (hrefString.trim().length() > 0) {
        if ((filterValue.trim().length() > 0) && (hrefString.indexOf(filterValue) >= 0)) {
            extractURLFromString(hrefString)
        } else if (filterValue.trim().length() == 0) {
            extractURLFromString(hrefString)
        }
    }
}


def extractURLFromString(String urlString) {
    urlStartString = '<a href="'
    urlEndString = ".zip"
    urlStartOffset = urlString.indexOf(urlStartString) + urlStartString.length()
    urlEndOffset = urlString.indexOf(urlEndString) + urlEndString.length()
    newString = "https://code.google.com/p/rivta/downloads/" + urlString.substring(urlStartOffset,urlEndOffset)
}


def replaceNullWithSpace(String value) {
    presentValue = value.trim()
    if (presentValue != "null") {
        newValue = presentValue
    } else {
        newValue = " "
    }
    return newValue
}

def verifyURL(String urlString) {
    result = true
    try {
        String html = urlString.toURL().text
    } catch (FileNotFoundException) {
        result = false
    }
    return result
}

//---------------------------------------- End of method section ----------------------------------------//



/**
 ////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////
 //////////////////// Executing section /////////////////////////////
 ////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////
 */

//---------------------------------------- Usage settings ----------------------------------------//
charset = "ISO-8859-1"                   //--- ("ISO-8859-1", "UTF-8")
delimiterToken = '/'                     //--- (mac = '/', windows = '.')
outputType = output2All                  //--- (output2All, output2WikiPageRIVTA)
//-----------------------------------------------------------------------------------------------//

if (getValuesFromParameters() == true) {

    //-----Fetch a copy of the contents of the Excel file. Store the data in a map that contains named attributes
    def index = 0
    def mapIndex = 0
    def excel = new ExcelReader(excelFile)

    excel.eachLine {

        log("Excel: " + "${cell(0)}".trim() + "\t" + "${cell(1)}".trim() + "\t" + "${cell(2)}".trim() + "\t" + "${cell(3)}".trim()
                + "\t" + "${cell(4)}".trim() + "\t" + "${cell(5)}".trim() + "\t" + "${cell(6)}".trim(), false)


        //-----Empty Excel rows are not used
        if (("${index}" > 0) && ("${cell(2)}".length() > 0)  && ("${cell(2)}" != "null")) {


            //-----The name variable is used when deciding if the current domain is already stored in the target map or not
            if (("${cell(2)}".trim().isEmpty() == false) || (mapIndex == 0)) {
                //-----Add domain to the target map
                    excelMasterFile[mapIndex] = [
                            subdomainswedish:"${cell(0)}".trim(),
                            subdomainenglish:"${cell(1)}".trim(),
                            rivtacommonname:"${cell(2)}".trim(),
                            rivtaservicedomain:"${cell(3)}".trim(),
                            version:"${cell(4)}".trim(),
                            rivtabp20:"${cell(5)}".trim(),
                            rivtabp21:"${cell(6)}".trim(),
                            domaincategory:"${cell(7)}".trim(),
                            altlink:"${cell(8)}".trim()
                    ]
                    mapIndex += 1
            }
        }
        index += 1
    }


    if ((outputType == output2WikiPageRIVTA) || (outputType == output2All)) {

        /**
         ///////////////////////////////////////////////////////////////////////////////////
         ///////////////////////////////////////////////////////////////////////////////////
         ///////////////////////////// Write Wiki file RIVTA //////////////////////////////
         ///////////////////////////////////////////////////////////////////////////////////
         ///////////////////////////////////////////////////////////////////////////////////
         */

        //-----Create HTML contents, save them to a temporary map and sort the map
        tempWikiContentsRIVTA = []

        excelMasterFileSize = excelMasterFile.size()
        excelMasterFileSize.times {

            def thisIndex = it
            userfriendlyServiceDomainName = replaceNullWithSpace(excelMasterFile[thisIndex].rivtacommonname)
            serviceDomainName             = replaceNullWithSpace(excelMasterFile[thisIndex].rivtaservicedomain)
            version                       = replaceNullWithSpace(excelMasterFile[thisIndex].version)
            altLink                       = replaceNullWithSpace(excelMasterFile[thisIndex].altlink)
            rivtaBP20                     = replaceNullWithSpace(excelMasterFile[thisIndex].rivtabp20)
            rivtaBP21                     = replaceNullWithSpace(excelMasterFile[thisIndex].rivtabp21)
            serviceContractCategory       = replaceNullWithSpace(excelMasterFile[thisIndex].domaincategory)

            log("userfriendlyServiceDomainName: " + userfriendlyServiceDomainName, false)
            log("serviceDomainName: " + serviceDomainName, false)
            log("version: " + version, false)
            log("rivtaBP20: " + rivtaBP20, false)
            log("rivtaBP21: " + rivtaBP21, false)
            log("serviceContractCategory: " + serviceContractCategory, false)

            downloadURL = buildloadLinks(serviceDomainName, version, altLink)
            if (downloadURL != "") {
                downloadLink = "[" + downloadURL + " Ladda ner]"
            } else {
                downloadLink = ""
            }

            //-----Write Wiki table, using available data
            //**************2do: version should be replaced when alternate version (from Excel) has been used
            if (userfriendlyServiceDomainName.trim().length() > 0 ) {
                tempWikiContentsRIVTA <<
                        wikiTableStringRIVTA(userfriendlyServiceDomainName) +
                        wikiTableStringRIVTA(serviceDomainName) +
                        wikiTableStringRIVTA(version) +
                        wikiTableStringRIVTA(rivtaBP20) +
                        wikiTableStringRIVTA(rivtaBP21) +
                        wikiTableStringRIVTA(serviceContractCategory) +
                        wikiTableStringRIVTA(downloadLink) +
                        wikiTableStringRIVTA(" ")
            }
        }
        tempWikiContentsRIVTA.sort()


        //-----Write the beginning of the Wiki file (RIVTA)
        pageHeaderLines = WikiTableRIVTAPageBeginning.size()
        new File(targetDirWiki + wikiTableFileRIVTA).newOutputStream().withWriter(charset) {
            writer -> pageHeaderLines.times {
                writer.write WikiTableRIVTAPageBeginning[it] + "\n"
            }
            writer.write wikiTableStringRIVTA("*Populärnamn*")
            writer.write wikiTableStringRIVTA("*Tjänstedomän*")
            writer.write wikiTableStringRIVTA("*Version*")
            writer.write wikiTableStringRIVTA("*RIV-TA BP 2.0*")
            writer.write wikiTableStringRIVTA("*RIV-TA BP 2.1*")
            writer.write wikiTableStringRIVTA("*[CehisKategorier Cehis kategori]*")
            writer.write wikiTableStringRIVTA(" ")
            writer.write wikiTableStringRIVTA(" ") + "\n"
        }

        //-----Use the sorted map as input, write the map contents to the Wiki file (RIVTA)
        new File(targetDirWiki + wikiTableFileRIVTA).withWriterAppend(charset) {
            out ->  tempWikiContentsRIVTA.each {
                out.println it
            }
        }

        //-----Write the end of the Wiki file
        pageFooterLines = WikiTableRIVTAPageEnd.size()
        new File(targetDirWiki + wikiTableFileRIVTA).withWriterAppend(charset) {
            writer -> pageFooterLines.times {
                writer.write WikiTableRIVTAPageEnd[it] + "\n"
            }
        }
    }

    //-----Show execution statistics
    ShowExecutionStatistics()
}


//-----Exit the script execution
log("\n", true)
log("ReturnCode = " + returnCode, true)
return returnCode