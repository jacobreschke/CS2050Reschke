package CS2050Reschke.M04.Iteration02;

/**
 * Drone class holds all information of a drone necessary for the Hangar class
 * Drone is an abstract class
 */
public abstract class Drone {

    // Made droneId static because although ID's belong to the drone, the system of numbering
    // belongs to the class as a whole. I then apply the ID to a string that belongs to the object
    private static int droneId = 1000;
    final private String id;
    final private String type;
    final private String manufacturer;
    final private int year;
    final private double payload;

    /**
     * Drone constructor
     * @param type type of drone (Standard/Priority)
     * @param manufacturer manufacturer of drone
     * @param year year of drone
     * @param payload drones current payload
     */
    Drone(String type, String manufacturer, int year, double payload) {
        this.id = assignId();
        this.type = type;
        this.manufacturer = manufacturer;
        this.year = year;
        this.payload = payload;
    }

    private String assignId() {
        String id = "D" + String.valueOf(droneId);
        droneId++;
        return id;
    }

    public String getId() { return id; }

    /**
     * @return drone type
     */
    public String getType() {
        return type;
    }

    /**
     * @return drone manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @return drone year
     */
    public int getYear() {
        return year;
    }

    /**
     * @return drones current payload
     */
    public double getPayload() {
        return payload;
    }

    /**
     * @return drone information in String format
     */
    @Override
    public String toString() {
        return " Drone - " + manufacturer + " | Year: " + year + " | Payload: " + payload + " kg";
    }
}
