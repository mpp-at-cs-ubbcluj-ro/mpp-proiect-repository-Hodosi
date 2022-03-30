package service;

import model.Participant;

import java.util.List;

//public interface IParticipantService  <ID, T extends HasId<ID>>{
//    void save(String username, String name, int age, int testId);
//    T findByUsername(String username);
//    List<T> findAllParticipantsForTest(Integer id);
//}

public interface IParticipantService {
    void save(String username, String name, int age, int testId);
    Participant findByUsername(String username);
    List<Participant> findAllParticipantsForTest(Integer id);
}
