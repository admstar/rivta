| **Version** | **Datum** | **Ändringar** | **Person** |
|:------------|:----------|:---------------|:-----------|
| 1 | 2013-10-22 | - | Björn Genfors |
| 2 | 2013-12-16 | Justerat beskrivningen av HSAIdType och orgUnitHSAId (enligt [issue 221](https://code.google.com/p/rivta/issues/detail?id=221)) | Björn Genfors |
| 3 | 2014-02-17 | Justerat schema-innehåll | Khaled Daham |
| 4 | 2014-03-21 | -Lagt till ResultType, som används för att hantera logiska fel. <br>-Lagt till RoleCode 0..1 i LegalAuthenticatorType (behov fanns i kontrakten för bild och EKG). <br>-Lagt till länkar till lista över vanligt förekommande kodverk och identifierare på lämpliga ställen. <table><thead><th> Björn Genfors </th></thead><tbody>
<tr><td> 5 </td><td> 2014-05-13 </td><td> -Lagt till PQIntervalType. <br>-Justerat DateType att tillåta variabel noggrannhet. <br>-Förtydligat beskrivningen av vissa typer och/eller fält. </td><td> Björn Genfors </td></tr>
<tr><td> 6 </td><td> 2014-08-21 </td><td> Uppdaterad beskrivning för DateType och TimeStampType.</td><td> Khaled Daham </td></tr>
<tr><td> 7 </td><td> 2014-09-10 </td><td> -Korrigerat dokumentation för DateType för att revertera möjligheten till variabel noggrannhet (kompatibilitetsbekymmer med ISO-standarder) <br>-Lagt till en ny typ, PartialDateType, för att bära information om datum med variabel noggrannhet. </td><td> Björn Genfors </td></tr></tbody></table>

<h1>Här dokumenteras informella best-practices för datatypshantering i tjänstekontrakt</h1>

Denna sida syftar till att öka återanvändningen av datatyper, och  i förlängningen verka för återanvändbara profiler.<br>
<br>
<h2>Gemensamma informationskomponenter</h2>
I tjänstekontraktsbeskrivningarna används ett antal komponenter som är gemensamma för vissa meddelanden i flera domäner eller inom denna domän, och dessa beskrivs i detta avsnitt.<br>
Observera att med anledning av att tjänstekontrakten även kan stödjas av producentsystem som saknar (fullständig) HSA-id-information så är HSA-id-attribut i beskrivningarna nedan valfria. Se även avsnittet ”Informationssäkerhet” ovan.<br>
<br>
<h3><a href='ActorType.md'>ActorType</a></h3>
Information om medarbetare i vård- och omsorg som genomfört den behandling som rapporteras genom tjänstekontrakt i denna domän.<br>
<br>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>hsaId	</td><td>HSAIdType</td><td>	HSA-id för personen	</td><td>0..1</td></tr>
<tr><td>name	</td><td>string	</td><td>Namn på personen. Minst ett av dessa fälten hsaId och name ska anges.	</td><td>0..1</td></tr>
<tr><td>personTelecom	</td><td>string	</td><td>Telefon till personen.	</td><td>0..1</td></tr>
<tr><td>personEmail	</td><td>string	</td><td>Epostadress till personen.	</td><td>0..1</td></tr>
<tr><td>personAddress	</td><td>string	</td><td>Postadress till personen.	</td><td>0..1</td></tr></tbody></table>

<h3><a href='CVType.md'>CVType</a></h3>
Typ som beskriver kodade värden med en struktur hämtad från HL7 v3 CV (”CodedValue”). Kodade värden avser officiellt hanterade kodverk som hänvisas till med codeSystem OID/UUID <a href='ListOfCommonlyUsedCodeSystems.md'>(Vanligt förekommande kodverk)</a>.<br>
<br>
För annan användning av koder, exempelvis för lokala kodverk utan OID, skall originalText attributet användas för att ge kodens text i det lokala systemet, och övriga attribut lämnas tomma.<br>
<br>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>code	</td><td>string	</td><td>Kod enligt producentsystemets kodverk. Om code anges skall också codeSystem  samt displayName anges.	</td><td>0..1</td></tr>
<tr><td>codeSystem	</td><td>string	</td><td>Anger kodverket som definierar koden. Dvs UID/OID för det kodverk som används. Om codeSystem anges skall också code samt displayName anges.	</td><td>0..1</td></tr>
<tr><td>codeSystemName	</td><td>string	</td><td>Kodverkets namn i klartext. Skall anges när så är möjligt.	</td><td>0..1</td></tr>
<tr><td>codeSystemVersion	</td><td>string	</td><td>Om tillämpbart, versionsangivelse som definierats av det givna kodsystemet.	</td><td>0..1</td></tr>
<tr><td>displayName	</td><td>string	</td><td>Koden i klartext, under vilket det producerande systemet visar koden för sina användare. Om separat displayName inte finns i producerande system skall det ange samma värde som för code. </td><td>	0..1</td></tr>
<tr><td>originalText	</td><td>string	</td><td> originalText ska användas vid överföring av värden som kommer från lokala kodverk som ej är identifierade med OID eller när kod helt saknas. I sådana fall skall en beskrivande text anges i originalText. Om originalText anges kan ingen av de övriga elementen anges.	</td><td>0..1</td></tr></tbody></table>

<h3><a href='DatePeriodType.md'>DatePeriodType</a></h3>
Ett datumintervall anges normalt sett med ett start- och ett slutdatum, men öppna intervall är tillåtna. Huruvida ändpunkterna inkluderas i intervallet eller ej bör tydligt beskrivas vid varje enskild tillämpning.<br>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>start	</td><td>DateType	</td><td>Periodens startdatum. Minst ett av start och end skall anges.	</td><td>0..1</td></tr>
<tr><td>end	</td><td>DateType	</td><td>Periodens slutdatum. Minst ett av start och end skall anges.	</td><td>0..1</td></tr></tbody></table>

<h3>DateType</h3>
Datum anges på formatet ”ÅÅÅÅMMDD”. Detta motsvarar den ISO 8601 och ISO 8824-kompatibla formatbeskrivningen ”YYYYMMDD”.<br>
Tidszon anges inte i meddelandeformaten. All information om datum och tidpunkter som utbyts via tjänsterna ska ange datum och tidpunkter i den tidszon som gäller/gällde i Sverige vid den tidpunkt som respektive datum- eller tidpunktsfält bär information om. Såväl tjänstekonsumenter som tjänsteproducenter skall med andra ord förutsätta att datum och tidpunkter som utbyts är i tidszonerna CET (svensk normaltid) respektive CEST (svensk normaltid med justering för sommartid).<br>
<br>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>date	</td><td>string	</td><td>Datum uttrycks på formatet ”ÅÅÅÅMMDD”.	</td><td>1..1</td></tr></tbody></table>

<h3><a href='HealthcareProfessionalType.md'>HealthcareProfessionalType</a></h3>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>authorTime	</td><td>TimeStampType	</td><td>Den tidpunkt då dokumentet skapades, eller annan tillämplig tidpunkt.	</td><td>1..1</td></tr>
<tr><td>healthcareProfessionalHSAId	</td><td>HSAIdType	</td><td>HSA-id för vård- och omsorgspersonal. Skall anges om tillgänglig.	</td><td>0..1</td></tr>
<tr><td>healthcareProfessionalName	</td><td>string	</td><td>Namn på vård- och omsorgspersonal. Om tillgängligt skall detta anges.	</td><td>0..1</td></tr>
<tr><td>healthcareProfessionalRoleCode	</td><td>CVType	</td><td>Information om personens befattning. Om möjligt skall <a href='ListOfCommonlyUsedCodeSystems.md'>KV Befattning</a> användas.	</td><td>0..1</td></tr>
<tr><td>healthCareProfessionalOrgUnit	</td><td>OrgUnitType	</td><td>Den organisation som angiven vård- och omsorgsperson är uppdragstagare på. Om tillgängligt skall detta anges.</td><td>0..1</td></tr>
<tr><td>healthcareProfessionalCareUnitHSAId</td><td>	HSAIdType</td><td>	HSA-id för PDL-enhet som vård- och omsorgspersonen är uppdragstagare för. Skall anges om tillgänglig. </td><td>0..1 </td></tr>
<tr><td>healthcareProfessionalCareGiverHSAId	</td><td>HSAIdType</td><td> 	HSA-id för vårdgivaren, som är vårdgivare för den enhet som författaren är uppdragstagare för. Skall anges om tillgänglig.</td><td>0..1</td></tr></tbody></table>

<h3><a href='HSAIdType.md'>HSAIdType</a></h3>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>hsaId	</td><td>string	</td><td>HSA-id enligt definition från Inera AB. I de fall då HSA-id inte finns tillgängligt ska ett för källsystemet lokalt id användas. Lokala id:n får enbart användas i OrgUnitType, och då endast i undantagsfall.	</td><td>1..1</td></tr></tbody></table>

<h3><a href='IIType.md'>IIType</a></h3>
En universellt unik identifierare.<br>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>root	</td><td>string	</td><td>En universellt unik identifierare eller en identifierare som tillsammans med värdet för ”extention” ger en universellt unik identifierare.	</td><td>1..1</td></tr>
<tr><td>extension	</td><td>string	</td><td>En textsträng som tillsammans med värdet för "root" bildar en unik identifierare. Används om värdet på "root" inte är universellt unikt. </td><td>0..1</td></tr></tbody></table>

<h3><a href='LegalAuthenticatorType.md'>LegalAuthenticatorType</a></h3>
Används för att representera en signatur i samband med journalhandlingar.<br>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>signatureTime</td><td>	TimeStampType</td><td>	Tidpunkt för signering</td><td>	1..1</td></tr>
<tr><td>legalAuthenticatorHSAId	</td><td>HSAIdType	</td><td>HSA-id för person som signerat dokumentet	</td><td>0..1</td></tr>
<tr><td>legalAuthenticatorName	</td><td>string	</td><td>Namnen i klartext för signerande person	</td><td>0..1</td></tr>
<tr><td>legalAuthenticatorRoleCode	</td><td>CVType	</td><td>Information om personens befattning. Om möjligt skall <a href='ListOfCommonlyUsedCodeSystems.md'>KV Befattning</a> användas.	</td><td>0..1</td></tr></tbody></table>

<h3><a href='MultimediaType.md'>MultimediaType</a></h3>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>id	</td><td>string	</td><td>Identitet på multimediaobjekt som används vid referenser inom multimediadokument.	</td><td>0..1</td></tr>
<tr><td>mediaType	</td><td>MediaTypeEnum	</td><td>Mediatyper enligt HL7	</td><td>1..1</td></tr>
<tr><td>value	</td><td>base64Binary	</td><td>Value är binärdata som representerar objektet. Ett och endast ett av value och reference ska anges. 	</td><td>0..1</td></tr>
<tr><td>reference	</td><td>anyURI	</td><td>Referens till extern bild i form av en URL. Ett och endast ett av value och reference ska anges.	</td><td>0..1</td></tr></tbody></table>

<h3><a href='OrgUnitType.md'>OrgUnitType</a></h3>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>orgUnitHSAId	</td><td>HSAIdType	</td><td>HSA-id för organisationsenhet. Om tillgängligt skall detta anges. I de fall HSA-id saknas kan ett för källsystemet unikt id användas.	</td><td>0..1</td></tr>
<tr><td>orgUnitName	</td><td>string	</td><td>Namn på organisationsenhet. Om tillgängligt skall detta anges.	</td><td>0..1</td></tr>
<tr><td>orgUnitTelecom	</td><td>string	</td><td>Telefon till organisationsenhet.	</td><td>0..1</td></tr>
<tr><td>orgUnitEmail	</td><td>string	</td><td>Epost till organisationsenhet.	</td><td>0..1</td></tr>
<tr><td>orgUnitAddress	</td><td>string	</td><td>Postadress till organisationsenhet. Skrivs på ett så naturligt sätt som möjligt, exempelvis:<br>”Storgatan 12<br>468 91 Lilleby”	</td><td>0..1</td></tr>
<tr><td>orgUnitLocation	</td><td>string	</td><td>Text som anger namnet på plats eller ort för enhetens eller funktionens fysiska placering	</td><td>0..1</td></tr></tbody></table>

<h3><a href='PartialDateType.md'>PartialDateType</a></h3>
Kan beskriva ett datum med variabel noggrannhet.<br>
<br>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>format </td><td>DateTypeFormatEnum </td><td> Enum som beskriver datumets noggrannhet. Tillåtna värden är "YYYYMMDD", "YYYYMM" och "YYYY".	</td><td>1..1</td></tr>
<tr><td>value </td><td>string </td><td>Sträng som håller själva datumet, och uttrycks på det format som anges i format.	</td><td>1..1</td></tr></tbody></table>

<h3><a href='PatientSummaryHeaderType.md'>PatientSummaryHeaderType</a></h3>
Innehåller basinformation om ett dokument. Används för tjänstekontrakt som tillverkas enligt HL7-metoden "Green CDA".<br>
<br>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>documentId	</td><td>string	</td><td>Dokumentets identitet som är unik inom källsystemet.	</td><td>1..1</td></tr>
<tr><td>sourceSystemHSAId </td><td>HSAIdType </td><td>HSAid för det system som dokumentet är skapat i.	</td><td>1..1</td></tr>
<tr><td>documentTitle</td><td>	string	</td><td>Titel som beskriver den information som sänds i dokumentet.	</td><td>0..1</td></tr>
<tr><td>documentTime</td><td>	TimeStampType	</td><td>Händelsetidpunkt, om relevant.	</td><td>0..1</td></tr>
<tr><td>patientId	</td><td>PersonIdType	</td><td>Id för patienten. </td><td> 1..1</td></tr>
<tr><td>accountableHealthcareProfessional	</td><td>HealthcareProfessionalType	</td><td>Ansvarig hälso- och sjukvårdsperson.	</td><td>1..1</td></tr>
<tr><td>legalAuthenticator	</td><td>LegalAuthenticatorType </td><td>Information om vem som signerat informationen i dokumentet.</td><td> 0..1</td></tr>
<tr><td>approvedForPatient	</td><td>boolean </td><td>Anger om information får delas till patient. Värdet sätts i sådant fall till true, i annat fall till false.	</td><td>1..1</td></tr>
<tr><td>careContactId	</td><td>string </td><td>Identitet för den vård- och omsorgskontakt som föranlett den information som omfattas av dokumentet. Identiteten är unik inom källsystemet </td><td>0..1</td></tr>
<tr><td>nullified	</td><td>boolean </td><td>Anger om dokumentet makulerats i källsystemet. Sätts i så fall till true annars false. Används bl.a. i statistik-/rapportuttag med hjälp av tjänstekontrakten.	</td><td>0..1</td></tr>
<tr><td>nullifiedReason </td><td>string </td><td>Anger orsak till makulering.	</td><td>0..1</td></tr></tbody></table>

<h3>PersonIdType</h3>
Person-id är vanligtvis ett personnummer, men kan även vara samordningsnummer eller reservnummer. Syftar till att identifiera en privatperson.<br>
<br>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>id	</td><td>string	</td><td>Identiteten enligt den identitetstyp (type) som angivits. Om identiteten är av typ personnummer eller samordningsnummer skall denna anges med 12 tecken utan skiljetecken.	</td><td>1..1</td></tr>
<tr><td>type	</td><td>string </td><td><a href='ListOfCommonlyUsedCodeSystems.md'>OID</a> för typ av identifierare.	</td><td>1..1</td></tr></tbody></table>

<h3><a href='PQIntervalType.md'>PQIntervalType</a></h3>
Typ som baseras på datatypen IVL_PQ enligt HL7, och som beskriver överföring av intervaller av mätbara värden (”Physical Quantity”). Ett intervall som är öppet i ena änden kan anges.<br>
Tillåtna värden för ”unit” bestäms av <a href='http://unitsofmeasure.org/ucum.html'>http://unitsofmeasure.org/ucum.html</a>. Dimension ska preciseras av fältregel vid tillämpning (ex. ”Massa”). Vaksamhet skall iakttagas vid konvertering mellan enheter.<br>
<br>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>low </td><td>double	</td><td>Mätetal mätt i enheten som anges av ”unit”. Minst ett av fälten low och high måste anges.	</td><td>0..1</td></tr>
<tr><td>high </td><td>double	</td><td>Mätetal mätt i enheten som anges av ”unit”. Minst ett av fälten low och high måste anges.	</td><td>0..1</td></tr>
<tr><td>unit	</td><td>string	</td><td>Enhet enligt <a href='http://unitsofmeasure.org/ucum.html'>UCUM</a></td><td>1..1</td></tr></tbody></table>

<h3><a href='PQType.md'>PQType</a></h3>
Typ som baseras på datatypen PQ enligt HL7, och som beskriver överföring av mätbara värden (”Physical Quantity”). Tillåtna värden för ”unit” bestäms av <a href='http://unitsofmeasure.org/ucum.html'>http://unitsofmeasure.org/ucum.html</a>. Dimension ska preciseras av fältregel vid tillämpning (ex. ”Massa”). Vaksamhet skall iakttagas vid konvertering mellan enheter.<br>
<br>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>value	</td><td>double	</td><td>Mätetal mätt i enheten som anges av ”unit”.	</td><td>1..1</td></tr>
<tr><td>unit	</td><td>string	</td><td>Enhet enligt <a href='http://unitsofmeasure.org/ucum.html'>UCUM</a></td><td>1..1</td></tr></tbody></table>

<h3><a href='ResultType.md'>ResultType</a></h3>
Typ som ska hantera logiska fel.<br>
<br>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>resultCode </td><td>ResultCodeEnum	</td><td>Kan endast vara OK, INFO eller ERROR. </td><td>1..1</td></tr>
<tr><td>errorCode </td><td>ErrorCodeEnum	</td><td>Sätts endast om resultCode är ERROR. Tillåtna värden sätts per tjänstedomän.</td><td>0..1</td></tr>
<tr><td>subcode</td><td>string </td><td>Tillåtna värden sätts per tjänstedomän.</td><td>0..1</td></tr>
<tr><td>logId </td><td>string </td><td>En UUID som kan användas vid felanmälan för att användas vid felsökning av producent.</td><td>1..1</td></tr>
<tr><td>message </td><td>string </td><td>En beskrivande text som kan visas för användaren.</td><td>0..1</td></tr></tbody></table>

<h3><a href='TimePeriodType.md'>TimePeriodType</a></h3>
Ett tidsintervall anges normalt sett med en start- och en sluttidpunkt, men öppna intervall är tillåtna. Huruvida ändpunkterna inkluderas i intervallet eller ej bör tydligt beskrivas vid varje enskild tillämpning.<br>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>start	</td><td>TimeStampType	</td><td>Periodens starttid. Minst ett av start och end skall anges.	</td><td>0..1</td></tr>
<tr><td>end	</td><td>TimeStampType	</td><td>Periodens sluttid. Minst ett av start och end skall anges.	</td><td>0..1</td></tr></tbody></table>

<h3><a href='TimeStampType.md'>TimeStampType</a></h3>
Tidpunkter anges alltid på formatet ”ÅÅÅÅMMDDttmmss”, vilket motsvarar den ISO 8601 och ISO 8824-kompatibla formatbeskrivningen ”YYYYMMDDhhmmss”.<br>
Tidszon anges inte i meddelandeformaten. All information om datum och tidpunkter som utbyts via tjänsterna ska ange datum och tidpunkter i den tidszon som gäller/gällde i Sverige vid den tidpunkt som respektive datum- eller tidpunktsfält bär information om. Såväl tjänstekonsumenter som tjänsteproducenter skall med andra ord förutsätta att datum och tidpunkter som utbyts är i tidszonerna CET (svensk normaltid) respektive CEST (svensk normaltid med justering för sommartid).<br>
<br>
<table><thead><th> <b>Namn</b> </th><th> <b>Datatyp</b> </th><th> <b>Beskrivning</b> </th><th> <b>Kardinalitet</b> </th></thead><tbody>
<tr><td>timestamp	</td><td>string	</td><td>Tid uttrycks på formatet ”ÅÅÅÅMMDDttmmss”.	</td><td>1..1</td></tr>