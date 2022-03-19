package repository;

import model.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantDbRepository implements IParticipantRepository<Integer, Participant> {
    private final JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public ParticipantDbRepository(Properties properties){
        logger.info("initializing UserDbRepository with properties: {} ", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(Participant participant) {
        logger.traceEntry("saving task {} ", participant);
        Connection connection = dbUtils.getConnection();
        String query = "INSERT into participants(name, age) values (?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, participant.getName());
            preparedStatement.setInt(2, participant.getAge());

            int result = preparedStatement.executeUpdate();
            logger.trace("saved {} instances", result);

        } catch (SQLException exception){
            logger.error(exception);
            System.err.println("Error DB " + exception);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Participant entity) {

    }

    @Override
    public Participant findOne(Integer id) {
        logger.traceEntry("finding task with id {} ", id);
        Connection connection = dbUtils.getConnection();
        String query = "SELECT * from participants where id_participant = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()) {
                    int idParticipant = resultSet.getInt("id_participant");
                    String nameParticipant = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    Participant participant = new Participant(nameParticipant, age);
                    participant.setId(idParticipant);
                    logger.traceExit(participant);
                    return participant;
                }
            }
        } catch (SQLException exception){
            logger.error(exception);
            System.err.println("Error DB " + exception);
        }

        return null;
    }

    @Override
    public Participant findByName(String name) {
        logger.traceEntry("finding task with name {} ", name);
        Connection connection = dbUtils.getConnection();
        String query = "SELECT * from participants where name = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, name);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()) {
                    int idParticipant = resultSet.getInt("id_participant");
                    String nameParticipant = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    Participant participant = new Participant(nameParticipant, age);
                    participant.setId(idParticipant);
                    logger.traceExit(participant);
                    return participant;
                }
            }
        } catch (SQLException exception){
            logger.error(exception);
            System.err.println("Error DB " + exception);
        }

        return null;
    }

    @Override
    public Iterable<Participant> findAll() {
        return null;
    }

    @Override
    public Iterable<Participant> findAllParticipantsForTest(int integer) {
        logger.traceEntry("finding all participants for a test");
        Connection connection = dbUtils.getConnection();
        List<Participant> partcipantList = new ArrayList<>();
        String query = "SELECT * from test_participant_relation TPR INNER JOIN participants P ON TPR.id_participant = P.id_participant where id_test = ? ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, integer);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    int idParticipant = resultSet.getInt("id_participant");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    Participant participant = new Participant(name, age);
                    participant.setId(idParticipant);
                    partcipantList.add(participant);
                }
            }
        } catch (SQLException exception){
            logger.error(exception);
            System.err.println("Error DB " + exception);
        }
        logger.traceExit();

        return partcipantList;
    }
}
