package View.ViewStructuresPages;

import Model.DeliveryArea;
import Model.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewDeliveryAreasController {

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
        assert pnlQueries != null : "fx:id=\"pnlQueries\" was not injected: check your FXML file 'ViewDeliveryAreas.fxml'.";
        assert query_result != null : "fx:id=\"query_result\" was not injected: check your FXML file 'ViewDeliveryAreas.fxml'.";

        try {

            //Reset the result list each time a query is called upon
            if(!(query_result.getItems().isEmpty())){

                query_result.getItems().clear();
            }

            Restaurant rest = Restaurant.getInstance();

            if(!(rest.getAreas().isEmpty())){

                List<String> results = rest.getAreas().values().stream().map(DeliveryArea::toString).toList();
                results.forEach(r->query_result.getItems().add(r));

            }
            else{
                if(!(query_result.getItems().isEmpty())){

                    query_result.getItems().clear();
                }
                query_result.getItems().add("There are no delivery areas to show at the moment.");

            }


        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }
}