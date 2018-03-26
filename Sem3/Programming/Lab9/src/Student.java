public class Student implements Comparable<Student> {
    private String zachetkaName;
    private String name;
    private int course;
    private int group;

    public Student() {}
    public Student(String zachetkaName, String name, int course, int group) {
        this.zachetkaName = zachetkaName;
        this.name = name;
        this.course = course;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public int getCourse() {
        return course;
    }

    public int getGroup() {
        return group;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(zachetkaName+ " " + name+ ", "+ course+" course, average score is "+group);
        return stringBuilder.toString();
    }
    public void println() {
        System.out.println(this.toString());
    }
    public int compareTo(Student secondStudent) {
        if(this.course < secondStudent.course)
            return -1;
        else if(this.course > secondStudent.course)
            return 1;
        else
            if(this.averageScore < secondStudent.averageScore)
                return -1;
            else if(this.averageScore > secondStudent.averageScore)
                return 1;
            else
                return this.name.compareTo(secondStudent.name);
    }
    public int hashCode() {
        return name.hashCode();
    }
    public boolean equals(Object secondStudent) {
        if(this.toString().equals(secondStudent.toString()))
            return true;
        return false;
    }
}