package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;


/**
 * Tests StudentDirectory. Rewriting provided tests as instructed on Piazza for
 * Lab 1.
 * 
 * @author Sarah Heckman
 */
public class StudentDirectoryTest {

	/** Valid course records */
	private final String validTestFile = "test-files/student_records.txt";
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

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {
		// Reset student_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_student_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "student_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

//	/**
//	 * Tests StudentDirectory().
//	 */
//	@Test
//	public void testStudentDirectory() {
//		//Test that the StudentDirectory is initialized to an empty list
//		StudentDirectory sd = new StudentDirectory();
//		assertFalse(sd.removeStudent("sesmith5"));
//		assertEquals(0, sd.getStudentDirectory().length);
//	}

	/**
	 * Tests StudentDirectory()
	 */
	@Test
	public void testStudentDirectory() {
		// Test that null constructor creates empty directory
		StudentDirectory emptyDir = new StudentDirectory();
		assertFalse(emptyDir.removeStudent("teststudent"));
		assertEquals(0, emptyDir.getStudentDirectory().length);
	}

//	/**
//	 * Tests StudentDirectory.testNewStudentDirectory().
//	 */
//	@Test
//	public void testNewStudentDirectory() {
//		//Test that if there are students in the directory, they 
//		//are removed after calling newStudentDirectory().
//		StudentDirectory sd = new StudentDirectory();
//		
//		sd.loadStudentsFromFile(validTestFile);
//		assertEquals(10, sd.getStudentDirectory().length);
//		
//		sd.newStudentDirectory();
//		assertEquals(0, sd.getStudentDirectory().length);
//	}

	/**
	 * Tests that newStudentDirectory() method resets StudentDirectory object
	 */
	@Test
	public void testNewStudentDirectory() {
		StudentDirectory testDir = new StudentDirectory();
		testDir.addStudent("Harry", "Potter", "hpotter", "scarboy@ncsu.edu", "expectopatronum", "expectopatronum", 15);
		testDir.addStudent("Ronald", "Weasley", "rweasle", "scabbersfan92@ncsu.edu", "obliviate", "obliviate", 12);
		testDir.addStudent("Hermione", "Granger", "hgrange", "SPEWPres@ncsu.edu", "leviOHsa", "leviOHsa", 18);

		assertEquals(3, testDir.getStudentDirectory().length);

		testDir.newStudentDirectory();
		assertEquals(0, testDir.getStudentDirectory().length);
	}

//	/**
//	 * Tests StudentDirectory.loadStudentsFromFile().
//	 */
//	@Test
//	public void testLoadStudentsFromFile() {
//		StudentDirectory sd = new StudentDirectory();
//				
//		//Test valid file
//		sd.loadStudentsFromFile(validTestFile);
//		assertEquals(10, sd.getStudentDirectory().length);
//	}

	/**
	 * Tests loadStudentsFromFile()
	 */
	@Test
	public void testLoadStudentsFromFile() {
		StudentDirectory testDir = new StudentDirectory();

		testDir.loadStudentsFromFile(validTestFile);
		assertEquals(10, testDir.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.addStudent().
	 */
	@Test
	public void testAddStudent() {
		StudentDirectory sd = new StudentDirectory();

		// Test valid Student
		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		String[][] studentDirectory = sd.getStudentDirectory();
		assertEquals(1, studentDirectory.length);
		assertEquals(FIRST_NAME, studentDirectory[0][0]);
		assertEquals(LAST_NAME, studentDirectory[0][1]);
		assertEquals(ID, studentDirectory[0][2]);

		// Test addStudent with null password
		StudentDirectory sd1 = new StudentDirectory();
		try {
			sd1.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, null, PASSWORD, MAX_CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			String[][] sdTest = sd1.getStudentDirectory();
			assertEquals(0, sdTest.length);
		}

		// Test addStudent with null repeatPassword
		StudentDirectory sd2 = new StudentDirectory();
		try {
			sd2.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, null, MAX_CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			String[][] sdTest = sd2.getStudentDirectory();
			assertEquals(0, sdTest.length);
		}

		// Test addStudent with empty String password
		StudentDirectory sd3 = new StudentDirectory();
		try {
			sd3.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, "", PASSWORD, MAX_CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			String[][] sdTest = sd3.getStudentDirectory();
			assertEquals(0, sdTest.length);
		}

		// Test addStudent with empty String password
		StudentDirectory sd4 = new StudentDirectory();
		try {
			sd4.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "", MAX_CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			String[][] sdTest = sd4.getStudentDirectory();
			assertEquals(0, sdTest.length);
		}

		// Test addStudent with duplicate Student
		StudentDirectory sd5 = new StudentDirectory();
//		Student student1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
//		Student student2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		assertTrue(sd5.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS));
		assertFalse(sd5.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS));
		assertTrue(sd5.addStudent(FIRST_NAME, LAST_NAME, "nrgarner", EMAIL, PASSWORD, PASSWORD, MAX_CREDITS));
	}

//	/**
//	 * Tests StudentDirectory.removeStudent().
//	 */
//	@Test
//	public void testRemoveStudent() {
//		StudentDirectory sd = new StudentDirectory();
//				
//		//Add students and remove
//		sd.loadStudentsFromFile(validTestFile);
//		assertEquals(10, sd.getStudentDirectory().length);
//		assertTrue(sd.removeStudent("efrost"));
//		String [][] studentDirectory = sd.getStudentDirectory();
//		assertEquals(9, studentDirectory.length);
//		assertEquals("Lane", studentDirectory[5][0]);
//		assertEquals("Berg", studentDirectory[5][1]);
//		assertEquals("lberg", studentDirectory[5][2]);
//	}

	/**
	 * Tests removeStudent()
	 */
	@Test
	public void testRemoveStudent() {
		StudentDirectory testDir = new StudentDirectory();

		// Load students from file, remove some
		testDir.loadStudentsFromFile(validTestFile);
		assertEquals(10, testDir.getStudentDirectory().length);
		assertTrue(testDir.removeStudent("shansen"));
		assertTrue(testDir.removeStudent("lberg"));

		// Attempt to remove non-existent student
		assertFalse(testDir.removeStudent("triddle"));

		// Check for proper contents
		String[][] dirArray = testDir.getStudentDirectory();
		assertEquals(8, dirArray.length);
		assertEquals("Demetrius", dirArray[2][0]);
		assertEquals("ahicks", dirArray[6][2]);
		assertEquals("Frost", dirArray[4][1]);
	}

	/**
	 * Tests StudentDirectory.saveStudentDirectory().
	 */
	@Test
	public void testSaveStudentDirectory() {
		StudentDirectory sd = new StudentDirectory();

		// Add a student
		sd.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15);
		assertEquals(1, sd.getStudentDirectory().length);
		sd.saveStudentDirectory("test-files/actual_student_records.txt");
		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
	}
		
	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));

			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
}