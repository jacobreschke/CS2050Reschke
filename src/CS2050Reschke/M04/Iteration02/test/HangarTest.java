package CS2050Reschke.M04.Iteration02;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HangarTest {

    @Test
    void testValidFileName() {
        Hangar hangar = new Hangar();
        assertTrue(hangar.loadFromCsv("drones_test.csv"));
    }

    @Test
    void testInvalidFileName() {
        Hangar hangar = new Hangar();
        assertFalse(hangar.loadFromCsv("badFileName.csv"));
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
        assertNull(hangar.parseDroneLine("a,DJI,2021,12.5", 1));
    }

    @Test
    void testParseValidTypeButFullWordStandard() {
        Hangar hangar = new Hangar();
        assertNotNull(hangar.parseDroneLine("Standard,DJI,2021,12.5", 1));
    }

    @Test
    void testParseValidTypeButFullWordPriority() {
        Hangar hangar = new Hangar();
        assertNotNull(hangar.parseDroneLine("Priority,DJI,2021,12.5", 1));
    }

    @Test
    void testParseEmptyDrone() {
        Hangar hangar = new Hangar();
        assertNull(hangar.parseDroneLine(",,,", 1));
    }

    @Test
    void testParseEmptyString() {
        Hangar hangar = new Hangar();
        assertNull(hangar.parseDroneLine("", 1));
    }

    @Test
    void testParseWhitespaceOnly() {
        Hangar hangar = new Hangar();
        assertNull(hangar.parseDroneLine("   ", 1));
    }

    @Test
    void testParseInvalidYear() {
        Hangar hangar = new Hangar();
        assertNull(hangar.parseDroneLine("S,DJI,twenty,12.5", 1));
    }

    @Test
    void testParseInvalidPayload() {
        Hangar hangar = new Hangar();
        assertNull(hangar.parseDroneLine("S,DJI,2021,-12.5", 1));
    }

    @Test
    void testParseZeroKGPayload() {
        Hangar hangar = new Hangar();
        assertNotNull(hangar.parseDroneLine("S,DJI,2021,0.0", 1));
    }

    @Test
    void testParseLowercaseTypeStandardAccepted() {
        Hangar hangar = new Hangar();
        assertNotNull(hangar.parseDroneLine("s,DJI,2021,12.5", 1));
    }

    @Test
    void testParseLowercaseTypePriorityAccepted() {
        Hangar hangar = new Hangar();
        assertNotNull(hangar.parseDroneLine("p,DJI,2021,12.5", 1));
    }

    @Test
    void testParseLowercaseTypeStandardIsUpper() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("s,DJI,2021,12.5", 1);

        assertEquals("S", drone.getType());
    }

    @Test
    void testParseLowercaseTypePriorityIsUpper() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("p,DJI,2021,12.5", 1);

        assertEquals("P", drone.getType());
    }

    @Test
    void testParseExtraSpaces() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("   P   ,   DJI,2021  ,  12.5   ", 1);

        assertEquals("P", drone.getType());
        assertEquals("DJI", drone.getManufacturer());
        assertEquals(2021, drone.getYear());
        assertEquals(12.5, drone.getPayload());
    }

    @Test
    void testParseEmptyType() {
        Hangar hangar = new Hangar();
        assertNull(hangar.parseDroneLine(",DJI,2021,12.5", 1));
    }

    @Test
    void testParseInvalidManufacturer() {
        Hangar hangar = new Hangar();
        assertNull(hangar.parseDroneLine("S,,2021,12.5", 1));
    }

    @Test
    void testParseEmptyYear() {
        Hangar hangar = new Hangar();
        assertNull(hangar.parseDroneLine("S,DJI,,12.5", 1));
    }

    @Test
    void testParseEmptyPayload() {
        Hangar hangar = new Hangar();
        assertNull(hangar.parseDroneLine("S,DJI,2021,", 1));
    }

    @Test
    void testParseNotEnoughFields() {
        Hangar hangar = new Hangar();
        assertNull(hangar.parseDroneLine("DJI,2021,12.5", 1));
    }

    @Test
    void testParseTooManyFields() {
        Hangar hangar = new Hangar();
        assertNull(hangar.parseDroneLine("S,S,DJI,2021,12.5", 1));
    }

    @Test
    void testParseIntPayload() {
        Hangar hangar = new Hangar();
        assertNotNull(hangar.parseDroneLine("S,DJI,2021,12", 1));
    }

    @Test
    void testParseNonDoubleOrIntPayload() {
        Hangar hangar = new Hangar();
        assertNull(hangar.parseDroneLine("S,DJI,2021,asdf", 1));
    }

    @Test
    void testParseYearBelowMinimum() {
        Hangar hangar = new Hangar();
        assertNull(hangar.parseDroneLine("S,DJI,1899,12.5", 1));
    }

    @Test
    void testParseYearAboveMaximum() {
        Hangar hangar = new Hangar();
        assertNull(hangar.parseDroneLine("S,DJI,2101,12.5", 1));
    }

    @Test
    void testParseYearMinimumBound() {
        Hangar hangar = new Hangar();
        assertNotNull(hangar.parseDroneLine("S,DJI,1900,12.5", 1));
    }

    @Test
    void testParseYearMaximumBound() {
        Hangar hangar = new Hangar();
        assertNotNull(hangar.parseDroneLine("S,DJI,2100,12.5", 1));
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

        ArrayList<Drone> drones = hangar.searchDronesByTypeAndManufacturer("S", "DJI");

        assertEquals(1, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeMultipleDrones() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2019, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2020, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));

        ArrayList<Drone> drones = hangar.searchDronesByTypeAndManufacturer("S", "DJI");

        assertEquals(4, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeByLowerCaseTypeS() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 22.0));

        ArrayList<Drone> drones = hangar.searchDronesByTypeAndManufacturer("s", "DJI");

        assertEquals(1, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeLowerCaseTypeP() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2021, 22.0));

        ArrayList<Drone> drones = hangar.searchDronesByTypeAndManufacturer("p", "DJI");

        assertEquals(1, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeByFullWordStandard() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 22.0));

        ArrayList<Drone> drones = hangar.searchDronesByTypeAndManufacturer("Standard", "DJI");

        assertEquals(1, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeByFullWordPriority() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2021, 22.0));

        ArrayList<Drone> drones = hangar.searchDronesByTypeAndManufacturer("Priority", "DJI");

        assertEquals(1, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeLowerCaseManufacturer() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2021, 22.0));

        ArrayList<Drone> drones = hangar.searchDronesByTypeAndManufacturer("P", "dji");

        assertEquals(1, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeUpperCaseManufacturer() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel Robotics", 2021, 22.0));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2021, 22.0));

        ArrayList<Drone> drones = hangar.searchDronesByTypeAndManufacturer("S", "AUTEL ROBOTICS");

        assertEquals(1, drones.size());
    }

    @Test
    void testSearchByManufacturerAndTypeIncorrectTypeInputReturnsEmptyList() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));

        ArrayList<Drone> drones = hangar.searchDronesByTypeAndManufacturer("a", "DJI");

        assertTrue(drones.isEmpty());
    }

    @Test
    void testSearchByManufacturerAndTypeIncorrectManufacturerInputReturnsEmptyList() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));

        ArrayList<Drone> drones = hangar.searchDronesByTypeAndManufacturer("Standard", "asdf");

        assertTrue(drones.isEmpty());
    }

    @Test
    void testSortByYear() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2020, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2019, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2018, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2017, 10));

        ArrayList<Drone> sorted = hangar.sortDronesByYear();

        assertEquals(2017, sorted.get(0).getYear());
        assertEquals(2018, sorted.get(1).getYear());
        assertEquals(2019, sorted.get(2).getYear());
        assertEquals(2020, sorted.get(3).getYear());
        assertEquals(2021, sorted.get(4).getYear());
        assertEquals(2022, sorted.get(5).getYear());
    }

    @Test
    void testEmptyListSortByYear() {
        Hangar hangar = new Hangar();

        ArrayList<Drone> sortedDrones = hangar.sortDronesByYear();

        assertTrue(sortedDrones.isEmpty());
    }

    @Test
    void testReturnedListIsSameSizeAsOriginalYear() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new PriorityDrone("P", "DJI", 2018, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2020, 10));
        hangar.addDrone(new StandardDrone("S", "Skydio", 2020, 10));

        ArrayList<Drone> sortedDrones = hangar.sortDronesByYear();

        assertEquals(hangar.getDrones().size(), sortedDrones.size());
    }

    @Test
    void testOriginalListNotModifiedAfterYearSort() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2020, 10));

        hangar.sortDronesByYear();

        assertEquals(2022, hangar.getDrones().get(0).getYear());
        assertEquals(2021, hangar.getDrones().get(1).getYear());
        assertEquals(2020, hangar.getDrones().get(2).getYear());
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

        ArrayList<Drone> sorted = hangar.sortDronesByPayload();

        assertEquals(17.0, sorted.get(0).getPayload());
        assertEquals(18.0, sorted.get(1).getPayload());
        assertEquals(19.0, sorted.get(2).getPayload());
        assertEquals(20.0, sorted.get(3).getPayload());
        assertEquals(21.0, sorted.get(4).getPayload());
        assertEquals(22.0, sorted.get(5).getPayload());
    }

    @Test
    void testEmptyListSortByPayload() {
        Hangar hangar = new Hangar();

        ArrayList<Drone> sortedDrones = hangar.sortDronesByPayload();

        assertTrue(sortedDrones.isEmpty());
    }

    @Test
    void testReturnedListIsSameSizeAsOriginalPayload() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new PriorityDrone("P", "DJI", 2018, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2020, 10));
        hangar.addDrone(new StandardDrone("S", "Skydio", 2020, 10));

        ArrayList<Drone> sortedDrones = hangar.sortDronesByPayload();

        assertEquals(hangar.getDrones().size(), sortedDrones.size());
    }

    @Test
    void testOriginalArrayListNotModifiedAfterPayloadSort() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 21.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2020, 20.0));

        hangar.sortDronesByPayload();

        assertEquals(22.0, hangar.getDrones().get(0).getPayload());
        assertEquals(21.0, hangar.getDrones().get(1).getPayload());
        assertEquals(20.0, hangar.getDrones().get(2).getPayload());
    }

    @Test
    void testAddDroneReturnsTrue() {
        Hangar hangar = new Hangar();

        assertTrue(hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0)));
    }

    @Test
    void testAddNullDroneReturnsFalse() {
        Hangar hangar = new Hangar();

        assertFalse(hangar.addDrone(null));
    }

    @Test
    void testDroneIsAdded() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));

        assertEquals(1, hangar.getDrones().size());
    }

    @Test
    void testHasDronesReturnsTrue() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "Autel", 2022, 22.0));

        assertTrue(hangar.hasDrones());
    }

    @Test
    void testHasDronesReturnsFalse() {
        Hangar hangar = new Hangar();

        assertFalse(hangar.hasDrones());
    }

    @Test
    void testCountDronesByManufacturer() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2023, 25.0));
        hangar.addDrone(new StandardDrone("S", "Skydio", 2024, 18.0));

        assertEquals(2, hangar.countDronesByManufacturer("DJI"));
    }

    @Test
    void testCountDronesByManufacturerNoMatches() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));

        assertEquals(0, hangar.countDronesByManufacturer("Skydio"));
    }

    @Test
    void testSearchDroneById() {
        Hangar hangar = new Hangar();
        Drone drone = new StandardDrone("S", "DJI", 2022, 22.0);

        hangar.addDrone(drone);
        hangar.getDronesMap().put(drone.getId(), drone);

        assertEquals(drone, hangar.searchDroneById(drone.getId()));
    }

    @Test
    void testSearchDroneByIdNotFound() {
        Hangar hangar = new Hangar();
        Drone drone = new StandardDrone("S", "DJI", 2022, 22.0);

        hangar.addDrone(drone);
        hangar.getDronesMap().put(drone.getId(), drone);

        assertNull(hangar.searchDroneById("D9999"));
    }

    @Test
    void testClearDronesArray() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));
        hangar.clearDronesArray();

        assertTrue(hangar.getDrones().isEmpty());
        assertFalse(hangar.hasDrones());
    }

    @Test
    void testClearDronesMap() {
        Hangar hangar = new Hangar();
        Drone drone = new StandardDrone("S", "DJI", 2022, 22.0);

        hangar.getDronesMap().put(drone.getId(), drone);
        hangar.clearDronesMap();

        assertTrue(hangar.getDronesMap().isEmpty());
    }

    @Test
    void testAddDroneToMaintenanceQueue() {
        Hangar hangar = new Hangar();
        Drone drone = new StandardDrone("S", "DJI", 2022, 22.0);

        hangar.addDroneToQueue(drone);

        assertEquals(drone, hangar.getDronesMaintenanceQueue().peek());
    }

    @Test
    void testFindDuplicateDroneInMaintenanceQueue() {
        Hangar hangar = new Hangar();
        Drone drone = new StandardDrone("S", "DJI", 2022, 22.0);

        hangar.addDroneToQueue(drone);

        assertTrue(hangar.findDuplicateDrone(drone));
    }

    @Test
    void testFindDuplicateDroneFalseWhenQueueEmpty() {
        Hangar hangar = new Hangar();
        Drone drone = new StandardDrone("S", "DJI", 2022, 22.0);

        assertFalse(hangar.findDuplicateDrone(drone));
    }

    @Test
    void testAddDuplicateDroneToMaintenanceQueueDoesNotAddTwice() {
        Hangar hangar = new Hangar();
        Drone drone = new StandardDrone("S", "DJI", 2022, 22.0);

        hangar.addDroneToQueue(drone);
        hangar.addDroneToQueue(drone);

        assertEquals(drone, hangar.getDronesMaintenanceQueue().dequeue());
        assertNull(hangar.getDronesMaintenanceQueue().peek());
    }

    @Test
    void testMaintenanceQueuePeek() {
        Hangar hangar = new Hangar();
        Drone drone = new StandardDrone("S", "DJI", 2022, 22.0);

        hangar.addDroneToQueue(drone);

        assertEquals(drone, hangar.getDronesMaintenanceQueue().peek());
    }

    @Test
    void testMaintenanceQueueDequeue() {
        Hangar hangar = new Hangar();
        Drone drone = new StandardDrone("S", "DJI", 2022, 22.0);

        hangar.addDroneToQueue(drone);

        assertEquals(drone, hangar.getDronesMaintenanceQueue().dequeue());
        assertTrue(hangar.getDronesMaintenanceQueue().isEmpty());
    }



    @Test
    void testUniqueIdsAreGeneratedInOrder() {
        Drone drone1 = new StandardDrone("S", "DJI", 2021, 12.5);
        Drone drone2 = new PriorityDrone("P", "Skydio", 2024, 18.0);

        int id1 = Integer.parseInt(drone1.getId().substring(1));
        int id2 = Integer.parseInt(drone2.getId().substring(1));

        assertEquals(id1 + 1, id2);
    }

    @Test
    void testDroneIdStartsWithD() {
        Drone drone = new StandardDrone("S", "DJI", 2021, 12.5);

        assertTrue(drone.getId().startsWith("D"));
    }


    @Test
    void testSearchEmptyHangarReturnsEmptyList() {
        Hangar hangar = new Hangar();

        ArrayList<Drone> drones = hangar.searchDronesByTypeAndManufacturer("S", "DJI");

        assertTrue(drones.isEmpty());
    }


    @Test
    void testSortByYearWithSameYearKeepsBothDrones() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 20));
        hangar.addDrone(new PriorityDrone("P", "Skydio", 2022, 25));

        ArrayList<Drone> sorted = hangar.sortDronesByYear();

        assertEquals(2, sorted.size());
        assertEquals(2022, sorted.get(0).getYear());
        assertEquals(2022, sorted.get(1).getYear());
    }

    @Test
    void testSortByPayloadWithSamePayloadKeepsBothDrones() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 20));
        hangar.addDrone(new PriorityDrone("P", "Skydio", 2024, 20));

        ArrayList<Drone> sorted = hangar.sortDronesByPayload();

        assertEquals(2, sorted.size());
        assertEquals(20, sorted.get(0).getPayload());
        assertEquals(20, sorted.get(1).getPayload());
    }


}