package service;

import model.HasId;

import java.util.List;

public interface IParticipantService  <ID, T extends HasId<ID>>{
    void save(String name, int age, int testId);
    T findByName(String name);
    List<T> findAllParticipantsForTest(Integer id);
}
