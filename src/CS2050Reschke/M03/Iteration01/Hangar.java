package CS2050Reschke.M03.Iteration01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hangar class manages a collection of drone objects
 * Allows loading, searching, sorting, and displaying drones
 * by a command line interface
 */
public class Hangar {

    private ArrayList<Drone> drones = new ArrayList<>();
    private Drone[] sortedDrones;

    Scanner input = new Scanner(System.in);


    /**
     * Default constructor
     */
    public Hangar() {

    }

    /**
     * Returns the ArrayList of drones currently stored in the hangar
     *
     * @return ArrayList of Drone objects
     */
    public ArrayList<Drone> getDrones() {
        return drones;
    }

    /**
     * Reads input from the user and returns in String type with whitespace trimmed
     *
     * @return trimmed user input: String
     */
    public String getUserInput() {
        String userInput = input.nextLine();
        return userInput.trim();
    }

    /**
     * Handles the command line interface options of the program
     */
    public void displayCLI() {
        boolean exit = false;
        // do/while loop here because we know this will run at least once
        do {
            displayCLIOptions();

            // getUserInput() returns a string so we take as string and convert to int here that
            // way we can reuse it for parts of the program that needs a string DRY/SRP
            String userInput = getUserInput();
            int userDecision;

            try {
                userDecision = Integer.parseInt(userInput);
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input. Please enter a number from 1 to 7.");
                continue;
            }

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
                        input.close();
                        break;
                    default:
                        // If user doesn't enter an int tell them to enter a number
                        System.out.println("Invalid input, Please enter a number");
                        // Clears input so we don't infinitely loop
                        input.next();

                        break;
            }
        } while (!exit);

    }

    /**
     * Displays the user options of the program
     */
    private void displayCLIOptions() {
        System.out.println("1. Load Drones from CSV");
        System.out.println("2. Display Hangar Inventory");
        System.out.println("3. Search Drones (Manufacturer & Type)");
        System.out.println("4. View Inventory Sorted by Payload (Manual Sort)");
        System.out.println("5. View Inventory Sorted by Year (Manual Sort)");
        System.out.println("6. Count Drones by Manufacturer");
        System.out.println("7. Exit");
        System.out.print("Enter your choice (1-7): ");
    }

    /**
     * Displays all drones in the hangar sorted by payload
     */
    private void displayDronesByPayload() {
        if (!hasDrones()) {
            System.out.println("No drones in the hangar.");
        } else {
            sortedDrones = sortDronesByPayload();
            System.out.println("Sorted Inventory By Payload:");
            for (Drone drone : sortedDrones) {
                System.out.println(drone);
            }
        }
    }

    /**
     * Populates an array the size of drones ArrayList and populates that array with
     * drones in the same order as the drones ArrayList then sorts by payload
     *
     * @return Array of drones sorted by payload
     */
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

    /**
     * Displays drones sorted by year
     */
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

    /**
     * Populates an array the size of drones ArrayList and populates that array with
     * drones in the same order as the drones ArrayList then sorts by year
     *
     * @return Array of drones sorted by year
     */
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

    /**
     * Filters and displays drones by type and manufacturer. Allows drone type by
     * lowercase or full word and converts to "S" or "P"
     *
     * @param type the drones type ("S" or "P")
     * @param manufacturer the drones manufacturer
     * @return List of drones matching type and manufacturer
     */
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

    /**
     * Counts and displays how many drones match the given manufacturer
     * @param key the manufacturer name to search
     */
    public void displayManufacturerCount(String key) {
        int count = 0;
        for (Drone drone : drones) {
            if (drone.getManufacturer().equals(key)) {
                count++;
            }
        }
        System.out.println(key + " appears: " + count + " times.");
    }

    /**
     * Loads drone data from a CSV file
     * validates each line and tracks the parsing to display results
     *
     * @param filename filename of the drones in csv format
     * @return true if file loaded, false if no file found
     */
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
        // I wanted to make this a helper function but I didnt want to feed 6 variables
        // to a method. Design wise it seems kinda unnecessary.
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

    /**
     * Parses a line of a CSV file and creates a Standard or Priority Drone object based on type
     *
     * @param line the actual line data of the CSV being parsed
     * @param lineNumber the line number being parsed
     * @return Drone object if valid line, null if invalid
     */
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

    /**
     * Adds a drone to the hangar if it isnt a duplicate
     * @param parsedDrone drone parsed by parseDroneLine()
     * @return true if added successfully, false if a duplicate
     */
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

    /**
     * Searches the drone ArrayList and returns true if theres a duplicate
     *
     * @param parsedDrone drone you want to search for duplicates
     * @return true if a duplicate exists, false if no duplicate was found.
     */
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

    /**
     * Checks displays all drones currently in the hangar
     * if no drones exists prints a message
     */
    public void displayHangarInventory() {
        if (hasDrones()) {
            for (Drone drone : drones) {
                System.out.println(drone);
            }
        } else {
            System.out.println("No drones in the hangar");
        }
    }

    /**
     * Checks if there are drones in the hangar
     * @return true if there are drones, false if there are no drones
     */
    public boolean hasDrones() {
        // made purely for if checks in other methods and for testing
        return !drones.isEmpty();
    }

}