import java.io.IOException;
import java.util.*;


public class erde_calculator {
	public static void main(String[] args) throws IOException
	{

		String root = "/home/farig/Desktop/reddit_data/performance_measure/";
		
		ArrayList<String> userList = new ArrayList<String>();
		
		fileIO fr = new fileIO(root + "valList.txt", "r");
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			userList.add(line);
		}
		
		HashMap<String, String> trueResult = new HashMap<String, String>();
		
		fr = new fileIO(root + "risk_golden_truth.txt", "r");
		
		line = "";
		
		int totalPosCount = 0;
		
		while((line = fr.read())!=null)
		{
			String currUserId = line.split(" ")[0];
			String result = line.split(" ")[1];
			if(userList.contains(currUserId))
			{
				if(result.equals("1"))totalPosCount++;
				trueResult.put(currUserId, result);
			}
				
		}
		
		//System.out.println(trueResult.size());
		
		HashMap<String, Integer> writingpu = new HashMap<String, Integer>();
		
		fr = new fileIO(root + "writings-per-subject-all-train.txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			writingpu.put(line.split("\t\t")[0], Integer.parseInt(line.split("\t\t")[1]));
		}
		
		String[] features = {"metamap", "regex", "joint"};
		for(String feature:features)
		{
			String rootF = root + feature + "_features/output/";
			
			HashMap<String, Integer> predChunk = new HashMap<String, Integer>();
			
			for(int chunk=1; chunk<=10; chunk++)
			{
				fileIO f = new fileIO(rootF + feature + "_output_" + chunk, "r");
				
				String l = f.read();
				
				int userCount = 0;
				
				while((l = f.read())!=null)
				{
					String currClass = l.split(" ")[0];
					String currUser = userList.get(userCount);
					userCount++;
					if(predChunk.containsKey(currUser))continue;
					if(currClass.equals("1"))predChunk.put(currUser, chunk);
				}
			}
			
			//System.out.println(predChunk.size());
			
			fileIO fw = new fileIO(rootF + "erde.txt", "w");
			
			double total_erde = 0.0;
			
			for(String userId:userList)
			{
				int real, predicted, totalWriteCount, chunk = 0;
				double erde = 0;
				
				totalWriteCount = writingpu.get(userId);
				real = Integer.parseInt(trueResult.get(userId));
				if(predChunk.containsKey(userId))
				{
					predicted = 1;
					chunk = predChunk.get(userId);
				}
				else predicted = 0;
				
				if(predicted == 1 && real == 0)erde = (totalPosCount*1.0)/userList.size();
				else if(predicted == 0 && real == 1)erde = 1.0;
				else if(predicted == 1 && real == 1)
				{
					double risk_factor = ((totalWriteCount*1.0*chunk)/10) - 0.0; 
					erde = 1.0 - (1.0/(Math.exp(risk_factor)));
				}
				else if(predicted == 0 && real == 0)erde = 0;
				
				fw.write(userId + "," + erde + "\n");
				
				total_erde+=erde;
			}
			fw.close();
			
			System.out.println(feature + ":" + total_erde/(userList.size()));
		}
		
		
		
	}
}
