package Model;

import Model.Exceptions.ConvertToExpressException;
import Model.Exceptions.IllegalCustomerException;
import Model.Exceptions.NoComponentsException;
import Model.Exceptions.SensitiveException;
import Model.Requests.AddRecordRequest;
import Model.Requests.RemoveRecordRequest;
import Utils.Expertise;
import Utils.ImageManager;
import Utils.MyFileLogWriter;
import Utils.Neighberhood;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * A class that represents a restaurant object
 * @author Eddie Kanevsky
 *
 */
public class Restaurant implements Serializable {

	private static Restaurant restaurant = null;
	private ImageManager manager;
	private HashMap<Integer,Cook> cooks;
	private HashMap<Integer,DeliveryPerson> deliveryPersons;
	private HashMap<Integer,Customer> customers;
	private HashMap<Integer,Dish> dishes;
	private HashMap<Integer, Component> components;
	private HashMap<Integer, Order> orders;
	private HashMap<Integer, Delivery> deliveries;
	private HashMap<Integer,DeliveryArea> areas;
	private HashMap<Customer, TreeSet<Order>> orderByCustomer;
	private HashMap<DeliveryArea, HashSet<Order>> orderByDeliveryArea;
	private HashSet<Customer> blackList;
	private HashMap<String,TreeSet<AddRecordRequest>> addRecordHistory; //keeps track of added records history
	private HashMap<String,TreeSet<RemoveRecordRequest>> removeRecordHistory; //keeps track of removed records history

	// hashmap to allow us to work on each customer by his username
	private HashMap<String, Customer> usersList;

	// used to protect ID counters after each serialization
	protected int runningCooks;
	protected int runningDelipersons;
	protected int runningCustomers;
	protected int runningComponents;
	protected int runningDishes;
	protected int runningOrders;
	protected int runningDeliveries;
	protected int runningAreas;



	private boolean firstRun;

	public static Restaurant getInstance()
	{
		if(restaurant == null)
			restaurant = new Restaurant();
		return restaurant;
	}

	private Restaurant() {
		if(!loadDatabase("Rest.ser")) {
			manager = ImageManager.getInstance();
			cooks = new HashMap<>();
			deliveryPersons = new HashMap<>();
			customers = new HashMap<>();
			dishes = new HashMap<>();
			components = new HashMap<>();
			orders = new HashMap<>();
			deliveries = new HashMap<>();
			areas = new HashMap<>();
			blackList = new HashSet<>();
			orderByCustomer = new HashMap<>();
			orderByDeliveryArea = new HashMap<>();
			usersList = new HashMap<>();
			addRecordHistory = new HashMap<>();
			removeRecordHistory = new HashMap<>();
		}
	}

