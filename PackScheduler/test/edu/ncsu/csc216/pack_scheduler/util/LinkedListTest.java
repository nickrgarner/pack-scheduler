package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests custom LinkedList class for proper functionality and exception
 * handling. List does not allow duplicate or null elements.
 * 
 * @author Nick Garner
 *
 */
public class LinkedListTest {

	/** LinkedList object to test */
	LinkedList<String> testList;
	/** Valid string */
	private static final String STRING1 = "One ring to rule them all. One ring to find them. One ring to bring them all. And in the darkness bind them.";
	/** Valid string */
	private static final String STRING2 = "YOU SHALL NOT PASS!";
	/** Valid string */
	private static final String STRING3 = "The beacons are lit! Gondor calls for aid!";
	/** Valid string */
	private static final String STRING4 = "Nobody tosses a Dwarf!";
	/** Valid string */
	private static final String STRING5 = "A wizard is never late, Frodo Baggins. Nor is he early.";
	/** Valid string */
	private static final String STRING6 = "One does not simply walk into Mordor";
	/** Valid string */
	private static final String STRING7 = "You fool. No man can kill me. Die now.";
	/** Valid string */
	private static final String STRING8 = "I am no man.";
	/** Valid string */
	private static final String STRING9 = "Fly, you fools!";
	/** Valid string */
	private static final String STRING10 = "They're taking the hobbits to Isengard!";
	/** Valid string */
	private static final String STRING11 = "So it begins";
	/** Duplicate string to test adding a duplicate */
	private static final String STRINGDUPE = "They're taking the hobbits to Isengard!";
	/** Null string to test adding a null object */
	private static final String STRINGNULL = null;

	/**
	 * Runs before each test to instantiate a new LinkedList object to test on
	 * 
	 * @throws Exception If error occurs during setup
	 */
	@Before
	public void setUp() throws Exception {
		testList = new LinkedList<String>();
	}

	/**
	 * Tests that null constructor properly creates an empty list
	 */
	@Test
	public void testLinkedList() {
		assertEquals(0, testList.size());
	}

	/**
	 * Tests that size() properly returns the number of non-null elements in the
	 * list
	 */
	@Test
	public void testSize() {
		testList.add(0, STRING1);
		testList.add(1, STRING2);
		testList.add(2, STRING3);
		assertEquals(3, testList.size());
	}

