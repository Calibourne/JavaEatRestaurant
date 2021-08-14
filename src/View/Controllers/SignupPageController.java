package View.Controllers;

import Model.Customer;
import Model.Requests.AddRecordRequest;
import Model.Restaurant;
import Utils.Gender;
import Utils.Neighberhood;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.StyleClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleRole;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;


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
    private ToggleButton toggleHidePassword;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField visiblePassField;
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
    private Label suggested_lbl;

    private static final String DARKRED_BAR    = "darkred-bar";
    private static final String RED_BAR    = "red-bar";
    private static final String ORANGE_BAR = "orange-bar";
    private static final String YELLOW_BAR = "yellow-bar";
    private static final String GREEN_BAR  = "green-bar";
    private static final String[] barColorStyleClasses = {DARKRED_BAR, RED_BAR, ORANGE_BAR, YELLOW_BAR, GREEN_BAR };

    public void initialize() {
        signup_grid.setVisible(true);
        signup_grid.setDisable(false);
        return_grid.setDisable(true);
        return_grid.setVisible(false);
        visiblePassField.visibleProperty().bind(toggleHidePassword.selectedProperty());
        passwordField.visibleProperty().bind(toggleHidePassword.selectedProperty().not());
        visiblePassField.visibleProperty().addListener((opt,oldValue, newValue)->{
            if(newValue){
                visiblePassField.textProperty().unbind();
                passwordField.textProperty().bind(visiblePassField.textProperty());
            }
            else{
                passwordField.textProperty().unbind();
                visiblePassField.textProperty().bind(passwordField.textProperty());
            }
        });

        usernameField.textProperty().addListener(c->{
            if(usernameField.getText().length()==0) {
                username_alert.setVisible(false);
            }
            else{
                Restaurant rest = Restaurant.getInstance();
                if(rest.getUsersList().get(usernameField.getText())!=null){
                    username_alert.setText("This Username is already taken, please try again!");
                    username_alert.setStyle("-fx-text-fill: red");
                }
                else{
                    username_alert.setText("This username still available, Hooray!");
                    username_alert.setStyle("-fx-text-fill: #00ff00");
                }
                username_alert.setVisible(true);
            }
        });

        genderCb.getItems().addAll(Arrays.stream(Gender.values()).toList());
        neighbourhoodCb.getItems().addAll(Arrays.stream(Neighberhood.values()).toList());

        passwordStrengthInd.progressProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double progress = newValue == null ? 0 : newValue.doubleValue();
                if (progress < 0.2) {
                    setBarStyleClass(passwordStrengthInd, DARKRED_BAR);
                } else if (progress < 0.4) {
                    setBarStyleClass(passwordStrengthInd, RED_BAR);
                } else if (progress < 0.6) {
                    setBarStyleClass(passwordStrengthInd, ORANGE_BAR);
                } else if(progress < 0.8) {
                    setBarStyleClass(passwordStrengthInd, YELLOW_BAR);
                }
                else {
                    setBarStyleClass(passwordStrengthInd, GREEN_BAR);
                }
            }

            private void setBarStyleClass(ProgressBar bar, String barStyleClass) {
                bar.getStyleClass().removeAll(barColorStyleClasses);
                bar.getStyleClass().add(barStyleClass);
            }
        });
    }


    @FXML
    private void generateUsername(KeyEvent e){
    if (fnameInput.getText().length() > 0 && lnameInput.getText().length() > 0)
        suggested_lbl.setText(String.format("%s%s%d",fnameInput.getText(),lnameInput.getText(), Customer.getIdCounter()));
    else
        suggested_lbl.setText("Enter your full name first");
    }


    @FXML
    private void registerButtonOnAction(ActionEvent e){
        try{
           if(!passwordField.getText().equals(confirmPasswordField.getText()))
               throw new Exception();
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
           ((Customer)request.getRecord()).setPassword(passwordField.getText());
           if(usernameField.getText().length()>0)
               ((Customer) request.getRecord()).setUsername(usernameField.getText());
               request.saveRequest();
        }catch(Exception ex){
            System.out.println("Try again!");
            return;
        }
        Restaurant.getInstance().saveDatabase("Rest.ser");
        System.out.println("Success!");
        signup_grid.setVisible(false);
        signup_grid.setDisable(true);
        return_grid.setDisable(false);
        return_grid.setVisible(true);

    }

    @FXML
    private void exitButtonOnAction(ActionEvent e){
        if(e.getSource() == returnButton) {
            Restaurant rest = Restaurant.getInstance();
            LoginPageController.setCustomer(rest.getUsersList().get(usernameField.getText()));
        }
        try {
            Stage s = (Stage) cancelButton.getScene().getWindow();
            Parent p = FXMLLoader.load(getClass().getResource("../fxmls/LoginPage.fxml"));
            ControllerUtils.changeScreen(s, p);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Determines a strength of a password.
     * The code below uses regex (regular expressions),
     * so here is a brief explanation about each of the commands:
     * [a-z]+ : the sequence has at least 1 character, and it's a lowercase english letter only
     * [a-z]* : the sequence has only lower
     * @param e
     * The key event
     *
     */
    @FXML
    private void determinePasswordStrength(KeyEvent e){
        String password = passwordField.getText();
        if(!password.matches("[A-Za-z0-9!@#$%^&*]*"))
            try {
                passwordField.setText(password.substring(0, password.length() - 1));
            }catch(StringIndexOutOfBoundsException se){
                passwordField.setText("");
            }
        if(password.length() == 0){
            passStrengthLbl.setText("");
            passwordStrengthInd.setProgress(0);
        }
        else if(password.length()<6){
            passStrengthLbl.setText("none");

            passStrengthLbl.setStyle("-fx-text-fill: black");
            passwordStrengthInd.setProgress(0.1);
        }
        else{
            if(password.matches("[0-9]*")) {
                passStrengthLbl.setText("low");
                passStrengthLbl.setStyle("-fx-text-fill: red");
                passwordStrengthInd.setProgress(0.3);
            }
            else{
                if(password.length() <=8){
                    passStrengthLbl.setText("low");
                    passStrengthLbl.setStyle("-fx-text-fill: red");
                    passwordStrengthInd.setProgress(0.3);
                }
                else if(password.length() <= 10){
                    if(password.matches("^[a-zA-Z0-9]+$")) {
                        passStrengthLbl.setText("fair");
                        passStrengthLbl.setStyle("-fx-text-fill: orange");
                        passwordStrengthInd.setProgress(0.45);
                    }
                    else{
                        passStrengthLbl.setText("good");
                        passStrengthLbl.setStyle("-fx-text-fill: yellow");
                        passwordStrengthInd.setProgress(0.65);
                    }
                }
                else{
                    if(password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d).+$")
                    ){
                        passStrengthLbl.setText("strong");
                        passStrengthLbl.setStyle("-fx-text-fill: green");
                        passwordStrengthInd.setProgress(0.85);
                    }
                    else{
                        if(password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")){
                            passStrengthLbl.setText("very strong");
                            passStrengthLbl.setStyle("-fx-text-fill: darkgreen");
                            passwordStrengthInd.setProgress(0.95);
                        }
                        else{
                            passStrengthLbl.setText("good");
                            passStrengthLbl.setStyle("-fx-text-fill: yellow");
                            passwordStrengthInd.setProgress(0.65);
                        }
                    }
                }
            }
        }
    }
}
