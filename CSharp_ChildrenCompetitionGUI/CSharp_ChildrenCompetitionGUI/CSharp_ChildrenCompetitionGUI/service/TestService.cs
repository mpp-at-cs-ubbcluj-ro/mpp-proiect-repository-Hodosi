using System.Collections.Generic;
using CSharp_ChildrenCompetitionGUI.model;
using CSharp_ChildrenCompetitionGUI.repository;

namespace CSharp_ChildrenCompetitionGUI.service
{
    public class TestService : ITestService<int, Test>
    {
        public ITestRepository<int, Test> testRepository;
        public IParticipantRepository<int, Participant> participantRepository;

        public TestService(ITestRepository<int, Test> testRepository, IParticipantRepository<int, Participant> participantRepository)
        {
            this.testRepository = testRepository;
            this.participantRepository = participantRepository;
        }

        public List<TestDTO> findAllTestDTOs()
        {
            // throw new System.NotImplementedException();
            IEnumerable<Test> tests = testRepository.findAll();

            List<TestDTO> testDtos = new List<TestDTO>();

            foreach (Test test in tests)
            {
                string type = test.type.type;
                TestAgeCategory testAgeCategory = test.category;
                string ageCategory = testAgeCategory.minAge.ToString() + " - " + testAgeCategory.maxAge.ToString();

                IEnumerable<Participant> participantsForTest =
                    participantRepository.findAllParticipantsForTest(test.id);

                int noCompetitors = ((List<Participant>) participantsForTest).Count;

                TestDTO testDTO = new TestDTO(type, ageCategory, noCompetitors);
                testDTO.id = test.id;
                testDtos.Add(testDTO);
            }

            return testDtos;
        }
    }
}