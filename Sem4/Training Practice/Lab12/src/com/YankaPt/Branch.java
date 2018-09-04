package com.YankaPt;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Branch implements Serializable {
    private List<Leaf> list;
    private int id;
    private String color, tree;

    Branch() {
        list = new ArrayList<>();
    }

    Branch(List<Leaf> list, int id, String color, String tree) {
        this.list = list;
        this.id = id;
        this.color = color;
        this.tree = tree;
    }

    List<Leaf> getList() {
        return list;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    String getColor() {
        return color;
    }

    void setColor(String color) {
        this.color = color;
    }

    String getTree() {
        return tree;
    }

    void setTree(String tree) {
        this.tree = tree;
    }

    Element createElement(Document doc) {
        Element root = doc.createElement("branch");
        String
                id = String.valueOf(getId()),
                color = getColor(),
                tree = getTree();
        root.setAttribute("branchid", id);
        root.setAttribute("color", color);
        root.setAttribute("tree", tree);
        return root;
    }

    @Override
    public String toString() {
        return "Branch #"+getId();
    }
}
