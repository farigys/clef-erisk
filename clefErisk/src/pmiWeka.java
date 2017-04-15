import java.util.*;
import java.io.IOException;

//creates weka feature vectors based on high PMI BOW

public class pmiWeka {
	public static void main(String[] args) throws IOException
	{
		int size = 100;
		
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data/";
		
		ArrayList<String> valList = new ArrayList<String>();
		
		HashMap<String, String> totalPostCount = new HashMap<String, String>();
		
		HashMap<String, String> classes = new HashMap<String, String>();
		
		fileIO fr = new fileIO(root + "valList.txt", "r");
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			valList.add(line);
		}
		
		fr = new fileIO(root + "risk_golden_truth.txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			classes.put(line.split(" ")[0], line.split(" ")[1]);
		}
		
		fr = new fileIO(root + "writings-per-subject-all-train.txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			totalPostCount.put(line.split("\t\t")[0], line.split("\t\t")[1]);
		}
		
		fileIO fw1 = new fileIO(root + "pmiUnigramsTrain.arff", "w");
		
		fw1.write("@relation clef-erisk-pmi\n\n");
		
		for(int i=1; i<=size; i++)fw1.write("@attribute unigram" + i + " real\n");
		//for(int i=1; i<=size; i++)fw1.write("@attribute bigram" + i + " real\n");
		
		fw1.write("@attribute class {0,1}\n\n@data\n");
		
		fileIO fw2 = new fileIO(root + "pmiUnigramsVal.arff", "w");
		
		fw2.write("@relation clef-erisk-pmi\n\n");
		
		for(int i=1; i<=size; i++)fw2.write("@attribute unigram" + i + " real\n");
		//for(int i=1; i<=size; i++)fw2.write("@attribute bigram" + i + " real\n");
		
		fw2.write("@attribute class {0,1}\n\n@data\n");
		
		fr = new fileIO(root + "pmiUnigramsFeatures.txt", "r");
		
		line = "";
		
		System.out.println(valList.size());
		

		//int temp = 0;
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split(":");
			String userId = parts[0];
			//System.out.println(userId);
			double totalPost = Double.parseDouble(totalPostCount.get(userId));
			String currClass = classes.get(userId);
			if(!valList.contains(userId))
			{
				//temp++;
				for(int i=1; i<parts.length; i++)
				{
					fw1.write(Double.parseDouble(parts[i]) + ",");
				}
				fw1.write(currClass + "\n");
			}
			else
			{
				for(int i=1; i<parts.length; i++)
				{
					fw2.write(Double.parseDouble(parts[i]) + ",");
				}
				fw2.write(currClass + "\n");
			}
			
		}
		
		fw1.close();
		fw2.close();
		
	}
}
