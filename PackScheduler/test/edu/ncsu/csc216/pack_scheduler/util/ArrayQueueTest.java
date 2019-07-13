package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests ArrayQueue class for proper functionality and exception handling
 * 
 * @author Nick Garner
 *
 */
public class ArrayQueueTest {

	/** Queue object to test */
	ArrayQueue<String> queue;
	/** String object to test with Queue */
	private static final String STRING1 = "That's not a horse, you're banging coconuts together!";
	/** String object to test with Queue */
	private static final String STRING2 = "Bring us a shrubbery!";
	/** String object to test with Queue */
	private static final String STRING3 = "What is the airspeed velocity of an unladen swallow?";
	/** String object to test with Queue */
	private static final String STRING4 = "We are the Knights who say.....NI!";
	/** String object to test with Queue */
	private static final String STRING5 = "What are you going to do? Bleed on me??";
	/** String object to test with Queue */
	private static final String STRING6 = "I fart in your general direction!";

	/**
	 * Runs before each test to instantiate a new ArrayQueue object for testing
	 * 
	 * @throws Exception If an error occurs during setup
	 */
	@Before
	public void setUp() throws Exception {
		queue = new ArrayQueue<String>(5);
	}

	/**
	 * Tests that constructor properly creates an empty queue
	 */
	@Test
	public void testArrayQueue() {
		assertEquals(0, queue.size());
		assertTrue(queue.isEmpty());
	}

	/**
	 * Tests that enqueue properly throws exceptions for duplicate elements or full
	 * queue and properly adds elements
	 */
	@Test
	public void testEnqueue() {
		queue.enqueue(STRING1);
		queue.enqueue(STRING2);
		try {
			queue.enqueue(STRING2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Object to add cannot be a duplicate", e.getMessage());
		}
		queue.enqueue(STRING3);
		queue.enqueue(STRING4);
		queue.enqueue(STRING5);
		assertFalse(queue.isEmpty());
		assertEquals(5, queue.size());
		try {
			queue.enqueue(STRING6);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Queue is full.", e.getMessage());
		}
	}

	/**
	 * Tests that dequeue properly returns the top-most element and throws exception
	 * when queue is empty
	 */
	@Test
	public void testDequeue() {
		queue.enqueue(STRING1);
		queue.enqueue(STRING2);
		queue.enqueue(STRING3);
		queue.enqueue(STRING4);
		queue.enqueue(STRING5);
		assertEquals(STRING1, queue.dequeue());
		assertEquals(STRING2, queue.dequeue());
		assertEquals(STRING3, queue.dequeue());
		assertEquals(STRING4, queue.dequeue());
		assertEquals(STRING5, queue.dequeue());
		try {
			queue.dequeue();
			fail();
		} catch (NoSuchElementException e) {
			assertNull(e.getMessage());
		}
	}

//	@Test
//	public void testIsEmpty() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSize() {
//		fail("Not yet implemented");
//	}

	/**
	 * Tests that setCapacity properly updates the queue's capacity
	 */
	@Test
	public void testSetCapacity() {
		try {
			queue.setCapacity(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Capacity is invalid.", e.getMessage());
		}
		queue.enqueue(STRING1);
		queue.enqueue(STRING2);
		queue.enqueue(STRING3);
		queue.enqueue(STRING4);
		queue.enqueue(STRING5);
		try {
			queue.setCapacity(4);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Capacity is invalid.", e.getMessage());
		}
		queue.setCapacity(6);
		queue.enqueue(STRING6);
		assertEquals(6, queue.size());
		assertEquals(STRING1, queue.dequeue());
	}
}