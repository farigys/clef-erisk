import java.util.*;
import java.io.File;
import java.io.IOException;

//distribute postwise feature vectors into chunks for training data

public class chunk_separator {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/features/anonymous_chunks_feature_vectors/";
		
		String rootD = root + "regexOutput/";
		
		for(int i = 1; i<=10; i++)
		{
			File f = new File(rootD + "chunk " + i);
			f.mkdir();
		}
		
		File folder = new File(rootD);
		
		File[] listOfFiles = folder.listFiles();
		
		for(int i=0; i<listOfFiles.length; i++)
		{
			if(listOfFiles[i].isDirectory())continue;
			String filename = listOfFiles[i].getAbsolutePath();
			//System.out.println(filename);
			fileIO fr = new fileIO(filename, "r");
			String line = "";
			while((line = fr.read())!=null)
			{
				String userId = line.split(":")[0].split("_")[0] + "_" + line.split(":")[0].split("_")[1];
				String chunk = line.split(":")[0].split("_")[2];
				fileIO fw = new fileIO(rootD + "chunk " + chunk + "/" + userId + "_" + chunk + ".txt", "w+");
				fw.write(line + "\n");
				fw.close();
			}
			
		}
		
	}
	
}
