import java.io.IOException;
import java.util.*;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

//creates word count dictionary from JAN 2015 external reddit data

public class wordCount {
	public static void main(String[] args) throws IOException
	{
		ArrayList<String> classesToDelete = new ArrayList<String>();
		String[] tags = {":", ".", ",", "\"", "'", "''", "\"\"", "-LRB-", "#", "`", "``"};
		
		for(int m=0; m<tags.length; m++)classesToDelete.add(tags[m]);
		MaxentTagger tagger = new MaxentTagger("models/english-left3words-distsim.tagger");
		String root = "/home/farig/Desktop/Depression vs Non-depression_ JAN 2015/";
		String droot = root + "depression reddit posts/";
		String ndroot = root + "non-depression reddit posts/";
		
		HashMap<String, Integer> dCountMap = new HashMap<String, Integer>();
		HashMap<String, Integer> ndCountMap = new HashMap<String, Integer>();
		
		int dTotalWordCount = 0;
		int ndTotalWordCount = 0;
		
		for(int i=21; i<=40; i++)
		{
			fileIO fr = new fileIO(droot + "file" + i + ".txt", "r");
			String line = "";
			
			while((line = fr.read())!=null)
			{
				if(line.contains("-----------------"))continue;
				line = line.trim();
				if(line.equals(""))continue;
				String taggedText = tagger.tagString(line);
				String[] parts = taggedText.split(" ");
				for(int wordC=0; wordC<parts.length; wordC++)//for unigram, change the limit
				{
					//if(parts[wordC].equals(" "))continue;
					//System.out.println(parts[wordC]);
					//String firstWord = parts[wordC];
					//String secondWord = parts[wordC + 1];
					
					//uncomment the bottom for bigram
//					if(firstWord.contains("http://") || firstWord.contains("https://") || firstWord.contains("www."))
//		    			continue;
//		    		if(firstWord.startsWith("r/") || firstWord.startsWith("/r/"))
//		    			continue;
//		    		if(secondWord.contains("http://") || secondWord.contains("https://") || secondWord.contains("www."))
//		    			continue;
//		    		if(secondWord.startsWith("r/") || secondWord.startsWith("/r/"))
//		    			continue;
//					String input = firstWord.split("_")[0] + " " + secondWord.split("_")[0];
					//String posTag = parts[wordC].split("_")[1];
					//if(classesToDelete.contains(posTag))continue;
			    	//if(posTag.equals("CD"))continue;

					
					//uncomment the bottom for unigram
					if(parts[wordC].contains("http://") || parts[wordC].contains("https://") || parts[wordC].contains("www."))
		    			continue;
		    		if(parts[wordC].startsWith("r/") || parts[wordC].startsWith("/r/"))
		    			continue;
					String input = parts[wordC].split("_")[0].toLowerCase();
					String posTag = parts[wordC].split("_")[1];
					if(classesToDelete.contains(posTag))continue;
			    	if(posTag.equals("CD"))continue;
			    	if(dCountMap.containsKey(input))
			    	{
			    		int temp = dCountMap.get(input);
			    		temp++;
			    		dCountMap.put(input, temp);
			    	}
			    	else dCountMap.put(input, 1);

			    	dTotalWordCount++;
				}
			}
			
		}
		
		for(int i=21; i<=40; i++)
		{
			fileIO fr = new fileIO(ndroot + "file" + i + ".txt", "r");
			String line = "";
			
			while((line = fr.read())!=null)
			{
				if(line.contains("-----------------"))continue;
				line = line.trim();
				if(line.equals(""))continue;
				String taggedText = tagger.tagString(line);
				String[] parts = taggedText.split(" ");
				for(int wordC=0; wordC<parts.length - 1; wordC++)
				{
					//if(parts[wordC].equals(" "))continue;
					//System.out.println(parts[wordC]);
					String firstWord = parts[wordC];
					String secondWord = parts[wordC + 1];
					
					//uncomment the bottom for bigram
//					if(firstWord.contains("http://") || firstWord.contains("https://") || firstWord.contains("www."))
//		    			continue;
//		    		if(firstWord.startsWith("r/") || firstWord.startsWith("/r/"))
//		    			continue;
//		    		if(secondWord.contains("http://") || secondWord.contains("https://") || secondWord.contains("www."))
//		    			continue;
//		    		if(secondWord.startsWith("r/") || secondWord.startsWith("/r/"))
//		    			continue;
//					String input = firstWord.split("_")[0] + " " + secondWord.split("_")[0];
					//String posTag = parts[wordC].split("_")[1];
					//if(classesToDelete.contains(posTag))continue;
			    	//if(posTag.equals("CD"))continue;

					
					//uncomment the bottom for unigram
					if(parts[wordC].contains("http://") || parts[wordC].contains("https://") || parts[wordC].contains("www."))
		    			continue;
		    		if(parts[wordC].startsWith("r/") || parts[wordC].startsWith("/r/"))
		    			continue;
					String input = parts[wordC].split("_")[0].toLowerCase();
					String posTag = parts[wordC].split("_")[1];
					if(classesToDelete.contains(posTag))continue;
			    	if(posTag.equals("CD"))continue;
			    	if(ndCountMap.containsKey(input))
			    	{
			    		int temp = ndCountMap.get(input);
			    		temp++;
			    		ndCountMap.put(input, temp);
			    	}
			    	else ndCountMap.put(input, 1);

			    	ndTotalWordCount++;
				}
			}
			
			
		}
		
		System.out.println("total depression word count: " + dTotalWordCount);
		System.out.println("total non-depression word count: " + ndTotalWordCount);
		
		fileIO fw = new fileIO(root + "depressionUnigramCount2.txt", "w");
		Iterator it = dCountMap.entrySet().iterator();
		
		fw.write("total bigram Count\t" + dTotalWordCount);
		
		while(it.hasNext())
		{
			Map.Entry entry = (Map.Entry) it.next();
			fw.write("\n" + entry.getKey().toString() + "\t" + entry.getValue().toString());
		}
		fw.close();
		
		fw = new fileIO(root + "non-depressionUnigramCount2.txt", "w");
		it = ndCountMap.entrySet().iterator();
		
		fw.write("total bigram Count\t" + ndTotalWordCount);
		
		while(it.hasNext())
		{
			Map.Entry entry = (Map.Entry) it.next();
			fw.write("\n" + entry.getKey().toString() + "\t" + entry.getValue().toString());
		}
		fw.close();
	}
}
