package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Interface declares methods for Stack implementation of Array and Linked
 * lists.
 * 
 * @author Nick Garner
 *
 * @param <E> Generic parameter
 */
public interface Stack<E> {

	/**
	 * Adds the element to the top of the Stack
	 * 
	 * @param element The element to add to the top of the Stack
	 * @throws IllegalArgumentException If no room is available in the Stack
	 */
	public void push(E element);

	/**
	 * Removes and returns the element at the top of the Stack
	 * 
	 * @return Returns the element at the top of the Stack
	 * @throws EmptyStackException If Stack is empty
	 */
	public E pop();

	/**
	 * Returns true if Stack is empty
	 * 
	 * @return True if Stack is empty
	 */
	public boolean isEmpty();

	/**
	 * Returns number of elements in the Stack
	 * 
	 * @return Number of elements in the Stack
	 */
	public int size();

	/**
	 * Sets the maximum number of elements the Stack can hold
	 * 
	 * @param capacity The capacity to set
	 * @throws IllegalArgumentException If capacity is negative or less than size()
	 */
	public void setCapacity(int capacity);

}
