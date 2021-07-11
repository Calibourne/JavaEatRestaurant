package View.Controllers;

import Model.Restaurant;
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
