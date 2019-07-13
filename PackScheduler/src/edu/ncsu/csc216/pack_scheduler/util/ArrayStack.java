package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Class defines state and behavior for an Array List object modeled after a
 * Stack. Standard push and pop functionality is provided along with
 * setCapacity.
 * 
 * @author Nick Garner
 *
 * @param <E>
 */
public class ArrayStack<E> implements Stack<E> {

	/** ArrayList object to be manipulated */
	private ArrayList<E> list;
	/** Max number of elements the stack can hold */
	private int capacity;

	/**
	 * Constructs a new ArrayStack object based on the given capacity
	 * 
	 * @param capacity The capacity to set for the ArrayStack
	 */
	public ArrayStack(int capacity) {
		list = new ArrayList<E>();
		setCapacity(capacity);
	}

	/**
	 * Adds the element to the top of the Stack
	 * 
	 * @param element The element to add to the top of the Stack
	 * @throws IllegalArgumentException If no room is available in the Stack
	 */
	@Override
	public void push(E element) {
		if (size() == capacity) {
			throw new IllegalArgumentException("Stack is full.");
		}
		try {
			list.add(0, element);
		} catch (IllegalArgumentException e) {
			throw e;
		}
	}

	/**
	 * Removes and returns the element at the top of the Stack
	 * 
	 * @return Returns the element at the top of the Stack
	 * @throws EmptyStackException If Stack is empty
	 */
	@Override
	public E pop() {
		if (size() == 0) {
			throw new EmptyStackException();
		} else {
			return list.remove(0);
		}
	}

	/**
	 * Returns true if Stack is empty
	 * 
	 * @return True if Stack is empty
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns number of elements in the Stack
	 * 
	 * @return Number of elements in the Stack
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Sets the maximum number of elements the Stack can hold
	 * 
	 * @param capacity The capacity to set
	 * @throws IllegalArgumentException If capacity is negative or less than size()
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException("Capacity is invalid.");
		}
		this.capacity = capacity;

	}

}
