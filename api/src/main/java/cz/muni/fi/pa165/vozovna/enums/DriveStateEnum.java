package cz.muni.fi.pa165.vozovna.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Enum pro aktuální stav jízdy.
 *
 * @author eva.neduchalova
 */
public enum DriveStateEnum {

    /**
     * Vozidlo je rezervováno pro jízdu.
     */
    RESERVED("reserved", "drive.state.reserved"),
    /**
     * Jízda právě probíhá (vozidlo je půjčeno).
     */
    ONGOING("ongoing", "drive.state.ongoing"),
    /**
     * Již proběhlá jízda.
     */
    FINISHED("finished", "drive.state.finished"),
    /**
     * Zrušená jízda.
     */
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
