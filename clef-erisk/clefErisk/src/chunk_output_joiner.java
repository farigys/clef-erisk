import java.io.IOException;
import java.util.*;

//creates final submission file 

public class chunk_output_joiner {
	public static void main(String[] args) throws IOException
	{		
		int chunk = 10; //change it to 3, 4, 5...
		
		int prevChunk = chunk - 1;
		
		String root = "/home/farig/Desktop/clef erisk results/chunk " + chunk + " results/individual/"; //change it to 3, 4, 5...
		
		String prevDir = "/home/farig/Desktop/clef erisk results/chunk " + prevChunk + " results/individual/";
		
		HashMap<String, String> prevPredictionA = fileRead(prevDir + "UArizonaA_" + prevChunk + ".txt");
		HashMap<String, String> prevPredictionB = fileRead(prevDir + "UArizonaB_" + prevChunk + ".txt");
		HashMap<String, String> prevPredictionC = fileRead(prevDir + "UArizonaC_" + prevChunk + ".txt");
		HashMap<String, String> prevPredictionD = fileRead(prevDir + "UArizonaD_" + prevChunk + ".txt");
		HashMap<String, String> prevPredictionE = fileRead(prevDir + "UArizonaE_" + prevChunk + ".txt");
		
		HashMap<String, String> currPredictionA = fileRead(root + "UArizonaA_" + chunk + "temp.txt");
		HashMap<String, String> currPredictionB = fileRead(root + "UArizonaB_" + chunk + "temp.txt");
		HashMap<String, String> currPredictionC = fileRead(root + "UArizonaC_" + chunk + "temp.txt");
		HashMap<String, String> currPredictionD = fileRead(root + "UArizonaD_" + chunk + "temp.txt");
		
		fileIO f = new fileIO(root + "testPostCount.txt", "r");
		
		ArrayList<String> userList = new ArrayList<String>();
		
		String line = "";
		
		while((line = f.read())!=null)
		{
			userList.add(line.split(",")[0]);
		}
		
		int A0to1 = 0, A1to0 = 0, B0to1 = 0, B1to0 = 0, C0to1 = 0, C1to0 = 0, D0to1 = 0, D1to0 = 0;
		int E0to1 = 0, E1to0 = 0;
		
		//////////////model A//////////////////////
		System.out.println("--------model A-----------");
		fileIO fw = new fileIO(root + "UArizonaA_" + chunk + ".txt", "w");
		
		for(int i=0; i<userList.size(); i++)
		{
			String user = userList.get(i);
			
			String prevPred = prevPredictionA.get(user);
			
			String currPred = currPredictionC.get(user);
			
			if(prevPred.equals("1"))
			{
				if(currPred.equals("0"))
					A1to0++;
					//System.out.println("changed from 1->0 for " + user);
				fw.write(user + "\t\t" + prevPred + "\n");
			}
			else
			{
				if(currPred.equals("1"))
					A0to1++;
					//System.out.println("changed from 0->1 for " + user);
				fw.write(user + "\t\t" + currPred + "\n"); 
			}
			
		}
		fw.close();
		/////////////////////////////////////////////////
		//////////////model B//////////////////////
		System.out.println("--------model B-----------");
			fw = new fileIO(root + "UArizonaB_" + chunk + ".txt", "w");
			
			for(int i=0; i<userList.size(); i++)
			{
				String user = userList.get(i);
				
				String prevPred = prevPredictionB.get(user);
				
				String currPred = currPredictionB.get(user);
				
				if(prevPred.equals("1"))
				{
					if(currPred.equals("0"))
						B1to0++;
						//System.out.println("changed from 1->0 for " + user);
					fw.write(user + "\t\t" + prevPred + "\n");
				}
				else
				{
					if(currPred.equals("1"))
						B0to1++;
						//System.out.println("changed from 0->1 for " + user);
					fw.write(user + "\t\t" + currPred + "\n"); 
				}
				
			}
			fw.close();
			/////////////////////////////////////////////////
			//////////////model C//////////////////////
			System.out.println("--------model C-----------");
			fw = new fileIO(root + "UArizonaC_" + chunk + ".txt", "w");
			
			for(int i=0; i<userList.size(); i++)
			{
				String user = userList.get(i);
				
				String prevPred = prevPredictionC.get(user);
				
				String currPred = currPredictionC.get(user);
				
				if(prevPred.equals("1"))
				{
					if(currPred.equals("0"))
						C1to0++;
						//System.out.println("changed from 1->0 for " + user);
					fw.write(user + "\t\t" + prevPred + "\n");
				}
				else
				{
					if(currPred.equals("1"))
						C0to1++;
						//System.out.println("changed from 0->1 for " + user);
					fw.write(user + "\t\t" + currPred + "\n"); 
				}
				
			}
			fw.close();
			/////////////////////////////////////////////////
			//////////////model D//////////////////////
			System.out.println("--------model D-----------");
			fw = new fileIO(root + "UArizonaD_" + chunk + ".txt", "w");
			
			for(int i=0; i<userList.size(); i++)
			{
				String user = userList.get(i);
				
				String prevPred = prevPredictionD.get(user);
				
				String currPred = currPredictionD.get(user);
				
				if(prevPred.equals("1"))
				{
					if(currPred.equals("0"))
						D1to0++;
						//System.out.println("changed from 1->0 for " + user);
					fw.write(user + "\t\t" + prevPred + "\n");
				}
				else
				{
					if(currPred.equals("1"))
						D0to1++;
						//System.out.println("changed from 0->1 for " + user);
					fw.write(user + "\t\t" + currPred + "\n"); 
				}
				
			}
			fw.close();
			/////////////////////////////////////////////////
			//////////////model E//////////////////////
			System.out.println("--------model E-----------");
			fw = new fileIO(root + "UArizonaE_" + chunk + ".txt", "w");
			
			for(int i=0; i<userList.size(); i++)
			{
				String user = userList.get(i);
				
				String prevPred = prevPredictionE.get(user);
				
				String currPred = currPredictionB.get(user);
				
				if(prevPred.equals("1"))
				{
					if(currPred.equals("0"))
						E1to0++;
						//System.out.println("changed from 1->0 for " + user);
					fw.write(user + "\t\t" + prevPred + "\n");
				}
				else
				{
					if(currPred.equals("1"))
						E0to1++;
						//System.out.println("changed from 0->1 for " + user);
					fw.write(user + "\t\t" + currPred + "\n"); 
				}
				
			}
			fw.close();
			/////////////////////////////////////////////////
			
		
		System.out.println("model A: 1->0: " + A1to0 + ", 0->1: " + A0to1);
		System.out.println("model B: 1->0: " + B1to0 + ", 0->1: " + B0to1);
		System.out.println("model C: 1->0: " + C1to0 + ", 0->1: " + C0to1);
		System.out.println("model D: 1->0: " + D1to0 + ", 0->1: " + D0to1);
		System.out.println("model E: 1->0: " + E1to0 + ", 0->1: " + E0to1);
		
	}
	
	static HashMap<String, String> fileRead(String filename) throws IOException
	{
		HashMap<String, String> predictionMap = new HashMap<String, String>();
		
		fileIO fr = new fileIO(filename, "r");
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			predictionMap.put(line.split("\t\t")[0], line.split("\t\t")[1]);
		}
		
		return predictionMap;
	}
}
