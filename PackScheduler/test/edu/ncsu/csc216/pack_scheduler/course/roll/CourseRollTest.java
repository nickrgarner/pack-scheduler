package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.*;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

import org.junit.Before;
import org.junit.Test;

/**
 * Class tests CourseRoll for proper functionality and exception handling.
 * 
 * @author Nick Garner
 *
 */
public class CourseRollTest {

	/** CourseRoll object to test */
	private CourseRoll testRoll;
	/** Course to test with roll */
	private static final Course COURSE1 = new Course("CSC216", "Java II", "651", 4, "jep", 10, "MW", 1030, 1130);
	/** Valid student */
	private static final Student STUDENT1 = new Student("Harry", "Potter", "hpotter", "hpotter@ncsu.edu",
			"caputdraconis");
	/** Valid student */
	private static final Student STUDENT2 = new Student("Ron", "Weasley", "rweasle", "rweasle@ncsu.edu",
			"fordangliaflyer");
	/** Valid student */
	private static final Student STUDENT3 = new Student("Herminone", "Granger", "hgrange", "hgrange@ncsu.edu",
			"SPEWPres");
	/** Valid student */
	private static final Student STUDENT4 = new Student("Draco", "Malfoy", "dmalfoy", "dmalfoy@ncsu.edu",
			"xXD3athEat3rXx");
	/** Valid student */
	private static final Student STUDENT5 = new Student("Ginny", "Weasley", "gweasle", "gweasle@ncsu.edu", "HPGW4Ever");
	/** Valid student */
	private static final Student STUDENT6 = new Student("Neville", "Longbottom", "nlongbo", "nlongbo@ncsu.edu",
			"RealBoyWhoLived");
	/** Valid student */
	private static final Student STUDENT7 = new Student("Luna", "Lovegood", "llovego", "llovego@ncsu.edu",
			"ThisTastesPurple");
	/** Valid student */
	private static final Student STUDENT8 = new Student("Cedric", "Diggory", "cdiggor", "cdiggor@ncsu.edu",
			"TwilightWasAMistake");
	/** Valid student */
	private static final Student STUDENT9 = new Student("Lavender", "Brown", "lbrown", "lbrown@ncsu.edu", "WONWON");
	/** Valid student */
	private static final Student STUDENT10 = new Student("Fred", "Weasley", "fweasle", "fweasle@ncsu.edu",
			"UpToNoGood");
	/** Extra student to test enrollmentCap */
	private static final Student STUDENT11 = new Student("George", "Weasley", "gweasle", "gweasle@ncsu.edu",
			"mischiefmanaged");
	/** Duplicate student to check canEnroll */
	private static final Student DUPESTUDENT = new Student("Harry", "Potter", "hpotter", "hpotter@ncsu.edu",
			"caputdraconis");
	/** Null student to check add and drop */
	private static final Student NULLSTUDENT = null;

	/**
	 * Runs before every test to instantiate a new CourseRoll object with capacity
	 * 10.
	 * 
	 * @throws Exception If error occurs during setup.
	 */
	@Before
	public void setUp() throws Exception {
		testRoll = new CourseRoll(COURSE1, 10);
	}

	/**
	 * Tests CourseRoll constructor with minimum enrollmentCap
	 */
	@SuppressWarnings("unused")
	@Test
	public void testCourseRoll() {
		assertEquals(10, testRoll.getEnrollmentCap());
		assertEquals(10, testRoll.getOpenSeats());
		try {
			CourseRoll nullCourse = new CourseRoll(null, 10);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Course cannot be null.", e.getMessage());
		}
	}

