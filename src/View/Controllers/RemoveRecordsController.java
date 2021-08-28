package View.Controllers;

import Model.DeliveryArea;
import Model.Record;
import Model.Requests.RemoveRecordRequest;
import Model.Restaurant;
import View.newElements.imageListCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class RemoveRecordsController {
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
            record_combo.getItems().addAll(Restaurant.getInstance().getCooks().values());
        }
        if(removeDeliPersons_sctn != null){
            record_combo.getItems().addAll(Restaurant.getInstance().getCustomers().values());
        }
        if(removeCustomers_sctn != null){
            record_combo.setCellFactory(list->new imageListCell<>());
            record_combo.getItems().addAll(Restaurant.getInstance().getCustomers().values());
        }
        if(removeComponents_sctn != null){
            //record_combo.setPromptText("Choose ingredient to remove");
            record_combo.getItems().addAll(Restaurant.getInstance().getComponents().values());
        }
        if(removeDishes_sctn != null){
            //record_combo.setPromptText("Choose dish to remove");
            record_combo.getItems().addAll(Restaurant.getInstance().getDishes().values());
        }
        if(removeDishes_sctn != null){
            //record_combo.setPromptText("Choose dish to remove");
            record_combo.getItems().addAll(Restaurant.getInstance().getDishes().values());
        }
        if(removeOrders_sctn != null){
            //record_combo.setPromptText("Choose order to remove");
            record_combo.getItems().addAll(Restaurant.getInstance().getOrders().values());
        }
        if(removeDeliveries_sctn != null){
            //record_combo.setPromptText("Choose delivery to remove");
            record_combo.getItems().addAll(Restaurant.getInstance().getDeliveries().values());
        }
        if(removeAreas_sctn != null){
            //record_combo.setPromptText("Choose delivery area to remove");
            record_combo.getItems().addAll(Restaurant.getInstance().getAreas().values());
            newArea_combo.getItems().addAll(Restaurant.getInstance().getAreas().values());
        }
    }
    @FXML
    private void handleButtonClick(ActionEvent e){
        try {
            result_label.setText("");
            RemoveRecordRequest request = new RemoveRecordRequest(null);
            if (removeAreas_sctn == null) {
                Record record = record_combo.getSelectionModel().getSelectedItem();
                request = new RemoveRecordRequest(record);
            }
            else {
                DeliveryArea oldArea = (DeliveryArea) record_combo.getSelectionModel().getSelectedItem();
                DeliveryArea newArea = newArea_combo.getSelectionModel().getSelectedItem();
                request = new RemoveRecordRequest(oldArea, newArea);
                newArea_combo.getItems().clear();
                request.saveRequest();
                record_combo.getItems().clear();
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Delivery Area removed successfully");
                initialize();
                Restaurant.getInstance().saveDatabase("Rest.ser");
                if(Restaurant.getInstance().getAreas().values().size()>2) {
                    return;
                }
                GridPane pane = (GridPane) removeAreas_sctn.getParent();
                pane.getChildren().remove(removeAreas_sctn);
            }
            request.saveRequest();
            record_combo.getItems().clear();
            if (removeCooks_sctn != null) {
                Restaurant rest = Restaurant.getInstance();
                if(rest.getCooks().size()==0){
                    GridPane pane = (GridPane) removeCooks_sctn.getParent();
                    pane.getChildren().remove(removeCooks_sctn);
                }
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Cook removed successfully");
            }
            if (removeDeliPersons_sctn != null) {
                Restaurant rest = Restaurant.getInstance();
                if(rest.getDeliveryPersons().size()==0){
                    GridPane pane = (GridPane) removeDeliPersons_sctn.getParent();
                    pane.getChildren().remove(removeDeliPersons_sctn);
                }
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Delivery Person removed successfully");
            }
            if (removeCustomers_sctn != null) {
                Restaurant rest = Restaurant.getInstance();
                if(rest.getCustomers().size()==0){
                    GridPane pane = (GridPane) removeCustomers_sctn.getParent();
                    pane.getChildren().remove(removeCustomers_sctn);
                }
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Customer removed successfully");
            }
            if (removeComponents_sctn != null) {
                Restaurant rest = Restaurant.getInstance();
                if(rest.getComponents().size()==0){
                    GridPane pane = (GridPane) removeComponents_sctn.getParent();
                    pane.getChildren().remove(removeComponents_sctn);
                }
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Ingredient removed successfully");
            }
            if (removeDishes_sctn != null) {
                Restaurant rest = Restaurant.getInstance();
                if(rest.getDishes().size()==0){
                    GridPane pane = (GridPane) removeDishes_sctn.getParent();
                    pane.getChildren().remove(removeDishes_sctn);
                }
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Dish removed successfully");
            }
            if (removeOrders_sctn != null) {
                Restaurant rest = Restaurant.getInstance();
                if(rest.getOrders().size()==0){
                    GridPane pane = (GridPane) removeOrders_sctn.getParent();
                    pane.getChildren().remove(removeOrders_sctn);
                }
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Dish removed successfully");
            }
            if (removeDeliveries_sctn != null) {
                Restaurant rest = Restaurant.getInstance();
                if(rest.getCooks().size()==0){
                    GridPane pane = (GridPane) removeDeliveries_sctn.getParent();
                    pane.getChildren().remove(removeDeliveries_sctn);
                }
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Dish removed successfully");
            }
            initialize();
            Restaurant.getInstance().saveDatabase("Rest.ser");
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }
}