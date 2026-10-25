package parking.facility;

public class Space{
    private final int floorNumber;
    public int getFloorNumber(){return floorNumber;}

    private final int spaceNumber;
    public int getSpaceNumber(){return spaceNumber;}

    private vehicle.Car occupyingCar;

    public Space(int floorNumber, int spaceNumber){
        this.floorNumber = floorNumber;
        this.spaceNumber = spaceNumber;
        occupyingCar = null;
    }

    public boolean isTaken()
    {
        return occupyingCar != null;
    }

    public void addOccupyingCar(vehicle.Car c){
        occupyingCar = c;
    }

    public void removeOccupyingCar(){
        occupyingCar = null;
    }

    public String getCarLicensePlate(){
        return occupyingCar.getLicensePlate();
    }

    public vehicle.Size getOccupyingCarSize(){
        return occupyingCar.getSpotOccupation();
    }
}