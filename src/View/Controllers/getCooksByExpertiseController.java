package View.Controllers;

import Model.Cook;
import Model.Restaurant;
import Utils.Expertise;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;
import java.util.List;

/**
 * This is a controller class for the getCooksByExpertise query page
 * @author Daniel Sharon
 */

public class getCooksByExpertiseController {

    @FXML
    private AnchorPane pnlQueries;

    @FXML
    private ComboBox<Expertise> expertise_combobox;

    @FXML
    private ListView<String> query_result;


    @FXML
    private void initialize(){
        try{
            expertise_combobox.getItems().addAll(Arrays.stream(Expertise.values()).toList());
            expertise_combobox.valueProperty().addListener((opt, oldValue, newValue)->{
                if(!newValue.equals(oldValue)){
                    Restaurant rest = Restaurant.getInstance();
                    query_result.getItems().clear();
                    if(Arrays.stream(Expertise.values()).anyMatch(e->e.equals(newValue))) {
                        List<String> res = rest.getCooksByExpertise(newValue).values().stream().map(Cook::toString).toList();
                        res.forEach(r -> query_result.getItems().add(r));
                        if(res.size() == 0){
                            query_result.getItems().add("No cooks available for the selected expertise");
                        }
                    }
                }
            });

        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }
}