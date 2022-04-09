using System.Collections.Generic;
using CSharp_ChildrenCompetitionGUI.model;

namespace CSharp_ChildrenCompetitionGUI.repository
{
    public interface IParticipantRepository : IRepository<int, Participant>
    {
        Participant findByUsername(string username);
        IEnumerable<Participant> findAllParticipantsForTest(int id);
    }
}