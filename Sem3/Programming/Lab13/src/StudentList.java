import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class StudentList extends DefaultListModel<Student> {
    @Override
    public void addElement(Student element) {
        super.addElement(element);
    }

    private Student.StudentComparator studentComparator = new Student.StudentComparator();

    public void sort(){
        ArrayList<Student> list = Collections.list(this.elements());
        list.sort(studentComparator);
        this.removeAllElements();
        for(Student s : list){
            this.addElement(s);
        }
    }
    public StudentList clone() {
        try {
            return (StudentList) super.clone();
        }catch (Exception ex) {return null;}
    }
}
