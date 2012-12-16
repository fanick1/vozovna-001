package cz.muni.fi.pa165.vozovna.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import org.apache.cxf.frontend.ClientProxyFactoryBean;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import cz.muni.fi.pa165.vozovna.service.VehicleService;
import cz.muni.fi.pa165.vozovna.ws.UserWebService;
import cz.muni.fi.pa165.vozovna.ws.VehicleWebService;

/**
 * Simple ws client
 * @author Frantisek Veverka, 207422@mail.muni.cz
 *
 */
public class Client {
	private static final Logger LGR = LoggerFactory.getLogger(Client.class);

	private static final String CFG_FILE = "client.properties";

	/**
	 * Properties file keys
	 */
	private static final String WS_LOCATION = "webservice.location";
	private static final String WS_TIMEOUT = "webservice.timeout";
	private static final String WS_USER_LOCATION = "webservice.userservice.location";
	private static final String WS_VEHICLE_LOCATION = "webservice.vehicleservice.location";

	private final String serverPath;
	private final String userServicePath;
	private final String vehicleServicePath;
	private final long timeout;
	private UserWebService userWebService;
	private VehicleWebService vehicleWebService;

	public Client() {
		Configuration config = null;
		try {
			config = new PropertiesConfiguration(CFG_FILE);
		} catch (ConfigurationException e) {
			throw new IllegalStateException(
					"Cannot load the configuration file: " + CFG_FILE, e);
		}

		serverPath = config.getString(WS_LOCATION);
		if (serverPath == null) {
			throw new IllegalStateException(
					"Configuration file doesn't contain the " + WS_LOCATION
							+ " property.");
		}
		
		System.out.println("Server path: " + serverPath);
		userServicePath = config.getString(WS_USER_LOCATION);
		if (userServicePath == null) {
			throw new IllegalStateException(
					"Configuration file doesn't contain the "
							+ WS_USER_LOCATION + " property.");
		}
		System.out.println("User web service path: " + userServicePath);
		vehicleServicePath = config.getString(WS_VEHICLE_LOCATION);
		if (vehicleServicePath == null) {
			throw new IllegalStateException(
					"Configuration file doesn't contain the "
							+ WS_VEHICLE_LOCATION + " property.");
		}
		System.out.println("Vehicle web service path: " + vehicleServicePath);

		timeout = config.getLong(WS_TIMEOUT, 20000);
		System.out.println("Connection/data timeout: " + timeout + "ms");
		System.out.println("");
		init();
	}

	private void init() {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(UserWebService.class);
		factory.setAddress(serverPath + "/" + userServicePath);
		userWebService = (UserWebService) factory.create();

		factory.setServiceClass(VehicleWebService.class);
		factory.setAddress(serverPath + "/" + vehicleServicePath);
		vehicleWebService = (VehicleWebService) factory.create();
		
		org.apache.cxf.endpoint.Client client = ClientProxy.getClient(userWebService);
		if(client != null){
			HTTPConduit conduit = (HTTPConduit) client.getConduit();
			HTTPClientPolicy policy = new HTTPClientPolicy();
			policy.setConnectionTimeout(timeout);
			policy.setReceiveTimeout(timeout);
			conduit.setClient(policy);
		}
		
	}

	private void findUserById(Long id) {
		System.out.println("Finding user with id " + id);
		if(id == null){
			throw new ParamRequiredException("id");
		}
		UserDTO u = userWebService.getById(id);
		if(u != null){
			displayUser(u);
		}else{
			System.out.println("No records for user id " + id);
		}
	}


	private void createUser(UserDTO user) {
		System.out.println("Creating new user.");
		Long id = userWebService.create(user);
		if(id != null){
			System.out.println("New user created with id " + id);
		}
	}

	private void removeUser(Long id) {
		System.out.println("Removing user with id " + id);
		if(id == null){
			throw new ParamRequiredException("id");
		}
		UserDTO user = new UserDTO();
		user.setId(id);
		userWebService.remove(user);
	}

