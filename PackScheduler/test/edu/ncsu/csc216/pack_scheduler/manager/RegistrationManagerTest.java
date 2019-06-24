package edu.ncsu.csc216.pack_scheduler.manager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 * Tests RegistrationManager class
 * 
 * @author Nick Garner
 *
 */
public class RegistrationManagerTest {

	private RegistrationManager manager;
	private static final String PROP_FILE = "registrar.properties";
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Sets up the CourseManager and clears the data.
	 * 
	 * @throws Exception if error
	 */
	@Before
	public void setUp() throws Exception {
		manager = RegistrationManager.getInstance();
		manager.clearData();
	}

	/**
	 * Tests that getCourseCatalog properly returns the CourseCatalog object
	 * associated with RegistrationManager
	 */
	@Test
	public void testGetCourseCatalog() {
		assertEquals(0, manager.getCourseCatalog().getCourseCatalog().length);
		manager.getCourseCatalog().loadCoursesFromFile("test-files/course_records.txt");
		assertEquals(8, manager.getCourseCatalog().getCourseCatalog().length);
	}

	/**
	 * Tests that getStudentDirectory properly returns the StudentDirectory object
	 * associated with RegistrationManager
	 */
	@Test
	public void testGetStudentDirectory() {
		assertEquals(0, manager.getStudentDirectory().getStudentDirectory().length);
		manager.getStudentDirectory().loadStudentsFromFile("test-files/student_records.txt");
		assertEquals(10, manager.getStudentDirectory().getStudentDirectory().length);
	}

	/**
	 * Tests that login method properly grants/blocks access for both students and
	 * registrars, as appropriate.
	 */
	@Test
	public void testLogin() {
		manager.logout();
		manager.getStudentDirectory().addStudent("Fred", "Weasley", "fweasle", "fweasle@ncsu.edu", "pw", "pw", 15);
		assertTrue(manager.login("fweasle", "pw"));
		manager.logout();
		assertFalse(manager.login("fweasle", "pigsnout"));

		Properties prop = new Properties();
		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot load properties file");
		}

		assertTrue(manager.login(prop.getProperty("id"), prop.getProperty("pw")));
		manager.logout();
		assertFalse(manager.login(prop.getProperty("id"), "badpassword"));
	}

	/**
	 * Tests that logout method properly changes currentUser from Student
	 */
	@Test
	public void testLogout() {
		manager.getStudentDirectory().addStudent("Fred", "Weasley", "fweasle", "fweasle@ncsu.edu", "mischiefmanaged",
				"mischiefmanaged", 15);
		assertTrue(manager.login("fweasle", "mischiefmanaged"));
		assertEquals(manager.getCurrentUser(), manager.getStudentDirectory().getStudentById("fweasle"));
		manager.logout();
		assertNotEquals(manager.getCurrentUser(), manager.getStudentDirectory().getStudentById("fweasle"));
	}

	/**
	 * Tests that getCurrentUser properly returns User object associated with
	 * current login
	 */
	@Test
	public void testGetCurrentUser() {
		String hashPW = "";
		String password = "mischiefmanaged";
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(password.getBytes());
			hashPW = new String(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
		Student s = new Student("Fred", "Weasley", "fweasle", "fweasle@ncsu.edu", hashPW, 15);
		manager.getStudentDirectory().addStudent("Fred", "Weasley", "fweasle", "fweasle@ncsu.edu", "mischiefmanaged",
				"mischiefmanaged", 15);
		assertTrue(manager.login("fweasle", "mischiefmanaged"));
		assertEquals(manager.getCurrentUser(), s);
	}
}