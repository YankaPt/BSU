package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeopleResultSetExtractor {
    public static List<Person> buildPersons(ResultSet resultSet) throws SQLException {
        List<Person> people = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong("personId");
            Person person;
            if (people.contains(new Person(id))) {
                person = people.get(people.indexOf(new Person(id)));
            } else {
                person = initializePersonAndGet(resultSet, id, people);
            }
            person.getTraits().add(resultSet.getString("trait"));
        }
        return people;
    }

    private static Person initializePersonAndGet(ResultSet resultSet, Long id, List<Person> people) {
        Person person = new Person();
        person.setId(id);
        try {
            person.setFirstName(resultSet.getString("firstName"));
            person.setLastName(resultSet.getString("lastName"));
            person.setBio(resultSet.getString("bio"));
            people.add(person);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return person;
    }
}
