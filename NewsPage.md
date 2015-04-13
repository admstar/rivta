

# Nyheter #

### 2015-01-13 2 nya RIVTA anvisningar ###

Det finns numera ARK\_0034 anvisning kryptografi och ARK\_0036 anvisning Tjänsteplattform upplagda.

### 2014-12-29 Omlagt hur länkar sker till downloads ###

Efter policy ändring på Google code så finns zip filer numera att nå via RIVTA.SE.  Länk http://rivta.se

### 2014-09-25 Uppdatering av ARK-005 och ARK-006 ###

Både tjänsteschema och domänschema har bytt till nya mallen och dessutom fått version inlagt i tabellen.

### 2014-08-28 Uppdatering av mall för tjänstekontraktsbeskrivning (ARK\_0015) ###

För att motsvara krav i konfigurationsstyrning och från generering av WEB sidor har smärre ändringar införts. OBS obligatoriskt att använda denna nya version eftersom skript utgår från detta.

### 2014-08-04 Uppdatering av script för hantering av domäner ###

Scriptet för att verifiera innehåll i en tjänstedomän har uppdaterats till version 1.2.

  * Motsvarar nu den aktuella version 2.0.5 av RIVTA Konfigurationsstyrning.
  * Ändrad regel för förenkling av namnsättning av TKB och AB.
  * Bättre kontroll av versionsnumrering.
  * Visar om ändringar skett i en tag efter det att den skapas.
  * Tydligare meddelanden

Se https://code.google.com/p/rivta/wiki/ToolsDomainManagement för mera information och länk till programmet.


### 2014-06-30 Uppdatering 2.0.5 av Konfigurationsstyrningen publicerad ###

En uppdatering av konfigurationsstyrningen har publicerats. De viktigaste förändringarna framgår av revisionshistoriken, och är:

  * Ersatte alla referenser till ”CeHis” med ”Inera”.
  * Tog bort alla referenser till självdeklaration av följsamhet.
  * Fixade enstaka typos.
  * Ändrade namnsättningsregeln för TKB och AB. Dessa skall inte längre ha versionsbeteckning i filnamnet.
  * Förtydliga mappstrukturen och kravet på en undermapp per intraktion (WSDL-fil).
  * Förtydliga versionreglerna. Tredje versionstalet uppstår endast efter den första dokumentationsuppdateringen och kan därför aldrig vara 0.
  * Förtydliga att Inera Arkitektur och regelverk skapar zip-filerna vid release.
  * Förtydliga att en tag alltid skapas mha kommandot ”svn copy”. Adderade Bilaga 5 som beskriver hur en tjänstedomän byter namn (namnrymnd).

Ni hittar, som alltid, dokumentet här: http://rivta.se/documents/ARK_0007.

I samband med detta har det sk "verifieringsscriptet" uppdaterats. Se ToolsDomainManagement.

### 2014-04-08 Revision E av RIVTA Översikt publicerad ###
En ny revision med fokus på källsystemsadressering och ändrade krav på hantering av anropsbehörighet har publicerats. Dokumentet återfinns på http://rivta.se/documents/ARK_0001 .

### 2014-04-07 Ny tabell med godkända domäner ###
Nu har vi byggt om tabellen över godkända domäner. Allt för att få den tydligare och förhoppningsvis enklare. Den finns på denna länk:
https://code.google.com/p/rivta/wiki/ServiceDomainTable

