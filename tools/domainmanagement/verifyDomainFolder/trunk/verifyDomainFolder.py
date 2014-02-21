#!/usr/bin/env python


# Version 1.1 - 2014-02-21
#   Added a check of the file names of the TKB and AB in the docs directory.
# Version 1.0 - 2014-01-31 - LEO
#   Initial version


import os
import argparse
import glob
import string
import re

####################################################################################
# Global constants and variables
####################################################################################

programDescription = '''
The program verifies a service domain structure. 
It iterates through all folders in a RIVTA Service Domain structure, starting from a trunk or specific tags folder.
It verifies that the folder is defined according to the RIV TA Konfigurationsstyrning document,
see http://rivta.se/documents/ARK_0007. 
\n

If a tags folder is analyzed the name of the tag is verified. If no errors are detected the program can
optionally create a zip archive. 

\n
The program requires Python v2.  
\n
This program was last updated $LastChangedDate$ in revision $Rev$.

'''


# Constants
TOPLEVELDOMAINDIRECTORY = 'riv'
ZIPCOMMAND = 'zip -r'

# Content in test-suite is not analyzed yet
# OBS, for exact match "^" and "$" should be added at the beginning and end of the patterns below

MANDATORYCONTENT = { 
	'trunkKey' : [['^code_gen$', '1'], ['^docs$','1'], ['^schemas$', '1'], ['^test-suite$', '0']] ,
	'code_gen' : [[ '^jaxws$', '1'], ['^wcf$', '1']] ,
	'code_gen/jaxws' : [[ '^pom.xml$', '1']] ,
	'code_gen/wcf' : [[ '.bat$', '1']] ,
	'docs' : [[ '^TKB_#DOMAIN#.doc', '1'], [ '^AB_#DOMAIN#.doc', '1']] ,
	'schemas' : [[ '^interactions$', '1'], ['^core_components$', '1']] ,
	'schemas/core_components' : [[ '.xsd$', '1']] ,
	'schemas/interactions' : [[ 'Interaction$', '1']] ,
	'interactionKey' : [[ '.wsdl$', '1'], [ '.xsd$', '1']]   
		     }

# Global variables 
globRc = 0
analyzeMode = ''
domainNameVersionRc = ''

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
	global domainNameVersionRc

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

	if key in MANDATORYCONTENT:	
		dirPattern = MANDATORYCONTENT[key]	
	else:
		return

	qprint('')
	qprint('Analyzing folder: ' + displayKey)	
# 	qprint('')

	currentDir = rootDir + '/' + subDir	

	content = os.listdir(currentDir) 

	# Verify that all mandatory items are present
	for pattern in dirPattern:
		# Get a pattern at a time
		pString = pattern[0]
		pString = pString.replace('#DOMAIN#', domainNameVersionRc)
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
		if pCtrl == '0':
			# This is an optional item
			if noOccur < 1:
				qprint('   *** Information - optional item not included: ' + pString)
		elif pCtrl == '1':
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
			pString = pString.replace('#DOMAIN#', domainNameVersionRc)

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
# getNameAboveTrunk - extract name of domain based on directory names above trunk
####################################################################################
def getNameAboveTrunk(sourceDir):
	# Get name of actual directories from sourcedir (trunk) upwards
	head, dummy = os.path.split(sourceDir)
	head, lvl3  = os.path.split(head)
	head, lvl2  = os.path.split(head)
	head, lvl1  = os.path.split(head)

	qprint('')
	qprint('Analyzing directories above trunk')	

	# We must check if there is an old 2-level domain structure
	if lvl1 == TOPLEVELDOMAINDIRECTORY:
		threeLevel = False;
		qprint('>  Seems to be a two level domain structure')
	else:
		threeLevel = True;


	# Create the domain name based on the folder structure
	if threeLevel:
		dnFolders = lvl1 + '_' + lvl2 + '_' + lvl3
	else:
		dnFolders = lvl2 + '_' + lvl3

	print('    Domain folder structure: ' + dnFolders.replace('_','/'))

	return(dnFolders)

