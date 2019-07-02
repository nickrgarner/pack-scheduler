package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * Class defines state and behavior for Schedule object to maintain an ArrayList
 * of courses. Class features basic add, remove and reset functionality as well
 * as set/get functionality for schedule title.
 * 
 * @author Nick Garner
 *
 */
public class Schedule {

	/** ArrayList of courses to manipulate */
	private ArrayList<Course> schedule;
	/** Title of the schedule object */
	private String title;

	/**
	 * Null constructor creates an empty ArrayList of courses and sets schedule
	 * title to "My Schedule"
	 */
	public Schedule() {
		schedule = new ArrayList<Course>();
		title = "My Schedule";
	}

	/**
	 * Adds course to the end of the schedule if it is not a duplicate or
	 * conflicting
	 * 
	 * @param c The course to add
	 * @return Returns true if course is successfully added
	 * @throws IllegalArgumentException If course is a duplicate or creates a
	 *                                  schedule conflict
	 */
	public boolean addCourseToSchedule(Course c) {
		for (int i = 0; i < schedule.size(); i++) {
			if (c.isDuplicate(schedule.get(i))) {
				throw new IllegalArgumentException("You are already enrolled in " + c.getName());
			} else {
				try {
					c.checkConflict(schedule.get(i));
				} catch (ConflictException e) {
					throw new IllegalArgumentException("The course cannot be added due to a conflict.");
				}
			}
		}
		schedule.add(schedule.size(), c);
		return true;
	}

	/**
	 * Searches the schedule for a Course matching the parameter's name and removes
	 * if found.
	 * 
	 * @param c The course to remove
	 * @return Returns true if course was successfully removed, false otherwise
	 */
	public boolean removeCourseFromSchedule(Course c) {
		for (int i = 0; i < schedule.size(); i++) {
			if (c.getName().equals(schedule.get(i).getName())) {
				schedule.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Resets the schedule to an empty ArrayList with default capacity and title
	 */
	public void resetSchedule() {
		schedule = new ArrayList<Course>();
		setTitle("My Schedule");
	}

	/**
	 * Returns a 2D string array of the schedule with each course's name, section,
	 * title, and meeting information.
	 * 
	 * @return Returns 2D string array with course name, section, title, and meeting
	 *         information for every course in schedule.
	 */
	public String[][] getScheduledCourses() {
		String[][] output = new String[schedule.size()][4];
		for (int i = 0; i < schedule.size(); i++) {
			output[i] = schedule.get(i).getShortDisplayArray();
		}
		return output;
	}

	/**
	 * Sets schedule title to the given String if not null
	 * 
	 * @param title The String to set the schedule title to
	 * @throws IllegalArgumentException If given String is null
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
	}

	/**
	 * Returns the current title of the schedule as a String
	 * 
	 * @return Returns the title of the schedule
	 */
	public String getTitle() {
		return title;
	}
}
