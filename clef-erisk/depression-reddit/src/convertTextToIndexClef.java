import java.io.*;
import java.util.*;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class convertTextToIndexClef {
	public static void main(String[] args) throws IOException
	{
		int maxPostLength = 0;
		
		MaxentTagger tagger = new MaxentTagger("models/english-left3words-distsim.tagger");
		String root = "/home/farig/Desktop/reddit data/clef_text/";
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
		
		//System.out.println(dic);
		
		int unknownTag = dict.size() + 1;
		
		File file = new File(root + "negative_examples_anonymous_chunks_text_titles/");
		
		File[] list = file.listFiles();
		
		int c = 0;
		
		for(int y=0; y<list.length; y++)
		{
			System.out.println(c++);
			String filename = list[y].getName();
			if(!filename.startsWith("train"))continue;
			
			fr = new fileIO(root + "negative_examples_anonymous_chunks_text_titles/" + filename, "r");
			
			fileIO fw = new fileIO(root + "negative_examples_anonymous_chunks_text_titles_indexed/" + filename, "w");
			
			line = "";
			
			int postLength = 0;
			
			//int postid = 0;
			
			while((line = fr.read())!=null)
			{
				if(line.equals("&*&*&*&*&*&*&*&*&*&*"))
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
						else if(word.contains("/r/"))
						{
							fw.write(dict.get("subr") + " ");
						}
						else
						{
							fw.write(unknownTag + " ");
						}
					}
				}
			}
			
			fw.close();
						
		}

		/////////////////////////////////////////////////////////////////////////////////////
		
		file = new File(root + "positive_examples_anonymous_chunks_text_titles/");
		
		list = file.listFiles();
		
		for(int y=0; y<list.length; y++)
		{
			String filename = list[y].getName();
			if(!filename.startsWith("train"))continue;
			
			fr = new fileIO(root + "positive_examples_anonymous_chunks_text_titles/" + filename, "r");
			
			fileIO fw = new fileIO(root + "positive_examples_anonymous_chunks_text_titles_indexed/" + filename, "w");
			
			line = "";
			
			int postLength = 0;
			
			//int postid = 0;
			
			while((line = fr.read())!=null)
			{
				if(line.equals("&*&*&*&*&*&*&*&*&*&*"))
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
						else if(word.contains("/r/"))
						{
							fw.write(dict.get("subr") + " ");
						}
						else
						{
							fw.write(unknownTag + " ");
						}
					}
				}
			}
			
			fw.close();
						
		}


		
		System.out.println(maxPostLength);
		
	}
}
