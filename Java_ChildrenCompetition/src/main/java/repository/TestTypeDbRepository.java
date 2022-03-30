package repository;

import model.TestType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class TestTypeDbRepository implements ITestTypeRepository {
    private final JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public TestTypeDbRepository(Properties properties){
        logger.info("initializing TestDbRepository with properties: {} ", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(TestType entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, TestType entity) {

    }

    @Override
    public TestType findOne(Integer id) {
        logger.traceEntry("finding task with id {} ", id);
        Connection connection = dbUtils.getConnection();
        String query = "SELECT * from test_type where id_test_type = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()) {
                    int idType = resultSet.getInt("id_test_type");
                    String type = resultSet.getString("type");
                    TestType testType = new TestType(type);
                    testType.setId(idType);
                    logger.traceExit(testType);
                    return testType;
                }
            }
        } catch (SQLException exception){
            logger.error(exception);
            System.err.println("Error DB " + exception);
        }

        return null;
    }

    @Override
    public Iterable<TestType> findAll() {
        return null;
    }
}
