package com.YankaPt;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Game extends Application implements Observable {
    public static void main(String[] args) {
        launch(args);
    }

    private List<Observer> observerList = new ArrayList<>();
    private Set<Brick> setOfHitBlocks;
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
        Scene scene = new Scene(root, 1400, 900);
        Label background = new Label();
        background.setGraphic(new ImageView("background.jpg"));
        primaryStage.setScene(scene);
        setOfHitBlocks = new HashSet<>();
        platform = new Platform();
        ball = new Ball(600, 820, Math.PI*0.8);
        platform.setPositionX(300);
        platform.setLayoutX(platform.getPositionX());
        platform.setLayoutY(840-platform.getHeight());
        ball.setLayoutX(ball.getX());
        ball.setLayoutY(ball.getY());
        root.getChildren().addAll(background,platform, ball);
        double row=20;
        double column=40;
        int l = 25;
        for(int i=0; i<l; i++) {
            Brick b = new Brick(this,1);
            b.setLayoutX(row+=b.width);
            b.setLayoutY(column);
            root.getChildren().add(b);
        }
        row =20;
        column = 80;
        for(int i=0; i<l; i++) {
            Brick b = new Brick(this,2);
            b.setLayoutX(row+=b.width);
            b.setLayoutY(column);
            root.getChildren().add(b);
        }
        row =20;
        column = 120;
        for(int i=0; i<l; i++) {
            Brick b = new Brick(this,1);
            b.setLayoutX(row+=b.width);
            b.setLayoutY(column);
            root.getChildren().add(b);
        }
        primaryStage.show();
        scene.setOnKeyPressed(event -> {
            if(event.getCode().getName().equals("Left"))
                platform.positionX-=20;
            if(event.getCode().getName().equals("Right"))
                platform.positionX+=20;
            platform.setLayoutX(platform.getPositionX());
        });
        new JFXPanel();
        String bip = "/home/jan/BSU/Sem4/Training Practice/Lab8/src/music.mp3";
        Media hit = new Media(new File(bip).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
        timeline = new Timeline (
                new KeyFrame (
                        Duration.millis(5),
                        e -> {
                            mediaPlayer.play();
                            if(observerList.isEmpty()) {
                                timeline.stop();
                                primaryStage.setTitle("Victory!");
                                return;
                            }
                            setOfHitBlocks.clear();
                            ball.move();
                            ball.setLayoutX(ball.getX());
                            ball.setLayoutY(ball.getY());
                            primaryStage.setTitle("Balls: "+lives);
                            setOfHitBlocks.add(ball.hitCheck(scene, platform));
                            if(!ball.isActive()) {
                                timeline.pause();
                                lives--;
                                primaryStage.setTitle("Balls: "+lives);
                                root.getChildren().remove(ball);
                                if(lives==0) {
                                    timeline.stop();
                                    primaryStage.setTitle("Defeat");
                                    return;
                                }
                                ball = new Ball(600, 820, Math.PI*0.8);
                                ball.setLayoutX(ball.getX());
                                ball.setLayoutY(ball.getY());
                                root.getChildren().add(ball);
                                scene.setOnMouseClicked(event -> {
                                    timeline.play();
                                });
                            }
                            notifyObservers();
                            for(Brick b: setOfHitBlocks) {
                                if(!observerList.contains(b))
                                    root.getChildren().remove(b);
                            }
                        }
                )
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Integer.MAX_VALUE);
        timeline.play();
    }
}
