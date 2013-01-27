package cz.muni.fi.pa165.vozovna.service;

/**
 * Class for generating test data. For development and test purpose only, not to be used in production.
 * 
 * @author Eva Neduchalová, učo 359893
 */
public interface DevelopementDataGenerator {

    /**
     * Generates test data.
     */
    public void generateTestDataIfNoneExist();

}
