using System.Collections.Generic;
using CSharp_ChildrenCompetitionGUI.model;

namespace CSharp_ChildrenCompetitionGUI.service
{
    public interface IParticipantService<ID, T> where T : HasId<ID>
    {
        void save(string username, string name, int age, int testId);
        T findByUsername(string username);
        List<T> findAllParticipantsForTest(int id);
    }
}