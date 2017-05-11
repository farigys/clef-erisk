import java.io.IOException;
import java.util.*;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

//calculates PMI for words from external data

public class pmiCount {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/Depression vs Non-depression_ JAN 2015/";
		HashMap<String, Double> pmiMap = new HashMap<String, Double>();
		
		ArrayList<String> words = new ArrayList<String>();
		
		HashMap<String, Integer> dWordCount = new HashMap<String, Integer>();
		HashMap<String, Integer> ndWordCount = new HashMap<String, Integer>();
		
		//fileIO fr = new fileIO(root + "depressionWordCount.txt", "r");//for unigram
		fileIO fr = new fileIO(root + "depressionUnigramCount2.txt", "r");
		String line = fr.read();
		
		//int dTotalWordCount = Integer.parseInt(line.split(",")[1]);//for unigram
		int dTotalWordCount = Integer.parseInt(line.split("\t")[1]);
		
		while((line = fr.read())!=null)
		{
			//String term = line.split(",")[0];//for unigram
			String term = line.split("\t")[0];
			//int count = Integer.parseInt(line.split(",")[1]);//for unigram
			int count = Integer.parseInt(line.split("\t")[1]);
			dWordCount.put(term, count);
			words.add(term);
		}
		
		//fr = new fileIO(root + "non-depressionWordCount.txt", "r");//for unigram
		fr = new fileIO(root + "non-depressionUnigramCount2.txt", "r");
		line = fr.read();
		
		//int ndTotalWordCount = Integer.parseInt(line.split(",")[1]);//for unigram
		int ndTotalWordCount = Integer.parseInt(line.split("\t")[1]);
		
		while((line = fr.read())!=null)
		{
			//String term = line.split(",")[0];//for unigram
			String term = line.split("\t")[0];
			//int count = Integer.parseInt(line.split(",")[1]);//for unigram
			int count = Integer.parseInt(line.split("\t")[1]);
			ndWordCount.put(term, count);
		}
		
		for(int i=0; i<words.size(); i++)
		{
			String currWord = words.get(i);
			int wInD = dWordCount.get(currWord);
			int wInND = 0;
			if(ndWordCount.containsKey(currWord))wInND = ndWordCount.get(currWord);
			
			double pmi = ((wInD + 1)/(dTotalWordCount * 1.0))/((wInND + 1)/((dTotalWordCount + ndTotalWordCount)*1.0));
			
			pmiMap.put(currWord, Math.log(pmi));
		}
		
		fileIO fw = new fileIO(root + "unigramPMI2.txt", "w");
		
		Iterator it = pmiMap.entrySet().iterator();
		
		while(it.hasNext())
		{
			Map.Entry entry = (Map.Entry)it.next();
			
			fw.write(entry.getKey() + "\t" + entry.getValue() + "\n");
		}
	}
}
