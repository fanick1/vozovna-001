package cz.muni.fi.pa165.vozovna;

/**
 * This class purpose is to provide testing data for different access 
 * to underlying datasources
 * 
 * @author Frantisek Veverka, 207422@mail.muni.cz
 *
 */
public abstract class TestSetup{
	
	/**
	 * Does the setup (cleans the database / fills the database with testing data)
	 * @throws Exception
	 */
	public abstract void setup() throws Exception;
}
