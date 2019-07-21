package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedList<E> extends AbstractSequentialList<E> {

	private ListNode front = null;
	private ListNode back = null;
	private int size;

	public LinkedList() {
		front = new ListNode(null, null, back);
		back = new ListNode(null, front, null);
		size = 0;
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		try {
			return new LinkedListIterator(index);
		} catch (IndexOutOfBoundsException e) {
			throw e;
		}
	}

	@Override
	public void add(int index, E element) {
		if (contains(element)) {
			throw new IllegalArgumentException("List cannot contain duplicate elements.");
		} else {
			super.add(index, element);
		}
	}

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

	private class LinkedListIterator implements ListIterator<E> {

		public ListNode previous;
		public ListNode next;
		public int previousIndex;
		public int nextIndex;
		public ListNode lastRetrieved;

		public LinkedListIterator(int index) {
			if (index < 0 || index >= size) {
				throw new IndexOutOfBoundsException();
			}
			lastRetrieved = null;
			// Set pointers to front and null, set counter to -1 since we're starting at
			// null front
			ListNode current = front;
			ListNode previous = null;
			int counter = -1;
			// Move pointers through list until counter = the index you want
			while (current.next != null && counter < index) {
				previous = current;
				current = current.next;
				counter++;
			}
			this.previous = previous;
			this.next = current;
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
				throw new NullPointerException();
			} else {
				previous.next = new ListNode(data, previous, next);
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

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}

		@Override
		public void set(E arg0) {
			// TODO Auto-generated method stub

		}

	}
}
