import java.io.IOException;
import java.util.*;

//creates liblinear feature vectors for regex features

public class regexLiblinear {
	public static void main(String[] args) throws IOException
	{
		HashMap<String, Double> postCount = new HashMap<String, Double>();
		HashMap<String, String> classMap = new HashMap<String, String>();
		String root = "/home/farig/Desktop/features/";
		
		fileIO ftrain = new fileIO(root + "trainList.txt", "r");
		HashSet<String> trainList = new HashSet<String>();
		String ln = "";
		
		while((ln = ftrain.read())!=null)
		{
			trainList.add(ln);
		}
		ftrain.close();
		
		ftrain = new fileIO(root + "valList.txt", "r");
		HashSet<String> valList = new HashSet<String>();
		ln = "";
		
		while((ln = ftrain.read())!=null)
		{
			valList.add(ln);
		}
		ftrain.close();
		
		ftrain = new fileIO(root + "firstTestUserList.txt", "r");
		HashSet<String> List100 = new HashSet<String>();
		ln = "";
		
		while((ln = ftrain.read())!=null)
		{
			List100.add(ln.split(" ")[0]);
		}
		ftrain.close();
		
		ftrain = new fileIO(root + "testList.txt", "r");
		HashSet<String> testList = new HashSet<String>();
		ln = "";
		
		while((ln = ftrain.read())!=null)
		{
			testList.add(ln.split(" ")[0]);
		}
		ftrain.close();
		
		
			
		fileIO f1 = new fileIO(root + "writings-per-subject-all-train.txt", "r");
		
		String line = "";
		
		while((line = f1.read())!=null)
		{
			String userId = line.split("\t\t")[0];
			double postc = Double.parseDouble(line.split("\t\t")[1]);
			postCount.put(userId, postc);
		}
		f1.close();
		
		//comment this part out when not dealing with test set
		
//		f1 = new fileIO(root + "testPostCount.txt", "r");
//		
//		line = "";
//		
//		postCount.clear();
//		
//		while((line = f1.read())!=null)
//		{
//			String userId = line.split(",")[0];
//			double postc = Double.parseDouble(line.split(",")[1]);
//			postCount.put(userId, postc);
//		}
//		f1.close();
		
		
		
//		fileIO fwtest = new fileIO(root + "regexFeaturesLibSVMTest.txt", "w");
//		
//		fileIO fr = new fileIO(root + "regexFeaturesTest.txt", "r");
//		
//		line = "";
//		
//		while((line = fr.read())!=null)
//		{
//			String[] parts = line.split(":");
//			String userId = parts[0];
//			double postc = postCount.get(userId);
//			String currClass = "?";
//			
//			fwtest.write(currClass);
//			
//			for(int c=1; c<parts.length; c++)
//			{
//				fwtest.write(" " + c + ":" + Double.parseDouble(parts[c])/postc);
//			}
//			fwtest.write("\n");
//		}
//		fwtest.close();
		
///////////////////////////////////////////////////////
		
		//uncomment everything below this when not handling test data
		f1 = new fileIO(root + "risk_golden_truth.txt", "r");
		
		line = "";
		
		while((line = f1.read())!=null)
		{
			String userId = line.split(" ")[0];
			String currClass = line.split(" ")[1];
			classMap.put(userId, currClass);
		}
		f1.close();
		
		fileIO fwtr = new fileIO(root + "regexFeaturesLibSVMTrain.txt", "w");
		fileIO fwval = new fileIO(root + "regexFeaturesLibSVMVal.txt", "w");
		fileIO fw100 = new fileIO(root + "regexFeaturesLibSVM100.txt", "w");
		
		fileIO fr = new fileIO(root + "regexFeatures.txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split(":");
			String userId = parts[0];
			double postc = postCount.get(userId);
			String currClass = classMap.get(userId);
			
			if(trainList.contains(userId))
			{
				fwtr.write(currClass);
				
				for(int c=1; c<parts.length; c++)
				{
					fwtr.write(" " + c + ":" + Double.parseDouble(parts[c])/postc);
				}
				fwtr.write("\n");
			}
			
			if(valList.contains(userId))
			{
				fwval.write(currClass);
				
				for(int c=1; c<parts.length; c++)
				{
					fwval.write(" " + c + ":" + Double.parseDouble(parts[c])/postc);
				}
				fwval.write("\n");
			}
			
			if(List100.contains(userId))
			{
				fw100.write(currClass);
				
				for(int c=1; c<parts.length; c++)
				{
					fw100.write(" " + c + ":" + Double.parseDouble(parts[c])/postc);
				}
				fw100.write("\n");
			}
			
			
		}
		fwtr.close();
		fwval.close();
		fw100.close();
	}
}
