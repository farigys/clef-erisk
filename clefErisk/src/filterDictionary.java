import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

//filter the dictionary created by createDictionary.java based on word frequency

public class filterDictionary {
public static void main(String argv[]) throws IOException{
		
		HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
		HashMap<String, String> indexedDict = new HashMap<String, String>();
		ArrayList<Integer> countList = new ArrayList<Integer>();
		ArrayList<String> classesToDelete = new ArrayList<String>();
		String[] tags = {":", ".", ",", "\"", "'", "''", "\"\"", "-LRB-", "#", "`", "``", "CD"};
		
		for(int m=0; m<tags.length; m++)classesToDelete.add(tags[m]);
		
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data/";
		File writefile = new File(root + "filtered_dictionary_100.txt");
     	if (!writefile.exists()) {
    		writefile.createNewFile();
    	}
    	FileWriter fw = new FileWriter(writefile.getAbsoluteFile());
    	BufferedWriter bw = new BufferedWriter(fw);
    	
    	File  f = new File(root + "dictionary.txt");
	    FileInputStream fis = new FileInputStream(f); 
	    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	    
	    String line = "";
	    int index = 1;
	    
	    while((line = reader.readLine())!=null)
	    {
	    	String[] parts = line.split("\t");
	    	String word = parts[0];
	    	int count = Integer.parseInt(parts[2]);
	    	
	    	if(word.contains("http://") || word.contains("https://") || word.contains("www.") || word.contains(".html"))continue;
	    	if(word.startsWith("r/") || word.startsWith("/r/"))continue;
	    	String[] wordParts = word.split("_");
	    	
	    	String posTag = wordParts[wordParts.length-1];
	    	
	    	if(classesToDelete.contains(posTag))continue;
	    	String currWord = wordParts[0];
	    	for(int k=1; k<wordParts.length-1; k++)
	    	{
	    		currWord+= "_" + wordParts[k];
	    	}
	    	//if(wordParts.length>2)System.out.println(word);
	    	//System.out.println(currWord);
	    	String pattern= ".*[A-Za-z0-9\\$].*";
	    	if(!currWord.matches(pattern))
	    	{
	    		System.out.println(currWord);
	    		continue;
	    	}
	    	dictionary.put(word, count);
	    	countList.add(count);
	    	//bw.write(word + "\t" + index + "\n");
	    	//index++;
	    }
	    
	    
	    Collections.sort(countList);
	    Collections.reverse(countList);
	    //System.out.println(countList.get(1000));
	    int threshold = 693;
	    
	    //System.out.println(threshold);
	    
	    Iterator iter = dictionary.entrySet().iterator();
	    
	    while(iter.hasNext() && index <= 1000)
	    {
	    	Map.Entry e = (Map.Entry) iter.next();
	    	String word = e.getKey().toString();
	    	int count = Integer.parseInt(e.getValue().toString());
	    	
	    	if(count<threshold)continue;
	    	bw.write(word + "\t" + index + "\n");
	    	index++;
	    }
	    
	    bw.write("URL_URL" + "\t" + index + "\n");
	    bw.write("SUBR_SUBR" + "\t" + (index+1) + "\n");
	    bw.write("PUNC_PUNC" + "\t" + (index+2) + "\n");
	    bw.write("NMBR_NMBR" + "\t" + (index+3));
	    bw.close();
	} 
}

