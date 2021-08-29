package Model.Requests;
import Model.*;
import Model.Record;
import Model.Exceptions.NoComponentsException;
import Utils.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EditRecordRequest extends RecordRequest{
    private String[] argsNames;
    private Object[] args;
    private Object[] oldArgs;
    public EditRecordRequest(Record record, Object... args) throws IllegalArgumentException{
        this.args = args;
        oldArgs = new Object[args.length];
        argsNames = new String[args.length];
        if(Arrays.stream(args).anyMatch(o -> o.equals(null)))
            throw new IllegalArgumentException();
        this.record = record;
    }
    @Override
    public boolean saveRequest() {
        if(record instanceof DeliveryArea){
            argsNames[0] = "Area name";
            oldArgs[0] = ((DeliveryArea) record).getAreaName();
            ((DeliveryArea) record).setAreaName((String) args[0]);
            Set<Neighberhood> selected = (Set<Neighberhood>) args[1],
                    current = ((DeliveryArea) record).getNeighberhoods();
            argsNames[1] = "Neighbourhoods";
            oldArgs[1] = ((DeliveryArea) record).getNeighberhoods();
            Set<Neighberhood> intersect = selected.stream().filter(e->current.contains(e)).collect(Collectors.toSet());
            Set<Neighberhood> unite = Stream.concat(selected.stream(), current.stream()).collect(Collectors.toSet());
            Set<Neighberhood> notIntersect = unite.stream().filter(e->!intersect.contains(e)).collect(Collectors.toSet());
            notIntersect.forEach(e->{
                if(selected.contains(e))
                    ((DeliveryArea) record).addNeiberhood(e);
                else if(current.contains(e))
                    ((DeliveryArea) record).removeNeighberhood(e);
            });
        }
        if(record instanceof Person){
            argsNames[0] = "First name";
            oldArgs[0] = ((Person) record).getFirstName();
            ((Person) record).setFirstName((String) args[0]);
            argsNames[1] = "Last name";
            oldArgs[1] = ((Person) record).getLastName();
            ((Person) record).setLastName((String) args[1]);
            argsNames[2] = "Gender";
            oldArgs[2] = ((Person) record).getGender();
            ((Person) record).setGender((Gender) args[2]);
            argsNames[3] = "Birthdate";
            oldArgs[3] = ((Person) record).getBirthDay();
            ((Person) record).setBirthDay((LocalDate) args[3]);
            if(record instanceof Cook){
                argsNames[4] = "Expertise";
                oldArgs[4] = ((Cook) record).getExpert();
                ((Cook) record).setExpert((Expertise) args[4]);
                argsNames[5] = "Is chef";
                oldArgs[5] = ((Cook) record).isChef();
                ((Cook) record).setChef((boolean) args[5]);
            }
            if(record instanceof Customer){
                argsNames[4] = "Neighbourhood";
                oldArgs[4] = ((Customer) record).getNeighberhood();
                ((Customer) record).setNeighberhood((Neighberhood) args[4]);
                argsNames[5] = "Is sensitive to gluten";
                oldArgs[5] = ((Customer) record).isSensitiveToGluten();
                ((Customer) record).setSensitiveToGluten((Boolean) args[5]);
                argsNames[6] = "Is sensitive to lactose";
                oldArgs[6] = ((Customer) record).isSensitiveToLactose();
                ((Customer) record).setSensitiveToLactose((Boolean) args[6]);
                //TODO Add change password attribute in edit customer fxml
                argsNames[7] = "Image";
                oldArgs[7] = SwingFXUtils.toFXImage(((Customer) record).getProfileImg(false), null);
                setCustomerImage((Image) args[7]);
                if(args.length==9) {
                    oldArgs[8] = "Password";
                    oldArgs[8] = ((Customer) record).getPassword();
                    ((Customer) record).setPassword((String) args[8]);
                }
                if(args.length == 10){
                    argsNames[9] = "Username";
                    oldArgs[9] = ((Customer) record).getUsername();
                    Restaurant.getInstance().getUsersList().remove((String) oldArgs[9]);
                    ((Customer) record).setUsername((String) args[9]);
                    Restaurant.getInstance().getUsersList().put((String) args[9], (Customer) record);
                }
            }
            if(record instanceof DeliveryPerson){
                argsNames[4] = "Vehicle";
                oldArgs[4] = ((DeliveryPerson) record).getVehicle();
                ((DeliveryPerson) record).setVehicle((Vehicle) args[4]);
                argsNames[5] = "Delivery area";
                oldArgs[5] = ((DeliveryPerson) record).getArea();
                ((DeliveryPerson) record).setArea((DeliveryArea) args[5]);
            }
        }
        if(record instanceof Component){
            argsNames[0] = "Ingredient name";
            oldArgs[0] = ((Component) record).getComponentName();
            ((Component) record).setComponentName((String) args[0]);
            argsNames[1] = "Ingredient price";
            oldArgs[1] = ((Component) record).getPrice();
            ((Component) record).setPrice((Double) args[1]);
            argsNames[2] = "Contains gluten";
            oldArgs[2] = ((Component) record).isHasGluten();
            ((Component) record).setHasGluten((Boolean) args[2]);
            argsNames[3] = "Contains lactose";
            oldArgs[3] = ((Component) record).isHasLactose();
            ((Component) record).setHasLactose((Boolean) args[3]);
        }
        if(record instanceof Dish){
            argsNames[0] = "Dish name";
            oldArgs[0] = ((Dish) record).getDishName();
            ((Dish) record).setDishName((String) args[0]);
            argsNames[1] = "Dish type";
            oldArgs[1] = ((Dish) record).getType();
            ((Dish) record).setType((DishType) args[1]);
            argsNames[2] = "Time to prepare";
            oldArgs[2] = ((Dish) record).getTimeToMake();
            ((Dish) record).setTimeToMake((Integer) args[2]);
            Map<Component, Long> current = ((Dish) record).getComponents().stream()
                    .collect(Collectors.groupingBy(Function.identity(),Collectors.counting())),
                    selected = ((List<Component>) args[3]).stream()
                            .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
            argsNames[3] = "Ingredients";
            oldArgs[3] = ((Dish) record).getComponents().stream()
                    .sorted(Comparator.comparing(Component::getId)).toList();
            args[3] = ((List<Component>) args[3]).stream()
                    .sorted(Comparator.comparing(Component::getId)).toList();
            Set<Component> intersect = selected.keySet().stream().filter(e->current.keySet().contains(e)).collect(Collectors.toSet());
            Set<Component> unite = Stream.concat(current.keySet().stream(),selected.keySet().stream()).collect(Collectors.toSet());
            Set<Component> notIntersect = unite.stream().filter(e->!intersect.contains(e)).collect(Collectors.toSet());
            intersect.forEach(e->{
                long currentOccurrences = current.get(e),
                        selectedOccurrences = selected.get(e);
                if (currentOccurrences > selectedOccurrences) {
                    long diff = currentOccurrences - selectedOccurrences;
                    for (int i = 0; i < diff; i++) {
                        try {
                            ((Dish) record).removeComponent(e);
                        } catch (NoComponentsException ex) {
                            ex.getMessage();
                        }
                    }
                }
                if (currentOccurrences < selectedOccurrences) {
                    long diff = selectedOccurrences - currentOccurrences;
                    for (int i = 0; i < diff; i++) {
                        ((Dish) record).addComponent(e);
                    }
                }
            });
            notIntersect.forEach(e->{
                if (current.containsKey(e)) {
                    for (int i = 0; i < current.get(e); i++) {
                        try {
                            ((Dish) record).removeComponent(e);
                        } catch (NoComponentsException ex) {
                            ex.getMessage();
                        }
                    }
                }
                if (selected.containsKey(e)) {
                    for (int i = 0; i < selected.get(e); i++) {
                        ((Dish) record).addComponent(e);
                    }
                }
            });
            if(((Dish) record).getComponents().size()==0){
                Restaurant rest = Restaurant.getInstance();
                try {
                    RemoveRecordRequest request = new RemoveRecordRequest(record);
                    request.saveRequest();
                    rest.saveDatabase("Rest.ser");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        if(record instanceof Order){
            argsNames[0] = "Customer";
            oldArgs[0] = ((Order) record).getCustomer();
            ((Order) record).setCustomer((Customer) args[0]);
            Map<Dish, Long> current = ((Order) record).getDishes().stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())),
                    selected = ((List<Dish>) args[1]).stream()
                            .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
            argsNames[1] = "Dishes";
            oldArgs[1] = ((Order) record).getDishes().stream()
                    .sorted(Comparator.comparing(Dish::getId)).toList();
            args[1] = ((List<Dish>) args[1]).stream()
                    .sorted(Comparator.comparing(Dish::getId)).toList();
            Set<Dish> intersect = selected.keySet().stream().filter(e->current.keySet().contains(e)).collect(Collectors.toSet());
            Set<Dish> unite = Stream.concat(current.keySet().stream(),selected.keySet().stream()).collect(Collectors.toSet());
            Set<Dish> notIntersect = unite.stream().filter(e->!intersect.contains(e)).collect(Collectors.toSet());
            intersect.forEach(e->{
                long currentOccurrences = current.get(e),
                        selectedOccurrences = selected.get(e);
                if (currentOccurrences > selectedOccurrences) {
                    long diff = currentOccurrences - selectedOccurrences;
                    for (int i = 0; i < diff; i++) {
                        ((Order) record).removeDish(e);
                    }
                }
                if (currentOccurrences < selectedOccurrences) {
                    long diff = selectedOccurrences - currentOccurrences;
                    for (int i = 0; i < diff; i++) {
                        ((Order) record).addDish(e);
                    }
                }
            });
            notIntersect.forEach(e->{
                if (current.containsKey(e)) {
                    for (int i = 0; i < current.get(e); i++) {
                            ((Order) record).removeDish(e);
                    }
                }
                if (selected.containsKey(e)) {
                    for (int i = 0; i < selected.get(e); i++) {
                        ((Order) record).addDish(e);
                    }
                }
            });
            if(args.length==3) {
                argsNames[2] = "Delivery";
                oldArgs[2] = ((Order) record).getDelivery();
                ((Order) record).setDelivery((Delivery) args[2]);
            }
        }
        if(record instanceof Delivery){
            argsNames[0] = "Delivery person";
            oldArgs[0] = ((Delivery) record).getDeliveryPerson();
            DeliveryPerson dp = (DeliveryPerson) args[0];
            ((Delivery) record).setDeliveryPerson(dp);
            ((Delivery) record).setArea(dp.getArea());
            argsNames[1] = "Date of delivery";
            oldArgs[1] = ((Delivery) record).getDeliveryDate();
            ((Delivery) record).setDeliveryDate((LocalDate) args[1]);
            argsNames[2] = "Is delivered";
            oldArgs[2] = ((Delivery) record).isDelivered();
            ((Delivery) record).setDelivered((Boolean) args[2]);
            if(record instanceof RegularDelivery){
                Set<Order> current = ((RegularDelivery) record).getOrders(),
                        selected = (Set<Order>) args[3];
                argsNames[3] = "Orders";
                oldArgs[3] = ((RegularDelivery) record).getOrders();
                Set<Order> intersect = selected.stream().filter(e->current.contains(e)).collect(Collectors.toSet());
                Set<Order> unite = Stream.concat(selected.stream(), current.stream()).collect(Collectors.toSet());
                Set<Order> notIntersect = unite.stream().filter(e->!intersect.contains(e)).collect(Collectors.toSet());
                notIntersect.forEach(e->{
                    if(selected.contains(e))
                        ((RegularDelivery) record).addOrder(e);
                    else if(current.contains(e))
                        ((RegularDelivery) record).removeOrder(e);
                });
            }
            if(record instanceof ExpressDelivery){
                argsNames[3] = "Order";
                oldArgs[3] = ((ExpressDelivery) record).getOrder();
                ((ExpressDelivery) record).setOrder((Order) args[3]);
                argsNames[4] = "Postage";
                oldArgs[4] = ((ExpressDelivery) record).getPostage();
                ((ExpressDelivery) record).setPostage((Double) args[4]);
            }
        }
        return true;
    }

    @Override
    protected void setCustomerImage(Image img) {
        Image current = SwingFXUtils.toFXImage(((Customer)record).getProfileImg(false),null);
        if (!ImageManager.isImageEqual(current, img))
            super.setCustomerImage(img);
    }

    @Override
    public String toString() {
        String edited = String.format("Edited %s: \n", record);
        for (int i = 0; i < args.length; i++) {
            if(!(args[i] instanceof Collection<?>) && !(args[i] instanceof Image)){
                if(!argsNames[i].equals("Password")) {
                    edited += (args[i].equals(oldArgs[i])) ? "" :
                            String.format("%s: %s -> %s\n", argsNames[i], oldArgs[i], args[i]);
                }
                else{
                    edited += (args[i].equals(oldArgs[i])) ? "" :
                            String.format("%s changed\n", argsNames[i]);
                }
            }
            else{
                if(args[i] instanceof Collection<?>){
                    Collection<?> olist, nlist;
                    olist = ((Collection<Object>) oldArgs[i]);

                    nlist = ((Collection<Object>) args[i]);
                    edited += (olist.equals(nlist)) ? "" :
                            String.format("%s changed\n", argsNames[i]);
                }
                if(args[i] instanceof Image){
                    edited += (ImageManager.isImageEqual((Image)args[i],((Image)oldArgs[i]))) ? "" :
                            String.format("%s changed\n", argsNames[i]);
                }
            }
        }
        return String.format("%s%s",edited,super.toString());
    }
}
