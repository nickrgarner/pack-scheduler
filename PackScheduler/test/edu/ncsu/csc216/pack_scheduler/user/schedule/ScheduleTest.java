package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Class tests Schedule class for proper behavior
 * 
 * @author Nick Garner
 *
 */
public class ScheduleTest {

	/** Schedule object to test */
	private Schedule testSchedule;
	/** Valid course */
	private static final Course COURSE1 = new Course("AS231", "Care of Magical Creatures", "001", 3, "rhagrid", "MWF",
			1020, 1150);
	/** Valid course */
	private static final Course COURSE2 = new Course("HI310", "A History of Magic", "002", 3, "cbinns", "TH", 1300,
			1450);
	/** Valid course */
	private static final Course COURSE3 = new Course("CS115", "Intro to Herbology", "001", 3, "psprout", "MWF", 1400,
			1450);
	/** Valid course */
	private static final Course COURSE4 = new Course("T226", "Advanced Transfiguration", "003", 3, "mmcgonagall", "MWF",
			830, 1000);
	/** Valid course */
	private static final Course COURSE5 = new Course("CH330", "Advanced Potions", "001", 4, "ssnape", "TH", 900, 1130);
	/** Course that conflicts with COURSE5 */
	private static final Course COURSECONFLICT = new Course("DF316", "Defense Against the Dark Arts", "001", 4,
			"rlupin", "TH", 830, 1000);
	/** Course with same name as COURSE1 */
	private static final Course COURSEDUPE = new Course("AS231", "Care of Magical Creatures", "002", 3, "rhagrid", "TH",
			1200, 1250);
	/** Course to be left off Schedule to test removeCourseFromSchedule exception */
	private static final Course COURSEMIA = new Course("C110", "Charms I", "002", 3, "fflitwick", "A");

	/**
	 * Runs before every test to instantiate a schedule object for testing
	 * 
	 * @throws Exception If error occurs during setup
	 */
	@Before
	public void setUp() throws Exception {
		testSchedule = new Schedule();
	}

	/**
	 * Tests that null constructor properly creates an empty ArrayList with default
	 * title
	 */
	@Test
	public void testSchedule() {
		assertEquals(0, testSchedule.getScheduledCourses().length);
		assertEquals("My Schedule", testSchedule.getTitle());
	}

	/**
	 * Tests that addCourseToSchedule properly adds course object to the end of the
	 * schedule ArrayList and properly throws exceptions when given a duplicate or
	 * conflicting Course
	 */
	@Test
	public void testAddCourseToSchedule() {
		assertTrue(testSchedule.addCourseToSchedule(COURSE1));
		assertTrue(testSchedule.addCourseToSchedule(COURSE2));
		assertTrue(testSchedule.addCourseToSchedule(COURSE3));
		assertTrue(testSchedule.addCourseToSchedule(COURSE4));
		assertTrue(testSchedule.addCourseToSchedule(COURSE5));
		assertEquals(5, testSchedule.getScheduledCourses().length);
		assertEquals(COURSE1.getName(), testSchedule.getScheduledCourses()[0][0]);
		assertEquals(COURSE3.getSection(), testSchedule.getScheduledCourses()[2][1]);
		assertEquals(COURSE5.getTitle(), testSchedule.getScheduledCourses()[4][2]);
		assertEquals(COURSE2.getMeetingString(), testSchedule.getScheduledCourses()[1][3]);
		// Test dupe add
		try {
			testSchedule.addCourseToSchedule(COURSEDUPE);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("You are already enrolled in AS231", e.getMessage());
		}
		// Test conflict add
		try {
			testSchedule.addCourseToSchedule(COURSECONFLICT);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("The course cannot be added due to a conflict.", e.getMessage());
		}

	}

	/**
	 * Tests that removeCourseFromSchedule properly removes the matching Course name
	 * and shifts the remaining elements
	 */
	@Test
	public void testRemoveCourseFromSchedule() {
		assertTrue(testSchedule.addCourseToSchedule(COURSE1));
		assertTrue(testSchedule.addCourseToSchedule(COURSE2));
		assertTrue(testSchedule.addCourseToSchedule(COURSE3));
		assertTrue(testSchedule.addCourseToSchedule(COURSE4));
		assertTrue(testSchedule.addCourseToSchedule(COURSE5));
		assertEquals(5, testSchedule.getScheduledCourses().length);
		// Remove non-existant course
		assertFalse(testSchedule.removeCourseFromSchedule(COURSEMIA));
		assertEquals(5, testSchedule.getScheduledCourses().length);
		// Remove from middle
		assertTrue(testSchedule.removeCourseFromSchedule(COURSE2));
		assertEquals(4, testSchedule.getScheduledCourses().length);
		assertEquals(COURSE1.getName(), testSchedule.getScheduledCourses()[0][0]);
		assertEquals(COURSE3.getName(), testSchedule.getScheduledCourses()[1][0]);
		// Remove from front
		assertTrue(testSchedule.removeCourseFromSchedule(COURSE1));
		assertEquals(3, testSchedule.getScheduledCourses().length);
		assertEquals(COURSE3.getName(), testSchedule.getScheduledCourses()[0][0]);
		assertEquals(COURSE4.getName(), testSchedule.getScheduledCourses()[1][0]);
		// Remove from end
		assertTrue(testSchedule.removeCourseFromSchedule(COURSE5));
		assertEquals(2, testSchedule.getScheduledCourses().length);
		assertEquals(COURSE3.getName(), testSchedule.getScheduledCourses()[0][0]);
		assertEquals(COURSE4.getName(), testSchedule.getScheduledCourses()[1][0]);
	}

	/**
	 * Tests that resetSchedule properly empties the Schedule ArrayList
	 */
	@Test
	public void testResetSchedule() {
		assertTrue(testSchedule.addCourseToSchedule(COURSE1));
		assertTrue(testSchedule.addCourseToSchedule(COURSE2));
		assertTrue(testSchedule.addCourseToSchedule(COURSE3));
		assertTrue(testSchedule.addCourseToSchedule(COURSE4));
		assertTrue(testSchedule.addCourseToSchedule(COURSE5));
		assertEquals(5, testSchedule.getScheduledCourses().length);
		testSchedule.resetSchedule();
		assertEquals(0, testSchedule.getScheduledCourses().length);
	}

	/**
	 * Tests that setTitle() properly throws an IllegalArgumentException for a null
	 * parameter
	 */
	@Test
	public void testSetTitle() {
		try {
			testSchedule.setTitle(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Title cannot be null.", e.getMessage());
		}
	}
}