import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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


public class textCollection {
	public static void main(String argv[]) throws IOException, ParserConfigurationException, SAXException {
		int max = 0;
		//MaxentTagger tagger = new MaxentTagger("models/english-left3words-distsim.tagger");
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data/";
		
		ArrayList<String> posUserIdList = new ArrayList<String>();
		ArrayList<String> negUserIdList = new ArrayList<String>();
		
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
        }
        
        for(int userCount=0; userCount<negUserIdList.size(); userCount++)
        {
        	String currUserId = negUserIdList.get(userCount);
        	int currChunk;
        	
        	File writefile = new File(root + "negative_examples_anonymous_chunks_texts/" + currUserId + ".txt");
         	if (!writefile.exists()) {
        		writefile.createNewFile();
        	}
        	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
        	BufferedWriter bw = new BufferedWriter(fw);
        	
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

		    		Node nNode = nList.item(temp);
		    		
		    		NodeList list = nNode.getChildNodes();
		    		
		    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		    			Element eElement = (Element) nNode;
		    			String text = eElement.getElementsByTagName("TEXT").item(0).getTextContent();
		    			String[] parts = text.split(" ");
		    			int currLength = parts.length;
		    			if(currLength>max)
		    			{
		    				max = currLength;
		    				System.out.println(currChunk + " " + currUserId);
		    			}
		    			bw.write(text + "\n");
		    		}
		    	}
		    	
        	}
        	bw.close();
        }
        System.out.println("max Length: " + max);
	}
}
