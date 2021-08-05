package View.QueryPages;

import Model.Order;
import Model.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

public class orderWaitingTimeController {

    @FXML
    private AnchorPane pnlQueries;

    @FXML
    private ComboBox<Order> order_combobox;

    @FXML
    private void initialize(){
        try{
            Restaurant rest = Restaurant.getInstance();
            order_combobox.getItems().addAll(rest.getOrders().values());

        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }
}