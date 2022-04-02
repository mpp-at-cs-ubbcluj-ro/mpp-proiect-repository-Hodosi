package competition.services;

import competition.model.Participant;
import competition.model.TestDTO;
import competition.model.User;

public interface ICompetitionServices {
    void login(User user, ICompetitionObserver client) throws CompetitionException;
    void logout(User user, ICompetitionObserver client) throws CompetitionException;
    User findUserByUsername(String username) throws CompetitionException;
    Participant findParticipantByUsername(String username) throws CompetitionException;
    void saveParticipant(String username, String name, int age, int testId) throws CompetitionException;
    void saveRelation(int idTest, int idParticipant) throws CompetitionException;
    TestDTO[] findAllTestDTOs() throws CompetitionException;
    Participant[] findAllParticipantsForTest(Integer id) throws CompetitionException;
}
