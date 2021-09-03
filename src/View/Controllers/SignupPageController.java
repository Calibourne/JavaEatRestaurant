package View.Controllers;

import Model.Customer;
import Model.Requests.AddRecordRequest;
import Model.Restaurant;
import Utils.Gender;
import Utils.Neighberhood;
import Utils.SFXManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * This controller serves the sign-up page of the program
 * @authors Eddie Kanevsky, Daniel Sharon
 */
public class SignupPageController {
    @FXML
    private GridPane signup_grid;
    @FXML
    private GridPane return_grid;
    @FXML
    private TextField fnameInput;
    @FXML
    private TextField lnameInput;
    @FXML
    private TextField usernameField;
    @FXML
    private ComboBox<Gender> genderCb;
    @FXML
    private ComboBox<Neighberhood> neighbourhoodCb;
    @FXML
    private DatePicker birthdateDP;
    @FXML
    private CheckBox glutenCheckbox;
    @FXML
    private CheckBox lactoseCheckbox;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label passStrengthLbl;
    @FXML
    private ProgressBar passwordStrengthInd;
    @FXML
    private Button cancelButton;
    @FXML
    private Button returnButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Label username_alert;
    @FXML
    private Label pass_alert;
    @FXML
    private Label error_label;


    public void initialize() {
        error_label.setText("");
        Pattern passwordValidChars = Pattern.compile("[A-Za-z0-9!@#$%^&*.\\[\\](){}]*");
        Pattern wordPattern = Pattern.compile("(([a-zA-Z]*)([ \\-\']?)([a-zA-Z]*))*");
        Pattern intPattern = Pattern.compile("[0-9]*");
        Pattern stringPattern = Pattern.compile("[ A-Za-z0-9\\-]*");


        signup_grid.setVisible(true);
        signup_grid.setDisable(false);
        return_grid.setDisable(true);
        return_grid.setVisible(false);

        usernameField.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
        ControllerUtils.setAlerts(usernameField, stringPattern, error_label);
        passwordField.setTextFormatter(ControllerUtils.textFormatter(passwordValidChars));
        ControllerUtils.setAlerts(passwordField, passwordValidChars, error_label);
        confirmPasswordField.setTextFormatter(ControllerUtils.textFormatter(passwordValidChars));
        ControllerUtils.setAlerts(confirmPasswordField, passwordValidChars, error_label);
        fnameInput.setTextFormatter(ControllerUtils.textFormatter(wordPattern));
        ControllerUtils.setAlerts(fnameInput, wordPattern, error_label);
        lnameInput.setTextFormatter(ControllerUtils.textFormatter(wordPattern));
        ControllerUtils.setAlerts(lnameInput, wordPattern, error_label);

        birthdateDP.setConverter(ControllerUtils.getStringConverter());

        usernameField.textProperty().addListener(c->{
            if(usernameField.getText().length()==0) {
                username_alert.setVisible(false);
            }
            else{
                Restaurant rest = Restaurant.getInstance();
                if(rest.getUsersList().get(usernameField.getText())!=null){
                    username_alert.setText("This username is already taken!");
                    username_alert.setStyle("-fx-text-fill: red");
                }
                else{
                    username_alert.setText("This username is available");
                    username_alert.setStyle("-fx-text-fill: #00ff00");
                }
                username_alert.setVisible(true);
            }
        });
        passwordField.textProperty().addListener((obs, o, n)->{
            determinePasswordStrength();
            if (!n.equals(confirmPasswordField.getText())) {
                pass_alert.setText("Passwords don't match 😟");
                pass_alert.setStyle("-fx-text-fill: red");
            }
            else {
                pass_alert.setText("Passwords match 👍");
                pass_alert.setStyle("-fx-text-fill: #00ff00");
            }
        });
        confirmPasswordField.textProperty().addListener((obs, o, n)->{
            if (!n.equals(passwordField.getText())) {
                pass_alert.setText("Passwords don't match 😟");
                pass_alert.setStyle("-fx-text-fill: red");
            }
            else {
                pass_alert.setText("Passwords match 👍");
                pass_alert.setStyle("-fx-text-fill: #00ff00");
            }
        });

        genderCb.getItems().addAll(Arrays.stream(Gender.values()).toList());
        neighbourhoodCb.getItems().addAll(Arrays.stream(Neighberhood.values()).toList());

        ControllerUtils.initStrengthIndicator(passwordStrengthInd);
    }


    /**
     * This method serves the register button, prompts the user to log in upon successful account creation
     */
    @FXML
    private void registerButtonOnAction(){
        try{
            error_label.setText("");
           if(!passwordField.getText().equals(confirmPasswordField.getText()) && passwordField.getText().length()>0)
               throw new IllegalArgumentException();
           if(username_alert.getText().contains("taken"))
               throw new IllegalArgumentException();

           String fname = fnameInput.getText();
           String lname = lnameInput.getText();
           Gender gender = genderCb.getValue();
           Neighberhood neighborhood = neighbourhoodCb.getValue();
           boolean glutenIntolerant = glutenCheckbox.isSelected(),
                   lactoseIntolerant = lactoseCheckbox.isSelected();
           LocalDate ld = birthdateDP.getValue();
           AddRecordRequest request = new AddRecordRequest(
                   new Customer(-1),
                   fname,
                   lname,
                   ld,
                   gender,
                   neighborhood,
                   glutenIntolerant,
                   lactoseIntolerant
           );
           ((Customer)request.getRecord()).setProfileImg("Default");
           ((Customer)request.getRecord()).setPassword(passwordField.getText());
           ((Customer) request.getRecord()).setUsername(usernameField.getText());
           request.saveRequest();
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
        }catch(IllegalArgumentException ex){
            error_label.setText("Please fill all the required fields");
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
            return;
        }
        Restaurant.getInstance().saveDatabase("Rest.ser");
        System.out.println("Success!");
        signup_grid.setVisible(false);
        signup_grid.setDisable(true);
        return_grid.setDisable(false);
        return_grid.setVisible(true);

    }

    /**
     * This method serves the cancel button and sends the user back to the login page when prompted
     */
    @FXML
    private void exitButtonOnAction(ActionEvent e){
        if(e.getSource() == returnButton) {
            Restaurant rest = Restaurant.getInstance();
            LoginPageController.setCustomer(rest.getUsersList().get(usernameField.getText()));
        }
        try {
            Stage s = (Stage) cancelButton.getScene().getWindow();
            Parent p = FXMLLoader.load(getClass().getResource("fxmls/LoginPage.fxml"));
            ControllerUtils.changeScreen(s, p);
            SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    @FXML
    private void determinePasswordStrength(){
        ControllerUtils.determinePasswordStrength(passwordField, passStrengthLbl, passwordStrengthInd);
    }
}