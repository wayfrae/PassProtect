package passProtect;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class PasswordManager 
{
	private PasswordRecord records;
	
	//File to hold all PasswordRecords
	private static File passwordFile = new File("./src/files/TestPassManager.txt");
	//Temporary file used in the Remove method
	private static File tmpFile = new File("./src/files/tmp.txt");
	
	
	public PasswordManager(File file)
	{
		
	}
	
	
//	public PasswordRecord search()
//	{
//		TODO
//	}
	
	
	/**
	 * METHOD add adds a Password Record to the passwordFile text file
	 * 
	 * @param record pass a PasswordRecord file
	 */
	public static void add(PasswordRecord record)
	{
		PrintWriter pWriter;
		try 
		{
			pWriter = new PrintWriter(new FileWriter(passwordFile, true));
			pWriter.println(record.getDomain() + "\t" + record.getUsername() + "\t" + record.getPassword());
			pWriter.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
	}

	/**
	 * METHOD remove searches through the passwordFile and removes the passed instance of PasswordRecord
	 * via completely rewriting the passwordFile to a temporary file (tmpFile) but passing over the
	 * instance of PasswordRecord, then overwriting the passwordFile with tmpFile.
	 * 
	 * @param record
	 * @throws IOException
	 */
	public static void remove(PasswordRecord record) throws IOException
	{
		//Initialize needed vairables
		BufferedWriter writer = null;
		BufferedReader reader = null;
		FileWriter fw = null;
		FileReader fr = null;
		String line = null;
		
		//Take the input record and split it into parts, turn it into a string.
		String recordString = record.getDomain().toString() + "\t" + record.getUsername().toString()
				+ "\t" + record.getPassword().toString();
		
		fw = new FileWriter(tmpFile);
		fr = new FileReader(passwordFile);
		writer = new BufferedWriter(fw);
		reader = new BufferedReader(fr);
		
		
			while ((line = reader.readLine()) != null) 
			{
				if(line.contains(recordString))
				{
					//Do nothing
				} else
				{
					writer.write(line + "\n");
				}
			}
			
		//Have to close our readers and writers in order to use the .toPath methods below
		reader.close();
		writer.close();
		fw.close();
		fr.close();
			
		//Files must be converted into Paths to make use of the Files.copy method (Very useful)
		Files.copy(tmpFile.toPath(), passwordFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
		
		
	}
	
	public void edit(PasswordRecord record)
	{
		//TODO Do we really need an edit method? Maybe just require delete and re-add
	}
	
	
	
	
}
