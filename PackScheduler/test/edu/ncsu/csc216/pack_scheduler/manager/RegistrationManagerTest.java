package edu.ncsu.csc216.pack_scheduler.manager;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

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
		// Test Student login
		manager.logout();
		manager.getStudentDirectory().addStudent("Fred", "Weasley", "fweasle", "fweasle@ncsu.edu", "pw", "pw", 15);
		assertTrue(manager.login("fweasle", "pw"));
		manager.logout();
		assertFalse(manager.login("fweasle", "pigsnout"));

		// Test Faculty login
		manager.logout();
		manager.getFacultyDirectory().addFaculty("Albus", "Dumbledore", "adumble", "adumble@ncsu.edu", "pw", "pw", 3);
		manager.getFacultyDirectory().addFaculty("Severus", "Snape", "ssnape", "ssnape@ncsu.edu", "pw", "pw", 2);
		assertTrue(manager.login("adumble", "pw"));
		assertFalse(manager.login("ssnape", "pw"));
		manager.logout();
		assertFalse(manager.login("adumble", "badpassword"));

		Properties prop = new Properties();
		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot load properties file");
		}

		assertTrue(manager.login(prop.getProperty("id"), prop.getProperty("pw")));
		manager.logout();
		assertFalse(manager.login(prop.getProperty("id"), "badpassword"));

		// Test user doesn't exist
		try {
			manager.login("fflitwick", "pw");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("User doesn't exist.", e.getMessage());
		}
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

	/**
	 * Tests RegistrationManager.enrollStudentInCourse()
	 */
	@Test
	public void testEnrollStudentInCourse() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// test if not logged in
		try {
			manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.enrollStudentInCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull("RegistrationManager.enrollStudentInCourse() - currentUser is null, so cannot enroll in course.",
					manager.getCurrentUser());
		}

		// test if registrar is logged in
		manager.login("registrar", "Regi5tr@r");
		try {
			manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.enrollStudentInCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"RegistrationManager.enrollStudentInCourse() - currentUser is registrar, so cannot enroll in course.",
					"registrar", manager.getCurrentUser().getId());
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(3, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(1, scheduleFrostArray.length);
		assertEquals("CSC226", scheduleFrostArray[0][0]);
		assertEquals("001", scheduleFrostArray[0][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleFrostArray[0][2]);
		assertEquals("MWF 9:35AM-10:25AM", scheduleFrostArray[0][3]);
		assertEquals("9", scheduleFrostArray[0][4]);

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		// Check Student Schedule
		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(10, scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(3, scheduleHicksArray.length);
		assertEquals("CSC216", scheduleHicksArray[0][0]);
		assertEquals("001", scheduleHicksArray[0][1]);
		assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
		assertEquals("9", scheduleHicksArray[0][4]);
		assertEquals("CSC226", scheduleHicksArray[1][0]);
		assertEquals("001", scheduleHicksArray[1][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2]);
		assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3]);
		assertEquals("8", scheduleHicksArray[1][4]);
		assertEquals("CSC116", scheduleHicksArray[2][0]);
		assertEquals("003", scheduleHicksArray[2][1]);
		assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2]);
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3]);
		assertEquals("9", scheduleHicksArray[2][4]);

		manager.logout();
	}

	/**
	 * Tests RegistrationManager.dropStudentFromCourse()
	 */
	@Test
	public void testDropStudentFromCourse() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// test if not logged in
		try {
			manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.dropStudentFromCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull("RegistrationManager.dropStudentFromCourse() - currentUser is null, so cannot enroll in course.",
					manager.getCurrentUser());
		}

		// test if registrar is logged in
		manager.login("registrar", "Regi5tr@r");
		try {
			manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.dropStudentFromCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"RegistrationManager.dropStudentFromCourse() - currentUser is registrar, so cannot enroll in course.",
					"registrar", manager.getCurrentUser().getId());
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		assertFalse(
				"Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: False - student not enrolled in the course",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));

		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(0, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(0, scheduleFrostArray.length);

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(10, scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(3, scheduleHicksArray.length);
		assertEquals("CSC216", scheduleHicksArray[0][0]);
		assertEquals("001", scheduleHicksArray[0][1]);
		assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
		assertEquals("9", scheduleHicksArray[0][4]);
		assertEquals("CSC226", scheduleHicksArray[1][0]);
		assertEquals("001", scheduleHicksArray[1][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2]);
		assertEquals("MWF 9:35AM-10:25AM", scheduleHicksArray[1][3]);
		assertEquals("9", scheduleHicksArray[1][4]);
		assertEquals("CSC116", scheduleHicksArray[2][0]);
		assertEquals("003", scheduleHicksArray[2][1]);
		assertEquals("Intro to Programming - Java", scheduleHicksArray[2][2]);
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[2][3]);
		assertEquals("9", scheduleHicksArray[2][4]);

		assertTrue("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(7, scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(2, scheduleHicksArray.length);
		assertEquals("CSC216", scheduleHicksArray[0][0]);
		assertEquals("001", scheduleHicksArray[0][1]);
		assertEquals("Programming Concepts - Java", scheduleHicksArray[0][2]);
		assertEquals("TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
		assertEquals("9", scheduleHicksArray[0][4]);
		assertEquals("CSC116", scheduleHicksArray[1][0]);
		assertEquals("003", scheduleHicksArray[1][1]);
		assertEquals("Intro to Programming - Java", scheduleHicksArray[1][2]);
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[1][3]);
		assertEquals("9", scheduleHicksArray[1][4]);

		assertFalse("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: False - already dropped",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));

		assertTrue("Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")));

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(3, scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(1, scheduleHicksArray.length);
		assertEquals("CSC116", scheduleHicksArray[0][0]);
		assertEquals("003", scheduleHicksArray[0][1]);
		assertEquals("Intro to Programming - Java", scheduleHicksArray[0][2]);
		assertEquals("TH 11:20AM-1:10PM", scheduleHicksArray[0][3]);
		assertEquals("9", scheduleHicksArray[0][4]);

		assertTrue("Action: drop\nUser: efrost\nCourse: CSC116-003\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC116", "003")));

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(0, scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(0, scheduleHicksArray.length);

		manager.logout();
	}

	/**
	 * Tests RegistrationManager.resetSchedule()
	 */
	@Test
	public void testResetSchedule() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// Test if not logged in
		try {
			manager.resetSchedule();
			fail("RegistrationManager.resetSchedule() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull("RegistrationManager.resetSchedule() - currentUser is null, so cannot enroll in course.",
					manager.getCurrentUser());
		}

		// test if registrar is logged in
		manager.login("registrar", "Regi5tr@r");
		try {
			manager.resetSchedule();
			fail("RegistrationManager.resetSchedule() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals("RegistrationManager.resetSchedule() - currentUser is registrar, so cannot enroll in course.",
					"registrar", manager.getCurrentUser().getId());
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: False - Student max credits are 3 and course has 4.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC226-001, CSC230-001\nResults: False - cannot exceed max student credits.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		manager.resetSchedule();
		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals(0, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals(0, scheduleFrostArray.length);

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		manager.resetSchedule();
		// Check Student schedule
		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals(0, scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(0, scheduleHicksArray.length);

		manager.logout();
	}

	/**
	 * Tests addFacultyToCourse
	 */
	@Test
	public void testAddFacultyToCourse() {
		// Create Course and Faculty object
		Course c = new Course("CSC216", "Java II", "001", 4, null, 10, "MWF", 1030, 1200);
		Course c1 = new Course("CSC230", "C and Software Tools", "002", 3, null, 10, "MWF", 1100, 1300);
		Faculty f = new Faculty("First", "Last", "flast", "flast@ncsu.edu", "pw", 1);

		// Attempt to add without login
		try {
			manager.addFacultyToCourse(c, f);
		} catch (IllegalArgumentException e) {
			assertEquals("Must be logged in as registrar.", e.getMessage());
		}

		// Login as registrar
		Properties prop = new Properties();
		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot load properties file");
		}

		assertTrue(manager.login(prop.getProperty("id"), prop.getProperty("pw")));

		// Add Faculty to course
		assertTrue(manager.addFacultyToCourse(c, f));
		assertEquals("flast", c.getInstructorId());
		assertEquals(1, f.getSchedule().getNumScheduledCourses());

		// Check exception propagation
		try {
			manager.addFacultyToCourse(c1, f);
		} catch (IllegalArgumentException e) {
			assertEquals("The course cannot be assigned due to a conflict.", e.getMessage());
		}

		manager.logout();
	}

	/**
	 * Tests removeFacultyFromCourse
	 */
	@Test
	public void testRemoveFacultyFromCourse() {
		// Create Course and Faculty object
		Course c = new Course("CSC216", "Java II", "001", 4, null, 10, "MWF", 1030, 1200);
		Faculty f = new Faculty("First", "Last", "flast", "flast@ncsu.edu", "pw", 1);

		// Attempt to remove without login
		assertFalse(manager.removeFacultyFromCourse(c, f));

		// Login as registrar
		Properties prop = new Properties();
		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot load properties file");
		}

		assertTrue(manager.login(prop.getProperty("id"), prop.getProperty("pw")));

		// Attempt to remove without adding
		assertFalse(manager.removeFacultyFromCourse(c, f));

		// Add course, check, and remove
		assertTrue(manager.addFacultyToCourse(c, f));
		assertEquals("flast", c.getInstructorId());
		assertEquals(1, f.getSchedule().getNumScheduledCourses());
		assertTrue(manager.removeFacultyFromCourse(c, f));
		assertNull(c.getInstructorId());
		assertEquals(0, f.getSchedule().getNumScheduledCourses());

		manager.logout();

	}

	/**
	 * Tests resetFacultySchedule
	 */
	@Test
	public void resetFacultySchedule() {
		// Create Course and Faculty object
		Course c = new Course("CSC216", "Java II", "001", 4, null, 10, "MWF", 1030, 1200);
		Faculty f = new Faculty("First", "Last", "flast", "flast@ncsu.edu", "pw", 1);

		// Login as registrar
		Properties prop = new Properties();
		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot load properties file");
		}

		assertTrue(manager.login(prop.getProperty("id"), prop.getProperty("pw")));

		// Add course, check, and reset schedule
		assertTrue(f.getSchedule().addCourseToSchedule(c));
		assertEquals("flast", c.getInstructorId());
		assertEquals(1, f.getSchedule().getNumScheduledCourses());
		manager.resetFacultySchedule(f);
		assertNull(c.getInstructorId());
		assertEquals(0, f.getSchedule().getNumScheduledCourses());
	}
}