	//region Getters/Setters
	public HashMap<Integer,Cook> getCooks() {
		return cooks;
	}
	public void setCooks(HashMap<Integer,Cook> cooks) {
		this.cooks = cooks;
	}
	public HashMap<Integer,DeliveryPerson> getDeliveryPersons() {
		return deliveryPersons;
	}
	public void setDeliveryPersons(HashMap<Integer,DeliveryPerson> deliveryPersons) {
		this.deliveryPersons = deliveryPersons;
	}
	public HashMap<Integer,Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(HashMap<Integer,Customer> customers) {
		this.customers = customers;
	}
	public HashMap<Integer,Dish> getDishes() {
		return dishes;
	}
	public void setDishes(HashMap<Integer,Dish> dishes) {
		this.dishes = dishes;
	}
	public HashMap<Integer, Component> getComponents() {
		return components;
	}
	public void setComponenets(HashMap<Integer, Component> components) {
		this.components = components;
	}
	public HashMap<Integer,Order> getOrders() {
		return orders;
	}
	public void setOrders(HashMap<Integer,Order> orders) {
		this.orders = orders;
	}
	public HashMap<Integer,Delivery> getDeliveries() {
		return deliveries;
	}
	public void setDeliveries(HashMap<Integer,Delivery> deliveries) {
		this.deliveries = deliveries;
	}
	public HashMap<Integer,DeliveryArea> getAreas() {
		return areas;
	}
	public void setAreas(HashMap<Integer,DeliveryArea> areas) {
		this.areas = areas;
	}
	public HashMap<Customer, TreeSet<Order>> getOrderByCustomer() {
		return orderByCustomer;
	}
	public void setOrderByCustomer(HashMap<Customer, TreeSet<Order>> orderByCustomer) {
		this.orderByCustomer = orderByCustomer;
	}
	public HashMap<DeliveryArea, HashSet<Order>> getOrderByDeliveryArea() {
		return orderByDeliveryArea;
	}
	public void setOrderByDeliveryArea(HashMap<DeliveryArea, HashSet<Order>> orderByDeliveryArea) {
		this.orderByDeliveryArea = orderByDeliveryArea;
	}
	public HashMap<String, TreeSet<AddRecordRequest>> getAddRecordHistory() {
		return addRecordHistory;
	}
	public HashMap<String, TreeSet<RemoveRecordRequest>> getRemoveRecordHistory() {
		return removeRecordHistory;
	}
	public HashMap<String, Customer> getUsersList() {
		return usersList;
	}
	public HashSet<Customer> getBlacklist() {
		return blackList;
	}
	public boolean getFirstRun()
	{
		return firstRun;
	}
	public void setFirstRun(boolean firstRun)
	{
		this.firstRun = firstRun;
	}
	//endregion
	//region Add/Remove/Find x
	/**
	 * Tries to add a new cook to the restaurant
	 * @param cook
	 * the cook
	 * @return
	 * Success / Failure of the insertion
	 */
	public boolean addCook(Cook cook) {
		if(cook == null || getCooks().containsValue(cook))
			return false;

		return getCooks().put(cook.getId(),cook) == null;
	}

	/**
	 * Tries to add a new delivery person to the restaurant
	 * @param dp
	 * the delivery person
	 * @param da
	 * the area of the delivery person
	 * @return
	 * Success / Failure of the insertion
	 */
	public boolean addDeliveryPerson(DeliveryPerson dp, DeliveryArea da) {
		if(dp == null || getDeliveryPersons().containsValue(dp) || !getAreas().containsValue(da))
			return false;

		return getDeliveryPersons().put(dp.getId(),dp) == null && da.addDelPerson(dp);
	}

	/**
	 * Tries to add a new customer to the restaurant
	 * @param cust
	 * the customer
	 * @return
	 * Success / Failure of the insertion
	 */
	public boolean addCustomer(Customer cust) {
		if (cust == null || getCustomers().containsValue(cust))
			return false;
		getUsersList().put(cust.getUsername(),cust);
		return getCustomers().put(cust.getId(), cust) == null;
	}

	/**
	 * Tries to add a new component to the restaurant
	 * @param comp
	 * the component
	 * @return
	 * Success / Failure of the insertion
	 */
	public boolean addComponent(Component comp) {
		if(comp == null || getComponents().containsKey(comp.getId()))
			return false;

		return getComponents().put(comp.getId(), comp) ==null;
	}

	/**
	 * Tries to add a new dish to the restaurant
	 * @param dish
	 * the cook
	 * @return
	 * Success / Failure of the insertion
	 */
	public boolean addDish(Dish dish) {
		if(dish == null || getDishes().containsKey(dish.getId()))
			return false;
		for(Component c : dish.getComponents()) {
			if(!getComponents().containsKey(c.getId()))
				return false;
		}

		return getDishes().put(dish.getId(), dish) ==null;
	}

	/**
	 * Tries to add a new order to the restaurant
	 * @param order
	 * the order
	 * @return
	 * Success / Failure of the insertion
	 */
	public boolean addOrder(Order order) {
		try {
			if(order == null || getOrders().containsKey(order.getId()))
				return false;
			if(order.getCustomer() == null || !getCustomers().containsKey(order.getCustomer().getId()))
				return false;
			if(getBlacklist().contains(order.getCustomer())) {
				throw new IllegalCustomerException();
			}
			for(Dish d : order.getDishes()){
				if(!getDishes().containsKey(d.getId()))
					return false;
				for(Component c: d.getComponents()) {
					if(order.getCustomer().isSensitiveToGluten() && c.isHasGluten()) {
						throw new SensitiveException(order.getCustomer().getFirstName() + " " +order.getCustomer().getLastName(), d.getDishName());
					}
					else if(order.getCustomer().isSensitiveToLactose() && c.isHasLactose()) {
						throw new SensitiveException(order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName(), d.getDishName());
					}
				}
			}
			return getOrders().put(order.getId(), order) == null;
		}catch(SensitiveException | IllegalCustomerException e) {
			System.out.println(e.getMessage());
			System.out.println("Failed to add order " + Order.getIdCounter());
			return false;
		}
	}

