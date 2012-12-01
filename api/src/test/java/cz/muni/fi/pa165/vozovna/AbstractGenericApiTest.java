package cz.muni.fi.pa165.vozovna;

import org.junit.Before;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Ancestor to all of our test cases
 * @author Frantisek Veverka, 207422@mail.muni.cz
 *
 */
public class AbstractGenericApiTest{

	private static final Logger LGR = LoggerFactory.getLogger(AbstractGenericApiTest.class);
	
	/**
	 * This guy here makes sure, we'll have the testing data prepared
	 * This is the strategy design pattern in action!
	 */
	protected TestSetup testSetupStrategy;
	
	/**
	 * Runs the setup strategy
	 * Prepares all the data in db (cleans db/inserts new data if needed)
	 */
	@Before
	public void setUpTestData() throws Exception{
		LGR.trace("Setting up the data");
		if (testSetupStrategy == null){
			throw new IllegalStateException("TestSetup strategy was not set properly.Did you forget to call setTestSetup()?");
		}
		testSetupStrategy.setup();
		LGR.trace("All Set!");
	}

	/**
	 * Sets the testSetup
	 * @param testSetup
	 */
	public void setTestSetup(TestSetup testSetup) {
		this.testSetupStrategy = testSetup;
	}

}