package model;

import java.io.Serializable;
import java.util.Objects;

public class BirthDate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String day, month, year;

	/**
	 * @param day
	 * @param month
	 * @param year
	 */
	public BirthDate(String day, String month, String year) {
		super();
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return day + "/" + month + "/" + year;
	}

	@Override
	public int hashCode() {
		return Objects.hash(day, month, year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BirthDate other = (BirthDate) obj;
		return Objects.equals(day, other.day) && Objects.equals(month, other.month) && Objects.equals(year, other.year);
	}

}
