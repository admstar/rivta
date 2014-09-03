#!/bin/sh 

# The following environment variable must be set
# HOME

SVNRIVDIR=$HOME/rivta-read-only/ServiceInteractions/riv
WWWDIR=$HOME/tmp/www
DOMAINTABLEURL='http://code.google.com/p/rivta/wiki/ServiceDomainTable'
TAKPROD=$HOME/rivta/tools/domainmanagement/generateinfo/trunk/TakProd.csv
TAKQA=$HOME/rivta/tools/domainmanagement/generateinfo/trunk/TakQA.csv

swipl -q -f main.pl 
