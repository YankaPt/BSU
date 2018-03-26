import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.zip.DataFormatException;

public class Student {
    private int studentRecordBook;
    private String surname;
    private int group;
    private int course;

    Student(String s) throws DataFormatException, NumberFormatException {
        StringTokenizer sb = new StringTokenizer(s, " ");
        if(sb.countTokens() % 2 != 0) throw new DataFormatException();
        studentRecordBook = Integer.parseInt(sb.nextToken());
        surname = sb.nextToken();
        group = Integer.parseInt(sb.nextToken());
        course = Integer.parseInt(sb.nextToken());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student) {
            Student student = (Student) obj;
            if (surname.compareTo(student.surname) == 0 /*&& studentRecordBook == student.studentRecordBook && course == student.course && group == student.group*/) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return surname;
    }

    public String toXMLString(){
        return studentRecordBook + " " + surname + " " + group + " " + course;
    }


    public String getInformation() {
        return "N" + studentRecordBook + '\n' + "Surname: " + surname + " Group: " + group + " Course: " + course;
    }
    static class StudentComparator implements Comparator<Student> {
        @Override
        public int compare(Student a, Student b) {
            if(a.course == b.course)
                if(a.group==b.group)
                    return a.surname.compareTo(b.surname);
            else
                return ((Integer)a.group).compareTo(b.group);
            return ((Integer)a.course).compareTo(b.course);
        }
    }
}
