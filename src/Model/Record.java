package Model;

import Model.Requests.RecordRequest;

import java.io.Serializable;

/**
 * A superclass for all the record types within the database
 * is used in {@link RecordRequest} class
 */
public abstract class Record implements Serializable {
    private int id;
    public abstract String description();
    public Record(int id){
        setId(id);
    }
    public Integer getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
}
