using System.Collections.Generic;
using CSharp_ChildrenCompetition.repository;
using CSharp_ChildrenCompetitionGUI.model;
using CSharp_ChildrenCompetitionGUI.repository;

namespace CSharp_ChildrenCompetitionGUI.service
{
    public class ParticipantService : IParticipantService<int, Participant>
    {
        public IParticipantRepository<int, Participant> participantRepository;
        public ITestRepository<int, Test> testRepository;

        public ParticipantService(IParticipantRepository<int, Participant> participantRepository, ITestRepository<int, Test> testRepository)
        {
            this.participantRepository = participantRepository;
            this.testRepository = testRepository;
        }

        public void save(string username, string name, int age, int testId)
        {
            // throw new System.NotImplementedException();
            Participant participant = participantRepository.findByUsername(username);
            if (participant == null)
            {
                Participant newParticipant = new Participant(username, name, age);
                participantRepository.save(newParticipant);
                return;
            }

            if (participant.age != age || !participant.name.Equals(name))
            {
                throw new InvalidUsernameException();
            }

            List<Test> testList = (List<Test>)testRepository.findAllTestsForParticipant(participant.id);
            if (testList.Count >= 2)
            {
                throw new TestLimitException();
            }

            if (testList[0].id.Equals(testId))
            {
                throw new TestJoinedException();
            }
        }

        public Participant findByUsername(string username)
        {
            // throw new System.NotImplementedException();
            return participantRepository.findByUsername(username);
        }

        public List<Participant> findAllParticipantsForTest(int id)
        {
            // throw new System.NotImplementedException();
            return (List<Participant>) participantRepository.findAllParticipantsForTest(id);
        }
    }
}