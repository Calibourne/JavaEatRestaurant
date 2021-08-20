package View.CustomerStage;

import Model.Customer;
import Model.Order;
import Model.Requests.AddRecordRequest;
import Model.Requests.RecordRequest;
import Model.Restaurant;
import View.Controllers.LoginPageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.CheckListView;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CustomerCartAndHistoryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pnlQueries;

    @FXML
    private Label home_page_header;

    @FXML
    private Tab shopping_cart_button;

    @FXML
    private CheckListView<?> shopping_cart_list;

    @FXML
    private Tab order_history_button;

    @FXML
    private ListView<String> order_history_list;

    @FXML
    void orderHistoryButtonPressed(ActionEvent event) {
        Set<String> ts = restaurant.getAddRecordHistory()
                .get(Order.class.getSimpleName()).stream().filter(r->((Order)r.getRecord()).getCustomer().equals(customer))
                .map(RecordRequest::toString).collect(Collectors.toSet());
        order_history_list.getItems().addAll(ts);
    }

    @FXML
    void shoppingCartButtonPressed(ActionEvent event) {

    }

    Restaurant restaurant;
    private Customer customer;

    @FXML
    void initialize() {
        try {
            restaurant = Restaurant.getInstance();
            assert pnlQueries != null : "fx:id=\"pnlQueries\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";
            assert home_page_header != null : "fx:id=\"home_page_header\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";
            assert shopping_cart_button != null : "fx:id=\"shopping_cart_button\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";
            assert shopping_cart_list != null : "fx:id=\"shopping_cart_list\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";
            assert order_history_button != null : "fx:id=\"order_history_button\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";
            assert order_history_list != null : "fx:id=\"order_history_list\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";

            customer = LoginPageController.getCustomer();
            home_page_header.setText(customer.getFirstName() + "'s Orders");

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }


    }
}