	/**
	 * Tries to add a new cook to the restaurant
	 * @param delivery
	 * the delivery
	 * @return
	 * Success / Failure of the insertion
	 */
	public boolean addDelivery(Delivery delivery) {
		if(delivery == null || getDeliveries().containsValue(delivery) || !getDeliveryPersons().containsValue(delivery.getDeliveryPerson()))
			return false;
		if(delivery instanceof RegularDelivery)
		{
			try {
				RegularDelivery rDelivery = (RegularDelivery)delivery;
				if(rDelivery.getOrders().size() == 1)
					throw new ConvertToExpressException();
				for(Order o : rDelivery.getOrders()){
					if(!getOrders().containsValue(o))
						return false;
					o.setDelivery(delivery);
					TreeSet<Order> ordersOfCustomer = getOrderByCustomer().get(o.getCustomer());
					if(ordersOfCustomer == null)
						ordersOfCustomer = new TreeSet<>();
					ordersOfCustomer.add(o);
					getOrderByCustomer().put(o.getCustomer(), ordersOfCustomer);
				}
			}
			catch(ConvertToExpressException e)
			{
				MyFileLogWriter.println(e.getMessage());
				ExpressDelivery eDelivery = new ExpressDelivery(delivery.getDeliveryPerson(), delivery.getArea(), delivery.isDelivered(),
						((RegularDelivery) delivery).getOrders().first(), delivery.getDeliveryDate());
				eDelivery.getArea().addDelivery(eDelivery);
				TreeSet<Order> ordersOfCustomer = getOrderByCustomer().get(eDelivery.getOrder().getCustomer());
				if(ordersOfCustomer == null)
					ordersOfCustomer = new TreeSet<>();
				ordersOfCustomer.add(eDelivery.getOrder());
				getOrderByCustomer().put(eDelivery.getOrder().getCustomer(), ordersOfCustomer);
				return getDeliveries().put(eDelivery.getId(),eDelivery) == null;
			}
			return delivery.getArea().addDelivery(delivery) && getDeliveries().put(delivery.getId(), delivery) == null;
		}

		if(delivery instanceof ExpressDelivery)
		{
			ExpressDelivery eDelivery = (ExpressDelivery)delivery;
			if(!getOrders().containsValue(eDelivery.getOrder()))
				return false;
			TreeSet<Order> ordersOfCustomer = getOrderByCustomer().get(eDelivery.getOrder().getCustomer());
			if(ordersOfCustomer == null)
				ordersOfCustomer = new TreeSet<>();
			ordersOfCustomer.add(eDelivery.getOrder());
			getOrderByCustomer().put(eDelivery.getOrder().getCustomer(), ordersOfCustomer);

			return delivery.getArea().addDelivery(eDelivery) && getDeliveries().put(eDelivery.getId(),eDelivery) == null;
		}
		return false;
	}

	/**
	 * Tries to add a new delivery area to the restaurant
	 * @param da
	 * the delivery area
	 * @return
	 * Success / Failure of the insertion
	 */
	public boolean addDeliveryArea(DeliveryArea da) {
		if(da == null || getAreas().containsValue(da))
			return false;
		return getAreas().put(da.getId(), da) == null;
	}

	/**
	 * Tries to remove a cook from the restaurant
	 * @param cook
	 * the cook
	 * @return
	 * Success / Failure of the removal
	 */
	public boolean removeCook(Cook cook) {
		if(cook == null || !getCooks().containsValue(cook))
			return false;
		return getCooks().remove(cook.getId(), cook);
	}

