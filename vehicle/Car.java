package vehicle;

import vehicle.Size;

public class Car{
    private final String licensePlate;
    public String getLicensePlate(){return licensePlate;}

    private final Size spotOccupation;
    public Size getSpotOccupation(){return spotOccupation;}

    private int preferredFloor;
    public int getPreferredFloor(){return preferredFloor;}
    public void setPreferredFloor(int preferredFloor){this.preferredFloor = preferredFloor;}

    private String ticketId;
    public String getTicketId(){return ticketId;}
    public void setTicketId(String ticketId){this.ticketId = ticketId;}

    public Car(String licensePlate, vehicle.Size spotOccupation, int preferredFloor){
        this.licensePlate = licensePlate;
        this.preferredFloor = preferredFloor;
        this.spotOccupation = spotOccupation;
    }


}