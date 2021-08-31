package View.Controllers;

//import Model.DeliveryArea;

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
 * A class that controls the Remove records selection page
 * @author Eddie Kanevsky
 */
public class RemoveRecordsPageController extends RecordManagementController{

    //region Properties
    @FXML
    private Button removeCooks_btn;
    @FXML
    private Button removeDeliPersons_btn;
    @FXML
    private Button removeCustomers_btn;
    @FXML
    private Button removeComponents_btn;
    @FXML
    private Button removeDishes_btn;
    @FXML
    private Button removeOrders_btn;
    @FXML
    private Button removeDeliveries_btn;
    @FXML
    private Button removeAreas_btn;
    @FXML
    private GridPane removeWindow;
    @FXML
    private GridPane alert_grid;
    @FXML
    private Label alert_lbl;
    //endregion

    public void handleButtonClick(ActionEvent e) {
        if(e.getSource() == removeCooks_btn && getRestaurant().getCooks().size()==0){
            alert_grid.toFront();
            alert_lbl.setText("No cooks to remove");
        }
        else if(e.getSource() == removeDeliPersons_btn && getRestaurant().getDeliveryPersons().size()==0){
            alert_grid.toFront();
            alert_lbl.setText("No deliverymen to remove");
        }
        else if(e.getSource() == removeCustomers_btn && getRestaurant().getCustomers().size()==0){
            alert_grid.toFront();
            alert_lbl.setText("No customers to remove");
        }
        else if(e.getSource() == removeComponents_btn && getRestaurant().getComponents().size()==0){
            alert_grid.toFront();
            alert_lbl.setText("No ingredients to remove");
        }
        else if(e.getSource() == removeDishes_btn && getRestaurant().getDishes().size()==0){
            alert_grid.toFront();
            alert_lbl.setText("No dishes to remove");
        }
        else if(e.getSource() == removeOrders_btn && getRestaurant().getOrders().size()==0){
            alert_grid.toFront();
            alert_lbl.setText("No orders to remove");
        }
        else if(e.getSource() == removeDeliveries_btn && getRestaurant().getDeliveries().size()==0){
            alert_grid.toFront();
            alert_lbl.setText("No deliveries to remove");
        }
        else if(e.getSource() == removeAreas_btn && getRestaurant().getAreas().size()<=1){
            alert_grid.toFront();
            alert_lbl.setText("You need to have at least 2 delivery areas to be able to remove one");
        }
        else {
            try {
                //removeWindow.getChildren().clear();
                removeWindow.toFront();
                if (e.getSource() == removeCooks_btn) {
                    System.out.println("test");
                    alert_lbl.setText("No Cooks to remove");
                    alert_grid.toFront();
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/removeCooks.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    removeWindow.getChildren().add(node);
                    removeWindow.toFront();
                }
                if (e.getSource() == removeCustomers_btn) {
                    alert_lbl.setText("No Customers to remove");
                    alert_grid.toFront();
                    removeWindow.toFront();
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/removeCustomers.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    removeWindow.getChildren().add(node);
                }
                if (e.getSource() == removeDeliPersons_btn) {
                    alert_lbl.setText("No Deliverymen to remove");
                    alert_grid.toFront();
                    removeWindow.toFront();
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/removeDeliveryPersons.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    removeWindow.getChildren().add(node);
                }
                if (e.getSource() == removeComponents_btn) {
                    alert_lbl.setText("No Ingredients to remove");
                    alert_grid.toFront();
                    removeWindow.toFront();
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/removeIngredients.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    removeWindow.getChildren().add(node);
                }
                if (e.getSource() == removeDishes_btn) {
                    alert_lbl.setText("No Dishes to remove");
                    alert_grid.toFront();
                    removeWindow.toFront();
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/removeDishes.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    removeWindow.getChildren().add(node);
                }
                if (e.getSource() == removeOrders_btn) {
                    alert_lbl.setText("No Orders to remove");
                    alert_grid.toFront();
                    removeWindow.toFront();
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/removeOrders.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    removeWindow.getChildren().add(node);
                }
                if (e.getSource() == removeDeliveries_btn) {
                    alert_lbl.setText("No Deliveries to remove");
                    alert_grid.toFront();
                    removeWindow.toFront();
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/removeDeliveries.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    removeWindow.getChildren().add(node);
                }
                if (e.getSource() == removeAreas_btn) {
                    alert_lbl.setText("No Delivery areas to remove");
                    alert_grid.toFront();
                    removeWindow.toFront();
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/removeDeliveryAreas.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    removeWindow.getChildren().add(node);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //super.handleButtonClick(e);
        }
    }
}