	/**
	 * Tries to remove a delivery person from the restaurant
	 * @param dp
	 * the delivery person
	 * @return
	 * Success / Failure of the removal
	 */
	public boolean removeDeliveryPerson(DeliveryPerson dp) {
		if(dp == null || !getDeliveryPersons().containsValue(dp))
			return false;
		for(Delivery d : getDeliveries().values()) {
			if(d.getDeliveryPerson().equals(dp)) {
				d.setDeliveryPerson(null);
			}
		}
		return getDeliveryPersons().remove(dp.getId(), dp) && dp.getArea().removeDelPerson(dp);
	}

	/**
	 * Tries to remove a customer from the restaurant
	 * @param cust
	 * the customer
	 * @return
	 * Success / Failure of the removal
	 */
	public boolean removeCustomer(Customer cust) {
		if(cust == null || !getCustomers().containsValue(cust))
			return false;
		getOrderByCustomer().remove(cust);
		return getCustomers().remove(cust.getId(), cust);
	}

	/**
	 * Tries to remove a dish from the restaurant
	 * @param dish
	 * the dish
	 * @return
	 * Success / Failure of the removal
	 */
	public boolean removeDish(Dish dish) {
		if(dish == null || !getDishes().containsValue(dish))
			return false;
		for(Delivery d : getDeliveries().values()) {
			if(!d.isDelivered()) {
				if(d instanceof RegularDelivery)
				{
					RegularDelivery rD = (RegularDelivery)d;
					for(Order o : rD.getOrders()) {
						if(o.getDishes().contains(dish) && !o.removeDish(dish))
							return false;
					}
				}
				if(d instanceof ExpressDelivery)
				{
					ExpressDelivery rD = (ExpressDelivery)d;
					if(rD.getOrder().getDishes().contains(dish) && rD.getOrder().removeDish(dish)) {
							return false;
					}
				}
			}
		}
		return getDishes().remove(dish.getId(),dish);
	}

	/**
	 * Tries to remove a component from the restaurant
	 * @param comp
	 * the component
	 * @return
	 * Success / Failure of the removal
	 */
	public boolean removeComponent(Component comp) {
		if(comp == null || !getComponents().containsValue(comp))
			return false;
		for(Dish d : getDishes().values().stream().toList()) {
			try {
				if(d.getComponents().contains(comp)) {
					d.removeComponent(comp);
				}
			}
			catch(NoComponentsException e)
			{
				MyFileLogWriter.println(e.getMessage());
				try{
					RemoveRecordRequest request = new RemoveRecordRequest(d);
					request.saveRequest();
				}catch(Exception E){

				}
			}
		}
		return getComponents().remove(comp.getId(),comp);
	}

	/**
	 * Tries to remove an order from the restaurant
	 * @param order
	 * the order
	 * @return
	 * Success / Failure of the removal
	 */
	public boolean removeOrder(Order order) {
		if (order == null || !getOrders().containsKey(order.getId()))
			return false;
		if (getOrders().remove(order.getId()) != null) {
			if (order.getDelivery() instanceof RegularDelivery) {
				RegularDelivery rd = (RegularDelivery) order.getDelivery();
				return rd.removeOrder(order);
			} else {
				ExpressDelivery ed = (ExpressDelivery) order.getDelivery();
				if(ed != null)
					ed.setOrder(null);
				return true;
			}
		}
		return false;
	}

	/**
	 * Tries to remove a delivery from the restaurant
	 * @param delivery
	 * the delivery
	 * @return
	 * Success / Failure of the removal
	 */
	public boolean removeDelivery(Delivery delivery) {
		if(delivery == null || !getDeliveries().containsValue(delivery))
		{
			return false;
		}
		if(delivery instanceof RegularDelivery)
		{
			RegularDelivery rD = (RegularDelivery) delivery;
			for(Order o : rD.getOrders()) {
				o.setDelivery(null);
			}
		}
		if(delivery instanceof ExpressDelivery)
		{
			ExpressDelivery eD = (ExpressDelivery) delivery;
			eD.getOrder().setDelivery(null);
		}
		return getDeliveries().remove(delivery.getId(),delivery) && delivery.getArea().removeDelivery(delivery);
	}

