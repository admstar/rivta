

# Översikt #

## Viktiga termer ##

### Tjänstekonsument ###
Informationssystem där aktörens agerande leder till automatiskt informationsutbyte med andra system (tjänsteproducenter).
Tjänstekonsumenten är alltså ett IT-system som initierar ett specifikt informationsutbyte.



Det motsvarar begreppet _Initiativtagare_ i RIV Tekniska anvisningar.

### Tjänsteproducent ###
Tjänsteproducenter uppvisar ett tekniskt gränssnitt som möjliggör för tjänstekonsumenter att genom frågemeddelanden förändra eller begära information.
Det tekniska gränssnittet följer fastställda standarder för säker meddelandebaserad kommunikation (teknisk interoperabilitet).
De hanterar frågemeddelanden och producerar svarsmeddelanden enligt för funktionen fastställt nationellt tjänstekontrakt (semantisk interoperabilitet).



Det motsvarar begreppet _Utförare_ i RIV Tekniska anvisningar.

### Tjänstekontrakt ###
Systemoberoende tekniska kontrakt som reglerar samspelet mellan olika komponenter i systemlandskapet, med utgångspunkt i
process- och informationsmodeller.
Dessa är upprättade enligt anvisningar för teknisk interoperabilitet i den tekniska arkitekturen vilket innebär
att tjänstekontraktet säkerställer teknisk interoperabilitet genom att följa anvisning vid upprättandet.
Ett enskilt tjänstekontrakt består av ett innehåll (nyttolast), kuvert och regler vilket är grunden för semantisk interoperabilitet.
Semantisk interoperabilitet i sin tur förutsätter en gemensam referensmodell för informationsstruktur.

När ett tjänstekontrakt installeras i en tjänsteplattform skapas en virtuell tjänst.

### Virtuell tjänst ###
Integrationstjänst i en tjänsteplattform som är baserad på ett tjänstekontrakt. Den är en nationell ställföreträdare för alla lokala
tjänsteproducenter som uppfyller ett tjänstekontraktet. Den uppträder som om det
fanns en nationell tjänsteproducent, men dirigerar vidare frågemeddelanden till
respektive informationsägares tjänsteproducent och förmedlar
svarsmeddelandet i retur.

I samband med vidareförmedling verifieras att tjänstekonsumenten är behörig
att integrera med adresserad informationsägare.

Exempel:

  * Den virtuella tjänsten ”Hämta individens tidbokningar” förmedlar frågemeddelanden vidare till respektive vårdenhets tidbokningssystem (tjänsteproducent).
  * Den virtuella tjänsten ”Hämta individens listningar” (kundval/valfrihetssystem/vårdval) förmedlar frågemeddelanden vidare till respektive läns eller kommuns listningssystem (tjänsteproducent).

### Tjänstedomän ###
För att kunna tolkas, administreras och versionshanteras behöver de tjänstekontrakt som stödjer en viss process
eller informationsbehov grupperas. En sådan gruppering benämns Tjänstedomän.


## Tjänsteplattformen (TP) ##

### Vad är en TP (enligt T-boken)? ###
Tjänsteplattform är ett samlingsnamn för de komponenter som utgör plattform för tjänstebaserad integration över huvudmannagränser.
Varje samverkansdomän i en federation kan ha en egen instans eller ingå i den överordnade domänens plattform.
Den nationella samverkansdomänen har en instans som publicerar virtuella tjänster och aggregerande tjänster för
alla nationella tjänstekontrakt.
All samverkan mellan samverkansdomäner och inom den nationella domänen sker via tjänster i den nationella tjänsteplattformen.

### Vad är en virtualiseringsplattform (enligt T-boken)? ###
Virtualiseringsplattformen är en central komponent i TP.
Den erbjuder infrastruktur för virtuella tjänster.
Dess primära syfte är systematisk realisering av virtuella tjänster.
Som en effekt kan generella aspekter av virtualisering förändras i plattformen i stället för att alla
enskilda tjänster behöver förändras. Det ger gemensam hantering av gemensamma behov, så som felhantering,
åtkomstkontroll, övervakning, vägval. loggning, uppdatering av engagemangsindex, SLA-uppföljning mot tjänsteproducenter m.m.

### Vad menas med vägval i TP? ###
En anropande tjänstekonsument behöver inte hålla reda på den tekniska adressen (IP-adress, FQHN) till alla de
producenter som den anropar. Istället skickar konsumenten med en sk logisk adress till TP. TP översätter den
logiska adressen och skickar meddelandet vidare till rätt mottagare.
På detta sätt uppnås sk "lös koppling" mellan de system som är integrerade via TP.
Ett adressbyte kan genomföras en gång i TP, inte i potentiellt hundratals konsumentsystem.

### Vad menas med åtkomstkontroll i TP? ###
En tjänstekonsument har inte automatiskt rätt att anropa producentsystem via TP.
Informationsägaren/systemägaren måste explicit ge tillstånd för varje anropande system.
Dessa godkännanden lagras i TP och är en förutsättning för att ett anrop skall skickas vidare.

# Adresseringsmekanismer #

