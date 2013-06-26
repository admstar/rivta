/**
 * ////////////////////////////////////////////////////////////////////////////////////////////
 * //                                                                                        //
 * //   A 'bot' script that creates documentation of service domains and service contracts   //
 * //                                                                                        //
 * ////////////////////////////////////////////////////////////////////////////////////////////
 *
 * Information sources used by this script:
 * - RIV TA (http://code.google.com/p/rivta/)
 * - The VIFO map
 *
 * Sources that are planned to be used: 
 * - CeHis documentation of status for service domains and service contracts
 * - CeHis documentation of contact information for service domains
 *
 *
 * @author Peter Hernfalk
 */

import static groovy.io.FileType.FILES

//-----Definitions
excelMasterFile = []
freeplaneMapBeginning = []
freeplaneMapBeginning[0]  = '<map version="freeplane 1.2.0">'
freeplaneMapBeginning[1]  = ""
freeplaneMapBeginning[2]  = '<!--To view this file, download free mind mapping software Freeplane from http://freeplane.sourceforge.net -->'
freeplaneMapBeginning[3]  = '<node TEXT="TK-karta" FOLDED="false" ID="ID_1723255651" CREATED="1283093380553" MODIFIED="1369133974067"><hook NAME="MapStyle">'
freeplaneMapBeginning[4]  = ""
freeplaneMapBeginning[5]  = '<map_styles>'
freeplaneMapBeginning[6]  = '<stylenode LOCALIZED_TEXT="styles.root_node">'
freeplaneMapBeginning[7]  = '<stylenode LOCALIZED_TEXT="styles.predefined" POSITION="right">'
freeplaneMapBeginning[8]  = '<stylenode LOCALIZED_TEXT="default" MAX_WIDTH="600" COLOR="#000000" STYLE="as_parent">'
freeplaneMapBeginning[9]  = '<font NAME="SansSerif" SIZE="12" BOLD="false" ITALIC="false"/>'
freeplaneMapBeginning[10] = '</stylenode>'
freeplaneMapBeginning[11] = '<stylenode LOCALIZED_TEXT="defaultstyle.details"/>'
freeplaneMapBeginning[12] = '<stylenode LOCALIZED_TEXT="defaultstyle.note"/>'
freeplaneMapBeginning[13] = '<stylenode LOCALIZED_TEXT="defaultstyle.floating">'
freeplaneMapBeginning[14] = '<edge STYLE="hide_edge"/>'
freeplaneMapBeginning[15] = '<cloud COLOR="#f0f0f0" SHAPE="ROUND_RECT"/>'
freeplaneMapBeginning[16] = '</stylenode>'
freeplaneMapBeginning[17] = '</stylenode>'
freeplaneMapBeginning[18] = '<stylenode LOCALIZED_TEXT="styles.user-defined" POSITION="right">'
freeplaneMapBeginning[19] = '<stylenode LOCALIZED_TEXT="styles.topic" COLOR="#18898b" STYLE="fork">'
freeplaneMapBeginning[20] = '<font NAME="Liberation Sans" SIZE="12" BOLD="true"/>'
freeplaneMapBeginning[21] = '</stylenode>'
freeplaneMapBeginning[22] = '<stylenode LOCALIZED_TEXT="styles.subtopic" COLOR="#cc3300" STYLE="fork">'
freeplaneMapBeginning[23] = '<font NAME="Liberation Sans" SIZE="12" BOLD="true"/>'
freeplaneMapBeginning[24] = '</stylenode>'
freeplaneMapBeginning[25] = '<stylenode LOCALIZED_TEXT="styles.subsubtopic" COLOR="#669900">'
freeplaneMapBeginning[26] = '<font NAME="Liberation Sans" SIZE="12" BOLD="true"/>'
freeplaneMapBeginning[27] = '</stylenode>'
freeplaneMapBeginning[28] = '<stylenode LOCALIZED_TEXT="styles.important">'
freeplaneMapBeginning[29] = '<icon BUILTIN="yes"/>'
freeplaneMapBeginning[30] = '</stylenode>'
freeplaneMapBeginning[31] = '</stylenode>'
freeplaneMapBeginning[32] = '<stylenode LOCALIZED_TEXT="styles.AutomaticLayout" POSITION="right">'
freeplaneMapBeginning[33] = '<stylenode LOCALIZED_TEXT="AutomaticLayout.level.root" COLOR="#000000">'
freeplaneMapBeginning[34] = '<font SIZE="20"/>'
freeplaneMapBeginning[35] = '</stylenode>'
freeplaneMapBeginning[36] = '<stylenode LOCALIZED_TEXT="AutomaticLayout.level,1" COLOR="#0033ff">'
freeplaneMapBeginning[37] = '<font SIZE="18"/>'
freeplaneMapBeginning[38] = '</stylenode>'
freeplaneMapBeginning[39] = '<stylenode LOCALIZED_TEXT="AutomaticLayout.level,2" COLOR="#00b439">'
freeplaneMapBeginning[40] = '<font SIZE="16"/>'
freeplaneMapBeginning[41] = '</stylenode>'
freeplaneMapBeginning[42] = '<stylenode LOCALIZED_TEXT="AutomaticLayout.level,3" COLOR="#990000">'
freeplaneMapBeginning[43] = '<font SIZE="14"/>'
freeplaneMapBeginning[44] = '</stylenode>'
freeplaneMapBeginning[45] = '<stylenode LOCALIZED_TEXT="AutomaticLayout.level,4" COLOR="#111111">'
freeplaneMapBeginning[46] = '<font SIZE="12"/>'
freeplaneMapBeginning[47] = '</stylenode>'
freeplaneMapBeginning[48] = '</stylenode>'
freeplaneMapBeginning[49] = '</stylenode>'
freeplaneMapBeginning[50] = '</map_styles>'
freeplaneMapBeginning[51] = '</hook>'
freeplaneMapBeginning[52] = '<hook NAME="AutomaticEdgeColor" COUNTER="2"/>'
freeplaneMapEnd = []
freeplaneMapEnd[0] = '</node>'
freeplaneMapEnd[1] = '</map>'
HtmlTableBeginning = []
HtmlTableBeginning[0]  = '<table border="1" cellpadding="1" cellspacing="1" style="width: 730px; height: 1396px;">'
HtmlTableBeginning[1]  = '<tbody>'
HtmlTableBeginning[2]  = '<tr>'
HtmlTableBeginning[3]  = '<td>'
HtmlTableBeginning[4]  = '<p><strong>Svenskt tjänstedomännamn</strong></p>'
HtmlTableBeginning[5]  = '</td>'
HtmlTableBeginning[6]  = '<td>'
HtmlTableBeginning[7]  = '<p>'
HtmlTableBeginning[8]  = '<strong><strong><br />'
HtmlTableBeginning[9]  = 'Namn på google code, rivta-webben</strong></strong></p>'
HtmlTableBeginning[10] = '</td>'
HtmlTableBeginning[11] = '<td>'
HtmlTableBeginning[12] = '<p><strong>Ingående tjänstekontrakt</strong></p>'
HtmlTableBeginning[13] = '</td>'
HtmlTableBeginning[14] = '<td>'
HtmlTableBeginning[15] = '<p><strong>Tjänstedomän-förvaltare, kontakt</strong></p>'
HtmlTableBeginning[16] = '</td>'
HtmlTableBeginning[17] = '</tr>'
HtmlTableEnd = []
HtmlTableEnd[0] = '</tbody>'
HtmlTableEnd[1] = '</table>'
htmltTableFile = "/_devHtmlTables.html"
htbSize = HtmlTableBeginning.size
hteSize = HtmlTableEnd.size

