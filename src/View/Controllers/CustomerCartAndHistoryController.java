package View.Controllers;

import Model.Record;
import Model.*;
import Model.Requests.AddRecordRequest;
import Model.Requests.RecordRequest;
import Model.Requests.RemoveRecordRequest;
import Utils.SFXManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This Controller is for the CustomerCartAndHistory page
 * @author Daniel Sharon
 */
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
    private ListView<RecordRequest> order_history_list;

    @FXML
    private AnchorPane cart_and_history_pane;

    public static Set<ListedRecord> order_in_cart;

    private Restaurant restaurant;
    private Customer customer;
    private static Order order_to_change;

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

    /**
     * This method is used to switch the inner pane of the tab to the order history view
     */
    @FXML
    private void orderHistoryButtonPressed() {
        order_history_list.getItems().clear();
        SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
        try {
            history_empty_message.setText("");

            Set<RecordRequest> s = restaurant.getAddRecordHistory()
                    .get(Order.class.getSimpleName()).stream()
                    .filter(rr->((Order)rr.getRecord()).getCustomer().equals(customer))
                    .filter(rr->restaurant.getOrders().containsValue((Order)rr.getRecord()))
                    .collect(Collectors.toSet());
            System.out.println(s);
            if (s.size() == 0)
                throw new NullPointerException();
            order_history_list.getItems().addAll(s);
        } catch (NullPointerException e) {
            history_empty_message.setText("No orders to show at the moment");
        }
    }

    /**
     * This method is used to switch the inner pane of the tab to the shopping cart view
     */
    @FXML
    private void shoppingCartButtonPressed() {
        try {
            cart_empty_message.setText("");
            shopping_cart_list.getItems().clear();
            shopping_cart_list.getItems().addAll(order_in_cart);
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
            if(shopping_cart_list.getItems().size()==0){
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            cart_empty_message.setText("Shopping Cart currently empty, please place a new order");
        }


    }

    /**
     * @return Order to be edited in the history pane
     */
    public static Order getOrder_to_change() {
        return order_to_change;
    }

    public void clearCart(){
        try {
            cart_empty_message.setText("");
            shopping_cart_list.getItems().clear();
            order_in_cart.clear();
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
            if (shopping_cart_list.getItems().size() == 0) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            cart_empty_message.setText("Shopping Cart currently empty, please place a new order");
        }
    }

    /**
     * This method is used to place and order selected from the listview of the shopping cart
     * @param event
     */
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

            orderRequest.saveRequest();
//            deliveryRequest.saveRequest();
            Order o = (Order) orderRequest.getRecord();
            clearCart();
            order_in_cart = new HashSet<>();
            cart_empty_message.setStyle("-fx-text-fill: #00ff00");
            cart_empty_message.setText("Order placed successfully");
            restaurant.saveDatabase("Rest.ser");
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
        }catch (Exception e){
            e.printStackTrace();
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            cart_empty_message.setStyle("-fx-text-fill: red");
            cart_empty_message.setText("Failed to place an order");
        }
    }

    /**
     *  This method is used to delete an order selected from the order history list
     */
    @FXML
    void deleteHistoryOrder() {
        try {
            history_empty_message.setText("");
            System.out.println("test");
            RemoveRecordRequest request = new RemoveRecordRequest(order_history_list.getSelectionModel().getSelectedItem().getRecord());
            request.saveRequest();
            orderHistoryButtonPressed();
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
        } catch (Exception e){
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            history_empty_message.setText("Please select an order to delete");
        }

    }

    /**
     * A method used to send the user to the edit order page for the selected order upon pressing the 'edit order' button
     */
    @FXML
    void editHistoryOrder() {
        Node page = null;
        try {
            order_to_change = (Order) order_history_list.getSelectionModel().getSelectedItem().getRecord();
            if (order_to_change == null)
                throw new NullPointerException();
            page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/EditCustomerOrders.fxml")));
            cart_and_history_pane.getChildren().add(page);
            page.toFront();
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
        }catch(NullPointerException e){
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            history_empty_message.setText("Please select an order");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}