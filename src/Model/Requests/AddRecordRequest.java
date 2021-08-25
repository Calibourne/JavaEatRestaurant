package Model.Requests;

import Model.*;
import Model.Record;
import Model.Exceptions.ConvertToExpressException;
import Utils.*;
import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

public class AddRecordRequest extends RecordRequest {
    private boolean toBlacklist;
    public AddRecordRequest(Record record, Object... args) throws Exception {
        if(Arrays.stream(args).anyMatch(a->a.equals(null)))
            throw new Exception();
        if (record instanceof DeliveryArea) {
            this.record = new DeliveryArea(
                    (String) args[0],
                    (HashSet<Neighberhood>) args[1],
                    (int) args[2]
            );
        }
        if (record instanceof Cook) {
            this.record = new Cook(
                    (String) args[0],
                    (String) args[1],
                    (LocalDate) args[2],
                    (Gender) args[3],
                    (Expertise) args[4],
                    (boolean) args[5]
            );
        }
        if (record instanceof DeliveryPerson) {
            this.record = new DeliveryPerson(
                    (String) args[0],
                    (String) args[1],
                    (LocalDate) args[2],
                    (Gender) args[3],
                    (Vehicle) args[4],
                    (DeliveryArea) args[5]
            );
        }
        if (record instanceof Customer) {
            if(args.length>1) {
                this.record = new Customer(
                        (String) args[0],
                        (String) args[1],
                        (LocalDate) args[2],
                        (Gender) args[3],
                        (Neighberhood) args[4],
                        (boolean) args[5],
                        (boolean) args[6]
                );
                if(args.length==8)
                setCustomerImage((Image) args[7]);
            }
            else{
                toBlacklist = true;
                this.record = record;
            }
        }
        if (record instanceof Component) {
            this.record = new Component(
                    (String) args[0],
                    (boolean) args[1],
                    (boolean) args[2],
                    (double) args[3]
            );
        }
        if (record instanceof Dish) {
            this.record = new Dish(
                    (String) args[0],
                    (DishType) args[1],
                    (ArrayList<Component>) args[2],
                    (int) args[3]
            );
        }
        if (record instanceof Order) {
            if(args.length == 3) {
                this.record = new Order(
                        (Customer) args[0],
                        (ArrayList<Dish>) args[1],
                        (Delivery) args[2]
                );
            }
            else {
                this.record = new Order(
                        (Customer) args[0],
                        (ArrayList<Dish>) args[1],
                        null
                );
            }
        }
        if (record instanceof Delivery) {
            if (record instanceof RegularDelivery) {
                try {
                    if (((TreeSet<Order>) args[0]).size() == 1)
                        throw new ConvertToExpressException();
                    this.record = new RegularDelivery(
                            (TreeSet<Order>) args[0],
                            (DeliveryPerson) args[1],
                            (DeliveryArea) args[2],
                            (boolean) args[3],
                            (LocalDate) args[4]
                    );
                } catch (ConvertToExpressException e) {
                    this.record = new ExpressDelivery(
                            (DeliveryPerson) args[1],
                            (DeliveryArea) args[2],
                            (boolean) args[3],
                            ((TreeSet<Order>) args[0]).first(),
                            (LocalDate) args[4]
                    );
                }
            }
            else {
                this.record = new ExpressDelivery(
                        (DeliveryPerson) args[0],
                        (DeliveryArea) args[1],
                        (boolean) args[2],
                        (Order) args[3],
                        (double) args[4],
                        (LocalDate) args[5]
                );
            }
        }
    }

    @Override
    public boolean saveRequest() {
        Restaurant restaurant = Restaurant.getInstance();
        TreeSet<AddRecordRequest> ts;
        boolean toReturn = true;
        try {
            if(record instanceof Customer){
                if(!toBlacklist) {
                    toReturn = restaurant.addCustomer((Customer) record);
                }
                else{
                    toReturn = restaurant.addCustomerToBlackList((Customer) record);
                    ts = restaurant.getAddRecordHistory().get("Blacklist");
                    if(ts == null) {
                        ts = new TreeSet<>();
                    }
                    toReturn = toReturn && ts.add(this);
                    restaurant.getAddRecordHistory().put("Blacklist", ts);
                    return toReturn;
                }
            }
            if(record instanceof Cook){
                toReturn = restaurant.addCook((Cook) record);
            }
            if(record instanceof DeliveryArea) {
                toReturn = restaurant.addDeliveryArea((DeliveryArea) record);
            }
            if(record instanceof DeliveryPerson){
                DeliveryArea da = ((DeliveryPerson) record).getArea();
                toReturn = restaurant.addDeliveryPerson((DeliveryPerson) record, da);
            }
            if(record instanceof Component){
                toReturn = restaurant.addComponent((Component) record);
            }
            if(record instanceof Dish){
                toReturn = restaurant.addDish((Dish) record);
            }
            if(record instanceof Order){
                toReturn = restaurant.addOrder((Order) record);
            }
            if(record instanceof Delivery){
                toReturn = restaurant.addDelivery((Delivery) record);
            }
        } catch(RuntimeException e){
            System.out.println(e.getMessage());
            return false;
        }
        ts = restaurant.getAddRecordHistory().get(record.getClass().getSimpleName());
        if(ts==null)
            ts = new TreeSet<>();
        toReturn = toReturn && ts.add(this);
        restaurant.getAddRecordHistory().put(record.getClass().getSimpleName(),ts);
        return toReturn;
    }

    @Override
    protected void setCustomerImage(Image img){
        if (!((Customer) record).getProfileImgName().equals("Default"))
            super.setCustomerImage(img);
    }

    @Override
    public String toString() {
        if(!toBlacklist)
            return String.format("Added %s, %s", record, super.toString());
        else
            return String.format("Blacklisted %s, %s",record, super.toString());
    }
}