logFile = "/_logFile.txt"
loglevelDebug = "DEBUG"
loglevelError = "ERROR"
loglevelInfo = "INFO"
loglevelWarning = "WARNING"
//lookForDomainContact = "lookForDomainContact"
//lookForDomainName = "lookForDomainName"
mapLevel1 = ['clinicalprocess':'Medarbetare', 'infrastructure':'Infrastruktur']
mapLevel2 = ['clinicalprocess':'Vård och omsorgs kärnprocess', 'infrastructure':'Infrastruktur']
mapLevel3 = ['activity':'Hantera aktiviteter', 'activityprescription':'Hantera aktiviteter', 'healthcond':'Hantera hälsorelaterade tillstånd', 'logistics':'Logistik']
mapLevel4 = ['description':'Tillståndsbeskrivning', 'request':'request', 'trunk':'trunk', 'actoutcome':'actoutcome', 'logistics':'logistics', 'organisation':'organisation']
mbSize = freeplaneMapBeginning.size
meSize = freeplaneMapEnd.size
mindmapFile = "/_devMap.mm"
outputCodeStructure = "codeStructure"
outputVIFOStructure = "VIFO"
codeStructureLevel1 = "Published Service contracts"
output2All = "all"
output2Mindmap = "mindmap"
output2HtmlTable = "htmlTable"
publishStatus4Wsdl = ['GetAllHealthcareFacilitiesInteraction_1.1_RIVTABP21.wsdl':true, 'DiscontinuePrescriptionInteraction_1.0_RIVTABP21.wsdl':true]
RIVTADomainFolder = "http://rivta.googlecode.com/svn/ServiceInteractions/riv/"
RIVTACheckoutCommand = "svn checkout " + RIVTADomainFolder + " "
trunkDocsSubPath = "/trunk/docs/"
wsdlList = []


