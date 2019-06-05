package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Student class.
 * 
 * @author Nick Garner
 *
 */
public class StudentTest {

	/** Test first name */
	private static final String FIRST_NAME = "Stu";
	/** Test last name */
	private static final String LAST_NAME = "Dent";
	/** Test id */
	private static final String ID = "sdent";
	/** Test email */
	private static final String EMAIL = "sdent@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max credits */
	private static final int MAX_CREDITS = 15;
	/** Student test object */
	private Student s;

	/**
	 * Sets up new student object with declared constants for field data before each
	 * test.
	 * 
	 * @throws Exception When any parameter is invalid.
	 */
	@Before
	public void setUp() throws Exception {
		s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		assertEquals(PASSWORD, s.getPassword());
		assertEquals(MAX_CREDITS, s.getMaxCredits());
	}

	/**
	 * Tests full Student constructor with custom maxCredits
	 */
	@Test
	public void testStudentStringStringStringStringStringInt() {
		// Tests null id param
		Student invalid = null;
		try {
			invalid = new Student(FIRST_NAME, LAST_NAME, null, EMAIL, PASSWORD, MAX_CREDITS);
		} catch (IllegalArgumentException e) {
			assertNull(invalid);
		}

		// Tests empty string id param
		invalid = null;
		try {
			invalid = new Student(FIRST_NAME, LAST_NAME, "", EMAIL, PASSWORD, MAX_CREDITS);
		} catch (IllegalArgumentException e) {
			assertNull(invalid);
		}

		// Tests valid input
		try {
			Student valid = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
			assertEquals(FIRST_NAME, valid.getFirstName());
			assertEquals(LAST_NAME, valid.getLastName());
			assertEquals(ID, valid.getId());
			assertEquals(EMAIL, valid.getEmail());
			assertEquals(PASSWORD, valid.getPassword());
			assertEquals(MAX_CREDITS, valid.getMaxCredits());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Tests student constructor with default maxCredits of 18
	 */
	@Test
	public void testStudentStringStringStringStringString() {
		Student credits = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		assertEquals(FIRST_NAME, s.getFirstName());
		assertEquals(LAST_NAME, s.getLastName());
		assertEquals(ID, s.getId());
		assertEquals(EMAIL, s.getEmail());
		assertEquals(PASSWORD, s.getPassword());
		assertEquals(18, credits.getMaxCredits());
	}

//	/**
//	 * Tests getFirstName().
//	 */
//	@Test
//	public void testGetFirstName() {
//		assertEquals(s.getFirstName(), s.getFirstName());
//	}

	/**
	 * Tests setFirstName().
	 */
	@Test
	public void testSetFirstName() {
		// Test that setting firstName to null does not change anything
		try {
			s.setFirstName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		}

		// Test that setting firstName to empty String does not change anything
		try {
			s.setFirstName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		}

//		// Test setting valid firstName
//		s.setFirstName("Fred");
//		assertEquals("Fred", s.getFirstName());
//		assertEquals(LAST_NAME, s.getLastName());
//		assertEquals(ID, s.getId());
//		assertEquals(EMAIL, s.getEmail());
//		assertEquals(PASSWORD, s.getPassword());
//		assertEquals(MAX_CREDITS, s.getMaxCredits());

	}

//	@Test
//	public void testGetLastName() {
//		fail("Not yet implemented");
//	}

	/**
	 * Tests setLastName()
	 */
	@Test
	public void testSetLastName() {
		// Test that setting lastName to null does not change anything
		try {
			s.setLastName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		}

		// Test that setting lastName to empty String does not change anything
		try {
			s.setLastName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		}

//		// Test setting valid lastName
//		s.setLastName("Weasley");
//		assertEquals(FIRST_NAME, s.getFirstName());
//		assertEquals("Weasley", s.getLastName());
//		assertEquals(ID, s.getId());
//		assertEquals(EMAIL, s.getEmail());
//		assertEquals(PASSWORD, s.getPassword());
//		assertEquals(MAX_CREDITS, s.getMaxCredits());
	}

//	@Test
//	public void testGetId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetEmail() {
//		fail("Not yet implemented");
//	}

	/**
	 * Tests setEmail().
	 */
	@Test
	public void testSetEmail() {
		// Test that setting email to null doesn't change anything
		try {
			s.setEmail(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		}

		// Test that setting email to empty string doesn't change anything
		try {
			s.setEmail("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		}

		// Test that setting email with missing @ sign doesn't change anything
		try {
			s.setEmail("studentncsu.edu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		}

		// Test that setting email with missing period doesn't change anything
		try {
			s.setEmail("student@ncsuedu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		}

		// Test that setting email with no period after @ sign doesn't change anything
		try {
			s.setEmail("stu.dent@ncsuedu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		}
//		
//		//Test setting valid email
//		s.setEmail("student@ncsu.edu");
//		assertEquals(FIRST_NAME, s.getFirstName());
//		assertEquals(LAST_NAME, s.getLastName());
//		assertEquals(ID, s.getId());
//		assertEquals("student@ncsu.edu", s.getEmail());
//		assertEquals(PASSWORD, s.getPassword());
//		assertEquals(MAX_CREDITS, s.getMaxCredits());
	}

//	@Test
//	public void testGetPassword() {
//		fail("Not yet implemented");
//	}

	/**
	 * Tests setPassword() method.
	 */
	@Test
	public void testSetPassword() {
		// Test that setting password with null value doesn't change anything
		try {
			s.setPassword(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		}

		// Test that setting password with empty String doesn't change anything
		try {
			s.setPassword("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		}
	}

//	@Test
//	public void testGetMaxCredits() {
//		fail("Not yet implemented");
//	}

	/**
	 * Tests setMaxCredits() method.
	 */
	@Test
	public void testSetMaxCredits() {
		// Test for maxCredits < 3
		try {
			s.setMaxCredits(2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		}

		// Test for maxCredits > 18
		try {
			s.setMaxCredits(19);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(FIRST_NAME, s.getFirstName());
			assertEquals(LAST_NAME, s.getLastName());
			assertEquals(ID, s.getId());
			assertEquals(EMAIL, s.getEmail());
			assertEquals(PASSWORD, s.getPassword());
			assertEquals(MAX_CREDITS, s.getMaxCredits());
		}
	}

	/**
	 * Test that equals() method works for all Student fields.
	 */
	@Test
	public void testEqualsObject() {
		// Create Student test objects
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s3 = new Student("Fred", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s4 = new Student(FIRST_NAME, "Weasley", ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s5 = new Student(FIRST_NAME, LAST_NAME, "fweas", EMAIL, PASSWORD, MAX_CREDITS);
		Student s6 = new Student(FIRST_NAME, LAST_NAME, ID, "fweas@ncsu.edu", PASSWORD, MAX_CREDITS);
		Student s7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "caput draconis", MAX_CREDITS);
		Student s8 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 16);
		Object test = new Object();
//		Student s9 = new Student();
//		Student s10 = new Student();

		// Test identical objects both ways
		assertTrue(s1.equals(s2));
		assertTrue(s2.equals(s1));

		// Test same object
		assertTrue(s1.equals(s1));

		// Test each field
		assertFalse(s1.equals(s3));
		assertFalse(s1.equals(s4));
		assertFalse(s1.equals(s5));
		assertFalse(s1.equals(s6));
		assertFalse(s1.equals(s7));
		assertFalse(s1.equals(s8));

		// Test compare to null object
		assertFalse(s1 == null);

		// Test class mismatch
		assertFalse(s1.equals(test));

//		//Test comparison to null Students
//		assertTrue(s9.equals(s10));
//		assertFalse(s1.equals(s9));
//		
//		//Test null Student email
//		assertFalse(s9.equals(s1));
//		
//		//Test null Student firstName
//		s9.setEmail(EMAIL);
//		assertFalse(s9.equals(s1));
//		
//		//Test null Student ID
//		s9.setFirstName(FIRST_NAME);
//		assertFalse(s9.equals(s1));

		// Test null Student lastName

	}

	/**
	 * Tests that hashCode() method produces same hash value for identical objects
	 * and different hash values for different objects
	 */
	@Test
	public void testHashCode() {
		// Create Student test objects
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s3 = new Student("Fred", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s4 = new Student(FIRST_NAME, "Weasley", ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s5 = new Student(FIRST_NAME, LAST_NAME, "fweas", EMAIL, PASSWORD, MAX_CREDITS);
		Student s6 = new Student(FIRST_NAME, LAST_NAME, ID, "fweas@ncsu.edu", PASSWORD, MAX_CREDITS);
		Student s7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "caput draconis", MAX_CREDITS);
		Student s8 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 16);

		// Test identical objects for same hash code
		assertEquals(s1.hashCode(), s2.hashCode());

		// Test each field for different hash codes
		assertNotEquals(s1.hashCode(), s3.hashCode());
		assertNotEquals(s1.hashCode(), s4.hashCode());
		assertNotEquals(s1.hashCode(), s5.hashCode());
		assertNotEquals(s1.hashCode(), s6.hashCode());
		assertNotEquals(s1.hashCode(), s7.hashCode());
		assertNotEquals(s1.hashCode(), s8.hashCode());
	}

	/**
	 * Tests that toString() method produces expected String output
	 */
	@Test
	public void testToString() {
		String s1 = "Stu,Dent,sdent,sdent@ncsu.edu,pw,15";
		assertEquals(s1, s.toString());
	}

}
