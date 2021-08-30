package View.QueryPages;

import Model.Order;
import Model.Restaurant;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * This is a controller class for the orderWaitingTime query page
 * @author Daniel Sharon
 */

public class orderWaitingTimeController {

    @FXML
    private AnchorPane pnlQueries;

    @FXML
    private ComboBox<Order> order_combobox;

    @FXML
    private Label query_result;

    @FXML
    private void initialize(){
        try{
            if(query_result.getText().length()>0){
                query_result.setText("");
            }
            Restaurant restaurant = Restaurant.getInstance();
            order_combobox.getItems().addAll(restaurant.getOrders().values());

            order_combobox.valueProperty().addListener((opt, oldValue, newValue)->{
                if(!newValue.equals(oldValue)){
                    try{
                        query_result.setText("");
                        if(restaurant.getOrders().containsValue(newValue)) {
                            query_result.setText("Estimated time for the selected order is "+newValue.orderWaitingTime(newValue.getDelivery().getArea())+" minutes");

                        }

                    }catch (NullPointerException ex) {
                        if (newValue.getDelivery() == null)
                            query_result.setText("The order has not been assigned to a delivery yet");
                        else if (newValue.getDelivery().getArea() == null)
                            query_result.setText("The order has not been assigned to a delivery area yet");
                        else
                            System.out.println(ex.getMessage());

                    }
                }
            });


        }catch (NullPointerException ex){


            System.out.println(ex.getMessage());
        }
    }
}