import java.io.IOException;
import java.util.*;

public class get_post_count_to_decide {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/clef/Latency_metamap_ngram/";
		
		HashMap<String, Integer> totalPostCount = new HashMap<String, Integer>();
		
		fileIO fr = new fileIO(root + "writings-per-subject-all-train.txt", "r");
		String line = "";
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split("\t\t");
			String username = parts[0];
			int postC = Integer.parseInt(parts[1]);
			totalPostCount.put(username, postC);
		}
		
		int[] window = {0,17,73};
		
		for(int w : window)
		{

			HashMap<String, Integer> postCount = new HashMap<String, Integer>();
			ArrayList<String> users = new ArrayList<String>();
			
			fr = new fileIO(root + "post_count_for_depressed_users_" + w + ".txt", "r");
			line = "";
			
			while((line = fr.read())!=null)
			{
				String[] parts = line.split(",");
				String username = parts[0];
				int postC = Integer.parseInt(parts[1]);
				String cat = parts[2];
				//if(cat.equals("1"))
				users.add(username);
				postCount.put(username, postC);
			}
			
			
		
			fileIO fw = new fileIO(root + "post_count_for_depressed_users_with_window_" + w + ".txt", "w");
			
			for(String username : users)
			{
				int predictCount = postCount.get(username) + w;
				int actualCount = totalPostCount.get(username);
				
				//if(actualCount<postCount.get(username))System.out.println("Genjam");
				
				fw.write(username + "," + Math.min(predictCount,actualCount) + "," +
				(actualCount - Math.min(predictCount,actualCount)) + "," + actualCount + "\n");
			}
			
			fw.close();
		}
		
		
	}
}
