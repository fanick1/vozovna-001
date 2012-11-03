package cz.muni.fi.pa165.vozovna.service.exceptions;

/**
 * VehicleServiceFailureException is thrown when any unknown error occurs 
 * while communicating with the VehicleService. 
 * 
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public class VehicleServiceFailureException extends RuntimeException {

    /**
     * @param message       Detail message
     */
    public VehicleServiceFailureException(String message) {
        super(message);
    }
    
    /**
     * @param message       Detail message
     * @param throwable     The cause
     */
    public VehicleServiceFailureException(String message, Throwable throwable) {
        super(message, throwable);
    }
  
    
}
