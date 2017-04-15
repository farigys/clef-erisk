import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

//calculates word count from training data

//wherever there is a "chunk n analysis", change n to the current chunk
public class pmiWordCalculator {
	public static void main(String argv[]) throws IOException, ParserConfigurationException, SAXException {
		int max = 0;
		MaxentTagger tagger = new MaxentTagger("models/english-left3words-distsim.tagger");
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data/";
		
		//String root = "/home/farig/Desktop/eRisk_test/chunk 4 analysis/"; //for testList, change to 3,4,5....
		//change the root for each new chunk publication
		int size = 100;
		
		
		ArrayList<String> posUserIdList = new ArrayList<String>();
		ArrayList<String> negUserIdList = new ArrayList<String>();
		
		ArrayList<String> totalUserList = new ArrayList<String>(); //for testList
		
		
		//remove comment when not analyzing test data
		File  fp = new File(root+"risk_golden_truth.txt");
        FileInputStream fisp = new FileInputStream(fp); 
        BufferedReader readerp = new BufferedReader(new InputStreamReader(fisp));
        
      //for testList
        //fileIO fr = new fileIO(root + "features/testList.txt", "r");
		//fileIO fr = new fileIO(root + "testPostCount.txt", "r");
		
		//HashMap<String, Integer> postCount = new HashMap<String, Integer>();
		
        String line = "";
        
        while((line=readerp.readLine())!=null)
        //while((line = fr.read())!=null) //for testList
        {
        	String[] parts = line.split(" ");
        	String userId = parts[0];
        	String cat = parts[1];
        	if(cat.equals("1"))posUserIdList.add(userId);
        	else negUserIdList.add(userId);
        	totalUserList.add(userId);
        	//postCount.put(userId, count);
        }
        readerp.close();
        
        fileIO f1 = new fileIO(root + "wordPMI.txt", "r");
        line = "";
        
        //ArrayList<Pattern> rxs = new ArrayList<Pattern>();
        
        
//        while((line=f1.read())!=null)
//        {
//        	  String currRegex = line;
//        	  rxs.add(Pattern.compile(line));
//        	
//        }
        
        //int length = rxs.size();
        
        //System.out.print(rxs);
        ArrayList<String> wordList = new ArrayList<String>();
        
        for(int p=0; p<size; p++)
        {
        	line = f1.read();
        	wordList.add(line.split("\t")[0]);
        }
        
        f1.close();
        
        f1 = new fileIO(root + "bigramPMI.txt", "r");
        line = "";
        
        ArrayList<String> bigramList = new ArrayList<String>();
        
        for(int p=0; p<size; p++)
        {
        	line = f1.read();
        	bigramList.add(line.split("\t")[0]);
        }
        
        f1.close();
        
        System.out.println(wordList.size());
        System.out.println(bigramList.size());
        
        //System.out.println(wordList.get(453));
        //System.out.println(bigramList.get(453));
        
        System.out.println(negUserIdList.size());
        System.out.println(posUserIdList.size());
        
        fileIO f2 = new fileIO(root + "pmiUnigramsFeatures.txt", "w");
//        fileIO f2 = new fileIO(root + "regexFeaturesTest.txt", "w"); //for testList
//        
//        fileIO f3 = new fileIO(root + "testPostCount.txt", "w");
        
        ///////////////////////////////////////////////////////////////////////////////////
        for(int userCount=0; userCount<negUserIdList.size(); userCount++)
        {
        	//int[] pmiCpu = new int[size];
        	int[] pmiCpu = new int[size];//while working with both uni and bi
        	String currUserId = negUserIdList.get(userCount);
        	f2.write(currUserId);
        	fileIO fwpu = new fileIO(root + "pmiWordsOutputsUnigrams/" + currUserId + ".txt", "w");
        	int currChunk;
        	
        	for(currChunk=1; currChunk<=10; currChunk++)
        	{
        		String rootDir = root + "negative_examples_anonymous_chunks/chunk " + Integer.toString(currChunk) + "/";
        		
        		String filename = currUserId + "_" + Integer.toString(currChunk) + ".xml";
        		
        		File fXmlFile = new File(rootDir + filename);
		    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    	Document doc = dBuilder.parse(rootDir + filename);
		    	
		    	doc.getDocumentElement().normalize();
		    	
		    	NodeList nList = doc.getElementsByTagName("WRITING");
		    	
		    	for (int temp = 0; temp < nList.getLength(); temp++) {
	    			int[] pmiCpp = new int[size];
	    			//int[] pmiCpp = new int[size];//change here for just n-gram
		    		Node nNode = nList.item(temp);
		    		fwpu.write(currUserId + "_" + currChunk + "_" + temp);
		    		NodeList list = nNode.getChildNodes();
		    		
		    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		    			Element eElement = (Element) nNode;
		    			String text = eElement.getElementsByTagName("TEXT").item(0).getTextContent();
		    			String[] parts = text.split(" ");
		    			for(int wordC=0; wordC<parts.length; wordC++)//for unigram
		    			{
		    				String input = parts[wordC].split("_")[0].toLowerCase();
		    				for (int wc=0; wc<wordList.size(); wc++)
		    				{
		    					if (wordList.get(wc).equals(input))
		    					{
		    						pmiCpu[wc]++;
		    						pmiCpp[wc]++;
		    					}
		    				}
		    			}
		    			
//		    			for(int wordC=0; wordC<parts.length -1; wordC++)//for bigram
//		    			{
//		    				String firstWord = parts[wordC];
//							String secondWord = parts[wordC + 1];
//		    				String input = firstWord.split("_")[0] + " " + secondWord.split("_")[0];
//		    				for (int wc=0; wc<bigramList.size(); wc++)
//		    				{
//		    					if (bigramList.get(wc).equals(input))
//		    					{
//		    						pmiCpu[size + wc]++;
//		    						pmiCpp[size + wc]++;
//		    						//pmiCpu[wc]++; //just bigram
//		    						//pmiCpp[wc]++; //just bigram
//		    					}
//		    				}
//		    			}
		    		}
		    		for(int t=0; t<size; t++)//change here
		    		{
		    			fwpu.write(":" + pmiCpp[t]);
		    		}
		    		fwpu.write("\n");
		    	}
		    	
        	}
        	fwpu.close();
        	for(int t=0; t<size; t++)//change here
    		{
    			f2.write(":" + pmiCpu[t]);
    		}
        	f2.write("\n");
        }
        
