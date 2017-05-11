import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class createDictionary {
	public static void main(String argv[]) throws IOException{
		MaxentTagger tagger = new MaxentTagger("models/english-left3words-distsim.tagger");
		HashMap<String, Integer> dict = new HashMap<String, Integer>();
		
		String root = "/home/farig/Desktop/reddit data/training posts/";
		File writefile = new File(root + "dictionary.txt");
     	if (!writefile.exists()) {
    		writefile.createNewFile();
    	}
    	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
    	BufferedWriter bw = new BufferedWriter(fw);
    	
    	/////////////////////////////////////////////////////////////////////////////////
    	
    	String rootDir = root + "negTrainPosts.txt";
    	
    	int url = 0;
    	int subr = 0;
    	
    	File folder = new File(rootDir);
		
		//File[] listOfFiles = folder.listFiles();
		
		//for(int n=0; n<listOfFiles.length; n++)
		{
			//String filename = listOfFiles[n].getName();
			File  f = new File(rootDir);
		    FileInputStream fis = new FileInputStream(f); 
		    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		    
		    String line = "";
		    
		    while((line=reader.readLine())!=null)
		    {
		    	if(line.equals("--------------------"))continue;
		    	String[] parts = tagger.tagString(line).split(" ");
		    	for(int c=0; c<parts.length; c++)
		    	{
		    		String currWord = parts[c];
		    		String word = parts[c].split("_")[0];
		    		//String pos = parts[c].split("_")[1];
		    		if(word.contains("https://") || word.contains("http://") || word.contains("www"))
		    		{
		    			url++;
		    			continue;
		    		}
		    		if(word.contains("/r/"))
		    		{
		    			subr++;
		    			continue;
		    		}
		    		
		    		if(word.equals(""))continue;
		    		if(dict.containsKey(word))
		    		{
		    			int tempCount = dict.get(word);
		    			tempCount++;
		    			dict.put(word, tempCount);
		    		}
		    		else 
		    		{
		    			dict.put(word, 1);
		    		}
		    	}
		    }
			
		}
		/////////////////////////////////////////////////////////////////////////////////////
		
		rootDir = root + "posTrainPosts.txt";
    	
    	folder = new File(rootDir);
		
		//listOfFiles = folder.listFiles();
		
		//for(int n=0; n<listOfFiles.length; n++)
		{
			//String filename = listOfFiles[n].getName();
			File  f = new File(rootDir);
		    FileInputStream fis = new FileInputStream(f); 
		    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		    
		    String line = "";
		    
		    while((line=reader.readLine())!=null)
		    {
		    	if(line.equals("--------------------"))continue;
		    	String[] parts = tagger.tagString(line).split(" ");
		    	for(int c=0; c<parts.length; c++)
		    	{
		    		String currWord = parts[c];
		    		String word = parts[c].split("_")[0];
		    		//String pos = parts[c].split("_")[1];
		    		if(word.contains("https://") || word.contains("http://") || word.contains("www"))
		    		{
		    			url++;
		    			continue;
		    		}
		    		if(word.contains("/r/"))
		    		{
		    			subr++;
		    			continue;
		    		}
		    		
		    		if(word.equals(""))continue;
		    		if(dict.containsKey(word))
		    		{
		    			int tempCount = dict.get(word);
		    			tempCount++;
		    			dict.put(word, tempCount);
		    		}
		    		else 
		    		{
		    			dict.put(word, 1);
		    		}
		    	}
		    }
			
		}
		
		dict.put("url", url);
		dict.put("subr", subr);
		
		//ArrayList<String> dictionary = new ArrayList<String>(dict);
		Iterator it = dict.entrySet().iterator();
		int index = 1;
		
		while(it.hasNext())
		{
			Map.Entry e = (Map.Entry)it.next();
			String word = e.getKey().toString();
			String count = e.getValue().toString();
			bw.write(word + "\t" + index + "\t" + count + "\n");
			index++;
		}
		
		
    	bw.close();
	}
}
