package View.Controllers;

import Model.*;
import Model.Requests.RecordRequest;
import Utils.SFXManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;

import java.util.Comparator;


/**
 * A controller for a restaurant dashboard page that shows its statistics (when a certain record was added/ removed / edited)
 * @author Eddie Kanevsky
 */
public class RestaurantDashboardController extends RecordManagementController{

    @FXML
    private Tab areas_tab;
    @FXML
    private Tab customers_tab;
    @FXML
    private Tab delipersons_tab;
    @FXML
    private Tab cooks_tab;
    @FXML
    private Tab components_tab;
    @FXML
    private Tab dishes_tab;
    @FXML
    private Tab orders_tab;
    @FXML
    private Tab deliveries_tab;

    @FXML
    private ListView<RecordRequest> areas_history;
    @FXML
    private ListView<RecordRequest> customers_history;
    @FXML
    private ListView<RecordRequest> delipersons_history;
    @FXML
    private ListView<RecordRequest> cooks_history;
    @FXML
    private ListView<RecordRequest> components_history;
    @FXML
    private ListView<RecordRequest> dishes_history;
    @FXML
    private ListView<RecordRequest> orders_history;
    @FXML
    private ListView<RecordRequest> deliveries_history;

    @FXML
    private Label numOfAreas;
    @FXML
    private Label numOfCooks;
    @FXML
    private Label numOfCustomers;
    @FXML
    private Label numOfBlacklisted;
    @FXML
    private Label numOfDeliPersons;
    @FXML
    private Label numOfComponents;
    @FXML
    private Label numOfDishes;
    @FXML
    private Label numOfOrders;
    @FXML
    private Label numOfDeliveries;
    @FXML
    private Label numOfRegular;
    @FXML
    private Label numOfExpress;
    @FXML
    private Label numOfDelivered;

