package View.Controllers;

import View.Tuple;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.CheckComboBox;


import java.net.URL;
import java.util.ArrayList;

public class ControllerUtils {

    private Pane viewl;

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
}