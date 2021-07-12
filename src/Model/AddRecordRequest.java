package Model;

import java.util.TreeSet;

public class AddRecordRequest extends RecordRequest{
    public AddRecordRequest(Record record) {
        super(record);
    }

    @Override
    public boolean saveRequest() {
        Restaurant restaurant = Restaurant.getInstance();
        TreeSet<AddRecordRequest> ts;
        boolean toReturn = true;
        if(record instanceof DeliveryArea){
            try{
                toReturn = restaurant.addDeliveryArea((DeliveryArea) record);

            }catch(RuntimeException e){
                System.out.println(e.getMessage());
                return false;
            }
        }
        ts = restaurant.getAddRecordHistory().get(record.getClass().getSimpleName());
        if(ts==null)
            ts = new TreeSet<>();
        toReturn = toReturn && ts.add(this);
        restaurant.getAddRecordHistory().put(record.getClass().getSimpleName(),ts);
        return toReturn;
    }
}
