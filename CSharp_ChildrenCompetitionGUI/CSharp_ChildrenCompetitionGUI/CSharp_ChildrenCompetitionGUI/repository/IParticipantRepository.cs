using CSharp_ChildrenCompetitionGUI.model;

namespace CSharp_ChildrenCompetitionGUI.repository
{
    public interface IParticipantRepository <ID, T> : IRepository<ID, T> where T : HasId<ID>
    {
        T findByName(string name);
    }
}