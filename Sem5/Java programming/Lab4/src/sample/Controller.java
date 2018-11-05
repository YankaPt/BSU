package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.*;


public class Controller {
    private FileChooser fileChooser;
    private KeywordFixer fixer;
    private String text;
    private File currentFile;
    @FXML private Pane rootPane;
    @FXML private Menu fileMenu;
    @FXML private TextArea mainTextArea;
    @FXML private Button fixButton;

    @FXML private void initialize() {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(0, new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.getExtensionFilters().add(1, new FileChooser.ExtensionFilter("Java Class Files", "*.java"));
    }

    @FXML private void fix() {
        try {
            text = fixer.fix(currentFile.getPath());
        } catch (IOException ex) {ex.printStackTrace();}
        mainTextArea.setText(text);
        try (FileWriter fileWriter = new FileWriter("result.java")) {
            fileWriter.write(text);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        fileMenu.getItems().get(1).setDisable(false);
    }

    @FXML private void openKeywordsFile() {
        File file = fileChooser.showOpenDialog(rootPane.getScene().getWindow());
        if (file != null) {
            fixer = new KeywordFixer(file.getPath());
            fileMenu.getItems().get(0).setDisable(false);
        }
    }

    @FXML private void openFile() {
        currentFile = fileChooser.showOpenDialog(rootPane.getScene().getWindow());
        if (currentFile != null) {
            StringBuilder data = new StringBuilder();
            try {
                FileReader fileReader = new FileReader(currentFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = bufferedReader.readLine();;
                while (line != null) {
                    data.append(line + "\n");
                    line = bufferedReader.readLine();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            text = data.toString();
            mainTextArea.setText(text);
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
    @FXML private void close() {
        Platform.exit();
    }
}