package com.YankaPt;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jan on 12.2.18.
 */
public class Course {
    private String name;
    private Set<Student> students;

    public Course(String name, Set<Student> students) { this.name = name; this.students = students;}

    public Set<Postgraduate> getPostgraduates(String nameOfSupervisor) {
        Set<Postgraduate> hashSet= new HashSet<>();
        for(Student i: students)
            if(i instanceof Postgraduate && ((Postgraduate) i).getSupervisor().getName().equals(nameOfSupervisor))
                hashSet.add((Postgraduate) i);
        return hashSet;
    }
}
