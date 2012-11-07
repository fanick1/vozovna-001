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
    RESERVED("reserved"),
    /**
     * Jízda právě probíhá (vozidlo je půjčeno).
     */
    ONGOING("ongoing"),
    /**
     * Již proběhlá jízda.
     */
    FINISHED("finished"),
    /**
     * Zrušená jízda.
     */
    CANCELLED("cancelled");
    
    private String name;

    private DriveStateEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<DriveStateEnum> getAllDriveStates() {
        return Arrays.asList(values());
    }
}
