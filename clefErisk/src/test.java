import java.io.File;
import java.io.IOException;

import java.util.regex.Pattern;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class test {
	public static void main(String[] args) throws IOException
	{
//		String root = "/home/farig/Desktop/chunk 1 results/ensemble/";
//		fileIO fr = new fileIO(root + "train/weka/metamapOutputToy.csv", "r");
//		
//		String line = fr.read();
//		
//		while((line = fr.read())!=null)
//		{
//			System.out.println(line.split(",")[2].split(":")[1]);
//		}
//		MaxentTagger tagger = new MaxentTagger(
//                "models/english-left3words-distsim.tagger");
// 
//        // The sample string
//        String sample = "Nairobi, Kenya (AP) _";
// 
//        // The tagged string
//        String tagged = tagger.tagString(sample);
// 
//        // Output the result
//        System.out.println(tagged);
//        
//        int chunk = 2;
//        
//        String root = "/home/farig/Desktop/clef erisk results/chunk " + chunk + " results/individual/";
//        
//        fileIO f = new fileIO(root + "UArizonaD_2.txt","r");
//        
//        String line = "";
//        
//        while((line = f.read())!=null)
//        {
//        	System.out.println(line.split("\t\t")[0] + "," + line.split("\t\t")[1]);
//        }
		
		//Pattern rx = Pattern.compile("She.*");
		
		//System.out.println(rx.matcher("She....").matches());
		
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data/";
		String rootDirIndexed = root + "positive_examples_anonymous_chunks_texts_indexed/";
		
		File fread = new File(rootDirIndexed);
		
		File[] listFiles = fread.listFiles();
		
		for(File f:listFiles)
		{
			String filename = f.getName();

			fileIO fr = new fileIO(rootDirIndexed + filename, "r");
			
			String line = "";
			
			while((line = fr.read())!=null)
			{
				String[] parts = line.split(" ");
				for(int i=0; i<parts.length; i++)
				{
					if(parts[i].length()>4)System.out.println(filename + " genjam");
				}
			}
			
		}
		

	}
}
