package CS2050Reschke.M03.Iteration01;

public class StandardDrone extends Drone {
    // I dont see a reason with the user stories or design requirements to make change anything in the standard
    // or priority drones beyond overriding toString to display Standard or Priority before the rest
    public StandardDrone(String type, String manufacturer, int year, double payload) {
        super(type, manufacturer, year, payload);
    }

    @Override
    public String toString() {
        return "Standard" + super.toString();
    }
}
