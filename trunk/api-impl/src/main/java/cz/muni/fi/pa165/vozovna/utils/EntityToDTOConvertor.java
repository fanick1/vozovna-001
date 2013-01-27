package cz.muni.fi.pa165.vozovna.utils;

import cz.muni.fi.pa165.vozovna.dto.DriveDTO;
import cz.muni.fi.pa165.vozovna.dto.ServiceIntervalDTO;
import cz.muni.fi.pa165.vozovna.dto.UserDTO;
import cz.muni.fi.pa165.vozovna.dto.VehicleDTO;
import cz.muni.fi.pa165.vozovna.entity.Drive;
import cz.muni.fi.pa165.vozovna.entity.ServiceInterval;
import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.entity.Vehicle;

/**
 * Utility class for morphing entities into DTOs
 * and vice versa
 * @author Frantisek Veverka, 207422@mail.muni.cz
 *
 */
public class EntityToDTOConvertor {

	/**
	 * Converts entity to DTO
	 */
	public static DriveDTO toDTO(Drive entity){
		if(entity == null){
			throw new IllegalArgumentException("Entity can't be null.");
		}

		DriveDTO dto = new DriveDTO();
		return applyToDTO(dto, entity);
		
	}

	/**
	 * Converts DTO to entity
	 */
	public static Drive toEntity(DriveDTO dto){
		if(dto == null){
			throw new IllegalArgumentException("dto can't be null.");
		}
		Drive drive = new Drive();
		
		drive.setDateFrom(dto.getDateFrom());
		drive.setDateTo(dto.getDateTo());
		drive.setDistance(dto.getDistance());
		drive.setId(dto.getId());
		drive.setState(dto.getState());
		
		if(dto.getUser() != null){
			drive.setUser( toEntity(dto.getUser()));
		}
		
		if(dto.getVehicle() != null){
			drive.setVehicle( toEntity(dto.getVehicle()));
		}
		
		return drive;
	}

	/**
	 * Overwrites DTO with information in entity
	 */
	public static DriveDTO applyToDTO(DriveDTO dto, Drive entity){
    	if(dto == null){
    		throw new IllegalArgumentException("Drive can't be null.");
    	}
    	
    	if(entity == null){
    		throw new IllegalArgumentException("Entity can't be null.");
    	}

    	dto.setId(entity.getId());
    	dto.setDistance(entity.getDistance());
    	    	
    	if(entity.getUser() != null){    		
    		dto.setUser(toDTO(entity.getUser()));
    	}
    	
    	if(entity.getVehicle() != null){
    		dto.setVehicle(toDTO(entity.getVehicle()));
    		dto.setVehicleId(entity.getVehicle().getId());
    	}
    	
    	dto.setDateFrom(entity.getDateFrom());
    	dto.setDateTo(entity.getDateTo());
    	dto.setState(entity.getState());

    	return dto;
	}
	
	
	/**
	 * Converts entity to DTO
	 */
	public static ServiceIntervalDTO toDTO(ServiceInterval entity){
		if(entity == null){
			throw new IllegalArgumentException("Entity can't be null.");
		}

		ServiceIntervalDTO dto = new ServiceIntervalDTO();
		return applyToDTO(dto, entity);
		
	}

	/**
	 * Converts DTO to entity
	 */
	public static ServiceInterval toEntity(ServiceIntervalDTO dto){    	
		if(dto == null){
    		throw new IllegalArgumentException("dto can't be null.");
    	}
		ServiceInterval entity = new ServiceInterval();
		entity.setId(dto.getId());
		entity.setInspectionInterval(dto.getInspectionInterval());
		entity.setDated(dto.getDated());
		entity.setVehicle(toEntity(dto.getVehicle()));
		entity.setDescription(dto.getDescription());
		return entity;
	}

