package domain;

import constants.UserType;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity(name = "User")
@Table(name = "Users", indexes = { @Index(name = "IDX_UserUsernamePassword", columnList = "username, password")})
public class User extends domain.Entity<Long> {

    private String username;
    private String password = null;
    private UserType userType;

    public User() {
    }

    public User(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(long id, String username, UserType userType) {
        setId(id);
        this.username = username;
        this.userType = userType;
    }

    public User(long id) {
        setId(id);
    }

    @Override
    @Id
    @GeneratedValue
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long aLong) {
        super.setId(aLong);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "username", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
