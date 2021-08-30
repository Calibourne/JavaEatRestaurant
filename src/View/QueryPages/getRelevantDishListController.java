package View.QueryPages;

import Model.Customer;
import Model.Dish;
import Model.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

/**
 * This is a controller class for the getRelevantDishList query page
 * @author Daniel Sharon
 */

public class getRelevantDishListController {

    @FXML
    private Button query_button;

    @FXML
    private AnchorPane pnlQueries;

    @FXML
    private ComboBox<Customer> customer_combobox;

    @FXML
    private ListView<String> query_result;

    @FXML
    private void initialize(){
        try{
            Restaurant rest = Restaurant.getInstance();
            customer_combobox.getItems().addAll(rest.getCustomers().values());
            customer_combobox.valueProperty().addListener((opt, oldValue, newValue)->{
                if(!newValue.equals(oldValue)){
                    query_result.getItems().clear();
                    if(rest.getCustomers().containsValue(newValue)) {
                        List<String> res = rest.getReleventDishList(newValue).stream().map(Dish::toString).toList();
                        res.forEach(r -> query_result.getItems().add(r));
                    }
                }
            });

        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }

}