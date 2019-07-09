package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Class tests LinkedAbstractList for proper functionality and exception
 * throwing.
 * 
 * @author Nick Garner
 *
 */
public class LinkedAbstractListTest {

	private LinkedAbstractList<String> prequelMemes;
	private static final String MEME1 = "Not just the men, but the women and the children too.";
	private static final String MEME2 = "This is getting ridiculous. Now there are two of them!";
	private static final String MEME3 = "It's over Anakin, I have the higher ground.";
	private static final String MEME4 = "So this is how democracy dies. With thunderous applause.";
	private static final String MEME5 = "Ah General Kenobi, you are a bold one.";
	private static final String MEME6 = "I AM THE SENATE!";

	/**
	 * Instantiates a new LinkedList before each test.
	 * 
	 * @throws Exception If an error occurs during setup.
	 */
	@Before
	public void setUp() throws Exception {
		prequelMemes = new LinkedAbstractList<String>(5);
	}

	/**
	 * Tests that constructor properly creates an empty list.
	 */
	@Test
	public void testLinkedAbstractList() {
		assertEquals(0, prequelMemes.size());
	}

	/**
	 * Tests that setCapacity properly throws exception for invalid capacity and
	 * properly sets capacity to int param.
	 */
	@Test
	public void testSetCapacity() {
		try {
			prequelMemes.setCapacity(-1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Capacity cannot be less than zero.", e.getMessage());
		}
		prequelMemes.add(0, MEME1);
		prequelMemes.add(1, MEME2);
		prequelMemes.add(2, MEME3);
		assertEquals(3, prequelMemes.size());
		try {
			prequelMemes.setCapacity(2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Capacity cannot be less than the current list's size.", e.getMessage());
		}
	}

	/**
	 * Tests that get() returns element at correct index and properly throws
	 * exception when index is out of bounds.
	 */
	@Test
	public void testGetInt() {
		try {
			prequelMemes.get(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		try {
			prequelMemes.get(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		try {
			prequelMemes.get(1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		prequelMemes.add(0, MEME1);
		prequelMemes.add(1, MEME2);
		prequelMemes.add(2, MEME3);
		prequelMemes.add(3, MEME4);
		prequelMemes.add(4, MEME5);
		assertEquals(5, prequelMemes.size());
		assertEquals(MEME1, prequelMemes.get(0));
		assertEquals(MEME5, prequelMemes.get(4));
		assertEquals(MEME3, prequelMemes.get(2));
	}

	/**
	 * Tests that set(int,E) properly replaces the data at the given index with the
	 * given data and properly throws exception if index is out of bounds or data is
	 * null or duplicate.
	 */
	@Test
	public void testSetIntE() {
		try {
			prequelMemes.set(0, null);
			fail();
		} catch (NullPointerException e) {
			assertEquals("Cannot add null element.", e.getMessage());
		}
		try {
			prequelMemes.set(-1, MEME5);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		try {
			prequelMemes.set(0, MEME5);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		try {
			prequelMemes.set(1, MEME5);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		prequelMemes.add(0, MEME1);
		prequelMemes.add(1, MEME2);
		prequelMemes.add(2, MEME3);
		try {
			prequelMemes.set(2, MEME2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Cannot add duplicate element.", e.getMessage());
		}
		assertEquals(MEME1, prequelMemes.set(0, MEME6));
		assertEquals(MEME6, prequelMemes.get(0));
		assertEquals(MEME3, prequelMemes.set(2, MEME1));
		assertEquals(MEME1, prequelMemes.get(2));
	}

	/**
	 * Tests that add(int, E) properly adds the data object at the specified index
	 * without overwriting existing elements. Also tests that exception is thrown
	 * for index out of bounds or null or duplicate element.
	 */
	@Test
	public void testAddIntE() {
		try {
			prequelMemes.add(0, null);
			fail();
		} catch (NullPointerException e) {
			assertEquals("Cannot add null element.", e.getMessage());
		}
		try {
			prequelMemes.add(-1, MEME5);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		try {
			prequelMemes.add(1, MEME5);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		prequelMemes.add(0, MEME1);
		prequelMemes.add(1, MEME2);
		prequelMemes.add(2, MEME3);
		try {
			prequelMemes.add(3, MEME2);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Cannot add duplicate element.", e.getMessage());
		}
		prequelMemes.add(3, MEME4);
		prequelMemes.add(4, MEME5);
		try {
			prequelMemes.add(5, MEME6);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("List is full.", e.getMessage());
		}
	}

	/**
	 * Tests that remove(int) properly removes the element at the specified index
	 * and returns the value there. Also tests that exception is properly thrown for
	 * index out of bounds.
	 */
	@Test
	public void testRemoveInt() {
		try {
			prequelMemes.remove(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		try {
			prequelMemes.remove(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		try {
			prequelMemes.remove(1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals("Index is out of bounds.", e.getMessage());
		}
		prequelMemes.add(0, MEME1);
		prequelMemes.add(1, MEME2);
		prequelMemes.add(2, MEME3);
		prequelMemes.add(3, MEME4);
		assertEquals(MEME1, prequelMemes.remove(0));
		assertEquals(3, prequelMemes.size());
		assertEquals(MEME3, prequelMemes.remove(1));
		assertEquals(2, prequelMemes.size());
		assertEquals(MEME4, prequelMemes.remove(1));
		assertEquals(1, prequelMemes.size());
	}
}