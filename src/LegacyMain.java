
import autopilot.OutputDocument;
import autopilot.Section;
import Model.*;
import Utils.CSVExporter;
import Utils.DishType;
import Utils.Expertise;
import Utils.Gender;
import Utils.MyFileLogWriter;
import Utils.Neighberhood;
import Utils.Vehicle;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;



public class LegacyMain {

	private static Restaurant rest = Restaurant.getInstance();

	private static OutputDocument document = new OutputDocument();
	private static Map<String,Command> commands = new HashMap<>();
	private static Map<String,Section> sections = new HashMap<>();
	private static final String OUTPUT_FILE = "output.txt";

	static {

		Section space = document.nextSection();
		Section finish = document.nextSection();

		Section deliverAreaSection = document.nextSection();
		Section customerSection = document.nextSection();
		Section cookSection = document.nextSection();
		Section deliveryPersonSection = document.nextSection();
		Section componentSection = document.nextSection();
		Section dishSection = document.nextSection();
		Section orderSection = document.nextSection();
		Section deliverySection = document.nextSection();
		Section addToBlackList = document.nextSection();

		Section removeDeliveryArea = document.nextSection();
		Section removeCustomer = document.nextSection();
		Section removeCook = document.nextSection();
		Section removeDeliveryPerson = document.nextSection();
		Section removeDelivery = document.nextSection();
		Section removeComponent = document.nextSection();
		Section removeDish = document.nextSection();
		Section removeOrder = document.nextSection();

		Section getDeliveriesByPerson = document.nextSection();
		Section getNumberOfDeliveriesPerType = document.nextSection();
		Section revenueFromExpressDeliveries = document.nextSection();
		Section getPopularComponents = document.nextSection();
		Section getProfitRelation = document.nextSection();
		Section createAIMacine = document.nextSection();

		sections.put("addDeliverArea",deliverAreaSection);
		sections.put("addCustomer",customerSection);
		sections.put("addCook",cookSection);
		sections.put("addDeliveryPerson",deliveryPersonSection);
		sections.put("addComponent",componentSection);
		sections.put("addDish",dishSection);
		sections.put("addOrder",orderSection);
		sections.put("addDelivery",deliverySection);
		sections.put("addExpressDelivery",deliverySection);

		sections.put("addToBlackList", addToBlackList);
		
		sections.put("removeDeliverArea",removeDeliveryArea);
		sections.put("removeCustomer",removeCustomer);
		sections.put("removeCook",removeCook);
		sections.put("removeDeliveryPerson",removeDeliveryPerson);
		sections.put("removeDelivery",removeDelivery);
		sections.put("removeComponent",removeComponent);
		sections.put("removeDish",removeDish);
		sections.put("removeOrder",removeOrder);
		
		sections.put("getDeliveriesByPerson",getDeliveriesByPerson);
		sections.put("getNumberOfDeliveriesPerType",getNumberOfDeliveriesPerType);
		sections.put("revenueFromExpressDeliveries",revenueFromExpressDeliveries);
		sections.put("getPopularComponents",getPopularComponents);
		sections.put("getProfitRelation", getProfitRelation);
		sections.put("createAIMacine",createAIMacine);
		
		sections.put("space",space);
		sections.put("finish",finish);

		commands.put("space", (section, args) -> {
			MyFileLogWriter.println("");
		});

		commands.put("finish", (section, args) -> {
			MyFileLogWriter.saveLogFile();
		});

		
		commands.put("addDeliverArea", (section, args) -> {
			HashSet<Neighberhood> n = new HashSet<>();
			n.add(Neighberhood.valueOf(args[2]));
			if(!args[3].equals("null"))
				n.add(Neighberhood.valueOf(args[3]));
			if(!args[4].equals("null"))
				n.add(Neighberhood.valueOf(args[4]));
			if(!args[5].equals("null"))
				n.add(Neighberhood.valueOf(args[5]));
			DeliveryArea da = new DeliveryArea(args[0], n, Integer.parseInt(args[1]));
			if(rest.addDeliveryArea(da)) {
				MyFileLogWriter.println("successfully added Delivery Area "+args[0]);
			}
			else
				MyFileLogWriter.println("failed to add Delivery Area "+args[0]);

		});

		
		commands.put("addCustomer", (section, args) -> {
			LocalDate date = LocalDate.of(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
			Customer c = new Customer(args[0], args[1], date, Gender.valueOf(args[5]), Neighberhood.valueOf(args[6]), Boolean.parseBoolean(args[7]), Boolean.parseBoolean(args[8]));
			if(rest.addCustomer(c))
				MyFileLogWriter.println("successfully added Customer "+args[0]+" "+args[1]);
				else
					MyFileLogWriter.println("failed to add Customer "+args[0]+" "+args[1]);
		});

		
		commands.put("addCook", (section, args) -> {
			LocalDate date = LocalDate.of(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
			Cook c = new Cook(args[0], args[1], date, Gender.valueOf(args[5]), Expertise.valueOf(args[6]), Boolean.parseBoolean(args[7]));
			if(rest.addCook(c))
				MyFileLogWriter.println("successfully added Cook "+args[0]+" "+args[1]);
			else
				MyFileLogWriter.println("failed to add Cook "+args[0]+" "+args[1]);
		});

		
		commands.put("addDeliveryPerson", (section, args) -> {
			LocalDate date = LocalDate.of(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
			DeliveryArea da = rest.getRealDeliveryArea(Integer.parseInt(args[7]));
			DeliveryPerson dp = new DeliveryPerson(args[0], args[1], date, Gender.valueOf(args[5]), Vehicle.valueOf(args[6]), da);

			if(rest.addDeliveryPerson(dp, da))
				MyFileLogWriter.println("successfully added Delivery Person "+args[0]+" "+args[1]);
			else
				MyFileLogWriter.println("failed to add Delivery Person "+args[0]+" "+args[1]);
		});

		
		commands.put("addComponent", (section, args) -> {
			Component c = new Component(args[0], Boolean.parseBoolean(args[1]), Boolean.parseBoolean(args[2]), Double.parseDouble(args[3]));
			if(rest.addComponent(c))
				MyFileLogWriter.println("successfully added Component "+args[0]);
			else
				MyFileLogWriter.println("failed to add Component "+args[0]);
		});

		
		commands.put("addDish", (section, args) -> {
			ArrayList<Component> comp = new ArrayList<>();
			comp.add(rest.getRealComponent(Integer.parseInt(args[3])));
			comp.add(rest.getRealComponent(Integer.parseInt(args[4])));
			comp.add(rest.getRealComponent(Integer.parseInt(args[5])));
			comp.add(rest.getRealComponent(Integer.parseInt(args[6])));
			Dish d = new Dish(args[0], DishType.valueOf(args[1]), comp, Integer.parseInt(args[2]));
			if(rest.addDish(d))
				MyFileLogWriter.println("successfully added Dish "+args[0]);
			else
				MyFileLogWriter.println("failed to add Dish "+args[0]);
		});

		
		commands.put("addOrder", (section, args) -> {
			Customer c = rest.getRealCustomer(Integer.parseInt(args[0]));
			ArrayList<Dish> dishes = new ArrayList<>();
			dishes.add(rest.getRealDish(Integer.parseInt(args[1])));
			dishes.add(rest.getRealDish(Integer.parseInt(args[2])));
			dishes.add(rest.getRealDish(Integer.parseInt(args[3])));
			Order o = new Order(c, dishes, null);
			if(rest.addOrder(o))
				MyFileLogWriter.println("successfully added Order "+o.getId());
			else
				MyFileLogWriter.println("failed to add Order "+o.getId());
		});


		commands.put("addDelivery", (section, args) -> {
			DeliveryPerson dp = rest.getRealDeliveryPerson(Integer.parseInt(args[0]));
			DeliveryArea da = rest.getRealDeliveryArea(Integer.parseInt(args[1]));
			TreeSet<Order> orders = new TreeSet<>();
			Order o = rest.getRealOrder(Integer.parseInt(args[3]));
			if(o!=null)
				orders.add(o);
			o = rest.getRealOrder(Integer.parseInt(args[4]));
			if(o!=null)
				orders.add(o);
			o = rest.getRealOrder(Integer.parseInt(args[5]));
			if(o!=null)
				orders.add(o);
			LocalDate deliveredDate = LocalDate.of(Integer.parseInt(args[6]), Integer.parseInt(args[7]), Integer.parseInt(args[8]));
			Delivery d = new RegularDelivery(orders, dp, da, Boolean.parseBoolean(args[2]), deliveredDate);
			if(rest.addDelivery(d))
				MyFileLogWriter.println("successfully added Delivery "+d.getId());
			else
				MyFileLogWriter.println("failed to add Delivery "+d.getId());
		});
		
		commands.put("addExpressDelivery", (section, args) -> {
			DeliveryPerson dp = rest.getRealDeliveryPerson(Integer.parseInt(args[0]));
			DeliveryArea da = rest.getRealDeliveryArea(Integer.parseInt(args[1]));
			Order o = rest.getRealOrder(Integer.parseInt(args[3]));
			double postage = Double.parseDouble(args[4]);
			LocalDate deliveredDate = LocalDate.of(Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]));
			Delivery d = null;
			if( o != null)
				d = new ExpressDelivery(dp, da, Boolean.parseBoolean(args[2]), o,postage, deliveredDate);
			if(rest.addDelivery(d))
				MyFileLogWriter.println("successfully added Delivery "+d.getId());
			else
				MyFileLogWriter.println("failed to add Delivery "+d.getId());
		});
		

		commands.put("addToBlackList", (section, args) -> {
			Customer c = rest.getRealCustomer(Integer.parseInt(args[0]));
			if(rest.addCustomerToBlackList(c))
				MyFileLogWriter.println("successfully added customer to blackList "+c.getId());
			else
				MyFileLogWriter.println("failed to add customer to blackList "+c.getId());
		});
		
		commands.put("getDeliveriesByPerson", (section, args) -> {
			DeliveryPerson dp = rest.getRealDeliveryPerson(Integer.parseInt(args[0]));
			int month = Integer.parseInt(args[1]);
			TreeSet<Delivery> result = rest.getDeliveriesByPerson(dp,month);
			if(result.isEmpty()) {MyFileLogWriter.println("No deliveries by this delivery person in month " + month + "\n");}
			else {
				MyFileLogWriter.println("The deliveries for delivery person " + dp.getId() +" in month " + month +  " is:");
				for(Delivery d: result) {
					MyFileLogWriter.println(d.toString());
				}
				MyFileLogWriter.println("\n");
			}
		});

		commands.put("getNumberOfDeliveriesPerType", (section, args) -> {
			HashMap<String,Integer>  result = rest.getNumberOfDeliveriesPerType();
			if(result.isEmpty()) { MyFileLogWriter.println("Has no deliveries in the system\n");}
			else {	MyFileLogWriter.println("Has " + result.get("Regular delivery") + " regular deliveries and " + result.get("Express delivery") + " express deliveries\n");}
		});
		
		commands.put("revenueFromExpressDeliveries", (section, args) -> {
			double result = rest.revenueFromExpressDeliveries();
			MyFileLogWriter.println("The revenue from express deliveries is: " + result+"\n");
		});

		commands.put("getPopularComponents", (section, args) -> {
			LinkedList<Component> result = rest.getPopularComponents();
			if(result.isEmpty()) { MyFileLogWriter.println("Has no components in the system\n");}
			else {
				MyFileLogWriter.println("The popular component is: " + result.get(0) + "\n");
				MyFileLogWriter.println("The component by populization:");
				for(Component c: result) {
					MyFileLogWriter.println(c.toString());
				}
				MyFileLogWriter.println("\n");
			}
		});
		
		commands.put("getProfitRelation", (section, args) -> {
			TreeSet<Dish> result = rest.getProfitRelation();
			if(result.isEmpty()) { MyFileLogWriter.println("Has no dishs in the system\n");}
			else {
				MyFileLogWriter.println("The Dish with the best relation is: " + result.first()+"\n");
				MyFileLogWriter.println("The dishes by populization:");
				for(Dish d: result) {
					MyFileLogWriter.println(d.toString());
				}
				MyFileLogWriter.println("\n");
			}
		});
		
		commands.put("createAIMacine", (section, args) -> {
			DeliveryPerson dp = rest.getRealDeliveryPerson(Integer.parseInt(args[0]));
			DeliveryArea da = rest.getRealDeliveryArea(Integer.parseInt(args[1]));
			TreeSet<Order> orders = new TreeSet<>();
			Order o = rest.getRealOrder(Integer.parseInt(args[2]));
			if(o!=null)
				orders.add(o);
			o = rest.getRealOrder(Integer.parseInt(args[3]));
			if(o!=null)
				orders.add(o);
			o = rest.getRealOrder(Integer.parseInt(args[4]));
			if(o!=null)
				orders.add(o);
			o = rest.getRealOrder(Integer.parseInt(args[5]));
			if(o!=null)
				orders.add(o);
			TreeSet<Delivery> result = rest.createAIMacine(dp, da, orders);
			if(result.isEmpty()) {MyFileLogWriter.println("Failed in 'create AI machine' method\n");}
			else {
				MyFileLogWriter.println("The machine Decided:");
				for(Delivery d: result) {
					MyFileLogWriter.println(d.toString());
				}
				MyFileLogWriter.println("\n");
			}
		});

		commands.put("removeDeliverArea", (section, args) -> {
			DeliveryArea da = rest.getRealDeliveryArea(Integer.parseInt(args[0]));
			DeliveryArea newDA = rest.getRealDeliveryArea(Integer.parseInt(args[1]));
			if(rest.removeDeliveryArea(da, newDA))
				MyFileLogWriter.println("successfully removed DeliveryArea "+args[0]);
			else
				MyFileLogWriter.println("failed to remove DeliveryArea "+args[0]);
		});

		commands.put("removeComponent", (section, args) -> {
			Component c = rest.getRealComponent(Integer.parseInt(args[0]));
			if(rest.removeComponent(c))
				MyFileLogWriter.println("successfully removed Component "+args[0]);
			else
				MyFileLogWriter.println("failed to remove Component "+args[0]);
		});
		
		commands.put("removeDish", (section, args) -> {
			Dish d = rest.getRealDish(Integer.parseInt(args[0]));
			if(rest.removeDish(d))
				MyFileLogWriter.println("successfully removed Dish "+args[0]);
			else
				MyFileLogWriter.println("failed to remove Dish "+args[0]);
		});
		
		commands.put("removeOrder", (section, args) -> {
			Order o = rest.getRealOrder(Integer.parseInt(args[0]));
			if(rest.removeOrder(o))
				MyFileLogWriter.println("successfully removed Order "+args[0]);
			else
				MyFileLogWriter.println("failed to remove Order "+args[0]);
		});
		
		commands.put("removeCustomer", (section, args) -> {
			Customer c = rest.getRealCustomer(Integer.parseInt(args[0]));
			if(rest.removeCustomer(c))
				MyFileLogWriter.println("successfully removed Customer "+args[0]);
			else
				MyFileLogWriter.println("failed to remove Customer "+args[0]);
		});

		commands.put("removeCook", (section, args) -> {
			Cook c = rest.getRealCook(Integer.parseInt(args[0]));
			if(rest.removeCook(c))
				MyFileLogWriter.println("successfully removed Cook "+args[0]);
			else
				MyFileLogWriter.println("failed to remove Cook "+args[0]);
		});

		commands.put("removeDeliveryPerson", (section, args) -> {
			DeliveryPerson dp = rest.getRealDeliveryPerson(Integer.parseInt(args[0]));
			if(rest.removeDeliveryPerson(dp))
				MyFileLogWriter.println("successfully removed Delivery Person "+args[0]);
			else
				MyFileLogWriter.println("failed to remove Delivery Person "+args[0]);
		});

		commands.put("removeDelivery", (section, args) -> {
			Delivery d = rest.getRealDelivery(Integer.parseInt(args[0]));
			if(rest.removeDelivery(d))
				MyFileLogWriter.println("successfully removed Delivery "+args[0]);
			else
				MyFileLogWriter.println("failed to remove Delivery "+args[0]);
		});




	}



	public static void main(String[] args) throws IOException {
		MyFileLogWriter.initializeMyFileWriter();

		if (rest.getFirstRun()) {
			try {
				List<String[]> input = CSVExporter.importCSV("input.csv");

				for (int i = 1; i < input.size(); i++) {

					//get row
					String[] values = input.get(i);

					if (values.length == 0)
						continue;
					//get command
					String command = values[0];

					//get params
					String[] params = Arrays.copyOfRange(values, 1, values.length);

					//send to func
					try {
						func(command, params);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} finally {
				// Shared s = Shared.getInstance();
				rest.saveDatabase("Rest.ser");
				System.out.println("All commands executed. Please check \"" + OUTPUT_FILE + "\".");
			}
		}
		else {
			System.out.println(rest.getAreas());
		}
	}

	private static void func(String command,String[] args){
		//extract command
		Command c = commands.get(command);

		//check that command exists
		if (c != null){
			//get relevant section
			Section section = sections.get(command);

			//execute
			c.execute(section,args);
		}
	}

	@FunctionalInterface
	private interface Command {
		void execute(Section section,String... args);
	}

}
