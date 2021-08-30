package View.QueryPages;

import Model.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * This is a controller class for the revenueFromExpressDeliveries query page
 * @author Daniel Sharon
 */

public class revenueFromExpressDeliveriesController {

    @FXML
    private AnchorPane pnlQueries;

    @FXML
    private Label query_result;

    public void initialize(){
        try {

            Restaurant restaurant = Restaurant.getInstance();

            //Reset the result list each time a query is called upon
            if(!(query_result.getText().isEmpty())){

                query_result.setText("");
            }

            query_result.setText(String.format("%.2f ₪",restaurant.revenueFromExpressDeliveries()));



        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }

    }

}