package edu.ncsu.csc216.pack_scheduler.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.io.File;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Class controls behavior for reading/writing student info in/out of database
 * 
 * @author Nick Garner
 *
 */
public class StudentRecordIO {

	/**
	 * Reads input from txt file and returns ArrayList of processed students, minus
	 * duplicates
	 * 
	 * @param fileName File name to read and process
	 * @return Returns SortedList of students
	 * @throws FileNotFoundException File cannot be found as named
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		SortedList<Student> output = new SortedList<Student>();
		while (fileReader.hasNextLine()) {
			try {
				Student student = processStudent(fileReader.nextLine());
				boolean duplicate = false;
				for (int i = 0; i < output.size(); i++) {
					Student s = output.get(i);
					if (student.equals(s)) {
						// it's a duplicate
						duplicate = true;
					}
				}
				if (!duplicate) {
					output.add(student);
				}
			} catch (IllegalArgumentException e) {
				// skip the line
			}
		}
		fileReader.close();
		return output;
	}

	/**
	 * Writes stored studentDirectory ArrayList of Students to given output fileName
	 * 
	 * @param fileName         File to write to
	 * @param studentDirectory ArrayList of stored student records
	 * @throws IOException Cannot write student records to file
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		for (int i = 0; i < studentDirectory.size(); i++) {
			fileWriter.println(studentDirectory.get(i).toString());
		}
		fileWriter.close();
	}

	/**
	 * Reads line in from input file and constructs new student object
	 * 
	 * @param line Line to be processed
	 * @return Student object with field info from input file
	 */
	private static Student processStudent(String line) {
		String firstName;
		String lastName;
		String id;
		String email;
		String password;
		int maxCredits;
		try {
			Scanner lineParse = new Scanner(line);
			lineParse.useDelimiter(",");
			firstName = lineParse.next();
			lastName = lineParse.next();
			id = lineParse.next();
			email = lineParse.next();
			password = lineParse.next();
			maxCredits = Integer.parseInt(lineParse.next());
			lineParse.close();
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException();
		}
		Student output = new Student(firstName, lastName, id, email, password, maxCredits);
		return output;
	}
}
