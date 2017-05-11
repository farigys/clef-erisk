import java.io.IOException;
import java.util.*;

//creates weka arff file of a user-level metamap feature vector for the training data, current
//version creates two files for train and val- see comments below for details

public class metamapFeaturesExtraction {
	public static void main(String[] args) throws IOException
	{
		HashMap<String, String> classes = new HashMap<String, String>();
		HashMap<String, Integer> postCount =  new HashMap<String, Integer>();
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
		
		fileIO fr = new fileIO(root + "risk_golden_truth.txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			classes.put(line.split(" ")[0], line.split(" ")[1]);
		}
		
		fr = new fileIO(root + "metamapFeatures.txt", "r");
		fileIO fw = new fileIO(root + "metamapFeaturesCompleteVectorsWeka.arff", "w");
		
		line = "";
		
		fw.write("@relation clef-erisk-metamap\n\n");
		
		for(int k=0; k<404; k++)
		{
			fw.write("@attribute cui" + (k+1) + " real\n");
		}
		
		fw.write("@attribute class {0,1}\n\n@data\n");
		
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split(",");
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
			fw.write(classes.get(userId) + "\n");
			
			//fw.write(Integer.toString(mobdCount+clndCount) + "," + Integer.toString(mentionCount)
				//	+ "," + avgMetamapCount + "," + avgMentionCount + "," + classes.get(userId) + "\n");
			
		}
		fw.close();
		
		//fr = new fileIO(root + "metamapFeaturesComplete.txt", "r");
		fr = new fileIO(root + "metamapFeatures.txt", "r");
		fileIO ft = new fileIO(root + "metamapFeaturestrainVectorsWeka.arff", "w");
		fileIO fv = new fileIO(root + "metamapFeaturesValVectorsWeka.arff", "w");
		
		ft.write("@relation clef-erisk-metamap\n\n");
		
		for(int k=0; k<404; k++)
		{
			ft.write("@attribute cui" + (k+1) + " real\n");
		}
		
		ft.write("@attribute class {0,1}\n\n@data\n");
		
		fv.write("@relation clef-erisk-metamap\n\n");
		
		for(int k=0; k<404; k++)
		{
			fv.write("@attribute cui" + (k+1) + " real\n");
		}
		
		fv.write("@attribute class {0,1}\n\n@data\n");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split(",");
			//System.out.println(parts.length);
			String userId = parts[0];
			//int mobdCount = Integer.parseInt(parts[1]);
			//int clndCount = Integer.parseInt(parts[2]);
			//int mentionCount = Integer.parseInt(parts[3]);
			
			//System.out.println(userId);
			
			int tempPostCount = postCount.get(userId);
			
			//double avgMetamapCount = (mobdCount + clndCount)/(tempPostCount * 1.0);
			//double avgMentionCount = mentionCount/(tempPostCount * 1.0);
			
			if(trainList.contains(userId))
			{
				for(int k=1; k<parts.length; k++)
				{
					ft.write((Double.parseDouble(parts[k])/(tempPostCount * 1.0)) + ",");
				}
				ft.write(classes.get(userId) + "\n");
			}
				//ft.write(Integer.toString(mobdCount+clndCount) + "," + Integer.toString(mentionCount)
				//	+ "," + avgMetamapCount + "," + avgMentionCount + "," + classes.get(userId) + "\n");
			else
			{
				System.out.println(userId);
				for(int k=1; k<parts.length; k++)
				{
					fv.write((Double.parseDouble(parts[k])/(tempPostCount * 1.0)) + ",");
				}
				fv.write(classes.get(userId) + "\n");
			}
				//fv.write(Integer.toString(mobdCount+clndCount) + "," + Integer.toString(mentionCount)
					//	+ "," + avgMetamapCount + "," + avgMentionCount + "," + classes.get(userId) + "\n");
					
		}
		fw.close();
		ft.close();
		fv.close();
		
		

	}
}
