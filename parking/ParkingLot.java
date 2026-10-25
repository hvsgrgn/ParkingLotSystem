package parking;
import vehicle.Size;

public class ParkingLot{
    private final parking.facility.Space[][] floorPlan;
    public parking.facility.Space[][] getFloorPlan(){return floorPlan;}

    public ParkingLot(int floorNumber, int spaceNumber) throws IllegalArgumentException{
        if (floorNumber < 1 || spaceNumber < 1)
            throw new IllegalArgumentException();
        floorPlan = new parking.facility.Space[floorNumber][spaceNumber];

        for (int i = 0; i < floorNumber; i++) {
            for (int j = 0; j < spaceNumber; j++) {
                floorPlan[i][j] = new parking.facility.Space(i, j);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        

        for (int floor = floorPlan.length - 1; floor >= 0; floor--) {
            parking.facility.Space[] currentFloor = floorPlan[floor];
            
            for (int space = 0; space < currentFloor.length; space++) {
                if (currentFloor[space].isTaken()) {
                    Size carSize = currentFloor[space].getOccupyingCarSize();
                    

                    if (carSize == Size.LARGE && space > 0 && 
                        currentFloor[space-1].isTaken() && 
                        currentFloor[space-1].getOccupyingCarSize() == Size.LARGE) {
                        continue;
                    }
                    sb.append(carSize == Size.LARGE ? "L L " : "S ");
                } else {
                    sb.append("X ");
                }
            }
            sb.append("\n");
        }
        
        return sb.toString().trim();
    }   
}