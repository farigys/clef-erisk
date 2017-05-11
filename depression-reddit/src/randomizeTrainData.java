import java.io.IOException;
import java.util.*;

public class randomizeTrainData {
	public static void main(String[] args) throws IOException
	{
		ArrayList<String> data = new ArrayList<String>();
		
		String root = "/home/farig/Desktop/reddit data/training-posts-NN/";
		
		fileIO fw = new fileIO(root + "trainDataSetUpdated.txt", "w");
		
		fileIO fr = new fileIO(root + "negativeTrainPostsIndexed.txt", "r");
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			String dataPoint = "0 " + line.trim(); 
			data.add(dataPoint);
		}
		
		fr = new fileIO(root + "positiveTrainPostsIndexed.txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			String dataPoint = "1 " + line.trim(); 
			data.add(dataPoint);
		}
		
		Collections.shuffle(data);
		
		for(int i=0; i<data.size(); i++)
		{
			fw.write(data.get(i) + "\n");
		}
		fw.close();
	}
}
