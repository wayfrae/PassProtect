package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import passProtect.UserManager;

public class UserManagerTest {

	@Test
	public void testHashPasswordOnConstructor() {
		UserManager test = new UserManager("username", "testing");
		UserManager test2 = new UserManager("user", "password");
		assertEquals("dc724af18fbdd4e59189f5fe768a5f8311527050", test.getUserPass());
		assertNotEquals(test.getUserPass(), test2.getUserPass());
	}

	@Test
	public void testValidatePassword() {
		UserManager test = new UserManager("testValidatePassword", "testing");
		test.createUser();
		assertTrue(test.validatePassword());
		
		UserManager test2 = new UserManager("testValidatePassword", "Testing");
		test2.createUser();
		assertFalse(test2.validatePassword());
		
		//test.removeUser();
		//test2.removeUser();
	}
	
	@Test
	public void testCreateUser() {
		UserManager test = new UserManager("testCreateUser", "testing");
		assertTrue("User Not Created", test.createUser());
		assertFalse("User duplicated", test.createUser());
		test.removeUser();		
	}
	
	@Test
	public void testRemoveUser() {
		UserManager test = new UserManager("testRemoveUser", "testing");
		test.createUser();
		assertTrue("User Not Removed", test.removeUser());
		assertFalse("User should not be found.", test.removeUser());		
	}
	
}
