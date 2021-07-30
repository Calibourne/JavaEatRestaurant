package View.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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


    @FXML
    private void LoginButtonOnAction(ActionEvent e){
        if(usernameField.getText().equals("m") && passwordField.getText().equals("m")){
            try {
                Stage s = (Stage) loginButton.getScene().getWindow();
                Parent p = FXMLLoader.load(getClass().getResource("../fxmls/managerPage.fxml"));
                changeScreen(s, p);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        if (usernameField.getText().equals("customer") && passwordField.getText().equals("customer")){
            loginMessageLabel.setText("Placeholder for correct login credentials"); // need to change to a main page prompt
        }
        else
            loginMessageLabel.setText("Username/Password is incorrect. Please try again.");
    }

    @FXML
    private void cancelButtonOnAction(ActionEvent e){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void registerButtonOnAction(ActionEvent e){
        try{
            Stage s = (Stage) registerButton.getScene().getWindow();
            Parent p = FXMLLoader.load(getClass().getResource("../fxmls/SignupPage.fxml"));
            changeScreen(s, p);
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    public void handleOnAction(ActionEvent e) {
        if (e.getSource() == loginButton) {
            LoginButtonOnAction(e);
        }
        if (e.getSource() == cancelButton){
            cancelButtonOnAction(e);
        }
    }

    /**
     * Helper function to organize the code
     * @param s
     * The window we want to affect
     * @param p
     * The new window we want to replace with
     * @throws IOException
     */
    private void changeScreen(Stage s, Parent p) throws IOException{
        s.hide();
        s.setScene(new Scene(p));
        s.centerOnScreen();
        s.show();
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