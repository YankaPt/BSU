package model;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Lab1TestDrive {
    public static void main(String... args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Lab1", "root", "root");
            MyJDBCTemplate myJDBCTemplate = new MyJDBCTemplate(connection);
            testGettingPerson(myJDBCTemplate);
            String option;
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("What are you want? [find|get|delete|exit]");
                option = scanner.nextLine();
                switch (option) {
                    case "find": {
                        System.out.println("Enter first trait");
                        String trait1 = scanner.nextLine();
                        System.out.println("Enter second trait");
                        String trait2 = scanner.nextLine();
                        Set<String> demands = new HashSet<>();
                        demands.add(trait1);
                        demands.add(trait2);
                        ResultSet resultSet = myJDBCTemplate.getAppropriatePersons(demands);
                        List<Person> resultPeople = PeopleResultSetExtractor.buildPersons(resultSet);
                        List<Person> idealPersons = resultPeople.stream()
                                .filter(person -> person.getTraits().size() == demands.size())
                                .collect(Collectors.toList());
                        System.out.println(idealPersons);
                        continue;
                    }
                    case "get": {
                        System.out.println("Enter person id");
                        String id = scanner.nextLine();
                        System.out.println(myJDBCTemplate.getPerson(Long.parseLong(id)));
                        continue;
                    }
                    case "delete": {
                        System.out.println("Enter person id");
                        String id = scanner.nextLine();
                        myJDBCTemplate.deletePerson(Long.parseLong(id));
                        continue;
                    }
                    case "exit": {
                        connection.close();
                        return;
                    }
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static void testGettingPerson(MyJDBCTemplate myJDBCTemplate) throws SQLException{
        Person person = myJDBCTemplate.getPerson(6L);
        System.out.println(person);
        System.out.println("____________________________________________________________");
    }
}
