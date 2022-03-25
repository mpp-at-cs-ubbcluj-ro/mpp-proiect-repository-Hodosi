using System;
using CSharp_ChildrenCompetitionGUI.model;
using CSharp_ChildrenCompetitionGUI.repository;

namespace CSharp_ChildrenCompetitionGUI.service
{
    public class TestParticipantRelationService : ITestParticipantRelationService<Tuple<int, int>, TestParticipantRelation>
    {
        public ITestParticipantRelationRepository<Tuple<int, int>, TestParticipantRelation> testParticipantRelationRepository;

        public TestParticipantRelationService(ITestParticipantRelationRepository<Tuple<int, int>, TestParticipantRelation> testParticipantRelationRepository)
        {
            this.testParticipantRelationRepository = testParticipantRelationRepository;
        }

        public void save(int idTest, int idParticipant)
        {
            // throw new NotImplementedException();
            Tuple<int, int> id = new Tuple<int, int>(idTest, idParticipant);
            TestParticipantRelation testParticipantRelation = new TestParticipantRelation(id);
            this.testParticipantRelationRepository.save(testParticipantRelation);
        }
    }
}