	/**
	 * Tries to remove a delivery area from the restaurant nd replace it with existing one
	 * @param oldArea
	 * the area to remove
	 * @return
	 * @param newArea
	 * the area to be replaced with
	 * Success / Failure of the removal
	 */
	public boolean removeDeliveryArea(DeliveryArea oldArea, DeliveryArea newArea) {
		if(oldArea == null || newArea == null || !getAreas().containsValue(oldArea) || !getAreas().containsValue(newArea))
			return false;
		for(Neighberhood n : oldArea.getNeighberhoods()) {
			if(!newArea.getNeighberhoods().contains(n))
				newArea.addNeiberhood(n);
		}
		for (Delivery d : oldArea.getDeliveries().values()) {
			newArea.addDelivery(d);
		}
		for (DeliveryPerson dp : oldArea.getDelPersons().values()) {
			newArea.addDelPerson(dp);
		}
		for(DeliveryPerson dp : oldArea.getDelPersons().values()) {
			dp.setArea(newArea);
		}
		for(Delivery d : oldArea.getDeliveries().values()) {
			d.setArea(newArea);
		}
		return getAreas().remove(oldArea.getId(), oldArea);
	}

	/**
	 * Finds a cook by the given id
	 * @param id
	 * the id to search with
	 * @return
	 * the (if found) cook (null otherwise)
	 */
	public Cook getRealCook(int id) {
		return getCooks().get(id);
	}

	/**
	 * Finds a delivery person by the given id
	 * @param id
	 * the id to search with
	 * @return
	 * the (if found) delivery person (null otherwise)
	 */
	public DeliveryPerson getRealDeliveryPerson(int id) {
		return getDeliveryPersons().get(id);
	}

	/**
	 * Finds a customer by the given id
	 * @param id
	 * the id to search with
	 * @return
	 * the (if found) customer (null otherwise)
	 */
	public Customer getRealCustomer(int id) {
		return getCustomers().get(id);
	}

	/**
	 * Finds a dish by the given id
	 * @param id
	 * the id to search with
	 * @return
	 * the (if found) dish (null otherwise)
	 */
	public Dish getRealDish(int id) {
		return getDishes().get(id);
	}

	/**
	 * Finds a component by the given id
	 * @param id
	 * the id to search with
	 * @return
	 * the (if found) component (null otherwise)
	 */
	public Component getRealComponent(int id) {
		return getComponents().get(id);
	}

	/**
	 * Finds an order by the given id
	 * @param id
	 * the id to search with
	 * @return
	 * the (if found) order (null otherwise)
	 */
	public Order getRealOrder(int id) {
		return getOrders().get(id);
	}

	/**
	 * Finds a delivery by the given id
	 * @param id
	 * the id to search with
	 * @return
	 * the (if found) delivery (null otherwise)
	 */
	public Delivery getRealDelivery(int id) {
		return getDeliveries().get(id);
	}

	/**
	 * Finds a delivery area by the given id
	 * @param id
	 * the id to search with
	 * @return
	 * the (if found) delivery area (null otherwise)
	 */
	public DeliveryArea getRealDeliveryArea(int id) {
		return getAreas().get(id);
	}