	/**
	 * Tests that add(int, E) adds the proper object to the given index and properly
	 * throws exceptions when given invalid parameters
	 */
	@Test
	public void testAddIntE() {
		// Add to end
		testList.add(0, STRING1);
		testList.add(1, STRING2);
		testList.add(2, STRING3);
		assertEquals(3, testList.size());
		assertEquals(STRING2, testList.get(1));
		// Add to middle
		testList.add(1, STRING4);
		assertEquals(4, testList.size());
		assertEquals(STRING4, testList.get(1));
		assertEquals(STRING2, testList.get(2));
		// Add to front
		testList.add(0, STRING5);
		assertEquals(5, testList.size());
		assertEquals(STRING5, testList.get(0));
		assertEquals(STRING1, testList.get(1));
		assertEquals(STRING4, testList.get(2));
		assertEquals(STRING2, testList.get(3));
		assertEquals(STRING3, testList.get(4));
		// Test capacity increase
		testList.add(5, STRING6);
		testList.add(6, STRING7);
		testList.add(7, STRING8);
		testList.add(8, STRING9);
		testList.add(9, STRING10);
		assertEquals(10, testList.size());
		testList.add(10, STRING11);
		assertEquals(STRING5, testList.get(0));
		assertEquals(STRING1, testList.get(1));
		assertEquals(STRING4, testList.get(2));
		assertEquals(STRING2, testList.get(3));
		assertEquals(STRING3, testList.get(4));
		assertEquals(STRING6, testList.get(5));
		assertEquals(STRING7, testList.get(6));
		assertEquals(STRING8, testList.get(7));
		assertEquals(STRING9, testList.get(8));
		assertEquals(STRING10, testList.get(9));
		assertEquals(STRING11, testList.get(10));
		assertEquals(11, testList.size());
		// Test bad index
		try {
			testList.add(12, "Extra string");
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		try {
			testList.add(-1, "Extra string");
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		// Test null add
		try {
			testList.add(11, STRINGNULL);
			fail();
		} catch (NullPointerException e) {
			assertEquals("Object to add cannot be null.", e.getMessage());
		}
		// Test duplicate add
		try {
			testList.add(11, STRINGDUPE);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("List cannot contain duplicate elements.", e.getMessage());
		}
	}

	/**
	 * Tests that remove(int) properly removes the given element and reorders the
	 * list
	 */
	@Test
	public void testRemoveInt() {
		testList.add(0, STRING1);
		testList.add(1, STRING2);
		testList.add(2, STRING3);
		testList.add(3, STRING4);
		testList.add(4, STRING5);
		testList.add(5, STRING6);
		testList.add(6, STRING7);
		testList.add(7, STRING8);
		testList.add(8, STRING9);
		testList.add(9, STRING10);
		testList.add(10, STRING11);
		assertEquals(11, testList.size());
		// Remove at front
		assertEquals(STRING1, testList.remove(0));
		assertEquals(10, testList.size());
		assertEquals(STRING2, testList.get(0));
		assertEquals(STRING3, testList.get(1));
		assertEquals(STRING4, testList.get(2));
		assertEquals(STRING5, testList.get(3));
		assertEquals(STRING6, testList.get(4));
		assertEquals(STRING7, testList.get(5));
		assertEquals(STRING8, testList.get(6));
		assertEquals(STRING9, testList.get(7));
		assertEquals(STRING10, testList.get(8));
		assertEquals(STRING11, testList.get(9));
		// Remove at middle
		assertEquals(STRING4, testList.remove(2));
		assertEquals(9, testList.size());
		assertEquals(STRING2, testList.get(0));
		assertEquals(STRING3, testList.get(1));
		assertEquals(STRING5, testList.get(2));
		assertEquals(STRING6, testList.get(3));
		assertEquals(STRING7, testList.get(4));
		assertEquals(STRING8, testList.get(5));
		assertEquals(STRING9, testList.get(6));
		assertEquals(STRING10, testList.get(7));
		assertEquals(STRING11, testList.get(8));
		// Remove at end
		assertEquals(STRING11, testList.remove(8));
		assertEquals(8, testList.size());
		assertEquals(STRING2, testList.get(0));
		assertEquals(STRING3, testList.get(1));
		assertEquals(STRING5, testList.get(2));
		assertEquals(STRING6, testList.get(3));
		assertEquals(STRING7, testList.get(4));
		assertEquals(STRING8, testList.get(5));
		assertEquals(STRING9, testList.get(6));
		assertEquals(STRING10, testList.get(7));

		// Test bad index
		try {
			testList.remove(8);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index: 8", e.getMessage());
		}
		try {
			testList.remove(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
	}

	/**
	 * Tests that set(int, E) properly sets the given index to the given object
	 */
	@Test
	public void testSetIntE() {
		testList.add(0, STRING1);
		testList.add(1, STRING2);
		testList.add(2, STRING3);
		// Test null obj
		try {
			testList.set(1, STRINGNULL);
			fail();
		} catch (NullPointerException e) {
			assertEquals("Object to set cannot be null.", e.getMessage());
		}
		// Test bad index
		try {
			testList.set(3, STRING4);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index: 3", e.getMessage());
		}
		try {
			testList.set(-1, STRING4);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		// Test duplicate obj
		try {
			testList.set(2, STRING2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("List cannot contain duplicate elements.", e.getMessage());
		}
		// Test good value
		assertEquals(STRING1, testList.set(0, STRING4));
		assertEquals(STRING4, testList.get(0));
		assertEquals(STRING3, testList.set(2, STRING5));
		assertEquals(STRING5, testList.get(2));
	}

	/**
	 * Tests that get(int) returns the proper object
	 */
	@Test
	public void testGetInt() {
		testList.add(0, STRING1);
		testList.add(1, STRING2);
		testList.add(2, STRING3);
		// Test bad index
		try {
			testList.get(3);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index: 3", e.getMessage());
		}
		try {
			testList.get(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
	}

	/**
	 * Tests LinkedListIterator inner class methods
	 */
	@Test
	public void testIterator() {
		ListIterator<String> nullIterator = testList.listIterator(0);
		assertFalse(nullIterator.hasNext());
		assertFalse(nullIterator.hasPrevious());
		try {
			nullIterator.previous();
			fail();
		} catch (NoSuchElementException e) {
			assertNull(e.getMessage());
		}
		testList.add(0, STRING1);
		testList.add(1, STRING2);
		testList.add(2, STRING3);
		ListIterator<String> iterator = testList.listIterator(1);
		assertTrue(iterator.hasPrevious());
		assertTrue(iterator.hasNext());
		assertEquals(0, iterator.previousIndex());
		assertEquals(1, iterator.nextIndex());
		assertEquals(STRING1, iterator.previous());
		assertEquals(STRING1, iterator.next());
		assertEquals(STRING2, iterator.next());
	}
}