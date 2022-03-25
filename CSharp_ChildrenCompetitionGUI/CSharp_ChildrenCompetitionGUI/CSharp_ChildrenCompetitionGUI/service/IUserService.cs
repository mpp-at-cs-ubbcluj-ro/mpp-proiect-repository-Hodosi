using CSharp_ChildrenCompetitionGUI.model;

namespace CSharp_ChildrenCompetitionGUI.service
{
    public interface IUserService<ID, T> where T : HasId<ID>
    {
        void login(string username, string password);
        void logout();
        T getCurrentUser();
    }
}