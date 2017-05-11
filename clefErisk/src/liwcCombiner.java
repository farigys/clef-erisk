import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

//joins chunk based user level LIWC feature vectors in one file

public class liwcCombiner {
	public static void main(String[] args) throws IOException, ParseException {
		DecimalFormat four = new DecimalFormat("#0.0000");
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data/negative_examples_anonymous_chunks/";
		
		HashMap<String, int[]> sentMap = new HashMap<String, int[]>();
		
		for(int chunk=1; chunk<=10; chunk++)
    	{
    		String currChunk = "chunk " + Integer.toString(chunk);
    		String rootDir = root + currChunk + "/";
			
			File folder = new File(rootDir);
			
			File[] listOfFiles = folder.listFiles();
			
			File file = new File(rootDir + "/PsyLingCount.csv");
			FileInputStream fis = new FileInputStream(file); 
		    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		    
		    String line = "";
		    
		    while((line=reader.readLine())!=null)
		    {
		    	//System.out.println(line);
		    	String[] parts = line.split(":");
		    	String userId = parts[0];
		    	int[] tempSent = new int[69];
		    	
		    	for(int x=2;x<parts.length;x++)
		    	{
		    		tempSent[x-2] = (int)Double.parseDouble(parts[x]);
		    	}
		    	
		    	if(sentMap.containsKey(userId))
		    	{
		    		int[] tempSent1 = sentMap.get(userId);
		    		for(int m=0; m<tempSent1.length; m++)
		    		{
		    			tempSent1[m]+=tempSent[m];
		    		}
		    		sentMap.put(userId, tempSent1);
		    	}
		    	
		    	else
		    	{
		    		sentMap.put(userId, tempSent);
		    	}
		    }
    	}
		
		root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data/positive_examples_anonymous_chunks/";
		
		for(int chunk=1; chunk<=10; chunk++)
    	{
    		String currChunk = "chunk " + Integer.toString(chunk);
    		String rootDir = root + currChunk + "/";
			
			File folder = new File(rootDir);
			
			File[] listOfFiles = folder.listFiles();
			
			File file = new File(rootDir + "/PsyLingCount.csv");
			FileInputStream fis = new FileInputStream(file); 
		    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		    
		    String line = "";
		    
		    while((line=reader.readLine())!=null)
		    {
		    	String[] parts = line.split(":");
		    	String userId = parts[0];
		    	int[] tempSent = new int[69];
		    	
		    	for(int x=2;x<parts.length;x++)
		    	{
		    		tempSent[x-2] = (int)Double.parseDouble(parts[x]);
		    	}
		    	
		    	if(sentMap.containsKey(userId))
		    	{
		    		int[] tempSent1 = sentMap.get(userId);
		    		for(int m=0; m<tempSent1.length; m++)
		    		{
		    			tempSent1[m]+=tempSent[m];
		    		}
		    		sentMap.put(userId, tempSent1);
		    	}
		    	
		    	else
		    	{
		    		sentMap.put(userId, tempSent);
		    	}
		    }
    	}
		
		File file = new File(root + "/PsyLingCount.csv");//PsychoLinguistic category cache
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		Iterator it = sentMap.entrySet().iterator();
		
		while(it.hasNext())
		{
			Map.Entry e = (Map.Entry)it.next();
			String userId = e.getKey().toString();
			int[] sentVals = (int[])e.getValue();
			bw.write(userId);
			for(int m=0; m<sentVals.length; m++)
			{
				bw.write(":" + Integer.toString(sentVals[m]));
			}
			bw.write("\n");
		}
		bw.close();
		//System.out.println(sentMap.size());
		
	}
}
