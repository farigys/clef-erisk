import java.io.IOException;
import java.util.*;

//divides users into train and val sets

public class trainValDivider {
	public static void main(String[] args) throws IOException
	{
		ArrayList<String> userList = new ArrayList<String>();
		String root = "/home/farig/Desktop/features/";
		fileIO fr = new fileIO(root + "risk_golden_truth.txt", "r");
		
		String line = "";
		
		while((line = fr.read())!=null)
		{
			userList.add(line.split(" ")[0]);
		}
		fr.close();
		
		Collections.shuffle(userList);
		
		fileIO ftrain = new fileIO(root + "trainList.txt", "w");
		fileIO fval = new fileIO(root + "valList.txt", "w");
		
		for(int i=0; i<400; i++)
		{
			ftrain.write(userList.get(i) + "\n");
		}
		ftrain.close();
		
		for(int i=400; i<userList.size(); i++)
		{
			fval.write(userList.get(i) + "\n");
		}
		fval.close();
	}
	
}
