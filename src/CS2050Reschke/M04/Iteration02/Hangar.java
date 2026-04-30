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

    public HashMap<String, Drone> getDronesMap() {
        return dronesMap;
    }

    public MaintenanceQueue<Drone> getDronesMaintenanceQueue() {
        return maintenanceQueue;
    }


    public boolean addDroneToQueue(Drone queueDrone) {
        if (queueDrone == null) {
            return false;
        }
        if (findDuplicateDrone(queueDrone)) {
            return false;
        }
        maintenanceQueue.offer(queueDrone);
        return true;
    }

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
     * <p>
     * The method only returns an ArrayList so that we can unit test it
     *
     * @param type         the drones type ("S" or "P")
     * @param manufacturer the drones manufacturer
     * @return List of drones matching type and manufacturer
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


    private void createMapOfDrones() {
        // loading all valid drones into an ArrayList first then building the hashMap from that data
        // Design wise parsing data can turn into a big job so I want to let the program focus
        // on only that job. Loading into the hashmap will be an equally big job.
        // separating each job feels like a much cleaner solution especially for debugging
        for (Drone drone : drones) {
            dronesMap.put(drone.getId().toUpperCase(), drone);
        }
    }


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

        for (Drone drone : maintenanceQueue) {
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

    public void clearDronesArray() {
        drones.clear();
    }

    public void clearDronesMap() {
        dronesMap.clear();
    }
}


class MaintenanceQueue<E> implements Iterable<E> {
    // Implements Iterable so that our checking for duplicates lets us use a for each loop
    // It allows me to keep queue private and still loop the list

    private LinkedList<E> queue;

    MaintenanceQueue() {
        queue = new LinkedList<>();
    }

    public void offer(E item) {
        queue.offer(item);
    }

    public E dequeue() {
        return queue.poll();
    }

    public E peek() {
        return queue.peek();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void displayQueue() {
        if (queue.isEmpty()) {
            System.out.println("Maintenance queue is empty");
        }
        for (E item : queue) {
            System.out.println(item);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return queue.iterator();
    }
}

class HangarConsole {
    // This class was created to separate the printing logic from the sorting/searching logic
    // This way if theres any bugs in the console output we know where to go

    private Hangar hangar;
    private Scanner input = new Scanner(System.in);


    public HangarConsole(Hangar hangar) {
        this.hangar = hangar;
    }

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

    private void displayDronesByPayload() {

        ArrayList<Drone> sortedDrones = hangar.sortDronesByPayload();

        if (!hangar.hasDrones()) {
            System.out.println("No drones in the hangar.");
        } else {
            System.out.println("Sorted Inventory By Payload:");
            displayDrones(sortedDrones);
        }
    }


    private void displayDronesByYear() {

        ArrayList<Drone> sortedDrones = hangar.sortDronesByYear();

        if (hangar.hasDrones()) {
            System.out.println("Sorted Inventory By Year:");
            displayDrones(sortedDrones);
        } else {
            System.out.println("No drones in the hangar");
        }
    }

    private void displayHangarInventory() {
        if (hangar.hasDrones()) {
            displayDrones(hangar.getDrones());
        } else {
            System.out.println("No drones in the hangar");
        }
    }

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

    public String getUserInput() {
        String userInput = input.nextLine();
        return userInput.trim();
    }

    private void displayDrones(ArrayList<Drone> drones) {
        // Refactor: Before I would loop and print multiple times, This helps DRY/SRP
        // Also changed name to displayDrones to keep consistency with display methods
        if (drones.isEmpty()) {
            System.out.println("No drones found.");
        } else {
            for (Drone drone : drones) {
                System.out.println(drone);
            }
        }
    }
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