package edu.ncsu.csc216.pack_scheduler.user;

/**
 * Class defines state and behavior for Student objects
 * 
 * @author Nick Garner
 *
 */
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
	public final static int MAX_CREDITS = 18;

	/**
	 * Construct a Student object with all fields
	 * 
	 * @param firstName  First name of the student
	 * @param lastName   Last name of the student
	 * @param id         Student's unity ID
	 * @param email      Student's email address
	 * @param password   Student's password
	 * @param maxCredits Maximum number of credits student can enroll in
	 */
	public Student(String firstName, String lastName, String id, String email, String password, int maxCredits)
			throws IllegalArgumentException {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
		setMaxCredits(maxCredits);
	}

	/**
	 * Constructs a student object with default maxCredits of 18
	 * 
	 * @param firstName First name of student
	 * @param lastName  Last name of student
	 * @param id        Student's unity ID
	 * @param email     Student's email address
	 * @param password  Student's password
	 */
	public Student(String firstName, String lastName, String id, String email, String password) {
		this(firstName, lastName, id, email, password, MAX_CREDITS);
	}

	/**
	 * Method to return firstName of student
	 * 
	 * @return Returns student's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Method to set firstName of student
	 * 
	 * @param firstName The firstName to set
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.equals("")) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Method to return lastName of student
	 * 
	 * @return Returns student's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Method to set lastName of student
	 * 
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		if (lastName == null || lastName.equals("")) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Method to return id of student
	 * 
	 * @return Returns student's Unity ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Method set's student's Unity ID
	 * 
	 * @param id the id to set
	 */
	private void setId(String id) {
		if (id == null || id.equals("")) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * Method to return student's email address
	 * 
	 * @return Returns student's email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Method to set student's email address
	 * 
	 * @param email The email to set
	 */
	public void setEmail(String email) {
		if (email == null || email.equals("")) {
			throw new IllegalArgumentException("Invalid email");
		}
		if (!email.contains("@") || !email.contains(".")) {
			throw new IllegalArgumentException("Invalid email");
		}
		if (email.lastIndexOf(".") < email.lastIndexOf("@")) {
			throw new IllegalArgumentException("Invalid email");
		}
		this.email = email;
	}

	/**
	 * Method to return password
	 * 
	 * @return Returns the student's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Method to set student's password
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		if (password == null || password.equals("")) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
	}

	/**
	 * Method to return maximum number of credits student can enroll in
	 * 
	 * @return Returns maxCredits for student
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the maximum number of credits the student can take
	 * 
	 * @param maxCredits the maxCredits to set
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 3 || maxCredits > 18) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * Ensures that student objects with matching field data hash to the same value
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + maxCredits;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	/**
	 * Compares given student object to this student to check for equality on all
	 * fields
	 * 
	 * @param obj The student to compare this student to
	 * @return Returns true if the students are the same for all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (maxCredits != other.maxCredits)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	/**
	 * Returns string representation of student object with all fields
	 * 
	 * @return Returns string representation of student info
	 */
	@Override
	public String toString() {
		return firstName + "," + lastName + "," + id + "," + email + "," + password + "," + maxCredits;
	}
}
