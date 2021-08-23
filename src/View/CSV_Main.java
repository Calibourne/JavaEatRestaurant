package View;

import Model.Requests.AddRecordRequest;
import Model.Requests.RecordRequest;
import Model.Requests.RemoveRecordRequest;
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



public class CSV_Main {

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
            try{
                AddRecordRequest request = new AddRecordRequest(
                        new DeliveryArea(-1),
                        args[0], n, Integer.parseInt(args[1])
                );
                request.saveRequest();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        commands.put("addCustomer", (section, args) -> {
            LocalDate date = LocalDate.of(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
            try{
                AddRecordRequest request = new AddRecordRequest(
                        new Customer(-1),
                        args[0], args[1], date,
                        Gender.valueOf(args[5]),
                        Neighberhood.valueOf(args[6]),
                        Boolean.parseBoolean(args[7]),
                        Boolean.parseBoolean(args[8])
                );
                request.saveRequest();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        commands.put("addCook", (section, args) -> {
            LocalDate date = LocalDate.of(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
            try{
                AddRecordRequest request = new AddRecordRequest(
                        new Cook(-1),
                        args[0], args[1], date,
                        Gender.valueOf(args[5]),
                        Expertise.valueOf(args[6]),
                        Boolean.parseBoolean(args[7])
                );
                request.saveRequest();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        commands.put("addDeliveryPerson", (section, args) -> {
            LocalDate date = LocalDate.of(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
            DeliveryArea da = rest.getRealDeliveryArea(Integer.parseInt(args[7]));
            try{
                AddRecordRequest request = new AddRecordRequest(
                        new DeliveryPerson(-1),
                        args[0],
                        args[1],
                        date,
                        Gender.valueOf(args[5]),
                        Vehicle.valueOf(args[6]),
                        da
                );
                request.saveRequest();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        commands.put("addComponent", (section, args) -> {
            try{
                AddRecordRequest request = new AddRecordRequest(
                        new Component(-1),
                        args[0],
                        Boolean.parseBoolean(args[1]),
                        Boolean.parseBoolean(args[2]),
                        Double.parseDouble(args[3])
                );
                request.saveRequest();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        commands.put("addDish", (section, args) -> {
            ArrayList<Component> comp = new ArrayList<>();
            comp.add(rest.getRealComponent(Integer.parseInt(args[3])));
            comp.add(rest.getRealComponent(Integer.parseInt(args[4])));
            comp.add(rest.getRealComponent(Integer.parseInt(args[5])));
            comp.add(rest.getRealComponent(Integer.parseInt(args[6])));
            try{
                AddRecordRequest request = new AddRecordRequest(
                        new Dish(-1),
                        args[0],
                        DishType.valueOf(args[1]),
                        comp,
                        Integer.parseInt(args[2])
                );
                request.saveRequest();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        commands.put("addOrder", (section, args) -> {
            Customer c = rest.getRealCustomer(Integer.parseInt(args[0]));
            ArrayList<Dish> dishes = new ArrayList<>();
            dishes.add(rest.getRealDish(Integer.parseInt(args[1])));
            dishes.add(rest.getRealDish(Integer.parseInt(args[2])));
            dishes.add(rest.getRealDish(Integer.parseInt(args[3])));
            try{
                AddRecordRequest request = new AddRecordRequest(
                        new Order(-1),
                        c, dishes
                );
                request.saveRequest();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            try{
                AddRecordRequest request = new AddRecordRequest(
                        new RegularDelivery(-1),
                        orders, dp, da,
                        Boolean.parseBoolean(args[2]),
                        deliveredDate
                );
                request.saveRequest();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        commands.put("addExpressDelivery", (section, args) -> {
            DeliveryPerson dp = rest.getRealDeliveryPerson(Integer.parseInt(args[0]));
            DeliveryArea da = rest.getRealDeliveryArea(Integer.parseInt(args[1]));
            Order o = rest.getRealOrder(Integer.parseInt(args[3]));
            double postage = Double.parseDouble(args[4]);
            LocalDate deliveredDate = LocalDate.of(Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]));
            if( o != null) {
                try {
                    AddRecordRequest request = new AddRecordRequest(
                            new ExpressDelivery(-1),
                            dp,
                            da,
                            Boolean.parseBoolean(args[2]),
                            o,
                            postage,
                            deliveredDate
                    );
                    request.saveRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        commands.put("addToBlackList", (section, args) -> {
            Customer c = rest.getRealCustomer(Integer.parseInt(args[0]));
            try {
                AddRecordRequest request = new AddRecordRequest(c);
                request.saveRequest();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            TreeSet<RecordRequest> result = rest.createAIMacine(dp, da, orders);
            if(result.isEmpty()) {MyFileLogWriter.println("Failed in 'create AI machine' method\n");}
            else {
                MyFileLogWriter.println("The machine Decided:");
                for(RecordRequest d: result) {
                    MyFileLogWriter.println(d.toString());
                }
                MyFileLogWriter.println("\n");
            }
        });

        commands.put("removeDeliverArea", (section, args) -> {
            DeliveryArea da;
            DeliveryArea newDA;
            try{
                RemoveRecordRequest request = new RemoveRecordRequest(
                        rest.getRealDeliveryArea(Integer.parseInt(args[0])),
                        rest.getRealDeliveryArea(Integer.parseInt(args[1]))
                );
                request.saveRequest();
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        commands.put("removeComponent", (section, args) -> {
            try{
                RemoveRecordRequest request = new RemoveRecordRequest(
                        rest.getRealComponent(Integer.parseInt(args[0]))
                );
                request.saveRequest();
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        commands.put("removeDish", (section, args) -> {
            try{
                RemoveRecordRequest request = new RemoveRecordRequest(
                        rest.getRealDish(Integer.parseInt(args[0]))
                );
                request.saveRequest();
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        commands.put("removeOrder", (section, args) -> {
            try{
                RemoveRecordRequest request = new RemoveRecordRequest(
                        rest.getRealOrder(Integer.parseInt(args[0]))
                );
                request.saveRequest();
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        commands.put("removeCustomer", (section, args) -> {
            try{
                System.out.println(rest.getRealCustomer(Integer.parseInt(args[0])));
                RemoveRecordRequest request = new RemoveRecordRequest(
                        rest.getRealCustomer(Integer.parseInt(args[0]))
                );
                request.saveRequest();
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        commands.put("removeCook", (section, args) -> {
            try{
                RemoveRecordRequest request = new RemoveRecordRequest(
                        rest.getRealCook(Integer.parseInt(args[0]))
                );
                request.saveRequest();
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        commands.put("removeDeliveryPerson", (section, args) -> {
            try{
                RemoveRecordRequest request = new RemoveRecordRequest(
                        rest.getRealDeliveryPerson(Integer.parseInt(args[0]))
                );
                request.saveRequest();
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        commands.put("removeDelivery", (section, args) -> {
            try{
                RemoveRecordRequest request = new RemoveRecordRequest(
                        rest.getRealDelivery(Integer.parseInt(args[0]))
                );
                request.saveRequest();
            }catch(Exception e){
                e.printStackTrace();
            }
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