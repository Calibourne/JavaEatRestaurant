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

import static View.Controllers.ControllerUtils.editListableNode;

public class RemoveRecordsController {

    //region Properties
    //region Sections
    @FXML
    private VBox removeRecord_sctn;
    @FXML
    private Group removeCook_sctn;
    @FXML
    private Group removeDeliPerson_sctn;
    @FXML
    private Group removeCustomer_sctn;
    @FXML
    private Group removeComponent_sctn;
    @FXML
    private Group removeDish_sctn;
    @FXML
    private Group removeOrder_sctn;
    @FXML
    private Group removeDelivery_sctn;
    @FXML
    private Group removeArea_sctn;
    @FXML
    private Group removeToBlacklist_sctn;
    @FXML
    private ComboBox<String> selectionBox;
    @FXML
    private Button removeCooks_btn;
    @FXML
    private Button removeAreas_btn;
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
    private Button removeToBlacklist_btn;

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

        menuButtons.add(removeCooks_btn.getId());
        menuButtons.add(removeAreas_btn.getId());
        menuButtons.add(removeDeliPersons_btn.getId());
        menuButtons.add(removeCustomers_btn.getId());
        menuButtons.add(removeComponents_btn.getId());
        menuButtons.add(removeDishes_btn.getId());
        menuButtons.add(removeOrders_btn.getId());
        menuButtons.add(removeDeliveries_btn.getId());

        groups.put(removeCooks_btn.getId(),removeCook_sctn);
        groups.put(removeAreas_btn.getId(), removeArea_sctn);
        groups.put(removeDeliPersons_btn.getId(),removeDeliPerson_sctn);
        groups.put(removeCustomers_btn.getId(),removeCustomer_sctn);
        groups.put(removeComponents_btn.getId(),removeComponent_sctn);
        groups.put(removeDishes_btn.getId(), removeDish_sctn);
        groups.put(removeOrders_btn.getId(), removeOrder_sctn);
        groups.put(removeDeliveries_btn.getId(), removeDelivery_sctn);

        groups.values().forEach(System.out::println);
        groups.values().forEach(g->createSections(g));
    }

    public void handleButtonClick(ActionEvent e) {
        if(e.getSource() instanceof Button) {
            Button btn = (Button) e.getSource();
            if(menuButtons.contains(btn.getId())) {
                for (Group g: groups.values()) {
                    g.setVisible(false);
                }
                groups.get(btn.getId()).setVisible(true);
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
            switch (group.getId()) {
                case "removeCook_sctn": {
                    editListableNode(new Tuple[]{
                            new Tuple<Cook>(grid, 0,
                                    restaurant.getCooks().values().stream().toList(),
                                    "Select Cook to Remove")
                    });
                    break;
                }
                case "removeDeliPerson_sctn": {
                    editListableNode(new Tuple[]{
                            new Tuple<DeliveryPerson>(grid, 0,
                                    restaurant.getDeliveryPersons().values().stream().toList(),
                                    "Select Delivery Person to Remove")
                    });
                    break;
                }
                case "removeCustomer_sctn": {
                    editListableNode(new Tuple[]{
                            new Tuple<Customer>(grid, 0,
                                    restaurant.getCustomers().values().stream().toList(),
                                    "Select Customer to Remove")
                    });
                    break;
                }
                case "removeComponent_sctn": {
                    editListableNode(new Tuple[]{
                            new Tuple<Component>(grid, 0,
                                    restaurant.getComponents().values().stream().toList(),
                                    "Select Ingredient to Remove")
                    });
                    break;
                }
                case "removeDish_sctn": {
                    editListableNode(new Tuple[]{
                            new Tuple<Dish>(grid, 0,
                                    restaurant.getDishes().values().stream().toList(),
                                    "Select Dish to Remove")
                    });
                    break;
                }
                case "removeOrder_sctn": {
                    editListableNode(new Tuple[]{
                            new Tuple<Order>(grid, 0,
                                    restaurant.getOrders().values().stream().toList(),
                                    "Select Order to Remove")
                    });
                    break;
                }
                case "removeDelivery_sctn": {
                    editListableNode(new Tuple[]{
                            new Tuple<Delivery>(grid, 0,
                                    restaurant.getDeliveries().values().stream().toList(),
                                    "Select Delivery to Remove")
                    });
                    break;
                }
                case "removeArea_sctn":{
                    editListableNode(new Tuple[]{
                            new Tuple<DeliveryArea>(grid, 0,
                                    restaurant.getAreas().values().stream().toList(),
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