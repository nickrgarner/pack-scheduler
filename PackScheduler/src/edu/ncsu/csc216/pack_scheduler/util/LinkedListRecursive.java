package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Class defines state and behavior for a recursive LinkedList. Inner ListNode
 * class defines state of list elements and recursive behavior of outer methods.
 * Functionality is provided for add, remove, get, set, and various data boolean
 * checks. To be used with FacultySchedule.
 * 
 * @author Nick Garner
 *
 * @param <E> Generic type
 */
public class LinkedListRecursive<E> {

	private ListNode front;
	private int size;

	/**
	 * Constructs a new, empty LinkedListRecursive with a null front and a size of
	 * 0.
	 */
	public LinkedListRecursive() {
		front = new ListNode(null, null);
		size = 0;
	}

	/**
	 * Returns true if list size is 0.
	 * 
	 * @return True if size is 0.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns size of list
	 * 
	 * @return Size of list
	 */
	public int size() {
		return size;
	}

	/**
	 * Adds the given element to the end of the list, no null or duplicate elements
	 * allowed.
	 * 
	 * @param element The data to add to the end of the list.
	 * @return True if element successfully added.
	 * @throws IllegalArgumentException If element is a duplicate of a pre-existing
	 *                                  list element.
	 */
	public boolean add(E element) {
		if (contains(element)) {
			throw new IllegalArgumentException("List cannot contain duplicate elements.");
		} else if (isEmpty()) {
			front = new ListNode(element, null);
			size++;
			return true;
		} else {
			try {
				add(size, element);
				return true;
			} catch (Exception e) {
				throw e;
			}
		}
	}

