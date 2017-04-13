package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import passProtect.PasswordManager;

public class testPasswordManger {
	
	@Test
	public void testAdd() {
		PasswordManager user = new PasswordManager("filename.txt");
		PasswordRecord pr = new PasswordRecord("domain", "user", "password");
		user.add(pr);
		assertTrue(user.getRecords().contains(pr));
	}
}
