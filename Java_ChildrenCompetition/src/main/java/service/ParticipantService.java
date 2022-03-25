package service;

import exception.InvalidUsernameException;
import exception.TestJoinedException;
import exception.TestLimitException;
import model.Participant;
import model.Test;
import repository.IParticipantRepository;
import repository.ITestRepository;

import java.util.List;

public class ParticipantService implements IParticipantService<Integer, Participant> {
    public final IParticipantRepository<Integer, Participant> participantRepository;
    public final ITestRepository<Integer, Test> testRepository;

    public ParticipantService(IParticipantRepository<Integer, Participant> participantRepository, ITestRepository<Integer, Test> testRepository) {
        this.participantRepository = participantRepository;
        this.testRepository = testRepository;
    }

    @Override
    public void save(String username, String name, int age, int testId) {
        Participant participant = participantRepository.findByUsername(username);
        if(participant == null){
            Participant newParticipant = new Participant(username, name, age);
            participantRepository.save(newParticipant);
            return;
        }
        if(participant.getAge() != age || !participant.getName().equals(name)){
            throw new InvalidUsernameException();
        }

        List<Test> testList = (List<Test>) testRepository.findAllTestsForParticipant(participant.getId());
        if(testList.size() >= 2){
            throw new TestLimitException();
        }

        if(testList.get(0).getId().equals(testId)){
            throw new TestJoinedException();
        }
    }

    @Override
    public Participant findByUsername(String username) {
        return participantRepository.findByUsername(username);
    }


    @Override
    public List<Participant> findAllParticipantsForTest(Integer id) {
        return (List<Participant>) participantRepository.findAllParticipantsForTest(id);
    }
}
