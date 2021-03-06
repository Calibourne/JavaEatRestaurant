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

/**
 * A record request that is responsible for editing records to the database
 * @author Eddie Kanevsky
 */
public class EditRecordRequest extends RecordRequest{
    final transient private Object[] args;
    transient Image img;

    /**
     * Initializes an EditRecordRequest according to record type
     * @param record
     * the record we want to edit
     * @param args
     * the arguments needed in order to edit the record
     * @throws IllegalArgumentException
     * whenever some/all the arguments are equal to null
     */
    public EditRecordRequest(Record record, Object... args) throws IllegalArgumentException{
        this.args = args;
        if(Arrays.stream(args).anyMatch(o->o==null))
            throw new IllegalArgumentException();
        this.record = record;
    }

    /**
     * attempts to edit the record,
     * and pushes itself to edit request history
     * @return
     * success / failure of the procedure
     */
    @Override
    public boolean saveRequest() {
        TreeSet<EditRecordRequest> ts;
        ts = Restaurant.getInstance().getEditRecordHistory().get(record.getClass().getSimpleName());
        if(ts==null)
            ts = new TreeSet<>();
        if(record instanceof DeliveryArea){
            ((DeliveryArea) record).setAreaName((String) args[0]);
            Set<Neighberhood> selected = (Set<Neighberhood>) args[1],
                    current = ((DeliveryArea) record).getNeighberhoods();
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
            ((Person) record).setFirstName((String) args[0]);
            ((Person) record).setLastName((String) args[1]);
            ((Person) record).setGender((Gender) args[2]);
            ((Person) record).setBirthDay((LocalDate) args[3]);
            if(record instanceof Cook){
                ((Cook) record).setExpert((Expertise) args[4]);
                ((Cook) record).setChef((boolean) args[5]);
            }
            if(record instanceof Customer){
                ((Customer) record).setNeighberhood((Neighberhood) args[4]);
                ((Customer) record).setSensitiveToGluten((Boolean) args[5]);
                ((Customer) record).setSensitiveToLactose((Boolean) args[6]);
                //TODO Add change password attribute in edit customer fxml
                setCustomerImage((Image) args[7]);
                if(args.length==9) {
                    ((Customer) record).setPassword((String) args[8]);
                }
                if(args.length == 10){
                    Restaurant.getInstance().getUsersList().remove(((Customer) record).getUsername());
                    ((Customer) record).setUsername((String) args[9]);
                    Restaurant.getInstance().getUsersList().put((String) args[9], (Customer) record);
                }
            }
            if(record instanceof DeliveryPerson){
                ((DeliveryPerson) record).setVehicle((Vehicle) args[4]);
                ((DeliveryPerson) record).setArea((DeliveryArea) args[5]);
            }
        }
        if(record instanceof Component){
            ((Component) record).setComponentName((String) args[0]);
            ((Component) record).setPrice((Double) args[1]);
            ((Component) record).setHasGluten((Boolean) args[2]);
            ((Component) record).setHasLactose((Boolean) args[3]);
        }
        if(record instanceof Dish){
            ((Dish) record).setDishName((String) args[0]);
            ((Dish) record).setType((DishType) args[1]);
            ((Dish) record).setTimeToMake((Integer) args[2]);
            Map<Component, Long> current = ((Dish) record).getComponents().stream()
                    .collect(Collectors.groupingBy(Function.identity(),Collectors.counting())),
                    selected = ((List<Component>) args[3]).stream()
                            .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
            Set<Component> intersect = selected.keySet().stream().filter(e-> current.containsKey(e)).collect(Collectors.toSet());
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
        }
        if(record instanceof Order){
            ((Order) record).setCustomer((Customer) args[0]);
            Map<Dish, Long> current = ((Order) record).getDishes().stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())),
                    selected = ((List<Dish>) args[1]).stream()
                            .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
            Set<Dish> intersect = selected.keySet().stream().filter(e-> current.containsKey(e)).collect(Collectors.toSet());
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
                ((Order) record).setDelivery((Delivery) args[2]);
            }
        }
        if(record instanceof Delivery){
            DeliveryPerson dp = (DeliveryPerson) args[0];
            ((Delivery) record).setDeliveryPerson(dp);
            ((Delivery) record).setArea(dp.getArea());
            ((Delivery) record).setDeliveryDate((LocalDate) args[1]);
            ((Delivery) record).setDelivered((Boolean) args[2]);
            if(record instanceof RegularDelivery){
                Set<Order> current = ((RegularDelivery) record).getOrders(),
                        selected = (Set<Order>) args[3];
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
                ((ExpressDelivery) record).setOrder((Order) args[3]);
                ((ExpressDelivery) record).setPostage((Double) args[4]);
            }
        }
        ts.add(this);
        Restaurant.getInstance().getEditRecordHistory().put(record.getClass().getSimpleName(), ts);
        return true;
    }

    /**
     * adapts the setImage to editRecordRequest
     * @param img
     * the chosen image
     */
    @Override
    protected void setCustomerImage(Image img) {
        Image current = SwingFXUtils.toFXImage(((Customer)record).getProfileImg(false),null);
        if (!ImageManager.isImageEqual(current, img))
            super.setCustomerImage(img);
    }

    @Override
    public String toString() {
            return String.format("Edited %s, %s", record, super.toString());
    }
}