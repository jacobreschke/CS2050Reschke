package CS2050Reschke.M03.Iteration01;

import java.util.ArrayList;
import java.util.Scanner;

public class DroneHangar {

    public static void main(String[] args) {
        Hangar hangar = new Hangar();
        hangar.displayCLI();
    }
}


abstract class Drone {

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


    String getType() {
        return type;
    }

    String getManufacturer() {
        return manufacturer;
    }

    int getYear() {
        return year;
    }

    double getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return type + " - " + manufacturer + " | Year: " + year + " | Payload: " + payload +" kg";
    }
}


class StandardDrone extends Drone {
    // I dont see a reason with the user stories or design requirements to make a StandardDrone or PriorityDrone inherited class
    // but in order to show I understand inheritance/polymorphism and potentially design for iteration02 I included it.
    StandardDrone(String type, String manufacturer, int year, double payload) {
        super(type, manufacturer, year, payload);
    }

}

class PriorityDrone extends Drone {

    PriorityDrone(String type, String manufacturer, int year, double payload) {
        super(type, manufacturer, year, payload);
    }

}


class Hangar {

    ArrayList<Drone> drones = new ArrayList<>();
    Drone[] sortedDrones;
    ArrayList<String> manufacturerTypes;

    Hangar() {

    }


    void displayCLI() {
        boolean exit = false;
        Scanner userInput = new Scanner(System.in);
        // do/while loop here because we know this will run at least once
        do {
            System.out.print("Enter your choice (1-7): ");

            // if the user inputs an integer enter the switch/case
            if (userInput.hasNextInt()) {
                int userDecision = userInput.nextInt();

                switch (userDecision) {
                    case 1:
                        // Load drones from CSV

                        break;
                    case 2:
                        // Display Hangar inventory
                        displayHangarInventory();
                        break;
                    case 3:
                        // Search drones (mfg and type)
                        break;
                    case 4:
                        // View inventory sorted by payload capacity
                        break;
                    case 5:
                        // view inventory sorted by year
                        break;
                    case 6:
                        // Count drones by manufacturer
                        break;
                    case 7:
                        // Exit
                        exit = true;
                        break;
                    default:
                        break;
                }
            } else {
                // If user doesn't enter an int tell them to enter a number
                System.out.println("Invalid input, Please enter a number");
                // Clears input so we dont infinitely loop
                userInput.next();
            }
        } while (!exit);

    }

    private void displayHangarInventory() {

        for (Drone drone : drones) {
            System.out.println(drone);
        }
    }
}


