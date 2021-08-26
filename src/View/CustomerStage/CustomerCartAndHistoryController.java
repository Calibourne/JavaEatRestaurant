package View.CustomerStage;

import Model.*;
import Model.Record;
import Model.Requests.AddRecordRequest;
import Model.Requests.RecordRequest;
import View.Controllers.LoginPageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerCartAndHistoryController {

    @FXML
    private Label history_empty_message;

    @FXML
    private Button place_order_button;

    @FXML
    private Button empty_cart_button;

    @FXML
    private Button clear_history_button;

    @FXML
    private Button delete_entry_button;

    @FXML
    private Button edit_order_button;

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
    private ListView<ListedRecord> shopping_cart_list;

    @FXML
    private Label cart_empty_message;

    @FXML
    private Tab order_history_button;

    @FXML
    private ListView<String> order_history_list;

    public static Set<ListedRecord> order_in_cart;

    private Restaurant restaurant;
    private Customer customer;

    @FXML
    void initialize() {

        assert pnlQueries != null : "fx:id=\"pnlQueries\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";
        assert home_page_header != null : "fx:id=\"home_page_header\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";
        assert shopping_cart_button != null : "fx:id=\"shopping_cart_button\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";
        assert shopping_cart_list != null : "fx:id=\"shopping_cart_list\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";
        assert order_history_button != null : "fx:id=\"order_history_button\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";
        assert order_history_list != null : "fx:id=\"order_history_list\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";
        restaurant = Restaurant.getInstance();
        customer = LoginPageController.getCustomer();
        home_page_header.setText(customer.getFirstName() + "'s Order");
        place_order_button.setOnAction(this::placeOrder);
        if (order_in_cart == null) {
            order_in_cart = new HashSet<>();
        }
    }

    @FXML
    private void orderHistoryButtonPressed() {
        order_history_list.getItems().clear();
        try {
            history_empty_message.setText("");
            List<String> s = restaurant.getAddRecordHistory()
                    .get(Order.class.getSimpleName()).stream().filter(r -> ((Order) r.getRecord()).getCustomer().equals(customer))
                    .map(RecordRequest::toString).collect(Collectors.toList());
            if (s.size() == 0)
                throw new NullPointerException();
            order_history_list.getItems().addAll(s);
        } catch (NullPointerException e) {
            order_history_list.getItems().add("You didn't order anything yet");
        }
    }

    @FXML
    //TODO Create a ListedRecord list from the dishes, then turn them back into dishes to form an order upon a button press
    private void shoppingCartButtonPressed() {
        try {
            cart_empty_message.setText("");
            //shopping_cart_list.getItems().clear(); line not working as intended
            shopping_cart_list.getItems().addAll(order_in_cart);
            if(shopping_cart_list.getItems().size()==0){
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            cart_empty_message.setText("Shopping Cart currently empty, please place a new order");
        }

    }

    public void clearHistory(){
        try {
            history_empty_message.setText("");
            order_in_cart.clear();
            order_history_list.getItems().clear();
            if (order_history_list.getItems().size() == 0) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            history_empty_message.setText("No orders to show at the moment");
        }
    }

    public void clearCart(){
        try {
            cart_empty_message.setText("");
            shopping_cart_list.getItems().clear();
            if (shopping_cart_list.getItems().size() == 0) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            cart_empty_message.setText("Shopping Cart currently empty, please place a new order");
        }
    }

    public void placeOrder(ActionEvent event){
        try {
            cart_empty_message.setText("");
            cart_empty_message.setStyle("-fx-text-fill: white");
            Random r = new Random();
            int choice;
            DeliveryPerson dp = null;
            do{
                choice = r.nextInt(DeliveryPerson.getIdCounter());
                dp = restaurant.getRealDeliveryPerson(choice);
            }while (dp == null);
            ArrayList<Record> dishes = new ArrayList<>(shopping_cart_list.getItems().stream().map(ListedRecord::getRecord).toList());
            if(dishes.size() == 0)
                throw new NullPointerException();
            AddRecordRequest orderRequest = new AddRecordRequest(new Order(-1),
                    customer, dishes
            );
            TreeSet<Order> order = new TreeSet<>();
            order.add((Order) orderRequest.getRecord());
            AddRecordRequest deliveryRequest = new AddRecordRequest(new RegularDelivery(-1),
                    order, dp, dp.getArea(), true , LocalDate.now()
            );
            ((Order)orderRequest.getRecord()).setDelivery((Delivery) deliveryRequest.getRecord());
            orderRequest.saveRequest();
            deliveryRequest.saveRequest();
            Order o = (Order) orderRequest.getRecord();
            clearCart();
            order_in_cart = new HashSet<>();
            cart_empty_message.setText("Order placed successfully and will arrive within " + o.orderWaitingTime(o.getDelivery().getArea()) + " minutes");
            restaurant.saveDatabase("Rest.ser");

        }catch (Exception e){
            //e.printStackTrace();
            cart_empty_message.setText("Failed to place an order");
            cart_empty_message.setStyle("-fx-text-fill: red");
        }
    }
}


/**/

//restaurant = Restaurant.getInstance();
            /*assert pnlQueries != null : "fx:id=\"pnlQueries\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";
            assert home_page_header != null : "fx:id=\"home_page_header\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";
            assert shopping_cart_button != null : "fx:id=\"shopping_cart_button\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";
            assert shopping_cart_list != null : "fx:id=\"shopping_cart_list\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";
            assert order_history_button != null : "fx:id=\"order_history_button\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";
            assert order_history_list != null : "fx:id=\"order_history_list\" was not injected: check your FXML file 'CustomerCartAndHistory.fxml'.";
            */
//customer = LoginPageController.getCustomer();
//home_page_header.setText(customer.getFirstName() + "'s Orders");