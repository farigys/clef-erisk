import java.io.IOException;
import java.util.*;

//joins metamap and regex feature vectors at user level and creates arff files for the test data

public class joinMetamapAndRegexTest {
	public static void main(String[] args) throws IOException
	{
		HashMap<String, ArrayList<Double>> regexMap = new HashMap<String, ArrayList<Double>>();
		HashMap<String, String> classMap = new HashMap<String, String>();
		HashMap<String, Double> postMap = new HashMap<String, Double>();
		ArrayList<String> trainList = new ArrayList<String>();
		ArrayList<String> valList = new ArrayList<String>();
		
		String root = "/home/farig/Desktop/eRisk_test/chunk 10 analysis/"; //change this to 3, 4, 5...
		
//		fileIO ftrain = new fileIO(root + "trainList.txt", "r");
//		fileIO fval = new fileIO(root + "valList.txt", "r");
//		
//		String line = "";
//		
//		while((line = ftrain.read())!=null)
//		{
//			trainList.add(line);
//		}
//		
//		line = "";
//		
//		while((line = fval.read())!=null)
//		{
//			valList.add(line);
//		}

		fileIO fr = new fileIO(root + "metamapFeatures.txt", "r");
		fileIO fr3 = new fileIO(root + "regexFeaturesTest.txt", "r");
		fileIO fr4 = new fileIO(root + "testPostCount.txt", "r");
		
		String line = "";
		
		while((line = fr4.read())!=null)
		{
			postMap.put(line.split(",")[0], Double.parseDouble(line.split(",")[1]));
		}
				
		line = "";
		
		while((line = fr3.read())!=null)
		{
			String[] parts = line.split(":");
			String userId = parts[0];
			ArrayList<Double> temp = new ArrayList<Double>();
			for(int i=1; i<parts.length; i++)
			{
				temp.add(Double.parseDouble(parts[i]));
			}
			regexMap.put(userId, temp);
		}
		
		
		//fileIO ft = new fileIO(root + "metamapRegexFeaturestrainVectorsWeka.arff", "w");
		//fileIO fv = new fileIO(root + "metamapRegexFeaturesValVectorsWeka.arff", "w");
		fileIO fw = new fileIO(root + "metamapRegexFeaturesTestVectorsWeka.arff", "w");
		
		
		
		fw.write("@relation clef-erisk-metamap-regex\n\n");
		
		for(int k=0; k<404; k++)
		{
			fw.write("@attribute cui" + (k+1) + " real\n");
		}
		
		for(int k=0; k<110; k++)
		{
			fw.write("@attribute regex" + (k+1) + " real\n");
		}
		
		fw.write("@attribute class {0,1}\n\n@data\n");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split(",");
			//System.out.println(parts.length);
			String userId = parts[0];
			//int mobdCount = Integer.parseInt(parts[1]);
			//int clndCount = Integer.parseInt(parts[2]);
			//int mentionCount = Integer.parseInt(parts[3]);
			
			System.out.println(userId);
			
			double tempPostCount = postMap.get(userId);
			
			//double avgMetamapCount = (mobdCount + clndCount)/(tempPostCount * 1.0);
			//double avgMentionCount = mentionCount/(tempPostCount * 1.0);
			
			
			
			for(int k=1; k<parts.length; k++)
			{
				fw.write((Double.parseDouble(parts[k])/(tempPostCount * 1.0)) + ",");
			}
			ArrayList<Double> temp = regexMap.get(userId);
			
			for(int k=0; k<temp.size(); k++)
			{
				fw.write(Double.toString(temp.get(k)/(tempPostCount * 1.0)) + ",");
			}
			fw.write("?\n");
			
				//fv.write(Integer.toString(mobdCount+clndCount) + "," + Integer.toString(mentionCount)
					//	+ "," + avgMetamapCount + "," + avgMentionCount + "," + classes.get(userId) + "\n");
					
		}
		fw.close();
		
		
		
		
	}
}
