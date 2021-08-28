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
import org.controlsfx.control.CheckListView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    @FXML
    private Button img_choose;
    @FXML
    private ImageView img_source;
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
    private CheckListView<Neighberhood> neighbourhoods_checkedList;
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
        Pattern stringPattern = Pattern.compile("(([a-zA-Z]*)([ -]?)([a-zA-Z]*))*");
        Restaurant rest = Restaurant.getInstance();
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
                                orders_checkedList.getItems().add(new ListedRecord(r));
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
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
        if(editCooks_sctn != null){
            records_combo.getItems().addAll(rest.getCooks().values().stream().toList());
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
                    System.out.println("It's normal keep going!");
                }
            });
        }
        if(editDeliPersons_sctn != null){
            records_combo.getItems().addAll(Restaurant.getInstance().getDeliveryPersons().values());
            records_combo.valueProperty().addListener((opt, oldValue, newValue)->{
                        try{
                            setPersonAttributes(newValue, stringPattern);

                            vehicles_combo.getItems().clear();
                            vehicles_combo.setValue(((DeliveryPerson)newValue).getVehicle());
                            vehicles_combo.getItems().addAll(Arrays.stream(Vehicle.values()).toList());

                            deliveryAreas_combo.getItems().clear();
                            deliveryAreas_combo.setValue(((DeliveryPerson)newValue).getArea());
                            deliveryAreas_combo.getItems().addAll(Restaurant.getInstance().getAreas().values());

                            info_grid.setVisible(true);
                            alert_grid.setVisible(false);
                        }
                        catch (NullPointerException e){
                            System.out.println(e.getMessage());
                        }
            });
        }
        if(editCustomers_sctn != null){
            records_combo.setCellFactory(list-> new imageListCell<>());
            records_combo.getItems().addAll(Restaurant.getInstance().getCustomers().values());
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
                    info_grid.setVisible(true);
                    alert_grid.setVisible(false);
                }
                catch (NullPointerException e){
                    e.printStackTrace();
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
        if(editDishes_sctn != null){
            records_combo.getItems().addAll(Restaurant.getInstance().getDishes().values());
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

                    addComponents_combo.setVisible(false);

                    info_grid.setVisible(true);
                    alert_grid.setVisible(false);
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
                    deliveryPersons_combo.getItems().clear();
                    deliveryPersons_combo.setValue(((Delivery)newValue).getDeliveryPerson());
                    deliveryPersons_combo.getItems().addAll(rest.getDeliveryPersons().values());

                    deliveryDate_dp.setValue(((Delivery)newValue).getDeliveryDate());

                    isDelivered_check.setSelected(((Delivery)newValue).isDelivered());

                    if(newValue instanceof RegularDelivery){
                        rd_vbox.setVisible(true);
                        ed_vbox.setVisible(false);

                        orders_checkedList.getItems().clear();
                        orders_checkedList.getItems().addAll(((RegularDelivery) newValue).getOrders().
                                stream().map(ListedRecord::new).toList());

                        addComponents_combo.setVisible(false);
                    }
                    else{
                        ed_vbox.setVisible(true);
                        rd_vbox.setVisible(false);

                        orders_combo.getItems().clear();
                        orders_combo.setValue(((ExpressDelivery)newValue).getOrder());
                        orders_combo.getItems().addAll(rest.getOrders().values());

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
        if(editAreas_sctn != null){
            records_combo.getItems().addAll(Restaurant.getInstance().getAreas().values());
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
    }

    @FXML
    private void handleButtonClick(ActionEvent e){
        if(e.getSource() == minus_btn) {
            if(components_checkedList != null){
                Set<ListedRecord> selectedItems = new HashSet<>(components_checkedList.getCheckModel().getCheckedItems());
                if(selectedItems.size() > 0) {
                    components_checkedList.getItems().removeAll(selectedItems);
                    components_checkedList.getCheckModel().clearChecks();
                }
            }
            if(dishes_checkedList != null){
                Set<ListedRecord> selectedItems = new HashSet<>(dishes_checkedList.getCheckModel().getCheckedItems());
                if(selectedItems.size() > 0) {
                    dishes_checkedList.getItems().removeAll(selectedItems);
                    dishes_checkedList.getCheckModel().clearChecks();
                }
            }
            if(orders_checkedList != null){
                Set<ListedRecord> selectedItems = new HashSet<>(orders_checkedList.getCheckModel().getCheckedItems());
                if(selectedItems.size() > 0) {
                    orders_checkedList.getItems().removeAll(selectedItems);
                    orders_checkedList.getCheckModel().clearChecks();
                }
            }
            if(neighbourhoods_checkedList != null){
                Set<Neighberhood> selectedItems = new HashSet<>(neighbourhoods_checkedList.getCheckModel().getCheckedItems());
                if(selectedItems.size() > 0) {
                    neighbourhoods_checkedList.getCheckModel().clearChecks();
                    neighbourhoods_checkedList.getItems().removeAll(selectedItems);
                }
            }
        }
        if(e.getSource() == plus_btn) {
            Restaurant rest = Restaurant.getInstance();
            if (components_checkedList != null) {
                addComponents_combo.getItems().clear();
                addComponents_combo.getItems().addAll(rest.getComponents().values());
                addComponents_combo.setVisible(true);
            }
            if (dishes_checkedList != null) {
                addComponents_combo.getItems().clear();
                addComponents_combo.getItems().addAll(rest.getDishes().values());
                addComponents_combo.setVisible(true);
            }
            if (orders_checkedList != null) {
                addComponents_combo.getItems().clear();
                List<Record> list = orders_checkedList.getItems().stream().map(ListedRecord::getRecord).toList();
                addComponents_combo.getItems().addAll(rest.getOrders().values()
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
            if(editAreas_sctn != null){
                try{
                    EditRecordRequest request = new EditRecordRequest(records_combo.getValue(), areaName_field.getText(),
                            neighbourhoods_checkedList.getItems().stream().collect(Collectors.toSet()));
                    request.saveRequest();
                    records_combo.getItems().clear();
                    records_combo.getItems().addAll(Restaurant.getInstance().getAreas().values());
                    info_grid.setVisible(false);
                    alert_grid.setVisible(true);
                }catch (IllegalArgumentException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if(editCooks_sctn != null) {
                try{
                    EditRecordRequest request = new EditRecordRequest(records_combo.getValue(),
                            fname_field.getText(), lname_field.getText(), genders_combo.getValue(), birthDate_dp.getValue(),
                            expertises_combo.getValue(),isChef_check.isSelected());
                    request.saveRequest();
                    records_combo.getItems().clear();
                    records_combo.getItems().addAll(Restaurant.getInstance().getCooks().values());
                    info_grid.setVisible(false);
                    alert_grid.setVisible(true);
                }catch (IllegalArgumentException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if(editDeliPersons_sctn != null) {
                try{
                    EditRecordRequest request = new EditRecordRequest(records_combo.getValue(),
                            fname_field.getText(), lname_field.getText(), genders_combo.getValue(), birthDate_dp.getValue(),
                            vehicles_combo.getValue(), deliveryAreas_combo.getValue());
                    request.saveRequest();
                    records_combo.getItems().clear();
                    records_combo.getItems().addAll(Restaurant.getInstance().getDeliveryPersons().values());
                    info_grid.setVisible(false);
                    alert_grid.setVisible(true);
                }catch (IllegalArgumentException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if(editCustomers_sctn != null) {
                try{
                    EditRecordRequest request = new EditRecordRequest(records_combo.getValue(),
                            fname_field.getText(), lname_field.getText(), genders_combo.getValue(), birthDate_dp.getValue(),
                            neighbourhoods_combo.getValue(), glutenIntolerant_check.isSelected(), lactoseIntolerant_check.isSelected(), img_source.getImage());
                    request.saveRequest();
                    records_combo.getItems().clear();
                    records_combo.getItems().addAll(Restaurant.getInstance().getCustomers().values());
                    info_grid.setVisible(false);
                    alert_grid.setVisible(true);
                }catch (IllegalArgumentException | NullPointerException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if(editComponents_sctn != null) {
                try{
                    EditRecordRequest request = new EditRecordRequest(records_combo.getValue(),
                            ingredientName_field.getText(), Double.parseDouble(ingredientPrice_field.getText()),
                            hasGluten_check.isSelected(), hasLactose_check.isSelected());
                    request.saveRequest();
                    records_combo.getItems().clear();
                    records_combo.getItems().addAll(Restaurant.getInstance().getComponents().values());
                    info_grid.setVisible(false);
                    alert_grid.setVisible(true);
                }catch (IllegalArgumentException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if(editDishes_sctn != null) {
                try{
                    EditRecordRequest request = new EditRecordRequest(records_combo.getValue(),
                            dishName_field.getText(), dishType_combo.getValue(), Integer.parseInt(dishPrepareTime_field.getText()),
                            components_checkedList.getItems().stream().map(ListedRecord::getRecord).toList());
                    request.saveRequest();
                    records_combo.getItems().clear();
                    records_combo.getItems().addAll(Restaurant.getInstance().getDishes().values());
                    info_grid.setVisible(false);
                    alert_grid.setVisible(true);
                }catch (IllegalArgumentException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if(editOrders_sctn != null) {
                try{
                    EditRecordRequest request = null;
                    if(deliveries_combo.getValue() == null)
                        request = new EditRecordRequest(records_combo.getValue(),
                                customers_combo.getValue(), dishes_checkedList.getItems().stream().map(ListedRecord::getRecord).toList());
                    else
                        request = new EditRecordRequest(records_combo.getValue(),
                                customers_combo.getValue(), dishes_checkedList.getItems().stream().map(ListedRecord::getRecord).toList(),
                                deliveries_combo.getValue());
                    request.saveRequest();
                    records_combo.getItems().clear();
                    records_combo.getItems().addAll(Restaurant.getInstance().getOrders().values());
                    info_grid.setVisible(false);
                    alert_grid.setVisible(true);
                }catch (IllegalArgumentException ex){
                    System.out.println(ex.getMessage());
                }
            }
            if(editDeliveries_sctn != null) {
                try{
                    EditRecordRequest request = null;
                    Delivery d = (Delivery) records_combo.getValue();
                    if(d instanceof RegularDelivery){
                        request = new EditRecordRequest(d,

                                deliveryPersons_combo.getValue(),
                                deliveryDate_dp.getValue(), isDelivered_check.isSelected(),
                                orders_checkedList.getItems().stream().map(ListedRecord::getRecord).collect(Collectors.toSet()));
                    }
                    else{
                        request = new EditRecordRequest(d,
                                deliveryAreas_combo.getValue() ,deliveryPersons_combo.getValue(),
                                deliveryDate_dp.getValue(), isDelivered_check.isSelected(),
                                orders_combo.getValue(), Double.parseDouble(expressFee_field.getText()));
                    }
                    request.saveRequest();
                    records_combo.getItems().clear();
                    records_combo.getItems().addAll(Restaurant.getInstance().getDeliveries().values());
                    info_grid.setVisible(false);
                    alert_grid.setVisible(true);
                }catch (IllegalArgumentException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    private void setNames(Record newValue, Pattern stringPattern){
        fname_field.setText(((Person)newValue).getFirstName());
        fname_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
        ControllerUtils.setAlerts(fname_field, stringPattern, alert_lbl);

        lname_field.setText(((Person)newValue).getLastName());
        lname_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
        ControllerUtils.setAlerts(lname_field, stringPattern, alert_lbl);
    }

    private void setPersonAttributes(Record newValue, Pattern stringPattern){
        setNames(newValue, stringPattern);

        genders_combo.getItems().clear();
        genders_combo.getItems().addAll(Arrays.stream(Gender.values()).toList());
        genders_combo.setValue(((Person)newValue).getGender());

        birthDate_dp.setValue(((Person)newValue).getBirthDay());

    }
}
