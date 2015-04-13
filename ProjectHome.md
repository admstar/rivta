# Välkommen till webbplatsen för utveckling av tjänstekontrakt #


Tjänstekontrakt regleras av RIV Tekniska anvisningar.
Syftet med denna anvisning är att beskriva hur man realiserar utbytet av information mellan två parter. RIV Tekniska Anvisningar är en integrerad del i den nationella arkitekturen som den är beskriven i [T-boken](http://rivta.se/documents/ARK_0019). Det är samtidigt en specifikation utan hårda kopplingar till vårdens specifika infrastruktur. RIV Tekniska Anvisningar är också utan koppling till specifika meddelandeformat så som EN-13606 och HL7.

T-boken samt de anvisningar och mallar som ingår i RIV TA 2.1 återfinns på [här](http://rivta.se/documents/).

Förtydliganden finns publicerade i [Frågor och svar](FAQ.md).

Verktyg:

  * [Generator för tjänstekontrakt](http://rivtatools-prod.appspot.com/)
  * [Verktyg för domänhantering](https://code.google.com/p/rivta/wiki/ToolsDomainManagement)
  * [Generera byggscript](https://code.google.com/p/rivta/wiki/ToolsGenerateBuildScript)

# Nyheter #
### 2015-01-13 2 nya RIVTA anvisningar upplagda ###

ARK\_0034 anvisning Kryptografi och ARK\_0036 Tjänsteplattform är upplagda på RIVTA siten.

### 2014-12-29 Omlagt hur länkar sker till downloads ###

Efter policy ändring på Google code så finns zip filer numera att nå via RIVTA.SE.  Länk http://rivta.se

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

### 2014-04-08 Ny revision av RIVTA Översikt publicerad ###
En ny revision med fokus på källsystemsadressering och ändrade krav på hantering av anropsbehörighet har publicerats. Dokumentet återfinns på http://rivta.se/documents/ARK_0001 .

### 2014-04-07 Ny tabell med godkända domäner ###
Nu har vi byggt om tabellen över godkända domäner. Allt för att få den tydligare och förhoppningsvis enklare. Den finns på denna länk:
https://code.google.com/p/rivta/wiki/ServiceDomainTable


[Äldre nyheter](NewsPage.md).

# Handledning #

RIV-TA [Konfigurationsstyrning](http://rivta.se/documents/ARK_0007) beskriver hur man praktiskt arbetar med utveckling på denna webbplats.

# Tjänstedomäner #

Tabellen över godkända tjänstedomäner återfinns [här](ServiceDomainTable.md).


# Regelverkets förvaltning #

Regelverket förvaltas av [Inera Arkitektur och regelverk](http://rivta.se). Synpunkter och ändringsförslag mailas till _arkitektur-at-inera.se_.