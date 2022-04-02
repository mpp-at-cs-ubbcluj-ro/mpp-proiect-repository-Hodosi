package competition.server;

import competition.model.*;
import competition.network.rpcprotocol.Request;
import competition.network.rpcprotocol.RequestType;
import competition.network.rpcprotocol.Response;
import competition.network.rpcprotocol.ResponseType;
import competition.persistence.IParticipantRepository;
import competition.persistence.ITestParticipantRelationRepository;
import competition.persistence.ITestRepository;
import competition.persistence.IUserRepository;
import competition.persistence.repository.jdbc.UserDbRepository;
import competition.services.CompetitionException;
import competition.services.ICompetitionObserver;
import competition.services.ICompetitionServices;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompetitionServicesImplementation implements ICompetitionServices {
    private IUserRepository userRepository;
    private IParticipantRepository participantRepository;
    private ITestRepository testRepository;
    private ITestParticipantRelationRepository testParticipantRelationRepository;
    private Map<String, ICompetitionObserver> loggedClients;

    public CompetitionServicesImplementation(IUserRepository userRepository, IParticipantRepository participantRepository, ITestRepository testRepository, ITestParticipantRelationRepository testParticipantRelationRepository) {
        this.userRepository = userRepository;
        this.participantRepository = participantRepository;
        this.testRepository = testRepository;
        this.testParticipantRelationRepository = testParticipantRelationRepository;
        loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(User user, ICompetitionObserver client) throws CompetitionException {
        User crtUser = userRepository.findByUsername(user.getUsername());
        if(crtUser != null) {
            if(loggedClients.get(crtUser.getUsername()) != null){
                throw new CompetitionException("User already logged in.");
            }
            if(!crtUser.getPassword().equals(user.getPassword())){
                throw new CompetitionException("Wrong password.");
            }
            loggedClients.put(crtUser.getUsername(), client);
            System.out.println("Synch login");
            notifyUsersLoggedIn(crtUser);
        }
        else {
            throw new CompetitionException("Authentication failed.");
        }
    }

    private final int defaultThreadsNo=5;
    private void notifyUsersLoggedIn(User user) throws CompetitionException {
        Iterable<User> users=userRepository.findAll();
        System.out.println("Logged "+ users);

        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for(User us : users){
            ICompetitionObserver comppetitionClient=loggedClients.get(us.getUsername());
            if (comppetitionClient!=null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying [" + us.getId()+ "] friend ["+user.getId()+"] logged in.");
                        comppetitionClient.userLoggedIn(user);
                    } catch (CompetitionException e) {
                        System.err.println("Error notifying friend " + e);
                    }
                });
        }

        executor.shutdown();
    }

    private void notifyUsersLoggedOut(User user) throws CompetitionException {
        Iterable<User> users=userRepository.findAll();
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);


        for(User us :users){
            ICompetitionObserver competitionClient=loggedClients.get(us.getUsername());
            if (competitionClient!=null)
                executor.execute(() -> {
                    try {
                        System.out.println("Notifying ["+us.getId()+"] friend ["+user.getId()+"] logged out.");
                        competitionClient.userLoggedOut(user);
                    } catch (CompetitionException e) {
                        System.out.println("Error notifying friend " + e);
                    }
                });

        }
        executor.shutdown();
    }

    private void notifySavedParticipant() throws CompetitionException{
        for (Map.Entry<String, ICompetitionObserver> entry : loggedClients.entrySet()){
            entry.getValue().participantSaved();
        }
    }

    @Override
    public void logout(User user, ICompetitionObserver client) throws CompetitionException {
        ICompetitionObserver localClient=loggedClients.remove(user.getUsername());
        if (localClient==null)
            throw new CompetitionException("User "+user.getId()+" is not logged in.");
        notifyUsersLoggedOut(user);
    }

    public synchronized User[] getLoggedUsers(User user) throws CompetitionException {
        Iterable<User> users=userRepository.findAll();
        Set<User> result=new TreeSet<User>();
        System.out.println("Logged friends for: "+user.getId());
        for (User us : users){
            if (loggedClients.containsKey(us.getUsername())){    //the friend is logged in
                result.add(new User(user.getFirstname(), user.getLastname(), user.getUsername(), user.getPassword()));
                System.out.println("+"+user.getId());
            }
        }
        System.out.println("Size "+result.size());
        return result.toArray(new User[result.size()]);
    }

    public TestDTO[] findAllTestDTOs() throws CompetitionException {
        Iterable<Test> tests =  testRepository.findAll();

        List<TestDTO> testDTOs = new ArrayList<TestDTO>();

        for(Test test : tests){
            String type = test.getType().getType();
            TestAgeCategory testAgeCategory = test.getCategory();
            String ageCategory = String.valueOf(testAgeCategory.getMinAge()) + " - " + String.valueOf(testAgeCategory.getMaxAge());

            Iterable<Participant> participantsForTest = participantRepository.findAllParticipantsForTest(test.getId());
            int noCompetitors = ((List<Participant>)participantsForTest).size();

            TestDTO testDTO = new TestDTO(type, ageCategory, noCompetitors);
            testDTO.setId(test.getId());
            testDTOs.add(testDTO);
        }

        return testDTOs.toArray(new TestDTO[testDTOs.size()]);
    }

    public Participant[] findAllParticipantsForTest(Integer id) throws CompetitionException{
        List<Participant> participants = (List<Participant>) participantRepository.findAllParticipantsForTest(id);
        return participants.toArray(new Participant[participants.size()]);
    }

    @Override
    public synchronized User findUserByUsername(String username) throws CompetitionException {
        System.out.println("Find by username : " + username);
        User user = userRepository.findByUsername(username);
        return user;
    }

    @Override
    public synchronized Participant findParticipantByUsername(String username) throws CompetitionException {
        System.out.println("Find by username : " + username);
        Participant participant = participantRepository.findByUsername(username);
        return participant;
    }


    @Override
    public void saveParticipant(String username, String name, int age, int testId) throws CompetitionException {
        Participant participant = participantRepository.findByUsername(username);
        if(participant == null){
            Participant newParticipant = new Participant(username, name, age);
            participantRepository.save(newParticipant);
            return;
        }
        if(participant.getAge() != age || !participant.getName().equals(name)){
            throw new CompetitionException("Invalid username");
        }

        List<Test> testList = (List<Test>) testRepository.findAllTestsForParticipant(participant.getId());
        if(testList.size() >= 2){
            throw new CompetitionException("Test limit exception");
        }

        if(testList.get(0).getId().equals(testId)){
            throw new CompetitionException("Test joined exception");
        }
    }

    @Override
    public void saveRelation(int idTest, int idParticipant) throws CompetitionException {
        Tuple<Integer, Integer> id = new Tuple<>(idTest, idParticipant);
        TestParticipantRelation testParticipantRelation = new TestParticipantRelation(id);
        this.testParticipantRelationRepository.save(testParticipantRelation);
        notifySavedParticipant();
    }
}
