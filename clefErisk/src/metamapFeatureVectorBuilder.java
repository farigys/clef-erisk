import java.io.File;
import java.io.IOException;
import java.util.*;

//creates feature vectors from metamap outputs

public class metamapFeatureVectorBuilder {
	public static void main(String[] args) throws IOException
	{
		HashSet<String> cuiSet = new HashSet<String>();
		
		ArrayList<String> cuiList = new ArrayList<String>();
		
		ArrayList<String> userList = new ArrayList<String>();
		
		String root = "/home/farig/Desktop/eRisk_test/chunk 10 analysis/";//change to 3,4,5.....
		
		fileIO fr = new fileIO(root + "cuiVocabulary.txt", "r");
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			cuiList.add(line);
		}
		
		fr = new fileIO(root + "testPostCount.txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			userList.add(line.split(",")[0]);
		}
		
		//File folder = new File(root + "metamapOutputs");
		
		//File[] fileList = folder.listFiles();
		
//		fileIO fw = new fileIO(root + "cuiVocabulary.txt","w");
//		
//		for(int i=0; i<fileList.length; i++)
//		{
//			String filename = fileList[i].getName();
//			
//			fileIO fr = new fileIO(root + "metamapOutputs/" + filename, "r");
//			
//			String line = "";
//			
//			while((line = fr.read())!=null)
//			{
//				String[] parts = line.split(":");
//				try
//				{
//					String cuis = parts[1];
//					String[] cuiList = cuis.split(",");
//					
//					for(int j=0; j<cuiList.length; j++)
//					{
//						cuiSet.add(cuiList[j]);
//					}
//				}catch(Exception e)
//				{
//					continue;
//				}
//			}
//			
//		}
//		
//		//System.out.println(cuiSet);
//		
//		ArrayList<String> cuiList = new ArrayList<String>(cuiSet);
//		
//		for(int i=0; i<cuiList.size(); i++)
//		{
//			fw.write(cuiList.get(i) + "\n");
//		}
//		fw.close();
		
		fileIO fwtotal = new fileIO(root + "metamapFeatures.txt", "w");
		
		for(int i=0; i<userList.size(); i++)
		{			
			int[] cuiCountpu = new int[cuiList.size()];
			

			String userId = userList.get(i);
			String filename = userId + ".txt";
			
			//System.out.println(filename);
			
			
			fwtotal.write(userId);
			
			fileIO fw = new fileIO(root + "metamapFeatureVectorsTest/" + filename, "w");
			
			fileIO fr1 = new fileIO(root + "metamapOutputs/" + filename, "r");
			
			String ln = "";
			
			while((ln = fr1.read())!=null)
			{

				int[] cuiCount = new int[cuiList.size()];
				String[] parts = ln.split(":");
				fw.write(parts[0]);
				try
				{
					String cuis = parts[1];
					String[] cuilist = cuis.split(",");
					
					for(int j=0; j<cuilist.length; j++)
					{
						if(!cuiList.contains(cuilist[j]))
						{
							//System.out.println(cuilist[j]);
							continue;
						}
						cuiCount[cuiList.indexOf(cuilist[j])]++;
						cuiCountpu[cuiList.indexOf(cuilist[j])]++;
					}
					for(int k=0; k<cuiCount.length; k++)
					{
						fw.write("," + Integer.toString(cuiCount[k]));
					}
					//System.out.println("ok this is working");
					fw.write("\n");
				}catch(Exception e)
				{
					for(int k=0; k<cuiCount.length; k++)
					{
						fw.write("," + Integer.toString(cuiCount[k]));
					}
					fw.write("\n");
					continue;
				}
				
			}
			fw.close();
			for(int k=0; k<cuiCountpu.length; k++)
			{
				fwtotal.write("," + Integer.toString(cuiCountpu[k]));
			}
			fwtotal.write("\n");
		}
		fwtotal.close();
		
	}
}
