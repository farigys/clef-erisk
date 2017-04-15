import java.io.IOException;
import java.util.*;


public class metamapFeaturesInWeka {
	public static void main(String[] args) throws IOException
	{
		HashMap<String, Integer> postCount =  new HashMap<String, Integer>();
		HashMap<String, Integer> classes = new HashMap<String, Integer>();
		HashMap<String, Double> sentMap = new HashMap<String, Double>();
		HashMap<String, ArrayList<Double>> engMap = new HashMap<String, ArrayList<Double>>();
		
		String root = "/home/farig/Desktop/features/";
		fileIO f = new fileIO(root + "writings-per-subject-all-train.txt", "r");
		
		String line = "";
		
		while((line = f.read())!=null)
		{
			String[] parts = line.split("\t\t");
			//System.out.println(parts.length);
			String userId = parts[0];
			int count = Integer.parseInt(parts[1]);
			
			postCount.put(userId, count);
		}
		
		f.close();
		
		f = new fileIO(root + "risk_golden_truth.txt", "r");
		
		line = "";
		
		while((line = f.read())!=null)
		{
			String[] parts = line.split(" ");
			//System.out.println(parts.length);
			String userId = parts[0];
			int currClass = Integer.parseInt(parts[1]);
			
			classes.put(userId, currClass);
		}
		
		f.close();
		
		f = new fileIO(root + "sentimentFeatures.txt", "r");
		
		line = "";
		
		while((line = f.read())!=null)
		{
			String[] parts = line.split(":");
			//System.out.println(parts.length);
			String userId = parts[0];
			double currClass = Double.parseDouble(parts[1]);
			
			sentMap.put(userId, currClass);
		}
		
		f.close();
		
		f = new fileIO(root + "engagementFeatures.txt", "r");
		
		line = "";
		
		while((line = f.read())!=null)
		{
			String[] parts = line.split(":");
	    	String userId = parts[0];
	    	ArrayList<Double> tempList = new ArrayList<Double>();
	    	for(int x=1; x<parts.length; x++)
	    	{
	    		tempList.add(Double.parseDouble(parts[x]));
	    	}
	    	engMap.put(userId, tempList);
		}
		
		f.close();
		
		fileIO fw = new fileIO(root + "baselineWeka.arff", "w");
		
		f = new fileIO(root + "metamapFeatures.txt", "r");
		
		line = "";
		
		while((line = f.read())!=null)
		{
			//System.out.println(line);
			String[] parts = line.split(",");
			String userId = parts[0];
			int mobdCount = Integer.parseInt(parts[1]);
			int clndCount = Integer.parseInt(parts[2]);
			int mentionCount = Integer.parseInt(parts[3]);
			
			int tempPostCount = postCount.get(userId);
			
			double avgMetamapCount = (mobdCount + clndCount)/(tempPostCount * 1.0);
			double avgMentionCount = mentionCount/(tempPostCount * 1.0);
			
			ArrayList<Double> engage = engMap.get(userId);
			
			fw.write(Integer.toString(mobdCount+clndCount) + "," + Integer.toString(mentionCount)
					+ "," + avgMetamapCount + "," + avgMentionCount + "," + sentMap.get(userId)
					);
			for(int k=0; k<engage.size(); k++)
			{
				fw.write("," + engage.get(k));
			}
			fw.write("," + classes.get(userId) + "\n");
			
		}
		f.close();

		fw.close();
		
		
	}
}
