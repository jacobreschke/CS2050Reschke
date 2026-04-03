package CS2050Reschke.M03.Iteration01;

public class StandardDrone extends Drone {
    // I dont see a reason with the user stories or design requirements to make a StandardDrone or PriorityDrone inherited class
    // but in order to show I understand inheritance/polymorphism and potentially design for iteration02 I included it.
    public StandardDrone(String type, String manufacturer, int year, double payload) {
        super(type, manufacturer, year, payload);
    }

}
