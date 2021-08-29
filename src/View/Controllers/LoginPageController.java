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

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer customer) {
        LoginPageController.customer = customer;
    }

    @FXML
    private void LoginButtonOnAction(ActionEvent e) {
        if (usernameField.getText().equals("m") && passwordField.getText().equals("m")) {
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

    @FXML
    private void cancelButtonOnAction(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void registerButtonOnAction(ActionEvent e) {
        try {
            Stage s = (Stage) registerButton.getScene().getWindow();
            Parent p = FXMLLoader.load(getClass().getResource("SignupPage.fxml"));
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
            ControllerUtils.changeScreen(s, p);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void handleOnAction(ActionEvent e) {
        if (e.getSource() == loginButton) {
            LoginButtonOnAction(e);
        }
        if (e.getSource() == cancelButton) {
            cancelButtonOnAction(e);
        }
    }


//    @FXML
//    private Text actiontarget;
//    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
//        actiontarget.setText("Sign in button pressed");
//    }
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
//
//        Scene scene = new Scene(root);
//
//        stage.setTitle("FXML Welcome");
//        stage.setScene(scene);
//        stage.show();
//    }

}