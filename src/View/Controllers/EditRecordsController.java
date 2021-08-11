package View.Controllers;

import Model.*;
import Model.Record;
import Utils.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.CheckListView;

import java.util.Arrays;

public class EditRecordsController {

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
    private Group editCooks_sctn;
    @FXML
    private ComboBox<Expertise> expertises_combo;
    @FXML
    private CheckBox isChef_check;
    // endregion
    // region Delivery Person attributes
    @FXML
    private Group editDeliPersons_sctn;
    @FXML
    private ComboBox<Vehicle> vehicles_combo;
    @FXML
    private ComboBox<DeliveryArea> deliveryAreas_combo;
    // endregion
    // region Customer attributes
    @FXML
    private Group editCustomers_sctn;
    @FXML
    private ComboBox<Neighberhood> neighbourhoods_combo;
    @FXML
    private CheckBox glutenIntolerant_check;
    @FXML
    private CheckBox lactoseIntolerant_check;
    // endregion
    // region Ingredient attributes
    @FXML
    private Group editComponents_sctn;
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
    private Group editDishes_sctn;
    @FXML
    private TextField dishName_field;
    @FXML
    private TextField dishPrepareTime_field;
    @FXML
    private ComboBox<DishType> dishType_combo;
    @FXML
    private CheckListView<ListedRecord> components_checkedList;
    // endregion
    // region Order attributes
    @FXML
    private Group editOrders_sctn;
    @FXML
    private ComboBox<Customer> customers_combo;
    @FXML
    private ComboBox<Delivery> deliveries_combo;
    @FXML
    private CheckListView<ListedRecord> dishes_checkedList;
    // endregion
    // region Delivery attributes
    @FXML
    private Group editDeliveries_sctn;
    @FXML
    private ComboBox<DeliveryPerson> deliveryPersons_combo;
    @FXML
    private DatePicker deliveryDate_dp;
    @FXML
    private CheckBox isDelivered_check;
    // region Regular Delivery attributes
    @FXML
    private VBox rd_vbox;
    @FXML
    private CheckListView<ListedRecord> orders_checkedList;
    // endregion
    // region Express Delivery attributes
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
    private Group editAreas_sctn;
    @FXML
    private TextField areaName_field;
    @FXML
    private TextField deliveryTime_field;
    @FXML
    private CheckListView<Neighberhood> neighbourhoods_checkedList;
    // endregion
    @FXML
    private ComboBox<Record> records_combo;
    @FXML
    GridPane info_grid;
    @FXML
    private Button submit;
    // endregion

