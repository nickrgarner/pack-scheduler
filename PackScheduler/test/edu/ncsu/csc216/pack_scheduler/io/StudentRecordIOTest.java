package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Tests StudentRecordIO class.
 * 
 * @author Nick Garner
 *
 */
public class StudentRecordIOTest {

	/** Valid student records */
	private final String validTestFile = "test-files/student_records.txt";
	/** Invalid student records */
	private final String invalidTestFile = "test-files/invalid_student_records.txt";

	/** Expected results for valid student records */
	private final String validStudent1 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
	private final String validStudent2 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	private final String validStudent3 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	private final String validStudent4 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	private final String validStudent5 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	private final String validStudent6 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	private final String validStudent7 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	private final String validStudent8 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";
	private final String validStudent9 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	private final String validStudent10 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";

	/** Array to hold expected results */
	private final String[] validStudents = { validStudent1, validStudent2, validStudent3, validStudent4, validStudent5,
			validStudent6, validStudent7, validStudent8, validStudent9, validStudent10 };
	
	/** Student's password after it has been hashed with SHA_256 */
	private String hashPW;
	/** Algorithm to use for hashing Student passwords */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Converts plain-text password for each Student to a hashed password hashPW
	 * @throws Exception When hashing algorithm cannot be found.
	 */
	@Before
	public void setUp() throws Exception {
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = new String(digest.digest());
			
			for (int i = 0; i < validStudents.length; i++) {
				validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
	}

	/**
	 * Tests readValidStudentRecords().
	 */
	@Test
	public void testReadValidStudentRecords() {
		try {
			SortedList<Student> students = StudentRecordIO.readStudentRecords(validTestFile);
			assertEquals(10, students.size());
			// Loop to check file contents
			for (int i = 0; i < validStudents.length; i++) {
				assertEquals(validStudents[i], students.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("Could not find file: " + validTestFile);
		}
	}

	/**
	 * Tests readInvalidStudentRecords().
	 */
	@Test
	public void testReadInvalidStudentRecords() {
		try {
			SortedList<Student> students = StudentRecordIO.readStudentRecords(invalidTestFile);
			assertEquals(0, students.size());
		} catch (FileNotFoundException e) {
			fail("Could not find file: " + invalidTestFile);
		}

	}

	/**
	 * Tests writeStudentRecords() method.
	 */
	@Test
	public void testWriteStudentRecords() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Harry", "Potter", "hpotter", "scarboy@ncsu.edu", "expectopatronum", 15));
		students.add(new Student("Ronald", "Weasley", "rweasley", "scabbersfan@ncsu.edu", "obliviate", 12));
		students.add(new Student("Hermione", "Granger", "hgrange", "SPEWPres@ncsu.edu", "leviosa", 18));

		try {
			StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt", students);
		} catch (IOException e) {
			fail("Cannot write to target file");
		}
	}

}