## Hur adresseras system i ett IP-nätverk? ##
Adressering sker i normalfallet baserat på IP-adress. Som exempel, IP-adressen till Ineras hemsida är 79.136.112.108.
I normallet används dock host- och domännamn som representerar de tekniska adresserna.
Tillsammans utgår det ett sk "fully-qualified host name" (FQHN). I Ineras fall är det namnet "www.inera.se".

## Vad menar vi med en "logisk adress"? ##
En viktig effekt av konceptet med tjänsteplattformen är att frikoppla konsumenter och producenter så långt det är möjligt.
Vi vill inte tvinga alla konsumenter att direkt administrera den tekniska adressen till samtliga producentsystem som
den vill koppla sig till, och i praktiken känner konsumenten över huvud taget inte till producentsystemet.
I stället används virtuella tjänster och logiska adresser som representerar en verksamhet (verksamhetsadressering) eller ett system (systemadressering).
I båda dessa fall används HSA-id som logisk adress (med något undantag).

## Vad menar vi med "verksamhetsadressering"? ##
Tjänstekontrakt som är verksamhetsadrsserade använder ett HSA-id som representerar en organisatorisk enhet som logisk adress.

## Vad menar vi med "systemadressering"? ##
Vid systemadressering används ett HSA-id som representerar ett källsystem som logisk adress.
Ett källsystem kan t ex vara ett journalsystem. I normalfallet är källsystemet inte samma sak som tjänsteproducenten.

## Var hanteras mappningen mellan logiska adresser och "verkliga"? ##
Tjänsteplattformen måste mappa en logisk adress till den teknisak adressen för ett producentsystem,
och skicka ett anrop vidare till producenten. Det är alltså inte producentsystemet som adresseras,
istället en bakomliggande verksamhet eller ett bakomliggande källsystem.
Producentsystemet kan sägas representera verksamheten eller källsystemet.
Mappningen mellan logisk adress och adress till tjänsteproducent lagras i Tjänsteadresseringskatalogen (TAK) i Tjänsteplattformen.

# Behörighetshantering #

## Får en tjänstekonsument anropa alla producenter? ##
Nej, i normalfallet måste verksamheten eller systemägare för källsystemet explicit godkänna varje tjänstekonsument innan anrop tillåts.

## Var finns behörighetsinformationen? ##
Denna information lagras i Tjänsteadresseringskatalaogen i Tjänsteplattformen.

# Tjänsteadresseringskatalogen (TAK) #

## Vad är TAK? ##
Tjänsteadresseringskatalogen är en databas med nio tabeller som innehåller den grundinformation som
Tjänsteplattformen behöver för att hantera virtuella tjänster (tjänstekontrakt),
anslutningar av konsumenter och producenter, logiska adresser samt behörigheter.

## Vilken information relaterat till tjänster och tjänstekontrakt lagras i TAK? ##
Namnet på tjänstekontraktet (hela namnrymden), version samt vilken version av RIVTA det bygger på.

## Vilken information relaterat till konsumenter lagras i TAK? ##
HSA-id (från SITHS-funktionscertifikat) som konsumenten har. Dessutom IP-adressen till konsumenten.

## Vilken information relaterat till producenter lagras i TAK? ##
HSA-id (från SITHS-funktionscertifikat) som producenten har. Dessutom IP-adressen till producenten.

## Vilken information relaterat till logiska adresser lagras i TAK? ##
Logiska adresser lagras i en tabell tillsammans med uppgifter om adressens gilitighetsperiod.
De logiska adresserna kopplas samman med tjänstekontrakt och producentsystem på ett sådan sätt att
Tjänsteplattformen utifrån ett anrop mot ett specifikt tjänstekontrakt och logisk adress kan se vilken
producent som skall adresseras.

## Vilken information relaterat till behörighet lagras i TAK? ##
Anropsbehörigheter lagras i en tabell tillsammans med uppgifter om  gilitighetsperiod.
En uppgift om behörighet kopplas samman med logisk adress, tjänstekontrakt och konsumentsystem.
Tjänsteplattformen kan utifrån denna information avgöra huruvida en viss tjänstekonsument får anropa en
specifik tjänst (tjänstekontrakt) på en specifik logisk adress.

## Hur administreras informationen i TAK? ##
Det är naturligtvis av yttersta vikt att information i Tjänsteadresseringskatalogen är korrekt,
samt att det finns en spårbarhet på vem som tillhandahållit vilken information.
Inom förvaltningen för den gemensamma Tjänsteplattformen använder man för närvarande ett system med
fyra olika Word-blanketter (A, B, C och D) för att inhämta informationen.
Dessa ligger till grund för uppdateringar av databasen, och arkiveras.
Det finns planer på att ta fram ett administrativt gränssnitt för att delegera ut administrationen av TAK närmare
verksamheterna.

## Vad används de fyra blanketterna till? ##
  * A - Information om tjänster (tjänstekontrakt), version osv.
  * B - Information om konsumentsystem
  * C - Information om producentsystem och logiska adresser
  * D - Information om anropsbehörighet

# Adressering i praktiken #

