package bsu.jp.model;

public class Deal {
    private long id;
    private String type;
    private String name;
    private String dateOfDeal;
    private long sumOfDeal;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfDeal() {
        return dateOfDeal;
    }

    public void setDateOfDeal(String dateOfDeal) {
        this.dateOfDeal = dateOfDeal;
    }

    public long getSumOfDeal() {
        return sumOfDeal;
    }

    public void setSumOfDeal(long sumOfDeal) {
        this.sumOfDeal = sumOfDeal;
    }
}
