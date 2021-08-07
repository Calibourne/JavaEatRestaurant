package View.Controllers;

import Model.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A superclass for add/remove/edit records controllers
 * for reference please look up: prototyping design pattern
 */
public abstract class RecordManagementController {
    @FXML
    protected Group welcome_sctn;
    @FXML
    protected Group alert_sctn;
    private Restaurant rest;
    private HashMap<String , Group> groups;
    private HashSet<String> menuButtons;

    public RecordManagementController() {
        setRestaurant(Restaurant.getInstance());
        setGroups(new HashMap<>());
        //setMenuButtons(new HashSet<>());
    }
    protected void initialize(){
    }
    protected void handleButtonClick(ActionEvent e){
        welcome_sctn.setVisible(false);
        alert_sctn.setVisible(false);
    }
    protected HashMap<String, Group> getGroups(){
        return this.groups;
    }
    private void setGroups(HashMap<String, Group> groups){
        this.groups = groups;
    }

    protected Restaurant getRestaurant(){
        return this.rest;
    }
    private void setRestaurant(Restaurant rest){
        this.rest = rest;
    }
}
