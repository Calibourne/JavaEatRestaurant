package View.CustomerStage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerHomeController {

    @FXML
    private ResourceBundle resources;

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

    @FXML
    void handleButtonClick(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert username_label != null : "fx:id=\"username_label\" was not injected: check your FXML file 'CustomerHome.fxml'.";
        assert homepage_button != null : "fx:id=\"homepage_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
        assert new_order_button != null : "fx:id=\"new_order_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
        assert relevant_dish_menu_button != null : "fx:id=\"relevant_dish_menu_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
        assert btnEditRecords != null : "fx:id=\"btnEditRecords\" was not injected: check your FXML file 'CustomerHome.fxml'.";
        assert popular_ingredients_button != null : "fx:id=\"popular_ingredients_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
        assert view_menu_button != null : "fx:id=\"view_menu_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
        assert settings_button != null : "fx:id=\"settings_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
        assert signout_button != null : "fx:id=\"signout_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";

    }
}