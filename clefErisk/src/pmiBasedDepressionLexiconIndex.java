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

//converts text files to indexed files based on the high-PMI unigram and bigram dictionary

//wherever there is a "chunk n analysis", change n to the current chunk
public class pmiBasedDepressionLexiconIndex {
	public static void main(String argv[]) throws IOException, ParserConfigurationException, SAXException {
		int max = 0;
		MaxentTagger tagger = new MaxentTagger("models/english-left3words-distsim.tagger");
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data_backup/";
		
		//String root = "/home/farig/Desktop/eRisk_test/chunk 10 analysis/"; //for testList, change to 3,4,5....
		//change the root for each new chunk publication
		
		
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
        	//int count = Integer.parseInt(parts[1]);
        	if(cat.equals("1"))posUserIdList.add(userId);
        	else negUserIdList.add(userId);
        	totalUserList.add(userId);
        	//postCount.put(userId, count);
        }
        //fr.close();
        
        //fileIO f1 = new fileIO(root + "unigramPMI.txt", "r");
        fileIO f1 = new fileIO(root + "unigramPMI.txt", "r");//for bigram
        line = "";
        
        ArrayList<String> rxs = new ArrayList<String>();
        
        for(int i=0; i<100; i++)
        {
        	line = f1.read();
        	String currStr = line.split("\t")[0];
        	//System.out.println(currStr);
        	rxs.add(currStr);
        }
        
        int length = rxs.size();
        
        //System.out.print(rxs);
        
        f1.close();
        
        //fileIO f2 = new fileIO(root + "unigramFeatures.txt", "w"); //for unigram
        fileIO f2 = new fileIO(root + "bigramFeatures.txt", "w"); //for bigram
        //fileIO f2 = new fileIO(root + "uFeaturesTest.txt", "w"); //for testList
        
        //fileIO f3 = new fileIO(root + "testPostCount.txt", "w");
        
        ///////////////////////////////////////////////////////////////////////////////////
        for(int userCount=0; userCount<negUserIdList.size(); userCount++)
        {
        	int[] regexCpu = new int[length];
        	String currUserId = negUserIdList.get(userCount);
        	f2.write(currUserId);
        	//fileIO fwpu = new fileIO(root + "unigramOutput/" + currUserId + ".txt", "w");//for unigram
        	fileIO fwpu = new fileIO(root + "bigramOutput/" + currUserId + ".txt", "w");//for bigram
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
	    			int[] regexCpp = new int[length];
		    		Node nNode = nList.item(temp);
		    		fwpu.write(currUserId + "_" + currChunk + "_" + temp);
		    		NodeList list = nNode.getChildNodes();
		    		
		    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		    			Element eElement = (Element) nNode;
		    			String title = eElement.getElementsByTagName("TITLE").item(0).getTextContent();
		    			String text = tagger.tagString(title + " " + eElement.getElementsByTagName("TEXT").item(0).getTextContent());
		    			String[] parts = text.split(" ");
		    			//for(int wordC=0; wordC<parts.length; wordC++)
			    		for(int wordC=0; wordC<parts.length-1; wordC++)//for bigram
		    			{
		    				//String input = parts[wordC].split("_")[0];//for unigram
		    				String input = parts[wordC].split("_")[0] + parts[wordC + 1].split("_")[0];//for bigram
		    				
		    				for (String rx : rxs)
		    				{
		    					if (rx.equals(input))
		    					{
		    						regexCpu[rxs.indexOf(rx)]++;
		    						regexCpp[rxs.indexOf(rx)]++;
		    					}
		    				}
		    			}
		    		}
		    		for(int t=0; t<length; t++)
		    		{
		    			fwpu.write(":" + regexCpp[t]);
		    		}
		    		fwpu.write("\n");
		    	}
		    	
        	}
        	fwpu.close();
        	for(int t=0; t<length; t++)
    		{
    			f2.write(":" + regexCpu[t]);
    		}
        	f2.write("\n");
        }
        
        /////////////////////////////////////////////////////////////////////////////////////////
        for(int userCount=0; userCount<posUserIdList.size(); userCount++)
        {
        	int[] regexCpu = new int[length];
        	String currUserId = posUserIdList.get(userCount);
        	f2.write(currUserId);
        	//fileIO fwpu = new fileIO(root + "unigramOutput/" + currUserId + ".txt", "w");//for unigram
        	fileIO fwpu = new fileIO(root + "bigramOutput/" + currUserId + ".txt", "w");//for bigram
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
	    			int[] regexCpp = new int[length];
		    		Node nNode = nList.item(temp);
		    		fwpu.write(currUserId + "_" + currChunk + "_" + temp);
		    		NodeList list = nNode.getChildNodes();
		    		
		    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		    			Element eElement = (Element) nNode;
		    			String title = eElement.getElementsByTagName("TITLE").item(0).getTextContent();
		    			String text = tagger.tagString(title + " " + eElement.getElementsByTagName("TEXT").item(0).getTextContent());
		    			String[] parts = text.split(" ");
		    			//for(int wordC=0; wordC<parts.length; wordC++)
		    			for(int wordC=0; wordC<parts.length-1; wordC++)//for bigram
		    			{
		    				//String input = parts[wordC].split("_")[0];//for unigram
		    				String input = parts[wordC].split("_")[0] + parts[wordC + 1].split("_")[0];//for bigram
		    				for (String rx : rxs)
		    				{
		    					if (rx.equals(input))
		    					{
		    						regexCpu[rxs.indexOf(rx)]++;
		    						regexCpp[rxs.indexOf(rx)]++;
		    					}
		    				}
		    			}
		    		}
		    		for(int t=0; t<length; t++)
		    		{
		    			fwpu.write(":" + regexCpp[t]);
		    		}
		    		fwpu.write("\n");
		    	}
		    	
        	}
        	fwpu.close();
        	for(int t=0; t<length; t++)
    		{
    			f2.write(":" + regexCpu[t]);
    		}
        	f2.write("\n");
        }
        f2.close();
        //System.out.println("max Length: " + max);
        
        //uncomment everything from here upto the ///////// part for the trainList analysis
        
        
	}
}