	/**
	 * Adds the given element at the specified index in the list, no null or
	 * duplicate elements allowed.
	 * 
	 * @param index   Position in the list to add the element.
	 * @param element Data to add to the list at the specified index.
	 * @throws NullPointerException      If element is null.
	 * @throws IndexOutOfBoundsException If index is less than 0 or greater than the
	 *                                   list's current size.
	 * @throws IllegalArgumentException  If element is a duplicate of a pre-existing
	 *                                   list element.
	 */
	public void add(int index, E element) {
		if (element == null) {
			throw new NullPointerException("List cannot contain null elements.");
		} else if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		} else if (contains(element)) {
			throw new IllegalArgumentException("List cannot contain duplicate elements.");
		} else if (index == 0) {
			front = new ListNode(element, front.next);
			size++;
		} else {
			front.add(index, element);
		}
	}

	/**
	 * Returns the node data at the specified index.
	 * 
	 * @param index List position of the data to return
	 * @return Returns data at the specified index.
	 * @throws IndexOutOfBoundsException If index is less than 0 or greater than the
	 *                                   list's current size.
	 */
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		} else {
			return front.get(index);
		}
	}

	/**
	 * Attempts to remove the node with the given element, returns true if
	 * successful.
	 * 
	 * @param element Data of the node to remove.
	 * @return True if successfully removed.
	 */
	public boolean remove(E element) {
		if (element == null) {
			return false;
		} else if (isEmpty()) {
			return false;
		} else if (front.data.equals(element)) {
			front = front.next;
			size--;
			return true;
		} else {
			return front.remove(element);
		}
	}

	/**
	 * Removes the element at the specified index and returns the node data that was
	 * there.
	 * 
	 * @param index Position in the list of the node to remove.
	 * @return Data contained in the removed node.
	 * @throws IndexOutOfBoundsException If given index is less than 0 or greater
	 *                                   than the list's current size.
	 */
	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		} else if (index == 0) {
			E temp = front.data;
			front = front.next;
			size--;
			return temp;
		} else {
			return front.remove(index);
		}
	}

	/**
	 * Sets the node data at the specified index to the given element and returns
	 * the data that was replaced.
	 * 
	 * @param index   Position in the list of the node to replace the data of.
	 * @param element New data to set in the specified node.
	 * @return Data that was previously held in the given node.
	 * @throws IndexOutOfBoundsException If index is less than 0 or greater than the
	 *                                   list's current size.
	 */
	public E set(int index, E element) {
		if (element == null) {
			throw new NullPointerException("List cannot contain null elements.");
		} else if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		} else {
			return front.set(index, element);
		}
	}

	/**
	 * Traverses the entire list and returns true if the given element is present
	 * anywhere.
	 * 
	 * @param element The element to search for.
	 * @return True if element is present in the list.
	 */
	public boolean contains(E element) {
		if (element == null) {
			return false;
		} else if (isEmpty()) {
			return false;
		} else if (front.data == element) {
			return true;
		} else {
			return front.contains(element);
		}
	}

	/**
	 * Inner class defines state of ListNodes that make up LinkedListRecursive's
	 * elements. Behavior is provided for recursive functionality of outer methods.
	 * 
	 * @author Nick Garner
	 *
	 */
	private class ListNode {

		/** Object data contained in the list nodes */
		public E data;
		/** Pointer to the next node in the list */
		public ListNode next;

		/**
		 * Constructs a new ListNode with the given data and a pointer to the given next
		 * node in the list.
		 * 
		 * @param data Object data to hold in the node.
		 * @param next Pointer to the next node after this one.
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}

		/**
		 * Recursive behavior of outer add method. Traverses the list until reaching the
		 * specified node, then inserts a new node with the given element and increments
		 * the list's size.
		 * 
		 * @param index   Position in the list to add the new node.
		 * @param element Object data to place into the new node.
		 */
		public void add(int index, E element) {
			int counter = 1;
			if (index == counter) {
				next = new ListNode(element, next);
				size++;
			} else {
				index--;
				next.add(index, element);
			}
		}

		/**
		 * Recursive behavior of outer get method. Traverses the list until reaching the
		 * specified index, then returns the data contained in that node.
		 * 
		 * @param index Position in the list of the node to return data from.
		 * @return Data contained in the specified node.
		 */
		public E get(int index) {
			int counter = 0;
			if (index == counter) {
				return this.data;
			} else {
				index--;
				return next.get(index);
			}
		}

		/**
		 * Recursive behavior of outer remove(int) method. Traverses the list until
		 * reaching the specified index, then removes the node and returns the data that
		 * was contained there and decrements the list's size.
		 * 
		 * @param index Position in the list of the node to remove.
		 * @return Data previously held in removed node.
		 */
		public E remove(int index) {
			int counter = 1;
			if (index == counter) {
				E temp = next.data;
				next = next.next;
				size--;
				return temp;
			} else {
				index--;
				return next.remove(index);
			}
		}

		/**
		 * Recursive behavior of outer remove(E) method. Traverses the list until
		 * reaching a node with the given element, then removes that node, decrements
		 * the list size, and returns true. Returns false if element is not found.
		 * 
		 * @param element Data of the node to remove.
		 * @return True if node removed successfully, false otherwise.
		 */
		public boolean remove(E element) {
			try {
				// Element match, remove node
				if (next.data.equals(element)) {
					next = next.next;
					size--;
					return true;
					// No match, move to next node
				} else {
					return next.remove(element);
				}
			// You've hit end of list, it's not there
			} catch (NullPointerException e) {
				return false;
			}
		}

		/**
		 * Recursive behavior of outer set method. Traverses the list until reaching the
		 * specified index, then replaces the data there with the given element and
		 * returns the old data.
		 * 
		 * @param index   Position in the list of the data to replace with element.
		 * @param element Data to replace the given node's data with.
		 * @return Old data contained in the node.
		 */
		public E set(int index, E element) {
			int counter = 0;
			if (index == counter) {
				E temp = this.data;
				this.data = element;
				return temp;
			} else {
				index--;
				return next.set(index, element);
			}
		}

		/**
		 * Recursive bevhavior of outer contains method. Traverses the list attempting
		 * to find a node with the given element data. Returns true if a match is found,
		 * false otherwise.
		 * 
		 * @param element The node data to search for.
		 * @return True if element is found, false otherwise.
		 */
		public boolean contains(E element) {
			if (next == null) {
				return false;
			} else if (next.data == element) {
				return true;
			} else {
				return next.contains(element);
			}
		}
	}
}