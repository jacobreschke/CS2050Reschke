package CS2050Reschke.M04.Iteration02;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Hangar class manages a collection of drone objects
 * Allows loading, searching, sorting, and displaying drones
 * by a command line interface
 */
public class Hangar {

    // Refactor: Removed the sortedDrones array. sort algorithms now create their own and use Collections
    //private Drone[] sortedDrones;

    private ArrayList<Drone> drones = new ArrayList<>();
    private HashMap<String, Drone> dronesMap = new HashMap<>();
    private MaintenanceQueue<Drone> maintenanceQueue = new MaintenanceQueue<>();


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
     * Returns the HashMap of drones stored in the hangar
     *
     * @return HashMap of Drone objects
     */
    public HashMap<String, Drone> getDronesMap() {
        return dronesMap;
    }

    /**
     * Returns the Queue of Maintenance drones
     * @return the drone maintenance queue
     */
    public MaintenanceQueue<Drone> getDronesMaintenanceQueue() {
        return maintenanceQueue;
    }


    /**
     * Adds a drone to the maintenance queue if the drone isnt null and if it isnt already in the queue
     *
     * @param queueDrone The drone that should be added to the maintenanceQueue
     * @return true if added, false if null or duplicate
     */
    public boolean addDroneToQueue(Drone queueDrone) {
        // This is a boolean for test purposes
        if (queueDrone == null) {
            return false;
        }
        if (findDuplicateDrone(queueDrone)) {
            return false;
        }
        maintenanceQueue.offer(queueDrone);
        return true;
    }

    /**
     * Gets the drone that matches the input key and outputs the drone
     *
     * @param idSearchKey The ID of the drone you wish to find
     * @return the drone value that matches the key
     */
    public Drone searchDroneById(String idSearchKey) {
        // This whole methods reason is so we can unit test
        if (idSearchKey == null) {
            return null;
        }
        return dronesMap.get(idSearchKey.toUpperCase());
    }


    /**
     * Populates an array the size of drones ArrayList and populates that array with
     * drones in the same order as the drones ArrayList then sorts by payload
     *
     * @return Array of drones sorted by payload
     */
    public ArrayList<Drone> sortDronesByPayload() {
        // REFACTOR: Uses Collections now
        ArrayList<Drone> sortedByPayload = new ArrayList<>(drones);
        Collections.sort(sortedByPayload, Comparator.comparing(Drone::getPayload));

        return sortedByPayload;
    }


    /**
     * Populates an array the size of drones ArrayList and populates that array with
     * drones in the same order as the drones ArrayList then sorts by year
     *
     * @return Array of drones sorted by year
     */
    public ArrayList<Drone> sortDronesByYear() {
        // REFACTOR: Uses Collections now
        ArrayList<Drone> sortedByYear = new ArrayList<>(drones);
        Collections.sort(sortedByYear, Comparator.comparing(Drone::getYear));

        return sortedByYear;
    }

    /**
     * Filters and displays drones by type and manufacturer. Allows drone type by
     * lowercase or full word and converts to "S" or "P"
     * The method only returns an ArrayList so that we can unit test it
     *
     * @param type the drones type ("S" or "P")
     * @param manufacturer the drones manufacturer
     * @return ArrayList of drones matching type and manufacturer
     */
    public ArrayList<Drone> searchDronesByTypeAndManufacturer(String type, String manufacturer) {
        // Refactor: No longer prints the drone just finds and returns
        ArrayList<Drone> returnDrones = new ArrayList<>();

        // Allows the user to type standard or priority instead of just s or p
        if (type.equalsIgnoreCase("standard")) {
            type = "S";
        } else if (type.equalsIgnoreCase("priority")) {
            type = "P";
        }

        // returning a drones array purely for testing. returnDrones does nothing in the program
        for (Drone drone : drones) {
            // Ignore case sensitivity so user can input lowercase and uppercase
            if (drone.getType().equalsIgnoreCase(type) && drone.getManufacturer().equalsIgnoreCase(manufacturer)) {
                returnDrones.add(drone);
            }
        }


        return returnDrones;
    }

