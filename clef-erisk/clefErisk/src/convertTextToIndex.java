import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

//converts text files into indexed files based on a dictionary

public class convertTextToIndex {
public static void main(String argv[]) throws IOException{
	
	ArrayList<String> classesToDelete = new ArrayList<String>();
	String[] tags = {":", ".", ",", "\"", "'", "''", "\"\"", "-LRB-", "#", "`", "``"};
	
	for(int m=0; m<tags.length; m++)classesToDelete.add(tags[m]);
		
		HashMap<String, String> dictMap = new HashMap<String, String>();
		
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data/";
		File  f = new File(root + "filtered_dictionary_100.txt"); //provide the name of the dictionary here
	    FileInputStream fis = new FileInputStream(f); 
	    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	    
	    String line = "";
	    
	    while((line=reader.readLine())!=null)
	    {
	    	String[] parts = line.split("\t");
	    	if(parts.length>2)throw new ArrayIndexOutOfBoundsException();
	    	dictMap.put(parts[0], parts[1]);
	    }
    	
    	/////////////////////////////////////////////////////////////////////////////////
    	
    	String rootDir = root + "negative_examples_anonymous_chunks_texts/";
    	String rootDirIndexed = root + "negative_examples_anonymous_chunks_texts_indexed/";
    	
    	File folder = new File(rootDir);
		
		File[] listOfFiles = folder.listFiles();
		
		for(int n=0; n<listOfFiles.length; n++)
		{
			String filename = listOfFiles[n].getName();
			System.out.println("Now in: " + filename);
			File  f1 = new File(rootDir+filename);
		    FileInputStream fis1 = new FileInputStream(f1); 
		    BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
		    
		    File writefile = new File(rootDirIndexed+filename);
	     	if (!writefile.exists()) {
	    		writefile.createNewFile();
	    	}
	    	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
	    	BufferedWriter bw = new BufferedWriter(fw);
		    
		    line = "";
		    
		    
		    while((line=reader1.readLine())!=null)
		    {
		    	String writable = "";
		    	//if(line.equals(""))continue;
		    	if(line.equals("------------------"))
		    	{
		    		bw.write("\n");
		    		continue;
		    	}
		    	String[] parts = line.split(" ");
		    	for(int c=0; c<parts.length; c++)
		    	{
		    		if(parts[c].equals(""))continue;
		    		//dict.add(parts[c]);
		    		String toWrite = parts[c]; 
		    		if(parts[c].contains("http://") || parts[c].contains("https://") || parts[c].contains("www."))
		    			toWrite = "URL_URL";
		    		if(parts[c].startsWith("r/") || parts[c].startsWith("/r/"))
		    			toWrite = "SUBR_SUBR";
		    		String[] wordParts = toWrite.split("_");
			    	String posTag = wordParts[wordParts.length-1];
			    	
			    	if(classesToDelete.contains(posTag))continue;
			    	if(posTag.equals("CD"))toWrite = "NMBR_NMBR";
			    	if(!dictMap.containsKey(toWrite))continue;
			    	
		    		writable = writable + " " + dictMap.get(toWrite);
		    	}
		    	bw.write(writable.trim() + " ");
		    }
		    reader1.close();
		    //if(!writable.equals(""))bw.write(writable);
		    bw.close();
			
		}
		/////////////////////////////////////////////////////////////////////////////////////
		
		rootDir = root + "positive_examples_anonymous_chunks_texts/";
		rootDirIndexed = root + "positive_examples_anonymous_chunks_texts_indexed/";
    	
    	folder = new File(rootDir);
		
		listOfFiles = folder.listFiles();
		
		for(int n=0; n<listOfFiles.length; n++)
		{
			String filename = listOfFiles[n].getName();
			System.out.println("Now in: " + filename);
			File  f1 = new File(rootDir+filename);
		    FileInputStream fis1 = new FileInputStream(f1); 
		    BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
		    
		    File writefile = new File(rootDirIndexed+filename);
	     	if (!writefile.exists()) {
	    		writefile.createNewFile();
	    	}
	    	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
	    	BufferedWriter bw = new BufferedWriter(fw);
		    	    
		    line = "";
		    //String writable = "";
		    
		    while((line=reader1.readLine())!=null)
		    {
		    	String writable = "";
		    	//if(line.equals(""))continue;
		    	if(line.equals("------------------"))
		    	{
		    		bw.write("\n");
		    		continue;
		    	}
		    	String[] parts = line.split(" ");
		    	for(int c=0; c<parts.length; c++)
		    	{
		    		if(parts[c].equals(""))continue;
		    		//dict.add(parts[c]);
		    		String toWrite = parts[c]; 
		    		if(parts[c].contains("http://") || parts[c].contains("https://") || parts[c].contains("www."))
		    			toWrite = "URL_URL";
		    		if(parts[c].startsWith("r/") || parts[c].startsWith("/r/"))
		    			toWrite = "SUBR_SUBR";
		    		String[] wordParts = toWrite.split("_");
			    	String posTag = wordParts[wordParts.length-1];
			    	
			    	if(classesToDelete.contains(posTag))continue;
			    	if(posTag.equals("CD"))toWrite = "NMBR_NMBR";
			    	if(!dictMap.containsKey(toWrite))continue;
			    	
		    		writable = writable + " " + dictMap.get(toWrite);
		    	}
		    	bw.write(writable.trim() + " ");
		    }
		    reader1.close();
		    //if(!writable.equals(""))bw.write(writable);
		    bw.close();
			
		}
		
	}
}
