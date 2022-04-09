using System;
using CSharp_ChildrenCompetitionGUI.model;

namespace services
{
    public interface ICompetitionServices
    {
        void login(User user, ICompetitionObserver client);
        void logout(User user, ICompetitionObserver client);
        User findUserByUsername(String username);
        Participant findParticipantByUsername(String username);
        void saveParticipant(String username, String name, int age, int testId);
        void saveRelation(int idTest, int idParticipant);
        User[] getLoggedUsers(User user);
        TestDTO[] findAllTestDTOs();
        Participant[] findAllParticipantsForTest(int id);
    }
}