package View.Controllers;

import Model.Customer;
import Utils.ImageManager;
import Utils.SFXManager;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This controller serves as the hub for the customer stage, and includes the different main customer menu buttons that are located on the left navigation bar
 * @author Daniel Sharon
 */
public class CustomerHomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private StackPane content_pane;

    @FXML
    private URL location;

    @FXML
    private Label username_label;

    @FXML
    private Button homepage_button;

    @FXML
    private Button new_order_button;

    @FXML
    private Button relevant_dish_menu_button;

    @FXML
    private Button cooks_expertise_button;

    @FXML
    private Button popular_ingredients_button;

    @FXML
    private Button view_menu_button;

    @FXML
    private Button settings_button;

    @FXML
    private Button signout_button;

    @FXML
    private ImageView user_img;

    private Customer customer;

    @FXML
    void initialize() {
        try {
            assert username_label != null : "fx:id=\"username_label\" was not injected: check your FXML file 'CustomerHome.fxml'.";
            assert homepage_button != null : "fx:id=\"homepage_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
            assert new_order_button != null : "fx:id=\"new_order_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
            assert relevant_dish_menu_button != null : "fx:id=\"relevant_dish_menu_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
            assert cooks_expertise_button != null : "fx:id=\"cooks_expertise_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
            assert popular_ingredients_button != null : "fx:id=\"popular_ingredients_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
            assert view_menu_button != null : "fx:id=\"view_menu_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
            assert settings_button != null : "fx:id=\"settings_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";
            assert signout_button != null : "fx:id=\"signout_button\" was not injected: check your FXML file 'CustomerHome.fxml'.";

            Node loadedPage;
            loadedPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/CustomerCartAndHistory.fxml")));
            content_pane.getChildren().add(loadedPage);
            customer = LoginPageController.getCustomer();
            if (customer.getProfileImgName().equals("Default")) {
                BufferedImage bi = ImageManager.getInstance().getImage("Alert");
                System.out.println(bi);
                user_img.setImage(SwingFXUtils.toFXImage(bi, null));
            } else {
                user_img.setImage(SwingFXUtils.toFXImage(customer.getProfileImg(false), null));
            }

//        username_label.setText("Welcome, \n"+customer.getFirstName()+" "+customer.getLastName()); This makes the label span on two lines
            username_label.setText("Welcome, " + customer.getFirstName() + " " + customer.getLastName());

        } catch (NullPointerException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method makes the different menu buttons function and send the user to the corresponding pages
     */
    @FXML
    public void handleButtonClick(ActionEvent event) {
        try {
            Node loadedPage;
            switch (((Node) event.getSource()).getId()) {

                case "new_order_button":
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    loadedPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/AddCustomerOrder.fxml")));
                    content_pane.getChildren().clear();
                    content_pane.getChildren().add(loadedPage);
                    break;
                case "homepage_button":
                    loadedPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/CustomerCartAndHistory.fxml")));
                    content_pane.getChildren().clear();
                    content_pane.getChildren().add(loadedPage);
                    break;
                case "view_menu_button":
                    //SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    loadedPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/CustomerMenu.fxml")));
                    content_pane.getChildren().clear();
                    content_pane.getChildren().add(loadedPage);
                    break;
                case "popular_ingredients_button":
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    loadedPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/getPopularComponent.fxml")));
                    content_pane.getChildren().clear();
                    content_pane.getChildren().add(loadedPage);
                    break;
                case "cooks_expertise_button":
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    loadedPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/getCooksByExpertise.fxml")));
                    content_pane.getChildren().clear();
                    content_pane.getChildren().add(loadedPage);
                    break;
                case "relevant_dish_menu_button":
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    loadedPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/getCustomerRelevantDishList.fxml")));
                    content_pane.getChildren().clear();
                    content_pane.getChildren().add(loadedPage);
                    break;
                case "settings_button":
                    SFXManager.getInstance().playSound("src/View/sfx/click_sound2.wav");
                    loadedPage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/editCustomerDetails.fxml")));
                    content_pane.getChildren().clear();
                    content_pane.getChildren().add(loadedPage);
                    break;
            }
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method sends the user back to the login page when he presses the sign-out button
     */
    public void signout() {
        try {
            Stage s = (Stage) signout_button.getScene().getWindow();
            SFXManager.getInstance().playSound("src/View/sfx/Windows_XP_Logoff_Sound.wav");
            Parent p = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/LoginPage.fxml")));
            s.hide();
            s.setScene(new Scene(p));
            s.centerOnScreen();
            s.show();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}