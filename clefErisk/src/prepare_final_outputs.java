import java.io.IOException;


public class prepare_final_outputs {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/clef erisk results/chunk 10 results/";
		
		fileIO fr = new fileIO(root + "individual/UArizonaE_10.txt", "r");
		
		fileIO fw = new fileIO(root + "UArizonaE_10.txt", "w");
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			String userId = line.split("\t\t")[0];
			String cat = line.split("\t\t")[1];
			
			if(cat.equals("0"))cat = "2";
			
			fw.write(userId + "\t\t" + cat + "\n");
		}
		fw.close();
	}
}
