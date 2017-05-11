import java.io.File;
import java.io.IOException;


public class chunkwise_divider_metamap_regex {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/reddit data/performance_measure/";
		
		for(int i=1; i<=10; i++)
		{
			File file = new File(root + "regex/chunk " + i);
			if(!file.exists())file.mkdir();
		}
		
		for(int i=1; i<=10; i++)
		{
			File file = new File(root + "metamap/chunk " + i);
			if(!file.exists())file.mkdir();
		}
		
		File file = new File(root + "metamapFeatureVectors/");
		
		File[] listFiles = file.listFiles();
		
		for(File fi:listFiles)
		{
			String filename = fi.getName();
			String username = filename.split("\\.")[0];
			
			for(int chunk=1; chunk<=10; chunk++)
			{
				fileIO fr = new fileIO(root + "metamapFeatureVectors/" + filename, "r");
				
				fileIO fw = new fileIO(root + "metamap/chunk " + chunk + "/" + filename, "w");
				
				String line = "";
				
				while((line=fr.read())!=null)
				{
					int currChunk = Integer.parseInt(line.split(",")[0].split("_")[2]);
					if(currChunk>chunk)break;
					fw.write(line + "\n");
				}
				fw.close();
			}
		}
		
		file = new File(root + "regexOutput/");
		
		listFiles = file.listFiles();
		
		for(File fi:listFiles)
		{
			String filename = fi.getName();
			String username = filename.split("\\.")[0];
			
			for(int chunk=1; chunk<=10; chunk++)
			{
				fileIO fr = new fileIO(root + "regexOutput/" + filename, "r");
				
				fileIO fw = new fileIO(root + "regex/chunk " + chunk + "/" + filename, "w");
				
				String line = "";
				
				while((line=fr.read())!=null)
				{
					int currChunk = Integer.parseInt(line.split(",")[0].split("_")[2]);
					if(currChunk>chunk)break;
					fw.write(line + "\n");
				}
				fw.close();
			}
		}
	}
}
