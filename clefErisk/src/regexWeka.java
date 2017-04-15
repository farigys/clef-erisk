import java.io.IOException;
import java.util.*;

//create weka arff file from the user level feature vector

public class regexWeka {
	public static void main(String[] args) throws IOException
	{
		HashMap<String, String> classes = new HashMap<String, String>();
		HashMap<String, Integer> postCount =  new HashMap<String, Integer>();
		ArrayList<String> trainList = new ArrayList<String>();
		ArrayList<String> valList = new ArrayList<String>();
		
		String root = "/home/farig/Desktop/eRisk_test/chunk 10 analysis/"; //change this to 3, 4, 5.....
		
		//fileIO f = new fileIO(root + "writings-per-subject-all-train.txt", "r");
		fileIO f = new fileIO(root + "testPostCount.txt", "r");
		
		String line = "";
		
		while((line = f.read())!=null)
		{
			String[] parts = line.split(",");
			//System.out.println(parts.length);
			String userId = parts[0];
			int count = Integer.parseInt(parts[1]);
			
			postCount.put(userId, count);
		}
		
		f.close();
		
//		fileIO fr = new fileIO(root + "risk_golden_truth.txt", "r");
//		
//		line = "";
//		
//		while((line = fr.read())!=null)
//		{
//			classes.put(line.split(" ")[0], line.split(" ")[1]);
//		}
		
		fileIO fr = new fileIO(root + "regexFeaturesTest.txt", "r");
		fileIO fw = new fileIO(root + "regexFeaturesTestWeka.arff", "w");
		
		line = "";
		
		fw.write("@relation clef-erisk-regex\n\n");
		
		for(int k=0; k<110; k++)
		{
			fw.write("@attribute regex" + (k+1) + " real\n");
		}
		
		fw.write("@attribute class {0,1}\n\n@data\n");
		
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split(":");
			String userId = parts[0];
			//int mobdCount = Integer.parseInt(parts[1]);
			//int clndCount = Integer.parseInt(parts[2]);
			//int mentionCount = Integer.parseInt(parts[3]);
			
			int tempPostCount = postCount.get(userId);
			
			//double avgMetamapCount = (mobdCount + clndCount)/(tempPostCount * 1.0);
			//double avgMentionCount = mentionCount/(tempPostCount * 1.0);
			//System.out.println(parts.length);
			for(int k=1; k<parts.length; k++)
			{
				fw.write((Double.parseDouble(parts[k])/(tempPostCount * 1.0)) + ",");
			}
			fw.write("?\n");
			
			//fw.write(Integer.toString(mobdCount+clndCount) + "," + Integer.toString(mentionCount)
				//	+ "," + avgMetamapCount + "," + avgMentionCount + "," + classes.get(userId) + "\n");
			
		}
		fw.close();
		

	}
}
