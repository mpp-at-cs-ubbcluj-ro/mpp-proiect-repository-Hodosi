package competition.persistence.repository.jdbc;

import competition.model.Participant;
import competition.model.ParticipantORM;
import competition.model.TestAgeCategory;
import competition.persistence.IParticipantRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ParticipantOrmRepository implements IParticipantRepository {
    private final SessionFactory sessionFactory;
    private final JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();


    public ParticipantOrmRepository(Properties properties, SessionFactory sessionFactory){
        logger.info("initializing UserDbRepository with properties: {} ", properties);
        dbUtils = new JdbcUtils(properties);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void save(Participant participant) {
//        logger.traceEntry("saving task {} ", participant);
//        Connection connection = dbUtils.getConnection();
//        String query = "INSERT into participants(name, age, username) values (?,?,?)";
//
//        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
//            preparedStatement.setString(1, participant.getName());
//            preparedStatement.setInt(2, participant.getAge());
//            preparedStatement.setString(3, participant.getUsername());
//
//            int result = preparedStatement.executeUpdate();
//            logger.trace("saved {} instances", result);
//
//        } catch (SQLException exception){
//            logger.error(exception);
//            System.err.println("Error DB " + exception);
//        }
//        logger.traceExit();

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                ParticipantORM participantORM = new ParticipantORM(participant.getUsername(), participant.getName(), participant.getAge());
                session.save(participantORM);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare ORM "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Participant entity) {

    }

    @Override
    public Participant findOne(Integer id) {
//        logger.traceEntry("finding task with id {} ", id);
//        Connection connection = dbUtils.getConnection();
//        String query = "SELECT * from participants where id_participant = ?";
//
//        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
//            preparedStatement.setInt(1, id);
//            try(ResultSet resultSet = preparedStatement.executeQuery()){
//                if(resultSet.next()) {
//                    int idParticipant = resultSet.getInt("id_participant");
//                    String username = resultSet.getString("username");
//                    String nameParticipant = resultSet.getString("name");
//                    int age = resultSet.getInt("age");
//                    Participant participant = new Participant(username, nameParticipant, age);
//                    participant.setId(idParticipant);
//                    logger.traceExit(participant);
//                    return participant;
//                }
//            }
//        } catch (SQLException exception){
//            logger.error(exception);
//            System.err.println("Error DB " + exception);
//        }
//
//        return null;

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                ParticipantORM participantORM = session.createQuery("from ParticipantORM where id_participant = :id", ParticipantORM.class)
                        .setParameter("id", id)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();

                if(participantORM == null){
                    return null;
                }

                Participant participant = new Participant(participantORM.getUsername(), participantORM.getName(), participantORM.getAge());
                participant.setId(participantORM.getId());

                return participant;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }

        return null;
    }

    @Override
    public Participant findByUsername(String username) {
//        logger.traceEntry("finding task with username {} ", username);
//        Connection connection = dbUtils.getConnection();
//        String query = "SELECT * from participants where username = ?";
//
//        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
//            preparedStatement.setString(1, username);
//            try(ResultSet resultSet = preparedStatement.executeQuery()){
//                if(resultSet.next()) {
//                    int idParticipant = resultSet.getInt("id_participant");
//                    String usernameParticipant = resultSet.getString("username");
//                    String nameParticipant = resultSet.getString("name");
//                    int age = resultSet.getInt("age");
//                    Participant participant = new Participant(usernameParticipant, nameParticipant, age);
//                    participant.setId(idParticipant);
//                    logger.traceExit(participant);
//                    return participant;
//                }
//            }
//        } catch (SQLException exception){
//            logger.error(exception);
//            System.err.println("Error DB " + exception);
//        }
//
//        return null;

        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                ParticipantORM participantORM = session.createQuery("from ParticipantORM where username = :us", ParticipantORM.class)
                        .setParameter("us", username)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();

                if(participantORM == null){
                    return null;
                }

                Participant participant = new Participant(participantORM.getUsername(), participantORM.getName(), participantORM.getAge());
                participant.setId(participantORM.getId());

                return participant;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
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
                    String username = resultSet.getString("username");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    Participant participant = new Participant(username, name, age);
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
