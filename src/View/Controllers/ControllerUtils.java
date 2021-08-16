package View.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class ControllerUtils {

    public static void showAlertMessage(Collection<Group> groups, Group section,String prompt){
        groups.forEach(g->g.setVisible(false));
        Pane grid = (Pane) section.getChildren().get(0);
        ((Label) grid.getChildren().get(1)).setText(prompt);
        section.setVisible(true);
    }
    public static void showAlertMessage(Collection<Group> groups, Group section,Group visible_section,String prompt){
        showAlertMessage(groups, section, prompt);
        visible_section.setVisible(false);
    }

    /**
     * Helper function to organize the code
     * @param s
     * The window we want to affect
     * @param p
     * The new window we want to replace with
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
     * @return
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

    public static void setAlerts(TextField field, Pattern pattern) {
        field.setOnKeyTyped(ke-> {
            if(!Objects.equals(ke.getCharacter(), "\b")) {
                String newText = field.getText() + ke.getCharacter();
                if (pattern.matcher(newText).matches()) {
                    field.getStyleClass().clear();
                    field.getStyleClass().add("text-input");
                    field.getStyleClass().add("text-field");
                }
                else {
                    field.getStyleClass().clear();
                    field.getStyleClass().add("text-input");
                    if(field.getText().length()>0)
                        field.getStyleClass().add("WarningAlert");
                    else
                        field.getStyleClass().add("ErrorAlert");
                }
            }
        });
        field.textProperty().addListener((obs, o, n)->{
            if(n.length()>0 && o.length()>0){
                field.getStyleClass().clear();
                field.getStyleClass().add("text-input");
                field.getStyleClass().add("text-field");
            }
            else {
                /*field.getStyleClass().removeAll("WarningAlert");
                field.getStyleClass().removeAll("text-field");*/
                field.getStyleClass().clear();
                field.getStyleClass().add("text-input");
                field.getStyleClass().add("ErrorAlert");
            }

        });
        field.focusedProperty().addListener((obs, o, n) -> {
            if(field.getText().length()>0) {
                if (!n) {
                    field.getStyleClass().remove("WarningAlert");
                }
            }
            else{
                field.getStyleClass().clear();
                field.getStyleClass().add("text-input");
                field.getStyleClass().add("ErrorAlert");
            }
        });
    }
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
}