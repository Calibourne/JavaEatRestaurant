package View.Controllers;

import Model.*;
import Model.Requests.AddRecordRequest;
import Utils.*;
import View.newElements.imageListCell;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckListView;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class AddRecordsController {
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
    @FXML
    private Button img_choose;
    @FXML
    private ImageView img_source;
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
    private CheckListView<ListedRecord> components_checkedList;
    // endregion
    // region Order attributes
    @FXML
    private Group addOrders_sctn;
    @FXML
    private VBox ingredients_vbox;
    @FXML
    private ComboBox<Customer> customers_combo;
    @FXML
    private ComboBox<Delivery> deliveries_combo;
    @FXML
    private CheckListView<ListedRecord> dishes_checkedList;
    @FXML
    private CheckListView<ListedRecord> dishesIngredients_checkedList;
    @FXML
    private ComboBox<Component> addSubcomponents_combo;
    @FXML
    private Button addSubcomp_btn;
    @FXML
    private Label dish_id;
    @FXML
    private Label dish_name;
    @FXML
    private Button Iplus_btn;
    @FXML
    private Button Iminus_btn;
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
    private Button rd_btn;
    @FXML
    private VBox rd_vbox;
    @FXML
    private CheckListView<Order> orders_checkedList;
    // endregion
    // region Express Delivery attributes
    @FXML
    private Button ed_btn;
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
    private CheckListView<Neighberhood> neighbourhoods_checkedList;
    // endregion
    // region Blacklist attributes
    @FXML
    private Group addToBlacklist_sctn;
    @FXML
    private ComboBox<Customer> customersToBlacklist_combo;
    // endregion

    @FXML
    private Button submit;
    @FXML
    private ComboBox addComponents_combo;
    @FXML
    private Button plus_btn;
    @FXML
    private Button minus_btn;
    @FXML
    private AnchorPane window;
    @FXML
    private GridPane info_grid;
    @FXML
    private Label result_label;

    // endregion


    private Restaurant rest;

    @FXML
    private void initialize(){
        try{
            rest = Restaurant.getInstance();
            Pattern intPattern = Pattern.compile("([0-9]+)?");
            Pattern doublePattern = Pattern.compile("((([1-9])(\\d*)|0)(\\.\\d*)?)?");
            Pattern stringPattern = Pattern.compile("(([a-zA-Z]*)([ -]?)([a-zA-Z]*))*");
            if(addCooks_sctn!=null || addDeliPersons_sctn!=null || addCustomers_sctn!=null) {

                fname_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
                ControllerUtils.setAlerts(fname_field, stringPattern);

                lname_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
                ControllerUtils.setAlerts(lname_field, stringPattern);

                genders_combo.getItems().addAll(Arrays.stream(Gender.values()).toList());

                birthDate_dp.setConverter(ControllerUtils.getStringConverter());
                birthDate_dp.setPromptText("dd/mm/yyyy");

                if(addCooks_sctn!=null){
                    expertises_combo.getItems().addAll(Arrays.stream(Expertise.values()).toList());
                }
                if(addDeliPersons_sctn!=null){
                    vehicles_combo.getItems().addAll(Arrays.stream(Vehicle.values()).toList());
                    deliveryAreas_combo.getItems().addAll(rest.getAreas().values());
                }
                if(addCustomers_sctn!=null){
                    neighbourhoods_combo.getItems().addAll(Arrays.stream(Neighberhood.values()).toList());
                    ControllerUtils.setFileChooser(img_choose, img_source);
                }
            }
            if(addComponents_sctn!=null){
                ingredientName_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
                ControllerUtils.setAlerts(ingredientName_field, stringPattern);

                ingredientPrice_field.setTextFormatter(ControllerUtils.textFormatter(doublePattern));
                ControllerUtils.setAlerts(ingredientPrice_field,doublePattern);
                //Pattern err = Pattern.compile("((([1-9])(\\d*)|0)(\\.))");
            }
            if(addDishes_sctn!=null){
                dishType_combo.getItems().addAll(Arrays.stream(DishType.values()).toList());
                addComponents_combo.getItems().addAll(rest.getComponents().values());
                addComponents_combo.setVisible(false);
                addComponents_combo.setOnAction(action->{
                    Component c = (Component) addComponents_combo.getValue();
                    if(c != null) {
                        components_checkedList.getItems().add(new ListedRecord(c));
                        addComponents_combo.setVisible(false);
                    }
                });
                //components_checkedList.getItems().addAll(rest.getComponents().values());

                dishName_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
                ControllerUtils.setAlerts(dishName_field, stringPattern);

                dishPrepareTime_field.setTextFormatter(ControllerUtils.textFormatter(intPattern));
            }
            if(addOrders_sctn!=null){
                customers_combo.setCellFactory(list->new imageListCell<>());
                customers_combo.getItems().addAll(rest.getCustomers().values());
                deliveries_combo.getItems().addAll(rest.getDeliveries().values());
                addSubcomponents_combo.getItems().addAll(rest.getComponents().values());

                addComponents_combo.setOnAction(action->{
                    Dish d = (Dish) addComponents_combo.getValue();
                    if(d != null) {
                        dishesIngredients_checkedList.getItems().addAll(
                                d.getComponents().stream().map(ListedRecord::new).toList()
                        );
                        dish_id.setText(""+d.getId());
                        //dishes_checkedList.getItems().add(new ListedRecord(d));
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
            if(addDeliveries_sctn!=null){
                deliveryPersons_combo.getItems().addAll(rest.getDeliveryPersons().values());
                orders_combo.getItems().addAll(rest.getOrders().values());
                addComponents_combo.setOnAction(action->{
                    Order o = (Order) addComponents_combo.getValue();
                    if(o != null) {
                        orders_checkedList.getItems().add(o);
                        addComponents_combo.setVisible(false);
                    }
                });
            }
            if(addAreas_sctn!=null){
                addComponents_combo.getItems().addAll(Arrays.stream(Neighberhood.values()).toList());
                addComponents_combo.setVisible(false);
                addComponents_combo.setOnAction(action->{
                    Neighberhood n = (Neighberhood) addComponents_combo.getValue();
                    if(n != null) {
                        neighbourhoods_checkedList.getItems().add(n);
                        addComponents_combo.setVisible(false);
                    }
                });
                areaName_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
                deliveryTime_field.setTextFormatter(ControllerUtils.textFormatter(intPattern));
            }
            if(addToBlacklist_sctn!=null){
                customersToBlacklist_combo.setCellFactory(list->new imageListCell<>());
                customersToBlacklist_combo.getItems()
                        .addAll(rest.getCustomers().values().stream().
                                filter(c->!rest.getBlacklist().contains(c))
                                .toList());
                if(customersToBlacklist_combo.getItems().size() == 0){
                    GridPane g = (GridPane) window.getParent();
                    g.getChildren().remove(window);
                }
            }
        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    private void handleButtonClick(ActionEvent e){
        if (e.getSource() == ed_btn)
        {
            info_grid.setVisible(true);
            ed_vbox.setVisible(true);
            rd_vbox.setVisible(false);
        }
        if (e.getSource() == rd_btn)
        {
            info_grid.setVisible(true);
            ed_vbox.setVisible(false);
            rd_vbox.setVisible(true);
        }
        if(e.getSource() == submit){
            addRecord();
        }
        if(e.getSource() == plus_btn){
            addComponents_combo.getItems().clear();
            if(addAreas_sctn!=null){
                Set<Neighberhood> set = new HashSet<>(neighbourhoods_checkedList.getItems());
                addComponents_combo.getItems().addAll(
                        Arrays.stream(Neighberhood.values()).toList()
                                .stream().filter(n->!set.contains(n)).toList()
                );
            }
            if(addDishes_sctn!=null){
                addComponents_combo.getItems().addAll(rest.getComponents().values());
            }
            if(addOrders_sctn!=null){
                dishesIngredients_checkedList.getItems().clear();
                ingredients_vbox.setVisible(false);
                addComponents_combo.getItems().addAll(rest.getDishes().values());
            }

            if(addDeliveries_sctn!=null){
                Set<Order> set = new HashSet<>(orders_checkedList.getItems());
                addComponents_combo.getItems().addAll(
                        rest.getOrders().values()
                                .stream().filter(n->!set.contains(n)).toList()
                );
            }
            addComponents_combo.setVisible(true);
        }
        if(e.getSource() == minus_btn){
            if(addAreas_sctn!=null){
                Set<Neighberhood> set = new HashSet<>(neighbourhoods_checkedList.getCheckModel().getCheckedItems());
                neighbourhoods_checkedList.getCheckModel().clearChecks();
                neighbourhoods_checkedList.getItems().removeAll(set);
            }
            if(addDishes_sctn!=null){
                Set<ListedRecord> set = new HashSet<>(components_checkedList.getCheckModel().getCheckedItems());
                components_checkedList.getCheckModel().clearChecks();
                components_checkedList.getItems().removeAll(set);
            }
            if(addOrders_sctn!=null){
                Set<ListedRecord> set = new HashSet<>(dishes_checkedList.getCheckModel().getCheckedItems());
                dishes_checkedList.getCheckModel().clearChecks();
                dishes_checkedList.getItems().removeAll(set);
            }
        }
    }
    private void addRecord(){
        try {
            result_label.setText("");
            AddRecordRequest request = new AddRecordRequest(null);
            // The code for selecting the checked items is taken from the following site:
            // https://www.tabnine.com/code/java/classes/org.controlsfx.control.CheckComboBox
            if (addAreas_sctn != null) {
                ObservableList<Neighberhood> selectedItems = neighbourhoods_checkedList.getItems();
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
                neighbourhoods_checkedList.getCheckModel().clearChecks();
                areaName_field.clear();
                deliveryTime_field.clear();
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Delivery Area added successfully");
            }
            if (addCooks_sctn != null || addCustomers_sctn != null || addDeliPersons_sctn != null){
                String fname = fname_field.getText().length()>0?fname_field.getText():null;
                String lname = lname_field.getText().length()>0?lname_field.getText():null;
                Gender gender = genders_combo.getValue();
                LocalDate birthdate = birthDate_dp.getValue();
                if(fname==null || lname==null || gender==null || birthdate==null){
                    throw new NullPointerException();
                }
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
                    result_label.setStyle("-fx-text-fill: #00ff00");
                    result_label.setText("Cook added successfully");
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
                    result_label.setStyle("-fx-text-fill: #00ff00");
                    result_label.setText("Delivery man added successfully");
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
                            isLactoseIntolerant,
                            img_source.getImage()
                    );
                    neighbourhoods_combo.getSelectionModel().clearSelection();
                    glutenIntolerant_check.setSelected(false);
                    lactoseIntolerant_check.setSelected(false);
                    Image img = SwingFXUtils.toFXImage(ImageManager.getInstance().getImage("Default"), null);
                    img_source.setImage(img);
                    result_label.setStyle("-fx-text-fill: #00ff00");
                    result_label.setText("Customer added successfully");
                }
                fname_field.clear();
                lname_field.clear();
                genders_combo.getSelectionModel().clearSelection();
                birthDate_dp.setValue(null);
            }
            if (addComponents_sctn != null) {
                String ingredientName = ingredientName_field.getText().length()>0?ingredientName_field.getText():null;
                Double ingredientPrice = ingredientPrice_field.getText().length()>0? Double.valueOf(ingredientPrice_field.getText()) :null;
                boolean hasGluten = hasGluten_check.isSelected();
                boolean hasLactose = hasLactose_check.isSelected();
                if(ingredientName==null || ingredientPrice_field.getText()==null){
                    throw new NullPointerException();
                }

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
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Ingredient added successfully");
            }
            if (addDishes_sctn != null) {
                String dishName = dishName_field.getText().length()>0?dishName_field.getText():null;
                int dishPrepareTime = Integer.parseInt(dishPrepareTime_field.getText());
                DishType dishType = dishType_combo.getValue();
                List<Component> selectedItems = components_checkedList.getItems().stream()
                        .map(ListedRecord::getRecord).map(r->(Component)r).toList();
                ArrayList<Component> selectedIngredients = new ArrayList<>(selectedItems);

                if(dishName==null || dishPrepareTime_field.getText()==null || dishType==null || selectedItems.size()==0){
                    throw new NullPointerException();
                }

                request = new AddRecordRequest(new Dish(-1), dishName, dishType, selectedIngredients, dishPrepareTime);
                dishType_combo.getSelectionModel().clearSelection();
                components_checkedList.getItems().clear();
                dishName_field.clear();
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Dish added successfully");
            }
            if (addOrders_sctn != null) {
                Customer customer = customers_combo.getValue();
                Delivery delivery = deliveries_combo.getValue();
                List<Dish> selectedItems = dishes_checkedList.getItems().stream()
                        .map(ListedRecord::getRecord).map(r->(Dish)r).toList();
                ArrayList<Dish> selectedDishes = new ArrayList<>(selectedItems);
                if (delivery!= null)
                    request = new AddRecordRequest(new Order(-1),
                            customer,
                            selectedDishes,
                            delivery
                    );
                else
                    request = new AddRecordRequest(
                            new Order(-1),
                            customer,
                            selectedDishes
                    );
                customers_combo.getSelectionModel().clearSelection();
                deliveries_combo.getSelectionModel().clearSelection();
                dishes_checkedList.getItems().clear();
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Order added successfully");
            }
            if (addDeliveries_sctn != null) {
                DeliveryPerson deliveryPerson = deliveryPersons_combo.getValue();
                DeliveryArea area = deliveryPerson.getArea();
                LocalDate deliveryDate = deliveryDate_dp.getValue();
                boolean isDelivered = isDelivered_check.isSelected();
                if(rd_vbox.isVisible()) {
                    List<Order> selectedItems = orders_checkedList.getItems();
                    request = new AddRecordRequest(
                            new RegularDelivery(-1),
                            new TreeSet<>(selectedItems),
                            deliveryPerson,
                            area,
                            isDelivered,
                            deliveryDate
                    );
                    orders_checkedList.getItems().clear();
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
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Delivery added successfully");
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
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Successfully blacklisted the customer");
                return;
            }
            request.saveRequest();
            Restaurant.getInstance().saveDatabase("Rest.ser");
            System.out.printf("%s was added successfully\n", request.getRecord());

        }catch (Exception e){
            result_label.setStyle("-fx-text-fill: red");
            result_label.setText("Please fill all required fields");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}