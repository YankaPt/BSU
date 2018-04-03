package sample;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2FX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FlowPane root = new FlowPane();
        primaryStage.setTitle("Task2");
        primaryStage.setScene(new Scene(root, 300, 275));

        TextArea textArea = new TextArea();
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        Button parseButton = new Button("Parse!");
        List<String> dates = new ArrayList<>();
        parseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dates.clear();
                String test = textArea.getText();
                String regex = "((((0[1-9]|[12]\\d|3[01])[./](0[1357]|10|12))|((0[1-9]|[12]\\d|30)[./](0[2469]|11)))[./]\\d{4})";
                Matcher m = Pattern.compile(regex).matcher(test);
                while (m.find()) {
                    //dates.add(test.substring(m.start(1),m.start(1)+10));
                    dates.add(m.group(1));
                }
                StringBuilder builder = new StringBuilder();
                for(String i : dates) {
                    builder.append(i);
                    builder.append("    ");
                }
                if(builder.length()!=0)
                outputArea.setText(builder.toString());
                else
                    outputArea.setText("");
            }
        });

        root.getChildren().addAll(textArea, parseButton, outputArea);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
