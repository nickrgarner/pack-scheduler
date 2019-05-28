package edu.ncsu.csc216.pack_scheduler.user;

public class Student {
	
	/** Student's first name */
	private String firstName;
	/** Student's last name */
	private String lastName;
	/** Student's Unity ID */
	private String id;
	/** Student's email address */
	private String email;
	/** Student's password */
	private String password;
	/** Maximum number of credits the student can enroll in */
	private int maxCredits;
	/** Maximum number of credits any student can enroll in */
	public final int MAX_CREDITS = 18;
	
	/**
	 * Construct a Student object with all fields
	 * @param firstName First name of the student
	 * @param lastName Last name of the student
	 * @param id Student's unity ID
	 * @param email Student's email address
	 * @param password Student's password
	 * @param maxCredits Maximum number of credits student can enroll in
	 */
	public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) throws IllegalArgumentException {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
		setMaxCredits(maxCredits);
	}
	
	/**
	 * Constructs a student object with default maxCredits of 18
	 * @param firstName First name of student
	 * @param lastName Last name of student
	 * @param id Student's unity ID
	 * @param email Student's email address
	 * @param password Student's password
	 */
	public Student(String firstName, String lastName, String id, String email, String password) {
		this(firstName, lastName, id, email, password, 0);
		setMaxCredits(MAX_CREDITS);
	}
	
	/**
	 * Method to return firstName of student
	 * @return Returns student's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Method to set firstName of student
	 * @param firstName The firstName to set
	 */
	public void setFirstName(String firstName) {
		if (firstName.equals(null) || firstName.equals("")) {
			throw new IllegalArgumentException();
		}
		this.firstName = firstName;
	}
	
	/**
	 * Method to return lastName of student
	 * @return Returns student's last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Method to set lastName of student
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		if (lastName.equals(null) || lastName.equals("")) {
			throw new IllegalArgumentException();
		}
		this.lastName = lastName;
	}

	/**
	 * Method to return id of student
	 * @return Returns student's Unity ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	private void setId(String id) {
		if (id.equals(null) || id.equals("")) {
			throw new IllegalArgumentException();
		}
		this.id = id;
	}

	/**
	 * Method to return student's email address
	 * @return Returns student's email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Method to set student's email address
	 * @param email The email to set
	 */
	public void setEmail(String email) {
		if (email.equals(null) || email.equals("")) {
			throw new IllegalArgumentException();
		}
		if (!email.contains("@") || !email.contains(".")) {
			throw new IllegalArgumentException();
		}
		if (email.lastIndexOf(".") < email.lastIndexOf("@")) {
			throw new IllegalArgumentException();
		}
		this.email = email;
	}

	/**
	 * Method to return password
	 * @return Returns the student's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Method to set student's password
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		if (password.equals(null) || password.equals("")) {
			throw new IllegalArgumentException();
		}
		this.password = password;
	}

	/**
	 * Method to return maximum number of credits student can enroll in
	 * @return Returns maxCredits for student
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * @param maxCredits the maxCredits to set
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 3 || maxCredits > 18) {
			throw new IllegalArgumentException();
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * Returns string representation of student object with all fields
	 * @return Returns string representation of student info
	 */
	@Override
	public String toString() {
		return firstName + "," + lastName + "," + id + "," + email + "," + password + "," + maxCredits;
	}
}
