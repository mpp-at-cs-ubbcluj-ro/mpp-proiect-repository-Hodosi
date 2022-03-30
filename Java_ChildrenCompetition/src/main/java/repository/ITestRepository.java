package repository;

import model.Test;

//public interface ITestRepository<ID, T extends HasId<ID>> extends IRepository<ID, T>{
//    Iterable<T> findAllTestsForParticipant(int id);
//}

public interface ITestRepository extends IRepository<Integer, Test>{
    Iterable<Test> findAllTestsForParticipant(int id);
}