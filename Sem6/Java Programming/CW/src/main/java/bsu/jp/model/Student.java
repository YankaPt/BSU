package bsu.jp.model;

public class Student {
    private Long id;
    private String name;
    private Integer form;
    private String formLetter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getForm() {
        return form;
    }

    public void setForm(Integer form) {
        this.form = form;
    }

    public String getFormLetter() {
        return formLetter;
    }

    public void setFormLetter(String formLetter) {
        this.formLetter = formLetter;
    }
}
