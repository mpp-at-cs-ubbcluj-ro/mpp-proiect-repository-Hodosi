package competition.services;

import competition.model.User;

public interface ICompetitionObserver {
    void userLoggedIn(User user) throws CompetitionException;
    void userLoggedOut(User user) throws CompetitionException;
    void participantSaved() throws CompetitionException;
}
