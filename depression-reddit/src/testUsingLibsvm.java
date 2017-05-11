//Tests with the same training model but with multiple test models in a loop

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class testUsingLibsvm {
	public static void main(String[] args) throws IOException
	{
		//change negative to positive while doing positive results
		
		String root = "/home/farig/Desktop/libsvm-3.22/";
		
		File file = new File(root + "libsvm_vectors/test_negative/");
		
		File[] listOfFiles = file.listFiles();
		
		for(int i=0; i<listOfFiles.length; i++)
		{
			String filename = listOfFiles[i].getName();
			
			//System.out.println(filename);
			
			//String command = "ping www.google.com";
			
			String command = root + "svm-predict -b 1 "  + root + "libsvm_vectors/test_negative/" + filename + " " + root + "libsvm_vectors/model.model "
					+ root + "libsvm_vectors/outputs_negative/" + filename;
			
			//System.out.println(command);
			
	        Process proc = Runtime.getRuntime().exec(command);
	        	        
	        BufferedReader reader =  new BufferedReader(new InputStreamReader(proc.getInputStream()));

	          String line = "";
	          while((line = reader.readLine()) != null) {
	              System.out.println(line);
	          }
		}
		
		
        
	}
}
