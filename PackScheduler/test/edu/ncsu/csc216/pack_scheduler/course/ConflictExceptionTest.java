/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests ConflictException class
 * 
 * @author Nick Garner
 *
 */
public class ConflictExceptionTest {

	/**
	 * Tests ConflictException(String message) constructor
	 */
	@Test
	public void testConflictExceptionString() {
		ConflictException ce = new ConflictException("This is a test conflict.");
		assertEquals("This is a test conflict.", ce.getMessage());
	}

	/**
	 * Tests ConflictException null constructor
	 */
	@Test
	public void testConflictException() {
		ConflictException ce = new ConflictException();
		assertEquals("Schedule conflict.", ce.getMessage());
	}
}