	@SuppressWarnings("unchecked")
	// Daniel's attempt at fixing the query so it works with a hashmap customer structure. The original method is below this one
	public Collection<Dish> getReleventDishList(Customer c){
		HashMap<Integer,Dish> dishList = new HashMap<>();
		if(!c.isSensitiveToGluten() && !c.isSensitiveToLactose())
			return (Collection<Dish>) getDishes().values();
		for(Dish d : getDishes().values()) {
			boolean isValid = true;
			for(Component comp : d.getComponents()) {
				if(c.isSensitiveToGluten() && c.isSensitiveToLactose()) {
					if(comp.isHasGluten() || comp.isHasLactose()) {
						isValid = false;
						break;
					}
				}
				else if(c.isSensitiveToGluten() && comp.isHasGluten()) {
					isValid = false;
					break;
				}
				else if(c.isSensitiveToLactose() && comp.isHasLactose()) {
					isValid = false;
					break;
				}
			}
			if(isValid)
				dishList.put(d.getId() , d);
		}
		return dishList.values();
	}
/* original method
	public Collection<Dish> getReleventDishList(Customer c){
		HashMap<Integer,Dish> dishList = new HashMap<>();
		if(!c.isSensitiveToGluten() && !c.isSensitiveToLactose())
			return (Collection<Dish>) getDishes();
		for(Dish d : getDishes()) {
			boolean isValid = true;
			for(Component comp : d.getComponenets()) {
				if(c.isSensitiveToGluten() && c.isSensitiveToLactose()) {
					if(comp.isHasGluten() || comp.isHasLactose()) {
						isValid = false;
						break;
					}
				}
				else if(c.isSensitiveToGluten() && comp.isHasGluten()) {
					isValid = false;
					break;
				}
				else if(c.isSensitiveToLactose() && comp.isHasLactose()) {
					isValid = false;
					break;
				}
			}
			if(isValid)
				dishList.add(d);
		}
		return dishList;
	}
 */

	/* unused method
	public void deliver(Delivery d) {
		d.setDelivered(true);
		for(Order o : d.getOrders()) {
			MyFileLogWriter.println(o+" had reached to Customer "+o.getCustomer());
		}
	}

	 */

	public HashMap<Integer,Cook> getCooksByExpertise(Expertise e){
		HashMap<Integer,Cook> cooks = new HashMap<>();
		for(Cook c : getCooks().values()) {
			if(c.getExpert().equals(e))
				cooks.put(c.getId(),c);
		}
		return cooks;
	}

	/**
	 * Adds a customer to a blacklist
	 * @param c
	 * the customer
	 * @return
	 * Success / Failure of the insertion
	 */
	public boolean addCustomerToBlackList(Customer c)
	{
		if(c!=null)
			return blackList.add(c);
		return false;
	}
	//endregion
	//region Queries
	/**
	 * Finds all of the deliveries done by a specific delivery person
	 * in specific month
	 * @param dp
	 * the delivery person
	 * @param month
	 * the wanted month
	 * @return
	 * the ordered set of done deliveries
	 */
	public TreeSet<Delivery> getDeliveriesByPerson(DeliveryPerson dp, int month)
	{
		Comparator<Delivery> comp = (d1,d2)->{
			int result1 = d1.getDeliveryDate().getMonth().compareTo(d2.getDeliveryDate().getMonth());
			if(result1==0)
			{
				int result2 = ((Integer)d1.getDeliveryDate().getDayOfMonth()).compareTo(d2.getDeliveryDate().getDayOfMonth());
				if(result2 == 0)
					return ((Integer)d2.getId()).compareTo(d1.getId());
				return result2;
			}
			return result1;
		};
		TreeSet<Delivery> sortByMonth = new TreeSet<>(comp);
		sortByMonth.addAll(deliveries.values());
		TreeSet<Delivery> query = new TreeSet<>(comp);
		Iterator<Delivery> st = sortByMonth.iterator();
		Delivery st_d = null, ed_d = null;
		boolean flag = false;
		while(st.hasNext() && !flag)
		{
			st_d = st.next();
			if(st_d.getDeliveryDate().getMonthValue() == month)
				flag = !flag;
		}
		Iterator<Delivery> ed = st;
		if(!flag)
			return query;
		flag = false;
		ed_d = st_d;
		while(ed.hasNext() && !flag)
		{
			if(ed_d.getDeliveryDate().getMonthValue() != month)
			{
				flag = !flag;
				break;
			}
			ed_d = ed.next();
		}
		TreeSet<Delivery> range = new TreeSet<>();
		if(st_d != null && ed_d != null)
			range = (TreeSet<Delivery>) sortByMonth.subSet(st_d, ed_d);
		else if (st_d != null)
			range = (TreeSet<Delivery>) sortByMonth.tailSet(st_d);
		for(Delivery d : range)
		{
			if(d.getDeliveryPerson().equals(dp))
				query.add(d);
		}
		return query;
	}

