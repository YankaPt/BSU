package com.YankaPt;

/**
 * Created by jan on 12.2.18.
 */
public class Person {
    private String name;

    public Person(String name) {this.name = name;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
class Academic extends Person {
    Academic(String name) {super(name);}
}
