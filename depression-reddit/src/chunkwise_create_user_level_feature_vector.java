import java.io.File;
import java.io.IOException;
import java.util.*;


public class chunkwise_create_user_level_feature_vector {
	
	public static ArrayList<String> trainList = new ArrayList<String>();
	public static ArrayList<String> valList = new ArrayList<String>();
	public static HashMap<String, String> golden_truth = new HashMap<String, String>();
	public static HashMap<String, String> write_count = new HashMap<String, String>();
	
	public static void main(String[] args) throws IOException
	{	
		String root = "/home/farig/Desktop/reddit data/performance_measure/";
		
//		for(int i=1; i<=10; i++)
//		{
//			File file = new File(root + "joint_features/chunk " + i);
//			if(!file.exists())file.mkdir();
//		}
//		
//		for(int i=1; i<=10; i++)
//		{
//			File file = new File(root + "joint_features/chunk " + i);
//			if(!file.exists())file.mkdir();
//		}
		
		
		String line;
		
		fileIO fr = new fileIO(root + "trainList.txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			trainList.add(line + ".txt");
		}
		
		fr = new fileIO(root + "valList.txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			valList.add(line + ".txt");
		}
		
		fr = new fileIO(root + "risk_golden_truth.txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			golden_truth.put(line.split(" ")[0], line.split(" ")[1]);
		}
		
		fr = new fileIO(root + "writings-per-subject-all-train.txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			write_count.put(line.split("\t\t")[0], line.split("\t\t")[1]);
		}
		
		
		//featureVectorBuilderPerUser("metamap",root, 1, 0);
		//featureVectorBuilderPerUser("regex",root, 1, 0);
		//featureVectorBuilderPerUser("metamap",root, 10, 1);
		//featureVectorBuilderPerUser("regex",root, 10, 1);
		
		joinFeatures();
		for(int c=1; c<=10; c++)
		{
			String input = root + "joint_features/chunk" + c + "val.arff";
			String output = root + "joint_features/chunk" + c + "val.txt";
			
			convertArffToLibsvm(input, output);
		}
		convertArffToLibsvm(root + "joint_features/trainVector.arff", root + "joint_features/trainVector.txt");
		
	}
	
	private static void convertArffToLibsvm(String input, String output) throws IOException {
		fileIO f = new fileIO(input, "r");
		fileIO fw = new fileIO(output, "w");
		
		String line  = "";
		
		while((line = f.read())!=null)
		{
			if(line.startsWith("@") || line.equals(""))continue;
			String[] parts = line.split(",");
			fw.write(parts[parts.length-1]);
			for(int i=0; i<parts.length-1; i++)
			{
				fw.write(" " + (i+1) + ":" + parts[i]);
			}
			fw.write("\n");
		}
		fw.close();
		
	}

	public static void featureVectorBuilderPerUser(String feature, String root, int chunkStart, int train) throws IOException
	{
		String separator = "";
		if(feature.equals("metamap"))separator = ",";
		else separator = ":";
		
		for(int chunk=chunkStart; chunk<=10; chunk++)
		{
			String writeD = root + feature + "_features/";
			
			File folder = new File(root + feature + "/chunk " + chunk + "/");
			
			fileIO fw, fw1;
			
			ArrayList<String> userList;
			
			if(train == 0)
			{
				fw = new fileIO(writeD + "chunk" + chunk + "val.txt","w");
				fw1 = new fileIO(writeD + "chunk" + chunk + "val.arff","w");
				userList = new ArrayList<String>(valList);
			}
			else 
			{
				fw = new fileIO(writeD + "trainVector.txt","w");
				fw1 = new fileIO(writeD + "trainVector.arff","w");
				userList = new ArrayList<String>(trainList);
			}
			
			if(feature.equals("metamap"))
			{
				fw1.write("@relation clef-erisk-metamap\n\n");
				
				for(int k=0; k<404; k++)
				{
					fw1.write("@attribute metamap" + (k+1) + " real\n");
				}
				
				fw1.write("@attribute class {0,1}\n\n@data\n");
			}
			else if(feature.equals("regex"))
			{
				fw1.write("@relation clef-erisk-regex\n\n");
				
				for(int k=0; k<110; k++)
				{
					fw1.write("@attribute regex" + (k+1) + " real\n");
				}
				
				fw1.write("@attribute class {0,1}\n\n@data\n");
			}
			
			
			for(String filename:userList)
			{
				fileIO f = new fileIO(root + feature + "/chunk " + chunk + "/" + filename, "r");
				
				String l = f.read();
				
				String[] parts = l.split(separator);
				
				int[] count = new int[parts.length-1];
				
				for(int i=1; i<parts.length; i++)
				{
					count[i-1] = Integer.parseInt(parts[i]);
				}
				
				//System.out.println(count.length);
				
				while((l = f.read())!=null)
				{
					parts = l.split(separator);
					
					//int[] count = new int[parts.length-1];
					
					for(int i=1; i<parts.length; i++)
					{
						count[i-1] += Integer.parseInt(parts[i]);
					}
				}
				String userId = filename.split("\\.")[0];
				//fw.write(filename.split("\\.")[0]);
				fw.write(golden_truth.get(userId));
				
				for(int j=0; j<count.length; j++)
				{
					fw.write(" " + Integer.toString(j+1) + ":" + (count[j]*1.0)/Double.parseDouble(write_count.get(userId)));
				}
				fw.write("\n");
				
				for(int j=0; j<count.length; j++)
				{
					fw1.write((count[j]*1.0)/Double.parseDouble(write_count.get(userId)) + ",");
				}
				fw1.write(golden_truth.get(userId) + "\n");
				
			}
			fw.close();
			fw1.close();
			
			
		}
	}
	
