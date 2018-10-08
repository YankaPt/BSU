package sample.model;

public class PersonRespect {
    private Person person;
    private int respect;

    public PersonRespect(Person person) {
        this.person = person;
        respect = 0;
    }

    public PersonRespect(Person person, int respect) {
        this.person = person;
        this.respect = respect;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getRespect() {
        return respect;
    }

    public void setRespect(int respect) {
        this.respect = respect;
    }
}
