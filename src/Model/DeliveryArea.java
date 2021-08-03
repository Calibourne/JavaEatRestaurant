package Model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Utils.Neighberhood;

/**
 * A class that represents a delivery area object
 * @author Eddie Kanevsky
 */
public class DeliveryArea extends Record {
	private static int idCounter = 1;
	private int id;
	private String areaName;
	private HashMap<Integer,DeliveryPerson> delPersons;
	private HashMap<Integer,Delivery> deliveries;
	private HashSet<Neighberhood> neighberhoods;
	private final int deliverTime;
	
	/**
	 * A "full" constructor for a delivery area
	 * @param areaName
	 * The name of the delivery area
	 * @param neighberhoods
	 * The neighborhoods in the delivery area
	 * @param deliverTime
	 * The delivery time to the delivery area from the restaurant
	 */
	public DeliveryArea(String areaName, HashSet<Neighberhood> neighberhoods, int deliverTime) {
		super();
		this.id = idCounter++;
		this.areaName = areaName;
		this.neighberhoods = neighberhoods;
		this.deliverTime = deliverTime;
		delPersons = new HashMap<>();
		deliveries = new HashMap<>();
	}
	
	/**
	 * A partial constructor for a delivery area<br>
	 * A temporary type object
	 * @param id
	 * The id of the delivery area
	 */
	public DeliveryArea(int id) {
		this.id = id;
		this.deliverTime = 0;
	}

	/**
	 * @return the idCounter
	 */
	public static int getIdCounter() {
		return idCounter;
	}

	/**
	 * @param idCounter the id counter to set
	 */
	public static void setIdCounter(int idCounter) {
		DeliveryArea.idCounter = idCounter;
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
	 * @return the area name
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName area name to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return unmodifiable collection of delivery persons associated with the current area
	 */
	public Map<Integer,DeliveryPerson> getDelPersons() {
		return Collections.unmodifiableMap(delPersons);
	}
	
	/**
	 * Tries to add a delivery person to a delivery area
	 * @param dp
	 * the delivery person to add
	 * @return
	 * Success / Failure of the insertion
	 */
	public boolean addDelPerson(DeliveryPerson dp)
	{
		if(dp!=null)
			return delPersons.put(dp.getId(), dp)==null;
		return false;
	}
	
	/**
	 * Tries to remove a delivery person from the delivery area
	 * @param dp
	 * the delivery person to remove
	 * @return
	 * Success / Failure of the removal
	 */
	public boolean removeDelPerson(DeliveryPerson dp)
	{
		if(dp!=null)
			return delPersons.remove(dp.getId()) != null;
		return false;
	}
	
	/**
	 * @return unmodifiable collection of deliveries in the current area
	 */
	public Map<Integer,Delivery> getDeliveries() {
		return Collections.unmodifiableMap(deliveries);
	}
	
	/**
	 * Tries to add a new delivery to the area
	 * @param d
	 * the delivery to add 
	 * @return
	 * Success / Failure of the insertion
	 */
	public boolean addDelivery(Delivery d)
	{
		if(d!=null)
			return deliveries.put(d.getId(), d)==null;
		return false;
	}
	
	/**
	 * Tries to remove a delivery from the area
	 * @param d
	 * the delivery to remove
	 * @return
	 * Success / Failure of the removal
	 */
	public boolean removeDelivery(Delivery d)
	{
		if(d!=null)
			return deliveries.remove(d.getId(), d);
		return false;
	}
	
	/**
	 * @return unmodifiable collection of neighborhoods in the current area
	 */
	public Set<Neighberhood> getNeighberhoods() {
		return Collections.unmodifiableSet(neighberhoods);
	}
	
	/**
	 * Tries to add a new neighborhood to the area
	 * @param neiberhood
	 * the neighborhood to add
	 * @return
	 * Success / Failure of the insertion
	 */
	public boolean addNeiberhood(Neighberhood neiberhood)
	{
		if(neiberhood!=null)
			return neighberhoods.add(neiberhood);
		return false;
	}
	
	/**
	 * Tries to remove a neighborhood from the area
	 * @param neighberhood
	 * the neighborhood to remove
	 * @return
	 * Success / Failure of the removal
	 */
	public boolean removeNeighberhood(Neighberhood neighberhood)
	{
		if(neighberhood!=null)
			return neighberhoods.remove(neighberhood);
		return false;
	}

	/**
	 * @return the delivery time
	 */
	public int getDeliverTime() {
		return deliverTime;
	}

	@Override
	public String toString() {
		return String.format("Area: \"%s\", delivery time: %d minutes", areaName, deliverTime);
	}

	@Override
	public String description() {
		return "DeliveryArea [id=" + id + ", areaName=" + areaName + ", neighberhoods=" + neighberhoods
				+ ", deliverTime=" + deliverTime + "]";
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
		DeliveryArea other = (DeliveryArea) obj;
		if (id != other.id)
			return false;
		return true;
	}	
}