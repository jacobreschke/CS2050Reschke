package CS2050Reschke.M03.Iteration01;

public class PriorityDrone extends Drone {

    public PriorityDrone(String type, String manufacturer, int year, double payload) {
        super(type, manufacturer, year, payload);
    }

    // Showing override
    @Override
    public String toString() {
        return "Priority" + super.toString();
    }
}
