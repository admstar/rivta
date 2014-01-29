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
RIV TA Konfigurationsstyrning document. see http://rivta.se/documents/ARK_0007. 

\n
The program requires Python v2. 
 
\n
This program was last updated $LastChangedDate$ in revision $Rev$.

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

topLevelDomaindirectory = 'riv'
zipCommand = 'zip -r'

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

parser.add_argument('targetdir', 
                    help='The folder where the zip archive should be placed. Specify "." for current directory. The folder must exist!')

parser.add_argument('sourcedir', 
                    help='The RIVTA tags folder to compress. Specify "." for current directory.')


args = parser.parse_args()

quiet=args.quiet

# Verify target directory
targetDir=args.targetdir
targetDir=os.path.abspath(targetDir)

if not os.path.isdir(targetDir):
	print('')
	print('** Error: Target directory ' + targetDir + ' does not exist!')
	print('')
	parser.print_help()
	exit(1)

# Verify source directory
sourceDir=args.sourcedir
sourceDir=os.path.abspath(sourceDir)

if not os.path.isdir(sourceDir):
	print('')
	print('** Error: Source directory ' + sourceDir + ' does not exist!')
	print('')
	parser.print_help()
	exit(1)


# Save current working directory
dirAtStart = os.getcwd()

# Move working directory to the source directory. It should be a tag-folder for the domain.
os.chdir(sourceDir)

# Get name of actual directories from sourcedir upwards
head, tagName = os.path.split(sourceDir)
head, tagDir  = os.path.split(head)
head, lvl3  = os.path.split(head)
head, lvl2  = os.path.split(head)
head, lvl1  = os.path.split(head)

# We must check if there is an old 2-level domian structure
if lvl1 == topLevelDomaindirectory:
	threeLevel = False;
	qprint('>  Seems to be a two level domain structure')
else:
	threeLevel = True;


# Create the domain name based on the folder structure
if threeLevel:
	dnFolders = lvl1 + '_' + lvl2 + '_' + lvl3
else:
	dnFolders = lvl2 + '_' + lvl3

print('dnFolders: ' + dnFolders)

#print(lvl1)
#print(lvl2)
#print(lvl3)
#print(tagDir)
#print(tagName)

# Extract the name parts of the name of the TAG directory 
rest = tagName
if threeLevel:
	tlvl1, dummy, rest = rest.partition('_')

tlvl2,    dummy, rest = rest.partition('_')
tlvl3,    dummy, rest = rest.partition('_')
tversion, dummy, rest = rest.partition('_')
trc,      dummy, rest = rest.partition('_')

# Create the domain name bases on the name of the TAGs folder
if threeLevel:
	dnTagName = tlvl1 + '_' + tlvl2 + '_' + tlvl3
else:
	dnTagName = tlvl2 + '_' + tlvl3

print('dnTagname: ' + dnTagName)

#print(tlvl1)
#print(tlvl2)
#print(tlvl3)
print('tversion: ' + tversion)
print('trc: ' + trc)

# Verification 1 - verify that the domain name part of tag is correct
if not dnFolders == dnTagName:
	qprint('** Error: The name part of the tag folder ("' + dnTagName + '") is not equal to domain folder structure ("' + dnFolders.replace('_','/') + '")')
	#exit(10)

# Verification 2 - verify that the version is two or three digits with dots in between
rePattern = re.compile('[0-9]+\.[0-9]+(\.[0-9]+)?$')
mObj = rePattern.match(tversion)

if not mObj:
	qprint('** Error: The version part of the tag folder ("' + tversion + '") is not two or three numbers with dots in between!')
	exit(11)

# Verification 3 - verify that the optional RCXX part is correct.
# We only check if there exist an RC specification
if trc:
	print('TRC true')

	rePattern = re.compile('RC[0-9]+$')
	mObj = rePattern.match(trc)

	if not mObj:
		qprint('** Error: The RC part of the tag folder ("' + trc + '") is not "RC" followed by a number!')
		exit(12)

# Verification 4 - Verify the content of the source folder with the verifyDomainFolder script
# Will be added

# Finally time to create the zip file
zipFilename = 'ServiceContracts' + '_' + dnTagName + '_' + tversion
if trc:
	zipFilename = zipFilename + '_' + trc

print('zipFilename: ' + zipFilename)

exit(42)


# Get going, recursevly analyze the domain folders
rc = verifyDir('')

# Restore working directoy 
os.chdir(dirAtStart)

# Exist with error code
print ''
print 'Exit:', globRc
exit(globRc)

#################################################
# Todo
# - Add verification 4 above, and invoke the verifyDomainFolder script  