	private void updateUser(UserDTO user) {		
		System.out.println("Updating user");
		userWebService.update(user);
		System.out.println("Updated");
	}

	private void findAllUsers() {
		System.out.println("Retrieving all users.");
		List<UserDTO> user = userWebService.findAll();
		if(user == null)
		{
			System.out.println("No user records.");
		}else{
			displayUsers(user);
		}
		
	}

	private void findUserByLastName(String lastName) {
		System.out.println("Finding all users with last name: " + lastName);
		List<UserDTO> user = userWebService.findAll();
		if(user == null)
		{
			System.out.println("No user records.");
		}else{
			displayUsers(user);
		}
	}


	private void findVehicleById(Long id) {
		System.out.println("Finding vehicle with id: " + id);
		if(id == null){
			throw new ParamRequiredException("id");
		}
		VehicleDTO vehicle = vehicleWebService.getById(id);
		if(vehicle == null){
			System.out.println("No user records.");
		}else{
				displayVehicle(vehicle);
		}	
		
	}


	private void createVehicle(VehicleDTO vehicle) {
		System.out.println("Creating new vehicle.");
		Long id = vehicleWebService.create(vehicle);
		if(id != null){
			System.out.println("New vehicle created with id: " + id);
		}
	}

	private void removeVehicle(Long id) {
		System.out.println("Removing vehicle with id: " + id);
		if(id == null){
			throw new ParamRequiredException("id");
		}
		VehicleDTO vehicle = new VehicleDTO();
		vehicle.setId(id);
		vehicleWebService.remove(vehicle);
		System.out.println("Vehicle removed");
		
	}

	private void updateVehicle(VehicleDTO vehicle) {
		System.out.println("Updating vehicle with id: " + vehicle.getId() );
		vehicleWebService.update(vehicle);
		System.out.println("Vehicle updated");
	}

	private void findAllVehicles() {
		System.out.println("Retrieving all vehicles.");
		List<VehicleDTO> vehicles = vehicleWebService.findAll();
		if(vehicles == null)
		{
			System.out.println("No vehicle records.");
		}else{
			displayVehicles(vehicles);
			System.out.println("Total count:"+vehicles.size());
		}
	}

	private void displayUsers(List<UserDTO> user) {
		for(UserDTO u:user){
			displayUser(u);
		}
		System.out.println("Total count :" + user.size());
	}
	
	private void displayUser(UserDTO u) {
		System.out.println("USER:");
		System.out.println(" username  :  " + u.getUsername());
		System.out.println(" Id        :  " + u.getId());
		System.out.println(" First name:  " + u.getFirstName());
		System.out.println(" Last name :  " + u.getLastName());
		System.out.println(" Enabled   :  " + u.getEnabled());
		System.out.println(" Is admin  :  " + u.getIsAdmin());
		System.out.println(" User class:  " + u.getUserClass());
	}

	private void displayVehicles(List<VehicleDTO> vehicles) {
		for(VehicleDTO v:vehicles){
			displayVehicle(v);
		}
	}

	private void displayVehicle(VehicleDTO vehicle) {
		
		System.out.println("VEHICLE:");
		System.out.println(" Id        :  " + vehicle.getId());
		System.out.println(" Brand     :  " + vehicle.getBrand());
		System.out.println(" Engine    :  " + vehicle.getEngineType());
		System.out.println(" Type      :  " + vehicle.getType());
		System.out.println(" VIN       :  " + vehicle.getVin());
		System.out.println(" Distance  :  " + vehicle.getDistanceCount());
		System.out.println(" User class:  " + vehicle.getUserClass());
		System.out.println(" Year      :  " + vehicle.getYearMade());
	}

