import java.io.IOException;
import java.util.*;

//divide the userIds in two classes

public class divide_risk_golden_truth {
	public static void main(String args[]) throws IOException
	{
		HashMap<String, String> classMap = new HashMap<String, String>();
		HashMap<String, String> countMap = new HashMap<String, String>();
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data_backup/";
		fileIO f1 = new fileIO(root + "risk_golden_truth.txt", "r");
		fileIO f2 = new fileIO(root + "scripts evaluation/writings-per-subject-all-train.txt", "r");
		
		String line = "";
		
		while((line = f1.read())!=null)
		{
			String[] parts = line.split(" ");
			classMap.put(parts[0], parts[1]);
		}
		f1.close();
		
		while((line = f2.read())!=null)
		{
			String[] parts = line.split("\t\t");
			countMap.put(parts[0], parts[1]);
		}
		f2.close();
		
		fileIO fw1 = new fileIO(root + "risk_golden_truth_positive.txt", "w");
		fileIO fw2 = new fileIO(root + "risk_golden_truth_negative.txt", "w");
		
		Iterator it = classMap.entrySet().iterator();
		
		while(it.hasNext())
		{
			Map.Entry e = (Map.Entry) it.next();
			String userId = e.getKey().toString();
			String currClass = e.getValue().toString();
			
			if(currClass.equals("1"))fw1.write(userId + "," + countMap.get(userId) + "\n");
			else fw2.write(userId + "," + countMap.get(userId) + "\n");
		}
		fw1.close();
		fw2.close();
	}
}
