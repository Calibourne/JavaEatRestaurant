package Model;

import Model.Exceptions.NoComponentsException;
import Utils.DishType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class that represents a dish object
 * @author Eddie Kanevsky
 */
public class Dish extends Record {
	private static int idCounter = 1;
	private int id;
	private String dishName;
	private DishType type;
	private ArrayList<Component> components;
	private double price;
	private int timeToMake;
	
	/**
	 * A "full" constructor for a dish 
	 * @param dishName
	 * The dish name
	 * @param type
	 * The dish type
	 * @param components
	 * The dish components
	 * @param timeToMake
	 * The dish preparing time
	 */
	public Dish(String dishName, DishType type, ArrayList<Component> components, int timeToMake) {
		super();
		this.id = idCounter++;
		this.dishName = dishName;
		this.type = type;
		this.components = components;
		this.timeToMake = timeToMake;
		price = calcDishPrice();
	}
	
	/**
	 * A partial constructor for a dish<br>
	 * A temporary type object
	 * @param id
	 * The id of the dish
	 */
	public Dish(int id) {
		this.id = id;
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
		Dish.idCounter = idCounter;
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
	 * @return the dish name
	 */
	public String getDishName() {
		return dishName;
	}
	
	/**
	 * @param dishName the dish name to set
	 */
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	
	/**
	 * @return the dish type
	 */
	public DishType getType() {
		return type;
	}
	
	/**
	 * @param type the dish type to set
	 */
	public void setType(DishType type) {
		this.type = type;
	}
	
	/**
	 * @return unmodifiable list of current dish components
	 */
	public List<Component> getComponents() {
		return Collections.unmodifiableList(components);
	}
	
	/**
	 * Tries to add a new component to the dish
	 * @param c
	 * the component to add
	 * @return
	 * Success / Failure of the insertion
	 */
	public boolean addComponent(Component c)
	{
		if(c!=null)
			return components.add(c);
		return false;
	}
	
	/**
	 * Tries to remove a component from the dish 
	 * @param c
	 * the component to remove
	 * @return
	 * Success / Failure of the removal
	 * @throws NoComponentsException
	 * whether there is no components in the current dish
	 */
	public boolean removeComponent(Component c) throws NoComponentsException
	{
		if(c!=null)
		{
			if(components.size() <= 1)
			{
				if(components.isEmpty())
					throw new NoComponentsException(this);
				else
				{
					if(components.remove(c))
						throw new NoComponentsException(this); 
					return false;
				}
			}
			return components.remove(c);
		}
		return false;
	}
	
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		if(price > 0.0)
			this.price = price;
		else
			price = 0.0;
	}
	
	/**
	 * @return the time to make
	 */
	public int getTimeToMake() {
		return timeToMake;
	}
	
	/**
	 * @param timeToMake the new time to set
	 */
	public void setTimeToMake(int timeToMake) {
		this.timeToMake = timeToMake;
	}


	@Override
	public String toString() {
		String allergens = "";
		if(getComponents().stream().anyMatch(Component::isHasGluten)){
			allergens += ", contains gluten";
			if(getComponents().stream().anyMatch(Component::isHasLactose))
				allergens += " and lactose";
		}
		else if(getComponents().stream().anyMatch(Component::isHasLactose))
			allergens += ", contains lactose";

		return String.format("%s - %s, price: %.2f₪", dishName, type, price) + allergens;
	}

	// A backup of the original ToString so we can clean up the ToString to our liking
	@Override
	public String description() {
		return "Dish [id=" + id + ", dishName=" + dishName + ", type=" + type + ", price=" + price + ", timeToMake="
				+ timeToMake + "]";
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
		Dish other = (Dish) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	/**
	 * Calculates the price of the dish 
	 * @return the calculated price
	 */
	public double calcDishPrice() {
		double price = 0.0;
		for(Component c : components) {
			price += c.getPrice();
		}
		price = price*3;
		return price;
	}

	public Dish cloneDish() {
		Dish cloned = new Dish(-1);
		cloned.dishName = dishName;
		cloned.type = type;
		cloned.price = price;
		cloned.components = components;
		return cloned;
	}
}