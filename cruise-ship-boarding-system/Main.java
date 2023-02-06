package w1867160_classes.task3;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    // define the variables as static variables for simplicity of the code and easy
    // accessibility
    static int totalCabins = 2;
    static int maxPassengers = 3;
    static int totalWaiting = 5;
    static int cabinCounter, passengerCounter, enqueueCabin, enqueuePassenger = 0;
    static String firstName, lastName, choice, cabinName;
    static int cabinNum, searchCabin, passengersPerCabin, i, j, x;
    static double expense, allExpenses;
    static boolean found;
    static Scanner input = new Scanner(System.in);
    static Passenger[] passengers;
    static String[] unsortedArray;

    public static void main(String[] args) {
        choice = "";

        // the constructor of the CircularQueue is invoked into the main method
        CircularQueue myQueue = new CircularQueue(totalWaiting);

        // create an array which will store the Passenger class as elements for the
        // passengers in the list
        Passenger[] waitingPassengers = new Passenger[totalWaiting];

        // create an array which will store the Cabin class as elements for cabins in
        // the cruise
        Cabin[] cabinList = new Cabin[totalCabins];
        for (i = 0; i < totalCabins; i++) {
            cabinList[i] = new Cabin(cabinName, passengersPerCabin, passengers);
        }

        // create an array which will store the Cabin class as elements for cabins for
        // waiting passengers
        Cabin[] waitingCabins = new Cabin[totalCabins];
        for (i = 0; i < totalCabins; i++) {
            waitingCabins[i] = new Cabin(cabinName, passengersPerCabin, passengers);
        }

        // call the initialize function
        initialise(cabinList);

        System.out.println("-------------------------Welcome to Cruise Ship Boarding System--------------------------");

        while (!choice.equalsIgnoreCase("X")) {
            System.out.println(
                    "-----------------------------------------------------------------------------------------");
            System.out.println("Enter 'A' to add customers to a cabin.");
            System.out.println("Enter 'V' to view all cabinList.");
            System.out.println("Enter 'D' to delete customers from a cabin.");
            System.out.println("Enter 'E' to view empty cabinList.");
            System.out.println("Enter 'F' to find a cabin from a customer's name.");
            System.out.println("Enter 'S' to store program data into a file.");
            System.out.println("Enter 'L' load program data from a file.");
            System.out.println("Enter 'O' to view passengers ordered alphabetically by name.");
            System.out.println("Enter 'T' to view the expenses per passenger and the total expense of all passengers.");
            System.out.println("Enter 'X' to exit.");
            System.out.println(
                    "-----------------------------------------------------------------------------------------");
            choice = input.next();

            // call the functions according to the users choice
            switch (choice.toUpperCase()) {
                case "A":
                    if (cabinCounter >= totalCabins) {
                        // if the cabins are full, passengers will be added to a waiting list
                        waitingQueue(myQueue, waitingCabins, waitingPassengers);
                    } else {
                        // passengers will be added to cabin in the cruise
                        addCustomer(cabinList);
                    }
                    break;
                case "V":
                    viewAllCabins(cabinList);
                    break;
                case "D":
                    // store the return value of deleteCabin()
                    cabinNum = deleteCabin(cabinList);
                    // if returned value is 12, break
                    if (cabinNum != totalCabins) {
                        // proceed if there are passengers in the waiting list and if the cabin exists
                        if (enqueuePassenger != 0) {
                            // get the no.of passengers in the immediate waiting cabin
                            int length = waitingCabins[0].getPassengersOnCabin();
                            // create an array to store the passengers in the waiting cabin
                            Passenger[] dequeuePassengers = new Passenger[length];
                            // add all the passengers in the waiting cabin into the array
                            for (i = 0; i < length; i++) {
                                dequeuePassengers[i] = myQueue.deQueue();
                                // subtract the passenger from the waiting list
                                passengerCounter++;
                                enqueuePassenger--;
                            }
                            // set the information of the passengers in the waiting cabin into the deleted
                            // cabin
                            cabinList[cabinNum] = new Cabin(waitingCabins[0].getCabinName(),
                                    waitingCabins[0].getPassengersOnCabin(), dequeuePassengers);
                            enqueueCabin--;
                        } else {
                            // if waiting list is empty, subtract the cabin counter
                            cabinCounter--;
                        }
                    }
                    break;
                case "E":
                    viewsEmptyCabins(cabinList);
                    break;
                case "F":
                    findCabin(cabinList);
                    break;
                case "S":
                    storeData(cabinList);
                    break;
                case "L":
                    loadData();
                    break;
                case "O":
                    System.out.println("Sorted Cabin Names \r\n");
                    // create an array which would store only the occupied cabin names
                    unsortedArray = new String[cabinCounter];
                    j = 0;
                    for (i = 0; i < totalCabins; i++) {
                        if (!cabinList[i].getCabinName().equals("empty")) {
                            unsortedArray[j] = cabinList[i].getCabinName().toLowerCase();
                            j++;
                        }
                    }
                    // pass the new array into the sort function
                    sortList(unsortedArray);

                    // create an array which would store all the passenger names
                    System.out.println();
                    System.out.println("Sorted Passenger Names \r\n");
                    unsortedArray = new String[passengerCounter];
                    x = 0;
                    for (i = 0; i < totalCabins; i++) {
                        if (!cabinList[i].getCabinName().equals("empty")) {
                            passengers = cabinList[i].getPassenger();
                            for (j = 0; j < cabinList[i].getPassengersOnCabin(); j++) {
                                unsortedArray[x] = passengers[j].getFullName().toLowerCase();
                                x++;
                            }
                        }
                    }
                    sortList(unsortedArray);
                    break;
                case "T":
                    totalExpense(cabinList);
                    break;
                case "X":
                    System.out.println("Thank you. Have a nice day!");
                    break;
                default:
                    System.out.println("Invalid choice! \r\n");
            }
        }
    }

    private static void initialise(Cabin[] cabinList) {
        for (i = 0; i < totalCabins; i++) {
            cabinList[i].setCabinName("empty");
        }
        System.out.println("Initialise");
    }

    private static void addCustomer(Cabin[] cabinList) {
        cabinNum = 0;
        passengersPerCabin = 0;

        while (cabinNum != totalCabins) {
            System.out.println("Enter a Cabin Number from 0-" + (totalCabins - 1) + " or " + totalCabins + " to exit");
            if (input.hasNextInt()) {
                cabinNum = input.nextInt();
                if ((cabinNum >= 0 && cabinNum < totalCabins) && (cabinList[cabinNum].getCabinName().equals("empty"))) {
                    // proceed only if the cabin number is valid and if the cabin is empty
                    System.out.println("Enter name for Cabin No " + cabinNum + ":");
                    cabinName = input.next();
                    // cabin name should be alphabetical and cannot be empty
                    if (cabinName.matches("^[a-zA-Z]*$") && !cabinName.equals("empty")) {
                        // get the no.of passengers in a cabin
                        passengersPerCabin = passengerNumber();

                        Passenger[] passengerList = new Passenger[maxPassengers];

                        for (i = 0; i < passengersPerCabin; i++) {
                            System.out
                                    .println("Information of Passenger " + (i + 1) + " in Cabin No " + cabinNum + " :");
                            // store the personal details of each passenger in the passengerList array
                            personalInfo(passengerList, i);
                            passengerCounter++;
                        }
                        // set the input provided by the user into a single element of the array.
                        cabinList[cabinNum] = new Cabin(cabinName, passengersPerCabin, passengerList);
                        cabinCounter++;
                    } else {
                        System.out.println("Must enter a valid name!");
                    }
                } else if (cabinNum != 12) {
                    System.out.println("This Cabin is Occupied or Invalid Cabin Number \r\n");
                }
            } else {
                System.out.println("Enter an Integer! \r\n");
                input.next();
            }
        }
    }

    private static void viewAllCabins(Cabin[] cabinList) {
        // display cabins according to the vacancy
        for (i = 0; i < totalCabins; i++) {
            if (cabinList[i].getCabinName().equals("empty")) {
                System.out.println("Room No " + i + " is Empty.");
            } else {
                System.out.println("Room No " + i + " occupied by " + cabinList[i].getCabinName());
            }
        }
    }

    private static int deleteCabin(Cabin[] cabinList) {
        System.out.println("Enter cabin to delete");
        if (input.hasNextInt()) {
            searchCabin = input.nextInt();

            // set the cabin name to "empty" and subtract the cabin counter only if the
            // cabin is occupied and has a valid number
            if ((searchCabin >= 0 && searchCabin < totalCabins)
                    && (!cabinList[searchCabin].getCabinName().equals("empty"))) {
                for (i = 0; i < cabinList[searchCabin].getPassengersOnCabin(); i++) {
                    passengerCounter--;
                }
                cabinList[searchCabin].setCabinName("empty");
                System.out.println("Cabin No " + searchCabin + " was removed");
                // return the value of the deleted cabin number
                return searchCabin;
            } else {
                System.out.println("This Cabin is already empty or Cabin Number is out of Range.");
            }
        } else {
            System.out.println("Enter a valid Room Number!");
            input.next();
        }
        // return this value if a cabin was not deleted
        return totalCabins;
    }

    private static void viewsEmptyCabins(Cabin[] cabinList) {
        for (i = 0; i < totalCabins; i++) {
            // display the cabins only if they are empty
            if (cabinList[i].getCabinName().equals("empty")) {
                System.out.println("Room No." + i + " is Empty");
            }
        }
    }

    private static void findCabin(Cabin[] cabinList) {
        found = false;
        System.out.println("Enter Cabin Name: ");
        String searchName = input.next();

        for (i = 0; i < totalCabins; i++) {
            if (searchName.equalsIgnoreCase(cabinList[i].getCabinName())) {
                System.out.println("Cabin No " + i);
                // set the value to true if cabin is found
                found = true;
            }
        }
        if (!found) {
            // if found is false display this message
            System.out.println("Cabin not found!");
        }
    }

    private static void storeData(Cabin[] cabinList) {
        try {
            try (FileWriter myWriter = new FileWriter("CruiseShip.txt")) {
                for (i = 0; i < totalCabins; i++) {
                    if (!cabinList[i].getCabinName().equals("empty")) {
                        // write the data of only the cabins that are occupied into the file
                        passengers = cabinList[i].getPassenger();
                        myWriter.write("Cabin Number : " + i + "\r\n");
                        myWriter.write("Cabin Name : " + cabinList[i].getCabinName() + "\r\n");
                        myWriter.write("\r\n");
                        for (j = 0; j < cabinList[i].getPassengersOnCabin(); j++) {
                            // write the data of each passenger of the occupied cabins
                            myWriter.write("Passenger Number : " + (j + 1) + "\r\n");
                            myWriter.write("First Name : " + passengers[j].getFirstName() + "\r\n");
                            myWriter.write("Last Name : " + passengers[j].getLastName() + "\r\n");
                            myWriter.write("Expense : " + passengers[j].getExpenses() + "\r\n");
                            myWriter.write("\r\n");
                        }
                    }
                }
            }
            System.out.println("Data written into file.");

        } catch (IOException error) {
            // provide feedback if there is an error when writing into the file
            System.out.println("Error");
            System.out.println("Exception is " + error);
        }
    }

    private static void loadData() {
        try {
            File myFile = new File("CruiseShip.txt");
            Scanner readFile = new Scanner(myFile);
            // read data from file until all the data has loaded
            while (true) {
                if (readFile.hasNext()) {
                    String fileLine = readFile.nextLine();
                    System.out.println(fileLine);
                } else {
                    break;
                }
            }
            readFile.close();

        } catch (IOException error) {
            // provide feedback if there is an error when loading data from the file
            System.out.println("Error");
            System.out.println("Exception is " + error);
        }
    }

    /*
     * this method sorts the given Strings of the array by
     * comparing characters of the same position of 2 adjoining Strings.
     * the parameters are the array to be sorted and
     * position of the first character of the Strings to be compared
     */
    private static void sort(String[] checkList, int x) {
        for (int i = 0; i < checkList.length; i++) {
            for (int j = 0; j < (checkList.length - 1); j++) {
                // if the adjoining Strings are the same , move to the next comparison
                if (!checkList[j].equals(checkList[j + 1]) && (x < checkList[j].length())
                        && (x < checkList[j + 1].length())) {
                    // proceed if the Strings are different and index of the comparing character is
                    // less than the length of the 2 strings
                    if (checkList[j].charAt(x) > checkList[j + 1].charAt(x)) {
                        // swap the Strings if the characters are in the wrong order
                        String temp = checkList[j];
                        checkList[j] = checkList[j + 1];
                        checkList[j + 1] = temp;
                    } else if (checkList[j].charAt(x) == checkList[j + 1].charAt(x)) {
                        // create a new array if the characters in the same position are the same and
                        // add the 2 Strings to the array
                        String[] word = new String[2];
                        word[0] = checkList[j];
                        word[1] = checkList[j + 1];
                        // pass the new array into the function and call it as a recursive function
                        sort(word, (x + 1));
                        checkList[j] = word[0];
                        checkList[j + 1] = word[1];
                    }
                }
            }
        }
    }

    private static void sortList(String[] unsortedArray) {
        sort(unsortedArray, 0);
        for (String name : unsortedArray) {
            System.out.println(name);
        }
    }

    private static void totalExpense(Cabin[] cabinList) {
        allExpenses = 0;

        for (i = 0; i < totalCabins; i++) {
            if (!cabinList[i].getCabinName().equals("empty")) {
                // display the expenses of each passenger in the relevant cabins
                passengers = cabinList[i].getPassenger();
                System.out.println("Cabin Number : " + i);
                for (j = 0; j < cabinList[i].getPassengersOnCabin(); j++) {
                    // accumulate the expenses of each passenger
                    allExpenses += passengers[j].getExpenses();
                    System.out.println("Expense of Passenger Number " + (j + 1) + ": " + passengers[j].getExpenses());
                }
                System.out.println();
            }
        }
        // display the accumulated expense of all the passengers
        System.out.println("Total Expense of All Passengers: " + allExpenses);
    }

    private static void personalInfo(Passenger[] passengerList, int i) {
        expense = 0;
        System.out.println("Enter first name:");
        firstName = input.next();
        System.out.println("Enter surname:");
        lastName = input.next();
        while (true) {
            System.out.println("Enter expenses for passengers:");
            if (input.hasNextDouble()) {
                expense = input.nextDouble();
                System.out.println();
                // break the loop only if all the information is provided correctly
                break;
            } else {
                System.out.println("Invalid Amount! \r\n");
                input.next();
            }
        }
        // store the information of the passenger the array passed as a parameter
        passengerList[i] = new Passenger(firstName, lastName, expense);
    }

    public static int passengerNumber() {
        while (true) {
            System.out.println("Enter number of passengers per cabin. (Max " + maxPassengers + ")");
            if (input.hasNextInt()) {
                passengersPerCabin = input.nextInt();
                if (passengersPerCabin > 0 && passengersPerCabin <= maxPassengers) {
                    // break the loop only if the no.of passengers are provided correctly
                    break;
                } else {
                    System.out.println("Enter a integer between 1-3! \r\n");
                }
            } else {
                System.out.println("Enter an integer! \r\n");
                // set the non-integer value as a junk value
                input.next();
            }
        }
        // return the no.of passengers
        return passengersPerCabin;
    }

    private static void waitingQueue(CircularQueue myQueue, Cabin[] waitingCabins, Passenger[] waitingPassengers) {
        found = false;
        passengersPerCabin = 0;

        if (enqueueCabin < totalCabins) {
            System.out.println("All cabins are full! Passengers will be added to a waiting list.");
            System.out.println("Maximum of " + totalWaiting + " Passengers can be added to the waiting list.");
            System.out.println("Enter cabin name");
            cabinName = input.next();

            // get the no.of passengers in a cabin
            passengersPerCabin = passengerNumber();

            for (i = 0; i < passengersPerCabin; i++) {
                // waiting queue is not full
                if (enqueuePassenger < waitingPassengers.length) {
                    System.out.println("Information of passenger " + (i + 1) + ":");
                    // get the information about the passenger from user
                    personalInfo(waitingPassengers, enqueuePassenger);

                    // add the passenger into the waiting queue
                    myQueue.enQueue(waitingPassengers[enqueuePassenger]);
                    enqueuePassenger++;

                    // store the information about the waiting cabin an element in the array
                    waitingCabins[enqueueCabin].setCabinName(cabinName);
                    waitingCabins[enqueueCabin].setPassengersOnCabin(i + 1);
                    found = true;
                }
            }
            if (found) {
                enqueueCabin++;
            }
            if (enqueuePassenger >= waitingPassengers.length) {
                System.out.println("Waiting Queue is full. Cannot add more Passengers.");
            }
        } else {
            System.out.println("Waiting Cabins are full. Cannot add more Cabins.");
        }
    }
}
