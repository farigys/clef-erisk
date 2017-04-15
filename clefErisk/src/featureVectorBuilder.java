import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.*;


public class featureVectorBuilder {
	public static void main(String[] args) throws IOException, ParseException {
		HashMap<String, ArrayList<Double>> engageF = new HashMap<String, ArrayList<Double>>();
		HashMap<String, ArrayList<Double>> metamapF = new HashMap<String, ArrayList<Double>>();
		HashMap<String, ArrayList<Double>> psylingF = new HashMap<String, ArrayList<Double>>();
		
		String root = "/home/farig/Desktop/features";
		
		File file = new File(root + "/basicFeatures");//PsychoLinguistic category cache
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		////////////reading engagement features
		file = new File(root + "/engagementFeatures.txt");
		FileInputStream fis = new FileInputStream(file); 
	    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
	    
	    String line = "";
	    
	    while((line=reader.readLine())!=null)
	    {
	    	String[] parts = line.split(":");
	    	String userId = parts[0];
	    	ArrayList<Double> tempList = new ArrayList<Double>();
	    	for(int x=1; x<parts.length; x++)
	    	{
	    		tempList.add(Double.parseDouble(parts[x]));
	    	}
	    	engageF.put(userId, tempList);
	    }
	    
	    ////////////reading engagement features
		file = new File(root + "/metamapFeatures.txt");
		fis = new FileInputStream(file); 
	    reader = new BufferedReader(new InputStreamReader(fis));
	    
	    line = "";
	    
	    while((line=reader.readLine())!=null)
	    {
	    	String[] parts = line.split(",");
	    	String userId = parts[0];
	    	ArrayList<Double> tempList = new ArrayList<Double>();
	    	for(int x=1; x<parts.length; x++)
	    	{
	    		tempList.add(Double.parseDouble(parts[x]));
	    	}
	    	metamapF.put(userId, tempList);
	    }
	    
	    ////////////reading engagement features
	  	file = new File(root + "/PsyLingCount.csv");
		fis = new FileInputStream(file); 
	    reader = new BufferedReader(new InputStreamReader(fis));
	    
	    line = "";
	    
	    while((line=reader.readLine())!=null)
	    {
	    	String[] parts = line.split(":");
	    	String userId = parts[0];
	    	ArrayList<Double> tempList = new ArrayList<Double>();
	    	for(int x=1; x<parts.length; x++)
	    	{
	    		tempList.add(Double.parseDouble(parts[x]));
	    	}
	    	psylingF.put(userId, tempList);
	    }
	    
	    file = new File(root + "/firstTestUserList.txt");
		fis = new FileInputStream(file); 
	    reader = new BufferedReader(new InputStreamReader(fis));
	    
	    line = "";
	    
	    while((line=reader.readLine())!=null)
	    {
	    	String[] parts = line.split(" ");
	    	String userId = parts[0];
	    	String cat = parts[1];
	    	
	    	bw.write(cat);
	    	
	    	ArrayList<Double> tempeng = engageF.get(userId);
	    	ArrayList<Double> tempmeta = metamapF.get(userId);
	    	ArrayList<Double> temppsy = psylingF.get(userId);
	    	
	    	int c = 1;
	    	
	    	for(int x=0; x<tempeng.size(); x++)
	    	{
	    		bw.write(" " + c + ":" + tempeng.get(x));
	    		c++;
	    	}
	    	for(int x=0; x<tempmeta.size(); x++)
	    	{
	    		bw.write(" " + c + ":" + tempmeta.get(x));
	    		c++;
	    	}
	    	for(int x=0; x<temppsy.size(); x++)
	    	{
	    		bw.write(" " + c + ":" + temppsy.get(x));
	    		c++;
	    	}
	    	bw.write("\n");
	    }
	}
}
