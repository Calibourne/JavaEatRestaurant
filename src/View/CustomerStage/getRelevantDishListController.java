package View.CustomerStage;

import Model.Dish;
import Model.Restaurant;
import View.Controllers.LoginPageController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class getRelevantDishListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pnlQueries;

    @FXML
    private ListView<String> query_result;

    @FXML
    void initialize() {
        try {
            assert pnlQueries != null : "fx:id=\"pnlQueries\" was not injected: check your FXML file 'getRelevantDishList.fxml'.";
            assert query_result != null : "fx:id=\"query_result\" was not injected: check your FXML file 'getRelevantDishList.fxml'.";

            Restaurant rest = Restaurant.getInstance();
            query_result.getItems().clear();

            List<String> res = rest.getReleventDishList(LoginPageController.getCustomer()).stream().map(Dish::toString).toList();
            res.forEach(r -> query_result.getItems().add(r));


        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }

    }
}