package View.Controllers;

//import Model.DeliveryArea;
//import View.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class EditRecordsPageController extends RecordManagementController {

    //region Properties
    @FXML
    private Button editCooks_btn;
    @FXML
    private Button editAreas_btn;
    @FXML
    private Button editDeliPersons_btn;
    @FXML
    private Button editCustomers_btn;
    @FXML
    private Button editComponents_btn;
    @FXML
    private Button editDishes_btn;
    @FXML
    private Button editOrders_btn;
    @FXML
    private Button editDeliveries_btn;
    @FXML
    private GridPane editWindow;
    @FXML
    private GridPane alert_grid;
    @FXML
    private Label alert_lbl;
    //endregion

    @FXML
    protected void handleButtonClick(ActionEvent e) {
        if (e.getSource() == editDeliPersons_btn && getRestaurant().getAreas().size() == 0) {
            alert_lbl.setText("Please add at least 1 delivery area first");
            alert_grid.toFront();
        } else if (e.getSource() == editDishes_btn && getRestaurant().getComponents().size() == 0) {
            alert_lbl.setText("Please add some ingredients first");
            alert_grid.toFront();
        } else if (e.getSource() == editOrders_btn && getRestaurant().getDishes().size() == 0) {
            alert_lbl.setText("Please add some dishes first");
            alert_grid.toFront();
        } else if (e.getSource() == editDeliveries_btn && getRestaurant().getOrders().size() == 0) {
            alert_lbl.setText("Please add some orders first");
            alert_grid.toFront();
        } else {
            try {
                editWindow.toFront();
                editWindow.getChildren().clear();
                if (e.getSource() == editCooks_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("editCooks.fxml"));
                    editWindow.getChildren().add(node);
                }
                if (e.getSource() == editCustomers_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("editCustomers.fxml"));
                    editWindow.getChildren().add(node);
                }
                if (e.getSource() == editDeliPersons_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("editDeliveryPersons.fxml"));
                    editWindow.getChildren().add(node);
                }
                if (e.getSource() == editComponents_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("editIngredients.fxml"));
                    editWindow.getChildren().add(node);
                }
                if (e.getSource() == editDishes_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("editDishes.fxml"));
                    editWindow.getChildren().add(node);
                }
                if (e.getSource() == editOrders_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("editOrders.fxml"));
                    editWindow.getChildren().add(node);
                }
                if (e.getSource() == editDeliveries_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("editDeliveries.fxml"));
                    editWindow.getChildren().add(node);
                }
                if (e.getSource() == editAreas_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("editDeliveryAreas.fxml"));
                    editWindow.getChildren().add(node);
                }
                editWindow.setVisible(true);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
            //super.handleButtonClick(e);
        }
    }
}