package CS2050Reschke.M03.Iteration01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Hangar {

    private ArrayList<Drone> drones = new ArrayList<>();
    private Drone[] sortedDrones;

    public Hangar() {

    }

    public ArrayList<Drone> getDrones() {
        return drones;
    }

    public String getUserInput() {
        Scanner input = new Scanner(System.in);
        String userInput = input.nextLine();
        return userInput.trim();
    }

    public void displayCLI() {
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
                        // clear the drones array before parsing so we don't allow duplicating the list
                        drones.clear();

                        System.out.print("Enter CSV file name: ");
                        String fileName = getUserInput();
                        loadFromCsv(fileName);
                        break;
                    case 2:
                        displayHangarInventory();
                        break;
                    case 3:
                        // Search drones (mfg and type)
                        System.out.print("Enter manufacturer: ");
                        String manufacturer = getUserInput();
                        System.out.print("Enter drone type (Standard/Priority): ");
                        String type = getUserInput();

                        displayDronesByTypeAndManufacturer(type, manufacturer);
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
                        System.out.print("Enter manufacturer to count: ");
                        String key = getUserInput();
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
                // Clears input so we don't infinitely loop
                userInput.next();
            }
        } while (!exit);

    }

    private void displayDronesByPayload() {
        if (hasDrones()) {
            System.out.println("No drones in the hangar.");
        } else {
            sortedDrones = sortDronesByPayload();
            System.out.println("Sorted Inventory By Payload:");
            for (Drone drone : sortedDrones) {
                System.out.println(drone);
            }
        }
    }

    public Drone[] sortDronesByPayload() {
        // Chose insertion sort for both sorting algorithms because its faster(slightly) and it's
        // possible that we get partially sorted drones and selection sort will mess that up

        // Create array of size drones array list
        Drone[] array = new Drone[drones.size()];
        // populate the new array with same elements of drones arraylist
        for (int i = 0; i < drones.size(); i++) {
            array[i] = drones.get(i);
        }

        // insertion sort
        for (int i = 1; i < array.length; i++) {
            Drone current = array[i];
            double currentPayload = current.getPayload();
            int j = i - 1;

            while (j >= 0 && array[j].getPayload() > currentPayload) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = current;
        }

        return array;
    }

    private void displayDronesByYear() {
        if (hasDrones()) {
            sortedDrones = sortDronesByYear();
            System.out.println("Sorted Inventory By Year:");
            for (Drone drone : sortedDrones) {
                System.out.println(drone);
            }
        } else {
            System.out.println("No drones in the hangar");
        }
    }

    public Drone[] sortDronesByYear() {
        // See sortDronesByPayload for design choice and explanation of method
        Drone[] array = new Drone[drones.size()];
        for (int i = 0; i < drones.size(); i++) {
            array[i] = drones.get(i);
        }
        for (int i = 1; i < array.length; i++) {

            Drone current = array[i];
            int currentYear = current.getYear();
            int j = i - 1;

            while (j >= 0 && array[j].getYear() > currentYear) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = current;
        }
        return array;
    }

    public ArrayList<Drone> displayDronesByTypeAndManufacturer(String type, String manufacturer) {
        // I originally had this as void and just printing out the drones
        // but changed to return an arraylist so that I can unit test
        if (type.equalsIgnoreCase("standard")) {
            type = "S";
        } else if (type.equalsIgnoreCase("priority")) {
            type = "P";
        }

        ArrayList<Drone> returnDrones = new ArrayList<>();
        boolean droneFound = false;
        for (Drone drone : drones) {
            // Ignore case sensitivity so user can input lowercase and uppercase
            if (drone.getType().equalsIgnoreCase(type) && drone.getManufacturer().equalsIgnoreCase(manufacturer)) {
                returnDrones.add(drone);
                System.out.println(drone);
                droneFound = true;
            }
        }
        if (!droneFound) {
            System.out.println("No drones available.");
        }
        return returnDrones;
    }

    public void displayManufacturerCount(String key) {
        int count = 0;
        for (Drone drone : drones) {
            if (drone.getManufacturer().equals(key)) {
                count++;
            }
        }
        System.out.println(key + " appears: " + count + " times.");
    }

    public boolean loadFromCsv(String filename) {
        // I made this a boolean so that I could test good and bad file names

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
                        }
                    }
                }

            }
        } catch (FileNotFoundException exception) {
            System.out.println("Error: File not found");
            return false;
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
        return true;
    }

    public Drone parseDroneLine(String line, int lineNumber) {
        String[] parts = line.split(",");

        // if the csv has more or less than 4 parts of data reject it
        if (parts.length != 4) {
            System.out.println("Line " + lineNumber + ": wrong number of fields (need 4) " + line);
            return null;
        }

        // Grab the data types from the csv file line
        // Convert drone type to uppercase
        String droneTypeText = parts[0].trim().toUpperCase();
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

        // Convert the year to int
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

        // convert payload to double
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

        // Strips a "Standard" input to "S" or "Priority" to "P"
        droneTypeText = droneTypeText.substring(0, 1);
        if (droneTypeText.equalsIgnoreCase("S")) {
            return new StandardDrone(droneTypeText, droneManufacturerText, year, payload);
        } else if (droneTypeText.equalsIgnoreCase("P")) {
            return new PriorityDrone(droneTypeText, droneManufacturerText, year, payload);
        } else {
            return null;
        }

    }

    public boolean addDrone(Drone parsedDrone) {
        // loop through all current drones
        // Check if parsed drone is a duplicate
        // return true/false for testing
        if (findDuplicateDrone(parsedDrone)) {
            return false;
        }
        drones.add(parsedDrone);
        return true;
    }

    public boolean findDuplicateDrone(Drone parsedDrone) {
        // linear search through drones, if duplicate is found return true, else return false
        for (Drone drone : drones) {
            if (parsedDrone.getType().equals(drone.getType()) &&
                    parsedDrone.getManufacturer().equals(drone.getManufacturer()) &&
                    parsedDrone.getYear() == drone.getYear() &&
                    parsedDrone.getPayload() == drone.getPayload()) {
                System.out.println("Error: Duplicate drone not added: " + parsedDrone);
                return true;
            }
        }

        return false;
    }

    public void displayHangarInventory() {
        if (hasDrones()) {
            for (Drone drone : drones) {
                System.out.println(drone);
            }
        }
        System.out.println("No drones in the hangar");
    }

    public boolean hasDrones() {
        // made purely for if checks in other methods and for testing
        return !drones.isEmpty();
    }

}