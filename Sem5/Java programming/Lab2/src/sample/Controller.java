package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import sample.model.Person;
import sample.model.RelationsBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Controller {
    @FXML
    private Pane rootPane;
    @FXML
    private TextField personField;
    @FXML
    private TextField traitField;
    @FXML
    private TextField demandField;
    @FXML
    private Button traitButton;
    @FXML
    private Button demandButton;
    @FXML
    private ListView<Person> initialList;
    @FXML
    private ListView<Person> finalList;
    @FXML
    private MenuItem saveMenuItem;

    private FileChooser fileChooser = new FileChooser();
    private List<Person> personList = new ArrayList<>();
    private Person currentPerson;

    @FXML
    private void openFile() {
        File file = fileChooser.showOpenDialog(rootPane.getScene().getWindow());
        if (file != null) {
            try {
                personList.clear();
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                while (true) {
                    personList.add((Person) objectInputStream.readObject());
                }
            } catch (IOException ex) {
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            refreshLists();
            saveMenuItem.setDisable(false);
        }
    }

    @FXML
    private void saveFile() {
        File file = fileChooser.showSaveDialog(rootPane.getScene().getWindow());
        if (file != null) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                for (Person person : personList) {
                    objectOutputStream.writeObject(person);
                }
                objectOutputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void setPerson() {
        String personFieldContent = personField.getText();
        if (!"".equals(personFieldContent)) {
            setInputFormDisable(false);
            saveMenuItem.setDisable(false);
            Optional<Person> optionalPerson = personList.stream()
                    .filter(person -> person.getName().equals(personFieldContent))
                    .findFirst();
            currentPerson = optionalPerson.orElseGet(this::addNewPersonAndGet);
            currentPerson.setName(personFieldContent);
        } else {
            setInputFormDisable(true);
        }
    }

    @FXML
    private void insertTrait() {
        if (!"".equals(traitField.getText())) {
            currentPerson.getQualities().add(traitField.getText());
            refreshLists();
            traitField.clear();
        }
    }

    @FXML
    private void insertDemand() {
        if (!"".equals(demandField.getText())) {
            currentPerson.getDemands().add(demandField.getText());
            refreshLists();
            demandField.clear();
        }
    }

    @FXML
    private void clearAll() {
        personList.clear();
        refreshLists();
    }

    private void setInputFormDisable(boolean isDisabled) {
        traitField.setDisable(isDisabled);
        traitButton.setDisable(isDisabled);
        demandField.setDisable(isDisabled);
        demandButton.setDisable(isDisabled);
    }

    private Person addNewPersonAndGet() {
        Person person = new Person();
        personList.add(person);
        initialList.getItems().add(person);
        return person;
    }

    private void refreshLists() {
        initialList.getItems().setAll(personList);
        finalList.getItems().clear();
        List<Person> clonedList = clonePersonList(personList);
        RelationsBuilder.buildRelationsWithAll(clonedList);
        Collections.sort(clonedList);
        while (!clonedList.isEmpty()) {
            if (clonedList.contains(clonedList.get(0).getBestPair())) {
                finalList.getItems().addAll(clonedList.get(0), clonedList.get(0).getBestPair());
                clonedList.remove(clonedList.get(0).getBestPair());
                clonedList.remove(clonedList.get(0));
            } else {
                clonedList.remove(0);
            }
        }
    }

    private List<Person> clonePersonList(List<Person> personList) {
        List<Person> clonedList = new ArrayList<>();
        for (Person person : personList) {
            clonedList.add(new Person(person));
        }
        return clonedList;
    }
}
