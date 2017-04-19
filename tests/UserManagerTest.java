package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import passProtect.UserManager;

public class UserManagerTest {

	@Test
	public void testHashPasswordOnUserCreation() {
		UserManager test = new UserManager("username", "testing");
		UserManager test2 = new UserManager("user", "password");
		assertEquals("dc724af18fbdd4e59189f5fe768a5f8311527050", test.getUserPass());
		assertNotEquals(test.getUserPass(), test2.getUserPass());
	}

	@Test
	public void testValidatePassword() {
		UserManager test = new UserManager("username", "testing");
		test.createUser();
		assertTrue(test.validatePassword());
		
		UserManager test2 = new UserManager("username", "Testing");
		assertFalse(test2.validatePassword());
		
	}
	
	@Test
	public void testCreateUser() {
		UserManager test = new UserManager("testCreateUser", "testing");
		assertTrue("User Created", test.createUser());
		assertFalse("User duplicated", test.createUser());
		test.removeUser();	
		
	}
	
	@Test
	public void testRemoveUser() {
		UserManager test = new UserManager("testRemoveUser", "testing");
		test.createUser();
		assertTrue("User Not Removed", test.removeUser());
		assertFalse("User still exists", test.removeUser());	
		test.removeUser();
	}
	
}
