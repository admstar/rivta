/**
 * ///////////////////////////////////////////////////////////////////////////////////////////
 * //                                                                                       //
 * //   This script creates documentation of service domains in the format of a wiki page   //
 * //                                                                                       //
 * ///////////////////////////////////////////////////////////////////////////////////////////
 *
 * Information sources used by this script:
 * - Main source: the Excel document describing service domains (Master.xls)
 * - Download links from RIV TA (http://code.google.com/p/rivta/)
 *
 *
 * @author Peter Hernfalk
 * Last update: 2013-09-20
 */


import groovy.util.slurpersupport.GPathResult
import static groovy.io.FileType.FILES

//-----Definitions
cehisCategoriesLink = "http://code.google.com/p/rivta/wiki/CehisKategorier"
excelMasterFile = []
loglevelDebug = "DEBUG"
loglevelError = "ERROR"
loglevelInfo = "INFO"
loglevelWarning = "WARNING"
output2All = "all"
output2WikiTableRIVTA = "wikiTableRIVTA"
serviceDomains = []
useLogging = true
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

def wikiTableStringRIVTA (String stringContent) {
    "|| " + stringContent + " "
}


/** Logs text */
def log(level, text) {
    //-----2do: add logic that directs the output to configured target
    if (useLogging == true) {
        println text
    }
}


/** Displays execution statistics */
def ShowExecutionStatistics() {
    log(loglevelInfo, "")
    log(loglevelInfo, "----- Execution statistics -----")
    log(loglevelInfo, serviceDomains.size + " service domains were used")
}


