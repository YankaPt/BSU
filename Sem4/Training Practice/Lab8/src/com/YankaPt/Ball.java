package com.YankaPt;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


public class Ball extends Label {
    private double x;
    private double y;
    private ImageView imageView = new ImageView("Ball.png");
    private double r = imageView.getImage().getHeight()/2;
    private double angle; //TODO private
    private double velocity = 4;
    private final double ELASTICITY = 0.98;
    private boolean isActive = true;
    private double delta = 5;

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
    public Brick hitCheck(Scene scene, Platform platform) {
        //angle=angle%(2*Math.PI);
        int quarter = (int)Math.floor(angle/Math.PI*2);
        switch (quarter) {
            case 0: {
                if(x+2*r>=scene.getWidth())
                    angle = (Math.PI - angle)/ELASTICITY;
                else if(y<=0)
                    angle = (2*Math.PI-angle);
                break;
            }
            case 1: {
                if(x<=0)
                    angle = (Math.PI - angle)*ELASTICITY;
                else if(y<=0)
                    angle = (Math.PI*2 - angle);
                break;
            }
            //TODO Elasticity of platform
            case 2: {
                if(x<=0)
                    angle = (3*Math.PI - angle)*ELASTICITY;
                else if(y+2*r>=scene.getHeight()-platform.getHeight()) {
                    if((platform.getLayoutX()<=x+r) && (platform.getLayoutX() + platform.width>=x+r))
                        angle = (Math.PI*2 - angle);//*((x-platform.getLayoutX())/platform.width);
                    else
                        isActive = false;
                }
                break;
            }
            case 3: {
                if(x+2*r>=scene.getWidth())
                    angle = (-angle+3*Math.PI)/ELASTICITY;
                else if(y+2*r>=scene.getHeight()-platform.getHeight()) {
                    if((platform.getLayoutX()<=x+r) && (platform.getLayoutX() + platform.width>=x+r))
                        angle = (Math.PI*2 - angle);//*((x-platform.getLayoutX())/platform.width);
                    else
                        isActive = false;
                }
                break;
            }
            default: break;
        }
        return brickHitCheck(scene);
    }
    private Brick brickHitCheck(Scene scene) {
        double bX,bY;
        int quarter = (int)Math.floor(angle/Math.PI*2);
        for(Node node: scene.getRoot().getChildrenUnmodifiable()) {
            if(node instanceof Brick && !((Brick)node).isDestroy()) {
                bX = node.getLayoutX();
                bY = node.getLayoutY();
                //if((Math.abs(bX-x-r)<=delta || (Math.abs(bX+((Brick)node).width-x+r)<=delta)) && (Math.abs(bY-y-r)<=delta || (Math.abs(bY+((Brick)node).height -y+r)<=delta))) {
                if((bX<=x+r && (bX+((Brick)node).width>=x+r) && (Math.abs(bY-y-2*r)<=delta || (Math.abs(bY+((Brick)node).height - y)<=delta)))) {
                        switch (quarter) {
                            case 0: {
                                angle = (2*Math.PI-angle);
                                break;
                            }
                            case 1: {
                                angle = (Math.PI*2 - angle);
                                break;
                            }
                            case 2: {
                                angle = (Math.PI*2 - angle);
                                break;
                            }
                            case 3: {
                                angle = (Math.PI*2 - angle);
                                break;
                            }
                            default: break;
                        }
                    return (Brick) node;
                }
                if((bY<=y+r && (bY+((Brick)node).height>=y+r) && (Math.abs(bX-x-2*r)<=delta || (Math.abs(bX+((Brick)node).width - x)<=delta)))) {
                    switch (quarter) {
                        case 0: {
                            angle = (Math.PI - angle);
                            break;
                        }
                        case 1: {
                            angle = (Math.PI - angle);
                            break;
                        }
                        case 2: {
                            angle = (3*Math.PI - angle);
                            break;
                        }
                        case 3: {
                            angle = (-angle+3*Math.PI);
                            break;
                        }
                        default: break;
                    }
                    return (Brick) node;
                }
            }
        }
        return null;
    }
    public boolean isActive() {return isActive;}
}