## Kan TPs seriekopplas? ##
Den nationella arkitekturen som den är beskriven i T-boken och detaljerad i RIV TA utgår ifrån att flera
tjänsteplattformar kan seriekopplas. Typfallet är att en region/landsting har en regional instans (en sk RTjP) som används för regional trafik, men också utgör gateway för alla integrationer till tjänster utanför regionen. Praktiskt kan man i normalfallet tänka sig en kedja på upp till tre plattformar, RTjP-NTjP-RTjP, för kommunikation ut ur en region,
via den gemensamma plattformen (NTjP) och in till en tjänst i en annan region. Teoretisk skall dock kedjan kunna vara längre.

## Vilken information finns i en regional TAK? ##
När plattformar kopplas i serie måste de samspela vad gäller vägval (adressering) och behörighetskontroll.
Det innebär att det kommer att vara ett visst överlapp av information mellan regionala och den gemensamma TAKen.
Dock måste även adresser till angränsande plattformar finnas med.

## Hur kopplas praktisk tre plattformar in i serie? ##
Låt oss anta att en tjänstekonsument kopplas samman med en tjänsteproducent (som representerar en verksamhet) via tre plattformar;
K1 - RTjP1 - NTjP - RTjP2 - P1 - V1.

![http://rivta.googlecode.com/svn/wiki/images/IllustrationerTAK.png](http://rivta.googlecode.com/svn/wiki/images/IllustrationerTAK.png)

Ett exempel på en sådan integration skulle kunna gälla NPÖ2 som tjänstekonsument och tjänsten GetCareDocumentation.
En tjänstekonsument på VGR, K1, är ansluten till VGRs regionala plattform,
RTjP1.
När en journal skall hämtas från Hjärtkliniken på Karolinska sjukhuset inom SLL kommer anropet att routas via den gemensamma plattformen (NTjP),
och sedan vidare till SLLs regionala plattform (RTjP2).
Därefter går det till den producent, Take Care (P1), som representerar Hjärtkliniken. Den slutgiltiga adressaten, logiska adressen, är alltså Hjärtkliniken (V1).

| **Beteckning** | **Exempel** |
|:---------------|:------------|
| K1 | Regional applikation inom VGR |
| RTjP1 | Regional tjänsteplattform inom VGR |
| NTjP | Gemensam tjänsteplattform |
| RTjP2 | Regional tjänsteplattform inom SLL |
| P1 | Take care - journalsystem inom SLL |
| V1 | Hjärtkliniken på Karolinska sjukhuset, SLL |

#### RTjP1-TAK ####
För att detta exempel skall fungera så måste RTjP1-TAK (dvs den TAK som används av RTjP1) innehålla förljande information:



| **Fält** | **Data** |
|:----------|:---------|
| Tjänst | GetCareDocumentation |
| Tjänstekonsument | K1 |
| Tjänsteproducent | NTjP |
| Logisk adress | V1 |
| Adresseringsinformation | att anrop adresserade till adressen (verksamheten) V1, för tjänsten GetCareDocumentation, skall skickas till tjänsteproducenten NTjP |
| Behörighetinformation | att tjänstekonsumenten K1 har behörighet att adressera verksamheten V1 för tjänsten GetCareDocumentation |


Det är i den första tjänsteplattformen i kedjan som det sker en behörighetskontroll av att den ursprungliga tjänstekonsumenten har behörighet att anropa verksamheten i fråga. RTjP1 har ingen information om RTjP2 och P1.

#### NTjP-TAK ####

NTjP-TAK kommer att ha information om:




| **Fält** | **Data** |
|:----------|:---------|
| Tjänst | GetCareDocumentation |
| Tjänstekonsument | RTjP1 |
| Tjänsteproducent | RTjP2 |
| Logisk adress | V1 |
| Adresseringsinformation | att anrop adresserade till adressen (verksamheten) V1, för tjänsten GetCareDocumentation, skall skickas till tjänsteproducenten RTjP2 |
| Behörighetinformation | att tjänstekonsumenten RTjP1 har behörighet att adressera verksamheten V1 för tjänsten GetCareDocumentation |








Behörighetskontrollen begränsas här till att verifiera att den regionala tjänsteplattformen RTjP1 har rättighet att andressera V1 för GetCareDocumentation.
NTjP känner inte till K1 eller P1.

#### RTjP2-TAK ####

RTjP2-TAK innehåller:

| **Fält** | **Data** |
|:----------|:---------|
| Tjänst | GetCareDocumentation |
| Tjänstekonsument | NTjP |
| Tjänsteproducent | P1 |
| Logisk adress | V1 |
| Adresseringsinformation | att anrop adresserade till adressen (verksamheten) V1, för tjänsten GetCareDocumentation, skall skickas till tjänsteproducenten P1 |
| Behörighetinformation | att tjänstekonsumenten NTjP har behörighet att adressera verksamheten V1 för tjänsten GetCareDocumentation |

Behörighetskontrollen begränsas här till att verifiera att den gemensamma tjänsteplattformen NTjP har rättighet att andressera V1 för GetCareDocumentation.
RTjP2 känner inte till K1 eller RTjP1.