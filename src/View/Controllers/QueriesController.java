package View.Controllers;

import Utils.SFXManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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

    }


    public void handleButtonClick(ActionEvent event) {
        if(event.getSource()== ai_macine_button){
            paneAiMacine.setStyle("-fx-background-color: #1F4591");
            paneAiMacine.toFront();
        }
    }

    // method(s) used to switch between scenes within a stage


    public void queryButtonPushed(ActionEvent event) {
        try{
            Node queryPage;
            switch(((Node)event.getSource()).getId()){
                case "profit_relation_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../QueryPages/getProfitRelation.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    query_pane.getChildren().add(queryPage);
                    break;
                case "ai_macine_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../QueryPages/createAIMacine.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    query_pane.getChildren().add(queryPage);
                    break;
                case "express_revenue_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../QueryPages/revenueFromExpressDeliveries.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    query_pane.getChildren().add(queryPage);
                    break;
                case "delis_by_type_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../QueryPages/getNumberOfDeliveriesPerType.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    query_pane.getChildren().add(queryPage);
                    break;
                case "delis_by_dp_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../QueryPages/getDeliveriesByPerson.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    query_pane.getChildren().add(queryPage);
                    break;
                case "order_waiting_time_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../QueryPages/orderWaitingTime.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    query_pane.getChildren().add(queryPage);
                    break;
                case "popular_ingredients_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../QueryPages/getPopularComponent.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    query_pane.getChildren().add(queryPage);
                    break;
                case "cooks_by_expertise_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../QueryPages/getCooksByExpertise.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    query_pane.getChildren().add(queryPage);
                    break;
                case "relevant_dish_list_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../QueryPages/getRelevantDishList.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    query_pane.getChildren().add(queryPage);
                    break;

            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


}