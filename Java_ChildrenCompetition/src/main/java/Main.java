import model.*;
import repository.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args){
        System.out.println("Hello");

        Properties properties = new Properties();
        try{
            properties.load(new FileReader("db.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }

//        IUserRepository<Integer, User> userRepository = new UserDbRepository(properties);
//        User testUser = new User("testFirstname", "testLastname", "testUsername", "testPassword");
//        userRepository.save(testUser);

//        ITestRepository<Integer, Test> testRepository = new TestDbRepository(properties);
//        List<Test> allTest = (List<Test>) testRepository.findAll();
//        for(Test test : allTest){
//            System.out.println(test.getType().getType());
//            System.out.println(test.getCategory().getMinAge());
//            System.out.println(test.getCategory().getMaxAge());
//        }

        IParticipantRepository<Integer, Participant> participantRepository = new ParticipantDbRepository(properties);
//        participantRepository.save(new Participant("test1", 6));
//        participantRepository.save(new Participant("test2", 9));
//        participantRepository.save(new Participant("test3", 12));
//        participantRepository.save(new Participant("test4", 7));
//        participantRepository.save(new Participant("test5", 10));
//        participantRepository.save(new Participant("test6", 13));
//        participantRepository.save(new Participant("test7", 8));
//        participantRepository.save(new Participant("test8", 11));
//        participantRepository.save(new Participant("test9", 14));
//        participantRepository.save(new Participant("test10", 15));

        ITestParticipantRelationRepository<Tuple<Integer, Integer>, TestParticipantRelation> testParticipantRelationRepository = new TestParticipantRelationDbRepository(properties);

//        testParticipantRelationRepository.save(new TestParticipantRelation(new Tuple<>(1, 1)));
//        testParticipantRelationRepository.save(new TestParticipantRelation(new Tuple<>(2, 2)));
//        testParticipantRelationRepository.save(new TestParticipantRelation(new Tuple<>(3, 3)));
//        testParticipantRelationRepository.save(new TestParticipantRelation(new Tuple<>(4, 4)));
//        testParticipantRelationRepository.save(new TestParticipantRelation(new Tuple<>(5, 5)));
//        testParticipantRelationRepository.save(new TestParticipantRelation(new Tuple<>(6, 6)));
//        testParticipantRelationRepository.save(new TestParticipantRelation(new Tuple<>(7, 7)));
//        testParticipantRelationRepository.save(new TestParticipantRelation(new Tuple<>(8, 8)));
//        testParticipantRelationRepository.save(new TestParticipantRelation(new Tuple<>(9, 9)));
//        testParticipantRelationRepository.save(new TestParticipantRelation(new Tuple<>(9, 10)));

        ITestRepository<Integer, Test> testRepository = new TestDbRepository(properties);
        List<Test> allTest = (List<Test>) testRepository.findAll();
        for(Test test : allTest){
            System.out.println(test.getType());
            System.out.println(String.valueOf(test.getCategory().getMinAge()) + " " + String.valueOf(test.getCategory().getMaxAge()));
            List<Integer> participantsID = (List<Integer>) testParticipantRelationRepository.findAllParticipantsIDForTest(test.getId());
            System.out.println(participantsID.size());
            Participant participant = participantRepository.findOne(participantsID.get(0));
            System.out.println(participant.getAge());
        }
    }
}
