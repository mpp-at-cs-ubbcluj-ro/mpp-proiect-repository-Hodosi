using CSharp_ChildrenCompetitionGUI.model;

namespace CSharp_ChildrenCompetitionGUI.repository
{
    public interface ITestTypeRepository <ID, T> : IRepository<ID, T> where T : HasId<ID>
    {
        
    }
}