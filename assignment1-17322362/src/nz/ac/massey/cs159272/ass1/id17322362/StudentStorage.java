package nz.ac.massey.cs159272.ass1.id17322362;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class StudentStorage {
	public void save(java.util.Collection<Student> studentList,java.io.File file) throws IOException {
		FileOutputStream studentFile = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(studentFile);
		oos.writeObject(studentList);
		oos.close();		
	}
	public void save(java.util.Collection<Student> studentList,String fileName) throws IOException{
		File f = new File(fileName);
		save(studentList, f); 
	}
	public java.util.Collection<Student> fetch(java.io.File file) throws IOException, ClassNotFoundException{
		FileInputStream studentFile = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(studentFile);
		List<Student> list = (List<Student>) ois.readObject();
		ois.close();
		return list;	
	}
}
