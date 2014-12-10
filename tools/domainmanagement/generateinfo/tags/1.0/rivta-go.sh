#!/bin/sh 

# The following environment variable must be set

export SVNDIR=$HOME/rivta-read-only
export SVNRIVDIR=$SVNDIR/ServiceInteractions/riv

export EXECDIR=$SVNDIR/tools/domainmanagement/generateinfo/tags/1.0
#export EXECDIR=$SVNDIR/tools/domainmanagement/generateinfo/trunk
export TAKPROD=$EXECDIR/TakProd.csv
export TAKQA=$EXECDIR/TakQA.csv

export WWWDIR=$HOME/tmp/www
export DOMAINSDIR=$WWWDIR/domains

export TKBTEXTDIR=$HOME/tmp/tkb_store

export DOMAINTABLEURL='http://code.google.com/p/rivta/wiki/ServiceDomainTable'

# Check if libreoffice is running. Must be killed before prolog code is started!
# ps -ef | grep libreoffice

echo "*** Update SVN"
cd $SVNDIR
svn update

# Remove all the domain files. Count and check overall size for later verification
echo "*** Checks sizes and remove HTML files"

cd $DOMAINSDIR

NOFILES_BEFORE=$(ls | wc | awk '{print $1}')
SIZEALLFILES_BEFORE=$(du -sb . | awk '{print $1}')

rm $DOMAINSDIR/*.html

# Run the prolog stuff
echo "*** Run the prolog stuff"
cd $EXECDIR  

swipl -q -g external_main main.pl
RC=$?

echo "RC from prolog = $RC"

# Check RC, and verify the the html files "seems" to be generated ok
# if so, call rsync to send it up to one.com

if [ $RC -gt 0 ]; then
    echo "SWIPL did not return RC==0, existing"
    exit $RC
fi

# Check the generated domain files. Count and check overall size.
echo "*** Verify the generated files" 
cd $DOMAINSDIR


NOFILES_AFTER=$(ls | wc | awk '{print $1}')
SIZEALLFILES_AFTER=$(du -sb . | awk '{print $1}')

# Accept that number of HTML files and total size decrease slightly

NO_BEFORE="$(($NOFILES_BEFORE - 5))"
SZ_BEFORE="$(($SIZEALLFILES_BEFORE - 10000))"

if [ $NOFILES_AFTER -lt $NO_BEFORE ]; then
    echo "Number of HTML files decreased too much - exiting"
    exit 42
fi

if [ $SIZEALLFILES_AFTER -lt $SZ_BEFORE ]; then
    echo "Size of all HTML files decreased too much - exiting"
    exit 43
fi

# All looks good so far, copy the html files to the server
echo "*** All looks good, send it to the server"
rsync -avz --rsh='sshpass -e ssh' $DOMAINSDIR/ rivta.se@ssh.rivta.se:/www/domains/




