import java.io.*;
import java.util.*;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class convertTextToIndex {
	public static void main(String[] args) throws IOException
	{
		int maxPostLength = 0;
		
		MaxentTagger tagger = new MaxentTagger("models/english-left3words-distsim.tagger");
		String root = "/home/farig/Desktop/reddit data/training-posts-NN/";
		fileIO fr = new fileIO(root + "dictionary.txt", "r");
		
		HashMap<String, String> dict = new HashMap<String, String>();
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split("\t");
			String word = parts[0];
			String index = parts[1];
			
			dict.put(word, index);
		}
		
		fr = new fileIO(root + "depression/hot/postWithTitles.txt", "r");
		
		fileIO fw = new fileIO(root + "positiveTrainPostsIndexed.txt", "w");
		
		line = "";
		
		int postLength = 0;
		
		while((line = fr.read())!=null)
		{
			if(line.startsWith("--------------------"))
			{
				fw.write("\n");
				if(postLength>maxPostLength)maxPostLength = postLength;
				postLength = 0;
				continue;
			}
			
			String[] texts = tagger.tagString(line).split(" ");
			
			postLength+=texts.length;
			
			for(int x=0; x<texts.length; x++)
			{
				String word = texts[x].split("_")[0];
				
				if(dict.containsKey(word))
				{
					fw.write(dict.get(word) + " ");
				}
				else
				{
					if(word.contains("https://") || word.contains("http://") || word.contains("www"))
					{
						fw.write(dict.get("url") + " ");
					}
					if(word.contains("/r/"))
					{
						fw.write(dict.get("subr") + " ");
					}
				}
			}
		}
		
		//fw.close();
		/////////////////////////////////////////////////////////////////////////////////////
		fr = new fileIO(root + "depression/top/postWithTitles.txt", "r");
		
		//fileIO fw = new fileIO(root + "positiveTrainPostsIndexed.txt", "w");
		
		line = "";
		
		postLength = 0;
		
		while((line = fr.read())!=null)
		{
			if(line.startsWith("--------------------"))
			{
				fw.write("\n");
				if(postLength>maxPostLength)maxPostLength = postLength;
				postLength = 0;
				continue;
			}
			
			String[] texts = tagger.tagString(line).split(" ");
			
			postLength+=texts.length;
			
			for(int x=0; x<texts.length; x++)
			{
				String word = texts[x].split("_")[0];
				
				if(dict.containsKey(word))
				{
					fw.write(dict.get(word) + " ");
				}
				else
				{
					if(word.contains("https://") || word.contains("http://") || word.contains("www"))
					{
						fw.write(dict.get("url") + " ");
					}
					if(word.contains("/r/"))
					{
						fw.write(dict.get("subr") + " ");
					}
				}
			}
		}
		
		fw.close();
		
		/////////////////////////////////////////////////////////////////////////////////////
		
		fr = new fileIO(root + "non-depression/hot/postWithTitles.txt", "r");
		
		fw = new fileIO(root + "negativeTrainPostsIndexed.txt", "w");
		
		line = "";
		
		postLength = 0;
		
		while((line = fr.read())!=null)
		{
			if(line.startsWith("--------------------"))
			{
				fw.write("\n");
				if(postLength>maxPostLength)maxPostLength = postLength;
				postLength = 0;
				continue;
			}
			
			String[] texts = tagger.tagString(line).split(" ");
			
			postLength+=texts.length;
			
			for(int x=0; x<texts.length; x++)
			{
				String word = texts[x].split("_")[0];
				
				if(dict.containsKey(word))
				{
					fw.write(dict.get(word) + " ");
				}
				else
				{
					if(word.contains("https://") || word.contains("http://") || word.contains("www"))
					{
						fw.write(dict.get("url") + " ");
					}
					if(word.contains("/r/"))
					{
						fw.write(dict.get("subr") + " ");
					}
				}
			}
		}
		//////////////////////////////////////////////////////////////////////////
		
		fr = new fileIO(root + "non-depression/top/postWithTitles.txt", "r");
		
		//fw = new fileIO(root + "negativeTrainPostsIndexed.txt", "w");
		
		line = "";
		
		postLength = 0;
		
		while((line = fr.read())!=null)
		{
			if(line.startsWith("--------------------"))
			{
				fw.write("\n");
				if(postLength>maxPostLength)maxPostLength = postLength;
				postLength = 0;
				continue;
			}
			
			String[] texts = tagger.tagString(line).split(" ");
			
			postLength+=texts.length;
			
			for(int x=0; x<texts.length; x++)
			{
				String word = texts[x].split("_")[0];
				
				if(dict.containsKey(word))
				{
					fw.write(dict.get(word) + " ");
				}
				else
				{
					if(word.contains("https://") || word.contains("http://") || word.contains("www"))
					{
						fw.write(dict.get("url") + " ");
					}
					if(word.contains("/r/"))
					{
						fw.write(dict.get("subr") + " ");
					}
				}
			}
		}
		
		fw.close();
		
		System.out.println(maxPostLength);
	}
}
