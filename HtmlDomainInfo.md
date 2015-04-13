

# Generering av webbsidor som beskriver domäner och kontrakt #

Detta prototyp användes under andra halvåret 2014 för att generera HTML-information som presenterades på:

  * http://rivta.se
  * Detta "framades" även in på [Ineras hemsida](http://www.inera.se/)

Under december 2014 utvecklades ett verktyg (inspirerat av denna prototyp) som tog över uppgiften att upprätthålla och presentera kontrakts- och anslutningsinformation.

För att installera och exekvera denna prototyp, gör följande:

  1. Sätt upp ett Linuxsystem (enbart testat på Debian)
  1. Installera subversion (SVN)
```
% sudo apt-get update
% sudo apt-get install subversion
```
  1. Installera SWI Prolog, se http://www.swi-prolog.org/
```
% sudo apt-add-repository ppa:swi-prolog/stable
% sudo apt-get update
% sudo apt-get install swi-prolog
```
  1. Installera paketet docx2txt
```
% sudo apt-get install docx2txt
```
  1. Om rsync över ssh skall användas för publicering, installera sshpass
```
% sudo apt-get install sshpass
```
  1. Checka ut en read-only kopia av hela svn-trädet med kommandot:
```
cd $HOME
svn checkout http://rivta.googlecode.com/svn/ rivta-read-only
```
  1. Kopiera filen $HOME/rivta-read-only/tools/domainmanagement/generateinfo/tags/1.0/rivta-go.sh till lämpligt mapp (som gärna finns in pathen)
  1. Editera filen, verifiera att PATH-satserna är rimliga samt sista satsen som skickar de genererade HTML-filerna för publicering.
  1. Om rsync över SSH skall användas, lägg miljövariabeln "SSHPASS" till lösenordet för SHH.
  1. Exekvera rivta-go.sh (och håll tummarna)

## Reservlösning på Inera ##
Denna applikation är installerad på servern ntjp-info på Inera. Ovanstående steg är genomförda.

Ett anpassat start-up script finns tillgängligt i mappen $HOME/bin. Det heter runit.sh, som kör jobbet och skapar en unik log. Loggen återfinns i samma mapp.

Ett cron-jobb kör hela rasket kl 05:00 varje morgon.