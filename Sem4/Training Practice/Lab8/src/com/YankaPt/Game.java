package com.YankaPt;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game extends Application implements Observable {
    public static void main(String[] args) {
        launch(args);
    }

    private List<Observer> observerList = new ArrayList<>();
    private Set<Integer> setOfHitBlocks;
    private Timeline timeline;
    private Ball ball;
    private Platform platform;
    private int lives = 3;

    @Override
    public void addObserver(Observer o) {
        observerList.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observerList.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o: observerList)
            o.update(setOfHitBlocks);
    }
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        primaryStage.setTitle("Lab8");
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        setOfHitBlocks = new HashSet<>();
        platform = new Platform();
        ball = new Ball(300, 300, 0.5);
        platform.setPositionX(300);
        platform.setLayoutX(platform.getPositionX());
        platform.setLayoutY(550);
        ball.setLayoutX(ball.getX());
        ball.setLayoutY(ball.getY());
        List<Brick> bricks = new ArrayList<>();
        bricks.add(new Brick(this,1));
        double row=0;
        double column=0;
        for(Brick b: bricks)
        {
            b.setLayoutX(row+=40);
            b.setLayoutY(column+=20);
            root.getChildren().add(b);
            observerList.add(b);

        }
        bricks = null;
        root.getChildren().addAll(platform, ball);
        primaryStage.show();
        scene.setOnKeyPressed(event -> {
            if(event.getCode().getName().equals("Left"))
                platform.positionX-=20;
            if(event.getCode().getName().equals("Right"))
                platform.positionX+=20;
            platform.setLayoutX(platform.getPositionX());
        });
        int i=0;
        timeline = new Timeline (
                new KeyFrame (
                        Duration.millis(20),
                        e -> {
                            setOfHitBlocks.clear();
                            ball.move();
                            primaryStage.setTitle(""+i);
                            ball.setLayoutX(ball.getX());
                            ball.setLayoutY(ball.getY());
                            ball.hitCheck(scene);
                            notifyObservers();
                        }
                )
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(10000);
        timeline.play();
        //root.getChildren().addAll(new ImageOfKey(this), new LogOfKeys(this));
        //root.setLeft(new ImageOfKey(this));
        //root.setRight(new LogOfKeys(this));

    }
    private void hitCheck() {
        int quarter = (int)Math.round(ball.getAngle()/Math.PI*4);
        switch (quarter) {
            case 0: {
                //check
                //setOfHitBlocks.add(brickHashCode);
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                if(false) {
                    lives--;
                    ball = new Ball(300, 400, 2);
                }
                break;
            }
            default: break;
        }
    }
}
