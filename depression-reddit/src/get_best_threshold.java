import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;


public class get_best_threshold {
	public static void main(String[] args) throws IOException
	{
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		
		String root = "/home/farig/Desktop/clef/Latency_metamap_ngram/";
		
		fileIO fr = new fileIO(root + "complete_decision_vector.txt", "r");
		
		String line = "";
		
		HashMap<String, ArrayList<Double>> vectors = new HashMap<String, ArrayList<Double>>();
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split(",");
			
			String username = parts[0];
			
			ArrayList<Double> temp = new ArrayList<Double>();
			
			for(int i=1; i<parts.length; i++)temp.add(Double.parseDouble(parts[i]));
			
			vectors.put(username, temp);
		}
		
		HashMap<String, Integer> goldenTruth = new HashMap<String, Integer>();
		
		ArrayList<String> users = new ArrayList<String>();
		
		fr = new fileIO(root + "risk_golden_truth.txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split(" ");
			
			users.add(parts[0]);
			
			goldenTruth.put(parts[0], Integer.parseInt(parts[1]));
		}
		
		HashMap<String, Integer> writeCount = new HashMap<String, Integer>();
		
		//ArrayList<String> totalCount = new ArrayList<String>();
		
		fr = new fileIO(root + "writings-per-subject-all-train.txt", "r");
		
		line = "";
		
		while((line = fr.read())!=null)
		{
			String[] parts = line.split("\t\t");
			
			writeCount.put(parts[0], Integer.parseInt(parts[1]));
		}
		
		
		
		
		
//		double th = 0.01;
//		
//		while(th<1.0)
//		{
//			int TPT=0, TNT=0, FPT=0, FNT=0;
//			double acct, prect, rect, f1t;
//			for(String username : users)
//			{
//				int golden = goldenTruth.get(username);
//				
//				double dec = vectors.get(username).get(1998);
//				if(golden == 1 && dec>=th)TPT++;
//				else if(golden == 1 && dec<th)FNT++;
//				else if(golden == 0 && dec>=th)FPT++;
//				else if(golden == 0 && dec<th)TNT++;
//			}
//			
//			acct = (TPT+TNT)*1.0/(TPT+TNT+FPT+FNT);
//			prect = (TPT*1.0)/(TPT+FPT);
//			rect = (TPT*1.0)/(TPT+FNT);
//			f1t = (2*prect*rect)/(prect+rect);
//			
//			System.out.println(df.format(th) + "," + TPT + "," + TNT + "," + FPT + "," + FNT);
//			
//			//System.out.println(df.format(th) + "," + acct + "," + prect + "," + rect + "," + f1t);
//			
//			th+=0.01;
//		}
//		
		//int window;
		
		int[] windows = {0,17,73};
		
		fileIO fw = new fileIO(root + "window_performance_for_0.5_best_three.csv", "w");
		
		

				
		//for(window=0; window<100; window++)
		for(int window : windows)
		{
			fileIO fw1 = new fileIO(root + "post_count_for_depressed_users_" + window + ".txt", "w");
			double threshold = 0.5;
			
			//while(threshold < 1.0)
			{
				int TP=0, TN=0, FP=0, FN=0;
				double acc, prec, rec, f1, f1_penalized;
				
				ArrayList<Double> penalties = new ArrayList<Double>();
				
				for(String username : users)
				{
					int depFlag = 0;
					
					//System.out.println(username);
					
					ArrayList<Double> decisions = vectors.get(username);
					
					int golden = goldenTruth.get(username);
					
					for(int i=0; i<decisions.size() - window; i++)
					{
						double decision = decisions.get(i);
						if(decision>=threshold)
						{
							int brokenFlag = 0;
							for(int w=1; w<=window; w++)
							{
								double nextDecision = decisions.get(i+w);
								if(nextDecision<threshold)
								{
									brokenFlag = 1;
									break;
								}
							}
							if(brokenFlag == 0)
							{
								if(golden == 1)
								{
									TP++;
									//if(threshold==0.5)
									{
										//System.out.println("Dhuksi");
										fw1.write(username + "," + (i+1) + ",1" + "\n"); 
										penalties.add(predictScore(Math.min(i+window, writeCount.get(username))));
									}
								}
								else FP++;
								depFlag = 1;
								break;
							}
						}
					}
						
						//double[] decs = new double[window+1];
						
//						for(int w=1; w<window; w++)
//						{
//							if(decisions.get(i+w)<threshold)continue;
//							else brokenFlag = 0;
//							//decs[w] = decisions.get(i+w);
//						}
						
						
						
						
						//if(decision>=threshold && i<1999-window)
//						if(brokenFlag == 0)
//						{
//							{
//								if(golden == 1)TP++;
//								else FP++;
//								depFlag = 1;
//								break;
//							}
//							
//						}
//					}
					if(depFlag == 1)continue;
					if(golden == 0)TN++;
					else
					{
						FN++;
						fw1.write(username + "," + writeCount.get(username) + ",0" + "\n");  
						penalties.add(predictScore(writeCount.get(username)));
					}
				}
				
				//System.out.println(df.format(threshold) + " " + TP + " " + TN + " " + FP + " " + FN);
				
				if(penalties.size()!=83)System.out.println("Genjam");
				
				double avg = 0.0;
				
				for(double pen : penalties)avg+=pen;
				
				avg = avg/(penalties.size()*1.0);
				
				acc = (TP+TN)*1.0/(TP+TN+FP+FN);
				prec = (TP*1.0)/(TP+FP);
				rec = (TP*1.0)/(TP+FN);
				f1 = (2*prec*rec)/(prec+rec);
				f1_penalized = f1*avg;
				//System.out.println(df.format(threshold) + "_" + window + "," + acc + "," + prec + "," + rec + "," + f1 + "\n");
				
				fw.write(df.format(threshold) + "_" + window + "," + acc + "," + prec + "," + rec + "," + f1 + "," + f1_penalized +  "," + avg + "," + TP + "," + TN + "," + FP + "," + FN + "\n");
				
				threshold+=0.01;
			}

			fw1.close();
		}
		
		fw.close();
		
		ArrayList<Double> pen = new ArrayList<Double>();
		
		for(String user : users)
		{
			if(goldenTruth.get(user) == 1)
			{
				pen.add(predictScore(writeCount.get(user)));
			}
		}
		
		double avg = 0.0;
		
		for(double p : pen)avg+=p;
		
		avg = avg/(pen.size()*1.0);
		
		System.out.println(pen.size() + " " + avg);
	}
	
	public static double predictScore(int postCount)
	{
		return 1.0 - (-1.0 + 2.0/(1.0 + Math.exp(-0.0078*((postCount+1) -1.0)))); //+1 is for 0-indexed post count
		
	}
}

