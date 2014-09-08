#!/bin/sh 

# The following environment variable must be set

SVNRIVDIR=$HOME/rivta-read-only/ServiceInteractions/riv
WWWDIR=$HOME/tmp/www
EXECDIR=$HOME/rivta/tools/domainmanagement/generateinfo/trunk
TAKPROD=$EXECDIR/TakProd.csv
TAKQA=$EXECDIR/TakQA.csv
DOMAINTABLEURL='http://code.google.com/p/rivta/wiki/ServiceDomainTable'

# Remove all the domain files. Maybe count and check overall size for later verification
# Count the number of html files

cd $WWWDIR/domains 
find . -name "*.html" -print | wc | column -t 

#swipl -g external_main main.pl

echo $?

# Check RC, and verify the the html files "seems" to be generated ok
# if so, call rsync to send it up to one.com



