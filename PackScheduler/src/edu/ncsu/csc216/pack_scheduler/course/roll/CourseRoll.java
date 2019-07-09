package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;

/**
 * Class defines state and behavior for CourseRoll objects that maintain a
 * LinkedAbstractList of Students that are enrolled in a Course. Class has
 * functionality for enroll, drop, and open seat checks.
 * 
 * @author Nick Garner
 *
 */
public class CourseRoll {

	private LinkedAbstractList<Student> roll;
	private int enrollmentCap;
	private static final int MIN_ENROLLMENT = 10;
	private static final int MAX_ENROLLMENT = 250;

	/**
	 * Creates a CourseRoll object with the specified enrollmentCap
	 * 
	 * @param enrollmentCap The maximum number of students that can be added to the
	 *                      CourseRoll
	 * @throws IllegalArgumentException If enrollmentCap is less than 0.
	 */
	public CourseRoll(int enrollmentCap) throws IllegalArgumentException {
		roll = new LinkedAbstractList<Student>(enrollmentCap);
		setEnrollmentCap(enrollmentCap);
	}

	/**
	 * Returns the current enrollmentCap of the CourseRoll as an int value
	 * 
	 * @return Returns the current enrollmentCap of the CourseRoll as an int value
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	/**
	 * Sets the enrollmentCap to the provided int parameter.
	 * 
	 * @param enrollmentCap The maximum number of Students that can be added to the
	 *                      CourseRoll.
	 * @throws IllegalArgumentException If enrollmentCap is outside the bounds of
	 *                                  the min and max allowed enrollment or is
	 *                                  lower than the roll's current size.
	 */
	public void setEnrollmentCap(int enrollmentCap) {
		if (enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException("Enrollment cap is not valid.");
		}
		if (roll != null && enrollmentCap < roll.size()) {
			throw new IllegalArgumentException("Enrollment cap is not valid.");
		}
		if (roll != null) {
			this.enrollmentCap = enrollmentCap;
			roll.setCapacity(enrollmentCap);
		}
	}

	/**
	 * Adds the given Student object to the CourseRoll.
	 * 
	 * @param s The Student object to add to the CourseRoll.
	 * @throws IllegalArgumentException If an exception is thrown from
	 *                                  LinkedAbstractList.add(int,E).
	 */
	public void enroll(Student s) {
		try {
			roll.add(roll.size(), s);
		} catch (Exception e) {
			throw new IllegalArgumentException("Student could not be enrolled.");
		}
	}

	/**
	 * Searches the roll for a Student matching s and removes that Student from the
	 * Course roll.
	 * 
	 * @param s The Student to remove.
	 * @throws IllegalArgumentException If s is null or if
	 *                                  LinkedAbstractList.remove(int) throws an
	 *                                  exception.
	 */
	public void drop(Student s) {
		if (s == null) {
			throw new IllegalArgumentException("Student cannot be null.");
		}
		int index = 0;
		while (index < roll.size() && !s.equals(roll.get(index))) {
			index++;
		}
		if (index < roll.size()) {
			if (s.equals(roll.get(index))) {
				roll.remove(index);
			}
		}
	}

	/**
	 * Returns the number of remaining open seats in the course.
	 * 
	 * @return Returns the enrollmentCap minus the number of students already
	 *         enrolled.
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}

	/**
	 * Checks that the CourseRoll has room remaining and that the Student to enroll
	 * is not a duplicate.
	 * 
	 * @param s The student to check for enrollment eligibility.
	 * @return Returns true if CourseRoll has room and Student is not a duplicate.
	 */
	public boolean canEnroll(Student s) {
		// boolean dupeCheck = false;
		int index = 0;
		while (index < roll.size() && !s.equals(roll.get(index))) {
			index++;
		}
		return (!(index != roll.size() || roll.size() == getEnrollmentCap()));
	}
}
