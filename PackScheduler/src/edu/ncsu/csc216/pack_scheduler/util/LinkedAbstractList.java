package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Class defines state and behavior for a custom LinkedAbstractList class that does not
 * allow duplicates or null elements. Used for CourseRoll to manage the list of
 * Students registered for a Course. Class implements AbstractList methods for
 * get, set, add, remove, and size.
 * 
 * @author Nick Garner
 *
 * @param <E> The object type that the list will manage.
 */
public class LinkedAbstractList<E> extends AbstractList<E> {

	/** The node representing the front of the LinkedAbstractList */
	private ListNode front;
	/** The number of elements in the list */
	private int size;
	/** The maximum allowed number of elements in the list */
	private int capacity;

	/**
	 * Constructor initializes the front of the list to null and the size to 0.
	 * Capacity is set to the given int parameter.
	 * 
	 * @param capacity The number to set the list's capacity to.
	 * @throws IllegalArgumentException If capacity is less than 0.
	 */
	public LinkedAbstractList(int capacity) throws IllegalArgumentException {
		front = null;
		size = 0;
		setCapacity(capacity);
	}

	/**
	 * Sets the list's capacity to the given int parameter.
	 * 
	 * @param capacity The number to set the list's capacity to.
	 * @throws IllegalArgumentException If parameter is less than 0 or less than the
	 *                                  list's size.
	 */
	public void setCapacity(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Capacity cannot be less than zero.");
		}
		if (capacity < size) {
			throw new IllegalArgumentException("Capacity cannot be less than the current list's size.");
		}
		this.capacity = capacity;
	}

	/**
	 * Returns the element at the specified index.
	 * 
	 * @param index The index of the element to get.
	 * @return The element at the specified index.
	 * @throws IndexOutOfBoundsException If index is less than 0 or greater than or
	 *                                   equal to the list's size.
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		ListNode current = front;
		int i = 0;
		while (current != null && i < index) {
			current = current.next;
			i++;
		}
		if (current != null && i == index) {
			return current.data;
		}
		return null;
	}

	/**
	 * Replaces the object data at the given index with the parameter data.
	 * 
	 * @param index The index of the element to replace the data of.
	 * @param data  The data to set.
	 * @return Returns the data that was overwritten.
	 * @throws NullPointerException      When data parameter is null.
	 * @throws IllegalArgumentException  When data parameter is a duplicate of data
	 *                                   already in the list.
	 * @throws IndexOutOfBoundsException When index is less than 0 or greater than
	 *                                   or equal to the list's size.
	 */
	@Override
	public E set(int index, E data) {
		if (data == null) {
			throw new NullPointerException("Cannot add null element.");
		}
		if (dupeCheck(data)) {
			throw new IllegalArgumentException("Cannot add duplicate element.");
		}
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		ListNode current = front;
		int i = 0;
		while (current != null && i < index) {
			current = current.next;
			i++;
		}
		if (index == 0) {
			E temp = front.data;
			front.data = data;
			return temp;
		} else if (current != null && i == index) {
			E temp = current.data;
			current.data = data;
			return temp;
		}
		return null;
	}

	/**
	 * Inserts the given element at the specified index in the list.
	 * 
	 * @param index The index at which to add the element.
	 * @param data  The object data to add to the list at the specified index.
	 * @throws IllegalArgumentException  If list is full or element is a duplicate.
	 * @throws NullPointerException      If element to add is null.
	 * @throws IndexOutOfBoundsException If index is less than 0 or greater than the
	 *                                   list's size.
	 */
	@Override
	public void add(int index, E data) {
		if (capacity == size()) {
			throw new IllegalArgumentException("List is full.");
		}
		if (data == null) {
			throw new NullPointerException("Cannot add null element.");
		}
		if (dupeCheck(data)) {
			throw new IllegalArgumentException("Cannot add duplicate element.");
		}
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		ListNode toAdd = new ListNode(data);
		ListNode current = front;
		ListNode previous = null;
		int i = 0;
		while (current != null && i < index) {
			previous = current;
			current = current.next;
			i++;
		}
		if (index == 0) {
			toAdd.next = front;
			front = toAdd;
			size++;
		} else if (i == index) {
			previous.next = toAdd;
			toAdd.next = current;
			size++;
		}
	}

	/**
	 * Moves sequentially down the list to the given index and updates list
	 * references to remove the element at the index.
	 * 
	 * @param index The index of the element to remove from the list.
	 * @return The element that was removed.
	 * @throws IndexOutOfBoundsException If index is less than 0 or greater than or
	 *                                   equal to the list's size.
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		ListNode current = front;
		ListNode previous = null;
		int i = 0;
		while (current != null && i < index) {
			previous = current;
			current = current.next;
			i++;
		}
		if (current != null && i == index) {
			if (current == front) {
				front = current.next;
				size--;
				return current.data;
			} else {
				previous.next = current.next;
				size--;
				return current.data;
			}
		}
		return null;
	}

	/**
	 * Returns number of elements currently in the list.
	 * 
	 * @return The number of elements in the list.
	 */
	@Override
	public int size() {
		return size;
	}

	private boolean dupeCheck(E data) {
		ListNode current = front;
		while (current != null) {
			if (current.data == data) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	/**
	 * Inner class defines state and constructors for LinkedAbstractList node objects. Each
	 * element in the LinkedAbstractList consists of a data Object and a reference to the
	 * next element in the list.
	 * 
	 * @author Nick Garner
	 *
	 */
	private class ListNode {

		/** The object data contained in the node */
		E data;
		/** Reference to the next node in the LinkedAbstractList */
		private ListNode next;

		/**
		 * Constructs a ListNode with no reference to the next node. Used for adding an
		 * element to the end of the list.
		 * 
		 * @param data The object data to contain in the ListNode
		 */
		public ListNode(E data) {
			this(data, null);
		}

		/**
		 * Constructs a ListNode with object data and a reference to the next Node. Used
		 * for inserting an element in the middle of a list.
		 * 
		 * @param data The object data to contain in the ListNode
		 * @param next Reference to the next element in the linked list
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}
}
