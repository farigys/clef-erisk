import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

//extracts sentences containing depression regexes

public class regexSentenceExtractor {
	public static void main(String[] args) throws IOException
	{
		MaxentTagger tagger = new MaxentTagger("models/english-left3words-distsim.tagger");
		
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data_backup/";
		
		fileIO f = new fileIO(root + "risk_golden_truth.txt","r");
		
		ArrayList<String> userList = new ArrayList<String>();
		
		String line = "";
		
		while((line = f.read())!=null)
		{
			userList.add(line.split(" ")[0]);
		}
		
		fileIO f1 = new fileIO(root + "regexList.txt", "r");
        line = "";
        
        ArrayList<Pattern> rxs = new ArrayList<Pattern>();
        
        while((line=f1.read())!=null)
        {
        	String currRegex = line;
        	rxs.add(Pattern.compile(line));
        }
        
        int length = rxs.size();
        
        System.out.println(userList.size());
		
		for(int i=0; i<userList.size(); i++)
		{
			String userId = userList.get(i);
			String filename = root + "trainAllUserSentences/" + userId + ".txt";
			fileIO fr = new fileIO(filename, "r");
			
			fileIO fw = new fileIO(root + "trainAllUserRegexSentences/" + userId + ".txt", "w");
			
			String l = "";
			
			while((l = fr.read())!=null)
			{
				String taggedText = tagger.tagString(l);
    			String[] parts = taggedText.split(" ");
    			for(int wordC=0; wordC<parts.length; wordC++)
    			{
    				String input = parts[wordC].split("_")[0].toLowerCase();
    				for (Pattern rx : rxs)
    				{
    					if (rx.matcher(input).matches())
    					{
    						fw.write(l.trim() + "\n");
    						break;
    					}
    				}
    				break;
    			}
			}
			fw.close();
		}
	}
}
