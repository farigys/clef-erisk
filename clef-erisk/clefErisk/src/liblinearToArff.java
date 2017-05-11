import java.io.IOException;
import java.util.*;

//converts liblinear files to arff files

public class liblinearToArff {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/features/";
		
		fileIO fr = new fileIO(root + "regexFeaturesLibSVMVal.txt", "r");
		fileIO fw = new fileIO(root + "regexFeaturesLibSVMValWeka.arff", "w");
		
		fw.write("@relation clef-erisk-regex\n\n");
		
		for(int i=0; i<110; i++)
		{
			fw.write("@attribute regex" + (i+1) + " real" + "\n");
		}
		
		fw.write("@attribute class {0,1}\n\n@data\n");
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split(" ");
			String classtype = parts[0];
			
			for(int j=1; j<parts.length; j++)
			{
				String[] parts2 = parts[j].split(":");
				fw.write(parts2[1] + ",");
			}
			fw.write(classtype + "\n");
		}
		fw.close();
	}
}
