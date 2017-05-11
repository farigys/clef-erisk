import java.io.File;
import java.io.IOException;
import java.util.*;

public class divide_clef_postwise_vectors_in_train_and_val {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/reddit data/clef_text/test_vectors/";
		
		ArrayList<String> trainList = new ArrayList<String>();
		
		ArrayList<String> valList = new ArrayList<String>();
		
		String line;
		
		fileIO fr = new fileIO(root + "trainList.txt", "r");
		
		line = "";
		
		while((line=fr.read())!=null)
		{
			trainList.add(line + ".txt");
		}
		
		fr = new fileIO(root + "valList.txt", "r");
		
		line = "";
		
		while((line=fr.read())!=null)
		{
			valList.add(line + ".txt");
		}
		
		String writefolder = "";
		
		File folder = new File(root + "test_vectors_negative/");
		
		File[] listFiles = folder.listFiles();
		
		for(File fi:listFiles)
		{
			String filename = fi.getName();
			
			if(trainList.contains(filename))writefolder = "test_vectors_negative_train/";
			else writefolder = "test_vectors_negative_val/";
			
			fileIO fr1 = new fileIO(root + "test_vectors_negative/" + filename, "r");
			fileIO fw = new fileIO(root + writefolder + filename, "w");
			
			String l = "";
			
			while((l = fr1.read())!=null)
			{
				fw.write(l + "\n");
			}
			fw.close();
		}
		////////////////////////////////////////////////////////////////
		folder = new File(root + "test_vectors_positive/");
		
		listFiles = folder.listFiles();
		
		for(File fi:listFiles)
		{
			String filename = fi.getName();
			
			if(trainList.contains(filename))writefolder = "test_vectors_positive_train/";
			else writefolder = "test_vectors_positive_val/";
			
			fileIO fr1 = new fileIO(root + "test_vectors_positive/" + filename, "r");
			fileIO fw = new fileIO(root + writefolder + filename, "w");
			
			String l = "";
			
			while((l = fr1.read())!=null)
			{
				fw.write(l + "\n");
			}
			fw.close();
		}
		
	}
	
}


















