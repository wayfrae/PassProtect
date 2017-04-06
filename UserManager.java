package passProtect;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * CLASS UserManager
 *
 */
public class UserManager {
	/** FIELD userName holds the user's login name */
	private String userName;
	/** FIELD userPass holds the user's password */
	private String userPass;
	/**
	 * FIELD userFile holds PasswordManager object for retrieving specific user
	 * data
	 */
	private PasswordManager userFile;

	List<UserManager> masterFile = new LinkedList<>();

	/**
	 * CONSTRUCTOR UserManager Logs the user into the UserManager if the
	 * username and password match a record on file.
	 * 
	 * @param user
	 * @param pass
	 */
	public UserManager(String user, String pass) {
		this.userName = user;
		this.userPass = hashPassword(pass);
		
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userPass
	 */
	public String getUserPass() {
		return this.userPass;
	}

	/**
	 * @param userPass
	 *            the userPass to set
	 */
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	/**
	 * METHOD validatePassword verifies that the username and password used to
	 * create the object match a username and password in the master.txt file
	 * 
	 * @return true if the username and password matches a username and password
	 *         in the file.
	 */
	public boolean validatePassword() {
		List<UserManager> masterFile = new LinkedList<>();
		try (Scanner reader = new Scanner(UserManager.class.getResourceAsStream("/files/master.txt"))) {
			while (reader.hasNextLine()) {
				String[] data = reader.nextLine().split("\t");
				masterFile.add(new UserManager(data[0], data[1]));				
			}
		}
		for (UserManager u : masterFile) {
			System.out.println("U: " + u.getUserName() +" " + u.getUserPass());
			System.out.println("this: " + this.getUserName() +" and " + this.getUserPass());
			if (u.getUserName().equals(this.getUserName())) {				
				if (u.getUserPass().equals(this.getUserPass())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * METHOD isAvailable verifies that the username has not already been used
	 * 
	 * @return true if the username is available for use
	 */
	public boolean isAvailable() {
		List<UserManager> masterFile = new LinkedList<>();
		try (Scanner reader = new Scanner(UserManager.class.getResourceAsStream("/files/master.txt"))) {
			while (reader.hasNextLine()) {
				String[] data = reader.nextLine().split("\t");
				masterFile.add(new UserManager(data[0], data[1]));				
			}
		}
		for (UserManager u : masterFile) {
			System.out.println(u.getUserName() + " vs " + this.getUserName());
			if (u.getUserName().equals(this.getUserName())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * METHOD createUser adds username and password to the master.txt file
	 * 
	 * @return
	 */
	public boolean createUser() {
		if (isAvailable()) {
			try {
				PrintWriter writer = new PrintWriter(new FileWriter("./src/files/master.txt", true));
				writer.println(this.getUserName() + "\t" + this.getUserPass());
				writer.close();
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public String hashPassword(String pass) {
		String hashedPass = "";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] bytes = md.digest(pass.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			hashedPass = sb.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		return hashedPass;

	}

}
