package cz.muni.fi.pa165.vozovna.entity;

import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The User class represents any user present in the application
 *
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
@Entity
@Table(name = "Users")
public class User {

    /**
     * Unique users ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * Users firstName
     */
    @Column(length = 50)
    private String firstName;
    
    /**
     * Users lastName
     */
    @Column(length = 50)
    private String lastName;
    
    /**
     * VozovnaUser's category - important when deciding which vehicles can be reserved
     */
    @Column(nullable = false)
    private UserClassEnum userClass;
    
    /**
     * True if this user is administrator
     */
    @Column(nullable = false)
    private Boolean isAdmin;

    /**
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
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

    /**
     * @return the userClass
     */
    public UserClassEnum getUserClass() {
        return this.userClass;
    }

    /**
     * @param userClass the userClass to set
     */
    public void setUserClass(UserClassEnum userClass) {
        this.userClass = userClass;
    }

    /**
     * @return the isAdmin
     */
    public Boolean getIsAdmin() {
        return this.isAdmin;
    }

    /**
     * @param isAdmin the isAdmin to set
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /*
     * (non-Javadoc) @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /*
     * (non-Javadoc) @see java.lang.Object#equals(java.lang.Object)
     */
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
        User other = (User) obj;
        if (id == null || other.id == null) { //can't compare null ids
            return false;
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc) @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [id=");
        builder.append(id);
        builder.append(", firstName=");
        builder.append(firstName);
        builder.append(", lastName=");
        builder.append(lastName);
        builder.append(", userClass=");
        builder.append(userClass);
        builder.append(", isAdmin=");
        builder.append(isAdmin);
        builder.append("]");
        return builder.toString();
    }
}
