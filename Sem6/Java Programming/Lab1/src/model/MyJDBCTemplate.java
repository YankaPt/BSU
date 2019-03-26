package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyJDBCTemplate {
    private static final String SQL_QUERY_FOR_GETTING_APPROPRIATE_PERSONS = "select * from persons join person2trait p2t on persons.id = p2t.personId where trait in (?, ?)";
    private static final String SQL_STEMENT_FOR_SAVING_PERSON = "insert into persons (firstName, lastName, bio) VALUES (?,?,?)";
    private static final String SQL_STATEMENT_FOR_DELETING_PERSON = "delete from persons where id =";
    private Connection connection;

    public MyJDBCTemplate(Connection connection) {
        this.connection = connection;
    }

    public ResultSet getAppropriatePersons(Set<String> demands) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_FOR_GETTING_APPROPRIATE_PERSONS);
        preparedStatement.setString(1, (String) demands.toArray()[0]);
        preparedStatement.setString(2, (String) demands.toArray()[1]);
        return preparedStatement.executeQuery();
    }

    public Person getPerson(Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from persons join person2trait p2t on persons.id = p2t.personId where id="+id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        Person person = new Person();
        person.setId(id);
        person.setFirstName(resultSet.getString("firstName"));
        person.setLastName(resultSet.getString("lastName"));
        person.setBio(resultSet.getString("bio"));
        do {
            person.getTraits().add(resultSet.getString("trait"));
        } while (resultSet.next());
        return person;
    }

    public List<Person> getAllPersons() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from persons");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Person> personList = new ArrayList<>();
        while (resultSet.next()) {
            Person person = new Person(resultSet.getLong("id"));
            person.setFirstName(resultSet.getString("firstName"));
            person.setLastName(resultSet.getString("lastName"));
            person.setBio(resultSet.getString("bio"));
            personList.add(person);
        }
        return personList;
    }

    public void savePerson(Person person) throws SQLException {
        PreparedStatement preparedStatementForPerson = connection.prepareStatement(SQL_STEMENT_FOR_SAVING_PERSON);
        preparedStatementForPerson.setString(1, person.getFirstName());
        preparedStatementForPerson.setString(2, person.getLastName());
        preparedStatementForPerson.setString(3, person.getBio());
        preparedStatementForPerson.execute();
    }

    public void deletePerson(Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_STATEMENT_FOR_DELETING_PERSON + " " + id);
        preparedStatement.execute();
    }
}
