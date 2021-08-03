package Model;

import java.io.Serializable;
import java.time.LocalDate;

import Utils.Gender;
/**
 * An abstrat class that represents a person object
 * @author Eddie Kanevsky
 */
public abstract class Person extends Record {
	
	private int id;
	private String firstName;
	private String lastName;
	private LocalDate birthDay;
	private Gender gender;
	
	/**
	 * A "full" constructor for a person
	 * @param id
	 * The id of the person
	 * @param firstName
	 * The first name of the person
	 * @param lastName
	 * The last name of the person
	 * @param birthDay
	 * The birth date of the person
	 * @param gender
	 * The gender of the person
	 */
	public Person(int id, String firstName, String lastName, LocalDate birthDay, Gender gender) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDay = birthDay;
		this.gender = gender;
	}
	
	/**
	 * A partial constructor for a person<br>
	 * A temporary type object
	 * @param id
	 * The id of a person
	 */
	public Person(int id) {
		this.id = id;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * @param firstName the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @param lastName the last name to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return the birthday
	 */
	public LocalDate getBirthDay() {
		return birthDay;
	}
	
	/**
	 * @param birthDay the birthday to set 
	 */
	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}
	
	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}
	
	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s", firstName, lastName);
	}

	@Override
	public String description() {
		return "[firstName=" + firstName + ", lastName=" + lastName + "]";
	}
}
