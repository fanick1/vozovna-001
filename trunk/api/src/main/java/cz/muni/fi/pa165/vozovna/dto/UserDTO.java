package cz.muni.fi.pa165.vozovna.dto;

import cz.muni.fi.pa165.vozovna.entity.User;
import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;

/**
 * Data Transfer Object for User
 * 
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
     * Category - important when deciding which vehicles can be reserved
     */
    private UserClassEnum userClass;

    /**
     * True if this user is administrator
     */
    private Boolean isAdmin;
    
    /**
     * If true, user can be removed from system, because has not drives in future.
     */
    private boolean canRemove;

    private String username;

    private String password;

    private Boolean enabled = Boolean.TRUE;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setCanRemove(boolean canRemove) {
        this.canRemove = canRemove;
    }
    
    public boolean getCanRemove() {
        return this.canRemove;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public UserDTO() {

    }

    public UserDTO(User user) {
        fromUser(user);
    }

    /**
     * Fills properties from user
     * 
     * @param user Original user
     */
    public final void fromUser(User user) {
        if (user == null) {
            return;
        }
        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        isAdmin = user.getIsAdmin();
        userClass = user.getUserClass();
        username = user.getUsername();
        password = user.getPassword();
        enabled = user.isEnabled();
    }

    /**
     * Returns user with same properties
     * 
     * @return User
     */
    public User toNewUser() {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserClass(userClass);
        user.setIsAdmin(isAdmin);
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(enabled);
        return user;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName + " (" + this.username + ")";
    }

    @Override
    public String toString() {
        return "UserDTO [\nid=" + id + "\nfirstName=" + firstName + "\nlastName=" + lastName + "\nuserClass=" + userClass + "\nisAdmin="
                + isAdmin + "\nusername=" + username + "\nenabled=" + enabled + "\n]";
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
        if (id == null || other.id == null) { // can't compare null ids
            return false;
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
