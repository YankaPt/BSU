package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import sample.model.Person;
import sample.model.RelationsBuilder;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Controller {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mysql";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";
    private static final String SQL_INSERT_PERSON = "insert into persons (name, traits, demands) values (?, ?, ?)";
    private static final String SQL_SELECT_PERSONS = "select * from persons";
    private static final String SQL_CLEAR_DB = "delete from persons";
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

    private List<Person> personList = new ArrayList<>();
    private Person currentPerson;

    @FXML
    private void openFile() {
        personList.clear();
        personList.addAll(getPersonsFromDB());
        refreshLists();
        saveMenuItem.setDisable(false);
    }

    @FXML
    private void saveFile() {
        try(Connection connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD)) {
            PreparedStatement clearStatement = connection.prepareStatement(SQL_CLEAR_DB);
            clearStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Person person : personList) {
            savePerson(person);
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

    private void savePerson(Person person) {
        try(Connection connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_PERSON);
            preparedStatement.clearParameters();
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, traitsToString(person.getQualities()));
            preparedStatement.setString(3, traitsToString(person.getDemands()));
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private String traitsToString(Set<String> traits) {
        StringBuilder builder = new StringBuilder();
        for (String trait: traits) {
            builder.append(trait);
            builder.append(" ");
        }
        return builder.toString();
    }

    private List<Person> getPersonsFromDB() {
        List<Person> people = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(DB_URL, LOGIN, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PERSONS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                people.add(mapPerson(resultSet));
            }

        } catch (
                SQLException ex) {
            ex.printStackTrace();
        }
        return people;
    }

    private Person mapPerson(ResultSet resultSet) throws SQLException {
        Person person = new Person();
        person.setName(resultSet.getString("name"));
        person.setQualities(parseTraits(resultSet.getString("traits")));
        person.setDemands(parseTraits(resultSet.getString("demands")));
        return person;
    }

    private Set<String> parseTraits(String traitString) {
        return Set.of(traitString.split(" "));
    }
}
