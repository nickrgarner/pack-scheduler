package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Class tests LinkedListRecursive for proper functionality and exception
 * handling.
 * 
 * @author Nick Garner
 *
 */
public class LinkedListRecursiveTest {

	/** LinkedListRecursive object to test */
	LinkedListRecursive<String> testList;
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
	 * Runs before each test to instantiate a new LinkedListRecursive object to test
	 * on
	 * 
	 * @throws Exception If error occurs during setup
	 */
	@Before
	public void setUp() throws Exception {
		testList = new LinkedListRecursive<String>();
	}

	/**
	 * Tests that constructor properly instantiates an empty list
	 */
	@Test
	public void testLinkedListRecursive() {
		assertEquals(0, testList.size());
	}

	/**
	 * Tests that isEmpty properly returns true for empty list, false for occupied
	 * list
	 */
	@Test
	public void testIsEmpty() {
		assertTrue(testList.isEmpty());
		assertTrue(testList.add(STRING1));
		assertFalse(testList.isEmpty());
	}

	/**
	 * Tests that size returns correct number of elements in list
	 */
	@Test
	public void testSize() {
		assertTrue(testList.add(STRING1));
		assertTrue(testList.add(STRING2));
		assertTrue(testList.add(STRING3));
		assertEquals(3, testList.size());
	}

	/**
	 * Tests that add(E) properly adds to end of list and throws exception for null
	 * or duplicate elements
	 */
	@Test
	public void testAddE() {
		// Add first element
		assertTrue(testList.add(STRING10));

		// Try duplicate
		try {
			testList.add(STRING10);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("List cannot contain duplicate elements.", e.getMessage());
		}
		try {
			testList.add(STRINGDUPE);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("List cannot contain duplicate elements.", e.getMessage());
		}

		// Try null element
		try {
			testList.add(STRINGNULL);
			fail();
		} catch (NullPointerException e) {
			assertEquals("List cannot contain null elements.", e.getMessage());
		}

		// Add after first element
		assertTrue(testList.add(STRING1));
	}

