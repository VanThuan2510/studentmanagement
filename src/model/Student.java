package model;

import java.io.Serializable;
import java.util.Objects;

public class Student implements Serializable {

	private static final long serialVersionUID = -8950945688057768215L;
	private String student_name;
	private int student_id;
	private Province province;
	private BirthDate date;
	private boolean gender;
	private float subject1, subject2, subject3;

	/**
	 * @param student_name
	 * @param student_id
	 * @param province
	 * @param date
	 * @param gender
	 * @param subject1
	 * @param subject2
	 * @param subject3
	 */
	public Student(String student_name, int student_id, Province province, BirthDate date, boolean gender,
			float subject1, float subject2, float subject3) {
		super();
		this.student_name = student_name;
		this.student_id = student_id;
		this.province = province;
		this.date = date;
		this.gender = gender;
		this.subject1 = subject1;
		this.subject2 = subject2;
		this.subject3 = subject3;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public BirthDate getDate() {
		return date;
	}

	public void setDate(BirthDate date) {
		this.date = date;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public float getSubject1() {
		return subject1;
	}

	public void setSubject1(float subject1) {
		this.subject1 = subject1;
	}

	public float getSubject2() {
		return subject2;
	}

	public void setSubject2(float subject2) {
		this.subject2 = subject2;
	}

	public float getSubject3() {
		return subject3;
	}

	public void setSubject3(float subject3) {
		this.subject3 = subject3;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(date, other.date) && gender == other.gender && Objects.equals(province, other.province)
				&& student_id == other.student_id && Objects.equals(student_name, other.student_name)
				&& Float.floatToIntBits(subject1) == Float.floatToIntBits(other.subject1)
				&& Float.floatToIntBits(subject2) == Float.floatToIntBits(other.subject2)
				&& Float.floatToIntBits(subject3) == Float.floatToIntBits(other.subject3);
	}

	@Override
	public String toString() {
		return "Student ID : " + student_id;
	}

}
