

# Verifiering av tjänstedomäner #

Verktyg för att verifiera och hantera innehållet i en tjänstedomän. Dessa script bygger främst på regelverket i
[RIV-TA Konfigurationsstyrning](http://rivta.se/documents/ARK_0007).

## Git bash ##

Git bash tillhandahåller en Unixliknande miljö i Windows och rekommenderas för alla som är bekväma i bash-miljön. I många stycken överträffar den Cygwin.

## Installera Python ##

De script som har extension ".py" är implementerade i programmeringsspråket Python, och kräver att Python version 2.7.X är installerad på datorn (OBS, fungerar INTE med Python version 3).

I Linux och MacOs är Python normalt förinstallerat. Det kan laddas ner från http://www.python.org/download/.

Mer information om att använda Python under Windows återfinns här: http://docs.python.org/2/using/windows.html.



### Köra Pythonscript ###

För Unixbaserade system (inkl MacOS) bör det vara tillräckligt att göra scriptet _executable_, lägga det i pathen och sedan köra det direkt. Ex:
<pre>
> chmod 755 verifyDomainFolder.py<br>
> ./verifyDomainFolder.py<br>
</pre>

I Windows får man ge det som en parameter till _python_-kommandet.

<pre>
C:\User\kurre> python verifyDomainFolder.py<br>
</pre>


## verifyDomainFolder.py 1.2 ##

Detta script utgår från en _trunk/_ eller specifik _tags/tag-namn/_ mapp och går igenom mappstrukturen för en Tjänstedomän och verifierar att alla obligatoriska objekt existerar på rätt plats med rätt namn (enligt
[RIV-TA Konfigurationsstyrning](http://rivta.se/documents/ARK_0007)). Scriptet varnar även när det upptäcks innehåll som ej är obligatoriskt. Detta kan, men behöver inte,
representera ett fel.

Om scriptet verifierar en struktur under en _tag_ så kommer även namnet på taggen att verifieras gentemot namnstättningsreglerna i Konfigurationsstyrningen.
Det sker då en mappning mot överliggande mapp-namn (upp till _riv_ i strukturen som alltså måste vara utcheckat).
Man kan även välja att be scriptet skapa en zip-fil. Det förutsätter att det finns ett _zip-kommando_ tillgängligt i PATH-en på den dator som används.


Scriptet lämnar en returkod som motsvarar antalet hittade fel. RC=0 innebär alltså att allt är ok, och kan nyttjas om det används automatiserat.

Anges flaggan "-h" skrivs en användarinstruktion ut.

Scriptet ligger i SVN och kan laddas ner från:
http://code.google.com/p/rivta/source/browse/tools/domainmanagement/verifyDomainFolder/tags/release-1.2



## diff ##

**diff** är ett kommando som finns tillgängligt i Unixbaserade OS, inklusive MacOS (och Git bash). Det används för att jämföra innehållet i textfiler, men kan också rekursivt jämföra alla filer i två mappstrukturer. Det finns många kommandoflaggor, men **diff --recursive --brief mapp1 mapp2** visar skillnaden mellan t ex två TAGS (versioner) av en tjänstedomän.

Exempel:

```
leo@mintcin ~/rivta-read-only/ServiceInteractions/riv/clinicalprocess/healthcond/actoutcome/tags $ diff --recursive --brief clinicalprocess_healthcond_actoutcome_2.0_RC12  clinicalprocess_healthcond_actoutcome_2.0_RC11 
Files clinicalprocess_healthcond_actoutcome_2.0_RC12/docs/Arkitekturella beslut.docx and clinicalprocess_healthcond_actoutcome_2.0_RC11/docs/Arkitekturella beslut.docx differ
Only in clinicalprocess_healthcond_actoutcome_2.0_RC11/docs: MIM_GetMaternityMedicalHistory.pdf
Files clinicalprocess_healthcond_actoutcome_2.0_RC12/docs/Tjanstekontraktsbeskrivning clinicalprocess_healthcond_actoutcome.docx and clinicalprocess_healthcond_actoutcome_2.0_RC11/docs/Tjanstekontraktsbeskrivning clinicalprocess_healthcond_actoutcome.docx differ

```