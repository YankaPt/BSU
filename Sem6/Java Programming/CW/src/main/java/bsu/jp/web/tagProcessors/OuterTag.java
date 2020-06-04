package bsu.jp.web.tagProcessors;

import bsu.jp.model.CV;
import bsu.jp.model.Student;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OuterTag extends BodyTagSupport {
    private List<Student> students = new ArrayList<>();
    private String name;
    private String color;
    private String marker;

    @Override
    public int doStartTag() throws JspException {
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doAfterBody() throws JspException {
        bodyContent.clearBody();
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            JspWriter writer = pageContext.getOut();
            writer.write("<p><font color='" + color + "'>" + name + "</font></p>");
            for (Student student : students) {
                writer.write("<p><font color='" +

                        color + "'>"+Character.toString((char) 9)+translateMarkerToASCII(marker)+

                        "  "+student.getName() + "</font></p>");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        students.clear();
        bodyContent.clearBody();
        return EVAL_PAGE;
    }

    private String translateMarkerToASCII(String marker) {
        switch (marker) {
            case "square": {
                return Character.toString((char) 930);
            }
            case "circle": {
                return Character.toString((char) 176);
            }
            default:
                return marker;
        }
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }
}
