package View.Controllers;

import Utils.SFXManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * The controller for the manager page
 * @author Eddie Kanevsky
 */
public class ManagerPageController {
    // region FXML comps
    @FXML
    private Label lblGreeting;

    @FXML
    private Button view_records;

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
    private Button btnSignOut;
    // endregion
    // region panels
    @FXML
    private Pane pnlChoosedPage;
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
    @FXML
    private void initialize(){
        initDashboard();
    }

    /**
     * This method is in charge of sending the user to the scene corresponding to the selected button
     * @param e
     */
    @FXML
    private void handleButtonClick(ActionEvent e) {
        if(e.getSource() == btnOverview) {
            initDashboard();
        }
        if(e.getSource() == btnAddRecords) {
            initRecords(e);
        }
        if(e.getSource() == btnRemoveRecords){
            initRecords(e);
        }
        if (e.getSource() == btnEditRecords){
            initRecords(e);
        }

        if(e.getSource() == btnQueries) {
            initQueries();
        }
        if(e.getSource() == view_records) {
            initViewRecords();
        }
        if(e.getSource() == btnSignOut) {
            try {
                SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Logoff_Sound.wav");
                Stage s = (Stage) btnSignOut.getScene().getWindow();
                Parent p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/LoginPage.fxml")));
                s.hide();
                s.setScene(new Scene(p));
                s.centerOnScreen();
                s.show();
            } catch (IOException | NullPointerException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Shows a sub-window with the dashboard (RestaurantDashboard.fxml)
     */
    private void initDashboard() {
        try {
            pnlChoosedPage.getChildren().clear();
            Node recordsA = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/RestaurantDashboard.fxml")));
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
            // SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav"); plays at login, not how we intended
            pnlChoosedPage.getChildren().add(recordsA);
        } catch(IOException | NullPointerException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Shows a sub-windpw with the record-management pages (add / remove / edit)
     * @param e
     * the Action event (which button pressed)
     */
    private void initRecords(ActionEvent e) {
        if(e.getSource() == btnAddRecords)
            try {
                pnlChoosedPage.getChildren().clear();
                Node recordsA = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/addRecords.fxml")));
                SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                pnlChoosedPage.getChildren().add(recordsA);
            } catch(IOException | NullPointerException ex){
                System.out.println(ex.getMessage());
            }
        if(e.getSource() == btnRemoveRecords)
            try{
                pnlChoosedPage.getChildren().clear();
                Node recordsR = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/removeRecords.fxml")));
                SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                pnlChoosedPage.getChildren().add(recordsR);
            }catch (IOException | NullPointerException ex) {
                System.out.println(ex.getMessage());
            }
        if(e.getSource() == btnEditRecords)
            try {
                pnlChoosedPage.getChildren().clear();
                Node recordsE = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/editRecords.fxml")));
                SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                pnlChoosedPage.getChildren().add(recordsE);
            }catch (IOException | NullPointerException ex){
                System.out.println(ex.getMessage());
            }
    }

    /**
     * Shows the query sub-window (Queries.fxml)
     */
    private void initQueries(){
        try{
            pnlChoosedPage.getChildren().clear();
            Node queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/Queries.fxml")));
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
            pnlChoosedPage.getChildren().add(queryPage);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Shows the structures view page (ViewStructures.fxml)
     */
    private void initViewRecords(){
        try{
            pnlChoosedPage.getChildren().clear();
            Node ViewRecordsPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/ViewStructures.fxml")));
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
            pnlChoosedPage.getChildren().add(ViewRecordsPage);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

}