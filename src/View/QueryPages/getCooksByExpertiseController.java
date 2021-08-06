package View.QueryPages;

import Model.Cook;
import Model.Dish;
import Model.Restaurant;
import Utils.Expertise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;
import java.util.List;

public class getCooksByExpertiseController {

    @FXML
    private AnchorPane pnlQueries;

    @FXML
    private ComboBox<Expertise> expertise_combobox;

    @FXML
    private ListView<String> query_result;

    @FXML
    private Button query_button;



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

    // not working atm for some reason
    public void buttonPressed(ActionEvent event){
        try {

            //Daniel's attempt at clearing the list for each button press, doesn't work atm
            if(!(query_result.getItems().isEmpty())){

                query_result.getItems().clear();
            }

            Restaurant rest = Restaurant.getInstance();

            if(!(rest.getCooksByExpertise(expertise_combobox.getSelectionModel().getSelectedItem()).values().isEmpty())){

                List<String> results = rest.getCooksByExpertise(expertise_combobox.getValue()).values().stream().map(Cook::toString).toList();
                results.forEach(r->query_result.getItems().add(r));

            }
            else{
                if(!(query_result.getItems().isEmpty())){

                    query_result.getItems().clear();
                }
                query_result.getItems().add("No cooks available for the selected expertise");

            }


        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }

    }
}