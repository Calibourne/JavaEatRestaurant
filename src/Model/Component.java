package Model;

import java.io.Serializable;

/**
 * A class that represents a dish component object
 * @author Eddie Kanevsky
 */
public class Component extends Record implements Comparable<Component> {
	private static int idCounter = 1;
	private String componentName;
	private boolean hasLactose;
	private boolean hasGluten;
	private double price;
	
	/**
	 * A "full" constructor for a component
	 * @param componentName
	 * The name of the component
	 * @param hasLactose
	 * Does the component contain lactose
	 * @param hasGluten
	 * Does the component contain gluten
	 * @param price
	 * The price of the component
	 */
	public Component(String componentName, boolean hasLactose, boolean hasGluten, double price) {
		super(idCounter++);
		this.componentName = componentName;
		this.hasLactose = hasLactose;
		this.hasGluten = hasGluten;
		setPrice(price);
	}
	
	/**
	 * A partial constructor for a component<br>
	 * A temporary type object
	 * @param id
	 * The id of the dish
	 */
	public Component(int id) {
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
		Component.idCounter = idCounter;
	}

	/**
	 * @return the component name
	 */
	public String getComponentName() {
		return componentName;
	}

	/**
	 * @param componentName the component name to set
	 */
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	/**
	 * @return do contain lactose
	 */
	public boolean isHasLactose() {
		return hasLactose;
	}

	/**
	 * @param hasLactose do contain lactose to set
	 */
	public void setHasLactose(boolean hasLactose) {
		this.hasLactose = hasLactose;
	}

	/**
	 * @return do contain gluten
	 */
	public boolean isHasGluten() {
		return hasGluten;
	}

	/**
	 * @param hasGluten do contain gluten to set
	 */
	public void setHasGluten(boolean hasGluten) {
		this.hasGluten = hasGluten;
	}

	/**
	 * @return the price of the component
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

	
	@Override
	public String toString() {
		String allergens = "";
		if(hasGluten || hasLactose)
			allergens+=", contains ";
		if(hasGluten) {
			allergens+="gluten";
			if(hasLactose)
				allergens += " and ";
		}
		if(hasLactose)
			allergens += "lactose";
		return String.format("%s, price: %.2f₪", componentName, price) + allergens;
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
		Component other = (Component) obj;
		return getId().equals(other.getId());
	}

	@Override
	public int compareTo(Component other) {
		int priceCompare = ((Double)getPrice()).compareTo(other.getPrice());
		if(priceCompare == 0)
			return getId().compareTo(other.getId());
		return priceCompare;
	}

	@Override
	public String description() {
		return "Component [id=" + getId() + ", componentName=" + componentName + ", price=" + price + "]";
	}
}