package View.Controllers;

//import Model.DeliveryArea;
//import View.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    private Button editToBlacklist_btn;
    @FXML
    private GridPane editWindow;
    //endregion

    @FXML
    protected void handleButtonClick(ActionEvent e) {
        if (e.getSource() == editDeliPersons_btn && getRestaurant().getAreas().size() == 0) {
            ControllerUtils.showAlertMessage(getGroups().values(), alert_sctn, welcome_sctn, "Please add some delivery areas first");
        } else if (e.getSource() == editToBlacklist_btn && getRestaurant().getCustomers().size() == 0) {
            ControllerUtils.showAlertMessage(getGroups().values(), alert_sctn, welcome_sctn, "Please add some customers first");
        } else if (e.getSource() == editDishes_btn && getRestaurant().getComponents().size() == 0) {
            ControllerUtils.showAlertMessage(getGroups().values(), alert_sctn, welcome_sctn, "Please add some ingredients first");
        } else if (e.getSource() == editOrders_btn && getRestaurant().getDishes().size() == 0) {
            ControllerUtils.showAlertMessage(getGroups().values(), alert_sctn, welcome_sctn, "Please add some dishes first");
        } else if (e.getSource() == editDeliveries_btn && getRestaurant().getOrders().size() == 0) {
            ControllerUtils.showAlertMessage(getGroups().values(), alert_sctn, welcome_sctn, "Please add some orders first");
        } else {
            try {
                welcome_sctn.setVisible(false);
                alert_sctn.setVisible(false);
                editWindow.setVisible(false);
                editWindow.getChildren().clear();
                if (e.getSource() == editCooks_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("../fxmls/editRecordsPage/editCooks.fxml"));
                    editWindow.getChildren().add(node);
                }
                if (e.getSource() == editCustomers_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("../fxmls/editRecordsPage/editCustomers.fxml"));
                    editWindow.getChildren().add(node);
                }
                if (e.getSource() == editDeliPersons_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("../fxmls/editRecordsPage/editDeliveryPersons.fxml"));
                    editWindow.getChildren().add(node);
                }
                if (e.getSource() == editComponents_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("../fxmls/editRecordsPage/editIngredients.fxml"));
                    editWindow.getChildren().add(node);
                }
                if (e.getSource() == editDishes_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("../fxmls/editRecordsPage/editDishes.fxml"));
                    editWindow.getChildren().add(node);
                }
                if (e.getSource() == editOrders_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("../fxmls/editRecordsPage/editOrders.fxml"));
                    editWindow.getChildren().add(node);
                }
                if (e.getSource() == editDeliveries_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("../fxmls/editRecordsPage/editDeliveries.fxml"));
                    editWindow.getChildren().add(node);
                }
                if (e.getSource() == editAreas_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("../fxmls/editRecordsPage/editDeliveryAreas.fxml"));
                    editWindow.getChildren().add(node);
                }
                if (e.getSource() == editToBlacklist_btn) {
                    Node node = FXMLLoader.load(getClass().getResource("../fxmls/editRecordsPage/editToBlacklist.fxml"));
                    editWindow.getChildren().add(node);
                }
                editWindow.setVisible(true);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            //super.handleButtonClick(e);
        }
    }
}