package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests ArrayStack class for proper functionality and exception handling.
 * 
 * @author Nick Garner
 *
 */
public class ArrayStackTest {

	/** Stack object to test */
	ArrayStack<String> stack;
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
		stack = new ArrayStack<String>(5);
	}

	/**
	 * Tests that constructor properly creates an empty stack
	 */
	@Test
	public void testArrayStack() {
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
			assertEquals("Object to add cannot be a duplicate", e.getMessage());
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
			assertEquals("Stack is full.", e.getMessage());
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
	 * Tests that setCapacity properly updates the stack's capacity
	 */
	@Test
	public void testSetCapacity() {
		try {
			stack.setCapacity(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Capacity is invalid.", e.getMessage());
		}
		stack.push(STRING1);
		stack.push(STRING2);
		stack.push(STRING3);
		stack.push(STRING4);
		stack.push(STRING5);
		try {
			stack.setCapacity(4);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Capacity is invalid.", e.getMessage());
		}
		stack.setCapacity(6);
		stack.push(STRING6);
		assertEquals(6, stack.size());
		assertEquals(STRING6, stack.pop());
	}

}
