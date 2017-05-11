//Extracts titles for each posts and join them with the content of original post
import java.io.File;
import java.io.IOException;
import java.util.*;

public class title_extractor {
	public static void main(String[] args) throws IOException
	{
		ArrayList<String> titleList = new ArrayList<String>();
		String root = "/home/farig/Desktop/reddit data/non-depression-data/top/";
		fileIO f = new fileIO(root + "postInfo.txt", "r");
		
		String line = f.read();
		
		//int count = 0;
		
		while((line = f.read())!=null)
		{
			//count++;
			String[] parts = line.split("\t");
			//if(parts.length!=7)System.out.println(parts.length);
			//System.out.println(count + ":" + parts[3]);
			String title = parts[3];
			titleList.add(title);
			
		}
		
		System.out.println(titleList.size());
		
		int postCount = 0;
		
		File file = new File(root + "post contents");
		
		File[] listOfFiles = file.listFiles();
		
		fileIO fw = new fileIO(root + "postWithTitles.txt", "w");
		
		for(int i=0; i<listOfFiles.length; i++)
		{
			//System.out.println(listOfFiles[i].getName());
			if(!listOfFiles[i].getName().startsWith("file"))continue;
			else
			{
				f = new fileIO(root + "post contents/" + listOfFiles[i].getName() , "r");
				
				line = "";
				
				//int count = 0;
				
				int lineCount = 0;
				
				//String s = "--------------------";
				
				//System.out.println(s.equals("-------------------------"));
				
				while((line = f.read())!=null)
				{
					//if(postCount==titleList.size())break;
					if(line.equals("--------------------"))
					{
						//System.out.println(postCount);
						lineCount = 0;
						postCount++;
						fw.write("--------------------" + Integer.toString(postCount) + "\n");
						continue;
					}
					if(lineCount == 0)
					{
						lineCount++;
						fw.write(titleList.get(postCount) + "\n");
					}
					fw.write(line + "\n");
					
				}
				f.close();
			}
		}
		fw.close();
		//System.out.println(listOfFiles.leng);
		
		
		
	}
}
