/*
 * Assignment: Final Project
 * Class: CSIS-1410-005
 * Programmers: Alan Banner, Alan Bischoff, Zach Frazier, Tim Lawrence
 * Created: Apr 6, 2017
 */
package passProtect;

/** CLASS PasswordRecord
 *  Creates and stores usernames and passwords for each domain listed.
 */
public class PasswordRecord {
	
	private String domain, username, password;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PasswordRecord other = (PasswordRecord) obj;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
}