	/**
	 * Finds the amount of done regular and express deliveries during the last year
	 * @return
	 * map with amount of reg. deliveries and exp. deliveries
	 */
	public HashMap<String,Integer> getNumberOfDeliveriesPerType()
	{
		String key1 = "Regular delivery", key2 = "Express delivery";
		int currentYear = LocalDate.now().getYear();
		HashMap<String, Integer> query = new HashMap<>();
		query.put(key1, 0);
		query.put(key2, 0);

		Comparator<Delivery> comp = (d1, d2) -> d1.getDeliveryDate().compareTo(d2.getDeliveryDate());
		TreeSet<Delivery> tree = new TreeSet<>(comp);
		tree.addAll(deliveries.values());
		Iterator<Delivery> st = tree.iterator();
		boolean found = false;
		Delivery st_d = null;
		while(st.hasNext() && !found) {
			st_d = st.next();
			found = st_d.getDeliveryDate().getYear()==currentYear;
		}
		TreeSet<Delivery> currentYearQuery = (TreeSet<Delivery>) tree.tailSet(st_d);
		for (Delivery d : currentYearQuery) {
			if(d instanceof RegularDelivery)
				query.put(key1, query.get(key1)+1);
			if(d instanceof ExpressDelivery)
				query.put(key2, query.get(key2)+1);
		}
		return query;
	}

	/**
	 * Calculates the revenue of the Express deliveries
	 * @return
	 * the calculated revenue
	 */
	public double revenueFromExpressDeliveries()
	{
		double revenue = 0.0;
		double firstExpressD = 30.0;
		HashSet<ExpressDelivery> exp_d = new HashSet<>();
		for(Delivery d : deliveries.values())
			if(d instanceof ExpressDelivery)
				exp_d.add((ExpressDelivery) d);
		HashSet<Customer> exp_c = new HashSet<>();
		for(ExpressDelivery d : exp_d)
		{
			if(!exp_c.contains(d.getOrder().getCustomer()))
			{
				exp_c.add(d.getOrder().getCustomer());
				revenue += firstExpressD;
			}
			revenue += d.getPostage();
		}
		return revenue;
	}

	/**
	 * Ranks the components by used popularity in the dishes
	 * @return
	 * the ranked list of components (without components that do not appear in any dish)
	 */
	public LinkedList<Component> getPopularComponents()
	{
		HashMap<Component, Integer> comps = new HashMap<>();
		for(Dish d: dishes.values())
		{
			for(Component c: d.getComponents())
			{
				if(comps.get(c) == null)
					comps.put(c, 1);
				else
					comps.put(c, comps.get(c)+1);
			}


		}
		Comparator<Component> comp = (c1, c2) -> {
			int res = comps.get(c2).compareTo(comps.get(c1));
			if(res == 0)
				return ((Integer)c2.getId()).compareTo(c1.getId());
			return res;
		};
		LinkedList<Component> query = new LinkedList<>(comps.keySet());
		Collections.sort(query,comp);
		return query;
	}

	/**
	 * Ranks the dishes served by profit relation
	 * @return
	 * the ranked dish set
	 */
	public TreeSet<Dish> getProfitRelation()
	{
		Comparator<Dish> comp = (d1,d2)->{
			Double rel1 = d1.getPrice()/d1.getTimeToMake();
			Double rel2 = d2.getPrice()/d2.getTimeToMake();
			int result = rel2.compareTo(rel1);
			if(result == 0)
				return ((Integer)d2.getId()).compareTo(d1.getId());
			return result;
		};
		TreeSet<Dish> query = new TreeSet<>(comp);
		query.addAll(dishes.values());
		return query;
	}

