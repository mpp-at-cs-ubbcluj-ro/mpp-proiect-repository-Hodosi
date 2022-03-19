package repository;

import model.TestAgeCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class TestAgeCategoryDbRepository implements ITestAgeCategoryRepository<Integer, TestAgeCategory> {
    private final JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public TestAgeCategoryDbRepository(Properties properties){
        logger.info("initializing TestDbRepository with properties: {} ", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(TestAgeCategory entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, TestAgeCategory entity) {

    }

    @Override
    public TestAgeCategory findOne(Integer id) {
        logger.traceEntry("finding task with id {} ", id);
        Connection connection = dbUtils.getConnection();
        String query = "SELECT * from test_age_category where id_test_age_category = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()) {
                    int idAgeCategory = resultSet.getInt("id_test_age_category");
                    int minAge = resultSet.getInt("min_age");
                    int maxAge = resultSet.getInt("max_age");
                    TestAgeCategory testAgeCategory = new TestAgeCategory(minAge, maxAge);
                    testAgeCategory.setId(idAgeCategory);
                    logger.traceExit(testAgeCategory);
                    return testAgeCategory;
                }
            }
        } catch (SQLException exception){
            logger.error(exception);
            System.err.println("Error DB " + exception);
        }

        return null;
    }

    @Override
    public Iterable<TestAgeCategory> findAll() {
        return null;
    }
}
