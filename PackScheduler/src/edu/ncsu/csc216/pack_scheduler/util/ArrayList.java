package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Class defines state and behavior for custom ArrayList that does not allow
 * duplicate or null elements
 * 
 * @author Nick Garner
 *
 * @param <E> Generic type to be parameterized when instantiated
 */
public class ArrayList<E> extends AbstractList<E> {

	/** Default list capacity */
	private static final int INIT_SIZE = 10;
	/** List to hold E objects */
	private E[] list;
	/** Current capacity of list */
	private int size;

	/**
	 * Null constructor creates an empty list of default size 10
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		Object[] oList = new Object[INIT_SIZE];
		list = (E[]) oList;
		size = INIT_SIZE;
	}

	/**
	 * Adds the given object at the specified index and subsequently shifts
	 * surrounding elements to make room
	 * 
	 * @param index The index of the list at which to place the object
	 * @param obj   The object to add to the list
	 * @throws NullPointerException      If obj is null
	 * @throws IndexOutOfBoundsException If index is less than 0 or greater than
	 *                                   size()
	 * @throws IllegalArgumentException  If obj is a duplicate of an element already
	 *                                   in the list
	 */
	public void add(int index, E obj) {
		if (obj == null) {
			throw new NullPointerException("Object to add cannot be null");
		}
		if (index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		}
		// Check for dupes
		for (int i = 0; i < size(); i++) {
			if (obj.equals(get(i))) {
				throw new IllegalArgumentException("Object to add cannot be a duplicate");
			}
		}
		// Check if capacity needs to be increased
		if (this.size() == size) {
			this.growArray();
		}
		// Add element to end
		if (index == size()) {
			list[index] = obj;
		}
		// Add element to front or middle
		else if (index < size()) {
			// Shift elements after index to the right
			for (int i = size(); i > index; i--) {
				list[i] = list[i - 1];
			}
			set(index, obj);
		}
	}
	
	/**
	 * Creates a new list with double the current list's capacity and copies over
	 * the original list's elements
	 */
	@SuppressWarnings("unchecked")
	private void growArray() {
		Object[] newList = new Object[size * 2];
		for (int i = 0; i < size(); i++) {
			newList[i] = get(i);
		}
		list = (E[]) newList;
		size *= 2;
	}

	/**
	 * Removes the element at the specified index and shifts the remaining elements
	 * to fill the hole
	 * 
	 * @param index The index at which to remove the element
	 * @return Returns the object that was removed
	 * @throws NullPointerException      If obj is null
	 * @throws IndexOutOfBoundsException If index is less than 0 or greater than
	 *                                   size()
	 * @throws IllegalArgumentException  If obj is a duplicate of an element already
	 *                                   in the list
	 * 
	 */
	public E remove(int index) {
		if (index < 0 || index >= this.size()) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		}
		E removed = get(index);
		// Loop to shift elements left
		for (int i = index; i < size(); i++) {
			list[i] = list[i + 1];
		}
		return removed;
	}

	/**
	 * Replaces the element at the specified index with the given object
	 * 
	 * @param index The position at which to set the element
	 * @param obj   The object to set at the index
	 * @return Returns the object that was replaced
	 * @throws NullPointerException      If obj is null
	 * @throws IndexOutOfBoundsException If index is less than 0 or greater than
	 *                                   size()
	 * @throws IllegalArgumentException  If obj is a duplicate of an element already
	 *                                   in the list
	 */
	public E set(int index, E obj) {
		if (obj == null) {
			throw new NullPointerException("Object to set cannot be null");
		}
		if (index < 0 || index >= this.size()) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		}
		// Check for dupes
		for (int i = 0; i < size(); i++) {
			if (obj.equals(get(i))) {
				throw new IllegalArgumentException("Object to set cannot be a duplicate");
			}
		}
		E temp = get(index);
		list[index] = obj;
		return temp;
	}

	/**
	 * Returns the element at the given index
	 * 
	 * @param index The index of the element to return
	 * @return Returns the element at the given index
	 * @throws IndexOutOfBoundsException If the given index is less than zero or
	 *                                   greater than size()
	 */
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		} else {
			return list[index];
		}
	}

	/**
	 * Returns the number of elements in the list as an int
	 * 
	 * @return Returns the number of elements in the list
	 */
	public int size() {
		int numElements = 0;
		for (int i = 0; i < this.size; i++) {
			if (list[i] != null) {
				numElements++;
			}
		}
		return numElements;
	}
}