package View.newElements;

import Model.Customer;
import Model.Record;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A class for displaying customer's profile picture
 */
public class imageListCell<T extends Record> extends ListCell<T> {
    @Override
    protected void updateItem(T item, boolean isEmpty) {
        super.updateItem(item, isEmpty);
        setGraphic(null);
        setText(null);
        if(item!=null){
            if(item instanceof Customer) {
                Customer c = (Customer) item;
                Image img = SwingFXUtils.toFXImage(c.getProfileImg(true), null);
                ImageView imageView = new ImageView(img);
                imageView.setFitHeight(0);
                imageView.setFitWidth(0);
                setGraphic(imageView);
                setText(c.toString());
            }
        }
    }
}

