import java.io.IOException;
import java.util.*;

public class collectBasicInfo {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data_backup/";
		
		fileIO fr = new fileIO(root + "risk_golden_truth.txt", "r");
		
		String line = "";
		
		ArrayList<String> posUsers = new ArrayList<String>();
		ArrayList<String> negUsers = new ArrayList<String>();
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split(" ");
			if(parts[1].equals("0"))negUsers.add(parts[0]);
			else if(parts[1].equals("1"))posUsers.add(parts[0]);
		}
		
		System.out.println(posUsers.size() + "," + negUsers.size());
		
		fr = new fileIO(root + "engagementFeatures.txt", "r");
		
		line = "";
		
		ArrayList<Double> posActTime = new ArrayList<Double>();
		ArrayList<Double> posWaitTime = new ArrayList<Double>();
		ArrayList<Double> negActTime = new ArrayList<Double>();
		ArrayList<Double> negWaitTime = new ArrayList<Double>();
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split(":");
			
			String userId = parts[0];
			
			if(posUsers.contains(userId))
			{
				posActTime.add(Double.parseDouble(parts[1]));
				
				posWaitTime.add(Double.parseDouble(parts[9]));
			}
			
			else if(negUsers.contains(userId))
			{
				negActTime.add(Double.parseDouble(parts[1]));
				
				negWaitTime.add(Double.parseDouble(parts[9]));	
			}
			
			
		}
		
		fr.close();
		
		double actTime = 0.0;
		double waitTime = 0.0;
		
		for(int i =0; i<posActTime.size(); i++)
		{
			actTime += posActTime.get(i);
			waitTime += posWaitTime.get(i);
		}
		
		System.out.println("Positive users\n----------------\n" + (actTime/posActTime.size()) + "," + (waitTime/posWaitTime.size()));
		
		actTime = 0.0;
		waitTime = 0.0;
		
		for(int i =0; i<negActTime.size(); i++)
		{
			actTime += negActTime.get(i);
			waitTime += negWaitTime.get(i);
		}
		
		System.out.println("Negative users\n----------------\n" + (actTime/negActTime.size()) + "," + (waitTime/negWaitTime.size()));
		
		
	}
}
