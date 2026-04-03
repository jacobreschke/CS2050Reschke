package CS2050Reschke.M03.Iteration01;

import java.io.File;
import java.io.FileNotFoundException;
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
        return type + " - " + manufacturer + " | Year: " + year + " | Payload: " + payload + " kg";
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
            System.out.println("1. Load Drones from CSV");
            System.out.println("2. Display Hangar Inventory");
            System.out.println("3. Search Drones (Manufacturer & Type)");
            System.out.println("4. View Inventory Sorted by Payload (Manual Sort)");
            System.out.println("5. View Inventory Sorted by Year (Manual Sort)");
            System.out.println("6. Count Drones by Manufacturer");
            System.out.println("7. Exit");
            System.out.print("Enter your choice (1-7): ");

            // if the user inputs an integer enter the switch/case
            if (userInput.hasNextInt()) {
                int userDecision = userInput.nextInt();

                switch (userDecision) {
                    case 1:
                        // clear the drones array before parsing so we dont allow duplicating the list
                        drones.clear();
                        // TODO: Change this to input when done
                        // Method to get input and return
                        String fileName = "drones_test2.csv";
                        loadFromCsv(fileName);
                        break;
                    case 2:
                        displayHangarInventory();
                        break;
                    case 3:
                        // Search drones (mfg and type)
                        // TODO: get input for mfg and type
                        String manufacturer = "DJ1I";
                        String type = "S";
                        displayDronesByManufacturerAndType(type, manufacturer);
                        break;
                    case 4:
                        // View inventory sorted by payload capacity
                        displayDronesByPayload();
                        break;
                    case 5:
                        // view inventory sorted by year
                        displayDronesByYear();
                        break;
                    case 6:
                        // Count drones by manufacturer
                        // TODO: Get user input for count
                        String key = "DJI";
                        displayManufacturerCount(key);
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


    private void displayDronesByPayload() {
    }

    private void displayDronesByYear() {
    }

    private void displayDronesByManufacturerAndType(String type, String manufacturer) {

        for (Drone drone : drones) {
            if (drone.getType().equals(type) && drone.getManufacturer().equals(manufacturer)) {
                System.out.println(drone);
            }
        }

    }

    private void displayManufacturerCount(String key) {
        int count = 0;
        for (Drone drone : drones) {
            if (drone.getManufacturer().equals(key)) {
                count++;
            }
        }
        System.out.println(key + " appears: " + count + " times.");
    }


    void loadFromCsv(String filename) {

        int totalLinesRead = 0;
        int blankLinesSkipped = 0;
        int dronesAdded = 0;
        int invalidLinesSkipped = 0;
        int addFailures = 0;

        try (Scanner fileScan = new Scanner(new File(filename))) {

            while (fileScan.hasNextLine()) {
                String line = fileScan.nextLine();
                totalLinesRead++;
                boolean shouldProcess = true;

                if (line == null || line.trim().isEmpty()) {
                    shouldProcess = false;
                    blankLinesSkipped++;
                }

                if (shouldProcess) {
                    Drone parsedDrone = parseDroneLine(line, totalLinesRead);
                    if (parsedDrone == null) {
                        invalidLinesSkipped++;
                    } else {
                        boolean added = addDrone(parsedDrone);
                        if (added) {
                            dronesAdded++;
                        } else {
                            addFailures++;
                            System.out.println("Line " + totalLinesRead + ": could not add Drone");
                        }
                    }
                }

            }
        } catch (FileNotFoundException exception) {
            System.out.println("Error: File not found");
            return;
        }
        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.println("File Load Summary: " + filename);
        System.out.println("------------------------------------------------------------");
        System.out.println("Total lines read:            " + totalLinesRead);
        System.out.println("Blank lines skipped:         " + blankLinesSkipped);
        System.out.println("Invalid lines skipped:       " + invalidLinesSkipped);
        System.out.println("Drones successfully added:   " + dronesAdded);
        System.out.println("Add failures (library full): " + addFailures);
        System.out.println("------------------------------------------------------------");
        System.out.println();
    }

    private Drone parseDroneLine(String line, int lineNumber) {
        String[] parts = line.split(",");

        // if the csv has more or less than 4 parts of data reject it
        if (parts.length != 4) {
            System.out.println("Line " + lineNumber + ": wrong number of fields (need 4) " + line);
            return null;
        }

        // Grab the data types from the csv file line
        String droneTypeText = parts[0].trim();
        String droneManufacturerText = parts[1].trim();
        String droneYearText = parts[2].trim();
        String dronePayloadText = parts[3].trim();

        if (droneTypeText.isEmpty()) {
            System.out.println("Line " + lineNumber + ": missing type");
            return null;
        }
        if (droneManufacturerText.isEmpty()) {
            System.out.println("Line " + lineNumber + ": missing manufacturer");
            return null;
        }
        if (droneYearText.isEmpty()) {
            System.out.println("Line " + lineNumber + ": missing year");
            return null;
        }
        if (dronePayloadText.isEmpty()) {
            System.out.println("Line " + lineNumber + ": missing payload");
            return null;
        }

        // Convert the year and payload text to int and double
        int year;
        try {
            year = Integer.parseInt(droneYearText);
        } catch (NumberFormatException exception) {
            System.out.println("Line " + lineNumber + ": year is not a number \"" + droneYearText + "\"");
            return null;
        }

        // validate that the year is valid
        if (year < 1900 || year > 2100) {
            System.out.println("Line " + lineNumber + ": year is out of valid range " + year);
            return null;
        }

        double payload;
        try {
            payload = Double.parseDouble(dronePayloadText);
        } catch (NumberFormatException exception) {
            System.out.println("Line " + lineNumber + ": Payload is not a number \"" + dronePayloadText + "\"");
            return null;
        }

        // validate payload is a + number. Allowing 0 because drone could have no payload
        if (payload < 0) {
            System.out.println("Line " + lineNumber + ": Payload is not valid \"" + dronePayloadText + "\"");
            return null;
        }

        if (droneTypeText.equalsIgnoreCase("S")) {
            return new StandardDrone(droneTypeText, droneManufacturerText, year, payload);
        } else if (droneTypeText.equalsIgnoreCase("P")) {
            return new PriorityDrone(droneTypeText, droneManufacturerText, year, payload);
        } else {
            return null;
        }

    }

    boolean addDrone(Drone parsedDrone) {
        // Already verified that the data is valid so no more verification here
        drones.add(parsedDrone);
        return true;
    }

    private void displayHangarInventory() {

        for (Drone drone : drones) {
            System.out.println(drone);
        }
    }

}


