package CS2050Reschke.M03.Iteration01.test;

import CS2050Reschke.M03.Iteration01.Drone;
import CS2050Reschke.M03.Iteration01.Hangar;
import CS2050Reschke.M03.Iteration01.PriorityDrone;
import CS2050Reschke.M03.Iteration01.StandardDrone;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HangarTest {

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
    void testParseInvalidType(){
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("a,DJI,2021,12.5", 1);
        assertNull(drone);
    }

    @Test
    void testParseInvalidYear(){
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,DJI,twenty,12.5", 1);
        assertNull(drone);
    }
    @Test
    void testParseInvalidPayload(){
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,DJI,2021,-12.5", 1);
        assertNull(drone);
    }
    @Test
    void testParseZeroKGPayload(){
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,DJI,2021,0.0", 1);
        assertNotNull(drone);
    }

    @Test
    void testParseLowercaseTypeStandard() {
        // if (droneTypeText.equalsIgnoreCase("S")) {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("s,DJI,2021,12.5", 1);
        assertNotNull(drone);
    }

    @Test
    void testParseLowercaseTypePriority() {
        // if (droneTypeText.equalsIgnoreCase("P")) {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("p,DJI,2021,12.5", 1);
        assertNotNull(drone);
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
        Drone drone = hangar.parseDroneLine("P   ,   DJI,2021  , 12.5", 1);
        assertNotNull(drone);
    }

    @Test
    void testParseEmptyType() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine(",DJI,2021,12.5", 1);
        assertNull(drone);
    }

    @Test
    void testParseInvalidManufacturer(){
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
    void testParseNonIntYear() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,DJI,asdf,12.5", 1);
        assertNull(drone);
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
    void testParseManufacturerWithSpacesInName() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,Autel Robotics,2021,12.5", 1);
        assertNotNull(drone);
    }

    @Test
    void testAddDrone() {
        Hangar hangar = new Hangar();
        Drone drone = hangar.parseDroneLine("S,DJI,2021,12.5", 1);

        hangar.addDrone(drone);

        assertTrue(hangar.getDrones().contains(drone));
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
    void testOriginalListNotModifiedAfterYearSort() {
        // Testing to make sure the original drones array isnt changed after calling sortDronesByYear()
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 10));
        hangar.addDrone(new StandardDrone("S", "DJI", 2020, 10));

        hangar.addDrone(new PriorityDrone("P", "DJI", 2019, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2018, 10));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2017, 10));

        Drone[] sorted = hangar.sortDronesByYear();

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

        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 21.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2020, 20.0));

        hangar.addDrone(new PriorityDrone("P", "DJI", 2019, 19.0));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2018, 18.0));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2017, 17.0));

        Drone[] sorted = hangar.sortDronesByPayload();

        assertEquals(17.0, sorted[0].getPayload());
        assertEquals(18.0, sorted[1].getPayload());
        assertEquals(19.0, sorted[2].getPayload());
        assertEquals(20.0, sorted[3].getPayload());
        assertEquals(21.0, sorted[4].getPayload());
        assertEquals(22.0, sorted[5].getPayload());
    }

    @Test
    void testOriginalListNotModifiedAfterPayloadSort() {
        Hangar hangar = new Hangar();

        hangar.addDrone(new StandardDrone("S", "DJI", 2022, 22.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2021, 21.0));
        hangar.addDrone(new StandardDrone("S", "DJI", 2020, 20.0));

        hangar.addDrone(new PriorityDrone("P", "DJI", 2019, 19.0));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2018, 18.0));
        hangar.addDrone(new PriorityDrone("P", "DJI", 2017, 17.0));

        Drone[] sorted = hangar.sortDronesByPayload();

        assertEquals(22.0, hangar.getDrones().get(0).getPayload());
        assertEquals(21.0, hangar.getDrones().get(1).getPayload());
        assertEquals(20.0, hangar.getDrones().get(2).getPayload());
        assertEquals(19.0, hangar.getDrones().get(3).getPayload());
        assertEquals(18.0, hangar.getDrones().get(4).getPayload());
        assertEquals(17.0, hangar.getDrones().get(5).getPayload());
    }



}