import java.io.File;
import java.io.IOException;
import java.util.*;


public class svm_data_on_lstm_embeddings {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/reddit data/clef_text/vectors/";
		
		fileIO fw = new fileIO(root + "avg_embeddings_per_user_combined.txt", "w");
		
		File file = new File(root + "lstm_embeddings_combined/");
		
		File[] listFiles = file.listFiles();
		
		for(File f : listFiles)
		{
			
			ArrayList<ArrayList<Double>> embeddingMatrix = new ArrayList<ArrayList<Double>>();
			
			String filename = f.getName();
			
			if(!filename.startsWith("train"))continue;
			
			fw.write(filename.split("\\.")[0]);
			
			fileIO fr = new fileIO(root + "lstm_embeddings/" + filename, "r");
			
			String line = "";
			
			while((line = fr.read())!=null)
			{
				ArrayList<Double> temp = new ArrayList<Double>();
				
				String[] parts = line.split(" ");
				
				for(String part : parts)
				{
					temp.add(Double.parseDouble(part));
				}
				embeddingMatrix.add(temp);
			}
			
//			for(int i=0; i<embeddingMatrix.size() - 1; i++)
//			{
//				if(embeddingMatrix.get(i).size() != embeddingMatrix.get(i+1).size())System.out.println("Genjam");
//				//if(embeddingMatrix.get(i).size() != 64)System.out.print("size: " + embeddingMatrix.get(i).size() + " ");
//			}
//			//System.out.println();
			
			//System.out.println(embeddingMatrix.size() + " " + embeddingMatrix.get(0).size());
			
			int size = embeddingMatrix.get(0).size();
			
			//double[] addition = new double[size];
			
			double addition;
			
			for(int j=0; j<size; j++)
			{
				addition = 0;
				for(int i=0; i<embeddingMatrix.size(); i++)
				{
					addition += embeddingMatrix.get(i).get(j);
				}
				addition = addition/embeddingMatrix.size();
				
				fw.write(" " + addition);
			}
			fw.write("\n");
		}
		fw.close();
	}
}
