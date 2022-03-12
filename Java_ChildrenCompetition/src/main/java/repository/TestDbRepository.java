package repository;

import model.Test;
import model.TestAgeCategory;
import model.TestType;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestDbRepository implements ITestRepository<Integer, Test>{
    private final JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public TestDbRepository(Properties properties){
        logger.info("initializing TestDbRepository with properties: {} ", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(Test entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Test entity) {

    }

    public TestType findOneTestType(Integer id){
        logger.traceEntry("finding task with id {} ", id);
        Connection connection = dbUtils.getConnection();
        String query = "SELECT * from test_type where id_test_type = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                int idType = resultSet.getInt("id_test_type");
                String type = resultSet.getString("type");
                TestType testType = new TestType(type);
                testType.setId(idType);
                logger.traceExit(testType);
                return testType;
            }
        } catch (SQLException exception){
            logger.error(exception);
            System.err.println("Error DB " + exception);
        }

        return null;
    }

    public TestAgeCategory findOneTestAgeCategory(Integer id){
        logger.traceEntry("finding task with id {} ", id);
        Connection connection = dbUtils.getConnection();
        String query = "SELECT * from test_age_category where id_test_age_category = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                int idAgeCategory = resultSet.getInt("id_test_age_category");
                int minAge = resultSet.getInt("min_age");
                int maxAge = resultSet.getInt("max_age");
                TestAgeCategory testAgeCategory = new TestAgeCategory(minAge, maxAge);
                testAgeCategory.setId(idAgeCategory);
                logger.traceExit(testAgeCategory);
                return testAgeCategory;
            }
        } catch (SQLException exception){
            logger.error(exception);
            System.err.println("Error DB " + exception);
        }

        return null;
    }

    @Override
    public Test findOne(Integer id) {
        logger.traceEntry("finding task with id {} ", id);
        Connection connection = dbUtils.getConnection();
        String query = "SELECT * from tests where id_test = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                int idTest = resultSet.getInt("id_test");
                int idTestType = resultSet.getInt("id_test_type");
                int idAgeCategory = resultSet.getInt("id_test_age_category");
                TestType testType = findOneTestType(idTestType);
                TestAgeCategory testAgeCategory = findOneTestAgeCategory(idAgeCategory);
                Test test = new Test(testType, testAgeCategory);
                test.setId(idTest);
                logger.traceExit(test);
                return test;
            }
        } catch (SQLException exception){
            logger.error(exception);
            System.err.println("Error DB " + exception);
        }

        return null;
    }

    @Override
    public Iterable<Test> findAll() {
        logger.traceEntry("finding all task");
        Connection connection = dbUtils.getConnection();
        List<Test> testList = new ArrayList<>();
        String query = "SELECT * from tests";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    int idTest = resultSet.getInt("id_test");
                    int idTestType = resultSet.getInt("id_test_type");
                    int idAgeCategory = resultSet.getInt("id_test_age_category");
                    TestType testType = findOneTestType(idTestType);
                    TestAgeCategory testAgeCategory = findOneTestAgeCategory(idAgeCategory);
                    Test test = new Test(testType, testAgeCategory);
                    test.setId(idTest);
                    testList.add(test);
                }
            }
        } catch (SQLException exception){
            logger.error(exception);
            System.err.println("Error DB " + exception);
        }
        logger.traceExit();

        return testList;
    }
}
