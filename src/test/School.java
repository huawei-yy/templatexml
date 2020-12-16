package test;

import java.util.ArrayList;
import java.util.List;
import test.Class;

public class School {
	private String name;
	private List<Class> classes = new ArrayList<Class>();
	private List<Student> students = new ArrayList<Student>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Class> getClasses() {
		return classes;
	}

	public void setClasses(List<Class> classes) {
		this.classes = classes;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

}
