package com.YankaPt;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class Ball extends Label {
    private double x;
    private double y;
    private double r = 20.0;
    private ImageView imageView = new ImageView("Spiral.png");
    private double angle;
    private double velocity = 5.0;

    public Ball(double x, double y, double angle) {
        super();
        this.x = x;
        this.y = y;
        this.angle = angle;
        setGraphic(imageView);
    };

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public double getAngle() {
        return angle;
    }

    public void move() {
        /*x+=Math.cos(angle)*velocity;
        y+=Math.sin(angle)*velocity;*/
        x+=100;
        y+=100;
    }
}
