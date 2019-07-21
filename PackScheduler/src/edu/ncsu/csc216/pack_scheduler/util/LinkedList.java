package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Class defines LinkedList structure with generic type using Iterator to carry
 * out list operations.
 * 
 * @author Nick Garner
 *
 * @param <E> Generic type
 */
public class LinkedList<E> extends AbstractSequentialList<E> {

	private ListNode front;
	private ListNode back;
	private int size;

	/**
	 * Constructs a new LinkedList with null front and back elements
	 */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null);
		front.next = back;
		back.prev = front;
		size = 0;
	}

	/**
	 * Instantiates a new list iterator at the given index
	 * 
	 * @param index The position to instantiate the iterator at
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		try {
			return new LinkedListIterator(index);
		} catch (IndexOutOfBoundsException e) {
			throw e;
		}
	}

	/**
	 * Adds the specified element at the given list index
	 * 
	 * @param index   The index to insert the new element at
	 * @param element The element to add to the list
	 * @throws IllegalArgumentException If element is a duplicate of an existing
	 *                                  list element
	 */
	@Override
	public void add(int index, E element) {
		for (int i = 0; i < size; i++) {
			if (get(i).equals(element)) {
				throw new IllegalArgumentException ("List cannot contain duplicate elements.");
			}
		}
		super.add(index, element);
//		if (index < 0 || index > size) {
//			throw new IndexOutOfBoundsException("Index is out of bounds.");
//		} else {
//			ListIterator<E> iterator = listIterator(index);
//			iterator.add(element);
//		}
	}

	/**
	 * Sets the element at the given index to the element param
	 * 
	 * @param index   Index of the element to set
	 * @param element The element to override the target node with
	 * @throws IllegalArgumentException If element is a duplicate of an existing
	 *                                  list element
	 */
	@Override
	public E set(int index, E element) {
		for (int i = 0; i < size; i++) {
			if (get(i).equals(element)) {
				throw new IllegalArgumentException("List cannot contain duplicate elements.");
			}
		}
		return super.set(index, element);
	}

	/**
	 * Returns the elements of items in the list
	 * 
	 * @return Number of elements in the list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Inner class defines state and constructors for ListNodes that will serve as
	 * elements in the LinkedList. ListNodes have pointers for both next and
	 * previous element in the list.
	 * 
	 * @author Nick Garner
	 *
	 */
	private class ListNode {

		/** Object data contained in the node */
		public E data;
		/** Pointer to the previous element in the list */
		public ListNode prev;
		/** Pointer to the next element in the list */
		public ListNode next;

		/**
		 * Constructs a new ListNode with the given Object data and null references to
		 * previous and next.
		 * 
		 * @param data Object data to store in this ListNode
		 */
		public ListNode(E data) {
			this(data, null, null);
		}

		/**
		 * Constructs a ListNode with the given Object data and the specified references
		 * to the previous and next elements in the list.
		 * 
		 * @param data Object data to store in this ListNode
		 * @param prev Pointer to the previous node in the list
		 * @param next Pointer to the next node in the list
		 */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}

	/**
	 * Inner class defines state and behavior of iterator object to traverse outer
	 * LinkedList class. Iterator shares LinkedList's type and provides most of the
	 * list's add, remove, get etc functionality.
	 * 
	 * @author Nick Garner
	 *
	 */
	private class LinkedListIterator implements ListIterator<E> {

		/** Pointer referencing the previous node in the list */
		public ListNode previous;
		/** Pointer referencing the next node in the list */
		public ListNode next;
		/** Index of the previous node */
		public int previousIndex;
		/** Index of the next node */
		public int nextIndex;
		/** Node object last retrieved by previous() or next() methods */
		public ListNode lastRetrieved;

		/**
		 * Constructs a new LinkedListIterator at the specified list index
		 * 
		 * @param index Determines the starting location of the iterator in the list.
		 *              Iterator is placed between index and index - 1.
		 */
		public LinkedListIterator(int index) {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException("Index is out of bounds.");
			}
			lastRetrieved = null;
			// Set pointers to front and null, set counter to -1 since we're starting at
			// null front
			ListNode ptrCurrent = front;
			ListNode ptrPrevious = null;
			int counter = -1;
			// Move pointers through list until counter = the index you want
			while (ptrCurrent.next != null && counter < index) {
				ptrPrevious = ptrCurrent;
				ptrCurrent = ptrCurrent.next;
				counter++;
			}
			this.previous = ptrPrevious;
			this.next = ptrCurrent;
			previousIndex = counter - 1;
			nextIndex = counter;
		}

		/**
		 * Adds a new node with the given data between the iterator's current previous
		 * and next nodes.
		 * 
		 * @param data The data to store in the new node to be added
		 * @throws NullPointerException If data param is null
		 */
		@Override
		public void add(E data) {
			if (data == null) {
				throw new NullPointerException("Object to add cannot be null.");
			} else {
				previous.next = new ListNode(data, previous, next);
				next.prev = previous.next;
				lastRetrieved = null;
				size++;
			}
		}

		/**
		 * Returns true if the list iterator has more elements traversing forward
		 * 
		 * @return True if the iterator has more elements traversing forward
		 */
		@Override
		public boolean hasNext() {
			return next.data != null;
		}

		/**
		 * Returns true if the list iterator has more elements traversing in reverse
		 * 
		 * @return True if the iterator has more elements traversing in reverse
		 */
		@Override
		public boolean hasPrevious() {
			return previous.data != null;
		}

		/**
		 * Returns the data contained in the next node in the list, unless null
		 * 
		 * @return Data in the next node in the list
		 * @throws NoSuchElementException If next.data is null
		 */
		@Override
		public E next() {
			if (next.data == null) {
				throw new NoSuchElementException();
			} else {
				lastRetrieved = next;
				return next.data;
			}
		}

		/**
		 * Returns the index of the next element in the list
		 * 
		 * @return Index of the next element in the list
		 */
		@Override
		public int nextIndex() {
			return nextIndex;
		}

		/**
		 * Returns the data contained in the previous node in the list, unless null
		 * 
		 * @return Data in the previous node in the list
		 * @throws NoSuchElementException If previous.data is null
		 */
		@Override
		public E previous() {
			if (previous.data == null) {
				throw new NoSuchElementException();
			} else {
				lastRetrieved = previous;
				return previous.data;
			}
		}

		/**
		 * Returns the index of the previous element in the list
		 * 
		 * @return Index of the previous element in the list
		 */
		@Override
		public int previousIndex() {
			return previousIndex;
		}

		/**
		 * Removes the lastRetrieved element. Throws exception if lastRetrieved is null.
		 * 
		 * @throws IllegalStateException If lastRetrieved is null
		 */
		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalStateException("lastRetrieved is null.");
			} else {
				lastRetrieved.prev.next = lastRetrieved.next;
				lastRetrieved.next.prev = lastRetrieved.prev;
				lastRetrieved = null;
				size--;
			}

		}

		/**
		 * Sets the lastRetrieved element to the given data param
		 * 
		 * @param data The data to set the lastRetrieved element to
		 * @throws IllegalStateException if data is null
		 */
		@Override
		public void set(E data) {
			if (lastRetrieved == null) {
				throw new IllegalStateException("lastRetrieved is null.");
			} else if (data == null) {
				throw new NullPointerException("Object to set cannot be null.");	
			} else {
				lastRetrieved.data = data;
			}
		}
	}
}
