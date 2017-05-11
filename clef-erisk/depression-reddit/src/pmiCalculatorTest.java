//Creates test vectors using top 100 unigrams and bigrams using PMI for test data

import java.util.*;
import java.io.File;
import java.io.IOException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class pmiCalculatorTest {
	public static int size = 100;
	
	public static void main(String[] args) throws IOException
	{
		MaxentTagger tagger = new MaxentTagger("models/english-left3words-distsim.tagger");
		
		//String root = "/home/farig/Desktop/reddit data/positive_examples_anonymous_chunks_texts/";
		
		//switch between positive and negative anonymous chunks
		
		String root = "/home/farig/Desktop/reddit data/negative_examples_anonymous_chunks_texts/";
		
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
        
        ArrayList<String> users = new ArrayList<String>();
        
        File file = new File(root);
        
        File[] listFiles = file.listFiles();
        
        for(int i=0; i<listFiles.length; i++)
        {
        	String filename = listFiles[i].getName();
        	
        	if(filename.startsWith("train"))
        	{
        		users.add(filename);
        	}
        }
		
        for(int k=0; k<users.size(); k++)
        {
        	String filename = users.get(k);
        	
        	int[] count = new int[2*size];
    		
    		fileIO f = new fileIO(root + filename, "r");
    		
    		fileIO fw = new fileIO(rootD + "test vectors negative/" + filename, "w");
    		
    		line = "";
    		
    		int c = 1;
    		
    		while((line = f.read())!=null)
    		{
    			if(line.equals("------------------"))
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
    				//String text = tagger.tagString(line);
    				String text = line;
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
	
	
}