//---------------------------------------- Usage settings ----------------------------------------//
charset = "ISO-8859-1"                        //--- ("ISO-8859-1", "UTF-8")
delimiterToken = '/'                         //--- (mac = '/', windows = '.')
downloadTKFiles = false                      //--- (true, false)
excelFile = "/Users/peterhernfalk/Desktop/_Peter_Files/Ineraprojekt/AL/Aktiviteter/Landskap med TP och TK/Groovyscript/Underlag/MasterTest.xls"
mindmapStructure = outputCodeStructure       //--- (outputCodeStructure, outputVIFOStructure)
outputType = output2All                  //--- (output2All, output2Mindmap,output2HtmlTable) 
RIVTATargetFolder = "/Users/peterhernfalk/Desktop/_Peter_Files/rivta/"
useAndVerifyDocumentationURL = false           //--- (true, false)
useStatusFilter = false                      //--- (true, false)
useVIFOLevel1ForCodeStructure = false         //--- (true, false)
//-----------------------------------------------------------------------------------------------//


//-----Downloads all TK files from the RIV-TA site
RIVTACheckoutCommandToFolder = RIVTACheckoutCommand + RIVTATargetFolder
if (downloadTKFiles == true) {

    //-----Delete the local target folder before download
    new File(RIVTATargetFolder).deleteDir()
    
    //-----Download files from the RIV-TA site to the local target folder
    def process = RIVTACheckoutCommandToFolder.execute()
    process.in.eachLine { line -> log(loglevelDebug, "Downloading: " + line) }
}

//-----Download information regarding current status for service domains and service contracts
if (useStatusFilter == true) {
    //-----2do: add download code that fills status map(s), such as "publishStatus4Wsdl"
}  

