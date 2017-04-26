/*
 * Assignment: Final Project
 * Class: CSIS-1410-005
 * Programmers: Alan Banner, Alan Bischoff, Zach Frazier, Tim Lawrence
 * Created: Apr 6, 2017
 */
package passProtect;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


public class PasswordManager {
	private List<PasswordRecord> records;

	// File to hold all PasswordRecords
	private File passwordFile;
	// Temporary file used in the Remove method
	private static File tmpFile = new File("./src/files/tmp.txt");

	public PasswordManager(String filename) {
		this.passwordFile = new File("./src/files/" + filename + ".pwr");
		this.records = new ArrayList<PasswordRecord>();

		// Initialize needed vairables

		BufferedReader reader = null;
		FileReader fr = null;
		String line = null;

		try {
			fr = new FileReader(passwordFile);
			reader = new BufferedReader(fr);

			while ((line = reader.readLine()) != null) {
				if (line != "") {
					String[] data = encodeString(unescapeString(line), passwordFile.toString()).split("\t", -1);
					PasswordRecord pr = new PasswordRecord(data[0], data[1], data[2]);
					this.records.add(pr);
				}
			}

			// Have to close our readers and writers in order to use the .toPath
			// methods below
			reader.close();
			fr.close();
		} catch (FileNotFoundException | NoSuchFileException e) {

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<PasswordRecord> search(String query)
	{
		List<PasswordRecord> results = new ArrayList<PasswordRecord>();
		query = query.toLowerCase();
			for(PasswordRecord pr : this.records){
				if(pr.getDomain().toLowerCase().contains(query) || pr.getUsername().toLowerCase().contains(query)){
					results.add(pr);
				}
			}
		
		return results;
	}

	/**
	 * METHOD add adds a Password Record to the passwordFile text file
	 * 
	 * @param record
	 *            pass a PasswordRecord file
	 */
	public boolean add(PasswordRecord record) {
		
		for(PasswordRecord pr : records){
			if(pr.equals(record)){
				return false;
			}
		}
		
		PrintWriter pWriter;
		try {
			pWriter = new PrintWriter(new FileWriter(passwordFile, true));
			pWriter.println(escapeString(encodeString(record.getDomain() + "\t" + record.getUsername() + "\t" + record.getPassword(), passwordFile.toString())));
			pWriter.close();
			this.records.add(record);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;

	}

	/**
	 * METHOD remove searches through the passwordFile and removes the passed
	 * instance of PasswordRecord via completely rewriting the passwordFile to a
	 * temporary file (tmpFile) but passing over the instance of PasswordRecord,
	 * then overwriting the passwordFile with tmpFile.
	 * 
	 * @param record
	 * @throws IOException
	 */
	public void remove(PasswordRecord record) throws IOException {
		// Initialize needed vairables
		BufferedWriter writer = null;
		BufferedReader reader = null;
		FileWriter fw = null;
		FileReader fr = null;
		String line = null;
		String decodedLine = null;

		// Take the input record and split it into parts, turn it into a string.
		String recordString = record.getDomain().toString() + "\t" + record.getUsername().toString() + "\t"
				+ record.getPassword().toString();

		fw = new FileWriter(tmpFile);
		fr = new FileReader(passwordFile);
		writer = new BufferedWriter(fw);
		reader = new BufferedReader(fr);

		while ((line = reader.readLine()) != null) {
			decodedLine = encodeString(unescapeString(line), passwordFile.toString());
			if (decodedLine.contains(recordString)) {
				this.records.remove(record);
			} else {
				writer.write(line + "\n");
			}
		}

		// Have to close our readers and writers in order to use the .toPath
		// methods below
		reader.close();
		writer.close();
		fw.close();
		fr.close();

		// Files must be converted into Paths to make use of the Files.copy
		// method (Very useful)
		Files.copy(tmpFile.toPath(), passwordFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

	}

	public List<PasswordRecord> getRecords() {

		return this.records;
	}

	public void edit(PasswordRecord record) {
		// TODO Do we really need an edit method? Maybe just require delete and
		// re-add
	}
	
	private static String encodeString(String str, String key){
		StringBuilder sb = new StringBuilder(str);
		
		for(int i=0; i<sb.length(); i++){
			sb.setCharAt(i, (char) (sb.charAt(i) ^ key.charAt(i % key.length()) ^ 0x7F));
		}
		
		return sb.toString();
	}
	
	private static String escapeString(String str){
		return str.replace("\177",  "\177\165").replace("\n", "\177\0").replace("\r", "\177\3");
	}
	
	private static String unescapeString(String str){
		if(!str.contains("\177")){
			return str;
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<str.length(); i++){
			char c = str.charAt(i);
			if(c == '\177'){
				i++;
				c = (char) (str.charAt(i) + 10);
			}
			sb.append(c);
		}
		
		return sb.toString();
	}

}
