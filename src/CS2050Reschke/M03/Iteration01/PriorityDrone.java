package CS2050Reschke.M03.Iteration01;

/**
 * Concrete Priority Drone class that inherits from Drone
 */
public class PriorityDrone extends Drone {

    /**
     * Priority Drone constructor
     * @param type type of drone (Standard/Priority)
     * @param manufacturer manufacturer of drone
     * @param year year of drone
     * @param payload drones current payload
     */
    public PriorityDrone(String type, String manufacturer, int year, double payload) {
        super(type, manufacturer, year, payload);
    }

    /**
     * toString override
      * @return string displaying drone type priority as a prefix to Drone toString
     */
    @Override
    public String toString() {
        return "Priority" + super.toString();
    }
}
