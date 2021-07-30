package View.Controllers;

import Model.Customer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.AccessibleRole;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class SignupPageController {
    @FXML
    private TextField fnameInput;
    @FXML
    private TextField lnameInput;
    @FXML
    private TextField usernameField;
    @FXML
    private ToggleButton toggleHidePassword;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField visiblePassField;
    @FXML
    private  PasswordField confirmPasswordField;

    public void initialize() {
        visiblePassField.textProperty().bind(passwordField.textProperty());
        visiblePassField.visibleProperty().bind(toggleHidePassword.selectedProperty());
        passwordField.visibleProperty().bind(toggleHidePassword.selectedProperty().not());
    }

    @FXML
    private void togglePassword(MouseEvent e){
    }
    public void generateUsername(KeyEvent e){
        if(fnameInput.getText().length() > 0 && lnameInput.getText().length() > 0)
            usernameField.setText(fnameInput.getText() + lnameInput.getText() + Customer.getIdCounter()+1);
        else
            usernameField.setText("Enter your full name first");
    }
}