	@SuppressWarnings("static-access")
	public static Options generateOptions() {
		Options options = new Options();

		OptionGroup op = new OptionGroup();

		Option help = new Option("help", "print this help");

		Option findUserById = new Option("findUserById", "Search for user");
		Option createUser = new Option("createUser", "Create new user");
		Option removeUser = new Option("removeUser", "Remove a user");
		Option updateUser = new Option("updateUser", "Update user");
		Option findAllUsers = new Option("findAllUsers", "Find all users");
		Option findUserByLastName = new Option("findUserByLastName",
				"Search for user with given last name");

		Option getVehicleById = new Option("findVehicleById",
				"Search for vehicle");
		Option createVehicle = new Option("createVehicle", "Create new vehicle");
		Option removeVehicle = new Option("removeVehicle",
				"Remove existing vehicle");
		Option updateVehicle = new Option("updateVehicle", "Update vehicle");
		Option findAllVehicles = new Option("findAllVehicles",
				"Find all vehicles");

		op.addOption(help);

		op.addOption(findUserById);
		op.addOption(createUser);
		op.addOption(removeUser);
		op.addOption(updateUser);
		op.addOption(findAllUsers);
		op.addOption(findUserByLastName);

		op.addOption(getVehicleById);
		op.addOption(createVehicle);
		op.addOption(removeVehicle);
		op.addOption(updateVehicle);
		op.addOption(findAllVehicles);

		op.setRequired(true);

		Option id = OptionBuilder.withArgName("id").hasArg()
				.withDescription("Id of the user/vehicle").create("id");

		Option firstName = OptionBuilder.withArgName("name").hasArg()
				.withDescription("User's first name").create("firstName");

		Option lastName = OptionBuilder.withArgName("name").hasArg()
				.withDescription("User's last name").create("lastName");

		Option userClass = OptionBuilder
				.withArgName("[1|2|3]")
				.hasArg()
				.withDescription(
						"User's class [1|2|3]\n 1-PRESIDENT\n 2-MANAGER\n 3-EMPLOYEE")
				.create("userClass");

		Option username = OptionBuilder.withArgName("name").hasArg()
				.withDescription("Username").create("username");

		Option password = OptionBuilder.withArgName("password").hasArg()
				.withDescription("Password").create("password");

		Option disabled = new Option("disabled", "User is disabled");
		Option admin = new Option("admin", "User is admin");

		Option brand = OptionBuilder.withArgName("brand").hasArg()
				.withDescription("Brand of the vehicle").create("brand");

		Option distance = OptionBuilder.withArgName("distance").hasArg()
				.withDescription("The distance the vehicle can go")
				.create("distance");

		Option engine = OptionBuilder.withArgName("type").hasArg()
				.withDescription("The engine type of the vehicle")
				.create("engine");
		Option type = OptionBuilder.withArgName("type").hasArg()
				.withDescription("The type of the vehicle").create("type");
		Option vin = OptionBuilder.withArgName("vin number").hasArg()
				.withDescription("The VIN number of the vehicle").create("vin");
		Option year = OptionBuilder.withArgName("year").hasArg()
				.withDescription("The year of vehicle's assembly")
				.create("year");

		options.addOptionGroup(op);
		options.addOption(id);
		options.addOption(firstName);
		options.addOption(lastName);
		options.addOption(userClass);
		options.addOption(username);
		options.addOption(password);
		options.addOption(disabled);
		options.addOption(admin);
		options.addOption(brand);
		options.addOption(distance);
		options.addOption(engine);
		options.addOption(type);
		options.addOption(vin);
		options.addOption(year);

		return options;
	}



