import java.io.File;
import java.io.IOException;
import java.util.*;

public class testSetCreate {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/eRisk_test/chunk 1/";
		
		fileIO fw = new fileIO("/home/farig/Desktop/features/testList.txt", "w");
		
		File folder = new File(root);
		File[] listOfFiles = folder.listFiles();
		
		for(int i=0; i<listOfFiles.length; i++)
		{
			if(listOfFiles[i].isFile())
			{
				String filename = listOfFiles[i].getName();
				
				StringTokenizer tokens = new StringTokenizer(filename, "_.");
				
				filename = tokens.nextToken().toString() + "_" + tokens.nextToken().toString();
				
				fw.write(filename + " ?\n");
			}
		}
		fw.close();
	}
}
