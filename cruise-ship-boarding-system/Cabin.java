package w1867160_classes.task3;

public class Cabin {
    private String cabinName;
    private int getPassengersOnCabin;
    Passenger[] myPassenger;

    //Cabin class constructor
    public Cabin(String cabinName, int getPassengersOnCabin, Passenger[] myPassenger) {
        this.cabinName = cabinName;
        this.myPassenger = myPassenger;
        this.getPassengersOnCabin = getPassengersOnCabin;

    }

    public void setCabinName(String cabinName) {
        this.cabinName = cabinName;
    }

    public String getCabinName() {
        return this.cabinName;
    }

    public Passenger[] getPassenger() {
        return myPassenger;
    }

    public void setPassenger(Passenger[] myPassenger) {
        this.myPassenger = myPassenger;
    }

    public void setPassengersOnCabin(int getPassengersOnCabin) {
        this.getPassengersOnCabin = getPassengersOnCabin;
    }

    public int getPassengersOnCabin() {
        return getPassengersOnCabin;
    }
}
