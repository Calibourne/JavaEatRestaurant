package View.Controllers;

//import Model.DeliveryArea;
//import View.Main;

import Utils.SFXManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Objects;

/**
 * A class that controls the Add records selection page
 * @author Eddie Kanevsky
 */
public class AddRecordsPageController extends RecordManagementController {

    //region Properties
    @FXML
    private Button addCooks_btn;
    @FXML
    private Button addAreas_btn;
    @FXML
    private Button addDeliPersons_btn;
    @FXML
    private Button addCustomers_btn;
    @FXML
    private Button addComponents_btn;
    @FXML
    private Button addDishes_btn;
    @FXML
    private Button addOrders_btn;
    @FXML
    private Button addDeliveries_btn;
    @FXML
    private Button addToBlacklist_btn;
    @FXML
    private GridPane addWindow;
    @FXML
    private GridPane alert_grid;
    @FXML
    private Label alert_lbl;
    //endregion

    @FXML
    protected void handleButtonClick(ActionEvent e) {
        if (e.getSource() == addDeliPersons_btn && getRestaurant().getAreas().size() == 0) {
            alert_lbl.setText("Please add at least 1 delivery area first");
            alert_grid.toFront();
        } else if (e.getSource() == addToBlacklist_btn && getRestaurant().getCustomers().size() == 0) {
            alert_lbl.setText("Please add at least 1 customers first");
            alert_grid.toFront();
        } else if (e.getSource() == addDishes_btn && getRestaurant().getComponents().size() == 0) {
            alert_lbl.setText("Please add at least 1 ingredients first");
            alert_grid.toFront();
        } else if (e.getSource() == addOrders_btn && getRestaurant().getDishes().size() == 0) {
            alert_lbl.setText("Please add at least 1 dishes first");
            alert_grid.toFront();
        } else if (e.getSource() == addDeliveries_btn && getRestaurant().getOrders().size() == 0) {
            alert_lbl.setText("Please add at least 1 orders first");
            alert_grid.toFront();
        } else {
            try {
                addWindow.toFront();
                addWindow.getChildren().clear();
                if (e.getSource() == addCooks_btn) {
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/addCooks.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    addWindow.getChildren().add(node);
                }
                if (e.getSource() == addCustomers_btn) {
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/addCustomers.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    addWindow.getChildren().add(node);
                }
                if (e.getSource() == addDeliPersons_btn) {
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/addDeliveryPersons.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    addWindow.getChildren().add(node);
                }
                if (e.getSource() == addComponents_btn) {
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/addIngredients.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    addWindow.getChildren().add(node);
                }
                if (e.getSource() == addDishes_btn) {
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/addDishes.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    addWindow.getChildren().add(node);
                }
                if (e.getSource() == addOrders_btn) {
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/addOrders.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    addWindow.getChildren().add(node);
                }
                if (e.getSource() == addDeliveries_btn) {
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/addDeliveries.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    addWindow.getChildren().add(node);
                }
                if (e.getSource() == addAreas_btn) {
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/addDeliveryAreas.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    addWindow.getChildren().add(node);
                }
                if (e.getSource() == addToBlacklist_btn) {
                    alert_lbl.setText("No more customers to blacklist, please add at least 1 more customer first");
                    alert_grid.toFront();
                    addWindow.toFront();
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/addToBlacklist.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    addWindow.getChildren().add(node);
                }
                addWindow.setVisible(true);
            } catch (IOException | NullPointerException ex) {
                ex.printStackTrace();
            }
            //super.handleButtonClick(e);
        }
    }
}