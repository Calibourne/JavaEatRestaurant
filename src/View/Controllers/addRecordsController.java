package View.Controllers;

import Model.*;
import Utils.*;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;

import java.util.Arrays;

public class addRecordsController {
    // region Properties
    // region Person attributes
    @FXML
    private TextField fname_field;
    @FXML
    private TextField lname_field;
    @FXML
    private ComboBox<Gender> genders_combo;
    @FXML
    private DatePicker birthDate_dp;
    // endregion
    // region Cook attributes
    @FXML
    private Group addCooks_sctn;
    @FXML
    private ComboBox<Expertise> expertises_combo;
    @FXML
    private CheckBox isChef_check;
    // endregion
    // region Delivery Person attributes
    @FXML
    private Group addDeliPersons_sctn;
    @FXML
    private ComboBox<Vehicle> vehicles_combo;
    @FXML
    private ComboBox<DeliveryArea> deliveryAreas_combo;
    // endregion
    // region Customer attributes
    @FXML
    private Group addCustomers_sctn;
    @FXML
    private ComboBox<Neighberhood> neighbourhoods_combo;
    @FXML
    private CheckBox glutenIntolerant_check;
    @FXML
    private CheckBox lactoseIntolerant_check;
    // endregion
    // region Ingredient attributes
    @FXML
    private Group addComponents_sctn;
    @FXML
    private TextField ingredientName_field;
    @FXML
    private TextField ingredientPrice_field;
    @FXML
    private CheckBox hasGluten_check;
    @FXML
    private CheckBox hasLactose_check;
    // endregion
    // region Dish attributes
    @FXML
    private Group addDishes_sctn;
    @FXML
    private TextField dishName_field;
    @FXML
    private ComboBox<DishType> dishType_combo;
    @FXML
    private CheckComboBox<Component> components_checkedCombo;
    // endregion
    // region Order attributes
    @FXML
    private Group addOrders_sctn;
    @FXML
    private ComboBox<Customer> customers_combo;
    @FXML
    private ComboBox<Delivery> deliveries_combo;
    @FXML
    private CheckComboBox<Dish> dishes_checkedCombo;
    // endregion
    // region Delivery attributes
    @FXML
    private Group addDeliveries_sctn;
    @FXML
    private ComboBox<DeliveryPerson> deliveryPersons_combo;
    @FXML
    private DatePicker dateOfDelivery_DP;
    // region Regular Delivery attributes
    @FXML
    private RadioButton rd_RB;
    @FXML
    private VBox rd_vbox;
    @FXML
    private CheckComboBox<Order> orders_checkedCombo;
    // endregion
    // region Express Delivery attributes
    @FXML
    private RadioButton ed_RB;
    @FXML
    private VBox ed_vbox;
    @FXML
    private ComboBox<Order> orders_combo;
    @FXML
    private TextField expressFee_field;
    // endregion
    // endregion
    // region Delivery Areas attributes
    @FXML
    private Group addAreas_sctn;
    @FXML
    private TextField areaName_field;
    @FXML
    private TextField deliveryTime_field;
    @FXML
    private CheckComboBox<Neighberhood> neighbourhoods_checkedCombo;
    // endregion
    // region Blacklist attributes
    @FXML
    private Group addToBlacklist_sctn;
    @FXML
    private ComboBox<Customer> customersToBlacklist_combo;
    // endregion
    @FXML
    private Button submit;
    // endregion
    @FXML
    private void initialize(){
        try{
            Restaurant rest = Restaurant.getInstance();
            if(addCooks_sctn!=null || addDeliPersons_sctn!=null || addCustomers_sctn!=null) {
                genders_combo.getItems().addAll(Arrays.stream(Gender.values()).toList());
                genders_combo.setPromptText("Choose gender:");
            }
            if(addCooks_sctn!=null){
                expertises_combo.getItems().addAll(Arrays.stream(Expertise.values()).toList());
                expertises_combo.setPromptText("Choose expertise:");
            }
            if(addDeliPersons_sctn!=null){
                vehicles_combo.getItems().addAll(Arrays.stream(Vehicle.values()).toList());
                vehicles_combo.setPromptText("Choose vehicle:");
                deliveryAreas_combo.getItems().addAll(rest.getAreas().values());
                deliveryAreas_combo.setPromptText("Choose delivery area:");
            }
            if(addCustomers_sctn!=null){
                neighbourhoods_combo.getItems().addAll(Arrays.stream(Neighberhood.values()).toList());
                neighbourhoods_combo.setPromptText("Choose neighbourhood:");
            }
            if(addDishes_sctn!=null){
                dishType_combo.getItems().addAll(Arrays.stream(DishType.values()).toList());
                dishType_combo.setPromptText("Choose dish type:");
                components_checkedCombo.getItems().addAll(rest.getComponents().values());
                components_checkedCombo.setTitle("Choose ingredients:");
            }
            if(addOrders_sctn!=null){
                customers_combo.getItems().addAll(rest.getCustomers().values());
                customers_combo.setPromptText("Choose customer:");
                deliveries_combo.getItems().addAll(rest.getDeliveries().values());
                deliveries_combo.setPromptText("Choose delivery:");
                dishes_checkedCombo.getItems().addAll(rest.getDishes().values());
                dishes_checkedCombo.setTitle("Choose dishes:");
            }
            if(addDeliveries_sctn!=null){
                deliveryPersons_combo.getItems().addAll(rest.getDeliveryPersons().values());
                deliveryPersons_combo.setPromptText("Choose delivery person:");
                orders_checkedCombo.getItems().addAll(rest.getOrders().values());
                orders_checkedCombo.setTitle("Choose orders:");
                orders_combo.getItems().addAll(rest.getOrders().values());
                orders_combo.setPromptText("Choose order:");
            }
            if(addAreas_sctn!=null){
                neighbourhoods_checkedCombo.getItems().addAll(Arrays.stream(Neighberhood.values()).toList());
                neighbourhoods_checkedCombo.setTitle("Choose neighbourhoods:");
            }
            if(addToBlacklist_sctn!=null){
                customersToBlacklist_combo.getItems()
                        .addAll(rest.getCustomers().values().stream().
                                filter(c->!rest.getBlacklist().contains(c))
                                .toList());
                customersToBlacklist_combo.setPromptText("Choose customer to blacklist:");
            }
        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    private void handleButtonClick(ActionEvent e){
        if (e.getSource() == ed_RB)
        {
            ed_vbox.setVisible(true);
            rd_vbox.setVisible(false);
        }
        if (e.getSource() == rd_RB)
        {
            ed_vbox.setVisible(false);
            rd_vbox.setVisible(true);
        }
    }
}
