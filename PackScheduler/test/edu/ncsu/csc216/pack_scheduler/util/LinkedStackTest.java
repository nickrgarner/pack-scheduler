package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.EmptyStackException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests LinkedStack class for proper functionality and exception handling.
 * 
 * @author Nick Garner
 *
 */
public class LinkedStackTest {

	/** Stack object to test */
	LinkedStack<String> stack;
	/** String object to test with Stack */
	private static final String STRING1 = "That's not a horse, you're banging coconuts together!";
	/** String object to test with Stack */
	private static final String STRING2 = "Bring us a shrubbery!";
	/** String object to test with Stack */
	private static final String STRING3 = "What is the airspeed velocity of an unladen swallow?";
	/** String object to test with Stack */
	private static final String STRING4 = "We are the Knights who say.....NI!";
	/** String object to test with Stack */
	private static final String STRING5 = "What are you going to do? Bleed on me??";
	/** String object to test with Stack */
	private static final String STRING6 = "I fart in your general direction!";

	/**
	 * Runs before each test to instantiate a new ArrayStack object for testing
	 * 
	 * @throws Exception If an error occurs during setup
	 */
	@Before
	public void setUp() throws Exception {
		stack = new LinkedStack<String>(5);
	}

	/**
	 * Tests that constructor properly creates an empty stack
	 */
	@Test
	public void testLinkedStack() {
		assertEquals(0, stack.size());
		assertTrue(stack.isEmpty());
	}

	/**
	 * Tests that push properly throws exceptions for duplicate elements or full
	 * stack and properly adds elements
	 */
	@Test
	public void testPush() {
		stack.push(STRING1);
		stack.push(STRING2);
		try {
			stack.push(STRING2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Cannot add duplicate element.", e.getMessage());
		}
		stack.push(STRING3);
		stack.push(STRING4);
		stack.push(STRING5);
		assertFalse(stack.isEmpty());
		assertEquals(5, stack.size());
		try {
			stack.push(STRING6);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("List is full.", e.getMessage());
		}

	}

	/**
	 * Tests that pop properly returns top-most element and throws exception when
	 * stack is empty
	 */
	@Test
	public void testPop() {
		stack.push(STRING1);
		stack.push(STRING2);
		stack.push(STRING3);
		stack.push(STRING4);
		stack.push(STRING5);
		assertEquals(STRING5, stack.pop());
		assertEquals(STRING4, stack.pop());
		assertEquals(STRING3, stack.pop());
		assertEquals(STRING2, stack.pop());
		assertEquals(STRING1, stack.pop());
		try {
			stack.pop();
			fail();
		} catch (EmptyStackException e) {
			assertNull(e.getMessage());
		}
	}

	/**
	 * Tests that setCapacity properly updates the list's capacity
	 */
	@Test
	public void testSetCapacity() {
		stack.push(STRING1);
		stack.push(STRING2);
		stack.push(STRING3);
		stack.push(STRING4);
		stack.push(STRING5);
		stack.setCapacity(6);
		stack.push(STRING6);
		assertEquals(STRING6, stack.pop());
	}
}