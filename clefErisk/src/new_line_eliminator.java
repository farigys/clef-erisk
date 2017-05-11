import java.io.IOException;

//eliminates new line from LSTM outputs

public class new_line_eliminator {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/clef erisk results/chunk 5 results/individual/";
		fileIO fr = new fileIO(root + "regexOutputTestGRUTemp.txt", "r");
		fileIO fw = new fileIO(root + "regexOutputTestGRU.txt", "w");
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			if(line.equals(""))continue;
			fw.write(line + "\n");
		}
		fw.close();
		
		fr = new fileIO(root + "jointOutputTestGRUTemp.txt", "r");
		fw = new fileIO(root + "jointOutputTestGRU.txt", "w");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			if(line.equals(""))continue;
			fw.write(line + "\n");
		}
		fw.close();
	}
}
