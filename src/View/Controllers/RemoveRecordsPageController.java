package View.Controllers;

//import Model.DeliveryArea;

import Model.*;
import Utils.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


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
    //endregion

    public void handleButtonClick(ActionEvent e) {
        removeWindow.setVisible(false);
        if(e.getSource() == removeCooks_btn && getRestaurant().getCooks().size()==0){
            ControllerUtils.showAlertMessage(getGroups().values(), alert_sctn,welcome_sctn, "You don't have any cooks to remove");
        }
        else if(e.getSource() == removeDeliPersons_btn && getRestaurant().getDeliveryPersons().size()==0){
            ControllerUtils.showAlertMessage(getGroups().values(),alert_sctn,welcome_sctn,"You don't have any deliverymen to remove");
        }
        else if(e.getSource() == removeCustomers_btn && getRestaurant().getCustomers().size()==0){
            ControllerUtils.showAlertMessage(getGroups().values(), alert_sctn,welcome_sctn, "You don't have any customers to remove");
        }
        else if(e.getSource() == removeComponents_btn && getRestaurant().getComponents().size()==0){
            ControllerUtils.showAlertMessage(getGroups().values(),alert_sctn,welcome_sctn,"Tou don't have any ingredients to remove");
        }
        else if(e.getSource() == removeDishes_btn && getRestaurant().getDishes().size()==0){
            ControllerUtils.showAlertMessage(getGroups().values(),alert_sctn,welcome_sctn,"You don't have any dishes to remove");
        }
        else if(e.getSource() == removeOrders_btn && getRestaurant().getOrders().size()==0){
            ControllerUtils.showAlertMessage(getGroups().values(),alert_sctn,welcome_sctn,"You don't have any orders to remove");
        }
        else if(e.getSource() == removeDeliveries_btn && getRestaurant().getDeliveries().size()==0){
            ControllerUtils.showAlertMessage(getGroups().values(),alert_sctn,welcome_sctn,"You don't have any deliveries to remove");
        }
        else if(e.getSource() == removeAreas_btn && getRestaurant().getAreas().size()<=1){
            ControllerUtils.showAlertMessage(getGroups().values(),alert_sctn,welcome_sctn,"You should have at least 2 delivery areas to be able to remove one");
        }
        else {
            try {
                welcome_sctn.setVisible(false);
                alert_sctn.setVisible(false);
                removeWindow.getChildren().clear();
                if (e.getSource() == removeCooks_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("../fxmls/removeRecordsPage/removeCooks.fxml"));
                    removeWindow.getChildren().add(node);
                }
                if (e.getSource() == removeCustomers_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("../fxmls/removeRecordsPage/removeCustomers.fxml"));
                    removeWindow.getChildren().add(node);
                }
                if (e.getSource() == removeDeliPersons_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("../fxmls/removeRecordsPage/removeDeliveryPersons.fxml"));
                    removeWindow.getChildren().add(node);
                }
                if (e.getSource() == removeComponents_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("../fxmls/removeRecordsPage/removeIngredients.fxml"));
                    removeWindow.getChildren().add(node);
                }
                if (e.getSource() == removeDishes_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("../fxmls/removeRecordsPage/removeDishes.fxml"));
                    removeWindow.getChildren().add(node);
                }
                if (e.getSource() == removeOrders_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("../fxmls/removeRecordsPage/removeOrders.fxml"));
                    removeWindow.getChildren().add(node);
                }
                if (e.getSource() == removeDeliveries_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("../fxmls/removeRecordsPage/removeDeliveries.fxml"));
                    removeWindow.getChildren().add(node);
                }
                if (e.getSource() == removeAreas_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("../fxmls/removeRecordsPage/removeDeliveryAreas.fxml"));
                    removeWindow.getChildren().add(node);
                }
                removeWindow.setVisible(true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //super.handleButtonClick(e);
        }
    }
}