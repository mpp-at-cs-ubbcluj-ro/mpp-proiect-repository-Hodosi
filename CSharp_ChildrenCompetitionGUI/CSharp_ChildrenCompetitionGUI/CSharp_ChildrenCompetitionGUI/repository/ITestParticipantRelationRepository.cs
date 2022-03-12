using System.Collections.Generic;
using CSharp_ChildrenCompetitionGUI.model;

namespace CSharp_ChildrenCompetitionGUI.repository
{
    public interface ITestParticipantRelationRepository <ID, T> : IRepository<ID, T> where T : HasId<ID>
    {
        IEnumerable<int> findAllParticipantsIDForTest(int id);
    }
}