package edu.ncsu.csc216.pack_scheduler.user;

/**
 * Superclass defines shared state and behavior for Student and Registrar users
 * of PackScheduler system. Includes name, Unity ID, email, and password state
 * with associated getters and setters.
 * 
 * @author Nick Garner
 *
 */
public abstract class User {

	/** User's first name */
	private String firstName;
	/** User's last name */
	private String lastName;
	/** User's Unity ID */
	private String id;
	/** User's email address */
	private String email;
	/** User's password */
	private String password;

	/**
	 * Superclass constructor to create a User object with appropriate first and
	 * last name, id, email, and password for use with Pack Scheduler system.
	 * 
	 * @param firstName First name to set for User
	 * @param lastName  Last name to set for User
	 * @param id        Unity ID to set for User
	 * @param email     Email address to set for User
	 * @param password  Plain text password to set for User
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
	}

	/**
	 * Method to return firstName of User as a String
	 * 
	 * @return Returns User's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Method to set firstName of User
	 * 
	 * @param firstName The firstName to set
	 * @throws IllegalArgumentException When parameter is null or the empty string.
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.equals("")) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Method to return lastName of User as a String
	 * 
	 * @return Returns User's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Method to set lastName of User to given String parameter.
	 * 
	 * @param lastName the lastName to set
	 * @throws IllegalArgumentException When parameter is null or the empty string.
	 */
	public void setLastName(String lastName) {
		if (lastName == null || lastName.equals("")) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Method to return Unity ID of User as a String
	 * 
	 * @return Returns User's Unity ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * Method set's User's Unity ID to given String parameter.
	 * 
	 * @param id the id to set
	 */
	protected void setId(String id) {
		if (id == null || id.equals("")) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * Method to return User's email address as a String
	 * 
	 * @return Returns User's email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Method to set User's email address to given String parameter.
	 * 
	 * @param email The email to set
	 * @throws IllegalArgumentException When parameter is null or the empty string,
	 *                                  if parameter does not contain at least one
	 *                                  "@" sign and one . (dot) sign, or if there
	 *                                  is no dot after the last "@" sign.
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
	 * @return Returns the User's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Method to set User's password
	 * 
	 * @param password the password to set
	 * @throws IllegalArgumentException When parameter is null or the empty string.
	 */
	public void setPassword(String password) {
		if (password == null || password.equals("")) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
	}

	/**
	 * Ensures that User objects with matching field data hash to the same value
	 * 
	 * @return Hash value of method caller
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	/**
	 * Compares given User object to this User to check for equality on all
	 * fields
	 * 
	 * @param obj The User to compare this User to
	 * @return Returns true if the Users are the same for all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
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
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}