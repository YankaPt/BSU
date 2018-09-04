package com.YankaPt;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;

public class Leaf implements Serializable {
    private String leafColor;
    private int quantity;

    Leaf(String leafColor, int quantity) {
        this.quantity = quantity;
        this.leafColor = leafColor;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String  getLeafColor() {
        return leafColor;
    }

    public void setLeafColor(String leafColor) {
        this.leafColor = leafColor;
    }

    Element createElement(Document doc) {
        Element
                element = doc.createElement("leaf"),
                leafColor = doc.createElement("leafColor"),
                quantity = doc.createElement("quantity");
        leafColor.setTextContent(String.valueOf(getLeafColor()));
        quantity.setTextContent(String.valueOf(getQuantity()));
        element.appendChild(quantity);
        element.appendChild(leafColor);
        return element;
    }
}