//-----Analyze and filter the downloaded TK files, extract information ad write it to a temporary map
new File(RIVTATargetFolder).eachFileRecurse(FILES) {
    if((it.name.endsWith('.wsdl')) && (it.path.contains("trunk"))) {
        
        ext = it.path.tokenize(delimiterToken)

        //-----VIFO-structure: look up and use the swedish names that corresponds to the path tokens
        def pathLevel2 = ext[RIVTATargetFolder.count(delimiterToken)-1]
        def pathLevel3 = ext[RIVTATargetFolder.count(delimiterToken)]
        def pathLevel4 = ext[RIVTATargetFolder.count(delimiterToken)+1]


        //-----Chosen language
        if (mindmapStructure == outputVIFOStructure) {
            Level1 = mapLevel1.get(pathLevel2)
            Level2 = mapLevel2.get(pathLevel2)
            Level3 = mapLevel3.get(pathLevel3)
            Level4 = mapLevel4.get(pathLevel4)
        } else {
            if (useVIFOLevel1ForCodeStructure == true) { 
                Level1 = mapLevel1.get(pathLevel2)
            } else {
                Level1 = codeStructureLevel1
            }
            Level2 = pathLevel2
            Level3 = pathLevel3
            Level4 = pathLevel4
            if ((Level4 == "tags") || (Level4 == "trunk")) { Level4 = " " } //-----Not perfect; it creates an empty node at level 4
        }
        
        //-----Initialize the Level variables
        if (Level1 == null) { Level1 = ext[RIVTATargetFolder.count(delimiterToken)-2] }
        if (Level2 == null) { Level2 = pathLevel2 }
        if (Level3 == null) { Level3 = pathLevel3 }
        if (Level4 == null) { Level4 = pathLevel4 }
        
        Level5 = it.name
                
        //-----Filter the contracts that will be displayed in the presentation map
        if (IsContractPublished(Level1, Level2, Level3, Level4, Level5) == true) {
            //-----Read and parse current wsdl file, extract the method names
            Level6 = "" 
            def xml = new XmlSlurper().parse(it)
            def wsdl = xml.children().children().each { 
                if (it.name() == "operation") { 
                    def tkPrefix = ""        //"TK: "
                    /*if (outputType == output2HtmlTable) {
                        tkPrefix = ""
                    }*/
                    Level6 = tkPrefix + it.attributes().name
                }
            }
            log(loglevelDebug, "Nivå 1: " + Level1 + "   Nivå 2: " + Level2 + "   Nivå 3: " + Level3 + "   Nivå 4: " + Level4 + "   Nivå 5: " + Level5 + "   Nivå 6: " + Level6)
            wsdlList << Level1 + delimiterToken + Level2 + delimiterToken + Level3 + delimiterToken + Level4 + delimiterToken + Level5 + delimiterToken + Level6
        }
    }
}



//-----Sort the map that contains all Service Contract descriptions
wsdlList.sort()
def wsdlListSize = wsdlList.size

