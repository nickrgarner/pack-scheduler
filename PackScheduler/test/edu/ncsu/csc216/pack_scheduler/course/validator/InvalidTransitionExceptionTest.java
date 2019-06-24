package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

//import org.junit.Before;
import org.junit.Test;

/**
 * Tests InvalidTransitionException custom exception class
 * @author Nick Garner
 *
 */
public class InvalidTransitionExceptionTest {

//	@Before
//	public void setUp() throws Exception {
//	}

	/**
	 * Tests constructor with custom message
	 */
	@Test
	public void testMessageConstructor() {
		InvalidTransitionException e = new InvalidTransitionException("This transition is not valid.");
		assertEquals("This transition is not valid.", e.getMessage());
	}
	
	/**
	 * Tests null constructor
	 */
	@Test
	public void testNullConstructor() {
		InvalidTransitionException e = new InvalidTransitionException();
		assertEquals("Invalid FSM Transition.", e.getMessage());
	}

}
