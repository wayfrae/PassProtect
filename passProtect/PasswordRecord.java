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

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public File getUserFile() {
		return userFile;
	}

	public void setUserFile(File userFile) {
		this.userFile = userFile;
	}
	
	
}
