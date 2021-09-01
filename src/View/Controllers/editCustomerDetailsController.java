package View.Controllers;

import Model.Customer;
import Model.Requests.EditRecordRequest;
import Model.Restaurant;
import Utils.Gender;
import Utils.Neighberhood;
import Utils.SFXManager;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * the controller for customer's user settings
 * @author Eddie Kanevsky
 */
public class editCustomerDetailsController{

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
    private PasswordField npass_field;

    @FXML
    private PasswordField rpass_field;

    @FXML
    private Label username_lbl;

    @FXML
    private Button submit;

    @FXML
    private Label change_lbl;

    @FXML
    private Label pass_alert;

    @FXML
    private Label passStrengthLbl;

    @FXML
    private ProgressBar passwordStrengthInd;

    Customer user;

    @FXML
    private void initialize(){
        Pattern stringPattern = Pattern.compile("(([a-zA-Z]*)([ \\-\']?)([a-zA-Z]*))*");

        ControllerUtils.setFileChooser(img_choose, img_source);
        try{
            user = LoginPageController.getCustomer();
            fname_field.setText(user.getFirstName());
            fname_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
            ControllerUtils.setAlerts(fname_field, stringPattern ,change_lbl);
            lname_field.setText(user.getLastName());
            lname_field.setTextFormatter(ControllerUtils.textFormatter(stringPattern));
            ControllerUtils.setAlerts(lname_field, stringPattern ,change_lbl);
            birthDate_dp.setValue(user.getBirthDay());
            genders_combo.getItems().addAll(Arrays.stream(Gender.values()).toList());
            genders_combo.setValue(user.getGender());
            neighbourhoods_combo.getItems().addAll(Arrays.stream(Neighberhood.values()).toList());
            neighbourhoods_combo.setValue(user.getNeighberhood());
            glutenIntolerant_check.setSelected(user.isSensitiveToGluten());
            lactoseIntolerant_check.setSelected(user.isSensitiveToLactose());
            img_source.setImage(SwingFXUtils.toFXImage(user.getProfileImg(false), null));
            username_lbl.setText(user.getUsername());
        }catch(NullPointerException e){
            e.getMessage();
        }
        npass_field.textProperty().addListener((obs, o, n)->{
            if(npass_field.getText().equals(rpass_field.getText())){
                if(npass_field.getText().equals("")){
                    pass_alert.setText("");
                }
                else{
                    pass_alert.setStyle("-fx-text-fill: #00ff00");
                    pass_alert.setText("Passwords match 👍");
                }
            }else {
                pass_alert.setStyle("-fx-text-fill: red");
                pass_alert.setText("Passwords don't match 😟");
            }
        });
        rpass_field.textProperty().addListener((obs, o, n)->{
            if(rpass_field.getText().equals(npass_field.getText())){
                pass_alert.setStyle("-fx-text-fill: #00ff00");
                pass_alert.setText("Passwords match 👍");
            }else {
                pass_alert.setStyle("-fx-text-fill: red");
                pass_alert.setText("Passwords don't match 😟");
            }
        });
        submit.setOnAction(this::handleButtonClick);
        npass_field.setOnKeyTyped(this::determinePasswordStrength);
        ControllerUtils.initStrengthIndicator(passwordStrengthInd);
    }

    private void determinePasswordStrength(KeyEvent e){
        ControllerUtils.determinePasswordStrength(npass_field, passStrengthLbl, passwordStrengthInd);
    }

    @FXML
    private void handleButtonClick(ActionEvent e) {
        if(e.getSource() == submit){
            change_lbl.setText("");
            Restaurant rest = Restaurant.getInstance();
            EditRecordRequest request = null;
            String fname = (fname_field.getText().length() > 0)? fname_field.getText():null;
            String lname = (lname_field.getText().length() > 0)? lname_field.getText():null;
            try{
                request = new EditRecordRequest(rest.getRealCustomer(user.getId()),
                        fname, lname, genders_combo.getValue(), birthDate_dp.getValue(),
                        neighbourhoods_combo.getValue(), glutenIntolerant_check.isSelected(), lactoseIntolerant_check.isSelected(), img_source.getImage());
                request.saveRequest();
                Restaurant.getInstance().saveDatabase("Rest.ser");
                Stage window = (Stage) submit.getScene().getWindow();
                SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                Parent customerHome = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../CustomerStage/CustomerHome.fxml")));
                ControllerUtils.changeScreen(window, customerHome);

            }catch (IllegalArgumentException ex){
                SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
                change_lbl.setText("Please fill out all the required fields");
            }catch (NullPointerException | IOException ex){
                    SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Critical_Stop.wav");
                    System.out.println(ex.getMessage());
            }
        }
    }

}