    @FXML
    private void initialize(){
        Restaurant rest = Restaurant.getInstance();
        try{
            info_grid.setVisible(false);
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
        if(editCooks_sctn != null){
            records_combo.getItems().addAll(rest.getCooks().values().stream().toList());
            records_combo.valueProperty().addListener((opt, oldValue, newValue)->{
                if(!newValue.equals(oldValue)){
                    try{
                        fname_field.setText(((Person)newValue).getFirstName());
                        lname_field.setText(((Person)newValue).getLastName());
                        genders_combo.setValue(((Person)newValue).getGender());
                        genders_combo.getItems().addAll(Arrays.stream(Gender.values()).toList());
                        birthDate_dp.setValue(((Person)newValue).getBirthDay());
                        expertises_combo.setValue(((Cook)newValue).getExpert());
                        expertises_combo.getItems().addAll(Arrays.stream(Expertise.values()).toList());
                        isChef_check.setSelected(((Cook)newValue).isChef());
                    }
                    catch (NullPointerException e){
                        System.out.println(e.getMessage());
                    }
                }
            });
        }
        if(editDeliPersons_sctn != null){
            records_combo.getItems().addAll(Restaurant.getInstance().getDeliveryPersons().values());
            records_combo.valueProperty().addListener((opt, oldValue, newValue)->{
                        try{
                            fname_field.setText(((Person)newValue).getFirstName());
                            lname_field.setText(((Person)newValue).getLastName());
                            genders_combo.setValue(((Person)newValue).getGender());
                            genders_combo.getItems().addAll(Arrays.stream(Gender.values()).toList());
                            birthDate_dp.setValue(((Person)newValue).getBirthDay());
                            vehicles_combo.setValue(((DeliveryPerson)newValue).getVehicle());
                            vehicles_combo.getItems().addAll(Arrays.stream(Vehicle.values()).toList());
                            deliveryAreas_combo.setValue(((DeliveryPerson)newValue).getArea());
                            deliveryAreas_combo.getItems().addAll(Restaurant.getInstance().getAreas().values());
                            info_grid.setVisible(true);
                        }
                        catch (NullPointerException e){
                            System.out.println(e.getMessage());
                        }
            });
        }
        if(editCustomers_sctn != null){
            records_combo.getItems().addAll(Restaurant.getInstance().getCustomers().values());
            records_combo.valueProperty().addListener((opt, oldValue, newValue)->{
                try{
                    fname_field.setText(((Person)newValue).getFirstName());
                    lname_field.setText(((Person)newValue).getLastName());
                    genders_combo.setValue(((Person)newValue).getGender());
                    genders_combo.getItems().addAll(Arrays.stream(Gender.values()).toList());
                    birthDate_dp.setValue(((Person)newValue).getBirthDay());
                    neighbourhoods_combo.setValue(((Customer)newValue).getNeighberhood());
                    neighbourhoods_combo.getItems().addAll(Arrays.stream(Neighberhood.values()).toList());
                    glutenIntolerant_check.setSelected(((Customer)newValue).isSensitiveToGluten());
                    lactoseIntolerant_check.setSelected(((Customer)newValue).isSensitiveToLactose());
                    info_grid.setVisible(true);
                }
                catch (NullPointerException e){
                    System.out.println(e.getMessage());
                }
            });
        }
        if(editComponents_sctn != null){
            records_combo.getItems().addAll(Restaurant.getInstance().getComponents().values());
            records_combo.valueProperty().addListener((opt, oldValue, newValue)->{
                try{
                    ingredientName_field.setText(((Component)newValue).getComponentName());
                    ingredientPrice_field.setText(String.format("%.2f",((Component)newValue).getPrice()));
                    hasGluten_check.setSelected(((Component)newValue).isHasGluten());
                    hasLactose_check.setSelected(((Component)newValue).isHasLactose());
                    info_grid.setVisible(true);
                }
                catch (NullPointerException | ClassCastException e){
                    System.out.println(e.getMessage());
                }
            });
        }
        if(editDishes_sctn != null){
            records_combo.getItems().addAll(Restaurant.getInstance().getDishes().values());
            records_combo.valueProperty().addListener((opt, oldValue, newValue)->{
                try{
                    dishName_field.setText(((Dish)newValue).getDishName());
                    dishPrepareTime_field.setText(String.format("%d",((Dish)newValue).getTimeToMake()));
                    dishType_combo.setValue(((Dish)newValue).getType());
                    dishType_combo.getItems().addAll(Arrays.stream(DishType.values()).toList());
                    components_checkedList.getItems().clear();
                    components_checkedList.getItems().addAll(((Dish)newValue).getComponents()
                            .stream().map(ListedRecord::new).toList());
                    info_grid.setVisible(true);
                }
                catch (NullPointerException | ClassCastException e){
                    System.out.println(e.getMessage());
                }
            });
        }
        if(editOrders_sctn != null){
            records_combo.getItems().addAll(Restaurant.getInstance().getOrders().values());
            records_combo.valueProperty().addListener((opt, oldValue, newValue)->{
                try{
                    customers_combo.setValue(((Order)newValue).getCustomer());
                    customers_combo.getItems().addAll(rest.getCustomers().values());
                    deliveries_combo.getItems().addAll(
                            rest.getDeliveries().values().stream().filter(d -> d instanceof RegularDelivery).toList()
                    );
                    deliveries_combo.setValue(((Order)newValue).getDelivery());
                    dishes_checkedList.getItems().clear();
                    dishes_checkedList.getItems().addAll(((Order)newValue).getDishes()
                            .stream().map(ListedRecord::new).toList());
                    info_grid.setVisible(true);
                }
                catch (NullPointerException | ClassCastException e){
                    System.out.println(e.getMessage());
                }
            });
        }
        if(editDeliveries_sctn != null){
            records_combo.getItems().addAll(Restaurant.getInstance().getDeliveries().values());
            records_combo.valueProperty().addListener((opt, oldValue, newValue)->{
                try{
                    deliveryPersons_combo.setValue(((Delivery)newValue).getDeliveryPerson());
                    deliveryPersons_combo.getItems().addAll(rest.getDeliveryPersons().values());
                    deliveryDate_dp.setValue(((Delivery)newValue).getDeliveryDate());
                    isDelivered_check.setSelected(((Delivery)newValue).isDelivered());
                    if(newValue instanceof RegularDelivery){
                        rd_vbox.setVisible(true);
                        ed_vbox.setVisible(false);
                        orders_checkedList.getItems().addAll(((RegularDelivery) newValue).getOrders().
                                stream().map(ListedRecord::new).toList());
                    }
                    else{
                        ed_vbox.setVisible(true);
                        rd_vbox.setVisible(false);
                        orders_combo.setValue(((ExpressDelivery)newValue).getOrder());
                        orders_combo.getItems().addAll(rest.getOrders().values());
                        expressFee_field.setText(String.format("%.2f",((ExpressDelivery)newValue).getPostage()));
                    }
                    info_grid.setVisible(true);
                }
                catch (NullPointerException | ClassCastException e){
                    System.out.println(e.getMessage());
                }
            });
        }
        if(editAreas_sctn != null){
            records_combo.getItems().addAll(Restaurant.getInstance().getAreas().values());
            records_combo.valueProperty().addListener((opt, oldValue, newValue)->{
                try{

                    info_grid.setVisible(true);
                }
                catch (NullPointerException | ClassCastException e){
                    System.out.println(e.getMessage());
                }
            });
        }
    }

    @FXML
    private void handleButtonClick(ActionEvent e){

    }
}