	/**
	 * An AI machine that decides how to create deliveries
	 * @param dp
	 * the delivery person
	 * @param da
	 * the delivery area
	 * @param orders
	 * the orders
	 * @return
	 * an ordered set of decided deliveries
	 */
	public TreeSet<Delivery> createAIMacine(DeliveryPerson dp, DeliveryArea da, TreeSet<Order> orders)
	{
		TreeSet<Delivery> AIdeliveries = new TreeSet<>((d1,d2)->{
			int result = d1.getClass().getSimpleName().compareTo(d2.getClass().getSimpleName());
			if(result == 0)
				return ((Integer)d1.getId()).compareTo(d2.getId());
			return result;
		});
		if(orders.size() <= 2)
		{
			for (Order order : orders)
				AIdeliveries.add(new ExpressDelivery(dp,da, false, order, LocalDate.now()));
		}
		else
		{
			TreeSet<Order> regOrders = new TreeSet<>();
			try
			{
				for (Order order : orders) {
					if(order.getCustomer().isSensitiveToGluten() || order.getCustomer().isSensitiveToLactose())
						AIdeliveries.add(new ExpressDelivery(dp, da, false, order, LocalDate.now()));
					else
						regOrders.add(order);
				}
				if(!regOrders.isEmpty())
				{
					if(regOrders.size() == 1)
						throw new ConvertToExpressException();
					RegularDelivery regularOrders = new RegularDelivery(regOrders, dp, da, false, LocalDate.now());
					AIdeliveries.add(regularOrders);
				}
			}
			catch(ConvertToExpressException e)
			{
				MyFileLogWriter.println(e.getMessage());
				ExpressDelivery delivery = new ExpressDelivery(dp, da, false, regOrders.first(), LocalDate.now());
				AIdeliveries.add(delivery);
			}
		}
		return AIdeliveries;
	}
	//endregion
	/// region Save and Load
	public boolean saveDatabase(String filename) {
		try
		{
			//Saving of object in a file
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			runningCustomers 	= Customer.getIdCounter();
			runningCooks 		= Cook.getIdCounter();
			runningDelipersons 	= DeliveryPerson.getIdCounter();
			runningComponents 	= Component.getIdCounter();
			runningDishes 		= Dish.getIdCounter();
			runningOrders 		= Order.getIdCounter();
			runningDeliveries 	= Delivery.getIdCounter();
			runningAreas 		= DeliveryArea.getIdCounter();
			out.writeObject(this);

			out.close();
			file.close();
			return true;

		}

		catch(IOException ex)
		{
			ex.printStackTrace();
			System.out.println(ex.getClass());
			System.out.println("Was unable to save");
			return false;
		}
	}
	public boolean loadDatabase(String filename) {
		Restaurant res;
		try
		{
			// Reading the object from a file
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);
			// Method for deserialization of object
			res = (Restaurant)in.readObject();

			in.close();
			file.close();
			if(res!=null)
			{
				System.out.println("Database has been loaded successfully ");
				this.areas = res.areas;
				this.blackList = res.blackList;
				this.components = res.components;
				this.cooks = res.cooks;
				this.customers = res.customers;
				this.deliveries = res.deliveries;
				this.deliveryPersons = res.deliveryPersons;
				this.dishes = res.dishes;
				this.orders = res.orders;
				this.orderByCustomer = res.orderByCustomer;
				this.orderByDeliveryArea = res.orderByDeliveryArea;
				this.usersList = res.usersList;
				this.addRecordHistory = res.addRecordHistory;
				this.removeRecordHistory = res.removeRecordHistory;
				Customer.setIdCounter(res.runningCustomers);
				Cook.setIdCounter(res.runningCooks);
				DeliveryPerson.setIdCounter(res.runningDelipersons);
				DeliveryArea.setIdCounter(res.runningAreas);
				Component.setIdCounter(res.runningComponents);
				Dish.setIdCounter(res.runningDishes);
				Order.setIdCounter(res.runningOrders);
				Delivery.setIdCounter(res.runningDeliveries);
				this.manager = res.manager;
				setFirstRun(false);
				return true;
			}
			throw new Exception();
		}
		catch(FileNotFoundException ex)
		{
			System.err.println("\'Ser\' file not found");
			System.out.println("Failed to load the database");
			setFirstRun(true);
			return false;
		}
		catch(IOException ex)
		{
			System.err.println("IOException is caught");
			System.out.println("Failed to load the database");
			setFirstRun(true);
			return false;
		}

		catch(ClassNotFoundException ex)
		{
			System.err.println("ClassNotFoundException is caught");
			System.out.println("Failed to load the database");
			setFirstRun(true);
			return false;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println("Failed to load the database");
			setFirstRun(true);
			return false;
		}
	}
	/// endregion
}