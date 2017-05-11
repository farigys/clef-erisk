import java.io.IOException;
import java.io.File;
import java.util.*;

public class extract_tru_positives_and_negatives {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/reddit data/";
		
		ArrayList<String> posUserList = new ArrayList<String>();
		ArrayList<String> negUserList = new ArrayList<String>();
		
		fileIO fr = new fileIO(root + "testUserListNegative.txt", "r");
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			negUserList.add(line);
		}
		//System.out.println(negUserList.size());
		
		fr.close();
		
		fr = new fileIO(root + "testUserListPositive.txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			posUserList.add(line);
		}
		//System.out.println(posUserList.size());
		
		fr.close();
		/////////////////////////////////////////////////////////////////////////////
		/////////////////////Positive starts/////////////////////////////////////////
		
		int postCount = 0; //post count is zero-indexed
		int totalPostCount = 0;
		
		fileIO fw = new fileIO(root + "training posts/posTrainPosts.txt","w");
		
		for(int i=0; i<posUserList.size(); i++)
		{
			postCount = 0;
			if(posUserList.get(i).equals("train_subject9115.txt"))continue;
			//System.out.println(posUserList.get(i));
			if(totalPostCount >= 1000)break;
			
			ArrayList<Integer> postList = new ArrayList<Integer>();
			
			fileIO f = new fileIO(root + "libsvm_vectors/outputs_positive/" + posUserList.get(i),"r");
			
			line = f.read();
			
			System.out.println(line);
			
			if(!line.equals("labels 1 0"))System.out.println("Genjam");
			
			while((line = f.read())!=null)
			{
				String[] parts = line.split(" ");
				//System.out.println(parts[0]);
				if(Double.parseDouble(parts[1])>=0.7)
				{
					postList.add(postCount);
					totalPostCount++;
				}
				postCount++;
			}
			
			//System.out.println(postList);
			
			f = new fileIO(root + "positive_examples_anonymous_chunks_text/" + posUserList.get(i),"r");
			
			line = "";
			
			int postCount1 = 0;
			
			//String s = "--------------------";
			
			//System.out.println(s.equals("-------------------------"));
			
			while((line = f.read())!=null)
			{
				//if(postCount==titleList.size())break;
				if(!postList.contains(postCount1))
				{
					if(line.equals("--------------------"))
					{
						//System.out.println("Dhuksi");
						postCount1++;
						continue;
					}
				}
				else
				{
					if(line.equals("--------------------"))
					{
						postCount1++;
						fw.write("--------------------\n");
						continue;
					}
					else
						fw.write(line + "\n");
				}
				
				
			}
		}
		fw.close();
		//System.out.println(totalPostCount);
		System.out.println("------------------");
		/////////////////////////////////////////////////////////////////////////////
		/////////////////////Negative starts/////////////////////////////////////////
		postCount = 0; //post count is zero-indexed
		totalPostCount = 0;
		
		fw = new fileIO(root + "training posts/negTrainPosts.txt","w");
		
		for(int i=0; i<negUserList.size(); i++)
		{
			postCount = 0;
			//System.out.println(negUserList.get(i));
			if(totalPostCount >= 1000)break;
			
			ArrayList<Integer> postList = new ArrayList<Integer>();
			
			fileIO f = new fileIO(root + "libsvm_vectors/outputs_negative/" + negUserList.get(i),"r");
			
			line = f.read();
			
			System.out.println(line);
			
			if(!line.equals("labels 1 0"))System.out.println("Genjam");
			
			while((line = f.read())!=null)
			{
				String[] parts = line.split(" ");
				if(Double.parseDouble(parts[2])>=0.7)
				{
					postList.add(postCount);
					totalPostCount++;
				}
				postCount++;
			}
			
			f = new fileIO(root + "negative_examples_anonymous_chunks_text/" + negUserList.get(i),"r");
			
			line = "";
			
			int postCount1 = 0;
			
			//String s = "--------------------";
			
			//System.out.println(s.equals("-------------------------"));
			
			while((line = f.read())!=null)
			{
				//if(postCount==titleList.size())break;
				if(!postList.contains(postCount1))
				{
					if(line.equals("--------------------"))
					{
						//System.out.println(postCount);
						postCount1++;
						continue;
					}
				}
				else
				{
					if(line.equals("--------------------"))
					{
						postCount1++;
						fw.write("--------------------\n");
						continue;
					}
					else
						fw.write(line + "\n");
				}
				
				
			}
		}
		fw.close();
		//System.out.println(totalPostCount);
	}
	
}
