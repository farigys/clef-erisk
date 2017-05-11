import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

//collects text for users with low number of posts

public class collect_bottom_user_text {
	public static void main(String args[]) throws IOException
	{
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data/";
		/////////////////////////////////////////////////////////////////////////////
		
		ArrayList<String> userList = new ArrayList<String>();
		
		fileIO f1 = new fileIO(root + "risk_golden_truth_negative.txt", "r");
		
		String line = "";
		
		for(int i=0; i<20; i++)
		{
			line = f1.read();
			String[] parts = line.split(",");
			userList.add(parts[0]);
		}
		
		String rootD1 = root + "negative_examples_anonymous_chunks_texts_indexed/";
		String rootD2 = root + "negative_examples_anonymous_chunks_texts_bottom_20/";
		
		
    	
    	for(int i=0; i<userList.size(); i++)
    	{
    		String filename = userList.get(i) + ".txt";
    		Files.copy(Paths.get(rootD1 + filename), Paths.get(rootD2 + filename), StandardCopyOption.REPLACE_EXISTING);

    	}
    	f1.close();
    	
    	////////////////////////////////////////////////////////////////////////////////////
    	
    	userList = new ArrayList<String>();
		
		f1 = new fileIO(root + "risk_golden_truth_positive.txt", "r");
		
		line = "";
		
		for(int i=0; i<20; i++)
		{
			line = f1.read();
			String[] parts = line.split(",");
			userList.add(parts[0]);
		}
		
		rootD1 = root + "positive_examples_anonymous_chunks_texts_indexed/";
		rootD2 = root + "positive_examples_anonymous_chunks_texts_bottom_20/";
		
		
    	
    	for(int i=0; i<userList.size(); i++)
    	{
    		String filename = userList.get(i) + ".txt";
    		Files.copy(Paths.get(rootD1 + filename), Paths.get(rootD2 + filename), StandardCopyOption.REPLACE_EXISTING);

    	}
	}
}
