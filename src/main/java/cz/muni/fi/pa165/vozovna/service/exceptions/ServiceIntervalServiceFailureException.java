package cz.muni.fi.pa165.vozovna.service.exceptions;

/**
 * ServiceIntervalServiceFailureException is thrown when any unknown error occurs 
 * while communicating with the ServiceIntervalService. 
 * 
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public class ServiceIntervalServiceFailureException extends RuntimeException {

    /**
     * @param message       Detail message
     */
    public ServiceIntervalServiceFailureException(String message) {
        super(message);
    }
    
    /**
     * @param message       Detail message
     * @param throwable     The cause
     */
    public ServiceIntervalServiceFailureException(String message, Throwable throwable) {
        super(message, throwable);
    }
  
    
}
