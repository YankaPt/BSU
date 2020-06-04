package bsu.jp.dao;

import bsu.jp.model.Student;
import org.mariadb.jdbc.MariaDbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private Connection connection;

    public StudentDAO() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/java", "root", "");
    }

    public String testQuery() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from students");
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getString(2)+" "+resultSet.getString(3);
    }

    public List<Student> findAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("select * from students order by form");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Student student = new Student();
            student.setId(resultSet.getLong("id"));
            student.setName(resultSet.getString("name"));
            student.setForm(resultSet.getInt("form"));
            student.setFormLetter(resultSet.getString("form_letter"));
            students.add(student);
        }
        return students;
    }

    public List<List<Student>> findAllWithFormDistribution() throws SQLException {
        List<Student> students = findAll();
        List<List<Student>> result = new ArrayList<>();
        List<Student> tempList = new ArrayList<>();
        Integer form = 0;
        String formLetter ="";
        for (Student student : students) {
            if (!(student.getForm().equals(form)&&student.getFormLetter().equals(formLetter))) {
                form = student.getForm();
                formLetter = student.getFormLetter();
                tempList = new ArrayList<>();
                result.add(tempList);
            }
            tempList.add(student);
        }
        return result;
    }

    public Integer getCountOfStudentsInForm(Integer form, String formLetter) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select count(*) as summ from students where form=? and form_letter=?");
        statement.setInt(1, form);
        statement.setString(2, formLetter);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    public void addStudent(Student student) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into students (name, form, form_letter) values (?,?,?)");
        statement.setString(1,student.getName());
        statement.setInt(2,student.getForm());
        statement.setString(3, student.getFormLetter());
        statement.execute();
    }
}
