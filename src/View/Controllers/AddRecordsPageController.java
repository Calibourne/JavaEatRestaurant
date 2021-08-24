package View.Controllers;

//import Model.DeliveryArea;
//import View.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//import javafx.geometry.Pos;
        import javafx.scene.Node;
        import javafx.scene.control.*;
        import javafx.scene.layout.GridPane;

        import java.io.IOException;
import java.util.Objects;

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
    //endregion

    @FXML
    protected void handleButtonClick(ActionEvent e) {
        if (e.getSource() == addDeliPersons_btn && getRestaurant().getAreas().size() == 0) {
            ControllerUtils.showAlertMessage(getGroups().values(), alert_sctn, welcome_sctn, "Please add some delivery areas first");
        } else if (e.getSource() == addToBlacklist_btn && getRestaurant().getCustomers().size() == 0) {
            ControllerUtils.showAlertMessage(getGroups().values(), alert_sctn, welcome_sctn, "Please add some customers first");
        } else if (e.getSource() == addDishes_btn && getRestaurant().getComponents().size() == 0) {
            ControllerUtils.showAlertMessage(getGroups().values(), alert_sctn, welcome_sctn, "Please add some ingredients first");
        } else if (e.getSource() == addOrders_btn && getRestaurant().getDishes().size() == 0) {
            ControllerUtils.showAlertMessage(getGroups().values(), alert_sctn, welcome_sctn, "Please add some dishes first");
        } else if (e.getSource() == addDeliveries_btn && getRestaurant().getOrders().size() == 0) {
            ControllerUtils.showAlertMessage(getGroups().values(), alert_sctn, welcome_sctn, "Please add some orders first");
        } else {
            try {
                welcome_sctn.setVisible(false);
                alert_sctn.setVisible(false);
                addWindow.setVisible(false);
                addWindow.getChildren().clear();
                if (e.getSource() == addCooks_btn) {
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addCooks.fxml")));
                    addWindow.getChildren().add(node);
                }
                if (e.getSource() == addCustomers_btn) {
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addCustomers.fxml")));
                    addWindow.getChildren().add(node);
                }
                if (e.getSource() == addDeliPersons_btn) {
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addDeliveryPersons.fxml")));
                    addWindow.getChildren().add(node);
                }
                if (e.getSource() == addComponents_btn) {
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addIngredients.fxml")));
                    addWindow.getChildren().add(node);
                }
                if (e.getSource() == addDishes_btn) {
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addDishes.fxml")));
                    addWindow.getChildren().add(node);
                }
                if (e.getSource() == addOrders_btn) {
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addOrders.fxml")));
                    addWindow.getChildren().add(node);
                }
                if (e.getSource() == addDeliveries_btn) {
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addDeliveries.fxml")));
                    addWindow.getChildren().add(node);
                }
                if (e.getSource() == addAreas_btn) {
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addDeliveryAreas.fxml")));
                    addWindow.getChildren().add(node);
                }
                if (e.getSource() == addToBlacklist_btn) {
                    Node node = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addToBlacklist.fxml")));
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