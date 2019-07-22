package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Tests FacultyDirectory for proper functionality and exception handling
 * 
 * @author Nick Garner
 *
 */
public class FacultyDirectoryTest {

	/** Valid Faculty records */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Test first name */
	private static final String FIRST_NAME = "Albus";
	/** Test last name */
	private static final String LAST_NAME = "Dumbledore";
	/** Test id */
	private static final String ID = "adumble";
	/** Test email */
	private static final String EMAIL = "adumble@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max courses */
	private static final int MAX_COURSES = 3;

	/**
	 * Resets faculty_records.txt for use in other tests.
	 * 
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests FacultyDirectory()
	 */
	@Test
	public void testFacultyDirectory() {
		FacultyDirectory directory = new FacultyDirectory();
		assertFalse(directory.removeFaculty("adumble"));
		assertEquals(0, directory.getFacultyDirectory().length);
	}

	/**
	 * Tests that newFacultyDirectory() method resets FacultyDirectory object
	 */
	@Test
	public void testNewFacultyDirectory() {
		FacultyDirectory directory = new FacultyDirectory();
		directory.addFaculty("Albus", "Dumbledore", "adumble", "adumble@ncsu.edu", "pw", "pw", 2);
		directory.addFaculty("Minerva", "McGonagall", "mmcgonag", "mmcgonag@ncsu.edu", "pw", "pw", 3);
		directory.addFaculty("Severus", "Snape", "ssnape", "ssnape@ncsu.edu", "pw", "pw", 1);

		assertEquals(3, directory.getFacultyDirectory().length);

		directory.newFacultyDirectory();
		assertEquals(0, directory.getFacultyDirectory().length);
	}

	/**
	 * Tests loadFacultyFromFile
	 */
	@Test
	public void testLoadFacultyFromFile() {
		FacultyDirectory testDir = new FacultyDirectory();
		testDir.loadFacultyFromFile(validTestFile);
		assertEquals(8, testDir.getFacultyDirectory().length);
	}

	/**
	 * Tests addFaculty exception handling
	 */
	@Test
	public void testAddFaculty() {
		FacultyDirectory testDir = new FacultyDirectory();
		try {
			testDir.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, "pw1", MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Passwords do not match", e.getMessage());
		}
		assertTrue(testDir.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES));
		assertFalse(testDir.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES));
	}

	/**
	 * Tests removeFaculty()
	 */
	@Test
	public void testRemoveFaculty() {
		FacultyDirectory testDir = new FacultyDirectory();

		// Load students from file, remove some
		testDir.loadFacultyFromFile(validTestFile);
		assertEquals(8, testDir.getFacultyDirectory().length);
		assertTrue(testDir.removeFaculty("awitt"));
		assertTrue(testDir.removeFaculty("lwalls"));

		// Attempt to remove non-existent Faculty
		assertFalse(testDir.removeFaculty("triddle"));

		// Check for proper contents
		String[][] dirArray = testDir.getFacultyDirectory();
		assertEquals(6, dirArray.length);
		assertEquals("Fiona", dirArray[0][0]);
		assertEquals("nbrady", dirArray[5][2]);
		assertEquals("Aguirre", dirArray[2][1]);
	}

	/**
	 * Tests saveFacultyDirectory()
	 */
	@Test
	public void testSaveFacultyDirectory() {
		FacultyDirectory testDir = new FacultyDirectory();
		assertTrue(testDir.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES));
		assertEquals(1, testDir.getFacultyDirectory().length);
		testDir.saveFacultyDirectory("test-files/actual_faculty_records.txt");
	}

	/**
	 * Tests getFacultyById()
	 */
	@Test
	public void testGetFacultyById() {
		FacultyDirectory testDir = new FacultyDirectory();
		Faculty testFaculty = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		assertTrue(testDir.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES));
		assertEquals(1, testDir.getFacultyDirectory().length);
		assertEquals(testFaculty, testDir.getFacultyById(ID));
	}

}
