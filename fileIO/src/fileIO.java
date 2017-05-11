import java.io.*;

public class fileIO {
	private BufferedReader reader = null;
	private BufferedWriter bw = null;
	
	public fileIO(String fileName, String ioType)
	{
		if(ioType.equals("r"))
		{
			try{
				File file = new File(fileName);
				FileInputStream fis = new FileInputStream(file); 
			    this.reader = new BufferedReader(new InputStreamReader(fis));
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		else if(ioType.equals("w"))
		{
			try{
				File file = new File(fileName);
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				this.bw = new BufferedWriter(fw);
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else if(ioType.equals("w+"))
		{
			try{
				File file = new File(fileName);
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
				this.bw = new BufferedWriter(fw);
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
//	public Object open(String fileName, String ioType)
//	{
//		//filename is the absolute path of the file you want to write
//		//ioType is the type of IO you want to perform
//		//available IO types: "r": read, "w": write, "w+": append
//		Object returnObject = null;
//		if(ioType.equals("r"))
//		{
//			try{
//				File file = new File(fileName);
//				FileInputStream fis = new FileInputStream(file); 
//			    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
//			    return reader;
//			}catch(Exception ex){
//				return ex;
//			}
//		}
//		else if(ioType.equals("w"))
//		{
//			try{
//				File file = new File(fileName);
//				// if file doesnt exists, then create it
//				if (!file.exists()) {
//					file.createNewFile();
//				}
//
//				FileWriter fw = new FileWriter(file.getAbsoluteFile());
//				BufferedWriter bw = new BufferedWriter(fw);
//				return bw;
//			}catch(Exception ex)
//			{
//				return ex;
//			}
//		}
//		else if(ioType.equals("w+"))
//		{
//			try{
//				File file = new File(fileName);
//				// if file doesnt exists, then create it
//				if (!file.exists()) {
//					file.createNewFile();
//				}
//
//				FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
//				BufferedWriter bw = new BufferedWriter(fw);
//				return bw;
//			}catch(Exception ex)
//			{
//				return ex;
//			}
//		}
//		else
//		{
//			Exception ex = new NullPointerException();
//			return ex;
//		}
//	}
	
	public void write(String toWrite) throws IOException
	{
		this.bw.write(toWrite);
	}
	
	//public boolean moreLines
	
	public String read() throws IOException
	{
		return this.reader.readLine();
	}
	
	public void close() throws IOException
	{
		//System.out.println(reader);
		try{
			if(!this.reader.equals(null))
			{
				//System.out.println("Trying to close reader");
				this.reader.close();
			}
			if(!this.bw.equals(null))this.bw.close();
		}catch(NullPointerException ex)
		{
			//ex.printStackTrace();
			try{
				this.bw.close();
			}catch(Exception e)
			{
				
			}
			
		}
		
	}
}
