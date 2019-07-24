package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.util.LinkedList;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.User;

/**
 * Class defines methods for read and write of Faculty objects between the
 * FacultyDirectory and a text file.
 * 
 * @author Nick Garner
 *
 */
public class FacultyRecordIO {

	/**
	 * Reads input from txt file and returns LinkedList of processed Faculty, minus
	 * duplicates
	 * 
	 * @param fileName File name to read and process
	 * @return Returns LinkedList of Faculty
	 * @throws FileNotFoundException File cannot be found as named
	 */
	public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		LinkedList<Faculty> output = new LinkedList<Faculty>();
		while (fileReader.hasNextLine()) {
			try {
				Faculty faculty = processFaculty(fileReader.nextLine());
				boolean duplicate = false;
				for (int i = 0; i < output.size(); i++) {
					User s = output.get(i);
					if (faculty.equals(s)) {
						// it's a duplicate
						duplicate = true;
					}
				}
				if (!duplicate) {
					output.add(faculty);
				}
			} catch (IllegalArgumentException e) {
				// skip the line
			}
		}
		fileReader.close();
		return output;
	}

	/**
	 * Writes stored facultyDirectory to given output fileName
	 * 
	 * @param fileName         File to write to
	 * @param facultyDirectory LinkedList of stored faculty records
	 * @throws IOException Cannot write student records to file
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		for (int i = 0; i < facultyDirectory.size(); i++) {
			fileWriter.println(facultyDirectory.get(i).toString());
		}
		fileWriter.close();
	}

	/**
	 * Reads line in from input file and constructs new Faculty object
	 * 
	 * @param line Line to be processed
	 * @return Faculty object with field info from input file
	 */
	private static Faculty processFaculty(String line) {
		String firstName;
		String lastName;
		String id;
		String email;
		String password;
		int maxCourses;
		try {
			Scanner lineParse = new Scanner(line);
			lineParse.useDelimiter(",");
			firstName = lineParse.next();
			lastName = lineParse.next();
			id = lineParse.next();
			email = lineParse.next();
			password = lineParse.next();
			maxCourses = Integer.parseInt(lineParse.next());
			lineParse.close();
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException();
		}
		Faculty output = new Faculty(firstName, lastName, id, email, password, maxCourses);
		return output;
	}
}
