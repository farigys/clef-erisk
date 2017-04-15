import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;


public class sentimentCalculator {
	static ArrayList<String> posUserIdList = new ArrayList<String>();
	static ArrayList<String> negUserIdList = new ArrayList<String>();
	static ArrayList<String> userIdList = new ArrayList<String>();
		
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data_backup/";

		File writefile = new File(root + "sentimentFeatures.txt");
     	if (!writefile.exists()) {
    		writefile.createNewFile();
	    }
	    FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
	    BufferedWriter bw = new BufferedWriter(fw);
		
		Properties props = new Properties();
    	props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
    	StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
    	
    	File  fp = new File(root+"firstTestUserList.txt");
	    FileInputStream fisp = new FileInputStream(fp); 
	    BufferedReader readerp = new BufferedReader(new InputStreamReader(fisp));
	    
	    String line = "";
	    
	    while((line=readerp.readLine())!=null)
	    {
	    	String[] parts = line.split(" ");
	    	String userId = parts[0];
	    	String cat = parts[1];
	    	if(cat.equals("1"))posUserIdList.add(userId);
	    	else negUserIdList.add(userId);
	    	userIdList.add(userId);
	    }
	    
//	    userIdList.clear();
//	    userIdList.add("train_subject1839");
//	    userIdList.add("train_subject416");
//	    userIdList.add("train_subject7670");
	    
	    for(int userCount=0; userCount<userIdList.size(); userCount++)
        {
        	String currUserId = userIdList.get(userCount);
        	//if(negUserIdList.contains(currUserId))rootDir = root + "negative_examples_anonymous_chunks/chunk " + Integer.toString(currChunk) + "/";
    		//if(negUserIdList.contains(currUserId))continue;
    		//else rootDir = root + "positive_examples_anonymous_chunks/chunk " + Integer.toString(currChunk) + "/";
    		
        	System.out.println("current User: " + currUserId);
        	
        	int currChunk;
        	String currChunkSentiment = currUserId;
        	
        	int totalSentiment = 0;
			int count = 0;
        	
        	for(currChunk=1; currChunk<=10; currChunk++)
        	{	
        		//System.out.println("current chunk: " + currChunk);
    			String rootDir = "";
        		//rootDir = root + "positive_examples_anonymous_chunks/chunk " + Integer.toString(currChunk) + "/";
    			if(negUserIdList.contains(currUserId))rootDir = root + "negative_examples_anonymous_chunks/chunk " + Integer.toString(currChunk) + "/";
        		//if(negUserIdList.contains(currUserId))continue;
        		else rootDir = root + "positive_examples_anonymous_chunks/chunk " + Integer.toString(currChunk) + "/";
        		
        		String filename = currUserId + "_" + Integer.toString(currChunk) + ".xml";
   
        		File fXmlFile = new File(rootDir + filename);
		    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    	Document doc = dBuilder.parse(rootDir + filename);
		    	
		    	doc.getDocumentElement().normalize();
		    	
		    	NodeList nList = doc.getElementsByTagName("WRITING");
		    	
		    	for (int temp = nList.getLength()-1; temp >=0 ; temp--) {

		    		Node nNode = nList.item(temp);
		    		
		    		NodeList list = nNode.getChildNodes();
		    		
		    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		    			Element eElement = (Element) nNode;
		    			String title = eElement.getElementsByTagName("TITLE").item(0).getTextContent();
		    			String date = eElement.getElementsByTagName("DATE").item(0).getTextContent().trim();
		    			String text = eElement.getElementsByTagName("TEXT").item(0).getTextContent();
		    			
		    	    			    			
		    			Annotation annotation = pipeline.process(text);
		    	        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
		    	        	//System.out.print(sentence);
		    	        	count++;
		    	            Tree tree = sentence.get(SentimentAnnotatedTree.class);
		    	            int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
		    	            //System.out.println(":" + (sentiment));
		    	            totalSentiment += sentiment;
		    	        }
		    	        
		    	        
		    			
		    			//currChunkSentiment = currChunkSentiment + ":" + Double.toString(avgSentiment);
		    		}
		    	}
		    	
        	}
        	double avgSentiment = totalSentiment/(count * 1.0);
        	bw.write(currUserId + ":" + Double.toString(avgSentiment) + "\n");
        	
        }
    	bw.close();
	}
}
