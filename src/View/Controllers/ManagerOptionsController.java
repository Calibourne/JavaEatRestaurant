package View.Controllers;

import Model.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ManagerOptionsController {

    @FXML
    private RadioButton updateDB_RB;
    @FXML
    private RadioButton performQ_RB;
    @FXML
    private VBox updateDB_options;
    @FXML
    private VBox performQ_options;
    @FXML
    private Button addRecord_btn;
    @FXML
    private Button removeRecord_btn;

    private Restaurant restaurant;

    public void initialize(){
        restaurant = Restaurant.getInstance();
        updateDB_options.setVisible(false);
        performQ_options.setVisible(false);

    }

    public void handleButtonClick(ActionEvent e) {
        if (e.getSource() == updateDB_RB)
            viewDatabaseOptions();
        if (e.getSource() == performQ_RB)
            viewQueriesOptions();
        if (e.getSource() == addRecord_btn)
        {
            ArrayList<Control> comps = new ArrayList<>();
            comps.add(new Label("Choose which record to add:"));

            try {
                Stage s = new Stage();
                Parent p = FXMLLoader.load(getClass().getResource("RecordWindow.fxml"));
                s.setScene(new Scene(p));
                s.getIcons().add(new Image("/Icon.jpg"));
                s.showAndWait();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            //showPopupWindow("Add Record", comps);
        }
    }

    public void viewDatabaseOptions()
    {
        updateDB_options.setVisible(true);
        performQ_options.setVisible(false);
    }

    public void viewQueriesOptions()
    {
        updateDB_options.setVisible(false);
        performQ_options.setVisible(true);
    }

    public void showPopupWindow(String labelMessage, String buttonMessage)
    {
        Stage popup_window=new Stage();
        popup_window.initModality(Modality.APPLICATION_MODAL);
        popup_window.setTitle("Attention please");
        popup_window.getIcons().add(new Image("Icon.jpg"));
        Label label1= new Label(labelMessage);
        Button button1= new Button(buttonMessage);
        button1.setOnAction(e -> popup_window.close());
        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1, button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popup_window.setScene(scene1);
        popup_window.showAndWait();
    }

    public void showPopupWindow(String title,Collection<Control> components)
    {
        Stage popup_window=new Stage();
        popup_window.initModality(Modality.APPLICATION_MODAL);
        popup_window.setTitle(title);
        popup_window.getIcons().add(new Image("Icon.jpg"));
        VBox layout= new VBox(10);
        layout.getChildren().addAll(components);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popup_window.setScene(scene1);
        popup_window.showAndWait();
    }
}
