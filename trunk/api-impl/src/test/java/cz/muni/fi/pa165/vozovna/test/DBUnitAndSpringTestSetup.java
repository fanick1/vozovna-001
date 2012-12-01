package cz.muni.fi.pa165.vozovna.test;

import java.sql.Connection;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

import cz.muni.fi.pa165.vozovna.TestSetup;

/**
 * DB setup using dbunit and spring
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
public class DBUnitAndSpringTestSetup extends TestSetup{

	private static final Logger LGR = LoggerFactory.getLogger(DBUnitAndSpringTestSetup.class);

	/**
	 * Spring applicationContext
	 */
	private String testDataSetXMLPath;
	
	/**
	 * The datasource
	 */
	private DataSource dataSource;
	/**
	 * @param dataSource datasource of the database
	 * @param testDataSetXMLPath Path to test case-specific XML file with testing data (package-wise)
	 */
	public DBUnitAndSpringTestSetup(DataSource dataSource, String testDataSetXMLPath) {
		this.dataSource = dataSource;
		this.testDataSetXMLPath = testDataSetXMLPath;
	}

	
	@Override
	public void setup() throws Exception{
	        Connection conn = dataSource.getConnection();
	        try {
	            IDatabaseConnection connection = new DatabaseConnection(conn);
	            LGR.info("*** Deletes data ***");
	            
	            DatabaseOperation.DELETE_ALL.execute(connection, new XmlDataSet(ClassLoader.getSystemResourceAsStream(testDataSetXMLPath)));
	            LGR.info("*** Inserts new data ***");
	            DatabaseOperation.CLEAN_INSERT.execute(connection, new XmlDataSet(ClassLoader.getSystemResourceAsStream(testDataSetXMLPath)));
	        } finally {
	            DataSourceUtils.releaseConnection(conn, dataSource);
	            LGR.info("*** Finished inserting test data ***");
	        }
	 }
}
