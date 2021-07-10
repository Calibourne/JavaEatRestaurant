package View.Controllers;

//import Model.DeliveryArea;
import Model.*;
import Utils.*;
//import View.Main;
import View.Tuple;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.util.*;
import static View.Controllers.ControllerUtils.*;

public class RecordsManagementController {

    //region Properties
    //region Sections
    @FXML
    private VBox addRecord_sctn;
    @FXML
    private Group addCook_sctn;
    @FXML
    private Group addDeliPerson_sctn;
    @FXML
    private Group addCustomer_sctn;
    @FXML
    private Group addComponent_sctn;
    @FXML
    private Group addDish_sctn;
    @FXML
    private Group addOrder_sctn;
    @FXML
    private Group addDelivery_sctn;
    @FXML
    private Group addArea_sctn;
    @FXML
    private Group addToBlacklist_sctn;
    @FXML
    private ComboBox<String> selectionBox;
    @FXML
    private Button return_btn;
    @FXML
    private Button addCooks_btn;
    @FXML
    private Button addAreas_btn;
    @FXML
    private Button addDeliPersons_btn;
    @FXML
    private Button addCustomers_btn;
    @FXML
    private Button addComponents_btn;
    @FXML
    private Button addDishes_btn;
    @FXML
    private Button addOrders_btn;
    @FXML
    private Button addDeliveries_btn;
    @FXML
    private Button addToBlacklist_btn;

    //endregion
    @FXML
    private VBox rd_vbox;
    @FXML
    private VBox ed_vbox;
    @FXML
    private RadioButton ed_RB;
    @FXML
    private RadioButton rd_RB;

    private Restaurant restaurant;
    HashSet<String> menuButtons;
    HashMap<String, Group> groups;
    //endregion


    public void initialize(){
        groups = new HashMap<>();
        menuButtons = new HashSet<>();
        restaurant = Restaurant.getInstance();

        menuButtons.add(addCooks_btn.getId());
        menuButtons.add(addAreas_btn.getId());
        menuButtons.add(addDeliPersons_btn.getId());
        menuButtons.add(addCustomers_btn.getId());
        menuButtons.add(addComponents_btn.getId());
        menuButtons.add(addDishes_btn.getId());
        menuButtons.add(addOrders_btn.getId());
        menuButtons.add(addDeliveries_btn.getId());
        menuButtons.add(addToBlacklist_btn.getId());
        menuButtons.add(return_btn.getId());

        groups.put(addCooks_btn.getId(),addCook_sctn);
        groups.put(addAreas_btn.getId(), addArea_sctn);
        groups.put(addDeliPersons_btn.getId(),addDeliPerson_sctn);
        groups.put(addCustomers_btn.getId(),addCustomer_sctn);
        groups.put(addComponents_btn.getId(),addComponent_sctn);
        groups.put(addDishes_btn.getId(), addDish_sctn);
        groups.put(addOrders_btn.getId(), addOrder_sctn);
        groups.put(addDeliveries_btn.getId(), addDelivery_sctn);
        groups.put(addToBlacklist_btn.getId(), addToBlacklist_sctn);

        groups.values().forEach(g->createSections(g));;
    }

