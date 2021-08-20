package Model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * An abstract class that represents a delivery object
 * @author Eddie Kanevsky
 */
public abstract class Delivery extends Record {
	private static int idCounter = 1;
	private DeliveryPerson deliveryPerson;
	private DeliveryArea area;
	private boolean isDelivered;
	private LocalDate deliveryDate;
	
	/**
	 * A "full" constructor for a delivery 
	 * @param deliveryPerson
	 * The delivery person of the delivery
	 * @param area
	 * The delivery area of the delivery
	 * @param isDelivered
	 * Is the delivery sent (delivered)
	 * @param deliveryDate
	 * the delivery date of the current delivery
	 */
	public Delivery(DeliveryPerson deliveryPerson, DeliveryArea area,
			boolean isDelivered, LocalDate deliveryDate) {
		super(idCounter++);
		this.deliveryPerson = deliveryPerson;
		this.area = area;
		this.isDelivered = isDelivered;
		this.deliveryDate = deliveryDate;
	}
	
	/**
	 * A partial constructor for a delivery<br>
	 * A temporary type object
	 * @param id
	 * The id of the delivery
	 */
	public Delivery(int id) {
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
		Delivery.idCounter = idCounter;
	}

	/**
	 * @return the delivery person of the current delivery
	 */
	public DeliveryPerson getDeliveryPerson() {
		return deliveryPerson;
	}

	/**
	 * @param deliveryPerson the delivery person to set
	 */
	public void setDeliveryPerson(DeliveryPerson deliveryPerson) {
		this.deliveryPerson = deliveryPerson;
	}

	/**
	 * @return the area of the current delivery
	 */
	public DeliveryArea getArea() {
		return area;
	}

	/**
	 * @param area the new area to set
	 */
	public void setArea(DeliveryArea area) {
		this.area = area;
	}

	/**
	 * @return is delivered
	 */
	public boolean isDelivered() {
		return isDelivered;
	}

	/**
	 * @param isDelivered the isDelivered to set
	 */
	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

	/**
	 * @return the delivery date
	 */
	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * @param deliveryDate the delivery date to set
	 */
	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@Override
	public String toString() {
		return "Delivery [id=" + getId() + ", deliveryPerson=" + deliveryPerson + ", area=" + area + ", isDelivered="
				+ isDelivered + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getId();
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
		Delivery other = (Delivery) obj;
		if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}
