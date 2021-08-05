package View.QueryPages;

import Model.Customer;
import Model.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

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

        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }

    // currently not working, need to fix
    public void buttonPressed(ActionEvent event){
        try {
            Restaurant rest = Restaurant.getInstance();

            if(rest.getCustomers().containsValue(customer_combobox.getSelectionModel().getSelectedItem())){
                //query_result.setText(rest.getRelevantDishList(customer_combobox.getSelectionModel().getSelectedItem());
                query_result.getItems().addAll(rest.getReleventDishList(customer_combobox.getSelectionModel().getSelectedItem()).toString());
                System.out.println("test");
            }


        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }

    }

}