package View.Controllers;

import Model.Record;
import Model.*;
import Model.Requests.EditRecordRequest;
import Utils.*;
import View.newElements.imageListCell;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A controller that controls the structures edit pages
 * @author Eddie Kanevsky
 */
public class EditRecordsController extends RecordManagementController{

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
    @FXML
    private Button img_choose;
    @FXML
    private ImageView img_source;
    @FXML
    private PasswordField npass_field;
    @FXML
    private PasswordField rpass_field;
    @FXML
    private Label pass_alert;
    @FXML
    private TextField usernameField;
    @FXML
    private Label username_alert;
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
    private ListView<ListedRecord> components_checkedList;
    // endregion
    // region Order attributes
    @FXML
    private Group editOrders_sctn;
    @FXML
    private ComboBox<Customer> customers_combo;
    @FXML
    private ComboBox<Delivery> deliveries_combo;
    @FXML
    private ListView<ListedRecord> dishes_checkedList;
    @FXML
    private ComboBox<Component> addSubcomponents_combo;
    @FXML
    private ListView<ListedRecord> dishesIngredients_checkedList;
    @FXML
    private Button Iplus_btn;
    @FXML
    private Button Iminus_btn;
    @FXML
    private Label dish_id;
    @FXML
    private Label dish_name;
    @FXML
    private VBox ingredients_vbox;
    @FXML
    private Button addSubcomp_btn;
    @FXML
    private Label orderPrice_lbl;
    @FXML
    private Label dishPrice_lbl;
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
    private ListView<Order> orders_checkedList;
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
    private ListView<Neighberhood> neighbourhoods_checkedList;
    // endregion
    @FXML
    private ComboBox<Record> records_combo;
    @FXML
    private GridPane info_grid;
    @FXML
    private GridPane alert_grid;
    @FXML
    private ComboBox addComponents_combo;
    @FXML
    private Button minus_btn;
    @FXML
    private Button plus_btn;
    @FXML
    private Button submit;
    @FXML
    private Label alert_lbl;
    // endregion

    @FXML
    private void initialize(){
        Pattern intPattern = Pattern.compile("([0-9]+)?");
        Pattern doublePattern = Pattern.compile("((([1-9])(\\d*)|0)(\\.\\d*)?)?");
        Pattern stringPattern = Pattern.compile("(([a-zA-Z]*)([ \\-']?)([a-zA-Z]*))*");
        try{
            info_grid.setVisible(false);
            addComponents_combo.setOnAction(action->{
                if(addComponents_combo.getSelectionModel().getSelectedItem() != null){
                    try{
                            if (components_checkedList != null) {
                                Record r = (Record) addComponents_combo.getSelectionModel().getSelectedItem();
                                components_checkedList.getItems().add(new ListedRecord(r));
                            }
                            if (dishes_checkedList != null) {
                                Record r = (Record) addComponents_combo.getSelectionModel().getSelectedItem();
                                dishes_checkedList.getItems().add(new ListedRecord(r));
                            }
                            if (orders_checkedList != null) {
                                Record r = (Record) addComponents_combo.getSelectionModel().getSelectedItem();
                                orders_checkedList.getItems().add((Order)r);
                            }
                            if (neighbourhoods_checkedList != null) {
                                Neighberhood n = (Neighberhood) addComponents_combo.getSelectionModel().getSelectedItem();
                                neighbourhoods_checkedList.getItems().add(n);
                            }
                            addComponents_combo.setVisible(false);
                    }
                    catch (NullPointerException e){

                    }
                }
            });
        }
        catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
        if(editCooks_sctn != null){
            initCooksPage(stringPattern);
        }
        if(editDeliPersons_sctn != null){
            initDeliveryPersonsPage(stringPattern);
        }
        if(editCustomers_sctn != null){
            initCustomersPage(stringPattern);
        }
        if(editComponents_sctn != null){
            initIngredientPage(stringPattern, doublePattern);
        }
        if(editDishes_sctn != null){
            initDishesPage(stringPattern, intPattern);
        }
        if(editOrders_sctn != null){
            initOrdersPage();
        }
        if(editDeliveries_sctn != null){
            initDeliveriesPage(doublePattern);
        }
        if(editAreas_sctn != null){
            initAreasPage(stringPattern);
        }
    }