	/**
	 * Tests that setEnrollmentCap properly changes the enrollmentCap of a list and
	 * properly throws exception when param is outside legal bounds.
	 */
	@Test
	public void testSetEnrollmentCap() {
		try {
			testRoll.setEnrollmentCap(9);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Enrollment cap is not valid.", e.getMessage());
		}
		try {
			testRoll.setEnrollmentCap(251);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Enrollment cap is not valid.", e.getMessage());
		}
		testRoll.setEnrollmentCap(11);
		testRoll.enroll(STUDENT1);
		testRoll.enroll(STUDENT2);
		testRoll.enroll(STUDENT3);
		testRoll.enroll(STUDENT4);
		testRoll.enroll(STUDENT5);
		testRoll.enroll(STUDENT6);
		testRoll.enroll(STUDENT7);
		testRoll.enroll(STUDENT8);
		testRoll.enroll(STUDENT9);
		testRoll.enroll(STUDENT10);
		testRoll.enroll(STUDENT11);
		try {
			testRoll.setEnrollmentCap(10);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Enrollment cap is not valid.", e.getMessage());
		}
	}

	/**
	 * Tests that enroll(Student) properly throws exception when attempting to
	 * enroll a null student
	 */
	@Test
	public void testEnroll() {
		try {
			testRoll.enroll(NULLSTUDENT);
		} catch (IllegalArgumentException e) {
			assertEquals("Student could not be enrolled.", e.getMessage());
		}
		testRoll.enroll(STUDENT1);
		testRoll.enroll(STUDENT2);
		testRoll.enroll(STUDENT3);
		testRoll.enroll(STUDENT4);
		testRoll.enroll(STUDENT5);
		testRoll.enroll(STUDENT6);
		testRoll.enroll(STUDENT7);
		testRoll.enroll(STUDENT8);
		testRoll.enroll(STUDENT9);
		testRoll.enroll(STUDENT10);
		testRoll.enroll(STUDENT11);
		assertEquals(1, testRoll.getNumberOnWaitlist());
	}

	/**
	 * Tests that drop(Student) removes the correct student and properly throws
	 * exception for null student.
	 */
	@Test
	public void testDrop() {
		try {
			testRoll.drop(NULLSTUDENT);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Student cannot be null.", e.getMessage());
		}
		testRoll.enroll(STUDENT1);
		testRoll.enroll(STUDENT2);
		testRoll.enroll(STUDENT3);
		testRoll.enroll(STUDENT4);
		testRoll.enroll(STUDENT5);
		testRoll.enroll(STUDENT6);
		testRoll.enroll(STUDENT7);
		testRoll.enroll(STUDENT8);
		testRoll.enroll(STUDENT9);
		testRoll.enroll(STUDENT10);
		testRoll.enroll(STUDENT11);
		assertEquals(1, testRoll.getNumberOnWaitlist());
		testRoll.drop(STUDENT1);
		assertEquals(0, testRoll.getOpenSeats());
		assertEquals(0, testRoll.getNumberOnWaitlist());
		testRoll.drop(STUDENT10);
		assertEquals(1, testRoll.getOpenSeats());
		testRoll.drop(STUDENT4);
		assertEquals(2, testRoll.getOpenSeats());
		testRoll.drop(STUDENT1);
		assertEquals(2, testRoll.getOpenSeats());
	}

	/**
	 * Tests that canEnroll(Student) returns true for valid student and false for
	 * duplicate student or full list.
	 */
	@Test
	public void testCanEnroll() {
		testRoll.enroll(STUDENT1);
		testRoll.enroll(STUDENT2);
		testRoll.enroll(STUDENT3);
		testRoll.enroll(STUDENT4);
		testRoll.enroll(STUDENT5);
		testRoll.enroll(STUDENT6);
		testRoll.enroll(STUDENT7);
		testRoll.enroll(STUDENT8);
		testRoll.enroll(STUDENT9);
		assertTrue(testRoll.canEnroll(STUDENT10));
		assertFalse(testRoll.canEnroll(STUDENT9));
		assertFalse(testRoll.canEnroll(DUPESTUDENT));
		testRoll.enroll(STUDENT10);
		assertTrue(testRoll.canEnroll(STUDENT11));
	}

}
