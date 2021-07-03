package Model;

import java.time.LocalDate;

import Utils.Expertise;
import Utils.Gender;

/**
 * A class that represents a cook object
 * @author Eddie Kanevsky
 */
public class Cook extends Person{
	private static int idCounter = 1;
	private Expertise expert;
	private boolean isChef;
	
	/**
	 * A "full" constructor for a cook
	 * @param firstName
	 * The first name of the cook
	 * @param lastName
	 * The last name of the cook
	 * @param birthDay
	 * The birth date of the cook
	 * @param gender
	 * The gender of the cook
	 * @param expert
	 * The expertise of the cook
	 * @param isChef
	 * Is chef or not?
	 */
	public Cook(String firstName, String lastName, LocalDate birthDay, Gender gender, Expertise expert,
			boolean isChef) {
		super(idCounter++, firstName, lastName, birthDay, gender);
		this.expert = expert;
		this.isChef = isChef;
	}
	
	/**
	 * A partial constructor for a cook<br>
	 * A temporary type object
	 * @param id
	 * The id of the cook
	 */
	public Cook(int id) {
		super(id);
	}

	/**
	 * @return the expertise
	 */
	public Expertise getExpert() {
		return expert;
	}

	/**
	 * @param expert the expertise to set
	 */
	public void setExpert(Expertise expert) {
		this.expert = expert;
	}

	/**
	 * @return is chef?
	 */
	public boolean isChef() {
		return isChef;
	}

	/**
	 * @param isChef the "is chef?" to set
	 */
	public void setChef(boolean isChef) {
		this.isChef = isChef;
	}
	

	/**
	 * @return the idCounter
	 */
	public static int getIdCounter() {
		return idCounter;
	}

	/**
	 * @param idCounter the idCounter to set
	 */
	public static void setIdCounter(int idCounter) {
		Cook.idCounter = idCounter;
	}

	@Override
	public String toString() {
		return super.toString()+" Cook [expert=" + expert + "]";
	}


	
	
	
	
	
}
