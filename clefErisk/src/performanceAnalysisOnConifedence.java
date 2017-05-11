import java.io.IOException;
import java.util.*;

//performance analysis from libsvm output depending on confidence level

public class performanceAnalysisOnConifedence {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/libsvm-3.22/";
		
		fileIO fr = new fileIO(root + "metamapRegexFeaturesValVectorsWeka.txt", "r");
		
		ArrayList<Integer> actualClassList = new ArrayList<Integer>();
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			actualClassList.add(Integer.parseInt(line.split(" ")[0]));
		}
		
		double[] thresholds = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};
		
		
		int[][] predictedClass = new int[9][86];
		
		fr = new fileIO(root + "metamapRegexOutput", "r");
		
		line = fr.read();
		
		int j = 0;
		
		while((line = fr.read())!=null)
		{
			double confidence = Double.parseDouble(line.split(" ")[1]);
			
			for(int i=0; i<thresholds.length; i++)
			{
				double threshold = thresholds[i];
				if(confidence>threshold)predictedClass[i][j] = 1;
				else predictedClass[i][j] = 0;
			}
			j++;
		}
		
//		for(int i=0; i<9; i++)
//		{
//			for(int k=0; k<86; k++)
//			{
//				System.out.print(predictedClass[i][k] + ",");
//			}
//			System.out.println();
//		}
		
		fileIO fw = new fileIO(root + "peformanceMeasures.txt", "w+");
		
		//System.out.println("Accuracy, precision, recall, f-measure");
		
		fw.write("metamap+regex\n");
		
		for(int i=0; i<9; i++)
		{
			int[] predictedValues = predictedClass[i];
			int tp = 0, tn = 0, fp = 0, fn = 0;
			double accuracy, precision, recall, fmeasure;
			for(int k=0; k<predictedValues.length; k++)
			{
				int actualVal = actualClassList.get(k);
				int predictedVal = predictedValues[k];
				if(actualVal == 1 && predictedVal == 1)tp++;
				if(actualVal == 0 && predictedVal == 1)fp++;
				if(actualVal == 0 && predictedVal == 0)tn++;
				if(actualVal == 1 && predictedVal == 0)fn++;
			}
			
			accuracy = (tp + tn)*1.0/(tp+tn+fp+fn);
			precision = (tp*1.0)/(tp+fp);
			recall = (tp * 1.0)/(tp + fn);
			fmeasure = (2.0*precision*recall)/(precision + recall);
			
			fw.write(thresholds[i] + "," + accuracy + "," + precision + "," + recall + "," + fmeasure + "\n");
		}
		fw.close();
	}
}
