import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.MyJDBCTemplate;
import model.PeopleResultSetExtractor;
import model.Person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Controller {
    @FXML
    private TextField firstTrait;
    @FXML
    private TextField secondTrait;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField bioField;
    @FXML
    private TableView<Person> personTable;
    @FXML
    private ListView<Person> idealList;
    private MyJDBCTemplate myJDBCTemplate;
    private Set<String> demands = new HashSet<>();

    public Controller() {
    }

    @FXML
    public void initialize() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Lab1", "root", "root");
            myJDBCTemplate = new MyJDBCTemplate(connection);
            final ObservableList<Person> data = FXCollections.observableArrayList(myJDBCTemplate.getAllPersons());
            TableColumn<Person, String> idColumn = new TableColumn<>("Id");
            TableColumn<Person, String> firstNameColumn = new TableColumn<>("First Name");
            TableColumn<Person, String> lastNameColumn = new TableColumn<>("Last Name");
            TableColumn<Person, String> bioColumn = new TableColumn<>("Bio");
            personTable.getColumns().addAll(idColumn, firstNameColumn, lastNameColumn, bioColumn);
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            bioColumn.setCellValueFactory(new PropertyValueFactory<>("bio"));
            personTable.setItems(data);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void findIdealPersons() {
        if (firstTrait.getText() != null && secondTrait.getText() != null) {
            demands.clear();
            demands.add(firstTrait.getText());
            demands.add(secondTrait.getText());
        } else {
            return;
        }
        try {
            List<Person> appropriatePersons = PeopleResultSetExtractor.buildPersons(myJDBCTemplate.getAppropriatePersons(demands));
            List<Person> idealPersons = appropriatePersons.stream()
                    .filter(person -> person.getTraits().size() == demands.size())
                    .collect(Collectors.toList());
            idealList.getItems().setAll(idealPersons);
            idealList.refresh();
        } catch (SQLException e) {
        }
    }

    @FXML
    public void findPerson() {
        if (idTextField.getText() != null && !idTextField.getText().isEmpty()) {
            try {
                idealList.getItems().clear();
                idealList.getItems().addAll(myJDBCTemplate.getPerson(Long.parseLong(idTextField.getText())));
                idealList.refresh();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    public void deletePerson() {
            try {
                myJDBCTemplate.deletePerson(personTable.getSelectionModel().getSelectedItem().getId());
                personTable.setItems(FXCollections.observableArrayList(myJDBCTemplate.getAllPersons()));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
    }

    @FXML
    public void addPerson() {
        if (nameField.getText()!=null && surnameField.getText() != null && bioField.getText() != null) {
            Person person = new Person();
            person.setFirstName(nameField.getText());
            person.setLastName(surnameField.getText());
            person.setBio(bioField.getText());
            try {
                myJDBCTemplate.savePerson(person);
                personTable.setItems(FXCollections.observableArrayList(myJDBCTemplate.getAllPersons()));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
