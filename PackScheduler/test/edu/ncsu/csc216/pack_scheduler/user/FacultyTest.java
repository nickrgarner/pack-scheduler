package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests Faculty class for proper functionality and exception handling.
 * 
 * @author Nick Garner
 *
 */
public class FacultyTest {

	/** Test first name */
	private static final String FIRST_NAME = "Charles";
	/** Test last name */
	private static final String LAST_NAME = "Xavier";
	/** Test id */
	private static final String ID = "cxavier";
	/** Test email */
	private static final String EMAIL = "cxavier@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max courses */
	private static final int MAX_COURSES = 1;
	/** Faculty test object */
	private Faculty f;

	/**
	 * Runs before each test to instantiate a Faculty object for testing
	 * 
	 * @throws Exception If error occurs during setup
	 */
	@Before
	public void setUp() throws Exception {
		f = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
	}

	/**
	 * Tests Faculty constructor for proper setting of fields
	 */
	@Test
	public void testFaculty() {
		assertEquals(FIRST_NAME, f.getFirstName());
		assertEquals(LAST_NAME, f.getLastName());
		assertEquals(ID, f.getId());
		assertEquals(PASSWORD, f.getPassword());
		assertEquals(MAX_COURSES, f.getMaxCourses());
	}

	/**
	 * Tests that setMaxCourses properly throws exception for value out of bounds
	 * and properly changes maxCourses field
	 */
	@Test
	public void testSetMaxCourses() {
		// Bad values
		try {
			f.setMaxCourses(0);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid max courses", e.getMessage());
		}
		try {
			f.setMaxCourses(4);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Invalid max courses", e.getMessage());
		}
		// Good value
		f.setMaxCourses(2);
		assertEquals(2, f.getMaxCourses());
	}

	/**
	 * Tests that toString returns the proper String representation of the Faculty
	 * object
	 */
	@Test
	public void testToString() {
		assertEquals(FIRST_NAME + "," + LAST_NAME + "," + ID + "," + EMAIL + "," + PASSWORD + "," + MAX_COURSES,
				f.toString());
	}

	/**
	 * Tests that identical objects hash to the same value
	 */
	@Test
	public void testHashCode() {
		Faculty compare = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		assertEquals(compare.hashCode(), f.hashCode());
	}

	/**
	 * Tests that equals returns True for identical objects
	 */
	@Test
	public void testEqualsObject() {
		Faculty compare = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		assertTrue(f.equals(compare));
		compare.setMaxCourses(2);
		assertFalse(f.equals(compare));
		assertFalse(f == null);
	}

}