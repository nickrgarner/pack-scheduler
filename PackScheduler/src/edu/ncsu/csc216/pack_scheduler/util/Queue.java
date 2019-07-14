package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Interface defines shared behavior for ArrayQueue and LinkedQueue classes,
 * including enqueue and dequeue and isEmpty check.
 * 
 * @author Nick Garner
 *
 * @param <E> Generic parameter
 */
public interface Queue<E> {

	/**
	 * Adds the element to the back of the Queue
	 * 
	 * @param element The element to add
	 * @throws IllegalArgumentException If there is no room to add element
	 */
	void enqueue(E element);

	/**
	 * Removes and returns the element at the front of the Queue
	 * 
	 * @return Returns the element at the front of the Queue
	 * @throws NoSuchElementException If Queue is empty
	 */
	E dequeue();

	/**
	 * Returns true if Queue is empty
	 * 
	 * @return True if Queue is empty
	 */
	boolean isEmpty();

	/**
	 * Returns number of elements in the Queue
	 * 
	 * @return Number of elements in the Queue
	 */
	int size();

	/**
	 * Sets the capacity of the Queue to the given int param
	 * 
	 * @param capacity Capacity to set
	 * @throws IllegalArgumentException If param is negative or less than size()
	 */
	void setCapacity(int capacity);
}
