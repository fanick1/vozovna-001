package cz.muni.fi.pa165.vozovna.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Enum for employee groups. Different group may reserve different vehicles.
 * 
 * @author eva.neduchalova
 */
public enum UserClassEnum {

    PRESIDENT("president", "users.class.president"),
    MANAGER("manager", "users.class.manager"),
    EMPLOYEE("employee", "users.class.employee");

    private String name;
    private String code;

    private UserClassEnum(String name, String code) {
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

    public static List<UserClassEnum> getAllUserClasses() {
        return Arrays.asList(values());
    }

    @Override
    public String toString() {
        return getName();
    }

}
