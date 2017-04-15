
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.StringTokenizer;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class xmlParser {

  public static void main(String argv[]) {

	MaxentTagger tagger = new MaxentTagger("models/english-left3words-distsim.tagger");
	String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data/positive_examples_anonymous_chunks/";
	
	for(int chunk=1; chunk<=10; chunk++)
	{
		String currChunk = "chunk " + Integer.toString(chunk);
		String rootDir = root + currChunk + "/";
		
		File folder = new File(rootDir);
		
		File[] listOfFiles = folder.listFiles();
		
		for(int n=0; n<listOfFiles.length; n++)
		{
			String filename = listOfFiles[n].getName();
			
			System.out.println("now in: " + filename);
			
		    try {
		    	
		    	File fXmlFile = new File(rootDir + filename);
		    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    	Document doc = dBuilder.parse(rootDir + filename);
		    	
		    	doc.getDocumentElement().normalize();

		    	//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		    	
		    	String userId = doc.getElementsByTagName("ID").item(0).getTextContent();
		    	
		    	//System.out.println(userId);

		    	NodeList nList = doc.getElementsByTagName("WRITING");

		    	System.out.println("----------------------------");

		    	for (int temp = 0; temp < nList.getLength(); temp++) {

		    		Node nNode = nList.item(temp);
		    		
		    		NodeList list = nNode.getChildNodes();
		    		
		    		String taggedTitle = "";
		    		String taggedText = "";
		    		String formattedDate = "";
		    		
		    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		    			Element eElement = (Element) nNode;
		    			String title = eElement.getElementsByTagName("TITLE").item(0).getTextContent();
		    			if(!title.equals(""))
		    			{
		    				taggedTitle = tagger.tagString(title);
		    			}
		    			String date = eElement.getElementsByTagName("DATE").item(0).getTextContent().trim();
		    			StringTokenizer tokens = new StringTokenizer(date, " -:");
		    			
		    			while(tokens.hasMoreTokens())
		    			{
		    				formattedDate += tokens.nextToken().toString();
		    			}
		    			String text = eElement.getElementsByTagName("TEXT").item(0).getTextContent();
		    			taggedText = tagger.tagString(text);
		    			//System.out.println("Date: "+ formattedDate);
		    			//System.out.println("Text: "+ text + "\n");
		    			//System.out.println("Text: "+ taggedText + "\n");
		    		}
		    		for (int i = 0; i < list.getLength(); i++) {

		                Node tempnode = list.item(i);

		    	   // get the salary element, and update the value
		    		   if ("TEXT".equals(tempnode.getNodeName())) {
		    			   tempnode.setTextContent(taggedText);
		    		   }
		    		   if ("DATE".equals(tempnode.getNodeName())) {
		    			   tempnode.setTextContent(formattedDate);
		    		   }
		    		   if ("TITLE".equals(tempnode.getNodeName())) {
		    			   tempnode.setTextContent(taggedTitle);
		    		   }
		    		}
		    	}
		    	TransformerFactory transformerFactory = TransformerFactory.newInstance();
		    	Transformer transformer = transformerFactory.newTransformer();
		    	DOMSource source = new DOMSource(doc);
		    	StreamResult result = new StreamResult(new File(rootDir + filename));
		    	transformer.transform(source, result);
		    	
		        } catch (Exception e) {
		    	e.printStackTrace();
		        }
		}
	}
	

  }

}
