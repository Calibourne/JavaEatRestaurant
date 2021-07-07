package View.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerPageController {
    @FXML
    private Label lblGreeting;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnRecords;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;

    @FXML
    private Pane pnlRecords;

    @FXML
    private AnchorPane pnlRecordsManagement;
    public void initialize(){
        pnlOverview.setStyle("-fx-background-color: #1F4591");
        pnlOverview.toFront();
        try {
            Node recordsM = FXMLLoader.load(getClass().getResource("Records.fxml"));
            pnlRecordsManagement.getChildren().add(recordsM);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleButtonClick(ActionEvent e) {
        if(e.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color: #1F4591");
            pnlOverview.toFront();
        }
        if(e.getSource() == btnRecords) {
            pnlRecords.setStyle("-fx-background-color: #1F4591");
            pnlRecords.toFront();
        }
        if(e.getSource() == btnOrders) {
            pnlOrders.setStyle("-fx-background-color: #13194c");
            pnlOrders.toFront();
        }
        if(e.getSource() == btnSignout) {
            try {
                Stage s = (Stage) btnSignout.getScene().getWindow();
                Parent p = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
                s.hide();
                s.setScene(new Scene(p));
                s.centerOnScreen();
                s.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}