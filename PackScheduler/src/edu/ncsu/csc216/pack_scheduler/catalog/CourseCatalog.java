package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;

/**
 * CourseCatalog maintains a SortedList of all Courses that may be scheduled,
 * and provides methods to manipulated list including add, remove, and load from
 * file.
 * 
 * @author Nick Garner
 *
 */
public class CourseCatalog {

	/** SortedList to contain courses for scheduling */
	private SortedList<Course> catalog;

	/**
	 * Constructs an empty CourseCatalog object
	 */
	public CourseCatalog() {
		newCourseCatalog();
	}

	/**
	 * Creates an empty course catalog. All courses in the previous list are lost
	 * unless saved by the user.
	 */
	public void newCourseCatalog() {
		catalog = new SortedList<Course>();
	}

	/**
	 * Constructs the course catalog by reading in course information from given
	 * file. Throws an IllegalArgumentException if the file cannot be found.
	 * 
	 * @param fileName File containing the courses to be loaded
	 * @throws IllegalArgumentException When given file cannot be read
	 */
	public void loadCoursesFromFile(String fileName) {
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}

	}

	/**
	 * Adds a Course manually to the catalog. Returns true if the course was
	 * successfully added and false if the course was unable to be added because it
	 * matched an existing course in name and section.
	 * 
	 * @param name         Name of course to add
	 * @param title        Title of course to add
	 * @param section      Section of course to add
	 * @param credits      Number of credits of course to add
	 * @param instructorId Unity ID of instructor of course to add
	 * @param meetingDays  Days that the course to be added meets
	 * @param startTime    Starting time of the course to be added in 24hr format
	 * @param endTime      Ending time of the course to be added in 24hr format
	 * @return True if successfully added, false otherwise
	 * @throws IllegalArgumentException When course to add cannot be created due to
	 *                                  violated constructor constraints
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId,
			String meetingDays, int startTime, int endTime) throws IllegalArgumentException {
		Course courseAdd;
		if (meetingDays.equals("A")) {
			courseAdd = new Course(name, title, section, credits, instructorId, meetingDays);
		}
		else {
			courseAdd = new Course(name, title, section, credits, instructorId, meetingDays, startTime, endTime);
		}
		for (int i = 0; i < catalog.size(); i++) {
			if (courseAdd.compareTo(catalog.get(i)) == 0) {
				return false;
			}
		}
		return catalog.add(courseAdd);
	}

	/**
	 * Removes course with matching name and section from catalog. Returns true if
	 * removed, false otherwise.
	 * 
	 * @param name    Name of course to remove
	 * @param section Section of course to remove
	 * @return True if removed, false otherwise
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			Course c = catalog.get(i);
			if (c.getName().equals(name) && c.getSection().equals(section)) {
				catalog.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Searches catalog for course with matching name and section and returns that
	 * course object.
	 * 
	 * @param name    Course name to search
	 * @param section Course section to search
	 * @return Returns course if found, otherwise returns null
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				return catalog.get(i);
			}
		}
		return null;
	}

	/**
	 * Returns all courses in the catalog as a 2D string array with columns for
	 * name, section, title, and meeting information.
	 * 
	 * @return 2D String array containing name, section, title, and meeting
	 *         information for all course in catalog.
	 */
	public String[][] getCourseCatalog() {
		String[][] output = new String[catalog.size()][4];
		for (int i = 0; i < catalog.size(); i++) {
			Course c = catalog.get(i);
			output[i][0] = c.getName();
			output[i][1] = c.getSection();
			output[i][2] = c.getTitle();
			output[i][3] = c.getMeetingString();
		}
		return output;
	}

	/**
	 * Saves courses in catalog to a file.
	 * 
	 * @param fileName Name of file to save course catalog to
	 * @throws IllegalArgumentException If file cannot be written to
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}

}