	/**
	 * Tests that add(int E) properly inserts element at given index and throws
	 * exceptions for index out of bounds, null, or duplicate elements
	 */
	@Test
	public void testAddIntE() {
		// Test exceptions
		try {
			testList.add(0, STRINGNULL);
			fail();
		} catch (NullPointerException e) {
			assertEquals("List cannot contain null elements.", e.getMessage());
		}
		try {
			testList.add(-1, STRING4);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		try {
			testList.add(1, STRING5);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}

		// Valid add
		testList.add(0, STRING10);
		assertEquals(1, testList.size());

		// Try to add duplicate
		try {
			testList.add(1, STRING10);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("List cannot contain duplicate elements.", e.getMessage());
		}
		try {
			testList.add(1, STRINGDUPE);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("List cannot contain duplicate elements.", e.getMessage());
		}

		// Valid adds
		testList.add(1, STRING6);
		testList.add(1, STRING7);
		assertEquals(3, testList.size());
		assertEquals(STRING7, testList.get(1));
		assertEquals(STRING6, testList.get(2));
	}

	/**
	 * Tests that get returns correct element at index and throws exception for
	 * index out of bounds
	 */
	@Test
	public void testGet() {
		assertTrue(testList.add(STRING8));
		assertTrue(testList.add(STRING9));
		assertTrue(testList.add(STRING10));
		assertTrue(testList.add(STRING11));
		assertEquals(4, testList.size());
		assertEquals(STRING8, testList.get(0));
		assertEquals(STRING11, testList.get(3));
		assertEquals(STRING9, testList.get(1));

		// Test index out of bounds
		try {
			testList.get(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		try {
			testList.get(4);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
	}

	/**
	 * Tests that remove removes correct node based on given element and returns
	 * true for success, false for failure.
	 */
	@Test
	public void testRemoveE() {
		// Remove from empty list
		assertFalse(testList.remove(STRING1));

		// Remove null element
		assertFalse(testList.remove(STRINGNULL));

		// Add elements
		assertTrue(testList.add(STRING1));
		assertTrue(testList.add(STRING2));
		assertTrue(testList.add(STRING3));
		assertTrue(testList.add(STRING4));
		assertTrue(testList.add(STRING5));
		assertTrue(testList.add(STRING6));
		assertTrue(testList.add(STRING7));
		assertTrue(testList.add(STRING8));
		assertEquals(8, testList.size());

		// Remove from front
		assertTrue(testList.remove(STRING1));
		assertEquals(7, testList.size());
		assertEquals(STRING2, testList.get(0));

		// Remove from back
		assertTrue(testList.remove(STRING8));
		assertEquals(6, testList.size());
		assertEquals(STRING7, testList.get(5));

		// Remove from middle
		assertTrue(testList.remove(STRING4));
		assertEquals(5, testList.size());
		assertEquals(STRING5, testList.get(2));

		// Remove non-existent element
		assertFalse(testList.remove(STRING11));
	}

	/**
	 * Tests that remove(int) removes proper node and throws exception for index out
	 * of bounds.
	 */
	@Test
	public void testRemoveInt() {
		assertTrue(testList.add(STRING1));
		assertTrue(testList.add(STRING2));
		assertTrue(testList.add(STRING3));
		assertTrue(testList.add(STRING4));
		assertTrue(testList.add(STRING5));
		assertTrue(testList.add(STRING6));
		assertTrue(testList.add(STRING7));
		assertTrue(testList.add(STRING8));
		assertEquals(8, testList.size());

		// Remove from front
		assertEquals(STRING1, testList.remove(0));
		assertEquals(7, testList.size());
		assertEquals(STRING2, testList.get(0));

		// Remove from back
		assertEquals(STRING8, testList.remove(6));
		assertEquals(6, testList.size());
		assertEquals(STRING7, testList.get(5));

		// Remove from middle
		assertEquals(STRING4, testList.remove(2));
		assertEquals(5, testList.size());
		assertEquals(STRING5, testList.get(2));

		// Test index out of bounds
		try {
			testList.remove(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		try {
			testList.remove(5);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
	}

	/**
	 * Tests that set(int E) changes correct node to given element and returns old
	 * node data. Also tests for exceptions for null element and index out of
	 * bounds.
	 */
	@Test
	public void testSet() {
		assertTrue(testList.add(STRING1));
		assertTrue(testList.add(STRING2));
		assertTrue(testList.add(STRING3));
		assertTrue(testList.add(STRING4));
		assertEquals(4, testList.size());

		// Set front
		assertEquals(STRING1, testList.set(0, STRING5));
		assertEquals(4, testList.size());
		assertEquals(STRING5, testList.get(0));

		// Set back
		assertEquals(STRING4, testList.set(3, STRING6));
		assertEquals(4, testList.size());
		assertEquals(STRING6, testList.get(3));

		// Set middle
		assertEquals(STRING2, testList.set(1, STRING7));
		assertEquals(4, testList.size());
		assertEquals(STRING7, testList.get(1));

		// Test index exception
		try {
			testList.set(-1, STRING8);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		try {
			testList.set(4, STRING8);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}

		// Test null exception
		try {
			testList.set(2, STRINGNULL);
			fail();
		} catch (NullPointerException e) {
			assertEquals("List cannot contain null elements.", e.getMessage());
		}
	}

	/**
	 * Tests that contains properly returns true for element in list and false
	 * otherwise.
	 */
	@Test
	public void testContains() {
		assertTrue(testList.add(STRING1));
		assertTrue(testList.add(STRING2));
		assertTrue(testList.add(STRING3));
		assertTrue(testList.add(STRING10));
		assertEquals(4, testList.size());

		assertTrue(testList.contains(STRING1));
		assertTrue(testList.contains(STRING3));
		assertTrue(testList.contains(STRING10));
		assertTrue(testList.contains(STRINGDUPE));
		assertFalse(testList.contains(STRING4));
		assertFalse(testList.contains(STRINGNULL));
		assertFalse(testList.contains(""));
	}
}