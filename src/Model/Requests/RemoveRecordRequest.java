package Model.Requests;

import Model.Record;
import Model.*;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * A record request that is responsible for removing records from the database
 * @author Eddie Kanevsky
 */
public class RemoveRecordRequest extends RecordRequest {
    private DeliveryArea newArea;

    /**
     * Initializes an RemoveRecordRequest according to record type
     * @param record
     * the record to remove
     * @param args
     * additional argument for deliveryArea
     * @throws IllegalArgumentException
     */
    public RemoveRecordRequest(Record record, Object... args) throws IllegalArgumentException{
        if(Arrays.stream(args).anyMatch(o->o==null))
            throw new IllegalArgumentException();
        this.record = record;
        if (record instanceof DeliveryArea) {
            newArea = (DeliveryArea) args[0];
        }
    }

    /**
     * attempts to remove the record from the database,
     * and pushes itself to remove request history
     * @return
     * success / failure of the procedure
     */
    @Override
    public boolean saveRequest() {
        Restaurant restaurant = Restaurant.getInstance();
        TreeSet<RemoveRecordRequest> ts;
        boolean toReturn = true;
        try {
            if(record instanceof Customer){
                Customer c = restaurant.getRealCustomer(record.getId());
                toReturn = restaurant.removeCustomer(c);
            }
            if(record instanceof Cook){
                Cook c = restaurant.getRealCook(record.getId());
                toReturn = restaurant.removeCook(c);
            }
            if(record instanceof DeliveryArea){
                DeliveryArea da = restaurant.getRealDeliveryArea(record.getId());
                toReturn = restaurant.removeDeliveryArea(da, newArea);
            }
            if(record instanceof DeliveryPerson){
                DeliveryPerson dp = restaurant.getRealDeliveryPerson(record.getId());
                toReturn = restaurant.removeDeliveryPerson(dp);
            }
            if(record instanceof Component){
                Component c = restaurant.getRealComponent(record.getId());
                toReturn = restaurant.removeComponent(c);
            }
            if(record instanceof Dish){
                Dish d = restaurant.getRealDish(record.getId());
                toReturn = restaurant.removeDish(d);
            }
            if(record instanceof Order){
                Order o = restaurant.getRealOrder(record.getId());
                toReturn = restaurant.removeOrder(o);
            }
            if(record instanceof Delivery){
                Delivery d = restaurant.getRealDelivery(record.getId());
                toReturn = restaurant.removeDelivery(d);
            }
        } catch(RuntimeException e){
            System.out.println(e.getMessage());
            return false;
        }
        ts = restaurant.getRemoveRecordHistory().get(record.getClass().getSimpleName());
        if(ts==null)
            ts = new TreeSet<>();
        toReturn = toReturn && ts.add(this);
        restaurant.getRemoveRecordHistory().put(record.getClass().getSimpleName(),ts);
        return toReturn;
    }

    @Override
    public String toString() {
            return String.format("Removed %s, %s", record, super.toString());
    }
}
