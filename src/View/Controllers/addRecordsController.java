package View.Controllers;

import Model.*;
import Model.Record;
import Model.Requests.AddRecordRequest;
import Utils.*;
import impl.org.controlsfx.collections.ReadOnlyUnbackedObservableList;
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
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    private TextField dishPrepareTime_field;
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
    private DatePicker deliveryDate_dp;
    @FXML
    private CheckBox isDelivered_check;
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
            Pattern intPattern = Pattern.compile("([0-9]+)?");
            Pattern doublePattern = Pattern.compile("((([1-9])(\\d*)|0)(\\.\\d*)?)?");
            Pattern stringPattern = Pattern.compile("(([a-zA-Z]*)([ -]?)([a-zA-Z]*))*");
            if(addCooks_sctn!=null || addDeliPersons_sctn!=null || addCustomers_sctn!=null) {
                /** see {@link ControllerUtils} for more details*/
                fname_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
                lname_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
                genders_combo.getItems().addAll(Arrays.stream(Gender.values()).toList());
                genders_combo.setAccessibleText("Choose gender:");

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
            }
            if(addComponents_sctn!=null){
                ingredientName_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
                ingredientPrice_field.setTextFormatter(ControllerUtils.textFormatter(doublePattern));
                ingredientPrice_field.setOnKeyTyped(ke->{
                    Pattern err = Pattern.compile("((([1-9])(\\d*)|0)(\\.))");
                    if(err.matcher(ingredientPrice_field.getText()).matches()){
                        ingredientPrice_field.setStyle("-fx-text-fill: goldenrod");
                    }
                    else{
                        ingredientPrice_field.setStyle("-fx-text-fill: white");
                    }
                });
            }
            if(addDishes_sctn!=null){
                dishType_combo.getItems().addAll(Arrays.stream(DishType.values()).toList());
                dishType_combo.setPromptText("Choose dish type:");
                components_checkedCombo.getItems().addAll(rest.getComponents().values());
                components_checkedCombo.setTitle("Choose ingredients:");
                dishName_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
                dishPrepareTime_field.setTextFormatter(ControllerUtils.textFormatter(intPattern));
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
                areaName_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
                areaName_field.textProperty().addListener((ov, oldValue, newValue)->{
                    if(oldValue.length()>0){
                        oldValue.substring(0,1).toUpperCase();
                    }
                });
                deliveryTime_field.setTextFormatter(ControllerUtils.textFormatter(intPattern));
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
                Integer deliveryTime = deliveryTime_field.getText().length()>0? Integer.parseInt(deliveryTime_field.getText()) :null;
                request = new AddRecordRequest(
                        new DeliveryArea(-1),
                        areaName,
                        selectedNeighbourhoods,
                        deliveryTime
                );
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
                            gender,
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
                Integer ingredientPrice = ingredientPrice_field.getText().length()>0? Integer.valueOf(ingredientPrice_field.getText()) :null;
                boolean hasGluten = hasGluten_check.isSelected();
                boolean hasLactose = hasLactose_check.isSelected();
                request = new AddRecordRequest(
                        new Component(-1),
                        ingredientName,
                        hasGluten,
                        hasLactose,
                        ingredientPrice
                );
                ingredientName_field.clear();
                ingredientPrice_field.clear();
                hasGluten_check.setSelected(false);
                hasLactose_check.setSelected(false);
            }
            if (addDishes_sctn != null) {
                String dishName = dishName_field.getText().length()>0?dishName_field.getText():null;
                int dishPrepareTime = Integer.parseInt(dishPrepareTime_field.getText());
                DishType dishType = dishType_combo.getValue();
                ReadOnlyUnbackedObservableList<Component> selectedItems =
                        (ReadOnlyUnbackedObservableList<Component>) components_checkedCombo.getCheckModel().getCheckedItems();
                ArrayList<Component> selectedComponents = new ArrayList<>(selectedItems.stream().toList());
                request = new AddRecordRequest(new Dish(-1), dishName, dishType, selectedComponents, dishPrepareTime);
                dishType_combo.getSelectionModel().clearSelection();
                components_checkedCombo.getCheckModel().clearChecks();
                dishName_field.clear();
            }
            if (addOrders_sctn != null) {
                Customer customer = customers_combo.getValue();
                Delivery delivery = deliveries_combo.getValue();
                ReadOnlyUnbackedObservableList<Dish> selectedItems =
                        (ReadOnlyUnbackedObservableList<Dish>) dishes_checkedCombo.getCheckModel().getCheckedItems();
                ArrayList<Dish> selectedDishes = new ArrayList<>(selectedItems.stream().toList());
                if (delivery!= null)
                    request = new AddRecordRequest(new Order(-1), customer, selectedDishes, delivery);
                else
                    request = new AddRecordRequest(
                            new Order(-1),
                            customer,
                            selectedDishes
                    );
                customers_combo.getSelectionModel().clearSelection();
                deliveries_combo.getSelectionModel().clearSelection();
                dishes_checkedCombo.getCheckModel().clearChecks();
            }
            if (addDeliveries_sctn != null) {
                DeliveryPerson deliveryPerson = deliveryPersons_combo.getValue();
                DeliveryArea area = deliveryPerson.getArea();
                LocalDate deliveryDate = deliveryDate_dp.getValue();
                boolean isDelivered = isDelivered_check.isSelected();
                if(rd_RB.isSelected()) {
                    ReadOnlyUnbackedObservableList<Order> selectedItems =
                            (ReadOnlyUnbackedObservableList<Order>) orders_checkedCombo.getCheckModel().getCheckedItems();
                    TreeSet<Order> selectedOrders = new TreeSet<>(selectedItems.stream().toList());
                    request = new AddRecordRequest(
                            new RegularDelivery(-1),
                            selectedOrders,
                            deliveryPerson,
                            area,
                            isDelivered,
                            deliveryDate
                    );
                    orders_checkedCombo.getCheckModel().clearChecks();
                }
                else {
                    Order order = orders_combo.getValue();
                    int fee = Integer.parseInt(expressFee_field.getText());
                    request = new AddRecordRequest(
                            new ExpressDelivery(-1),
                            order,
                            deliveryPerson,
                            area,
                            isDelivered,
                            deliveryDate
                    );
                    orders_combo.getSelectionModel().clearSelection();
                    expressFee_field.clear();
                }
            }
            if (addToBlacklist_sctn != null) {
                Customer toBlacklist = customersToBlacklist_combo.getValue();
                request = new AddRecordRequest(toBlacklist);
                request.saveRequest();
                Restaurant.getInstance().saveDatabase("Rest.ser");
                customersToBlacklist_combo.getSelectionModel().clearSelection();
                customersToBlacklist_combo.setValue(null);
                customersToBlacklist_combo.setPromptText("Choose customer to blacklist");
                System.out.printf("%s added to blacklist\n", toBlacklist);
                return;
            }
            request.saveRequest();
            Restaurant.getInstance().saveDatabase("Rest.ser");
            System.out.printf("%s was added successfully\n", request.getRecord());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}