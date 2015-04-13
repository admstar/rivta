# Lista över vanligt förekommande kodverk och identifierare #

Denna sida syftar till att sammanställa information om kodverk och identifierare som förekommer i tjänstekontrakten. Liknande sammanställningar finns i gamla RIV-specar (exempelvis NPÖ:s), men dels är informationen statisk och riskerar att vara obsolet, och dels är den spridd över flera dokument. Målet är att denna sida ska kunna hållas uppdaterad.


## Kodverk ##

| **Namn** | **OID** | **Ägare/förvaltare och länk** | **Innehåll** |
|:---------|:--------|:---------------------------------|:--------------|
| ATC | 1.2.752.129.2.2.3.1.1 (OID från Arkitekturledningen, SKL) | Underhålls av [WHO Collaborating Centre for Drug Statistics Methodology, Oslo, Norge](http://www.whocc.no/atcddd/) <br> I Sverige oklart vilken instans som ansvarar men <a href='http://www.lakemedelsverket.se'>Läkemedelsverket</a> har övergripande ansvar för läkemedelsfrågor <table><thead><th> Klassificeringssystem för läkemedel. Läkemedlen indelas i olika grupper efter indikationsområde. </th></thead><tbody>
<tr><td> HL7 Healthcare Service Location </td><td> 2.16.840.1.113883.6.259 </td><td> CDC <a href='http://phinvads.cdc.gov/vads/ViewCodeSystem.action?id=2.16.840.1.113883.6.259'>(Länk till KV)</a> </td><td> HSLOC innehåller beskrivande koder för olika funktionella delar av vårdapparaten. </td></tr>
<tr><td> HL7 RoleClass </td><td> 2.16.840.1.113883.5.110 </td><td> HL7 <a href='http://www.hl7.org/implement/standards/fhir/v3/RoleClass/index.html'>http://www.hl7.org/implement/standards/fhir/v3/RoleClass/index.html</a> </td><td> Kodsystemet innehåller beskrivningar av roller, d.v.s. beskrivningar av associationer mellan två entiteter.  </td></tr>
<tr><td> HSA Verksamhetskod</td><td> 1.2.752.129.2.2.1.3 </td><td> Förvaltas av HSA förvaltningsgrupp, Inera. <a href='http://www.inera.se/Documents/TJANSTER_PROJEKT/Katalogtjanst_HSA/Innehall/HSA_innehall_verksamhetskod.pdf'>(Länk till KV)</a> </td><td> Klassifikation av verksamhet. </td></tr>
<tr><td> ICD-10-SE (version 2011) </td><td> 1.2.752.116.1.1.1.1.3 </td><td> Socialstyrelsen. </td><td> Internationell statistisk klassifikation av sjukdomar och relaterade hälsoproblem, systematisk förteckning. </td></tr>
<tr><td> ICF </td><td> 1.2.752.116.1.1.3.3.1 </td><td> Internationell nivå: WHO men licensierat till Sverige.<br>Nationell nivå: Socialstyrelsen. </td><td> Klassifikation av funktionstillstånd, funktionshinder och hälsa Baseras på en internationell klassifikation med namnet International Classification of Functioning, Disability and Health. </td></tr>
<tr><td> KKO </td><td> 1.2.752.116.1.3.4.1.1 </td><td> Socialstyrelsen. </td><td> Klassifikation av kontaktorsaker. </td></tr>
<tr><td> KV Befattning </td><td> 1.2.752.129.2.2.1.4 </td><td> Förvaltas av HSA förvaltningsgrupp, Inera. <a href='http://www.inera.se/Documents/TJANSTER_PROJEKT/Katalogtjanst_HSA/Innehall/hsa_innehall_befattning.pdf'>(Länk till KV)</a> </td><td> Information befattning för en hälso- och sjukvårdsperson. </td></tr>
<tr><td> KV Kön</td><td> 1.2.752.129.2.2.1.1 </td><td> Socialstyrelsen </td><td> Anger kön på en person. </td></tr>
<tr><td> KVÅ</td><td> 1.2.752.116.1.3.2.1.4 </td><td> Socialstyrelsen </td><td> Klassifikation av åtgärder. Anger aktivitetskod/åtgärdskod. </td></tr>
<tr><td> NKOO</td><td> 1.2.752.116.2.3.1.1 </td><td> Socialstyrelsen </td><td> Kodsystem för klassificering av ordinationsorsaker. <a href='http://www.cehis.se/images/uploads/dokumentarkiv/Implementationsguide_ordinationsorsak_20130620.pdf'>(Länk till implementationsguide)</a> </td></tr>
<tr><td> NPL </td><td> 1.2.752.129.2.1.5.1</td><td> Läkemedelsverket </td><td> NPL-id beskriver ett visst läkemedel från en viss tillverkare med en viss beredningsform, styrka och smak.  </td></tr>
<tr><td> NPL-pack </td><td> 1.2.752.129.2.1.5.2</td><td> Läkemedelsverket </td><td> NPL-pack-id beskriver ett visst läkemedel enligt NPL-beskrivning ovan, i en viss förpackningsstorlek.  </td></tr>
<tr><td> NPU </td><td> 1.2.752.108.1 </td><td> Förvaltningen av den svenska versionen av NPU-systemet, som tidigare sköttes av Equalis, upphörde 1 januari 2014. Socialstyrelsen har i juni 2014 <a href='http://www.socialstyrelsen.se/publikationer2014/2014-6-23'>rekommenderat</a> ett övertagande av förvaltningen. </td><td> NPU-systemet (Nomenclature of properties and units) är ett register med systematiska, unika benämningar och koder för laboratorieundersökningar. </td></tr>
<tr><td> SNOMED CT </td><td> 2.16.840.1.113883.6.96 </td><td> Förvaltningen av den svenska versionen av SNOMED CT sköts av Socialstyrelsen. </td><td> SNOMED CT innehåller en stor mängd kliniska termer. </td></tr>
<tr><td> SNOMED CT SE </td><td> 1.2.752.116.2.1.1</td><td> Förvaltningen av den svenska versionen av SNOMED CT sköts av Socialstyrelsen. </td><td> SNOMED CT innehåller en stor mängd kliniska termer. </td></tr></tbody></table>


<h2>Identifierare</h2>

<table><thead><th> <b>Namn</b> </th><th> <b>OID</b> </th><th> <b>Ägare/förvaltare och länk</b> </th><th> <b>Innehåll</b> </th></thead><tbody>
<tr><td> GLN </td><td> 1.3.88 </td><td> GS1 </td><td> GLN (Global Location Number) används för att identifiera en (fysisk eller juridisk) plats som behöver bli unikt identifierad. </td></tr>
<tr><td> HSA-id </td><td> 1.2.752.129.2.1.4.1 </td><td> Inera, förvaltningsgrupp HSA </td><td> Id för objekt i HSA-katalogen. </td></tr>
<tr><td> Nationell OID för lokala id:n </td><td> 1.2.752.129.2.1.2.1</td><td> SKL </td><td> "Slask-OID" som används för att peka på lokala id:n, som inte sällan bildas med en konkatenering av system-id och systemets lokala id av instansen som ska identifieras. </td></tr>
<tr><td> Personnummer </td><td> 1.2.752.129.2.1.3.1 </td><td> <a href='http://www.skatteverket.se'>Skatteverket</a> </td><td> Person-id för någon som är folkbokförd i Sverige enligt SKV704. </td></tr>
<tr><td> Samordningsnummer </td><td> 1.2.752.129.2.1.3.3 </td><td> <a href='http://www.skatteverket.se'>Skatteverket</a> </td><td> Person-id för någon som inte är eller har varit folkbokförd i Sverige enligt SKV704. Samordningsnummer tilldelas av Skatteverket på begäran av en myndighet. </td></tr>
<tr><td> SLL-reservnummer </td><td> 1.2.752.97.3.1.3 </td><td> SLL HSF </td><td> Reservnummer tillhörande SLL-person/patient. </td></tr>