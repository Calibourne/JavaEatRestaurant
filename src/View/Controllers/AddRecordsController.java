package View.Controllers;

import Model.*;
import Model.Requests.AddRecordRequest;
import Utils.*;
import View.newElements.imageListCell;
import javafx.collections.ListChangeListener;
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

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A controller that controls the structures add pages
 * @author Eddie Kanevsky
 */
public class AddRecordsController extends RecordManagementController{
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
    private ListView<ListedRecord> components_checkedList;
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
    private ListView<ListedRecord> dishes_checkedList;
    @FXML
    private ListView<ListedRecord> dishesIngredients_checkedList;
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
    @FXML
    private Label orderPrice_lbl;
    @FXML
    private Label dishPrice_lbl;
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
    private ListView<Order> orders_checkedList;
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
    private ListView<Neighberhood> neighbourhoods_checkedList;
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


    @FXML
    private void initialize(){
        try{
            Pattern intPattern = Pattern.compile("([0-9]+)?");
            Pattern doublePattern = Pattern.compile("((([1-9])(\\d*)|0)(\\.\\d*)?)?");
            Pattern stringPattern = Pattern.compile("(([a-zA-Z]*)([ -]?)([a-zA-Z]*))*");

            if(addCooks_sctn!=null || addDeliPersons_sctn!=null || addCustomers_sctn!=null) {
                initPersonsPages(stringPattern);
            }
            if(addComponents_sctn!=null){
                initIngredientPage(stringPattern, doublePattern);
            }
            if(addDishes_sctn!=null){
                initDishesPage(stringPattern, intPattern);
            }
            if(addOrders_sctn!=null){
                initOrdersPage();
            }
            if(addDeliveries_sctn!=null){
                initDeliveriesPage();
            }
            if(addAreas_sctn!=null){
                initAreasPage(stringPattern, intPattern);
            }
            if(addToBlacklist_sctn!=null){
                initBlacklistPage();
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
                addComponents_combo.getItems().addAll(getRestaurant().getComponents().values());
            }
            if(addOrders_sctn!=null){
                dishesIngredients_checkedList.getItems().clear();
                ingredients_vbox.setVisible(false);
                addComponents_combo.getItems().addAll(getRestaurant().getDishes().values());
            }

            if(addDeliveries_sctn!=null){
                Set<Order> set = new HashSet<>(orders_checkedList.getItems());
                addComponents_combo.getItems().addAll(
                        getRestaurant().getOrders().values()
                                .stream().filter(n->!set.contains(n)).toList()
                );
            }
            addComponents_combo.setVisible(true);
        }
        if(e.getSource() == minus_btn){
            if(addAreas_sctn!=null){
                Set<Neighberhood> set = new HashSet<>(neighbourhoods_checkedList.getSelectionModel().getSelectedItems());
                neighbourhoods_checkedList.getSelectionModel().clearSelection();
                neighbourhoods_checkedList.getItems().removeAll(set);
            }
            if(addDishes_sctn!=null){
                Set<ListedRecord> set = new HashSet<>(components_checkedList.getSelectionModel().getSelectedItems());
                components_checkedList.getSelectionModel().clearSelection();
                components_checkedList.getItems().removeAll(set);
            }
            if(addOrders_sctn!=null){
                Set<ListedRecord> set = new HashSet<>(dishes_checkedList.getSelectionModel().getSelectedItems());
                dishes_checkedList.getSelectionModel().clearSelection();
                dishes_checkedList.getItems().removeAll(set);
            }
            if(addDeliveries_sctn!=null){
                Set<Order> set = new HashSet<>(orders_checkedList.getSelectionModel().getSelectedItems());
                orders_checkedList.getSelectionModel().clearSelection();
                orders_checkedList.getItems().removeAll(set);
            }
        }
    }
    private void addRecord() {
        result_label.setText("");
        AddRecordRequest request = null;
        if (addAreas_sctn != null) {
            request = createAreaRequest();
        }
        if (addCooks_sctn != null || addCustomers_sctn != null || addDeliPersons_sctn != null) {
            String fname = fname_field.getText().length() > 0 ? fname_field.getText() : null;
            String lname = lname_field.getText().length() > 0 ? lname_field.getText() : null;
            Gender gender = genders_combo.getValue();
            LocalDate birthdate = birthDate_dp.getValue();
            if (addCooks_sctn != null) {
                try{
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
                }catch (IllegalArgumentException e) {
                    result_label.setStyle("-fx-text-fill: red");
                    result_label.setText("Please fill all required fields");
                    SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
                }
            }
            if (addDeliPersons_sctn != null) {
                try {
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
                }catch (IllegalArgumentException e) {
                    result_label.setStyle("-fx-text-fill: red");
                    result_label.setText("Please fill all required fields");
                    SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
                }
            }
            if (addCustomers_sctn != null) {
                try {
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
                    try {
                        Image img = SwingFXUtils.toFXImage(ImageManager.getInstance().getImage("Default"), null);
                        img_source.setImage(img);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    result_label.setStyle("-fx-text-fill: #00ff00");
                    result_label.setText("Customer added successfully");
                }catch (IllegalArgumentException e) {
                    result_label.setStyle("-fx-text-fill: red");
                    result_label.setText("Please fill all required fields");
                    SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
                }
            }
            fname_field.clear();
            lname_field.clear();
            genders_combo.getSelectionModel().clearSelection();
            birthDate_dp.setValue(null);
        }
        if (addComponents_sctn != null) {
            try {
                String ingredientName = ingredientName_field.getText().length() > 0 ? ingredientName_field.getText() : null;
                Double ingredientPrice = ingredientPrice_field.getText().length() > 0 ? Double.valueOf(ingredientPrice_field.getText()) : null;
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
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Ingredient added successfully");
            }catch (IllegalArgumentException e) {
                result_label.setStyle("-fx-text-fill: red");
                result_label.setText("Please fill all required fields");
                SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            }
        }
        if (addDishes_sctn != null) {
            try {
                String dishName = dishName_field.getText().length() > 0 ? dishName_field.getText() : null;
                int dishPrepareTime = Integer.parseInt(dishPrepareTime_field.getText());
                DishType dishType = dishType_combo.getValue();
                List<Component> selectedItems = components_checkedList.getItems().stream()
                        .map(ListedRecord::getRecord).map(r -> (Component) r).toList();
                System.out.println(selectedItems.size());
                if (components_checkedList.getItems().size() == 0)
                    throw new NullPointerException();
                ArrayList<Component> selectedIngredients = new ArrayList<>(selectedItems);

                request = new AddRecordRequest(new Dish(-1), dishName, dishType, selectedIngredients, dishPrepareTime);
                dishType_combo.getSelectionModel().clearSelection();
                components_checkedList.getItems().clear();
                dishName_field.clear();
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Dish added successfully");
            } catch (IllegalArgumentException e) {
                result_label.setStyle("-fx-text-fill: red");
                result_label.setText("Please fill all required fields");
                SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            } catch (NullPointerException e) {
                result_label.setStyle("-fx-text-fill: red");
                result_label.setText("Please add at least 1 ingredient to the dish");
                SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            }
        }
        if (addOrders_sctn != null) {
            try {
                Customer customer = customers_combo.getValue();
                Delivery delivery = deliveries_combo.getValue();
                List<Dish> selectedItems = dishes_checkedList.getItems().stream()
                        .map(ListedRecord::getRecord).map(r -> (Dish) r).toList();
                ArrayList<Dish> selectedDishes = new ArrayList<>(selectedItems);
                if (selectedDishes.size() == 0)
                    throw new NullPointerException();
                if (delivery != null)
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
            } catch (IllegalArgumentException e) {
                result_label.setStyle("-fx-text-fill: red");
                result_label.setText("Please fill all required fields");
                SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            } catch (NullPointerException e) {
                result_label.setStyle("-fx-text-fill: red");
                result_label.setText("Please add at least 1 dish to the order");
                SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            }
        }
        if (addDeliveries_sctn != null) {
            try {
                DeliveryPerson deliveryPerson = deliveryPersons_combo.getValue();
                DeliveryArea area = deliveryPerson.getArea();
                LocalDate deliveryDate = deliveryDate_dp.getValue();
                boolean isDelivered = isDelivered_check.isSelected();
                if (rd_vbox.isVisible()) {
                    List<Order> selectedItems = orders_checkedList.getItems();
                    if (selectedItems.size() == 0)
                        throw new NullPointerException();
                    request = new AddRecordRequest(
                            new RegularDelivery(-1),
                            new TreeSet<>(selectedItems),
                            deliveryPerson,
                            area,
                            isDelivered,
                            deliveryDate
                    );
                    orders_checkedList.getItems().clear();
                } else {
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
            } catch (IllegalArgumentException e) {
                result_label.setStyle("-fx-text-fill: red");
                result_label.setText("Please fill all required fields");
                SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            } catch (NullPointerException e) {
                result_label.setStyle("-fx-text-fill: red");
                result_label.setText("Please add at least 1 order to the delivery");
                SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            }
        }
        if (addToBlacklist_sctn != null) {
            try {
                Customer toBlacklist = customersToBlacklist_combo.getValue();
                request = new AddRecordRequest(toBlacklist);
                request.saveRequest();
                getRestaurant().saveDatabase("Rest.ser");
                customersToBlacklist_combo.getSelectionModel().clearSelection();
                customersToBlacklist_combo.setValue(null);
                customersToBlacklist_combo.setPromptText("Choose customer to blacklist");
                System.out.printf("%s added to blacklist\n", toBlacklist);
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Successfully blacklisted the customer");
                return;
            }catch (IllegalArgumentException e) {
                result_label.setStyle("-fx-text-fill: red");
                result_label.setText("Please fill all required fields");
                SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            }
        }
        if(request!=null) {
            request.saveRequest();
            Restaurant.getInstance().saveDatabase("Rest.ser");
            System.out.printf("%s was added successfully\n", request.getRecord());
        }
    }

    /**
     * Initializes and populates the fields of customers / delivery persons / cooks pages
     * @param stringPattern
     * pattern that restricts user input
     */
    private void initPersonsPages(Pattern stringPattern){
        fname_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
        ControllerUtils.setAlerts(fname_field, stringPattern, result_label);

        lname_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
        ControllerUtils.setAlerts(lname_field, stringPattern, result_label);

        genders_combo.getItems().addAll(Arrays.stream(Gender.values()).toList());

        birthDate_dp.setConverter(ControllerUtils.getStringConverter());
        birthDate_dp.setPromptText("dd/mm/yyyy");

        if(addCooks_sctn!=null){
            expertises_combo.getItems().addAll(Arrays.stream(Expertise.values()).toList());
        }
        if(addDeliPersons_sctn!=null){
            vehicles_combo.getItems().addAll(Arrays.stream(Vehicle.values()).toList());
            deliveryAreas_combo.getItems().addAll(getRestaurant().getAreas().values());
        }
        if(addCustomers_sctn!=null){
            neighbourhoods_combo.getItems().addAll(Arrays.stream(Neighberhood.values()).toList());
            ControllerUtils.setFileChooser(img_choose, img_source);
        }
    }

    /**
     * Initializes and populates the fields of ingredients page
     * @param stringPattern
     * pattern that restricts user input for ingredient name
     * @param doublePattern
     * pattern that restricts user input for ingredient price
     */
    private void initIngredientPage(Pattern stringPattern, Pattern doublePattern){
        ingredientName_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
        ControllerUtils.setAlerts(ingredientName_field, stringPattern, result_label);

        ingredientPrice_field.setTextFormatter(ControllerUtils.textFormatter(doublePattern));
        ControllerUtils.setAlerts(ingredientPrice_field,doublePattern, result_label);
    }

    /**
     * Initializes and populates the fields of dishes page
     * @param stringPattern
     * pattern that restricts user input for dish name
     * @param intPattern
     * pattern that restricts user input for dish time to make
     */
    private void initDishesPage(Pattern stringPattern, Pattern intPattern){
        dishType_combo.getItems().addAll(Arrays.stream(DishType.values()).toList());
        addComponents_combo.getItems().addAll(getRestaurant().getComponents().values());
        addComponents_combo.setVisible(false);
        addComponents_combo.setOnAction(action->{
            Component c = (Component) addComponents_combo.getValue();
            if(c != null) {
                components_checkedList.getItems().add(new ListedRecord(c));
                addComponents_combo.setVisible(false);
            }
        });

        dishName_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
        ControllerUtils.setAlerts(dishName_field, stringPattern, result_label);

        dishPrepareTime_field.setTextFormatter(ControllerUtils.textFormatter(intPattern));
        ControllerUtils.setAlerts(dishPrepareTime_field, intPattern, result_label);
    }

    /**
     * Initializes and populates the fields of orders page
     */
    private void initOrdersPage(){
        dish_id.setText("0");
        customers_combo.setCellFactory(list->new imageListCell<>());
        customers_combo.getItems().addAll(getRestaurant().getCustomers().values());
        deliveries_combo.getItems().addAll(getRestaurant().getDeliveries().values());
        addSubcomponents_combo.getItems().addAll(getRestaurant().getComponents().values());

        addComponents_combo.setOnAction(action->{
            Dish d = (Dish) addComponents_combo.getValue();
            if(d != null) {
                dishesIngredients_checkedList.getItems().addAll(
                        d.getComponents().stream().map(ListedRecord::new).toList()
                );
                dish_id.setText(""+d.getId());
                ingredients_vbox.setVisible(true);
                addComponents_combo.setVisible(false);
                dish_name.setText(d.getDishName()+" ingredients: ");
                dishPrice_lbl.setText(String.format("%.2f₪",d.getPrice()));
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
            Collection<Component> selectedList = dishesIngredients_checkedList.getItems()
                    .stream().map(ListedRecord::getRecord).map(r->(Component)r).toList(),
                    dishList = getRestaurant().getRealDish(Integer.parseInt(dish_id.getText()))
                            .getComponents().stream().toList();
            if(dishList.equals(selectedList)){
                dishes_checkedList.getItems().add(
                        new ListedRecord(getRestaurant().getRealDish(Integer.parseInt(dish_id.getText())))
                );
            }
            else {
                Dish d = getRestaurant().getRealDish(Integer.parseInt(dish_id.getText()));
                dishes_checkedList.getItems().add(
                        new ListedRecord(new Dish("Custom " + d.getDishName() ,d.getType(), new ArrayList<>(selectedList), d.getTimeToMake()))
                );
            }
            dishesIngredients_checkedList.getItems().clear();
            ingredients_vbox.setVisible(false);
        });
        Iplus_btn.setOnAction(action->{
            addSubcomponents_combo.getItems().clear();
            addSubcomponents_combo.getItems().addAll(getRestaurant().getComponents().values());
            addSubcomponents_combo.setVisible(true);
        });
        Iminus_btn.setOnAction(action -> {
            Set<ListedRecord> set = new HashSet<>(dishesIngredients_checkedList.getSelectionModel().getSelectedItems());
            dishesIngredients_checkedList.getSelectionModel().clearSelection();
            dishesIngredients_checkedList.getItems().removeAll(set);
        });
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
                    double price = 3 * dishesIngredients_checkedList.getItems().stream()
                            .map(ListedRecord::getRecord).map(r->(Component)r)
                            .map(Component::getPrice).reduce(0.0, Double::sum);
                    dishPrice_lbl.setText(String.format("%.2f₪", price));
                }
            }catch(NullPointerException e){
                dishPrice_lbl.setText("0₪");
            }
        });
    }

    /**
     * Initializes and populates the fields of deliveries page
     */
    private void initDeliveriesPage(){
        deliveryPersons_combo.getItems().addAll(getRestaurant().getDeliveryPersons().values());
        orders_combo.getItems().addAll(getRestaurant().getOrders().values());
        addComponents_combo.setOnAction(action->{
            Order o = (Order) addComponents_combo.getValue();
            if(o != null) {
                orders_checkedList.getItems().add(o);
                addComponents_combo.setVisible(false);
            }
        });
    }

    /**
     * Initializes and populates the fields of delivery areas page
     */
    private void initAreasPage(Pattern stringPattern, Pattern intPattern){
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

    /**
     * Initializes and populates the fields of add to blacklist page
     */
    private void initBlacklistPage(){
        customersToBlacklist_combo.setCellFactory(list->new imageListCell<>());
        customersToBlacklist_combo.getItems()
                .addAll(getRestaurant().getCustomers().values().stream().
                        filter(c->!getRestaurant().getBlacklist().contains(c))
                        .toList());
        if(customersToBlacklist_combo.getItems().size() == 0){
            GridPane g = (GridPane) window.getParent();
            g.getChildren().remove(window);
        }
    }

    /**
     * creates a delivery area based on parameters entered and puts it in record request for analytics purposes
     * if fails to create one, shows alert to the user
     * @return
     * the record request created
     */
    public AddRecordRequest createAreaRequest(){
        try {
            ObservableList<Neighberhood> selectedItems = neighbourhoods_checkedList.getItems();
            System.out.println(selectedItems.size());
            if (selectedItems.size() == 0)
                throw new NullPointerException();
            HashSet<Neighberhood> selectedNeighbourhoods = new HashSet<>(selectedItems.stream().toList());
            selectedNeighbourhoods = (selectedNeighbourhoods.size() > 0) ? selectedNeighbourhoods : null;
            String areaName = areaName_field.getText().length() > 0 ? areaName_field.getText() : null;
            Integer deliveryTime = deliveryTime_field.getText().length() > 0 ? Integer.parseInt(deliveryTime_field.getText()) : null;
            AddRecordRequest request = new AddRecordRequest(
                    new DeliveryArea(-1),
                    areaName,
                    selectedNeighbourhoods,
                    deliveryTime
            );
            neighbourhoods_checkedList.getItems().clear();
            areaName_field.clear();
            deliveryTime_field.clear();
            result_label.setStyle("-fx-text-fill: #00ff00");
            result_label.setText("Delivery Area added successfully");
            return request;
        } catch (IllegalArgumentException e) {
            result_label.setStyle("-fx-text-fill: red");
            result_label.setText("Please fill all required fields");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            return null;
        } catch (NullPointerException e) {
            result_label.setStyle("-fx-text-fill: red");
            result_label.setText("Please add at least 1 neighbourhood to the delivery area");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            return null;
        }
    }

    /**
     *
     * @return
     */
    public AddRecordRequest createPersonRequest(){
        String fname = fname_field.getText().length() > 0 ? fname_field.getText() : null;
        String lname = lname_field.getText().length() > 0 ? lname_field.getText() : null;
        Gender gender = genders_combo.getValue();
        LocalDate birthdate = birthDate_dp.getValue();
        if (addCooks_sctn != null) {
            return createCookRequest(fname, lname, birthdate, gender);
        }
        if (addDeliPersons_sctn != null) {
            return createDeliveryPersonRequest(fname, lname, birthdate, gender);
        }
        if (addCustomers_sctn != null) {
            try {
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
                try {
                    Image img = SwingFXUtils.toFXImage(ImageManager.getInstance().getImage("Default"), null);
                    img_source.setImage(img);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                result_label.setStyle("-fx-text-fill: #00ff00");
                result_label.setText("Customer added successfully");
            }catch (IllegalArgumentException e) {
                result_label.setStyle("-fx-text-fill: red");
                result_label.setText("Please fill all required fields");
                SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            }
        }
        fname_field.clear();
        lname_field.clear();
        genders_combo.getSelectionModel().clearSelection();
        birthDate_dp.setValue(null);
        return null;
    }

    /**
     * creates a cook based on parameters entered and puts it in record request for analytics purposes
     * if fails to create one, shows alert to the user
     * @param fname
     * the first name of the cook
     * @param lname
     * the last name of the cook
     * @param birthdate
     * the virth date of the cook
     * @param gender
     * the gender of the cook
     * @return
     * the record request created
     */
    private AddRecordRequest createCookRequest(String fname, String lname, LocalDate birthdate, Gender gender){
        try{
            Expertise expertise = expertises_combo.getValue();
            boolean isChef = isChef_check.isSelected();
            AddRecordRequest request = new AddRecordRequest(
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
            return request;
        }catch (IllegalArgumentException e) {
            result_label.setStyle("-fx-text-fill: red");
            result_label.setText("Please fill all required fields");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            return null;
        }
    }

    /**
     * creates a delivery person based on parameters entered and puts it in record request for analytics purposes
     * if fails to create one, shows alert to the user
     * @param fname
     * the first name of the cook
     * @param lname
     * the last name of the cook
     * @param birthdate
     * the virth date of the cook
     * @param gender
     * the gender of the cook
     * @return
     * the record request created
     */
    private AddRecordRequest createDeliveryPersonRequest(String fname, String lname, LocalDate birthdate, Gender gender){
        try {
            Vehicle vehicle = vehicles_combo.getValue();
            DeliveryArea area = deliveryAreas_combo.getValue();
            AddRecordRequest request = new AddRecordRequest(
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
            return request;
        }catch (IllegalArgumentException e) {
            result_label.setStyle("-fx-text-fill: red");
            result_label.setText("Please fill all required fields");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            return null;
        }
    }
}