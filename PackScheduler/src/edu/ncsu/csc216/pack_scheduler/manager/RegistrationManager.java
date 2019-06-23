package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;

/**
 * Class defines Singleton model and behavior for RegistrationManager object.
 * This class controls login/out logic as well as some basic getters for the
 * CourseCatalog and StudentDirectory sorted lists. Inner class defines
 * constructor for Registrar objects.
 * 
 * @author Nick Garner
 *
 */
public class RegistrationManager {

	/** Singleton instance of class */
	private static RegistrationManager instance;
	/** Catalog of courses in PackScheduler */
	private CourseCatalog courseCatalog;
	/** Directory of students in PackScheduler */
	private StudentDirectory studentDirectory;
	/** Holds credentials of registrar login */
	private User registrar;
	/** The user currently logged into the system */
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/** Properties file for registrar fields */
	private static final String PROP_FILE = "registrar.properties";

	/**
	 * Creates a new RegistrationManager object
	 */
	private RegistrationManager() {
		createRegistrar();
		courseCatalog = new CourseCatalog();
		studentDirectory = new StudentDirectory();
	}

	/**
	 * Creates a new Registrar user based on field data from registrar.properties
	 * file
	 */
	private void createRegistrar() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			String hashPW = hashPW(prop.getProperty("pw"));

			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"),
					prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}

	/**
	 * Helper method to hash passwords for use in comparisons for login.
	 * @param pw The plain text password to hash
	 * @return Returns password hashed with SHA-256
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return new String(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * Checks to see if single instance of RegistrationManager has been instantiated
	 * and then returns that instance
	 * 
	 * @return Returns the static single instance of RegistrationManager
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

	/**
	 * Returns the courseCatalog Sorted List.
	 * 
	 * @return Returns the courseCatalog object.
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * Returns the studentDirectory Sorted List.
	 * 
	 * @return Returns the studentDirectory object.
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}

	/**
	 * Checks that the given password matches the stored student password, or if the
	 * id is a Registrar id, that the given password matches the Registrar password.
	 * 
	 * @param id       The id to compare to records
	 * @param password The password to compare to records
	 * @return Returns true if login is successful, false otherwise.
	 */
	public boolean login(String id, String password) {
		Student s = studentDirectory.getStudentById(id);
		if (!(s == null)) {
			try {
				MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				String localHashPW = new String(digest.digest());
				if (s.getPassword().equals(localHashPW)) {
					currentUser = s;
					return true;
				}
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException();
			}
		}

		if (registrar.getId().equals(id)) {
			MessageDigest digest;
			try {
				digest = MessageDigest.getInstance(HASH_ALGORITHM);
				digest.update(password.getBytes());
				String localHashPW = new String(digest.digest());
				if (registrar.getPassword().equals(localHashPW)) {
					currentUser = registrar;
					return true;
				}
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalArgumentException();
			}
		}

		return false;
	}

	/**
	 * Ends the session of a Student logged in and returns the system to the
	 * Registrar control.
	 */
	public void logout() {
		currentUser = registrar;
	}

	/**
	 * Returns the User object that is currently logged into the system. This may be
	 * a Student or Registrar.
	 * 
	 * @return Returns the User that is currently logged into the system.
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Resets both courseCatalog and studentDirectory to empty Sorted Lists and
	 * removes all data from them.
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
	}

	/**
	 * Defines constructor for Registrar User, differentiated from Student User.
	 * Registrars have a different GUI view and privileges to manipulate the course
	 * catalog.
	 * 
	 * @author Nick Garner
	 *
	 */
	private static class Registrar extends User {
		/**
		 * Create a registrar user with the user id and password in the
		 * registrar.properties file.
		 * 
		 * @param firstName First name to set for Registrar
		 * @param lastName  Last name to set for Registrar
		 * @param id        Unity ID to set for Registrar
		 * @param email     Email address to set for Registrar
		 * @param password  Plain text password to set for Registrar
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}
}