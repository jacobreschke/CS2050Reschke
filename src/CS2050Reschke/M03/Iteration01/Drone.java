package CS2050Reschke.M03.Iteration01;

/**
 * Drone class holds all information of a drone necessary for the Hangar class
 * Drone is an abstract class
 */
public abstract class Drone {

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
        this.type = type;
        this.manufacturer = manufacturer;
        this.year = year;
        this.payload = payload;
    }


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
