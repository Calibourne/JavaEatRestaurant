package View.Controllers;

//import Model.DeliveryArea;

import Model.*;
import Utils.*;
import View.Tuple;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static View.Controllers.ControllerUtils.editListableNode;

public class RemoveRecordsController extends RecordManagementController{

    //region Properties
    //region Sections
    @FXML
    private VBox removeRecord_sctn;
    @FXML
    private Group removeCooks_sctn;
    @FXML
    private Group removeDeliPersons_sctn;
    @FXML
    private Group removeCustomers_sctn;
    @FXML
    private Group removeComponents_sctn;
    @FXML
    private Group removeDishes_sctn;
    @FXML
    private Group removeOrders_sctn;
    @FXML
    private Group removeDeliveries_sctn;
    @FXML
    private Group removeAreas_sctn;
    @FXML
    private Button removeCooks_btn;
    @FXML
    private Button removeDeliPersons_btn;
    @FXML
    private Button removeCustomers_btn;
    @FXML
    private Button removeComponents_btn;
    @FXML
    private Button removeDishes_btn;
    @FXML
    private Button removeOrders_btn;
    @FXML
    private Button removeDeliveries_btn;
    @FXML
    private Button removeAreas_btn;

    //endregion


    public void initialize(){
        super.initialize(Arrays.stream(new Group[]{
                        removeCooks_sctn,
                        removeDeliPersons_sctn,
                        removeCustomers_sctn,
                        removeComponents_sctn,
                        removeDishes_sctn,
                        removeOrders_sctn,
                        removeDeliveries_sctn,
                        removeAreas_sctn
                }).collect(Collectors.toList()),
                Arrays.stream(new Button[]{
                        removeCooks_btn,
                        removeDeliPersons_btn,
                        removeCustomers_btn,
                        removeComponents_btn,
                        removeDishes_btn,
                        removeOrders_btn,
                        removeDeliveries_btn,
                        removeAreas_btn
                }).collect(Collectors.toList()));
    }

    public void handleButtonClick(ActionEvent e) {
        if(e.getSource() instanceof Button) {
            Button btn = (Button) e.getSource();
            if(getMenuButtons().contains(btn.getId())) {
                for (Group g: getGroups().values()) {
                    g.setVisible(false);
                }
                getGroups().get(btn.getId()).setVisible(true);
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
    @Override
    protected void createSections(Group group) {
        try{
            GridPane grid = (GridPane) group.getChildren().get(0);
            switch (group.getId()) {
                case "removeCooks_sctn": {
                    editListableNode(new Tuple[]{
                            new Tuple<Cook>(grid, 0,
                                    getRestaurant().getCooks().values().stream().toList(),
                                    "Select Cook to Remove")
                    });
                    break;
                }
                case "removeDeliPersons_sctn": {
                    editListableNode(new Tuple[]{
                            new Tuple<DeliveryPerson>(grid, 0,
                                    getRestaurant().getDeliveryPersons().values().stream().toList(),
                                    "Select Delivery Person to Remove")
                    });
                    break;
                }
                case "removeCustomers_sctn": {
                    editListableNode(new Tuple[]{
                            new Tuple<Customer>(grid, 0,
                                    getRestaurant().getCustomers().values().stream().toList(),
                                    "Select Customer to Remove")
                    });
                    break;
                }
                case "removeComponents_sctn": {
                    editListableNode(new Tuple[]{
                            new Tuple<Component>(grid, 0,
                                    getRestaurant().getComponents().values().stream().toList(),
                                    "Select Ingredient to Remove")
                    });
                    break;
                }
                case "removeDishes_sctn": {
                    editListableNode(new Tuple[]{
                            new Tuple<Dish>(grid, 0,
                                    getRestaurant().getDishes().values().stream().toList(),
                                    "Select Dish to Remove")
                    });
                    break;
                }
                case "removeOrders_sctn": {
                    editListableNode(new Tuple[]{
                            new Tuple<Order>(grid, 0,
                                    getRestaurant().getOrders().values().stream().toList(),
                                    "Select Order to Remove")
                    });
                    break;
                }
                case "removeDeliveries_sctn": {
                    editListableNode(new Tuple[]{
                            new Tuple<Delivery>(grid, 0,
                                    getRestaurant().getDeliveries().values().stream().toList(),
                                    "Select Delivery to Remove")
                    });
                    break;
                }
                case "removeAreas_sctn":{
                    editListableNode(new Tuple[]{
                            new Tuple<DeliveryArea>(grid, 0,
                                    getRestaurant().getAreas().values().stream().toList(),
                                    "Select Delivery Area to Remove")
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
    /*private void addRecordSelectionGroup(ComboBox cb) {
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
    }*/
}