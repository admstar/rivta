

# Introduktion verktyg #

Verktyg för att generera byggskript. Skripten används för att generera .net eller java versioner av tjänstekontrakten.

## Ladda ner skripten ##

Skript kommer man åt via antingen:

  * [Hämta ut subversion repot](http://code.google.com/p/rivta/source/checkout)  där scriptet ligger under katalogen tools
  * eller ladda ner skriptet [direkt](http://code.google.com/p/rivta/source/browse/tools/generators/build-script-generators/tags/release-1.1/)

## Byggskript för Java (JAX-WS) ##

Ett skript som genererar en pom.xml. För att skriptet skall fungera krävs att:

  * Java är installerat
  * JAVA\_HOME är satt
  * Groovy är installerat, minst version 1.8.1
  * GROOVY\_HOME är satt och GROOVY\_HOME/bin finns på path

Kör skriptet `JaxWsMavenPomGenerator.groovy` för att få mer info om parametrar för skriptet.

Detta script uppdaterades till version 1.1 2014-01-31. Ändringen innebar att den resulterande POM-filen
läggs under mappen code\_gen så som [Konfigurationsstyrningen](http://rivta.se/documents/ARK_0007) stipulerar. Dessutom rättades pathen i POM-file som
pekar ut schemas/.


## Byggskript för .net (WCF) ##

Ett skript som genererar en bat-fil. För att skriptet skall fungera krävs att:

  * .NET framework

Kör skriptet `WcfSvcutilBatfileGenerator.groovy` för att få mer info om parametrar för skriptet.

Detta script uppdaterades till version 1.1 2014-01-31. Ändringen innebar att den resulterande bat-filen
läggs under mappen code\_gen så som [Konfigurationsstyrningen](http://rivta.se/documents/ARK_0007) stipulerar.