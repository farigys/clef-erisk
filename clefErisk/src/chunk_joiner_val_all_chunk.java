import java.io.IOException;
import java.util.*;

//creates final decision file based on the predictions of each chunk by joining each chunk results

public class chunk_joiner_val_all_chunk {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Downloads/output/";
		
		HashMap<String, Integer> actualMap = new HashMap<String, Integer>();
		HashMap<String, Integer> predictedMap = new HashMap<String, Integer>();
		
		for(int chunk = 1; chunk < 10; chunk++)
		{
			fileIO fr = new fileIO(root + "chunk " + chunk + "/regex_metamap_output_" + chunk + ".txt","r");
            fileIO fw = new fileIO(root + "chunk " + chunk + "/UArizonaB_" + chunk + ".txt","w");
			String line = "";
			
			while((line = fr.read())!=null)
			{
				if(line.equals(""))continue;
				String[] parts = line.split(" ");
				String userId = parts[0];
				int actual = Integer.parseInt(parts[1]);
				int predict = Integer.parseInt(parts[2]);
				
				actualMap.put(userId, actual);
				
				fw.write(userId + "\t\t" + predict + "\n");
				
				if(predictedMap.containsKey(userId))
				{
					if(predictedMap.get(userId) == 0)
					{
						predictedMap.put(userId, predict);
					}
				}
				else predictedMap.put(userId, predict);
			}
			fw.close();
		}
		
		fileIO fw = new fileIO(root + "risk_golden_truth.txt", "w");
		
		Iterator it = actualMap.entrySet().iterator();
		
		while(it.hasNext())
		{
			Map.Entry entry = (Map.Entry) it.next();
			
			fw.write(entry.getKey() + " " + entry.getValue() + "\n");
		}
		fw.close();
		
		fileIO fr = new fileIO(root + "chunk 10/regex_metamap_output_10.txt","r");
        fw = new fileIO(root + "chunk 10/UArizonaB_10.txt","w");
		String line = "";
		
		while((line = fr.read())!=null)
		{
			if(line.equals(""))continue;
			String[] parts = line.split(" ");
			String userId = parts[0];
			int actual = Integer.parseInt(parts[1]);
			int predict = Integer.parseInt(parts[2]);
			
			actualMap.put(userId, actual);
			
			//fw.write(userId + "\t\t" + predict + "\n");
			
			//if(predictedMap.containsKey(userId))
			{
				if(predictedMap.get(userId) == 0)
				{
					predictedMap.put(userId, 2);
				}
			}
			fw.write(userId + "\t\t" + predictedMap.get(userId) + "\n");
			//else predictedMap.put(userId, predict);
		
		}
		fw.close();
	}
}
