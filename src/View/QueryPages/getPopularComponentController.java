package View.QueryPages;

import Model.Component;
import Model.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

/**
 * This is a controller class for the getPopularComponent query page
 * @author Daniel Sharon
 */
public class getPopularComponentController {

    @FXML
    private AnchorPane pnlQueries;

    @FXML
    private ListView<String> query_result;

    public void initialize(){
        try {

            //Reset the result list each time a query is called upon
            if(!(query_result.getItems().isEmpty())){

                query_result.getItems().clear();
            }

            Restaurant rest = Restaurant.getInstance();

            if(!(rest.getPopularComponents().isEmpty())){

                List<String> results = rest.getPopularComponents().stream().map(Component::toString).toList();
                results.forEach(r->query_result.getItems().add(r));

            }
            else{
                if(!(query_result.getItems().isEmpty())){

                    query_result.getItems().clear();
                }
                query_result.getItems().add("There are no ingredient to show at the moment.");

            }

        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }
}