# clef-erisk
This project contains codes and data files for clef-erisk shared task

#Codes:

antiDepressantUsage.java: calculates the occurence of anti-depressant usage in posts (creates both post level and user level files)

arffToLiblinear.java: code for converting weka data files to liblinear data files

bag_of_words_feature_vector_builder.java: creates BOW feature vectors for the training data (creates both post level and user level files)

chunk_joiner_val_all_chunks.java: creates final decision file based on the predictions of each chunk by joining each chunk results for validation data

chunk_output_joiner.java: creates final submission file

chunk_separator.java: distribute postwise feature vectors into chunks for training data

collect_bottom_user_text.java: collects text for users with low number of posts

convertTextToIndex.java: converts text files into indexed files based on a dictionary

createDictionary.java: creates a dictionary based on the training file

createValOutputComparisonForModels.java: creates a side-by-side comparison file for all feature sets for validation data

divide_risk_golden_truth.java: divide the userIds in two classes

ensembleDataCreate.java: create ensemble data for both train and test data

extractEngagementFeatures.java: extracts engagement features (outdated)

featureVectorBuilder.java: creates feature vectors based on some basic features (outdated)

filterDictionary.java: filter the dictionary created by createDictionary.java based on word frequency

filterUsers.java: filter users based on their total post count (outdated)

findDiagnosedDepression.java: searches for the terms diag\* and depress\* within a specified word window

fullFeatureVectorBuilder.java: updated version of featureVectorBuilder.java (outdated)

joinMetamapAndRegex.java: joins metamap and regex feature vectors at user level and creates arff files for the training data

joinMetamapAndRegexTest.java: joins metamap and regex feature vectors at user level and creates arff files for the test data

liblinearToArff.java: converts liblinear files to arff files

liwcCombiner.java: joins chunk based user level LIWC feature vectors in one file

metamapFeaturesExtraction.java: creates weka arff file of a user-level metamap feature vector for the training data

metamapFeaturesExtractionTest.java: creates weka arff file of a user-level metamap feature vector for the test data

metamapFeaturesInWeka.java: joins lots of old feature vectors with metamap feature vectors at a user level (outdated)

metamapFeatureVectorBuilder.java: creates feature vectors from metamap outputs

new_line_eliminator.java: eliminates new line from LSTM outputs

outputFormatter.java: formats outputs as per clef directions

performanceAnalysisOnConfidence.java: performance analysis from libsvm output depending on confidence level

pmiBasedDepressionLexiconIndex.java: converts text files to indexed files based on the high-PMI unigram and bigram dictionary

pmiCount.java: calculates PMI for words from external data

pmiWeka.java: creates weka feature vectors based on high PMI BOW

pmiWordCalculator.java: calculates word count from training data

prepare_final_outputs.java: replaces 0s with 2s in the final submission

regexCalculator.java: calculate chunkwise regex word occurences and creates feature vectors on both post-level and user-level

regexLiblinear.java: creates liblinear feature vectors for regex features

regexSentenceExtractor.java: extracts sentences containing depression regexes

regexWeka.java: creates weka arff file from the user level feature vector

sentimentAnalyzer.java: runs LIWC sentiment analysis on train data

sentimentCalculator.java: runs Stanford Sentiment Analyzer on train data

stemCombiner.java: combines multiple occuring stems into one

textCollection.java: collects texts with titles and separates each post using a special sparator

trainValDivider.java: divides users into train and val sets

wordCount.java: creates word count dictionary from JAN 2015 external reddit data

xmlParser.java: parses XML files 


Sequence of running codes for the trst phase of the shared task:

regexCalculator.java->mobdFeatureExtraction.py->metamapFeatureVectorBuilder.java->metamapFeatureExtractionTest.java->RegexWeka.java->joinMetamapAndRegexTest.java->arffToLiblinear.java->ensembleDataCreate.java->outputFormatter.java->chunkOutputJoiner.java->prepare_final_outputs.java








