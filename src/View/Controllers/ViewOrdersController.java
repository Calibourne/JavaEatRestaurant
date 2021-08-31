package View.Controllers;

import Model.Order;
import Model.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This is a controller class for the View Orders page
 * @author Daniel Sharon
 */

public class ViewOrdersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pnlQueries;

    @FXML
    private ListView<String> query_result;

    @FXML
    void initialize() {
        assert pnlQueries != null : "fx:id=\"pnlQueries\" was not injected: check your FXML file 'ViewOrders.fxml'.";
        assert query_result != null : "fx:id=\"query_result\" was not injected: check your FXML file 'ViewOrders.fxml'.";

        try {

            //Reset the result list each time a query is called upon
            if(!(query_result.getItems().isEmpty())){

                query_result.getItems().clear();
            }

            Restaurant rest = Restaurant.getInstance();

            if(!(rest.getOrders().isEmpty())){

                List<String> results = rest.getOrders().values().stream().map(Order::toString).toList();
                results.forEach(r->query_result.getItems().add(r));

            }
            else{
                if(!(query_result.getItems().isEmpty())){

                    query_result.getItems().clear();
                }
                query_result.getItems().add("There are no orders to show at the moment.");

            }


        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }
}