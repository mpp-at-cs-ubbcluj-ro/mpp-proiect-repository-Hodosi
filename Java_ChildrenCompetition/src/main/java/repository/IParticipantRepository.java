package repository;

import model.HasId;

public interface IParticipantRepository<ID, T extends HasId<ID>> extends IRepository<ID, T>{
    T findByName(String name);
}
