package View.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QueriesController implements Initializable {

    @FXML
    public ComboBox<String> queryBox;


    //ObservableList<String> list = FXCollections.observableArrayList("one", "two", "three");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //queryBox.setItems(list);
        queryBox.getItems().add("Get relevant dish list");
        queryBox.getItems().add("Get cooks by expertise");
        queryBox.getItems().add("Get popular ingredients");
        //queryBox.getItems().add("Order waiting time"); might be unnecessary here
        queryBox.getItems().add("Get deliveries by delivery person");
        queryBox.getItems().add("Get number of deliveries per type");
        queryBox.getItems().add("Revenue from express deliveries");
        queryBox.getItems().add("Get profit relation");
        queryBox.getItems().add("Create AI machine");

    }
}