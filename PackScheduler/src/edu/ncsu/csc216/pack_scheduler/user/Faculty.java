package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * Class defines state and behavior for Faculty objects. Faculty are a subclass
 * of Users and have the additional field maxCourses, denoting how many courses
 * the Faculty can teach per term.
 * 
 * @author Nick Garner
 *
 */
public class Faculty extends User {

	/** Schedule of classes for Faculty member */
	private FacultySchedule schedule;
	/** Maximum number of courses the Faculty member can teach */
	private int maxCourses;
	/** Smallest maxCourses number a Faculty can have */
	public static final int MIN_COURSES = 1;
	/** Largets maxCourses number a Faculty can have */
	public static final int MAX_COURSES = 3;

	/**
	 * Constructs a new Faculty object with an empty schedule
	 * 
	 * @param firstName  First name to set
	 * @param lastName   Last name to set
	 * @param id         Unity ID to set
	 * @param email      Email address to set
	 * @param password   Plain text password to set
	 * @param maxCourses Max courses to set
	 */
	public Faculty(String firstName, String lastName, String id, String email, String password, int maxCourses) {
		super(firstName, lastName, id, email, password);
		setMaxCourses(maxCourses);
		schedule = new FacultySchedule(id);
	}

	/**
	 * Sets maxCourses to the given int param
	 * 
	 * @param maxCourses The maximum number of courses to set for maxCourses
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < 1 || maxCourses > 3) {
			throw new IllegalArgumentException("Maximum Courses must be between 1 and 3 inclusive.");
		}
		this.maxCourses = maxCourses;
	}

	/**
	 * Returns the maximum courses this Faculty member can teach
	 * 
	 * @return Int representing the maximum courses this Faculty member can teach
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/**
	 * Returns the FacultySchedule for this Faculty
	 * 
	 * @return FacultySchedule object for this Faculty
	 */
	public FacultySchedule getSchedule() {
		return schedule;
	}

	/**
	 * Returns true if faculty is scheduled for more than their max number of
	 * courses.
	 * 
	 * @return True if FacultySchedule.getNumScheduledCourses() is greater than
	 *         maxCourses
	 */
	public boolean isOverloaded() {
		return schedule.getNumScheduledCourses() > maxCourses;
	}

	/**
	 * Ensures that equal objects hash to the same value
	 * 
	 * @return Hash value as an int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	/**
	 * Returns true if this Faculty object matches the parameter object
	 * 
	 * @param obj The object to compare to this Faculty object
	 * @return True if matches param object
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		if (maxCourses != other.maxCourses)
			return false;
		return true;
	}

	/**
	 * Returns a String representation of this Faculty object with name, Unity ID,
	 * email, password, and maximum number of courses.
	 * 
	 * @return String representation of the Faculty object's field data
	 */
	@Override
	public String toString() {
		return this.getFirstName() + "," + this.getLastName() + "," + this.getId() + "," + this.getEmail() + ","
				+ this.getPassword() + "," + this.getMaxCourses();
	}

}