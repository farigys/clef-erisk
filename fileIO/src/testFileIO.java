import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class testFileIO {
	public static void main(String[] args) throws IOException
	{
		String root = "/home/farig/Desktop/";
//		fileIO f = new fileIO(root+"example.txt", "w");
//		f.write("Hello World!");
//		f.write("Ding!");
//		f.close();
//		
//		f = new fileIO(root+"example.txt", "w+");
//		f.write("\nHello Again, World!");	
//		f.close();
//		
//		f = new fileIO(root+"example.txt", "r");
//		String line = "";
//		while((line = f.read())!=null)
//		{
//			System.out.println(line);
//		}
		
		FileWriter fw = new FileWriter(root + "abc.txt", true);
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write("hello\n");
		
		bw.close();
	}
}
