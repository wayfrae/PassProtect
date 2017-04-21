package passProtect;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
	
	String master = "./src/files/master.txt";
	String temp = "./src/files/temp.txt";
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
		this.userPass = hashPassword(userPass);
	}

	/**
	 * METHOD validatePassword verifies that the username and password used to
	 * create the object match a username and password in the master.txt file
	 * 
	 * @return true if the username and password matches a username and password
	 *         in the file.
	 */
	public boolean validatePassword() {
		
		try (Scanner reader = new Scanner(new FileInputStream(master))) {
			while (reader.hasNextLine()) {
				String[] data = reader.nextLine().split("\t");
				if (data[0].equals(this.getUserName()) && data[1].equals(this.getUserPass())) {
					return true;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * METHOD createUser adds username and password to the master.txt file
	 * 
	 * @return boolean value of whether the user was created successfully or not
	 */
	public boolean createUser() {
		BufferedWriter writer = null;
		boolean success = false;
		if (isAvailable()) {
			try {
				writer = new BufferedWriter(new FileWriter(master, true));
				writer.write(this.getUserName() + "\t" + this.getUserPass() + "\n");
				success = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (success) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * METHOD removeUser removes username and password from the master.txt file
	 * 
	 * @return boolean value of whether the user was removed successfully or not
	 */
	public boolean removeUser() {
		BufferedWriter writerTemp = null;
		BufferedWriter writer = null;
		if (!isAvailable()) {
			// System.out.println("Is Not Available!");
			try (Scanner reader = new Scanner(new FileInputStream(master))) {
				writerTemp = new BufferedWriter(new FileWriter("./src/files/temp.txt"));
				while (reader.hasNextLine()) {
					String[] data = reader.nextLine().split("\t");
					// System.out.println("Data: " + data[0] + " getUserName():
					// " + this.getUserName());
					if (!data[0].equals(this.getUserName())) {
						writerTemp.write(data[0] + "\t" + data[1] + "\n");
					}

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					writerTemp.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try (Scanner readerTemp = new Scanner(new FileInputStream("./src/files/temp.txt"))) {
				writer = new BufferedWriter(new FileWriter(master, false));
				while (readerTemp.hasNextLine()) {
					String line = readerTemp.nextLine();
					// System.out.println(line);
					writer.write(line + "\n");
				}
				writer.close();
				return true;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return false;
	}

	/**
	 * METHOD hashPassword hashes the provided string using SHA-1
	 * 
	 * @return the hashed string
	 */
	private String hashPassword(String pass) {
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

	/**
	 * METHOD isAvailable verifies that the username has not already been used
	 * 
	 * @return true if the username is available for use
	 */
	private boolean isAvailable() {
		try (Scanner reader = new Scanner(new FileInputStream(master))) {
			while (reader.hasNextLine()) {
				String[] data = reader.nextLine().split("\t");
				if (data[0].equals(this.getUserName())) {
					return false;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userPass == null) ? 0 : userPass.hashCode());
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
		UserManager other = (UserManager) obj;
		if (userPass == null) {
			if (other.userPass != null)
				return false;
		} else if (!userPass.equals(other.userPass))
			return false;
		return true;
	}

}
