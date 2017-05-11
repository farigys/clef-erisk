import java.io.IOException;
import java.util.HashMap;


public class create_weka_feature_vector {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data/";
		
		String filename = "bag_of_words_per_user";//bow
		//String filename = "unigramFeatures";//for unigram
		//String filename = "bigramFeatures";//for bigram
		//String filename = "metamapFeatures";//for metamap
		//String filename = "regexFeatures";//for regex
		//String filename = "unigramFeatures_bigramFeatures";//for uni and bigram
		//String filename = "unigramFeatures_metamapFeatures";//for unigram and metamap
		//String filename = "unigramFeatures_bigramFeatures_metamapFeatures";//for unigram bigram and metamap
		//String filename = "regexFeatures_metamapFeatures";//for regex and metamap
		//String filename = "embeddingFeatures";//lstm embeddings
		//String filename = "embeddingFeatures_unigramFeatures_bigramFeatures";//embedding unigram and bigram
		//String filename = "embeddingFeatures_regexFeatures";//embedding and regex features
		//String filename = "embeddingFeatures_unigramFeatures_bigramFeatures_metamapFeatures";//embedding unigram and bigram
		
		
		//String sep = " "; //for lst embeddings
		String sep = ",";//for everyone else except lstm embeddings
		//String sep = ":";//for ngram and regex
		
		HashMap<String, String> classMap = new HashMap<String, String>();
		
		fileIO fr = new fileIO(root + "risk_golden_truth.txt", "r");
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split(" ");
			classMap.put(parts[0], parts[1]);
		}
		
		fr = new fileIO(root + filename + ".txt","r");
		
		fileIO fw = new fileIO(root + filename + ".arff", "w");
		
		line = fr.read();
		
		String[] parts = line.split(sep);
		
		System.out.println(parts.length);
		
		fw.write("@relation " + filename + "_clef\n\n");
		
		for(int i=1; i<parts.length; i++)
		{
			fw.write("@attribute " + filename + i +  " real\n");
		}
		
		fw.write("@attribute class {0,1}\n");
		
		fw.write("\n@data\n");
		
		int totalAttrs = parts.length;
		
		System.out.println(totalAttrs);
		
		for(int i=1; i<parts.length; i++)
		{
			fw.write(parts[i] + ",");
		}
		fw.write(classMap.get(parts[0]) + "\n");
		
		while((line = fr.read())!=null)
		{
			int attrCount = 1;
			parts = line.split(sep);
			for(int i=1; i<parts.length; i++)
			{
				attrCount++;
				fw.write(parts[i] + ",");
			}
			fw.write(classMap.get(parts[0]) + "\n");
			System.out.println("AttrCount for " + parts[0] + ": " + attrCount);
		}
		fw.close();
	}
}
