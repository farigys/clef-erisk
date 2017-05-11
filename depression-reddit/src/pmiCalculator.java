//Creates train vectors using top 100 unigrams and bigrams based on PMI

import java.util.*;
import java.io.IOException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class pmiCalculator {
	public static int size = 100;
	
	public static void main(String[] args) throws IOException
	{
		MaxentTagger tagger = new MaxentTagger("models/english-left3words-distsim.tagger");
		
		String root = "/home/farig/Desktop/reddit data/non-depression data/top/";
		
		String rootD = "/home/farig/Desktop/reddit data/";
		
		fileIO f1 = new fileIO(rootD + "unigramPMI.txt", "r");
        String line = "";
        
        //System.out.print(rxs);
        ArrayList<String> wordList = new ArrayList<String>();
        
        for(int p=0; p<size; p++)
        {
        	line = f1.read();
        	wordList.add(line.split("\t")[0]);
        }
        
        f1.close();
        
        f1 = new fileIO(rootD + "bigramPMI.txt", "r");
        line = "";
        
        ArrayList<String> bigramList = new ArrayList<String>();
        
        for(int p=0; p<size; p++)
        {
        	line = f1.read();
        	bigramList.add(line.split("\t")[0]);
        }
        
        f1.close();
		
		int[] count = new int[2*size];
		
		fileIO f = new fileIO(root + "postWithTitles.txt", "r");
		
		fileIO fw = new fileIO(root + "trainFeatureVectorsNonDepression.txt", "w");
		
		line = "";
		
		int c = 1;
		
		while((line = f.read())!=null)
		{
			if(line.equals("--------------------" + Integer.toString(c)))
			{
				fw.write(Integer.toString(c));
				c++;
				
				for(int i=0; i<2*size; i++)
				{
					fw.write(";" + count[i]);
				}
				fw.write("\n");
				count = new int[2*size];
			}
			else
			{
				String text = tagger.tagString(line);
				String[] parts = text.split(" ");
    			for(int wordC=0; wordC<parts.length; wordC++)//for unigram
    			{
    				String input = parts[wordC].split("_")[0].toLowerCase();
    				for (int wc=0; wc<wordList.size(); wc++)
    				{
    					if (wordList.get(wc).equals(input))
    					{
    						count[wc]++;
    					}
    				}
    			}
    			
    			for(int wordC=0; wordC<parts.length -1; wordC++)//for bigram
    			{
    				String firstWord = parts[wordC];
					String secondWord = parts[wordC + 1];
    				String input = firstWord.split("_")[0] + " " + secondWord.split("_")[0];
    				for (int wc=0; wc<bigramList.size(); wc++)
    				{
    					if (bigramList.get(wc).equals(input))
    					{
    						count[size + wc]++;
    					}
    				}
    			}
			}
		}
		fw.close();
	}
	
	
}
