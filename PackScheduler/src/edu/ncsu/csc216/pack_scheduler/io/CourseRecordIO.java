/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Class controls behavior for reading/writing course info in/out of
 * WolfScheduler
 * 
 * @author Nick Garner
 *
 */
public class CourseRecordIO {

	/**
	 * Reads courses in from text file by line, adds to SortedList storage
	 * 
	 * @param fileName Name of file to read in from
	 * @return Returns SortedList of valid courses
	 * @throws FileNotFoundException Input file is missing
	 */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		SortedList<Course> courses = new SortedList<Course>();
		while (fileReader.hasNextLine()) {
			try {
				Course course = readCourse(fileReader.nextLine());
				boolean duplicate = false;
				for (int i = 0; i < courses.size(); i++) {
					Course c = courses.get(i);
					if (course.getName().equals(c.getName()) && course.getSection().equals(c.getSection())) {
						// it's a duplicate
						duplicate = true;
					}
				}
				if (!duplicate) {
					courses.add(course);
				}
			} catch (IllegalArgumentException e) {
				// skip the line
			}
		}
		fileReader.close();
		return courses;
	}

	/**
	 * Reads line in from input file and passes to Course constructor
	 * 
	 * @param nextLine Line to read from
	 * @return output Course constructed from parsed text input
	 */
	private static Course readCourse(String nextLine) {
		String name;
		String title;
		String section;
		int credits;
		String instructorId;
		int enrollmentCap;
		String meetingDays;
		int startTime;
		int endTime;
		Course output;
		try {
			Scanner lineParse = new Scanner(nextLine);
			lineParse.useDelimiter(",");
			name = lineParse.next();
			title = lineParse.next();
			section = lineParse.next();
			credits = Integer.parseInt(lineParse.next());
			instructorId = lineParse.next();
			enrollmentCap = Integer.parseInt(lineParse.next());
			meetingDays = lineParse.next();
			if (meetingDays.equals("A") && !lineParse.hasNext()) {
				lineParse.close();
				Faculty professor = RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId);
				output = new Course(name, title, section, credits, null, enrollmentCap, meetingDays);
				if (professor == null) {
					return output;
				} else {
					professor.getSchedule().addCourseToSchedule(output);
					return output;
				}
			}
			startTime = Integer.parseInt(lineParse.next());
			endTime = Integer.parseInt(lineParse.next());
			lineParse.close();
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException();
		}
		Faculty professor = RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId);
		output = new Course(name, title, section, credits, null, enrollmentCap, meetingDays, startTime, endTime);
		if (professor == null) {
			return output;
		} else {
			professor.getSchedule().addCourseToSchedule(output);
			return output;
		}
	}
	
	/**
	 * Writes stored SortedList of courses to output file
	 * 
	 * @param fileName File to be written to
	 * @param courses  SortedList of stored courses
	 * @throws IOException Cannot write course records to file
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		for (int i = 0; i < courses.size(); i++) {
			fileWriter.println(courses.get(i).toString());
		}
		fileWriter.close();
	}

}
