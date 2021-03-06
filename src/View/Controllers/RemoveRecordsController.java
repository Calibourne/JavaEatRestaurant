package View.Controllers;

import Model.DeliveryArea;
import Model.Record;
import Model.Requests.RemoveRecordRequest;
import Utils.SFXManager;
import View.newElements.imageListCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * A controller that controls the structures remove pages
 * @author Eddie Kanevsky
 */
public class RemoveRecordsController extends RecordManagementController{
    @FXML
    private Label result_label;
    @FXML
    private Group removeCooks_sctn;
    @FXML
    private Group removeDeliPersons_sctn;
    @FXML
    private Group removeCustomers_sctn;
    @FXML
    private Group removeComponents_sctn;
    @FXML
    private Group removeDishes_sctn;
    @FXML
    private Group removeOrders_sctn;
    @FXML
    private Group removeDeliveries_sctn;
    @FXML
    private Group removeAreas_sctn;
    @FXML
    private ComboBox<Record> record_combo;
    @FXML
    private ComboBox<DeliveryArea> newArea_combo;
    @FXML
    private Button submit;
    @FXML
    private void initialize(){
        if(removeCooks_sctn != null){
            record_combo.getItems().addAll(getRestaurant().getCooks().values());
        }
        if(removeDeliPersons_sctn != null){
            record_combo.getItems().addAll(getRestaurant().getCustomers().values());
        }
        if(removeCustomers_sctn != null){
            record_combo.setCellFactory(list->new imageListCell<>());
            record_combo.getItems().addAll(getRestaurant().getCustomers().values());
        }
        if(removeComponents_sctn != null){
            record_combo.getItems().addAll(getRestaurant().getComponents().values());
        }
        if(removeDishes_sctn != null){
            record_combo.getItems().addAll(getRestaurant().getDishes().values());
        }
        if(removeDishes_sctn != null){
            record_combo.getItems().addAll(getRestaurant().getDishes().values());
        }
        if(removeOrders_sctn != null){
            record_combo.getItems().addAll(getRestaurant().getOrders().values());
        }
        if(removeDeliveries_sctn != null){
            record_combo.getItems().addAll(getRestaurant().getDeliveries().values());
        }
        if(removeAreas_sctn != null){
            record_combo.getItems().addAll(getRestaurant().getAreas().values());
            newArea_combo.getItems().addAll(getRestaurant().getAreas().values());
        }
    }

    /**
     * This method controls the action of the removal button according to the current active pane
     */
    @FXML
    private void handleButtonClick(ActionEvent e){
        try {
            result_label.setText("");
            RemoveRecordRequest request = null;
            if (removeAreas_sctn == null) {
                Record record = record_combo.getSelectionModel().getSelectedItem();
                request = new RemoveRecordRequest(record);
            }
            else {
                DeliveryArea oldArea = (DeliveryArea) record_combo.getSelectionModel().getSelectedItem();
                DeliveryArea newArea = newArea_combo.getSelectionModel().getSelectedItem();
                request = new RemoveRecordRequest(oldArea, newArea);
                newArea_combo.getItems().clear();
                if(request.saveRequest())
                {
                    record_combo.getItems().clear();
                    result_label.setStyle("-fx-text-fill: #00ff00");
                    result_label.setText("Delivery Area removed successfully");
                    initialize();
                    getRestaurant().saveDatabase("Rest.ser");
                    if(getRestaurant().getAreas().values().size()>2)
                        return;
                }
                GridPane pane = (GridPane) removeAreas_sctn.getParent();
                pane.getChildren().remove(removeAreas_sctn);
            }
            if(request.saveRequest()) {
                record_combo.getItems().clear();
                if (removeCooks_sctn != null) {
                    if (getRestaurant().getCooks().size() == 0) {
                        GridPane pane = (GridPane) removeCooks_sctn.getParent();
                        pane.getChildren().remove(removeCooks_sctn);
                    }
                    result_label.setStyle("-fx-text-fill: #00ff00");
                    result_label.setText("Cook removed successfully");
                }
                if (removeDeliPersons_sctn != null) {
                    if (getRestaurant().getDeliveryPersons().size() == 0) {
                        GridPane pane = (GridPane) removeDeliPersons_sctn.getParent();
                        pane.getChildren().remove(removeDeliPersons_sctn);
                    }
                    result_label.setStyle("-fx-text-fill: #00ff00");
                    result_label.setText("Delivery Person removed successfully");
                }
                if (removeCustomers_sctn != null) {
                    if (getRestaurant().getCustomers().size() == 0) {
                        GridPane pane = (GridPane) removeCustomers_sctn.getParent();
                        pane.getChildren().remove(removeCustomers_sctn);
                    }
                    result_label.setStyle("-fx-text-fill: #00ff00");
                    result_label.setText("Customer removed successfully");
                }
                if (removeComponents_sctn != null) {
                    if (getRestaurant().getComponents().size() == 0) {
                        GridPane pane = (GridPane) removeComponents_sctn.getParent();
                        pane.getChildren().remove(removeComponents_sctn);
                    }
                    result_label.setStyle("-fx-text-fill: #00ff00");
                    result_label.setText("Ingredient removed successfully");
                }
                if (removeDishes_sctn != null) {
                    if (getRestaurant().getDishes().size() == 0) {
                        GridPane pane = (GridPane) removeDishes_sctn.getParent();
                        pane.getChildren().remove(removeDishes_sctn);
                    }
                    result_label.setStyle("-fx-text-fill: #00ff00");
                    result_label.setText("Dish removed successfully");
                }
                if (removeOrders_sctn != null) {
                    if (getRestaurant().getOrders().size() == 0) {
                        GridPane pane = (GridPane) removeOrders_sctn.getParent();
                        pane.getChildren().remove(removeOrders_sctn);
                    }
                    result_label.setStyle("-fx-text-fill: #00ff00");
                    result_label.setText("Dish removed successfully");
                }
                if (removeDeliveries_sctn != null) {
                    if (getRestaurant().getCooks().size() == 0) {
                        GridPane pane = (GridPane) removeDeliveries_sctn.getParent();
                        pane.getChildren().remove(removeDeliveries_sctn);
                    }
                    result_label.setStyle("-fx-text-fill: #00ff00");
                    result_label.setText("Dish removed successfully");
                }
                initialize();
                getRestaurant().saveDatabase("Rest.ser");
            }
        } catch (IllegalArgumentException ex){
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
        }

    }
}