    /**
     * Counts and displays how many drones match the given manufacturer
     *
     * @param key the manufacturer name to search
     */
    public int countDronesByManufacturer(String key) {
        int count = 0;

        for (Drone drone : drones) {
            if (drone.getManufacturer().equalsIgnoreCase(key)) {
                count++;
            }
        }
        return count;
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

        // Creating a map of drones from the array list of drones. See the method
        // for design reasoning
        createMapOfDrones();

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
     * loops through the drone array and adds the drone to the HashMap of drones using the ID as the key
     */
    private void createMapOfDrones() {
        // loading all valid drones into an ArrayList first then building the hashMap from that data
        // Design wise parsing data can turn into a big job so I want to let the program focus
        // on only that job. Loading into the hashmap will be an equally big job.
        // separating each job feels like a much cleaner solution especially for debugging
        for (Drone drone : drones) {
            dronesMap.put(drone.getId().toUpperCase(), drone);
        }
    }


    /**
     * Optional treemap implementation. It creates a TreeMap of Key:String and Value:Drone ArrayList
     * If the manufacturer doesnt exist, add it to the map, if mfg exists, add to the mfg array
     *
     * @return A populated TreeMap of drones sorted by manufacturer
     */
    public TreeMap<String, ArrayList<Drone>> groupDronesByManufacturer() {
        // Extra treemap for drones stores data in key order
        // in this case the key is the mfg and the value is an arraylist of drones

        // The tree is built with the mfg as they key which contains the
        // reference address to the value which is the arraylist

        TreeMap<String, ArrayList<Drone>> groupedDrones = new TreeMap<>();

        for (Drone drone : drones) {
            // Pull the drones mfg
            String manufacturer = drone.getManufacturer();

            // Checks if its a new mfg
            if (!groupedDrones.containsKey(manufacturer)) {
                // if not then add the mfg
                groupedDrones.put(manufacturer, new ArrayList<>());
            }

            // get from the map the manufacturer then add the drone
            groupedDrones.get(manufacturer).add(drone);
        }

        return groupedDrones;
    }

    /**
     * Parses a line of a CSV file and creates a Standard or Priority Drone object based on type
     *
     * @param line       the actual line data of the CSV being parsed
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
     *
     * @param parsedDrone drone parsed by parseDroneLine()
     * @return true if added successfully, false if a duplicate
     */
    public boolean addDrone(Drone parsedDrone) {
        // Refactor: Moved duplicate checking out of this method
        if (parsedDrone == null) {
            return false;
        }
        drones.add(parsedDrone);
        return true;
    }


    public boolean findDuplicateDrone(Drone droneToAdd) {
        // This null check theoretically shouldnt be reached but just in case
        if (droneToAdd == null) {
            return false;
        }

        for (Drone drone : maintenanceQueue.getQueue()) {
            if (droneToAdd.getId().equals(drone.getId())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if there are drones in the hangar
     *
     * @return true if there are drones, false if there are no drones
     */
    public boolean hasDrones() {
        // made purely for if checks in other methods and for testing
        return !drones.isEmpty();
    }

    /**
     * Empties the drones array
     */
    public void clearDronesArray() {
        drones.clear();
    }

    /**
     * Empties the drones Map
     */
    public void clearDronesMap() {
        dronesMap.clear();
    }
}

/**
 * This class uses Java's LinkedList to create a queue for the drone maintenance
 *
 * It uses a LinkedList for the Queue because its a much more efficient way to support
 * a first in first out operation.
 *
 * @param <E>
 */
class MaintenanceQueue<E> {

    // Creates an empty LinkedList
    private LinkedList<E> queue = new LinkedList<>();;


    /**
     * Gets the queue LinkedList and returns it
     * @return
     */
    public LinkedList<E> getQueue() {
        // The better design would be to implement Iterable because this method
        // allows calling all LinkedList methods from it effectively removing the design choice
        // of creating our own methods. But I dont remember us learning Iterable so I
        // created a getter instead.
        return queue;
    }

    /**
     * Adds to the end of the ArrayList
     * @param item The generic(drone) that will be added
     */
    public void offer(E item) {
        queue.offer(item);
    }

    /**
     * Removes and returns the generic(drone) at the end of the queue and returns it
     * @return The generic that was removed
     */
    public E dequeue() {
        return queue.poll();
    }

    /**
     * Checks the element at the front of the queue without removing it
     * @return returns the generic(drone) at the front of the queue
     */
    public E peek() {
        return queue.peek();
    }

    /**
     * Check if the queue is empty and returns true if empty, false if it contains anything
     * @return true if empty, false if full
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Loops through the queue if it contains elements
     */
    public void displayQueue() {
        if (queue.isEmpty()) {
            System.out.println("Maintenance queue is empty");
        }
        for (E item : queue) {
            System.out.println(item);
        }
    }

}

/**
 * This Classes purpose is to separate the command line logic from the hangar logic.
 * Separating the logic allows for easier debugging and adding/removing functionality from the
 * command line
 */
class HangarConsole {
    // This class was created to separate the printing logic from the sorting/searching logic
    // This way if theres any bugs in the console output we know where to go

    // Create an empty hangar
    private Hangar hangar;
    private Scanner input = new Scanner(System.in);


    /**
     * HangarConsole constructor takes in a hangar as input
     * @param hangar The hangar that it is displaying the command line information for
     */
    public HangarConsole(Hangar hangar) {
        this.hangar = hangar;
    }

    /**
     * This method is the entry to the program. It displays the command line
     * of the program until the user enters 12 (exit). If the user doesnt enter
     * a valid number it enters the default case where it tells the user to try again
     */
    public void run() {
        // Entry point to the program. Similar to game development I'll have a Game.run() that
        // is the highest level of the program.

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
                System.out.println("Invalid input. Please enter a number from 1 to 12.");
                continue;
            }

            switch (userDecision) {
                case 1:
                    // clear the drones array before parsing so we don't allow duplicating the list
                    hangar.clearDronesArray();
                    hangar.clearDronesMap();
                    System.out.print("Enter CSV file name: ");

                    String fileName = getUserInput();
                    hangar.loadFromCsv(fileName);
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

                    ArrayList<Drone> results = hangar.searchDronesByTypeAndManufacturer(type, manufacturer);

                    if (results.isEmpty()) {
                        System.out.println("No drones available.");
                    } else {
                        displayDrones(results);
                    }
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
                    String mfgSearchKey = getUserInput();
                    int count = hangar.countDronesByManufacturer(mfgSearchKey);
                    System.out.println(mfgSearchKey + " appears: " + count + " times.");

                    break;
                case 7:
                    // Search by Id
                    System.out.print("Enter Drone ID: ");
                    String idSearchKey = getUserInput();
                    Drone foundDrone = hangar.searchDroneById(idSearchKey);
                    if (hangar.getDronesMap().isEmpty()) {
                        System.out.println("Hangar is empty");
                    } else if (foundDrone == null) {
                        System.out.println("Drone not found");
                    } else {
                        System.out.println(foundDrone);
                    }

                    break;
                case 8:
                    // Add drone to queue
                    System.out.print("Enter drone ID to add to queue:");
                    Drone queueDrone = hangar.searchDroneById(getUserInput());
                    if (hangar.getDronesMap().isEmpty()) {
                        System.out.println("Hangar is empty");
                    } else if (queueDrone == null) {
                        System.out.println("Drone not found");
                    } else if (hangar.findDuplicateDrone(queueDrone)) {
                        System.out.println("Drone is already in the maintenance queue.");
                    } else {
                        hangar.addDroneToQueue(queueDrone);
                        System.out.println(queueDrone.getId() + " added to the maintenance queue.");
                    }

                    break;
                case 9:
                    // View next drone in queue
                    System.out.println("Next drone in queue: " + hangar.getDronesMaintenanceQueue().peek());
                    break;
                case 10:
                    // Process next drone in queue
                    System.out.println("Processing drone: " + hangar.getDronesMaintenanceQueue().peek());
                    hangar.getDronesMaintenanceQueue().dequeue();


                    break;
                case 11:
                    // Display Queue
                    hangar.getDronesMaintenanceQueue().displayQueue();
                    break;
                case 12:
                    // Exit
                    exit = true;
                    input.close();
                    break;
                case 13:
                    // TreeMap grouping
                    displayDronesGroupedByManufacturer();
                    break;
                default:
                    // If user doesn't enter a number in the list tell them to try again
                    System.out.println("Enter a number between 1 and 13.");
                    break;
            }
        } while (!exit);
    }

    /**
     * Displays all drones sorted by payload
     */
    private void displayDronesByPayload() {

        ArrayList<Drone> sortedDrones = hangar.sortDronesByPayload();

        if (!hangar.hasDrones()) {
            System.out.println("No drones in the hangar.");
        } else {
            System.out.println("Sorted Inventory By Payload:");
            displayDrones(sortedDrones);
        }
    }

    /**
     * Displays all drones sorted by year
     */
    private void displayDronesByYear() {

        ArrayList<Drone> sortedDrones = hangar.sortDronesByYear();

        if (hangar.hasDrones()) {
            System.out.println("Sorted Inventory By Year:");
            displayDrones(sortedDrones);
        } else {
            System.out.println("No drones in the hangar");
        }
    }

    /**
     * Displays the hangar current inventory unsorted
     */
    private void displayHangarInventory() {
        if (hangar.hasDrones()) {
            displayDrones(hangar.getDrones());
        } else {
            System.out.println("No drones in the hangar");
        }
    }

    /**
     * loops through the input array and prints the drones.
     * @param drones the array of drones that will be printed to console
     */
    private void displayDrones(ArrayList<Drone> drones) {
        // Refactor: Made this method so that I can call it inside the displayByYear/Payload methods
        // as well as displayInventory()
        if (drones.isEmpty()) {
            System.out.println("No drones found.");
        } else {
            for (Drone drone : drones) {
                System.out.println(drone);
            }
        }
    }

    /**
     * Displays the options for the command line
     */
    private void displayCLIOptions() {
        System.out.println("1. Load Drones from CSV");
        System.out.println("2. Display Hangar Inventory");
        System.out.println("3. Search Drones (Manufacturer & Type)");
        System.out.println("4. View Inventory Sorted by Payload");
        System.out.println("5. View Inventory Sorted by Year");
        System.out.println("6. Count Drones by Manufacturer");
        System.out.println("7. Search Drone by ID");
        System.out.println("8. Add Drone to Maintenance Queue");
        System.out.println("9. View Next Drone in Queue");
        System.out.println("10. Process Next Drone in Queue");
        System.out.println("11. Display Queue");
        System.out.println("12. Exit");
        System.out.println("13. (Extra) Display Drones Sorted By Manufacturer");
        System.out.print("Enter your choice (1-13): ");
    }

    /**
     * Asks the user for input and returns in type string with whitespace trimmed
     * @return String of user input
     */
    public String getUserInput() {
        String userInput = input.nextLine();
        return userInput.trim();
    }

    /**
     * Display drones in a TreeMap sorted by manufacturer
     */
    private void displayDronesGroupedByManufacturer() {
        // Fill the treemap
        TreeMap<String, ArrayList<Drone>> groupedDrones = hangar.groupDronesByManufacturer();

        // safety check
        if (groupedDrones.isEmpty()) {
            System.out.println("No drones in the hangar.");
            return;
        }

        // print through the drones
        for (String manufacturer : groupedDrones.keySet()) {
            System.out.println();
            System.out.println(manufacturer + ":");

            for (Drone drone : groupedDrones.get(manufacturer)) {
                System.out.println("  " + drone);
            }
        }
    }


}