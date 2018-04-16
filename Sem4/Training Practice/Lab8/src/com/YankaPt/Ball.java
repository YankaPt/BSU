package com.YankaPt;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class Ball extends Label {
    private double x;
    private double y;
    private ImageView imageView = new ImageView("Spiral.png");
    private double r = imageView.getFitHeight();
    private double angle;
    private double velocity = 15.0;

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
        x+=Math.cos(angle)*velocity;
        y-=Math.sin(angle)*velocity;
    }
    public int hitCheck(Scene scene) {
        angle=angle%(2*Math.PI);
        int quarter = (int)Math.floor(angle/Math.PI*2);
        switch (quarter) {
            case 0: {
                if(x+2*r>=520)
                    angle = Math.PI - angle;
                else if(y-2*r<=0)
                    angle = 2*Math.PI-angle;
                //setOfHitBlocks.add(brickHashCode);
                break;
            }
            case 1: {
                if(x-2*r<=20)
                    angle = Math.PI - angle;
                else if(y-2*r<=0)
                    angle = Math.PI*2 - angle;
                break;
            }
            case 2: {
                if(x-2*r<=20)
                    angle = 2*Math.PI - angle;
                else if(y+2*r>=520)
                    angle = Math.PI*3/2 - angle;
                break;
            }
            case 3: {
                if(x+2*r>=520)
                    angle = -angle+3*Math.PI;
                break;
            }
            default: break;
        }
        return 0;
    }
}
