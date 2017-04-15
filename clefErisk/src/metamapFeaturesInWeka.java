import java.io.IOException;
import java.util.*;

//joins lots of old feature vectors with metamap feature vectors at a user level

public class metamapFeaturesInWeka {
	public static void main(String[] args) throws IOException
	{
		HashMap<String, Integer> postCount =  new HashMap<String, Integer>();
		HashMap<String, Integer> classes = new HashMap<String, Integer>();
		HashMap<String, Double> sentMap = new HashMap<String, Double>();
		HashMap<String, Double> deprMap = new HashMap<String, Double>();
		HashMap<String, ArrayList<Double>> engMap = new HashMap<String, ArrayList<Double>>();
		HashMap<String, ArrayList<Double>> regexMap = new HashMap<String, ArrayList<Double>>();
		ArrayList<String> trainList = new ArrayList<String>();
		ArrayList<String> valList = new ArrayList<String>();
		
		String root = "/home/farig/Desktop/features/";
		
		fileIO ftrain = new fileIO(root + "trainList.txt", "r");
		fileIO fval = new fileIO(root + "valList.txt", "r");
		
		String line = "";
		
		while((line = ftrain.read())!=null)
		{
			trainList.add(line);
		}
		
		line = "";
		while((line = fval.read())!=null)
		{
			valList.add(line);
		}
		
		fileIO f = new fileIO(root + "writings-per-subject-all-train.txt", "r");
		
		line = "";
		
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
		
		f = new fileIO(root + "depressionDiagnosisCount.txt", "r");
		
		line = "";
		
		while((line = f.read())!=null)
		{
			String[] parts = line.split(":");
			//System.out.println(parts.length);
			String userId = parts[0];
			double currClass = Double.parseDouble(parts[1]);
			
			deprMap.put(userId, currClass);
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
		
		fileIO regex = new fileIO(root + "regexFeatures.txt", "r");
		
		line = "";
		
		int regexLength = 0;
		
		while((line = regex.read())!=null)
		{
			String[] parts = line.split(":");
			regexLength = parts.length - 1;
	    	String userId = parts[0];
	    	ArrayList<Double> tempList = new ArrayList<Double>();
	    	for(int x=1; x<parts.length; x++)
	    	{
	    		tempList.add(Double.parseDouble(parts[x]));
	    	}
	    	regexMap.put(userId, tempList);
		}
		regex.close();
		
		fileIO fw = new fileIO(root + "baselineWekaVal.arff", "w");
		
		f = new fileIO(root + "metamapFeaturesComplete.txt", "r");
		
		line = "";
		
		for(int k=0; k<regexLength; k++)
		{
			fw.write("@attribute attribute" + (k+1) + " " + "real\n");
		}
		
		while((line = f.read())!=null)
		{
			//System.out.println(line);
			String[] parts = line.split(",");
			String userId = parts[0];
			//if(valList.contains(userId))continue;
			if(trainList.contains(userId))continue;
			int mobdCount = Integer.parseInt(parts[1]);
			int clndCount = Integer.parseInt(parts[2]);
			int mentionCount = Integer.parseInt(parts[3]);
			
			int tempPostCount = postCount.get(userId);
			
			double avgMetamapCount = (mobdCount + clndCount)/(tempPostCount * 1.0);
			double avgMentionCount = mentionCount/(tempPostCount * 1.0);
			
			ArrayList<Double> engage = engMap.get(userId);
			ArrayList<Double> regexList = regexMap.get(userId);
			
			fw.write(Integer.toString(mobdCount+clndCount) + "," + Integer.toString(mentionCount)
					+ "," + avgMetamapCount + "," + avgMentionCount);
			
			for(int k=0; k<engage.size(); k++)
			{
				fw.write("," + engage.get(k));
			}
			for(int k=0; k<regexList.size(); k++)
			{
				fw.write("," + (regexList.get(k))/(tempPostCount * 1.0));
			}
			fw.write("," + deprMap.get(userId) + "," + classes.get(userId) + "\n");
			
		}
		f.close();

		fw.close();
		
		
	}
}