    /**
     * Makes the most buttons clickable (the ones that mostly appear)
     * @param e
     * the event of mouse btn clicked
     */
    @FXML
    private void handleButtonClick(ActionEvent e){
        if(e.getSource() == minus_btn) {
            if(components_checkedList != null){
                Set<ListedRecord> selectedItems = new HashSet<>(components_checkedList.getSelectionModel().getSelectedItems());
                if(selectedItems.size() > 0) {
                    components_checkedList.getItems().removeAll(selectedItems);
                    components_checkedList.getSelectionModel().clearSelection();
                }
            }
            if(dishes_checkedList != null){
                Set<ListedRecord> selectedItems = new HashSet<>(dishes_checkedList.getSelectionModel().getSelectedItems());
                if(selectedItems.size() > 0) {
                    dishes_checkedList.getItems().removeAll(selectedItems);
                    dishes_checkedList.getSelectionModel().clearSelection();
                }
            }
            if(orders_checkedList != null){
                Set<Order> selectedItems = new HashSet<>(orders_checkedList.getSelectionModel().getSelectedItems());
                if(selectedItems.size() > 0) {
                    orders_checkedList.getItems().removeAll(selectedItems);
                    orders_checkedList.getSelectionModel().clearSelection();
                }
            }
            if(neighbourhoods_checkedList != null){
                Set<Neighberhood> selectedItems = new HashSet<>(neighbourhoods_checkedList.getSelectionModel().getSelectedItems());
                if(selectedItems.size() > 0) {
                    neighbourhoods_checkedList.getSelectionModel().clearSelection();
                    neighbourhoods_checkedList.getItems().removeAll(selectedItems);
                }
            }
        }
        if(e.getSource() == plus_btn) {
            if (components_checkedList != null) {
                addComponents_combo.getItems().clear();
                addComponents_combo.getItems().addAll(getRestaurant().getComponents().values());
                addComponents_combo.setVisible(true);
            }
            if (dishes_checkedList != null) {
                addComponents_combo.getItems().clear();
                addComponents_combo.getItems().addAll(getRestaurant().getDishes().values());
                addComponents_combo.setVisible(true);
            }
            if (orders_checkedList != null) {
                addComponents_combo.getItems().clear();
                List<Order> list = orders_checkedList.getItems();
                addComponents_combo.getItems().addAll(getRestaurant().getOrders().values()
                        .stream().filter(o->!list.contains(o))
                        .toList()
                );
                addComponents_combo.setVisible(true);
            }
            if (neighbourhoods_checkedList != null) {
                addComponents_combo.getItems().clear();
                List<Neighberhood> list = neighbourhoods_checkedList.getItems().stream().toList();
                addComponents_combo.getItems().addAll(Arrays.stream(Neighberhood.values()).toList()
                        .stream().filter(n->!list.contains(n))
                        .toList()
                );
                addComponents_combo.setVisible(true);
            }
        }
        if(e.getSource() == submit) {
            editRecords();
        }
    }

    /**
     * attempts to edit a chosen record according to given arguments
     */
    private void editRecords() {
        EditRecordRequest request = null;
        if(editAreas_sctn != null){
            request = editAreaRequest();
        }
        if(editCooks_sctn != null) {
            request = editCookRequest();
        }
        if(editDeliPersons_sctn != null) {
            request = editDeliveryPersonRequest();
        }
        if(editCustomers_sctn != null) {
            request = editCustomerRequest();
        }
        if(editComponents_sctn != null) {
            request = editComponentRequest();
        }
        if(editDishes_sctn != null) {
            request = editDishRequest();
        }
        if(editOrders_sctn != null) {
            request = editOrderRequest();
        }
        if(editDeliveries_sctn != null) {
            request = editDeliveryRequest();
        }
        if(request!=null) {
            if(request.saveRequest())
                getRestaurant().saveDatabase("Rest.ser");
        }
    }

    /**
     * sets the first and last name fields of the person selected
     * @param record
     * the person we want to edit
     * @param stringPattern
     * the input restriction pattern
     */
    private void setNames(Record record, Pattern stringPattern){
        lname_field.setTextFormatter(null);
        fname_field.setTextFormatter(null);
        fname_field.setText("");
        fname_field.setText(((Person)record).getFirstName());
        fname_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
        ControllerUtils.setAlerts(fname_field, stringPattern, alert_lbl);

        lname_field.setText("");
        lname_field.setText(((Person)record).getLastName());
        lname_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
        ControllerUtils.setAlerts(lname_field, stringPattern, alert_lbl);
    }


