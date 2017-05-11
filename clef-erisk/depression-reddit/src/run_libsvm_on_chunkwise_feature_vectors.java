import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class run_libsvm_on_chunkwise_feature_vectors {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/libsvm-3.22/";
		
		runLibsvm("joint", root);
	}
	
	public static void runLibsvm(String feature, String root) throws IOException
	{
		String rootD = "/home/farig/Desktop/reddit_data/performance_measure/" + feature + "_features/";
		
		String trainFile = rootD + "trainVector.txt";
		
		String command = root + "svm-train -b 1 " + trainFile + " " + rootD + "model.model";
		
		//System.out.println(command);
		
		Process proc = Runtime.getRuntime().exec(command);
        
        BufferedReader reader =  new BufferedReader(new InputStreamReader(proc.getInputStream()));

          String line = "";
          while((line = reader.readLine()) != null) {
              System.out.println(line);
          }
		
		for(int i=1; i<=10; i++)
		{
			
			String testFile = rootD + "chunk" + i + "val.txt";
			
			//System.out.println(filename);
			
			//String command = "ping www.google.com";
			
			command = root + "svm-predict -b 1 "  + testFile + " " + rootD + "model.model "
					+ rootD + "output/" + feature + "_output_" + i;
			
			//System.out.println(command);
			
	        proc = Runtime.getRuntime().exec(command);
	        	        
	        reader =  new BufferedReader(new InputStreamReader(proc.getInputStream()));

	          line = "";
	          while((line = reader.readLine()) != null) {
	              System.out.println(line);
	          }
		}
	}
}
