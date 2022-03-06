using CSharp_ChildrenCompetition.model;

namespace CSharp_ChildrenCompetition.repository
{
    public class ParticipantRepository : AbstractRepository<int, Participant>
    {
        public ParticipantRepository(Validator<Participant> validator) : base(validator)
        {
            
        }
    }
}