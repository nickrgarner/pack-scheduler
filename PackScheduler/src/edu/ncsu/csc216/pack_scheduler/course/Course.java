package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Class defines state and behavior for Course objects to be used in
 * WolfScheduler object. Courses can be created with either set meeting times or
 * as "Arranged". Methods include getters and setters as well as duplicate
 * checks and various String and String array outputs.
 * 
 * @author Nick Garner, nrgarner
 *
 */
public class Course extends Activity implements Comparable<Course> {
	/** Course's name */
	private String name;
	/** Course's section */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course instructor's Unity ID */
	private String instructorId;
	/** CourseNameValidator object to check that course name is valid */
	private CourseNameValidator validator;
	/** Expected number of digits for section number */
	private static final int SECTION_LENGTH = 3;
	/** Max # of chars for course name */
	private static final int MAX_NAME_LENGTH = 6;
	/** Min # of chars for course name */
	private static final int MIN_NAME_LENGTH = 4;
	/** Max # of credit hours for course */
	private static final int MAX_CREDITS = 5;
	/** Min # of credit hours for course */
	private static final int MIN_CREDITS = 1;

	/**
	 * Constructs a course object with values for all fields
	 * 
	 * @param name         name of course
	 * @param title        title of course
	 * @param section      section of course
	 * @param credits      number of credit hours for course
	 * @param instructorId instructor's unity id
	 * @param meetingDays  meeting days for course as series of Chars
	 * @param startTime    start time for course
	 * @param endTime      end time for course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays,
			int startTime, int endTime) throws IllegalArgumentException {
		super(title, meetingDays, startTime, endTime);
		validator = new CourseNameValidator();
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
	}

	/**
	 * Constructs a course object with the given name, title, section, credits,
	 * instructorId, and meetingDays
	 * 
	 * @param name         name of course
	 * @param title        title of course
	 * @param section      section of course
	 * @param credits      number of credit hours for course
	 * @param instructorId instructor's unity id
	 * @param meetingDays  meeting days for course as series of Chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) throws IllegalArgumentException {
		this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}

	/**
	 * Returns the Course's name as a String
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set's the course's name to given name parameter if parameter is appropriate
	 * length and not null.
	 * 
	 * @param name The name to set
	 * @throws IllegalArgumentException If name parameter is null or outside length
	 *                                  requirements set by MIN_NAME_LENGTH and
	 *                                  MAX_NAME_LENGTH
	 */
	private void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Invalid course name");
		}
		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("Invalid course name");
		}
		try {
			validator.isValid(name);
		} catch (InvalidTransitionException e) {
			throw new IllegalArgumentException("Invalid course name");
		}
		this.name = name;
	}

	/**
	 * Return's the Course's section as a String.
	 * 
	 * @return The section as a String.
	 * 
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the Course's section to the given String parameter if parameter is 3
	 * characters in length and not null.
	 * 
	 * @param section The section to set
	 * @throws IllegalArgumentException When parameter is null or the empty string
	 *                                  or not 3 chars in length.
	 */
	public void setSection(String section) {
		if (section == null) {
			throw new IllegalArgumentException("Invalid section number");
		}
		if (section.equals("")) {
			throw new IllegalArgumentException("Invalid section number");
		}
		if (section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section number");
		}
		this.section = section;
	}

	/**
	 * Returns number of credit hours for the course as an int
	 * 
	 * @return number of credit hours as an int value.
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the number of credit hours for the Course from the given int parameter,
	 * as long as the parameter is within the bounds of MIN_CREDITS and MAX_CREDITS.
	 * 
	 * @param credits the number of credit hours to set
	 * @throws IllegalArgumentException If credits parameter is outside bounds of
	 *                                  MIN_CREDITS and MAX_CREDITS.
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credit hours");
		}
		this.credits = credits;
	}

	/**
	 * Returns the course instructor's Unity ID as a String.
	 * 
	 * @return Unity ID of the instructor as a String.
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the Unity ID for the course's instructor from the given String
	 * parameter, as long as the parameter is not null or the empty string.
	 * 
	 * @param instructorId The Unity ID to set for the course instructor
	 * @throws IllegalArgumentException If instructorId parameter is null or the
	 *                                  empty string.
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId == null || instructorId.equals("")) {
			throw new IllegalArgumentException("Invalid instructor unity id");
		}
		this.instructorId = instructorId;
	}

	/**
	 * Compares method caller course to given activity to check if they have the
	 * same name, used in PackScheduler.addCourse
	 * 
	 * @param activity Activity to compare to this course
	 * @return True if activity name matches course name
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if (activity.getClass() != this.getClass()) {
			return false;
		}
		return ((Course) activity).getName().equals(getName());
	}

	/**
	 * Sets meetingDays for Course to given string
	 * 
	 * @param meetingDays The meeting days to set
	 * @throws IllegalArgumentException If meetingDays parameter is null or the
	 *                                  empty string, if meetingDays contains "A"
	 *                                  (Arranged) alongside any other characters,
	 *                                  or if the parameter contains any characters
	 *                                  besides "A" or days of the week.
	 */
	@Override
	public void setMeetingDays(String meetingDays) {
		if (meetingDays == null || meetingDays.equals("")) {
			throw new IllegalArgumentException();
		}
		if (meetingDays.contains("A") && meetingDays.length() != 1) {
			throw new IllegalArgumentException();
		}
		// Checks for invalid meeting day characters
		for (int i = 0; i < meetingDays.length(); i++) {
			String temp = meetingDays.substring(i, i + 1);
			if (!temp.equalsIgnoreCase("M") && !temp.equalsIgnoreCase("T") && !temp.equalsIgnoreCase("W")
					&& !temp.equalsIgnoreCase("H") && !temp.equalsIgnoreCase("F") && !temp.equalsIgnoreCase("A")) {
				throw new IllegalArgumentException();
			}
		}
		super.setMeetingDays(meetingDays);
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if (this.getMeetingDays().equals("A")) {
			return name + "," + this.getTitle() + "," + section + "," + credits + "," + instructorId + ","
					+ this.getMeetingDays();
		}
		return name + "," + this.getTitle() + "," + section + "," + credits + "," + instructorId + ","
				+ this.getMeetingDays() + "," + this.getStartTime() + "," + this.getEndTime();
	}

	/**
	 * Hash code override to ensure equal objects hash to same value
	 * 
	 * @return hashCode for Course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Compares given course object to this course to check for equality on all
	 * fields
	 * 
	 * @param obj the Object to compare
	 * @return true if the objects are the same for all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Returns String array of length 4 with Course name, section, title, and
	 * meeting information.
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] output = { this.getName(), this.getSection(), this.getTitle(), this.getMeetingString() };
		return output;
	}

	/**
	 * Returns String array of length 7 with information for all Course object
	 * fields, plus empty string at last position.
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] output = { this.getName(), this.getSection(), this.getTitle(), String.valueOf(this.getCredits()),
				this.getInstructorId(), this.getMeetingString(), "" };
		return output;
	}

	/**
	 * Compares given Course object to this course object and returns negative int,
	 * positive int, or 0 depending on comparison.
	 * 
	 * @return Returns 0 if Courses match in name and section, negative int if this
	 *         Course is less than given Course for either field, or positive int if this Course is
	 *         greater than given Course for either field.
	 */
	@Override
	public int compareTo(Course comparison) {
		if (this.getName().equals(comparison.getName()) && this.getSection().equals(comparison.getSection())) {
			return 0;
		} else if (this.getName().equals(comparison.getName()) && !this.getSection().equals(comparison.getSection())) {
			return this.getSection().compareTo(comparison.getSection());
		} else {
			return this.getName().compareTo(comparison.getName());
		}
	}
}