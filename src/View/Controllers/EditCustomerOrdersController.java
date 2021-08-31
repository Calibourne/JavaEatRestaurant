package View.Controllers;

import Model.*;
import Model.Requests.EditRecordRequest;
import Utils.SFXManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckListView;

import java.net.URL;
import java.util.*;

/**
 * This controller serves the page that appears when the customer presses the edit order button in the order history page
 * @authors Eddie Kanevsky, Daniel Sharon
 */
public class EditCustomerOrdersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Group editOrders_sctn;

    @FXML
    private GridPane info_grid;

    @FXML
    private ComboBox<Dish> addComponents_combo;

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
    private Label alert_lbl;

    @FXML
    private GridPane alert_grid;

    /**
     * This method serves the different buttons that are in the edit orders page
     */
    @FXML
    void handleButtonClick(ActionEvent event) {
        if(event.getSource() == submit) {
            try {
                if (dishes_checkedList.getItems().size() == 0)
                    throw new NullPointerException();
                Order o = CustomerCartAndHistoryController.getOrder_to_change();
                EditRecordRequest request = new EditRecordRequest(o,
                        o.getCustomer(), dishes_checkedList.getItems().stream().map(ListedRecord::getRecord).toList());
                request.saveRequest();
                info_grid.setVisible(false);
                alert_grid.setVisible(true);
            } catch (IllegalArgumentException ex) {
                alert_lbl.setText("Please fill out all the required fields");
                SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
                System.out.println(ex.getMessage());
            } catch (NullPointerException ex){
                alert_lbl.setText("Please add at least 1 dish to the order");
                SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            }
        }
        if(event.getSource() == plus_btn){
            addComponents_combo.getItems().clear();
            addComponents_combo.getItems().addAll(Restaurant.getInstance().getDishes().values());
            addComponents_combo.setVisible(true);
        }
        if(event.getSource() == minus_btn){
            Set<ListedRecord> selectedItems = new HashSet<>(dishes_checkedList.getCheckModel().getCheckedItems());
            if(selectedItems.size() > 0) {
                dishes_checkedList.getItems().removeAll(selectedItems);
                dishes_checkedList.getCheckModel().clearChecks();
            }
        }
    }

    @FXML
    void initialize() {
        assert editOrders_sctn != null : "fx:id=\"editOrders_sctn\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert info_grid != null : "fx:id=\"info_grid\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert addComponents_combo != null : "fx:id=\"addComponents_combo\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert dishes_checkedList != null : "fx:id=\"dishes_checkedList\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert minus_btn != null : "fx:id=\"minus_btn\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert plus_btn != null : "fx:id=\"plus_btn\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert ingredients_vbox != null : "fx:id=\"ingredients_vbox\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert dish_name != null : "fx:id=\"dish_name\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert dish_id != null : "fx:id=\"dish_id\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert addSubcomponents_combo != null : "fx:id=\"addSubcomponents_combo\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert dishesIngredients_checkedList != null : "fx:id=\"dishesIngredients_checkedList\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert Iminus_btn != null : "fx:id=\"Iminus_btn\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert addSubcomp_btn != null : "fx:id=\"addSubcomp_btn\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert Iplus_btn != null : "fx:id=\"Iplus_btn\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert submit != null : "fx:id=\"submit\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert alert_lbl != null : "fx:id=\"alert_lbl\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        assert alert_grid != null : "fx:id=\"alert_grid\" was not injected: check your FXML file 'EditCustomerOrders.fxml'.";
        Restaurant rest = Restaurant.getInstance();
        Order order = CustomerCartAndHistoryController.getOrder_to_change();
        if(order!=null){
            System.out.println(order);
            dishes_checkedList.getItems().addAll(order.getDishes().stream().map(ListedRecord::new).toList());
            addComponents_combo.getItems().addAll(rest.getDishes().values());
            addSubcomponents_combo.getItems().addAll(rest.getComponents().values());
            addComponents_combo.setOnAction(action->{
                Dish d = addComponents_combo.getValue();
                if(d != null) {
                    dishesIngredients_checkedList.getItems().addAll(
                            d.getComponents().stream().map(ListedRecord::new).toList()
                    );
                    dish_id.setText(""+d.getId());
                    ingredients_vbox.setVisible(true);
                    addComponents_combo.setVisible(false);
                    dish_name.setText(d.getDishName()+" ingredients: ");
                }
            });

            addSubcomponents_combo.setOnAction(action -> {
                Component c = addSubcomponents_combo.getValue();
                if(c != null) {
                    dishesIngredients_checkedList.getItems().add(new ListedRecord(c));
                    addSubcomponents_combo.setVisible(false);
                }
            });
            addSubcomp_btn.setOnAction(action -> {
                List<Component> selectedList = dishesIngredients_checkedList.getItems()
                        .stream().map(ListedRecord::getRecord).map(r->(Component)r).toList(),
                        dishList = rest.getRealDish(Integer.parseInt(dish_id.getText())).getComponents()
                                .stream().sorted(Comparator.comparing(Component::getId)).toList();
                if(dishList.equals(selectedList)){
                    dishes_checkedList.getItems().add(
                            new ListedRecord(rest.getRealDish(Integer.parseInt(dish_id.getText())))
                    );
                }
                else {
                    Dish d = rest.getRealDish(Integer.parseInt(dish_id.getText()));
                    dishes_checkedList.getItems().add(
                            new ListedRecord(new Dish("custom made " + d.getDishName() ,d.getType(), new ArrayList<>(dishList), d.getTimeToMake()))
                    );
                }
                dishesIngredients_checkedList.getItems().clear();
                ingredients_vbox.setVisible(false);
            });
            Iplus_btn.setOnAction(action->{
                addSubcomponents_combo.getItems().clear();
                addSubcomponents_combo.getItems().addAll(rest.getComponents().values());
                addSubcomponents_combo.setVisible(true);
            });
            Iminus_btn.setOnAction(action -> {
                Set<ListedRecord> set = new HashSet<>(dishesIngredients_checkedList.getCheckModel().getCheckedItems());
                dishesIngredients_checkedList.getCheckModel().clearChecks();
                dishesIngredients_checkedList.getItems().removeAll(set);
            });
        }
    }
}