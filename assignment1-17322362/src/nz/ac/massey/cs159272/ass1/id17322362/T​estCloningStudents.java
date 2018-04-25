package nz.ac.massey.cs159272.ass1.id17322362;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

	
class T​estCloningStudents {	
	
	@Test
	public void testDeepCloneAddress() throws CloneNotSupportedException {
		Student s1 = new Student();
		s1.setDob(new Date());
		s1.setCourse(new Course());
		s1.setAddress(new Address());
		Student s2 = s1.clone();
		assertTrue(s1.getAddress() != s2.getAddress());	
		assertEquals(s1.getAddress(),s2.getAddress());
	}
	@Test
	public void testDeepCloneDob() throws CloneNotSupportedException {
		Student s1 = new Student();
		s1.setDob(new Date());
		s1.setCourse(new Course());
		s1.setAddress(new Address());
		Student s2 = s1.clone();
		assertTrue(s1.getDob() != s2.getDob());
		assertEquals(s1.getDob(),s2.getDob());		
	}
	@Test
	public void testShadowCloneCourse() throws CloneNotSupportedException {
		Student s1 = new Student();
		s1.setDob(new Date());
		s1.setCourse(new Course());
		s1.setAddress(new Address());
		Student s2 = s1.clone();
		assertTrue(s1.getCourse() == s2.getCourse());
	}

}
