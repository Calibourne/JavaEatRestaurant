package View.Controllers;

import Model.Restaurant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A superclass for add/remove/edit records controllers
 * for reference please lookup: prototyping design pattern
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
        setMenuButtons(new HashSet<>());
    }
    protected void initialize(Collection<Group> groupList, Collection<Button> buttons){
        HashMap<String, Button> buttonDict = (HashMap<String, Button>) buttons.stream()
                .collect(Collectors.toMap(Button::getId,Function.identity()));
        groupList.forEach(g->groups.put(buttonDict
                .get(g.getId().replace("sctn","btn")).getId(),g));
        buttons.forEach(b->menuButtons.add(b.getId()));
        groups.values().forEach(this::createSections);
        welcome_sctn.setVisible(true);
    }
    public void handleButtonClick(ActionEvent e){
        welcome_sctn.setVisible(false);
        alert_sctn.setVisible(false);
        if(e.getSource() instanceof Button) {
            Button btn = (Button) e.getSource();
            if(getMenuButtons().contains(btn.getId())) {
                for (Group g: getGroups().values()) {
                    g.setVisible(false);
                }
                getGroups().get(btn.getId()).setVisible(true);
            }
        }
    }
    protected HashMap<String, Group> getGroups(){
        return this.groups;
    }
    private void setGroups(HashMap<String, Group> groups){
        this.groups = groups;
    }

    protected HashSet<String> getMenuButtons() {
        return menuButtons;
    }
    private void setMenuButtons(HashSet<String> menuButtons){
        this.menuButtons = menuButtons;
    }
    protected Restaurant getRestaurant(){
        return this.rest;
    }
    private void setRestaurant(Restaurant rest){
        this.rest = rest;
    }
    protected abstract void createSections(Group g);
}
