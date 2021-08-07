package View.QueryPages;

import Model.Delivery;
import Model.DeliveryPerson;
import Model.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class getDeliveriesByPersonController {

    @FXML
    private Button query_button;

    @FXML
    private AnchorPane pnlQueries;

    @FXML
    private ComboBox<DeliveryPerson> dp_combobox;

    @FXML
    private ComboBox<Integer> month_combobox;

    @FXML
    private ListView<String> query_result;

    @FXML
    private void initialize(){
        Restaurant restaurant = Restaurant.getInstance();

        dp_combobox.getItems().addAll(restaurant.getDeliveryPersons().values());

        month_combobox.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12);

    }

    @FXML
    private void buttonPressed(ActionEvent event){
        try{
            //Reset the result list each time a query is called upon
            if(!(query_result.getItems().isEmpty())){

                query_result.getItems().clear();
            }

            Restaurant restaurant = Restaurant.getInstance();
            DeliveryPerson dp = dp_combobox.getValue();
            Integer month = month_combobox.getValue();

            List<String> results = restaurant.getDeliveriesByPerson(dp, month).stream().toList().stream().map(Delivery::toString).toList();
            results.forEach(r->query_result.getItems().add(r));

            if(results.size() == 0){
                query_result.getItems().add("No relevant deliveries for selected delivery guy and month");
            }



        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }
}