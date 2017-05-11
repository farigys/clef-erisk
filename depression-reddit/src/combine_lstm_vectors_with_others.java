import java.io.File;
import java.io.IOException;
import java.util.*;

public class combine_lstm_vectors_with_others {

	public static String root = "/home/farig/Desktop/reddit data/clef_text/vectors/";
	public static void main(String[] args) throws IOException
	{		
		combine("positive", "train");
		combine("positive", "val");
		combine("negative", "train");
		combine("negative", "val");
	}

	private static void combine(String cat, String cohort) throws IOException
	{
		String readD = root + "test_vectors/test_vectors_" + cat + "_" + cohort + "/";
		
		File file = new File(readD);
		
		File[] listFiles = file.listFiles();
		
		for(File f:listFiles)
		{
			ArrayList<String> lstmVectors = new ArrayList<String>();
			String filename = f.getName();
			if(!filename.startsWith("train"))continue;
			fileIO fr = new fileIO(readD + filename, "r");
			String line = "";
			while((line = fr.read())!=null)
			{
				lstmVectors.add(line);
			}
			
			ArrayList<String> regexVectors = new ArrayList<String>();
			
			fr = new fileIO(root + "unigramOutput/" + filename, "r");
			line = "";
			while((line = fr.read())!=null)
			{
				String[] parts = line.split(":");
				String input = "";
				for(int i=1; i<parts.length; i++)
				{
					input = input + " " + parts[i];
				}
				regexVectors.add(input);
			}
			
			ArrayList<String> metamapVectors = new ArrayList<String>();
			
			fr = new fileIO(root + "metamapFeatureVectors/" + filename, "r");
			line = "";
			while((line = fr.read())!=null)
			{
				String[] parts = line.split(",");
				String input = "";
				for(int i=1; i<parts.length; i++)
				{
					input = input + " " + parts[i];
				}
				metamapVectors.add(input);
			}
			
			//System.out.println(metamapVectors.size() + " " + regexVectors.size() + " " + lstmVectors.size());
			fileIO fw = new fileIO(root + "test_vectors_combined/test_vectors_" + cat + "_" + cohort + "/" + filename, "w");
			
			for(int j=0; j<metamapVectors.size(); j++)
			{
				fw.write(lstmVectors.get(j).trim() + regexVectors.get(j) + " " + metamapVectors.get(j) + "\n");
			}
			fw.close();
			
		}
		
	}
}
