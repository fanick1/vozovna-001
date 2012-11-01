package cz.muni.fi.pa165.vozovna.dto;

import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;

/**
 * Data Transfer Object for  User
 * @author Lukas Hajek <359617@mail.muni.cz>
 */
public class UserDTO implements java.io.Serializable {
    
    /**
     * Unique users ID
     */
    private Long id;
    
    /**
     * Users firstName
     */
    private String firstName;
    
    /**
     * Users lastName
     */
    private String lastName;
    
    /**
     * VozovnaUser's category - important when deciding which vehicles can be reserved
     */
    private UserClassEnum userClass;
    
    /**
     * True if this user is administrator
     */
    private Boolean isAdmin;

    
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserClassEnum getUserClass() {
        return this.userClass;
    }

    public void setUserClass(UserClassEnum userClass) {
        this.userClass = userClass;
    }

    public Boolean getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    
    /**
     * Fills properties from user
     * @param user Pattern user
     */
    public void fromUser(User user) {
        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        isAdmin = user.getIsAdmin();
        userClass = user.getUserClass();
    }
    
    /**
     * Returns user with same properties
     * @return User
     */
    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserClass(userClass);
        user.setIsAdmin(isAdmin);
        
        return user;
    }
    
   
    
    @Override
    public String toString() {
        return "UserDTO{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", userClass=" + userClass + ", isAdmin=" + isAdmin + '}';
    }
 
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
  
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UserDTO other = (UserDTO) obj;
        if (id == null || other.id == null) { //can't compare null ids
            return false;
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
    
    
}
