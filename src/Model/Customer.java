package Model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;

import Utils.Gender;
import Utils.ImageManager;
import Utils.Neighberhood;

/**
 * A class that represents a customer object
 * @author Eddie Kanevsky
 */
public class Customer extends Person{
	
	private static int idCounter = 1;
	private String profileImgName;
	private Neighberhood neighberhood;
	private boolean isSensitiveToLactose;
	private boolean isSensitiveToGluten;
	private String username, password;
	
	/**
	 * A "full" constructor for a customer
	 * @param firstName
	 * The first name of the customer
	 * @param lastName
	 * The last name of the customer
	 * @param birthDay
	 * The birth date of the customer
	 * @param gender
	 * The gender of the customer
	 * @param neighberhood
	 * The neighborhood of the customer
	 * @param isSensitiveToLactose
	 * Is customer sensitive to lactose?
	 * @param isSensitiveToGluten
	 * Is customer sensitive to gluten?
	 */
	public Customer(String firstName, String lastName, LocalDate birthDay, Gender gender,
			Neighberhood neighberhood,	boolean isSensitiveToLactose, boolean isSensitiveToGluten) {
		super(idCounter++, firstName, lastName, birthDay, gender);
		this.neighberhood = neighberhood;
		this.isSensitiveToLactose = isSensitiveToLactose;
		this.isSensitiveToGluten = isSensitiveToGluten;
		this.username = String.format("%s%s%d",  firstName, lastName, getId());
		this.password = "123456";
		setProfileImg("Default");
	}
	
	/**
	 * A partial constructor for a customer<br>
	 * A temporary type object
	 * @param id
	 * The id of the customer
	 */
	public Customer(int id) {
		super(id);
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
		Customer.idCounter = idCounter;
	}

	/**
	 * @return the neighborhood
	 */
	public Neighberhood getNeighberhood() {
		return neighberhood;
	}

	/**
	 * @param neighberhood the neighborhood to set
	 */
	public void setNeighberhood(Neighberhood neighberhood) {
		this.neighberhood = neighberhood;
	}

	/**
	 * @return is sensitive to lactose
	 */
	public boolean isSensitiveToLactose() {
		return isSensitiveToLactose;
	}

	/**
	 * @param isSensitiveToLactose the new sensitivity to lactose to set
	 */
	public void setSensitiveToLactose(boolean isSensitiveToLactose) {
		this.isSensitiveToLactose = isSensitiveToLactose;
	}

	/**
	 * @return is sensitive to gluten
	 */
	public boolean isSensitiveToGluten() {
		return isSensitiveToGluten;
	}

	/**
	 * @param isSensitiveToGluten the new sensitivity to gluten to set
	 */
	public void setSensitiveToGluten(boolean isSensitiveToGluten) {
		this.isSensitiveToGluten = isSensitiveToGluten;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BufferedImage getProfileImg() {
		try{
			return ImageManager.getInstance().getImage(profileImgName);
		}catch(IllegalArgumentException | IOException e){
			return null;
		}
	}
	public void setProfileImg(String img){
		this.profileImgName = img;
	}

	@Override
	public String description() {
		return super.toString()+" Customer [isSensitiveToLactose=" + isSensitiveToLactose + ", isSensitiveToGluten=" + isSensitiveToGluten
				+ "]";
	}
}
