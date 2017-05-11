import java.io.*;


public class divide_train_and_val {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/reddit data/training posts/";
		
		fileIO fr = new fileIO(root + "positiveTrainPostsIndexed.txt", "r");
		
		fileIO fw1 = new fileIO(root + "positiveTrainPostsIndexed1.txt", "w");
		
		fileIO fw2 = new fileIO(root + "positiveValPostsIndexed.txt", "w");
		
		String line = "";
		
		int lineCount = 0;
		
		while((line = fr.read())!=null)
		{
			if(lineCount<800)fw1.write(line + "\n");
			else fw2.write(line + "\n");
			lineCount++;
		}
		fw1.close();
		fw2.close();
		
		fr = new fileIO(root + "negativeTrainPostsIndexed.txt", "r");
		
		fw1 = new fileIO(root + "negativeTrainPostsIndexed1.txt", "w");
		
	    fw2 = new fileIO(root + "negativeValPostsIndexed.txt", "w");
		
		line = "";
		
		lineCount = 0;
		
		while((line = fr.read())!=null)
		{
			if(lineCount<800)fw1.write(line + "\n");
			else fw2.write(line + "\n");
			lineCount++;
		}
		fw1.close();
		fw2.close();
	}
}
