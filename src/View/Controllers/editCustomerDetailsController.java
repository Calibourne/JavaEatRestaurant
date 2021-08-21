package View.Controllers;

import Model.Customer;
import Model.Requests.EditRecordRequest;
import Model.Restaurant;
import Utils.Gender;
import Utils.ImageManager;
import Utils.Neighberhood;
import View.CustomerStage.CustomerHomeController;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Arrays;

public class editCustomerDetailsController {

    @FXML
    private TextField fname_field;

    @FXML
    private TextField lname_field;

    @FXML
    private DatePicker birthDate_dp;

    @FXML
    private ComboBox<Gender> genders_combo;

    @FXML
    private ComboBox<Neighberhood> neighbourhoods_combo;

    @FXML
    private CheckBox glutenIntolerant_check;

    @FXML
    private CheckBox lactoseIntolerant_check;

    @FXML
    private ImageView img_source;
    @FXML
    private Button img_choose;

    @FXML
    private TextField npass_field;

    @FXML
    private TextField rpass_field;

    @FXML
    private Label username_lbl;

    @FXML
    private Button submit;

    @FXML
    private Label change_lbl;

    Customer user;

    @FXML
    private void initialize(){
        ControllerUtils.setFileChooser(img_choose, img_source);
        try{
            user = LoginPageController.getCustomer();
            fname_field.setText(user.getFirstName());
            lname_field.setText(user.getLastName());
            birthDate_dp.setValue(user.getBirthDay());
            genders_combo.getItems().addAll(Arrays.stream(Gender.values()).toList());
            genders_combo.setValue(user.getGender());
            neighbourhoods_combo.getItems().addAll(Arrays.stream(Neighberhood.values()).toList());
            neighbourhoods_combo.setValue(user.getNeighberhood());
            glutenIntolerant_check.setSelected(user.isSensitiveToGluten());
            lactoseIntolerant_check.setSelected(user.isSensitiveToLactose());
            username_lbl.setText(user.getUsername());
        }catch(NullPointerException e){
            e.getMessage();
        }
        submit.setOnAction(this::handleButtonClick);
    }

    @FXML
    private void handleButtonClick(ActionEvent e) {
        if(e.getSource() == submit){
            Restaurant rest = Restaurant.getInstance();
            EditRecordRequest request = null;
            try{
                request = new EditRecordRequest(rest.getRealCustomer(user.getId()),
                        fname_field.getText(), lname_field.getText(), genders_combo.getValue(), birthDate_dp.getValue(),
                        neighbourhoods_combo.getValue(), glutenIntolerant_check.isSelected(), lactoseIntolerant_check.isSelected(), img_source.getImage());
                request.saveRequest();
            }catch (IllegalArgumentException ex){
                System.out.println(ex.getMessage());
            }
            request.saveRequest();
            Restaurant.getInstance().saveDatabase("Rest.ser");
            //change_lbl.setVisible(true);
            //change_lbl.requestFocus();
        }
    }

}
