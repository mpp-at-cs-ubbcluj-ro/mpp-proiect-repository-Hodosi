using System;
using CSharp_ChildrenCompetition.model;

namespace CSharp_ChildrenCompetition.repository
{
    public class TestParticipantRelationRepository : AbstractRepository<Tuple<int, int>, TestParticipantRelation>
    {
        public TestParticipantRelationRepository(Validator<TestParticipantRelation> validator) : base(validator)
        {
            
        }
    }
}