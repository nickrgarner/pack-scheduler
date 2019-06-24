package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

//import org.junit.Before;
import org.junit.Test;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;

/**
 * Tests that CourseNameValidator state design properly transitions Course name
 * parsing between possible states.
 * 
 * @author Nick Garner
 *
 */
public class CourseNameValidatorTest {

	CourseNameValidator validator;

//	@Before
//	public void setUp() throws Exception {
//	}

	/**
	 * Tests parsing a letter as the initial character
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testInitialLetter() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSC116";
		assertTrue(validator.isValid(courseName));
	}

	/**
	 * Tests parsing a digit as the initial character
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testInitialDigit() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "1CSC216";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must start with a letter.", e.getMessage());
		}
	}

	/**
	 * Tests parsing a non-letter-or-digit as the initial character
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testInitialSymbol() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "!CSC216";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	/**
	 * Tests parsing a letter after an initial letter
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateLLetter() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSC216";
		assertTrue(validator.isValid(courseName));
	}

	/**
	 * Tests parsing a digit after an initial letter
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateLDigit() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "C216";
		assertTrue(validator.isValid(courseName));
	}

	/**
	 * Tests parsing a non-letter-or-digit after an initial letter
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateLSymbol() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "C!SC216";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	/**
	 * Tests parsing a letter after an initial two letters
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateLLLetter() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSC216";
		assertTrue(validator.isValid(courseName));
	}

	/**
	 * Tests parsing a digit after an initial two letters
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateLLDigit() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CS216";
		assertTrue(validator.isValid(courseName));
	}

	/**
	 * Tests parsing a non-letter-or-digit after an initial two letters
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateLLSymbol() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CS!C216";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	/**
	 * Tests parsing a letter after an initial three letters
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateLLLLetter() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSCS216";
		assertTrue(validator.isValid(courseName));
	}

	/**
	 * Tests parsing a digit after an initial three letters
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateLLLDigit() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSC216";
		assertTrue(validator.isValid(courseName));
	}

	/**
	 * Tests parsing a non-letter-or-digit after an initial three letters
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateLLLSymbol() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSC!216";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	/**
	 * Tests parsing a letter after an initial four letters
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateLLLLLetter() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSCSE216";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot start with more than 4 letters.", e.getMessage());
		}
	}

	/**
	 * Tests parsing a digit after an initial four letters
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateLLLLDigit() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSCS216";
		assertTrue(validator.isValid(courseName));
	}

	/**
	 * Tests parsing a non-letter-or-digit after an initial four letters
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateLLLLSymbol() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSCS!216";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	/**
	 * Tests parsing a letter after a single digit
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateDLetter() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSC2E";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
	}

	/**
	 * Tests parsing a digit after a single digit
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateDDigit() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSC216";
		assertTrue(validator.isValid(courseName));
	}

	/**
	 * Tests parsing a non-letter-or-digit after a single digit
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateDSymbol() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSC2!16";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	/**
	 * Tests parsing a letter after two digits
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateDDLetter() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSC22E";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
	}

	/**
	 * Tests parsing a digit after two digits
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateDDDigit() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSC230";
		assertTrue(validator.isValid(courseName));
	}

	/**
	 * Tests parsing a non-letter-or-digit after two digits
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateDDSymbol() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSC21!6";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	/**
	 * Tests parsing a letter after three digits
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateDDDLetter() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSC230E";
		assertTrue(validator.isValid(courseName));
	}

	/**
	 * Tests parsing a digit after three digits
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateDDDDigit() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSC2301";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have 3 digits.", e.getMessage());
		}
	}

	/**
	 * Tests parsing a non-letter-or-digit after three digits
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateDDDSymbol() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSC216!";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	/**
	 * Tests parsing a letter after a suffix letter
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateSuffixLetter() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSC230EF";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have a 1 letter suffix.", e.getMessage());
		}
	}

	/**
	 * Tests parsing a digit after a suffix letter
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateSuffixDigit() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSC230E2";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot contain digits after the suffix.", e.getMessage());
		}
	}

	/**
	 * Tests parsing a non-letter-or-digit after a suffix letter
	 * 
	 * @throws Exception If isValid method encounters an Invalid Transition
	 */
	@Test
	public void testStateSuffixSymbol() throws Exception {
		validator = new CourseNameValidator();
		String courseName = "CSC216E!";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}
}
