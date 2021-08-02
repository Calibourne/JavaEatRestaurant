package View.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class QueriesController {

    @FXML
    private Button relevant_dish_list_button;

    @FXML
    private Button cooks_by_expertise_button;

    @FXML
    private Button popular_ingredients_button;

    @FXML
    private Button order_waiting_time_button;

    @FXML
    private Button delis_by_dp_button;

    @FXML
    private Button delis_by_type_button;

    @FXML
    private Button express_revenue_button;

    @FXML
    private Button profit_relation_button;

    @FXML
    private Button ai_macine_button;

    @FXML
    private GridPane query_pane;

    @FXML
    private Pane paneRelevantDish;

    @FXML
    private Pane paneCooksExpertise;

    @FXML
    private Pane panePopIngredients;

    @FXML
    private Pane paneOrderWaitingTime;

    @FXML
    private Pane paneDelisByDP;

    @FXML
    private Pane paneDelisByType;

    @FXML
    private Pane paneRevenueExpress;

    @FXML
    private Pane paneProfitRelation;

    @FXML
    private Pane paneAiMacine;


    public void initialize(){
//        initAiMacine(); not working atm
    }


    public void handleButtonClick(ActionEvent event) {
        if(event.getSource()== ai_macine_button){
            paneAiMacine.setStyle("-fx-background-color: #1F4591");
            paneAiMacine.toFront();

        }

    }

    // (currently non-functional) method(s) used to switch between scenes within a stage
    public void aiMacineButtonPushed(ActionEvent event) throws IOException {
        Parent aiMacineParent = FXMLLoader.load(getClass().getResource("createAIMacine.fxml)"));
        Scene aiMacineScene = new Scene(aiMacineParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(aiMacineScene);
        window.show();
    }


//    public void initAiMacine(){
//        try{
//            Node aiMacinePage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../fxmls/createAIMacine.fxml")));
//            query_pane.getChildren().add(aiMacinePage);
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }
//    } not working atm

}