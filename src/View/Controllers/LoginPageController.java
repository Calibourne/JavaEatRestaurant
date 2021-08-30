package View.Controllers;

import Model.Customer;
import Model.Restaurant;
import Utils.ImageManager;
import Utils.SFXManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This controller serves the login page
 * @author Daniel Sharon
 */
public class LoginPageController {

    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private Restaurant rest;

    private static Customer customer;

    @FXML
    private void initialize() {
        if (customer != null) {
            usernameField.setText(customer.getUsername());
            passwordField.setText(customer.getPassword());
        }
        rest = Restaurant.getInstance();
        rest.saveDatabase("Rest.ser");
        ImageManager.getInstance().getImages().forEach((k, v) -> System.out.printf("%s: %s\n", k, v));
        //rest.getAddRecordHistory().get(DeliveryPerson.class.getSimpleName()).forEach(System.out::println);
    }

    /**
     * This getter and setter are used to feed the customer attributes to the customer stage pages
     * @return Customer to be logged in
     */
    public static Customer getCustomer() {
        return customer;
    }
    public static void setCustomer(Customer customer) {
        LoginPageController.customer = customer;
    }

    /**
     * This method is used to check the log in details and send the user to the manager customer stages
     */
    @FXML
    private void LoginButtonOnAction() {
        if ((usernameField.getText().equals("manager") && passwordField.getText().equals("manager")) || ((usernameField).getText().equals("m") && passwordField.getText().equals("m"))) {
            try {
                Stage s = (Stage) loginButton.getScene().getWindow();
                Parent p = FXMLLoader.load(getClass().getResource("managerPage.fxml"));
                SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Logon_Sound.wav");
                ControllerUtils.changeScreen(s, p);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }else if (rest.getUsersList().containsKey(usernameField.getText())
                && passwordField.getText().equals(rest.getUsersList().get(usernameField.getText()).getPassword())) {
            try {
                customer = rest.getUsersList().get(usernameField.getText());
                Stage s = (Stage) loginButton.getScene().getWindow();
                Parent p = FXMLLoader.load(getClass().getResource("../CustomerStage/CustomerHome.fxml"));
                SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Logon_sound.wav");
                ControllerUtils.changeScreen(s, p);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } else {
            loginMessageLabel.setText("Username/Password is incorrect. Please try again.");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
        }
    }

    /**
     * Method to close the program. Same purpose as the 'X'  button on Windows
     */
    @FXML
    private void cancelButtonOnAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Method to send the user to the sign up page
     */
    @FXML
    private void registerButtonOnAction() {
        try {
            Stage s = (Stage) registerButton.getScene().getWindow();
            Parent p = FXMLLoader.load(getClass().getResource("SignupPage.fxml"));
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
            ControllerUtils.changeScreen(s, p);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * This method assigns the 'Enter' and 'Esc' buttons to the login and cancel buttons accordingly
     */
    public void handleOnAction(ActionEvent e) {
        if (e.getSource() == loginButton) {
            LoginButtonOnAction();
        }
        if (e.getSource() == cancelButton) {
            cancelButtonOnAction();
        }
    }
}