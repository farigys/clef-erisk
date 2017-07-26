import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.*;


public class create_cui_dictionary {
	public static void main(String[] args) throws IOException
	{
		HashSet<String> cuiSet = new HashSet<String>();
		
		//ArrayList<String> cuiList = new ArrayList<String>();
		
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data_backup/";
		
		File folder = new File(root + "metamapOutputs/metamapOutputs/");
		
				File[] fileList = folder.listFiles();
				
				fileIO fw = new fileIO(root + "cuiVocabularyTotal.txt","w");
				
				for(int i=0; i<fileList.length; i++)
				{
					String filename = fileList[i].getName();
					
					fileIO fr = new fileIO(root + "metamapOutputs/metamapOutputs/" + filename, "r");
					
					String line = "";
					
					while((line = fr.read())!=null)
					{
						String[] parts = line.split(":");
						try
						{
							String cuis = parts[1];
							String[] cuiList = cuis.split(",");
							
							for(int j=0; j<cuiList.length; j++)
							{
								cuiSet.add(cuiList[j]);
							}
						}catch(Exception e)
						{
							continue;
						}
					}
					
				}
				
				//System.out.println(cuiSet);
				
				ArrayList<String> cuiList = new ArrayList<String>(cuiSet);
				
				for(int i=0; i<cuiList.size(); i++)
				{
					fw.write(cuiList.get(i) + "\n");
				}
				fw.close();
	}
}
