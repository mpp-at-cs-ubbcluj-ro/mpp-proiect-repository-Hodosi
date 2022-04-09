using CSharp_ChildrenCompetitionGUI.model;

namespace services
{
    public interface ICompetitionObserver
    {
        void userLoggedIn(User user);
        void userLoggedOut(User user);
        void participantSaved();
    }
}