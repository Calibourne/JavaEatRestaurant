package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Utils.MyFileLogWriter;

/**
 * A class that represents an order object
 * @author Eddie Kanevsky
 */
public class Order implements Comparable<Order>, Serializable {
	
	private static int idCounter = 1;
	private int id;
	private Customer customer;
	private ArrayList<Dish> dishes;
	private Delivery delivery;
	
	/**
	 * A "full" constructor for a order 
	 * @param customer
	 * The customer of the order
	 * @param dishes
	 * The dishes on the order
	 * @param delivery
	 * The linked delivery
	 */
	public Order(Customer customer, ArrayList<Dish> dishes, Delivery delivery) {
		super();
		this.id = idCounter++;
		this.customer = customer;
		this.dishes = dishes;
		this.delivery = delivery;
	}
	
	/**
	 * A partial constructor for an order<br>
	 * A temporary type object
	 * @param id
	 * The id of the order
	 */
	public Order(int id) {
		this.id = id;
	}
	
	/**
	 * @return the id counter
	 */
	public static int getIdCounter() {
		return idCounter;
	}

	/**
	 * @param idCounter
	 */
	public static void setIdCounter(int idCounter) {
		Order.idCounter = idCounter;
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
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * @return unmodifiable list of the dishes in the current order
	 */
	public List<Dish> getDishes() {
		return Collections.unmodifiableList(dishes);
	}
	
	/**
	 * Tries to add a new dish to the order
	 * @param d
	 * the dish
	 * @return
	 * Success / Failure of the insertion
	 */
	public boolean addDish(Dish d)
	{
		if(d!=null)
			return dishes.add(d);
		return false;
	}
	
	/**
	 * Tries to remove a dish from the order
	 * @param d
	 * the dish
	 * @return
	 * Success / Failure of the insertion
	 */
	public boolean removeDish(Dish d)
	{
		if(d!=null)
			return dishes.remove(d);
		return false;
	}

	/**
	 * @return the delivery
	 */
	public Delivery getDelivery() {
		return delivery;
	}

	/**
	 * @param delivery the delivery to set
	 */
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", customer=" + customer + "]";
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
		Order other = (Order) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public int compareTo(Order other) {
		return ((Integer)id).compareTo((Integer)other.id);
	}
	
	public double calcOrderRevenue() {
		double revenue = 0.0;
		for(Dish d : getDishes()) {
			double price = d.getPrice();
			double cost = 0.0;
			for(Component c : d.getComponents()) {
				cost += c.getPrice();
			}
			revenue += (price - cost);
		}
		MyFileLogWriter.println("Order Revenue = "+revenue);
		return revenue;
	}
	
	/**
	 * Calculates a waiting time for the order and prints it
	 * @param da
	 * The delivery area of the order
	 */
	public int orderWaitingTime(DeliveryArea da) {
		int time = 0;
		time += da.getDeliverTime();
		for(Dish d : getDishes()) {
			time += d.getTimeToMake();
		}
		MyFileLogWriter.println("Time for order: "+this+" is "+time+" minutes");
		return time;
	}

	
	
}
