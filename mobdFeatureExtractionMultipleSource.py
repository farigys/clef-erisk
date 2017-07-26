#Extracts metamap concepts from given text. Metamap receives texts as sentences. 
#this is specifically used for clef-erisk test set, as each chunk is released
#the data file is updated. For the code on training data, see mobdFeatureExtractionMultiSem.py
#This focuses on three ontologies: SNOMEDCT-US, RXNORM and CHV
import numpy as np
import matplotlib.pyplot as plt
import json
import collections
from pprint import pprint
from pymetamap import MetaMap
import itertools
import glob
import xml.sax
import xml.etree.ElementTree as ET

mm = MetaMap.get_instance('/home/farig/Desktop/public_mm/bin/metamap16')
rootD = "/home/farig/Desktop/eRisk_test/"
userIdFile = open(rootD + "testPostCount.txt")
writefile = open(rootD + "chunk 10 analysis/metamapFeaturesTest.txt", 'a+')  #change here.................................

userList = []
posUserList = []
negUserList = []
totalUserList = []

userList = userIdFile.readlines()

lineCount = 0

for userIdLine in userList:
    lineCount = lineCount + 1
    #if lineCount <= 200:
	#continue
	#break
    eachLine = userIdLine.split(",")
    #print eachLine
    #if eachLine[1] == '1\n':
    #posUserList.append(eachLine[0])
    #else:
    #negUserList.append(eachLine[0])
    totalUserList.append(eachLine[0])

#print len(negUserList)
print len(totalUserList)

#totalUserList = ["train_subject416"]

userCount = 0
skiplist = ["train_subject7938", "train_subject687", "train_subject1939", "train_subject1909"]

totalUserList = ["test_subject499"] #delete this while running for the entire userList

for userId in totalUserList:
    raiseFlag = 0
    writefile1 = open(rootD + userId + ".txt", 'a+') #change here............................
    writefile2 = open(rootD + userId + "concept_triggers.txt", 'a+') #change here............................
    userCount = userCount + 1
    if userId in skiplist: 
	raiseFlag = 1
    #if userCount == 301:
	#break
    #if userId == "train_subject7938":
	#continue
    #if userId == "train_subject687":
	#continue
    postCount = 0
    totalText = ""
    mobdCount = 0
    clndCount = 0
    print userId
    for chunk in range(1,11): #change here........................
        rootDir = ""
        #print chunk
        #if userId in posUserList:
            #rootDir = rootD + "positive_examples_anonymous_chunks/chunk " + str(chunk) + "/"
        #else:
            #rootDir = rootD + "negative_examples_anonymous_chunks/chunk " + str(chunk) + "/" 
        
	#rootDir = rootD + "chunk 10 analysis/chunk " + str(chunk) + "/" #change here...................................
        rootDir = rootD + "chunk " + str(chunk) + " analysis/chunk " + str(chunk) + "/"
	filename = str(userId) + "_" + str(chunk) + ".xml"
        #print filename
        tree = ET.parse(rootDir + filename) 
        root = tree.getroot()
        #userId = root[0].text
        for i in range(1,len(root)):
            #totalText = totalText + " " + root[i][3].text
	    writefile1.write(userId + "_" + str(chunk) + "_" + str(i) + ":")
	    stringToMap = root[i][3].text
	    stringList = stringToMap.split(".")
            sents = []
	    conceptsList = []
	    #print stringList
	    #print "\n"
   	    for string in stringList:
		#print string + "\n"
		if len(string) > 1500:
		    continue
		#sents.append(string)
		concepts = []
		concepts, error = mm.extract_concepts([string], source='SNOMEDCT_US,CHV,RXNORM', allow_overmatches=False, allow_concept_gaps=True)
		for concept in concepts:
		    try:
                        semtypes = concept.semtypes
                    except:
                        continue
                    if "mobd" in semtypes or "clnd" in semtypes:
                        writefile2.write(concept.trigger + ";" + concept.preferred_name + ";" + semtypes + ";" + string + "\n")  
		    if "mobd" in semtypes:
                        mobdCount = mobdCount + len(concept.trigger.split(','))
                        flag = 1
		        writefile1.write(concept.cui + ",")
                        #print concept.trigger + " " + concept.preferred_name + " " + concept.semtypes
                    if "clnd" in semtypes:
                        clndCount = clndCount + len(concept.trigger.split(','))
                        flag = 1
		        writefile1.write(concept.cui + ",")                 
		conceptsList.append(concepts)
            #if len(root[i][3].text) > 10000:
             #   print len(root[i][3].text)
		#continue
            flag = 0
            
            #sents = [root[i][3].text]
            #concepts, error = mm.extract_concepts(sents, source='SNOMEDCT_US,CHV,RXNORM', allow_overmatches=False, allow_concept_gaps=True)
	    
            if flag == 1: 
                postCount = postCount + 1    
            #sents = [totalText]
	    writefile1.write("\n");

    #concepts, error = mm.extract_concepts(sents, source='SNOMEDCT_US', allow_overmatches=False, allow_concept_gaps=True)

    #for concept in concepts:
    #    try:
    #        semtypes = concept.semtypes
    #    except:
    #        continue
    #    if "mobd" in semtypes:
    #        mobdCount = mobdCount + len(concept.trigger.split(','))
    #        print concept.trigger + " " + concept.preferred_name + " " + concept.semtypes
    #    if "clnd" in semtypes:
    #        clndCount = clndCount + len(concept.trigger.split(','))
    #        #print concept.trigger + " " + concept.preferred_name + " " + concept.semtypes
        
    print mobdCount, clndCount
    #writefile.write(userId + "," + str(mobdCount) + "," + str(clndCount) + "," + str(postCount) + "\n")
    writefile1.close()
    writefile2.close()
writefile.close()




    
    
    
    
    
    
    
    
    


