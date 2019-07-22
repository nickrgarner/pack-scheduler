package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Class maintains a directory of Faculty records with unique IDs. To be used in
 * conjunction with RegistrationManager.
 * 
 * @author Nick Garner
 *
 */
public class FacultyDirectory {

	/** List of Faculty in the directory */
	private LinkedList<Faculty> facultyDirectory;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Creates an empty faculty directory
	 */
	public FacultyDirectory() {
		newFacultyDirectory();
	}

	/**
	 * Creates a new faculty directory linked list
	 */
	public void newFacultyDirectory() {
		facultyDirectory = new LinkedList<Faculty>();
	}

	/**
	 * Constructs the faculty directory by reading in data from a given input file.
	 * 
	 * @param fileName The file to load into the directory.
	 * @throws IllegalArgumentException If file cannot be read.
	 */
	public void loadFacultyFromFile(String fileName) {
		try {
			facultyDirectory = FacultyRecordIO.readFacultyRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}

	/**
	 * Adds a new Faculty member to the faculty directory. Returns true if
	 * successfully added, false if the faculty member has a duplicate ID as another
	 * faculty already in the directory.
	 * 
	 * @param firstName      Faculty's first name
	 * @param lastName       Faculty's last name
	 * @param id             Faculty's Unity ID
	 * @param email          Faculty's email address
	 * @param password       Faculty's password
	 * @param repeatPassword Faculty's repeat password
	 * @param maxCourses     Faculty's max courses
	 * @return True if added
	 * @throws IllegalArgumentException When password cannot be hashed or if the two
	 *                                  passwords do not match.
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, String password,
			String repeatPassword, int maxCourses) {
		String hashPW = "";
		String repeatHashPW = "";
		if (password == null || repeatPassword == null || password.equals("") || repeatPassword.equals("")) {
			throw new IllegalArgumentException("Invalid password");
		}
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(password.getBytes());
			hashPW = new String(digest1.digest());

			MessageDigest digest2 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest2.update(repeatPassword.getBytes());
			repeatHashPW = new String(digest2.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}

		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}

		Faculty faculty = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);

		for (int i = 0; i < facultyDirectory.size(); i++) {
			User f = facultyDirectory.get(i);
			if (f.getId().equals(faculty.getId())) {
				return false;
			}
		}
		return facultyDirectory.add(faculty);
	}

	/**
	 * Removes the faculty with the given ID from the directory. Returns true if
	 * removed, false if the faculty is not in the list.
	 * 
	 * @param facultyId The ID of the faculty member to remove.
	 * @return True if removed, false if not in list.
	 */
	public boolean removeFaculty(String facultyId) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			User f = facultyDirectory.get(i);
			if (f.getId().equals(facultyId)) {
				facultyDirectory.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a 2D string array with all Faculty in the directory and columns for
	 * first name, last name, and ID.
	 * 
	 * @return 2D string array with faculty's first name, last name, and ID.
	 */
	public String[][] getFacultyDirectory() {
		String[][] output = new String[facultyDirectory.size()][3];
		for (int i = 0; i < facultyDirectory.size(); i++) {
			User f = facultyDirectory.get(i);
			output[i][0] = f.getFirstName();
			output[i][1] = f.getLastName();
			output[i][2] = f.getId();
		}
		return output;
	}

	/**
	 * Saves all faculty in the directory to a file.
	 * 
	 * @param fileName Name of the file to save to.
	 * @throws IllegalArgumentException If file cannot be written to.
	 */
	public void saveFacultyDirectory(String fileName) {
		try {
			FacultyRecordIO.writeFacultyRecords(fileName, facultyDirectory);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}

	/**
	 * Searches the directory sequentially for the given ID and returns the matching
	 * record.
	 * 
	 * @param id The Unity ID to search the directory for.
	 * @return Returns the Faculty object if a match is found, else returns null
	 */
	public Faculty getFacultyById(String id) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			if (facultyDirectory.get(i).getId().equalsIgnoreCase(id)) {
				return facultyDirectory.get(i);
			}
		}
		return null;
	}
}