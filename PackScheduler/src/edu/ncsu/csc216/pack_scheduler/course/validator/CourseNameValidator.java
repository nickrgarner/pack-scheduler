package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Class defines State pattern implementation for CourseNameValidator function.
 * Four inner classes serve as possible states when parsing a Course name, with
 * isValid method serving as the main validation check.
 * 
 * @author Nick Garner
 *
 */
public class CourseNameValidator {

	private boolean validEndState;
	private int letterCount;
	private int digitCount;
	private State currentState;
	private State stateInitial;
	private State stateLetter;
	private State stateNumber;
	private State stateSuffix;

	/**
	 * Constructs a new CourseNameValidator object with reset letter and digit
	 * counts, and instantiates possible course name states.
	 */
	public CourseNameValidator() {
		letterCount = 0;
		digitCount = 0;
		validEndState = false;
		// Instantiate states
		stateInitial = new InitialState();
		stateLetter = new LetterState();
		stateNumber = new NumberState();
		stateSuffix = new SuffixState();
		// Set current state to Initial
		currentState = stateInitial;
	}

	/**
	 * Checks the given String parameter to make sure it is a valid course name.
	 * 
	 * @param courseName The String to check for validity.
	 * @return Returns true if String is a valid name, false otherwise.
	 * @throws InvalidTransitionException If any character in the course name
	 *                                    initiates a state transfer that is not
	 *                                    allowed.
	 */
	public boolean isValid(String courseName) throws InvalidTransitionException {
		validEndState = false;
		letterCount = 0;
		digitCount = 0;
		for (int i = 0; i < courseName.length(); i++) {
			char currentChar = courseName.charAt(i);
			if (Character.isLetter(currentChar)) {
				currentState.onLetter();
			} else if (Character.isDigit(currentChar)) {
				currentState.onDigit();
			} else {
				currentState.onOther();
			}
//			if (!Character.isLetterOrDigit(currentChar)) {
//				currentState.onOther();
//			} else if (Character.isLetter(currentChar)) {
//				currentState.onLetter();
//			} else if (Character.isDigit(currentChar)) {
//				currentState.onDigit();
//			}
		}
		if (currentState != stateLetter && digitCount == 3) {
			validEndState = true;
		}

		currentState = stateInitial;

		return validEndState;
	}

	/**
	 * Superclass for the concrete states. Defines behavior when Course name char is
	 * not a letter or digit.
	 * 
	 * @author Nick Garner
	 *
	 */
	private abstract class State {
		/**
		 * Null constructor
		 */
		public State() {
			// Null
		}

		/**
		 * Abstract method for parsing a letter in Course name
		 * 
		 * @throws InvalidTransitionException If letter initiates a state transition
		 *                                    that is not allowed.
		 */
		public abstract void onLetter() throws InvalidTransitionException;

		/**
		 * Abstract method for parsing a number in Course name
		 * 
		 * @throws InvalidTransitionException If number initiates a state transition
		 *                                    that is not allowed.
		 */
		public abstract void onDigit() throws InvalidTransitionException;

		/**
		 * Throws an InvalidTransitionException when parsing a character that is not a
		 * letter or digit.
		 * 
		 * @throws InvalidTransitionException To notify the user that characters can
		 *                                    only be letters or digits in Course name.
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
	}

	/**
	 * The default state for the beginning of the Course name. Only letters can be
	 * legally put here.
	 * 
	 * @author Nick Garner
	 *
	 */
	private class InitialState extends State {
		/**
		 * Null constructor
		 */
		private InitialState() {
		}

		/**
		 * Increments the letter counter and transitions the currentState to the letter
		 * state when parsing a letter at the beginning of a Course name
		 */
		@Override
		public void onLetter() {
			letterCount++;
			currentState = stateLetter;
		}

		/**
		 * Throws an exception to notify the user that a Course name cannot start with a
		 * digit.
		 * 
		 * @throws InvalidTransitionException To notify the user that a Course name
		 *                                    cannot start with a digit.
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");
		}
	}

	/**
	 * Defines behavior when isValid is currently parsing letters in the course
	 * name.
	 * 
	 * @author Nick Garner
	 *
	 */
	private class LetterState extends State {
		/** Max number of letters allowed for Course name prefix */
		private static final int MAX_PREFIX_LETTERS = 4;

		/**
		 * Null constructor
		 */
		private LetterState() {
		}

		/**
		 * When parsing a letter, increments letterCount unless letterCount is equal to
		 * MAX_PREFIX_LETTERS
		 * 
		 * @throws InvalidTransitionException If called when letterCount >=
		 *                                    MAX_PREFIX_LETTERS
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (letterCount < MAX_PREFIX_LETTERS) {
				letterCount++;
			} else {
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}
		}

		/**
		 * When parsing a digit, increments digitCount and transitions currentState to
		 * stateNumber
		 */
		@Override
		public void onDigit() {
			digitCount++;
			currentState = stateNumber;
		}
	}

	/**
	 * Defines behavior when isValid is currently parsing digits in the Course name.
	 * 
	 * @author Nick Garner
	 *
	 */
	private class NumberState extends State {
		/** Exact number of digits required for Course name */
		private static final int COURSE_NUMBER_LENGTH = 3;

		/**
		 * Null constructor
		 */
		private NumberState() {
		}

		/**
		 * When parsing a letter, throws an exception if digitCount !=
		 * COURSE_NUMBER_LENGTH, otherwise transitions currentState to stateSuffix\
		 * 
		 * @throws InvalidTransitionException If digitCount != COURSE_NUMBER_LENGTH
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (digitCount < COURSE_NUMBER_LENGTH) {
				throw new InvalidTransitionException("Course name must have 3 digits.");
			} else {
				currentState = stateSuffix;
			}
		}

		/**
		 * When parsing a digit, throws an exception if digitCount >=
		 * COURSE_NUMBER_LENGTH, otherwise increments digitCount.
		 * 
		 * @throws InvalidTransitionException If digitCount >= COURSE_NUMBER_LENGTH
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			if (digitCount < COURSE_NUMBER_LENGTH) {
				digitCount++;
			} else {
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
		}
	}

	/**
	 * Defines behavior when isValid is parsing Course name in the suffix state
	 * 
	 * @author Nick Garner
	 *
	 */
	private class SuffixState extends State {
		/**
		 * Null constructor
		 */
		private SuffixState() {
		}

		/**
		 * Throws an exception when called, because Course name is only allowed one
		 * suffix letter
		 * 
		 * @throws InvalidTransitionException Course name is only allowed one suffix
		 *                                    letter
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
		}

		/**
		 * Throws an exception when called, because Course name can only have a letter
		 * suffix
		 * 
		 * @throws InvalidTransitionException Course name is only allowed to have
		 *                                    letters in the suffix
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
		}
	}
}