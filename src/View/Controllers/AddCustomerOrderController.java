package View.Controllers;

import Model.Component;
import Model.Dish;
import Model.ListedRecord;
import Model.Restaurant;
import Utils.SFXManager;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckListView;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This Controller is for the AddCustomerOrder page
 * @author Daniel Sharon
 */
public class AddCustomerOrderController extends RecordManagementController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private AnchorPane placeOrder_pane;

    @FXML
    private Group addOrders_sctn;

    @FXML
    private GridPane info_grid;

    @FXML
    private ComboBox addComponents_combo;

    @FXML
    private CheckListView<ListedRecord> dishes_checkedList;

    @FXML
    private Button minus_btn;

    @FXML
    private Button plus_btn;

    @FXML
    private VBox ingredients_vbox;

    @FXML
    private Label dish_name;

    @FXML
    private Label dish_id;

    @FXML
    private Label alert_lbl;

    @FXML
    private Label orderPrice_lbl;

    @FXML
    private Label dishPrice_lbl;

    @FXML
    private ComboBox<Component> addSubcomponents_combo;

    @FXML
    private CheckListView<ListedRecord> dishesIngredients_checkedList;

    @FXML
    private Button Iminus_btn;

    @FXML
    private Button addSubcomp_btn;

    @FXML
    private Button Iplus_btn;

    @FXML
    private Button submit;

    @FXML
    private GridPane alert_grid;

    @FXML
    void initialize() {
        try{

            assert addOrders_sctn != null : "fx:id=\"addOrders_sctn\" was not injected: check your FXML file 'AddCustomerOrder.fxml'.";
            assert info_grid != null : "fx:id=\"info_grid\" was not injected: check your FXML file 'AddCustomerOrder.fxml'.";
            assert addComponents_combo != null : "fx:id=\"addComponents_combo\" was not injected: check your FXML file 'AddCustomerOrder.fxml'.";
            assert dishes_checkedList != null : "fx:id=\"dishes_checkedList\" was not injected: check your FXML file 'AddCustomerOrder.fxml'.";
            assert minus_btn != null : "fx:id=\"minus_btn\" was not injected: check your FXML file 'AddCustomerOrder.fxml'.";
            assert plus_btn != null : "fx:id=\"plus_btn\" was not injected: check your FXML file 'AddCustomerOrder.fxml'.";
            assert ingredients_vbox != null : "fx:id=\"ingredients_vbox\" was not injected: check your FXML file 'AddCustomerOrder.fxml'.";
            assert dish_name != null : "fx:id=\"dish_name\" was not injected: check your FXML file 'AddCustomerOrder.fxml'.";
            assert dish_id != null : "fx:id=\"dish_id\" was not injected: check your FXML file 'AddCustomerOrder.fxml'.";
            assert addSubcomponents_combo != null : "fx:id=\"addSubcomponents_combo\" was not injected: check your FXML file 'AddCustomerOrder.fxml'.";
            assert dishesIngredients_checkedList != null : "fx:id=\"dishesIngredients_checkedList\" was not injected: check your FXML file 'AddCustomerOrder.fxml'.";
            assert Iminus_btn != null : "fx:id=\"Iminus_btn\" was not injected: check your FXML file 'AddCustomerOrder.fxml'.";
            assert addSubcomp_btn != null : "fx:id=\"addSubcomp_btn\" was not injected: check your FXML file 'AddCustomerOrder.fxml'.";
            assert Iplus_btn != null : "fx:id=\"Iplus_btn\" was not injected: check your FXML file 'AddCustomerOrder.fxml'.";
            assert submit != null : "fx:id=\"submit\" was not injected: check your FXML file 'AddCustomerOrder.fxml'.";
            assert alert_grid != null : "fx:id=\"alert_grid\" was not injected: check your FXML file 'AddCustomerOrder.fxml'.";

            orderPrice_lbl.setText("0₪");
            dishes_checkedList.getItems().addListener((ListChangeListener<? super ListedRecord>) change -> {
                try{
                    double price = dishes_checkedList.getItems().stream()
                            .map(ListedRecord::getRecord).map(r -> (Dish) r)
                            .map(Dish::getPrice).reduce(0.0, Double::sum);
                   orderPrice_lbl.setText(String.format("%.2f₪", price));
                }catch (NullPointerException e){
                    orderPrice_lbl.setText("0₪");
                }
            });
            dishPrice_lbl.setText("0₪");
            dishesIngredients_checkedList.getItems().addListener((ListChangeListener<? super ListedRecord>)change -> {
                try{
                    Collection<Component> cmp = dishesIngredients_checkedList.getItems().stream()
                            .map(ListedRecord::getRecord).map(r->(Component)r).collect(Collectors.toList());
                    Dish d = getRestaurant().getRealDish(Integer.parseInt(dish_id.getText()));
                    if(d.getComponents().equals(cmp)){
                        dishPrice_lbl.setText(String.format("%.2f₪", d.getPrice()));
                    }else{
                        double price = dishesIngredients_checkedList.getItems().stream()
                                .map(ListedRecord::getRecord).map(r->(Component)r)
                                .map(Component::getPrice).reduce(0.0, Double::sum);
                        dishPrice_lbl.setText(String.format("%.2f₪", price));
                    }
                }catch(NullPointerException e){
                    dishPrice_lbl.setText("0₪");
                }
            });
            alert_lbl.setText("");
            Restaurant rest = Restaurant.getInstance();
            addSubcomponents_combo.getItems().addAll(rest.getComponents().values());

            addComponents_combo.setOnAction(action->{
                SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                Dish d = (Dish) addComponents_combo.getValue();
                if(d != null) {
                    dishesIngredients_checkedList.getItems().addAll(
                            d.getComponents().stream().map(ListedRecord::new).toList()
                    );
                    dishPrice_lbl.setText(String.format("%.2f₪",d.getPrice()));
                    dish_id.setText(""+d.getId());
                    ingredients_vbox.setVisible(true);
                    addComponents_combo.setVisible(false);
                }
            });

            addSubcomponents_combo.setOnAction(action -> {
                SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                Component c = addSubcomponents_combo.getValue();
                if(c != null) {
                    dishesIngredients_checkedList.getItems().add(new ListedRecord(c));
                    addSubcomponents_combo.setVisible(false);
                }
            });
            addSubcomp_btn.setOnAction(action -> {
                SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                Collection<Component> selectedList = dishesIngredients_checkedList.getItems()
                        .stream().map(ListedRecord::getRecord).map(r->(Component)r).toList(),
                        dishList = rest.getRealDish(Integer.parseInt(dish_id.getText()))
                                .getComponents().stream().toList();
                if(dishList.equals(selectedList)){
                    dishes_checkedList.getItems().add(
                            new ListedRecord(rest.getRealDish(Integer.parseInt(dish_id.getText())))
                    );
                }
                else {
                    Dish d = rest.getRealDish(Integer.parseInt(dish_id.getText()));
                    dishes_checkedList.getItems().add(
                            new ListedRecord(new Dish("Custom " + d.getDishName() ,d.getType(), new ArrayList<>(selectedList), d.getTimeToMake()))
                    );
                }
                dishesIngredients_checkedList.getItems().clear();
                ingredients_vbox.setVisible(false);
            });
            Iplus_btn.setOnAction(action->{
                SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                addSubcomponents_combo.getItems().clear();
                addSubcomponents_combo.getItems().addAll(rest.getComponents().values());
                addSubcomponents_combo.setVisible(true);
            });
            Iminus_btn.setOnAction(action -> {
                SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                Set<ListedRecord> set = new HashSet<>(dishesIngredients_checkedList.getCheckModel().getCheckedItems());
                dishesIngredients_checkedList.getCheckModel().clearChecks();
                dishesIngredients_checkedList.getItems().removeAll(set);
            });
            plus_btn.setOnAction(action->{
                SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                addComponents_combo.getItems().clear();
                dishesIngredients_checkedList.getItems().clear();
                ingredients_vbox.setVisible(false);
                addComponents_combo.getItems().addAll(rest.getDishes().values());
                addComponents_combo.setVisible(true);
            });
            minus_btn.setOnAction(action->{
                SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                if(addOrders_sctn!=null){
                    Set<ListedRecord> set = new HashSet<>(dishes_checkedList.getCheckModel().getCheckedItems());
                    dishes_checkedList.getCheckModel().clearChecks();
                    dishes_checkedList.getItems().removeAll(set);
                }
            });
            submit.setOnAction((action->{
                try {
                    alert_lbl.setText("");
                    Set<ListedRecord> dishes_in_order = new HashSet<>(dishes_checkedList.getItems());
                    if(dishes_in_order.size() == 0)
                        throw new NullPointerException();
                    CustomerCartAndHistoryController.order_in_cart = dishes_in_order;
                    StackPane pane = (StackPane)placeOrder_pane.getParent();
                    Node cart = FXMLLoader.load(getClass().getResource("fxmls/CustomerCartAndHistory.fxml"));
                    pane.getChildren().remove(placeOrder_pane);
                    pane.getChildren().add(cart);
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                }catch (IOException ex){
                    System.out.println("Error Loading");
                    SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
                }
                catch (NullPointerException ex){
                    alert_lbl.setText("Please add dishes to your order first!");
                    SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
                }
            }));
        }catch(NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }
}