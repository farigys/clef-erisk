import java.io.File;
import java.io.IOException;

//creates BOW feature vectors for the training data (creates both post level and user level files)

public class bag_of_words_feature_vector_builder {
	public static void main(String[] args) throws IOException
	{
		int length = 1005;
		
		String root = "/home/farig/Desktop/eRisk@CLEF2017 - released training data/";
		
		String[] types = {"positive", "negative"};
		
		fileIO fwt = new fileIO(root + "bag_of_words_per_user.txt", "w");
		
		for(String type:types)
		{
			String rootRead = root + type + "_examples_anonymous_chunks_texts_indexed/";
			String rootWrite = root + type + "_examples_anonymous_chunks_texts_indexed_feature_vector/";
			
			File fread = new File(rootRead);
			
			File[] listFiles = fread.listFiles();
			
			System.out.println(listFiles.length);
			
			for(File f:listFiles)
			{
				int[] bowpu = new int[length];
				
				String filename = f.getName();
				
				if(!filename.startsWith("train"))continue;
				
				fwt.write(filename.split("\\.")[0]);

				fileIO fwpu = new fileIO(rootWrite + filename, "w");
				
				fileIO fr = new fileIO(rootRead + filename, "r");
				
				String l = "";
				
				while((l = fr.read())!=null)
				{
					int[] bowpp = new int[length];
					//String line = l.trim();
					if(l.length() == 1)
					{
						fwpu.write(Integer.toString(bowpp[1]));
						for(int x=2; x<bowpp.length; x++)
							fwpu.write("," + Integer.toString(bowpp[x]));
						fwpu.write("\n");
						continue;
					}
					String[] parts = l.trim().split(" ");
					for(String part:parts)
					{
						//System.out.print(part + " ");
						bowpu[Integer.parseInt(part)]++;
						bowpp[Integer.parseInt(part)]++;	
					}
					fwpu.write(Integer.toString(bowpp[1]));
					for(int x=2; x<bowpp.length; x++)
						fwpu.write("," + Integer.toString(bowpp[x]));
					fwpu.write("\n");
					
				}
				fwpu.close();
				//System.out.println();
				
				for(int x=1; x<bowpu.length; x++)
				{
					fwt.write("," + Integer.toString(bowpu[x]));
					//System.out.println(bowpu.length);
				}
				fwt.write("\n");
			}
			
		}
		fwt.close();
		
	}
}
