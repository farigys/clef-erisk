//create user level data from post level data

import java.io.File;
import java.io.IOException;


public class combineOutputs {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/libsvm-3.22/";
		
		//File file = new File(root + "libsvm_vectors/outputs/");
		
		fileIO fw = new fileIO(root + "libsvm_vectors/prob_outputs.csv", "w");
		
		fileIO fr = new fileIO(root + "libsvm_vectors/testUserList.txt", "r");
		
		String l = "";
		
		int i = 0;
		
		int depressed = 0;
		
		while((l = fr.read())!=null)
		{
			String filename = l;
			
			fileIO f = new fileIO(root + "libsvm_vectors/outputs/" + filename, "r");
			
			String line = f.read();
			
			fw.write(filename);
			
			int lineCount = 0;
			
			int flag = 0;
			
			while((line = f.read())!=null)
			{
				if(lineCount == 100)break;
				String[] parts = line.split(" ");
				String prob = parts[1];
				fw.write("," + prob);
				if(Double.parseDouble(prob)>0.9 && flag == 0)
				{
					flag = 1;
					if(lineCount<5)depressed++;
					System.out.println(filename + "," + (lineCount+1));
				}
				lineCount++;
				
			}
			//if(flag == 0)System.out.println(filename);
			fw.write("\n");
		}
		fw.close();
		
		//System.out.println(depressed);
	}
		
}
