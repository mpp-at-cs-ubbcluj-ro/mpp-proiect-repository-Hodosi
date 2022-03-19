package repository;

import model.HasId;
import model.Test;

public interface ITestRepository<ID, T extends HasId<ID>> extends IRepository<ID, T>{
    Iterable<T> findAllTestsForParticipant(int id);
}
