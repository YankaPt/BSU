package com.YankaPt;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Lab11 extends Application {

    private static final int COUNT_LIMIT = 100000;

    @Override
    public void init() {
        for (int i = 0; i < COUNT_LIMIT; i++) {
            double progress = i / COUNT_LIMIT;
            notifyPreloader(new Preloader.ProgressNotification(progress));
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setMinWidth(scene.getWidth());
        primaryStage.setMinHeight(scene.getHeight());
        primaryStage.setTitle("Lab 11");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.setProperty("javafx.preloader", "com.YankaPt.MyPreloader");
        launch(args);
    }
}