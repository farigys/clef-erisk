import java.io.IOException;
import java.util.*;

public class outputFormatter {
	public static void main(String[] args) throws IOException
	{
		ArrayList<String> testUserList = new ArrayList<String>();
		
		String root = "/home/farig/Desktop/clef erisk results/chunk 10 results/individual/"; //change it to 3, 4, 5...
		
		int chunk = 10; //change it to 3, 4, 5...
		
		fileIO fr = new fileIO(root + "testPostCount.txt", "r");
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			String userId = line.split(",")[0];
			
			testUserList.add(userId);
		}
		
		HashMap<String, String> outputMap = new HashMap<String, String>();
		
		fr = new fileIO(root + "jointOutputTestGRU.txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			String userId = line.split(" ")[0];
			
			String output = line.split(" ")[1];
			
			outputMap.put(userId, output);
		}
		
		fileIO fw1 = new fileIO(root + "UArizonaC_" + chunk + "temp.txt", "w");
		fileIO fw2 = new fileIO(root + "UArizonaE_" + chunk + "temp.txt", "w");
		
		for(int i=0; i<testUserList.size(); i++)
		{
			fw1.write(testUserList.get(i) + "\t\t" + outputMap.get(testUserList.get(i)) + "\n");
			fw2.write(testUserList.get(i) + "\t\t0\n");
		}
		fw1.close();
		fw2.close();
		
		int index = 0;
		
		fr = new fileIO(root + "ensembleOutput.csv", "r");
		
		fileIO fw = new fileIO(root + "UArizonaD_" + chunk + "temp.txt", "w");
		
		line = fr.read();
		
		while((line = fr.read())!=null)
		{
			String output = line.split(",")[2].split(":")[1];
			fw.write(testUserList.get(index) + "\t\t" + output + "\n");
			index++;
		}
		fw.close();
		
		index = 0;
		
		fr = new fileIO(root + "regexOutput.csv", "r");
		
		fw = new fileIO(root + "UArizonaB_" + chunk + "temp.txt", "w");
		
		line = fr.read();
		
		while((line = fr.read())!=null)
		{
			String output = line.split(",")[2].split(":")[1];
			fw.write(testUserList.get(index) + "\t\t" + output + "\n");
			index++;
		}
		fw.close();
		
		index = 0;
		
		fr = new fileIO(root + "jointOutput", "r");
		
		fw = new fileIO(root + "UArizonaA_" + chunk + "temp.txt", "w");
		
		line = fr.read();
		
		while((line = fr.read())!=null)
		{
			String output = line.split(" ")[0];
			fw.write(testUserList.get(index) + "\t\t" + output + "\n");
			index++;
		}
		fw.close();
		
	}
}
