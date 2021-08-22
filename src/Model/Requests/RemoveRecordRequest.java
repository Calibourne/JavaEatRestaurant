package Model.Requests;

import Model.*;
import Model.Record;
import Model.Exceptions.ConvertToExpressException;
import Utils.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

public class RemoveRecordRequest extends RecordRequest {
    private DeliveryArea newArea;
    public RemoveRecordRequest(Record record, Object... args) throws Exception {
        if(Arrays.stream(args).anyMatch(a->a.equals(null)))
            throw new Exception();
        this.record = record;
        if (record instanceof DeliveryArea) {
            newArea = (DeliveryArea) args[0];
        }
    }

    @Override
    public boolean saveRequest() {
        Restaurant restaurant = Restaurant.getInstance();
        TreeSet<RemoveRecordRequest> ts;
        boolean toReturn = true;
        try {
            if(record instanceof Customer){
                    toReturn = restaurant.removeCustomer((Customer) record);
            }
            if(record instanceof Cook){
                toReturn = restaurant.removeCook((Cook) record);
            }
            if(record instanceof DeliveryArea){
                toReturn = restaurant.removeDeliveryArea((DeliveryArea) record, newArea);
            }
            if(record instanceof DeliveryPerson){
                toReturn = restaurant.removeDeliveryPerson((DeliveryPerson) record);
            }
            if(record instanceof Component){
                toReturn = restaurant.removeComponent((Component) record);
            }
            if(record instanceof Dish){
                toReturn = restaurant.removeDish((Dish) record);
            }
            if(record instanceof Order){
                toReturn = restaurant.removeOrder((Order) record);
            }
            if(record instanceof Delivery){
                toReturn = restaurant.removeDelivery((Delivery) record);
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
