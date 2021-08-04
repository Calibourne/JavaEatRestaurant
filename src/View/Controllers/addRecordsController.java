package View.Controllers;

import Model.*;
import Model.Record;
import Model.Requests.AddRecordRequest;
import Utils.*;
import impl.org.controlsfx.collections.ReadOnlyUnbackedObservableList;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

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

                // the following code is taken from
                // https://stackoverflow.com/questions/32346893/javafx-datepicker-not-updating-value
                birthDate_dp.setConverter(new StringConverter<LocalDate>() {
                    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    @Override
                    public String toString(LocalDate localDate) {
                        if(localDate==null)
                            return "";
                        return dateTimeFormatter.format(localDate);
                    }
                    @Override
                    public LocalDate fromString(String dateString) {
                        if(dateString==null || dateString.trim().isEmpty())
                            return null;
                        try{
                            return LocalDate.parse(dateString,dateTimeFormatter);
                        }
                        catch(Exception e){
                            //Bad date value entered
                            return null;
                        }
                    }
                });
                birthDate_dp.setPromptText("dd/mm/yyyy");
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
        if(e.getSource() == submit){
            addRecord();
        }
    }
    private void addRecord(){
        try {
            AddRecordRequest request = new AddRecordRequest(new Record() {
                @Override
                public String description() {
                    return null;
                }
            });
            // The code for selecting the checked items is taken from the following site:
            // https://www.tabnine.com/code/java/classes/org.controlsfx.control.CheckComboBox
            if (addAreas_sctn != null) {
                ReadOnlyUnbackedObservableList<Neighberhood> selectedItems =
                        (ReadOnlyUnbackedObservableList<Neighberhood>) neighbourhoods_checkedCombo.getCheckModel().getCheckedItems();
                HashSet<Neighberhood> selectedNeighbourhoods = new HashSet<>(selectedItems.stream().toList());
                selectedNeighbourhoods = (selectedNeighbourhoods.size()>0)?selectedNeighbourhoods:null;
                String areaName = areaName_field.getText().length()>0?areaName_field.getText():null;
                String deliveryTime = deliveryTime_field.getText().length()>0?deliveryTime_field.getText():null;
                request = new AddRecordRequest(new DeliveryArea(-1), areaName, selectedNeighbourhoods, deliveryTime);
                neighbourhoods_checkedCombo.getCheckModel().clearChecks();
                areaName_field.clear();
                deliveryTime_field.clear();
            }
            if (addCooks_sctn != null || addCustomers_sctn != null || addDeliPersons_sctn != null){
                String fname = fname_field.getText().length()>0?fname_field.getText():null;
                String lname = lname_field.getText().length()>0?lname_field.getText():null;
                Gender gender = genders_combo.getValue();
                LocalDate birthdate = birthDate_dp.getValue();
                if (addCooks_sctn != null) {
                    Expertise expertise = expertises_combo.getValue();
                    boolean isChef = isChef_check.isSelected();
                    request = new AddRecordRequest(
                            new Cook(-1),
                            fname,
                            lname,
                            birthdate,
                            gender,
                            expertise,
                            isChef
                    );
                    expertises_combo.getSelectionModel().clearSelection();
                    isChef_check.setSelected(false);
                }
                if (addDeliPersons_sctn != null) {
                    Vehicle vehicle = vehicles_combo.getValue();
                    DeliveryArea area = deliveryAreas_combo.getValue();
                    request = new AddRecordRequest(
                            new DeliveryPerson(-1),
                            fname,
                            lname,
                            birthdate,
                            gender,
                            vehicle,
                            area
                    );
                    vehicles_combo.getSelectionModel().clearSelection();
                    deliveryAreas_combo.getSelectionModel().clearSelection();
                }
                if (addCustomers_sctn != null) {
                    Neighberhood neighbourhood = neighbourhoods_combo.getValue();
                    boolean isGlutenIntolerant = glutenIntolerant_check.isSelected();
                    boolean isLactoseIntolerant = lactoseIntolerant_check.isSelected();
                    request = new AddRecordRequest(
                            new Customer(-1),
                            fname,
                            lname,
                            birthdate,
                            neighbourhood,
                            isGlutenIntolerant,
                            isLactoseIntolerant
                    );
                    neighbourhoods_combo.getSelectionModel().clearSelection();
                    glutenIntolerant_check.setSelected(false);
                    lactoseIntolerant_check.setSelected(false);
                }
                fname_field.clear();
                lname_field.clear();
                genders_combo.getSelectionModel().clearSelection();
                birthDate_dp.setValue(null);
            }
            if (addComponents_sctn != null) {
                String ingredientName = ingredientName_field.getText().length()>0?ingredientName_field.getText():null;
                String ingredientPrice = ingredientPrice_field.getText().length()>0?ingredientPrice_field.getText():null;
                boolean hasGluten = hasGluten_check.isSelected();
                boolean hasLactose = hasLactose_check.isSelected();
                request = new AddRecordRequest(new Component(-1), ingredientName, ingredientPrice, hasGluten, hasLactose);
                ingredientName_field.clear();
                ingredientPrice_field.clear();
                hasGluten_check.setSelected(false);
                hasLactose_check.setSelected(false);
            }
            if (addDishes_sctn != null) {
                String dishName = dishName_field.getText().length()>0?dishName_field.getText():null;
                DishType dishType = dishType_combo.getValue();
                ReadOnlyUnbackedObservableList<Component> selectedItems =
                        (ReadOnlyUnbackedObservableList<Component>) components_checkedCombo.getCheckModel().getCheckedItems();
                ArrayList<Component> selectedComponents = (ArrayList<Component>) selectedItems.stream().toList();
                request = new AddRecordRequest(new Dish(-1), dishName, dishType, selectedComponents);
                dishType_combo.getSelectionModel().clearSelection();
                selectedItems.clear();
                dishName_field.clear();
            }
            request.saveRequest();
            Restaurant.getInstance().saveDatabase("Rest.ser");
            System.out.printf("%s was added successfully", request.getRecord());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}