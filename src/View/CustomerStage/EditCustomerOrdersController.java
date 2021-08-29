package View.CustomerStage;

import Model.Component;
import Model.Dish;
import Model.ListedRecord;
import Model.Order;
import Model.Requests.EditRecordRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckListView;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCustomerOrdersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Group editOrders_sctn;

    @FXML
    private GridPane info_grid;

    @FXML
    private ComboBox<Dish> addComponents_combo;

    @FXML
    private CheckListView<ListedRecord> dishes_checkedList;

    @FXML
    private Button minus_btn;

    @FXML
    private Button plus_btn;

    @FXML
    private VBox ingredients_vbox;

    @FXML
    private Label dish_name;

    @FXML
    private Label dish_id;

    @FXML
    private ComboBox<Component> addSubcomponents_combo;

    @FXML
    private CheckListView<ListedRecord> dishesIngredients_checkedList;

    @FXML
    private Button Iminus_btn;

    @FXML
    private Button addSubcomp_btn;

    @FXML
    private Button Iplus_btn;

    @FXML
    private Button submit;

    @FXML
    private Label alert_lbl;

    @FXML
    private GridPane alert_grid;

    private static Order order;

    public static void setOrder(Order order) {
        EditCustomerOrdersController.order = order;
    }

    @FXML
    void handleButtonClick(ActionEvent event) {
        try {
            EditRecordRequest request = null;
            request = new EditRecordRequest(order,
                    order.getCustomer(), dishes_checkedList.getItems().stream().map(ListedRecord::getRecord).toList());

            request.saveRequest();
            info_grid.setVisible(false);
            alert_grid.setVisible(true);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void initialize() {
        assert editOrders_sctn != null : "fx:id=\"editOrders_sctn\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert info_grid != null : "fx:id=\"info_grid\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert addComponents_combo != null : "fx:id=\"addComponents_combo\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert dishes_checkedList != null : "fx:id=\"dishes_checkedList\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert minus_btn != null : "fx:id=\"minus_btn\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert plus_btn != null : "fx:id=\"plus_btn\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert ingredients_vbox != null : "fx:id=\"ingredients_vbox\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert dish_name != null : "fx:id=\"dish_name\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert dish_id != null : "fx:id=\"dish_id\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert addSubcomponents_combo != null : "fx:id=\"addSubcomponents_combo\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert dishesIngredients_checkedList != null : "fx:id=\"dishesIngredients_checkedList\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert Iminus_btn != null : "fx:id=\"Iminus_btn\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert addSubcomp_btn != null : "fx:id=\"addSubcomp_btn\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert Iplus_btn != null : "fx:id=\"Iplus_btn\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert submit != null : "fx:id=\"submit\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert alert_lbl != null : "fx:id=\"alert_lbl\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert alert_grid != null : "fx:id=\"alert_grid\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";


    }
}