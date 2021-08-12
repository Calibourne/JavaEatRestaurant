package View.CustomerStage;

import Model.Customer;
import Model.Restaurant;
import View.Controllers.LoginPageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerHomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private StackPane query_pane;

    @FXML
    private URL location;

    @FXML
    private Label username_label;

    @FXML
    private Button homepage_button;

    @FXML
    private Button new_order_button;

    @FXML
    private Button relevant_dish_menu_button;

    @FXML
    private Button btnEditRecords;

    @FXML
    private Button popular_ingredients_button;

    @FXML
    private Button view_menu_button;

    @FXML
    private Button settings_button;

    @FXML
    private Button signout_button;


    Restaurant restaurant = Restaurant.getInstance();
    private Customer customer;

    @FXML
    void initialize() {
        try {
            assert username_label != null : "fx:id=\"username_label\" was not injected: check your FXML file 'CustomerHome.fxml'.";
            assert homepage_button != null : "fx:id=\"homepage_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
            assert new_order_button != null : "fx:id=\"new_order_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
            assert relevant_dish_menu_button != null : "fx:id=\"relevant_dish_menu_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
            assert btnEditRecords != null : "fx:id=\"btnEditRecords\" was not injected: check your FXML file 'CustomerHome.fxml'.";
            assert popular_ingredients_button != null : "fx:id=\"popular_ingredients_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
            assert view_menu_button != null : "fx:id=\"view_menu_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
            assert settings_button != null : "fx:id=\"settings_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
            assert signout_button != null : "fx:id=\"signout_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";

            customer = LoginPageController.getCustomer();
//        username_label.setText("Welcome, \n"+customer.getFirstName()+" "+customer.getLastName()); This makes the label span on two lines
            username_label.setText("Welcome, " + customer.getFirstName() + " " + customer.getLastName());

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
        @FXML
        public void handleButtonClick(ActionEvent event) {
            try{
                Node queryPage;
                switch(((Node)event.getSource()).getId()){
//                    case "profit_relation_button":
//                        queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../QueryPages/getProfitRelation.fxml")));
//                        query_pane.getChildren().add(queryPage);
//                        break;
//                    case "ai_macine_button":
//                        queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../QueryPages/createAIMacine.fxml")));
//                        query_pane.getChildren().add(queryPage);
//                        break;
//                    case "express_revenue_button":
//                        queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../QueryPages/revenueFromExpressDeliveries.fxml")));
//                        query_pane.getChildren().add(queryPage);
//                        break;
//                    case "delis_by_type_button":
//                        queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../QueryPages/getNumberOfDeliveriesPerType.fxml")));
//                        query_pane.getChildren().add(queryPage);
//                        break;
//                    case "delis_by_dp_button":
//                        queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../QueryPages/getDeliveriesByPerson.fxml")));
//                        query_pane.getChildren().add(queryPage);
//                        break;
//                    case "order_waiting_time_button":
//                        queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../QueryPages/orderWaitingTime.fxml")));
//                        query_pane.getChildren().add(queryPage);
//                        break;
                    case "popular_ingredients_button":
                        queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../CustomerStage/getPopularComponent.fxml")));
                        query_pane.getChildren().add(queryPage);
                        break;
//                    case "cooks_by_expertise_button":
//                        queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../QueryPages/getCooksByExpertise.fxml")));
//                        query_pane.getChildren().add(queryPage);
//                        break;
//                    case "relevant_dish_list_button":
//                        queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../QueryPages/getRelevantDishList.fxml")));
//                        query_pane.getChildren().add(queryPage);
//                        break;

                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }


    public void signout(){
        try {
            Stage s = (Stage) signout_button.getScene().getWindow();
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