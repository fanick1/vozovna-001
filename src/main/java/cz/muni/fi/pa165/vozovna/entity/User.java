package cz.muni.fi.pa165.vozovna.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import cz.muni.fi.pa165.vozovna.enums.UserClassEnum;

/**
 * The User class represents any user present in the application
 * 
 * @author Frantisek Veverka, 207422@mail.muni.cz
 */
@Entity
@Table(name = "Users")
public class User implements org.springframework.security.core.userdetails.UserDetails {

    @Transient
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    @Transient
    public static final String ROLE_USER = "ROLE_USER";

    /**
     * Unique users ID
     */
    @Id
    @GeneratedValue(generator = "user_id_sequence")
    @GenericGenerator(name = "user_id_sequence", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
            @Parameter(name = "sequence_name", value = "user_id_sequence"), @Parameter(name = "initial_value", value = "1"),
            @Parameter(name = "increment_size", value = "1") })
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
     * User class - important when deciding which vehicles can be reserved
     */
    @Column(nullable = false)
    private UserClassEnum userClass;

    /**
     * True if this user is administrator
     */
    @Column(nullable = false)
    private Boolean isAdmin;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enabled;

    public User() {
        super();
        this.enabled = Boolean.TRUE;
        this.isAdmin = Boolean.FALSE;
    }

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
        if (id == null || other.id == null) { // can't compare null ids
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>(1);
        if (Boolean.TRUE.equals(isAdmin)) {
            roles.add(new SimpleGrantedAuthority(ROLE_ADMIN));
        } else {
            roles.add(new SimpleGrantedAuthority(ROLE_USER));
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        if (enabled == null) {
            return false;
        }
        return enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        if (enabled == null) {
            return false;
        }
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (enabled == null) {
            return false;
        }
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        if (enabled == null) {
            return false;
        }
        return enabled;
    }

}