//-----2do: modify this method so that it uses the version when deciding if to return a link to the zip file or to the doomains download page
def getDownloadLinkFromRIVTA(String filterValue) {
    urlToRIVTADownloadBegin = "http://code.google.com/p/rivta/downloads/list?can=2"
    urlToRIVTADownloadFilter = "&q=TD%3D"
    urlToRIVTADownloadEnd = "&colspec=Filename+TD+Summary+Uploaded+ReleaseDate+Size+DownloadCount"

    urlToZipFiles = urlToRIVTADownloadBegin
    if (filterValue.isEmpty() == false) {
        adjustedFilterValue = filterValue.replaceAll(":", ".")
        adjustedFilterValue = adjustedFilterValue.replaceAll(" ", "")
        urlToZipFiles += urlToRIVTADownloadFilter + adjustedFilterValue
        if (adjustedFilterValue.indexOf(" ") > 0) {
            log(loglevelDebug, "   Filtervalue: before, after: " + filterValue + " " + adjustedFilterValue)
        }
    }
    urlToZipFiles += urlToRIVTADownloadEnd

    @Grab(group='org.ccil.cowan.tagsoup', module='tagsoup', version='1.2' )
    XmlSlurper slurper = new XmlSlurper(new org.ccil.cowan.tagsoup.Parser());
    GPathResult nodes = slurper.parse(urlToZipFiles)

    log(loglevelDebug, "   Download URL: " + urlToZipFiles)

    numberOfZipFiles = 0
    //-----Extracts proper URL:s from the table at the RIVTA download page
    new URL(urlToZipFiles).eachLine{
        (it =~ /.*<a href="(.*?)">/).each{
            //parsedURL = parseFilteredURLFromHref(it.toString(), "")
            parsedURL = parseFilteredURLFromHref(it.toString(), ".zip")
            if (parsedURL != null) {
                log(loglevelDebug, "parsedURL: " + parsedURL)
                numberOfZipFiles ++
            }
        }
    }
    if (numberOfZipFiles > 1) {
        returnURL = urlToZipFiles
    } else {
        returnURL = parsedURL
    }
    returnURL
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
    urlStartString = "<a"
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
excelFile = "/Users/peterhernfalk/Desktop/_Peter_Files/HOS-projekt/AL/Aktiviteter/Landskap med TP och TK/Groovyscript/Underlag/MasterTest.xls"
outputType = output2All                  //--- (output2All, output2WikiTableRIVTA)
localRIVTATargetFolder = "/Users/peterhernfalk/Desktop/_Peter_Files/rivta/"
useLogging = false                        //--- (true, false)
//-----------------------------------------------------------------------------------------------//


//-----Fetch a copy of the contents of the Excel file. Store the data in a map that contains named attributes
def index = 0
def mapIndex = 0
def excel = new ExcelReader(excelFile)

excel.eachLine {

    log(loglevelDebug, "Excel: " + "${cell(0)}".trim() + "\t" + "${cell(1)}".trim() + "\t" + "${cell(2)}".trim() + "\t" + "${cell(3)}".trim()
     + "\t" + "${cell(4)}".trim() + "\t" + "${cell(5)}".trim() + "\t" + "${cell(6)}".trim() + "\t" + "${cell(7)}".trim()
     + "\t" + "${cell(8)}".trim() + "\t" + "${cell(9)}".trim() + "\t" + "${cell(10)}".trim() + "\t" + "${cell(11)}".trim()
     + "\t" + "${cell(12)}".trim() + "\t" + "${cell(13)}".trim())

    //-----Empty Excel rows are not used
    if (("${index}" > 0) && ("${cell(2)}".length() > 0)  && ("${cell(2)}" != "null")) {

        //-----The name variable is used when deciding if the current domain is already stored in the target map or not
        def name = "${cell(2)}".trim()
        def domainExistenceInTargetMap = excelMasterFile.find {it.swedishsubdomain == name}

        if (("${cell(2)}".trim().isEmpty() == false) || (mapIndex == 0)) {
            //-----Add domain to the target map
            if (domainExistenceInTargetMap == null) {
                excelMasterFile[mapIndex] = [
                        contract:"${cell(0)}".trim(),
                        contractcategory:"${cell(1)}".trim(),
                        swedishsubdomain:"${cell(2)}".trim(),
                        englishsubdomain:"${cell(3)}".trim(),
                        vifodomainswedish:"${cell(4)}".trim(),
                        vifodomainenglish:"${cell(5)}".trim(),
                        rivtaname:"${cell(6)}".trim(),
                        rivtacommonname:"${cell(7)}".trim(),
                        rivtaservicedomain:"${cell(8)}".trim(),
                        version:"${cell(9)}".trim(),
                        rivtabp20:"${cell(10)}".trim(),
                        rivtabp21:"${cell(11)}".trim(),
                        domaincategory:"${cell(12)}".trim(),
                        domaincontact:"${cell(13)}".trim()
                ]
                mapIndex += 1
            }

        }
    }
    index += 1
}


if ((outputType == output2WikiTableRIVTA) || (outputType == output2All)) {

    /**
     ///////////////////////////////////////////////////////////////////////////////////
     ///////////////////////////////////////////////////////////////////////////////////
     ///////////////////////////// Write Wiki file RIVTA //////////////////////////////
     ///////////////////////////////////////////////////////////////////////////////////
     ///////////////////////////////////////////////////////////////////////////////////
     */

    //-----Create HTML contents, save them to a temporary map and sort the map
    tempWikiContentsRIVTA = []

    excelMasterFileSize = excelMasterFile.size()-1
    excelMasterFileSize.times {

        def thisIndex = it+1
        userfriendlyServiceDomainName = replaceNullWithSpace(excelMasterFile[thisIndex].rivtacommonname)
        serviceDomainName             = replaceNullWithSpace(excelMasterFile[thisIndex].rivtaservicedomain)
        version                       = replaceNullWithSpace(excelMasterFile[thisIndex].version)
        rivtaBP20                     = replaceNullWithSpace(excelMasterFile[thisIndex].rivtabp20)
        rivtaBP21                     = replaceNullWithSpace(excelMasterFile[thisIndex].rivtabp21)
        serviceContractCategory       = replaceNullWithSpace(excelMasterFile[it+1].contractcategory)

        log(loglevelDebug, "userfriendlyServiceDomainName: " + userfriendlyServiceDomainName)
        log(loglevelDebug, "serviceDomainName: " + serviceDomainName)
        log(loglevelDebug, "version: " + version)
        log(loglevelDebug, "rivtaBP20: " + rivtaBP20)
        log(loglevelDebug, "rivtaBP21: " + rivtaBP21)
        log(loglevelDebug, "serviceContractCategory: " + serviceContractCategory)

        downloadURL = getDownloadLinkFromRIVTA(serviceDomainName)
        if (downloadURL != null) {
            downloadLink = "[" + getDownloadLinkFromRIVTA(serviceDomainName) + " Ladda ner]"
        } else {
            downloadLink = ""
        }

        //-----Write Wiki table, using available data
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
    new File(localRIVTATargetFolder + wikiTableFileRIVTA).newOutputStream().withWriter(charset) {
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
    new File(localRIVTATargetFolder + wikiTableFileRIVTA).withWriterAppend(charset) {
        out ->  tempWikiContentsRIVTA.each {
            out.println it
        }
    }

    //-----Write the end of the Wiki file
    pageFooterLines = WikiTableRIVTAPageEnd.size()
    new File(localRIVTATargetFolder + wikiTableFileRIVTA).withWriterAppend(charset) {
        writer -> pageFooterLines.times {
            writer.write WikiTableRIVTAPageEnd[it] + "\n"
        }
    }
}

//-----Show execution statistics
ShowExecutionStatistics()
