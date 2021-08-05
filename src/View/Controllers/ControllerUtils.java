package View.Controllers;

import View.Tuple;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;


import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class ControllerUtils {

    public static void editListableNode(Tuple[] list){
        for (Tuple t: list) {
            ObservableList<Node> children = t.getParent().getChildrenUnmodifiable();
            Node toChange = children.get(t.getNodeIdx());
            if(toChange instanceof ComboBox) {
                ComboBox cb = (ComboBox) toChange;
                cb.setItems(FXCollections.observableList(t.getList()));
                cb.setPromptText(t.getTextPrompt());
            }
            if(toChange instanceof CheckComboBox) {
                CheckComboBox ccb = (CheckComboBox) toChange;
                ccb.getItems().addAll(t.getList());
                ccb.setTitle(t.getTextPrompt());
                ccb.setShowCheckedCount(true);
            }
        }
    }
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
}