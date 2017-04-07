/*
 * Assignment: PassProtect
 * Class: CSIS-1410-005
 * Programmer: Zach Frazier
 * Created: Apr 6, 2017
 */
package passProtect;

import java.io.File;

/** CLASS PasswordRecord
 *  Creates and stores usernames and passwords for each domain listed.
 */
public class PasswordRecord {
	
	private String domain, username, password;
	private File userFile;
	
	/**
	 * CONSTRUCTOR PasswordRecord
	 * @param domain
	 * @param username
	 * @param password
	 */
	public PasswordRecord(String site, String user, String pass) {
		this.domain = site;
		this.username = user;
		this.password = pass;
	}
	
	
}
