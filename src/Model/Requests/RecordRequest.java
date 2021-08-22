package Model.Requests;

import Model.Customer;
import Model.Record;
import Utils.ImageManager;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * This class will be responsible for adding/removing/editing records from the database
 * It's used as a wrapper for the management purposes
 */
public abstract class RecordRequest implements Serializable, Comparable<RecordRequest> {
    protected final LocalDate dateOfRequest;
    protected final LocalTime timeOfRequest;
    protected Record record;
    public RecordRequest(){
        this.dateOfRequest = LocalDate.now();
        this.timeOfRequest = LocalTime.now();
    }

    public abstract boolean saveRequest();

    @Override
    public int compareTo(RecordRequest o) {
        return record.getId().compareTo(o.record.getId());
    }

    public Record getRecord() {
        return record;
    }

    protected void setCustomerImage(Image img){
        Customer c = (Customer)record;
        ImageManager manager = ImageManager.getInstance();
        int id = c.getId();
        String saveS = "Customer" + id;
        manager.saveProfileImage(img, saveS);
        c.setProfileImg("Customer" + id);
    }

    public LocalDate getDateOfRequest() {
        return dateOfRequest;
    }

    public LocalTime getTimeOfRequest() {
        return timeOfRequest;
    }

    @Override
    public String toString() {
        String date = dateOfRequest.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String time = timeOfRequest.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return String.format("at %s at %s", date,time);
    }
}
