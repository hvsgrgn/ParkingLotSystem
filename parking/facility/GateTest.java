package parking.facility;

import vehicle.Car;
import vehicle.Size;
import parking.ParkingLot;
import static vehicle.Size.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class GateTest{

    private ParkingLot parkingLot;
    private Gate gate;
    @BeforeEach
    public void setUp(){
        this.parkingLot = new ParkingLot(6,5);
        this.gate = new Gate(parkingLot);
    }

    @Test
    public void testFindAnyAvailableSpaceForCar() throws parking.facility.CarNotRegisteredError{
        Car bigCar = new Car("ABC-123", LARGE, 0);
        Space result = gate.findAnyAvailableSpaceForCar(bigCar);
        assertNotNull(result, "\nThere should have been an empty spot in an empty parking lot!\n");
        assertEquals(0, result.getFloorNumber(), "\nResult should have been on ground floor!\n");
        assertEquals(1, result.getSpaceNumber(), "\nThe spot should have been the first on the floor!\n");
        gate.registerCars(bigCar);
        System.out.println(parkingLot);
        assertTrue(parkingLot.getFloorPlan()[0][0].isTaken(), "Because of the large car, the spot before should also have been taken!");

        Car smallCar = new Car("DEF-456", SMALL, 3);
        result = gate.findAnyAvailableSpaceForCar(smallCar);
        assertNotNull(result, "\nThere should have been an empty spot in an almost empty parking lot!\n");
        assertEquals(0, result.getFloorNumber(), "\nResult should have been on the ground floor!\n");
        assertEquals(2, result.getSpaceNumber(), "\nThe spot should have been the third on the floor!\n");
    
        Car smallCar2 = new Car("ABC-456", SMALL, 0); 
        fillFloor(0);
        result = gate.findAnyAvailableSpaceForCar(smallCar);
        assertNotNull(result, "\nThere should have been an empty spot in an almost empty parking lot!\n");
        assertEquals(1, result.getFloorNumber(), "\nResult should have been on the first floor!\n");
        assertEquals(0, result.getSpaceNumber(), "\nThe spot should have been the first on the floor!\n");


        fillAllSpaces();
        Car noSpaceCar = new Car("GHI-789", SMALL, 0);
        assertNull(gate.findAnyAvailableSpaceForCar(noSpaceCar), "There should not have been any spaces left for this car!");

    }



    @ParameterizedTest
    @CsvSource({
                    "'ABC-123', LARGE, 2",
                    "'DEF-456', SMALL, 3",
                    "'GHI-789', SMALL, 0",
            })
    public void testFindPreferredAvailableSpaceForCar(String plate, Size size, int preferredFloor) throws parking.facility.CarNotRegisteredError{
        Car car = new Car(plate, size, preferredFloor);
        Space result = gate.findPreferredAvailableSpaceForCar(car);
        assertNotNull(result, "\nThere should have been an empty spot in an empty parking lot!\n");
        int floor = result.getFloorNumber();
        int spaceNumber = result.getSpaceNumber();
        Space[][] floorPlan = parkingLot.getFloorPlan();
        assertEquals(floor, preferredFloor, "\nResult should have been on floor " + preferredFloor + ", but instead it was on floor " + floor + "!\n");
        if (size == LARGE){    
            assertEquals(spaceNumber, 1, "\nResult should have been space number 1, but instead it was space number " + spaceNumber + "!\n");
            if(preferredFloor > 0)
            {
                fillFloor(preferredFloor);
                result = gate.findPreferredAvailableSpaceForCar(car);
                assertNotNull(result, "\nThere should have been an empty spot in an almost empty parking lot!\n");
                floor = result.getFloorNumber();
                spaceNumber = result.getSpaceNumber();
                assertEquals(floor, preferredFloor - 1, "\nResult should have been on floor " + (preferredFloor - 1) + ", but instead it was on floor " + floor + "!\n");
                assertEquals(spaceNumber, 1, "\nResult should have been space number 1, but instead it was space number " + spaceNumber + "!\n");
            }
            
        }
        else{
            assertEquals(spaceNumber, 0, "\nResult should have been space number 0, but instead it was space number " + spaceNumber + "!\n");
            fillAllSpaces();
            result = gate.findPreferredAvailableSpaceForCar(car);
            assertNull(result, "\nThe parkinglot was full, the gate should not have registered the car!\n");
        }
    }

    @ParameterizedTest
    @CsvSource({
                    "'ABC-123', LARGE, 2",
                    "'DEF-456', SMALL, 3",
                    "'GHI-789', SMALL, 0",
            })
    public void testRegisterCar(String plate, Size size, int preferredFloor) throws parking.facility.CarNotRegisteredError{
        Car car = new Car(plate, size, preferredFloor);
        assertTrue(gate.registerCar(car));
        gate.deRegisterCar(car.getTicketId());
        fillAllSpaces();
        assertFalse(gate.registerCar(car));  
    }
    
    @ParameterizedTest
    @CsvSource({
                    "'ABC-123', LARGE, 2",
                    "'DEF-456', SMALL, 3",
                    "'GHI-789', SMALL, 0",
            })
    public void testDeRegisterCar(String plate, Size size, int preferredFloor) throws parking.facility.CarNotRegisteredError{
        Car car = new Car(plate, size, preferredFloor);
        car.setTicketId(car.getLicensePlate());
        assertThrows(CarNotRegisteredError.class, () -> {gate.deRegisterCar(car.getTicketId());});
        gate.registerCar(car);
        assertDoesNotThrow(() -> {gate.deRegisterCar(car.getTicketId());});
    }

    private void fillAllSpaces() throws parking.facility.CarNotRegisteredError{
        for (int i = 0; i < parkingLot.getFloorPlan().length; i++) {
            fillFloor(i);
        }
    }

    private void fillFloor(int floor) throws parking.facility.CarNotRegisteredError{
        Space[][] floorPlan = parkingLot.getFloorPlan();
        for (int i = 0; i < floorPlan[floor].length; i++) {
            Car car = new Car("FILLER" + floor + "-" + i, Size.SMALL, floor);
            parkingLot.getFloorPlan()[floor][i].addOccupyingCar(car);
        }
    }
}