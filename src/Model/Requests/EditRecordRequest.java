package Model.Requests;
import Model.DeliveryArea;
import Model.Record;
import Utils.Neighberhood;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EditRecordRequest extends RecordRequest{
    private Object[] args;
    public EditRecordRequest(Record record, Object... args) throws IllegalArgumentException{
        this.args = args;
        if(Arrays.stream(args).anyMatch(o -> o.equals(null)))
            throw new IllegalArgumentException();
        this.record = record;
    }
    @Override
    public boolean saveRequest() {
        if(record instanceof DeliveryArea){
            ((DeliveryArea) record).setAreaName((String) args[0]);
            Set<Neighberhood> notintersect = ((List<Neighberhood>) args[1]).stream().collect(Collectors.toSet());
            Set<Neighberhood> set = ((List<Neighberhood>) args[1]).stream().collect(Collectors.toSet());
            Set<Neighberhood> intersect = ((List<Neighberhood>) args[1]).stream().collect(Collectors.toSet());
            intersect.retainAll(((DeliveryArea) record).getNeighberhoods());
            notintersect = notintersect.stream().filter(e->!intersect.contains(e)).collect(Collectors.toSet());
            System.out.println("--- intersects ---");
            intersect.forEach(System.out::println);
            System.out.println("--- --- ---");
            System.out.println("--- not intersects ---");
            notintersect.forEach(System.out::println);
            System.out.println("--- --- ---");
            System.out.println("--- old ---");
            ((DeliveryArea) record).getNeighberhoods().forEach(System.out::println);
            System.out.println("--- --- ---");
            Set<Neighberhood> finalNotintersect = notintersect;
            notintersect.forEach(e->{
                if(finalNotintersect.contains(e))
                    ((DeliveryArea) record).addNeiberhood(e);
                else
                    ((DeliveryArea) record).removeNeighberhood(e);
            });
            System.out.println("--- new ---");
            ((DeliveryArea) record).getNeighberhoods().forEach(System.out::println);
            System.out.println("--- --- ---");

        }
        return true;
    }
}
