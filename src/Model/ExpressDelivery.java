package Model;

import java.time.LocalDate;

/**
 * A class that represents a Express delivery object
 * @author Eddie Kanevsky
 */
public class ExpressDelivery extends Delivery {
	private Order order;
	private double postage;
	
	/**
	 * A 1st "full" constructor for an express delivery 
	 * @param deliveryPerson
	 * The delivery person of the express delivery
	 * @param area
	 * The delivery area of the express delivery
	 * @param isDelivered
	 * Is the express delivery sent (delivered)
	 * @param deliveryDate
	 * the delivery date of the current express delivery
	 * @param order
	 * the order of the current express delivery
	 * @param postage
	 * the postage of the express delivery
	 */
	public ExpressDelivery(DeliveryPerson deliveryPerson, DeliveryArea area, boolean isDelivered,
			Order order, double postage ,LocalDate deliveryDate) {
		super(deliveryPerson, area, isDelivered, deliveryDate);
		this.postage = postage;
		this.order = order;
	}

	/**
	 * A 2nd "full" constructor for an express delivery 
	 * @param deliveryPerson
	 * The delivery person of the express delivery
	 * @param area
	 * The delivery area of the express delivery
	 * @param isDelivered
	 * Is the express delivery sent (delivered)
	 * @param deliveryDate
	 * the delivery date of the current express delivery
	 * @param order
	 * the order of the current express delivery
	 */
	public ExpressDelivery(DeliveryPerson deliveryPerson, DeliveryArea area, boolean isDelivered,
			Order order, LocalDate deliveryDate) {
		super(deliveryPerson, area, isDelivered, deliveryDate);
		postage = 5.0;
		this.order = order;
	}
	
	/**
	 * A partial constructor for an express delivery<br>
	 * A temporary type object
	 * @param id
	 * The id of the express delivery
	 */
	public ExpressDelivery(int id) {
		super(id);
	}

	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * @return the postage
	 */
	public double getPostage() {
		return postage;
	}

	/**
	 * @param postage the postage to set
	 */
	public void setPostage(double postage) {
		this.postage = postage;
	}

	@Override
	public String toString() {
		return String.format("Express delivery #%d, delivered by %s to %s", getId(), getDeliveryPerson(), getOrder().getCustomer());
	}

	@Override
	public String description() {
		return "Express d" + super.toString().substring(1,super.toString().length()-1) + " postage=" + getPostage() + "]";
	}
}