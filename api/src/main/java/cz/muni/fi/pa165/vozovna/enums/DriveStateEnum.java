package cz.muni.fi.pa165.vozovna.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Enum for current drive state
 *
 * @author eva.neduchalova
 */
public enum DriveStateEnum {

    RESERVED("reserved", "drive.state.reserved"),
    ONGOING("ongoing", "drive.state.ongoing"),
    FINISHED("finished", "drive.state.finished"),
    CANCELLED("cancelled", "drive.state.cancelled");
    
    private String name;
    private String code;
    
    private DriveStateEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public static List<DriveStateEnum> getAllDriveStates() {
        return Arrays.asList(values());
    }
}
