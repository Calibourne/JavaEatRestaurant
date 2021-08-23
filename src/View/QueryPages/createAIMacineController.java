package View.QueryPages;

import Model.*;
import impl.org.controlsfx.collections.ReadOnlyUnbackedObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeSet;

public class createAIMacineController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane aiMacinePane;

    @FXML
    private ComboBox<DeliveryPerson> dp_combobox;

    @FXML
    private ComboBox<DeliveryArea> da_combobox;

    @FXML
    private CheckComboBox<Order> order_combobox;

    @FXML
    private ListView<Delivery> query_result;

    @FXML
    private Button reject_button;

    @FXML
    private Button accept_button;


    Restaurant rest = Restaurant.getInstance();

    @FXML
    void initialize() {
        assert aiMacinePane != null : "fx:id=\"aiMacinePane\" was not injected: check your FXML file 'createAIMacine.fxml'.";
        assert dp_combobox != null : "fx:id=\"dp_combobox\" was not injected: check your FXML file 'createAIMacine.fxml'.";
        assert da_combobox != null : "fx:id=\"da_combobox\" was not injected: check your FXML file 'createAIMacine.fxml'.";
        assert order_combobox != null : "fx:id=\"order_combobox\" was not injected: check your FXML file 'createAIMacine.fxml'.";
        assert query_result != null : "fx:id=\"query_result\" was not injected: check your FXML file 'createAIMacine.fxml'.";

        dp_combobox.getItems().addAll(rest.getDeliveryPersons().values());
        da_combobox.getItems().addAll(rest.getAreas().values());
        order_combobox.getItems().addAll(rest.getOrders().values());

        query_result.setVisible(false);
        accept_button.setVisible(false);
        reject_button.setVisible(false);

        accept_button.setOnAction(a -> {
            query_result.getItems().forEach(rest::addDelivery);
            query_result.getItems().clear();
            rest.saveDatabase("Rest.ser");
            initialize();
        });

        reject_button.setOnAction(a -> {
            query_result.getItems().clear();
            initialize();
        });

    }

    @FXML
    public void buttonPressed(){
        query_result.setVisible(true);
        accept_button.setVisible(true);
        reject_button.setVisible(true);
        DeliveryPerson dp = dp_combobox.getValue();
        DeliveryArea da = da_combobox.getValue();
        ReadOnlyUnbackedObservableList<Order> selectedItems =
                (ReadOnlyUnbackedObservableList<Order>) order_combobox.getCheckModel().getCheckedItems();
        TreeSet<Order> orders = new TreeSet<>(selectedItems.stream().toList());

       // rest.createAIMacine(dp, da, orders).forEach(rest::addDelivery);

        query_result.getItems().addAll(rest.createAIMacine(dp, da, orders));

       // query_result.setText("AI Machine has created a delivery beep beep boop boop");
    }


}