        /////////////////////////////////////////////////////////////////////////////////////////
        for(int userCount=0; userCount<posUserIdList.size(); userCount++)
        {
        	//int[] pmiCpu = new int[size];
        	int[] pmiCpu = new int[size];//while working with both uni and bi
        	String currUserId = posUserIdList.get(userCount);
        	f2.write(currUserId);
        	fileIO fwpu = new fileIO(root + "pmiWordsOutputsUnigrams/" + currUserId + ".txt", "w");
        	int currChunk;
        	
        	for(currChunk=1; currChunk<=10; currChunk++)
        	{
        		String rootDir = root + "positive_examples_anonymous_chunks/chunk " + Integer.toString(currChunk) + "/";
        		
        		String filename = currUserId + "_" + Integer.toString(currChunk) + ".xml";
        		
        		File fXmlFile = new File(rootDir + filename);
		    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    	Document doc = dBuilder.parse(rootDir + filename);
		    	
		    	doc.getDocumentElement().normalize();
		    	
		    	NodeList nList = doc.getElementsByTagName("WRITING");
		    	
		    	for (int temp = 0; temp < nList.getLength(); temp++) {
		    		int[] pmiCpp = new int[size];
	    			//int[] pmiCpp = new int[size];//change here for just n-gram
		    		Node nNode = nList.item(temp);
		    		fwpu.write(currUserId + "_" + currChunk + "_" + temp);
		    		NodeList list = nNode.getChildNodes();
		    		
		    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		    			Element eElement = (Element) nNode;
		    			String text = eElement.getElementsByTagName("TEXT").item(0).getTextContent();
		    			String[] parts = text.split(" ");
		    			for(int wordC=0; wordC<parts.length; wordC++)//for unigram
		    			{
		    				String input = parts[wordC].split("_")[0].toLowerCase();
		    				for (int wc=0; wc<wordList.size(); wc++)
		    				{
		    					if (wordList.get(wc).equals(input))
		    					{
		    						pmiCpu[wc]++;
		    						pmiCpp[wc]++;
		    					}
		    				}
		    			}
		    			
//		    			for(int wordC=0; wordC<parts.length -1; wordC++)//for bigram
//		    			{
//		    				String firstWord = parts[wordC];
//							String secondWord = parts[wordC + 1];
//		    				String input = firstWord.split("_")[0] + " " + secondWord.split("_")[0];
//		    				for (int wc=0; wc<bigramList.size(); wc++)
//		    				{
//		    					if (bigramList.get(wc).equals(input))
//		    					{
//		    						pmiCpu[size + wc]++;
//		    						pmiCpp[size + wc]++;
//		    						//pmiCpu[wc]++; //just bigram
//		    						//pmiCpp[wc]++; //just bigram
//		    					}
//		    				}
//		    			}
		    		}
		    		for(int t=0; t<size; t++)//change here
		    		{
		    			fwpu.write(":" + pmiCpp[t]);
		    		}
		    		fwpu.write("\n");
		    	}
		    	
        	}
        	fwpu.close();
        	for(int t=0; t<size; t++)//change here
    		{
    			f2.write(":" + pmiCpu[t]);
    		}
        	f2.write("\n");
        }
        f2.close();
        //System.out.println("max Length: " + max);
        
        //uncomment everything from here upto the ///////// part for the trainList analysis
        
        //comment the bottom to ignore testList
