import java.io.IOException;
import java.util.*;

public class create_postwise_decision_vector_clef_train {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/clef/Latency_metamap_ngram/";
			
		fileIO fr = new fileIO(root + "cui_ngram1_400.csv", "r");
		
		String l = fr.read();
		
		String[] parts = l.split(",");
		
		HashMap<String, ArrayList<Double>> vector = new HashMap<String, ArrayList<Double>>();
		
		for(int i=1; i<parts.length; i++)
		{
			vector.put(parts[i], new ArrayList<Double>());
		}
		
		while((l = fr.read())!=null)
		{
			String[] preds = l.split(",");
			
			//System.out.println(preds.length - 1);
			
			for(int i=1; i<preds.length; i++)
			{
				String username = parts[i];
				ArrayList<Double> tempList = vector.get(username);
				tempList.add(Double.parseDouble(preds[i]));
				vector.put(username, tempList);
			}
		}
		
		System.out.println(vector.size());
		
		//System.out.println(parts.length);
		
		for(int limit = 400; limit<2000; limit+=400)
		{
			String filename = "cui_ngram" + limit + "_" + (limit+400) + ".csv";
			
			//System.out.println(filename);
			
			fr = new fileIO(root + filename, "r");
			
			String line = fr.read();
			
//			if(line.equals(l))System.out.println("milse");
//			
//			//System.out.println(line);
//			
//			String[] temp = line.split(",");
//			
//			//System.out.println(temp.length);
//			
//			if(parts.length != temp.length)System.out.println("Genjam in size");
//			
//			for(int i=0; i<temp.length; i++)
//			{
//				if(!parts[i].equals(temp[i]))System.out.println("Genjam in username");
//			}
			
			while((line = fr.read())!=null)
			{
				String[] preds = line.split(",");
				
				//System.out.println(preds.length - 1);
				
				for(int i=1; i<preds.length; i++)
				{
					String username = parts[i];
					ArrayList<Double> tempList = vector.get(username);
					tempList.add(Double.parseDouble(preds[i]));
					vector.put(username, tempList);
				}
			}
		}
		
		Iterator it = vector.entrySet().iterator();
		
		fileIO fw = new fileIO(root + "complete_decision_vector.txt", "w");
		
		while(it.hasNext())
		{
			Map.Entry e = (Map.Entry)it.next();
			
			String username = e.getKey().toString();
			ArrayList<Double> predVals = (ArrayList<Double>)e.getValue();
			fw.write(username);
			for(int i=0; i<predVals.size(); i++)fw.write("," + predVals.get(i));
			fw.write("\n");
			
			//if(predVals.size()!=1999)System.out.println(predVals.size());
		}
		fw.close();
	}
}
