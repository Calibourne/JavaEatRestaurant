package Model.Requests;

import Model.*;
import Model.Record;

import java.util.TreeSet;

public class AddRecordRequest extends RecordRequest {
    private boolean toBlacklist;
    public AddRecordRequest(Record record) {
        super(record);
    }
    public AddRecordRequest(Customer customer, boolean toBlacklist){
        super(customer);
        this.toBlacklist = toBlacklist;
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
}