if ((outputType == output2Mindmap) || (outputType == output2All)) {
/** 
    ///////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////
    //////////////////// Write mindmap i Freeplane format /////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////
*/
    //-----Write a Freeplane file, containing all Service Contract descriptions
    new File(RIVTATargetFolder + mindmapFile).newOutputStream().withWriter(charset) { 
        writer -> mbSize.times { writer.write freeplaneMapBeginning[it] + "\n" }
    }
    
    //-----Write nodes in mindmap format to the mindmap file
    def lastLevel1 = ""
    def lastLevel2 = ""
    def lastLevel3 = ""
    def lastLevel4 = ""
    def firstTime = true
    
    new File(RIVTATargetFolder + mindmapFile).withWriterAppend(charset) { 
        writer -> wsdlListSize.times { 
            def ext = wsdlList[it].tokenize(delimiterToken)
            //-----2do: look up and use the swedish names that corresponds to the path tokens
            def newLevel1 = ext[0]
            def newLevel2 = ext[1]
            def newLevel3 = ext[2]
            def newLevel4 = ext[3]
            def newLevel5 = ext[4]
            def newLevel6 = ext[5]
            
            def urlEmptyLevel4 = RIVTADomainFolder + newLevel2 + delimiterToken + newLevel3 + trunkDocsSubPath
            def urlAllLevels = RIVTADomainFolder + newLevel2 + delimiterToken + newLevel3 + delimiterToken + newLevel4 + trunkDocsSubPath
    
            //-----Write the first node
            if(firstTime == true) { 
                firstTime = false
                writer.write '<node TEXT="' + newLevel1 + '" POSITION="right" ID="ID_' + (it.value+1) + '" CREATED="1" MODIFIED="1">' + "\n" 
                writer.write '<node TEXT="' + newLevel2 + '"ID="ID_' + (it.value+2) + '" CREATED="1" MODIFIED="1">' + "\n"
                writer.write '<node TEXT="' + newLevel3 + '"ID="ID_' + (it.value+3) + '" CREATED="1" MODIFIED="1">' + "\n"
                writer.write '<node TEXT="' + newLevel4 + '"ID="ID_' + (it.value+3) + '" CREATED="1" MODIFIED="1">' + "\n"
                writer.write '<node TEXT="' + newLevel5 + '"ID="ID_' + (it.value+5) + '" CREATED="1" MODIFIED="1">' + "\n"
                //-----Add a link to the docs folder on the RIV-TA site
                
                if (newLevel4 == " ") {
                    if ((useAndVerifyDocumentationURL == true) && (getResponseCode(urlEmptyLevel4) == 200)) {
                        writer.write '<node TEXT="Dokumentation" ID="ID_' + (10000+it.value+1) + '" CREATED="1" MODIFIED="1" LINK="' + RIVTADomainFolder + newLevel2 + delimiterToken + newLevel3 + trunkDocsSubPath + '"/>' + "\n"
                    }
                } else if ((useAndVerifyDocumentationURL == true) && (getResponseCode(urlAllLevels) == 200)) {
                    writer.write '<node TEXT="Dokumentation" ID="ID_' + (10000+it.value+1) + '" CREATED="1" MODIFIED="1" LINK="' + RIVTADomainFolder + newLevel2 + delimiterToken + newLevel3 + delimiterToken + newLevel4 + trunkDocsSubPath + '"/>' + "\n"
                }
                writer.write '<node TEXT="' + newLevel6 + '"ID="ID_' + (it.value+6) + '" CREATED="1" MODIFIED="1"/>' + "\n"
                writer.write  '</node>' + "\n"
    
            //-----Write all other nodes (except the first)
            } else {
                if ((newLevel1 == lastLevel1) && (newLevel2 == lastLevel2) && (newLevel3 == lastLevel3) && (newLevel4 == lastLevel4)) {
                    writer.write '<node TEXT="' + newLevel5 + '"ID="ID_' + (it.value+5) + '" CREATED="1" MODIFIED="1">' + "\n"
                    if (newLevel4 == " ") {
                        if ((useAndVerifyDocumentationURL == true) && (getResponseCode(urlEmptyLevel4) == 200)) {
                            writer.write '<node TEXT="Dokumentation" ID="ID_' + (20000+it.value+1) + '" CREATED="1" MODIFIED="1" LINK="' + RIVTADomainFolder + newLevel2 + delimiterToken + newLevel3 + trunkDocsSubPath + '"/>' + "\n"
                        }
                    } else if ((useAndVerifyDocumentationURL == true) && (getResponseCode(urlAllLevels) == 200)) {
                            writer.write '<node TEXT="Dokumentation" ID="ID_' + (20000+it.value+1) + '" CREATED="1" MODIFIED="1" LINK="' + RIVTADomainFolder + newLevel2 + delimiterToken + newLevel3 + delimiterToken + newLevel4 + trunkDocsSubPath + '"/>' + "\n"
                    }
                    writer.write '<node TEXT="' + newLevel6 + '"ID="ID_' + (it.value+6) + '" CREATED="1" MODIFIED="1"/>' + "\n"
                    writer.write  '</node>' + "\n"
                } else {
                    //-----Multiple nodes at level 4
                    if ((newLevel1 == lastLevel1) && (newLevel2 == lastLevel2) && (newLevel3 == lastLevel3) && (newLevel4 != lastLevel4)) {
                        1.times { writer.write  '</node>' + "\n" }
    
                    //-----Multiple nodes at level 3
                    } else if ((newLevel1 == lastLevel1) && (newLevel2 == lastLevel2) && (newLevel3 != lastLevel3)) {
                        2.times { writer.write  '</node>' + "\n" }
                        writer.write '<node TEXT="' + newLevel3 + '"ID="ID_' + (it.value+3) + '" CREATED="1" MODIFIED="1">' + "\n"
    
                    //-----Multiple nodes at level 2
                    } else if ((newLevel1 == lastLevel1) && (newLevel2 != lastLevel2)) {
                        3.times { writer.write  '</node>' + "\n" }
                        writer.write '<node TEXT="' + newLevel2 + '"ID="ID_' + (it.value+2) + '" CREATED="1" MODIFIED="1">' + "\n"
                        writer.write '<node TEXT="' + newLevel3 + '"ID="ID_' + (it.value+3) + '" CREATED="1" MODIFIED="1">' + "\n"
    
                    } else {
                        4.times { writer.write  '</node>' + "\n" }
                        writer.write '<node TEXT="' + newLevel1 + '" POSITION="right" ID="ID_' + (it.value+1) + '" CREATED="1" MODIFIED="1">' + "\n" 
                        writer.write '<node TEXT="' + newLevel2 + '"ID="ID_' + (it.value+2) + '" CREATED="1" MODIFIED="1">' + "\n"
                        writer.write '<node TEXT="' + newLevel3 + '"ID="ID_' + (it.value+3) + '" CREATED="1" MODIFIED="1">' + "\n"
                        }
                    writer.write '<node TEXT="' + newLevel4 + '"ID="ID_' + (it.value+3) + '" CREATED="1" MODIFIED="1">' + "\n"
                    writer.write '<node TEXT="' + newLevel5 + '"ID="ID_' + (it.value+5) + '" CREATED="1" MODIFIED="1">' + "\n"
                    if (newLevel4 == " ") {
                        if ((useAndVerifyDocumentationURL == true) && (getResponseCode(urlEmptyLevel4) == 200)) {
                            writer.write '<node TEXT="Dokumentation" ID="ID_' + (30000+it.value+1) + '" CREATED="1" MODIFIED="1" LINK="' + RIVTADomainFolder + newLevel2 + delimiterToken + newLevel3 + trunkDocsSubPath + '"/>' + "\n"
                        }
                    } else if ((useAndVerifyDocumentationURL == true) && (getResponseCode(urlAllLevels) == 200)) {
                            writer.write '<node TEXT="Dokumentation" ID="ID_' + (30000+it.value+1) + '" CREATED="1" MODIFIED="1" LINK="' + RIVTADomainFolder + newLevel2 + delimiterToken + newLevel3 + delimiterToken + newLevel4 + trunkDocsSubPath + '"/>' + "\n"
                    }
                    writer.write '<node TEXT="' + newLevel6 + '"ID="ID_' + (it.value+6) + '" CREATED="1" MODIFIED="1"/>' + "\n"
                    writer.write  '</node>' + "\n"
                }
            }
            lastLevel1 = newLevel1
            lastLevel2 = newLevel2
            lastLevel3 = newLevel3
            lastLevel4 = newLevel4
        }
        writer.write  '</node>' + "\n" + '</node>' + "\n" + '</node>' + "\n" + '</node>' + "\n"
    }
        
    //-----Write the last part of the mindmap file
    new File(RIVTATargetFolder + mindmapFile).withWriterAppend(charset) { 
        writer -> meSize.times { writer.write freeplaneMapEnd[it] + "\n" }
    }
} 

