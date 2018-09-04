package com.YankaPt;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class Platform extends Label {
    private ImageView imageView = new ImageView("platform1.png");
    public double width;
    private double height;
    double positionX;

    public Platform() {
        super();
        setGraphic(imageView);
        width = imageView.getImage().getWidth();
        height = imageView.getImage().getHeight();
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

}
