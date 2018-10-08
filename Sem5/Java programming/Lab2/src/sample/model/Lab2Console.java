package sample.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lab2Console {
    private static int counter = 0;
    private static final int NUMBER_OF_TOTAL_QUALITIES = 10;
    private static final int NUMBER_OF_ENOUGH_QUALITIES = 3;
    private static final int NUMBER_OF_QUALITIES_FOR_EACH = 4;
    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            people.add(generatePerson());
        }
        for (Person p : people)
            System.out.println(p);
        System.out.println("________________________________________________________\n");
        RelationsBuilder.buildRelationsWithAll(people);
        Collections.sort(people);
        for (Person p : people)
            System.out.println(p);
    }
    public static Person generatePerson() {
        Person person = new Person();
        person.setName("Person" + counter++);
        for (int i=0; i < NUMBER_OF_QUALITIES_FOR_EACH; i++) {
            person.getQualities().add("qual"+(Math.round(Math.random()*NUMBER_OF_TOTAL_QUALITIES)));
        }
        for (int i=0; i < NUMBER_OF_QUALITIES_FOR_EACH; i++) {
            person.getDemands().add("qual"+(Math.round(Math.random()*NUMBER_OF_TOTAL_QUALITIES)));
        }
        return person;
    }
}