//      for(int userCount=0; userCount<totalUserList.size(); userCount++)
//      {
//      	int[] regexCpu = new int[length];
//      	String currUserId = totalUserList.get(userCount);
//      	int postcount = 0;
//      	f2.write(currUserId);
//      	fileIO fwpu = new fileIO(root + "regexOutputTest/" + currUserId + ".txt", "w");
//      	int currChunk;
//      	String rootD = "/home/farig/Desktop/eRisk_test/";
//      	
//      	for(currChunk=1; currChunk<=4; currChunk++) //change limit to 2,3,4 as data gets published
//      	{
//      		String rootDir = rootD + "chunk " + Integer.toString(currChunk) + " analysis/chunk " + Integer.toString(currChunk) + "/";
//      		
//      		String filename = currUserId + "_" + Integer.toString(currChunk) + ".xml";
//      		
//      		File fXmlFile = new File(rootDir + filename);
//		    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//		    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//		    	Document doc = dBuilder.parse(rootDir + filename);
//		    	
//		    	doc.getDocumentElement().normalize();
//		    	
//		    	NodeList nList = doc.getElementsByTagName("WRITING");
//		    	
//		    	postcount += nList.getLength();
//		    	
//		    	for (int temp = 0; temp < nList.getLength(); temp++) {
//		    		
//	    			int[] regexCpp = new int[length];
//		    		Node nNode = nList.item(temp);
//		    		fwpu.write(currUserId + "_" + currChunk + "_" + temp);
//		    		NodeList list = nNode.getChildNodes();
//		    		
//		    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//		    			Element eElement = (Element) nNode;
//		    			String text = eElement.getElementsByTagName("TEXT").item(0).getTextContent();
//		    			String taggedText = tagger.tagString(text);
//		    			String[] parts = taggedText.split(" ");
//		    			for(int wordC=0; wordC<parts.length; wordC++)
//		    			{
//		    				String input = parts[wordC].split("_")[0].toLowerCase();
//		    				for (Pattern rx : rxs)
//		    				{
//		    					if (rx.matcher(input).matches())
//		    					{
//		    						regexCpu[rxs.indexOf(rx)]++;
//		    						regexCpp[rxs.indexOf(rx)]++;
//		    					}
//		    				}
//		    			}
//		    		}
//		    		for(int t=0; t<length; t++)
//		    		{
//		    			fwpu.write(":" + regexCpp[t]);
//		    		}
//		    		fwpu.write("\n");
//		    	}
//		    	
//      	}
//      	fwpu.close();
//      	for(int t=0; t<length; t++)
//  		{
//  			f2.write(":" + regexCpu[t]);
//  		}
//      	f2.write("\n");
//      	f3.write(currUserId + "," + postcount + "\n");
//      }
//      f2.close();
//      f3.close();
	}
}
