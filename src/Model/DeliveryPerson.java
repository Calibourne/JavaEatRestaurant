package Model;

import java.time.LocalDate;

import Utils.Gender;
import Utils.Vehicle;

/**
 * A class that represents a delivery person object
 * @author Eddie Kanevsky
 */
public class DeliveryPerson extends Person {
	private static int idCounter = 1;
	private Vehicle vehicle;
	private DeliveryArea area;
	
	/**
	 * A "full" constructor for a delivery person
	 * @param firstName
	 * The first name of the delivery person
	 * @param lastName
	 * The last name of the delivery person
	 * @param birthDate
	 * The birth date of the delivery person
	 * @param gender
	 * The gender of the delivery person
	 * @param vehicle
	 * The vehicle of the delivery person
	 * @param deliveryArea
	 * The delivery area of the delivery person
	 */
	public DeliveryPerson(String firstName, String lastName, LocalDate birthDay, Gender gender, Vehicle vehicle,
			DeliveryArea area) {
		super(idCounter++, firstName, lastName, birthDay, gender);
		this.vehicle = vehicle;
		this.area = area;
	}
	
	/**
	 * A partial constructor for a delivery person<br>
	 * A temporary type object
	 * @param id
	 * The id of the delivery person
	 */
	public DeliveryPerson(int id) {
		super(id);
	}
	
	/**
	 * @return the id counter
	 */
	public static int getIdCounter() {
		return idCounter;
	}
	
	/**
	 * @param idCounter the id counter to set
	 */
	public static void setIdCounter(int idCounter) {
		DeliveryPerson.idCounter = idCounter;
	}
	
	/**
	 * @return the vehicle
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}
	
	/**
	 * @param vehicle the vehicle to set
	 */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	/**
	 * @return the area
	 */
	public DeliveryArea getArea() {
		return area;
	}
	
	/**
	 * @param area the area to set
	 */
	public void setArea(DeliveryArea area) {
		this.area = area;
	}
	
	@Override
	public String toString() {
		return super.toString()+" DeliveryPerson [vehicle=" + vehicle + "]";
	}
}
