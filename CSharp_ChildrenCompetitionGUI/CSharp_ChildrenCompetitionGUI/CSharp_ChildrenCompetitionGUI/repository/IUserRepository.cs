using CSharp_ChildrenCompetitionGUI.model;

namespace CSharp_ChildrenCompetitionGUI.repository
{
    public interface IUserRepository <ID, T> : IRepository<ID, T> where T : HasId<ID>
    {
        void setCurrentUser(T entity);
        T getCurrentUser();
        T findByUsername(string username);
    }
}