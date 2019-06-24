package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidatorFSM;

public class CourseNameValidatorFSMTest {

	CourseNameValidatorFSM validator;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInitialLetter() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSC116";
		assertTrue(validator.isValid(courseName));
	}

	@Test
	public void testInitialDigit() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "1CSC216";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must start with a letter.", e.getMessage());
		}
	}

	@Test
	public void testInitialSymbol() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "!CSC216";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	@Test
	public void testStateLLetter() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSC216";
		assertTrue(validator.isValid(courseName));
	}

	@Test
	public void testStateLDigit() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "C216";
		assertTrue(validator.isValid(courseName));
	}
	
	@Test
	public void testStateLSymbol() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "C!SC216";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	@Test
	public void testStateLLLetter() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSC216";
		assertTrue(validator.isValid(courseName));
	}

	@Test
	public void testStateLLDigit() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CS216";
		assertTrue(validator.isValid(courseName));
	}
	
	@Test
	public void testStateLLSymbol() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CS!C216";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	@Test
	public void testStateLLLLetter() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSCS216";
		assertTrue(validator.isValid(courseName));
	}

	@Test
	public void testStateLLLDigit() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSC216";
		assertTrue(validator.isValid(courseName));
	}
	
	@Test
	public void testStateLLLSymbol() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSC!216";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	@Test
	public void testStateLLLLLetter() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSCSE216";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot start with more than 4 letters.", e.getMessage());
		}
	}

	@Test
	public void testStateLLLLDigit() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSCS216";
		assertTrue(validator.isValid(courseName));
	}
	
	@Test
	public void testStateLLLLSymbol() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSCS!216";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}

	@Test
	public void testStateDLetter() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSC2E";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
	}

	@Test
	public void testStateDDigit() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSC216";
		assertTrue(validator.isValid(courseName));
	}

	@Test
	public void testStateDSymbol() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSC2!16";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}
	
	@Test
	public void testStateDDLetter() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSC22E";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
	}

	@Test
	public void testStateDDDigit() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSC230";
		assertTrue(validator.isValid(courseName));
	}

	@Test
	public void testStateDDSymbol() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSC21!6";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}
	
	@Test
	public void testStateDDDLetter() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSC230E";
		assertTrue(validator.isValid(courseName));
	}

	@Test
	public void testStateDDDDigit() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSC2301";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have 3 digits.", e.getMessage());
		}
	}

	@Test
	public void testStateDDDSymbol() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSC216!";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}
	
	@Test
	public void testStateSuffixLetter() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSC230EF";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have a 1 letter suffix.", e.getMessage());
		}
	}

	@Test
	public void testStateSuffixDigit() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSC230E2";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot contain digits after the suffix.", e.getMessage());
		}
	}
	
	@Test
	public void testStateSuffixSymbol() throws Exception {
		validator = new CourseNameValidatorFSM();
		String courseName = "CSC216E!";
		try {
			validator.isValid(courseName);
			fail();
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}
}
