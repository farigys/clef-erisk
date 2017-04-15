import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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


public class sentimentAnalyzer {
	public static void main(String[] args) throws IOException, ParseException {
		DecimalFormat four = new DecimalFormat("#0.0000");
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data/";
		//

    	
    	//Trie t = new Trie();
    	
    	ArrayList<String> psywordlist = new ArrayList<String>();
    	
    	
    	
    	HashMap<String, ArrayList<Integer>> regCatMap = new HashMap<String, ArrayList<Integer>>();
    	
    	HashMap<String, ArrayList<String>> regexMap = new HashMap<String, ArrayList<String>>(); 
    	
    	HashSet<Pattern> regexset = new HashSet<Pattern>();
    	
    	HashSet<String> dict = new HashSet<String>();
    	
    	String rootDir = root+"LIWC/";
    	
    	File  fregex = new File(rootDir+"/LIWC2001_English.dic");
	    FileInputStream fisregex = new FileInputStream(fregex); 
	    BufferedReader readerregex = new BufferedReader(new InputStreamReader(fisregex));
	    String line = "";
	    readerregex.readLine();
	    
	    while((line = readerregex.readLine())!=null)
	    {
	    	StringTokenizer token = new StringTokenizer(line, "\t");
	    	String regex1 = token.nextToken();
	    	//if(regex1.length()<=3 && regex1.contains("*"))System.out.println(regex1);
	    	if(!regex1.contains("*"))dict.add(regex1);
	    	else
	    	{
	    		String regex2 = regex1.substring(0, 2);
	    		//System.out.println(regex2);
	    		if(regexMap.containsKey(regex2))
	    		{
	    			ArrayList<String> temp = regexMap.get(regex2);
	    			StringTokenizer token1 = new StringTokenizer(regex1, "*");
	    			String temps = token1.nextToken();
	    			regex1 = temps + "[.]*";
	    			temp.add(regex1);
	    			regexMap.put(regex2, temp);
	    		}
	    		else
	    		{
	    			ArrayList<String> temp = new ArrayList<String>();
	    			StringTokenizer token1 = new StringTokenizer(regex1, "*");
	    			String temps = token1.nextToken();
	    			regex1 = temps + "[.]*";
	    			temp.add(regex1);
	    			regexMap.put(regex2, temp);
	    		}
	    		
	    	}
	    	
	    	//wordCat regCat = new wordCat(regex1);
	    	
	    	ArrayList<Integer> cat = new ArrayList<Integer>();
	    	
	    	while(token.hasMoreTokens())
	    	{
	    		//int catno = Integer.parseInt(token.nextToken().toString());
	    		cat.add(Integer.parseInt(token.nextToken().toString()));
	    	}
	    	regCatMap.put(regex1, cat);
	    	
	    }
    	//System.out.println(regCatMap);
    	
    	
    	
    	readerregex.close();
    	////////////////////////////////////////////////////////////////////////////
    	
    	root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data/negative_examples_anonymous_chunks/";
		
    	
    	for(int chunk=1; chunk<=10; chunk++)
    	{
    		String currChunk = "chunk " + Integer.toString(chunk);
    		rootDir = root + currChunk + "/";
			
			File folder = new File(rootDir);
			
			File[] listOfFiles = folder.listFiles();
			
			File file = new File(rootDir + "/PsyLingCount.csv");//PsychoLinguistic category cache
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int n=0; n<listOfFiles.length; n++)
			{
				String filename = listOfFiles[n].getName();
				
				System.out.println("now in: " + filename);
				
				double[] arr = new double[69];
				
				String userId = "";
				String text = "";
				String date = "";
				
				int tokenCount = 0;
				
			    try {
			    	
			    	File fXmlFile = new File(rootDir + filename);
			    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			    	Document doc = dBuilder.parse(rootDir + filename);
			    	
			    	doc.getDocumentElement().normalize();

			    	//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			    	
			    	userId = doc.getElementsByTagName("ID").item(0).getTextContent();
			    	
			    	//System.out.println(userId);

			    	NodeList nList = doc.getElementsByTagName("WRITING");

			    	System.out.println("----------------------------");

			    	for (int temp = 0; temp < nList.getLength(); temp++) {

			    		Node nNode = nList.item(temp);
			    		
			    		NodeList list = nNode.getChildNodes();
			    		
			    		String formattedDate = "";
			    		
			    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			    			Element eElement = (Element) nNode;
			    			String title = eElement.getElementsByTagName("TITLE").item(0).getTextContent();
			    			
			    			date = eElement.getElementsByTagName("DATE").item(0).getTextContent().trim();
			    			StringTokenizer tokens = new StringTokenizer(date, " -:");
			    			
			    			while(tokens.hasMoreTokens())
			    			{
			    				formattedDate += tokens.nextToken().toString();
			    			}
			    			text = eElement.getElementsByTagName("TEXT").item(0).getTextContent();
			    			
			    			//System.out.println("Date: "+ formattedDate);
			    			//System.out.println("Text: "+ text + "\n");
			    			//System.out.println("Text: "+ taggedText + "\n");
			    			StringTokenizer token1 = new StringTokenizer(text, " \n\t\r:,-!()[]{}?\"';.");
							String previousWord = "";
							tokenCount += token1.countTokens();
							while(token1.hasMoreTokens())
							{
								String match = token1.nextToken().toString().trim();
								//if(word.endsWith("."))word = word.substring(0, word.length()-1);
								
								if(dict.contains(match.toLowerCase()))
						  		{
						  			//System.out.println(key+" "+pair.getValue()+" from dict");
						  			ArrayList<Integer> tempc = regCatMap.get(match.toLowerCase());
						  			for(int i=0; i<tempc.size(); i++)
						  			{
						  				int cat = tempc.get(i);
						  				//int[] tempx = psyWordMap.get(filename);
						  				//tempx[cat] += Integer.parseInt(pair.getValue().toString());
						  				//psyWordMap.put(filename, tempx);
						  				arr[cat]++;
						  			}
						  		}
						  		else
						  		{
						  			if(match.length()<2)continue;
						  			String key1 = match.toLowerCase().substring(0, 2);
						  			if(!regexMap.containsKey(key1))continue;
						  			else
						  			{
						  				ArrayList<String> tempreg = regexMap.get(key1);
						  				for(int i=0; i<tempreg.size(); i++)
						  				{
						  					Pattern pattern = Pattern.compile(tempreg.get(i));
						  					//System.out.println(pattern);
						  					//System.out.println("Matching "+match+" with "+tempreg.get(i));
						  					Matcher matcher = pattern.matcher(match.toLowerCase());
						  					if(matcher.find())
						  					{
						  						//System.out.println("match found for "+match+" with "+tempreg.get(i)+", "+key+" "+pair.getValue());
						  						//System.out.println(match.toLowerCase());
						  						ArrayList<Integer> tempc = regCatMap.get(tempreg.get(i));
						  						for(int j=0; j<tempc.size(); j++)
						  		      			{
						  		      				int cat = tempc.get(j);
						  		      				//int[] tempx = psyWordMap.get(filename);
						  		      				//tempx[cat] += Integer.parseInt(pair.getValue().toString());
						  		      				//psyWordMap.put(filename, tempx);
						  		      				arr[cat]++;
						  		      			}
						  						break;
						  					}
						  				}
						  			}
						  			
						  		}
								
							}
			    		}
			    	}
			    	
			        } catch (Exception e) {
			    	e.printStackTrace();
			        }
					
//					for(int cnt =0; cnt<arr.length; cnt++)
//					{
//						arr[cnt] = arr[cnt]/(double)tokenCount;
//					}
							
						
						
					bw.write(userId + ":" + date + ":");
					for(int x=1; x<68; x++)bw.write(arr[x] + ":");
					bw.write(arr[68] + ":" + tokenCount + "\n");
					
				}
				bw.close();
			}
			
		}
	}

