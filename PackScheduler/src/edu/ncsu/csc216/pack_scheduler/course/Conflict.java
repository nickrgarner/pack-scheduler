/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Class provides interface for implementing classes to have behavior to check
 * for scheduling conflicts
 * 
 * @author Nick Garner
 *
 */
public interface Conflict {

	/**
	 * Checks the Activity parameter for any day and time conflicts with existing
	 * scheduled Course and Event objects
	 * 
	 * @param possibleConflictingActivity The Activity to check for conflicts
	 * @throws ConflictException When the parameter Activity conflicts with an
	 *                           existing schedule object
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

}
