package competition.persistence.repository.jdbc;

import model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class TestParticipantRelationDbRepository implements ITestParticipantRelationRepository {
    private final JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public TestParticipantRelationDbRepository(Properties properties){
        logger.info("initializing TestDbRepository with properties: {} ", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(TestParticipantRelation entity) {
        logger.traceEntry("saving task {} ", entity);
        Connection connection = dbUtils.getConnection();
        String query = "INSERT into test_participant_relation(id_test, id_participant) values (?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, entity.getId().getLeft());
            preparedStatement.setInt(2, entity.getId().getRight());

            int result = preparedStatement.executeUpdate();
            logger.trace("saved {} instances", result);

        } catch (SQLException exception){
            logger.error(exception);
            System.err.println("Error DB " + exception);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Tuple<Integer, Integer> integerIntegerTuple) {

    }

    @Override
    public void update(Tuple<Integer, Integer> integerIntegerTuple, TestParticipantRelation entity) {

    }

    @Override
    public TestParticipantRelation findOne(Tuple<Integer, Integer> integerIntegerTuple) {
        return null;
    }

    @Override
    public Iterable<TestParticipantRelation> findAll() {
        return null;
    }
}
