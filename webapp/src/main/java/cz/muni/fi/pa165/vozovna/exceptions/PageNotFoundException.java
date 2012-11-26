package cz.muni.fi.pa165.vozovna.exceptions;

@SuppressWarnings("serial")
public class PageNotFoundException extends Exception {

    public PageNotFoundException() {
        super();
    }

    /**
     * @param message
     * @param cause
     */
    public PageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public PageNotFoundException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public PageNotFoundException(Throwable cause) {
        super(cause);
    }
}