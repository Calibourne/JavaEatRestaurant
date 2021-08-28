package View.QueryPages;

import Model.Dish;
import Model.Restaurant;
import Utils.DocxWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class getProfitRelationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pnlQueries;

    @FXML
    private ListView<String> query_result;

    @FXML
    private Button generate_doc_button;

    @FXML
    private Label success_label;

    Restaurant rest = Restaurant.getInstance();
    @FXML
    void initialize() {
        assert pnlQueries != null : "fx:id=\"pnlQueries\" was not injected: check your FXML file 'getProfitRelation.fxml'.";
        assert query_result != null : "fx:id=\"query_result\" was not injected: check your FXML file 'getProfitRelation.fxml'.";

        try {
            success_label.setText("");

            //Reset the result list each time a query is called upon
            if(!(query_result.getItems().isEmpty())){

                query_result.getItems().clear();
            }

            if(!(rest.getProfitRelation().isEmpty())){

                List<String> results = rest.getProfitRelation().stream().map(Dish::toString).toList();
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

    @FXML
    void generateDOCX(ActionEvent event) {
        List<Dish> ts = Restaurant.getInstance().getProfitRelation().stream().toList();
        String[] prompt = new String[ts.size()+1];
        prompt[0] = "SUBTITLE-CENTER~(Dish price / minute of preparation)";
        for (int i = 1; i < prompt.length-1; i++) {
            prompt[i] = String.format("%s profit: %.2f ₪/min",ts.get(i).getDishName(),ts.get(i-1).getPrice() / (1.0 *ts.get(i-1).getTimeToMake()));
            if(i > 0) {
                if (i == 1) {
                    prompt[1] = "🥇 " + prompt[1];
                } else if (i == 2) {
                    prompt[2] = "🥈 " + prompt[2];
                } else if (i == 3) {
                    prompt[3] = "🥉 " + prompt[3];
                } else if (i > 3) {
                    prompt[i] = i + ") " + prompt[i];
                }
                prompt[i] = "-CENTER~" + prompt[i];
            }
        }
        DocxWriter.getInstance().writeToDocx("profits.docx", "JavaEat Profits List", prompt);

        success_label.setStyle("-fx-text-fill: #00ff00");
        success_label.setText("Document generated successfully");
    }
}