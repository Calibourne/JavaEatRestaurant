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
import java.util.stream.Collectors;

import static View.Controllers.ControllerUtils.*;

public class AddRecordsController extends RecordManagementController{

    //region Properties
    //region Sections
    @FXML
    private VBox addRecord_sctn;
    @FXML
    private Group addCooks_sctn;
    @FXML
    private Group addDeliPersons_sctn;
    @FXML
    private Group addCustomers_sctn;
    @FXML
    private Group addComponents_sctn;
    @FXML
    private Group addDishes_sctn;
    @FXML
    private Group addOrders_sctn;
    @FXML
    private Group addDeliveries_sctn;
    @FXML
    private Group addAreas_sctn;
    @FXML
    private Group addToBlacklist_sctn;
    @FXML
    private ComboBox<String> selectionBox;
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

    //endregion

    public void initialize(){
        super.initialize(Arrays.stream(new Group[]{
                addCooks_sctn,
                addDeliPersons_sctn,
                addCustomers_sctn,
                addComponents_sctn,
                addDishes_sctn,
                addOrders_sctn,
                addDeliveries_sctn,
                addAreas_sctn,
                addToBlacklist_sctn
        }).collect(Collectors.toList()),
                Arrays.stream(new Button[]{
                addCooks_btn,
                addDeliPersons_btn,
                addCustomers_btn,
                addComponents_btn,
                addDishes_btn,
                addOrders_btn,
                addDeliveries_btn,
                addAreas_btn,
                addToBlacklist_btn
        }).collect(Collectors.toList()));
        /*groups = new HashMap<>();
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

        groups.put(addCooks_btn.getId(),addCook_sctn);
        groups.put(addAreas_btn.getId(), addArea_sctn);
        groups.put(addDeliPersons_btn.getId(),addDeliPerson_sctn);
        groups.put(addCustomers_btn.getId(),addCustomer_sctn);
        groups.put(addComponents_btn.getId(),addComponent_sctn);
        groups.put(addDishes_btn.getId(), addDish_sctn);
        groups.put(addOrders_btn.getId(), addOrder_sctn);
        groups.put(addDeliveries_btn.getId(), addDelivery_sctn);
        groups.put(addToBlacklist_btn.getId(), addToBlacklist_sctn);

        */
    }

    public void handleButtonClick(ActionEvent e) {
        super.handleButtonClick(e);
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

    @Override
    protected void createSections(Group group) {
        try{
            GridPane grid = (GridPane) group.getChildren().get(0);
            if (group.getId().equals(addCooks_sctn.getId()) ||
                group.getId().equals(addCustomers_sctn.getId()) ||
                group.getId().equals(addDeliPersons_sctn.getId())) {
                VBox genderContainer = (VBox) grid.getChildren().get(3);
                editListableNode(new Tuple[]{
                            new Tuple<Gender>(genderContainer, 1,
                            Arrays.stream(Gender.values()).toList(),
                                    "Select gender")
                        });
            }

            switch (group.getId()) {
                case "addCooks_sctn": {
                    VBox expertiseContainer = (VBox) grid.getChildren().get(4);
                    editListableNode(new Tuple[]{
                            new Tuple<Expertise>(expertiseContainer, 1,
                                    Arrays.stream(Expertise.values()).toList(),
                                    "Select Expertise")
                    });
                    break;
                }
                case "addDeliPersons_sctn": {
                    VBox vehicleContainer = (VBox) grid.getChildren().get(4),
                            deliveryAreaContainer = (VBox) grid.getChildren().get(5);
                    editListableNode(new Tuple[]{
                            new Tuple<Vehicle>(vehicleContainer, 1,
                                    Arrays.stream(Vehicle.values()).toList(),
                                    "Select vehicle type"),
                            new Tuple<DeliveryArea>(deliveryAreaContainer, 1,
                                    getRestaurant().getAreas().values().stream().toList(),
                                    "Assign to delivery area")
                    });
                    break;
                }
                case "addCustomers_sctn": {
                    VBox neighborhoodContainer = (VBox) grid.getChildren().get(4);
                    editListableNode(new Tuple[]{
                            new Tuple<Neighberhood>(neighborhoodContainer, 1,
                                    Arrays.stream(Neighberhood.values()).toList(),
                                    "Select Neighbourhood")
                    });
                    break;
                }
                case "addComponents_sctn": {
                    // TODO fill in (if necessary)
                    break;
                }
                case "addDishes_sctn": {
                    VBox dishTypeContainer = (VBox) grid.getChildren().get(1),
                            ingredientsContainer = (VBox) grid.getChildren().get(2);
                    editListableNode(new Tuple[]{
                            new Tuple<DishType>(dishTypeContainer, 1,
                                    Arrays.stream(DishType.values()).toList(),
                                    "Select dish type"),
                            new Tuple<Component>(ingredientsContainer, 1,
                                    getRestaurant().getComponents().values().stream().toList(),
                                    "Selected ingredients")
                    });
                    break;
                }
                case "addOrders_sctn": {
                    VBox customerContainer = (VBox) grid.getChildren().get(0),
                            dishesContainer = (VBox) grid.getChildren().get(1),
                            deliveryContainer = (VBox) grid.getChildren().get(2);
                    editListableNode(new Tuple[]{
                            new Tuple<Customer>(customerContainer, 1,
                                    getRestaurant().getCustomers().values().stream().toList(),
                                    "Select customer"),
                            new Tuple<Dish>(dishesContainer, 1,
                                    getRestaurant().getDishes().values().stream().toList(),
                                    "Selected dishes"),
                            new Tuple<Delivery>(deliveryContainer, 1,
                                    getRestaurant().getDeliveries().values().stream()
                                            .filter(d -> d instanceof RegularDelivery).toList(),
                                    "(*Optional) Select Delivery")
                    });
                    break;
                }
                case "addDeliveries_sctn": {
                    VBox deliveryPersonContainer = (VBox) grid.getChildren().get(0);
                    editListableNode(new Tuple[]{
                            new Tuple<DeliveryPerson>(deliveryPersonContainer, 1,
                                    getRestaurant().getDeliveryPersons().values().stream().toList(),
                                    "Assign delivery person"),
                            new Tuple<Order>((Parent) ed_vbox.getChildren().get(0), 1,
                                    getRestaurant().getOrders().values().stream()
                                            .filter(o -> o.getDelivery() == null).toList(),
                                    "Select Express order"),
                            new Tuple<Order>(rd_vbox, 1,
                                    getRestaurant().getOrders().values().stream()
                                            .filter(o -> o.getDelivery() == null).toList(),
                                    "Selected orders")
                    });
                    break;
                }
                case "addAreas_sctn":{
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
                                    getRestaurant().getCustomers().values().stream().filter(c->!getRestaurant().getBlacklist().contains(c)).toList(),
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
        if(getRestaurant().getAreas().size() > 0) {
            set.add("Delivery Person");
        }
        if (getRestaurant().getCustomers().size() > 0){
            set.add("Blacklisted Customer");
        }
        if(getRestaurant().getComponents().size() > 0) {
            set.add("Dish");
        }
        if(getRestaurant().getDishes().size() > 0 &&
                getRestaurant().getCustomers().values().stream()
                        .filter(c->!getRestaurant().getBlacklist().contains(c)).toList().size()>0) {
            set.add("Order");
        }
        if(getRestaurant().getOrders().size()>0 && getRestaurant().getDeliveryPersons().size()>0){
            set.add("Delivery");
        }

        cb.getItems().addAll(FXCollections.observableList(set.stream().sorted().toList()));
    }
}