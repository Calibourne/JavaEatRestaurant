package View.QueryPages;

import Model.*;
import Model.Requests.RecordRequest;
import Utils.SFXManager;
import impl.org.controlsfx.collections.ReadOnlyUnbackedObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
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

    @FXML
    private Label null_label;

    @FXML
    private Label result_label;

    Restaurant rest = Restaurant.getInstance();

    Set<RecordRequest> requestSet;

    @FXML
    void initialize() {
        assert aiMacinePane != null : "fx:id=\"aiMacinePane\" was not injected: check your FXML file 'createAIMacine.fxml'.";
        assert dp_combobox != null : "fx:id=\"dp_combobox\" was not injected: check your FXML file 'createAIMacine.fxml'.";
        assert da_combobox != null : "fx:id=\"da_combobox\" was not injected: check your FXML file 'createAIMacine.fxml'.";
        assert order_combobox != null : "fx:id=\"order_combobox\" was not injected: check your FXML file 'createAIMacine.fxml'.";
        assert query_result != null : "fx:id=\"query_result\" was not injected: check your FXML file 'createAIMacine.fxml'.";

        //result_label.setText("");
        null_label.setText("");

        dp_combobox.getItems().clear();
        dp_combobox.getItems().addAll(rest.getDeliveryPersons().values());
        dp_combobox.setValue(null);

        da_combobox.getItems().clear();
        da_combobox.getItems().addAll(rest.getAreas().values());
        da_combobox.setValue(null);

        order_combobox.getItems().clear();
        order_combobox.getItems().addAll(rest.getOrders().values().stream().filter(order -> order.getDelivery()==null).toList());

        query_result.setVisible(false);
        accept_button.setVisible(false);
        reject_button.setVisible(false);

        accept_button.setOnAction(a -> {
            query_result.getItems().clear();
            requestSet.forEach(RecordRequest::saveRequest);
            result_label.setStyle("-fx-text-fill: #00ff56");
            result_label.setText("Deliveries created successfully");
            initialize();
        });

        reject_button.setOnAction(a -> {
            query_result.getItems().clear();
            initialize();
        });

    }

    @FXML
    public void buttonPressed(){
        try {
            null_label.setText("");
            result_label.setText("");
            DeliveryPerson dp = dp_combobox.getValue();
            DeliveryArea da = da_combobox.getValue();
            ReadOnlyUnbackedObservableList<Order> selectedItems =
                    (ReadOnlyUnbackedObservableList<Order>) order_combobox.getCheckModel().getCheckedItems();
            if (dp == null || da == null || selectedItems.size() == 0) {
                throw new NullPointerException();
            }
            query_result.setVisible(true);
            accept_button.setVisible(true);
            reject_button.setVisible(true);
            TreeSet<Order> orders = new TreeSet<>(selectedItems.stream().toList());

            // rest.createAIMacine(dp, da, orders).forEach(rest::addDelivery);
            requestSet = rest.createAIMacine(dp, da, orders);
            query_result.getItems().addAll(requestSet.stream().map(RecordRequest::getRecord).map(r -> (Delivery) r).toList());
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
            // query_result.setText("AI Machine has created a delivery beep beep boop boop");
        }catch(NullPointerException e){
            null_label.setStyle("-fx-text-fill: red");
            null_label.setText("Please make sure that all the forms are filled");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
        }
    }


}