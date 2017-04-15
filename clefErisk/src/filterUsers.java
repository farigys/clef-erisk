import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.*;

public class filterUsers {
	public static void main(String[] args) throws IOException, ParseException {
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data/";
		
		File  f1 = new File(root + "risk_golden_truth.txt");
	    FileInputStream fis1 = new FileInputStream(f1); 
	    BufferedReader reader1 = new BufferedReader(new InputStreamReader(fis1));
		
	    HashMap<String, String> temp = new HashMap<String, String>();
	    
	    String line = "";
	    
	    while((line = reader1.readLine())!=null)
	    {
	    	String[] parts = line.split(" ");
	    	String userId = parts[0];
	    	String cat = parts[1];
	    	temp.put(userId,  cat);
	    }
	    
		File  f = new File(root + "engagementFeatures.txt");
	    FileInputStream fis = new FileInputStream(f); 
	    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	    
	    line = "";
	    
	    while((line = reader.readLine())!=null)
	    {
	    	String[] parts = line.split(":");
	    	String userId = parts[0];
	    	int totalPosts = Integer.parseInt(parts[2]);
	    	if(totalPosts<49)
	    		System.out.println(userId + " " + temp.get(userId));
	    	
	    }
	}
}
