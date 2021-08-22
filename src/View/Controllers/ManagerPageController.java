package View.Controllers;

import Model.*;
import Model.Requests.RecordRequest;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;

public class ManagerPageController {
    // region FXML comps
    @FXML
    private Label lblGreeting;

    @FXML
    private Pane pnlViewContents;

    @FXML
    private Button view_records;

    // region buttons
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnAddRecords;

    @FXML
    private Button btnEditRecords;

    @FXML
    private Button btnRemoveRecords;

    @FXML
    private Button btnQueries;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignOut;
    // endregion
    // region panels
    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlAddRecords;

    @FXML
    private Pane pnlEditRecords;

    @FXML
    private Pane pnlRemoveRecords;

    @FXML
    private Pane pnlQueries;

    @FXML
    private AnchorPane pnlRecordsManagement;
    // endregion
    // region labels
    @FXML
    private Label numOfDeliveries_lbl;

    @FXML
    private Label numOfDelivered_lbl;

    @FXML
    private Label numOfCustomers_lbl;

    @FXML
    private Label numOfBlacklisted_lbl;
    // endregion

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

    private Restaurant res;
    // endregion
    @FXML
    private void initialize(){
        initOverview();
    }
    @FXML
    private void handleButtonClick(ActionEvent e) {
        if(e.getSource() == btnOverview) {
            initOverview();
            pnlOverview.setStyle("-fx-background-color: #1F4591");
            pnlOverview.toFront();
        }
        if(e.getSource() == btnAddRecords) {
            initRecords();
            pnlAddRecords.setStyle("-fx-background-color: #1F4591");
            pnlAddRecords.toFront();
        }
        if(e.getSource() == btnRemoveRecords){
            initRecords();
            pnlRemoveRecords.setStyle("-fx-background-color: #1F4591");
            pnlRemoveRecords.toFront();
        }
        if (e.getSource() == btnEditRecords){
            initRecords();
            pnlEditRecords.setStyle("-fx-background-color: #1F4591");
            pnlEditRecords.toFront();
        }

        if(e.getSource() == btnQueries) {
            initQueries();
            pnlQueries.setStyle("-fx-background-color: #13194c");
            pnlQueries.toFront();
        }
        if(e.getSource() == view_records) {
            initViewRecords();
            pnlViewContents.setStyle("-fx-background-color: #13194c");
            pnlViewContents.toFront();
        }
        if(e.getSource() == btnSignOut) {
            try {
                Stage s = (Stage) btnSignOut.getScene().getWindow();
                Parent p = FXMLLoader.load(getClass().getResource("../fxmls/LoginPage.fxml"));
                s.hide();
                s.setScene(new Scene(p));
                s.centerOnScreen();
                s.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
    private void initOverview() {
        res = Restaurant.getInstance();
        numOfDeliveries_lbl.setText(String.format("%d",res.getDeliveries().size()));
        numOfDelivered_lbl.setText(String.format("%d",res.getDeliveries().values()
                .stream().filter(Delivery::isDelivered).toList().size()));
        numOfCustomers_lbl.setText(String.format("%d",res.getCustomers().size()));
        numOfBlacklisted_lbl.setText(String.format("%d",res.getBlacklist().size()));

        pnlOverview.setStyle("-fx-background-color: #1F4591");
        pnlOverview.toFront();
    }
    private void initRecords() {
            try {
                pnlAddRecords.getChildren().clear();
                Node recordsA = FXMLLoader.load(getClass().getResource("../fxmls/addRecordsPage/addRecords.fxml"));
                pnlAddRecords.getChildren().add(recordsA);
            } catch(IOException e){
                System.out.println(e.getMessage());
            }
            try{
                pnlRemoveRecords.getChildren().clear();
                Node recordsR = FXMLLoader.load(getClass().getResource("../fxmls/removeRecordsPage/removeRecords.fxml"));;
                pnlRemoveRecords.getChildren().add(recordsR);
            }catch (IOException e) {
                System.out.println(e.getMessage());
            }
            try {
                pnlEditRecords.getChildren().clear();
                Node recordsE = FXMLLoader.load(getClass().getResource("../fxmls/editRecordsPage/editRecords.fxml"));
                pnlEditRecords.getChildren().add(recordsE);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
    }

    // This makes the query button clickable
    private void initQueries(){
        try{
            Node queryPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../fxmls/Queries.fxml")));
            pnlQueries.getChildren().add(queryPage);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    // This makes the View Records button clickable
    private void initViewRecords(){
        try{
            Node ViewRecordsPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../fxmls/ViewStructures.fxml")));
            pnlViewContents.getChildren().add(ViewRecordsPage);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private void initDashboard(Event e){
        if(res == null)
            res = Restaurant.getInstance();
        if(e.getSource() == areas_tab){

            numOfAreas.setText(res.getAreas().values().size()+"");
            areas_history.getItems().clear();
            try {
                areas_history.getItems().addAll(res.getAddRecordHistory().get(DeliveryArea.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                areas_history.getItems().addAll(res.getRemoveRecordHistory().get(DeliveryArea.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            areas_history.getItems().sort(
                    Comparator.comparing(RecordRequest::getDateOfRequest)
                            .thenComparing(RecordRequest::getTimeOfRequest)
                            .thenComparing(rr->rr.getRecord().getId())
            );
        }

        if(e.getSource() == customers_tab){
            numOfCustomers.setText(
                    res.getCustomers().values().stream()
                            .filter(c->!res.getBlacklist().contains(c)).toList().size()+""
            );
            numOfBlacklisted.setText(res.getBlacklist().size()+"");
            customers_history.getItems().clear();
            try {
                customers_history.getItems().addAll(res.getAddRecordHistory().get(Customer.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                customers_history.getItems().addAll(res.getRemoveRecordHistory().get(Customer.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                customers_history.getItems().addAll(res.getAddRecordHistory().get("Blacklist").stream().toList());
            }catch (NullPointerException ex){

            }
            customers_history.getItems().sort(
                    Comparator.comparing(RecordRequest::getDateOfRequest)
                            .thenComparing(RecordRequest::getTimeOfRequest)
                            .thenComparing(rr->rr.getRecord().getId())
            );
        }

        if(e.getSource() == delipersons_tab){
            numOfDeliPersons.setText(res.getDeliveryPersons().values().size()+"");
            res.getAddRecordHistory().get(DeliveryPerson.class.getSimpleName()).forEach(System.out::println);
            delipersons_history.getItems().clear();
            try {
                delipersons_history.getItems().addAll(res.getAddRecordHistory().get(DeliveryPerson.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                delipersons_history.getItems().addAll(res.getRemoveRecordHistory().get(DeliveryPerson.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            delipersons_history.getItems().sort(
                    Comparator.comparing(RecordRequest::getDateOfRequest)
                            .thenComparing(RecordRequest::getTimeOfRequest)
                            .thenComparing(rr->rr.getRecord().getId())
            );
        }

        if(e.getSource() == cooks_tab){
            numOfCooks.setText(res.getCustomers().values().size()+"");
            cooks_history.getItems().clear();
            try {
                cooks_history.getItems().addAll(res.getAddRecordHistory().get(Cook.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                cooks_history.getItems().addAll(res.getRemoveRecordHistory().get(Cook.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            cooks_history.getItems().sort(
                    Comparator.comparing(RecordRequest::getDateOfRequest)
                            .thenComparing(RecordRequest::getTimeOfRequest)
                            .thenComparing(rr->rr.getRecord().getId())
            );
        }

        if(e.getSource() == components_tab){
            numOfComponents.setText(res.getComponents().values().size()+"");
            components_history.getItems().clear();
            try {
                components_history.getItems().addAll(res.getAddRecordHistory().get(Component.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                res.getRemoveRecordHistory().get(Component.class.getSimpleName()).forEach(System.out::println);
                components_history.getItems().addAll(res.getRemoveRecordHistory().get(Component.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            components_history.getItems().sort(
                    Comparator.comparing(RecordRequest::getDateOfRequest)
                            .thenComparing(RecordRequest::getTimeOfRequest)
                            .thenComparing(rr->rr.getRecord().getId())
            );
        }

        if(e.getSource() == dishes_tab){
            numOfDishes.setText(res.getDishes().values().size()+"");
            dishes_history.getItems().clear();
            try {
                dishes_history.getItems().addAll(res.getAddRecordHistory().get(Dish.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                dishes_history.getItems().addAll(res.getRemoveRecordHistory().get(Dish.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            dishes_history.getItems().sort(
                    Comparator.comparing(RecordRequest::getDateOfRequest)
                            .thenComparing(RecordRequest::getTimeOfRequest)
                            .thenComparing(rr->rr.getRecord().getId())
            );
        }

        if(e.getSource() == orders_tab){
            numOfOrders.setText(res.getOrders().values().size()+"");
            orders_history.getItems().clear();
            try {
                orders_history.getItems().addAll(res.getAddRecordHistory().get(Order.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try{
                orders_history.getItems().addAll(res.getRemoveRecordHistory().get(Order.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            orders_history.getItems().sort(
                    Comparator.comparing(RecordRequest::getDateOfRequest)
                            .thenComparing(RecordRequest::getTimeOfRequest)
                            .thenComparing(rr->rr.getRecord().getId())
            );
        }

        if(e.getSource() == deliveries_tab){
            numOfDeliveries.setText(res.getDeliveries().values().size()+"");
            numOfRegular.setText(res.getDeliveries().values().stream().filter(d->d instanceof RegularDelivery).toList().size()+"");
            numOfExpress.setText(res.getDeliveries().values().stream().filter(d->d instanceof ExpressDelivery).toList().size()+"");
            numOfDelivered.setText(res.getDeliveries().values().stream().filter(Delivery::isDelivered).toList().size()+"");
            deliveries_history.getItems().clear();
            try {
                deliveries_history.getItems().addAll(res.getAddRecordHistory().get(RegularDelivery.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try {
                deliveries_history.getItems().addAll(res.getAddRecordHistory().get(ExpressDelivery.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try {
                deliveries_history.getItems().addAll(res.getRemoveRecordHistory().get(RegularDelivery.class.getSimpleName()).stream().toList());
            }catch (NullPointerException ex){

            }
            try {
                deliveries_history.getItems().addAll(res.getRemoveRecordHistory().get(ExpressDelivery.class.getSimpleName()).stream().toList());
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