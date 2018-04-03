package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class Task1 extends Application {
    Label label;
    ImageView successCheck = new ImageView("flag_ireland.png");
    ImageView failCheck = new ImageView("flag_great_britain.png");
    @Override
    public void start(Stage primaryStage) throws Exception{
        FlowPane root = new FlowPane();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        double aa = 789e7;
        ObservableList<String> options = FXCollections.observableArrayList(
                "Natural number",
                "Integer number",
                "Float number",
                "Date",
                "Time",
                "Email"
        );
        final ComboBox comboBox = new ComboBox(options);
        label = new Label("", failCheck);
        TextField textField = new TextField();
        Button checkButton = new Button("Check!");
        checkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String test = textField.getText();
                switch ((String)comboBox.getValue()) {
                    case "Natural number": {
                        if(Pattern.compile("^[^0]\\d*$").matcher(test).matches())
                            label.setGraphic(successCheck);
                        else
                            label.setGraphic(failCheck);
                        break;
                    }
                    case "Integer number": {
                        if(Pattern.compile("^[-+]?\\d+$").matcher(test).matches())
                            label.setGraphic(successCheck);
                        else
                            label.setGraphic(failCheck);
                        break;
                    }
                    case "Float number": {
                        if(Pattern.compile("^[-+]?(\\d*([.,]?\\d+(e[-+]?)?)\\d*|\\d+([.,]?\\d*))$").matcher(test).matches())
                            label.setGraphic(successCheck);
                        else
                            label.setGraphic(failCheck);
                        break;
                    }
                    case "Date": {
                        if(Pattern.compile("^(((0[1-9]|[12]\\d|3[01])[./](0[1357]|10|12))|((0[1-9]|[12]\\d|30)[./](0[2469]|11)))[./]\\d{4}$").matcher(test).matches())
                            label.setGraphic(successCheck);
                        else
                            label.setGraphic(failCheck);
                        break;
                    }
                    case "Time": {
                        if(Pattern.compile("^([01]\\d|2[0-3])([./:][0-5]\\d){1,2}$").matcher(test).matches())
                            label.setGraphic(successCheck);
                        else
                            label.setGraphic(failCheck);
                        break;
                    }
                    case "Email": {
                        if(Pattern.compile("^\\w+\\.?\\w+@\\w+\\.[a-z]{2,3}$").matcher(test).matches())
                            label.setGraphic(successCheck);
                        else
                            label.setGraphic(failCheck);
                        break;
                    }
                    default:break;
                }
            }
        });

        root.getChildren().addAll(textField, checkButton, comboBox, label);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
