

# Downloads #

## Ny plats för nerladdning av zip filer ##
**Fråga:**
Var finns zip filer som jag behöver ladda ner?

**Svar:**
Google code har infört ny policy om innehåll i uppladdade filer. Detta gör att RIVTA numera lagrar sina zip filer på siten RIVTA.SE http://rivta.se.

# Test #

## Testflagga i tjänstekontrakt ##
**Fråga:**
Vi har fått in ett ändringsförslag på att vi ska lägga till en testflagga i meddelandet för att man efter exempelvis journalsystemsförändringar skall kunna skicka testremisser i produktion. Finns det någon helhetssyn på detta från arkitekturledningen? Jag kan tycka förslaget griper över alla nationella kontrakt som driver någon slags process (t.ex intyg, tidbok, listningstjänst).

**Svar:**
CeHis _Arkitektur och regelverk_ har diskuterat frågan om testflagga i tjänstekontrakten. Det går att se nyttan av en sådan möjlighet. Den generella principen är dock att inte ska blanda in testadata i produktionsmiljöerna. Det finns alltid en risk att ett visst system inte korrekt implementerat kontrollen av flaggan. I flöden, där flera system anropas i serie, ökar risken att det kan begås misstag. Med tanke på de mycket höga krav på "korrekthet" som finns inom många områden inom vården anser vi att det inte skall öppnas upp för testdata man på det här sättet. Så, det generella svaret är nej. Om en specifik domän skulle vilja driva frågan vidare så görs det lämpligen via ett Arkitekturellt beslut (AB) med en tydlig konsekvensutredning.

# Projektplatsen Google code #

## Problem att visa/ladda ner PDF dokument ##
**Fråga:**
Hur gör jag när jag försöker ladda ner en fil och får meddelandet: "This file is not plain text (only UTF-8 and Latin-1 text encodings are currently supported)."?

**Svar:**
Välj alternativet "View raw file"

## Använda befintlig mailadress som konto i Google code ##
**Fråga:**
Kan jag använda mitt befintliga, ej Google-baserade, mailkonto när jag jobbar i Google code?

**Svar:**
Ja. Gå till google.se och välj att logga på. Klicka sedan på "Skapa ett konto", och därefter ”Jag föredrar att använda min nuvarande e-postadress”. Fyll i uppgifterna. Sedan fungerar det.

# Plattform enligt RIV-TA #

## Information om ursprungligt konsumentsystem ##
**Fråga:**
Vad heter den http-header där Tjänsteplattformen, eller en lokal plattform, placerar HSA-id för ursprungligt anropande konsumentsystem?

**Svar:**
x-rivta-original-serviceconsumer-hsaid. Detta ingår numera i [RIV Tekniska Anvisningar - Valfria tillägg](http://rivta.se/documents/ARK_0028).

Följande länk innehåller information kring hur man accessar http request-header-information; http://stackoverflow.com/questions/18877591/how-to-read-http-request-headers-in-a-wcf-web-service

# Namnsättning och versionshantering #

Vänligen konsultera [Konfigurationsstyrningen](http://rivta.se/documents/ARK_0007)
för frågor om namnsättningsregler. Svaren nedan tjänar endast som exempel  och förtydliganden.

## Taggning av release i subversion ##
**Fråga:**
Hur skall en release av en tjänstedomän namnsättas?

**Svar:**
Grundregeln för produktionssatta domäner är:
Tjänstedomännamn\_Version, där Tjänstedomännamnet är det samma som namnrymden för domänen avdelad med underscore.

Ex:
clinicalprocess\_activityprescription\_actoutcome\_2.1

Innan domänen nått produktionsstatus adderas "RCn"

Ex:
clinicalprocess\_activityprescription\_actoutcome\_2.1\_RC3

Ovanstående namn används även som TAG i subversion.


## Namnsättning av zipfil ##
**Fråga:**
Hur namnges zip-filen för en release av en domän?

**Svar:**
När en zip-fil skapas utifrån TAG-en i subversion skall prefixet "ServiceContracts_" adderas namnet._

Ex.
ServiceContracts\_clinicalprocess\_activityprescription\_actoutcome\_2.1\_RC3.zip

## Version av tjänstedomän och zip-fil ##
**Fråga:**
Vi har uppgraderat ett kontrakt i domänen till 1.1. Hur löser vi det med versionsnummer på wsdlerna och på zipfilen? Måste zipfilen bli 1.1 eller kan den vara kvar som 1.0? Måste vi göra nya tjänstekontrakt med version 1.1 på alla tjänster trots att dom kommer vara identiska med 1.0? Får en zipfil som heter 1.1 innehålla kontrakt som heter 1.0?

**Svar:**
Zip-filen skall alltid ges samma version som den taggning den motsvarar i subversion.
Grundprinicipen är att versionen skall motsvara den högsta versionen på de ingående kontrakten.
Vid varje förändring av innehållet som inte påverkar versionering läggs en tredje siffra på som räknas upp
Ex

Läge 1:
• Kontrakt A 1.0
• Kontrakt B 1.0
• Tag och zip blir 1.0

Läge 2:
• Kontrakt A 1.0
• Kontrakt B 1.1
• Tag och zip blir 1.1

Läge 3:
• Kontrakt A 1.1
• Kontrakt B 1.1
• Tag och zip blir 1.1.1

Läge 4: Enbart dokumentationsuppdatering
• Kontrakt A 1.1
• Kontrakt B 1.1
• Tag och zip blir 1.1.2

RIV TA kommer att förtydligas med den här anvisningen.

Så, applicerat på dina frågor:

ZIP-filen skall döpas med versionen på det högsta kontraktsversionen
Tjänster som inte ändrats skall inte förändras, dvs skall fortsatt vara 1.0
Zip 1.1 kan alltså innehålla kontrakt på 1.0 (dock inte motsatsen, en zip 1.0 kan inte innehålla kontrakt 1.1)