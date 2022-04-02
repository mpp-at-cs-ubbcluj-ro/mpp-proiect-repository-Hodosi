package competition.persistence.repository.jdbc;

import competition.model.Test;
import competition.model.TestAgeCategory;
import competition.model.TestType;
import competition.model.User;
import competition.persistence.IUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDbRepository implements IUserRepository {
    private User currentUser;
    private final JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public UserDbRepository(Properties properties) {
        logger.info("initializing UserDbRepository with properties: {} ", properties);
        dbUtils = new JdbcUtils(properties);
        currentUser = null;
    }

    @Override
    public void setCurrentUser(User entity) {
        this.currentUser = entity;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }


    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(User user) {
        logger.traceEntry("saving task {} ", user);
        Connection connection = dbUtils.getConnection();
        String query = "INSERT into users(firstname, lastname, username, password) values (?,?,?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());

            int result = preparedStatement.executeUpdate();
            logger.trace("saved {} instances", result);

        } catch (SQLException exception){
            logger.error(exception);
            System.err.println("Error DB " + exception);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Integer id) {
        logger.traceEntry("deleting task with {} ", id);
        Connection connection = dbUtils.getConnection();
        String query = "DELETE from users where id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();

        } catch (SQLException exception){
            logger.error(exception);
            System.err.println("Error DB " + exception);
        }
        logger.traceExit();

    }

    @Override
    public void update(Integer integer, User entity) {

    }

    @Override
    public User findOne(Integer id) {
        logger.traceEntry("finding task with id {} ", id);
        Connection connection = dbUtils.getConnection();
        String query = "SELECT * from users where id_user = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()) {
                    int idUser = resultSet.getInt("id_user");
                    String firstname = resultSet.getString("firstname");
                    String lastname = resultSet.getString("lastname");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    User user = new User(firstname, lastname, username, password);
                    user.setId(idUser);
                    logger.traceExit(user);
                    return user;
                }
            }
        } catch (SQLException exception){
            logger.error(exception);
            System.err.println("Error DB " + exception);
        }

        return null;
    }

    @Override
    public User findByUsername(String username) {
        logger.traceEntry("finding task with username {} ", username);
        Connection connection = dbUtils.getConnection();
        String query = "SELECT * from users where username = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, username);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()) {
                    int idUser = resultSet.getInt("id_user");
                    String firstname = resultSet.getString("firstname");
                    String lastname = resultSet.getString("lastname");
                    String usernameUser = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    User user = new User(firstname, lastname, usernameUser, password);
                    user.setId(idUser);
                    logger.traceExit(user);
                    return user;
                }
            }
        } catch (SQLException exception){
            logger.error(exception);
            System.err.println("Error DB " + exception);
        }

        return null;
    }

    @Override
    public Iterable<User> findAll() {
        logger.traceEntry("finding all users");
        Connection connection = dbUtils.getConnection();
        List<User> userList = new ArrayList<>();
        String query = "SELECT * from users";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    int idUser = resultSet.getInt("id_user");
                    String firstname = resultSet.getString("firstname");
                    String lastname = resultSet.getString("lastname");
                    String usernameUser = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    User user = new User(firstname, lastname, usernameUser, password);
                    user.setId(idUser);
                    userList.add(user);
                }
            }
        } catch (SQLException exception){
            logger.error(exception);
            System.err.println("Error DB " + exception);
        }
        logger.traceExit();

        return userList;
    }
}
