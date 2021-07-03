package View;

import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.List;

public class Tuple<T> {
    private Parent parent;
    private int node_idx;
    private List<T> list;
    private String textPrompt;

    public Tuple(Parent parent, int node_idx, List<T> list) {
        this.parent = parent;
        this.node_idx = node_idx;
        this.list = list;
    }
    public Tuple(Parent parent, int node_idx, List<T> list, String textPrompt) {
        this(parent,node_idx,list);
        this.textPrompt = textPrompt;
    }

    public Parent getParent() {
        return this.parent;
    }
    public Integer getNodeIdx() {
        return this.node_idx;
    }

    public List<T> getList(){
        return this.list;
    }

    public String getTextPrompt(){
        return this.textPrompt;
    }
}
