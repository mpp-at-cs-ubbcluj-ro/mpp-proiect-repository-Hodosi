using CSharp_ChildrenCompetitionGUI.model;

namespace CSharp_ChildrenCompetitionGUI.repository
{
    public interface IUserRepository : IRepository<int, User>
    {
        void setCurrentUser(User entity);
        User getCurrentUser();
        User findByUsername(string username);
    }
}