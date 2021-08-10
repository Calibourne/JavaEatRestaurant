package Model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class RegularDelivery extends Delivery {

	private TreeSet<Order> orders;
	
	/**
	 * A "full" constructor for a regular delivery 
	 * @param deliveryPerson
	 * The delivery person of the regular delivery
	 * @param orders
	 * The orders of the regular delivery
	 * @param area
	 * The delivery area of the regular delivery
	 * @param isDelivered
	 * Is the delivery sent (delivered)
	 * @param deliveryDate
	 * the delivery date of the current regular delivery
	 */
	public RegularDelivery(TreeSet<Order> orders, DeliveryPerson deliveryPerson, DeliveryArea area, boolean isDelivered,
			LocalDate deliveryDate){
		super(deliveryPerson, area, isDelivered, deliveryDate);
		this.orders=orders;
	}

	/**
	 * A partial constructor for a regular delivery<br>
	 * A temporary type object
	 * @param id
	 * The id of the regular delivery
	 */
	public RegularDelivery(int id) {
		super(id);
	}

	/**
	 * @return unmodifiable ordered set of the orders of the regular delivery 
	 */
	public SortedSet<Order> getOrders() {
		return Collections.unmodifiableSortedSet(orders);
	}
	
	/**
	 * Tries to add a new order to the regular delivery
	 * @param o
	 * the order
	 * @return
	 * Success / Failure of the insertion
	 */
	public boolean addOrder(Order o)
	{
		if(o!=null)
			return orders.add(o);
		return false;
	}
	
	/**
	 * Tries to remove an order from the regular delivery
	 * @param o
	 * the order
	 * @return
	 * Success / Failure of the removal
	 */
	public boolean removeOrder(Order o)
	{
		if(o!=null)
			return orders.remove(o);
		return false;
	}
	@Override
	public String toString() {
		return String.format("Regular delivery #%d delivered by %s", getId(), getDeliveryPerson());
	}

	@Override
	public String description() {
		return String.format("Regular delivery No%d", getId());
	}
}