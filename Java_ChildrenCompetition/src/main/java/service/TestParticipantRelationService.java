package service;

import model.TestParticipantRelation;
import model.Tuple;
import repository.ITestParticipantRelationRepository;

public class TestParticipantRelationService implements ITestParticipantRelationService {
    public final ITestParticipantRelationRepository testParticipantRelationRepository;

    public TestParticipantRelationService(ITestParticipantRelationRepository testParticipantRelationRepository) {
        this.testParticipantRelationRepository = testParticipantRelationRepository;
    }

    @Override
    public void save(int idTest, int idParticipant) {
        Tuple<Integer, Integer> id = new Tuple<>(idTest, idParticipant);
        TestParticipantRelation testParticipantRelation = new TestParticipantRelation(id);
        this.testParticipantRelationRepository.save(testParticipantRelation);
    }
}
