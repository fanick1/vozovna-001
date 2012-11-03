package cz.muni.fi.pa165.vozovna.service.exceptions;

/**
 * UserServiceFailureException is thrown when any unknown error occurs 
 * while communicating with the UserService. 
 * 
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public class UserServiceFailureException extends RuntimeException {

    /**
     * @param message       Detail message
     */
    public UserServiceFailureException(String message) {
        super(message);
    }
    
    /**
     * @param message       Detail message
     * @param throwable     The cause
     */
    public UserServiceFailureException(String message, Throwable throwable) {
        super(message, throwable);
    }
  
    
}
