package cz.muni.fi.pa165.vozovna.service.exceptions;

/**
 * DriveServiceFailureException is thrown when any unknown error occurs 
 * while communicating with the Drive Service. 
 * 
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public class DriveServiceFailureException extends RuntimeException {

    /**
     * @param message       Detail message
     */
    public DriveServiceFailureException(String message) {
        super(message);
    }
    
    /**
     * @param message       Detail message
     * @param throwable     The cause
     */
    public DriveServiceFailureException(String message, Throwable throwable) {
        super(message, throwable);
    }
  
    
}
