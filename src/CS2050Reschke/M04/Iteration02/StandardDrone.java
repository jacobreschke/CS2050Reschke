package CS2050Reschke.M04.Iteration02;

/**
 * Concrete Standard Drone class that inherits from Drone
 */
public class StandardDrone extends Drone {
    // I dont see a reason with the user stories or design requirements to make change anything in the standard
    // or priority drones beyond overriding toString to display Standard or Priority before the rest
    public StandardDrone(String type, String manufacturer, int year, double payload) {
        super(type, manufacturer, year, payload);
    }

    /**
     * toString override
     * @return string displaying drone type Standard as a prefix to Drone toString
     */
    @Override
    public String toString() {
        return getId() + " | Standard" + super.toString();
    }
}
