package View.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Helper class to organize the code
 */
public class ControllerUtils {

    public static void showAlertMessage(Group section,String prompt){
        Pane grid = (Pane) section.getChildren().get(0);
        ((Label) grid.getChildren().get(1)).setText(prompt);
    }

    /**
     * Changes the current scene with another
     * @param s
     * The window we want to affect
     * @param p
     * The new scene we want to replace with
     * @throws IOException
     */
    protected static void changeScreen(Stage s, Parent p) throws IOException{
        s.hide();
        s.setScene(new Scene(p));
        s.centerOnScreen();
        s.show();
    }

    /**
     * makes a text input restriction manager based on certain pattern
     * @param pattern
     * the restriction pattern
     * @return
     *the restriction manager
     */
    protected static TextFormatter<String> textFormatter(Pattern pattern){
        return new TextFormatter<String>(
                change-> {
                    if (pattern.matcher(change.getControlNewText()).matches()) {
                        // todo: remove error message/markup
                        return change; // allow this change to happen
                    } else {
                        // todo: add error message/markup
                        return null; // prevent change
                    }
                }
        );
    }

    /**
     * creates an alert system for a text-field using a restriction manager pattern
     * @param field
     * the field we want to attach the alert system to
     * @param pattern
     * the restriction pattern attached to the field
     * @param alert
     * the alert label we want to display the alert message
     */
    public static void setAlerts(TextField field, Pattern pattern, Label alert) {
        field.setOnKeyTyped(ke-> {
            if(!Objects.equals(ke.getCharacter(), "\b")) {
                String newText = field.getText() + ke.getCharacter();
                if (pattern.matcher(newText).matches()) {
                    field.getStyleClass().clear();
                    field.getStyleClass().add("text-input");
                    field.getStyleClass().add("text-field");
                    alert.setText("");
                }
                else {
                    field.getStyleClass().clear();
                    field.getStyleClass().add("text-input");
                    if(field.getText().length()>0) {
                        field.getStyleClass().add("WarningAlert");
                        alert.setText("Illegal character entered");
                        alert.setStyle("-fx-text-fill: gold");
                    }
                    else {
                        field.getStyleClass().add("ErrorAlert");
                    }
                }
            }
            else{
                field.getStyleClass().clear();
                field.getStyleClass().add("text-input");
                field.getStyleClass().add("text-field");
                alert.setText("");
            }
        });
        field.focusedProperty().addListener((obs, o, n) -> {
            if(field.getText().length()>0) {
                if (!n) {
                    field.getStyleClass().remove("WarningAlert");
                    field.getStyleClass().remove("ErrorAlert");
                }
            }
            else{
                field.getStyleClass().clear();
                field.getStyleClass().add("text-input");
                alert.setText("");
            }
        });
    }

    public static void setFileChooser(Button btn, ImageView imgView){
        btn.setOnAction(action->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );
            File selectedFile = fileChooser.showOpenDialog(btn.getScene().getWindow());
            if (selectedFile != null) {
                try {
                    BufferedImage bi = ImageIO.read(selectedFile);
                    Image img = SwingFXUtils.toFXImage(bi, null);
                    imgView.setImage(img);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * creates a string converter for a date picker
     * @return
     * the string converter
     */
    public static StringConverter<LocalDate> getStringConverter(){
        return new StringConverter<LocalDate>() {
            private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            @Override
            public String toString(LocalDate localDate) {
                if(localDate==null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }
            @Override
            public LocalDate fromString(String dateString) {
                if(dateString==null || dateString.trim().isEmpty())
                    return null;
                try{
                    return LocalDate.parse(dateString,dateTimeFormatter);
                }
                catch(Exception e){
                    //Bad date value entered
                    return null;
                }
            }
        };
    }

    /**
     * Determines a strength of a password
     * @param passwordField
     * the password field
     * @param strengthLbl
     * the label to show the strength
     * @param passwordStrengthInd
     * the bar to show the strength
     */
    public static void determinePasswordStrength(PasswordField passwordField, Label strengthLbl, ProgressBar passwordStrengthInd){
        String password = passwordField.getText();
        if(password.length() == 0){
            strengthLbl.setText("");
            passwordStrengthInd.setProgress(0);
        }
        else if(password.length()<6){
            strengthLbl.setText("none");

            strengthLbl.setStyle("-fx-text-fill: black");
            passwordStrengthInd.setProgress(0.1);
        }
        else{
            if(password.matches("[0-9]*")) {
                strengthLbl.setText("low");
                strengthLbl.setStyle("-fx-text-fill: red");
                passwordStrengthInd.setProgress(0.3);
            }
            else{
                if(password.length() <=8){
                    strengthLbl.setText("low");
                    strengthLbl.setStyle("-fx-text-fill: red");
                    passwordStrengthInd.setProgress(0.3);
                }
                else if(password.length() <= 10){
                    if(password.matches("^[a-zA-Z0-9]+$")) {
                        strengthLbl.setText("fair");
                        strengthLbl.setStyle("-fx-text-fill: orange");
                        passwordStrengthInd.setProgress(0.45);
                    }
                    else{
                        strengthLbl.setText("good");
                        strengthLbl.setStyle("-fx-text-fill: yellow");
                        passwordStrengthInd.setProgress(0.65);
                    }
                }
                else {
                    if(password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d).+$")
                    ){
                        strengthLbl.setText("strong");
                        strengthLbl.setStyle("-fx-text-fill: green");
                        passwordStrengthInd.setProgress(0.85);
                    }
                    else{
                        if(password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$")){
                            strengthLbl.setText("very strong");
                            strengthLbl.setStyle("-fx-text-fill: darkgreen");
                            passwordStrengthInd.setProgress(0.95);
                        }
                        else{
                            strengthLbl.setText("good");
                            strengthLbl.setStyle("-fx-text-fill: yellow");
                            passwordStrengthInd.setProgress(0.65);
                        }
                    }
                }
            }
        }
    }

    /**
     * initializes the password strength indicator colors
     * @param passwordStrengthInd
     * the progress bar served as an indicator
     */
    public static void initStrengthIndicator(ProgressBar passwordStrengthInd){
        passwordStrengthInd.progressProperty().addListener(new ChangeListener<>() {
            private static final String DARKRED_BAR    = "darkred-bar";
            private static final String RED_BAR    = "red-bar";
            private static final String ORANGE_BAR = "orange-bar";
            private static final String YELLOW_BAR = "yellow-bar";
            private static final String GREEN_BAR  = "green-bar";
            private static final String[] barColorStyleClasses = {DARKRED_BAR, RED_BAR, ORANGE_BAR, YELLOW_BAR, GREEN_BAR };
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
}