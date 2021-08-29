package View.Controllers;

import Utils.SFXManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ViewStructuresController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button customers_button;

    @FXML
    private Button ingredients_button;

    @FXML
    private Button cooks_button;

    @FXML
    private Button dishes_button;

    @FXML
    private Button orders_button;

    @FXML
    private Button deliveries_button;

    @FXML
    private Button dp_button;

    @FXML
    private Button da_button;

    @FXML
    private Button blacklist_button;

    @FXML
    private GridPane content_pane;


    @FXML
    void initialize() {
        assert customers_button != null : "fx:id=\"customers_button\" was not injected: check your FXML file 'ViewStructures.fxml'.";
        assert ingredients_button != null : "fx:id=\"ingredients_button\" was not injected: check your FXML file 'ViewStructures.fxml'.";
        assert cooks_button != null : "fx:id=\"cooks_button\" was not injected: check your FXML file 'ViewStructures.fxml'.";
        assert dishes_button != null : "fx:id=\"dishes_button\" was not injected: check your FXML file 'ViewStructures.fxml'.";
        assert orders_button != null : "fx:id=\"orders_button\" was not injected: check your FXML file 'ViewStructures.fxml'.";
        assert deliveries_button != null : "fx:id=\"deliveries_button\" was not injected: check your FXML file 'ViewStructures.fxml'.";
        assert dp_button != null : "fx:id=\"dp_button\" was not injected: check your FXML file 'ViewStructures.fxml'.";
        assert da_button != null : "fx:id=\"da_button\" was not injected: check your FXML file 'ViewStructures.fxml'.";
        assert blacklist_button != null : "fx:id=\"blacklist_button\" was not injected: check your FXML file 'ViewStructures.fxml'.";
        assert content_pane != null : "fx:id=\"content_pane\" was not injected: check your FXML file 'ViewStructures.fxml'.";

    }

    // method(s) used to switch between scenes within a stage
    @FXML
    public void queryButtonPushed(ActionEvent event) {
        try{
            Node queryPage;
            switch(((Node)event.getSource()).getId()){
                case "customers_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ViewStructuresPages/ViewCustomers.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    content_pane.getChildren().add(queryPage);
                    break;
                case "ingredients_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ViewStructuresPages/ViewIngredients.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    content_pane.getChildren().add(queryPage);
                    break;
                case "cooks_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ViewStructuresPages/ViewCooks.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    content_pane.getChildren().add(queryPage);
                    break;
                case "dp_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ViewStructuresPages/ViewDeliveryPersons.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    content_pane.getChildren().add(queryPage);
                    break;
                case "dishes_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ViewStructuresPages/ViewDishes.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    content_pane.getChildren().add(queryPage);
                    break;
                case "orders_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ViewStructuresPages/ViewOrders.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    content_pane.getChildren().add(queryPage);
                    break;
                case "deliveries_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ViewStructuresPages/ViewDeliveries.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    content_pane.getChildren().add(queryPage);
                    break;
                case "da_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ViewStructuresPages/ViewDeliveryAreas.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    content_pane.getChildren().add(queryPage);
                    break;
                case "blacklist_button":
                    queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../ViewStructuresPages/ViewBlacklist.fxml")));
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    content_pane.getChildren().add(queryPage);
                    break;

            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}