if ((outputType == output2HtmlTable) || (outputType == output2All)) {

    /** 
    ///////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////// Write html file /////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////
    */

    //-----Fetch a copy of the contents of the Excel file
    //-----2do: modify this so it's possible to use a smarter fetch och hash values
    def index = 0
    new ExcelReader(excelFile).eachLine {
        excelMasterFile[index] = [contract:"${cell(0)}".trim(), contractcategory:"${cell(1)}".trim(),
                rivtaname:"${cell(2)}".trim(), domaincategory:"${cell(3)}".trim(),
                vifodomain:"${cell(4)}".trim(), swedishdomain:"${cell(5)}".trim(),
                domaincontact:"${cell(6)}".trim()]
        log(loglevelDebug, "excelMasterFile[index]: " +excelMasterFile[index])
        index += 1
    }

    //-----2do: recursive logic that creates as many tables as needed
    
    //-----Write a Html Table file, containing all Service Contract descriptions
    new File(RIVTATargetFolder + htmltTableFile).newOutputStream().withWriter(charset) { 
        writer -> htbSize.times { writer.write HtmlTableBeginning[it] + "\n" }
    }

    //-----Write nodes in table format to the html file
    def lastLevel1 = ""
    def lastLevel2 = ""
    def lastLevel3 = ""
    def lastLevel4 = ""
    def lastServiceDomainContact = ""
    def lastServiceDomainName = ""
    def firstTime = true

    new File(RIVTATargetFolder + htmltTableFile).withWriterAppend(charset) { 
        writer -> wsdlListSize.times { 
            def ext = wsdlList[it].tokenize(delimiterToken)
            //-----2do: look up and use the swedish names that corresponds to the path tokens
            def newLevel1 = ext[0]
            def newLevel2 = ext[1]
            def newLevel3 = ext[2]
            def newLevel4 = ext[3]
            def newLevel5 = ext[4]
            def newLevel6 = ext[5]
            def urlEmptyLevel4 = RIVTADomainFolder + newLevel2 + delimiterToken + newLevel3 + trunkDocsSubPath
            def urlAllLevels = RIVTADomainFolder + newLevel2 + delimiterToken + newLevel3 + delimiterToken + newLevel4 + trunkDocsSubPath

            serviceDomainName = excelMasterFile.find { it.contract.toLowerCase() == newLevel6.toLowerCase() }?.with { map -> "$map.swedishdomain" }
            if (serviceDomainName == null) { serviceDomainName = " " }
            serviceDomainContact = excelMasterFile.find { it.contract.toLowerCase() == newLevel6.toLowerCase() }?.with { map -> "$map.domaincontact" }
            lastServiceDomainContact = addMailto(lastServiceDomainContact)
            log(loglevelDebug, "serviceDomain (Name+Contact): " + newLevel6 + "  " + serviceDomainName + "  " + lastServiceDomainContact)
            if(firstTime == true) {
                //log(loglevelDebug, "serviceDomain (Name+Contact): " + newLevel6 + "  " + serviceDomainName + "  " + lastServiceDomainContact)
                firstTime = false
                writer.write '<tr>' + "\n"
                writer.write '<td><p>' + serviceDomainName + '</p></td>' + "\n" 
                writer.write '<td><p>' + newLevel2 + ":" + newLevel3 + '</p></td>' + "\n" 
                
                //-----Add a link to the docs folder on the RIV-TA site
                writer.write '<td>' + "\n" 

                if (newLevel4 == " ") {
                    if ((useAndVerifyDocumentationURL == true) && (getResponseCode(urlEmptyLevel4) == 200)) {
                        writer.write '<a href="' + RIVTADomainFolder + newLevel2 + delimiterToken + newLevel3 + trunkDocsSubPath + '">Dokumentation</a><br>' + "\n" 
                    }
                } else if ((useAndVerifyDocumentationURL == true) && (getResponseCode(urlAllLevels) == 200)) {
                    writer.write '<a href="' + RIVTADomainFolder + newLevel2 + delimiterToken + newLevel3 + delimiterToken + newLevel4 + trunkDocsSubPath + '">Dokumentation</a><br>' + "\n" 
                }
                writer.write newLevel6 + "\n" 
            } else {
                //log(loglevelDebug, "serviceDomain (Name+Contact): " + newLevel6 + "  " + serviceDomainName + "  " + lastServiceDomainContact)
                if ((newLevel2 != lastLevel2) || (newLevel3 != lastLevel3)) {
                    writer.write '</td>' + "\n"
                    if (lastServiceDomainContact != null && lastServiceDomainContact.indexOf('@') >= 0) {
                        writer.write '<td><a href="' + lastServiceDomainContact + '"> Tjänstedomänansvarig</td>' + "\n"
                    } else {
                        writer.write "<td></td>" + "\n"
                    }
                    writer.write '</tr>' + "\n"
                    writer.write '<tr>' + "\n"
                    writer.write '<td><p>' + serviceDomainName + '</p></td>' + "\n"
                    writer.write '<td><p>' + newLevel2 + ":" + newLevel3 + '</p></td>' + "\n"
                    writer.write '<td>' + "\n" 
                    if (newLevel4 == " ") {
                        if ((useAndVerifyDocumentationURL == true) && (getResponseCode(urlEmptyLevel4) == 200)) {
                            writer.write '<a href="' + RIVTADomainFolder + newLevel2 + delimiterToken + newLevel3 + trunkDocsSubPath + '">Dokumentation</a><br>' + "\n" 
                        }
                    } else if ((useAndVerifyDocumentationURL == true) && (getResponseCode(urlAllLevels) == 200)) {
                        writer.write '<a href="' + RIVTADomainFolder + newLevel2 + delimiterToken + newLevel3 + delimiterToken + newLevel4 + trunkDocsSubPath + '">Dokumentation</a><br>' + "\n" 
                    }
                    writer.write newLevel6 + "\n" 
                } else {
                    writer.write '<br>' + newLevel6 + "\n" 
                }
            }
        lastLevel1 = newLevel1
        lastLevel2 = newLevel2
        lastLevel3 = newLevel3
        lastLevel4 = newLevel4
        lastServiceDomainContact = serviceDomainContact
        lastServiceDomainName = serviceDomainName
        }

    }

    //-----Write the last part of the Html file
    new File(RIVTATargetFolder + htmltTableFile).withWriterAppend(charset) { 
        writer -> hteSize.times { writer.write HtmlTableEnd[it] + "\n" }
    }
}


