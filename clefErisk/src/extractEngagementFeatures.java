import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class extractEngagementFeatures {
	static String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data_backup/";
	static ArrayList<String> posUserIdList = new ArrayList<String>();
	static ArrayList<String> negUserIdList = new ArrayList<String>();
	static ArrayList<String> userIdList = new ArrayList<String>();
	//static MaxentTagger tagger = new MaxentTagger("models/english-left3words-distsim.tagger");
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void main(String argv[]) throws IOException, ParserConfigurationException, SAXException, ParseException {
		File  fp = new File(root+"risk_golden_truth.txt");
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
	    
	   
		
		extractEngagementFeatures();
		extractURLsandQuestions();
		extractInsomniaIndex();
		
	}
	
	

	private static void extractInsomniaIndex() {
		
		
	}

	private static void extractURLsandQuestions() {
		
		
	}

	private static void extractEngagementFeatures() throws ParserConfigurationException, SAXException, IOException, ParseException {
		MaxentTagger tagger = new MaxentTagger("models/english-left3words-distsim.tagger"); 
		
		File writefile = new File(root + "engagementFeatures.txt");
	     	if (!writefile.exists()) {
	    		writefile.createNewFile();
	    }
	    FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
	    BufferedWriter bw = new BufferedWriter(fw);
		
	    for(int userCount=0; userCount<userIdList.size(); userCount++)
	        {
	        	String currUserId = userIdList.get(userCount);
	        	//if(negUserIdList.contains(currUserId))rootDir = root + "negative_examples_anonymous_chunks/chunk " + Integer.toString(currChunk) + "/";
        		//if(negUserIdList.contains(currUserId))continue;
        		//else rootDir = root + "positive_examples_anonymous_chunks/chunk " + Integer.toString(currChunk) + "/";
        		
	        	System.out.println("current User: " + currUserId);
	        	int currChunk;
	        	int totalposts = 0;
	        	int totalReplies = 0;
	        	String startDate = "";
	        	String endDate = "";
	        	String currDate = ""; 
	        	String prevDate = "";
	        	int totalUrls = 0;
	        	int totalQuestions = 0;
	        	ArrayList<Double> waitTimes = new ArrayList<Double>();
	        	int nightPosts = 0;
	        	
	        	for(currChunk=1; currChunk<=10; currChunk++)
	        	{	
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
			    			currDate = date;
			    			
			    			//System.out.println(currDate);
			    			if(currChunk ==1 && temp == nList.getLength()-1)
			    			{
			    				startDate = currDate;
			    				prevDate = startDate;
			    			}
			    			//System.out.println(title.length());
			    			if(title.equals("   "))totalReplies++;
			    			
			    			String taggedText = tagger.tagString(text);
			    			
			    			String[] parts = taggedText.split(" ");
			    			
			    			for(int words=0; words<parts.length; words++)
			    			{
			    				if(parts[words].contains("http://") || parts[words].contains("www."))totalUrls++;
			    				if(parts[words].startsWith("?"))totalQuestions++;
			    			}
			    			
			    			Date d1 = format.parse(prevDate);
			    			Date d2 = format.parse(currDate);
			    			
			    			long diff = d2.getTime() - d1.getTime();
			    			
			    			long diffSeconds = (diff/(1000));
			    			
//			    			if(currUserId.equals("train_subject416"))
//			    				System.out.println(prevDate + " " + currDate + " " + diffSeconds);
			    			
			    			waitTimes.add((double)diffSeconds);
			    			
			    			if(d2.getHours()>21 || d2.getHours()<6)nightPosts++;
			    			
			    			prevDate = currDate;
			    		}
			    	}
			    	totalposts+=nList.getLength();
			    	endDate = currDate;
	        	}

	        	Date d1 = format.parse(startDate);
	        	//System.out.println(startDate);
	        	//System.out.println(endDate);
    			Date d2 = format.parse(endDate);
    			
    			long diff = d2.getTime() - d1.getTime();
    			
    			double diffDays = (double) diff/(24*60*60*1000);
    			
    			int totalActTime;
    			
    			if(diffDays == 0) totalActTime = 0;
    			else totalActTime = (int)Math.ceil(diffDays);
    			
//    			if(currUserId.equals("train_subject416"))
//    			{
//    				for(int x=0; x<waitTimes.size(); x++)
//        			{
//        				System.out.println(waitTimes.get(x));
//        			}
//    			}
    			
    			double medianWait = 0.0;
    			Collections.sort(waitTimes);
    			int middle = waitTimes.size()/2;
    			if(waitTimes.size()%2 ==1)medianWait = waitTimes.get(middle);
    			else medianWait = (waitTimes.get(middle) + waitTimes.get(middle+1))/2.0;
    			
    			double maxWait = waitTimes.get(waitTimes.size()-1);
    			
    			double avgWait = 0.0;
    			
    			for(int x=0; x<waitTimes.size(); x++)
    			{
    				avgWait+=waitTimes.get(x);
    			}
    			
    			
    				
    			
    			avgWait/=(waitTimes.size()-1);
    			
//    			file format:
//    			userId -> active time in days -> total posts -> total replies -> total urls -> 
//    			total questions -> median wait time in minutes -> maximum wait time in minutes ->
//    			average wait time in minutes -> insomniac index 
    			
    			bw.write(currUserId + ":" + totalActTime + ":" + totalposts + ":" + totalReplies + ":"
    					+ totalUrls + ":" + totalQuestions + ":" + (medianWait/60.0) + ":" + (maxWait/60.0) + 
    					":" + (avgWait/60.0) + ":" + (nightPosts/(totalposts*1.0)) + "\n");
	        }
	    bw.close();
	}
}
