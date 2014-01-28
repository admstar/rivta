#!/usr/bin/env python

#
# LEO 2014-01-28
#

import os
import argparse
import glob
import string
import re

####################################################################################
# Global constants and variables
####################################################################################

programDescription = '''
The program creates a zip archive of a service domain, while verifying that fulfil naming and other rules defined in the 
RIV TA Konfigurationsstyrning document,
see http://rivta.se/documents/ARK_0007.
\n
The program requires Python v2. 

'''

mandatoryContent = { 
	'trunkKey' : [['code_gen', '1'], ['docs','1'], ['schemas', '1']] ,
	'code_gen' : [[ 'jaxws', '1'], ['wcf', '1']] ,
	'code_gen/jaxws' : [[ 'pom.xml', '1']] ,
	'code_gen/wcf' : [[ '.bat', '1']] ,
	'docs' : [[ '.doc', '2']] ,
	'schemas' : [[ 'interactions', '1'], ['core_components', '1']] ,
	'schemas/core_components' : [[ '.xsd$', '1']] ,
	'schemas/interactions' : [[ 'Interaction$', '1']] ,
	'interactionKey' : [[ '.wsdl$', '1'], [ '.xsd$', '1']]   
		     }

globRc = 0

####################################################################################
# qprint - Prints a string unless the quiet flag is set
####################################################################################
def qprint(string):
	if not quiet:
		print string
	return	

####################################################################################
# verifyDir - verifies a directory in a RIVTA Service Domain
####################################################################################
def verifyDir(subDir):
	global globRc

	if subDir == '':
		# The name of the root directory in the service domain structure is unkknown. 'trunk' is used as pattern key. 
		key = 'trunkKey'
		displayKey = 'TRUNK'
	elif subDir.endswith('Interaction'):
		# Identify an schema/XxInteraction directory and use the interaction pattern.
		key = 'interactionKey'
		displayKey = subDir
	else:
		# Otherwise the subDir is the key for the pattern.
		key = subDir
		displayKey = subDir

	if key in mandatoryContent:	
		dirPattern = mandatoryContent[key]	
	else:
		return

	qprint('')
	qprint('Analyzing folder: ' + displayKey)	
	qprint('')

	currentDir = rootDir + '/' + subDir	

	content = os.listdir(currentDir) 

	# Verify that all mandatory items are present
	for pattern in dirPattern:
		# Get a pattern at a time
		pString = pattern[0]
		pCtrl = pattern[1]

		# Count how many times the current pattern occurs in the directory
		noOccur = 0
		for dirItem in content:
			# For each item in the directory
			rePattern = re.compile(pString)
			mObj = rePattern.search(dirItem)
			if mObj:
				noOccur = noOccur + 1
		# Verify occurance and inform		
		if pCtrl == '1':
			# There should be at least one occurance				
			if noOccur < 1:
				globRc = globRc+1
				qprint('   *** Error   - Missing mandatory item: ' + pString)
		elif pCtrl == '2':
			# There should be at least two occurances
			if noOccur < 2:
				globRc = globRc+1
				qprint('   *** Error   - Expected two word documents (TKB and AB), found: ' + str(noOccur))


	# Check if unexpected items are present
	for dirItem in content:
		# Go through all items in folcer

		noOccur = 0
		for pattern in dirPattern:
			# Compare it to all patterns
			pString = pattern[0]

			rePattern = re.compile(pString)
			mObj = rePattern.search(dirItem)
			if mObj:
				noOccur = noOccur + 1

		if noOccur == 0:
			qprint('   **  Warning - Unexpected item found: ' + dirItem)
	
	# Recursivly go through existing subdirectoreis
	for item in content:
		if subDir == '':
			dir = item
		else:
			dir = subDir + '/' + item

		fullpath = currentDir + '/' + item

		if os.path.isdir(fullpath):
			verifyDir(dir)
		
	return			       

####################################################################################
# Start of main program
####################################################################################

# Obtain and analyze input arguemts
parser = argparse.ArgumentParser(description=programDescription)

parser.add_argument('-q', '--quiet', action="store_true",
                   help='Set to supress output (only rc will be set)')				   

parser.add_argument('rootdir', 
                    help='Base folder for the domain (trunk). Specify "." for current directory.')

args = parser.parse_args()

quiet=args.quiet
rootDir=args.rootdir

if not rootDir:
	rootDir=os.getcwd()
else:
	rootDir=os.path.abspath(rootDir)

if not quiet:
	qprint('')
	qprint('Root directory: ' + rootDir)

# Move working directory and get started with the root directory in the service domain
workingDir = os.getcwd()
os.chdir(rootDir)

# Get going, recursevly analyze the domain folders
rc = verifyDir('')

# Return working directoy 
os.chdir(workingDir)

# Exist with error code
print ''
print 'Exit:', globRc
exit(globRc)



