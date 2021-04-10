package domain;

import constants.UserType;

public class User extends Entity<Long> {

    private String username;
    private String password = null;
    private UserType userType;

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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
