import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

//creates a dictionary based on the training file

public class createDictionary {
	public static void main(String argv[]) throws IOException{
		
		HashMap<String, Integer> dict = new HashMap<String, Integer>();
		
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data/";
		File writefile = new File(root + "dictionary.txt");
     	if (!writefile.exists()) {
    		writefile.createNewFile();
    	}
    	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
    	BufferedWriter bw = new BufferedWriter(fw);
    	
    	/////////////////////////////////////////////////////////////////////////////////
    	
    	String rootDir = root + "negative_examples_anonymous_chunks_texts/";
    	
    	File folder = new File(rootDir);
		
		File[] listOfFiles = folder.listFiles();
		
		for(int n=0; n<listOfFiles.length; n++)
		{
			String filename = listOfFiles[n].getName();
			File  f = new File(rootDir+filename);
		    FileInputStream fis = new FileInputStream(f); 
		    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		    
		    String line = "";
		    
		    while((line=reader.readLine())!=null)
		    {
		    	String[] parts = line.split(" ");
		    	for(int c=0; c<parts.length; c++)
		    	{
		    		if(parts[c].equals(""))continue;
		    		if(dict.containsKey(parts[c]))
		    		{
		    			int tempCount = dict.get(parts[c]);
		    			tempCount++;
		    			dict.put(parts[c], tempCount);
		    		}
		    		else 
		    		{
		    			dict.put(parts[c], 1);
		    		}
		    	}
		    }
			
		}
		/////////////////////////////////////////////////////////////////////////////////////
		
		rootDir = root + "positive_examples_anonymous_chunks_texts/";
    	
    	folder = new File(rootDir);
		
		listOfFiles = folder.listFiles();
		
		for(int n=0; n<listOfFiles.length; n++)
		{
			String filename = listOfFiles[n].getName();
			File  f = new File(rootDir+filename);
		    FileInputStream fis = new FileInputStream(f); 
		    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		    
		    String line = "";
		    
		    while((line=reader.readLine())!=null)
		    {
		    	String[] parts = line.split(" ");
		    	for(int c=0; c<parts.length; c++)
		    	{
		    		if(parts[c].equals(""))continue;
		    		if(dict.containsKey(parts[c]))
		    		{
		    			int tempCount = dict.get(parts[c]);
		    			tempCount++;
		    			dict.put(parts[c], tempCount);
		    		}
		    		else 
		    		{
		    			dict.put(parts[c], 1);
		    		}
		    	}
		    }
			
		}
		
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
