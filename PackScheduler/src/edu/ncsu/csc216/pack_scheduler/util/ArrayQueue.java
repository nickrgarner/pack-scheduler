package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Class defines state and behavior for an ArrayList modeled after a Queue.
 * Standard enqueue and dequeue functionality is provided along with
 * setCapacity.
 * 
 * @author Nick Garner
 *
 * @param <E>
 */
public class ArrayQueue<E> implements Queue<E> {

	/** List to hold Queue elements */
	private ArrayList<E> list;
	/** Max number of elements the Queue can hold */
	private int capacity;

	/**
	 * Constructs a new ArrayQueue with capacity set to param
	 * 
	 * @param capacity The capacity to set
	 */
	public ArrayQueue(int capacity) {
		list = new ArrayList<E>();
		setCapacity(capacity);
	}

	/**
	 * Adds the element to the back of the Queue
	 * 
	 * @param element The element to add
	 * @throws IllegalArgumentException If there is no room to add element
	 */
	@Override
	public void enqueue(E element) {
		if (size() == capacity) {
			throw new IllegalArgumentException("Queue is full.");
		}
		try {
			list.add(size(), element);
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	/**
	 * Removes and returns the element at the front of the Queue
	 * 
	 * @return Returns the element at the front of the Queue
	 * @throws NoSuchElementException If Queue is empty
	 */
	@Override
	public E dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return list.remove(0);
	}

	/**
	 * Returns true if Queue is empty
	 * 
	 * @return True if Queue is empty
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * Returns number of elements in the Queue
	 * 
	 * @return Number of elements in the Queue
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Sets the capacity of the Queue to the given int param
	 * 
	 * @param capacity Capacity to set
	 * @throws IllegalArgumentException If param is negative or less than size()
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException("Capacity is invalid.");
		}
		this.capacity = capacity;
	}
}