	public static void joinFeatures() throws IOException
	{
		String root = "/home/farig/Desktop/reddit data/performance_measure/";
		String readDm = root + "metamap_features/";
		String readDr = root + "regex_features/";
		
		String writeD = root + "joint_features/";
		
		for(int chunk=1; chunk<=10; chunk++)
		{
			ArrayList<String> readm = new ArrayList<String>();
			ArrayList<String> readr = new ArrayList<String>();
			
			fileIO frm = new fileIO(readDm + "chunk" + chunk + "val.arff", "r");
			fileIO frr = new fileIO(readDr + "chunk" + chunk + "val.arff", "r");
			
			String line = "";
			
			while((line = frm.read())!=null)
			{
				if(line.startsWith("@") || line.equals(""))continue;
				readm.add(line.substring(0, line.length()-1));
			}
			
			line = "";
			
			while((line = frr.read())!=null)
			{
				if(line.startsWith("@") || line.equals(""))continue;
				readr.add(line);
			}
			
			if(readm.size() != readr.size())System.out.println("Genjam");
			
			fileIO fw = new fileIO(writeD + "chunk" + chunk + "val.arff","w");
			
			fw.write("@relation clef-erisk-joint\n\n");
			
			for(int k=0; k<514; k++)
			{
				fw.write("@attribute joint" + (k+1) + " real\n");
			}
			
			fw.write("@attribute class {0,1}\n\n@data\n");
			
			for(int x=0; x<readm.size(); x++)
			{
				//String temp = readm.get(x) + readr.get(x);
				//System.out.println(temp.split(",")[514]);
				fw.write(readm.get(x) + readr.get(x) + "\n");
			}
			fw.close();
			
		}
		//////////////////////////////////////////////////////////////////////
		
		ArrayList<String> readm = new ArrayList<String>();
		ArrayList<String> readr = new ArrayList<String>();
		
		fileIO frm = new fileIO(readDm + "trainVector.arff", "r");
		fileIO frr = new fileIO(readDr + "trainVector.arff", "r");
		
		String line = "";
		
		while((line = frm.read())!=null)
		{
			if(line.startsWith("@") || line.equals(""))continue;
			readm.add(line.substring(0, line.length()-1));
		}
		
		line = "";
		
		while((line = frr.read())!=null)
		{
			if(line.startsWith("@") || line.equals(""))continue;
			readr.add(line);
		}
		
		if(readm.size() != readr.size())System.out.println("Genjam");
		
		fileIO fw = new fileIO(writeD + "trainVector.arff","w");
		
		fw.write("@relation clef-erisk-joint\n\n");
		
		for(int k=0; k<514; k++)
		{
			fw.write("@attribute joint" + (k+1) + " real\n");
		}
		
		fw.write("@attribute class {0,1}\n\n@data\n");
		
		for(int x=0; x<readm.size(); x++)
		{
			fw.write(readm.get(x) + readr.get(x) + "\n");
		}
		fw.close();
	}
}





















