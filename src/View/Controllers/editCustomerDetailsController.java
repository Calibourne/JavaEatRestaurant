package View.Controllers;

import Model.Customer;
import Model.Requests.EditRecordRequest;
import Model.Restaurant;
import Utils.Gender;
import Utils.Neighberhood;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
        change_lbl.focusedProperty().addListener((obs, o, n) -> {
            change_lbl.setVisible(n);
        });
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
            EditRecordRequest request = new EditRecordRequest(rest.getRealCustomer(user.getId()));
            if(npass_field.getText().length()>0 && npass_field.getText().equals(rpass_field.getText()))
                request = new EditRecordRequest(rest.getRealCustomer(user.getId()),
                    fname_field.getText(), lname_field.getText(), genders_combo.getValue(), birthDate_dp.getValue(),
                    neighbourhoods_combo.getValue(), glutenIntolerant_check.isSelected(), lactoseIntolerant_check.isSelected(),
                    npass_field.getText()
                );
            else
                request = new EditRecordRequest(rest.getRealCustomer(user.getId()),
                        fname_field.getText(), lname_field.getText(), genders_combo.getValue(), birthDate_dp.getValue(),
                        neighbourhoods_combo.getValue(), glutenIntolerant_check.isSelected(), lactoseIntolerant_check.isSelected()
                );
            request.saveRequest();
            Restaurant.getInstance().saveDatabase("Rest.ser");
            change_lbl.setVisible(true);
            change_lbl.requestFocus();
        }
    }

}
