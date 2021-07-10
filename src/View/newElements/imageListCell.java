package View.newElements;

import Model.Customer;
import Model.Person;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

/**
 * A class for displaying customer's profile picture
 */
public class imageListCell extends ListCell<Person> {
    @Override
    protected void updateItem(Person item, boolean isEmpty) {
        super.updateItem(item, isEmpty);
        setGraphic(null);
        setText(null);
        if(item!=null){
            if(item instanceof Customer) {
                Customer c = (Customer) item;
                Image img = new Image("");
                ImageView imageView = new ImageView(img);
                setGraphic(imageView);
                setText(c.toString());
            }
        }
    }
}

