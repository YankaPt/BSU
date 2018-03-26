public class Student{
    private String surname;
    private int course;
    private int group;
    private int numberOfSRB;

    public int getNumberOfSRB() {
        return numberOfSRB;
    }

    public Student(String surname, int course, int group, int numberOfSRB) {
        this.surname = surname;
        this.course = course;
        this.group = group;
        this.numberOfSRB = numberOfSRB;
    }



    public String getSurname() {
        return surname;
    }

    public int getCourse() {
        return course;
    }

    public int getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return String.format("Course: %d, Group: %d, Surname: %s, Number of SRB: %d",
                course, group, surname, numberOfSRB);
    }
}
