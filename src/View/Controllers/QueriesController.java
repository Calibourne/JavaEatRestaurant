package View.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

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
    void handleButtonClick(ActionEvent event) {

    }

    // not working ,need to fix (button functions)
//    public void initialize(){
//        initAiMacine();
//    }
//
//
//
//    public void initAiMacine(){
//        try{
//            Node aiMacinePage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../fxmls/createAIMacine.fxml")));
//            query_pane.getChildren().add(aiMacinePage);
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }
//    }

}