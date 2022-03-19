package service;

import model.*;
import repository.IParticipantRepository;
import repository.ITestRepository;

import java.util.ArrayList;
import java.util.List;


public class TestService implements ITestService<Integer, Test> {
    public final ITestRepository<Integer, Test> testRepository;
    public final IParticipantRepository<Integer, Participant> participantRepository;

    public TestService(ITestRepository<Integer, Test> testRepository, IParticipantRepository<Integer, Participant> participantRepository) {
        this.testRepository = testRepository;
        this.participantRepository = participantRepository;
    }


    @Override
    public List<TestDTO> findAllTestDTOs() {
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

        return testDTOs;
    }
}
