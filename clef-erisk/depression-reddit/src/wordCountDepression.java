/*Count the individual words in all posts- depression and non-depression
 * */

import java.io.IOException;
import java.util.*;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class wordCountDepression {
	public static void main(String[] args) throws IOException
	{
		ArrayList<String> classesToDelete = new ArrayList<String>();
		String[] tags = {":", ".", ",", "\"", "'", "''", "\"\"", "-LRB-", "#", "`", "``"};
		
		for(int m=0; m<tags.length; m++)classesToDelete.add(tags[m]);
		MaxentTagger tagger = new MaxentTagger("models/english-left3words-distsim.tagger");
		//String root = "/home/farig/Desktop/reddit data/depression data/top/";
		String droot = "/home/farig/Desktop/reddit data/depression-data/hot/";
		String ndroot = "/home/farig/Desktop/reddit data/non-depression-data/hot/";
		
		HashMap<String, Integer> dCountMap = new HashMap<String, Integer>();
		HashMap<String, Integer> ndCountMap = new HashMap<String, Integer>();
		
		int dTotalWordCount = 0;
		int ndTotalWordCount = 0;
		
		//for(int i=21; i<=40; i++)
		{
			//fileIO fr = new fileIO(droot + "file" + i + ".txt", "r");
			fileIO fr = new fileIO(droot + "postWithTitles.txt", "r");
			String line = "";
			
			while((line = fr.read())!=null)
			{
				if(line.equals("--------------------"))continue;
				line = line.trim();
				if(line.equals(""))continue;
				String taggedText = tagger.tagString(line);
				String[] parts = taggedText.split(" ");
				if(parts.length == 0)continue;
				for(int wordC=0; wordC<parts.length; wordC++)//change limits for swithing between unigrams and bigrams
				{
					//if(parts[wordC].equals(" "))continue;
//					//System.out.println(parts[wordC]);
					
					//uncomment the bottom for bigram

//					String firstWord = parts[wordC];
//					String secondWord = parts[wordC + 1];
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
					/////////////////////////////////////////////////////
					
					//uncomment the bottom for unigram
					if(parts[wordC].contains("http://") || parts[wordC].contains("https://") || parts[wordC].contains("www."))
		    			continue;
		    		if(parts[wordC].startsWith("r/") || parts[wordC].startsWith("/r/"))
		    			continue;
					String input = parts[wordC].split("_")[0].toLowerCase();
					if(parts[wordC].split("_").length < 2)continue;
					String posTag = parts[wordC].split("_")[1];
					if(classesToDelete.contains(posTag))continue;
			    	if(posTag.equals("CD"))continue;
			    	/////////////////////////////////////////////////////
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
		
		//for(int i=21; i<=40; i++)
		{
			fileIO fr = new fileIO(ndroot + "postWithTitles.txt", "r");
			String line = "";
			
			while((line = fr.read())!=null)
			{
				if(line.equals("--------------------"))continue;
				line = line.trim();
				if(line.equals(""))continue;
				String taggedText = tagger.tagString(line);
				String[] parts = taggedText.split(" ");
				if(parts.length == 0)continue;
				for(int wordC=0; wordC<parts.length - 1; wordC++)//change limits for swithing between unigrams and bigrams
				{
					//if(parts[wordC].equals(" "))continue;
					//System.out.println(parts[wordC]);
					
					
//					//uncomment the bottom for bigram
//					String firstWord = parts[wordC];
//					String secondWord = parts[wordC + 1];
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
					////////////////////////////////////////////////////////
					
					//uncomment the bottom for unigram
					if(parts[wordC].contains("http://") || parts[wordC].contains("https://") || parts[wordC].contains("www."))
		    			continue;
		    		if(parts[wordC].startsWith("r/") || parts[wordC].startsWith("/r/"))
		    			continue;
					String input = parts[wordC].split("_")[0].toLowerCase();
					if(parts[wordC].split("_").length < 2)continue;
					String posTag = parts[wordC].split("_")[1];
					if(classesToDelete.contains(posTag))continue;
			    	if(posTag.equals("CD"))continue;
					///////////////////////////////////////////
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
		
		fileIO fw = new fileIO(droot + "depressionUnigramCount.txt", "w");//change unigram -> bigram
		Iterator it = dCountMap.entrySet().iterator();
		
		fw.write("total unigram Count\t" + dTotalWordCount);//change unigram -> bigram
		
		while(it.hasNext())
		{
			Map.Entry entry = (Map.Entry) it.next();
			int count = Integer.parseInt(entry.getValue().toString());
			if(count<10)continue;
			fw.write("\n" + entry.getKey().toString() + "\t" + entry.getValue().toString());
		}
		fw.close();
		
		fw = new fileIO(ndroot + "non-depressionUnigramCount.txt", "w");//change unigram -> bigram
		it = ndCountMap.entrySet().iterator();
		
		fw.write("total unigram Count\t" + ndTotalWordCount);//change unigram -> bigram
		
		while(it.hasNext())
		{
			Map.Entry entry = (Map.Entry) it.next();
			int count = Integer.parseInt(entry.getValue().toString());
			if(count<10)continue;
			fw.write("\n" + entry.getKey().toString() + "\t" + entry.getValue().toString());
		}
		fw.close();
	}
}