	/**
	 * Overwrites DTO with information in entity
	 */
	public static ServiceIntervalDTO applyToDTO(ServiceIntervalDTO dto, ServiceInterval entity){
		if(entity == null){
			throw new IllegalArgumentException("Entity can't be null.");
		}

		if(dto == null){
    		throw new IllegalArgumentException("dto can't be null.");
    	}
    	dto.setId(entity.getId());
    	dto.setInspectionInterval(entity.getInspectionInterval());
    	dto.setDated(entity.getDated());
    	dto.setDescription(entity.getDescription());
    	
    	if(entity.getVehicle() != null){
    		dto.setVehicle(toDTO(entity.getVehicle()));
    	}
    	
    	return dto;
    }

	/**
	 * Converts entity to DTO
	 */
	public static UserDTO toDTO(User entity){
		if(entity == null){
			throw new IllegalArgumentException("Entity can't be null.");
		}

		UserDTO dto = new UserDTO();
		return applyToDTO(dto, entity);		
	}

	/**
	 * Converts DTO to entity
	 */
	public static User toEntity(UserDTO dto){
    	if(dto == null){
    		throw new IllegalArgumentException("dto can't be null.");
    	}
    	User entity = new User();
    	entity.setId(dto.getId());
    	entity.setFirstName(dto.getFirstName());
    	entity.setLastName(dto.getLastName());
    	entity.setUserClass(dto.getUserClass());
    	entity.setIsAdmin(dto.getIsAdmin());
    	entity.setUsername(dto.getUsername());
    	entity.setPassword(dto.getPassword());
    	entity.setEnabled(dto.getEnabled());
    	
    	return entity;
	}

	/**
	 * Overwrites DTO with information in entity
	 */
	public static UserDTO applyToDTO(UserDTO dto, User entity){
		if(entity == null){
			throw new IllegalArgumentException("Entity can't be null.");
		}

		if(dto == null){
			throw new IllegalArgumentException("dto can't be null.");
		}
		
		dto.setId(entity.getId());
		dto.setFirstName(entity.getFirstName());
		dto.setLastName(entity.getLastName());
		dto.setIsAdmin(entity.getIsAdmin());
		dto.setUserClass(entity.getUserClass());
		dto.setUsername(entity.getUsername());
		dto.setPassword(entity.getPassword());
		dto.setEnabled(entity.isEnabled());
		
		return dto;
	}

	/**
	 * Converts entity to DTO
	 */
	public static VehicleDTO toDTO(Vehicle entity){
		if(entity == null){
			throw new IllegalArgumentException("Entity can't be null.");
		}

		VehicleDTO dto = new VehicleDTO();
		return applyToDTO(dto, entity);
		
	}

	/**
	 * Converts DTO to entity
	 */
	public static Vehicle toEntity(VehicleDTO dto){
		Vehicle entity = new Vehicle();
    	if(dto == null){
    		throw new IllegalArgumentException("dto can't be null.");
    	}
    	entity.setId(dto.getId());
    	entity.setBrand(dto.getBrand());
    	entity.setDistanceCount(dto.getDistanceCount());
    	entity.setEngineType(dto.getEngineType());
    	entity.setType(dto.getType());
    	entity.setVin(dto.getVin());
    	entity.setYearMade(dto.getYearMade());
    	entity.setUserClass(dto.getUserClass());
    	entity.setRegistrationPlate(dto.getRegistrationPlate());
    	return entity;

	}

	/**
	 * Overwrites DTO with information in entity
	 */
	public static VehicleDTO applyToDTO(VehicleDTO dto, Vehicle entity){
		if(entity == null){
			throw new IllegalArgumentException("Entity can't be null.");
		}
		if(dto == null){
			throw new IllegalArgumentException("dto can't be null.");
		}

		dto.setId(entity.getId());
		dto.setBrand(entity.getBrand());
		dto.setDistanceCount(entity.getDistanceCount());
		dto.setEngineType(entity.getEngineType());
		dto.setType(entity.getType());
		dto.setVin(entity.getVin());
		dto.setYearMade(entity.getYearMade());
		dto.setUserClass(entity.getUserClass());
		dto.setRegistrationPlate(entity.getRegistrationPlate());
		
		return dto;

	}

	
}
