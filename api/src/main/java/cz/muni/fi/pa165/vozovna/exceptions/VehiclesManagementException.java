package cz.muni.fi.pa165.vozovna.exceptions;

/**
 * Base exception for exceptions in Vehicles Management project
 *
 * @author eva.neduchalova
 *
 */
public class VehiclesManagementException extends Exception {

    public VehiclesManagementException() {
        super();
    }

    public VehiclesManagementException(String message, Throwable cause) {
        super(message, cause);
    }

    public VehiclesManagementException(String message) {
        super(message);
    }

    public VehiclesManagementException(Throwable cause) {
        super(cause);
    }
}