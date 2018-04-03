import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Lab7 extends Application implements Observable {

    public static void main(String[] args) {
        launch(args);
    }

    private List<Observer> observerList = new ArrayList<>();
    private String currentKey;

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
            o.update(currentKey);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        primaryStage.setTitle("Lab7");
        Scene scene = new Scene(root, 300, 275);
        primaryStage.setScene(scene);
        scene.setOnKeyTyped(event -> {
                currentKey = event.getCharacter();
                notifyObservers();
        });
        //root.getChildren().addAll(new ImageOfKey(this), new LogOfKeys(this));
        root.setLeft(new ImageOfKey(this));
        root.setRight(new LogOfKeys(this));
        primaryStage.show();
    }
}

class ImageOfKey extends Label implements Observer {
    private String currentKey;
    private ImageView image;

    ImageOfKey(Observable o) {
        o.addObserver(this);
        setFont(Font.font(150));
    }

    @Override
    public void update(String codeOfKey) {
        currentKey = codeOfKey;
        this.setText(codeOfKey);
        //TODO: change setText to setGraphics
    }
}

class LogOfKeys extends ListView implements Observer {
    private ObservableList<String> logs = FXCollections.observableArrayList();

    LogOfKeys(Observable o) {
        o.addObserver(this);
        this.setItems(logs);
        this.setMaxWidth(40);
    }

    @Override
    public void update(String codeOfKey) {
        logs.add(codeOfKey);
    }
}