	private void parse(CommandLine line) {
		if(line.hasOption("createUser")){
			checkUserArgs(line, false);
			UserDTO user = new UserDTO();
			if(line.hasOption("disabled"))
			{
				user.setEnabled(false);			
			}
			user.setFirstName(line.getOptionValue("firstName"));
			user.setLastName(line.getOptionValue("lastName"));
			if(line.hasOption("admin"))
			{
				user.setIsAdmin(true);
			}else{
				user.setIsAdmin(false);
			}
			user.setPassword(line.getOptionValue("password"));
			user.setUserClass(getUserClass(line.getOptionValue("userClass")));
			user.setUsername(line.getOptionValue("username"));
			createUser(user);
		}
		if(line.hasOption("findAllUsers")){
			findAllUsers();
		}
		if(line.hasOption("findUserById")){
			Long id = Long.valueOf(line.getOptionValue("id"));
			findUserById(id);
		}
		if(line.hasOption("removeUser")){
			Long id = Long.valueOf(line.getOptionValue("id"));
			removeUser(id);
		}
		if(line.hasOption("updateUser")){
			checkUserArgs(line, true);
			UserDTO user = new UserDTO();
			if(line.hasOption("disabled"))
			{
				user.setEnabled(false);			
			}
			user.setFirstName(line.getOptionValue("firstName"));
			user.setLastName(line.getOptionValue("lastName"));
			if(line.hasOption("admin"))
			{
				user.setIsAdmin(true);
			}else{
				user.setIsAdmin(false);
			}
			user.setPassword(line.getOptionValue("password"));
			user.setUserClass(getUserClass(line.getOptionValue("userClass")));
			user.setUsername(line.getOptionValue("username"));
			user.setId(Long.valueOf(line.getOptionValue("id")));
			updateUser(user);
		}
		if(line.hasOption("findUserByLastName")){
			findUserByLastName(line.getOptionValue("lastName"));
		}
		if(line.hasOption("createVehicle")){
			checkVehicleArgs(line,false);
			VehicleDTO vehicle = new VehicleDTO();
			vehicle.setBrand(line.getOptionValue("brand"));
			vehicle.setDistanceCount(Integer.valueOf(line.getOptionValue("distance")));
			vehicle.setEngineType(line.getOptionValue("engine"));
			vehicle.setType(line.getOptionValue("type"));
			vehicle.setUserClass(getUserClass(line.getOptionValue("userClass")));
			vehicle.setVin(line.getOptionValue("vin"));
			vehicle.setYearMade(Integer.valueOf(line.getOptionValue("year")));
			
			createVehicle(vehicle);
		}

		if(line.hasOption("findAllVehicles")){
			findAllVehicles();
		}
		if(line.hasOption("findVehicleById")){
			findVehicleById(Long.valueOf(line.getOptionValue("id")));
		}
		if(line.hasOption("removeVehicle")){
			Long id = Long.valueOf(line.getOptionValue("id"));
			removeVehicle(id);
		}
		if(line.hasOption("updateVehicle")){
			checkVehicleArgs(line, true);
			VehicleDTO vehicle = new VehicleDTO();
			
			vehicle.setBrand(line.getOptionValue("brand"));
			vehicle.setDistanceCount(Integer.valueOf(line.getOptionValue("distance")));
			vehicle.setEngineType(line.getOptionValue("engine"));
			vehicle.setType(line.getOptionValue("type"));
			vehicle.setUserClass(getUserClass(line.getOptionValue("userClass")));
			vehicle.setVin(line.getOptionValue("vin"));
			vehicle.setYearMade(Integer.valueOf(line.getOptionValue("year")));
			vehicle.setId(Long.valueOf(line.getOptionValue("id")));
			updateVehicle(vehicle);
		}
	}

	private void checkVehicleArgs(CommandLine line, boolean checkId) {		
		List<String> params = new ArrayList<>(Arrays.asList("brand","distance","engine","type","userClass","vin","year"));
		if(checkId){
			params.add("id");
		}
		for(String p : params){
			if(!line.hasOption(p)){
				throw new ParamRequiredException(p);
			}
			
		}
	}
	private void checkUserArgs(CommandLine line, boolean checkId) {
		List<String> params = Arrays.asList("firstName","lastName","password","username","userClass");
		if(checkId){
			params.add("id");
		}
		for(String p : params){
			if(!line.hasOption(p)){
				throw new ParamRequiredException(p);
			}
		}
	}
	private UserClassEnum getUserClass(String optionValue) {
		if(optionValue == null){
			 System.err.println("User class not set. Using EMPLOYEE as default.");
			return UserClassEnum.EMPLOYEE;
		}
		
		switch(optionValue){
		case "1": return UserClassEnum.PRESIDENT;
		case "2": return UserClassEnum.MANAGER;
		case "3": return UserClassEnum.EMPLOYEE;
		default: System.err.println("Unknown user class: " + optionValue +". Using EMPLOYEE as default.");
				return UserClassEnum.EMPLOYEE;
		}
	}

