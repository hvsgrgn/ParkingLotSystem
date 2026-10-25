package parking.facility;
import java.util.ArrayList;
import vehicle.Size;
import parking.facility.CarNotRegisteredError;
import vehicle.Car;
import parking.facility.Space;
import parking.ParkingLot;

public class Gate{
    private final ArrayList<Car> cars = new ArrayList<>();
    
    private final ParkingLot parkingLot;

    public Gate(ParkingLot parkingLot){
        this.parkingLot = parkingLot;
    }

    private Space findTakenSpaceByCar(Car c){
        Space[][] floorplan = parkingLot.getFloorPlan();
        for(Space[] floor : floorplan){
            for(Space space : floor){
                if(space.isTaken() && c.getTicketId().equals(space.getCarLicensePlate()))
                    return space;
            }
        }
        return null;
    }

    private Space findAvailableSpaceOnFloor(int floor, Car c){
        Space[][] floorplan = parkingLot.getFloorPlan();
        
        if(c.getSpotOccupation() == Size.SMALL)
        {
            for(Space space : floorplan[floor]){
                if (!space.isTaken())
                    return space;
            }
        }
        else{
            for(int i = 1; i < floorplan[floor].length; i++){
                System.out.println("Car is: " + c.getLicensePlate() +". Index of FIRST place IS " + i +", index of SECOND PLACE IS " + (i + 1));
                if (!floorplan[floor][i].isTaken() && !floorplan[floor][i-1].isTaken())
                    return floorplan[floor][i];
            }
        }
        return null;
    }

    public Space findAnyAvailableSpaceForCar(Car c){
        for(int i = 0; i < parkingLot.getFloorPlan().length; i++){
            Space temp = findAvailableSpaceOnFloor(i, c);
            if(temp != null)
                return temp;
        }
        return null;
    }

    public Space findPreferredAvailableSpaceForCar(Car c){
        int pref = c.getPreferredFloor();
        Space current = findAvailableSpaceOnFloor(pref, c);
        if(current != null)
            return current;

        int bot = pref - 1;
        int top = pref + 1;
        int h = parkingLot.getFloorPlan().length;

        while(bot >= 0 || top < h)
        {
            if(bot >=0){
                current = findAvailableSpaceOnFloor(bot, c);
                if(current != null)
                    return current;
                bot -= 1;
            }
            
            if(top < h){
                current = findAvailableSpaceOnFloor(top, c);
                if(current != null)
                    return current;
                top += 1;
            }
            
        }
        return null;
    }

    public boolean registerCar(Car c){
        Space ret =  findPreferredAvailableSpaceForCar(c);
        if(ret != null){
            cars.add(c);
            ret.addOccupyingCar(c);
            c.setTicketId(c.getLicensePlate());
            if(c.getSpotOccupation() == Size.LARGE)
                parkingLot.getFloorPlan()[ret.getFloorNumber()][ret.getSpaceNumber()-1].addOccupyingCar(c);
            return true;
        }
        return false;
    }

    public void registerCars(Car... cars){
        for (Car car : cars){
            if(!registerCar(car))
                System.err.println("Couldn't find a viable space for this car!");                
        }
    }

    public void deRegisterCar(String ticketId) throws CarNotRegisteredError{
        Car wanted = null;
        for(Car car : cars){
            if(car.getTicketId().equals(ticketId))
            {
                wanted = car;
                break;
            }
        }
        if(wanted == null)
            throw new CarNotRegisteredError("The car was not registered at the gate");
        Space space = findTakenSpaceByCar(wanted);
        if(space != null)
        {
            cars.remove(wanted);
            parkingLot.getFloorPlan()[space.getFloorNumber()][space.getSpaceNumber()].removeOccupyingCar();
            if(wanted.getSpotOccupation() == Size.LARGE)
                parkingLot.getFloorPlan()[space.getFloorNumber()][space.getSpaceNumber()+1].removeOccupyingCar();
        }
        else
            throw new CarNotRegisteredError("The car was registered at the gate, but not given a space ~somehow~");
    }
}