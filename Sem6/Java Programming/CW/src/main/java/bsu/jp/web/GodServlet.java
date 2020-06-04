package bsu.jp.web;

import bsu.jp.dao.DealDAO;
import bsu.jp.dao.StudentDAO;
import bsu.jp.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GodServlet extends HttpServlet {
    private StudentDAO studentDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            studentDAO = new StudentDAO();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Integer> integers = Stream.of(1,2,3,4,5).collect(Collectors.toList());
        request.setAttribute("integers", integers);
        try {
            request.setAttribute("forms",studentDAO.findAllWithFormDistribution());
            request.setAttribute("count", studentDAO.getCountOfStudentsInForm(11,"A"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        request.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        Integer form = Integer.parseInt(request.getParameter("form"));
        String formLetter = request.getParameter("formLetter");
        Student newStudent = new Student();
        newStudent.setName(name);
        newStudent.setForm(form);
        newStudent.setFormLetter(formLetter);
        try {
            studentDAO.addStudent(newStudent);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        response.sendRedirect("/CW/main");
    }

}
