package edu.ncsu.csc216.collections.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Class defines JUnit test behavior for SortedList library CSC216Collections
 * 
 * @author Nick Garner
 *
 */
public class SortedListTest {

	/**
	 * Method tests that SortedList constructor properly creates an empty ArrayList
	 * and properly increases in size past initial capacity of 10.
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));

		// Test that the list grows by adding at least 11 elements
		// Remember the list's initial capacity is 10
		assertTrue(list.add("one"));
		assertTrue(list.add("two"));
		assertTrue(list.add("three"));
		assertTrue(list.add("four"));
		assertTrue(list.add("five"));
		assertTrue(list.add("six"));
		assertTrue(list.add("seven"));
		assertTrue(list.add("eight"));
		assertTrue(list.add("nine"));
		assertTrue(list.add("ten"));
		assertTrue(list.add("eleven"));
		// Test for size of 11
		assertEquals(11, list.size());
	}

	/**
	 * Tests that SortedList.add method properly adds items in sorted order and does
	 * not add null or duplicate items.
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();

		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));

		// Test adding to the front, middle and back of the list
		// add to front
		assertTrue(list.add("apple"));
		assertEquals("apple", list.get(0));
		// add to end
		assertTrue(list.add("carrot"));
		assertEquals("carrot", list.get(2));
		// add to middle
		assertTrue(list.add("broccoli"));
		assertEquals("broccoli", list.get(2));

		// Test adding a null element
		try {
			list.add(null);
			fail();
		} catch (NullPointerException e) {
			assertEquals(null, e.getMessage());
			assertEquals(4, list.size());
		}

		// Test adding a duplicate element
		try {
			list.add("carrot");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Element already in list.", e.getMessage());
			assertEquals(4, list.size());
		}
	}

	/**
	 * Tests that SortedList.get method properly retrieves item at sorted index and
	 * properly throws IndexOutOfBoundsException for index < 0 or index >= size().
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();

		// Since get() is used throughout the tests to check the
		// contents of the list, we don't need to test main flow functionality
		// here. Instead this test method should focus on the error
		// and boundary cases.

		// Test getting an element from an empty list
		try {
			list.get(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(null, e.getMessage());
		}

		// Add some elements to the list
		assertTrue(list.add("apple"));
		assertTrue(list.add("banana"));
		assertTrue(list.add("carrot"));
		assertEquals(3, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("carrot", list.get(2));

		// Test getting an element at an index < 0
		try {
			list.get(-2);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(null, e.getMessage());
		}

		// Test getting an element at size
		try {
			list.get(3);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(null, e.getMessage());
		}
	}

	/**
	 * Tests that SortedList.remove properly removes element at given index and
	 * properly generates IndexOutOfBoundsException for index < 0 || index >=
	 * size().
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();

		// Test removing from an empty list
		try {
			list.remove(0);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(null, e.getMessage());
		}
		// Add some elements to the list - at least 4
		assertTrue(list.add("apple"));
		assertTrue(list.add("banana"));
		assertTrue(list.add("carrot"));
		assertTrue(list.add("dairy"));
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("carrot", list.get(2));
		assertEquals("dairy", list.get(3));

		// Test removing an element at an index < 0
		try {
			list.remove(-1);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(null, e.getMessage());
		}

		// Test removing an element at size
		try {
			list.remove(4);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(null, e.getMessage());
		}
		// Test removing a middle element
		list.remove(2);
		assertEquals(3, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("dairy", list.get(2));

		// Test removing the last element
		list.remove(2);
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));

		// Test removing the first element
		list.remove(0);
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));

		// Test removing the last element
		list.remove(0);
		assertEquals(0, list.size());
	}

	/**
	 * Test that SortedList.indexOf method properly retrieves the index of elements
	 * both on and not on the target list, and properly throws NullPointerException
	 * for null parameter.
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();

		// Test indexOf on an empty list
		assertEquals(-1, list.indexOf("television"));
		// Add some elements
		assertTrue(list.add("apple"));
		assertTrue(list.add("banana"));
		assertTrue(list.add("carrot"));
		assertTrue(list.add("dairy"));
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("carrot", list.get(2));
		assertEquals("dairy", list.get(3));

		// Test various calls to indexOf for elements in the list
		// and not in the list
		assertEquals(1, list.indexOf("banana"));
		assertEquals(-1, list.indexOf("frisbee"));
		assertEquals(3, list.indexOf("dairy"));
		assertEquals(-1, list.indexOf("butter"));

		// Test checking the index of null
		try {
			list.indexOf(null);
			fail();
		} catch (NullPointerException e) {
			assertEquals(null, e.getMessage());
		}
	}

	/**
	 * Tests that SortedList.clear method properly clears all members from the
	 * SortedList.
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		// Add some elements
		assertTrue(list.add("apple"));
		assertTrue(list.add("banana"));
		assertTrue(list.add("carrot"));
		assertTrue(list.add("dairy"));
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("carrot", list.get(2));
		assertEquals("dairy", list.get(3));

		// Clear the list
		list.clear();

		// Test that the list is empty
		assertEquals(0, list.size());
		assertTrue(list.isEmpty());

	}

	/**
	 * Tests that SortedList.isEmpty method properly returns True for empty lists
	 * and False for lists with members.
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();

		// Test that the list starts empty
		assertTrue(list.isEmpty());

		// Add at least one element
		assertTrue(list.add("apple"));
		assertTrue(list.add("banana"));
		assertTrue(list.add("carrot"));
		assertTrue(list.add("dairy"));
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("carrot", list.get(2));
		assertEquals("dairy", list.get(3));

		// Check that the list is no longer empty
		assertFalse(list.isEmpty());

	}

	/**
	 * Tests that SortedList.contains method properly returns True if list includes
	 * parameter and False otherwise.
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();

		// Test the empty list case
		assertFalse(list.contains("test"));

		// Add some elements
		assertTrue(list.add("apple"));
		assertTrue(list.add("banana"));
		assertTrue(list.add("carrot"));
		assertTrue(list.add("dairy"));
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		assertEquals("carrot", list.get(2));
		assertEquals("dairy", list.get(3));

		// Test some true and false cases
		assertTrue(list.contains("banana"));
		assertFalse(list.contains("java"));
		assertTrue(list.contains("dairy"));
		assertFalse(list.contains("pokemon"));
	}

	/**
	 * Tests that SortedList.equals method properly returns True for lists with
	 * identical members and False otherwise.
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		// Make two lists the same and one list different
		assertTrue(list1.add("apple"));
		assertTrue(list1.add("banana"));
		assertTrue(list1.add("carrot"));
		assertTrue(list1.add("dairy"));
		assertEquals(4, list1.size());
		assertEquals("apple", list1.get(0));
		assertEquals("banana", list1.get(1));
		assertEquals("carrot", list1.get(2));
		assertEquals("dairy", list1.get(3));

		assertTrue(list2.add("apple"));
		assertTrue(list2.add("banana"));
		assertTrue(list2.add("carrot"));
		assertTrue(list2.add("dairy"));
		assertEquals(4, list2.size());
		assertEquals("apple", list2.get(0));
		assertEquals("banana", list2.get(1));
		assertEquals("carrot", list2.get(2));
		assertEquals("dairy", list2.get(3));

		assertTrue(list3.add("scorbunny"));
		assertTrue(list3.add("grookey"));
		assertTrue(list3.add("sobble"));
		assertTrue(list3.add("wooloo"));
		assertEquals(4, list3.size());
		assertEquals("grookey", list3.get(0));
		assertEquals("scorbunny", list3.get(1));
		assertEquals("sobble", list3.get(2));
		assertEquals("wooloo", list3.get(3));

		// Test for equality and non-equality
		assertTrue(list1.equals(list2));
		assertTrue(list2.equals(list1));
		assertFalse(list1.equals(list3));
		assertFalse(list3.equals(list1));
		assertFalse(list2.equals(list3));
		assertFalse(list3.equals(list2));
	}

	/**
	 * Tests that SortedList.hashCode method properly assigns matching hash value to
	 * identical lists and different hash values for different lists.
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		// Make two lists the same and one list different
		assertTrue(list1.add("apple"));
		assertTrue(list1.add("banana"));
		assertTrue(list1.add("carrot"));
		assertTrue(list1.add("dairy"));
		assertEquals(4, list1.size());
		assertEquals("apple", list1.get(0));
		assertEquals("banana", list1.get(1));
		assertEquals("carrot", list1.get(2));
		assertEquals("dairy", list1.get(3));

		assertTrue(list2.add("apple"));
		assertTrue(list2.add("banana"));
		assertTrue(list2.add("carrot"));
		assertTrue(list2.add("dairy"));
		assertEquals(4, list2.size());
		assertEquals("apple", list2.get(0));
		assertEquals("banana", list2.get(1));
		assertEquals("carrot", list2.get(2));
		assertEquals("dairy", list2.get(3));

		assertTrue(list3.add("scorbunny"));
		assertTrue(list3.add("grookey"));
		assertTrue(list3.add("sobble"));
		assertTrue(list3.add("wooloo"));
		assertEquals(4, list3.size());
		assertEquals("grookey", list3.get(0));
		assertEquals("scorbunny", list3.get(1));
		assertEquals("sobble", list3.get(2));
		assertEquals("wooloo", list3.get(3));

		// Test for the same and different hashCodes
		assertEquals(list1.hashCode(), list2.hashCode());
		assertNotEquals(list1.hashCode(), list3.hashCode());
		assertNotEquals(list2.hashCode(), list3.hashCode());
	}
}