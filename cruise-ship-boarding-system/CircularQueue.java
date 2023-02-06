package w1867160_classes.task3;

public class CircularQueue {
    private int front;
    private int end;
    private Passenger[] items;

    public CircularQueue(int size) {
        this.items = new Passenger[size];
        this.front = -1;
        this.end = -1;
    }

    public void enQueue(Passenger myPassenger) {
        //if the queue is full
        if (front == (end + 1) % items.length) {
            System.out.println("Waiting Queue is full. Cannot add more Passengers.");

        } else {
            //if the queue is empty
            if (front == -1) {
                front = 0;
                end = 0;
            }
            //if an element is deleted and another is added to queue
            else if (end == (items.length - 1)) {
                end = 0;
            } else {
                end++;
            }
            System.out.println("Passenger has been Enqueued \r\n");
            //add the element to array
            items[end] = myPassenger;
        }
    }

    public Passenger deQueue() {
        //create a local variable to store the returning value
        Passenger temp;
        //if the queue is empty
        if (front == -1) {
            System.out.println("The Waiting Queue is Empty");
            return null;
        } else {
            //store the current element in the array in the variable
            temp = items[front];
            //if only one element is in the array
            if (front == end) {
                front = -1;
                end = -1;
            }
            //if the current element is at the last index of the array
            else if (front == (items.length - 1)) {
                front = 0;
            } else {
                front++;
            }
            System.out.println("Passenger has been Dequeued from the Waiting List");
            //return the stored variable
            return temp;
        }
    }
}
