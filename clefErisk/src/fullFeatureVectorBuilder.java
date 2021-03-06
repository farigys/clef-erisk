import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

//updated version of featureVectorBuilder.java

public class fullFeatureVectorBuilder  {
	public static void main(String[] args) throws IOException
	{
		HashMap<String, ArrayList<Double>> engMap = new HashMap<String, ArrayList<Double>>();
		HashMap<String, ArrayList<Double>> regexMap = new HashMap<String, ArrayList<Double>>();
		HashMap<String, String> classMap = new HashMap<String, String>();
		HashMap<String, Double> postMap = new HashMap<String, Double>();
		HashMap<String, Double> deprMap = new HashMap<String, Double>();
		ArrayList<String> trainList = new ArrayList<String>();
		ArrayList<String> valList = new ArrayList<String>();
		int engSize = 0;
		
		String root = "/home/farig/Desktop/features/";
		
		////////////reading engagement features
		fileIO f = new fileIO(root + "engagementFeatures.txt", "r");
		
		String line = "";
		
		while((line = f.read())!=null)
		{
			String[] parts = line.split(":");
	    	String userId = parts[0];
	    	ArrayList<Double> tempList = new ArrayList<Double>();
	    	for(int x=1; x<parts.length; x++)
	    	{
	    		tempList.add(Double.parseDouble(parts[x]));
	    	}
	    	engSize = tempList.size();
	    	engMap.put(userId, tempList);
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
		
		
		fileIO ftrain = new fileIO(root + "trainList.txt", "r");
		fileIO fval = new fileIO(root + "valList.txt", "r");
		
		line = "";
		
		while((line = ftrain.read())!=null)
		{
			trainList.add(line);
		}
		
		line = "";
		
		while((line = fval.read())!=null)
		{
			valList.add(line);
		}

		fileIO fr = new fileIO(root + "metamapFeatures.txt", "r");
		fileIO fr1 = new fileIO(root + "risk_golden_truth.txt", "r");
		fileIO fr3 = new fileIO(root + "regexFeatures.txt", "r");
		fileIO fr4 = new fileIO(root + "writings-per-subject-all-train.txt", "r");
		
		line = "";
		
		while((line = fr1.read())!=null)
		{
			classMap.put(line.split(" ")[0], line.split(" ")[1]);
		}
		
		line = "";
		
		while((line = fr4.read())!=null)
		{
			postMap.put(line.split("\t\t")[0], Double.parseDouble(line.split("\t\t")[1]));
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
		
		
		fileIO ft = new fileIO(root + "fullFeaturestrainVectorsWeka.arff", "w");
		fileIO fv = new fileIO(root + "fullFeaturesValVectorsWeka.arff", "w");
		fileIO fw = new fileIO(root + "fullFeaturesCompleteVectorsWeka.arff", "w");
		
		ft.write("@relation clef-erisk-metamap-regex\n\n");
		
		for(int k=0; k<404; k++)
		{
			ft.write("@attribute cui" + (k+1) + " real\n");
		}
		
		for(int k=0; k<110; k++)
		{
			ft.write("@attribute regex" + (k+1) + " real\n");
		}
		
		for(int k=0; k<engSize; k++)
		{
			ft.write("@attribute engagement" + (k+1) + " real\n");
		}
		
		ft.write("@attribute depression real\n@attribute class {0,1}\n\n@data\n");
		
		fv.write("@relation clef-erisk-metamap-regex\n\n");
		
		for(int k=0; k<404; k++)
		{
			fv.write("@attribute cui" + (k+1) + " real\n");
		}
		
		for(int k=0; k<110; k++)
		{
			fv.write("@attribute regex" + (k+1) + " real\n");
		}
		
		for(int k=0; k<engSize; k++)
		{
			fv.write("@attribute engagement" + (k+1) + " real\n");
		}
		
		fv.write("@attribute depression real\n@attribute class {0,1}\n\n@data\n");
		
		fw.write("@relation clef-erisk-metamap-regex\n\n");
		
		for(int k=0; k<404; k++)
		{
			fw.write("@attribute cui" + (k+1) + " real\n");
		}
		
		for(int k=0; k<110; k++)
		{
			fw.write("@attribute regex" + (k+1) + " real\n");
		}
		
		for(int k=0; k<engSize; k++)
		{
			fw.write("@attribute engagement" + (k+1) + " real\n");
		}
		
		fw.write("@attribute depression real\n@attribute class {0,1}\n\n@data\n");
		
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
			
			if(trainList.contains(userId))
			{
				for(int k=1; k<parts.length; k++)
				{
					ft.write((Double.parseDouble(parts[k])/(tempPostCount * 1.0)) + ",");
				}
				
				ArrayList<Double> temp = regexMap.get(userId);
				
				for(int k=0; k<temp.size(); k++)
				{
					ft.write(Double.toString(temp.get(k)/(tempPostCount * 1.0)) + ",");
				}
				
				ArrayList<Double> engage = engMap.get(userId);
				
				for(int k=0; k<engage.size(); k++)
				{
					ft.write(Double.toString(engage.get(k)) + ",");
				}
				
				ft.write((deprMap.get(userId)/(tempPostCount * 1.0)) + "," + classMap.get(userId) + "\n");
			}
				//ft.write(Integer.toString(mobdCount+clndCount) + "," + Integer.toString(mentionCount)
				//	+ "," + avgMetamapCount + "," + avgMentionCount + "," + classes.get(userId) + "\n");
			else
			{
				for(int k=1; k<parts.length; k++)
				{
					fv.write((Double.parseDouble(parts[k])/(tempPostCount * 1.0)) + ",");
				}
				ArrayList<Double> temp = regexMap.get(userId);
				
				for(int k=0; k<temp.size(); k++)
				{
					fv.write(Double.toString(temp.get(k)/(tempPostCount * 1.0)) + ",");
				}
				
				ArrayList<Double> engage = engMap.get(userId);
				
				for(int k=0; k<engage.size(); k++)
				{
					fv.write(Double.toString(engage.get(k)) + ",");
				}
				
				fv.write((deprMap.get(userId)/(tempPostCount * 1.0)) + "," + classMap.get(userId) + "\n");
			}
			
			for(int k=1; k<parts.length; k++)
			{
				fw.write((Double.parseDouble(parts[k])/(tempPostCount * 1.0)) + ",");
			}
			ArrayList<Double> temp = regexMap.get(userId);
			
			for(int k=0; k<temp.size(); k++)
			{
				fw.write(Double.toString(temp.get(k)/(tempPostCount * 1.0)) + ",");
			}
			
			ArrayList<Double> engage = engMap.get(userId);
			
			for(int k=0; k<engage.size(); k++)
			{
				fw.write(Double.toString(engage.get(k)) + ",");
			}
			
			fw.write((deprMap.get(userId)/(tempPostCount * 1.0)) + "," + classMap.get(userId) + "\n");
			
				//fv.write(Integer.toString(mobdCount+clndCount) + "," + Integer.toString(mentionCount)
					//	+ "," + avgMetamapCount + "," + avgMentionCount + "," + classes.get(userId) + "\n");
					
		}
		fw.close();
		ft.close();
		fv.close();
		
		
		
		
	}
}