### 2014-03-19 Ytterligare utbildningstillfällen i T-bok och RIV TA ###
Den 6:e maj och 3:e juni genomförs den fjärde och femte omgången av utbildningen kring T-boken, RIV TA och Tjänsteplattformen. Mer information hittar du
[här](http://www.inera.se/TJANSTER--PROJEKT/Tjansteplattform/Utbildningsdag-i-T-boken/).

### 2014-03-05 Tredje tillfället av utbildningsdagen ###
Det tredje tillfället av utbildningsdagen i T-boken, RIV TA, Konfigurationsstyrning och Tjänsteplattformen genomfördes idag med 28 deltagare.

Presentationen från utbildningen återfinns längst ner på vår dokumentationssida, http://rivta.se/documents/.

### 2014-02-21 Uppdatering av script för hantering av domäner ###
Scriptet för att verifiera innehåll i en tjänstedomän har uppdaterats med kontroll av namn på AB och TKB. Dessutom finns nu en kort beskrivning
om hur man på ett enkelt sätt kan jämföra innehåller i olika versioner och release candidates.

Se https://code.google.com/p/rivta/wiki/ToolsDomainManagement för mera information.

### 2014-02-11 Uppdatering av Konfigurationsstyrningen ###
Uppmärksamma läsare har hört av sig och pekat på oklarheter i exempel och sökvägsdefinitioner i [Konfigurationsstyrningen](http://rivta.se/documents/ARK_0007). Vi har även passat på att definiera regler
för namnsättning av Tjänstekontraktsbeskrivning och Arkitekturella beslut.

### 2014-02-11 Ytterligare utbildningstillfälle i T-bok och RIV TA ###
Den 5:e mars genomförs den tredje omgången utbildning kring T-boken, RIV TA och Tjänsteplattformen. Mer information hittar du
[här](http://www.inera.se/TJANSTER--PROJEKT/Tjansteplattform/Utbildningsdag-i-T-boken/).

### 2014-01-31 Byggscripten för java och .Net har uppdaterats ###
Se https://code.google.com/p/rivta/wiki/ToolsGenerateBuildScript för mera information.

### 2014-01-28 Information om adressering ###
En ny FAQ som berör adressering i praktiken har lagts upp i wikin.
Den beskriver grunderna i adressering och behörighet och innehåller
exempel på hur adressering med seriekopplade plattformar hanteras.

Informationen återfinns [här](FaqAdressering.md).

### 2014-01-21 Script för att verifiera innehåll i tjänstedomän ###
Ett script för att verifiera att alla obligatoriska komponenter i en tjänstedomän existerar.
Mer information återfinns [här](ToolsDomainManagement.md).

### 2014-01-15 ZIP-filerna har flyttat ###
Google har beslutat att nerladdningsarean för projekt på Google code skall avvecklas.
Från och med idag kan man därför inte längre ladda upp paketerade Tjänstedomäner (zip-filer) i
Rivta-projektet.

Av den anledningen har vi flyttat nedladdningsarean till http://rivta.se/downloads.
Projekten kan inte själva ladda upp,
istället gör vi inom CeHis AR det i samband med att vi granskat och godkänt en release (eller releasekandidat).
[Tabellen](ServiceDomainTable.md) över godkända tjänstedomäner har uppdaterats så att alla länkar
pekar mot den nya arean.

### 2013-11-28 Utbildningsmaterial publicerat ###
Utbildningstillfälle nummer två om teknisk arkitektur, anvisningar och Tjänsteplattformen genomfördes idag med 24 deltagare. Materialet finns tillgängligt [här](http://rivta.se/documents/index.html#presentationer).

### 2013-11-07 Utbildning ###
Det första tillfället av utbildningsdagen i T-boken, RIV TA, Konfigurationsstyrning och Tjänsteplattformen genomfördes med 27 deltagare.

### 2013-10-29 Ny version av anvisning för Konfigurationsstyrning ###
Handledningen för hur man utvecklar och hanterar tjänstekontrakt har uppdaterats.
En av de viktigaste ändringarna rör namnsättning samt hur kontrakt och domäner
skall versioneras. Se http://rivta.se/documents/ARK_0007.

### 2013-10-01 Arkitekturella dokument samlade på ny webbplats ###
T-boken, RIV Tekniska anvisningar och övrig arkitekturell dokumentation har nu samlats på en ny webbplats, http://rivta.se/documents/ .
Genom att namnge dokumenten med sk "ARK-nummer" så säkrar vi också att dokumentlänkar skall fungera över tiden.

### 2013-10-01 Nyhetssida upplagd ###
Från och med idag kommer vi att lägga ut nyheter kring arkitektur, regelverk och anvisningar på denna sida.