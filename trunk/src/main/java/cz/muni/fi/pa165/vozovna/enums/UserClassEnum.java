package cz.muni.fi.pa165.vozovna.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Enum pro třídy zařazení zaměstnanců. Jednotlivé třídy zařazení opravňují k zapůjčování různých vozidel.
 *
 * @author eva.neduchalova
 */
public enum UserClassEnum {

    /**
     * Nejvyšší třída zařazení, jen pro prezidenta firmy. Opravňuje k zapůjčení
     * všech možných vozidel, včetně limuzíny.
     */
    PRESIDENT("president"),
    /**
     * Třída zařazení pro manažerské pozice.
     */
    MANAGER("manager"),
    /**
     * Obecná zařazení pro běžné zaměstnance. Možno půjčovat pouze levná auta.
     */
    EMPLOYEE("employee");
    private String name;

    private UserClassEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<UserClassEnum> getAllUserClasses() {
        return Arrays.asList(values());
    }

    @Override
    public String toString() {
        return getName();
    }
    
}
