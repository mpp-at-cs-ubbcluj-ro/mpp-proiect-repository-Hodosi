package service;

import model.HasId;

import java.util.List;

public interface IParticipantService  <ID, T extends HasId<ID>>{
    void save(String username, String name, int age, int testId);
    T findByUsername(String username);
    List<T> findAllParticipantsForTest(Integer id);
}
