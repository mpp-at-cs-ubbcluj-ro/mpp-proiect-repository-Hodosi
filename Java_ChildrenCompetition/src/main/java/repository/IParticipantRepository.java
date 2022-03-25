package repository;

import model.HasId;

public interface IParticipantRepository<ID, T extends HasId<ID>> extends IRepository<ID, T>{
    T findByUsername(String username);
    Iterable<T> findAllParticipantsForTest(int id);
}
