package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests CourseCatalog class, load, add, remove, save, and reset functions.
 * 
 * @author Nick Garner
 */
public class CourseCatalogTest {

	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";
	/** Invalid course records */
	private final String invalidTestFile = "test-files/invalid_course_records.txt";
	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Programming Concepts - Java";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 4;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Course meeting days */
	private static final String MEETING_DAYS = "MW";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception If something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {
		// Reset student_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_course_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests CourseCatalog() null constructor
	 */
	@Test
	public void testCourseCatalog() {
		// Tests that null constructor creates empty course catalog
		CourseCatalog emptyCatalog = new CourseCatalog();
		assertFalse(emptyCatalog.removeCourseFromCatalog("CSC216", "001"));
		assertEquals(0, emptyCatalog.getCourseCatalog().length);
	}

	/**
	 * Tests that newCourseCatalog() properly resets CourseCatalog object
	 */
	@Test
	public void testNewCourseCatalog() {
		CourseCatalog testCatalog = new CourseCatalog();
		testCatalog.addCourseToCatalog("CH101", "Intro to Potions", "001", 4, "ssnape", "MWF", 1030, 1230);
		testCatalog.addCourseToCatalog("CS210", "Advanced Herbology", "002", 3, "psprout", "TH", 1100, 1300);
		testCatalog.addCourseToCatalog("HI305", "A History of Magic", "001", 3, "cbinns", "MW", 1400, 1500);

		assertEquals(3, testCatalog.getCourseCatalog().length);

		testCatalog.newCourseCatalog();
		assertEquals(0, testCatalog.getCourseCatalog().length);
	}

	/**
	 * Tests loadCoursesFromFile()
	 */
	@Test
	public void testLoadCoursesFromFile() {
		CourseCatalog testCatalog = new CourseCatalog();

		// Test that no invalid courses are added
		testCatalog.loadCoursesFromFile(invalidTestFile);
		assertEquals(0, testCatalog.getCourseCatalog().length);

		// Test that file of valid courses are all added
		testCatalog.loadCoursesFromFile(validTestFile);
		assertEquals(8, testCatalog.getCourseCatalog().length);
	}

	/**
	 * Tests CourseCatalog.addCourseToCatalog()
	 */
	@Test
	public void testAddCourseToCatalog() {
		CourseCatalog cc = new CourseCatalog();

		// Test valid course
		assertTrue(cc.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME,
				END_TIME));
		String[][] catalog = cc.getCourseCatalog();
		assertEquals(1, catalog.length);
		assertEquals(NAME, catalog[0][0]);
		assertEquals(SECTION, catalog[0][1]);
		assertEquals(TITLE, catalog[0][2]);
		assertEquals("MW 1:30PM-2:45PM", catalog[0][3]);

		// Test add course with duplicate course
		assertFalse(cc.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, MEETING_DAYS, START_TIME,
				END_TIME));
		assertEquals(1, cc.getCourseCatalog().length);
	}

	/**
	 * Tests CourseCatalog.removeCourseFromCatalog()
	 */
	@Test
	public void testRemoveCourseFromCatalog() {
		CourseCatalog testCatalog = new CourseCatalog();

		// Load courses from catalog, remove some
		testCatalog.loadCoursesFromFile(validTestFile);
		assertTrue(testCatalog.removeCourseFromCatalog("CSC116", "002"));
		assertTrue(testCatalog.removeCourseFromCatalog("CSC226", "001"));

		// Attempt to remove non-existent course
		assertFalse(testCatalog.removeCourseFromCatalog("MA141", "001"));

		// Check for proper contents
		String[][] catArray = testCatalog.getCourseCatalog();
		assertEquals(6, catArray.length);
		assertEquals("003", catArray[1][1]);
		assertEquals("CSC230", catArray[5][0]);
		assertEquals("Programming Concepts - Java", catArray[4][2]);
		assertEquals("TH 1:30PM-2:45PM", catArray[2][3]);
	}
	
	/**
	 * Tests that CourseCatalog.getCourseFromCatalog() returns the proper course
	 */
	@Test
	public void testGetCourseFromCatalog() {
		CourseCatalog cc = new CourseCatalog();
		cc.loadCoursesFromFile(validTestFile);
		assertEquals(8, cc.getCourseCatalog().length);
		Course testCourse = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", "TH", 1330, 1445);
		assertEquals(testCourse, cc.getCourseFromCatalog("CSC216", "001"));
		assertEquals(null, cc.getCourseFromCatalog("MA241", "004"));
	}

//	@Test
//	public void testGetCourseCatalog() {
//		fail("Not yet implemented");
//	}

	/**
	 * Tests CourseCatalog.saveCourseCatalog()
	 */
	@Test
	public void testSaveCourseCatalog() {
		CourseCatalog cc = new CourseCatalog();

		// Add a course
		assertTrue(
				cc.addCourseToCatalog("CSC116", "Intro to Programming - Java", "003", 3, "spbalik", "MW", 1250, 1440));
		assertTrue(
				cc.addCourseToCatalog("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", "MW", 1330, 1445));
		assertTrue(cc.addCourseToCatalog("CSC216", "Programming Concepts - Java", "601", 4, "jep", "A", 0, 0));
		assertEquals(3, cc.getCourseCatalog().length);
		cc.saveCourseCatalog("test-files/actual_course_records.txt");
		checkFiles("test-files/expected_course_records.txt", "test-files/actual_course_records.txt");
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