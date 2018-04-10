package com.YankaPt;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class Platform extends Label {
    private ImageView imageView = new ImageView("flag_ireland.png");
    private double width = 60;
    private double height = 20;
    double positionX;

    public Platform() {
        super();
        super.setWidth(width);
        super.setHeight(height);
        setGraphic(imageView);
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }
}
