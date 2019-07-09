package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests checkConflict method in Activity class
 * 
 * @author Nick Garner
 *
 */
public class ActivityTest {

	/**
	 * Tests Activity.checkConflict()
	 */
	@Test
	public void testCheckConflict() {
		// Test for thrown exception when there is no conflict
		Activity a1 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "MW", 1330, 1445);
		Activity a2 = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "TH", 1330, 1445);
		try {
			a1.checkConflict(a2);
		} catch (ConflictException e) {
			fail(); // If an exception is thrown when there is no conflict
		}

		// Test for thrown exception when there is conflict
		a1.setMeetingDays("TH");
		a1.setActivityTime(1445, 1530);
		try {
			a1.checkConflict(a2);
			fail();
		} catch (ConflictException e) {
			// Check that internal state didn't change during method call
			assertEquals("TH 2:45PM-3:30PM", a1.getMeetingString());
			assertEquals("TH 1:30PM-2:45PM", a2.getMeetingString());
		}
	}
}