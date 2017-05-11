import java.io.IOException;


public class test {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/reddit data/glove.6B/";
		
		fileIO fr = new fileIO(root + "glove.6B.50d.txt", "r");
		
		String line = "";
		
		int lineCount = 0;
		
		while((line = fr.read())!=null)lineCount++;
		
		System.out.println(lineCount);
	}
}