####################################################################################
# verifyTag - verifies a tag definition in a RIVTA Service Domain
####################################################################################
def verifyTag(sourceDir):
	global globRc
	
	# Get name of actual directories from sourcedir upwards
	head, tagName = os.path.split(sourceDir)
	head, tagDir  = os.path.split(head)
	head, lvl3  = os.path.split(head)
	head, lvl2  = os.path.split(head)
	head, lvl1  = os.path.split(head)

	qprint('')
	qprint('Analyzing TAG name: ' + tagName)	

	# We must check if there is an old 2-level domain structure
	if lvl1 == TOPLEVELDOMAINDIRECTORY:
		threeLevel = False;
		qprint('>  Seems to be a two level domain structure')
	else:
		threeLevel = True;


	# Create the domain name based on the folder structure
	if threeLevel:
		dnFolders = lvl1 + '_' + lvl2 + '_' + lvl3
	else:
		dnFolders = lvl2 + '_' + lvl3

	print('    Domain folder structure: ' + dnFolders.replace('_','/'))

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

	# Verification 1 - verify that the domain name part of tag is correct
	if not dnFolders == dnTagName:
		qprint('   ** Error: The name part of the tag folder ("' + dnTagName + '") is not equal to domain folder structure ("' + dnFolders.replace('_','/') + '")')
		globRc = globRc+1
		return

	# Verification 2 - verify that the version is two or three digits with dots in between
	rePattern = re.compile('[0-9]+\.[0-9]+(\.[0-9]+)?$')
	mObj = rePattern.match(tversion)

	if not mObj:
		qprint('   ** Error: The version part of the tag folder ("' + tversion + '") is not two or three numbers with dots in between!')
		globRc = globRc+1
		return

	# Verification 3 - verify that the optional RCXX part is correct.
	# We only check if there exist an RC specification
	if trc:
		rePattern = re.compile('RC[0-9]+$')
		mObj = rePattern.match(trc)

		if not mObj:
			qprint('   ** Error: The RC part of the tag folder ("' + trc + '") is not "RC" followed by a number!')
			globRc = globRc+1
			return
	
	domainNameVersionRc = dnTagName + '_' + tversion
	if trc:
		domainNameVersionRc = domainNameVersionRc + '_' + trc

	return(domainNameVersionRc)

####################################################################################
# Start of main program
####################################################################################

# Obtain and analyze input arguemts
parser = argparse.ArgumentParser(description=programDescription)

parser.add_argument('-q', '--quiet', action="store_true",
                   help='Set to supress output (only rc will be set)')				   

parser.add_argument('-z', '--zipdir',  
                    help='If we are analyzing a tags folder (not a trunk folder) a zip file will be created in the zipdir folder. Specify "." for current directory. The folder must exist if this option is specified!')

parser.add_argument('rootdir', 
                    help='Base folder for the domain (trunk or a tag directory). Specify "." for current directory.')


args = parser.parse_args()

quiet=args.quiet

# Set up root directory
rootDir=args.rootdir

rootDir=os.path.abspath(rootDir)

if not os.path.isdir(rootDir):
	print('')
	print('** Error: root directory ' + rootDir + ' does not exist!')
	print('')
	parser.print_help()
	exit(-1)

if not quiet:
	qprint('')
	qprint('Root directory: ' + rootDir)

# Verify that we are analyzing a tag (not the trunk). Get name of actual directories from sourcedir upwards.
head, tagName = os.path.split(rootDir) # Current root dir, can be the name of a tag, or trunk, or anything
head, tagDir  = os.path.split(head)    # Its parent directory - can be 'tags' 

# This script can be invoked in three distinct ways
if tagDir == 'tags':
	# We are analyzing a TAGS directory
	# Here we can extract name, version and RC from the tag name
	print('In tag dir')
	analyzeMode = 'TAGS'

elif tagName == 'trunk':
	# We are analyzing a trunk directory
	# Run in a trunk, we will not know version nor RC
	print('In a trunk dir')
	analyzeMode = 'TRUNK'

else:
	# For example when run on an extracted zip file
	print('In neither tag nor trunk dir')
	analyzeMode = 'OTHER'	      

# Set up zip directory
zipDir=args.zipdir

if zipDir:
	zipDir=os.path.abspath(zipDir)
	# We will not analyze tag names and create zip if we are not in a tags directory
	if not analyzeMode == 'TAGS':
		print('')
		print('** Error: Not possible to create zip since we are not not analyzing a "tags" directory!')
		print('')
		parser.print_help()
		exit(-1)
		
	# Verify that the target directory for the zip exist
	if not os.path.isdir(zipDir):
		print('')
		print('** Error: zip target directory ' + zipDir + ' does not exist!')
		print('')
		parser.print_help()
		exit(-1)

		if not quiet:
			qprint('')
			qprint('A zip file should be created in: ' + zipDir)


# Move working directory and get started with the root directory in the service domain
workingDir = os.getcwd()
os.chdir(rootDir)

# If we are in below a TAGS folder we should verify the name of the TAG
if analyzeMode == 'TAGS':
	domainNameVersionRc=verifyTag(rootDir)
elif analyzeMode == 'TRUNK':
	domainNameVersionRc=getNameAboveTrunk(rootDir)
else:
	domainNameVersionRc = ''

# Then, finally, lets get going, recursevly analyze the domain folders
verifyDir('')

# If no error this far, and the user asked for a zip, we will try to create it
if zipDir:
	if globRc == 0 and analyzeMode=='TAGS' and domainNameVersionRc:
		zipName = 'ServiceContracts' + '_' + domainNameVersionRc
		qprint('')
		qprint('No Errors, zip file will be created')
		command = ZIPCOMMAND + ' ' + zipDir + '/' + zipName + '.zip *'
		qprint(command)
		os.system(command)
	else:
		qprint('')
		qprint('Due to errors no zip will be created')
		

# Return working directory 
os.chdir(workingDir)

# Exist with error code
print ''
if globRc > 0:
	print ' **** ', globRc, ' error(s) detected! ***'
else:
	print 'OK!!!'


print 'Exit with rc:', globRc
exit(globRc)
