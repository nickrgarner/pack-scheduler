package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Custom exception class for use in detecting scheduling conflicts between
 * Activities' meeting information.
 * 
 * @author Nick Garner
 *
 */
public class ConflictException extends Exception {

	/** ID used for serialization */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new ConflictException object with specified exception message
	 * 
	 * @param message The message to pass along with the exception object
	 */
	public ConflictException(String message) {
		super(message);
	}

	/**
	 * Null constructor creates a ConflictException object with the default message
	 * "Schedule conflict."
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}
}