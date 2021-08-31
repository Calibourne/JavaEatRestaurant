package View.Controllers;

import Model.Dish;
import Model.Restaurant;
import Utils.DishType;
import Utils.SFXManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This controller is used to display the restaurant menu in the customer stage
 * @author Daniel Sharon
 */
public class CustomerMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane pnlQueries;

    @FXML
    private Tab appetizers_button;

    @FXML
    private ListView<String> appetizers_list;

    @FXML
    private Tab main_dishes_button;

    @FXML
    private ListView<String> main_dishes_list;

    @FXML
    private Tab desserts_button;

    @FXML
    private ListView<String> desserts_list;

    @FXML
    void initialize() {
        assert pnlQueries != null : "fx:id=\"pnlQueries\" was not injected: check your FXML file 'CustomerMenu.fxml'.";
        assert appetizers_button != null : "fx:id=\"appetizers_button\" was not injected: check your FXML file 'CustomerMenu.fxml'.";
        assert appetizers_list != null : "fx:id=\"appetizers_list\" was not injected: check your FXML file 'CustomerMenu.fxml'.";
        assert main_dishes_button != null : "fx:id=\"main_dishes_button\" was not injected: check your FXML file 'CustomerMenu.fxml'.";
        assert main_dishes_list != null : "fx:id=\"main_dishes_list\" was not injected: check your FXML file 'CustomerMenu.fxml'.";
        assert desserts_button != null : "fx:id=\"desserts_button\" was not injected: check your FXML file 'CustomerMenu.fxml'.";
        assert desserts_list != null : "fx:id=\"desserts_list\" was not injected: check your FXML file 'CustomerMenu.fxml'.";
    }

    /**
     * A method that makes the appetizers button display the proper dish list
     */
    @FXML
    public void appetizersButtonPressed(){
        try {
            Restaurant rest = Restaurant.getInstance();
            appetizers_list.getItems().clear();

            for (Dish dish : rest.getDishes().values()) {
                if(dish.getType().equals(DishType.Starter)){
                    appetizers_list.getItems().add(dish.toString());
                }
            }
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * A method that makes the mains button display the proper dish list
     */
    @FXML
    public void mainsButtonPressed(){
        try {
            Restaurant rest = Restaurant.getInstance();
            main_dishes_list.getItems().clear();

            for (Dish dish : rest.getDishes().values()) {
                if(dish.getType().equals(DishType.Main)){
                    main_dishes_list.getItems().add(dish.toString());
                }
            }
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * A method that makes the dessert button display the proper dish list
     */
    @FXML
    public void dessertButtonPressed(){
        try {
            Restaurant rest = Restaurant.getInstance();
            desserts_list.getItems().clear();

            for (Dish dish : rest.getDishes().values()) {
                if(dish.getType().equals(DishType.Dessert)){
                    desserts_list.getItems().add(dish.toString());
                }
            }
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }

    }
}