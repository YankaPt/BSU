package sample.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Person implements Comparable<Person>, Serializable{
    private String name;
    private Set<String> qualities;
    private Set<String> demands;
    private Set<PersonRespect> personRespects = new HashSet<>();
    private int maxRespect;
    private Person bestPair;

    public Person() {
        qualities = new HashSet<>();
        demands = new HashSet<>();
        maxRespect = 0;
    }

    public Person(String name, Set<String> qualities, Set<String> demands) {
        this.name = name;
        this.qualities = qualities;
        this.demands = demands;
        maxRespect = 0;
    }

    public Person(Person person) {
        this.setName(person.name);
        this.setQualities(person.getQualities());
        this.setDemands(person.getDemands());
        this.personRespects = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getQualities() {
        return qualities;
    }

    public void setQualities(Set<String> qualities) {
        this.qualities = qualities;
    }

    public Set<String> getDemands() {
        return demands;
    }

    public void setDemands(Set<String> demands) {
        this.demands = demands;
    }

    public Set<PersonRespect> getPersonRespects() {
        return personRespects;
    }

    public void setPersonRespects(Set<PersonRespect> personRespects) {
        this.personRespects = personRespects;
    }

    public int getMaxRespect() {
        return maxRespect;
    }

    public void setMaxRespect(int maxRespect) {
        this.maxRespect = maxRespect;
    }

    public Person getBestPair() {
        return bestPair;
    }

    public void setBestPair(Person bestPair) {
        this.bestPair = bestPair;
    }

    @Override
    public String toString() {
        return name + " wants " + demands + " and has " + qualities;
    }

    @Override
    public int compareTo(Person o) {
        return ((Integer)maxRespect).compareTo(o.maxRespect);
    }
}
