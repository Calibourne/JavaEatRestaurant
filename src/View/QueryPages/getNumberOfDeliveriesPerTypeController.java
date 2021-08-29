package View.QueryPages;

import Model.Restaurant;
import Utils.SFXManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

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

    @FXML
    public void regularButtonPressed(){

//        //Reset the result list each time a query is called upon
//        if(!(query_result.getText().isEmpty())){
//
//            query_result.setText("");
//        }
        query_result.setText(String.format("%d regular deliveries have been delivered since January", rest.getNumberOfDeliveriesPerType().get("Regular delivery")));
        SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");

    }

    public void expressButtonPressed(){
        Restaurant rest = Restaurant.getInstance();
//        //Reset the result list each time a query is called upon
//        if(!(query_result.getText().isEmpty())){
//
//            query_result.setText("");
//        }
        query_result.setText(String.format("%d express deliveries have been delivered since January", rest.getNumberOfDeliveriesPerType().get("Express delivery")));
        SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
    }
}