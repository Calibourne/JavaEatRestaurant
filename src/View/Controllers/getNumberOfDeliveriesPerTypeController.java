package View.Controllers;

import Model.Restaurant;
import Utils.SFXManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is a controller class for the getNumberOfDeliveriesPerType query page
 * @author Daniel Sharon
 */

public class getNumberOfDeliveriesPerTypeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pnlQueries;

    @FXML
    private Button regular_button;

    @FXML
    private Button express_button;

    @FXML
    private Label query_result;

    Restaurant rest = Restaurant.getInstance();

    @FXML
    void initialize() {
        System.out.println(rest.getDeliveries().size());
        System.out.println(rest.getNumberOfDeliveriesPerType().get("Regular delivery"));
        System.out.println(rest.getNumberOfDeliveriesPerType().get("Express delivery"));
    }

    /**
     * This method activates the actual query function for deliveries of regular type
     */
    @FXML
    public void regularButtonPressed(){
        query_result.setText(String.format("%d regular deliveries have been delivered since January", rest.getNumberOfDeliveriesPerType().get("Regular delivery")));
        SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");

    }

    /**
     * This method activates the actual query function for deliveries of express type
     */
    public void expressButtonPressed(){
        Restaurant rest = Restaurant.getInstance();

        query_result.setText(String.format("%d express deliveries have been delivered since January", rest.getNumberOfDeliveriesPerType().get("Express delivery")));
        SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
    }
}