package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Superclass outlines shared state and behavior for Course and Event objects.
 * Methods provide getters/setters for shared Title, MeetingDays, and Time
 * fields for Courses and Events, String output, and shared abstract methods for
 * Course/Event object info output in array form.
 * 
 * @author Nick Garner
 *
 */
public abstract class Activity implements Conflict {

	/** Course's title */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;
	/** Max time of day */
	private static final int UPPER_TIME = 2400;
	/** Upper bound for minutes in hour */
	private static final int UPPER_HOUR = 60;
	/** Conversion factor for military time to 12h time */
	private static final int TIME_CONVERSION = 1200;

	/**
	 * Constructs an Activity object the given title, meeting days, and start/end
	 * times.
	 * 
	 * @param title       Title of the activity
	 * @param meetingDays Days of the week that the activity meets
	 * @param startTime   Start time for the activity
	 * @param endTime     End time for the activity
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) throws IllegalArgumentException {
		setTitle(title);
		setMeetingDays(meetingDays);
		setActivityTime(startTime, endTime);
	}

	/**
	 * Returns the activity's title
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set's the activity's title
	 * 
	 * @param title the title to set
	 * @throws IllegalArgumentException When title parameter is null or the empty
	 *                                  string.
	 */
	public void setTitle(String title) {
		if (title == null || title.equals("")) {
			throw new IllegalArgumentException("Invalid course title");
		}
		this.title = title;
	}

	/**
	 * Returns the days of the week that the activity meets
	 * 
	 * @return Days that the activity meets
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Sets the days of the week that the activity meets
	 * 
	 * @param meetingDays the days of the week to set for the activity schedule
	 * @throws IllegalArgumentException If parameter is empty, contains invalid
	 *                                  days, or contains a combination of days and
	 *                                  Arranged
	 */
	public void setMeetingDays(String meetingDays) {
		if (meetingDays == null || meetingDays.equalsIgnoreCase("")) {
			throw new IllegalArgumentException("Invalid meeting days");
		}
		if (!meetingDays.contains("M") && !meetingDays.contains("T") && !meetingDays.contains("W")
				&& !meetingDays.contains("H") && !meetingDays.contains("F") && !meetingDays.contains("A")) {
			throw new IllegalArgumentException("Invalid meeting days");
		}
		if (meetingDays.contains("A") && meetingDays.length() > 1) {
			throw new IllegalArgumentException("Invalid meeting days");
		}
		this.meetingDays = meetingDays;
	}

	/**
	 * Returns the daily start time for the activity
	 * 
	 * @return Activity start time of day
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the daily end time for the activity
	 * 
	 * @return activity end time of day
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Sets start and end times for the activity's scheduled meeting days
	 * 
	 * @param startTime The daily start time for the activity
	 * @param endTime   The daily end time for the activity
	 * @throws IllegalArgumentException When either parameter is less than 0 or
	 *                                  greater than UPPER_TIME; When endTime is
	 *                                  before startTime; When either parameter's
	 *                                  minutes are greater than 59; When either
	 *                                  parameter is non-zero for an Arranged ("A")
	 *                                  class
	 */
	public void setActivityTime(int startTime, int endTime) {
		if (startTime < 0 || startTime >= UPPER_TIME) {
			throw new IllegalArgumentException("Invalid start time");
		}
		if (endTime < 0 || endTime >= UPPER_TIME) {
			throw new IllegalArgumentException("Invalid end time");
		}
		if (endTime < startTime) {
			throw new IllegalArgumentException("Invalid course times");
		}
		if (meetingDays.equalsIgnoreCase("A") && (startTime != 0 || endTime != 0)) {
			throw new IllegalArgumentException("Invalid course times");
		}
		if ((startTime % 100) >= UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid start time");
		}
		if ((endTime % 100) >= UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid end time");
		}
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Returns String representation of meeting schedule with time in standard 12h
	 * format
	 * 
	 * @return Returns "Arranged" for "A" sections, otherwise returns meeting days
	 *         and time range for activity.
	 */
	public String getMeetingString() {
		if (meetingDays.equalsIgnoreCase("A")) {
			return "Arranged";
		}
		String startTimeAMString = Integer.toString(startTime);
		String endTimeAMString = Integer.toString(endTime);
		String startTimePMString = Integer.toString(startTime - TIME_CONVERSION);
		String endTimePMString = Integer.toString(endTime - TIME_CONVERSION);
		String startTimeStd = "";
		String endTimeStd = "";
		if (startTime < 1200) {
			startTimeStd = startTimeAMString.substring(0, startTimeAMString.length() - 2) + ":"
					+ startTimeAMString.substring(startTimeAMString.length() - 2) + "AM";
		} else if (startTime < 1300) {
			startTimeStd = 12 + ":" + startTimeAMString.substring(2) + "PM";
		} else {
			startTimeStd = startTimePMString.substring(0, startTimePMString.length() - 2) + ":"
					+ startTimePMString.substring(startTimePMString.length() - 2) + "PM";
		}
		if (endTime < 1200) {
			endTimeStd = endTimeAMString.substring(0, endTimeAMString.length() - 2) + ":"
					+ endTimeAMString.substring(endTimeAMString.length() - 2) + "AM";
		} else if (endTime < 1300) {
			endTimeStd = 12 + ":" + endTimeAMString.substring(2) + "PM";
		} else {
			endTimeStd = endTimePMString.substring(0, endTimePMString.length() - 2) + ":"
					+ endTimePMString.substring(endTimePMString.length() - 2) + "PM";
		}
		return meetingDays + " " + startTimeStd + "-" + endTimeStd;
	}

	/**
	 * Super method to check if this object is a duplicate of the parameter
	 * activity, implemented in Course and Event classes
	 * 
	 * @param activity Activity to check for duplicate
	 * @return True if activity is a duplicate of existing schedule item
	 */
	public abstract boolean isDuplicate(Activity activity);

	/**
	 * Super method to display abbreviated Course/Event information in a String
	 * array. Implemented in Course and Event.
	 * 
	 * @return String array of course/event information.
	 */
	public abstract String[] getShortDisplayArray();

	/**
	 * Super method to display full Course/Event information in a String array.
	 * Implemented in Course and Event.
	 * 
	 * @return String array of course/event information.
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * Hash code override to ensure equal objects hash to same value
	 * 
	 * @return hashCode for Activity
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/**
	 * Compares day and time meeting information for method caller to parameter
	 * Activity and throws ConflictException if a conflict is found
	 * 
	 * @param possibleConflictingActivity The Activity object to compare the method
	 *                                    caller's meeting information to.
	 * @throws ConflictException If a day and time conflict is detected.
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		String days = possibleConflictingActivity.getMeetingDays();
		int thisStart = this.getStartTime();
		int thisEnd = this.getEndTime();
		int conStart = possibleConflictingActivity.getStartTime();
		int conEnd = possibleConflictingActivity.getEndTime();
		boolean timeConflict = thisStart >= conStart && thisStart <= conEnd || thisEnd >= conStart && thisEnd <= conEnd
				|| thisStart <= conStart && thisEnd >= conEnd;
		// Loop through meetingDays string for possibleConflictingActivity
		for (int i = 0; i < days.length(); i++) {
			/*
			 * Check if this.meetingDays contains the current
			 * possibleConflictingActivity.meetingDays substring
			 */
			boolean dayConflict = this.meetingDays.contains(days.substring(i, i + 1));
			if (dayConflict && timeConflict) {
				throw new ConflictException();
			}
		}
	}
}