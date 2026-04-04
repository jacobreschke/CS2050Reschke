package CS2050Reschke.M03.Iteration01.test;

import CS2050Reschke.M03.Iteration01.Drone;
import CS2050Reschke.M03.Iteration01.Hangar;
import CS2050Reschke.M03.Iteration01.PriorityDrone;
import CS2050Reschke.M03.Iteration01.StandardDrone;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HangarTest {


    @Test
    void testValidFileName() {
        // Make sure drones_test.csv is in the project directory
        Hangar hangar = new Hangar();
        String goodFileName = "drones_test.csv";
        assertTrue(hangar.loadFromCsv(goodFileName));
    }

    @Test
    void testInvalidFileName() {
        Hangar hangar = new Hangar();
        String badFileName = "badFileName.csv";
        assertFalse(hangar.loadFromCsv(badFileName));
    }

    @Test
    void testAllUpperCaseFileName() {
        // Make sure drones_test.csv is in the project directory
        Hangar hangar = new Hangar();
        String goodFileName = "DRONES_TEST.CSV";
        assertTrue(hangar.loadFromCsv(goodFileName));
    }

    @Test
    void testAllLowerCaseFileName() {
        // Make sure drones_test.csv is in the project directory
        Hangar hangar = new Hangar();
        String goodFileName = "drones_test.csv";
        assertTrue(hangar.loadFromCsv(goodFileName));
    }

    @Test
    void testParseValidDrone() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,DJI,2021,12.5", 1);
        assertNotNull(drone);
        assertEquals("S", drone.getType());
        assertEquals("DJI", drone.getManufacturer());
        assertEquals(2021, drone.getYear());
        assertEquals(12.5, drone.getPayload());
    }

    @Test
    void testParseInvalidType() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("a,DJI,2021,12.5", 1);
        assertNull(drone);
    }

    @Test
    void testParseValidTypeButFullWordStandard() {
        // Found a bug here, If the user attempts to enter a drone that is "Standard" it should be allowed
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("Standard,DJI,2021,12.5", 1);
        assertNotNull(drone);
    }

    @Test
    void testParseValidTypeButFullWordPriority() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("Priority,DJI,2021,12.5", 1);
        assertNotNull(drone);
    }

    @Test
    void testParseEmptyDrone() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine(",,,", 1);
        assertNull(drone);
    }

    @Test
    void testParseEmptyString() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("", 1);
        assertNull(drone);
    }

    @Test
    void testParseWhitespaceOnly() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("   ", 1);
        assertNull(drone);
    }

    @Test
    void testParseInvalidYear() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,DJI,twenty,12.5", 1);
        assertNull(drone);
    }

    @Test
    void testParseInvalidPayload() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,DJI,2021,-12.5", 1);
        assertNull(drone);
    }

    @Test
    void testParseZeroKGPayload() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,DJI,2021,0.0", 1);
        assertNotNull(drone);
    }

    @Test
    void testParseLowercaseTypeStandardAccepted() {
        // if (droneTypeText.equalsIgnoreCase("S")) {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("s,DJI,2021,12.5", 1);
        assertNotNull(drone);
    }

    @Test
    void testParseLowercaseTypePriorityAccepted() {
        // if (droneTypeText.equalsIgnoreCase("P")) {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("p,DJI,2021,12.5", 1);
        assertNotNull(drone);
    }

    @Test
    void testParseLowercaseTypeStandardIsUpper() {
        // if (droneTypeText.equalsIgnoreCase("S")) {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("s,DJI,2021,12.5", 1);
        assertEquals("S", drone.getType());
    }

    @Test
    void testParseLowercaseTypePriorityIsUpper() {
        // if (droneTypeText.equalsIgnoreCase("P")) {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("p,DJI,2021,12.5", 1);
        assertEquals("P", drone.getType());
    }


    @Test
    void testParseExtraSpaces() {
        /*
        String droneTypeText = parts[0].trim();
        String droneManufacturerText = parts[1].trim();
        String droneYearText = parts[2].trim();
        String dronePayloadText = parts[3].trim();
         */

        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("   P   ,   DJI,2021  ,  12.5   ", 1);
        assertEquals("P", drone.getType());
        assertEquals("DJI", drone.getManufacturer());
        assertEquals(2021, drone.getYear());
        assertEquals(12.5, drone.getPayload());
    }

    @Test
    void testParseLowercaseTypeToUpperCase() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("p,DJI,2021,12.5", 1);
        assertEquals("P", drone.getType());

    }

    @Test
    void testParseEmptyType() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine(",DJI,2021,12.5", 1);
        assertNull(drone);
    }

    @Test
    void testParseInvalidManufacturer() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,,2021,12.5", 1);
        assertNull(drone);
    }

    @Test
    void testParseEmptyYear() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,DJI,,12.5", 1);
        assertNull(drone);
    }

    @Test
    void testParseEmptyPayload() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,DJI,2021,", 1);
        assertNull(drone);
    }

    @Test
    void testParseNotEnoughFields() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("DJI,2021,12.5", 1);
        assertNull(drone);
    }

    @Test
    void testParseTooManyFields() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,S,DJI,2021,12.5", 1);
        assertNull(drone);
    }

    @Test
    void testParseIntPayload() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,DJI,2021,12", 1);
        assertNotNull(drone);
    }

    @Test
    void testParseNonDoubleOrIntPayload() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,DJI,2021,asdf", 1);
        assertNull(drone);
    }

    @Test
    void testParseYearBelowMinimum() {
        // if (year < 1900 || year > 2100)
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,DJI,1899,12.5", 1);
        assertNull(drone);
    }

    @Test
    void testParseYearAboveMaximum() {
        // if (year < 1900 || year > 2100)
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,DJI,2101,12.5", 1);
        assertNull(drone);
    }

    @Test
    void testParseYearMinimumBound() {
        // if (year < 1900 || year > 2100)
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,DJI,1900,12.5", 1);
        assertNotNull(drone);
    }

    @Test
    void testParseYearMaximumBound() {
        // if (year < 1900 || year > 2100)
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,DJI,2100,12.5", 1);
        assertNotNull(drone);
    }

    @Test
    void testParseManufacturerWithSpacesInsideName() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,Autel Robotics,2021,12.5", 1);
        assertEquals("Autel Robotics", drone.getManufacturer());
    }




    @Test
    void testSearchByManufacturerAndTypeOneDrone() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 22.0));
        ArrayList<Drone> testDrones = hangar.displayDronesByTypeAndManufacturer("S", "DJI");
        assertEquals(testDrones.size(), hangar.getDrones().size());
    }

    @Test
    void testSearchByManufacturerAndTypeMultipleDrones() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2019, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2020, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));


        ArrayList<Drone> drones = hangar.displayDronesByTypeAndManufacturer("S", "DJI");
        assertEquals(drones.size(), hangar.getDrones().size());
    }

    @Test
    void testSearchByManufacturerAndTypeByUpperCaseTypeS() {
        // My first version of this and the following search by MFG & type tests compared a single element with each
        // other and always returned a pass which was incorrect logic
        // I needed to add a bad element and a good element and confirm size 1
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 22.0));

        ArrayList<Drone> drones = hangar.displayDronesByTypeAndManufacturer("S", "DJI");
        assertEquals(1, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeByLowerCaseTypeS() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 22.0));

        ArrayList<Drone> drones = hangar.displayDronesByTypeAndManufacturer("s", "DJI");
        assertEquals(1, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeByUppercaseTypeP() {
        // My first version of this and the following search by MFG & type tests compared a single element with each
        // other and always returned a pass which was incorrect logic
        // I needed to add a bad element and a good element and confirm size 1
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new StandardDrone("P", "DJI", 2021, 22.0));

        ArrayList<Drone> drones = hangar.displayDronesByTypeAndManufacturer("P", "DJI");
        assertEquals(1, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeLowerCaseTypeP() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new StandardDrone("P", "DJI", 2021, 22.0));

        ArrayList<Drone> drones = hangar.displayDronesByTypeAndManufacturer("p", "DJI");
        assertEquals(1, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeByUpperCaseTypeStandard() {
        // Found another bug on this one, Couldn't search by "Standard" only "S"
        // Fixed by converting Standard to S and Priority to P
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 22.0));

        ArrayList<Drone> drones = hangar.displayDronesByTypeAndManufacturer("Standard", "DJI");
        assertEquals(1, drones.size());
    }

    @Test
    void testSearchByManufacturerAndByTypeLowerCaseTypeStandard() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 22.0));

        ArrayList<Drone> drones = hangar.displayDronesByTypeAndManufacturer("standard", "DJI");
        assertEquals(1, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeByUpperCaseTypePriority() {
        Hangar hangar = new Hangar();


        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new StandardDrone("P", "DJI", 2021, 22.0));

        ArrayList<Drone> drones = hangar.displayDronesByTypeAndManufacturer("Priority", "DJI");
        assertEquals(1, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeLowerCaseTypePriority() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new StandardDrone("P", "DJI", 2021, 22.0));

        ArrayList<Drone> drones = hangar.displayDronesByTypeAndManufacturer("priority", "DJI");
        assertEquals(1, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeLowerCaseManufacturer() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new StandardDrone("P", "DJI", 2021, 22.0));

        ArrayList<Drone> drones = hangar.displayDronesByTypeAndManufacturer("P", "dji");
        assertEquals(1, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeUpperCaseManufacturer() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new StandardDrone("P", "DJI", 2021, 22.0));

        ArrayList<Drone> drones = hangar.displayDronesByTypeAndManufacturer("S", "AUTEL ROBOTICS");
        assertEquals(1, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeIncorrectTypeInput() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));

        ArrayList<Drone> drones = hangar.displayDronesByTypeAndManufacturer("a", "DJI");
        assertEquals(0, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeIncorrectManufacturerInput() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));

        ArrayList<Drone> drones = hangar.displayDronesByTypeAndManufacturer("Standard", "asdf");
        assertEquals(0, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeIncorrectBothInput() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));

        ArrayList<Drone> drones = hangar.displayDronesByTypeAndManufacturer("asdf", "asdf");
        assertEquals(0, drones.size());
    }




    @Test
    void testSortByYear() {
        // Testing my sorting algorithm
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2020, 10));

        hangar.addDrone(new PriorityDrone("P", "DJI", 2019, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2018, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2017, 10));

        Drone[] sorted = hangar.sortDronesByYear();

        // assert that the year is sorted correctly
        assertEquals(2017, sorted[0].getYear());
        assertEquals(2018, sorted[1].getYear());
        assertEquals(2019, sorted[2].getYear());
        assertEquals(2020, sorted[3].getYear());
        assertEquals(2021, sorted[4].getYear());
        assertEquals(2022, sorted[5].getYear());
    }

    @Test
    void testEmptyArraySortByYear() {
        Hangar hangar = new Hangar();
        Drone[] sortedDrones = hangar.sortDronesByYear();
        assertEquals(0, sortedDrones.length);
    }

    @Test
    void testAlreadySortedArrayYear() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new PriorityDrone("P", "DJI", 2017, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2018, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2019, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2020, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 10));

        Drone[] sortedDrones = hangar.sortDronesByYear();

        assertEquals(2017, sortedDrones[0].getYear());
        assertEquals(2018, sortedDrones[1].getYear());
        assertEquals(2019, sortedDrones[2].getYear());
        assertEquals(2020, sortedDrones[3].getYear());
        assertEquals(2021, sortedDrones[4].getYear());
        assertEquals(2022, sortedDrones[5].getYear());
    }

    @Test
    void testRandomArraySortingYear() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new PriorityDrone("P", "DJI", 2018, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2020, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2019, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2017, 10));

        Drone[] sortedDrones = hangar.sortDronesByYear();

        assertEquals(2017, sortedDrones[0].getYear());
        assertEquals(2018, sortedDrones[1].getYear());
        assertEquals(2019, sortedDrones[2].getYear());
        assertEquals(2020, sortedDrones[3].getYear());
        assertEquals(2021, sortedDrones[4].getYear());
        assertEquals(2022, sortedDrones[5].getYear());
    }

    @Test
    void testReturnedArrayIsSameSizeAsOriginalYear() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new PriorityDrone("P", "DJI", 2018, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2020, 10));
        hangar.addDrone(new StandardDrone("S", "Skydio", 2020, 10));
        hangar.addDrone(new StandardDrone("S", "Parrot", 2020, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2019, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2017, 10));

        Drone[] sortedDrones = hangar.sortDronesByYear();

        assertEquals(hangar.getDrones().size(), sortedDrones.length);
    }

    @Test
    void testOriginalListNotModifiedAfterYearSort() {
        // Testing to make sure the original drones array isn't changed after calling sortDronesByYear()
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2020, 10));

        hangar.addDrone(new PriorityDrone("P", "DJI", 2019, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2018, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2017, 10));

        hangar.sortDronesByYear();

        // assert that hangar get drones array get index get year = original
        assertEquals(2022, hangar.getDrones().get(0).getYear());
        assertEquals(2021, hangar.getDrones().get(1).getYear());
        assertEquals(2020, hangar.getDrones().get(2).getYear());
        assertEquals(2019, hangar.getDrones().get(3).getYear());
        assertEquals(2018, hangar.getDrones().get(4).getYear());
        assertEquals(2017, hangar.getDrones().get(5).getYear());
    }


    @Test
    void testSortByPayload() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 21.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 20.0));

        hangar.addDrone(new PriorityDrone("P", "DJI", 2021, 19.0));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2021, 18.0));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2021, 17.0));

        Drone[] sorted = hangar.sortDronesByPayload();

        assertEquals(17.0, sorted[0].getPayload());
        assertEquals(18.0, sorted[1].getPayload());
        assertEquals(19.0, sorted[2].getPayload());
        assertEquals(20.0, sorted[3].getPayload());
        assertEquals(21.0, sorted[4].getPayload());
        assertEquals(22.0, sorted[5].getPayload());
    }

    @Test
    void testEmptyArraySortByPayload() {
        Hangar hangar = new Hangar();
        Drone[] sortedDrones = hangar.sortDronesByPayload();
        assertEquals(0, sortedDrones.length);
    }

    @Test
    void testAlreadySortedArrayPayload() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new PriorityDrone("P", "DJI", 2021, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2021, 11));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2021, 12));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 13));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 14));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 15));

        Drone[] sortedDrones = hangar.sortDronesByPayload();

        assertEquals(10, sortedDrones[0].getPayload());
        assertEquals(11, sortedDrones[1].getPayload());
        assertEquals(12, sortedDrones[2].getPayload());
        assertEquals(13, sortedDrones[3].getPayload());
        assertEquals(14, sortedDrones[4].getPayload());
        assertEquals(15, sortedDrones[5].getPayload());
    }

    @Test
    void testRandomArraySortingPayload() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 15));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2021, 11));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2021, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 13));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2021, 12));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 14));

        Drone[] sortedDrones = hangar.sortDronesByPayload();

        assertEquals(10, sortedDrones[0].getPayload());
        assertEquals(11, sortedDrones[1].getPayload());
        assertEquals(12, sortedDrones[2].getPayload());
        assertEquals(13, sortedDrones[3].getPayload());
        assertEquals(14, sortedDrones[4].getPayload());
        assertEquals(15, sortedDrones[5].getPayload());
    }

    @Test
    void testReturnedArrayIsSameSizeAsOriginalPayload() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new PriorityDrone("P", "DJI", 2018, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2020, 10));
        hangar.addDrone(new StandardDrone("S", "Skydio", 2020, 10));
        hangar.addDrone(new StandardDrone("S", "Parrot", 2020, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2019, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2017, 10));

        Drone[] sortedDrones = hangar.sortDronesByPayload();

        assertEquals(hangar.getDrones().size(), sortedDrones.length);
    }

    @Test
    void testOriginalArrayListNotModifiedAfterPayloadSort() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 21.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2020, 20.0));

        hangar.addDrone(new PriorityDrone("P", "DJI", 2019, 19.0));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2018, 18.0));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2017, 17.0));

        hangar.sortDronesByPayload();

        assertEquals(22.0, hangar.getDrones().get(0).getPayload());
        assertEquals(21.0, hangar.getDrones().get(1).getPayload());
        assertEquals(20.0, hangar.getDrones().get(2).getPayload());
        assertEquals(19.0, hangar.getDrones().get(3).getPayload());
        assertEquals(18.0, hangar.getDrones().get(4).getPayload());
        assertEquals(17.0, hangar.getDrones().get(5).getPayload());
    }

    @Test
    void testAddDroneReturnsTrue() {
        Hangar hangar = new Hangar();
        assertTrue(hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0)));
    }

    @Test
    void testDroneIsAdded() {
        Hangar hangar = new Hangar();
        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));
        assertEquals(1, hangar.getDrones().size());
    }

    @Test
    void testFindDuplicateDrone() {
        Hangar hangar = new Hangar();
        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));
        assertTrue(hangar.findDuplicateDrone(new StandardDrone("S", "DJI", 2022, 22.0)));
    }

    @Test
    void testDuplicateDroneNotAllowed() {
        // found a bug in addDrone() while writing this test that allowed duplicates to be added
        Hangar hangar = new Hangar();
        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));
        assertFalse(hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0)));
        assertEquals(1, hangar.getDrones().size());
    }

    @Test
    void testInsertionSortAlgorithm() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel", 2022, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));

        Drone[] sortedArr = hangar.sortDronesByYear();

        // This should take 2 drones with the same year but different mfg
        // and when sorting keep them in the same original order
        assertEquals(hangar.getDrones().get(0), sortedArr[0]);
        assertEquals(hangar.getDrones().get(1), sortedArr[1]);
    }


    @Test
    void testDisplayHangarInventoryHasDrones() {
        Hangar hangar = new Hangar();
        hangar.addDrone(new StandardDrone("S", "Autel", 2022, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));

        assertTrue(hangar.hasDrones());
    }

    @Test
    void testDisplayHangarInventoryHasNoDrones() {
        Hangar hangar = new Hangar();

        assertFalse(hangar.hasDrones());
    }

}