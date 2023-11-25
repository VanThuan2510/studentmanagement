package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StudentMangement {
	private ArrayList<Student> list_students;
	private String choice;
	private String filename;

	public StudentMangement() {
		this.list_students = new ArrayList<Student>();
		this.choice = "";
		this.filename = "";
	}

	public ArrayList<Student> getList_students() {
		return list_students;
	}

	public void setList_students(ArrayList<Student> list_students) {
		this.list_students = list_students;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public String toString() {
		return list_students.toString() + "\n";
	}

	public void sortScoreFromLowestToHighest() {
		Comparator<Student> comparator1 = new Comparator<Student>() {

			@Override
			public int compare(Student o1, Student o2) {
				if (o1.getStudent_id() > o2.getStudent_id())
					return 1;
				else if (o1.getStudent_id() == o2.getStudent_id())
					return 0;
				else
					return -1;

			}
		};

		Collections.sort(list_students, comparator1);
	}

	public void sortScoreFromHighestToLowest() {
		Comparator<Student> comparator2 = new Comparator<Student>() {

			@Override
			public int compare(Student o1, Student o2) {
				if (o1.getStudent_id() > o2.getStudent_id())
					return -1;
				else if (o1.getStudent_id() == o2.getStudent_id())
					return 0;
				else
					return 1;

			}
		};

		Collections.sort(list_students, comparator2);

	}

	public boolean hasStudent(Student o) {
		for (Student student : list_students) {
			if (student.getStudent_id() == o.getStudent_id()) {
				return true;
			}
		}
		return false;
	}

	public void removeFromList(Student s) {
		for (int i = 0; i < list_students.size(); i++) {
			if (list_students.get(i).getStudent_id() == s.getStudent_id()) {
				list_students.remove(i);
			}
		}
	}

	public void addToList(Student student) {
		list_students.add(student);
	}

	public void update(Student o) {
		for (Student student : list_students) {
			if ((o.getStudent_id() == student.getStudent_id())) {
				student.setStudent_name(o.getStudent_name());
				student.setProvince(o.getProvince());
				student.setDate(o.getDate());
				student.setGender(o.isGender());
				student.setSubject1(o.getSubject1());
				student.setSubject2(o.getSubject2());
				student.setSubject3(o.getSubject3());
			}

		}
	}

}