    /**
     * initializes the dashboard and populates it with the requests info
     */
    @FXML
    private void initDashboard(Event e){
        if(e.getSource() == areas_tab){
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");

            numOfAreas.setText(getRestaurant().getAreas().values().size()+"");
            areas_history.getItems().clear();
            try {
                areas_history.getItems().addAll(getRestaurant().getAddRecordHistory().get(DeliveryArea.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                areas_history.getItems().addAll(getRestaurant().getRemoveRecordHistory().get(DeliveryArea.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                areas_history.getItems().addAll(getRestaurant().getEditRecordHistory().get(DeliveryArea.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            areas_history.getItems().sort(
                    Comparator.comparing(RecordRequest::getDateOfRequest)
                            .thenComparing(RecordRequest::getTimeOfRequest)
                            .thenComparing(rr->rr.getRecord().getId())
            );
        }

        if(e.getSource() == customers_tab){
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
            numOfCustomers.setText(
                    getRestaurant().getCustomers().values().stream()
                            .filter(c->!getRestaurant().getBlacklist().contains(c)).toList().size()+""
            );
            numOfBlacklisted.setText(getRestaurant().getBlacklist().size()+"");
            customers_history.getItems().clear();
            try {
                customers_history.getItems().addAll(getRestaurant().getAddRecordHistory().get(Customer.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                customers_history.getItems().addAll(getRestaurant().getRemoveRecordHistory().get(Customer.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                customers_history.getItems().addAll(getRestaurant().getEditRecordHistory().get(Customer.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                customers_history.getItems().addAll(getRestaurant().getAddRecordHistory().get("Blacklist").stream().toList());
            }catch (NullPointerException ex){

            }
            customers_history.getItems().sort(
                    Comparator.comparing(RecordRequest::getDateOfRequest)
                            .thenComparing(RecordRequest::getTimeOfRequest)
                            .thenComparing(rr->rr.getRecord().getId())
            );
        }

        if(e.getSource() == delipersons_tab){
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
            numOfDeliPersons.setText(getRestaurant().getDeliveryPersons().values().size()+"");
            delipersons_history.getItems().clear();
            try {
                delipersons_history.getItems().addAll(getRestaurant().getAddRecordHistory().get(DeliveryPerson.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                delipersons_history.getItems().addAll(getRestaurant().getRemoveRecordHistory().get(DeliveryPerson.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                delipersons_history.getItems().addAll(getRestaurant().getEditRecordHistory().get(DeliveryPerson.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            delipersons_history.getItems().sort(
                    Comparator.comparing(RecordRequest::getDateOfRequest)
                            .thenComparing(RecordRequest::getTimeOfRequest)
                            .thenComparing(rr->rr.getRecord().getId())
            );
        }

        if(e.getSource() == cooks_tab){
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
            numOfCooks.setText(getRestaurant().getCooks().values().size()+"");
            cooks_history.getItems().clear();
            try {
                cooks_history.getItems().addAll(getRestaurant().getAddRecordHistory().get(Cook.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                cooks_history.getItems().addAll(getRestaurant().getRemoveRecordHistory().get(Cook.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                cooks_history.getItems().addAll(getRestaurant().getEditRecordHistory().get(Cook.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            cooks_history.getItems().sort(
                    Comparator.comparing(RecordRequest::getDateOfRequest)
                            .thenComparing(RecordRequest::getTimeOfRequest)
                            .thenComparing(rr->rr.getRecord().getId())
            );
        }

        if(e.getSource() == components_tab){
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
            numOfComponents.setText(getRestaurant().getComponents().values().size()+"");
            components_history.getItems().clear();
            try {
                components_history.getItems().addAll(getRestaurant().getAddRecordHistory().get(Component.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                components_history.getItems().addAll(getRestaurant().getRemoveRecordHistory().get(Component.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                components_history.getItems().addAll(getRestaurant().getEditRecordHistory().get(DeliveryArea.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            components_history.getItems().sort(
                    Comparator.comparing(RecordRequest::getDateOfRequest)
                            .thenComparing(RecordRequest::getTimeOfRequest)
                            .thenComparing(rr->rr.getRecord().getId())
            );
        }

        if(e.getSource() == dishes_tab){
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
            numOfDishes.setText(getRestaurant().getDishes().values().size()+"");
            dishes_history.getItems().clear();
            try {
                dishes_history.getItems().addAll(getRestaurant().getAddRecordHistory().get(Dish.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                dishes_history.getItems().addAll(getRestaurant().getRemoveRecordHistory().get(Dish.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                dishes_history.getItems().addAll(getRestaurant().getEditRecordHistory().get(Dish.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            dishes_history.getItems().sort(
                    Comparator.comparing(RecordRequest::getDateOfRequest)
                            .thenComparing(RecordRequest::getTimeOfRequest)
                            .thenComparing(rr->rr.getRecord().getId())
            );
        }

        if(e.getSource() == orders_tab){
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
            numOfOrders.setText(getRestaurant().getOrders().values().size()+"");
            orders_history.getItems().clear();
            try {
                orders_history.getItems().addAll(getRestaurant().getAddRecordHistory().get(Order.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                orders_history.getItems().addAll(getRestaurant().getRemoveRecordHistory().get(Order.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                orders_history.getItems().addAll(getRestaurant().getEditRecordHistory().get(Order.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            orders_history.getItems().sort(
                    Comparator.comparing(RecordRequest::getDateOfRequest)
                            .thenComparing(RecordRequest::getTimeOfRequest)
                            .thenComparing(rr->rr.getRecord().getId())
            );
        }

        if(e.getSource() == deliveries_tab){
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
            numOfDeliveries.setText(getRestaurant().getDeliveries().values().size()+"");
            numOfRegular.setText(getRestaurant().getDeliveries().values().stream().filter(d->d instanceof RegularDelivery).toList().size()+"");
            numOfExpress.setText(getRestaurant().getDeliveries().values().stream().filter(d->d instanceof ExpressDelivery).toList().size()+"");
            numOfDelivered.setText(getRestaurant().getDeliveries().values().stream().filter(Delivery::isDelivered).toList().size()+"");
            deliveries_history.getItems().clear();
            try {
                deliveries_history.getItems().addAll(getRestaurant().getAddRecordHistory().get(RegularDelivery.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try {
                deliveries_history.getItems().addAll(getRestaurant().getAddRecordHistory().get(ExpressDelivery.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try {
                deliveries_history.getItems().addAll(getRestaurant().getRemoveRecordHistory().get(RegularDelivery.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try {
                deliveries_history.getItems().addAll(getRestaurant().getRemoveRecordHistory().get(ExpressDelivery.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                deliveries_history.getItems().addAll(getRestaurant().getEditRecordHistory().get(RegularDelivery.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                deliveries_history.getItems().addAll(getRestaurant().getEditRecordHistory().get(ExpressDelivery.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            deliveries_history.getItems().sort(
                    Comparator.comparing(RecordRequest::getDateOfRequest)
                            .thenComparing(RecordRequest::getTimeOfRequest)
                            .thenComparing(rr->rr.getRecord().getId())
            );
        }
    }
}