	private static void displayHelp() {

		System.out
				.println( "\nusage: Client <OPERATION> <[USER PARAMETERS|VEHICLE PARAMETERS]>"
						+ "\n<OPERATION>"
						+ "\n -help                  print this help"
						+ "\n   User:"
						+ "\n -createUser            Create new user. Requires all [USER PARAMETERS] but -id"
						+ "\n -findAllUsers          Find all users"
						+ "\n -findUserById          Search for user. Requires -id"
						+ "\n -findUserByLastName    Search for user with given last name. Requires -lastName"
						+ "\n -removeUser            Remove a user. Requires -username"
						+ "\n -updateUser            Update user. Requires all [USER PARAMETERS]"
						+ "\n   Vehicle:"
						+ "\n -createVehicle         Create new vehicle. Requires all [VEHICLE PARAMETERS] but -id"
						+ "\n -findAllVehicles       Find all vehicles"
						+ "\n -findVehicleById       Search for vehicle. Requires -id"
						+ "\n -removeVehicle         Remove existing vehicle. Requires -id"
						+ "\n -updateVehicle         Update vehicle. Requires all [VEHICLE PARAMETERS]"
						+ "\n"
						+ "\n[USER PARAMETERS]:"
						+ "\n -id <id>               Id of the user/vehicle. "
						+ "\n -firstName <name>      User's first name"
						+ "\n -lastName <name>       User's last name"
						+ "\n -password <password>   Password"
						+ "\n -username <name>       Username"
						+ "\n -disabled              User is disabled"
						+ "\n -admin                 User is admin"
						+ "\n -userClass <[1|2|3]>   User's class [1|2|3]"
						+ "\n                        1-PRESIDENT"
						+ "\n                        2-MANAGER"
						+ "\n                        3-EMPLOYEE"
						+ "\n"
						+ "\n[VEHICLE PARAMETERS]:"
						+ "\n -id <id>               Id of the user/vehicle"
						+ "\n -brand <brand>         Brand of the vehicle"
						+ "\n -distance <distance>   The distance the vehicle can go"
						+ "\n -engine <type>         The engine type of the vehicle"
						+ "\n -type <type>           The type of the vehicle"
						+ "\n -userClass <[1|2|3]>   User's class [1|2|3]"
						+ "\n                        1-PRESIDENT"
						+ "\n                        2-MANAGER"
						+ "\n                        3-EMPLOYEE"
						+ "\n -vin <vin number>      The VIN number of the vehicle"
						+ "\n -year <year>           The year of vehicle's assembly");

	}
	
	public static void main(String[] args) {

		Options options = generateOptions();
		
		CommandLineParser parser = new PosixParser();
		
		CommandLine line = null;
		try {
			line = parser.parse(options, args);
		} catch (ParseException e) {
			LGR.error("Error while parsing commandline.", e);
			System.err.println("Error while parsing commandline :" + e.getMessage());
		} finally{
			if(line == null || (line != null && line.hasOption("help")))
			{
				displayHelp();
				System.exit(2);
			}
		}

		Client client = null;
		try {
			client = new Client();
		} catch (Exception e) {
			LGR.error("Initialization failed.", e);
			System.out.println("Client failed to initialize. Message: "
					+ e.getMessage());
			System.exit(1);
		}

		try{
			client.parse(line);			
		}catch(ParamRequiredException e){
			System.out.println("Missing parameter: '" + e.getMessage() +"'.");
			System.exit(3);
		}catch(Throwable t){
			LGR.error("Some unexpected problems",t);
			System.out.println("Some unexpected problems occured while processing your request." + t.getMessage());
			System.exit(4);
		}
	}

}
