package View.Controllers;

import Model.Restaurant;

/**
 * A superclass for classes that do database management
 * @author Eddie Kanevsky
 */
public abstract class RecordManagementController {
    private Restaurant rest;

    public RecordManagementController() {
        setRestaurant(Restaurant.getInstance());
    }
    protected Restaurant getRestaurant(){
        return this.rest;
    }
    private void setRestaurant(Restaurant rest){
        this.rest = rest;
    }
}
