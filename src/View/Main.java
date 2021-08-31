package View;

import Model.Restaurant;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Restaurant rest = Restaurant.getInstance();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Controllers/fxmls/LoginPage.fxml")));
        primaryStage.setTitle("JavaEat");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/Icon.jpg"));
        primaryStage.show();
    }

    public static void showPopupWindow(String labelMessage, String buttonMessage)
    {
        Stage popup_window=new Stage();
        popup_window.initModality(Modality.APPLICATION_MODAL);
        popup_window.setTitle("Attention please");
        popup_window.getIcons().add(new Image("Icon.jpg"));
        Label label1= new Label(labelMessage);
        Button button1= new Button(buttonMessage);
        button1.setOnAction(e -> popup_window.close());
        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1, button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popup_window.setScene(scene1);
        popup_window.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}