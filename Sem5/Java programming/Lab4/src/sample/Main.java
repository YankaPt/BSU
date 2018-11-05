package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Lab4");
        primaryStage.setScene(new Scene(root, 700, 600));
        primaryStage.show();

        KeywordFixer keywordFixer = new KeywordFixer("keywords.txt");
        System.out.print(keywordFixer.fix("test.java"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
