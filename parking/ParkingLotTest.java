package parking;

import vehicle.Car;
import vehicle.Size;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ParkingLotTest{
    public void testConstructorWithInvalidValues(){
        assertThrows(IllegalArgumentException.class, () -> {ParkingLot parkingLot = new ParkingLot(0,0);});
        assertThrows(IllegalArgumentException.class, () -> {ParkingLot parkingLot = new ParkingLot(-1,2);});
        assertThrows(IllegalArgumentException.class, () -> {ParkingLot parkingLot = new ParkingLot(2,-1);});
    }

    public void testTextualRepresentation(){
        ParkingLot lot = new ParkingLot(3, 5);

        lot.getFloorPlan()[0][0].addOccupyingCar(new Car("SMALL1", Size.SMALL, 0));
        lot.getFloorPlan()[0][3].addOccupyingCar(new Car("LARGE1", Size.LARGE, 0));
        lot.getFloorPlan()[1][1].addOccupyingCar(new Car("SMALL2", Size.SMALL, 0));
    
        String expected = 
        "X X X X X \n" +
        "X S X X X \n" + 
        "S X L L X";     
    
        assertEquals(expected, lot.toString());

        lot.getFloorPlan()[0][3].removeOccupyingCar();

        expected =
        "X X X X X \n" +
        "X S X X X \n" + 
        "S X X X X";

        assertEquals(expected, lot.toString());

    }
}