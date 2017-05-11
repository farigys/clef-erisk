import java.io.IOException;
import java.util.*;

//combines multiple occuring stems into one

public class stemCombiner {
	public static void main(String[] args) throws IOException
	{
		ArrayList<String> stems = new ArrayList<String>();
		
		String root = "/home/farig/Desktop/Depression vs Non-depression_ JAN 2015/";
		
		fileIO fr = new fileIO(root + "wordPMIStemmed2.txt", "r");
		
		fileIO fw = new fileIO(root + "wordPMIStemmedFinal2.txt", "w");
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			String stem = line.split("\t")[0];
			if(stems.contains(stem))continue;
			stems.add(stem);
		}
		
		for(int i =0; i<stems.size(); i++)
		{
			fw.write(stems.get(i) + "\n");
		}
		
		fw.close();
	}
}
