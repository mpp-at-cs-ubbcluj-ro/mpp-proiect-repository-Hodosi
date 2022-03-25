using CSharp_ChildrenCompetitionGUI.model;

namespace CSharp_ChildrenCompetitionGUI.service
{
    public interface ITestParticipantRelationService<ID, T> where T : HasId<ID>
    {
        void save(int idTest, int idParticipant);
    }
}