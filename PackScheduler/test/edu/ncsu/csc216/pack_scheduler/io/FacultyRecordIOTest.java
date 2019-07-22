package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Tests FacultyRecordIO class for proper functionality and exception handling
 * 
 * @author Nick Garner
 *
 */
public class FacultyRecordIOTest {

	/** Valid Faculty records */
	private final String validTestFile = "test-files/faculty_records.txt";
	/** Invalid Faculty records */
	private final String invalidTestFile = "test-files/invalid_faculty_records.txt";

	/** Expected results for valid Faculty records */
	private final String validFaculty1 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,pw,2";
	private final String validFaculty2 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,pw,3";
	private final String validFaculty3 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,pw,1";
	private final String validFaculty4 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,pw,3";
	private final String validFaculty5 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,pw,1";
	private final String validFaculty6 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,pw,3";
	private final String validFaculty7 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,pw,1";
	private final String validFaculty8 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,pw,2";

	/** Array to hold expected Faculty results */
	private final String[] validFaculty = { validFaculty1, validFaculty2, validFaculty3, validFaculty4, validFaculty5,
			validFaculty6, validFaculty7, validFaculty8 };

	/** Faculty's password after it has been hashed with SHA-256 */
	private String hashPW;
	/** Algorithm to use for hashing Faculty passwords */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Runs before each test to convert plain-text password for each Student to a
	 * hashed password hashPW
	 * 
	 * @throws Exception If hash algorithm cannot be found
	 */
	@Before
	public void setUp() throws Exception {
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = new String(digest.digest());

			for (int i = 0; i < validFaculty.length; i++) {
				validFaculty[i] = validFaculty[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
	}

	/**
	 * Tests readFacultyRecords with valid entries
	 */
	@Test
	public void testReadValidFacultyRecords() {
		try {
			LinkedList<Faculty> faculty = FacultyRecordIO.readFacultyRecords(validTestFile);
			assertEquals(8, faculty.size());
			// Loop to check file contents
			for (int i = 0; i < validFaculty.length; i++) {
				assertEquals(validFaculty[i], faculty.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("Could not find file: " + validTestFile);
		}

	}

	/**
	 * Tests that readFacultyRecords properly ignores invalid lines in source file
	 */
	@Test
	public void testReadInvalidFacultyRecords() {
		try {
			LinkedList<Faculty> faculty = FacultyRecordIO.readFacultyRecords(invalidTestFile);
			assertEquals(0, faculty.size());
		} catch (FileNotFoundException e) {
			fail("Could not find file: " + invalidTestFile);
		}
	}

	/**
	 * Tests writeFacultyRecords()
	 */
	@Test
	public void testWriteFacultyRecords() {
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		faculty.add(new Faculty("Albus", "Dumbledore", "adumble", "adumble@ncsu.edu", "pw", 2));
		faculty.add(new Faculty("Minerva", "McGonagall", "mmcgonag", "mmcgonag@ncsu.edu", "pw", 3));
		faculty.add(new Faculty("Severus", "Snape", "ssnape", "ssnape@ncsu.edu", "pw", 1));

		try {
			FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_records.txt", faculty);
		} catch (IOException e) {
			fail("Cannot write to target file");
		}
	}
}