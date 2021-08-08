package View.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
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
    void queryButtonPushed(ActionEvent event) {

    }

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
}