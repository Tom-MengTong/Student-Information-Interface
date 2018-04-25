package nz.ac.massey.cs159272.ass1.id17322362;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestPersistency {

	@Test
	public void testSave_File() throws IOException {
		StudentStorage s1 = new StudentStorage();
		Collection<Student> studentList = new ArrayList<Student>();
		Student studentTest = new Student();
		Student studentTest_2 = new Student();
		studentTest.setFirstName("Tom");
		studentTest.setName("Tong");
		studentTest_2.setFirstName("Alice");
		studentTest_2.setName("Deng");
		studentList.add(studentTest);
		studentList.add(studentTest_2);
		Course math = new Course();
		Course java = new Course();
		math.setName("Mathematics");
		math.setNumber("001");
		java.setName("Java");
		java.setNumber("002");
		studentTest.setCourse(math);
		studentTest_2.setCourse(java);
		Date dateNow = new Date();
		studentTest.setDob(dateNow);
		studentTest_2.setDob(dateNow);
		Address myAddress = new Address();
		Address myAddress_2 = new Address();
		myAddress.setHouseNumber(13);
		myAddress.setPostCode("4010");
		myAddress.setStreet("Keiller Place");
		myAddress.setTown("Palmerston North");
		myAddress_2.setHouseNumber(1);
		myAddress_2.setPostCode("4010");
		myAddress_2.setStreet("Main Street");
		myAddress_2.setTown("Palmerston North");
		studentTest.setAddress(myAddress);
		studentTest_2.setAddress(myAddress_2);
		File f = new File("studentFile");
		s1.save(studentList,f);
		assertTrue(f.exists());
	}
	@Test
	public void testSave_String() throws IOException {
		StudentStorage s1 = new StudentStorage();
		Collection<Student> studentList = new ArrayList<Student>();
		String fileName = "studentFile_2";
		s1.save(studentList,fileName);
		File f = new File(fileName);
		assertTrue(f.exists());
	}
	@Test
	public void testFetch() throws IOException, ClassNotFoundException {
		StudentStorage s1 = new StudentStorage();
		Collection<Student> studentList = new ArrayList<Student>();
		File f = new File("studentFile");
		s1.save(studentList,f);
		Collection<Student> studentList_2 = s1.fetch(f);
		assertEquals(studentList,studentList_2);
	}
	@Test
	public void testReference() throws IOException, ClassNotFoundException {
		StudentStorage s1 = new StudentStorage();
		Collection<Student> studentList = new ArrayList<Student>();
		Student student_1 = new Student();
		Student student_2 = new Student();
		Course c1 = new Course();
		Address a1 = new Address();
		Address a2 = new Address();
		student_1.setCourse(c1);
		student_2.setCourse(c1);
		student_1.setAddress(a1);
		student_2.setAddress(a2);
		studentList.add(student_1);
		studentList.add(student_2);
		File f = new File("studentFile");
		s1.save(studentList,f);
		List<Student> studentList_2 = (List<Student>)s1.fetch(f);
		Student student_1_fetched = studentList_2.get(0);
		Student student_2_fetched = studentList_2.get(1);
		assertTrue(student_1.getCourse() == student_2.getCourse());
		assertTrue(student_1_fetched.getCourse() == student_2_fetched.getCourse());
		assertTrue(student_1_fetched.getAddress() != student_2_fetched.getAddress());
	}
	@Test
	public void testException(){
		Assertions.assertThrows(IOException.class, () ->{
			StudentStorage s1 = new StudentStorage();
			Collection<Student> studentList = new ArrayList<Student>();
			File f_1 = new File("studentFile");
			File f_2 = new File("studentFile_2_new");
			s1.save(studentList,f_1);
			s1.fetch(f_2);
		});
	}
}
