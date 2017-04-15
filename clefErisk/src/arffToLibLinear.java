import java.io.IOException;
import java.util.*;
//code for converting weka data files to liblinear data filees

public class arffToLibLinear {
	public static void main(String[] args) throws IOException
	{
		HashMap<String, String> depDiag = new HashMap<String, String>();
		
		String root = "/home/farig/Desktop/eRisk_test/chunk 10 analysis/"; //change this to 3,4,5...

//		fileIO fr = new fileIO("depressionDiagnosisCount.txt", "r");
//		
//		String line = "";
//		
//		while((line = fr.read())!=null)
//		{
//			String[] parts = line.split(":");
//			depDiag.put(parts[0], parts[1]);
//		}
//		fr.close();
		
		
		//fileIO f = new fileIO(root + "metamapWeka.arff", "r");
		fileIO f = new fileIO(root + "metamapRegexFeaturesTestVectorsWeka.arff", "r");
		fileIO fw = new fileIO(root + "metamapRegexFeaturesTestVectorsWeka.txt", "w");
//		fileIO f = new fileIO(root + "regexFeaturesTestWeka.arff", "r");
//		fileIO fw = new fileIO(root + "regexFeaturesTestWeka.txt", "w");
		//fileIO f = new fileIO("/home/farig/Desktop/eRisk@CLEF2017 - released training data/pmiWordsVal.arff", "r");
		//fileIO fw = new fileIO("/home/farig/Desktop/eRisk@CLEF2017 - released training data/pmiWordsVal.txt", "w");
//		fileIO f = new fileIO(root + "metamapFeaturesTestVectorsWeka.arff", "r");
//		fileIO fw = new fileIO(root + "metamapFeaturesTestVectorsWeka.txt", "w");
		
		String line  = "";
		
		while((line = f.read())!=null)
		{
			if(line.startsWith("@") || line.equals(""))continue;
			String[] parts = line.split(",");
			fw.write(parts[parts.length-1]);
			for(int i=0; i<parts.length-1; i++)
			{
				fw.write(" " + (i+1) + ":" + parts[i]);
			}
			fw.write("\n");
		}
		fw.close();
	}
}
