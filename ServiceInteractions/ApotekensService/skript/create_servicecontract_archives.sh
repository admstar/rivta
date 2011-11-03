#!/bin/bash

# Creates service contract zip archives from subversion
#
# 1. make sure your current working directory is the service contract package
# 2. run this program

# package name is super directory
top=$( expr $( pwd ) : '.*/\(.*\)' )
pkg=${top//_/.}

# get version from user
read -p "Version (ex: 1.0-beta): " ver
echo ""
[ -z "$ver" ] && echo "Error: Version must be specified" && exit 1

echo "Runs a subverison update"

# update, and get latest subversion rev
rev=$( expr "$( svn up )" : 'At revision \(.*\)\.' )

for d in $( echo */); do
    arch="ServiceContracts_${pkg}_$( basename $d )_${ver}-r${rev}.zip"
    echo "Processing --> \"$arch\""
    zip -rq $arch $d -x "*.DS_Store" -x "*.svn/*"
done
