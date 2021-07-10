package View.Controllers;

import Model.Delivery;
import Model.Restaurant;
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
import java.net.URL;
import java.util.Objects;

public class ManagerPageController {
    // region FXML comps
    @FXML
    private Label lblGreeting;

    // region buttons
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnAddRecords;

    @FXML
    private Button btnEditRecords;

    @FXML
    private Button btnRemoveRecords;

    @FXML
    private Button btnQueries;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignOut;
    // endregion
    // region panels
    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlAddRecords;

    @FXML
    private Pane pnlEditRecords;

    @FXML
    private Pane pnlRemoveRecords;

    @FXML
    private Pane pnlQueries;

    @FXML
    private AnchorPane pnlRecordsManagement;
    // endregion
    // region labels
    @FXML
    private Label numOfDeliveries_lbl;

    @FXML
    private Label numOfDelivered_lbl;

    @FXML
    private Label numOfCustomers_lbl;

    @FXML
    private Label numOfBlacklisted_lbl;
    // endregion

    // endregion
    public void initialize(){
        initOverview();
        initRecords();
        initQueries();
    }
    public void handleButtonClick(ActionEvent e) {
        if(e.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color: #1F4591");
            pnlOverview.toFront();
        }
        if(e.getSource() == btnAddRecords) {
            //pnlAddRecords.setStyle("-fx-background-color: #1F4591");
            //pnlAddRecords.toFront();
            try{
                Stage s = (Stage) btnAddRecords.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("../fxmls/addRecords.fxml"));
                s.setScene(new Scene(root));
            }catch (IOException ex){
                System.out.println(ex.getMessage());
            }
        }
        if(e.getSource() == btnQueries) {
            pnlQueries.setStyle("-fx-background-color: #13194c");
            pnlQueries.toFront();
        }
        if(e.getSource() == btnSignOut) {
            try {
                Stage s = (Stage) btnSignOut.getScene().getWindow();
                Parent p = FXMLLoader.load(getClass().getResource("../fxmls/LoginPage.fxml"));
                s.hide();
                s.setScene(new Scene(p));
                s.centerOnScreen();
                s.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
    private void initOverview() {
        Restaurant res = Restaurant.getInstance();
        numOfDeliveries_lbl.setText(String.format("%d",res.getDeliveries().size()));
        numOfDelivered_lbl.setText(String.format("%d",res.getDeliveries().values()
                .stream().filter(Delivery::isDelivered).toList().size()));
        numOfCustomers_lbl.setText(String.format("%d",res.getCustomers().size()));
        numOfBlacklisted_lbl.setText(String.format("%d",res.getBlacklist().size()));

        pnlOverview.setStyle("-fx-background-color: #1F4591");
        pnlOverview.toFront();
    }
    private void initRecords() {
        try {
            Node recordsM = FXMLLoader.load(getClass().getResource("../fxmls/addRecords.fxml"));
            pnlRecordsManagement.getChildren().add(recordsM);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This makes the query button clickable
    public void initQueries(){
        try{
            Node recordsM = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../fxmls/Queries.fxml")));
            pnlQueries.getChildren().add(recordsM);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}