    /**
     * sets the first and last name fields of the person selected
     * @param record
     * the person we want to edit
     * @param stringPattern
     * the input restriction pattern
     */
    private void setPersonAttributes(Record record, Pattern stringPattern){
        setNames(record, stringPattern);

        genders_combo.getItems().clear();
        genders_combo.getItems().addAll(Arrays.stream(Gender.values()).toList());
        genders_combo.setValue(((Person)record).getGender());

        birthDate_dp.setValue(((Person)record).getBirthDay());
        birthDate_dp.setConverter(ControllerUtils.getStringConverter());
        birthDate_dp.setPromptText("dd/mm/yyyy");

    }

    /**
     * Initializes and populates the fields of cooks page
     * @param stringPattern
     * pattern that restricts user input
     */
    private void initCooksPage(Pattern stringPattern){
        records_combo.getItems().addAll(getRestaurant().getCooks().values().stream().toList());
        records_combo.valueProperty().addListener((opt, oldValue, newValue)->{
            try {
                if (!newValue.equals(oldValue)) {
                    try {
                        setPersonAttributes(newValue, stringPattern);

                        expertises_combo.getItems().clear();
                        expertises_combo.getItems().addAll(Arrays.stream(Expertise.values()).toList());
                        expertises_combo.setValue(((Cook) newValue).getExpert());

                        isChef_check.setSelected(((Cook) newValue).isChef());

                        info_grid.setVisible(true);
                        alert_grid.setVisible(false);
                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        });
    }

    /**
     * Initializes and populates the fields of customers page
     * @param stringPattern
     * pattern that restricts user input
     */
    private void initCustomersPage(Pattern stringPattern){
        records_combo.setCellFactory(list-> new imageListCell<>());
        records_combo.getItems().addAll(getRestaurant().getCustomers().values());
        records_combo.valueProperty().addListener((opt, oldValue, newValue)->{
            try{
                setPersonAttributes(newValue, stringPattern);

                neighbourhoods_combo.getItems().clear();
                neighbourhoods_combo.getItems().addAll(Arrays.stream(Neighberhood.values()).toList());
                neighbourhoods_combo.setValue(((Customer)newValue).getNeighberhood());

                glutenIntolerant_check.setSelected(((Customer)newValue).isSensitiveToGluten());
                lactoseIntolerant_check.setSelected(((Customer)newValue).isSensitiveToLactose());

                img_source.setImage(SwingFXUtils.toFXImage(((Customer)newValue).getProfileImg(false), null));
                ControllerUtils.setFileChooser(img_choose, img_source);
                usernameField.setText("");
                npass_field.setText("");
                rpass_field.setText("");
                info_grid.setVisible(true);
                alert_grid.setVisible(false);
            }
            catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        });
        npass_field.textProperty().addListener((obs, o, n)->{
            if(npass_field.getText().equals(rpass_field.getText())){
                if(npass_field.getText().equals(""))
                    pass_alert.setText("");
                else{
                    pass_alert.setStyle("-fx-text-fill: #00ff00");
                    pass_alert.setText("Passwords match 👍");
                }
            }
            else{
                pass_alert.setStyle("-fx-text-fill: red");
                pass_alert.setText("Passwords don't match 😟");
            }
        });
        rpass_field.textProperty().addListener((obs, o, n)->{
            if(npass_field.getText().equals(rpass_field.getText())){
                pass_alert.setStyle("-fx-text-fill: #00ff00");
                pass_alert.setText("Passwords match 👍");
            }
            else{
                pass_alert.setStyle("-fx-text-fill: red");
                pass_alert.setText("Passwords don't match 😟");
            }
        });
        usernameField.textProperty().addListener(c->{
            if(usernameField.getText().length()>0) {
                if (getRestaurant().getUsersList().get(usernameField.getText()) != null) {
                    username_alert.setStyle("-fx-text-fill: red");
                    username_alert.setText("This username is taken");
                }
                else
                {
                    username_alert.setStyle("-fx-text-fill: #00ff00");
                    username_alert.setText("This username is available");
                }
            }
            else
                username_alert.setText("");

        });
    }

    /**
     * Initializes and populates the fields of delivery persons pages
     * @param stringPattern
     * pattern that restricts user input
     */
    private void initDeliveryPersonsPage(Pattern stringPattern){
        records_combo.getItems().addAll(getRestaurant().getDeliveryPersons().values());
        records_combo.valueProperty().addListener((opt, oldValue, newValue)->{
            try{
                setPersonAttributes(newValue, stringPattern);

                vehicles_combo.getItems().clear();
                vehicles_combo.setValue(((DeliveryPerson)newValue).getVehicle());
                vehicles_combo.getItems().addAll(Arrays.stream(Vehicle.values()).toList());

                deliveryAreas_combo.getItems().clear();
                deliveryAreas_combo.setValue(((DeliveryPerson)newValue).getArea());
                deliveryAreas_combo.getItems().addAll(getRestaurant().getAreas().values());

                info_grid.setVisible(true);
                alert_grid.setVisible(false);
            }
            catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        });
    }

    /**
     * Initializes and populates the fields of ingredients page
     * @param stringPattern
     * pattern that restricts user input for ingredient name
     * @param doublePattern
     * pattern that restricts user input for ingredient price
     */
    private void initIngredientPage(Pattern stringPattern, Pattern doublePattern){
        records_combo.getItems().addAll(getRestaurant().getComponents().values());
        records_combo.valueProperty().addListener((opt, oldValue, newValue)->{
            try{
                ingredientName_field.setText(((Component)newValue).getComponentName());
                ingredientPrice_field.setText(String.format("%.2f",((Component)newValue).getPrice()));

                ingredientName_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
                ControllerUtils.setAlerts(ingredientName_field, stringPattern, alert_lbl);
                ingredientPrice_field.setTextFormatter(ControllerUtils.textFormatter(doublePattern));
                ControllerUtils.setAlerts(ingredientPrice_field, doublePattern, alert_lbl);

                hasGluten_check.setSelected(((Component)newValue).isHasGluten());
                hasLactose_check.setSelected(((Component)newValue).isHasLactose());

                info_grid.setVisible(true);
                alert_grid.setVisible(false);
            }
            catch (NullPointerException | ClassCastException e){
                System.out.println(e.getMessage());
            }
        });
    }

    /**
     * Initializes and populates the fields of dishes page
     * @param stringPattern
     * pattern that restricts user input for dish name
     * @param intPattern
     * pattern that restricts user input for dish time to make
     */
    private void initDishesPage(Pattern stringPattern, Pattern intPattern){
        records_combo.getItems().addAll(getRestaurant().getDishes().values());
        records_combo.valueProperty().addListener((opt, oldValue, newValue)->{
            try{
                dishName_field.setText(((Dish)newValue).getDishName());
                dishName_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
                ControllerUtils.setAlerts(dishName_field, stringPattern, alert_lbl);

                dishPrepareTime_field.setText(String.format("%d",((Dish)newValue).getTimeToMake()));
                dishPrepareTime_field.setTextFormatter(ControllerUtils.textFormatter(intPattern));
                ControllerUtils.setAlerts(dishPrepareTime_field, intPattern, alert_lbl);

                dishType_combo.getItems().clear();
                dishType_combo.getItems().addAll(Arrays.stream(DishType.values()).toList());
                dishType_combo.setValue(((Dish)newValue).getType());

                components_checkedList.getItems().clear();
                components_checkedList.getItems().addAll(((Dish)newValue).getComponents()
                        .stream().map(ListedRecord::new).toList());
                addComponents_combo.setVisible(false);

                info_grid.setVisible(true);
                alert_grid.setVisible(false);
            }
            catch (NullPointerException | ClassCastException e){
                System.out.println(e.getMessage());
            }
        });
    }

    /**
     * Initializes and populates the fields of orders page
     */
    private void initOrdersPage(){
        records_combo.getItems().addAll(getRestaurant().getOrders().values());
        records_combo.valueProperty().addListener((opt, oldValue, newValue)->{
            try{
                customers_combo.setCellFactory(list->new imageListCell<>());
                customers_combo.setValue(((Order)newValue).getCustomer());
                customers_combo.getItems().addAll(getRestaurant().getCustomers().values().stream()
                        .filter(c->!getRestaurant().getBlacklist().contains(c)).toList());

                deliveries_combo.getItems().addAll(
                        getRestaurant().getDeliveries().values().stream().filter(d -> d instanceof RegularDelivery).toList()
                );
                deliveries_combo.setValue(((Order)newValue).getDelivery());

                dishes_checkedList.getItems().clear();
                dishes_checkedList.getItems().addAll(((Order)newValue).getDishes()
                        .stream().map(ListedRecord::new).toList());

                addComponents_combo.setVisible(false);

                info_grid.setVisible(true);
                alert_grid.setVisible(false);
            }
            catch (NullPointerException | ClassCastException e){
                System.out.println(e.getMessage());
            }
        });
        ControllerUtils.initOrderListViews(
                addSubcomponents_combo,
                addComponents_combo,
                dishesIngredients_checkedList,
                dishes_checkedList,
                addSubcomp_btn,
                Iplus_btn,
                Iminus_btn,
                dish_id,
                dishPrice_lbl,
                dish_name,
                orderPrice_lbl,
                ingredients_vbox
        );
    }

    /**
     * Initializes and populates the fields of deliveries page
     */
    private void initDeliveriesPage(Pattern doublePattern){
        records_combo.getItems().addAll(getRestaurant().getDeliveries().values());
        records_combo.valueProperty().addListener((opt, oldValue, newValue)->{
            try{
                deliveryPersons_combo.getItems().clear();
                deliveryPersons_combo.setValue(((Delivery)newValue).getDeliveryPerson());
                deliveryPersons_combo.getItems().addAll(getRestaurant().getDeliveryPersons().values());

                deliveryDate_dp.setValue(((Delivery)newValue).getDeliveryDate());
                deliveryDate_dp.setConverter(ControllerUtils.getStringConverter());
                deliveryDate_dp.setPromptText("dd/mm/yyyy");

                isDelivered_check.setSelected(((Delivery)newValue).isDelivered());

                if(newValue instanceof RegularDelivery){
                    rd_vbox.setVisible(true);
                    ed_vbox.setVisible(false);

                    orders_checkedList.getItems().clear();
                    orders_checkedList.getItems().addAll(((RegularDelivery) newValue).getOrders());

                    addComponents_combo.setVisible(false);
                }
                else{
                    ed_vbox.setVisible(true);
                    rd_vbox.setVisible(false);

                    orders_combo.getItems().clear();
                    orders_combo.setValue(((ExpressDelivery)newValue).getOrder());
                    orders_combo.getItems().addAll(getRestaurant().getOrders().values());

                    expressFee_field.setText(String.format("%.2f",((ExpressDelivery)newValue).getPostage()));
                }

                info_grid.setVisible(true);
                alert_grid.setVisible(false);
            }
            catch (NullPointerException | ClassCastException e){
                System.out.println(e.getMessage());
            }
        });
    }

    /**
     * Initializes and populates the fields of delivery areas page
     */
    private void initAreasPage(Pattern stringPattern){
        records_combo.getItems().addAll(getRestaurant().getAreas().values());
        records_combo.valueProperty().addListener((opt, oldValue, newValue)->{
            try{
                areaName_field.setText(((DeliveryArea)newValue).getAreaName());
                areaName_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
                ControllerUtils.setAlerts(areaName_field, stringPattern, alert_lbl);

                neighbourhoods_checkedList.getItems().clear();
                neighbourhoods_checkedList.getItems().addAll(((DeliveryArea)newValue).getNeighberhoods());

                addComponents_combo.setVisible(false);

                info_grid.setVisible(true);
                alert_grid.setVisible(false);
            }
            catch (NullPointerException | ClassCastException e){
                System.out.println(e.getMessage());
            }
        });
    }

    /**
     * attempts to edit a delivery area based on parameters entered and puts it in record request for analytics purposes
     * if fails to create one, shows alert to the user
     * @return
     * the created record request
     */
    public EditRecordRequest editAreaRequest(){
        try{
            if(neighbourhoods_checkedList.getItems().size() == 0)
                throw new NullPointerException();
            EditRecordRequest request = new EditRecordRequest(records_combo.getValue(), areaName_field.getText(),
                    neighbourhoods_checkedList.getItems().stream().collect(Collectors.toSet()));
            records_combo.getItems().clear();
            records_combo.getItems().addAll(getRestaurant().getAreas().values());
            info_grid.setVisible(false);
            alert_grid.setVisible(true);
            return request;
        }
        catch (IllegalArgumentException ex){
            alert_lbl.setText("Please fill all required fields");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            System.out.println(ex.getMessage());
            return null;
        }
        catch (NullPointerException ex){
            alert_lbl.setText("Please add at least 1 neighbourhood to the delivery area");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * attempts to edit a cook based on parameters entered and puts it in record request for analytics purposes.
     * if fails to create one, shows alert to the user
     * @return
     * the created record request
     */
    private EditRecordRequest editCookRequest(){
        try{
            EditRecordRequest request = new EditRecordRequest(records_combo.getValue(),
                    fname_field.getText(), lname_field.getText(), genders_combo.getValue(), birthDate_dp.getValue(),
                    expertises_combo.getValue(),isChef_check.isSelected());
            records_combo.getItems().clear();
            records_combo.getItems().addAll(getRestaurant().getCooks().values());
            info_grid.setVisible(false);
            alert_grid.setVisible(true);
            return request;
        }catch (IllegalArgumentException ex){
            alert_lbl.setText("Please fill all required fields");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * attempts to edit a delivery person based on parameters entered and puts it in record request for analytics purposes.
     * if fails to create one, shows alert to the user
     * @return
     * the created record request
     */
    private EditRecordRequest editDeliveryPersonRequest(){
        try{
            EditRecordRequest request = new EditRecordRequest(records_combo.getValue(),
                    fname_field.getText(), lname_field.getText(), genders_combo.getValue(), birthDate_dp.getValue(),
                    vehicles_combo.getValue(), deliveryAreas_combo.getValue());
            request.saveRequest();
            records_combo.getItems().clear();
            records_combo.getItems().addAll(getRestaurant().getDeliveryPersons().values());
            info_grid.setVisible(false);
            alert_grid.setVisible(true);
            return request;
        }
        catch (IllegalArgumentException ex){
            alert_lbl.setText("Please fill all required fields");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * attempts to edit a customer based on parameters entered and puts it in record request for analytics purposes.
     * if fails to create one, shows alert to the user
     * @return
     * the created record request
     */
    private EditRecordRequest editCustomerRequest(){
        try{
            EditRecordRequest request;
            if(!npass_field.getText().equals(rpass_field.getText()))
                throw new IllegalArgumentException();
            if(getRestaurant().getUsersList().get(usernameField.getText())!=null)
                throw new IllegalArgumentException();
            if(npass_field.getText().length()==0 && usernameField.getText().length()==0)
                request = new EditRecordRequest(records_combo.getValue(),
                        fname_field.getText(), lname_field.getText(), genders_combo.getValue(), birthDate_dp.getValue(),
                        neighbourhoods_combo.getValue(), glutenIntolerant_check.isSelected(), lactoseIntolerant_check.isSelected(), img_source.getImage());
            else if(npass_field.getText().length()>0)
                request = new EditRecordRequest(records_combo.getValue(),
                        fname_field.getText(), lname_field.getText(), genders_combo.getValue(), birthDate_dp.getValue(),
                        neighbourhoods_combo.getValue(), glutenIntolerant_check.isSelected(), lactoseIntolerant_check.isSelected(), img_source.getImage(),
                        npass_field.getText());
            else if(usernameField.getText().length()>0)
                request = new EditRecordRequest(records_combo.getValue(),
                        fname_field.getText(), lname_field.getText(), genders_combo.getValue(), birthDate_dp.getValue(),
                        neighbourhoods_combo.getValue(), glutenIntolerant_check.isSelected(), lactoseIntolerant_check.isSelected(), img_source.getImage(),
                        ((Customer)records_combo.getValue()).getPassword(), usernameField.getText());
            else
                request = new EditRecordRequest(records_combo.getValue(),
                        fname_field.getText(), lname_field.getText(), genders_combo.getValue(), birthDate_dp.getValue(),
                        neighbourhoods_combo.getValue(), glutenIntolerant_check.isSelected(), lactoseIntolerant_check.isSelected(), img_source.getImage(),
                        npass_field.getText(), usernameField.getText());
            records_combo.getItems().clear();
            records_combo.getItems().addAll(getRestaurant().getCustomers().values());
            info_grid.setVisible(false);
            alert_grid.setVisible(true);
            return request;
        }
        catch (IllegalArgumentException | NullPointerException ex){
            alert_lbl.setText("Please fill all required fields");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * attempts to edit an ingredient based on parameters entered and puts it in record request for analytics purposes.
     * if fails to create one, shows alert to the user
     * @return
     * the created record request
     */
    private EditRecordRequest editComponentRequest(){
        try{
            EditRecordRequest request = new EditRecordRequest(records_combo.getValue(),
                    ingredientName_field.getText(), Double.parseDouble(ingredientPrice_field.getText()),
                    hasGluten_check.isSelected(), hasLactose_check.isSelected());
            records_combo.getItems().clear();
            records_combo.getItems().addAll(getRestaurant().getComponents().values());
            info_grid.setVisible(false);
            alert_grid.setVisible(true);
            return request;
        }catch (IllegalArgumentException ex){
            alert_lbl.setText("Please fill all required fields");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * attempts to edit a dish based on parameters entered and puts it in record request for analytics purposes.
     * if fails to create one, shows alert to the user
     * @return
     * the created record request
     */
    private EditRecordRequest editDishRequest(){
        try{
            if(components_checkedList.getItems().size()==0)
                throw new NullPointerException();
            EditRecordRequest request = new EditRecordRequest(records_combo.getValue(),
                    dishName_field.getText(), dishType_combo.getValue(), Integer.parseInt(dishPrepareTime_field.getText()),
                    components_checkedList.getItems().stream().map(ListedRecord::getRecord).toList());
            records_combo.getItems().clear();
            records_combo.getItems().addAll(getRestaurant().getDishes().values());
            info_grid.setVisible(false);
            alert_grid.setVisible(true);
            return request;
        }
        catch (IllegalArgumentException ex){
            alert_lbl.setText("Please fill all required fields");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            System.out.println(ex.getMessage());
            return null;
        }
        catch (NullPointerException ex){
            alert_lbl.setText("Please add at least 1 ingredient to the dish");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            return null;
        }
    }

    /**
     * attempts to edit an order based on parameters entered and puts it in record request for analytics purposes.
     * if fails to create one, shows alert to the user
     * @return
     * the created record request
     */
    private EditRecordRequest editOrderRequest(){
        try{
            if(dishes_checkedList.getItems().size() == 0)
                throw new NullPointerException();
            EditRecordRequest request;
            if(deliveries_combo.getValue() == null)
                request = new EditRecordRequest(records_combo.getValue(),
                        customers_combo.getValue(), dishes_checkedList.getItems().stream().map(ListedRecord::getRecord).toList());
            else
                request = new EditRecordRequest(records_combo.getValue(),
                        customers_combo.getValue(), dishes_checkedList.getItems().stream().map(ListedRecord::getRecord).toList(),
                        deliveries_combo.getValue());
            records_combo.getItems().clear();
            records_combo.getItems().addAll(getRestaurant().getOrders().values());
            info_grid.setVisible(false);
            alert_grid.setVisible(true);
            return request;
        }
        catch (IllegalArgumentException ex){
            alert_lbl.setText("Please fill all required fields");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            return null;
        }
        catch (NullPointerException ex){
            alert_lbl.setText("Please add at least 1 dish to the order");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            return null;
        }
    }

    /**
     * attempts to edit a delivery based on parameters entered and puts it in record request for analytics purposes.
     * if fails to create one, shows alert to the user
     * @return
     * the created record request
     */
    private EditRecordRequest editDeliveryRequest(){
        try{
            if(orders_checkedList.getItems().size() == 0)
                throw new NullPointerException();
            EditRecordRequest request;
            Delivery d = (Delivery) records_combo.getValue();
            if(d instanceof RegularDelivery){
                request = new EditRecordRequest(d,

                        deliveryPersons_combo.getValue(),
                        deliveryDate_dp.getValue(), isDelivered_check.isSelected(),
                        new HashSet<>(orders_checkedList.getItems()));
            }
            else{
                request = new EditRecordRequest(d,
                        deliveryAreas_combo.getValue() ,deliveryPersons_combo.getValue(),
                        deliveryDate_dp.getValue(), isDelivered_check.isSelected(),
                        orders_combo.getValue(), Double.parseDouble(expressFee_field.getText()));
            }
            records_combo.getItems().clear();
            records_combo.getItems().addAll(getRestaurant().getDeliveries().values());
            info_grid.setVisible(false);
            alert_grid.setVisible(true);
            return request;
        }catch (IllegalArgumentException ex){
            alert_lbl.setText("Please fill all the required fields");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            return null;
        }catch (NullPointerException ex){
            if(rd_vbox.isVisible())
                alert_lbl.setText("Please add at least 1 order to the delivery");
            else
                alert_lbl.setText("Please fill all the required fields");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            return null;
        }
    }
}