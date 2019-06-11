package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.*;

//import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.collections.list.SortedList;
import edu.ncsu.csc216.pack_scheduler.user.Student;

import java.io.FileNotFoundException;
import java.io.IOException;

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
	private final String validStudent1 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,0�R��\"��u���\\7X�F���9�{-O�F�ap�,18";
	private final String validStudent2 = "Lane,Berg,lberg,sociis@non.org,0�R��\"��u���\\7X�F���9�{-O�F�ap�,14";
	private final String validStudent3 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,0�R��\"��u���\\7X�F���9�{-O�F�ap�,12";
	private final String validStudent4 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,0�R��\"��u���\\7X�F���9�{-O�F�ap�,3";
	private final String validStudent5 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,0�R��\"��u���\\7X�F���9�{-O�F�ap�,14";
	private final String validStudent6 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,0�R��\"��u���\\7X�F���9�{-O�F�ap�,11";
	private final String validStudent7 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,0�R��\"��u���\\7X�F���9�{-O�F�ap�,15";
	private final String validStudent8 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,0�R��\"��u���\\7X�F���9�{-O�F�ap�,5";
	private final String validStudent9 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,0�R��\"��u���\\7X�F���9�{-O�F�ap�,4";
	private final String validStudent10 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,0�R��\"��u���\\7X�F���9�{-O�F�ap�,17";

	/** Array to hold expected results */
	private final String[] validStudents = { validStudent1, validStudent2, validStudent3, validStudent4, validStudent5,
			validStudent6, validStudent7, validStudent8, validStudent9, validStudent10 };

//	@Before
//	public void setUp() throws Exception {
//	}

	/**
	 * Tests readValidCourseRecords().
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
	 * Tests readInvalidCourseRecords().
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
