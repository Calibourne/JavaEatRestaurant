package View.QueryPages;

import Model.Restaurant;
import Utils.Expertise;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;

public class getCooksByExpertiseController {

    @FXML
    private AnchorPane pnlQueries;

    @FXML
    private ComboBox<Expertise> expertise_combobox;

    @FXML
    private void initialize(){
        try{
            expertise_combobox.getItems().addAll(Arrays.stream(Expertise.values()).toList());

        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }
}