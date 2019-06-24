package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Custom exception class for use in detecting illegal transitions in the Course
 * name validator FSM.
 * 
 * @author Nick Garner
 *
 */
public class InvalidTransitionException extends Exception {

	/** ID used for serialization */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new InvalidTransitionException object with specified exception
	 * message
	 * 
	 * @param message The message to pass along with the exception object
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}

	/**
	 * Null constructor creates a InvalidTransitionException object with the default
	 * message "Invalid FSM Transition."
	 */
	public InvalidTransitionException() {
		this("Invalid FSM Transition.");
	}
}
