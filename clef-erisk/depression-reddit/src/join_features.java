import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class join_features {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data/";
		
		String file1 = "embeddingFeatures_unigramFeatures_bigramFeatures";//first file to join
		String file2 = "metamapFeatures";//second file to join
		
		String sep1 = ","; //first file's seperator
		String sep2 = ","; //second file's seperator
		
		HashMap<String, String> classMap = new HashMap<String, String>();
		
		fileIO fr = new fileIO(root + "risk_golden_truth.txt", "r");
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split(" ");
			classMap.put(parts[0], parts[1]);
		}
		
		HashMap<String, String> file1Map = new HashMap<String, String>();
		HashMap<String, String> file2Map = new HashMap<String, String>();
		
		fr = new fileIO(root + file1 + ".txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split(sep1);
			String username = parts[0];
			String temp = "";
			for(int i=1; i<parts.length - 1; i++)
			{
				temp += parts[i] + ",";
			}
			temp += parts[parts.length - 1];
			file1Map.put(username, temp);
		}
		
		fr = new fileIO(root + file2 + ".txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split(sep2);
			String username = parts[0];
			String temp = "";
			for(int i=1; i<parts.length - 1; i++)
			{
				temp += parts[i] + ",";
			}
			temp += parts[parts.length - 1];
			file2Map.put(username, temp);
		}
		
		fileIO fw = new fileIO(root + file1 + "_" + file2 + ".txt", "w");
		
		Iterator it = file1Map.entrySet().iterator();
		
		while(it.hasNext())
		{
			Map.Entry e = (Map.Entry)it.next();
			String username = e.getKey().toString();
			String join1 = e.getValue().toString();
			String join2 = file2Map.get(username);
			fw.write(username + "," + join1 + "," + join2 + "\n");
		}
		fw.close();
		
	}
}
