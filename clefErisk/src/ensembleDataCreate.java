import java.io.IOException;
import java.util.*;

//create ensemble data for both train and test data: follow the instruction in comments

public class ensembleDataCreate {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/clef erisk results/chunk 10 results/ensemble/"; //change this to 3,4,5.....
		
		
//		/////////////////////ensemble train starts///////////////////////////
//		ArrayList<String> trainUserList = new ArrayList<String>();
//		
//		HashMap<String, String> classMap = new HashMap<String, String>();
//		
//		fileIO fr = new fileIO(root + "risk_golden_truth.txt", "r");
//		
//		String line = "";
//		
//		while((line = fr.read())!=null)
//		{
//			String userId = line.split(" ")[0];
//			String currClass = line.split(" ")[1];
//			
//			trainUserList.add(userId);
//			classMap.put(userId, currClass);
//		}
//		
//		
//		////////////////////////////////////////////////////////////////
//		HashMap<String, Double> metamapLibsvmTrain = libsvmReader(root + "train/libsvm/metamapOutputToy", trainUserList);
//		HashMap<String, Double> regexLibsvmTrain = libsvmReader(root + "train/libsvm/regexOutputToy", trainUserList);
//		HashMap<String, Double> jointLibsvmTrain = libsvmReader(root + "train/libsvm/jointOutputToy", trainUserList);
//		///////////////////////////////////////////////////////////////
//		////////////////////////////////////////////////////////////////
//		HashMap<String, Double> metamapWekaTrain = wekaReader(root + "train/weka/metamapOutputToy.csv", trainUserList);
//		HashMap<String, Double> regexWekaTrain = wekaReader(root + "train/weka/regexOutputToy.csv", trainUserList);
//		HashMap<String, Double> jointWekaTrain = wekaReader(root + "train/weka/jointOutputToy.csv", trainUserList);
//		///////////////////////////////////////////////////////////////
//		////////////////////////////////////////////////////////////////
//		HashMap<String, Double> regexGruTrain = gruReader(root + "train/NN/regexOutputToyGRU.txt");
//		HashMap<String, Double> jointGruTrain = gruReader(root + "train/NN/jointOutputToyGRU.txt");
//		///////////////////////////////////////////////////////////////
//		
//		
//		fileIO fw = new fileIO(root + "ensembleTrain.arff", "w");
//		
//		System.out.println(jointGruTrain.size());
//		
//		for(int i=0; i<trainUserList.size(); i++)
//		{
//			String userId = trainUserList.get(i);
//			//System.out.println(userId);
//			fw.write(metamapLibsvmTrain.get(userId) + "," + regexLibsvmTrain.get(userId)
//					+ "," + jointLibsvmTrain.get(userId) + "," + metamapWekaTrain.get(userId)
//					+ "," + regexWekaTrain.get(userId) + "," + jointWekaTrain.get(userId) + ","
//					+ regexGruTrain.get(userId) + "," + jointGruTrain.get(userId) + "," + 
//					classMap.get(userId) + "\n");
//		}
//		fw.close();
//		////////////////ensemble train complete////////////////////////
		
		/////////////////////ensemble test starts///////////////////////////
		ArrayList<String> testUserList = new ArrayList<String>();
		
		fileIO fr = new fileIO(root + "testPostCount.txt", "r");
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			String userId = line.split(",")[0];
			
			testUserList.add(userId);
		}


		////////////////////////////////////////////////////////////////
		HashMap<String, Double> metamapLibsvmTest = libsvmReader(root + "test/libsvm/metamapOutput", testUserList);
		HashMap<String, Double> regexLibsvmTest = libsvmReader(root + "test/libsvm/regexOutput", testUserList);
		HashMap<String, Double> jointLibsvmTest = libsvmReader(root + "test/libsvm/jointOutput", testUserList);
		///////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////
		HashMap<String, Double> metamapWekaTest = wekaReader(root + "test/weka/metamapOutput.csv", testUserList);
		HashMap<String, Double> regexWekaTest = wekaReader(root + "test/weka/regexOutput.csv", testUserList);
		HashMap<String, Double> jointWekaTest = wekaReader(root + "test/weka/jointOutput.csv", testUserList);
		///////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////
		HashMap<String, Double> regexGruTest = gruReader(root + "test/NN/regexOutputTestGRU.txt");
		HashMap<String, Double> jointGruTest = gruReader(root + "test/NN/jointOutputTestGRU.txt");
		///////////////////////////////////////////////////////////////

		fileIO fw = new fileIO(root + "ensembleTest.arff", "w");
		
		System.out.println(jointGruTest.size());
		
		for(int i=0; i<testUserList.size(); i++)
		{
			String userId = testUserList.get(i);
			//System.out.println(userId);
			fw.write(metamapLibsvmTest.get(userId) + "," + regexLibsvmTest.get(userId)
					+ "," + jointLibsvmTest.get(userId) + "," + metamapWekaTest.get(userId)
					+ "," + regexWekaTest.get(userId) + "," + jointWekaTest.get(userId) + ","
					+ regexGruTest.get(userId) + "," + jointGruTest.get(userId) + "," + 
					"?\n");
		}
		fw.close();
		

		////////////////ensemble test complete////////////////////////
	}
	
	static HashMap<String, Double> libsvmReader(String filename, ArrayList<String> userList) throws IOException
	{
		int index = 0;
		
		HashMap <String, Double> libsvmPrediction = new HashMap<String, Double>();
		
		fileIO fr1 = new fileIO(filename, "r");
		
		String line = fr1.read();
		
		while((line = fr1.read())!=null)
		{
			libsvmPrediction.put(userList.get(index), Double.parseDouble(line.split(" ")[1]));
			index++;
		}
		return libsvmPrediction;
	}
	
	static HashMap<String, Double> wekaReader(String filename, ArrayList<String> userList) throws IOException
	{
		int index = 0;
		
		HashMap <String, Double> wekaPrediction = new HashMap<String, Double>();
		
		fileIO fr1 = new fileIO(filename, "r");
		
		String line = fr1.read();
		
		while((line = fr1.read())!=null)
		{
			String predictedVal = line.split(",")[2];
			String prediction = predictedVal.split(":")[1];
			//System.out.println(prediction);
			wekaPrediction.put(userList.get(index), Double.parseDouble(prediction));
			index++;
		}
		return wekaPrediction;
	}
	
	static HashMap<String, Double> gruReader(String filename) throws IOException
	{
		int index = 0;
		
		HashMap <String, Double> gruPrediction = new HashMap<String, Double>();
		
		fileIO fr1 = new fileIO(filename, "r");
		
		String line = "";
		
		while((line = fr1.read())!=null)
		{
			String[] parts = line.split(" ");
			String userId = parts[0];
			String prediction = parts[parts.length-1];
			
			gruPrediction.put(userId, Double.parseDouble(prediction));
			index++;
		}
		return gruPrediction;
	}
}