    public void handleButtonClick(ActionEvent e) {
        if(e.getSource() instanceof Button)
        {
            Button btn = (Button) e.getSource();
            if(menuButtons.contains(btn.getId())) {
                switch(btn.getId()){
                    case "addCooks_btn":
                    case "addAreas_btn":
                    case "addDeliPersons_btn":
                    case "addCustomers_btn":
                    case "addComponents_btn":
                    case "addDishes_btn":
                    case "addOrders_btn":
                    case "addDeliveries_btn":
                    case "addToBlacklist_btn": {
                        for (Group g: groups.values()) {
                            g.setVisible(false);
                        }
                        groups.get(btn.getId()).setVisible(true);
                        break;
                    }
                    case "return_btn":{
                        try {
                            Stage primaryStage = (Stage) return_btn.getScene().getWindow();
                            Parent root = FXMLLoader.load(getClass().getResource("../fxmls/managerPage.fxml"));
                            primaryStage.setScene(new Scene(root));
                        }
                        catch (IOException ex){
                            System.out.println(ex.getMessage());
                        }
                        break;
                    }
                }
            }
            /*
            try{
                if(!menuButtons.contains(btn)) {
                    Group group = (Group) btn.getParent();
                    switch (group.getId()) {
                        case "addCook_sctn":
                            break;
                        default:
                            System.out.println("unknown group");
                            break;
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex.getClass());
            }*/
        }
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
    private void createSections(Group group) {
        try{
            GridPane grid = (GridPane) group.getChildren().get(0);
            if (group.getId().equals("addCook_sctn") || group.getId().equals("addCustomer_sctn") || group.getId().equals("addDeliPerson_sctn")) {
                VBox genderContainer = (VBox) grid.getChildren().get(3);
                editListableNode(new Tuple[]{
                            new Tuple<Gender>(genderContainer, 1,
                            Arrays.stream(Gender.values()).toList(),
                                    "Select gender")
                        });
            }

            switch (group.getId()) {
                case "addCook_sctn": {
                    VBox expertiseContainer = (VBox) grid.getChildren().get(4);
                    editListableNode(new Tuple[]{
                            new Tuple<Expertise>(expertiseContainer, 1,
                                    Arrays.stream(Expertise.values()).toList(),
                                    "Select Expertise")
                    });
                    break;
                }
                case "addDeliPerson_sctn": {
                    VBox vehicleContainer = (VBox) grid.getChildren().get(4),
                            deliveryAreaContainer = (VBox) grid.getChildren().get(5);
                    editListableNode(new Tuple[]{
                            new Tuple<Vehicle>(vehicleContainer, 1,
                                    Arrays.stream(Vehicle.values()).toList(),
                                    "Select vehicle type"),
                            new Tuple<DeliveryArea>(deliveryAreaContainer, 1,
                                    restaurant.getAreas().values().stream().toList(),
                                    "Assign to delivery area")
                    });
                    break;
                }
                case "addCustomer_sctn": {
                    VBox neighborhoodContainer = (VBox) grid.getChildren().get(4);
                    editListableNode(new Tuple[]{
                            new Tuple<Neighberhood>(neighborhoodContainer, 1,
                                    Arrays.stream(Neighberhood.values()).toList(),
                                    "Select Neighbourhood")
                    });
                    break;
                }
                case "addComponent_sctn": {
                    // TODO fill in (if necessary)
                    break;
                }
                case "addDish_sctn": {
                    VBox dishTypeContainer = (VBox) grid.getChildren().get(1),
                            ingredientsContainer = (VBox) grid.getChildren().get(2);
                    editListableNode(new Tuple[]{
                            new Tuple<DishType>(dishTypeContainer, 1,
                                    Arrays.stream(DishType.values()).toList(),
                                    "Select dish type"),
                            new Tuple<Component>(ingredientsContainer, 1,
                                    restaurant.getComponents().values().stream().toList(),
                                    "Selected ingredients")
                    });
                    break;
                }
                case "addOrder_sctn": {
                    VBox customerContainer = (VBox) grid.getChildren().get(0),
                            dishesContainer = (VBox) grid.getChildren().get(1),
                            deliveryContainer = (VBox) grid.getChildren().get(2);
                    editListableNode(new Tuple[]{
                            new Tuple<Customer>(customerContainer, 1,
                                    restaurant.getCustomers().values().stream().toList(),
                                    "Select customer"),
                            new Tuple<Dish>(dishesContainer, 1,
                                    restaurant.getDishes().values().stream().toList(),
                                    "Selected dishes"),
                            new Tuple<Delivery>(deliveryContainer, 1,
                                    restaurant.getDeliveries().values().stream()
                                            .filter(d -> d instanceof RegularDelivery).toList(),
                                    "(*Optional) Select Delivery")
                    });
                    break;
                }
                case "addDelivery_sctn": {
                    VBox deliveryPersonContainer = (VBox) grid.getChildren().get(0);
                    editListableNode(new Tuple[]{
                            new Tuple<DeliveryPerson>(deliveryPersonContainer, 1,
                                    restaurant.getDeliveryPersons().values().stream().toList(),
                                    "Assign delivery person"),
                            new Tuple<Order>((Parent) ed_vbox.getChildren().get(0), 1,
                                    restaurant.getOrders().values().stream()
                                            .filter(o -> o.getDelivery() == null).toList(),
                                    "Select Express order"),
                            new Tuple<Order>(rd_vbox, 1,
                                    restaurant.getOrders().values().stream()
                                            .filter(o -> o.getDelivery() == null).toList(),
                                    "Selected orders")
                    });
                    break;
                }
                case "addArea_sctn":{
                    VBox neighbourhoodContainer = (VBox) grid.getChildren().get(1);
                    editListableNode(new Tuple[]{
                          new Tuple<Neighberhood>(neighbourhoodContainer, 1,
                                  Arrays.stream(Neighberhood.values()).toList(),
                                  "Neighbourhoods")
                    });
                    break;
                }
                case "addToBlacklist_sctn":{
                    VBox customerContainer = (VBox) grid.getChildren().get(0);
                    editListableNode(new Tuple[]{
                            new Tuple<Customer>(customerContainer,1,
                                    restaurant.getCustomers().values().stream().filter(c->!restaurant.getBlacklist().contains(c)).toList(),
                            "Select a customer to blacklist")
                    });
                    break;
                }
                default: {
                    System.out.println("Unsupported/Yet to implement section");
                    //Optional: throw new IllegalStateException("Unexpected value: " + group.getId());
                    break;
                }
            }
        }catch(NullPointerException e) {
            System.err.println(e.getMessage());
        }
        catch (ClassCastException e) {
            System.err.println(e.getMessage());
        }
    }
    private void addRecordSelectionGroup(ComboBox cb) {
        String[] startOptions = {
                "Delivery Area",
                "Cook",
                "Customer",
                "Ingredient"
        };
        Set<String> set = (Set<String>) new HashSet<String>(Arrays.stream(startOptions).toList());
        if(restaurant.getAreas().size() > 0) {
            set.add("Delivery Person");
        }
        if (restaurant.getCustomers().size() > 0){
            set.add("Blacklisted Customer");
        }
        if(restaurant.getComponents().size() > 0) {
            set.add("Dish");
        }
        if(restaurant.getDishes().size() > 0 &&
                restaurant.getCustomers().values().stream()
                        .filter(c->!restaurant.getBlacklist().contains(c)).toList().size()>0) {
            set.add("Order");
        }
        if(restaurant.getOrders().size()>0 && restaurant.getDeliveryPersons().size()>0){
            set.add("Delivery");
        }

        cb.getItems().addAll(FXCollections.observableList(set.stream().sorted().toList()));
    }
}