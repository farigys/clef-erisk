import java.io.IOException;
import java.util.*;

//creates a side-by-side comparison file for all feature sets for validation data

public class createValOutputComparisonForModels {
	public static void main(String[] args) throws IOException
	{
		HashMap<String, String> actualClassMap = new HashMap<String, String>();
		HashMap<String, String> metamapLibsvmMap = new HashMap<String, String>(); 
		HashMap<String, String> regexLibsvmMap = new HashMap<String, String>();
		HashMap<String, String> metamapRegexLibsvmMap = new HashMap<String, String>();
		HashMap<String, String> metamapWekaMap = new HashMap<String, String>();
		HashMap<String, String> regexWekaMap = new HashMap<String, String>();
		HashMap<String, String> metamapRegexWekaMap = new HashMap<String, String>();
		HashMap<String, String> regexGruMap = new HashMap<String, String>();
		HashMap<String, String> metamapRegexGruMap = new HashMap<String, String>();
		
		String root = "/home/farig/Desktop/features/";
		
		ArrayList<String> valUserList = new ArrayList<String>();
		
		fileIO fr = new fileIO(root + "valListAsItAppears", "r");
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			valUserList.add(line);
		}
		
		fr = new fileIO(root + "metamapRegexFeaturesValVectorsWeka.txt","r");
		
		line = "";
		
		int i = 0;
		
		while((line = fr.read())!=null)
		{
			actualClassMap.put(valUserList.get(i), line.split(" ")[0]);
			
			i++;
		}
		
		fr = new fileIO(root + "metamapVectorOutput", "r");
		
		line = fr.read();
		
		i = 0;
		
		while((line = fr.read())!=null)
		{
			metamapLibsvmMap.put(valUserList.get(i), line.split(" ")[1]);
			
			i++;
		}
		
		fr = new fileIO(root + "regexOutput", "r");
		
		line = fr.read();
		
		i = 0;
		
		while((line = fr.read())!=null)
		{
			regexLibsvmMap.put(valUserList.get(i), line.split(" ")[1]);
			
			i++;
		}
		
		fr = new fileIO(root + "metamapRegexOutput", "r");
		
		line = fr.read();
		
		i = 0;
		
		while((line = fr.read())!=null)
		{
			metamapRegexLibsvmMap.put(valUserList.get(i), line.split(" ")[1]);
			
			i++;
		}
		
		fr = new fileIO(root + "val_result.txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			regexGruMap.put(line.split(" ")[0], line.split(" ")[3]);
		}
		
		fr = new fileIO(root + "regex&cui_feature.txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			metamapRegexGruMap.put(line.split(" ")[0], line.split(" ")[3]);
		}
		
		
		for(int j=0; j<valUserList.size(); j++)
		{
			String userId = valUserList.get(j);
			System.out.println(userId + "," + metamapLibsvmMap.get(userId) + "," + regexLibsvmMap.get(userId)
					+ "," + metamapRegexLibsvmMap.get(userId) + "," + regexGruMap.get(userId)
					+ "," + metamapRegexGruMap.get(userId) + "," + actualClassMap.get(userId));
		}
		
	}
}
