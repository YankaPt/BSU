package bsu.jp.model;

public class CV {
    private String image;
    private String firstName;
    private String lastName;
    private Integer age;
    private String firstNameColor;
    private String lastNameColor;
    private String ageColor;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getFirstNameColor() {
        return firstNameColor;
    }

    public void setFirstNameColor(String firstNameColor) {
        this.firstNameColor = firstNameColor;
    }

    public String getLastNameColor() {
        return lastNameColor;
    }

    public void setLastNameColor(String lastNameColor) {
        this.lastNameColor = lastNameColor;
    }

    public String getAgeColor() {
        return ageColor;
    }

    public void setAgeColor(String ageColor) {
        this.ageColor = ageColor;
    }
}
