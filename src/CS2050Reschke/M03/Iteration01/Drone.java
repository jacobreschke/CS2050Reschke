package CS2050Reschke.M03.Iteration01;

public abstract class Drone {

    final private String type;
    final private String manufacturer;
    final private int year;
    final private double payload;

    Drone(String type, String manufacturer, int year, double payload) {
        this.type = type;
        this.manufacturer = manufacturer;
        this.year = year;
        this.payload = payload;
    }


    public String getType() {
        return type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getYear() {
        return year;
    }

    public double getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return " Drone - " + manufacturer + " | Year: " + year + " | Payload: " + payload + " kg";
    }
}
