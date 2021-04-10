package repo;

import constants.UserType;
import domain.User;
import domain.validator.UserValidator;
import exceptions.RepoException;
import repo.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UserDbRepository implements UserRepository {

    private final DBUtils dbUtils;
    private final UserValidator validator;

    public UserDbRepository(Properties properties, UserValidator validator) {
        this.dbUtils = new DBUtils(properties);
        this.validator = validator;
    }

    @Override
    public void add(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public User findOne() {
        return null;
    }

    @Override
    public User login(User user) {
        logger.traceEntry();
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement select = connection.prepareStatement("SELECT U.id, UT.type FROM Users AS U INNER JOIN UserTypes AS UT ON U.id_user_type = UT.id WHERE username = ? AND password = ? ;")) {
            select.setString(1, user.getUsername());
            select.setString(2, user.getPassword());
            try(ResultSet resultSet = select.executeQuery()) {
                if (!resultSet.next())
                    throw new RepoException("Invalid username/password!");
                user.setId(resultSet.getLong("id"));
                user.setUserType(UserType.valueOf(resultSet.getString("type").toUpperCase()));
            }
        } catch (SQLException ex) {
            logger.error(ex);
            throw new RepoException(ex.getMessage());
        }
        logger.traceExit();
        return user;
    }
}
