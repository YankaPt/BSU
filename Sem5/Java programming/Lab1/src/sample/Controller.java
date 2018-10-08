package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Controller {
    private FileChooser fileChooser;
    private HyphenationChecker hyphenationChecker;
    private String text;
    private Set<WordPair> set;
    @FXML private Pane rootPane;
    @FXML private Menu fileMenu;
    @FXML private TextArea mainTextArea;
    @FXML private ListView<WordPair> replacementsList;
    @FXML private Button fixButton;

    @FXML private void initialize() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(0, new FileChooser.ExtensionFilter("Text Files", "*.txt"));
    }

    @FXML private void fix() {
        for (WordPair pair : set) {
            text = text.replaceAll(pair.initialWord, pair.replaceWord);
        }
        mainTextArea.setText(text);
        fileMenu.getItems().get(1).setDisable(false);
        replacementsList.getItems().clear();
        set.clear();
    }

    @FXML private void openHyphenFile() {
        File file = fileChooser.showOpenDialog(rootPane.getScene().getWindow());
        if (file != null) {
            try {
                hyphenationChecker = new HyphenationChecker(file.getPath());
                fileMenu.getItems().get(0).setDisable(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML private void openFile() {
        File file = fileChooser.showOpenDialog(rootPane.getScene().getWindow());
        if (file != null) {
            readAndAnalyze(file);
            fixButton.setDisable(false);
        }
    }

    @FXML private void saveFile() {
        File file = fileChooser.showSaveDialog(rootPane.getScene().getWindow());
        if (file != null) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(text);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void readAndAnalyze(File file) {
        StringBuilder data = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();;
            while (line != null) {
                data.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        text = data.toString();
        mainTextArea.setText(text);
        String[] words = text.split(" ");
        set = new HashSet<>();
        for (String word : words) {
            WordPair pair = hyphenationChecker.checkWordHyphen(word);
            if (!pair.initialWord.equals(pair.replaceWord)) {
                set.add(pair);
            }
        }
        replacementsList.getItems().addAll(set);
    }
    @FXML private void close() {
        System.exit(0);
    }
}