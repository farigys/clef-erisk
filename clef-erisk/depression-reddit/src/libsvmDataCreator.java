//Creates libsvm vectors for train and test data

import java.util.*;
import java.io.File;
import java.io.IOException;

public class libsvmDataCreator {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/reddit data/";
		
		//uncomment the bottom to create train vectors
		
//		fileIO f = new fileIO(root + "trainFeatureVectorsDepression.txt","r");
//		
//		fileIO fw = new fileIO(root + "libsvm_vectors/train/train.txt",  "w");
//		
//		String line = "";
//		
//		while((line = f.read())!=null)
//		{
//			String[] parts = line.split(";");
//			fw.write("1");
//			for(int i=1; i<parts.length; i++)
//			{
//				fw.write(" " + i + ":" + parts[i]);
//			}
//			fw.write("\n");
//		}
//		
//		f.close();
//		
//		f = new fileIO(root + "trainFeatureVectorsNonDepression.txt","r");
//		
//		line = "";
//		
//		while((line = f.read())!=null)
//		{
//			String[] parts = line.split(";");
//			fw.write("0");
//			for(int i=1; i<parts.length; i++)
//			{
//				fw.write(" " + i + ":" + parts[i]);
//			}
//			fw.write("\n");
//		}
//		
//		fw.close();
		
		File file = new File(root + "test vectors negative/");
		
		File[] listofFiles = file.listFiles();
		
		fileIO names = new fileIO(root + "testUserListNegative.txt", "w");
		
		for(int i=0; i<listofFiles.length; i++)
		{
			names.write(listofFiles[i].getName() + "\n");
			
			fileIO f = new fileIO(root + "test vectors negative/" + listofFiles[i].getName(),  "r");
			
			fileIO fw = new fileIO(root + "libsvm_vectors/test negative/" + listofFiles[i].getName(),  "w");
			
			String line = "";
			
			while((line = f.read())!=null)
			{
				String[] parts = line.split(";");
				fw.write("0");//for positive, change it to 1
				for(int j=1; j<parts.length; j++)
				{
					fw.write(" " + j + ":" + parts[j]);
				}
				fw.write("\n");
			}
			
			f.close();
			fw.close();
		}
		names.close();
	}
}