//-----Write a text file, containing the paths and names of the wsdl files
File lstFile = new File(RIVTATargetFolder + logFile)
lstFile.withWriter{ 
    out ->  wsdlList.each {
        out.println it
      }
}

//-----Show execution statistics
ShowExecutionStatistics()


//-------------------- Methods --------------------//

/** Adds "mailto:" to a valid e-mail address */
def addMailto(lastServiceDomainContact) {
    if (lastServiceDomainContact != null && lastServiceDomainContact.indexOf('@') >= 0) {
        lastServiceDomainContact = "mailto:" + lastServiceDomainContact
    } else {
        lastServiceDomainContact = " "
    }
}

/** Logs text */
def log(level, text) {
    //-----2do: add logic that directs the output to configured target
    println text
}

/** Verifies if the URL is working or not */
public static int getResponseCode(String urlString) throws MalformedURLException, IOException {
    URL u = new URL(urlString); 
    HttpURLConnection huc =  (HttpURLConnection) u.openConnection(); 
    huc.setRequestMethod("GET"); 
    huc.connect(); 
    if (huc.getResponseCode() != 200) {
        log("INFO", "Broken link: " + urlString)
    }
    
    return huc.getResponseCode();
}


/** Displays execution statistics */
def ShowExecutionStatistics() {
    log("INFO", "")
    log("INFO", "----- Execution statistics -----")
    log("INFO", wsdlList.size + " WSDL files, excisting in trunk directories, were listed")
}


/** Decides wether a service contract is apporved to be published or not */
private boolean IsContractPublished(String level1, level2, level3, level4, level5) {
  isPublished = false

  if (useStatusFilter == true) {
      //-----Look up status for the wsdl file
      isPublished = publishStatus4Wsdl.get(level5)
  } else { 
      isPublished = true
  }
  
  return isPublished
}