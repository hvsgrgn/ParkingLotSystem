package parking.facility;

public class CarNotRegisteredError extends Exception {
    public CarNotRegisteredError(String message) {
        super(message);
    }
}