package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Class defines state and behavior for Student users, implements Comparable
 * interface. Student objects consist of relevant registration information like
 * name, id, email, password, etc. Functionality includes getters/setters,
 * equals and compareTo methods, and object String representation.
 * 
 * @author Nick Garner
 *
 */
public class Student extends User implements Comparable<Student> {

	/** Maximum number of credits the student can enroll in */
	private int maxCredits;
	/** Student's course schedule */
	private Schedule schedule;
	/** Maximum number of credits any student can enroll in */
	public final static int MAX_CREDITS = 18;

	/**
	 * Construct a Student object with all fields
	 * 
	 * @param firstName  First name of the student
	 * @param lastName   Last name of the student
	 * @param id         Student's unity ID
	 * @param email      Student's email address
	 * @param password   Student's password
	 * @param maxCredits Maximum number of credits student can enroll in
	 * @throws IllegalArgumentException When parameters violate requirements
	 *                                  detailed in setter methods.
	 */
	public Student(String firstName, String lastName, String id, String email, String password, int maxCredits)
			throws IllegalArgumentException {
		super(firstName, lastName, id, email, password);
		setMaxCredits(maxCredits);
		schedule = new Schedule();
	}

	/**
	 * Constructs a Student object with default maxCredits of 18
	 * 
	 * @param firstName First name of student
	 * @param lastName  Last name of student
	 * @param id        Student's unity ID
	 * @param email     Student's email address
	 * @param password  Student's password
	 */
	public Student(String firstName, String lastName, String id, String email, String password)
			throws IllegalArgumentException {
		this(firstName, lastName, id, email, password, MAX_CREDITS);
	}

	/**
	 * Method to return maximum number of credits this Student can enroll in as an
	 * int
	 * 
	 * @return Returns maxCredits for student
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the maximum number of credits the student can take
	 * 
	 * @param maxCredits the maxCredits to set
	 * @throws IllegalArgumentException When the maxCredits parameter is less than 3
	 *                                  or greater than 18
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 3 || maxCredits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * Ensures that student objects with matching field data hash to the same value
	 * 
	 * @return Hash value of method caller
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}

	/**
	 * Compares given student object to this student to check for equality on all
	 * fields
	 * 
	 * @param obj The student to compare this student to
	 * @return Returns true if the students are the same for all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (maxCredits != other.maxCredits)
			return false;
		return true;
	}

	/**
	 * Returns string representation of student object with all fields
	 * 
	 * @return Returns string representation of student info
	 */
	@Override
	public String toString() {
		return this.getFirstName() + "," + this.getLastName() + "," + this.getId() + "," + this.getEmail() + ","
				+ this.getPassword() + "," + this.getMaxCredits();
	}

	/**
	 * Returns -1, 0, or 1 if this object comes before, matches, or comes after the
	 * comparison parameter. Comparison is based on Student's lastName, firstName,
	 * and id fields, in that order.
	 * 
	 * @param comparison The Student to compare to the method caller based on
	 *                   lastName, firstName, and id.
	 */
	@Override
	public int compareTo(Student comparison) {
		int lastNameCompare = this.getLastName().compareTo(comparison.getLastName());
		int firstNameCompare = this.getFirstName().compareTo(comparison.getFirstName());
		int idCompare = this.getId().compareTo(comparison.getId());

		if (lastNameCompare != 0) {
			if (lastNameCompare < 0) {
				return -1;
			} else if (lastNameCompare > 0) {
				return 1;
			}
		} else if (firstNameCompare != 0) {
			if (firstNameCompare < 0) {
				return -1;
			} else if (firstNameCompare > 0) {
				return 1;
			}
		} else {
			if (idCompare < 0) {
				return -1;
			} else if (idCompare > 0) {
				return 1;
			} else {
				return 0;
			}
		}
		return 0;
//		
//		if (this.getLastName().compareTo(comparison.getLastName()) != 0) {
//			return this.getLastName().compareTo(comparison.getLastName());
//		}
//		else if (this.getFirstName().compareTo(comparison.getFirstName()) != 0) {
//			return this.getFirstName().compareTo(comparison.getFirstName());
//		}
//		else {
//			return this.getId().compareTo(comparison.getId());
//		}
//		for (int i = 0; i < this.getLastName().length() && i < comparison.getLastName().length(); i++) {
//			if (Integer.valueOf(this.getLastName().substring(i, i + 1)) > Integer.valueOf(comparison.getLastName().substring(i, i + 1))) {
//				return 1;
//			}
//			else if (Integer.valueOf(this.getLastName().substring(i, i + 1)) < Integer.valueOf(comparison.getLastName().substring(i, i + 1))) {
//				return -1;
//			}
//			if (i == this.getLastName().length() - 1 || i == comparison.getLastName().length() - 1) {
//				if (this.getLastName().length() > comparison.getLastName().length()) {
//					return 1;
//				}
//				else if (this.getLastName().length() < comparison.getLastName().length()) {
//					return -1;
//				}
//			}
//		}
	}

	/**
	 * Returns the schedule object owned by this Student
	 * 
	 * @return Returns the Student's schedule object
	 */
	public Schedule getSchedule() {
		return this.schedule;
	}

	/**
	 * Checks if Course will exceed Student's max credits or violates scheduling
	 * rules and returns false if so.
	 * 
	 * @param c Course to check for eligibility.
	 * @return Returns true if Course will not exceed Student's max credits if added
	 *         and does not violate Schedule rules.
	 */
	public boolean canAdd(Course c) {
		if ((schedule.getScheduleCredits() + c.getCredits()) > maxCredits) {
			return false;
		}
		return schedule.canAdd(c);
	}
}
