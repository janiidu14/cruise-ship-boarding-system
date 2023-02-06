package w1867160_classes.task3;

public class Passenger {
    private String firstName;
    private String lastName;
    private double expenses;

    //Passenger class constructor
    public Passenger(String firstName, String lastName, double expenses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.expenses = expenses;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    public double getExpenses() {
        return this.expenses;
    }

    public String getFullName(){
        return (this.firstName + " " + this.lastName);
    }
}